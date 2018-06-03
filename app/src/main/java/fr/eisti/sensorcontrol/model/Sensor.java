package fr.eisti.sensorcontrol.model;

/**
 * Created by eisti on 12/12/17.
 *
 * Class describing the sensor
 */

public class Sensor {
    private String name;
    private boolean isOn;
    private String sensorClass;
    private String location;

    public Sensor(String name, boolean isOn, String sensorClass, String location) {
        this.name = name;
        this.isOn = isOn;
        this.sensorClass = sensorClass;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isOn() {
        return isOn;
    }

    public void setOn(boolean on) {
        isOn = on;
    }

    public String getSensorClass() {
        return sensorClass;
    }

    public void setSensorClass(String sensorClass) {
        this.sensorClass = sensorClass;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
