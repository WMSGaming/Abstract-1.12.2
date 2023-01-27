package com.wms.abstractclient.module.modules.combat;

import com.wms.abstractclient.module.Module;
import com.wms.abstractclient.setting.Setting;
import com.wms.abstractclient.util.BlockUtil;
import com.wms.abstractclient.util.CombatUtil;
import com.wms.abstractclient.util.InventoryUtil;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.input.Keyboard;

public class SelfWeb extends Module {

    Setting rot = new Setting(this,"Rotate", true);

    public SelfWeb() {
        super("SelfWeb", Category.COMBAT, Keyboard.KEY_NONE);
        addSetting(rot);
    }

    @Override
    public void onTick() {
        if(nullcheck()){return;}
        if(!mc.player.isInWeb){
            if(mc.player.onGround){
                if(InventoryUtil.findHotbarBlock(Blocks.WEB) != -1){
                    BlockPos pos = CombatUtil.getPosByFloor();
                    mc.player.connection.sendPacket(new CPacketHeldItemChange(InventoryUtil.findHotbarBlock(Blocks.WEB)));
                    BlockUtil.placeBlock(pos,rot.isEnabled(), EnumHand.MAIN_HAND);
                    mc.player.connection.sendPacket(new CPacketHeldItemChange(mc.player.inventory.currentItem));
                    toggle();
                }else{
                    sendMsg("No webs in hotbar!");
                    toggle();
                }
            }
        }
    }
}
