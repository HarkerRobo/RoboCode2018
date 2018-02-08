package org.usfirst.frc.team1072.robot.commands;

import org.usfirst.frc.team1072.robot.OI;
import org.usfirst.frc.team1072.robot.Robot;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ArcadeDriveCommand extends Command {
	
	public static final double THRESHOLD = 0.15;
	
	public ArcadeDriveCommand() {
		requires(Robot.drivetrain);
	}
	
	// Called just before this Command runs the first time
	protected void initialize() {
	}
	
	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		double x = OI.gamepad.getLeftX(), y = OI.gamepad.getLeftY(),
				k = Math.max(1.0, Math.max(Math.abs(y + x * x), Math.abs(y - x * x)));
		if(Math.abs(x) < THRESHOLD && Math.abs(y) < THRESHOLD) {
			Robot.drivetrain.set((talon) -> talon.set(ControlMode.Disabled, 0));
		} else {
			Robot.drivetrain.getLeft().set(ControlMode.Velocity, 5000.0 * (y + x * Math.abs(x)) / k);
			Robot.drivetrain.getRight().set(ControlMode.Velocity, 5000.0 * (y - x * Math.abs(x)) / k);
		}
		
	}
	
	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}
	
	// Called once after isFinished returns true
	protected void end() {
		Robot.drivetrain.set((talon) -> talon.set(ControlMode.Disabled, 0));
	}
	
	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		Robot.drivetrain.set((talon) -> talon.set(ControlMode.Disabled, 0));
	}
}
