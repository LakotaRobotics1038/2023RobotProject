package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import frc.robot.constants.AcquisitionConstants;
import com.playingwithfusion.TimeOfFlight;

public class ConeAcquisition {
    private final CANSparkMax coneAcquisitionMotor = new CANSparkMax(AcquisitionConstants.kAcquisitonMotorPort,
            MotorType.kBrushless);

    private final TimeOfFlight distanceSensor = new TimeOfFlight(AcquisitionConstants.kAcquisitonMotorPort);

    private static ConeAcquisition instance;

    private ConeAcquisition() {

    }

    public double getDistanceSensor() {
        return distanceSensor.getRange();
    }

    public void AccuireCone() {
        coneAcquisitionMotor.set(AcquisitionConstants.kConstantMotorSpeed);
    }

    public void DisposeOfCode() {
        coneAcquisitionMotor.set(-AcquisitionConstants.kConstantMotorSpeed);
    }

    public static ConeAcquisition getInstance() {
        if (instance == null) {
            instance = new ConeAcquisition();
        }
        return instance;
    }
}
