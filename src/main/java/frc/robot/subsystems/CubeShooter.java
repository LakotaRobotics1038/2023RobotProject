package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.robot.constants.ShooterConstants;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class CubeShooter extends SubsystemBase {

    private CANSparkMax leftShooterMotor = new CANSparkMax(ShooterConstants.kCubeLeftMotorPort, MotorType.kBrushless);
    private CANSparkMax rightShooterMotor = new CANSparkMax(ShooterConstants.kCubeRightMotorPort, MotorType.kBrushless);

    private DigitalInput cubeLimitSwitch = new DigitalInput(ShooterConstants.kCubeLimitSwitchPort);

    public void loadCube() {
        if (!cubeLimitSwitch.get()) {
            leftShooterMotor.stopMotor();
            rightShooterMotor.stopMotor();
        } else {
            leftShooterMotor.set(ShooterConstants.kCubeLoadSpeed);
            rightShooterMotor.set(ShooterConstants.kCubeLoadSpeed);
        }
    }

    public void shootCube() {
        leftShooterMotor.set(ShooterConstants.kCubeShooterSpeed);
        rightShooterMotor.set(ShooterConstants.kCubeShooterSpeed);
    }
}
