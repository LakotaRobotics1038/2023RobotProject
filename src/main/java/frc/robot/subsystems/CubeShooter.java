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
import frc.robot.constants.NeoMotorConstants;

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
        rightShooterMotor.follow(leftShooterMotor, true);

        feederMotor.setSmartCurrentLimit(NeoMotorConstants.kMaxNeo550Current);
        leftShooterMotor.setSmartCurrentLimit(NeoMotorConstants.kMaxNeo550Current);
        rightShooterMotor.setSmartCurrentLimit(NeoMotorConstants.kMaxNeo550Current);

        leftShooterMotor.burnFlash();
        rightShooterMotor.burnFlash();
        feederMotor.burnFlash();
    }

    public void loadCube() {
        leftShooterMotor.set(CubeShooterConstants.kCubeLoadSpeed);
    }

    public void unloadCube() {
        leftShooterMotor.set(-CubeShooterConstants.kCubeLoadSpeed);
    }

    public void runShooter() {
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
        return this.setpoint != null ? velocity <= this.setpoint.value + CubeShooterConstants.kShooterTolerance &&
                velocity >= this.setpoint.value - CubeShooterConstants.kShooterTolerance : false;
    }
}