package frc.robot.autons;

import com.pathplanner.lib.PathPlannerTrajectory;

import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.RunCommand;

public class MountChargeStationPath extends Auton {
    public MountChargeStationPath(Alliance alliance) {
        super(alliance);
        PathPlannerTrajectory trajectory = alliance == Alliance.Blue ? Trajectories.MountChargeStationPathBlue()
                : Trajectories.MountChargeStationPathRed();
        super.addCommands(
                this.driveTrain.getTrajectoryCommand(trajectory),
                new RunCommand(() -> driveTrain.setX(), driveTrain));
        this.setInitialPose(trajectory);
    }
}
