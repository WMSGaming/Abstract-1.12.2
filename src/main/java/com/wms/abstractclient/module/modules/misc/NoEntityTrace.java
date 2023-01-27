package com.wms.abstractclient.module.modules.misc;

import com.wms.abstractclient.module.Module;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import org.lwjgl.input.Keyboard;

public class NoEntityTrace extends Module {
    public NoEntityTrace() {
        super("NoEntityTrace", Category.MISC, Keyboard.KEY_NONE);
    }
    private boolean focus = false;

    // Credits to Wurst-plus-three for this

    @Override
    public void onTick() {
        if(nullcheck())return;

        mc.world.loadedEntityList.stream()
                .filter(entity -> entity instanceof EntityLivingBase)
                .filter(entity -> mc.player == entity)
                .map(   entity -> (EntityLivingBase) entity)
                .filter(entity -> !(entity.isDead))
                .forEach(this::process);

        RayTraceResult normalResult = mc.objectMouseOver;

        if (normalResult != null) {
            focus = normalResult.typeOfHit == RayTraceResult.Type.ENTITY;
        }
    }

    private void process(EntityLivingBase event) {
        RayTraceResult bypassEntityResult = event.rayTrace(6, mc.getRenderPartialTicks());

        if (bypassEntityResult != null && focus) {
            if (bypassEntityResult.typeOfHit == RayTraceResult.Type.BLOCK) {
                BlockPos pos = bypassEntityResult.getBlockPos();

                if (mc.gameSettings.keyBindAttack.isKeyDown()) {
                    mc.playerController.onPlayerDamageBlock(pos, EnumFacing.UP);
                }
            }
        }
    }
}

