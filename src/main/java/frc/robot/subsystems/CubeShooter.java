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
    // instantiates all the motors
    private CANSparkMax feederMotor = new CANSparkMax(CubeShooterConstants.kFeederMotorPort, MotorType.kBrushless);
    private CANSparkMax leftShooterMotor = new CANSparkMax(CubeShooterConstants.kLeftMotorPort, MotorType.kBrushless);
    private CANSparkMax rightShooterMotor = new CANSparkMax(CubeShooterConstants.kRightMotorPort, MotorType.kBrushless);
    private DigitalInput cubeLimitSwitch = new DigitalInput(CubeShooterConstants.kCubeLimitSwitchPort);

    // instantiates encoders and PIDController
    private RelativeEncoder leftShooterEncoder = leftShooterMotor.getEncoder();
    private final SparkMaxPIDController leftShooterPidController = leftShooterMotor.getPIDController();

    // creates variables for shooterSpeed and the cubeshooter's setpoint
    private double shooterSpeed = CubeShooterConstants.kDefaultShooterSpeed;
    private CubeShooterSetpoints setpoint;

    // enums for all the different setpoints for the cubeshooter
    public enum CubeShooterSetpoints {
        mid(CubeShooterConstants.kMidShooterSetpoint),
        high(CubeShooterConstants.kHighShooterSetpoint),
        midFat(CubeShooterConstants.kMidShooterFatCubeSetpoint),
        highFat(CubeShooterConstants.kHighShooterFatCubeSetpoint);

        // variable for setpoint value
        public final int value;

        // method that instantiates value
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

        // restores factory defaults to motor controllers
        leftShooterMotor.restoreFactoryDefaults();
        rightShooterMotor.restoreFactoryDefaults();
        feederMotor.restoreFactoryDefaults();

        // sets the left PIDController's feedback device to the leftshooter encoder
        // sets the velocity conversion factor to the constant we have for that
        // so that the weird units you would get are converted into readable units.
        leftShooterPidController.setFeedbackDevice(leftShooterEncoder);
        leftShooterEncoder.setVelocityConversionFactor(CubeShooterConstants.kShooterVelocityConversionFactor);

        // sets the PID ad FF of the PID controller to our constants of P, I, D, and FF
        // sets the maximum output of the conroller to 1 and minimum of 0.01 so that
        // regardless of what the calculation says it can only send up to those limits
        leftShooterPidController.setP(CubeShooterConstants.kP);
        leftShooterPidController.setI(CubeShooterConstants.kI);
        leftShooterPidController.setD(CubeShooterConstants.kD);
        leftShooterPidController.setFF(CubeShooterConstants.kFF);
        leftShooterPidController.setOutputRange(0.01, 1);

        // inverts the feederMotor
        // sets the left and right shooter motor to coast mode
        // inverts left shooter motor
        // makes it so right shooter motor copys what left shooter motor does, but is
        // inverted(ex: left: 50, right: -50).
        feederMotor.setInverted(true);
        leftShooterMotor.setIdleMode(IdleMode.kCoast);
        rightShooterMotor.setIdleMode(IdleMode.kCoast);
        leftShooterMotor.setInverted(true);
        rightShooterMotor.follow(leftShooterMotor, true);

        // Sets all of the motors to a current limit so they can't go over a certain
        // current
        feederMotor.setSmartCurrentLimit(NeoMotorConstants.kMaxNeo550Current);
        leftShooterMotor.setSmartCurrentLimit(NeoMotorConstants.kMaxNeo550Current);
        rightShooterMotor.setSmartCurrentLimit(NeoMotorConstants.kMaxNeo550Current);

        // saves all the settings to the motor
        leftShooterMotor.burnFlash();
        rightShooterMotor.burnFlash();
        feederMotor.burnFlash();
    }

    // drives the motors inward to pick up a cube
    public void loadCube() {
        leftShooterMotor.set(CubeShooterConstants.kCubeLoadSpeed);
    }

    // moves the motors outwords to shoot out a cube
    public void unloadCube() {
        leftShooterMotor.set(-CubeShooterConstants.kCubeLoadSpeed);
    }

    // runs the motor to the shooter speed
    public void runShooter() {
        leftShooterMotor.set(shooterSpeed);
    }

    // sees if p is within the minimum and maximum and if it isnt brings it to the
    // closest one
    // then sets shooter speed to p
    public void setShooterSpeed(double p) {
        p = MathUtil.clamp(p, NeoMotorConstants.kMinPower, NeoMotorConstants.kMaxPower);
        shooterSpeed = p;
    }

    // returns shooter speed
    public double getShooterSpeed() {
        return this.shooterSpeed;
    }

    // if the cube is on the limit switch it stops the motor if it isnt
    // they keep spinning
    public void feedIn() {
        if (this.getLimit()) {
            feederMotor.stopMotor();
        } else {
            feederMotor.set(CubeShooterConstants.kFeederMotorSpeed);
        }
    }

    // spins the feeder wheels outward
    public void feedOut() {
        feederMotor.set(-CubeShooterConstants.kFeederMotorSpeed);
    }

    // stops feeder wheels
    public void stopFeeder() {
        feederMotor.stopMotor();
    }

    // stops shooter wheels
    public void stopShooter() {
        leftShooterMotor.stopMotor();
    }

    // disables shooter motors
    public void disable() {
        leftShooterMotor.disable();
    }

    // returns whether or not the cube is on the limit switch or not
    public boolean getLimit() {
        return !cubeLimitSwitch.get();
    }

    // sets the pid controller to what it needs to be for the setpoint
    public void enable(CubeShooterSetpoints setpoint) {
        this.setpoint = setpoint;
        leftShooterPidController.setReference(setpoint.value, ControlType.kVelocity);
    }

    // returns velocity of the left shooter encoder
    protected double getVelocity() {
        return leftShooterEncoder.getVelocity();
    }

    // sets the velocity to current velocity and
    // if the velocity is less than or equal to the setpoint plus the shooter
    // tolerance and
    // more than the setpoint minus the shooter tolerance it returns true or else it
    // returns false
    public boolean onTarget() {
        double velocity = this.getVelocity();
        return this.setpoint != null ? velocity <= this.setpoint.value + CubeShooterConstants.kShooterTolerance &&
                velocity >= this.setpoint.value - CubeShooterConstants.kShooterTolerance : false;

        // if (this.setpoint != null) {
        // return velocity <= this.setpoint.value +
        // CubeShooterConstants.kShooterTolerance &&
        // velocity >= this.setpoint.value - CubeShooterConstants.kShooterTolerance;
        // } else {
        // return false;
        // }
    }
}