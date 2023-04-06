package frc.robot.autons;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.subsystems.DriveTrain;

public class DetectChargeStation extends SequentialCommandGroup {
    public enum DetectionDirections {
        On(15, 14),
        Off(15, 2);

        public final int firstAngle;
        public final int secondAngle;

        private DetectionDirections(int firstAngle, int secondAngle) {
            this.firstAngle = firstAngle;
            this.secondAngle = secondAngle;
        }
    }

    DetectChargeStation(DriveTrain driveTrain, DetectionDirections direction) {
        addCommands(
                new WaitUntilCommand(() -> (int) Math.abs(driveTrain.getRoll()) >= direction.firstAngle),
                new WaitCommand(1.0),
                new WaitUntilCommand(() -> (int) Math.abs(driveTrain.getRoll()) <= direction.secondAngle));
    }
}
