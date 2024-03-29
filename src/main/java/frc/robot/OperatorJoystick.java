package frc.robot;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.Trigger;

import frc.robot.libraries.XboxController1038;
import frc.robot.subsystems.CubeShooter;
import frc.robot.subsystems.Shoulder;
import frc.robot.subsystems.SwagLights;
import frc.robot.subsystems.Vision;
import frc.robot.subsystems.Wrist;
import frc.robot.subsystems.CubeAcquisition.AcquisitionStates;
import frc.robot.subsystems.CubeShooter.CubeShooterSetpoints;
import frc.robot.subsystems.HybridAcquisition.HybridAcquisitionTypes;
import frc.robot.subsystems.Shoulder.ShoulderSetpoints;
import frc.robot.subsystems.Vision.CameraStream;
import frc.robot.subsystems.Wrist.WristSetpoints;
import frc.robot.commands.AcquireHybridCommand;
import frc.robot.commands.AcquireCubeCommand;
import frc.robot.commands.HybridAcquisitionPositionCommand;
import frc.robot.commands.CubeAcquisitionPositionCommand;
import frc.robot.commands.DisposeHybridCommand;
import frc.robot.commands.DisposeCubeCommand;
import frc.robot.commands.ManualShootCubeCommand;
import frc.robot.commands.ShootCubeCommand;
import frc.robot.commands.ShoulderPositionCommand;
import frc.robot.commands.WristPositionCommand;
import frc.robot.commands.HybridAcquisitionPositionCommand.FinishActions;
import frc.robot.constants.HybridAcquisitionConstants;
import frc.robot.constants.CubeShooterConstants;
import frc.robot.constants.IOConstants;

public class OperatorJoystick extends XboxController1038 {
    private CubeShooter cubeShooter = CubeShooter.getInstance();
    private Shoulder shoulder = Shoulder.getInstance();
    private Wrist wrist = Wrist.getInstance();
    private SwagLights swagLights = SwagLights.getInstance();
    private Vision vision = Vision.getInstance();

    public enum OperatorStates {
        CubeWhale,
        Cone,
        CubeHybrid
    }

    private OperatorStates currentMode = OperatorStates.CubeWhale;
    private boolean areCubesFat = false;

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
        swagLights.setOperatorState(this.currentMode);

        // Mode Toggle
        startButton
                .onTrue(new InstantCommand(() -> {
                    // Toggle through states
                    switch (currentMode) {
                        case CubeWhale:
                            this.currentMode = OperatorStates.Cone;
                            break;
                        case Cone:
                            this.currentMode = OperatorStates.CubeHybrid;
                            break;
                        case CubeHybrid:
                            this.currentMode = OperatorStates.CubeWhale;
                            break;
                    }

                    // Initialize new state
                    switch (currentMode) {
                        case CubeWhale:
                            new HybridAcquisitionPositionCommand(WristSetpoints.storage, ShoulderSetpoints.storage,
                                    false)
                                    .schedule();
                            wrist.setDefaultCommand(new WristPositionCommand(WristSetpoints.storage, true));
                            new WristPositionCommand(WristSetpoints.storage).schedule();
                            vision.setCamStream(CameraStream.cam0);
                            break;
                        case Cone:
                            new CubeAcquisitionPositionCommand(AcquisitionStates.Up).schedule();
                            wrist.setDefaultCommand(new WristPositionCommand(WristSetpoints.coneCarry, true));
                            new WristPositionCommand(WristSetpoints.coneCarry).schedule();
                            vision.setCamStream(CameraStream.cam1);
                            break;
                        case CubeHybrid:
                            new CubeAcquisitionPositionCommand(AcquisitionStates.Up).schedule();
                            wrist.setDefaultCommand(new WristPositionCommand(WristSetpoints.cubeCarry, true));
                            new WristPositionCommand(WristSetpoints.cubeCarry).schedule();
                            vision.setCamStream(CameraStream.cam1);
                            break;
                    }
                    swagLights.setOperatorState(this.currentMode);
                }));

