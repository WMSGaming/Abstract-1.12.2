package com.wms.abstractclient.module.modules.movement;

import com.wms.abstractclient.module.Module;
import com.wms.abstractclient.setting.Setting;
import org.lwjgl.input.Keyboard;

public class TimeChanger extends Module {

    Setting timer = new Setting(this,"Timer",5,1,20,1);

    public TimeChanger() {
        super("Timer", Category.MOVEMENT, Keyboard.KEY_NONE);
        addSetting(timer);
    }

    @Override
    public void onTick() {
        if(nullcheck()){return;}
        mc.timer.tickLength = (float) (50 / timer.getValue());
    }

    @Override
    public void onDisable() {
        mc.timer.tickLength = 50f;
    }
}
