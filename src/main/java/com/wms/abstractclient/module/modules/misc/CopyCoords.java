package com.wms.abstractclient.module.modules.misc;

import com.wms.abstractclient.module.Module;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public class CopyCoords extends Module {
    public CopyCoords() {
        super("CopyCoords", Category.MISC, Keyboard.KEY_NONE);
    }

    @Override
    public void onEnable() {
        if(nullcheck()){return;}
        String coords = "X: " + (int)mc.player.posX + " Y: " + (int)mc.player.posY + " Z: " +(int)mc.player. posZ;
        StringSelection stringSelection = new StringSelection(coords);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
        sendMsg("Copied coordinates to clipboard!");
        this.toggle();
    }
}
