package com.wms.abstractclient.module.modules.combat;

import com.wms.abstractclient.AbstractClient;
import com.wms.abstractclient.manager.RotationManager;
import com.wms.abstractclient.module.Module;
import com.wms.abstractclient.module.modules.render.HoleEsp;
import com.wms.abstractclient.setting.Setting;
import com.wms.abstractclient.util.BlockUtil;
import com.wms.abstractclient.util.CombatUtil;
import com.wms.abstractclient.util.RotationUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Burrow extends Module {

    Setting mode = new Setting(this,"Mode",Arrays.asList("Normal","2B2TPvP"));
    Setting block = new Setting(this,"Block", Arrays.asList("Obby","EChest"));
    Setting rotate = new Setting(this,"Rotate",false);

    public Burrow() {
        super("Burrow", Category.COMBAT, Keyboard.KEY_NONE);
        addSetting(mode);
        addSetting(block);
        addSetting(rotate);
    }

    @Override
    public void onEnable() {
        if(nullcheck()){return;}

        if(mode.getEnumValue().equalsIgnoreCase("Normal")) {
            this.doBurrow();
        }else {
            this.bypassBurrow();
        }
        toggle();
    }


    public void doBurrow(){
        BlockPos pos = CombatUtil.getPosByFloor();
        mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.41999998688698D, mc.player.posZ, true));
        mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.7531999805211997D, mc.player.posZ, true));
        mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 1.00133597911214D, mc.player.posZ, true));
        mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 1.16610926093821D, mc.player.posZ, true));
        placeBlock(pos);
        mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 1.5f, mc.player.posZ, false));
        mc.player.connection.sendPacket(new CPacketHeldItemChange(mc.player.inventory.currentItem));
    }

    public void bypassBurrow(){
        BlockPos pos = CombatUtil.getPosByFloor();

        mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.41999998688698D, mc.player.posZ, true));
        mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.7531999805211997D, mc.player.posZ, true));
        mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 1.00133597911214D, mc.player.posZ, true));
        mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 1.16610926093821D, mc.player.posZ, true));
        placeBlock(pos);
        mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX + 5, mc.player.posY, mc.player.posZ, true));
        mc.player.connection.sendPacket(new CPacketHeldItemChange(mc.player.inventory.currentItem));

    }

    public void placeBlock(BlockPos pos){
        if(findHotbar() != -1) {
            mc.player.connection.sendPacket(new CPacketHeldItemChange(findHotbar()));
            mc.player.swingArm(EnumHand.MAIN_HAND);
            BlockUtil.placeBlock(pos, rotate.isEnabled(), EnumHand.MAIN_HAND);
        }
    }
    private int findHotbar() {
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = mc.player.inventory.getStackInSlot(i);
            if (stack.getItem() == Item.getItemFromBlock(block.getEnumValue().equalsIgnoreCase("Obby") ? Blocks.OBSIDIAN : Blocks.ENDER_CHEST)) {
                return i;
            }
        }
        return -1;
    }
}
