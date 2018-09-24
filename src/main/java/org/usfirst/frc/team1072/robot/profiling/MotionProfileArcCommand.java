package org.usfirst.frc.team1072.robot.profiling;

import org.usfirst.frc.team1072.robot.profiling.MotionProfileBuilder.Group;

import com.ctre.phoenix.ErrorCode;
import com.ctre.phoenix.motion.MotionProfileStatus;
import com.ctre.phoenix.motion.SetValueMotionProfile;
import com.ctre.phoenix.motion.TrajectoryPoint;
import com.ctre.phoenix.motion.TrajectoryPoint.TrajectoryDuration;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
import jaci.pathfinder.Trajectory;

/**
 * Runs motion profiles
 */
public class MotionProfileArcCommand extends Command {
	
	/*
	 * Cut off the command when a certain number of points are still remaining to prevent drift, set to -1 to disable
	 */
	public static final int END_EARLY = 50;
	
	public static final int MIN_POINTS = 128;
	
	private int period;
	private Notifier notifier;
	private Group[] groups;
	private Status[] statuses;
	private MotionProfileStatus status;
	private boolean started;
	
	/**
	 * Creates a new motion profile command (not intended for use, create a
	 * builder instead)
	 */
	public MotionProfileArcCommand(int period, Group[] groups, Subsystem[] required) {
		this.period = period;
		this.groups = groups;
		for(Subsystem s : required)
			requires(s);
		notifier = new Notifier(this::processBuffer);
		statuses = new Status[groups.length];
	}
	
