package com.wms.abstractclient.module.modules.movement;

import com.wms.abstractclient.module.Module;
import org.lwjgl.input.Keyboard;

public class NoPush extends Module {
    public NoPush() {
        super("NoPush", Category.MOVEMENT, Keyboard.KEY_NONE);
    }
}
