package com.wms.abstractclient.module.modules.misc;

import com.mojang.authlib.GameProfile;
import com.wms.abstractclient.module.Module;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import org.lwjgl.input.Keyboard;

import java.util.UUID;

public class Fakeplayer extends Module {

    public EntityOtherPlayerMP fakePlayer;

    public Fakeplayer() {
        super("Fakeplayer", Category.MISC, Keyboard.KEY_NONE);
    }

    @Override
    public void onEnable(){
        if(mc.player != null && mc.world != null) {
            UUID uuid = mc.player.getUniqueID();

            (this.fakePlayer = new EntityOtherPlayerMP((World)mc.world, new GameProfile(UUID.fromString(uuid.toString()), mc.player.getDisplayNameString()))).copyLocationAndAnglesFrom((Entity)mc.player);
            this.fakePlayer.inventory.copyInventory(mc.player.inventory);
            mc.world.addEntityToWorld(-7777, (Entity)this.fakePlayer);

        }
    }
    @Override
    public void onDisable() {
        if (this.fakePlayer != null && mc.world != null) {
            mc.world.removeEntityFromWorld(-7777);
            this.fakePlayer = null;
        }
    }
}
