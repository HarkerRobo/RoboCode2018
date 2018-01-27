package org.usfirst.frc.team1072.robot.commands;

import org.usfirst.frc.team1072.robot.Robot;

import com.ctre.phoenix.motion.MotionProfileStatus;
import com.ctre.phoenix.motion.SetValueMotionProfile;
import com.ctre.phoenix.motion.TrajectoryPoint;
import com.ctre.phoenix.motorcontrol.ControlMode;

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
	public static final int PATHFINDER_PROFILE = 0;
	
	private static TankModifier temp;
	private static TrajectoryPoint trajPoint;
	
	private Trajectory left, right;
	private int leftIndex, rightIndex;
	private MotionProfileStatus leftStatus, rightStatus;
	
	public PathfinderCommand(Trajectory left, Trajectory right) {
		requires(Robot.drivetrain);
		this.left = left;
		this.right = right;
	}
	
	public PathfinderCommand(Trajectory trajectory) {
		this((temp = new TankModifier(trajectory).modify(ROBOT_WIDTH)).getLeftTrajectory(), temp.getRightTrajectory());
	}
	
	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.drivetrain.set((talon) -> talon.clearMotionProfileTrajectories());
		loadTrajectories();
		Robot.drivetrain.set((talon) -> talon.set(ControlMode.MotionProfile, SetValueMotionProfile.Enable.value));
	}
	
	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		loadTrajectories();
	}
	
	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		Robot.drivetrain.left().getMotionProfileStatus(leftStatus);
		Robot.drivetrain.right().getMotionProfileStatus(rightStatus);
		return leftIndex == left.segments.length && rightIndex == right.segments.length && leftStatus.isLast
				&& rightStatus.isLast;
	}
	
	// Called once after isFinished returns true
	protected void end() {
		Robot.drivetrain.set((talon) -> talon.set(ControlMode.MotionProfile, SetValueMotionProfile.Disable.value));
	}
	
	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		Robot.drivetrain.set((talon) -> talon.set(ControlMode.MotionProfile, SetValueMotionProfile.Disable.value));
	}
	
	public void loadTrajectories() {
		while(!Robot.drivetrain.left().isMotionProfileTopLevelBufferFull()) {
			Robot.drivetrain.left().pushMotionProfileTrajectory(
					toTrajPoint(left.segments[leftIndex], leftIndex == 0, ++leftIndex == left.segments.length));
		}
		while(!Robot.drivetrain.right().isMotionProfileTopLevelBufferFull()) {
			Robot.drivetrain.right().pushMotionProfileTrajectory(
					toTrajPoint(right.segments[rightIndex], rightIndex == 0, ++rightIndex == right.segments.length));
		}
	}
	
	public static TrajectoryPoint toTrajPoint(Segment seg, boolean first, boolean last) {
		trajPoint.headingDeg = seg.heading; // TODO check if radians
		trajPoint.isLastPoint = last;
		trajPoint.position = seg.position;
		trajPoint.profileSlotSelect0 = PATHFINDER_PROFILE;
		trajPoint.velocity = seg.velocity;
		trajPoint.zeroPos = first;
		return trajPoint;
	}
}
