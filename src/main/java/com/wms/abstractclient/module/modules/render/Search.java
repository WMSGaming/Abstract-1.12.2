package com.wms.abstractclient.module.modules.render;

import com.wms.abstractclient.module.Module;
import com.wms.abstractclient.setting.Setting;
import com.wms.abstractclient.util.RenderUtil;
import net.minecraft.tileentity.*;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.input.Keyboard;

import java.awt.*;

public class Search extends Module {

    Setting chests = new Setting(this,"Chests",true);
    Setting shulkers = new Setting(this,"Shulkers",true);
    Setting enderchests = new Setting(this,"EnderChests",true);
    Setting bed = new Setting(this,"Bed",true);


    public Search() {
        super("Search", Category.RENDER, Keyboard.KEY_NONE);
        addSetting(chests);
        addSetting(enderchests);
        addSetting(bed);
        addSetting(shulkers);
    }

    @Override
    public void onRender3D() {
        for (TileEntity entity : mc.world.loadedTileEntityList) {

            if(entity instanceof TileEntityChest && chests.isEnabled()){
                renderBox(entity.getPos(),new Color(190, 137, 78, 150));
            }
            if(entity instanceof TileEntityEnderChest && enderchests.isEnabled()){
                renderBox(entity.getPos(),new Color(27, 0, 61, 150));
            }
            if(entity instanceof TileEntityBed && bed.isEnabled()){
                renderBox(entity.getPos(),new Color(255, 76, 76, 150));
            }
            if(entity instanceof TileEntityShulkerBox && shulkers.isEnabled()){
                renderBox(entity.getPos(),new Color(193, 77, 255, 150));
            }

        }
    }
    public void renderBox(BlockPos bp, Color c){
        RenderUtil.drawBoxESP(bp,c,1,true,true,150);
    }
}
