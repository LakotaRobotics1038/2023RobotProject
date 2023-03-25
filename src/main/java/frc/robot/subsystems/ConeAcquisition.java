package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
// TODO: get this install working once we need this sensor
// import com.revrobotics.Rev2mDistanceSensor;
// import com.revrobotics.Rev2mDistanceSensor.Port;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.constants.ConeAcquisitionConstants;
import frc.robot.constants.NeoMotorConstants;

public class ConeAcquisition extends SubsystemBase {
    private final CANSparkMax coneAcquisitionMotor = new CANSparkMax(
            ConeAcquisitionConstants.kAcquisitionMotorPort,
            MotorType.kBrushless);

    // private final Rev2mDistanceSensor distanceSensor = new
    // Rev2mDistanceSensor(Port.kMXP);

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
        coneAcquisitionMotor.setSmartCurrentLimit(NeoMotorConstants.kMaxNeo550Current);
        coneAcquisitionMotor.burnFlash();
    }

    public double getConeDistance() {
        // return distanceSensor.GetRange();
        return 0.0;
    }

    public double getCurrent() {
        return coneAcquisitionMotor.getOutputCurrent();
    }

    public void acquire() {
        coneAcquisitionMotor.set(ConeAcquisitionConstants.kAcquireSpeed);
    }

    public void disposeCone() {
        coneAcquisitionMotor.set(-ConeAcquisitionConstants.kAcquireSpeed);
    }

    public void stop() {
        coneAcquisitionMotor.stopMotor();
    }
}
