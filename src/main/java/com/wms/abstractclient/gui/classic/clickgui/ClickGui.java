package com.wms.abstractclient.gui.classic.clickgui;

import com.wms.abstractclient.AbstractClient;
import com.wms.abstractclient.module.Module.Category;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClickGui extends GuiScreen {

    List<Frame> frames;

    public ClickGui()   {
        int x = 25;

        frames = new ArrayList<>();

        for(Category c : Category.values()) {
            frames.add(new Frame(c,x,20));
            x+=115;
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        ScaledResolution sr = new ScaledResolution(mc);
        if(AbstractClient.moduleManager.getModuleFromName("ClickGui").getSetting("Gradient").isEnabled()){
            this.drawGradientRect(0,0, sr.getScaledWidth(), sr.getScaledHeight(), new Color(0,0,0,0).getRGB(),AbstractClient.unicolour.getColor().getRGB());
        }

        for (Frame frame : frames) {
            frame.drawScreen(mouseX,mouseY,partialTicks);
            frame.updatePosition(mouseX,mouseY);
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        for (Frame frame : frames) {
            frame.mouseClick(mouseX,mouseY,mouseButton);
        }
    }
    @Override
    protected void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        for(Frame frame : frames) {
            frame.mouseReleased(mouseX, mouseY, mouseButton);
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        for(Frame frame : frames){
            frame.keyTyped(keyCode);
        }
        if (keyCode == Keyboard.KEY_ESCAPE)
        {
            mc.displayGuiScreen(null);

            if (mc.currentScreen == null)
            {
                mc.setIngameFocus();
            }
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
    @Override
    public void onGuiClosed() {
        if (mc.entityRenderer.getShaderGroup() != null) {
            mc.entityRenderer.getShaderGroup().deleteShaderGroup();
        }
    }
}
