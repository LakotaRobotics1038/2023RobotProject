package frc.robot.subsystems;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

import edu.wpi.first.networktables.BooleanPublisher;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.StringSubscriber;
import edu.wpi.first.networktables.StringTopic;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.VisionConstants;

public class Vision extends SubsystemBase {
    // Network Tables Setup
    NetworkTableInstance inst = NetworkTableInstance.getDefault();
    NetworkTable table = inst.getTable(VisionConstants.kTableName);
    BooleanPublisher enableProcessing;
    StringTopic valuesTopic = table.getStringTopic(VisionConstants.kValuesTopic);
    StringSubscriber valuesSubscriber = valuesTopic.subscribe("[]");
    JSONParser jsonParser = new JSONParser();

    // Instance Values
    private boolean enabled = false;

    // Singleton Setup
    private static Vision instance;

    public static Vision getInstance() {
        if (instance == null) {
            instance = new Vision();
        }
        return instance;
    }

    private Vision() {
    }

    @Override
    public void periodic() {
        // values = new JSONArray(valuesSubscriber.get());
    }
}
