package com.wms.abstractclient.module.modules.misc;

import com.wms.abstractclient.event.PacketReceiveEvent;
import com.wms.abstractclient.module.Module;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

public class NoRotate extends Module {
    public NoRotate() {
        super("NoRotate", Category.MISC, Keyboard.KEY_NONE);
    }
    @SubscribeEvent
    public void onPacket(PacketReceiveEvent event){
        if(nullcheck()){return;}
        if(event.getPacket() instanceof SPacketPlayerPosLook){
            SPacketPlayerPosLook packet = (SPacketPlayerPosLook) event.getPacket();
            packet.yaw = mc.player.rotationYaw;
            packet.pitch = mc.player.rotationPitch;
        }
    }
}
