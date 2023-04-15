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

    public void acquireCone(double speed) {
        speed = MathUtil.clamp(speed, NeoMotorConstants.kMinPower, NeoMotorConstants.kMaxPower);
        acquisitionMotor.set(speed);
    }

    public void disposeCone() {
        acquisitionMotor.set(-HybridAcquisitionConstants.kAcquireSpeed);
    }

    public void acquireCube(double speed) {
        speed = MathUtil.clamp(speed, -NeoMotorConstants.kMaxPower, -NeoMotorConstants.kMinPower);
        acquisitionMotor.set(speed);
    }

    public void disposeCube() {
        acquisitionMotor.set(HybridAcquisitionConstants.kAcquireSpeed);
    }

    public void stop() {
        acquisitionMotor.stopMotor();
    }
}
