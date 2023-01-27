package com.wms.abstractclient.event;

import net.minecraftforge.fml.common.eventhandler.Event;

public class PerspectiveEvent extends Event {
    private float aspect;

    public PerspectiveEvent(float aspect) {
        this.aspect = aspect;
    }

    public float getAspect() {
        return aspect;
    }

    public void setAspect(float aspect) {
        this.aspect = aspect;
    }
}