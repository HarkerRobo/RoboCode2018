package org.usfirst.frc.team1072.robot.commands.v2;

import org.usfirst.frc.team1072.robot.Robot;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class LowerElevatorCommand extends InstantCommand {
	
	public static final double OPEN_LOOP_SPEED = -0.5;
	
	public LowerElevatorCommand() {
		requires(Robot.elevator);
	}
	
	// Called just before this Command runs the first time
	protected void initialize() {
		if(Robot.elevator.isMotionMagicStatus() && Robot.elevator.isEncoderStatus()) {
			Robot.elevator.getMaster().set(ControlMode.MotionMagic, 0);
		} else if(Robot.elevator.isPositionClosedStatus() && Robot.elevator.isEncoderStatus()) {
			Robot.elevator.getMaster().set(ControlMode.Position, 0);
		} else {
			Robot.elevator.getMaster().set(ControlMode.PercentOutput, OPEN_LOOP_SPEED);
		}
	}
}
