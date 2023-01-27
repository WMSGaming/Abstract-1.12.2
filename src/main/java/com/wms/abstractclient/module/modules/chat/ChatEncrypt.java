package com.wms.abstractclient.module.modules.chat;

import com.wms.abstractclient.event.PacketReceiveEvent;
import com.wms.abstractclient.module.Module;
import net.minecraft.network.play.server.SPacketChat;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;


public class ChatEncrypt extends Module {

    private Encoder encoder = Base64.getEncoder();
    private Decoder decoder = Base64.getDecoder();

    public ChatEncrypt() {
        super("ChatEncrypt", Category.CHAT, Keyboard.KEY_NONE);
    }

    @SubscribeEvent
    public void onChat(ClientChatEvent event){
        if(nullcheck()){return;}

        if(event.getMessage().startsWith("/") || event.getMessage().startsWith(".")
                || event.getMessage().startsWith("!")
                || event.getMessage().startsWith(",")) {
            return;
        }

        String ogMsg =  event.getMessage();
        byte[] encodedData   = encoder.encode(ogMsg.getBytes());
        String encodedString = new String(encodedData);
        event.setMessage( "^"+ encodedString+"^");
    }

    @SubscribeEvent
    public void onPacket(PacketReceiveEvent event){
        if(nullcheck()){return;}
        if(event.getPacket() instanceof SPacketChat) {
            String message = ((SPacketChat) event.getPacket()).getChatComponent().getUnformattedText().split(" ")[1];
            try {

                String newString = message.replace("^", "");
                byte[] decodedDataInBytes = decoder.decode(newString.getBytes());
                String decodedData = new String(decodedDataInBytes);

                if (message.startsWith("^") && message.endsWith("^")) {
                    sendMsg(decodedData);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
