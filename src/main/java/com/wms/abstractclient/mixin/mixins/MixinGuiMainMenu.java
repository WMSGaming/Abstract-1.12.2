package com.wms.abstractclient.mixin.mixins;

import com.wms.abstractclient.gui.MainMenu;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({GuiMainMenu.class})
public class MixinGuiMainMenu extends GuiScreen {

    @Inject(method = "initGui", at = @At("HEAD"))
    public void initGui(CallbackInfo ci){
        mc.displayGuiScreen(new MainMenu());
    }
}