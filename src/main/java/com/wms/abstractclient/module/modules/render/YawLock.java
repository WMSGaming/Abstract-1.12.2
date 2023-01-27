package com.wms.abstractclient.module.modules.render;

import com.wms.abstractclient.module.Module;
import net.minecraft.util.EnumFacing;
import org.lwjgl.input.Keyboard;

public class YawLock extends Module {
    public YawLock() {
        super("YawLock", Category.RENDER, Keyboard.KEY_NONE);
    }

    @Override
    public void onTick() {
        if(nullcheck()){return;}
        EnumFacing direction = EnumFacing.fromAngle(mc.player.rotationYawHead);
        mc.player.rotationYaw = direction.getHorizontalAngle();
    }
}
