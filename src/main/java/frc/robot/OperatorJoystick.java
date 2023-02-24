package frc.robot;

import frc.robot.libraries.XboxController1038;
import frc.robot.commands.AcquireConeCommand;
import frc.robot.commands.AcquireCubeCommand;
import frc.robot.commands.CubeAcquisitionPositionCommand;
import frc.robot.commands.DisposeConeCommand;
import frc.robot.commands.ShootCubeCommand;
import frc.robot.constants.IOConstants;
import frc.robot.subsystems.CubeShooter;
import edu.wpi.first.wpilibj2.command.RunCommand;

public class OperatorJoystick extends XboxController1038 {

    private final CubeShooter cubeShooter = CubeShooter.getInstance();

    private static OperatorJoystick instance;

    public static OperatorJoystick getIntance() {
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
        super.yButton.whenPressed(new CubeAcquisitionPositionCommand());

        // Cube Shooter
        super.leftBumper.whileTrue(new ShootCubeCommand());
        super.leftTrigger.whileTrue(new RunCommand(cubeShooter::loadCube, cubeShooter));

        // Arm + Wrist + Shoulder

    }
}
