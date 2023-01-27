package com.wms.abstractclient.mixin.accesor;

import net.minecraft.util.Timer;

public interface IMinecraft
{
    Timer getTimer();

    void setRightClickDelayTimer(final int p0);
}