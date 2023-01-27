package com.wms.abstractclient.module.modules.movement;

import com.wms.abstractclient.module.Module;
import com.wms.abstractclient.setting.Setting;
import com.wms.abstractclient.util.MovementUtil;
import org.lwjgl.input.Keyboard;

public class ElytraFlight extends Module {

    Setting hSpeed = new Setting(this,"hSpeed",2.5,0.5,10,0.1);
    Setting vSpeed = new Setting(this,"vSpeed",2,0.5,5,0.1);

    public ElytraFlight() {
        super("ElytraFlight", Category.MOVEMENT, Keyboard.KEY_NONE);
        addSetting(hSpeed);
        addSetting(vSpeed);
    }

    @Override
    public void onTick() {
        if(nullcheck()) return;
        if(mc.player.isElytraFlying()){
            mc.player.motionY = 0;
            if(mc.player.movementInput.jump){
                mc.player.motionY += (float) vSpeed.getValue();
            }
            if(mc.player.movementInput.sneak){
                mc.player.motionY -= (float) vSpeed.getValue();
            }

            if(MovementUtil.isMoving()){
                MovementUtil.strafe((float) hSpeed.getValue());
            }else{
                mc.player.motionX = 0;
                mc.player.motionZ = 0;
            }

        }
    }
}
