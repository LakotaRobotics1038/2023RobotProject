package frc.robot.subsystems;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.autons.Auton;

public class Dashboard extends SubsystemBase {
    // Inputs
    private DriveTrain driveTrain = DriveTrain.getInstance();

    // Choosers
    private SendableChooser<Auton> autoChooser = new SendableChooser<>();

    // Tabs
    private ShuffleboardTab driversTab = Shuffleboard.getTab("Drivers");
    private ShuffleboardTab controlsTab = Shuffleboard.getTab("Controls");

    // Controls Tab Inputs
    private GenericEntry resetGyro = controlsTab.add("Reset Gyro", false)
            .withSize(1, 1)
            .withPosition(1, 1)
            .withWidget(BuiltInWidgets.kToggleButton)
            .getEntry();

    // Singleton Setup
    private static Dashboard instance;

    public static Dashboard getInstance() {
        if (instance == null) {
            System.out.println("Creating a new Dashboard");
            instance = new Dashboard();
        }
        return instance;
    }

    private Dashboard() {
        super();
        Shuffleboard.selectTab("Drivers");

        driversTab.add("Auton Choices", autoChooser)
                .withPosition(0, 0)
                .withSize(2, 1);

        driversTab.addNumber("Gyro", driveTrain::getHeading)
                .withPosition(2, 0);
        // .withWidget(BuiltInWidgets.kGyro);
    }

    @Override
    public void periodic() {
        // Controls Tab
        if (resetGyro.getBoolean(false)) {
            driveTrain.zeroHeading();
            resetGyro.setBoolean(false);
        }
    }

    public SendableChooser<Auton> getAutoChooser() {
        return autoChooser;
    }
}
