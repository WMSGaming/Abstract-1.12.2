package com.wms.abstractclient.util;

import com.wms.abstractclient.manager.RotationManager;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.List;

public class BlockUtil {

    public static Minecraft mc = Minecraft.getMinecraft();

    public static boolean isSurround(EntityPlayer p){
        BlockPos bloc = CombatUtil.getEnemyPosByFloor(p);
        return mc.world.getBlockState(bloc.east()).getBlock() != Blocks.AIR
                && mc.world.getBlockState(bloc.west()).getBlock() != Blocks.AIR
                && mc.world.getBlockState(bloc.north()).getBlock() != Blocks.AIR
                && mc.world.getBlockState(bloc.south()).getBlock() != Blocks.AIR;
    }
    public static List<EnumFacing> getPossibleSides(BlockPos pos) {
        List<EnumFacing> facings = new ArrayList<>();
        for (EnumFacing side : EnumFacing.values()) {
            BlockPos neighbour = pos.offset(side);
            if(mc.world.getBlockState(neighbour) == null)return facings;
            if(mc.world.getBlockState(neighbour).getBlock() == null)return facings;
            if (mc.world.getBlockState(neighbour).getBlock().canCollideCheck(mc.world.getBlockState(neighbour), false)) {
                IBlockState blockState = mc.world.getBlockState(neighbour);
                if (!blockState.getMaterial().isReplaceable()) {
                    facings.add(side);
                }
            }
        }
        return facings;
    }

    public static EnumFacing getFirstFacing(BlockPos pos) {
        for (EnumFacing facing : getPossibleSides(pos)) {
            return facing;
        }
        return null;
    }
    public static void rightClickBlock(BlockPos pos, Vec3d vec, EnumFacing direction, EnumHand hand) {
        float f = (float) (vec.x - (double) pos.getX());
        float f1 = (float) (vec.y - (double) pos.getY());
        float f2 = (float) (vec.z - (double) pos.getZ());
        mc.player.connection.sendPacket(new CPacketPlayerTryUseItemOnBlock(pos, direction, hand, f, f1, f2));
    }
    public static boolean placeBlock(BlockPos pos, boolean rotate, EnumHand hand) {
        EnumFacing side = getFirstFacing(pos);
        if (side == null) {
            return false;
        }

        BlockPos neighbour = pos.offset(side);
        EnumFacing opposite = side.getOpposite();

        Vec3d hitVec = new Vec3d(neighbour).add(0.5, 0.5, 0.5).add(new Vec3d(opposite.getDirectionVec()).scale(0.5));


        if (!mc.player.isSneaking()) {
            mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_SNEAKING));
            mc.player.setSneaking(true);
        }
        if(rotate){
            mc.player.connection.sendPacket(new CPacketPlayer.Rotation(RotationUtil.getRotationsBlock(pos,opposite,true)[0], RotationUtil.getRotationsBlock(pos,opposite,true)[1], mc.player.onGround));
        }

        rightClickBlock(neighbour, hitVec, opposite, hand);
        mc.player.swingArm(EnumHand.MAIN_HAND);

        mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
        mc.player.setSneaking(false);
        if(rotate) {
            //RotationManager.reset();
        }
        return true;
    }
}
