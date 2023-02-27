package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.Rev2mDistanceSensor;
import com.revrobotics.Rev2mDistanceSensor.Port;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.constants.ConeAcquisitionConstants;

public class ConeAcquisition extends SubsystemBase {
    private final CANSparkMax coneAcquisitionMotor = new CANSparkMax(
            ConeAcquisitionConstants.kAcquisitionMotorPort,
            MotorType.kBrushless);

    private final Rev2mDistanceSensor distanceSensor = new Rev2mDistanceSensor(Port.kMXP);

    // Singleton Setup
    private static ConeAcquisition instance;

    public static ConeAcquisition getInstance() {
        if (instance == null) {
            instance = new ConeAcquisition();
        }
        return instance;
    }

    private ConeAcquisition() {
        coneAcquisitionMotor.restoreFactoryDefaults();
    }

    public double getDistanceSensor() {
        return distanceSensor.GetRange();
    }

    public void acquireCone() {
        coneAcquisitionMotor.set(ConeAcquisitionConstants.kConstantMotorSpeed);
    }

    public void disposeCone() {
        coneAcquisitionMotor.set(-ConeAcquisitionConstants.kConstantMotorSpeed);
    }

    public void stopMotor() {
        coneAcquisitionMotor.stopMotor();
    }
}
