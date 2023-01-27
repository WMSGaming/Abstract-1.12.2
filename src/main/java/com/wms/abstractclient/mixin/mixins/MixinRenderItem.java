package com.wms.abstractclient.mixin.mixins;


import com.wms.abstractclient.AbstractClient;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.awt.*;

@Mixin(RenderItem.class)
public class MixinRenderItem {

    @Shadow
    private void renderModel(IBakedModel model, int color, ItemStack stack) {}

    @Redirect(method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/renderer/block/model/IBakedModel;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderItem;renderModel(Lnet/minecraft/client/renderer/block/model/IBakedModel;Lnet/minecraft/item/ItemStack;)V"))
    private void renderModelColor(RenderItem renderItem, IBakedModel model, ItemStack stack) {
        if (AbstractClient.moduleManager.getModuleFromName("Viewmodel").isEnabled()) {
            renderModel(model, new Color(255, 255,255, (int) AbstractClient.moduleManager.getModuleFromName("Viewmodel").getSetting("Alpha").getValue()).getRGB(), stack);
        } else {
            renderModel(model, -1, stack);
        }
    }
}
