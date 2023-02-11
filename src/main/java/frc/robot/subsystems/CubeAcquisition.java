package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Solenoid;

public class CubeAcquisition {
    private CANSparkMax feederMotor = new CANSparkMax(0, MotorType.kBrushless);
    private CANSparkMax acquisitionMotor = new CANSparkMax(0, MotorType.kBrushless);
    private Solenoid acquisitionSolenoid;

    private enum AcquisitionStates {
        In(true), Out(false);

        private final boolean value;

        AcquisitionStates(Boolean value) {
            this.value = value;
        }
    }

    public CubeAcquisition() {

    }

    public void activateAquisition() {
        if (!acquisitionSolenoid.get()) {
            acquisitionMotor.set(0.0);
        }
    }

    public void disactivateAcquisition() {
        acquisitionMotor.set(0);
    }

    public void setPosition(AcquisitionStates state) {
        acquisitionSolenoid.set(state.value);
    }

    public void activateTheFeeder() {
        feederMotor.set(0);
    }

    public void disactivateTheFeeder() {
        feederMotor.set(0);
    }
}
