package frc.robot.autons;

import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;

import frc.robot.constants.AutoConstants;

public class Trajectories {
    public static PathPlannerTrajectory LeaveCommunityPathCenterBlue() {
        return PathPlanner.loadPath("Leave Community Path (Center, Blue)",
                AutoConstants.kMaxSpeedMetersPerSecond / 2,
                AutoConstants.kMaxAccelerationMetersPerSecondSquared / 2);
    }

    public static PathPlannerTrajectory LeaveCommunityPathCenterRed() {
        return PathPlanner.loadPath("Leave Community Path (Center, Red)",
                AutoConstants.kMaxSpeedMetersPerSecond / 2,
                AutoConstants.kMaxAccelerationMetersPerSecondSquared / 2);
    }

    public static PathPlannerTrajectory LeaveCommunityPathScoringTableBlue() {
        return PathPlanner.loadPath("Leave Community Path (Scoring Table, Blue)",
                AutoConstants.kMaxSpeedMetersPerSecond / 2,
                AutoConstants.kMaxAccelerationMetersPerSecondSquared / 2);
    }

    public static PathPlannerTrajectory LeaveCommunityPathScoringTableRed() {
        return PathPlanner.loadPath("Leave Community Path (Scoring Table, Red)",
                AutoConstants.kMaxSpeedMetersPerSecond / 2,
                AutoConstants.kMaxAccelerationMetersPerSecondSquared / 2);
    }

    public static PathPlannerTrajectory LeaveCommunityPathSubstationBlue() {
        return PathPlanner.loadPath("Leave Community Path (Substation, Blue)",
                AutoConstants.kMaxSpeedMetersPerSecond / 2,
                AutoConstants.kMaxAccelerationMetersPerSecondSquared / 2);
    }

    public static PathPlannerTrajectory LeaveCommunityPathSubstationRed() {
        return PathPlanner.loadPath("Leave Community Path (Substation, Red)",
                AutoConstants.kMaxSpeedMetersPerSecond / 2,
                AutoConstants.kMaxAccelerationMetersPerSecondSquared / 2);
    }

    public static PathPlannerTrajectory MountChargeStationPathBlue() {
        return PathPlanner.loadPath("Mount Charge Station Path (Blue)",
                AutoConstants.kMaxSpeedMetersPerSecond / 2,
                AutoConstants.kMaxAccelerationMetersPerSecondSquared / 2);
    }

    public static PathPlannerTrajectory MountChargeStationPathRed() {
        return PathPlanner.loadPath("Mount Charge Station Path (Red)",
                AutoConstants.kMaxSpeedMetersPerSecond / 2,
                AutoConstants.kMaxAccelerationMetersPerSecondSquared / 2);
    }

    public static PathPlannerTrajectory TwoMeterPath() {
        return PathPlanner.loadPath("Two Meter Path",
                AutoConstants.kMaxSpeedMetersPerSecond / 2,
                AutoConstants.kMaxAccelerationMetersPerSecondSquared / 2);
    }
}
