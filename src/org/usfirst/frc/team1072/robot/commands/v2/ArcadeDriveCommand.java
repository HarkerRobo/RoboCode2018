package org.usfirst.frc.team1072.robot.commands;

import org.usfirst.frc.team1072.robot.OI;
import org.usfirst.frc.team1072.robot.Robot;
import org.usfirst.frc.team1072.robot.RobotMap;
import org.usfirst.frc.team1072.robot.Slot;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Drive in either arcade or tank drive
 */
public class ArcadeDriveCommand extends Command {
	
	public static final double THRESHOLD = 0.15, MAX_SPEED = 5000;

    public ArcadeDriveCommand() {
        requires(Robot.drivetrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    		if(Robot.drivetrain.isEncoderStatus() && Robot.drivetrain.isVelocityClosedStatus()) {
    			Robot.drivetrain.getLeft().selectProfileSlot(Slot.LEFT_VELOCITY.getSlot(), RobotMap.Drivetrain.ENCODER);
    			Robot.drivetrain.getRight().selectProfileSlot(Slot.RIGHT_VELOCITY.getSlot(), RobotMap.Drivetrain.ENCODER);
    		}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    		if(Robot.drivetrain.isMotorStatus()) {
    			double x = OI.gamepad.getLeftX();
    			double y = OI.gamepad.getLeftY();
    			double k = Math.max(1.0, Math.max(Math.abs(y + x * x), Math.abs(y - x * x)));
    			double left = (y + x * Math.abs(x)) / k;
    			double right = (y - x * Math.abs(x)) / k;
    			if(Math.abs(left) < THRESHOLD && Math.abs(right) < THRESHOLD) {
    				left = 0;
    				right = 0;
    			}
	    		if(Robot.drivetrain.isEncoderStatus() && Robot.drivetrain.isVelocityClosedStatus()) {
	    			Robot.drivetrain.getLeft().set(ControlMode.Velocity, MAX_SPEED * left);
	    			Robot.drivetrain.getRight().set(ControlMode.Velocity, MAX_SPEED * right);
	    		} else {
	    			Robot.drivetrain.getLeft().set(ControlMode.PercentOutput, left);
	    			Robot.drivetrain.getRight().set(ControlMode.PercentOutput, right);
	    		}
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
