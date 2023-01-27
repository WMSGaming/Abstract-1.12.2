package com.wms.abstractclient.module.modules.chat;

import com.wms.abstractclient.event.PacketReceiveEvent;
import com.wms.abstractclient.module.Module;
import com.wms.abstractclient.util.TimerUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraft.network.play.server.SPacketEntityStatus;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

public class EzPopper extends Module {

    private TimerUtil timer = new TimerUtil();

    public EzPopper() {
        super("EzPopper", Category.CHAT, Keyboard.KEY_NONE);
    }

    @SubscribeEvent
    public void onPacket(PacketReceiveEvent event){
        if (event.getPacket() instanceof SPacketEntityStatus) {
            SPacketEntityStatus packet = event.getPacket();
            if (packet.getOpCode() == 35 && packet.getEntity(mc.world) instanceof EntityPlayer) {
                String name = packet.getEntity(mc.world).getName();
                if(!name.equalsIgnoreCase(mc.player.getName())) {
                    if(timer.hasTimeElapsed(1000,true)) {
                        mc.player.connection.sendPacket(new CPacketChatMessage(name + " KEEP POPPING NN!"));
                    }
                }
            }
        }
    }
}
