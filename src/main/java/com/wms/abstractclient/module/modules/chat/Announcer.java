package com.wms.abstractclient.module.modules.chat;

import com.wms.abstractclient.module.Module;
import com.wms.abstractclient.setting.Setting;
import com.wms.abstractclient.util.TimerUtil;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import org.lwjgl.input.Keyboard;

import java.util.Random;

public class Announcer extends Module {

    private final TimerUtil timer = new TimerUtil();
    private final Random rand = new Random();
    Setting random = new Setting(this,"Randomize",true);
    int delay = 10000;

    public Announcer() {
        super("Announcer", Category.CHAT, Keyboard.KEY_NONE);
        addSetting(random);
    }
    @SubscribeEvent
    public void onPickUp(PlayerEvent.ItemPickupEvent event){
        if(nullcheck()){return;}
        if(timer.hasTimeElapsed(delay,true)) {
            mc.player.sendChatMessage("I picked up " + event.getStack().getCount() + " " + event.getStack().getDisplayName() + " thanks to @b$tr@ct!" + getRand());
        }
    }

    @SubscribeEvent
    public void onUseItem(LivingEntityUseItemEvent event) {
        if(nullcheck()){return;}
        if(timer.hasTimeElapsed(delay,true) && event.getEntity() == mc.player) {
            mc.player.sendChatMessage("i just used a " + event.getItem().getDisplayName() +  " thanks to @b$tr@ct!" + getRand());
        }
    }

    private String getRand(){
        if(random.isEnabled()){
            return ("{"+ rand.nextInt(1000) + "}");
        }else {
            return "";
        }
    }
}
