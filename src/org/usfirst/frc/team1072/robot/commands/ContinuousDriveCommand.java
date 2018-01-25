package org.usfirst.frc.team1072.robot.commands;

import java.util.function.DoubleSupplier;

import org.usfirst.frc.team1072.robot.Robot;
import org.usfirst.frc.team1072.robot.RobotMap;
import org.usfirst.frc.team1072.robot.Tuning;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Moves the robot based on left and right speed suppliers
 */
public class ContinuousDriveCommand extends Command {
	
	private DoubleSupplier left, right;

    public ContinuousDriveCommand(DoubleSupplier left, DoubleSupplier right) {
        requires(Robot.drivetrain);
        this.left = left;
        this.right = right;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    		Robot.drivetrain.set((talon) -> talon.selectProfileSlot(Tuning.Drivetrain.VEL_SLOT, RobotMap.Drivetrain.ENCODER));
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    		Robot.drivetrain.getLeft().set(ControlMode.Velocity, left.getAsDouble());
    		Robot.drivetrain.getRight().set(ControlMode.Velocity, right.getAsDouble());
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
