package com.vaban.petnettimepickerlib.model;

/**
 * Created by vaban on 2/20/2016.
 */
public class TimePickerData {

    private String name;
    private long time;

    public TimePickerData() {
        this.name = "";
        this.time = 0;
    }

    public TimePickerData(String name, long time) {
        this.name = name;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
