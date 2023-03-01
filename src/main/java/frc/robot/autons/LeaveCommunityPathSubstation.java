package frc.robot.autons;

import com.pathplanner.lib.PathPlannerTrajectory;

public class LeaveCommunityPathSubstation extends Auton {
    public LeaveCommunityPathSubstation() {
        PathPlannerTrajectory trajectory = Trajectories.LeaveCommunityPathSubstation();
        super.addCommands(
                this.driveTrain.getTrajectoryCommand(trajectory));
        this.setInitialPose(trajectory);
    }
}
