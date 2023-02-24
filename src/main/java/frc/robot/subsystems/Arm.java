package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.constants.ArmConstants;
import frc.robot.constants.PneumaticsConstants;

public class Arm extends SubsystemBase {
    DoubleSolenoid armExtension = new DoubleSolenoid(PneumaticsConstants.kModuleType,
            ArmConstants.kPushOutArmChannel,
            ArmConstants.kPullInArmChannel);

    private enum ArmExtensionStates {
        In, Out;
    }

    // Singleton Setup
    private static Arm instance;

    public Arm getInstance() {
        if (null == instance) {
            instance = new Arm();
        }
        return instance;
    }

    private Arm() {

    }

    public void setArmExtensionPosition(ArmExtensionStates state) {
        if (state.equals(ArmExtensionStates.In)) {
            armExtension.set(DoubleSolenoid.Value.kReverse);
        } else {
            armExtension.set(DoubleSolenoid.Value.kForward);
        }
    }

}
