package frc.robot;

import frc.robot.libraries.XboxController1038;
import frc.robot.commands.AcquireConeCommand;
import frc.robot.commands.AcquireCubeCommand;
import frc.robot.commands.CubeAcquisitionPositionCommand;
import frc.robot.commands.DisposeConeCommand;
import frc.robot.commands.ShootCubeCommand;
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
        super.rightBumper.whileTrue(new AcquireConeCommand());
        super.rightTrigger.whileTrue(new DisposeConeCommand());

        // Cube Acquisition
        super.bButton.whileTrue(new AcquireCubeCommand());
        super.yButton.onTrue(new CubeAcquisitionPositionCommand());

        // Cube Shooter
        super.leftBumper.whileTrue(new ShootCubeCommand());
        super.leftTrigger.whileTrue(new AcquireCubeCommand());

        // Arm + Wrist + Shoulder

    }
}
