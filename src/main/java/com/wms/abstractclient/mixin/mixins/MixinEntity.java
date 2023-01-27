package com.wms.abstractclient.mixin.mixins;

import com.wms.abstractclient.event.StepEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class MixinEntity {

    @Shadow
    public float stepHeight;

    @Shadow
    public abstract AxisAlignedBB getEntityBoundingBox();

    @Inject(method = "move", at = @At(value = "INVOKE", target = "Lnet/minecraft/profiler/Profiler;endSection()V", shift = Shift.BEFORE, ordinal = 0))
    public void onMove(MoverType type, double x, double y, double z, CallbackInfo info) {
        if (((Entity) (Object) this).equals(Minecraft.getMinecraft().player)) {
            
            StepEvent event = new StepEvent(getEntityBoundingBox(), stepHeight);
            MinecraftForge.EVENT_BUS.post(event);

            if (event.isCanceled()) {
                stepHeight = event.getHeight();
            }
        }
    }
}
