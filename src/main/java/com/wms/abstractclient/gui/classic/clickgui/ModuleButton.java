package com.wms.abstractclient.gui.classic.clickgui;

import com.wms.abstractclient.AbstractClient;
import com.wms.abstractclient.font.FontUtil;
import com.wms.abstractclient.gui.classic.clickgui.settingButtons.*;
import com.wms.abstractclient.module.Module;
import com.wms.abstractclient.setting.Setting;
import com.wms.abstractclient.util.GuiUtil;
import net.minecraft.client.gui.GuiScreen;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ModuleButton extends GuiScreen {

    public Frame frame;
    public Module module;
    public float offset;
    public boolean visible;
    public List<SettingComponent> settings;

    public ModuleButton(Frame frame, Module module, int offset)   {
        this.frame = frame;
        this.module = module;
        this.offset = offset;
        visible = false;
        settings = new ArrayList<>();

        //Settings

        int setOffset  = (int) Sizing.HEIGHT;

        settings.add(new DisplayedButton(null,this,setOffset));
        setOffset += Sizing.HEIGHT;

        for(Setting s : module.getSettings()){

            if(s.getType() == Setting.Type.BOOLEAN){
                settings.add(new BooleanButton(s,this, (int) setOffset));
            }else if(s.getType() == Setting.Type.NUMBER){
                settings.add(new NumberButton(s,this, (int) setOffset));
            }else if(s.getType() == Setting.Type.OPTION){
                settings.add(new OptionButton(s,this, (int) setOffset));
            }
            setOffset += Sizing.HEIGHT;
        }
        settings.add(new BindButton(null,this,setOffset));
        setOffset += Sizing.HEIGHT;

    }
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {


        this.drawGradientRect(frame.x, (int) (frame.y + offset), frame.x + (int) Sizing.WIDTH, (int) (frame.y + offset + Sizing.HEIGHT),module.isEnabled()  ? AbstractClient.unicolour.getColor().getRGB() : new Color(5, 5, 5, 230).getRGB(), new Color(20, 20, 20, 230).getRGB());


        FontUtil.drawStringWithShadow(module.getName(),frame.x + 2,frame.y + offset + 2, -1);
        if(module.getSettings().size() > 0) {
            FontUtil.drawStringWithShadow(this.visible ? "-" : "+", frame.x + (int) Sizing.WIDTH - 10, (int) (frame.y + offset) + 2, AbstractClient.unicolour.getColor().getRGB());
        }
        //Settings
        if(visible){
            for (SettingComponent setting : settings) {
                setting.drawScreen(mouseX,mouseY,partialTicks);
            }
        }

    }
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        boolean isHovered = GuiUtil.hovered(frame.x , frame.y, (int)Sizing.WIDTH, (int) Sizing.HEIGHT,mouseX,mouseY);
        if(visible){
            for (SettingComponent setting : settings) {
                setting.mouseClicked(mouseX,mouseY,mouseButton);
            }
        }
        if(hovered(mouseX,mouseY)){
            if(mouseButton == 0){
                module.toggle();
            }else if(mouseButton == 1){
                visible = !visible;
                frame.updateButtons();
            }
        }
    }


    public void mouseReleased(int mouseX, int mouseY, int state) {
        for (SettingComponent setting : settings) {
            setting.mouseReleased(mouseX,mouseY,state);
        }
    }


    public void onKey(int keyCode) {
        for (SettingComponent setting : settings) {
            setting.onKey(keyCode);
        }
    }

    public boolean hovered(int mouseX , int mouseY ) {
        return mouseX > frame.x && mouseX < frame.x + Sizing.WIDTH && mouseY > frame.y+offset && mouseY < frame.y + offset + Sizing.HEIGHT;
    }
}
