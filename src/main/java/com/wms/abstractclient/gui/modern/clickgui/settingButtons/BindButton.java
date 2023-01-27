package com.wms.abstractclient.gui.modern.clickgui.settingButtons;

import com.wms.abstractclient.AbstractClient;
import com.wms.abstractclient.font.FontUtil;
import com.wms.abstractclient.gui.modern.clickgui.ModuleButton;
import com.wms.abstractclient.gui.modern.clickgui.Sizing;
import com.wms.abstractclient.setting.Setting;
import net.minecraft.client.gui.Gui;
import org.lwjgl.input.Keyboard;

import java.awt.*;

public class BindButton extends SettingComponent {
    public Setting setting;
    public ModuleButton button;
    public int yOffset;

    boolean binding = false;

    public BindButton(Setting setting, ModuleButton moduleButton, int yOffset) {
        super(setting, moduleButton, yOffset);
        this.setting = setting;
        this.moduleButton = moduleButton;
        this.yOffset = yOffset;
    }
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        Gui.drawRect(moduleButton.frame.x, (int) (moduleButton.frame.y+ moduleButton.offset + yOffset), (int) (moduleButton.frame.x + Sizing.WIDTH), (int) (moduleButton.frame.y + moduleButton.offset + yOffset + Sizing.HEIGHT),new Color(0,0,0,175).getRGB());
        Gui.drawRect(moduleButton.frame.x +1, (int) (moduleButton.frame.y+ moduleButton.offset + yOffset), (int) (moduleButton.frame.x + Sizing.WIDTH) -1, (int) (moduleButton.frame.y + moduleButton.offset + yOffset + Sizing.HEIGHT),new Color(0,0,0,175).getRGB());


        try {
            FontUtil.drawStringWithShadow("Bind: " + (binding ? "..." : Keyboard.getKeyName(moduleButton.module.getBind())),moduleButton.frame.x + 2,moduleButton.frame.y + moduleButton.offset + yOffset + 2,hovered(mouseX,mouseY) ? AbstractClient.unicolour.getColor().getRGB() : -1);
        }catch (Exception e){
            FontUtil.drawStringWithShadow("Bind: NONE",moduleButton.frame.x + 2,moduleButton.frame.y + moduleButton.offset + yOffset + 2,hovered(mouseX,mouseY) ? AbstractClient.unicolour.getColor().getRGB() : -1);
        }

    }
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if(hovered(mouseX,mouseY)){
            if(mouseButton == 0){
                binding =! binding;
            }
        }
    }
    public void onKey(int key){
        if(binding){
            if (key == Keyboard.KEY_DELETE || key == Keyboard.KEY_ESCAPE || key == Keyboard.KEY_BACK || key == Keyboard.KEY_NONE) {
                moduleButton.module.setBind(Keyboard.KEYBOARD_SIZE);
            }else{
                moduleButton.module.setBind(key);
            }
            binding = false;
        }
    }
}
