package org.usfirst.frc.team1072.robot.commands.v2;

import org.usfirst.frc.team1072.robot.OI;
import org.usfirst.frc.team1072.robot.Robot;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class IntakeCommand extends Command {
	
	public static final double INTAKE_LEFT = 0.8, INTAKE_RIGHT = 1.0;
	public static final double OUTTAKE_LEFT = -0.6, OUTTAKE_RIGHT = -0.6;
	public static final double SLOW_OUTTAKE_LEFT = -0.4, SLOW_OUTTAKE_RIGHT = -0.4;
	public static final double SLOW_HEIGHT = 25000;
	
	private boolean outtaking;

    public IntakeCommand() {
        requires(Robot.intake);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    		if(OI.gamepad.getRightTriggerPressed()) {
    			if(Robot.intake.getRaise().get() != Value.kForward)
    				Robot.intake.getRaise().set(Value.kForward);
    			Robot.intake.getLeftRoller().set(ControlMode.PercentOutput, INTAKE_LEFT);
    			Robot.intake.getRightRoller().set(ControlMode.PercentOutput, INTAKE_RIGHT);
    		} else if (OI.gamepad.getLeftTriggerPressed()) {
    			outtaking = true;
    			if(Robot.elevator.getMaster().getSelectedSensorPosition(0) > SLOW_HEIGHT) {
    				Robot.intake.getLeftRoller().set(ControlMode.PercentOutput, SLOW_OUTTAKE_LEFT);
    				Robot.intake.getRightRoller().set(ControlMode.PercentOutput, SLOW_OUTTAKE_RIGHT);
    			} else {
    				Robot.intake.getLeftRoller().set(ControlMode.PercentOutput, OUTTAKE_LEFT);
    				Robot.intake.getRightRoller().set(ControlMode.PercentOutput, OUTTAKE_RIGHT);
    			}
    		} else {
    			if(outtaking) {
    				outtaking = false;
    				Robot.intake.getExpansion().set(Value.kForward);
    			}
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
