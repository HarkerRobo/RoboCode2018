package org.usfirst.frc.team1072.robot.commands;

import org.usfirst.frc.team1072.robot.Robot;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class VariableSpeedCommand extends Command {
	
	private double def;
	private String name;

    public VariableSpeedCommand(double def, String name) {
        requires(Robot.intake);
        this.def = def;
        this.name = name;
        SmartDashboard.putNumber(name + " speed", def);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    		Robot.intake.set((talon) -> talon.set(ControlMode.PercentOutput, SmartDashboard.getNumber(name + " speed", def)));
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    		Robot.intake.set((talon) -> talon.set(ControlMode.Disabled, 0));
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    		Robot.intake.set((talon) -> talon.set(ControlMode.Disabled, 0));
    }
}
