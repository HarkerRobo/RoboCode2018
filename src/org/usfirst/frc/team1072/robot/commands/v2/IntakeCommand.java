package org.usfirst.frc.team1072.robot.commands.v2;

import org.usfirst.frc.team1072.robot.OI;
import org.usfirst.frc.team1072.robot.Robot;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class IntakeCommand extends Command {
	
	public static final double INTAKE_LEFT = -0.5, INTAKE_RIGHT = -0.65;
	public static final double OUTTAKE_LEFT = 0.7, OUTTAKE_RIGHT = 0.7;

    public IntakeCommand() {
        requires(Robot.intake);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    		if(OI.gamepad.getLeftTriggerPressed()) {
    			Robot.intake.getLeftRoller().set(ControlMode.PercentOutput, INTAKE_LEFT);
    			Robot.intake.getRightRoller().set(ControlMode.PercentOutput, INTAKE_RIGHT);
    		} else if (OI.gamepad.getRightTriggerPressed()) {
    			Robot.intake.getLeftRoller().set(ControlMode.PercentOutput, OUTTAKE_LEFT);
    			Robot.intake.getRightRoller().set(ControlMode.PercentOutput, OUTTAKE_RIGHT);
    		} else {
    			Robot.intake.getLeftRoller().set(ControlMode.Disabled, 0);
    			Robot.intake.getRightRoller().set(ControlMode.Disabled, 0);
    		}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    		Robot.intake.getLeftRoller().set(ControlMode.Disabled, 0);
    		Robot.intake.getRightRoller().set(ControlMode.Disabled, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    		Robot.intake.getLeftRoller().set(ControlMode.Disabled, 0);
    		Robot.intake.getRightRoller().set(ControlMode.Disabled, 0);
    }
}
