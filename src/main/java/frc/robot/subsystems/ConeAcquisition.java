package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import frc.robot.constants.AcquisitionConstants;
import com.playingwithfusion.TimeOfFlight;

public class ConeAcquisition {
    private final CANSparkMax coneAcquisitionMotor = new CANSparkMax(
            AcquisitionConstants.kAcquisitionMotorPort,
            MotorType.kBrushless);

    private final TimeOfFlight distanceSensor = new TimeOfFlight(AcquisitionConstants.kAcquisitionSensorPort);

    private static ConeAcquisition instance;

    public static ConeAcquisition getInstance() {
        if (instance == null) {
            instance = new ConeAcquisition();
        }
        return instance;
    }

    private ConeAcquisition() {

    }

    public double getDistanceSensor() {
        return distanceSensor.getRange();
    }

    public void acquireCone() {
        coneAcquisitionMotor.set(AcquisitionConstants.kConstantMotorSpeed);
    }

    public void disposeCone() {
        coneAcquisitionMotor.set(-AcquisitionConstants.kConstantMotorSpeed);
    }
}
