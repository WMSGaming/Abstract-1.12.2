package com.wms.abstractclient.module.modules.combat;

import com.wms.abstractclient.AbstractClient;
import com.wms.abstractclient.module.Module;
import com.wms.abstractclient.setting.Setting;
import com.wms.abstractclient.util.BlockUtil;
import com.wms.abstractclient.util.CombatUtil;
import com.wms.abstractclient.util.TimerUtil;
import javafx.util.Pair;
import jdk.nashorn.internal.ir.Block;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.potion.Potion;
import net.minecraft.tileentity.TileEntityBed;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Explosion;
import org.lwjgl.input.Keyboard;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class BedAura extends Module {

    Setting bedDelay = new Setting(this,"BedDelay",2,0,10,1);
    Setting range = new Setting(this,"Range",5,3,6,1);
    Setting minDmg = new Setting(this,"MinDmg",5,3,6,1);
    private TimerUtil timer = new TimerUtil();

    public BedAura() {
        super("BedAura", Category.COMBAT, Keyboard.KEY_NONE);
        addSetting(bedDelay);
        addSetting(range);
        addSetting(minDmg);
    }

    @Override
    public void onTick() {
        if(nullcheck()){return;}

        if(CombatUtil.getClosestPlayer(10) != null){
            if(mc.player.getHeldItemMainhand().getItem() == Items.BED) {
                placeBed(CombatUtil.getClosestPlayer(10), EnumHand.MAIN_HAND);
                breakBed(CombatUtil.getClosestPlayer(10), EnumHand.MAIN_HAND);
            }else if(mc.player.getHeldItemOffhand().getItem() == Items.BED){
                placeBed(CombatUtil.getClosestPlayer(10), EnumHand.OFF_HAND);
                breakBed(CombatUtil.getClosestPlayer(10), EnumHand.OFF_HAND);
            }
        }else {
            AbstractClient.rotationManager.reset();
        }
    }

    @Override
    public void onDisable() {
        AbstractClient.rotationManager.reset();
    }

    public void breakBed(EntityPlayer target, EnumHand hand){
        TileEntityBed bed = mc.world.loadedTileEntityList.stream()
                .filter(entity -> entity instanceof TileEntityBed)
                .map(entity -> (TileEntityBed) entity)
                .min(Comparator.comparing(c -> target.getDistance(c.getPos().getX(),c.getPos().getY(),c.getPos().getZ())))
                .orElse(null);
        if(bed != null) {
            mc.player.connection.sendPacket(new CPacketPlayerTryUseItemOnBlock(bed.getPos(), EnumFacing.UP, hand , 0f, 0f, 0f));
            mc.player.swingArm(hand);
        }
    }

    public void placeBed(EntityPlayer target, EnumHand hand){
        //BlockPos bp = getBedPosition((float) range.getValue(),target, (float) minDmg.getValue(), (float) maxSelfDmg.getValue());
        BlockPos bp = CombatUtil.getEnemyPosByFloor(target);

        if(bp != null) {
            if(timer.hasTimeElapsed((long) (bedDelay.getValue() * 100), true)) {
                BlockPos north = bp.north();
                BlockPos east = bp.east();
                BlockPos south = bp.south();
                BlockPos west = bp.west();
                BlockPos northDown = bp.north().down();
                BlockPos eastDown = bp.east().down();
                BlockPos southDown = bp.south().down();
                BlockPos westDown = bp.west().down();

                if (mc.world.getBlockState(bp).getBlock() == Blocks.AIR && mc.world.getBlockState(bp.down()).getBlock() != Blocks.AIR
                        && mc.world.getBlockState(north).getBlock() == Blocks.AIR && mc.world.getBlockState(northDown).getBlock() != Blocks.AIR) {
                    rotateTaDirection(EnumFacing.NORTH);
                    BlockUtil.placeBlock(bp,false, hand);
                    mc.player.swingArm(hand);
                    return;
                }

                if (mc.world.getBlockState(bp).getBlock() == Blocks.AIR && mc.world.getBlockState(bp.down()).getBlock() != Blocks.AIR
                        && mc.world.getBlockState(east).getBlock() == Blocks.AIR && mc.world.getBlockState(eastDown).getBlock() != Blocks.AIR) {
                    rotateTaDirection(EnumFacing.EAST);
                    BlockUtil.placeBlock(bp,false, hand);
                    mc.player.swingArm(hand);
                    return;
                }

                if (mc.world.getBlockState(bp).getBlock() == Blocks.AIR && mc.world.getBlockState(bp.down()).getBlock() != Blocks.AIR
                        && mc.world.getBlockState(south).getBlock() == Blocks.AIR && mc.world.getBlockState(southDown).getBlock() != Blocks.AIR) {
                    rotateTaDirection(EnumFacing.SOUTH);
                    BlockUtil.placeBlock(bp,false, hand);
                    mc.player.swingArm(hand);
                    return;
                }
                if (mc.world.getBlockState(bp).getBlock() == Blocks.AIR && mc.world.getBlockState(bp.down()).getBlock() != Blocks.AIR
                        && mc.world.getBlockState(west).getBlock() == Blocks.AIR && mc.world.getBlockState(westDown).getBlock() != Blocks.AIR) {
                    rotateTaDirection(EnumFacing.WEST);
                    BlockUtil.placeBlock(bp,false, hand);
                    mc.player.swingArm(hand);
                }
            }
        }
    }
    public void rotateTaDirection(EnumFacing facing){
        AbstractClient.rotationManager.setRotations(facing.getHorizontalAngle(), mc.player.rotationPitch);
    }

    public BlockPos getBedPosition(float range, EntityPlayer player, float minDmg, float maxSelfDmg){
        return findBedBlocks((float) range).stream()
                .filter(pos -> calcDmg(pos,player) > minDmg && calcDmg(pos,mc.player) < (mc.player.getHealth() + mc.player.getAbsorptionAmount()))
                .max(Comparator.comparing(pos -> calcDmg(pos,player))).orElse(null);
    }

    public List<BlockPos> findBedBlocks(float range) {
        NonNullList<BlockPos> positions = NonNullList.create();
        positions.addAll(getSphere(CombatUtil.getPosByFloor(), range, (int) range, true, true, 0).stream().filter(this::canPlaceBed).collect(Collectors.toList()));
        return positions;
    }

    public boolean canPlaceBed(BlockPos bp){
        BlockPos north = bp.north();
        BlockPos east = bp.east();
        BlockPos south = bp.south();
        BlockPos west = bp.west();
        BlockPos northDown = bp.north().down();
        BlockPos eastDown = bp.east().down();
        BlockPos southDown = bp.south().down();
        BlockPos westDown = bp.west().down();

        if(mc.world.getBlockState(bp).getBlock() == Blocks.AIR && mc.world.getBlockState(bp.down()).getBlock() != Blocks.AIR
        && mc.world.getBlockState(north).getBlock() == Blocks.AIR && mc.world.getBlockState(northDown).getBlock() != Blocks.AIR){
            return true;
        }
        if(mc.world.getBlockState(bp).getBlock() == Blocks.AIR && mc.world.getBlockState(bp.down()).getBlock() != Blocks.AIR
                && mc.world.getBlockState(east).getBlock() == Blocks.AIR && mc.world.getBlockState(eastDown).getBlock() != Blocks.AIR){
            return true;
        }
        if(mc.world.getBlockState(bp).getBlock() == Blocks.AIR && mc.world.getBlockState(bp.down()).getBlock() != Blocks.AIR
                && mc.world.getBlockState(south).getBlock() == Blocks.AIR && mc.world.getBlockState(southDown).getBlock() != Blocks.AIR){
            return true;
        }
        if(mc.world.getBlockState(bp).getBlock() == Blocks.AIR && mc.world.getBlockState(bp.down()).getBlock() != Blocks.AIR
                && mc.world.getBlockState(west).getBlock() == Blocks.AIR && mc.world.getBlockState(westDown).getBlock() != Blocks.AIR){
            return true;
        }
        return false;
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
    public static float calcDmg(BlockPos p, EntityPlayer entity) {
        return calculateDamage(p.getX() + .5,p.getY()+1,p.getZ()+ .5,entity);
    }

    public static float calculateDamage(double posX, double posY, double posZ, Entity entity) {
        float doubleExplosionSize = 6.0F * 2.0F;
        double distancedsize = entity.getDistance(posX, posY, posZ) / (double) doubleExplosionSize;
        Vec3d vec3d = new Vec3d(posX, posY, posZ);
        double blockDensity = (double) entity.world.getBlockDensity(vec3d, entity.getEntityBoundingBox());
        double v = (1.0D - distancedsize) * blockDensity;
        float damage = (float) ((int) ((v * v + v) / 2.0D * 7.0D * (double) doubleExplosionSize + 1.0D));
        double finald = 1;
        if (entity instanceof EntityLivingBase) {
            finald = getBlastReduction((EntityLivingBase) entity, getDamageMultiplied(damage), new Explosion(mc.world, null, posX, posY, posZ, 6F, false, true));
        }
        return (float) finald;
    }

    public static float getBlastReduction(EntityLivingBase entity, float damage, Explosion explosion) {
        if (entity instanceof EntityPlayer) {
            EntityPlayer ep = (EntityPlayer) entity;
            DamageSource ds = DamageSource.causeExplosionDamage(explosion);
            damage = CombatRules.getDamageAfterAbsorb(damage, (float) ep.getTotalArmorValue(), (float) ep.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue());

            int k = EnchantmentHelper.getEnchantmentModifierDamage(ep.getArmorInventoryList(), ds);
            float f = MathHelper.clamp(k, 0.0F, 20.0F);
            damage = damage * (1.0F - f / 25.0F);

            if (entity.isPotionActive(Potion.getPotionById(11))) {
                damage = damage - (damage / 4);
            }

            damage = Math.max(damage - ep.getAbsorptionAmount(), 0.0F);
            return damage;
        }
        damage = CombatRules.getDamageAfterAbsorb(damage, (float) entity.getTotalArmorValue(), (float) entity.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue());
        return damage;
    }

    private static float getDamageMultiplied(float damage) {
        int diff = mc.world.getDifficulty().getId();
        return damage * (diff == 0 ? 0 : (diff == 2 ? 1 : (diff == 1 ? 0.5f : 1.5f)));
    }
}