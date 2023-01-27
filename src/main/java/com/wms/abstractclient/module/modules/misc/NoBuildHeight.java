package com.wms.abstractclient.module.modules.misc;

import com.wms.abstractclient.event.PacketSendEvent;
import com.wms.abstractclient.module.Module;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

public class NoBuildHeight extends Module {
    public NoBuildHeight() {
        super("BuildHeightBypass", Category.MISC, Keyboard.KEY_NONE);
    }

    @SubscribeEvent
    public void onPacket(PacketSendEvent event){
        if(event.getPacket() instanceof CPacketPlayerTryUseItemOnBlock){
            CPacketPlayerTryUseItemOnBlock useItem = (CPacketPlayerTryUseItemOnBlock) event.getPacket();
            if(useItem.getPos().getY() >= 255 && useItem.getDirection() == EnumFacing.UP){
                useItem.placedBlockDirection = EnumFacing.DOWN;
            }
        }
    }
}
