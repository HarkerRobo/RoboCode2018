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
public class LowerElevatorCommand extends InstantCommand {
	
	public static final double OPEN_LOOP_SPEED = -0.5;
	
	public LowerElevatorCommand() {
		requires(Robot.elevator);
	}
	
	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.elevator.getMaster().clearStickyFaults(0);
//		Robot.elevator.getMaster().setIntegralAccumulator(0, 0, 0);
		Robot.intake.open();
		if(false && Robot.elevator.isMotionMagicStatus() && Robot.elevator.isEncoderStatus()) {
			Robot.elevator.getMaster().selectProfileSlot(Slot.ELEVATOR_MOTION_MAGIC.getSlot(), 0);
			Robot.elevator.getMaster().set(ControlMode.MotionMagic, Elevator.BUFFER, DemandType.ArbitraryFeedForward, Robot.IS_COMP ? 0.11: 0.08);
		} else if(Robot.elevator.isPositionClosedStatus() && Robot.elevator.isEncoderStatus()) {
			Robot.elevator.getMaster().selectProfileSlot(Slot.ELEVATOR_POSITION.getSlot(), 0);
			Robot.elevator.getMaster().set(ControlMode.Position, Elevator.BUFFER, DemandType.ArbitraryFeedForward, Robot.IS_COMP ? 0.11: 0.08);
		} else {
			Robot.elevator.getMaster().set(ControlMode.PercentOutput, OPEN_LOOP_SPEED);
		}
	}
}
