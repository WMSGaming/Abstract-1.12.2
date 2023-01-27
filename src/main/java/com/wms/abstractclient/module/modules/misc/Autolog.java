package com.wms.abstractclient.module.modules.misc;

import com.wms.abstractclient.module.Module;
import com.wms.abstractclient.setting.Setting;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketDisconnect;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import org.lwjgl.input.Keyboard;

public class Autolog extends Module {

    Setting health = new Setting(this,"Health",10,10,20,1);

    public Autolog() {
        super("Autolog", Category.MISC, Keyboard.KEY_NONE);
        addSetting(health);
    }

    @Override
    public void onTick() {
        if(nullcheck()) return;

        if(mc.player.getHealth() < health.getValue() && !mc.player.isCreative()){
            mc.player.connection.sendPacket(new SPacketDisconnect(new TextComponentString("Automatically logged out due to low health")));
            toggle();
        }
    }
}
