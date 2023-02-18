package frc.robot.subsystems;

import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.CANSparkMax;
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
    private PIDController wristPIDController;
    private boolean isPIDEnabled;

    public static Wrist getInstance() {
        if (wristInstance == null) {
            wristInstance = new Wrist();
        }
        return wristInstance;
    }

    private Wrist() {
        super(new PIDController(WristConstants.kWristP, WristConstants.kWristI,
                WristConstants.kWristD));
        getController().disableContinuousInput();
    }

    public void setPIDTolerance() {
        getController().setTolerance(WristConstants.kWristPIDTolerance);
    }

    public void setPIDcontroller() {
        wristPIDController.setPID(WristConstants.kWristP, WristConstants.kWristI,
                WristConstants.kWristD);
    }

    public void enable() {
        wristPIDController.enableContinuousInput(WristConstants.kWristPIDMinimum,
                WristConstants.kWristPIDMinimum);
    }

    public void periodic() {
        if (isPIDEnabled) {
            wristMotor.set(wristPIDController.calculate(WristConstants.kWristPIDSpeed));
        }
    }

    public void disable() {
    }

    @Override
    public void useOutput(double output, double setpoint) {
        double power = MathUtil.clamp(output, -1, 1);
        wristMotor.set(power);
    }

    @Override
    public double getMeasurement() {
        return getWristEncoder();
    }

    public double getWristEncoder() {
        return wristEncoder.getPosition();
    }
}
