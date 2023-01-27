package com.wms.abstractclient.module.modules.combat;

import com.wms.abstractclient.AbstractClient;
import com.wms.abstractclient.event.PacketSendEvent;
import com.wms.abstractclient.manager.RotationManager;
import com.wms.abstractclient.module.Module;
import com.wms.abstractclient.setting.Setting;
import com.wms.abstractclient.util.CombatUtil;
import com.wms.abstractclient.util.RotationUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

public class Aura extends Module {


    Setting rotate = new Setting(this,"Rotate",true);
    Setting range = new Setting(this,"Range",5,3,6,1);
    Setting swordOnly = new Setting(this,"OnlySword",true);
    int waiting = 0;

    public Aura() {
        super("Aura", Category.COMBAT, Keyboard.KEY_NONE);
        addSetting(rotate);
        addSetting(range);
        addSetting(swordOnly);
    }

    @Override
    public void onTick() {
        if(nullcheck()){return;}
        if(CombatUtil.getClosestPlayer((float) range.getValue()) != null){
            EntityPlayer target = CombatUtil.getClosestPlayer((float) range.getValue());
            if(mc.player.getCooledAttackStrength(0) == 1){
                waiting++;
                if(waiting >= 20){
                    attackTarget(target);
                }
            }
        }else{
            AbstractClient.INSTANCE.rotationManager.reset();
        }
    }


    @Override
    public void onDisable() {
        if(nullcheck()){return;}
        AbstractClient.rotationManager.reset();


    }
    public void attackTarget(EntityPlayer target){

        float yaw = RotationUtil.getRotations(target)[0];
        float pitch = RotationUtil.getRotations(target)[1];

        if(swordOnly.isEnabled()) {
            if(mc.player.getHeldItemMainhand().getItem() instanceof ItemSword) {
                mc.playerController.attackEntity(mc.player, target);
                mc.player.swingArm(EnumHand.MAIN_HAND);
            }
        }else {
            mc.playerController.attackEntity(mc.player, target);
            mc.player.swingArm(EnumHand.MAIN_HAND);
        }

        if(rotate.isEnabled()){
            AbstractClient.rotationManager.setRotations(yaw, pitch);
        }
    }
}
