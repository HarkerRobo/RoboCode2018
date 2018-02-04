package org.usfirst.frc.team1072.robot.commands;
//package org.usfirst.frc.team1072.robot.commands;
//
//import java.util.Arrays;
//import java.util.List;
//
//import org.usfirst.frc.team1072.robot.MPSettings;
//
//import com.ctre.phoenix.motion.MotionProfileStatus;
//import com.ctre.phoenix.motion.SetValueMotionProfile;
//import com.ctre.phoenix.motion.TrajectoryPoint;
//import com.ctre.phoenix.motion.TrajectoryPoint.TrajectoryDuration;
//import com.ctre.phoenix.motorcontrol.ControlMode;
//import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
//import com.ctre.phoenix.motorcontrol.can.TalonSRX;
//
//import edu.wpi.first.wpilibj.Notifier;
//import edu.wpi.first.wpilibj.command.Command;
//import edu.wpi.first.wpilibj.command.Subsystem;
//import jaci.pathfinder.Trajectory;
//
///**
// * Run a pathfinder motion profile on a talon
// */
//public class MotionProfileCommand extends Command {
//	
//	private List<Group> groups;
//	private Notifier notifier;
//	private MotionProfileStatus status;
//	private int period;
//	
//	public MotionProfileCommand(int period, Subsystem... required) {
//		for(Subsystem s : required)
//			requires(s);
//		this.period = period;
//	}
//	
//	/**
//	 * To be used in the builder convention to specify groups of talons that
//	 * follow a trajectory, ie
//	 * 
//	 * new MotionProfileCommand(settings, targets, required).group(trajectory,
//	 * talon, talon).group(trajectory, talon, talon)
//	 */
//	public MotionProfileCommand group(Trajectory trajectory, MPSettings settings, TalonSRX... targets) {
//		groups.add(new Group(trajectory, settings, targets));
//		return this;
//	}
//	
//	// Called just before this Command runs the first time
//	protected void initialize() {
//		notifier = new Notifier(() -> groups.stream().flatMap((group) -> Arrays.stream(group.talons))
//				.forEach((talon) -> talon.processMotionProfileBuffer()));
//		status = new MotionProfileStatus();
//		for(Group g : groups) {
//			MPSettings settings = g.settings;
//			for(TalonSRX target : g.talons) {
//				target.set(ControlMode.MotionProfile, SetValueMotionProfile.Disable.value);
//				target.clearMotionProfileHasUnderrun(0);
//				target.clearMotionProfileTrajectories();
//				target.changeMotionControlFramePeriod(Math.max(1, period / 2));
//				target.configMotionProfileTrajectoryPeriod(period, 0);
//				target.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, Math.max(1, period / 2), 0);
//				target.configNeutralDeadband(settings.deadband, 0);
//				target.config_kF(settings.profileSlot, settings.F, 0);
//				target.config_kP(settings.profileSlot, settings.P, 0);
//				target.config_kI(settings.profileSlot, settings.I, 0);
//				target.config_kD(settings.profileSlot, settings.D, 0);
//				loadPoints();
//				target.set(ControlMode.MotionProfile, SetValueMotionProfile.Enable.value);
//			}
//		}
//		notifier.startPeriodic(period / 2000.0);
//	}
//	
//	// Called repeatedly when this Command is scheduled to run
//	protected void execute() {
//		loadPoints();
//	}
//	
//	// Make this return true when this Command no longer needs to run execute()
//	protected boolean isFinished() {
//		return groups.stream().flatMap((group) -> Arrays.stream(group.talons)).allMatch((talon) -> {
//			talon.getMotionProfileStatus(status);
//			return status.activePointValid && status.isLast;
//		});
//	}
//	
//	// Called once after isFinished returns true
//	protected void end() {
//		notifier.stop();
//		groups.stream().flatMap((group) -> Arrays.stream(group.talons))
//				.forEach((talon) -> talon.set(ControlMode.Disabled, 0));
//	}
//	
//	// Called when another command which requires one or more of the same
//	// subsystems is scheduled to run
//	protected void interrupted() {
//		notifier.stop();
//		groups.stream().flatMap((group) -> Arrays.stream(group.talons))
//				.forEach((talon) -> talon.set(ControlMode.Disabled, 0));
//	}
//	
//	private void loadPoints() {
//		while(loadNext < trajectory.segments.length && !target.isMotionProfileTopLevelBufferFull()) {
//			TrajectoryPoint tp = new TrajectoryPoint();
//			tp.position = trajectory.segments[loadNext].position * settings.unitsPerRotation;
//			tp.velocity = trajectory.segments[loadNext].velocity * settings.unitsPerRotation / 600.0;
//			tp.headingDeg = trajectory.segments[loadNext].heading * 180.0 / Math.PI;
//			tp.profileSlotSelect0 = settings.profileSlot;
//			tp.profileSlotSelect1 = 0;
//			tp.timeDur = TrajectoryDuration.Trajectory_Duration_0ms;
//			tp.zeroPos = loadNext == 0;
//			tp.isLastPoint = ++loadNext == trajectory.segments.length;
//			target.pushMotionProfileTrajectory(tp);
//		}
//	}
//	
//	static class Group {
//		private final Trajectory trajectory;
//		private MPSettings settings;
//		private final TalonSRX[] talons;
//		
//		/**
//		 * @param trajectory
//		 * @param settings
//		 * @param talons
//		 */
//		public Group(Trajectory trajectory, MPSettings settings, TalonSRX[] talons) {
//			super();
//			this.trajectory = trajectory;
//			this.settings = settings;
//			this.talons = talons;
//		}
//	}
//}
