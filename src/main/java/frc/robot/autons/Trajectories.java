package frc.robot.autons;

import java.util.List;

import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;

import frc.robot.constants.AutoConstants;

public class Trajectories {
    public static PathPlannerTrajectory LeaveCommunityPathCenter() {
        return PathPlanner.loadPath("Leave Community Path (Center)",
                AutoConstants.kMaxSpeedMetersPerSecond / 2,
                AutoConstants.kMaxAccelerationMetersPerSecondSquared / 2);
    }

    public static PathPlannerTrajectory LeaveCommunityPathScoringTable() {
        return PathPlanner.loadPath("Leave Community Path (Scoring Table)",
                AutoConstants.kMaxSpeedMetersPerSecond / 2,
                AutoConstants.kMaxAccelerationMetersPerSecondSquared / 2);
    }

    public static PathPlannerTrajectory LeaveCommunityPathSubstation() {
        return PathPlanner.loadPath("Leave Community Path (Substation)",
                AutoConstants.kMaxSpeedMetersPerSecond / 2,
                AutoConstants.kMaxAccelerationMetersPerSecondSquared / 2);
    }

    public static PathPlannerTrajectory Test() {
        return PathPlanner.loadPath("Test",
                AutoConstants.kMaxSpeedMetersPerSecond / 2,
                AutoConstants.kMaxAccelerationMetersPerSecondSquared / 2);
    }

    public static PathPlannerTrajectory MountChargeStationPath() {
        return PathPlanner.loadPath("Mount Charge Station Path",
                AutoConstants.kMaxSpeedMetersPerSecond / 2,
                AutoConstants.kMaxAccelerationMetersPerSecondSquared / 2);
    }

    public static List<PathPlannerTrajectory> TwoBallScoringTable() {
        return PathPlanner.loadPathGroup("Two Ball (Scoring Table)",
                AutoConstants.kMaxSpeedMetersPerSecond / (3 / 2),
                AutoConstants.kMaxAccelerationMetersPerSecondSquared / 2);
    }

    public static List<PathPlannerTrajectory> TwoBallSubstation() {
        return PathPlanner.loadPathGroup("Two Ball (Substation)",
                AutoConstants.kMaxSpeedMetersPerSecond / (3 / 2),
                AutoConstants.kMaxAccelerationMetersPerSecondSquared / 2);
    }

    public static List<PathPlannerTrajectory> TwoBallCenter() {
        return PathPlanner.loadPathGroup("Two Ball (Center)",
                AutoConstants.kMaxSpeedMetersPerSecond / (3 / 2),
                AutoConstants.kMaxAccelerationMetersPerSecondSquared / 2);
    }
}
