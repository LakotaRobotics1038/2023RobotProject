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
    // makes variables for the auton like allience = red or blue allience
    // drivetrain is just the drivetain
    // initial pose is your position at the start of the match
    private Pose2d initialPose;
    protected DriveTrain driveTrain = DriveTrain.getInstance();
    protected Alliance alliance;

    public Auton(Alliance alliance) {
        // instantiates the allience
        this.alliance = alliance;
    }

    protected void setInitialPose(PathPlannerTrajectory initialTrajectory) {
        // instantiates the position at the start of the match
        this.initialPose = initialTrajectory.getInitialHolonomicPose();

        // We need to invert the starting pose for the red alliance.
        // If the allience is Red then it will mirror the path planned
        if (this.alliance == Alliance.Red) {
            // mirrors the robot position in path planner
            Translation2d transformedTranslation = new Translation2d(this.initialPose.getX(),
                    FieldConstants.kFieldWidth - this.initialPose.getY());
            // Rotates the path
            Rotation2d transformedHeading = this.initialPose.getRotation().times(-1);
            // changes the inital pos to the new one
            this.initialPose = new Pose2d(transformedTranslation, transformedHeading);
        }
    }

    public Pose2d getInitialPose() {
        // returns the inital position
        return initialPose;
    }
}