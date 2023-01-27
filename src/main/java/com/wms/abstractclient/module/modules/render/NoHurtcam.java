package com.wms.abstractclient.module.modules.render;

import com.wms.abstractclient.module.Module;
import org.lwjgl.input.Keyboard;

public class NoHurtcam extends Module {
    public NoHurtcam() {
        super("NoHurtCam", Category.RENDER, Keyboard.KEY_NONE);
    }
}
