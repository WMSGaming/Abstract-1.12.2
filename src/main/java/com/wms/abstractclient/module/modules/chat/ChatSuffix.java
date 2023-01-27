package com.wms.abstractclient.module.modules.chat;

import com.wms.abstractclient.module.Module;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

public class ChatSuffix extends Module {
    public ChatSuffix() {
        super("ChatSuffix", Category.CHAT, Keyboard.KEY_NONE);
    }
    @SubscribeEvent
    public void onChat(ClientChatEvent event){
        if (nullcheck()) {return;}
        if(event.getMessage().startsWith("/") || event.getMessage().startsWith(".")
                || event.getMessage().startsWith("!")
                || event.getMessage().startsWith(",")) {
            return;
        }
        event.setMessage(event.getMessage() + " [Abstract]");
    }
}
