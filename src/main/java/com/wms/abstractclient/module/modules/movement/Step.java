package com.wms.abstractclient.module.modules.movement;

import com.wms.abstractclient.module.Module;
import com.wms.abstractclient.setting.Setting;
import org.lwjgl.input.Keyboard;

public class Step extends Module {

    Setting height = new Setting(this,"Height",1,1,2,1);

    public Step() {
        super("Step", Category.MOVEMENT, Keyboard.KEY_NONE);
        addSetting(height);
    }

    @Override
    public void onDisable() {
        mc.player.stepHeight = 0.6f;
    }

    @Override
    public void onTick() {
        if(nullcheck()){return;}
        mc.player.stepHeight = (float) height.getValue();
    }
}
