package frc.robot.subsystems;

import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.SparkMaxAbsoluteEncoder.Type;

import frc.robot.constants.WristConstants;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDSubsystem;

import edu.wpi.first.math.MathUtil;

public class Wrist extends PIDSubsystem {
    private static Wrist wristInstance;

    private CANSparkMax wristMotor = new CANSparkMax(WristConstants.kWristMotorPort, MotorType.kBrushless);
    private AbsoluteEncoder wristEncoder = wristMotor.getAbsoluteEncoder(Type.kDutyCycle);

    public enum WristSetpoints {
        storage(WristConstants.kStorageSetpoint),
        acquire(WristConstants.kAcquireSetpoint),
        mid(WristConstants.kMidSetpoint),
        high(WristConstants.kHighSetpoint);

        public final int value;

        WristSetpoints(int value) {
            this.value = value;
        }
    }

    public static Wrist getInstance() {
        if (wristInstance == null) {
            wristInstance = new Wrist();
        }
        return wristInstance;
    }

    private Wrist() {
        super(new PIDController(WristConstants.kP, WristConstants.kI,
                WristConstants.kD));
        wristMotor.restoreFactoryDefaults();
        wristMotor.setIdleMode(IdleMode.kBrake);
        wristMotor.setInverted(true);
        wristEncoder.setPositionConversionFactor(WristConstants.kEncoderConversion);
        wristEncoder.setInverted(true);
        getController().enableContinuousInput(0, WristConstants.kEncoderConversion);
        getController().setTolerance(WristConstants.kTolerance);
        wristMotor.burnFlash();
    }

    @Override
    public void useOutput(double output, double setpoint) {
        double power = MathUtil.clamp(output, -WristConstants.kMaxPower, WristConstants.kMaxPower);
        wristMotor.set(power);
    }

    @Override
    public double getMeasurement() {
        return getPosition();
    }

    public double getPosition() {
        return wristEncoder.getPosition();
    }

    public boolean onTarget() {
        return getController().atSetpoint();
    }

    @Override
    public void setSetpoint(double setpoint) {
        setpoint = MathUtil.clamp(setpoint, 0, WristConstants.kMaxDistance);
        super.setSetpoint(setpoint);
    }

    public void setSetpoint(WristSetpoints setpoint) {
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
