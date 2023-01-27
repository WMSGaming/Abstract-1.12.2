package com.wms.abstractclient.mixin.mixins;

import com.wms.abstractclient.AbstractClient;
import net.minecraft.entity.EntityLivingBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={EntityLivingBase.class})
public class MixinEntityLivingBase {
    @Inject(method={"getArmSwingAnimationEnd"}, at={@At(value="HEAD")}, cancellable=true)
    private void getArmSwingAnimationEnd(CallbackInfoReturnable<Integer> info) {
        try {
            if (AbstractClient.moduleManager.getModuleFromName("SwingSpeed").isEnabled()) {

                    info.setReturnValue((int)AbstractClient.moduleManager.getModuleFromName("SwingSpeed").getSetting("Speed").getValue());
            }
        }catch (Exception e){
            // crazy
        }
    }
}