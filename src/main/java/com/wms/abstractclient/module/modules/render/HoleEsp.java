package com.wms.abstractclient.module.modules.render;

import com.wms.abstractclient.AbstractClient;
import com.wms.abstractclient.module.Module;
import com.wms.abstractclient.setting.Setting;
import com.wms.abstractclient.util.CombatUtil;
import com.wms.abstractclient.util.RenderUtil;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.input.Keyboard;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class HoleEsp extends Module {


    public static final List<Block> unSafeBlocks = Arrays.asList(Blocks.OBSIDIAN, Blocks.BEDROCK, Blocks.ENDER_CHEST, Blocks.ANVIL);

    Setting unsafeRed = new Setting(this,"UnsafeRed",255,0,255,1);
    Setting unsafeGreen = new Setting(this,"UnsafeGreen",255,0,255,1);
    Setting unsafeBlue = new Setting(this,"UnsafeBlue",255,0,255,1);
    Setting range = new Setting(this,"Range",5,1,15,1);


    public HoleEsp() {
        super("HoleEsp", Category.RENDER, Keyboard.KEY_NONE);
        addSetting(unsafeRed);
        addSetting(unsafeGreen);
        addSetting(unsafeBlue);
        addSetting(range);
    }

    @Override
    public void onRender3D() {
        Color uni = AbstractClient.unicolour.getColor();
        for(BlockPos bp :  getSphere(range.getValue(),AirType.OnlyAir ,mc.player)){

            if(safeHole(bp)){
                //Render Safest holes (typically 'green holes')
                renderBox(bp, new Color(uni.getRed(),uni.getGreen(),uni.getBlue(),25));
            } else if(unsafeHole(bp)){
                //Render semi-Safest holes (typically 'red holes')
                renderBox(bp,new Color((int) unsafeRed.getValue(), (int)unsafeGreen.getValue(), (int)unsafeBlue.getValue(), 25));
            }
        }

    }
    public boolean isBlockUnSafe(BlockPos block) {
        Block b = mc.world.getBlockState(block).getBlock();
        return unSafeBlocks.contains(b);
    }
    public boolean safeHole(BlockPos p) {
        return mc.world.getBlockState(p).getBlock() == Blocks.AIR &&
                mc.world.getBlockState(p.up()).getBlock() == Blocks.AIR &&
                mc.world.getBlockState(p.down()).getBlock() == Blocks.BEDROCK &&
                mc.world.getBlockState(p.north()).getBlock() == Blocks.BEDROCK &&
                mc.world.getBlockState(p.east()).getBlock() == Blocks.BEDROCK &&
                mc.world.getBlockState(p.south()).getBlock() == Blocks.BEDROCK &&
                mc.world.getBlockState(p.west()).getBlock() == Blocks.BEDROCK;

    }
    public boolean unsafeHole(BlockPos p) {
        return mc.world.getBlockState(p).getBlock() == Blocks.AIR &&
                mc.world.getBlockState(p.up()).getBlock() == Blocks.AIR &&
                isBlockUnSafe(p.down()) &&
                isBlockUnSafe(p.north()) &&
                isBlockUnSafe(p.east()) &&
                isBlockUnSafe(p.south()) &&
                isBlockUnSafe(p.west());

    }

    public List<BlockPos> getSphere(final double radius,AirType airtype ,final EntityPlayer entityPlayer) {
        final ArrayList<BlockPos> sphere = new ArrayList<BlockPos>();
        final BlockPos pos = new BlockPos(entityPlayer.getPositionVector());
        final int posX = pos.getX();
        final int posY = pos.getY();
        final int posZ = pos.getZ();
        final int radiuss = (int)radius;
        for (int x = posX - radiuss; x <= posX + radius; ++x) {
            for (int z = posZ - radiuss; z <= posZ + radius; ++z) {
                for (int y = posY - radiuss; y < posY + radius; ++y) {
                    final double dist = (posX - x) * (posX - x) + (posZ - z) * (posZ - z) + (posY - y) * (posY - y);
                    if (dist < radius * radius) {
                        final BlockPos position = new BlockPos(x, y, z);
                        if (!mc.world.getBlockState(position).getBlock().equals(Blocks.AIR) || !airtype.equals(AirType.IgnoreAir)) {
                            if (mc.world.getBlockState(position).getBlock().equals(Blocks.AIR) || !airtype.equals(AirType.OnlyAir)) {
                                sphere.add(position);
                            }
                        }
                    }
                }
            }
        }
        return sphere;
    }
    public enum AirType	 {
        OnlyAir,
        IgnoreAir,
        None;
    }
    public void renderBox(BlockPos bp, Color c){
        RenderUtil.flatBoxEsp(bp,c);
    }
}
