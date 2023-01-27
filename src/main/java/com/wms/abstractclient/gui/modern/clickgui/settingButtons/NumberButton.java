package com.wms.abstractclient.gui.modern.clickgui.settingButtons;

import com.wms.abstractclient.AbstractClient;
import com.wms.abstractclient.font.FontUtil;
import com.wms.abstractclient.gui.modern.clickgui.ModuleButton;
import com.wms.abstractclient.gui.modern.clickgui.Sizing;
import com.wms.abstractclient.setting.Setting;
import net.minecraft.client.gui.Gui;

import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class NumberButton extends SettingComponent {
    public Setting setting;
    public ModuleButton button;
    public int yOffset;
    private boolean sliding = false;

    public NumberButton(Setting setting, ModuleButton moduleButton, int yOffset) {
        super(setting, moduleButton, yOffset);
        this.setting = setting;
        this.moduleButton = moduleButton;
        this.yOffset = yOffset;
    }
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {

        Gui.drawRect(moduleButton.frame.x, (int) (moduleButton.frame.y+ moduleButton.offset + yOffset), (int) (moduleButton.frame.x + Sizing.WIDTH), (int) (moduleButton.frame.y + moduleButton.offset + yOffset + Sizing.HEIGHT),new Color(0,0,0,175).getRGB());
        Gui.drawRect(moduleButton.frame.x +1, (int) (moduleButton.frame.y+ moduleButton.offset + yOffset), (int) (moduleButton.frame.x + Sizing.WIDTH) -1, (int) (moduleButton.frame.y + moduleButton.offset + yOffset + Sizing.HEIGHT),new Color(0,0,0,175).getRGB());
        double diff = Math.min(Sizing.WIDTH,Math.max(0,mouseX - moduleButton.frame.x));
        int renderWidth = (int) (Sizing.WIDTH * (setting.getValue() - setting.getMin()) / (setting.getMax() - setting.getMin()));

        Gui.drawRect(moduleButton.frame.x, (int) (moduleButton.frame.y+ moduleButton.offset + yOffset), (int) (moduleButton.frame.x + renderWidth), (int) (moduleButton.frame.y + moduleButton.offset + yOffset + Sizing.HEIGHT), AbstractClient.unicolour.getColor().getRGB());

        if(sliding){
            if(diff == 0){
                setting.setValue(setting.getMin());
            }else{
                setting.setValue(roundToPlace(((diff/ Sizing.WIDTH) * (setting.getMax() - setting.getMin()) + setting.getMin()), 1));
            }
        }

        FontUtil.drawStringWithShadow(setting.getName() + " : " + roundToPlace(setting.getValue(),1),moduleButton.frame.x + 2,moduleButton.frame.y + moduleButton.offset + yOffset + 2,hovered(mouseX,mouseY) ? AbstractClient.unicolour.getColor().getRGB() : -1);

    }
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if(hovered(mouseX,mouseY)) {
            sliding = true;
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        sliding = false;
    }

    public double roundToPlace(double value, int place){
        if(place < 0){
            return value;
        }
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(place, RoundingMode.HALF_DOWN);
        return bd.doubleValue();
    }
}
