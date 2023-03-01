package frc.robot.autons;

import com.pathplanner.lib.PathPlannerTrajectory;

import edu.wpi.first.wpilibj2.command.RunCommand;

public class MountChargeStationPath extends Auton {
    public MountChargeStationPath() {
        PathPlannerTrajectory trajectory = Trajectories.MountChargeStationPath();
        super.addCommands(
                this.driveTrain.getTrajectoryCommand(trajectory),
                new RunCommand(() -> driveTrain.setX(), driveTrain));
        this.setInitialPose(trajectory);
    }
}
