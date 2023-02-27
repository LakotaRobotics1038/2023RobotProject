package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
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

    private double shooterSpeed = CubeShooterConstants.kDefaultShooterSpeed;

    public enum CubeShooterSetpoints {
        low(CubeShooterConstants.kLowShooterSetpoint),
        mid(CubeShooterConstants.kMidShooterSetpoint),
        high(CubeShooterConstants.kHighShooterSetpoint);

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
        leftShooterMotor.setIdleMode(IdleMode.kCoast);
        rightShooterMotor.setIdleMode(IdleMode.kCoast);
        leftShooterMotor.setIdleMode(IdleMode.kCoast);
        leftShooterMotor.setInverted(true);
        rightShooterMotor.follow(leftShooterMotor, true);

        leftShooterEncoder.setVelocityConversionFactor(CubeShooterConstants.kShooterVelocityConversionFactor);
    }

    public void loadCube() {
        leftShooterMotor.set(CubeShooterConstants.kCubeLoadSpeed);
    }

    @Override
    protected void useOutput(double output, double setpoint) {
        double power = MathUtil.clamp(output, 0, 1);
        leftShooterMotor.set(power);
    }

    public void run() {
        leftShooterMotor.set(shooterSpeed);
    }

    public void setShooterSpeed(double p) {
        MathUtil.clamp(p, 0.01, 1);
        shooterSpeed = p;
    }

    public void feedIn() {
        if (this.getLimit()) {
            feederMotor.stopMotor();
        } else {
            feederMotor.set(CubeShooterConstants.kFeederMotorSpeed);
        }
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