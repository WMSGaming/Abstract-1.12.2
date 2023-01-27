package com.wms.abstractclient.module.modules.movement;

import com.wms.abstractclient.module.Module;
import org.lwjgl.input.Keyboard;

public class Sprint extends Module {

    public Sprint() {
        super("Sprint", Category.MOVEMENT, Keyboard.KEY_NONE);
    }

    @Override
    public void onTick() {
        if(nullcheck()) return;
        mc.player.setSprinting(true);
    }
}
