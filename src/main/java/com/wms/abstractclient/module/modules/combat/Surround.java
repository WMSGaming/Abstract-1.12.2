package com.wms.abstractclient.module.modules.combat;

import com.wms.abstractclient.AbstractClient;
import com.wms.abstractclient.manager.RotationManager;
import com.wms.abstractclient.module.Module;
import com.wms.abstractclient.setting.Setting;
import com.wms.abstractclient.util.*;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.input.Keyboard;

public class Surround extends Module {

    Setting rotate = new Setting(this,"Rotate",false);
    Setting center = new Setting(this,"Center",false);

    Setting delay = new Setting(this,"Delay",0.3,0,1,1);

    TimerUtil timer = new TimerUtil();

    public Surround() {
        super("Surround", Category.COMBAT, Keyboard.KEY_NONE);
        addSetting(rotate);
        addSetting(center);
        addSetting(delay);
    }

    @Override
    public void onEnable() {
        if(nullcheck()){return;}

        if(center.isEnabled()){
            BlockPos pos = CombatUtil.getPosByFloor();
            mc.player.connection.sendPacket(new CPacketPlayer.Position(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, true));
            mc.player.setPosition(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
        }
    }

    @Override
    public void onTick() {
        if(nullcheck()){return;}


        if(mc.player.onGround) {
            BlockPos pos = CombatUtil.getPosByFloor();


            if(!isBlockPlaceable(pos.north())) {
                placeBlock(pos.north());
            }

            if(!isBlockPlaceable(pos.east())) {
                placeBlock(pos.east());
            }

            if(!isBlockPlaceable(pos.south())) {
                placeBlock(pos.south());
            }

            if(!isBlockPlaceable(pos.west())) {
                placeBlock(pos.west());
            }

            mc.player.connection.sendPacket(new CPacketHeldItemChange(mc.player.inventory.currentItem));

        }else {
            toggle();
        }

    }
    public void placeBlock(BlockPos pos){
        if(InventoryUtil.findHotbarBlock(Blocks.OBSIDIAN) != -1) {
            if (timer.hasTimeElapsed((long) (delay.getValue() * 100), true)) {
                mc.player.connection.sendPacket(new CPacketHeldItemChange(InventoryUtil.findHotbarBlock(Blocks.OBSIDIAN)));
                BlockUtil.placeBlock(pos, rotate.isEnabled(), EnumHand.MAIN_HAND);
            }
        }
    }

    @Override
    public void onDisable() {
        if(nullcheck()){return;}
    }

    public boolean isBlockPlaceable(BlockPos blockpos) {
        return mc.world.getBlockState(blockpos).getBlock() ==  Blocks.OBSIDIAN  || mc.world.getBlockState(blockpos).getBlock() ==  Blocks.BEDROCK;
    }
}
