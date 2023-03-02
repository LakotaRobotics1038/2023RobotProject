package frc.robot;

import frc.robot.constants.IOConstants;
import frc.robot.libraries.FlightStick1038;
import frc.robot.subsystems.DriveTrain;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;

public class DriverFlightStick extends FlightStick1038 {
    // Subsystem Dependencies
    private final DriveTrain driveTrain = DriveTrain.getInstance();

    // Singleton Setup
    private static DriverFlightStick instance;

    public static DriverFlightStick getInstance() {
        if (instance == null) {
            System.out.println("Creating a new Driver Flight Stick");
            instance = new DriverFlightStick();
        }
        return instance;
    }

    private DriverFlightStick() {
        super(IOConstants.kDriverControllerPort);

        driveTrain.setDefaultCommand(new RunCommand(() -> {
            double sideways = super.getXAxis();
            double forward = super.getYAxis();
            double rotate = super.getZAxis();

            driveTrain.drive(-forward, -sideways, -rotate, true, true);
        }, driveTrain));

        // Re-orient robot to the field
        super.button3.whileTrue(new InstantCommand(driveTrain::zeroHeading, driveTrain));

        // Lock the wheels into an X formation
        super.button4.whileTrue(new RunCommand(driveTrain::setX, driveTrain));
    }
}
