package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.CubeAcquisition;
import frc.robot.subsystems.CubeAcquisition.AcquisitionStates;

public class CubeAcquisitionPositionCommand extends CommandBase {

    private CubeAcquisition cubeAcquisition = CubeAcquisition.getInstance();

    private AcquisitionStates state;

    public CubeAcquisitionPositionCommand(AcquisitionStates state) {
        this.state = state;
        this.addRequirements(cubeAcquisition);

    }

    public CubeAcquisitionPositionCommand() {
        this.addRequirements(cubeAcquisition);
    }

    @Override
    public void execute() {
        if (this.state != null) {
            this.cubeAcquisition.setPosition(state);
        } else {
            switch (cubeAcquisition.getCurrentState()) {
                case Down:
                    this.cubeAcquisition.setPosition(AcquisitionStates.Up);
                    break;
                case Up:
                    this.cubeAcquisition.setPosition(AcquisitionStates.Down);
                    break;
            }
        }
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
