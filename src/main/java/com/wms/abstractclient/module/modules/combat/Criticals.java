package com.wms.abstractclient.module.modules.combat;

import com.wms.abstractclient.event.PacketSendEvent;
import com.wms.abstractclient.module.Module;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

public class Criticals extends Module {
    public Criticals() {
        super("Criticals", Category.COMBAT, Keyboard.KEY_NONE);
    }
    @SubscribeEvent
    public void onPacket(PacketSendEvent event){
        if(event.getPacket() instanceof CPacketUseEntity){
            CPacketUseEntity packet = (CPacketUseEntity) event.getPacket();
            if(packet.getAction() == CPacketUseEntity.Action.ATTACK){
                if(mc.player.getHeldItemMainhand().getItem() instanceof ItemSword){
                    sendCriticalPackets();
                }
            }
        }
    }

    private void sendCriticalPackets() {
        if(mc.player.onGround && !mc.gameSettings.keyBindJump.isKeyDown()){
            mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.0625101, mc.player.posZ, false));
            mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, false));
            mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 1.1E-5, mc.player.posZ, false));
            mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, false));
        }
    }
}
