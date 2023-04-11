package frc.robot;

import frc.robot.constants.DriveConstants;
import frc.robot.constants.IOConstants;
import frc.robot.libraries.XboxController1038;
import frc.robot.subsystems.DriveTrain;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class DriverXboxController extends XboxController1038 {
    // Subsystem Dependencies
    private final DriveTrain driveTrain = DriveTrain.getInstance();

    // Singleton Setup
    private static DriverXboxController instance;

    public static DriverXboxController getInstance() {
        if (instance == null) {
            System.out.println("Creating a new Driver Xbox Controller");
            instance = new DriverXboxController();
        }
        return instance;
    }

    private DriverXboxController() {
        super(IOConstants.kDriverControllerPort);

        driveTrain.setDefaultCommand(new RunCommand(() -> {
            double sideways = super.getLeftX();
            double forward = super.getLeftY();
            double rotate = super.getRightX();

            driveTrain.drive(forward, -sideways, -rotate, true, true);
        }, driveTrain));

        new Trigger(() -> getPOVPosition() == PovPositions.Up)
                .whileTrue(new RunCommand(() -> driveTrain
                        .drive(DriveConstants.kFineAdjustmentPercent, 0, 0, true, true)));
        new Trigger(() -> getPOVPosition() == PovPositions.Down)
                .whileTrue(new RunCommand(() -> driveTrain
                        .drive(-DriveConstants.kFineAdjustmentPercent, 0, 0, true, true)));
        new Trigger(() -> getPOVPosition() == PovPositions.Left)
                .whileTrue(new RunCommand(() -> driveTrain
                        .drive(0, DriveConstants.kFineAdjustmentPercent, 0, true, true)));
        new Trigger(() -> getPOVPosition() == PovPositions.Right)
                .whileTrue(new RunCommand(() -> driveTrain
                        .drive(0, -DriveConstants.kFineAdjustmentPercent, 0, true, true)));

        // Re-orient robot to the field
        super.startButton.whileTrue(new InstantCommand(driveTrain::zeroHeading, driveTrain));

        // Lock the wheels into an X formation
        super.xButton.whileTrue(new RunCommand(driveTrain::setX, driveTrain));
    }
}
