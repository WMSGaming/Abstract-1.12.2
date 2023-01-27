package com.wms.abstractclient.module.modules.hub;

import com.wms.abstractclient.gui.classic.clickgui.ClickGui;
import com.wms.abstractclient.gui.modern.clickgui.ModernClickGui;
import com.wms.abstractclient.module.Module;
import com.wms.abstractclient.setting.Setting;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;

import java.util.Arrays;

public class ClickGuiModule extends Module {

    Setting clickgui = new Setting(this,"Gui", Arrays.asList("Classic","Modern"));
    Setting blur = new Setting(this,"Blur",false);
    Setting grad = new Setting(this,"Gradient",false);

    public ClickGuiModule() {
        super("ClickGui", Category.HUB, Keyboard.KEY_RSHIFT);
        addSetting(clickgui);
        addSetting(blur);
        addSetting(grad);
    }

    @Override
    public void onEnable(){

        if(clickgui.getEnumValue().equalsIgnoreCase("Classic")) {
            mc.displayGuiScreen(new ClickGui());
        }else {
            mc.displayGuiScreen(new ModernClickGui());
        }
        if(blur.isEnabled()) {
            if (OpenGlHelper.shadersSupported && mc.getRenderViewEntity() instanceof EntityPlayer) {
                if (mc.entityRenderer.getShaderGroup() != null) {
                    mc.entityRenderer.getShaderGroup().deleteShaderGroup();
                }
                mc.entityRenderer.loadShader(new ResourceLocation("shaders/post/blur.json"));
            }
        }
        toggle();
    }
}
