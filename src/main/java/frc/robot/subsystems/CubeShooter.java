package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.CubeShooterConstants;

public class CubeShooter extends SubsystemBase {
    private CANSparkMax feederMotor = new CANSparkMax(CubeShooterConstants.kFeederMotorPort, MotorType.kBrushless);
    private CANSparkMax leftShooterMotor = new CANSparkMax(CubeShooterConstants.kLeftMotorPort, MotorType.kBrushless);
    private CANSparkMax rightShooterMotor = new CANSparkMax(CubeShooterConstants.kRightMotorPort, MotorType.kBrushless);
    private DigitalInput cubeLimitSwitch = new DigitalInput(CubeShooterConstants.kCubeLimitSwitchPort);

    private RelativeEncoder leftShooterEncoder = leftShooterMotor.getEncoder();
    private final SparkMaxPIDController leftShooterPidController = leftShooterMotor.getPIDController();

    private double shooterSpeed = CubeShooterConstants.kDefaultShooterSpeed;
    private CubeShooterSetpoints setpoint;

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
        leftShooterMotor.restoreFactoryDefaults();
        rightShooterMotor.restoreFactoryDefaults();
        feederMotor.restoreFactoryDefaults();

        leftShooterPidController.setFeedbackDevice(leftShooterEncoder);
        leftShooterEncoder.setVelocityConversionFactor(CubeShooterConstants.kShooterVelocityConversionFactor);

        leftShooterPidController.setP(CubeShooterConstants.kP);
        leftShooterPidController.setI(CubeShooterConstants.kI);
        leftShooterPidController.setD(CubeShooterConstants.kD);
        leftShooterPidController.setFF(CubeShooterConstants.kFF);
        leftShooterPidController.setOutputRange(0.01, 1);

        feederMotor.setInverted(true);
        leftShooterMotor.setIdleMode(IdleMode.kCoast);
        rightShooterMotor.setIdleMode(IdleMode.kCoast);
        leftShooterMotor.setInverted(true);

        leftShooterMotor.burnFlash();
        rightShooterMotor.burnFlash();
    }

    public void loadCube() {
        rightShooterMotor.follow(leftShooterMotor, false);
        leftShooterMotor.set(CubeShooterConstants.kCubeLoadSpeed);
    }

    public void unloadCube() {
        rightShooterMotor.follow(leftShooterMotor, true);
        leftShooterMotor.set(-CubeShooterConstants.kCubeLoadSpeed);
    }

    public void runShooter() {
        rightShooterMotor.follow(leftShooterMotor, true);
        leftShooterMotor.set(shooterSpeed);
    }

    public void setShooterSpeed(double p) {
        MathUtil.clamp(p, 0.01, 1);
        shooterSpeed = p;
    }

    public double getShooterSpeed() {
        return this.shooterSpeed;
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

    public void stopShooter() {
        leftShooterMotor.stopMotor();
    }

    public void disable() {
        leftShooterMotor.disable();
    }

    public boolean getLimit() {
        return !cubeLimitSwitch.get();
    }

    public void enable(CubeShooterSetpoints setpoint) {
        this.setpoint = setpoint;
        leftShooterPidController.setReference(setpoint.value, ControlType.kVelocity);
    }

    protected double getVelocity() {
        return leftShooterEncoder.getVelocity();
    }

    public boolean onTarget() {
        double velocity = this.getVelocity();
        return velocity <= this.setpoint.value + CubeShooterConstants.kShooterTolerance &&
                velocity >= this.setpoint.value - CubeShooterConstants.kShooterTolerance;
    }

    public void setP(double p) {
        this.leftShooterPidController.setP(p);
    }

    public void setI(double i) {
        this.leftShooterPidController.setI(i);
    }

    public void setD(double d) {
        this.leftShooterPidController.setD(d);
    }

    public void setFF(double ff) {
        this.leftShooterPidController.setFF(ff);
    }
}