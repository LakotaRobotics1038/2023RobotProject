package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.PIDSubsystem;
import frc.robot.constants.CubeShooterConstants;

public class CubeShooter extends PIDSubsystem {
    private CANSparkMax feederMotor = new CANSparkMax(CubeShooterConstants.kFeederMotorPort, MotorType.kBrushless);
    private CANSparkMax leftShooterMotor = new CANSparkMax(CubeShooterConstants.kLeftMotorPort, MotorType.kBrushless);
    private CANSparkMax rightShooterMotor = new CANSparkMax(CubeShooterConstants.kRightMotorPort, MotorType.kBrushless);
    private RelativeEncoder leftShooterEncoder = leftShooterMotor.getEncoder();

    private DigitalInput cubeLimitSwitch = new DigitalInput(CubeShooterConstants.kCubeLimitSwitchPort);

    public enum CubeShooterSetpoints {
        low(0),
        medium(0),
        high(0);

        public final int value;

        CubeShooterSetpoints(int value) {
            this.value = value;
        }
    }

    // Singleton Setup
    private static CubeShooter instance;

    public static CubeShooter getInstance() {
        if (instance == null) {
            instance = new CubeShooter();
        }
        return instance;
    }

    private CubeShooter() {
        super(new PIDController(
                CubeShooterConstants.kShooterP,
                CubeShooterConstants.kShooterI,
                CubeShooterConstants.kShooterD));
        getController().setTolerance(CubeShooterConstants.kShooterTolerance);
        getController().disableContinuousInput();

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

    @Override
    protected void useOutput(double output, double setpoint) {
        double power = MathUtil.clamp(output, -1, 1);
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

    public void setSetpoint(CubeShooterSetpoints setpoint) {
        super.setSetpoint(setpoint.value);
    }

    @Override
    protected double getMeasurement() {
        return leftShooterEncoder.getVelocity();
    }

    public boolean onTarget() {
        return getController().atSetpoint();
    }
}