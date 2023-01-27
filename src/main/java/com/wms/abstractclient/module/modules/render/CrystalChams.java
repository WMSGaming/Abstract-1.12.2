package com.wms.abstractclient.module.modules.render;

import com.wms.abstractclient.module.Module;
import com.wms.abstractclient.setting.Setting;
import org.lwjgl.input.Keyboard;

public class CrystalChams extends Module {

    Setting crystals = new Setting(this,"Chams",true);
    Setting noBob = new Setting(this,"NoBob",false);
    Setting line = new Setting(this,"Line",true);
    Setting crystalScale = new Setting(this,"CrystalScale",0.35,0.1,1,1);


    public CrystalChams() {
        super("CrystalChams", Category.RENDER, Keyboard.KEY_NONE);

        addSetting(crystals);
        addSetting(noBob);
        addSetting(line);
        addSetting(crystalScale);

    }
}
