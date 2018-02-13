package org.usfirst.frc.team1072.robot.commands;

import org.usfirst.frc.team1072.robot.Robot;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.TimedCommand;

/**
 *
 */
public class EjectCommand extends TimedCommand {
	
	public EjectCommand() {
		super(0.5);
		requires(Robot.intake);
	}
	
	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.intake.set((talon) -> talon.set(ControlMode.PercentOutput, -0.5));
	}
	
	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
	}
	
	// Called once after timeout
	protected void end() {
		Robot.intake.set((talon) -> talon.set(ControlMode.Disabled, 0));
	}
	
	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
