package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.constants.ShoulderConstants;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Shoulder;
import frc.robot.subsystems.Wrist;
import frc.robot.subsystems.Arm.ArmExtensionStates;
import frc.robot.subsystems.Shoulder.ShoulderSetpoints;
import frc.robot.subsystems.Wrist.WristSetpoints;

public class ConeAcquisitionPositionCommand extends CommandBase {
    private Wrist wrist = Wrist.getInstance();
    private Shoulder shoulder = Shoulder.getInstance();
    private Arm arm = Arm.getInstance();

    public enum FinishActions {
        NoFinish,
        NoDisable,
        Default
    }

    private Timer delayTimer = new Timer();
    private WristSetpoints wristSetpoint;
    private ShoulderSetpoints shoulderSetpoint;
    private FinishActions finishAction = FinishActions.Default;
    private boolean extendArm = false;
    private double delayTime = 0;

    public ConeAcquisitionPositionCommand(WristSetpoints wristSetpoint, ShoulderSetpoints shoulderSetpoint,
            boolean extendArm) {
        this.addRequirements(wrist, shoulder, arm);
        this.wristSetpoint = wristSetpoint;
        this.shoulderSetpoint = shoulderSetpoint;
        this.extendArm = extendArm;
    }

    public ConeAcquisitionPositionCommand(WristSetpoints wristSetpoint, ShoulderSetpoints shoulderSetpoint,
            boolean extendArm,
            FinishActions finishAction) {
        this.addRequirements(wrist, shoulder, arm);
        this.wristSetpoint = wristSetpoint;
        this.shoulderSetpoint = shoulderSetpoint;
        this.extendArm = extendArm;
        this.finishAction = finishAction;
    }

    @Override
    public void initialize() {
        if (arm.getPosition() == ArmExtensionStates.Out && !this.extendArm) {
            arm.setPosition(ArmExtensionStates.In);

            double currentSetpoint = shoulder.getSetpoint();
            if (currentSetpoint == ShoulderSetpoints.high.value) {
                this.delayTime = ShoulderSetpoints.high.armDelay;
            } else if (currentSetpoint == ShoulderSetpoints.acquire.value) {
                this.delayTime = ShoulderSetpoints.acquire.armDelay;
            }

            delayTimer.restart();
        } else {
            shoulder.enable();
            shoulder.setSetpoint(shoulderSetpoint);
        }
        wrist.enable();
        wrist.setSetpoint(wristSetpoint);
    }

    @Override
    public void execute() {
        if (delayTimer.get() > delayTime) {
            if (!shoulder.isEnabled()) {
                shoulder.enable();
            }
            shoulder.setSetpoint(shoulderSetpoint);
            delayTimer.stop();
        }

        if (shoulder.getPosition() >= ShoulderConstants.kMinExtensionPosition && this.extendArm) {
            if (shoulder.onTarget()) {
                arm.setPosition(ArmExtensionStates.Out);
            }
        } else {
            arm.setPosition(ArmExtensionStates.In);
        }
    }

    @Override
    public boolean isFinished() {
        return finishAction != FinishActions.NoFinish &&
                wrist.onTarget() &&
                shoulder.onTarget();
    }

    @Override
    public void end(boolean interrupted) {
        if (finishAction != FinishActions.NoDisable) {
            wrist.disable();
            shoulder.disable();
        }
    }
}
