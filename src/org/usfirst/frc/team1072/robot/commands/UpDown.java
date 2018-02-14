package org.usfirst.frc.team1072.robot.commands;

import org.usfirst.frc.team1072.robot.Robot;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class UpDown extends InstantCommand {

    public UpDown() {
        requires(Robot.intake);
    }

    // Called once when the command executes
    protected void initialize() {
    		if(Robot.intake.getRaise().get() == Value.kForward)
    			Robot.intake.getRaise().set(Value.kReverse);
    		else
    			Robot.intake.getRaise().set(Value.kForward);
    }

}
