package frc.robot.subsystems;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.autons.AutonSelector.AutonChoices;
import frc.robot.constants.WristConstants;

public class Dashboard extends SubsystemBase {
    // Inputs
    private DriveTrain driveTrain = DriveTrain.getInstance();
    private CubeShooter cubeShooter = CubeShooter.getInstance();
    private Shoulder shoulder = Shoulder.getInstance();
    private Wrist wrist = Wrist.getInstance();

    // Choosers
    private SendableChooser<AutonChoices> autoChooser = new SendableChooser<>();

    // Tabs
    private ShuffleboardTab driversTab = Shuffleboard.getTab("Drivers");
    private ShuffleboardTab controlsTab = Shuffleboard.getTab("Controls");

    // Controls Tab Inputs
    private GenericEntry resetGyro = controlsTab.add("Reset Gyro", false)
            .withSize(1, 1)
            .withPosition(1, 1)
            .withWidget(BuiltInWidgets.kToggleButton)
            .getEntry();
    private GenericEntry wristP = driversTab.add("Wrist P", WristConstants.kP)
            .withPosition(2, 3)
            .withSize(1, 1)
            .getEntry();
    private GenericEntry wristI = driversTab.add("Wrist I", WristConstants.kI)
            .withPosition(3, 3)
            .withSize(1, 1)
            .getEntry();
    private GenericEntry wristD = driversTab.add("Wrist D", WristConstants.kD)
            .withPosition(4, 3)
            .withSize(1, 1)
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
        UsbCamera camera = CameraServer.startAutomaticCapture();
        Shuffleboard.selectTab("Drivers");

        driversTab.add("Auton Choices", autoChooser)
                .withPosition(0, 0)
                .withSize(2, 1);

        driversTab.addNumber("Gyro", driveTrain::getHeading)
                .withPosition(2, 0);
        // .withWidget(BuiltInWidgets.kGyro);

        driversTab.addNumber("Shooter Speed", cubeShooter::getVelocity)
                .withPosition(0, 1);

        driversTab.addNumber("Shooter Power", cubeShooter::getShooterSpeed)
                .withPosition(1, 1);

        driversTab.addNumber("Shoulder Position", shoulder::getPosition)
                .withPosition(0, 2);

        driversTab.addNumber("Shoulder Setpoint", shoulder::getSetpoint)
                .withPosition(1, 2);

        driversTab.addNumber("Wrist Position", wrist::getPosition)
                .withPosition(0, 3);

        driversTab.addNumber("Wrist Setpoint", wrist::getSetpoint)
                .withPosition(1, 3);

        driversTab.add(camera)
                .withPosition(5, 0)
                .withSize(4, 4);
    }

    @Override
    public void periodic() {
        // Controls Tab
        if (resetGyro.getBoolean(false)) {
            driveTrain.zeroHeading();
            resetGyro.setBoolean(false);
        }
        wrist.setP(wristP.getDouble(WristConstants.kP));
        wrist.setI(wristI.getDouble(WristConstants.kI));
        wrist.setD(wristD.getDouble(WristConstants.kD));
    }

    public SendableChooser<AutonChoices> getAutoChooser() {
        return autoChooser;
    }
}
