package org.usfirst.frc.team1072.robot.commands;

import org.usfirst.frc.team1072.robot.Robot;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class TestIntakeCommand extends Command {
	
	public static final double STALL_CURRENT = 13;
	
	public TestIntakeCommand() {
		requires(Robot.gearIntake);
	}
	
	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.gearIntake.getRollers().set(ControlMode.PercentOutput, 0.4);
	}
	
	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		SmartDashboard.putNumber("intake current", Robot.gearIntake.getRollers().getOutputCurrent());
		SmartDashboard.putNumber("Encoder value", Robot.gearIntake.getOrientation().getSelectedSensorVelocity(0));
		SmartDashboard.putNumber("Encoder position", Robot.gearIntake.getOrientation().getSelectedSensorPosition(0));
	}
	
	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return Robot.gearIntake.getRollers().getOutputCurrent() > STALL_CURRENT;
	}
	
	// Called once after isFinished returns true
	protected void end() {
		Robot.gearIntake.getRollers().set(ControlMode.PercentOutput, 0);
	}
	
	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
