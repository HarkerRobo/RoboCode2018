package org.usfirst.frc.team1072.robot.commands;

import org.usfirst.frc.team1072.robot.Robot;

import com.ctre.phoenix.motion.MotionProfileStatus;
import com.ctre.phoenix.motion.SetValueMotionProfile;
import com.ctre.phoenix.motion.TrajectoryPoint;
import com.ctre.phoenix.motion.TrajectoryPoint.TrajectoryDuration;
import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.command.Command;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Trajectory.Segment;
import jaci.pathfinder.modifiers.TankModifier;

/**
 * Follows a Pathfinder trajectory by adding the segments to the talon's
 * internal motion profiling buffer
 */
public class PathfinderCommand extends Command {
	
	public static final double ROBOT_WIDTH = 1;
	public static final int PATHFINDER_PROFILE = 3;
	
	private String name;
	private static TrajectoryPoint trajPoint = new TrajectoryPoint();
	
	private Trajectory traj;
	private int index;
	private MotionProfileStatus status = new MotionProfileStatus();
	private Notifier notifier;
	
	public PathfinderCommand(Trajectory traj) {
		requires(Robot.gearIntake);
		this.traj = traj;
	}
	
	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.gearIntake.getOrientation().clearMotionProfileHasUnderrun(0);
		Robot.gearIntake.getOrientation().clearMotionProfileTrajectories();
		Robot.gearIntake.getOrientation().config_kP(PATHFINDER_PROFILE, 1.0, 0);
		Robot.gearIntake.getOrientation().changeMotionControlFramePeriod(5);
		Robot.gearIntake.getOrientation().configMotionProfileTrajectoryPeriod(10, 0);
		notifier = new Notifier(() -> Robot.gearIntake.getOrientation().processMotionProfileBuffer());
		loadTrajectories();
		Robot.gearIntake.getOrientation().set(ControlMode.MotionProfile, SetValueMotionProfile.Enable.value);
		notifier.startPeriodic(0.005);
	}
	
	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		loadTrajectories();
	}
	
	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		Robot.gearIntake.getOrientation().getMotionProfileStatus(status);
		if(!status.activePointValid) {
			System.out.println("invalid active point");
		}
		if(status.isUnderrun) {
			System.out.println("talon buffer underrun");
		}
		System.out.println("mode: " + status.outputEnable);
		System.out.println("bottom buffer: " + status.btmBufferCnt);
		System.out.println("top buffer: " + status.topBufferCnt);
		if(status.isLast) {
			System.out.println("pathfinder command ending");
		}
		return index == traj.segments.length && status.isLast;
	}
	
	// Called once after isFinished returns true
	protected void end() {
		notifier.stop();
		Robot.gearIntake.getOrientation().set(ControlMode.MotionProfile, SetValueMotionProfile.Disable.value);
	}
	
	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		Robot.gearIntake.getOrientation().set(ControlMode.MotionProfile, SetValueMotionProfile.Disable.value);
	}
	
	public void loadTrajectories() {
		while(!Robot.gearIntake.getOrientation().isMotionProfileTopLevelBufferFull() && index < traj.segments.length) {
			TrajectoryPoint p = toTrajPoint(traj.segments[index], index == 0, ++index == traj.segments.length);
			System.out.println("pushing trajectory: " + p);
			if(p == null) {
				System.out.println("null trajectory");
				System.out.println("came from:");
				System.out.println(traj.segments[index - 1]);
			}
			Robot.gearIntake.getOrientation().pushMotionProfileTrajectory(p);
					
		}
	}
	
	public static TrajectoryPoint toTrajPoint(Segment seg, boolean first, boolean last) {
		if(seg == null) {
			System.out.println("err: asked to translate null segment");
		}
		trajPoint = new TrajectoryPoint();
		trajPoint.headingDeg = seg.heading; // TODO check if radians
		trajPoint.isLastPoint = last;
		trajPoint.position = seg.position;
		trajPoint.profileSlotSelect0 = PATHFINDER_PROFILE;
		trajPoint.velocity = seg.velocity;
		trajPoint.zeroPos = first;
		trajPoint.timeDur = TrajectoryDuration.Trajectory_Duration_0ms;
		return trajPoint;
	}
}
