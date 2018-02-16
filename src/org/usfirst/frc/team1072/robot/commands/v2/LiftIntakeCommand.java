package org.usfirst.frc.team1072.robot.commands.v2;

import org.usfirst.frc.team1072.robot.Robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class LiftIntakeCommand extends Command {
	
	boolean done;
	
    public LiftIntakeCommand() {
        requires(Robot.intake);
        done = false;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if (Robot.intake.getRaise().get() == DoubleSolenoid.Value.kOff) {
    		Robot.intake.getRaise().set(DoubleSolenoid.Value.kForward);
    	} else {
    		Robot.intake.getRaise().set(DoubleSolenoid.Value.kOff);
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	done = true;
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return done;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
