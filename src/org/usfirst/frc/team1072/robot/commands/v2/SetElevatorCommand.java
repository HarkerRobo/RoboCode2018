package org.usfirst.frc.team1072.robot.commands.v2;

import org.usfirst.frc.team1072.robot.Robot;
import org.usfirst.frc.team1072.robot.RobotMap;
import org.usfirst.frc.team1072.robot.Config;
import org.usfirst.frc.team1072.robot.subsystems.Elevator;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 * Command to move the elevator to a specific height
 */
public class SetElevatorCommand extends InstantCommand {

    private final double height;
    
	/**
	 * Initializes an elevator command
	 * @param height height (in feet) above the default position
	 */
    public SetElevatorCommand(double height) {
        requires(Robot.elevator);
        this.height = height * Config.Elevator.ENCODERTOFEET;
    }

    //Called just before this Command runs the first time
    protected void initialize() {
    		if(Robot.elevator.isMotionMagicStatus() && Robot.elevator.isEncoderStatus()) {
    			Robot.elevator.getMaster().set(ControlMode.MotionMagic, height);
    		} else if(Robot.elevator.isPositionClosedStatus() && Robot.elevator.isEncoderStatus()) {
    			Robot.elevator.getMaster().set(ControlMode.Position, height);
    		}
    }
}
