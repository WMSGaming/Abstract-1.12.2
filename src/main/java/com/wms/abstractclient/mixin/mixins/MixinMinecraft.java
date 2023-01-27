package com.wms.abstractclient.mixin.mixins;

import com.wms.abstractclient.mixin.accesor.IMinecraft;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Timer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = Minecraft.class)
public abstract class  MixinMinecraft implements IMinecraft {
    @Final
    @Shadow
    private Timer timer;
    @Override
    public Timer getTimer()
    {
        return timer;
    }

    @Accessor
    @Override
    public abstract void setRightClickDelayTimer(int delay);
}
