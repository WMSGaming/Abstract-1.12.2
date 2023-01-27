package com.wms.abstractclient.module.modules.misc;

import com.wms.abstractclient.module.Module;
import com.wms.abstractclient.util.InventoryUtil;
import net.minecraft.init.Items;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class MCPearl extends Module {
    public MCPearl() {
        super("MCPearl", Category.MISC, Keyboard.KEY_NONE);
    }

    @Override
    public void onUpdate(TickEvent.ClientTickEvent event) {
        if(nullcheck()){return;}
        if (Mouse.isButtonDown((int)2)) {
            if (InventoryUtil.findHotbarItem(Items.ENDER_PEARL) != -1) {
                mc.player.connection.sendPacket(new CPacketHeldItemChange(InventoryUtil.findHotbarItem(Items.ENDER_PEARL)));
                mc.playerController.processRightClick(mc.player, mc.world, EnumHand.MAIN_HAND);
                mc.player.connection.sendPacket(new CPacketHeldItemChange(mc.player.inventory.currentItem));
            }
        }
    }
}
