package com.wms.abstractclient.module.modules.render;

import com.wms.abstractclient.module.Module;
import org.lwjgl.input.Keyboard;

public class PlayerEsp extends Module {
    public PlayerEsp() {
        super("PlayerEsp", Category.RENDER, Keyboard.KEY_NONE);
    }
}
