package com.wms.abstractclient.setting;

import com.wms.abstractclient.module.Module;
import java.util.List;

public class Setting {
    public double min;
    public double max;
    public double value;


    public double increment;
    public List<String> enumValues;
    public String enumValue;
    public boolean enabled;
    public String name;
    public Module module;
    public Type type;

    public Setting(Module module,String name,boolean enabled) {
        this.module = module;
        this.name = name;
        this.enabled = enabled;
        this.type = Type.BOOLEAN;
    }
    public Setting(Module module,String name,double value,double min,double max, double increment) {
        this.module = module;
        this.name = name;
        this.value = value;
        this.min = min;
        this.max = max;
        this.increment = increment;
        this.type = Type.NUMBER;
    }
    public Setting(Module module, String name, List<String> enumValues) {
        this.name = name;
        this.module = module;
        this.enumValue = enumValues.get(0);
        this.enumValues = enumValues;
        this.type = Type.OPTION;
    }

    public double getIncrement() {
        return increment;
    }
    public void setIncrement(double increment) {
        this.increment = increment;
    }
    public Type getType() {
        return this.type;
    }
    public void toggle() {
        this.enabled = !enabled;
    }
    public List<String> getEnumValues() {
        return enumValues;
    }
    public void setEnumValues(List<String> enumValues) {
        this.enumValues = enumValues;
    }
    public String getEnumValue() {
        return enumValue;
    }
    public void setEnumValue(String enumValue) {
        this.enumValue = enumValue;
    }
    public double getMin() {
        return min;
    }
    public void setMin(double min) {
        this.min = min;
    }
    public double getMax() {
        return max;
    }
    public void setMax(double max) {
        this.max = max;
    }
    public double getValue() {
        return value;
    }
    public void setValue(double value) {
        this.value = value;
    }
    public boolean isEnabled() {
        return enabled;
    }
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Module getModule() {
        return module;
    }
    public void setModule(Module module) {
        this.module = module;
    }

    public enum Type {
        BOOLEAN,
        NUMBER,
        OPTION,
        BIND
    }
}
