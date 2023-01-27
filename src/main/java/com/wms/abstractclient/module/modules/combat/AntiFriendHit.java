package com.wms.abstractclient.module.modules.combat;

import com.wms.abstractclient.event.PacketSendEvent;
import com.wms.abstractclient.manager.FriendManager;
import com.wms.abstractclient.module.Module;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

public class AntiFriendHit extends Module {
    public AntiFriendHit() {
        super("AntiFriendHit", Category.COMBAT, Keyboard.KEY_NONE);
    }

    @SubscribeEvent
    public void onPacket(PacketSendEvent event){
        if(nullcheck()){return;}
        if(event.getPacket() instanceof CPacketUseEntity){
            CPacketUseEntity packet = (CPacketUseEntity) event.getPacket();
            if(packet.getAction().equals(CPacketUseEntity.Action.ATTACK)){
                Entity entity = packet.getEntityFromWorld(mc.world);
                if(entity instanceof EntityPlayer) {
                    if (FriendManager.isFriend((EntityPlayer) entity)) {
                        event.setCanceled(true);
                    }
                }
            }
        }
    }
}
