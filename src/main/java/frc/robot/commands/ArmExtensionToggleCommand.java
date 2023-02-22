package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Arm;

public class ArmExtensionToggleCommand extends CommandBase {
    private Arm arm = Arm.getInstance();

    public ArmExtensionToggleCommand() {
        this.addRequirements(arm);
    }

    @Override
    public boolean isFinished() {
        return true;
    }

    public void execute() {
        arm.setArmExtensionPosition(arm.ArmExtensionStates);
    }
}
