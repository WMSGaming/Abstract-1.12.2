package com.wms.abstractclient.gui.modern.clickgui;

import com.wms.abstractclient.AbstractClient;
import com.wms.abstractclient.font.FontUtil;
import com.wms.abstractclient.gui.modern.clickgui.settingButtons.SettingComponent;
import com.wms.abstractclient.module.Module;
import com.wms.abstractclient.module.Module.Category;
import com.wms.abstractclient.util.GuiUtil;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Frame extends GuiScreen{
    public int x,y,dragX,dragY;
    public Category category;
    public boolean visible, dragging;
    public List<ModuleButton> moduleButtons;

    public Frame(Category category,int x, int y) {
        this.category = category;
        this.x = x;
        this.y = y;

        moduleButtons = new ArrayList<>();

        visible = true;
        dragging = false;

        int offset = (int) Sizing.HEIGHT;

        for(Module m : AbstractClient.moduleManager.getModuleFromCategory(category)){
            moduleButtons.add(new ModuleButton(this,m, offset));
            offset+= Sizing.HEIGHT;
        }

    }
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        Color uni = AbstractClient.unicolour.getColor();

        Gui.drawRect(x,y-1,x + (int) Sizing.WIDTH, y + (int) Sizing.HEIGHT,new Color(0,0,0,220).getRGB());
        FontUtil.drawStringWithShadow(category.name ,x,y, -1);


        if(visible){
            for (ModuleButton moduleButton : moduleButtons) {
                moduleButton.drawScreen(mouseX,mouseY,partialTicks);
            }
        }

    }

    public boolean mouseClick(int mouseX, int mouseY, int mouseButton) {
        if(visible){
            for (ModuleButton moduleButton : moduleButtons) {
                    moduleButton.mouseClicked(mouseX,mouseY,mouseButton);
            }
        }

        if(GuiUtil.hovered(x,y, (int) Sizing.WIDTH, (int) Sizing.HEIGHT,mouseX,mouseY)) {
            if(mouseButton == 0){
                dragging = true;
                dragX = mouseX - x;
                dragY = mouseY - y;
            }else if(mouseButton == 1){
                visible = !visible;
            }
            return true;
        }
        return false;
    }

    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        if(mouseButton == 0 && dragging) dragging = false;
        for(ModuleButton mb : moduleButtons){
            mb.mouseReleased(mouseX,mouseY,mouseButton);
        }
    }
    public void keyTyped(int keyCode) {
        for (ModuleButton mb : moduleButtons) {
            mb.onKey(keyCode);
        }
    }
    public void updatePosition(int mouseX, int mouseY) {
        if(dragging) {
            x = mouseX - dragX;
            y = mouseY - dragY;
        }
    }

    public void updateButtons() {
        int offset = (int) Sizing.HEIGHT;

        for(ModuleButton button : moduleButtons) {
            button.offset = offset;
            offset += Sizing.HEIGHT;

            if(button.visible) {
                for(SettingComponent comp : button.settings) {
                    offset+= Sizing.HEIGHT;
                }
            }
        }
    }
}
