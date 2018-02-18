package org.usfirst.frc.team1072.robot.commands.v2;

import org.usfirst.frc.team1072.robot.OI;
import org.usfirst.frc.team1072.robot.Robot;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ElevatorDriveCommand extends Command {
	
	public static final double THRESHOLD = 0.10;
	
	private boolean started;

    public ElevatorDriveCommand() {
        requires(Robot.elevator);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    		if(Math.abs(OI.gamepad.getRightY()) > THRESHOLD) {
    			started = true;
    		}
    		if(started) {
    			Robot.elevator.getMaster().set(ControlMode.PercentOutput, Math.abs(OI.gamepad.getRightY()) > THRESHOLD ? OI.gamepad.getRightY() * 0.985 + 0.115 : 0.115);
    		}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    		Robot.elevator.getMaster().set(ControlMode.Disabled, 0);
    		started = false;
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    		Robot.elevator.getMaster().set(ControlMode.Disabled, 0);
    		started = false;
    }
}
