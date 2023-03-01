package frc.robot.autons;

import com.pathplanner.lib.PathPlannerTrajectory;

public class MountChargeStationPath extends Auton {
    public MountChargeStationPath() {
        PathPlannerTrajectory trajectory = Trajectories.MountChargeStationPath();
        super.addCommands(
                this.driveTrain.getTrajectoryCommand(trajectory));
        this.setInitialPose(trajectory);
    }
}
