package com.wms.abstractclient.event;

import com.wms.abstractclient.AbstractClient;
import com.wms.abstractclient.module.Module;
import org.lwjgl.input.Keyboard;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;

public class KeyEvent {

    @SubscribeEvent
    public void onKey(KeyInputEvent e) {
        if (!Keyboard.getEventKeyState()) return;
        for(Module m : AbstractClient.moduleManager.getModules()) {
            if(m.getBind() == Keyboard.getEventKey()) {
                m.toggle();
            }
        }
    }
}