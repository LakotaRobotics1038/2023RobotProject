package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.constants.CubeAcquisitionConstants;
import frc.robot.constants.PneumaticsConstants;

public final class CubeAcquisition extends SubsystemBase {

    private CANSparkMax acquisitionMotor = new CANSparkMax(CubeAcquisitionConstants.kCubeAcquisitionMotorPort,
            MotorType.kBrushless);
    private DoubleSolenoid acquisitionSolenoid = new DoubleSolenoid(PneumaticsConstants.kModuleType,
            CubeAcquisitionConstants.kPullOutAcquisitionChannel,
            CubeAcquisitionConstants.kPullInAcquisitionChannel);

    public enum AcquisitionStates {
        Down, Up;
    }

    private AcquisitionStates currentState;

    // Singleton Setup
    private static CubeAcquisition instance;

    public static CubeAcquisition getInstance() {
        if (instance == null) {
            instance = new CubeAcquisition();
        }
        return instance;
    }

    private CubeAcquisition() {
        setPosition(AcquisitionStates.Up);
        acquisitionMotor.restoreFactoryDefaults();
    }

    public void acquire() {
        if (currentState.equals(AcquisitionStates.Down)) {
            acquisitionMotor.set(CubeAcquisitionConstants.kCubeAcquisitionMotorSpeed);
        }
    }

    public void dispose() {
        if (currentState.equals(AcquisitionStates.Down)) {
            acquisitionMotor.set(-CubeAcquisitionConstants.kCubeAcquisitionMotorSpeed);
        }
    }

    public void stop() {
        acquisitionMotor.stopMotor();
    }

    public void setPosition(AcquisitionStates state) {
        if (state.equals(AcquisitionStates.Up)) {
            acquisitionSolenoid.set(DoubleSolenoid.Value.kReverse);
        } else {
            acquisitionSolenoid.set(DoubleSolenoid.Value.kForward);
        }
        currentState = state;
    }

    public AcquisitionStates getPosition() {
        return currentState;
    }
}
