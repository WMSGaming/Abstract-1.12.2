package com.wms.abstractclient.util;

import com.wms.abstractclient.manager.FriendManager;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.potion.Potion;
import net.minecraft.util.CombatRules;
import net.minecraft.util.DamageSource;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Explosion;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CombatUtil {
    public static Minecraft mc = Minecraft.getMinecraft();

    public static BlockPos getPosByFloor(){
        return new BlockPos(Math.floor(mc.player.posX), Math.floor(mc.player.posY), Math.floor(mc.player.posZ));
    }

    public static EntityPlayer getClosestPlayer(float maxRange) {
        List<EntityPlayer> closestPlayer = (List<EntityPlayer>) mc.world.playerEntities.stream().filter(player -> player.getDistance(mc.player) < maxRange && !FriendManager.isFriend(player) && player != mc.player && player.isEntityAlive()).collect(Collectors.toList());
        closestPlayer.sort(Comparator.comparingDouble(player -> player.getDistanceSq(mc.player)));
        if(!closestPlayer.isEmpty()) {
            return closestPlayer.get(0);
        }else{
            return null;
        }
    }

    public static BlockPos getEnemyPosByFloor(EntityPlayer p) {
        return new BlockPos(Math.floor(p.posX), Math.floor(p.posY), Math.floor(p.posZ));
    }

    public static boolean isNotVisible(BlockPos position, double offset) {
        if (offset > 50 || offset < -50) {
            return false;
        }
        return mc.world.rayTraceBlocks(new Vec3d(mc.player.posX, mc.player.posY + mc.player.getEyeHeight(), mc.player.posZ), new Vec3d(position.getX() + 0.5, position.getY() + offset, position.getZ() + 0.5), false, true, false) != null;
    }
    public static List<BlockPos> getSphere(BlockPos loc, float r, int h, boolean hollow, boolean sphere, int plus_y) {
        List<BlockPos> circleblocks = new ArrayList<>();
        int cx = loc.getX();
        int cy = loc.getY();
        int cz = loc.getZ();
        for (int x = cx - (int) r; x <= cx + r; x++) {
            for (int z = cz - (int) r; z <= cz + r; z++) {
                for (int y = (sphere ? cy - (int) r : cy); y < (sphere ? cy + r : cy + h); y++) {
                    double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? (cy - y) * (cy - y) : 0);
                    if (dist < r * r && !(hollow && dist < (r - 1) * (r - 1))) {
                        BlockPos l = new BlockPos(x, y + plus_y, z);
                        circleblocks.add(l);
                    }
                }
            }
        }
        return circleblocks;
    }
}
