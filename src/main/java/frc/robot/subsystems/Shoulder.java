package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.SparkMaxAbsoluteEncoder.Type;
import com.revrobotics.AbsoluteEncoder;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDSubsystem;
import edu.wpi.first.math.MathUtil;

public class Shoulder extends PIDSubsystem {

    private final int shoulderMotorPort = 0;

    public final CANSparkMax shoulderMotor = new CANSparkMax(shoulderMotorPort, MotorType.kBrushless);

    private AbsoluteEncoder shoulderEncoder = shoulderMotor.getAbsoluteEncoder(Type.kDutyCycle);

    private final double MAX_DISTANCE = 0.00;
    private final double TOLERANCE = 0.00;
    private final static double P = 0.00;
    private final static double I = 0.00;
    private final static double D = 0.00;

    // singleton setup

    private static Shoulder instance;

    public static Shoulder getInstance() {
        if (instance == null) {
            instance = new Shoulder();
        }

        return instance;
    }

    private Shoulder() {
        super(new PIDController(P, I, D));
        getController().setTolerance(TOLERANCE);
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

    @Override
    protected double getMeasurement() {
        return getShoulderEncoder();
    }

    public void setSetpoint(double setpoint) {
        setpoint = MathUtil.clamp(setpoint, 0, MAX_DISTANCE);
        super.setSetpoint(setpoint);
    }

    /*
     * public void shoulderPeriodic() {
     * if ( check if the PID controller is enabled ) {
     *
     * shoulderMotor.set(pid.calculate(shoulderEncoder.getDistance(), setpoint));
     *
     * } else {
     * break;
     * }
     */
}
