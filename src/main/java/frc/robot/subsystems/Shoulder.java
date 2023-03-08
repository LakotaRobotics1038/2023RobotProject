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
        getController().disableContinuousInput();
        shoulderMotor.burnFlash();
    }

    @Override
    protected void useOutput(double output, double setpoint) {
        double power = MathUtil.clamp(output, -0.5, 0.5);
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
}
