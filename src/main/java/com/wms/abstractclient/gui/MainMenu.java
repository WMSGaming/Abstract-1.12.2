package com.wms.abstractclient.gui;

import com.wms.abstractclient.AbstractClient;
import com.wms.abstractclient.font.FontUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import java.awt.*;
import java.io.IOException;

public class MainMenu extends GuiScreen {

    public static Minecraft mc = Minecraft.getMinecraft();
    private final String[] buttons = {"Solo", "Multiplayer", "Setting", "Exit"};

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        ScaledResolution sr = new ScaledResolution(mc);
        float width = sr.getScaledWidth()/2;
        float height = sr.getScaledHeight();
        drawImage(0,0,sr.getScaledWidth() ,sr.getScaledHeight(),"textures/background.png");
        int colour = AbstractClient.unicolour.getColor().getRGB();

        float y = height - (height/4);

        for (String button : buttons) {
            float x = width - (FontUtil.getStringWidth(button)/2);
            boolean isHovered = hovered((int) width - 50,  (int) (y-4), (int) 100, (int) (FontUtil.getFontHeight() + 8),mouseX,mouseY);
            Gui.drawRect((int) width - 51,  (int) (y-5), (int) width + 51, (int) (y + FontUtil.getFontHeight() + 5), colour);
            Gui.drawRect((int) width - 50,  (int) (y-4), (int) width + 50, (int) (y + FontUtil.getFontHeight() + 4), isHovered ? colour  : new Color(5, 5, 5).getRGB());
            FontUtil.drawStringWithShadow(button, x, y+1, -1);
            y += FontUtil.getFontHeight() + 15;
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        ScaledResolution sr = new ScaledResolution(mc);
        float width = sr.getScaledWidth()/2;
        float height = sr.getScaledHeight();
        float y = height - (height/4);

        for (String button : buttons) {
            boolean isHovered = hovered((int) width - 50,  (int) (y-4), (int) 100, (int) (FontUtil.getFontHeight() + 8),mouseX,mouseY);
            float x = width/2 - (FontUtil.getStringWidth(button)/2);
            if(isHovered){
                switch (button) {
                    case "Solo":
                        mc.displayGuiScreen(new GuiWorldSelection(this));
                        break;
                    case "Multiplayer":
                        mc.displayGuiScreen(new GuiMultiplayer(this));

                        break;
                    case "Setting":
                        mc.displayGuiScreen(new GuiOptions(this, mc.gameSettings));
                        break;
                    case "Exit":
                        mc.shutdown();
                        break;
                }
            }

            y += FontUtil.getFontHeight() + 15;
        }
    }
    public boolean hovered(int x ,int y ,int width, int height,int mouseX ,int mouseY ) {
        return mouseX > x  && mouseX< x  + width && mouseY > y && mouseY < y + height;
    }

    // Credits to Astro for this code
    public static void drawImage(int x, int y, int w, int h, String location) {
        GlStateManager.color(1, 1, 1);
        ResourceLocation resourceLocation = new ResourceLocation(location);
        mc.getTextureManager().bindTexture(resourceLocation);
        GlStateManager.enableBlend();
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
        Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0F, 0.0F, w, h, w, h);
        GlStateManager.disableBlend();
    }
}
