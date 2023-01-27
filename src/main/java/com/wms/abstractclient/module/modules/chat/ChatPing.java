package com.wms.abstractclient.module.modules.chat;

import com.wms.abstractclient.event.PacketReceiveEvent;
import com.wms.abstractclient.module.Module;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.play.server.SPacketChat;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

public class ChatPing extends Module {
    public ChatPing() {
        super("ChatPing", Category.CHAT, Keyboard.KEY_NONE);
    }

    @SubscribeEvent
    public void onPacket(PacketReceiveEvent event){
        if(nullcheck()){return;}
        if(event.getPacket() instanceof SPacketChat){
            try {
                String message = ((SPacketChat) event.getPacket()).getChatComponent().getUnformattedText().toLowerCase().split(" ")[1];
                if (message.contains(mc.player.getName().toLowerCase())) {
                    mc.getSoundHandler().playSound((ISound) PositionedSoundRecord.getRecord(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f));
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
