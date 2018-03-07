package org.usfirst.frc.team1072.robot.commands.v2;

import org.usfirst.frc.team1072.robot.Robot;
import org.usfirst.frc.team1072.robot.subsystems.Elevator;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SmallRaiseCommand extends Command {
	
	public static final double DIST = 0.25 * Elevator.FEET_TO_ENCODER;

    public SmallRaiseCommand() {
        requires(Robot.elevator);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    		Robot.elevator.getMaster().set(ControlMode.PercentOutput, 0.3);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.elevator.getMaster().getSelectedSensorPosition(0) > DIST;
    }

    // Called once after isFinished returns true
    protected void end() {
    		Robot.elevator.getMaster().set(ControlMode.Velocity, 0, DemandType.ArbitraryFeedForward, Robot.IS_COMP ? 0.11: 0.08);
    		Robot.intake.getExpansion().set(IntakeExpansionCommand.defaultLift);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
