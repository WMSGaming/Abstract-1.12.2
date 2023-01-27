package com.wms.abstractclient.event;

import net.minecraft.entity.MoverType;
import net.minecraftforge.fml.common.eventhandler.Event;

public class MotionEvent extends Event {
    private MoverType type;
    private double motionX, motionY, motionZ;

    public MotionEvent(MoverType type,double x, double y, double z){
        this.type = type;
        this.motionX = x;
        this.motionY = y;
        this.motionZ = z;
    }
    public MoverType getType() {
        return type;
    }

    public void setType(MoverType type) {
        this.type = type;
    }

    public double getMotionX() {
        return motionX;
    }

    public void setMotionX(double motionX) {
        this.motionX = motionX;
    }

    public double getMotionY() {
        return motionY;
    }

    public void setMotionY(double motionY) {
        this.motionY = motionY;
    }

    public double getMotionZ() {
        return motionZ;
    }

    public void setMotionZ(double motionZ) {
        this.motionZ = motionZ;
    }


}
