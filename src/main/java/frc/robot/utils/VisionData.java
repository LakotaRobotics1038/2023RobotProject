package frc.robot.utils;

import frc.robot.subsystems.Vision.VisionTarget;

public class VisionData {
    private double x;
    private double y;
    private double area;
    private double confidence;
    private VisionTarget target;

    public VisionData(String x, String y, String area, String confidence, String target) {
        this.x = Double.parseDouble(x);
        this.y = Double.parseDouble(y);
        this.area = Double.parseDouble(area);
        this.confidence = Double.parseDouble(confidence);
        this.target = VisionTarget.values()[Integer.parseInt(target)];
    }

    @Override
    public String toString() {
        return "x: " + this.x + " y: " + this.y + " area: " + this.area + " conf: " + this.confidence + " tgt: "
                + this.target;
    }
}
