package frc.robot.autons;

import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;

import frc.robot.constants.AutoConstants;

public class Trajectories {
    public static PathPlannerTrajectory LeaveCommunityPath() {
        return PathPlanner.loadPath("Leave Community Path",
                AutoConstants.kMaxSpeedMetersPerSecond,
                AutoConstants.kMaxAccelerationMetersPerSecondSquared);
    }

    public static PathPlannerTrajectory MountChargeStationPath() {
        return PathPlanner.loadPath("Mount Charge Station Path",
                AutoConstants.kMaxSpeedMetersPerSecond,
                AutoConstants.kMaxAccelerationMetersPerSecondSquared);
    }

    public static PathPlannerTrajectory TwoMeterPath() {
        return PathPlanner.loadPath("Two Meter Path",
                AutoConstants.kMaxSpeedMetersPerSecond,
                AutoConstants.kMaxAccelerationMetersPerSecondSquared);
    }
}
