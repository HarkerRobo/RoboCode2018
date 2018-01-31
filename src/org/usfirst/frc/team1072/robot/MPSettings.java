package org.usfirst.frc.team1072.robot;

/**
 * @author joel
 *
 */
public enum MPSettings {
	ORIENTATION(0.076, 2.0, 0.0, 20.0, 0.01, 4096.0, 2);
	
	public double F, P, I, D, deadband, unitsPerRotation;
	public int profileSlot;
	
	private MPSettings(double f, double p, double i, double d, double deadband, double unitsPerRotation,
			int profileSlot) {
		F = f;
		P = p;
		I = i;
		D = d;
		this.deadband = deadband;
		this.unitsPerRotation = unitsPerRotation;
		this.profileSlot = profileSlot;
	}
}
