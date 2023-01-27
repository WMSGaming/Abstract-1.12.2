package com.wms.abstractclient.module.modules.combat;

import com.wms.abstractclient.module.Module;
import com.wms.abstractclient.setting.Setting;
import net.minecraft.init.Items;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.input.Keyboard;

public class AutoBowRelease extends Module {

    Setting ticks = new Setting(this,"Ticks",5,1,20,1);

    public AutoBowRelease() {
        super("AutoBowRelease", Category.COMBAT, Keyboard.KEY_NONE);
        addSetting(ticks);
    }

    @Override
    public void onTick() {
        if(nullcheck()){return;}
        if(mc.player.getHeldItemMainhand().getItem() == Items.BOW  && mc.player.isHandActive() && mc.player.getItemInUseMaxCount() > ticks.getValue()){
            mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, mc.player.getHorizontalFacing()));
            mc.player.stopActiveHand();
        }
    }
}
