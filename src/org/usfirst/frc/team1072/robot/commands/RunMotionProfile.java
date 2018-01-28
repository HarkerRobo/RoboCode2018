package org.usfirst.frc.team1072.robot.commands;

import com.ctre.phoenix.motion.MotionProfileStatus;
import com.ctre.phoenix.motion.SetValueMotionProfile;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class RunMotionProfile extends Command {
	
	private Notifier notifier;
	private TalonSRX target;
	private int trajectoryPeriod;
	private MotionProfileStatus status;

    public RunMotionProfile(TalonSRX target, int trajectoryPeriod) {
        this.target = target;
        this.trajectoryPeriod = trajectoryPeriod;
        notifier = new Notifier(target::processMotionProfileBuffer);
        status = new MotionProfileStatus();
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    		System.out.println("Run profile init");
    		target.set(ControlMode.MotionProfile, SetValueMotionProfile.Enable.value);
    		notifier.startPeriodic(Math.max(1, trajectoryPeriod/2));
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        target.getMotionProfileStatus(status);
        return status.activePointValid && status.isLast;
    }

    // Called once after isFinished returns true
    protected void end() {
    		System.out.println("Run profile end");
    		notifier.stop();
    		target.set(ControlMode.MotionProfile, SetValueMotionProfile.Hold.value);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    		notifier.stop();
    		target.set(ControlMode.MotionProfile, SetValueMotionProfile.Hold.value);
    }
}
