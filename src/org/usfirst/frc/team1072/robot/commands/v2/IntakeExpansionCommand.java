package org.usfirst.frc.team1072.robot.commands.v2;

import org.usfirst.frc.team1072.robot.Robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class IntakeExpansionCommand extends InstantCommand {
	
	public static final DoubleSolenoid.Value defaultLift = DoubleSolenoid.Value.kReverse;
	
	public IntakeExpansionCommand() {
		requires(Robot.intake);
	}
	
	// Called just before this Command runs the first time
	protected void initialize() {
		if(Robot.intake.getExpansion().get() != defaultLift) {
			Robot.intake.getExpansion().set(defaultLift);
		} else {
			Robot.intake.getExpansion().set(defaultLift == DoubleSolenoid.Value.kForward ? DoubleSolenoid.Value.kReverse : DoubleSolenoid.Value.kForward);
		}
	}
}
