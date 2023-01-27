package com.wms.abstractclient.module.modules.movement;

import com.wms.abstractclient.module.Module;
import com.wms.abstractclient.setting.Setting;
import org.lwjgl.input.Keyboard;

public class ReverseStep extends Module {

    Setting motion = new Setting(this,"Motion",1,0.1,1,0.1);

    public ReverseStep() {
        super("ReverseStep", Category.MOVEMENT, Keyboard.KEY_NONE);
        addSetting(motion);
    }

    @Override
    public void onTick() {
        if(nullcheck()){return;}
        if (!ReverseStep.mc.player.onGround || ReverseStep.mc.player.isOnLadder() || ReverseStep.mc.player.isInWater() || ReverseStep.mc.player.isInLava() || ReverseStep.mc.player.movementInput.jump || ReverseStep.mc.player.noClip) {
            return;
        }
        mc.player.motionY = -motion.getValue();
    }
}
