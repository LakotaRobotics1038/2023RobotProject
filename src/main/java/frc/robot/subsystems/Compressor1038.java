package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Compressor;
import frc.robot.constants.PneumaticsConstants;

public class Compressor1038 {
    private static final Compressor compressor = new Compressor(PneumaticsConstants.kPressureSensorPort,
            PneumaticsConstants.kModuleType);

    // Singleton Setup
    private static Compressor1038 instance;

    public static Compressor1038 getInstance() {
        if (null == instance) {
            instance = new Compressor1038();
        }
        return instance;
    }

    private Compressor1038() {

    }

    public void run() {
        compressor.enableAnalog(PneumaticsConstants.kMinPressure, PneumaticsConstants.kMaxPressure);
    }

    public double getPressure() {
        return compressor.getPressure();
    }
}
