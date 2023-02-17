import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.CubeShooter;

public class ShootCubeCommand extends CommandBase {

    public ShootCubeCommand() {
        this.addRequirements(CubeShooter);
        this.addRequirements();

    }
}
