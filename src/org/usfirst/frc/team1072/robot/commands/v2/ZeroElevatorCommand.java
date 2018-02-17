package org.usfirst.frc.team1072.robot.commands.v2;

import org.usfirst.frc.team1072.robot.Config;
import org.usfirst.frc.team1072.robot.Robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ZeroElevatorCommand extends Command {
	
	public static final double STALL_CURRENT = 30;
	
	public static final double SPEED = -0.3;
	
	public ZeroElevatorCommand() {
		requires(Robot.elevator);
	}
	
	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.elevator.getMaster().set(ControlMode.PercentOutput, SPEED);
	}
	
	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		if(Robot.elevator.getMaster().getSensorCollection()
				.isRevLimitSwitchClosed() == (Config.Elevator.REVERSE_NORMAL == LimitSwitchNormal.NormallyOpen)) {
			return true;
		} else if(Robot.elevator.getMaster().getOutputCurrent() > STALL_CURRENT) {
			Robot.elevator.getMaster().setSelectedSensorPosition(0, 0, 0);
		}
		return false;
	}
	
	// Called once after isFinished returns true
	protected void end() {
		Robot.elevator.getMaster().set(ControlMode.Disabled, 0);
		Robot.elevator.getMaster().configForwardSoftLimitEnable(true, 0);
		Robot.elevator.getMaster().configReverseSoftLimitEnable(true, 0);
	}
	
	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		Robot.elevator.getMaster().set(ControlMode.Disabled, 0);
	}
}
