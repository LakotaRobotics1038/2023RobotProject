package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Arm.ArmExtensionStates;

public class ArmExtensionToggleCommand extends CommandBase {
    private Arm arm = Arm.getInstance();
    private ArmExtensionStates states;

    public ArmExtensionToggleCommand() {
        this.addRequirements(arm);
    }

    @Override
    public boolean isFinished() {
        return true;
    }

    public void execute() {
        switch (states) {
            case In:
                arm.setArmExtensionPosition(ArmExtensionStates.Out);
                break;
            case Out:
                arm.setArmExtensionPosition(ArmExtensionStates.In);
                break;
        }
    }
}
