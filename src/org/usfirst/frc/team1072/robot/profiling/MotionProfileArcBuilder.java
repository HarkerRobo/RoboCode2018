//package org.usfirst.frc.team1072.robot.profiling;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import com.ctre.phoenix.motorcontrol.can.TalonSRX;
//
//import edu.wpi.first.wpilibj.command.Subsystem;
//import jaci.pathfinder.Trajectory;
//
///**
// * @author joel
// *
// *         Creates commands that run motion profiles following the java builder
// *         paradigm
// * 
// *         This is used so that a single command can run multiple motion
// *         profiles on multiple motor controllers at the same time (e.g. the
// *         left and right sides of a drivetrain)
// */
//public class MotionProfileArcBuilder {
//	
//	private final int period;
//	private final Subsystem[] required;
//	private final List<Group> groups;
//	
//	/**
//	 * Begin building a new motion profile
//	 * 
//	 * @param period
//	 *            period of each segment (ms)
//	 * 
//	 * @param required
//	 *            subsystems that this command requires
//	 */
//	public MotionProfileArcBuilder(int period, Subsystem... required) {
//		this.period = period;
//		this.required = required;
//		this.groups = new ArrayList<Group>();
//	}
//	
//	/**
//	 * Add a new trajectory to the command assuming that FPID constants have
//	 * already been loaded into a profile
//	 * 
//	 * @param trajectory
//	 *            the trajectory to follow
//	 * @param profileSlot
//	 *            the profile slot to use in the talons
//	 * @param targets
//	 *            the talons to control
//	 * @return the builder
//	 */
//	public MotionProfileArcBuilder group(Trajectory trajectory, int profileSlot, double distancePerRotation,
//			double encoderFailureMeme, TalonSRX... targets) {
//		groups.add(new Group(trajectory, profileSlot, Double.NaN, Double.NaN, Double.NaN, Double.NaN, -1, Double.NaN,
//				-1, 4096.0, distancePerRotation, encoderFailureMeme, targets));
//		return this;
//	}
//	
//	/**
//	 * Add a new trajectory to the command with FPID constants sent at runtime
//	 * 
//	 * @param trajectory
//	 *            pathfinder trajectory to follow
//	 * @param profileSlot
//	 *            profile to store talon constants in
//	 * @param f
//	 *            FPID F constant
//	 * @param p
//	 *            FPID P constant
//	 * @param i
//	 *            FPID I constant
//	 * @param d
//	 *            FPID D constant
//	 * @param deadband
//	 *            deadband
//	 * @param unitsPerRotation
//	 *            encoder units per wheel rotation
//	 * @param distancePerRotation
//	 *            real world distance per wheel rotation
//	 * @param targets
//	 *            the talons to run this trajectory on
//	 * @return the builder
//	 */
//	public MotionProfileArcBuilder group(Trajectory trajectory, int profileSlot, double f, double p, double i, double d,
//			int integralZone, double maxIntegral, int allowableError, double unitsPerRotation,
//			double encoderFailureMeme, double distancePerRotation, TalonSRX... targets) {
//		groups.add(new Group(trajectory, profileSlot, f, p, i, d, integralZone, maxIntegral, allowableError,
//				unitsPerRotation, distancePerRotation, encoderFailureMeme, targets));
//		return this;
//	}
//	
//	/**
//	 * Turns this builder into a real command that can be run, but can no longer
//	 * have additional groups added
//	 * 
//	 * @return the command to run a motion profile
//	 */
//	public MotionProfileCommand build() {
//		return new MotionProfileArcCommand(period, groups.toArray(new Group[0]), required);
//	}
//	
//	static class Group {
//		private final Trajectory trajectory;
//		private final int profileSlot, auxProfileSlot, integralZone, allowableError;
//		private final double F, P, I, D, maxIntegral, unitsPerRotation, distancePerRotation, encoderFailureMeme;
//		private final TalonSRX[] targets;
//		
//		/**
//		 * @param trajectory
//		 *            pathfinder trajectory to follow
//		 * @param profileSlot
//		 *            profile to store talon constants in
//		 * @param f
//		 *            FPID F constant
//		 * @param p
//		 *            FPID P constant
//		 * @param i
//		 *            FPID I constant
//		 * @param d
//		 *            FPID D constant
//		 * @param deadband
//		 *            deadband
//		 * @param unitsPerRotation
//		 *            encoder units per wheel rotation
//		 * @param distancePerRotation
//		 *            real world distance per wheel rotation
//		 * @param targets
//		 *            the talons to run this trajectory on
//		 */
//		public Group(Trajectory trajectory, int profileSlot, double f, double p, double i, double d, int integralZone,
//				double maxIntegral, int allowableError, double unitsPerRotation, double distancePerRotation,
//				double encoderFailureMeme, TalonSRX... targets) {
//			super();
//			this.trajectory = trajectory;
//			this.profileSlot = profileSlot;
//			F = f;
//			P = p;
//			I = i;
//			D = d;
//			this.integralZone = integralZone;
//			this.maxIntegral = maxIntegral;
//			this.allowableError = allowableError;
//			this.unitsPerRotation = unitsPerRotation;
//			this.distancePerRotation = distancePerRotation;
//			this.encoderFailureMeme = encoderFailureMeme;
//			this.targets = targets;
//		}
//		
//		/**
//		 * @return the trajectory
//		 */
//		public Trajectory getTrajectory() {
//			return trajectory;
//		}
//		
//		/**
//		 * @return the profileSlot
//		 */
//		public int getProfileSlot() {
//			return profileSlot;
//		}
//		
//		/**
//		 * @return the f
//		 */
//		public double getF() {
//			return F;
//		}
//		
//		/**
//		 * @return the p
//		 */
//		public double getP() {
//			return P;
//		}
//		
//		/**
//		 * @return the i
//		 */
//		public double getI() {
//			return I;
//		}
//		
//		/**
//		 * @return the d
//		 */
//		public double getD() {
//			return D;
//		}
//		
//		/**
//		 * @return the integralZone
//		 */
//		public int getIntegralZone() {
//			return integralZone;
//		}
//		
//		/**
//		 * @return the allowableError
//		 */
//		public int getAllowableError() {
//			return allowableError;
//		}
//		
//		/**
//		 * @return the maxIntegral
//		 */
//		public double getMaxIntegral() {
//			return maxIntegral;
//		}
//		
//		/**
//		 * @return the unitsPerRotation
//		 */
//		public double getUnitsPerRotation() {
//			return unitsPerRotation;
//		}
//		
//		/**
//		 * @return the distancePerRotation
//		 */
//		public double getDistancePerRotation() {
//			return distancePerRotation;
//		}
//		
//		/**
//		 * @return the encoderFailureMeme
//		 */
//		public double getEncoderFailureMeme() {
//			return encoderFailureMeme;
//		}
//
//		/**
//		 * @return the targets
//		 */
//		public TalonSRX[] getTargets() {
//			return targets;
//		}
//	}
//}
