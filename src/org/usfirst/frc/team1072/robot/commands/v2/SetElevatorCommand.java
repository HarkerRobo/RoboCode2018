package org.usfirst.frc.team1072.robot.commands.v2;

import org.usfirst.frc.team1072.robot.Robot;
import org.usfirst.frc.team1072.robot.RobotMap;
import org.usfirst.frc.team1072.robot.Config;
import org.usfirst.frc.team1072.robot.subsystems.Elevator;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Command to move the elevator to a specific height
 */
public class SetElevatorCommand extends Command {

    private final double height;
    private boolean done;
    
	/**
	 * Initializes an elevator command
	 * @param height height (in feet) above the default position
	 */
    public SetElevatorCommand(double height) {
        requires(Robot.elevator);
        this.height = height*Config.Elevator.ENCODERTOFEET;
        done = false;
    }

    //Called just before this Command runs the first time
    protected void initialize() {
    	Robot.elevator.getMaster().set(ControlMode.Position, height);
    }

    //Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	done = true;
    }

    //Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return done;
        //Find a way to check if encoder is at desired position
    }

    //Called once after isFinished returns true
    protected void end() {
    }

    //Called when another command which requires one or more of the same
    //subsystems is scheduled to run
    protected void interrupted() {
    }
}
