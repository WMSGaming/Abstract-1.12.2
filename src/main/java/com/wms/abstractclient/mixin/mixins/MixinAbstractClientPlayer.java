package com.wms.abstractclient.mixin.mixins;

import com.wms.abstractclient.AbstractClient;
import com.wms.abstractclient.manager.BetaManager;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;
import java.util.Objects;

@Mixin({ AbstractClientPlayer.class })
public abstract class MixinAbstractClientPlayer {
    @Shadow
    @Nullable
    protected abstract NetworkPlayerInfo getPlayerInfo();
    
    @Inject(method = { "getLocationCape" }, at = { @At("HEAD") }, cancellable = true)
    public void getLocationCape(final CallbackInfoReturnable<ResourceLocation> loc) {
        if(AbstractClient.moduleManager.getModuleFromName("Cape").isEnabled()){
            if(BetaManager.isUserBeta(Objects.requireNonNull(this.getPlayerInfo()).getGameProfile().getName())){
                loc.setReturnValue(new ResourceLocation("textures/sleek.png"));
            }
        }
    }
}