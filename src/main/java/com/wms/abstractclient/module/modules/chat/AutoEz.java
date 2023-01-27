package com.wms.abstractclient.module.modules.chat;

import com.wms.abstractclient.module.Module;
import com.wms.abstractclient.util.TimerUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.CPacketChatMessage;
import org.lwjgl.input.Keyboard;

public class AutoEz extends Module {

    private TimerUtil timer = new TimerUtil();

    public AutoEz() {
        super("AutoEz", Category.CHAT, Keyboard.KEY_NONE);
    }

    @Override
    public void onTick() {
        if(nullcheck()){return;}

        for(EntityPlayer target : mc.world.playerEntities){
            if(target.getDistance(mc.player)  <= 15){
                if(!target.isEntityAlive() && target.getHealth() <= 0){
                    if(target != mc.player) {
                        if(timer.hasTimeElapsed(1000,true)) {
                            mc.player.connection.sendPacket(new CPacketChatMessage(target.getName() + " got $mok3d by @b$tr@ct "));
                            break;
                        }
                    }
                }
            }
        }
    }
}