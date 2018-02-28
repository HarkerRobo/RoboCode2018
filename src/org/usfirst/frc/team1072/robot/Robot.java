/*----------------------------------------------------------------------------*/
/*
 * Copyright (c) 2017-2018 FIRST. All Rights Reserved. Open Source Software -
 * may be modified and shared by FRC teams. The code must be accompanied by the
 * FIRST BSD license file in the root directory of the project.
 */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team1072.robot;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import jaci.pathfinder.Trajectory;

import java.io.FileNotFoundException;

import org.usfirst.frc.team1072.robot.commands.v2.ZeroElevatorCommand;
import org.usfirst.frc.team1072.robot.profiling.MotionProfileBuilder;
import org.usfirst.frc.team1072.robot.subsystems.Drivetrain;
import org.usfirst.frc.team1072.robot.subsystems.Elevator;
import org.usfirst.frc.team1072.robot.subsystems.Intake;

// import org.usfirst.frc.team1072.robot.subsystems.Drivetrain;
// import org.usfirst.frc.team1072.robot.subsystems.Elevator;
// import org.usfirst.frc.team1072.robot.subsystems.Intake;

import com.ctre.phoenix.motorcontrol.ControlMode;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends TimedRobot {
	
	public static final boolean IS_COMP = false;
	
	public static final Drivetrain drivetrain = Drivetrain.getInstance();
	public static final Elevator elevator = Elevator.getInstance();
	public static final Intake intake = Intake.getInstance();
	public static final Compressor compressor = new Compressor();
	
	private double minEncoder = Double.MAX_VALUE, maxEncoder = Double.MIN_VALUE;
	
	public static enum Position {
		LEFT, MID, RIGHT
	}
	
	public static enum Goal {
		SWITCH, SCALE, LINE
	}
	
	public static final SmartEnum<Position> position = new SmartEnum<Position>(Position.MID);
	public static final SmartEnum<Goal> goal = new SmartEnum<Goal>(Goal.SWITCH);
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		compressor.setClosedLoopControl(true);
		OI.initializeCommandBindings();
//		UsbCamera cam = CameraServer.getInstance().startAutomaticCapture();
//		cam.setResolution(640, 480);
	}
	
	/* (non-Javadoc)
	 * @see edu.wpi.first.wpilibj.IterativeRobotBase#robotPeriodic()
	 */
	@Override
	public void robotPeriodic() {
//		SmartDashboard.putNumber("left encoder", drivetrain.getLeft().getSelectedSensorPosition(0));
//		SmartDashboard.putNumber("right encoder", drivetrain.getRight().getSelectedSensorPosition(0));
//		SmartDashboard.putNumber("left encoder velocity", drivetrain.getLeft().getSelectedSensorVelocity(0));
//		SmartDashboard.putNumber("right encoder velocity", drivetrain.getRight().getSelectedSensorVelocity(0));
//		SmartDashboard.putNumber("Diff", drivetrain.getLeft().getSelectedSensorVelocity(0) - drivetrain.getRight().getSelectedSensorVelocity(0));
		SmartDashboard.putNumber("Elevator position", elevator.getMaster().getSelectedSensorPosition(0));
//		SmartDashboard.putNumber("left current", intake.getLeftRoller().getOutputCurrent());
//		SmartDashboard.putNumber("right current", intake.getRightRoller().getOutputCurrent());
//		SmartDashboard.putNumber("left output", drivetrain.getLeft().getMotorOutputPercent());
//		SmartDashboard.putNumber("right output", drivetrain.getRight().getMotorOutputPercent());
//		SmartDashboard.putNumber("left error", drivetrain.getLeft().getClosedLoopError(0));
//		SmartDashboard.putNumber("right error", drivetrain.getRight().getClosedLoopError(0));
//		SmartDashboard.putNumber("elevator output", elevator.getMaster().getOutputCurrent());
//		maxEncoder = Math.max(maxEncoder, elevator.getMaster().getSelectedSensorPosition(0));
//		minEncoder = Math.min(minEncoder, elevator.getMaster().getSelectedSensorPosition(0));
//		SmartDashboard.putNumber("elevator max", maxEncoder);
//		SmartDashboard.putNumber("elevator min", minEncoder);
//		SmartDashboard.putNumber("elevator speed", elevator.getMaster().getSelectedSensorVelocity(0));
//		SmartDashboard.putNumber("elevator error", elevator.getMaster().getClosedLoopError(0));
//		SmartDashboard.putNumber("elevator accumulator", elevator.getMaster().getIntegralAccumulator(0));
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {
		drivetrain.set((talon) -> talon.set(ControlMode.Disabled, 0));
		elevator.getMaster().set(ControlMode.Disabled, 0);
		intake.set((talon) -> talon.set(ControlMode.Disabled, 0));
		elevator.getMaster().configForwardSoftLimitEnable(false, 0);
		elevator.getMaster().configReverseSoftLimitEnable(false, 0);
		intake.raise();
		intake.close();
	}
	
	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}
	
	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * <p>
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		new ZeroElevatorCommand().start();
//		try {
//			Trajectory left = OI.readTrajectory("/home/lvuser/paths/leftPath10.csv"),
//					right = OI.readTrajectory("/home/lvuser/paths/rightPath10.csv");
//			new MotionProfileBuilder(10, Robot.drivetrain)
//			.group(left, Slot.LEFT_MOTION_PROFILE.getSlot(), 4.0 * Math.PI / 12.0/*0.31918*/, 0.945, Robot.drivetrain.getLeft())
//			.group(left, Slot.RIGHT_MOTION_PROFILE.getSlot(), 4.0 * Math.PI / 12.0/*0.31918*/, 1.0, Robot.drivetrain.getRight()).build().start();
//		} catch (FileNotFoundException e) {
//			System.err.println("Failed to read trajectory");
//		}
		
		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */
		
		// schedule the autonomous command (example)
	}
	
	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}
	
	@Override
	public void teleopInit() {
		new ZeroElevatorCommand().start();
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
	}
	
	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}
	
	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
}
