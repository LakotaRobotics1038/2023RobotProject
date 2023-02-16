package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.robot.constants.CubeAcquisitionConstants;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.Subsystem;

public class CubeAcquisition implements Subsystem {
    private CANSparkMax feederMotor = new CANSparkMax(CubeAcquisitionConstants.kCubeAcquisitionFeederPort,
            MotorType.kBrushless);
    private CANSparkMax acquisitionMotor = new CANSparkMax(CubeAcquisitionConstants.k, MotorType.kBrushless);
    private DoubleSolenoid acquisitionSolenoid = new DoubleSolenoid(PneumaticsModuleType.REVPH,
            CubeAcquisitionConstants.kPullOutAcquisitionChannel,
            CubeAcquisitionConstants.kPullInAcquisitionChannel);

    private enum AcquisitionStates {
        Down, Up;
    }

    private AcquisitionStates currentState;

    private static CubeAcquisition instance;

    public static CubeAcquisition getinstanceance() {
        if (instance == null) {
            instance = new CubeAcquisition();
        }
        return instance;

    }

    private CubeAcquisition() {
        setPosition(AcquisitionStates.Down);
    }

    public void activateAquisition() {
        if (currentState.equals(AcquisitionStates.Down)) {
            acquisitionMotor.set(CubeAcquisitionConstants.kCubeAcquisitionMotorSpeed);
        }
    }

    public void stopAcquisition() {
        acquisitionMotor.set(0);
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
        feederMotor.set(CubeAcquisitionConstants.kCubeAcquisitionFeederSpeed);
    }

    public void stopTheFeeder() {
        feederMotor.set(0);
    }
}
