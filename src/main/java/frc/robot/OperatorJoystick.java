package frc.robot;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.Trigger;

import frc.robot.libraries.XboxController1038;
import frc.robot.subsystems.CubeShooter;
import frc.robot.subsystems.Dashboard;
import frc.robot.subsystems.Shoulder;
import frc.robot.subsystems.SwagLights;
import frc.robot.subsystems.Wrist;
import frc.robot.subsystems.CubeAcquisition.AcquisitionStates;
import frc.robot.subsystems.CubeShooter.CubeShooterSetpoints;
import frc.robot.subsystems.Dashboard.Cameras;
import frc.robot.subsystems.Shoulder.ShoulderSetpoints;
import frc.robot.subsystems.Wrist.WristSetpoints;
import frc.robot.commands.AcquireConeCommand;
import frc.robot.commands.AcquireCubeCommand;
import frc.robot.commands.ConeAcquisitionPositionCommand;
import frc.robot.commands.CubeAcquisitionPositionCommand;
import frc.robot.commands.DisposeConeCommand;
import frc.robot.commands.DisposeCubeCommand;
import frc.robot.commands.ManualShootCubeCommand;
import frc.robot.commands.ShootCubeCommand;
import frc.robot.commands.ShoulderPositionCommand;
import frc.robot.commands.WristPositionCommand;
import frc.robot.commands.ConeAcquisitionPositionCommand.FinishActions;
import frc.robot.constants.CubeShooterConstants;
import frc.robot.constants.IOConstants;

public class OperatorJoystick extends XboxController1038 {
    private Dashboard dashboard = Dashboard.getInstance();
    private CubeShooter cubeShooter = CubeShooter.getInstance();
    private Shoulder shoulder = Shoulder.getInstance();
    private Wrist wrist = Wrist.getInstance();
    private SwagLights swagLights = SwagLights.getInstance();

    private boolean isCube = true;

    // Singleton Setup
    private static OperatorJoystick instance;

    public static OperatorJoystick getInstance() {
        if (instance == null) {
            System.out.println("Creating a new Operator");
            instance = new OperatorJoystick();
        }
        return instance;
    }

    private OperatorJoystick() {
        super(IOConstants.kOperatorControllerPort);
        swagLights.setOperatorState(isCube);

        // Mode Toggle
        startButton
                .onTrue(new InstantCommand(() -> {
                    this.isCube = !this.isCube;
                    swagLights.setOperatorState(isCube);
                    if (this.isCube) {
                        new ConeAcquisitionPositionCommand(WristSetpoints.storage, ShoulderSetpoints.storage, false)
                                .schedule();
                        dashboard.setCamera(Cameras.cubeCamera);
                        wrist.setDefaultCommand(new WristPositionCommand(WristSetpoints.storage, true));
                    } else {
                        new CubeAcquisitionPositionCommand(AcquisitionStates.Up).schedule();
                        dashboard.setCamera(Cameras.coneCamera);
                        wrist.setDefaultCommand(new WristPositionCommand(WristSetpoints.carry, true));
                    }
                }));

        // Cube Acquisition
        rightTrigger
                .and(() -> this.isCube)
                .whileTrue(new AcquireCubeCommand());
        rightBumper
                .and(() -> this.isCube)
                .whileTrue(new DisposeCubeCommand());
        bButton
                .and(() -> this.isCube)
                .onTrue(new CubeAcquisitionPositionCommand());

        // Cone Acquisition
        rightTrigger
                .and(() -> !this.isCube)
                .whileTrue(new AcquireConeCommand());
        rightBumper
                .and(() -> !this.isCube)
                .whileTrue(new DisposeConeCommand());

        // Cube Shooter
        // High
        ShootCubeCommand highShootCubeCommand = new ShootCubeCommand(CubeShooterSetpoints.high);
        yButton
                .and(() -> this.isCube)
                .whileTrue(highShootCubeCommand);

        // Mid
        ShootCubeCommand midShootCubeCommand = new ShootCubeCommand(CubeShooterSetpoints.mid);
        xButton
                .and(() -> this.isCube)
                .whileTrue(midShootCubeCommand);

        // Manual
        ManualShootCubeCommand manualShootCommand = new ManualShootCubeCommand();
        aButton
                .and(() -> this.isCube)
                .whileTrue(manualShootCommand);

        leftTrigger
                .and(() -> this.isCube)
                .onTrue(new InstantCommand(() -> {
                    highShootCubeCommand.overrideFeed();
                    midShootCubeCommand.overrideFeed();
                    manualShootCommand.feedOut();
                }));

        new Trigger(() -> getPOVPosition() == PovPositions.Up)
                .and(() -> this.isCube)
                .onTrue(new InstantCommand(() -> cubeShooter
                        .setShooterSpeed(cubeShooter.getShooterSpeed() + CubeShooterConstants.kShooterSpeedIncrement)));
        new Trigger(() -> getPOVPosition() == PovPositions.Down)
                .and(() -> this.isCube)
                .onTrue(new InstantCommand(() -> cubeShooter
                        .setShooterSpeed(cubeShooter.getShooterSpeed() - CubeShooterConstants.kShooterSpeedIncrement)));

        // Wrist + Shoulder
        // High
        yButton
                .and(() -> !this.isCube)
                .toggleOnTrue(new ConeAcquisitionPositionCommand(
                        WristSetpoints.high,
                        ShoulderSetpoints.high,
                        true,
                        FinishActions.NoFinish));

        // Mid
        xButton
                .and(() -> !this.isCube)
                .toggleOnTrue(new ConeAcquisitionPositionCommand(
                        WristSetpoints.mid,
                        ShoulderSetpoints.mid,
                        false,
                        FinishActions.NoFinish));

        // Low
        aButton
                .and(() -> !this.isCube)
                .toggleOnTrue(new ConeAcquisitionPositionCommand(
                        WristSetpoints.acquire,
                        ShoulderSetpoints.acquire,
                        true,
                        FinishActions.NoFinish));

        // Storage
        bButton
                .and(() -> !this.isCube)
                .toggleOnTrue(new ConeAcquisitionPositionCommand(
                        WristSetpoints.humanPlayer,
                        ShoulderSetpoints.humanPlayer,
                        false,
                        FinishActions.NoFinish));
    }

    /**
     * Sets the default command for the shoulder and wrist. This should be done in
     * {@link Robot#teleopInit} so that the cone scoring mechanism goes to storage
     * when the operator cancels their desired position
     */
    public void enableDefaults() {
        shoulder.setDefaultCommand(new ShoulderPositionCommand(ShoulderSetpoints.storage, true));
        wrist.setDefaultCommand(new WristPositionCommand(WristSetpoints.storage, true));
    }

    /**
     * Removes the default command for the shoulder and wrist. This should be done
     * in {@link Robot#autonomousInit} so that the cone scoring mechanism stays in
     * each position until specifically instructed to change by the auton
     */
    public void clearDefaults() {
        shoulder.removeDefaultCommand();
        wrist.removeDefaultCommand();
    }

    public boolean getCube() {
        return isCube;
    }
}
