package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
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
        getController().setTolerance(ShoulderConstants.kTolerance);
        getController().disableContinuousInput();
    }

    @Override
    protected void useOutput(double output, double setpoint) {
        double power = MathUtil.clamp(output, -1, 1);
        shoulderMotor.set(power);
    }

    public double getShoulderEncoder() {
        return shoulderEncoder.getPosition();
    }

    public boolean onTarget(double setPoint) {

        if (super.getSetpoint() == setPoint) {
            return true;
        } else {
            return false;
        }

    }

    @Override
    protected double getMeasurement() {
        return getShoulderEncoder();
    }

    public void setSetpoint(double setpoint) {
        setpoint = MathUtil.clamp(setpoint, 0, ShoulderConstants.kMaxDistance);
        super.setSetpoint(setpoint);
    }
}
