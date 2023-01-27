package com.wms.abstractclient.module.modules.movement;

import com.wms.abstractclient.module.Module;
import com.wms.abstractclient.setting.Setting;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.util.MovementInput;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

public class NoSlow extends Module {

    Setting strict = new Setting(this,"Strict",false);

    public NoSlow() {
        super("NoSlow", Category.MOVEMENT, Keyboard.KEY_NONE);
        addSetting(strict);
    }
    @SubscribeEvent
    public void onInput(InputUpdateEvent e){
        if(nullcheck()) {return;}
        if (mc.player.isHandActive()) {
            MovementInput movementInput = e.getMovementInput();
            movementInput.moveStrafe *= 5.0f;
            movementInput.moveForward *= 5.0f;
        }
    }
    @SubscribeEvent
    public void onEntityItemUse(LivingEntityUseItemEvent event) {
        if(strict.isEnabled()){
            //Insane bypass ong
            mc.player.connection.sendPacket(new CPacketHeldItemChange(mc.player.inventory.currentItem));
        }
    }
}
