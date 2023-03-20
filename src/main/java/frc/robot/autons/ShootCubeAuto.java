package frc.robot.autons;

import edu.wpi.first.wpilibj.DriverStation.Alliance;
import frc.robot.commands.CubeAcquisitionPositionCommand;
import frc.robot.commands.ShootCubeCommand;
import frc.robot.subsystems.CubeAcquisition.AcquisitionStates;
import frc.robot.subsystems.CubeShooter.CubeShooterSetpoints;

public class ShootCubeAuto extends Auton {
    public ShootCubeAuto(Alliance alliance) {
        super(alliance);

        super.addCommands(
                new ShootCubeCommand(CubeShooterSetpoints.high, 1.0),
                new CubeAcquisitionPositionCommand(AcquisitionStates.Up));
    }
}
