package frc.robot.autons;

import com.pathplanner.lib.PathPlannerTrajectory;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.constants.FieldConstants;
import frc.robot.subsystems.DriveTrain;

public abstract class Auton extends SequentialCommandGroup {
    private Pose2d initialPose;
    protected DriveTrain driveTrain = DriveTrain.getInstance();
    protected Alliance alliance;

    public Auton(Alliance alliance) {
        this.alliance = alliance;
    }

    protected void setInitialPose(PathPlannerTrajectory initialTrajectory) {
        this.initialPose = initialTrajectory.getInitialHolonomicPose();

        // We need to invert the starting pose for the red alliance.
        if (this.alliance == Alliance.Red) {
            Translation2d transformedTranslation = new Translation2d(this.initialPose.getX(),
                    FieldConstants.kFieldWidth - this.initialPose.getY());
            Rotation2d transformedHeading = this.initialPose.getRotation().times(-1);

            this.initialPose = new Pose2d(transformedTranslation, transformedHeading);
        }
    }

    public Pose2d getInitialPose() {
        return initialPose;
    }
}