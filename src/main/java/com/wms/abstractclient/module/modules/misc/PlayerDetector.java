package com.wms.abstractclient.module.modules.misc;

import com.mojang.text2speech.Narrator;
import com.wms.abstractclient.AbstractClient;
import com.wms.abstractclient.module.Module;
import com.wms.abstractclient.setting.Setting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.input.Keyboard;
import java.util.ArrayList;
import java.util.List;

public class PlayerDetector extends Module {
    private List<EntityPlayer> knownPlayers;
    private final Narrator narrator = Narrator.getNarrator();

    Setting sound =  new Setting(this,"Sound",true);

    public PlayerDetector() {
        super("PlayerDetector", Category.MISC, Keyboard.KEY_NONE);
        addSetting(sound);
    }

    @Override
    public void onEnable() {
        this.knownPlayers = new ArrayList<EntityPlayer>();
    }

    @Override
    public void onTick() {
        if(nullcheck()){return;}
        final List<EntityPlayer> tickPlayerList = new ArrayList<EntityPlayer>();

        for (final Entity entity : mc.world.getLoadedEntityList()) {
            if (entity instanceof EntityPlayer) {
                tickPlayerList.add((EntityPlayer) entity);
            }
        }
        if (tickPlayerList.size() > 0) {
            for (final EntityPlayer player : tickPlayerList) {
                if (player == mc.player) {
                    continue;
                }
                if (!this.knownPlayers.contains(player)) {
                    this.knownPlayers.add(player);

                    // Do sound
                    if(AbstractClient.friendManager.isFriend(player)) {
                        if(sound.isEnabled()) {
                            narrator.clear();
                            narrator.say("friend located");
                        }

                        sendMsg("Friend " + player.getName() + " has entered the render distance ");
                    }else if(!AbstractClient.friendManager.isFriend(player)) {
                        if(sound.isEnabled()) {
                            narrator.clear();
                            narrator.say("op located");
                        }
                        sendMsg("Opp " + player.getName() + " has entered the render distance ");
                    }

                    return;
                }
            }
        }
        if (this.knownPlayers.size() > 0) {
            knownPlayers.removeIf(player -> !tickPlayerList.contains(player));
        }
    }
}
