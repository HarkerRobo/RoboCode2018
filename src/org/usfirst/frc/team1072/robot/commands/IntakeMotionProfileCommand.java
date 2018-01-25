package org.usfirst.frc.team1072.robot.commands;

import org.usfirst.frc.team1072.robot.Robot;

import com.ctre.phoenix.motion.TrajectoryPoint;
import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.Command;

public class IntakeMotionProfileCommand extends Command{
	
	TrajectoryPoint [] traj;
	int [] degrees = {45,-45};
	//These need to be the same length, as traj will take parallel elements from degrees to determine what
	//heading to turn to
	boolean MPFinished;
	
	public IntakeMotionProfileCommand() {
		requires(Robot.gearIntake);
		traj = new TrajectoryPoint [2];
		MPFinished = false;
	}
	
	protected void initDefaultCommand() {
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		for (int i = 0; i < traj.length; i++) {
			traj[i] = new TrajectoryPoint();
			traj[i].position = 0;
			traj[i].headingDeg = degrees[i]; //My theory is that we only need one of these three values to move
			traj[i].velocity = 0;
			Robot.gearIntake.getOrientation().pushMotionProfileTrajectory(traj[i]);
		}
	}
	
	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		Robot.gearIntake.getRollers().set(ControlMode.MotionProfile,1);
		MPFinished = true;
		//For above, use 0 for disable, 1 for enable, 2 for hold
	}
	
	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return MPFinished;
	}
	
	// Called once after timeout
	protected void end() {
		Robot.gearIntake.getRollers().set(ControlMode.Disabled, 0);
	}
	
	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
