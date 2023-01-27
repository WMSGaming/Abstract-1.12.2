package com.wms.abstractclient.module.modules.chat;

import com.wms.abstractclient.module.Module;
import com.wms.abstractclient.util.TimerUtil;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class AutoExcuse extends Module {

    private Random rand = new Random();
    private TimerUtil timer = new TimerUtil();
    private final List<String> excuses = Arrays.asList("I totem failed!!!", "MY PING IS INSANE, UR BAD!","Im getting ddosed bozo","I had a lag spike!","This server is ass!");

    public AutoExcuse() {
        super("AutoExcuse", Category.CHAT, Keyboard.KEY_NONE);
    }

    @Override
    public void onTick() {
        if(nullcheck()){return;}
        if(timer.hasTimeElapsed(1500,true)) {
            if(!mc.player.isEntityAlive() && mc.player.getHealth() <= 0) {
                mc.player.sendChatMessage(randomExcuse());
            }
        }
    }

    private String randomExcuse(){
        return excuses.get(rand.nextInt(excuses.size()));
    }
}
