package com.wms.abstractclient.gui.modern.clickgui.settingButtons;

import com.wms.abstractclient.AbstractClient;
import com.wms.abstractclient.font.FontUtil;
import com.wms.abstractclient.gui.modern.clickgui.ModuleButton;
import com.wms.abstractclient.gui.modern.clickgui.Sizing;
import com.wms.abstractclient.setting.Setting;
import net.minecraft.client.gui.Gui;
import java.awt.*;


public class BooleanButton extends SettingComponent {

    public Setting setting;
    public ModuleButton button;
    public int yOffset;

    public BooleanButton(Setting setting, ModuleButton moduleButton, int yOffset) {
        super(setting, moduleButton, yOffset);
        this.setting = setting;
        this.moduleButton = moduleButton;
        this.yOffset = yOffset;
    }
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        Color uni = AbstractClient.unicolour.getColor();
        Gui.drawRect(moduleButton.frame.x, (int) (moduleButton.frame.y+ moduleButton.offset + yOffset), (int) (moduleButton.frame.x + Sizing.WIDTH), (int) (moduleButton.frame.y + moduleButton.offset + yOffset + Sizing.HEIGHT),new Color(0,0,0,175).getRGB());
        Gui.drawRect(moduleButton.frame.x +1, (int) (moduleButton.frame.y+ moduleButton.offset + yOffset), (int) (moduleButton.frame.x + Sizing.WIDTH) -1, (int) (moduleButton.frame.y + moduleButton.offset + yOffset + Sizing.HEIGHT),new Color(0,0,0,175).getRGB());
        FontUtil.drawStringWithShadow(setting.getName() + ": " + setting.isEnabled(),moduleButton.frame.x + 2,moduleButton.frame.y + moduleButton.offset + yOffset + 2,hovered(mouseX,mouseY) ? AbstractClient.unicolour.getColor().getRGB() : -1);

    }
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if(hovered(mouseX,mouseY)){
            if(mouseButton == 0){
                setting.toggle();
            }
        }
    }
}
