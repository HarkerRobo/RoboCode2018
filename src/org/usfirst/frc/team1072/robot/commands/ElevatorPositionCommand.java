package org.usfirst.frc.team1072.robot.commands;

import org.usfirst.frc.team1072.robot.Robot;
import org.usfirst.frc.team1072.robot.Config;
import org.usfirst.frc.team1072.robot.subsystems.Elevator;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Command to move the elevator to a specific height
 */
public class ElevatorPositionCommand extends Command {

    private final double height;
    
	/**
	 * Initializes an elevator command
	 * @param height height (in feet) above the default position
	 */
    public ElevatorPositionCommand(double height) {
        requires(Robot.elevator);
        this.height = height*Config.Elevator.ENCODERTOFEET;
    }

    //Called just before this Command runs the first time
    protected void initialize() {
        
    }

    //Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.elevator.getMaster().set(ControlMode.Position, height);
    }

    //Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    //Called once after isFinished returns true
    protected void end() {
    }

    //Called when another command which requires one or more of the same
    //subsystems is scheduled to run
    protected void interrupted() {
    }
}
