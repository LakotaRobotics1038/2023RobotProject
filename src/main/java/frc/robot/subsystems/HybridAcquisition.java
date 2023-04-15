package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
// TODO: get this install working once we need this sensor
// import com.revrobotics.Rev2mDistanceSensor;
// import com.revrobotics.Rev2mDistanceSensor.Port;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.constants.HybridAcquisitionConstants;
import frc.robot.constants.NeoMotorConstants;

public class HybridAcquisition extends SubsystemBase {
    private final CANSparkMax coneAcquisitionMotor = new CANSparkMax(
            HybridAcquisitionConstants.kAcquisitionMotorPort,
            MotorType.kBrushless);

    // private final Rev2mDistanceSensor distanceSensor = new
    // Rev2mDistanceSensor(Port.kMXP);

    // Singleton Setup
    private static HybridAcquisition instance;

    public static HybridAcquisition getInstance() {
        if (instance == null) {
            instance = new HybridAcquisition();
        }
        return instance;
    }

    private HybridAcquisition() {
        coneAcquisitionMotor.restoreFactoryDefaults();
        coneAcquisitionMotor.setIdleMode(IdleMode.kBrake);
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

    public void acquireCone(double speed) {
        speed = MathUtil.clamp(speed, NeoMotorConstants.kMinPower, NeoMotorConstants.kMaxPower);
        coneAcquisitionMotor.set(speed);
    }

    public void disposeCone() {
        coneAcquisitionMotor.set(-HybridAcquisitionConstants.kAcquireSpeed);
    }

    public void acquireCube(double speed) {
        speed = MathUtil.clamp(speed, -NeoMotorConstants.kMaxPower, -NeoMotorConstants.kMinPower);
        coneAcquisitionMotor.set(speed);
    }

    public void disposeCube() {
        coneAcquisitionMotor.set(HybridAcquisitionConstants.kAcquireSpeed);
    }

    public void stop() {
        coneAcquisitionMotor.stopMotor();
    }
}
