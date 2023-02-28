package frc.robot;

import frc.robot.libraries.XboxController1038;
import frc.robot.subsystems.CubeShooter.CubeShooterSetpoints;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.commands.AcquireConeCommand;
import frc.robot.commands.AcquireCubeCommand;
import frc.robot.commands.ArmExtensionCommand;
import frc.robot.commands.CubeAcquisitionPositionCommand;
import frc.robot.commands.DisposeConeCommand;
import frc.robot.commands.ManualShootCubeCommand;
import frc.robot.commands.ShootCubeCommand;
import frc.robot.commands.ShoulderPositionCommand;
import frc.robot.constants.IOConstants;

public class OperatorJoystick extends XboxController1038 {
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
        super.leftTrigger.whileTrue(new AcquireConeCommand());
        super.leftBumper.whileTrue(new DisposeConeCommand());

        // Cube Acquisition
        super.rightTrigger.whileTrue(new AcquireCubeCommand());
        // TODO replace CubeAcquisitionPositionCommand() with DisposeCubeCommand()
        super.rightBumper.onTrue(new CubeAcquisitionPositionCommand());

        // Cube Shooter
        ShootCubeCommand highShootCubeCommand = new ShootCubeCommand(CubeShooterSetpoints.high);
        super.xButton.whileTrue(highShootCubeCommand);

        ShootCubeCommand lowShootCubeCommand = new ShootCubeCommand(CubeShooterSetpoints.low);
        super.aButton.whileTrue(lowShootCubeCommand);

        // Arm + Wrist + Shoulder
        // b toggle arms in and arms out
        super.bButton.onTrue(new ArmExtensionCommand());

        // y manual revving shooter wheels
        super.yButton.whileTrue(new ManualShootCubeCommand());

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
