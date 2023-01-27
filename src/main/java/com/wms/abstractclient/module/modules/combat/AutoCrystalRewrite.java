package com.wms.abstractclient.module.modules.combat;

import com.wms.abstractclient.AbstractClient;
import com.wms.abstractclient.manager.FriendManager;
import com.wms.abstractclient.module.Module;
import com.wms.abstractclient.setting.Setting;
import com.wms.abstractclient.util.*;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.potion.Potion;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Explosion;
import org.lwjgl.input.Keyboard;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
public class AutoCrystalRewrite extends Module {


    Setting switchMode = new Setting(this,"Switch", Arrays.asList("None","Normal","Silent"));
    Setting swing = new Setting(this,"Swing", Arrays.asList("MainHand","OffHand","Auto"));
    Setting logicMode = new Setting(this,"Logic", Arrays.asList("Place, Break","Break, Place"));
    Setting rotate = new Setting(this,"Rotate",true);
    Setting speed = new Setting(this,"Speed",3,0,50,1);
    Setting ticksExisted = new Setting(this,"TicksExisted",1,0,10,1);
    Setting range = new Setting(this,"Range",3,3,6,1);
    Setting wallRange = new Setting(this,"WallRange",3,3,6,1);
    Setting setDead = new Setting(this,"SetDead",false);
    Setting minDmg = new Setting(this,"MinDmg",1,0,15,1);
    Setting maxSelfDmg = new Setting(this,"MaxSelfDmg",1,0,36,1);
    Setting targetRange = new Setting(this,"TargetRange",3,3,15,1);
    Setting antiSuicide = new Setting(this,"AntiSuicide",true);
    Setting newPlacement = new Setting(this,"1.13+ place",false);
    Setting antiWeakness = new Setting(this,"AntiWeakness",true);
    Setting renderDmg = new Setting(this,"RenderDmg",true);

    private float targetDamage;

    private BlockPos renderPos;
    private final TimerUtil timer = new TimerUtil();

    public AutoCrystalRewrite() {
        super("AutoCrystalRewrite", Category.COMBAT, Keyboard.KEY_NONE);
        addSetting(switchMode);
        addSetting(swing);
        addSetting(logicMode);
        addSetting(rotate);
        addSetting(speed);
        addSetting(ticksExisted);
        addSetting(range);
        addSetting(wallRange);
        addSetting(setDead);
        addSetting(minDmg);
        addSetting(maxSelfDmg);
        addSetting(targetRange);
        addSetting(antiSuicide);
        addSetting(newPlacement);
        addSetting(antiWeakness);
        addSetting(renderDmg);
    }
    @Override
    public void onRender3D() {
        Color uni = new Color(AbstractClient.unicolour.getRed(),AbstractClient.unicolour.getGreen(),AbstractClient.unicolour.getBlue(),150);
        if(renderPos != null) {
            RenderUtil.drawBoxESP(renderPos, uni, 1,true,true,75);
            if(renderDmg.isEnabled()) {
                RenderUtil.drawText(renderPos, String.valueOf((int)targetDamage), true);
            }
        }
    }

    @Override
    public void onEnable() {
        if(nullcheck()){return;}
    }

    @Override
    public void onDisable() {
        if(nullcheck()){return;}
        renderPos = null;
        mc.player.connection.sendPacket(new CPacketHeldItemChange(mc.player.inventory.currentItem));
    }

    @Override
    public void onTick() {
        if(nullcheck()){return;}

        if(getClosestPlayer((float) targetRange.getValue()) != null){
            doLogic(getClosestPlayer((float) targetRange.getValue()));
        }
    }

