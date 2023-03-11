package frc.robot.libraries;

import com.ctre.phoenix.sensors.Pigeon2;

import edu.wpi.first.wpilibj.interfaces.Gyro;

public class Pigeon1038 extends Pigeon2 implements Gyro {

    public Pigeon1038(int deviceNumber) {
        super(deviceNumber);
    }

    @Override
    public void close() throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Not Implemented");
    }

    @Override
    public void calibrate() {
        super.zeroGyroBiasNow();
    }

    @Override
    public void reset() {
        super.setYaw(0.0);
    }

    @Override
    public double getAngle() {
        return super.getYaw();
    }

    @Override
    public double getRoll() {
        return -super.getRoll();
    }

    @Override
    public double getRate() {
        double[] rate_xyz = {};
        super.getRawGyro(rate_xyz);
        return rate_xyz[2];
    }
}
