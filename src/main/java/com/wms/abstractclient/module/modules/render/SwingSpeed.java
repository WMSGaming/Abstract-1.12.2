package com.wms.abstractclient.module.modules.render;

import com.wms.abstractclient.module.Module;
import com.wms.abstractclient.setting.Setting;
import org.lwjgl.input.Keyboard;

public class SwingSpeed extends Module {

    Setting swingSpeed = new Setting(this,"Speed",5,1,15,1);

    public SwingSpeed() {
        super("SwingSpeed", Category.RENDER, Keyboard.KEY_NONE);
        addSetting(swingSpeed);
    }
}
