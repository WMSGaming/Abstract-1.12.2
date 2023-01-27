package com.wms.abstractclient.mixin.mixins;

import com.wms.abstractclient.AbstractClient;
import com.wms.abstractclient.module.Module;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderEnderCrystal;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.awt.*;

@Mixin( RenderEnderCrystal.class )
public class MixinRenderEnderCrystal {

    @Redirect(method = "doRender", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/ModelBase;render(Lnet/minecraft/entity/Entity;FFFFFF)V"))
    public void doRender(ModelBase modelBase, Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        Color uni = AbstractClient.unicolour.getColor();
        if(AbstractClient.moduleManager.getModuleFromName("CrystalChams").isEnabled()) {
            //Render crystal chams below!

            // Credits to MoneyMod for a lot of this since idk how to use GL stuff.

            Module chams = AbstractClient.moduleManager.getModuleFromName("CrystalChams");

            float bob = chams.getSetting("NoBob").isEnabled() ? 0f : ageInTicks;

            GlStateManager.scale(chams.getSetting("CrystalScale").getValue(), chams.getSetting("CrystalScale").getValue(), chams.getSetting("CrystalScale").getValue());


            if (chams.getSetting("Line").isEnabled()) {
                GL11.glPushMatrix();
                GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);
                GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
                GL11.glDisable(GL11.GL_TEXTURE_2D);
                GL11.glDisable(GL11.GL_LIGHTING);
                GL11.glDisable(GL11.GL_DEPTH_TEST);
                GL11.glEnable(GL11.GL_LINE_SMOOTH);
                GL11.glEnable(GL11.GL_BLEND);
                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

                GL11.glColor3f((float) uni.getRed() / 255, (float) uni.getGreen() / 255, (float) uni.getBlue() / 255);
                GL11.glLineWidth((float) 2);
                modelBase.render(entity, limbSwing, limbSwingAmount, bob, netHeadYaw, headPitch, scale);
                GL11.glPopAttrib();
                GL11.glPopMatrix();
            }

            if(chams.getSetting("Chams").isEnabled()) {
            //Draw outline for crystal

                    //Draw normal chams
                    GL11.glPushMatrix();
                    GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);
                    GL11.glDisable(GL11.GL_ALPHA_TEST);
                    GL11.glDisable(GL11.GL_TEXTURE_2D);
                    GL11.glDisable(GL11.GL_LIGHTING);
                    GL11.glEnable(GL11.GL_BLEND);
                    GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);


                if (chams.getSetting("Line").isEnabled()) {
                    GL11.glLineWidth((float)2);
                }

                GL11.glDisable(GL11.GL_DEPTH_TEST);
                GL11.glDepthMask(false);

                GL11.glEnable(GL11.GL_LINE_SMOOTH);
                GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_NICEST);

                GL11.glColor4f((float)uni.getRed()/255,(float)uni.getGreen()/255,(float)uni.getBlue()/255,(float)175/255);
                modelBase.render(entity, limbSwing, limbSwingAmount, bob, netHeadYaw, headPitch, scale);

                GL11.glDisable(GL11.GL_LINE_SMOOTH);
                GL11.glEnable(GL11.GL_DEPTH_TEST);
                GL11.glDepthMask(true);
                GL11.glEnable(GL11.GL_BLEND);
                GL11.glEnable(GL11.GL_LIGHTING);
                GL11.glEnable(GL11.GL_TEXTURE_2D);
                GL11.glEnable(GL11.GL_ALPHA_TEST);
                GL11.glPopAttrib();
                GL11.glPopMatrix();


            }else {
                modelBase.render(entity, limbSwing, limbSwingAmount, bob, netHeadYaw, headPitch, scale);
            }


        }else {
            modelBase.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        }
    }
}
