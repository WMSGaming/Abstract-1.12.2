package com.wms.abstractclient.module.modules.render;

import com.wms.abstractclient.module.Module;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.input.Keyboard;

public class Moonwalk extends Module {
    public Moonwalk() {
        super("MoonWalk",Category.RENDER, Keyboard.KEY_NONE);
    }

    @Override
    public void onTick() {
        if(nullcheck()){return;}
        for(EntityPlayer p : mc.world.playerEntities) {
            if(p != null) {
                p.limbSwing = 0;
                p.limbSwingAmount = 0;
                p.prevLimbSwingAmount = 0;
            }
        }
    }
}
