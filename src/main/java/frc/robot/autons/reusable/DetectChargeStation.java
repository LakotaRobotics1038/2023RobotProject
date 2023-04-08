package frc.robot.autons.reusable;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;

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

    public DetectChargeStation(DoubleSupplier roll, DetectionDirections direction) {
        addCommands(
                new WaitUntilCommand(() -> (int) Math.abs(roll.getAsDouble()) >= direction.firstAngle),
                new WaitCommand(1.0),
                new WaitUntilCommand(() -> (int) Math.abs(roll.getAsDouble()) <= direction.secondAngle));
    }
}
