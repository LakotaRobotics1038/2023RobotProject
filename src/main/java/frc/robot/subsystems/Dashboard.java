package frc.robot.subsystems;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.autons.Auton;
import frc.robot.constants.CubeShooterConstants;

public class Dashboard extends SubsystemBase {
    // Inputs
    private DriveTrain driveTrain = DriveTrain.getInstance();
    private CubeShooter cubeShooter = CubeShooter.getInstance();

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
    private GenericEntry shooterP = driversTab.add("Shooter P", CubeShooterConstants.kP)
            .withPosition(1, 3)
            .withSize(1, 1)
            .getEntry();
    private GenericEntry shooterI = driversTab.add("Shooter I", CubeShooterConstants.kI)
            .withPosition(2, 3)
            .withSize(1, 1)
            .getEntry();
    private GenericEntry shooterD = driversTab.add("Shooter D", CubeShooterConstants.kD)
            .withPosition(3, 3)
            .withSize(1, 1)
            .getEntry();
    private GenericEntry shooterFF = driversTab.add("Shooter FF", CubeShooterConstants.kFF)
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

        driversTab.addNumber("Shooter Power", cubeShooter::getShooterSpeed)
                .withPosition(1, 2);

        driversTab.addNumber("Shooter Speed", cubeShooter::getVelocity)
                .withPosition(1, 1);

        driversTab.add(camera);
    }

    @Override
    public void periodic() {
        // Controls Tab
        if (resetGyro.getBoolean(false)) {
            driveTrain.zeroHeading();
            resetGyro.setBoolean(false);
        }
        cubeShooter.setP(shooterP.getDouble(CubeShooterConstants.kP));
        cubeShooter.setI(shooterI.getDouble(CubeShooterConstants.kI));
        cubeShooter.setD(shooterD.getDouble(CubeShooterConstants.kD));
        cubeShooter.setFF(shooterFF.getDouble(CubeShooterConstants.kFF));
    }

    public SendableChooser<Auton> getAutoChooser() {
        return autoChooser;
    }
}
