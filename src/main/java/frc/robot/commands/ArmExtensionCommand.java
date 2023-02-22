package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Arm.ArmExtensionStates;

public class ArmExtensionCommand extends CommandBase {
    private Arm arm = Arm.getInstance();

    private ArmExtensionStates state;

    public ArmExtensionCommand(ArmExtensionStates state) {
        this.state = state;
        this.addRequirements(arm);
    }

    public ArmExtensionCommand() {

    }

    @Override

    public void execute() {

        /*
         * If a new extension position was explicitly passed set it,
         * If not toggle the current position
         */
        if (this.state != null) {
            this.arm.setArmExtensionPosition(state);
        } else {
            switch (state) {
                case In: // In -> Out
                    this.arm.setArmExtensionPosition(ArmExtensionStates.Out);
                    break;
                case Out: // Out -> In
                    this.arm.setArmExtensionPosition(ArmExtensionStates.In);
                    break;
            }
        }
    }

    @Override
    public boolean isFinished() {
        return true;
    }

}
