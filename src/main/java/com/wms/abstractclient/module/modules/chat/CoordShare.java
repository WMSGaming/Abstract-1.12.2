package com.wms.abstractclient.module.modules.chat;

import com.wms.abstractclient.AbstractClient;
import com.wms.abstractclient.event.PacketReceiveEvent;
import com.wms.abstractclient.manager.FriendManager;
import com.wms.abstractclient.module.Module;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraft.network.play.server.SPacketChat;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

public class CoordShare extends Module {
    public CoordShare() {
        super("CoordShare", Category.CHAT, Keyboard.KEY_NONE);
    }

    @Override
    public void onEnable() {
        sendMsg(" whisper ?coords to someone and if they have you friended, their coordinates will been sent to you!");
    }

    @SubscribeEvent
    public void onPacket(PacketReceiveEvent event){
        if(nullcheck()){return;}
        if(event.getPacket() instanceof SPacketChat){

            String message =  ((SPacketChat) event.getPacket()).getChatComponent().getUnformattedText().toLowerCase();
            String username = message.split(" ")[0];

            if(FriendManager.isNameFriend(username) || AbstractClient.friendManager.isNameFriend(username)){
                if(message.contains("?coords")) {
                    sendCoordinates(username);
                }
            }
        }
    }

    public void sendCoordinates(String ign){
        String info = " X:" + (int) mc.player.posX + " Z:" + (int) mc.player.posZ;
        mc.player.connection.sendPacket(new CPacketChatMessage("/msg " + ign + info));
        sendMsg("Sent coordinates to " + ign);
    }
}