        // Cube Whale Acquisition
        rightTrigger
                .and(() -> currentMode == OperatorStates.CubeWhale)
                .whileTrue(new AcquireCubeCommand());
        rightBumper
                .and(() -> currentMode == OperatorStates.CubeWhale)
                .whileTrue(new DisposeCubeCommand());
        bButton
                .and(() -> currentMode == OperatorStates.CubeWhale)
                .onTrue(new CubeAcquisitionPositionCommand());

        // Cone Acquisition
        rightTrigger
                .and(() -> currentMode == OperatorStates.Cone)
                .whileTrue(new AcquireHybridCommand(HybridAcquisitionTypes.Cone))
                .onFalse(new AcquireHybridCommand(HybridAcquisitionTypes.Cone,
                        HybridAcquisitionConstants.kHoldConeSpeed));
        rightBumper
                .and(() -> currentMode == OperatorStates.Cone)
                .whileTrue(new DisposeHybridCommand(HybridAcquisitionTypes.Cone));

        // Cube Hybrid Acquisition
        rightTrigger
                .and(() -> currentMode == OperatorStates.CubeHybrid)
                .whileTrue(new AcquireHybridCommand(HybridAcquisitionTypes.Cube));
        rightBumper
                .and(() -> currentMode == OperatorStates.CubeHybrid)
                .whileTrue(new DisposeHybridCommand(HybridAcquisitionTypes.Cube));

        // Cube Shooter
        // High
        ShootCubeCommand highShootCubeCommand = new ShootCubeCommand(CubeShooterSetpoints.high);
        xButton
                .and(() -> currentMode == OperatorStates.CubeWhale)
                .whileTrue(highShootCubeCommand)
                .whileTrue(new RunCommand(() -> {
                    if (cubeShooter.onTarget()) {
                        this.setRumble(RumbleType.kBothRumble, 1);
                    } else {
                        this.setRumble(RumbleType.kBothRumble, 0);
                    }
                }))
                .onFalse(new InstantCommand(() -> {
                    this.setRumble(RumbleType.kBothRumble, 0);
                }));

        // Mid
        ShootCubeCommand midShootCubeCommand = new ShootCubeCommand(CubeShooterSetpoints.mid);
        aButton
                .and(() -> currentMode == OperatorStates.CubeWhale)
                .whileTrue(midShootCubeCommand)
                .whileTrue(new RunCommand(() -> {
                    if (cubeShooter.onTarget()) {
                        this.setRumble(RumbleType.kBothRumble, 1);
                    } else {
                        this.setRumble(RumbleType.kBothRumble, 0);
                    }
                }))
                .onFalse(new InstantCommand(() -> {
                    this.setRumble(RumbleType.kBothRumble, 0);
                }));

        // Manual
        ManualShootCubeCommand manualShootCommand = new ManualShootCubeCommand();
        yButton
                .and(() -> currentMode == OperatorStates.CubeWhale)
                .whileTrue(manualShootCommand);

        leftTrigger
                .and(() -> currentMode == OperatorStates.CubeWhale)
                .whileTrue(new RunCommand(() -> {
                    highShootCubeCommand.feedOut();
                    midShootCubeCommand.feedOut();
                    manualShootCommand.feedOut();
                }));

        new Trigger(() -> getPOVPosition() == PovPositions.Up)
                .and(() -> currentMode == OperatorStates.CubeWhale)
                .onTrue(new InstantCommand(() -> cubeShooter
                        .setShooterSpeed(cubeShooter.getShooterSpeed() + CubeShooterConstants.kShooterSpeedIncrement)));
        new Trigger(() -> getPOVPosition() == PovPositions.Down)
                .and(() -> currentMode == OperatorStates.CubeWhale)
                .onTrue(new InstantCommand(() -> cubeShooter
                        .setShooterSpeed(cubeShooter.getShooterSpeed() - CubeShooterConstants.kShooterSpeedIncrement)));
        new Trigger(() -> getPOVPosition() == PovPositions.Left)
                .and(() -> currentMode == OperatorStates.CubeWhale)
                .onTrue(new InstantCommand(() -> {
                    midShootCubeCommand.setSetpoint(CubeShooterSetpoints.mid);
                    highShootCubeCommand.setSetpoint(CubeShooterSetpoints.high);
                    areCubesFat = false;
                }));
        new Trigger(() -> getPOVPosition() == PovPositions.Right)
                .and(() -> currentMode == OperatorStates.CubeWhale)
                .onTrue(new InstantCommand(() -> {
                    midShootCubeCommand.setSetpoint(CubeShooterSetpoints.midFat);
                    highShootCubeCommand.setSetpoint(CubeShooterSetpoints.highFat);
                    areCubesFat = true;
                }));

