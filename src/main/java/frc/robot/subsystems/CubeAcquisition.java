package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.constants.CubeAcquisitionConstants;
import frc.robot.constants.PneumaticsConstants;

public final class CubeAcquisition extends SubsystemBase {
    // instatiates the different motors and sets their channel and motor type
    private CANSparkMax acquisitionMotor = new CANSparkMax(CubeAcquisitionConstants.kCubeAcquisitionMotorPort,
            MotorType.kBrushless);
    private DoubleSolenoid acquisitionSolenoid = new DoubleSolenoid(PneumaticsConstants.kModuleType,
            CubeAcquisitionConstants.kPullOutAcquisitionChannel,
            CubeAcquisitionConstants.kPullInAcquisitionChannel);

    // enum for if the whale arms are up or down
    public enum AcquisitionStates {
        Down, Up;
    }

    // makes a variable for what the current state is
    private AcquisitionStates currentState;

    // Singleton Setup
    private static CubeAcquisition instance;

    public static CubeAcquisition getInstance() {
        if (instance == null) {
            instance = new CubeAcquisition();
        }
        return instance;
    }

    // sets the position of the arm to up and resets the settings of the motor
    private CubeAcquisition() {
        setPosition(AcquisitionStates.Up);
        acquisitionMotor.restoreFactoryDefaults();
    }

    // moves the whales down and starts the wheels so you can acquire a cube
    public void acquire() {
        if (currentState.equals(AcquisitionStates.Down)) {
            acquisitionMotor.set(CubeAcquisitionConstants.kCubeAcquisitionMotorSpeed);
        }
    }

    // makes sure the whales are down, then puts the motors to negative speed launch
    // them backwards(out)
    public void dispose() {
        if (currentState.equals(AcquisitionStates.Down)) {
            acquisitionMotor.set(-CubeAcquisitionConstants.kCubeAcquisitionMotorSpeed);
        }
    }

    // Mehtod to stop motor
    public void stop() {
        acquisitionMotor.stopMotor();
    }

    // if the cube state says up it will move the arm reverse
    // if the cube state says down it will move the arm forward
    // it will set the current state to what the state is in
    public void setPosition(AcquisitionStates state) {
        if (state.equals(AcquisitionStates.Up)) {
            acquisitionSolenoid.set(DoubleSolenoid.Value.kReverse);
        } else {
            acquisitionSolenoid.set(DoubleSolenoid.Value.kForward);
        }
        currentState = state;
    }

    // returns current state of the whale arm
    public AcquisitionStates getPosition() {
        return currentState;
    }
}
