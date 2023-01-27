package com.wms.abstractclient.module.modules.chat;

import com.wms.abstractclient.module.Module;
import com.wms.abstractclient.setting.Setting;
import com.wms.abstractclient.util.TimerUtil;
import net.minecraft.network.play.client.CPacketChatMessage;
import org.lwjgl.input.Keyboard;

public class AutoDupe extends Module {

    Setting delay = new Setting(this,"Delay",2,1,15,1);

    TimerUtil timer = new TimerUtil();

    public AutoDupe() {
        super("/dupe", Category.CHAT, Keyboard.KEY_NONE);
        addSetting(delay);
    }

    @Override
    public void onTick() {
        if(nullcheck()){return;}
        if(timer.hasTimeElapsed((long) delay.getValue() * 1000,true)){
            mc.player.connection.sendPacket(new CPacketChatMessage("/dupe"));
        }
    }
}
