package com.wms.abstractclient.module.modules.misc;

import com.wms.abstractclient.manager.FriendManager;
import com.wms.abstractclient.module.Module;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.RayTraceResult;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;


public class MCF extends Module {

    private boolean clicked = false;

    public MCF() {
        super("MCF", Category.MISC, Keyboard.KEY_NONE);
    }

    @Override
    public void onTick() {
        if (Mouse.isButtonDown((int)2)) {
            if (!this.clicked && mc.currentScreen == null) {
                addToFriends();
            }
            this.clicked = true;
        }else {
            this.clicked = false;
        }
    }

    public void addToFriends(){
        Entity entity;
        RayTraceResult result = MCF.mc.objectMouseOver;
        if (result != null && result.typeOfHit == RayTraceResult.Type.ENTITY && (entity = result.entityHit) instanceof EntityPlayer) {
            if (FriendManager.isFriend((EntityPlayer) entity)) {
                FriendManager.removeFriend((EntityPlayer) entity);
                sendMsg("Removed " + entity.getName());
            } else {
                FriendManager.addFriend((EntityPlayer) entity);
                sendMsg("Added " + entity.getName());
            }
        }
    }
}
