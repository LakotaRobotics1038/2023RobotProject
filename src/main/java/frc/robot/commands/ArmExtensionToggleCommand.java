package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Arm.ArmExtensionStates;

public class ArmExtensionToggleCommand extends CommandBase {

    private Arm arm = Arm.getInstance();
    private ArmExtensionStates state;

    public ArmExtensionToggleCommand() {
        addRequirements(arm);
    }

    public ArmExtensionToggleCommand(ArmExtensionStates state) {
        addRequirements(arm);
        this.state = state;
    }

    @Override
    public void execute() {
        if (this.state != null) {
            this.arm.setArmExtensionPosition(state);

        } else {
            switch (arm.armExtensionState) {
                case In:
                    this.arm.setArmExtensionPosition(ArmExtensionStates.Out);
                    break;

                case Out:
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
