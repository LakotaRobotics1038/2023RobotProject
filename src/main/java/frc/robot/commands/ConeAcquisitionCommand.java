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

public class ConeAcquisitionCommand extends CommandBase {
    private Wrist wrist = Wrist.getInstance();
    private Shoulder shoulder = Shoulder.getInstance();
    private Arm arm = Arm.getInstance();

    private Timer delayTimer = new Timer();
    private WristSetpoints wristSetpoint;
    private ShoulderSetpoints shoulderSetpoint;
    private boolean noFinish = false;
    private boolean extendArm = false;

    public ConeAcquisitionCommand(WristSetpoints wristSetpoint, ShoulderSetpoints shoulderSetpoint, boolean extendArm) {
        this.addRequirements(wrist, shoulder, arm);
        this.wristSetpoint = wristSetpoint;
        this.shoulderSetpoint = shoulderSetpoint;
        this.extendArm = extendArm;
    }

    public ConeAcquisitionCommand(WristSetpoints wristSetpoint, ShoulderSetpoints shoulderSetpoint, boolean extendArm,
            boolean noFinish) {
        this.addRequirements(wrist, shoulder, arm);
        this.wristSetpoint = wristSetpoint;
        this.shoulderSetpoint = shoulderSetpoint;
        this.extendArm = extendArm;
        this.noFinish = noFinish;
    }

    @Override
    public void initialize() {
        // TODO: What happens when this command is cancelled and the default takes over?
        if (arm.getPosition() == ArmExtensionStates.Out && !this.extendArm) {
            arm.setPosition(ArmExtensionStates.In);
            delayTimer.start();
        } else {
            shoulder.enable();
        }
        wrist.enable();
        wrist.setSetpoint(wristSetpoint);
        shoulder.setSetpoint(shoulderSetpoint);
    }

    @Override
    public void execute() {
        if (!shoulder.isEnabled() && delayTimer.get() > 1.0) {
            shoulder.enable();
            delayTimer.stop();
        }

        if (shoulder.getPosition() >= ShoulderConstants.kMinExtensionPosition && this.extendArm) {
            arm.setPosition(ArmExtensionStates.Out);
        } else {
            arm.setPosition(ArmExtensionStates.In);
        }
    }

    @Override
    public boolean isFinished() {
        return !noFinish && wrist.onTarget() && shoulder.onTarget();
    }

    @Override
    public void end(boolean interrupted) {
        wrist.disable();
        shoulder.disable();
    }
}
