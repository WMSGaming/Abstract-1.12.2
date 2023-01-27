package com.wms.abstractclient.module.modules.hub;

import com.wms.abstractclient.AbstractClient;
import com.wms.abstractclient.module.Module;
import com.wms.abstractclient.setting.Setting;
import org.lwjgl.input.Keyboard;

public class Colour extends Module {

    Setting red = new Setting(this,"Red",255,0,255,1);
    Setting green = new Setting(this,"Green",255,0,255,1);
    Setting blue = new Setting(this,"Blue",255,0,255,1);

    public Colour() {
        super("Colour", Category.HUB, Keyboard.KEY_NONE);
        addSetting(red);
        addSetting(green);
        addSetting(blue);
    }
    @Override
    public void onTick(){
        AbstractClient.unicolour.setRed((int) red.getValue());
        AbstractClient.unicolour.setGreen((int) green.getValue());
        AbstractClient.unicolour.setBlue((int) blue.getValue());
    }
}
