package frc.robot;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.Trigger;

import frc.robot.libraries.XboxController1038;
import frc.robot.subsystems.CubeShooter;
import frc.robot.subsystems.Shoulder;
import frc.robot.subsystems.Wrist;
import frc.robot.subsystems.CubeShooter.CubeShooterSetpoints;
import frc.robot.commands.AcquireCubeCommand;
import frc.robot.commands.CubeAcquisitionPositionCommand;
import frc.robot.commands.DisposeCubeCommand;
import frc.robot.commands.ManualShootCubeCommand;
import frc.robot.commands.ShootCubeCommand;
import frc.robot.commands.ShoulderPositionCommand;
import frc.robot.commands.WristPositionCommand;
import frc.robot.constants.CubeShooterConstants;
import frc.robot.constants.IOConstants;

public class OperatorJoystick extends XboxController1038 {
    private CubeShooter cubeShooter = CubeShooter.getInstance();
    private Shoulder shoulder = Shoulder.getInstance();
    private Wrist wrist = Wrist.getInstance();

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

        // Cone Acquisition
        // super.leftTrigger.whileTrue(new AcquireConeCommand());
        // super.leftBumper.whileTrue(new DisposeConeCommand());

        // Cube Acquisition
        super.rightTrigger.whileTrue(new AcquireCubeCommand());
        super.rightBumper.whileTrue(new DisposeCubeCommand());

        // Cube Shooter
        ShootCubeCommand highShootCubeCommand = new ShootCubeCommand(CubeShooterSetpoints.high);
        super.xButton.whileTrue(highShootCubeCommand);

        ShootCubeCommand midShootCubeCommand = new ShootCubeCommand(CubeShooterSetpoints.mid);
        super.aButton.whileTrue(midShootCubeCommand);

        ManualShootCubeCommand manualShootCommand = new ManualShootCubeCommand();
        super.yButton.whileTrue(manualShootCommand);

        super.leftTrigger.onTrue(new InstantCommand(() -> {
            highShootCubeCommand.overrideFeed();
            midShootCubeCommand.overrideFeed();
            manualShootCommand.feedOut();
        }));

        super.bButton.onTrue(new CubeAcquisitionPositionCommand());

        new Trigger(() -> super.getPOVPosition() == PovPositions.Up)
                .onTrue(new InstantCommand(() -> cubeShooter
                        .setShooterSpeed(cubeShooter.getShooterSpeed() + CubeShooterConstants.kShooterSpeedIncrement)));
        new Trigger(() -> super.getPOVPosition() == PovPositions.Down)
                .onTrue(new InstantCommand(() -> cubeShooter
                        .setShooterSpeed(cubeShooter.getShooterSpeed() - CubeShooterConstants.kShooterSpeedIncrement)));

        // Arm + Wrist + Shoulder
        // b toggle arms in and arms out

        leftBumper
                .whileTrue(new ShoulderPositionCommand(60, true))
                .whileTrue(new WristPositionCommand(130, true));
        shoulder.setDefaultCommand(new ShoulderPositionCommand(0, true));
        wrist.setDefaultCommand(new WristPositionCommand(0, true));

        /*
         * TODO we have options for controlling the robot:
         * 1. Use joystick with not PID to send direct power to the shoulder
         * 2. Use joystick with PID to set a setpoint
         * 3. Have buttons with predefined setpoints
         */

        // left stick move shoulder
        // right stick move wrist
    }
}
