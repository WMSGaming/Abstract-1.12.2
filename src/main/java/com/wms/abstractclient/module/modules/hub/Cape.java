package com.wms.abstractclient.module.modules.hub;

import com.wms.abstractclient.module.Module;
import org.lwjgl.input.Keyboard;

public class Cape extends Module {

    public Cape() {
        super("Cape", Category.HUB, Keyboard.KEY_NONE);
    }
}
