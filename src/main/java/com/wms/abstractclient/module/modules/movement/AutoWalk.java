package com.wms.abstractclient.module.modules.movement;

import com.wms.abstractclient.module.Module;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

public class AutoWalk extends Module {
    public AutoWalk() {
        super("AutoWalk", Category.MOVEMENT, Keyboard.KEY_NONE);
    }

    @Override
    public void onTick() {
        if(nullcheck()){return;}
        KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(),true);
    }

    @Override
    public void onDisable() {
        KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(),false);
    }
}
