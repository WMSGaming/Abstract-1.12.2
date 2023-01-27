package com.wms.abstractclient.module.modules.misc;

import com.wms.abstractclient.module.Module;
import com.wms.abstractclient.setting.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Items;
import org.lwjgl.input.Keyboard;

public class FastPlace extends Module {

    Setting onlyXP = new Setting(this,"OnlyXP",false);

    public FastPlace() {
        super("FastPlace", Category.MISC, Keyboard.KEY_NONE);
        addSetting(onlyXP);
    }

    @Override
    public void onTick() {
        if(nullcheck()){return;}
        if(onlyXP.isEnabled() && mc.player.getHeldItemMainhand().getItem() == Items.EXPERIENCE_BOTTLE) {
            mc.rightClickDelayTimer = 0;
        }else if(!onlyXP.isEnabled()){
            mc.rightClickDelayTimer = 0;
        }
    }
}
