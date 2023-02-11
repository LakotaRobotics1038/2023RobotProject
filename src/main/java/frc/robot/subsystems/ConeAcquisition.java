package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import frc.robot.constants.AcquisitionConstants;
import com.playingwithfusion.TimeOfFlight;

public class ConeAcquisition {
    private final CANSparkMax coneAcquisitionMotor = new CANSparkMax(AcquisitionConstants.ACQUISITION_MOTOR_PORT,
            MotorType.kBrushless);

    private final TimeOfFlight distanceSensor = new TimeOfFlight(AcquisitionConstants.ACQUISITION_MOTOR_PORT);

    private static ConeAcquisition instance;

    private ConeAcquisition() {

    }

    public double getDistanceSensor() {
        return distanceSensor.getRange();
    }

    public void AccuireCone() {
        coneAcquisitionMotor.set(AcquisitionConstants.CONSTANT_MOTOR_SPEED);
    }

    public void DisposeOfCode() {
        coneAcquisitionMotor.set(-AcquisitionConstants.CONSTANT_MOTOR_SPEED);
    }

    public ConeAcquisition getInstance() {
        if (instance == null) {
            instance = new ConeAcquisition();
        }
        return instance;
    }
}
