package com.wms.abstractclient.module.modules.movement;

import com.wms.abstractclient.AbstractClient;
import com.wms.abstractclient.event.PacketSendEvent;
import com.wms.abstractclient.module.Module;
import com.wms.abstractclient.setting.Setting;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

import java.util.Arrays;

public class NoFall extends Module {

    Setting mode = new Setting(this,"Mode", Arrays.asList("New","Old"));

    public NoFall() {
        super("NoFall", Category.MOVEMENT, Keyboard.KEY_NONE);
        addSetting(mode);
    }

    @Override
    public void onTick() {
        if(nullcheck()){return;}
        if(mc.player.fallDistance >= 5){
            if(mode.getEnumValue().equalsIgnoreCase("Old")) {
                mc.player.connection.sendPacket(new CPacketPlayer(true));
            }
        }
    }
    @SubscribeEvent
    public void onPacket(PacketSendEvent event){
        if(event.getPacket() instanceof CPacketPlayer.Position || event.getPacket() instanceof CPacketPlayer.PositionRotation){
            CPacketPlayer player = (CPacketPlayer) event.getPacket();
            if(mode.getEnumValue().equalsIgnoreCase("New")) {
                player.onGround = true;
            }
        }
    }
}
