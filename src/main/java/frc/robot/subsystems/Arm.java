package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Solenoid;

public class Arm {
    Solenoid armExtension;

    private static Arm inst;

    private enum ArmExtensionState {
        In(true),
        Out(false);

        private final boolean value;

        ArmExtensionState(boolean value) {
            this.value = value;
        }
    }

    private Arm() {

    }

    public Arm getInstance() {
        if (null == inst) {
            inst = new Arm();
        }
        return inst;
    }

    public void setArmExtensionPosition(ArmExtensionState state) {
        armExtension.set(state.value);
    }

}
