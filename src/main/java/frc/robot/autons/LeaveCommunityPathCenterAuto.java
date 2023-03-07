package frc.robot.autons;

import com.pathplanner.lib.PathPlannerTrajectory;

import edu.wpi.first.wpilibj.DriverStation.Alliance;

public class LeaveCommunityPathCenterAuto extends Auton {
    public LeaveCommunityPathCenterAuto(Alliance alliance) {
        super(alliance);

        PathPlannerTrajectory trajectory = alliance == Alliance.Blue ? Trajectories.LeaveCommunityPathCenterBlue()
                : Trajectories.LeaveCommunityPathCenterRed();

        super.addCommands(
                this.driveTrain.getTrajectoryCommand(trajectory));

        this.setInitialPose(trajectory);
    }
}