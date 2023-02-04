// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.autons.Auton;
import frc.robot.autons.AutonSelector;
import frc.robot.subsystems.Dashboard;
import frc.robot.subsystems.DriveTrain;

public class Robot extends TimedRobot {
    private Auton autonomousCommand;
    private AutonSelector autonSelector = AutonSelector.getInstance();

    // Subsystems
    private DriveTrain driveTrain = DriveTrain.getInstance();

    @Override
    public void robotInit() {
        DriverJoystick.getInstance();
        Dashboard.getInstance();
    }

    @Override
    public void robotPeriodic() {
        CommandScheduler.getInstance().run();
    }

    @Override
    public void disabledInit() {
    }

    @Override
    public void disabledPeriodic() {
    }

    @Override
    public void disabledExit() {
    }

    @Override
    public void autonomousInit() {
        autonomousCommand = autonSelector.chooseAuton();

        if (autonomousCommand != null) {
            driveTrain.resetOdometry(autonomousCommand.getInitialPose());
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
    }

    @Override
    public void teleopPeriodic() {
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
