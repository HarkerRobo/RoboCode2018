package org.usfirst.frc.team1072.robot.commands.v2;

import org.usfirst.frc.team1072.robot.Robot;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class LowerElevatorCommand extends Command {

    public LowerElevatorCommand() {
        requires(Robot.elevator);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.elevator.getMaster().set(ControlMode.Velocity, -1);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return Robot.elevator.isReverseLimitStatus();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.elevator.getMaster().set(ControlMode.Disabled, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.elevator.getMaster().set(ControlMode.Disabled, 0);
    }
}