	// Called just before this Command runs the first time
	protected void initialize() {
		System.err.println("Initializing MP Command");
		status = new MotionProfileStatus();
		started = false;
		for(int i = 0; i < groups.length; i++) {
			statuses[i] = new Status(groups[i].getTargets().length);
			for(TalonSRX target : groups[i].getTargets()) {
				// Disable the motors so nothing happens while loading
				target.set(ControlMode.MotionProfile, SetValueMotionProfile.Disable.value);
				// Clear any data from previous motion profiling
				log(target.clearMotionProfileHasUnderrun(1000), "Could not clear underrun");
				log(target.clearMotionProfileTrajectories(), "Could not clear trajectories");
				log(target.setIntegralAccumulator(0, 0, 1000), "Could not clear accumulator");
				log(target.setSelectedSensorPosition(0, 0, 1000), "Could not clear position");
				// Set the period, as well as a variety of status periods
				// recommended to be half of the period
				target.changeMotionControlFramePeriod(Math.max(1, period / 2));
				log(target.configMotionProfileTrajectoryPeriod(period, 1000), "Could not configure period");
				target.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, Math.max(1, period / 2), 1000);
				// Each constant can be configured at the start of the command
				// or preferably set in advance and then passed as NaN
				if(!Double.isNaN(groups[i].getF()))
					target.config_kF(groups[i].getProfileSlot(), groups[i].getF(), 0);
				if(!Double.isNaN(groups[i].getP()))
					target.config_kP(groups[i].getProfileSlot(), groups[i].getP(), 0);
				if(!Double.isNaN(groups[i].getI()))
					target.config_kI(groups[i].getProfileSlot(), groups[i].getI(), 0);
				if(!Double.isNaN(groups[i].getD()))
					target.config_kD(groups[i].getProfileSlot(), groups[i].getD(), 0);
				if(groups[i].getIntegralZone() != -1)
					target.config_IntegralZone(groups[i].getProfileSlot(), groups[i].getIntegralZone(), 0);
				if(!Double.isNaN(groups[i].getMaxIntegral()))
					target.configMaxIntegralAccumulator(groups[i].getProfileSlot(), groups[i].getMaxIntegral(), 0);
				if(groups[i].getAllowableError() != -1)
					target.configAllowableClosedloopError(groups[i].getProfileSlot(), groups[i].getAllowableError(), 0);
				target.selectProfileSlot(groups[i].getProfileSlot(), 0);
				target.processMotionProfileBuffer();
			}
		}
		loadPoints();
		// Notify talons to process points, again half of the period
		// (milliseconds -> seconds)
		notifier.startPeriodic(period / 2000.0);
		System.err.println("Finished initializing MP Command");
	}
	
	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		loadPoints();
		if(!started) {
			boolean shouldStart = true;
			for(Group g: groups) {
				for(TalonSRX t: g.getTargets()) {
					log(t.getMotionProfileStatus(status), "Could not get status");
					shouldStart = shouldStart && status.btmBufferCnt >= MIN_POINTS;
				}
			}
			if(shouldStart) {
				started = true;
				for(Group g : groups)
					for(TalonSRX target : g.getTargets())
						target.set(ControlMode.MotionProfile, SetValueMotionProfile.Enable.value);
				System.out.println("Started MP Command");
			}
		}
	}
	
	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		if(!started) {
			return false;
		}
		for(int i = 0; i < statuses.length; i++)
			for(int j = 0; j < statuses[i].loadNext.length; j++)
				if(statuses[i].loadNext[j] != groups[i].getTrajectory().length())
					return false;
		for(Group g : groups)
			for(TalonSRX t : g.getTargets()) {
				t.getMotionProfileStatus(status);
				if(status.hasUnderrun) {
					System.err.println("Underrun!");
				}
				if(status.btmBufferCnt + status.topBufferCnt < END_EARLY) {
					return true;
				}
				if(!status.activePointValid || !status.isLast)
					return false;
			}
		return true;
	}
	
	// Called once after isFinished returns true
	protected void end() {
		disable();
	}
	
	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		disable();
	}
	
	/*
	 * Stops all talons
	 */
	private void disable() {
		notifier.stop();
		for(Group g : groups)
			for(TalonSRX target : g.getTargets())
				target.set(ControlMode.PercentOutput, 0);
	}
	
	/**
	 * Fills the top buffer of every talon to make sure there are enough points
	 * to prevent underflow
	 */
	private void loadPoints() {
		for(int i = 0; i < groups.length; i++) {
			Trajectory trajectory = groups[i].getTrajectory();
			for(int j = 0; j < groups[i].getTargets().length; j++) {
				TalonSRX target = groups[i].getTargets()[j];
				while(statuses[i].loadNext[j] < trajectory.segments.length
						&& !target.isMotionProfileTopLevelBufferFull()) {
					// Convert a Pathfinder trajectory segment to a Phoenix trajectory point
					TrajectoryPoint tp = new TrajectoryPoint();
					tp.position = trajectory.segments[statuses[i].loadNext[j]].position
							/ groups[i].getDistancePerRotation() * groups[i].getUnitsPerRotation() * groups[i].getEncoderFailureMeme();
					tp.velocity = trajectory.segments[statuses[i].loadNext[j]].velocity
							/ groups[i].getDistancePerRotation() * groups[i].getUnitsPerRotation() / 10.0;
					tp.headingDeg = trajectory.segments[statuses[i].loadNext[j]].heading * 180.0 / Math.PI;
					tp.profileSlotSelect0 = groups[i].getProfileSlot();
					tp.profileSlotSelect1 = 0;
					tp.timeDur = TrajectoryDuration.Trajectory_Duration_0ms;
					tp.zeroPos = statuses[i].loadNext[j] == 0;
					tp.isLastPoint = ++statuses[i].loadNext[j] == trajectory.segments.length;
					target.pushMotionProfileTrajectory(tp);
				}
			}
		}
	}
	
	/**
	 * Make all talons process motion profile points
	 */
	private void processBuffer() {
		for(Group g : groups)
			for(TalonSRX talon : g.getTargets())
				talon.processMotionProfileBuffer();
	}
	
	/**
	 * Indicates how many of the points each talon has loaded into its top
	 * buffer
	 */
	class Status {
		/**
		 * talon i should have point loadNext[i] pushed into the top buffer when
		 * there is space
		 */
		private int[] loadNext;
		
		/**
		 * Create the status of a group following one trajectory
		 * 
		 * @param numTalons
		 *            the number of talons following that trajectory
		 */
		public Status(int numTalons) {
			loadNext = new int[numTalons];
		}
	}
	
	public boolean log(ErrorCode err, String value) {
		return log(err == ErrorCode.OK, value);
	}
	
	public boolean log(boolean good, String value) {
		if(!good)
			System.err.println("MP: " + value);
		return good;
	}

	/**
	 * @return the period
	 */
	public int getPeriod() {
		return period;
	}

	/**
	 * @return the notifier
	 */
	public Notifier getNotifier() {
		return notifier;
	}

	/**
	 * @return the groups
	 */
	public Group[] getGroups() {
		return groups;
	}

	/**
	 * @return the statuses
	 */
	public Status[] getStatuses() {
		return statuses;
	}

	/**
	 * @return the status
	 */
	public MotionProfileStatus getStatus() {
		return status;
	}

	/**
	 * @return the started
	 */
	public boolean isStarted() {
		return started;
	}
}
