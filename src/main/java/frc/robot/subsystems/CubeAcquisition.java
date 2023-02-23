package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.constants.CubeAcquisitionConstants;
import frc.robot.constants.PneumaticsConstants;


public final class CubeAcquisition extends SubsystemBase {
    private CANSparkMax feederMotor = new CANSparkMax(CubeAcquisitionConstants.kCubeAcquisitionFeederMotorPort,
            MotorType.kBrushless);
    private CANSparkMax acquisitionMotor = new CANSparkMax(CubeAcquisitionConstants.kCubeAcquisitionMotorPort,
            MotorType.kBrushless);
    private DoubleSolenoid acquisitionSolenoid = new DoubleSolenoid(PneumaticsConstants.kModuleType,
            CubeAcquisitionConstants.kPullOutAcquisitionChannel,
            CubeAcquisitionConstants.kPullInAcquisitionChannel);

    private enum AcquisitionStates {
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
        setPosition(AcquisitionStates.Down);
        feederMotor.restoreFactoryDefaults();
        acquisitionMotor.restoreFactoryDefaults();
    }

    public void activateAquisition() {
        if (currentState.equals(AcquisitionStates.Down)) {
            acquisitionMotor.set(CubeAcquisitionConstants.kCubeAcquisitionMotorSpeed);
        }
    }

    public void stopAcquisition() {
        acquisitionMotor.stopMotor();
    }

    public void setPosition(AcquisitionStates state) {
        if (state.equals(AcquisitionStates.Up)) {
            acquisitionSolenoid.set(DoubleSolenoid.Value.kReverse);
            currentState = state;
        } else {
            acquisitionSolenoid.set(DoubleSolenoid.Value.kForward);
            currentState = state;
        }
    }

    public void activateFeeder() {
        feederMotor.set(CubeAcquisitionConstants.kCubeAcquisitionFeederMotorSpeed);
    }

    public void stopFeeder() {
        feederMotor.stopMotor();
    }
}
