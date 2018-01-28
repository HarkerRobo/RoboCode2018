package org.usfirst.frc.team1072.robot.commands;

import com.ctre.phoenix.motion.SetValueMotionProfile;
import com.ctre.phoenix.motion.TrajectoryPoint;
import com.ctre.phoenix.motion.TrajectoryPoint.TrajectoryDuration;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.InstantCommand;
import jaci.pathfinder.Trajectory;

/**
 *
 */
public class LoadMotionProfile extends InstantCommand {
	
	private TalonSRX target;
	private Trajectory trajectory;
	private Runnable callback;
	private int trajectoryPeriod, profileSlot;

    public LoadMotionProfile(TalonSRX target, Trajectory trajectory, int trajectoryPeriod, int profileSlot, Runnable callback) {
        if(target == null)
        		throw new RuntimeException("Cannot load a null talon");
        if(trajectory == null)
        		throw new RuntimeException("Cannot load a null path");
        this.target = target;
        this.trajectory = trajectory;
        this.trajectoryPeriod = trajectoryPeriod;
        this.profileSlot = profileSlot;
        this.callback = callback;
    }
    
    public LoadMotionProfile(TalonSRX target, Trajectory trajectory, int trajectoryPeriod, int profileSlot) {
    		this(target, trajectory, trajectoryPeriod, profileSlot, () -> {});
    }

    @Override
    protected void initialize() {
    		target.set(ControlMode.MotionProfile, SetValueMotionProfile.Disable.value);
    		target.changeMotionControlFramePeriod(Math.max(1, trajectoryPeriod/2));
    		target.clearMotionProfileHasUnderrun(0);
    		target.clearMotionProfileTrajectories();
    		target.configMotionProfileTrajectoryPeriod(trajectoryPeriod, 0);
    		target.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, 0);
    		target.setSensorPhase(true); /* keep sensor and motor in phase */
    		target.configNeutralDeadband(0.01, 0);
    		target.config_kF(profileSlot, 0.076, 0);
    		target.config_kP(profileSlot, 2.000, 0);
    		target.config_kI(profileSlot, 0.0, 0);
    		target.config_kD(profileSlot, 20.0, 0);
    		for(int i = 0; i < trajectory.segments.length; i++) {
    			TrajectoryPoint tp = new TrajectoryPoint();
    			tp.position = trajectory.segments[i].position * 4096.0;
    			tp.velocity = trajectory.segments[i].velocity * 4096.0 / 600.0;
    			tp.headingDeg = trajectory.segments[i].heading;
    			tp.profileSlotSelect0 = profileSlot;
    			tp.profileSlotSelect1 = 0;
    			tp.timeDur = TrajectoryDuration.Trajectory_Duration_10ms;
    			tp.zeroPos = i == 0;
    			tp.isLastPoint = (i + 1) == trajectory.segments.length;
    		}
    }
    
    @Override
    protected void end() {
    		callback.run();
    }

}
