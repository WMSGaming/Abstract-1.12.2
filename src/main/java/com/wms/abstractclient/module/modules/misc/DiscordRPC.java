package com.wms.abstractclient.module.modules.misc;

import com.wms.abstractclient.DiscordPresence;
import com.wms.abstractclient.module.Module;
import org.lwjgl.input.Keyboard;

public class DiscordRPC extends Module {
    public DiscordRPC() {
        super("DiscordRPC", Category.MISC, Keyboard.KEY_NONE);
    }

    @Override
    public void onEnable() {
        DiscordPresence.start();
    }

    @Override
    public void onDisable() {
        DiscordPresence.stop();
    }
}
