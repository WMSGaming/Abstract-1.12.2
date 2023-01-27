package com.wms.abstractclient.mixin.mixins;


import com.mojang.authlib.GameProfile;
import com.wms.abstractclient.AbstractClient;
import com.wms.abstractclient.event.MotionEvent;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.MoverType;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(EntityPlayerSP.class)
public class MixinEntityPlayerSP extends AbstractClientPlayer {

    public MixinEntityPlayerSP(World worldIn, GameProfile playerProfile) {
        super(worldIn, playerProfile);
    }

    @Inject(method = "pushOutOfBlocks(DDD)Z", at = @At("HEAD"), cancellable = true)
    public void pushOutOfBlocks(double x, double y, double z, CallbackInfoReturnable<Boolean> callbackInfo) {
        if(AbstractClient.moduleManager.getModuleFromName("NoPush").isEnabled()) {
            callbackInfo.setReturnValue(false);
        }
    }


    @Inject(method = { "move" }, at = { @At("HEAD") }, cancellable = true)
    protected void move(final MoverType type, final double x, final double y, final double z, final CallbackInfo ci) {
        MotionEvent event = new MotionEvent(type, x, y, z);
        MinecraftForge.EVENT_BUS.post(event);
        if(event.getMotionX() != x || event.getMotionY() != y ||event.getMotionZ() != z){
            super.move(type, event.getMotionX(), event.getMotionY(), event.getMotionZ());
            ci.cancel();
        }
    }
}
