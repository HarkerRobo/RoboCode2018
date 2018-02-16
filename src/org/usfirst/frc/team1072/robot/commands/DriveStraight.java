package org.usfirst.frc.team1072.robot.commands;

import org.usfirst.frc.team1072.robot.Robot;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.TimedCommand;

/**
 *
 */
public class DriveStraight extends TimedCommand {
	
	public DriveStraight() {
		super(3.0);
		requires(Robot.drivetrain);
	}
	
	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.drivetrain.getLeft().setSelectedSensorPosition(0, 0, 100);
		Robot.drivetrain.getRight().setSelectedSensorPosition(0, 0, 100);
		Robot.drivetrain.getLeft().set(ControlMode.PercentOutput, 1.0);
		Robot.drivetrain.getRight().set(ControlMode.PercentOutput, 1.0);
	}
	
	// Called once after isFinished returns true
	protected void end() {
		Robot.drivetrain.set((talon) -> talon.set(ControlMode.Disabled, 0));
		System.out.println(
				"Drove for 1.0 seconds, moved " + (Robot.drivetrain.getLeft().getSelectedSensorPosition(0))
						+ ", " + (Robot.drivetrain.getRight().getSelectedSensorPosition(0)));
	}
	
	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
