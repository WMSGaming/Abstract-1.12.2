package com.wms.abstractclient.module.modules.movement;

import com.wms.abstractclient.event.PacketReceiveEvent;
import com.wms.abstractclient.event.PacketSendEvent;
import com.wms.abstractclient.module.Module;
import com.wms.abstractclient.setting.Setting;
import com.wms.abstractclient.util.MovementUtil;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.server.SPacketEntityTeleport;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;
public class EntityFlight extends Module {


    Setting cancelROT = new Setting(this,"cancelRotation",true);
    Setting cancelTP = new Setting(this,"cancelTP",true);
    Setting onground = new Setting(this,"onground",true);
    Setting clip = new Setting(this,"Clip",true);

    public EntityFlight() {
        super("EntityFlight", Category.MOVEMENT, Keyboard.KEY_NONE);
        addSetting(clip);
        addSetting(cancelROT);
        addSetting(cancelTP);
        addSetting(onground);
    }

    @Override
    public void onTick() {
        if(nullcheck()){return;}

        if(mc.player.getRidingEntity() != null) {
            mc.player.getRidingEntity().onGround = onground.isEnabled();
            mc.player.getRidingEntity().noClip = clip.isEnabled();


            double x = MovementUtil.directionSpeed(1)[0];
            double z = MovementUtil.directionSpeed(1)[1];

            float speedY = 0;

            if (mc.player.movementInput.sneak) {
                speedY = -0.25f;
            }

            if (mc.player.movementInput.jump) {
                speedY = 0.25f;
            }

            mc.player.setVelocity(0, speedY, 0);


            mc.player.getRidingEntity().motionX = x;
            mc.player.getRidingEntity().setPosition(mc.player.getRidingEntity().posX,mc.player.getRidingEntity().posY + speedY, mc.player.getRidingEntity().posZ);
            mc.player.getRidingEntity().motionZ = z;

        }

    }
    @SubscribeEvent
    public void onPacket(PacketReceiveEvent event){
        if(event.getPacket() instanceof SPacketEntityTeleport && cancelTP.isEnabled()){
            event.setCanceled(true);
        }

    }    @SubscribeEvent
    public void onPacket(PacketSendEvent event){
        if((event.getPacket() instanceof CPacketPlayer.PositionRotation ||  event.getPacket() instanceof CPacketPlayer.Rotation) && (cancelROT.isEnabled())){
            event.setCanceled(true);
        }
    }
}