    public void doLogic(EntityPlayer target){
        if(logicMode.getEnumValue().equalsIgnoreCase("Place, Break")){
            // Place then break
            doPlace(target);
            doBreak(target);

        }else if(logicMode.getEnumValue().equalsIgnoreCase("Break, Place")){
            // Break then place
            doBreak(target);
            doPlace(target);
        }
    }
    public void doPlace(EntityPlayer target){
        BlockPos bestPos = bestPosition((float)range.getValue(),target,(float)minDmg.getValue(),(float)maxSelfDmg.getValue()); // Making a variable for the best pos

        if(switchMode.getEnumValue().equalsIgnoreCase("Normal")){
            if(mc.player.getHeldItemMainhand().getItem() != Items.END_CRYSTAL && mc.player.getHeldItemOffhand().getItem() != Items.END_CRYSTAL){
                if(InventoryUtil.findHotbarItem(Items.END_CRYSTAL) != -1){
                    mc.player.inventory.currentItem = InventoryUtil.findHotbarItem(Items.END_CRYSTAL);
                }
            }
        }else if (switchMode.getEnumValue().equalsIgnoreCase("Silent")){
            if(mc.player.getHeldItemMainhand().getItem() != Items.END_CRYSTAL && mc.player.getHeldItemOffhand().getItem() != Items.END_CRYSTAL) {
                if (InventoryUtil.findHotbarItem(Items.END_CRYSTAL) != -1) {
                    mc.player.connection.sendPacket(new CPacketHeldItemChange(InventoryUtil.findHotbarItem(Items.END_CRYSTAL)));
                }
            }
        }


        if (bestPos != null) {
            renderPos = bestPos;
            if(antiSuicide.isEnabled() && calcDmg(bestPos,mc.player) > (mc.player.getHealth() + mc.player.getAbsorptionAmount())){return;} // Simple check so that we dont die lol
            placeCrystal(bestPos);

        }else {
            AbstractClient.rotationManager.reset();
        }
        if (switchMode.getEnumValue().equalsIgnoreCase("Silent")) {
            mc.player.connection.sendPacket(new CPacketHeldItemChange(mc.player.inventory.currentItem));
        }
    }

    public void doBreak(EntityPlayer target){
        if (timer.hasTimeElapsed((long) (1000 / speed.getValue()), false)) {
            breakCrystal(target);
            timer.rs();
        }
    }


    public void placeCrystal(BlockPos bp) {
        if(switchMode.getEnumValue().equalsIgnoreCase("Silent") || (mc.player.getHeldItemMainhand().getItem() == Items.END_CRYSTAL || mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL)) {

            if(rotate.isEnabled()) {
                float yaw = RotationUtil.getRotationsBlock(bp, EnumFacing.UP,true)[0];
                float pitch = RotationUtil.getRotationsBlock(bp, EnumFacing.UP,true)[1];

                AbstractClient.rotationManager.setRotations(yaw, pitch);

            }
            if(!switchMode.getEnumValue().equalsIgnoreCase("Silent")) {
                mc.player.connection.sendPacket(new CPacketPlayerTryUseItemOnBlock(bp, EnumFacing.UP, mc.player.getHeldItemMainhand().getItem() == Items.END_CRYSTAL ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND, 0f, 0f, 0f));
            }else {
                mc.player.connection.sendPacket(new CPacketPlayerTryUseItemOnBlock(bp, EnumFacing.UP, EnumHand.MAIN_HAND , 0f, 0f, 0f));
            }

            if(getCrystalHand() != null) {
                mc.player.swingArm(getCrystalHand());
            }


        }else {
            AbstractClient.rotationManager.reset();
        }
    }

    public void breakCrystal(EntityPlayer target){
        EntityEnderCrystal crystal = mc.world.loadedEntityList.stream()
                .filter(entity -> entity instanceof EntityEnderCrystal && checkWallBreak((EntityEnderCrystal) entity))
                .map(entity -> (EntityEnderCrystal) entity)
                .min(Comparator.comparing(c -> target.getDistance(c)))
                .orElse(null);

        if(crystal != null){
            if(mc.player.getDistance(crystal) <= range.getValue()) {
                float yaw = RotationUtil.getRotations(crystal)[0];
                float pitch = RotationUtil.getRotations(crystal)[1];

                if(rotate.isEnabled()) {
                    AbstractClient.rotationManager.setRotations(yaw, pitch);
                }

                if(antiSuicide.isEnabled() && calculateDamage(crystal, mc.player) >= (mc.player.getHealth() + mc.player.getAbsorptionAmount())) {return;} // Simple check so that we dont die lol

                if (crystal.ticksExisted >= ticksExisted.getValue()) {
                    mc.playerController.attackEntity(mc.player, crystal);
                    mc.player.swingArm(mc.player.getHeldItemMainhand().getItem() == Items.END_CRYSTAL ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND);
                    if (setDead.isEnabled() && mc.player.canEntityBeSeen(crystal)) {
                        crystal.setDead();
                    }
                }
            }
        }
    }
    public BlockPos bestPosition(float range, EntityPlayer player, float minDmg, float maxSelfDmg){
        return findCrystalBlocks((float) range).stream()
                .filter(pos -> calcDmg(pos,mc.player) < maxSelfDmg && (targetDamage=calcDmg(pos,player)) > minDmg && checkWallPlace(pos))
                .max(Comparator.comparing(pos -> calcDmg(pos,player))).orElse(null);
    }

