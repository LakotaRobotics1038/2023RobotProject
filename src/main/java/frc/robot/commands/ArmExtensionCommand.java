package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Arm.ArmExtensionStates;

/**
 * @deprecated
 *             Use {@link HybridAcquisitionPositionCommand} instead
 */
@Deprecated
public class ArmExtensionCommand extends CommandBase {
    private Arm arm = Arm.getInstance();

    private ArmExtensionStates state;

    public ArmExtensionCommand(ArmExtensionStates state) {
        this.state = state;
        this.addRequirements(arm);
    }

    public ArmExtensionCommand() {
        this.addRequirements(arm);
    }

    @Override
    public void execute() {
        if (this.state != null) {
            this.arm.setPosition(state);
        } else {
            switch (arm.getPosition()) {
                case In:
                    this.arm.setPosition(ArmExtensionStates.Out);
                    break;
                case Out:
                    this.arm.setPosition(ArmExtensionStates.In);
                    break;
            }
        }
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
