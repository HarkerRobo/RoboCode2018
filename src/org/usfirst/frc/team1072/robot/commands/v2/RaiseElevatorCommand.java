package org.usfirst.frc.team1072.robot.commands.v2;

import org.usfirst.frc.team1072.robot.Robot;
import org.usfirst.frc.team1072.robot.Slot;
import org.usfirst.frc.team1072.robot.subsystems.Elevator;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class RaiseElevatorCommand extends InstantCommand {
	
	public static final double OPEN_LOOP_SPEED = 0.5;
	
	public RaiseElevatorCommand() {
		requires(Robot.elevator);
	}
	
	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.elevator.getMaster().clearStickyFaults(0);
		Robot.intake.raise();
//		Robot.elevator.getMaster().setIntegralAccumulator(0, 0, 0);
		if(false && Robot.elevator.isMotionMagicStatus() && Robot.elevator.isEncoderStatus()) {
			Robot.elevator.getMaster().selectProfileSlot(Slot.ELEVATOR_MOTION_MAGIC.getSlot(), 0);
			Robot.elevator.getMaster().set(ControlMode.MotionMagic, Elevator.LENGTH - Elevator.BUFFER * 4.0, DemandType.ArbitraryFeedForward, Robot.IS_COMP ? 0.11: 0.08);
		} else if(Robot.elevator.isPositionClosedStatus() && Robot.elevator.isEncoderStatus()) {
			Robot.elevator.getMaster().selectProfileSlot(Slot.ELEVATOR_POSITION.getSlot(), 0);
			Robot.elevator.getMaster().set(ControlMode.Position, Elevator.LENGTH - Elevator.BUFFER * 4.0, DemandType.ArbitraryFeedForward, Robot.IS_COMP ? 0.11: 0.08);
		} else {
			Robot.elevator.getMaster().set(ControlMode.PercentOutput, OPEN_LOOP_SPEED);
		}
	}
}
