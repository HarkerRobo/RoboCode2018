package org.usfirst.frc.team1072.robot.commands;

import org.usfirst.frc.team1072.robot.Robot;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ClosedLoopCommand extends Command {
	
	private double target;

    public ClosedLoopCommand(double target) {
        requires(Robot.drivetrain);
        this.target = target;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    		Robot.drivetrain.set((talon) -> talon.set(ControlMode.Velocity, target));
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
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
    }
}
