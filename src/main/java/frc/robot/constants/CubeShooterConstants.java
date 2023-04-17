package frc.robot.constants;

public final class CubeShooterConstants {
    public static final int kRightMotorPort = 16;
    public static final int kLeftMotorPort = 15;

    public static final int kCubeLimitSwitchPort = 9;
    public static final int kFeederMotorPort = 14;

    public static final double kFeederMotorSpeed = 0.7;
    public static final double kCubeLoadSpeed = -0.3;
    public static final double kDefaultShooterSpeed = 0.5;
    public static final double kShooterSpeedIncrement = 0.05;

    public static final double kShooterVelocityConversionFactor = 1.0 / 5.0;
    public static final double kP = 0.0005;
    public static final double kI = 0.000002;
    public static final double kD = 0.000;
    public static final double kFF = 1 / NeoMotorConstants.kFreeSpeedRpm;
    public static final double kShooterTolerance = 5;

    public static final int kMaxShooterSetpoint = 2200;

    public static final int kMidShooterSetpoint = 1000;
    public static final int kHighShooterSetpoint = 1380;
    public static final int kMidShooterFatCubeSetpoint = 965;
    public static final int kHighShooterFatCubeSetpoint = 1330;
}
