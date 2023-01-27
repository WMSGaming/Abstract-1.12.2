package com.wms.abstractclient.module.modules.combat;

import com.wms.abstractclient.module.Module;
import com.wms.abstractclient.setting.Setting;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.Item;
import org.lwjgl.input.Keyboard;

import java.util.Arrays;

public class AutoTotem extends Module {

    Setting mode = new Setting(this,"Mode", Arrays.asList("Crystal","Gap","Bed"));
    Setting health = new Setting(this,"Health",15,1,36,1);

    public AutoTotem() {
        super("AutoTotem", Category.COMBAT, Keyboard.KEY_NONE);
        addSetting(mode);
        addSetting(health);
    }

    @Override
    public void onTick() {
        if(nullcheck()) {return;}

        if((mc.player.getHealth() + mc.player.getAbsorptionAmount()) > health.getValue()){

            if(mode.getEnumValue().equalsIgnoreCase("Crystal")){
                if (mc.player.getHeldItemOffhand().getItem() != Items.END_CRYSTAL) {
                    putIteminOffhand(getCrystal());
                }
            }else if(mode.getEnumValue().equalsIgnoreCase("Gap")){
                if (mc.player.getHeldItemOffhand().getItem() != Items.GOLDEN_APPLE) {
                    putIteminOffhand(getGapple());
                }
            }else if(mode.getEnumValue().equalsIgnoreCase("Bed")){
                if (mc.player.getHeldItemOffhand().getItem() != Items.BED) {
                    putIteminOffhand(getBed());
                }
            }
        }else{
            if (mc.player.getHeldItemOffhand().getItem() != Items.TOTEM_OF_UNDYING) {
                putIteminOffhand(getTotem());
            }
        }
    }
    private int getBed() {
        if (Items.BED == mc.player.getHeldItemOffhand().getItem()) return -1;
        for(int i = 45; i >= 0; i--) {
            final Item item = mc.player.inventory.getStackInSlot(i).getItem();
            if(item == Items.BED) {
                if (i < 9) {
                    return -1;
                }
                return i;
            }
        }
        return -1;
    }

    private int getTotem() {
        if (Items.TOTEM_OF_UNDYING == mc.player.getHeldItemOffhand().getItem()) return -1;
        for(int i = 45; i >= 0; i--) {
            final Item item = mc.player.inventory.getStackInSlot(i).getItem();
            if(item == Items.TOTEM_OF_UNDYING) {
                if (i < 9) {
                    return -1;
                }
                return i;
            }
        }
        return -1;
    }
    private int getCrystal() {
        if (Items.END_CRYSTAL == mc.player.getHeldItemOffhand().getItem()) return -1;
        for(int i = 45; i >= 0; i--) {
            final Item item = mc.player.inventory.getStackInSlot(i).getItem();
            if(item == Items.END_CRYSTAL) {
                if (i < 9) {
                    return -1;
                }
                return i;
            }
        }
        return -1;
    }
    private int getGapple() {
        if (Items.GOLDEN_APPLE == mc.player.getHeldItemOffhand().getItem()) return -1;
        for(int i = 45; i >= 0; i--) {
            final Item item = mc.player.inventory.getStackInSlot(i).getItem();
            if(item == Items.GOLDEN_APPLE) {
                if (i < 9) {
                    return -1;
                }
                return i;
            }
        }
        return -1;
    }
    public void putIteminOffhand(int slot) {
        if(slot != -1) {
            mc.playerController.windowClick(0, slot, 0, ClickType.PICKUP, mc.player);
            mc.playerController.windowClick(0, 45, 0, ClickType.PICKUP, mc.player);
            mc.playerController.windowClick(0, slot, 0, ClickType.PICKUP, mc.player);
            mc.playerController.updateController();
        }
    }
}
