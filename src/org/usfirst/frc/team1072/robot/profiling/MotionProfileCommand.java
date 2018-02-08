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
 *
 */
public class MotionProfileCommand extends Command {
	
	private int period;
	private Notifier notifier;
	private Group[] groups;
	private Status[] statuses;
	private MotionProfileStatus status;
	
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
				target.set(ControlMode.MotionProfile, SetValueMotionProfile.Disable.value);
				target.clearMotionProfileHasUnderrun(0);
				target.clearMotionProfileTrajectories();
				target.changeMotionControlFramePeriod(Math.max(1, period / 2));
				target.configMotionProfileTrajectoryPeriod(period, 0);
				target.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, Math.max(1, period / 2), 0);
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
	
	private void disable() {
		notifier.stop();
		for(Group g : groups)
			for(TalonSRX target : g.getTargets())
				target.set(ControlMode.Disabled, 0);
	}
	
	private void loadPoints() {
		for(int i = 0; i < groups.length; i++) {
			Trajectory trajectory = groups[i].getTrajectory();
			for(int j = 0; j < groups[i].getTargets().length; j++) {
				TalonSRX target = groups[i].getTargets()[j];
				while(statuses[i].loadNext[j] < trajectory.segments.length
						&& !target.isMotionProfileTopLevelBufferFull()) {
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
					//System.out.println("pushed pos = " + tp.position + " vel = " + tp.velocity);
				}
			}
		}
	}
	
	private void processBuffer() {
		for(Group g : groups)
			for(TalonSRX talon : g.getTargets())
				talon.processMotionProfileBuffer();
	}
	
	class Status {
		private int[] loadNext;
		
		public Status(int numTalons) {
			loadNext = new int[numTalons];
		}
	}
}
