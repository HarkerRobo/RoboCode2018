package org.usfirst.frc.team1072.robot.commands;

import org.usfirst.frc.team1072.robot.Robot;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class IntakeCommand extends Command {
    
    public static final double INTAKE_SPEED = 0.4;
    public static final double STALL_CURRENT = 18;

    public IntakeCommand() {
        requires(Robot.intake);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        Robot.intake.set((talon) -> talon.set(ControlMode.PercentOutput, INTAKE_SPEED));
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.intake.getLeftRoller().getOutputCurrent() + Robot.intake.getRightRoller().getOutputCurrent() > STALL_CURRENT;
    }

    // Called once after isFinished returns true
    protected void end() {
        Robot.intake.set((talon) -> talon.set(ControlMode.Disabled, 0));
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
