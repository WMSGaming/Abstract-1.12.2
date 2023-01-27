package com.wms.abstractclient.module.modules.movement;

import com.wms.abstractclient.event.PacketReceiveEvent;
import com.wms.abstractclient.module.Module;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.network.play.server.SPacketEntityStatus;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.network.play.server.SPacketExplosion;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

public class Velocity extends Module {
    public Velocity() {
        super("Velocity", Category.MOVEMENT, Keyboard.KEY_NONE);
    }
   @SubscribeEvent
   public void onPacket(PacketReceiveEvent event){
       Entity entity;
       SPacketEntityStatus packet;
       if (event.getPacket() instanceof SPacketEntityVelocity && ((SPacketEntityVelocity) event.getPacket()).getEntityID() == mc.player.getEntityId()) {
           event.setCanceled(true);
           return;
       }
       if (event.getPacket() instanceof SPacketEntityStatus && (packet = event.getPacket()).getOpCode() == 31 && (entity = packet.getEntity(mc.world)) instanceof EntityFishHook) {
           EntityFishHook fishHook = (EntityFishHook) entity;
           if (fishHook.caughtEntity == mc.player) {
               event.setCanceled(true);
           }
       }
       if (event.getPacket() instanceof SPacketExplosion) {
           event.setCanceled(true);
       }
   }
}
