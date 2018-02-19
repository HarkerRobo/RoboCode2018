package org.usfirst.frc.team1072.robot.commands.v2;

import org.usfirst.frc.team1072.robot.Robot;
import org.usfirst.frc.team1072.robot.Slot;
import org.usfirst.frc.team1072.robot.subsystems.Elevator;

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
		Robot.elevator.getMaster().setIntegralAccumulator(0, 0, 0);
		if(Robot.elevator.isMotionMagicStatus() && Robot.elevator.isEncoderStatus()) {
			Robot.elevator.getMaster().selectProfileSlot(Slot.ELEVATOR_MOTION_MAGIC.getSlot(), 0);
			Robot.elevator.getMaster().set(ControlMode.MotionMagic, Elevator.BUFFER);
		} else if(Robot.elevator.isPositionClosedStatus() && Robot.elevator.isEncoderStatus()) {
			Robot.elevator.getMaster().selectProfileSlot(Slot.ELEVATOR_POSITION.getSlot(), 0);
			Robot.elevator.getMaster().set(ControlMode.Position, Elevator.BUFFER);
		} else {
			Robot.elevator.getMaster().set(ControlMode.PercentOutput, OPEN_LOOP_SPEED);
		}
	}
}
