package com.wms.abstractclient.module.modules.render;

import com.wms.abstractclient.module.Module;
import org.lwjgl.input.Keyboard;

public class Fullbright extends Module {

    public float brightness;

    public Fullbright() {
        super("Fullbright", Category.RENDER, Keyboard.KEY_NONE);
    }

    @Override
    public void onEnable() {
        brightness = mc.gameSettings.gammaSetting;
        mc.gameSettings.gammaSetting = 500;
    }

    @Override
    public void onDisable() {
        mc.gameSettings.gammaSetting = brightness;
    }
}
