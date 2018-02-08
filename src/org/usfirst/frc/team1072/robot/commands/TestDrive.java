package org.usfirst.frc.team1072.robot.commands;

import org.usfirst.frc.team1072.robot.Robot;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class TestDrive extends Command {

    public TestDrive() {
        requires(Robot.drivetrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    		SmartDashboard.putNumber("Left Percent Output", 0);
    		SmartDashboard.putNumber("Right Percent Output", 0);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    		Robot.drivetrain.getLeft().set(ControlMode.PercentOutput, SmartDashboard.getNumber("Left Percent Output", 0));
    		Robot.drivetrain.getRight().set(ControlMode.PercentOutput, SmartDashboard.getNumber("Right Percent Output", 0));
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    		
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
