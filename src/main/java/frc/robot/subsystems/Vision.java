package frc.robot.subsystems;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import edu.wpi.first.networktables.BooleanPublisher;
import edu.wpi.first.networktables.BooleanTopic;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.StringSubscriber;
import edu.wpi.first.networktables.StringTopic;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.VisionConstants;

public class Vision extends SubsystemBase {
    // Enum for different things vision can find
    public enum VisionTarget {
        CUBE(0),
        CONE(1),
        APT1(2),
        APT2(3),
        APT3(4),
        APT4(5),
        APT5(6),
        APT6(7),
        APT7(8),
        APT8(9);

        public final int value;

        VisionTarget(int value) {
            this.value = value;
        }
    }

    // Instance Values
    private JSONParser jsonParser = new JSONParser();
    private boolean enabled0 = false;
    private boolean enabled1 = false;
    private JSONArray visionData;

    // Network Tables Setup
    NetworkTableInstance inst = NetworkTableInstance.getDefault();
    NetworkTable table = inst.getTable(VisionConstants.kTableName);
    BooleanTopic enabled0Topic = table.getBooleanTopic(VisionConstants.kEnabled0Topic);
    BooleanPublisher enabled0Publisher = enabled0Topic.publish();
    BooleanTopic enabled1Topic = table.getBooleanTopic(VisionConstants.kEnabled1Topic);
    BooleanPublisher enabled1Publisher = enabled1Topic.publish();
    StringTopic valuesTopic = table.getStringTopic(VisionConstants.kValuesTopic);
    StringSubscriber valuesSubscriber = valuesTopic.subscribe("[]");

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
        enabled0Publisher.set(enabled0);
        enabled1Publisher.set(enabled1);
        String value = valuesSubscriber.get();
        try {
            visionData = (JSONArray) jsonParser.parse(value);
        } catch (ParseException ex) {
            System.out.println("Failed to parse vision data");
        }
    }

    public void enable0() {
        enabled0 = true;
    }

    public void disable0() {
        enabled0 = false;
    }

    public boolean isEnabled0() {
        return enabled0;
    }

    public void enable1() {
        enabled1 = true;
    }

    public void disable1() {
        enabled1 = false;
    }

    public boolean isEnabled1() {
        return enabled1;
    }
}
