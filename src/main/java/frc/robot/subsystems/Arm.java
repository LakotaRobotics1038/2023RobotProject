package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.constants.ArmConstants;

public class Arm extends SubsystemBase {

    public ArmExtensionStates armExtensionState = ArmExtensionStates.In;

    private DoubleSolenoid armExtension = new DoubleSolenoid(PneumaticsModuleType.REVPH,
            ArmConstants.kPushOutArmChannel,
            ArmConstants.kPullInArmChannel);

    public enum ArmExtensionStates {
        In, Out;
    }

    private static Arm instance;

    public static Arm getInstance() {
        if (null == instance) {
            instance = new Arm();
        }
        return instance;
    }

    private Arm() {

    }

    public DoubleSolenoid.Value getArmExtension() {
        return armExtension.get();
    }

    public void setArmExtensionPosition(ArmExtensionStates state) {
        switch (state) {
            case In:
                armExtension.set(Value.kReverse);

            case Out:
                armExtension.set(Value.kForward);
                break;
        }
        armExtensionState = state;
    }
}
