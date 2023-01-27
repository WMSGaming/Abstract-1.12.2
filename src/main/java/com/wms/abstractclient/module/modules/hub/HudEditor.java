package com.wms.abstractclient.module.modules.hub;

import com.wms.abstractclient.AbstractClient;
import com.wms.abstractclient.font.FontUtil;
import com.wms.abstractclient.manager.FriendManager;
import com.wms.abstractclient.module.Module;
import com.wms.abstractclient.setting.Setting;
import com.wms.abstractclient.util.ColourUtil;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.Comparator;
import java.util.Objects;

public class HudEditor extends Module {

    Setting watermark = new Setting(this,"Watermark",true);
    Setting arraylist = new Setting(this,"ModuleList",false);
    Setting friends = new Setting(this,"Friends",false);
    Setting coordinates = new Setting(this,"Coordinates",false);

    public HudEditor() {
        super("HudEditor", Category.HUB, Keyboard.KEY_NONE);
        addSetting(watermark);
        addSetting(arraylist);
        addSetting(friends);
        addSetting(coordinates);
    }


    @Override
    public void onRender() {
        ScaledResolution sr = new ScaledResolution(mc);

        if(watermark.isEnabled()){
            FontUtil.drawStringWithShadow(AbstractClient.MOD_NAME + "§F" + AbstractClient.VERSION,2 ,2  ,AbstractClient.unicolour.getColor().getRGB());
        }

        if (friends.isEnabled()){
            int y = 20;

            FontUtil.drawStringWithShadow("Friends:" ,2,y,AbstractClient.unicolour.getColor().getRGB());
            y+=FontUtil.getFontHeight();

            for(EntityPlayer friend : mc.world.playerEntities){
                if (FriendManager.isFriend(friend)) {
                    FontUtil.drawStringWithShadow(friend.getName() + "§F [" + (int)mc.player.getDistance(friend) +"]",2,y,AbstractClient.unicolour.getColor().getRGB());
                    y+=FontUtil.getFontHeight();
                }
            }
        }

        if(coordinates.isEnabled()){

            String netherCoords = " [" + (int) (mc.player.posX * 8) + ", " + (int) (mc.player.posZ * 8) + "]";
            String normalCoords = (int)mc.player.posX + ", " + (int) mc.player.posY + ", " + (int) mc.player.posZ;
            String coords =  "XYZ: §F" + (mc.player.dimension == 0 ? (normalCoords + netherCoords) :  mc.player.dimension == 1 ?(netherCoords + normalCoords) : (normalCoords + netherCoords));

            FontUtil.drawStringWithShadow(coords , 2,(sr.getScaledHeight())-FontUtil.getFontHeight(), AbstractClient.unicolour.getColor().getRGB());
        }
        if(arraylist.isEnabled()){
            moduleList();
        }
    }

    public String getPing(){
        try {
            return String.valueOf(Objects.requireNonNull(mc.getConnection()).getPlayerInfo(mc.getConnection().getGameProfile().getId()).getResponseTime());
        }catch (Exception e){
            return "?";
        }
    }

    public void moduleList() {
        ScaledResolution sr = new ScaledResolution(mc);
        int yCoord = 0;

        AbstractClient.moduleManager.getModules().sort(Comparator.comparingDouble((Module module) -> FontUtil.getStringWidth(module.getName())).reversed());
        for (Module m : AbstractClient.moduleManager.getModules()) {
            if (m.isEnabled() && m.getDisplayed()) {
                FontUtil.drawStringWithShadow(m.getName(), sr.getScaledWidth() - FontUtil.getStringWidth(m.getName()), yCoord + 1, AbstractClient.unicolour.getColor().getRGB());
                yCoord += FontUtil.getFontHeight() - 2;
            }
        }
    }
}
