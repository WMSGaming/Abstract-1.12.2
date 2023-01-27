package com.wms.abstractclient.mixin.mixins;

import com.wms.abstractclient.AbstractClient;
import com.wms.abstractclient.event.PacketReceiveEvent;
import com.wms.abstractclient.event.PacketSendEvent;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import io.netty.channel.ChannelHandlerContext;



@Mixin(value = NetworkManager.class, priority = 634756347)
public class MixinNetworkManager {

    @Inject(method = "channelRead0", at = @At("HEAD"), cancellable = true)
    public void channelRead0(ChannelHandlerContext p_channelRead0_1_, Packet<?> p_channelRead0_2_, final CallbackInfo callbackInfo) {
        if(p_channelRead0_2_ == null){return;}
        PacketReceiveEvent event = new PacketReceiveEvent(p_channelRead0_2_);
        MinecraftForge.EVENT_BUS.post(event);
        if(event.isCanceled()) callbackInfo.cancel();
    }

    @Inject(method = "sendPacket(Lnet/minecraft/network/Packet;)V", at = @At("HEAD"), cancellable = true)
    private void onPacketSend(Packet<?> packet, CallbackInfo callbackInfo) {
        PacketSendEvent event = new PacketSendEvent(packet);
        MinecraftForge.EVENT_BUS.post(event);

        if(event.getPacket() instanceof CPacketPlayer.Rotation || event.getPacket() instanceof CPacketPlayer.PositionRotation){
            if(AbstractClient.rotationManager.isRotating()) {
                CPacketPlayer p = (CPacketPlayer) event.getPacket();
                p.yaw = AbstractClient.rotationManager.getYaw();
                p.pitch = AbstractClient.rotationManager.getPitch();
            }
        }

        if (event.isCanceled()) callbackInfo.cancel();

    }
}