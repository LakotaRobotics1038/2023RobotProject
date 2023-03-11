package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.SparkMaxAbsoluteEncoder.Type;
import com.revrobotics.AbsoluteEncoder;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDSubsystem;
import edu.wpi.first.math.MathUtil;

import frc.robot.constants.ShoulderConstants;

public class Shoulder extends PIDSubsystem {
    public final CANSparkMax shoulderMotor = new CANSparkMax(ShoulderConstants.kShoulderMotorPort,
            MotorType.kBrushless);

    private AbsoluteEncoder shoulderEncoder = shoulderMotor.getAbsoluteEncoder(Type.kDutyCycle);

    public enum ShoulderSetpoints {
        storage(ShoulderConstants.kStorageSetpoint),
        acquire(ShoulderConstants.kAcquireSetpoint),
        mid(ShoulderConstants.kMidSetpoint),
        high(ShoulderConstants.kHighSetpoint);

        public final int value;

        ShoulderSetpoints(int value) {
            this.value = value;
        }
    }

    // Singleton setup
    private static Shoulder instance;

    public static Shoulder getInstance() {
        if (instance == null) {
            instance = new Shoulder();
        }

        return instance;
    }

    private Shoulder() {
        super(new PIDController(ShoulderConstants.kP, ShoulderConstants.kI, ShoulderConstants.kD));
        shoulderMotor.restoreFactoryDefaults();
        shoulderMotor.setInverted(true);
        shoulderMotor.setIdleMode(IdleMode.kBrake);
        shoulderEncoder.setPositionConversionFactor(ShoulderConstants.kEncoderConversion);
        shoulderEncoder.setInverted(true);
        getController().setTolerance(ShoulderConstants.kTolerance);
        getController().enableContinuousInput(0, ShoulderConstants.kEncoderConversion);
        shoulderMotor.burnFlash();
    }

    @Override
    protected void useOutput(double output, double setpoint) {
        double power = MathUtil.clamp(output, -ShoulderConstants.kMaxPower, ShoulderConstants.kMaxPower);
        shoulderMotor.set(power);
    }

    @Override
    protected double getMeasurement() {
        return getPosition();
    }

    public double getPosition() {
        return shoulderEncoder.getPosition();
    }

    public boolean onTarget() {
        return getController().atSetpoint();
    }

    @Override
    public void setSetpoint(double setpoint) {
        setpoint = MathUtil.clamp(setpoint, 0, ShoulderConstants.kMaxDistance);
        super.setSetpoint(setpoint);
    }

    public void setSetpoint(ShoulderSetpoints setpoint) {
        setSetpoint(setpoint.value);
    }

    public void setP(double p) {
        getController().setP(p);
    }

    public void setI(double i) {
        getController().setI(i);
    }

    public void setD(double d) {
        getController().setD(d);
    }
}
