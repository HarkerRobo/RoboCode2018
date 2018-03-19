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
import edu.wpi.first.wpilibj.DriverStation;
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

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.sensors.PigeonIMU;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends TimedRobot {
	
	public static int TIMEOUT;
	
	public static final boolean IS_COMP = false;
	
	public static final Drivetrain drivetrain = Drivetrain.getInstance();
	public static final Elevator elevator = Elevator.getInstance();
	public static final Intake intake = Intake.getInstance();
	public static final Compressor compressor = new Compressor();
	public static final PigeonIMU pigeon = new PigeonIMU(intake.getRightRoller());
	
	public static boolean falling = false;
	
	public static enum Position {
		LEFT, MID, RIGHT
	}
	
	public static enum Goal {
		SWITCH, SCALE, LINE
	}
	
	public static String fieldLayout;
	
	public static Trajectory traj;
	
	public static SmartEnum<Position> position = new SmartEnum(Position.MID,"Starting Position of Robot.");
	public static SmartEnum<Goal> RR = new SmartEnum(Goal.LINE,"If Switch and Scale both on RIGHT side of field.");
	public static SmartEnum<Goal> LL = new SmartEnum(Goal.LINE,"If Switch and Scale both on LEFT side of field.");
	public static SmartEnum<Goal> LR = new SmartEnum(Goal.LINE,"If Switch on LEFT and Scale on RIGHT side of field.");
	public static SmartEnum<Goal> RL = new SmartEnum(Goal.LINE,"If Switch on RIGHT and Scale on LEFT side of field");
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		compressor.setClosedLoopControl(true);
		OI.initializeCommandBindings();
		if (DriverStation.getInstance().getGameSpecificMessage() != null && DriverStation.getInstance().getGameSpecificMessage() != "") {
			fieldLayout = DriverStation.getInstance().getGameSpecificMessage().substring(0,2);
			chooseAuton();
		}
	}
	
	/* (non-Javadoc)
	 * @see edu.wpi.first.wpilibj.IterativeRobotBase#robotPeriodic()
	 */
	@Override
	public void robotPeriodic() {
		if (DriverStation.getInstance().getGameSpecificMessage() != null && DriverStation.getInstance().getGameSpecificMessage().length() == 3) {
			fieldLayout = DriverStation.getInstance().getGameSpecificMessage().substring(0,2);
			chooseAuton();
		}
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
		chooseAuton();
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
	
	public static void log(String line) {
		
	}
	
	static void chooseAuton() {
		if (position.get().equals(Position.LEFT)) {
			if (fieldLayout == "RR") {
				if (RR.get().equals(Goal.SWITCH)) {
					traj = OI.readTrajectory("/home/lvuser/pathsLeftToRightSwitchBack.csv"); //Or LeftToRightSwitchSide, depending on situation
					return;
				}
				if (RR.get().equals(Goal.SCALE)) {
					traj = OI.readTrajectory("/home/lvuser/paths/LeftToRightScale.csv");
					return;
				}
				traj = OI.readTrajectory("/home/lvuser/paths/LeftLineCross.csv");
			}
			else if (fieldLayout == "LL") {
				if (LL.get().equals(Goal.SWITCH)) {
					traj = OI.readTrajectory("/home/lvuser/paths/LeftToLeftSwitchSide.csv"); //Or LeftToLeftSwitchFront, depending on situation
					return;
				}
				if (LL.get().equals(Goal.SCALE)) {
					traj = OI.readTrajectory("/home/lvuser/paths/LeftToLeftScale.csv");
					return;
				}
				traj = OI.readTrajectory("/home/lvuser/paths/LeftLineCross.csv");
			}
			else if (fieldLayout == "LR") {
				if (LR.get().equals(Goal.SWITCH)) {
					traj = OI.readTrajectory("/home/lvuser/paths/LeftToLeftSwitchSide.csv"); //Or LeftToLeftSwitchFront, depending on situation
					return;
				}
				if (LR.get().equals(Goal.SCALE)) {
					traj = OI.readTrajectory("/home/lvuser/paths/LeftToRightScale.csv");
					return;
				}
				traj = OI.readTrajectory("/home/lvuser/paths/LeftLineCross.csv");
			}
			else if (fieldLayout == "RL") {
				if (RL.get().equals(Goal.SWITCH)) {
					traj = OI.readTrajectory("/home/lvuser/pathsLeftToRightSwitchBack.csv"); //Or LeftToRightSwitchSide, depending on situation
					return;
				}
				if (RL.get().equals(Goal.SCALE)) {
					traj = OI.readTrajectory("/home/lvuser/paths/LeftToLeftScale.csv");
					return;
				}
				traj = OI.readTrajectory("/home/lvuser/paths/LeftLineCross.csv");
			}
		}
		else if (position.get().equals(Position.MID)) {
			if (fieldLayout == "RR") {
				if (RR.get().equals(Goal.SWITCH)) {
					traj = OI.readTrajectory("/home/lvuser/paths/MidToRightSwitchFront.csv"); //Or LeftToRightSwitchSide, depending on situation
					return;
				}
				if (RR.get().equals(Goal.SCALE)) {
					traj = OI.readTrajectory("/home/lvuser/paths/MidToRightScale.csv");
					return;
				}
				traj = OI.readTrajectory("/home/lvuser/paths/MidLineCross.csv");
			}
			else if (fieldLayout == "LL") {
				if (LL.get().equals(Goal.SWITCH)) {
					traj = OI.readTrajectory("/home/lvuser/paths/MidToLeftSwitchFront.csv"); //Or LeftToLeftSwitchBack, depending on situation
					return;
				}
				if (LL.get().equals(Goal.SCALE)) {
					traj = OI.readTrajectory("/home/lvuser/paths/MidToLeftScale.csv");
					return;
				}
				traj = OI.readTrajectory("/home/lvuser/paths/MidLineCross.csv");
			}
			else if (fieldLayout == "LR") {
				if (LR.get().equals(Goal.SWITCH)) {
					traj = OI.readTrajectory("/home/lvuser/paths/MidToLeftSwitchFront.csv"); //Or LeftToLeftSwitchBack, depending on situation
					return;
				}
				if (LR.get().equals(Goal.SCALE)) {
					traj = OI.readTrajectory("/home/lvuser/paths/MidToRightScale.csv");
					return;
				}
				traj = OI.readTrajectory("/home/lvuser/paths/MidLineCross.csv");
			}
			else if (fieldLayout == "RL") {
				if (RL.get().equals(Goal.SWITCH)) {
					traj = OI.readTrajectory("/home/lvuser/paths/MidToRightSwitchFront.csv"); //Or LeftToRightSwitchSide, depending on situation
					return;
				}
				if (RL.get().equals(Goal.SCALE)) {
					traj = OI.readTrajectory("/home/lvuser/paths/MidToLeftScale.csv");
					return;
				}
				traj = OI.readTrajectory("/home/lvuser/paths/MidLineCross.csv");
			}
		}
		else if (position.get().equals(Position.RIGHT)) {
			if (fieldLayout == "RR") {
				if (RR.get().equals(Goal.SWITCH)) {
					traj = OI.readTrajectory("/home/lvuser/paths/RightToRightSwitchSide.csv"); //Or RightToRightSwitchBack, depending on situation
					return;
				}
				if (RR.get().equals(Goal.SCALE)) {
					traj = OI.readTrajectory("/home/lvuser/paths/RightToRightScale.csv");
					return;
				}
				traj = OI.readTrajectory("/home/lvuser/paths/RightLineCross.csv");
			}
			else if (fieldLayout == "LL") {
				if (LL.get().equals(Goal.SWITCH)) {
					traj = OI.readTrajectory("/home/lvuser/paths/RightToLeftSwitchBack.csv"); //Or RightToLeftSwitchSide, depending on situation
					return;
				}
				if (LL.get().equals(Goal.SCALE)) {
					traj = OI.readTrajectory("/home/lvuser/paths/RightToLeftScale.csv");
					return;
				}
				traj = OI.readTrajectory("/home/lvuser/paths/RightLineCross.csv");
			}
			else if (fieldLayout == "LR") {
				if (LR.get().equals(Goal.SWITCH)) {
					traj = OI.readTrajectory("/home/lvuser/paths/RightToLeftSwitchBack.csv"); //Or RightToLeftSwitchSide, depending on situation
					return;
				}
				if (LR.get().equals(Goal.SCALE)) {
					traj = OI.readTrajectory("/home/lvuser/paths/RightToRightScale.csv");
					return;
				}
				traj = OI.readTrajectory("/home/lvuser/paths/RightLineCross.csv");
				return;
			}
			else if (fieldLayout == "RL") {
				if (RL.get().equals(Goal.SWITCH)) {
					traj = OI.readTrajectory("/home/lvuser/paths/RightToRightSwitchSide.csv"); //Or RightToRightSwitchBack, depending on situation
					return;
				}
				if (RL.get().equals(Goal.SCALE)) {
					traj = OI.readTrajectory("/home/lvuser/paths/RightToLeftScale.csv");
					return;
				}
				traj = OI.readTrajectory("/home/lvuser/paths/RightLineCross.csv");
			}
		}
		else {
			System.err.println("Choose an auton starting position!");
		}
	}
	
}
