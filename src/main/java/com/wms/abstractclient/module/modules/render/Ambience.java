package com.wms.abstractclient.module.modules.render;

import com.wms.abstractclient.module.Module;
import com.wms.abstractclient.setting.Setting;
import org.lwjgl.input.Keyboard;

public class Ambience extends Module {
    Setting alphaColor = new Setting(this,"Alpha",255,0,255,0.5);

    public Ambience() {
        super("Ambience", Category.RENDER, Keyboard.KEY_NONE);
        addSetting(alphaColor);
    }
}
