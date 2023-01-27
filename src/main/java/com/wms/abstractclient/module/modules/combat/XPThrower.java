package com.wms.abstractclient.module.modules.combat;

import com.wms.abstractclient.AbstractClient;
import com.wms.abstractclient.module.Module;
import com.wms.abstractclient.setting.Setting;
import com.wms.abstractclient.util.InventoryUtil;
import net.minecraft.init.Items;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.util.EnumHand;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class XPThrower extends Module {

    Setting rotate = new Setting(this,"Rotate",false);

    public XPThrower() {
        super("XPThrower", Category.COMBAT, Keyboard.KEY_NONE);
        addSetting(rotate);
    }

    @Override
    public void onTick() {
        if(nullcheck()){return;}
        if (Mouse.isButtonDown((int)2)) {
            if(rotate.isEnabled()){
                AbstractClient.rotationManager.setRotations(mc.player.getRotationYawHead(),90);
            }

            if(InventoryUtil.findHotbarItem(Items.EXPERIENCE_BOTTLE) != -1){
                mc.player.connection.sendPacket(new CPacketHeldItemChange(InventoryUtil.findHotbarItem(Items.EXPERIENCE_BOTTLE)));
                mc.playerController.processRightClick(mc.player,mc.world, EnumHand.MAIN_HAND);
            }

            mc.player.connection.sendPacket(new CPacketHeldItemChange(mc.player.inventory.currentItem));
        }
    }
}
