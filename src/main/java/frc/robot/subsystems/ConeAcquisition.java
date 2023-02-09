package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.playingwithfusion.TimeOfFlight;

public class ConeAcquisition {
    private final int ACQUISITION_MOTOR_PORT = 0;
    private final double CONSTANT_MOTOR_SPEED = 1;

    private final CANSparkMax coneAcquisitionMotor = new CANSparkMax(ACQUISITION_MOTOR_PORT, MotorType.kBrushless);

    private final TimeOfFlight distanceSensor = new TimeOfFlight(ACQUISITION_MOTOR_PORT);

    ConeAcquisition() {

    }

    public TimeOfFlight TimeOfFlight() {
        return getRange(distanceSensor);
    }
}
