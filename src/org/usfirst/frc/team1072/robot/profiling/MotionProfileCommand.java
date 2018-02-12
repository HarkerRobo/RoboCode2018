package org.usfirst.frc.team1072.robot.profiling;

import org.usfirst.frc.team1072.robot.profiling.MotionProfileBuilder.Group;

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
public class MotionProfileCommand extends Command {
	
	private int period;
	private Notifier notifier;
	private Group[] groups;
	private Status[] statuses;
	private MotionProfileStatus status;
	
	/**
	 * Creates a new motion profile command (not intended for use, create a
	 * builder instead)
	 */
	public MotionProfileCommand(int period, Group[] groups, Subsystem[] required) {
		this.period = period;
		this.groups = groups;
		for(Subsystem s : required)
			requires(s);
		notifier = new Notifier(this::processBuffer);
		statuses = new Status[groups.length];
	}
	
	// Called just before this Command runs the first time
	protected void initialize() {
		status = new MotionProfileStatus();
		for(int i = 0; i < groups.length; i++) {
			statuses[i] = new Status(groups[i].getTargets().length);
			for(TalonSRX target : groups[i].getTargets()) {
				// Disable the motors so nothing happens while loading
				target.set(ControlMode.MotionProfile, SetValueMotionProfile.Disable.value);
				// Clear any data from previous motion profiling
				target.clearMotionProfileHasUnderrun(0);
				target.clearMotionProfileTrajectories();
				// Set the period, as well as a variety of status periods
				// recommended to be half of the period
				target.changeMotionControlFramePeriod(Math.max(1, period / 2));
				target.configMotionProfileTrajectoryPeriod(period, 0);
				target.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, Math.max(1, period / 2), 0);
				// Each constant can be configured at the start of the command
				// or preferably set in advance and then passed as NaN
				if(!Double.isNaN(groups[i].getDeadband()))
					target.configNeutralDeadband(groups[i].getDeadband(), 0);
				if(!Double.isNaN(groups[i].getF()))
					target.config_kF(groups[i].getProfileSlot(), groups[i].getF(), 0);
				if(!Double.isNaN(groups[i].getP()))
					target.config_kP(groups[i].getProfileSlot(), groups[i].getP(), 0);
				if(!Double.isNaN(groups[i].getI()))
					target.config_kI(groups[i].getProfileSlot(), groups[i].getI(), 0);
				if(!Double.isNaN(groups[i].getD()))
					target.config_kD(groups[i].getProfileSlot(), groups[i].getD(), 0);
			}
		}
		loadPoints();
		for(Group g : groups)
			for(TalonSRX target : g.getTargets())
				target.set(ControlMode.MotionProfile, SetValueMotionProfile.Enable.value);
		// Notify talons to process points, again half of the period
		// (milliseconds -> seconds)
		notifier.startPeriodic(period / 2000.0);
	}
	
	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		loadPoints();
	}
	
	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		for(int i = 0; i < statuses.length; i++)
			for(int j = 0; j < statuses[i].loadNext.length; j++)
				if(statuses[i].loadNext[j] != groups[i].getTrajectory().length())
					return false;
		for(Group g : groups)
			for(TalonSRX t : g.getTargets()) {
				t.getMotionProfileStatus(status);
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
				target.set(ControlMode.Disabled, 0);
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
					// Convert a Pathfinder trajectory segment to a Phoenix
					// trajectory point
					TrajectoryPoint tp = new TrajectoryPoint();
					tp.position = trajectory.segments[statuses[i].loadNext[j]].position
							/ groups[i].getDistancePerRotation() * groups[i].getUnitsPerRotation();
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
}
