package com.wms.abstractclient.module.modules.movement;

import com.wms.abstractclient.event.MotionEvent;
import com.wms.abstractclient.module.Module;
import com.wms.abstractclient.setting.Setting;
import com.wms.abstractclient.util.MovementUtil;
import net.minecraft.init.MobEffects;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.potion.Potion;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

import java.util.Arrays;

public class Speed extends Module {
    private int strafeStage;
    private double baseSpeed = 0.2873;
    private double moveSpeed;
    private double latestMoveSpeed;
    private boolean accelerate;


    Setting mode = new Setting(this,"Speed", Arrays.asList("Strafe","yPort","5B5T"));
    Setting strict = new Setting(this, "Strict", true);
    Setting timer = new Setting(this, "Timer", true);


    public Speed() {
        super("Speed", Category.MOVEMENT, Keyboard.KEY_NONE);
        addSetting(mode);
        addSetting(strict);
        addSetting(timer);
    }

    @Override
    public void onEnable() {
        strafeStage = 4;
    }

    @Override
    public void onDisable() {
        reset();
    }

    public void reset() {
        strafeStage = 4;
        moveSpeed = 0;
        mc.timer.tickLength = 50f;
        latestMoveSpeed = 0;
        accelerate = false;
    }

    @Override
    public void onUpdate(TickEvent.ClientTickEvent event) {
        if (nullcheck()) {return;}
        if(mode.getEnumValue().equalsIgnoreCase("yPort")) {
            if(MovementUtil.isMoving()) {
                if (!(mc.gameSettings.keyBindJump.isKeyDown()  || mc.player.collidedHorizontally)) {

                    if (mc.player.onGround) {
                        mc.timer.tickLength = 50 / 1.15f;
                        mc.player.jump();
                        MovementUtil.strafe((float) (getBaseMoveSpeed() + 0.1));
                    } else {
                        mc.player.motionY = -1;
                        mc.timer.tickLength = 50f;
                    }
                }
            }
        }else if(mode.getEnumValue().equalsIgnoreCase("5B5T")) {
            if(mc.player.onGround && !mc.gameSettings.keyBindJump.isKeyDown()){
                mc.player.motionY = 0;
                MovementUtil.strafe(0.285f);
                mc.timer.tickLength = 50 / 1.15f;
            }
        }
    }


    //Shoutout to cosmos

    @SubscribeEvent
    public void onMotion(MotionEvent event) {
        if (nullcheck()) {return;}
        if(mode.getEnumValue().equalsIgnoreCase("Strafe")) {

            latestMoveSpeed = Math.sqrt(StrictMath.pow(mc.player.posX - mc.player.prevPosX, 2) + StrictMath.pow(mc.player.posZ - mc.player.prevPosZ, 2));
            double motionY = strict.isEnabled() ? 0.41999998688697815 : 0.3999999463558197;

            if (timer.isEnabled()) {
                mc.timer.tickLength = 50 / 1.088f;
            } else {
                mc.timer.tickLength = 50f;
            }


            // only attempt to modify speed if we are inputting movement
            if (MovementUtil.isMoving()) {


                // start the motion
                if (strafeStage == 1) {

                    // starting speed
                    moveSpeed = 1.35 * baseSpeed - 0.01;
                }

                // start jumping
                else if (strafeStage == 2) {


                    // scale jump speed if Jump Boost potion effect is active

                    // not really too useful for Speed like the other potion effects
                    if (mc.player.isPotionActive(MobEffects.JUMP_BOOST)) {
                        double amplifier = mc.player.getActivePotionEffect(MobEffects.JUMP_BOOST).getAmplifier();
                        motionY += (amplifier + 1) * 0.1;
                    }

                    // jump
                    mc.player.motionY = motionY;
                    event.setMotionY(motionY);


                    // alternate acceleration ticks
                    double acceleration = 1.395;

                    // if can accelerate, increase speed
                    if (accelerate) {
                        acceleration = 1.6835;
                    }

                    // since we just jumped, we can now move faster
                    moveSpeed *= acceleration;
                }

                // start actually speeding when falling
                else if (strafeStage == 3) {

                    // take into account our last tick's move speed
                    double scaledMoveSpeed = 0.66 * (latestMoveSpeed - baseSpeed);

                    // scale the move speed
                    moveSpeed = latestMoveSpeed - scaledMoveSpeed;

                    // we've just slowed down and need to alternate acceleration
                    accelerate = !accelerate;
                } else {
                    if ((mc.world.getCollisionBoxes(mc.player, mc.player.getEntityBoundingBox().offset(0, mc.player.motionY, 0)).size() > 0 || mc.player.collidedVertically) && strafeStage > 0) {

                        // reset strafe stage
                        strafeStage = MovementUtil.isMoving() ? 1 : 0;
                    }

                    // collision speed
                    moveSpeed = latestMoveSpeed - (latestMoveSpeed / 159);
                }

                moveSpeed = Math.max(moveSpeed, baseSpeed);
                float forward = mc.player.movementInput.moveForward;
                float strafe = mc.player.movementInput.moveStrafe;
                float yaw = mc.player.prevRotationYaw + (mc.player.rotationYaw - mc.player.prevRotationYaw) * mc.getRenderPartialTicks();

                // if we're not inputting any movements, then we shouldn't be adding any motion
                if (!MovementUtil.isMoving()) {
                    event.setMotionX(0);
                    event.setMotionZ(0);
                } else if (forward != 0) {
                    if (strafe > 0) {
                        yaw += forward > 0 ? -45 : 45;
                    } else if (strafe < 0) {
                        yaw += forward > 0 ? 45 : -45;
                    }

                    strafe = 0;

                    if (forward > 0) {
                        forward = 1;
                    } else if (forward < 0) {
                        forward = -1;
                    }
                }

                // our facing values, according to movement not rotations
                double cos = Math.cos(Math.toRadians(yaw));
                double sin = -Math.sin(Math.toRadians(yaw));

                // update the movements
                event.setMotionX((forward * moveSpeed * sin) + (strafe * moveSpeed * cos));
                event.setMotionZ((forward * moveSpeed * cos) - (strafe * moveSpeed * sin));

                // update
                strafeStage++;

            }
        }
    }
    public static double getBaseMoveSpeed() {
        double baseSpeed = 0.2873;
        if (mc.player != null && mc.player.isPotionActive(Potion.getPotionById(1))) {
            final int amplifier = mc.player.getActivePotionEffect(Potion.getPotionById(1)).getAmplifier();
            baseSpeed *= 1.0 + 0.2 * (amplifier + 1);
        }
        return baseSpeed;
    }
}
