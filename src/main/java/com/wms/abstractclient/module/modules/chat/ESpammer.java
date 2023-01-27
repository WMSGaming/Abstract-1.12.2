package com.wms.abstractclient.module.modules.chat;

import com.wms.abstractclient.module.Module;
import com.wms.abstractclient.setting.Setting;
import com.wms.abstractclient.util.TimerUtil;
import org.lwjgl.input.Keyboard;

public class ESpammer extends Module {

    Setting delay = new Setting(this,"Delay",5,1,10,1);
    private TimerUtil timer = new TimerUtil();

    public ESpammer() {
        super("ESpammer", Category.CHAT, Keyboard.KEY_NONE);
        addSetting(delay);
    }

    @Override
    public void onTick() {
        if(nullcheck()){return;}

        if(timer.hasTimeElapsed((long) (1000*delay.getValue()),true)){
            mc.player.sendChatMessage("e");
        }
    }
}
