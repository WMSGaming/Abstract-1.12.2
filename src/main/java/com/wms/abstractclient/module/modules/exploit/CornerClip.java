package com.wms.abstractclient.module.modules.exploit;

import com.wms.abstractclient.module.Module;
import net.minecraft.network.play.client.CPacketPlayer;
import org.lwjgl.input.Keyboard;

public class CornerClip extends Module {
    public CornerClip() {
        super("CornerClip", Category.EXPLOIT, Keyboard.KEY_NONE);
    }

    @Override
    public void onTick() {
        mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX,mc.player.posY - 0.0042123,mc.player.posZ,mc.player.onGround));
        mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX,mc.player.posY - 0.02141,mc.player.posZ,mc.player.onGround));
        mc.player.connection.sendPacket(new CPacketPlayer.PositionRotation(mc.player.posX,mc.player.posY - 0.097421,mc.player.posZ,500,500,mc.player.onGround));
        toggle();
    }
}
