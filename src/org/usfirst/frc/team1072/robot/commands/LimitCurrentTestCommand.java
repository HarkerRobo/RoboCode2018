package org.usfirst.frc.team1072.robot.commands;

import org.usfirst.frc.team1072.robot.Robot;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Limits current spikes as they happen by changing a large amount of current
 * through the Talon to a normal amount.
 *
 */
public class LimitCurrentTestCommand extends Command {
	
	double maxCurrent;
	boolean pastMaxCurrent;
	
	public LimitCurrentTestCommand() {
		requires(Robot.gears);
		maxCurrent = 17;
		pastMaxCurrent = false;
		
	}
	
	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.gears.getRollers().set(ControlMode.PercentOutput, 1);
		SmartDashboard.putNumber("max intake current", maxCurrent);
	}
	
	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		if(Robot.gears.getRollers().getOutputCurrent() > SmartDashboard.getNumber("max intake current",
				Robot.gears.getRollers().getOutputCurrent())) {
			pastMaxCurrent = true;
		}
	}
	
	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		if(pastMaxCurrent && System.currentTimeMillis() > 1000) {
			return true;
		}
		return false;
	}
	
	// Called once after isFinished returns true
	protected void end() {
		Robot.gears.getRollers().set(ControlMode.PercentOutput, 0);
	}
	
	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
	
}
