package com.wms.abstractclient.module;

import com.mojang.realmsclient.gui.ChatFormatting;
import com.wms.abstractclient.AbstractClient;
import com.wms.abstractclient.setting.Setting;
import com.wms.abstractclient.setting.SettingManager;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;
import java.util.List;

public class Module {

    public static Minecraft mc = Minecraft.getMinecraft();
    String name;
    int bind;
    Category category;
    boolean enabled, displayed;

    public Module(String name, Category category, int bind) {
        this.name = name;
        this.category = category;
        this.bind = bind;
        this.displayed = true;
    }

    public void onEnable() {}
    public void onDisable() {}
    public void onTick(){}
    public void onRender3D(){}
    public void onRender(){}

    public void addSetting(Setting setting) {
        SettingManager.addSetting(setting);
    }
    public void removeSetting(Setting setting) {
        SettingManager.removeSetting(setting);
    }

    public List<Setting> getSettings() {
        List<Setting> settings = new ArrayList<>();
        for(Setting s : AbstractClient.settingManager.settings) {
            if(s.getModule() == this) {
                settings.add(s);
            }
        }
        return settings;
    }
    @SubscribeEvent
    public void overlay(RenderGameOverlayEvent.Post event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.TEXT) {
            onRender();
        }
    }
    public Setting getSetting(String set){
        Setting setting = null;
        for(Setting s : this.getSettings()){
            if(s.getName().equalsIgnoreCase(set)){
                setting = s;
            }
        }
        return setting;
    }

    @SubscribeEvent
    public void onUpdate(final TickEvent.ClientTickEvent event)	{onTick();}

    @SubscribeEvent
    public void onRender(RenderWorldLastEvent e){onRender3D();}

    public boolean isEnabled(){
        return enabled;
    }

    public void enable() {
        setEnabled(true);
        onEnable();
        MinecraftForge.EVENT_BUS.register(this);

        if(AbstractClient.moduleManager.getModuleFromName("Notifications").isEnabled()){
            sendMsg(this.name + " has been enabled");
        }

    }

    public void disable() {
        setEnabled(false);
        onDisable();
        MinecraftForge.EVENT_BUS.unregister(this);

        if(AbstractClient.moduleManager.getModuleFromName("Notifications").isEnabled()){
            sendMsg(this.name + " has been disabled");
        }

    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void toggle() {
        if (isEnabled()) disable();
        else enable();
    }
    public boolean nullcheck(){
        return mc.player == null && mc.world == null;
    }

    public boolean getDisplayed(){
        return displayed;
    }

    public void setDisplayed(boolean displayed){
        this.displayed = displayed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBind() {
        return bind;
    }

    public void setBind(int bind) {
        this.bind = bind;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
    public void sendMsg(String msg){
        mc.player.sendMessage(new TextComponentString(ChatFormatting.BLUE + "[Abstract] "+ ChatFormatting.WHITE + msg));
    }
    public enum Category {
        COMBAT("Combat"),
        EXPLOIT("Exploit"),
        MOVEMENT("Movement"),
        MISC("Misc"),
        RENDER("Render"),
        CHAT("Chat"),
        HUB("Hub");

        public String name;
        Category(String name){
            this.name = name;
        }
    }
}
