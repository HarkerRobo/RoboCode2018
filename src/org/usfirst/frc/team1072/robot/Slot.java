package org.usfirst.frc.team1072.robot;

import com.ctre.phoenix.ErrorCode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

/**
 * @author joel
 *
 */
public enum Slot {
	LEFT_VELOCITY(0, 0, 0, 0, 0, 0), RIGHT_VELOCITY(0, 0, 0, 0, 0, 0), LEFT_POSITION(1, 0, 0, 0, 0, 0),
	RIGHT_POSITION(1, 0, 0, 0, 0, 0), LEFT_MOTION_PROFILE(2, 0, 0, 0, 0, 0), RIGHT_MOTION_PROFILE(2, 0, 0, 0, 0, 0),
	ELEVATOR_POSITION(0, 0, 0, 0, 0, 0), ELEVATOR_VELOCITY(1, 0, 0, 0, 0, 0);
	private int slot, integralZone;
	private double kF, kP, kI, kD;
	
	/**
	 * @param slot
	 * @param kF
	 * @param kP
	 * @param kI
	 * @param kD
	 * @param integralZone
	 */
	private Slot(int slot, double kF, double kP, double kI, double kD, int integralZone) {
		this.slot = slot;
		this.kF = kF;
		this.kP = kP;
		this.kI = kI;
		this.kD = kD;
		this.integralZone = integralZone;
	}
	
	public boolean configure(TalonSRX talon, int TIMEOUT) {
		return talon.config_kF(slot, kF, TIMEOUT) == ErrorCode.OK && talon.config_kP(slot, kP, TIMEOUT) == ErrorCode.OK
				&& talon.config_kI(slot, kI, TIMEOUT) == ErrorCode.OK
				&& talon.config_kD(slot, kD, TIMEOUT) == ErrorCode.OK
				&& talon.config_IntegralZone(slot, integralZone, TIMEOUT) == ErrorCode.OK;
	}
}
