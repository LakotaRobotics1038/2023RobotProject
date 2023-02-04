package frc.robot.autons;

import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;

import frc.robot.constants.AutoConstants;

public class Trajectories {
    public static PathPlannerTrajectory TestPath() {
        return PathPlanner.loadPath("Test Path",
                AutoConstants.kMaxSpeedMetersPerSecond,
                AutoConstants.kMaxAccelerationMetersPerSecondSquared);
    }

    public static PathPlannerTrajectory CirclePath() {
        return PathPlanner.loadPath("Circle Path",
                AutoConstants.kMaxSpeedMetersPerSecond,
                AutoConstants.kMaxAccelerationMetersPerSecondSquared);
    }

    public static PathPlannerTrajectory TwoMeterPath() {
        return PathPlanner.loadPath("Two Meter Path",
                AutoConstants.kMaxSpeedMetersPerSecond,
                AutoConstants.kMaxAccelerationMetersPerSecondSquared);
    }
}
