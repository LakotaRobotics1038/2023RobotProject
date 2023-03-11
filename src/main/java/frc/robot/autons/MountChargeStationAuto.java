package frc.robot.autons;

import com.pathplanner.lib.PathPlannerTrajectory;

import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.robot.commands.BalanceRobotCommand;

public class MountChargeStationAuto extends Auton {
    public MountChargeStationAuto(Alliance alliance) {
        super(alliance);
        PathPlannerTrajectory trajectory = alliance == Alliance.Blue ? Trajectories.MountChargeStationPathBlue()
                : Trajectories.MountChargeStationPathRed();
        super.addCommands(
                this.driveTrain.getTrajectoryCommand(trajectory),
                new BalanceRobotCommand(),
                new RunCommand(() -> driveTrain.setX(), driveTrain));
        this.setInitialPose(trajectory);
    }
}
