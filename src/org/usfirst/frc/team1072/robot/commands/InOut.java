package org.usfirst.frc.team1072.robot.commands;

import org.usfirst.frc.team1072.robot.Robot;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class InOut extends InstantCommand {

    public InOut() {
        requires(Robot.intake);
    }

    // Called once when the command executes
    protected void initialize() {
	    	if(Robot.intake.getExpansion().get() == Value.kForward)
				Robot.intake.getExpansion().set(Value.kReverse);
			else
				Robot.intake.getExpansion().set(Value.kForward);
    }

}