        // Wrist + Shoulder
        // Cone High
        yButton
                .and(() -> currentMode == OperatorStates.Cone)
                .toggleOnTrue(new HybridAcquisitionPositionCommand(
                        WristSetpoints.coneHigh,
                        ShoulderSetpoints.coneHigh,
                        true,
                        FinishActions.NoFinish));

        // Cone Mid
        xButton
                .and(() -> currentMode == OperatorStates.Cone)
                .toggleOnTrue(new HybridAcquisitionPositionCommand(
                        WristSetpoints.coneMid,
                        ShoulderSetpoints.coneMid,
                        false,
                        FinishActions.NoFinish));

        // Cone Acq Floor
        aButton
                .and(() -> currentMode == OperatorStates.Cone)
                .toggleOnTrue(new HybridAcquisitionPositionCommand(
                        WristSetpoints.coneAcqFloor,
                        ShoulderSetpoints.coneAcqFloor,
                        true,
                        FinishActions.NoFinish));

        // Cone Human Player
        bButton
                .and(() -> currentMode == OperatorStates.Cone)
                .toggleOnTrue(new HybridAcquisitionPositionCommand(
                        WristSetpoints.coneHumanPlayer,
                        ShoulderSetpoints.coneHumanPlayer,
                        false,
                        FinishActions.NoFinish));

        // Carry Cone
        leftTrigger
                .and(() -> currentMode == OperatorStates.Cone)
                .toggleOnTrue(new HybridAcquisitionPositionCommand(
                        WristSetpoints.coneCarry,
                        ShoulderSetpoints.storage,
                        false));

        // Cube High
        yButton
                .and(() -> currentMode == OperatorStates.CubeHybrid)
                .toggleOnTrue(new HybridAcquisitionPositionCommand(
                        WristSetpoints.cubeHigh,
                        ShoulderSetpoints.cubeHigh,
                        true,
                        FinishActions.NoFinish));

        // Cube Mid
        xButton
                .and(() -> currentMode == OperatorStates.CubeHybrid)
                .toggleOnTrue(new HybridAcquisitionPositionCommand(
                        WristSetpoints.cubeMid,
                        ShoulderSetpoints.cubeMid,
                        false,
                        FinishActions.NoFinish));

        // Cube Acq Floor
        aButton
                .and(() -> currentMode == OperatorStates.CubeHybrid)
                .toggleOnTrue(new HybridAcquisitionPositionCommand(
                        WristSetpoints.cubeAcqFloor,
                        ShoulderSetpoints.cubeAcqFloor,
                        true,
                        FinishActions.NoFinish));

        // Cube Human Player
        bButton
                .and(() -> currentMode == OperatorStates.CubeHybrid)
                .toggleOnTrue(new HybridAcquisitionPositionCommand(
                        WristSetpoints.cubeHumanPlayer,
                        ShoulderSetpoints.cubeHumanPlayer,
                        false,
                        FinishActions.NoFinish));

        // Carry Cube
        leftTrigger
                .and(() -> currentMode == OperatorStates.CubeHybrid)
                .toggleOnTrue(new HybridAcquisitionPositionCommand(
                        WristSetpoints.cubeCarry,
                        ShoulderSetpoints.storage,
                        false));
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

    /**
     * Is the current operator mode in control of a cube mechanism
     *
     * @return is the robot in cube mode
     */
    public boolean isCubeMode() {
        return currentMode == OperatorStates.CubeWhale || currentMode == OperatorStates.CubeHybrid;
    }
}
