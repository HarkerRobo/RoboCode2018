package org.usfirst.frc.team1072.robot.commands.v2;

import org.usfirst.frc.team1072.robot.Robot;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.TimedCommand;

/**
 *
 */
public class JiggleCommand extends TimedCommand {

	public static final double TIMEOUT = 0.4;
	public static final double SPEED = 0.4;
	
	private boolean reversed;
	
    public JiggleCommand() {
        super(TIMEOUT);
        requires(Robot.intake);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    		reversed = false;
    		Robot.intake.getLeftRoller().set(ControlMode.PercentOutput, SPEED);
    		Robot.intake.getRightRoller().set(ControlMode.PercentOutput, SPEED);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    		if(!reversed && timeSinceInitialized() > TIMEOUT / 2.0) {
    			reversed = true;
    			Robot.intake.getLeftRoller().set(ControlMode.PercentOutput, -SPEED);
    			Robot.intake.getRightRoller().set(ControlMode.PercentOutput, -SPEED);
    		}
    }

    // Called once after timeout
    protected void end() {
    		Robot.intake.getLeftRoller().set(ControlMode.Disabled, 0);
    		Robot.intake.getRightRoller().set(ControlMode.Disabled, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    		Robot.intake.getLeftRoller().set(ControlMode.Disabled, 0);
		Robot.intake.getRightRoller().set(ControlMode.Disabled, 0);
    }
}
