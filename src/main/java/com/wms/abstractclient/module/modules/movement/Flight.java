package com.wms.abstractclient.module.modules.movement;

import com.wms.abstractclient.module.Module;
import com.wms.abstractclient.setting.Setting;
import net.minecraft.network.play.client.CPacketPlayer;
import org.lwjgl.input.Keyboard;

import java.util.Arrays;

public class Flight extends Module {

    Setting mode = new Setting(this,"Mode", Arrays.asList("Creative","Old Verus"));

    public Flight() {
        super("Flight", Category.MOVEMENT, Keyboard.KEY_NONE);
        addSetting(mode);
    }

    @Override
    public void onTick() {
        if(nullcheck()) {return;}

        if(mode.getEnumValue().equalsIgnoreCase("Old Verus")){
            mc.player.onGround = true;
            mc.player.motionY = 0;
        }else {
            if(mc.player.ticksExisted % 4 == 0 && !mc.player.onGround){
                mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX,mc.player.posY - 0.04, mc.player.posZ, mc.player.onGround));
            }

            mc.player.capabilities.isFlying = true;
        }
    }

    @Override
    public void onDisable() {
        if(nullcheck()) {return;}
        mc.player.capabilities.isFlying = false;
    }
}
