package com.example.Smart_Attendance_System.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class ConfigProp {
    @Id
    String prop;
    String val;

    public ConfigProp() {
    }

    public ConfigProp(String prop, String val) {
        this.prop = prop;
        this.val = val;
    }

    public String getProp() {
        return prop;
    }

    public void setProp(String prop) {
        this.prop = prop;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    @Override
    public String toString() {
        return "ConfigProp{" +
                "prop='" + prop + '\'' +
                ", val='" + val + '\'' +
                '}';
    }
}
