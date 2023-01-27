package com.wms.abstractclient.module.modules.chat;

import com.wms.abstractclient.event.PacketReceiveEvent;
import com.wms.abstractclient.module.Module;
import com.wms.abstractclient.util.TimerUtil;
import io.netty.util.internal.MathUtil;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraft.network.play.server.SPacketChat;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

import java.util.Random;

public class AntiRetardClient extends Module {

    private final TimerUtil timer = new TimerUtil();
    private Random rand = new Random();

    public AntiRetardClient() {
        super("AntiRetardClient", Category.CHAT, Keyboard.KEY_NONE);
    }
    @SubscribeEvent
    public void onChat(PacketReceiveEvent event) {
        if (nullcheck()) {return;}

        if(event.getPacket() instanceof SPacketChat){
            SPacketChat packet = (SPacketChat) event.getPacket();
            String msg = packet.getChatComponent().getUnformattedText();
            if(msg.toLowerCase().contains("retard client".toLowerCase())){
                if(timer.hasTimeElapsed(500,true)) {
                    mc.player.connection.sendPacket(new CPacketChatMessage("@b$tr@ct client >> Retard client! {"+ rand.nextInt(1000) + "}"));
                }
            }
        }
    }
}
