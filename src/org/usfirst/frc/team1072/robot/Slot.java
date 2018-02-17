package org.usfirst.frc.team1072.robot;

import com.ctre.phoenix.ErrorCode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

/**
 * @author joel
 *
 */
public enum Slot {
	LEFT_VELOCITY(2, 0.7 * 1023.0 / 3000.0, 0, 0, 0, 0, 0, 0),
	RIGHT_VELOCITY(2, 0.7 * 1023.0 / 3000.0, 0, 0, 0, 0, 0,0),
	LEFT_POSITION(1, 0, 0, 0, 0, 0, 0, 0),
	RIGHT_POSITION(1, 0, 0, 0, 0, 0, 0, 0),
//	LEFT_MOTION_PROFILE(2, 0.7 * 1023.0 / 3000.0, 0.04, 0.000006, 0, 4096, 0, 0), //Carpet
//	RIGHT_MOTION_PROFILE(2, 0.65 * 1023.0 / 3000.0, 0.04, 0.000006, 0, 4096, 0, 0), //Carpet
//	LEFT_MOTION_PROFILE(0, 0.3 * 1023.0 / 1080.0, 0.065, 0.001, 0.00004, 200, 800, 10), //Floor
//	RIGHT_MOTION_PROFILE(0, 0.3 * 1023.0 / 1100.0, 0.065, 0.001, 0.00004, 200, 800, 10), //Floor
//	LEFT_MOTION_PROFILE(0, 1.0 * 1023.0 / 4900.0, 0.085, 0.0015, 0.00004, 200, 800, 0), //Floor fast
//	RIGHT_MOTION_PROFILE(0, 1.0 * 1023.0 / 4800.0, 0.085, 0.0015, 0.00004, 200, 800, 0), //Floor fast
	LEFT_MOTION_PROFILE(0, 1.0 * 1023.0 / 4860.0, 0.2 * 1023.0 / 4860.0, 0 * 0.001 * 1023.0 / 4860.0, 0.0000, 5000, 500000, 50), //Floor fast
	RIGHT_MOTION_PROFILE(0, 1.0 * 1023.0 / 4800.0, 0.2 * 1023.0 / 4800.0, 0 * 0.001 * 1023.0 / 4800.0, 0.0000, 5000, 500000, 50), //Floor fast
	ELEVATOR_POSITION(2, 0, 0, 0, 0, 0, 0, 0),
	ELEVATOR_VELOCITY(1, 0, 0, 0, 0, 0, 0, 0),
	ELEVATOR_MOTION_MAGIC(0, 0, 0, 0, 0, 0, 0, 0);
	private int slot, integralZone, allowableError;
	private double kF, kP, kI, kD, maxIntegral;
	
	/**
	 * @param slot
	 * @param kF
	 * @param kP
	 * @param kI
	 * @param kD
	 * @param integralZone
	 */
	private Slot(int slot, double kF, double kP, double kI, double kD, int integralZone, double maxIntegral,
			int allowableError) {
		this.slot = slot;
		this.kF = kF;
		this.kP = kP;
		this.kI = kI;
		this.kD = kD;
		this.integralZone = integralZone;
		this.maxIntegral = maxIntegral;
		this.allowableError = allowableError;
	}
	
	public boolean configure(TalonSRX talon, int TIMEOUT) {
		return talon.config_kF(slot, kF, TIMEOUT) == ErrorCode.OK && talon.config_kP(slot, kP, TIMEOUT) == ErrorCode.OK
				&& talon.config_kI(slot, kI, TIMEOUT) == ErrorCode.OK
				&& talon.config_kD(slot, kD, TIMEOUT) == ErrorCode.OK
				&& talon.config_IntegralZone(slot, integralZone, TIMEOUT) == ErrorCode.OK
				&& talon.configMaxIntegralAccumulator(slot, maxIntegral, TIMEOUT) == ErrorCode.OK
				&& talon.configAllowableClosedloopError(slot, allowableError, TIMEOUT) == ErrorCode.OK;
	}

	/**
	 * @return the slot
	 */
	public int getSlot() {
		return slot;
	}

	/**
	 * @return the integralZone
	 */
	public int getIntegralZone() {
		return integralZone;
	}

	/**
	 * @return the allowableError
	 */
	public int getAllowableError() {
		return allowableError;
	}

	/**
	 * @return the kF
	 */
	public double getkF() {
		return kF;
	}

	/**
	 * @return the kP
	 */
	public double getkP() {
		return kP;
	}

	/**
	 * @return the kI
	 */
	public double getkI() {
		return kI;
	}

	/**
	 * @return the kD
	 */
	public double getkD() {
		return kD;
	}

	/**
	 * @return the maxIntegral
	 */
	public double getMaxIntegral() {
		return maxIntegral;
	}
}
