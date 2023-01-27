package com.wms.abstractclient.module.modules.hub;

import com.wms.abstractclient.module.Module;
import org.lwjgl.input.Keyboard;

public class Notifications extends Module {
    public Notifications() {
        super("Notifications", Category.HUB, Keyboard.KEY_NONE);
    }
}
