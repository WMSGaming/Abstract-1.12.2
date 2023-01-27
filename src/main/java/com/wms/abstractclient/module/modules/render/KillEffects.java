package com.wms.abstractclient.module.modules.render;

import com.wms.abstractclient.module.Module;
import com.wms.abstractclient.util.TimerUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class KillEffects extends Module {

    private TimerUtil timer = new TimerUtil();

    public KillEffects() {
        super("KillEffects", Category.RENDER, Keyboard.KEY_NONE);
    }

    @Override
    public void onUpdate(TickEvent.ClientTickEvent event) {
        if(nullcheck()){return;}
        for(EntityPlayer target : mc.world.playerEntities){
            if(target.getDistance(mc.player)  <= 20){
                if(!target.isEntityAlive() && target.getHealth() <= 0){
                    if(target != mc.player) {
                        if(timer.hasTimeElapsed(250,true)) {
                            mc.world.spawnEntity((Entity)new EntityLightningBolt((World)KillEffects.mc.world, target.posX, target.posY, target.posZ, true));
                            mc.player.playSound(SoundEvents.ENTITY_LIGHTNING_THUNDER, 0.5f, 1.0f);
                            break;
                        }
                    }
                }
            }
        }
    }
}
