package com.wms.abstractclient.event;

import net.minecraft.network.Packet;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

@Cancelable
public class PacketReceiveEvent extends Event {

    public PacketReceiveEvent(Packet<?> p) {
        this.p = p;
    }

    final Packet<?> p;

    @SuppressWarnings("unchecked")
    public <T extends Packet<?>> T getPacket() {
        return (T) p;
    }
}
