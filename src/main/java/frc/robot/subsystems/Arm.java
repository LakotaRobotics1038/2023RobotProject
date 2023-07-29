package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.constants.ArmConstants;
import frc.robot.constants.PneumaticsConstants;

public class Arm extends SubsystemBase {
    // makes the solenoid variable and adds the channel for which pulls it in and
    // which pushes it out
    private DoubleSolenoid armExtension = new DoubleSolenoid(PneumaticsConstants.kModuleType,
            ArmConstants.kPushOutChannel,
            ArmConstants.kPullInChannel);

    // enum for diffent arm extension states IN and OUT
    public enum ArmExtensionStates {
        In, Out;
    }

    // makes enum for the current state
    private ArmExtensionStates currentState;

    // Singleton Setup
    private static Arm instance;

    public static Arm getInstance() {
        if (null == instance) {
            instance = new Arm();
        }
        return instance;
    }

    // moves the arm in
    private Arm() {
        setPosition(ArmExtensionStates.In);
    }

    // if the arm state says in it will move the arm reverse
    // if the arm state says our it will move the arm forward
    // it will set the current state to what the state is in
    public void setPosition(ArmExtensionStates state) {
        if (state.equals(ArmExtensionStates.In)) {
            armExtension.set(DoubleSolenoid.Value.kReverse);
        } else {
            armExtension.set(DoubleSolenoid.Value.kForward);
        }
        currentState = state;
    }

    // returns the state of the arm
    public ArmExtensionStates getPosition() {
        return currentState;
    }
}
