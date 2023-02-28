package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ConeAcquisition;

public class DisposeConeCommand extends CommandBase {
    private ConeAcquisition coneAcquisition = ConeAcquisition.getInstance();

    public DisposeConeCommand() {
        this.addRequirements(coneAcquisition);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void execute() {
        coneAcquisition.disposeCone();
    }

    @Override
    public void end(boolean interrupted) {
        coneAcquisition.stop();
    }
}
