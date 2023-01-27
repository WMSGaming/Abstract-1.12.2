package com.wms.abstractclient.module.modules.render;

import com.wms.abstractclient.module.Module;
import com.wms.abstractclient.setting.Setting;
import org.lwjgl.input.Keyboard;

public class Fov extends Module {

    Setting fov = new Setting(this,"Fov",120,120,200,1);

    public Fov() {
        super("Fov", Category.RENDER, Keyboard.KEY_NONE);
        addSetting(fov);
    }

    @Override
    public void onTick() {
        mc.gameSettings.fovSetting = (float) fov.getValue();
    }
}
