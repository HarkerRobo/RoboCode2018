package org.usfirst.frc.team1072.robot.commands;

import org.usfirst.frc.team1072.robot.Robot;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.TimedCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class SlowRaiseCommand extends TimedCommand {

    public SlowRaiseCommand(double timeout) {
        super(timeout);
        requires(Robot.gearIntake);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    		Robot.gearIntake.getOrientation().set(ControlMode.PercentOutput, -0.15);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    		if(Math.abs(Robot.gearIntake.getOrientation().getSelectedSensorVelocity(0)) > 150)
    			SmartDashboard.putNumber("velocity", Robot.gearIntake.getOrientation().getSelectedSensorVelocity(0));
    }

    // Called once after timeout
    protected void end() {
    		Robot.gearIntake.getOrientation().set(ControlMode.Disabled, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    		
    }
}
