package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.CubeShooterConstants;

public class CubeShooter extends SubsystemBase {
    private CANSparkMax feederMotor = new CANSparkMax(CubeShooterConstants.kFeederMotorPort, MotorType.kBrushless);
    private CANSparkMax leftShooterMotor = new CANSparkMax(CubeShooterConstants.kLeftMotorPort, MotorType.kBrushless);
    private CANSparkMax rightShooterMotor = new CANSparkMax(CubeShooterConstants.kRightMotorPort, MotorType.kBrushless);
    private RelativeEncoder leftShooterEncoder = leftShooterMotor.getEncoder();

    private DigitalInput cubeLimitSwitch = new DigitalInput(CubeShooterConstants.kCubeLimitSwitchPort);

    // Singleton Setup
    private static CubeShooter instance;

    public static CubeShooter getInstance() {
        if (instance == null) {
            instance = new CubeShooter();
        }
        return instance;
    }

    private CubeShooter() {
        leftShooterMotor.restoreFactoryDefaults();
        rightShooterMotor.restoreFactoryDefaults();
        feederMotor.restoreFactoryDefaults();

        feederMotor.setInverted(true);
        leftShooterMotor.setInverted(true);
        rightShooterMotor.follow(leftShooterMotor, true);

        leftShooterEncoder.setVelocityConversionFactor(CubeShooterConstants.kShooterVelocityConversionFactor);
    }

    public void loadCube() {
        if (this.getLimit()) {
            leftShooterMotor.stopMotor();
        } else {
            leftShooterMotor.set(CubeShooterConstants.kCubeLoadSpeed);
        }
    }

    public void setShooterSpeed(double power) {
        power = MathUtil.clamp(power, -1, 1);
        leftShooterMotor.set(power);
    }

    public void feedIn() {
        feederMotor.set(CubeShooterConstants.kFeederMotorSpeed);
    }

    public void feedOut() {
        feederMotor.set(-CubeShooterConstants.kFeederMotorSpeed);
    }

    public void stopFeeder() {
        feederMotor.stopMotor();
    }

    public void stopMotor() {
        leftShooterMotor.stopMotor();
    }

    public boolean getLimit() {
        return !cubeLimitSwitch.get();
    }

    public double getShooterVelocity() {
        return leftShooterEncoder.getVelocity();
    }
}