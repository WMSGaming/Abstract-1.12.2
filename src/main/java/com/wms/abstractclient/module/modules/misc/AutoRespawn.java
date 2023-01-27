package com.wms.abstractclient.module.modules.misc;

import com.wms.abstractclient.module.Module;
import net.minecraft.client.gui.GuiGameOver;
import org.lwjgl.input.Keyboard;

public class AutoRespawn extends Module {
    public AutoRespawn() {
        super("AutoRespawn", Category.MISC, Keyboard.KEY_NONE);
    }

    @Override
    public void onTick() {
    if(nullcheck()){return;}
        if(mc.currentScreen instanceof GuiGameOver){
            mc.player.respawnPlayer();
        }
    }
}
