package com.wms.abstractclient.gui.classic.clickgui.settingButtons;

import com.wms.abstractclient.AbstractClient;
import com.wms.abstractclient.gui.classic.clickgui.ModuleButton;
import com.wms.abstractclient.gui.classic.clickgui.Sizing;
import com.wms.abstractclient.setting.Setting;
import net.minecraft.client.gui.Gui;

import java.awt.*;

public class SettingComponent {

    public Setting setting;
    public ModuleButton moduleButton;
    public int yOffset;

    public SettingComponent(Setting setting, ModuleButton moduleButton, int yOffset){
        this.setting = setting;
        this.moduleButton = moduleButton;
        this.yOffset = yOffset;
    }
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        Gui.drawRect(moduleButton.frame.x, (int) (moduleButton.frame.y+ moduleButton.offset + yOffset), (int) (moduleButton.frame.x + Sizing.WIDTH), (int) (moduleButton.frame.y + moduleButton.offset + yOffset + Sizing.HEIGHT),AbstractClient.unicolour.getColor().getRGB());

        Gui.drawRect(moduleButton.frame.x+1, (int) (moduleButton.frame.y+ moduleButton.offset + yOffset), (int) (moduleButton.frame.x + Sizing.WIDTH)-1, (int) (moduleButton.frame.y + moduleButton.offset + yOffset + Sizing.HEIGHT),new Color(0,0,0,230).getRGB());

    }
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {

    }
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {

    }
    public void onKey(int key){

    }
    public boolean hovered(int mouseX ,int mouseY ) {
        return mouseX > moduleButton.frame.x && mouseX < moduleButton.frame.x + Sizing.WIDTH && mouseY > moduleButton.frame.y+ moduleButton.offset + yOffset && mouseY < moduleButton.frame.y + moduleButton.offset + yOffset + Sizing.HEIGHT;
    }
}