    public boolean checkWallPlace(BlockPos pos){
        if(CombatUtil.isNotVisible(pos, 2.70000004768372)){
                if(mc.player.getDistance(pos.getX(),pos.getY(),pos.getZ()) < wallRange.getValue()){
                    return true;
                }else {
                    return false;
                }
        }else {
            return true;
        }
    }

    public boolean checkWallBreak(EntityEnderCrystal crystal){
        if(!mc.player.canEntityBeSeen(crystal)){
            if(mc.player.getDistance(crystal) < wallRange.getValue()){
                return true;
            }else{
                return false;
            }
        }else {
            return true;
        }
    }

    public EnumHand getCrystalHand(){
        if(swing.getEnumValue().equalsIgnoreCase("MainHand")){
            if(mc.player.getHeldItemMainhand().getItem() == Items.END_CRYSTAL){
                return EnumHand.MAIN_HAND;
            }
        }else   if(swing.getEnumValue().equalsIgnoreCase("OffHand")){
            if(mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL){
                return EnumHand.OFF_HAND;
            }
        }else   if(swing.getEnumValue().equalsIgnoreCase("Auto")){
            if(mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL) {
                return EnumHand.OFF_HAND;
            }else if(mc.player.getHeldItemMainhand().getItem() == Items.END_CRYSTAL) {
                return EnumHand.MAIN_HAND;
            }
        }
        return null;
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

    public static float calculateDamage(EntityEnderCrystal crystal, Entity entity) {
        return calculateDamage(crystal.posX, crystal.posY, crystal.posZ, entity);
    }

    public static BlockPos getPosByFloor(){
        return new BlockPos(Math.floor(mc.player.posX), Math.floor(mc.player.posY), Math.floor(mc.player.posZ));
    }
    public List<BlockPos> findCrystalBlocks(float range) {
        NonNullList<BlockPos> positions = NonNullList.create();
        positions.addAll(getSphere(getPosByFloor(), range, (int) range, false, true, 0).stream().filter(c -> canPlaceCrystal(c,true)).collect(Collectors.toList()));
        return positions;
    }

    private boolean canPlaceCrystal(BlockPos blockPos, boolean specialEntityCheck) {
        BlockPos boost = blockPos.add(0, 1, 0);
        BlockPos boost2 = blockPos.add(0, 2, 0);
        try {
            if (!this.newPlacement.isEnabled()) {
                if (mc.world.getBlockState(blockPos).getBlock() != Blocks.BEDROCK && mc.world.getBlockState(blockPos).getBlock() != Blocks.OBSIDIAN) {
                    return false;
                }
                if (mc.world.getBlockState(boost).getBlock() != Blocks.AIR || mc.world.getBlockState(boost2).getBlock() != Blocks.AIR) {
                    return false;
                }
                if (!specialEntityCheck) {
                    return mc.world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(boost)).isEmpty() && mc.world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(boost2)).isEmpty();
                }
                for (Entity entity : mc.world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(boost))) {
                    if (entity instanceof EntityEnderCrystal) continue;
                    return false;
                }
                for (Entity entity : mc.world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(boost2))) {
                    if (entity instanceof EntityEnderCrystal) continue;
                    return false;
                }
            } else {
                if (mc.world.getBlockState(blockPos).getBlock() != Blocks.BEDROCK && mc.world.getBlockState(blockPos).getBlock() != Blocks.OBSIDIAN) {
                    return false;
                }
                if (mc.world.getBlockState(boost).getBlock() != Blocks.AIR) {
                    return false;
                }
                if (!specialEntityCheck) {
                    return mc.world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(boost)).isEmpty();
                }
                for (Entity entity : mc.world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(boost))) {
                    if (entity instanceof EntityEnderCrystal) continue;
                    return false;
                }
            }
        } catch (Exception ignored) {
            return false;
        }
        return true;
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
}
