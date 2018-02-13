package org.usfirst.frc.team1072.robot.commands;

import static org.usfirst.frc.team1072.robot.Config.Elevator.TIMEOUT;
import static org.usfirst.frc.team1072.robot.RobotMap.Elevator.ENCODER;

import org.usfirst.frc.team1072.robot.Robot;
import org.usfirst.frc.team1072.robot.subsystems.Elevator;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ZeroElevatorCommand extends Command {
	
	public static final double STALL_CURRENT = 10;
	
	public ZeroElevatorCommand() {
		requires(Robot.elevator);
	}
	
	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.elevator.getMaster().set(ControlMode.PercentOutput, -0.1);
	}
	
	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
	}
	
	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return Robot.elevator.getMaster().getOutputCurrent() > STALL_CURRENT;
	}
	
	// Called once after isFinished returns true
	protected void end() {
		if(Robot.elevator.isEncoderStatus() && Robot.elevator
				.log(Robot.elevator.getMaster().setSelectedSensorPosition(0, 0, 10), "Could not zero position")) {
			if(!Robot.elevator.isForwardLimitStatus() && Robot.elevator.log(
					Robot.elevator.getMaster().configForwardSoftLimitThreshold(Elevator.LENGTH, TIMEOUT),
					"Failed to configure forward soft limits")) {
				Robot.elevator.setForwardSoftLimitStatus(
						Robot.elevator.log(Robot.elevator.getMaster().configForwardSoftLimitEnable(true, TIMEOUT),
								"Failed to enable forward soft limits"));
			}
			if(!Robot.elevator.isReverseLimitStatus() && Robot.elevator.log(
					Robot.elevator.getMaster().configReverseSoftLimitThreshold(
							Robot.elevator.getMaster().getSelectedSensorPosition(ENCODER), TIMEOUT),
					"Failed to configure reverse soft limits")) {
				Robot.elevator.setReverseSoftLimitStatus(
						Robot.elevator.log(Robot.elevator.getMaster().configReverseSoftLimitEnable(true, TIMEOUT),
								"Failed to enable reverse soft limits"));
			}
		}
	}
	
	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
