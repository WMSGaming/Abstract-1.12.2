package com.wms.abstractclient.module.modules.render;

import com.wms.abstractclient.event.PerspectiveEvent;
import com.wms.abstractclient.module.Module;
import com.wms.abstractclient.setting.Setting;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

public class Aspect extends Module {

    Setting aspect = new Setting(this,"Ratio", mc.displayWidth / mc.displayHeight + 0.0, 0.0 ,3.0,1);

    public Aspect() {
        super("Aspect", Category.RENDER, Keyboard.KEY_NONE);
        addSetting(aspect);
    }
    @SubscribeEvent
    public void onPerspectiveEvent(PerspectiveEvent event){
        event.setAspect((float) aspect.getValue());
    }
}
