package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Arm.ArmExtensionStates;
import frc.robot.subsystems.Shoulder;
import frc.robot.subsystems.Shoulder.ShoulderSetpoints;

public class ShoulderPositionCommand extends CommandBase {
    private Shoulder shoulder = Shoulder.getInstance();
    private Arm arm = Arm.getInstance();
    private ShoulderSetpoints setpoint;
    private boolean noFinish = false;
    private Timer delayTimer = new Timer();
    private double delayTime = 0;

    /**
     * @deprecated
     *             Use {@link HybridAcquisitionPositionCommand} instead
     */
    @Deprecated
    public ShoulderPositionCommand(ShoulderSetpoints setpoint) {
        this.setpoint = setpoint;
        addRequirements(shoulder);
    }

    public ShoulderPositionCommand(ShoulderSetpoints setpoint, boolean noFinish) {
        this.setpoint = setpoint;
        this.noFinish = noFinish;
        addRequirements(shoulder);
    }

    @Override
    public void initialize() {
        if (arm.getPosition() == ArmExtensionStates.Out) {
            arm.setPosition(ArmExtensionStates.In);

            double currentSetpoint = shoulder.getSetpoint();
            if (currentSetpoint == ShoulderSetpoints.coneHigh.value) {
                this.delayTime = ShoulderSetpoints.coneHigh.armDelay;
            } else if (currentSetpoint == ShoulderSetpoints.coneAcqFloor.value) {
                this.delayTime = ShoulderSetpoints.coneAcqFloor.armDelay;
            } else if (currentSetpoint == ShoulderSetpoints.cubeHigh.value) {
                this.delayTime = ShoulderSetpoints.cubeHigh.armDelay;
            } else if (currentSetpoint == ShoulderSetpoints.cubeAcqFloor.value) {
                this.delayTime = ShoulderSetpoints.cubeAcqFloor.armDelay;
            }

            delayTimer.restart();
        } else {
            shoulder.enable();
        }

        shoulder.setSetpoint(setpoint);
    }

    @Override
    public void execute() {
        if (!shoulder.isEnabled() && delayTimer.get() > delayTime) {
            shoulder.enable();
            delayTimer.stop();
        }
    }

    @Override
    public boolean isFinished() {
        return !noFinish && shoulder.onTarget();
    }

    @Override
    public void end(boolean interrupted) {
        shoulder.disable();
    }
}
