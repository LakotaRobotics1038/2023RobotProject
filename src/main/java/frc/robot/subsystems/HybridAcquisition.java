package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.constants.HybridAcquisitionConstants;
import frc.robot.constants.NeoMotorConstants;

public class HybridAcquisition extends SubsystemBase {
    private final CANSparkMax acquisitionMotor = new CANSparkMax(
            HybridAcquisitionConstants.kAcquisitionMotorPort,
            MotorType.kBrushless);

    public enum HybridAcquisitionTypes {
        Cone,
        Cube
    }

    // Singleton Setup
    private static HybridAcquisition instance;

    public static HybridAcquisition getInstance() {
        if (instance == null) {
            instance = new HybridAcquisition();
        }
        return instance;
    }

    private HybridAcquisition() {
        acquisitionMotor.restoreFactoryDefaults();
        acquisitionMotor.setIdleMode(IdleMode.kBrake);
        acquisitionMotor.setSmartCurrentLimit(NeoMotorConstants.kMaxNeo550Current);
        acquisitionMotor.burnFlash();
    }

    public double getCurrent() {
        return acquisitionMotor.getOutputCurrent();
    }

    public void acquire(HybridAcquisitionTypes type, double speed) {
        switch (type) {
            case Cone:
                speed = MathUtil.clamp(speed, NeoMotorConstants.kMinPower, NeoMotorConstants.kMaxPower);
                break;
            case Cube:
                speed = MathUtil.clamp(speed, -NeoMotorConstants.kMaxPower, -NeoMotorConstants.kMinPower);
                break;
        }

        acquisitionMotor.set(speed);
    }

    public void dispose(HybridAcquisitionTypes type) {
        switch (type) {
            case Cone:
                acquisitionMotor.set(-HybridAcquisitionConstants.kAcquireSpeed);
                break;
            case Cube:
                acquisitionMotor.set(HybridAcquisitionConstants.kAcquireSpeed);
                break;
        }
    }

    public void stop() {
        acquisitionMotor.stopMotor();
    }
}
