// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.hal.ControlWord;
import edu.wpi.first.hal.DriverStationJNI;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.autons.Auton;
import frc.robot.autons.AutonSelector;
import frc.robot.constants.SwerveModuleConstants;
import frc.robot.subsystems.Compressor1038;
import frc.robot.subsystems.Dashboard;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.SwagLights;

public class Robot extends TimedRobot {
    // Singleton Instances
    private AutonSelector autonSelector = AutonSelector.getInstance();
    private SwagLights swagLights = SwagLights.getInstance();

    // Variables
    private Auton autonomousCommand;
    private ControlWord controlWordCache = new ControlWord();

    // Subsystems
    private DriveTrain driveTrain = DriveTrain.getInstance();

    @Override
    public void robotInit() {
        DriverXboxController.getInstance();
        OperatorJoystick.getInstance();
        Dashboard.getInstance();
    }

    @Override
    public void robotPeriodic() {
        CommandScheduler.getInstance().run();
    }

    @Override
    public void disabledInit() {
        System.out.println("Robot Disabled");
        DriverStationJNI.getControlWord(controlWordCache);
        if (controlWordCache.getEStop()) {
            swagLights.setEStop();
        } else {
            swagLights.setDisabled(true);
        }
    }

    @Override
    public void disabledPeriodic() {
    }

    @Override
    public void disabledExit() {
        swagLights.setDisabled(false);
    }

    @Override
    public void autonomousInit() {
        autonomousCommand = autonSelector.chooseAuton();

        if (autonomousCommand != null) {
            driveTrain.resetOdometry(autonomousCommand.getInitialPose());
            driveTrain.setDrivingIdleMode(SwerveModuleConstants.kAutoDrivingMotorIdleMode);
            autonomousCommand.schedule();
        }
    }

    @Override
    public void autonomousPeriodic() {
    }

    @Override
    public void autonomousExit() {
        if (autonomousCommand != null) {
            autonomousCommand.cancel();
        }
    }

    @Override
    public void teleopInit() {
        Dashboard.getInstance().clearTrajectory();
        driveTrain.setDrivingIdleMode(SwerveModuleConstants.kTeleopDrivingMotorIdleMode);
    }

    @Override
    public void teleopPeriodic() {
        Compressor1038.getInstance().run();
    }

    @Override
    public void teleopExit() {
    }

    @Override
    public void testInit() {
        CommandScheduler.getInstance().cancelAll();
    }

    @Override
    public void testPeriodic() {
    }

    @Override
    public void testExit() {
    }
}
