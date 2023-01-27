package com.wms.abstractclient.module.modules.render;

import com.wms.abstractclient.module.Module;
import com.wms.abstractclient.setting.Setting;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class Viewmodel extends Module {

    Setting x = new Setting(this,"X",0,-10,10,0.5);
    Setting y = new Setting(this,"Y",0,-10,10,0.5);
    Setting z = new Setting(this,"Z",0,-10,10,0.5);
    Setting scaleX = new Setting(this,"scaleX",10,-10,10,0.5);
    Setting scaleY = new Setting(this,"scaleY",10,-10,10,0.5);
    Setting scaleZ = new Setting(this,"scaleZ",10,-10,10,0.5);
    Setting alpha = new Setting(this,"Alpha",255,0,255,0.5);
    Setting swapAnim = new Setting(this,"NoSwapAnimation",true);

    public Viewmodel() {
        super("Viewmodel", Category.RENDER, Keyboard.KEY_NONE);
        addSetting(x);
        addSetting(y);
        addSetting(z);
        addSetting(scaleX);
        addSetting(scaleY);
        addSetting(scaleZ);
        addSetting(alpha);
        addSetting(swapAnim);

    }

    @Override
    public void onTick() {
        if(nullcheck()){return;}
        if(swapAnim.isEnabled() && mc.entityRenderer.itemRenderer.prevEquippedProgressMainHand >= 0.9) {
            mc.entityRenderer.itemRenderer.equippedProgressMainHand = 1.0f;
            mc.entityRenderer.itemRenderer.itemStackMainHand = mc.player.getHeldItemMainhand();
        }
    }

    @SubscribeEvent
    public void onHandRender(RenderHandEvent e){
        GL11.glTranslated(x.getValue(),y.getValue(),z.getValue());
        GL11.glScaled(scaleX.getValue(),scaleY.getValue(),scaleZ.getValue());
    }
}
