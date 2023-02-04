package frc.robot.autons;

import com.pathplanner.lib.PathPlannerTrajectory;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.DriveTrain;

public abstract class Auton extends SequentialCommandGroup {
    private Pose2d initialPose;
    protected DriveTrain driveTrain = DriveTrain.getInstance();

    public Auton() {
    }

    protected void setInitialPose(PathPlannerTrajectory initialTrajectory) {
        this.initialPose = initialTrajectory.getInitialHolonomicPose();
    }

    public Pose2d getInitialPose() {
        return initialPose;
    }
}