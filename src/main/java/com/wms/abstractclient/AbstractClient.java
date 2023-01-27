package com.wms.abstractclient;

import com.google.common.hash.Hashing;
import com.mojang.text2speech.Narrator;
import com.wms.abstractclient.event.KeyEvent;
import com.wms.abstractclient.font.CustomFontrenderer;
import com.wms.abstractclient.manager.BetaManager;
import com.wms.abstractclient.manager.ConfigManager;
import com.wms.abstractclient.manager.FriendManager;
import com.wms.abstractclient.manager.RotationManager;
import com.wms.abstractclient.setting.SettingManager;
import com.wms.abstractclient.manager.UniversalColour;
import com.wms.abstractclient.module.ModuleManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.opengl.Display;
import java.awt.*;
import java.nio.charset.StandardCharsets;


@Mod(
        modid = AbstractClient.MOD_ID,
        name = AbstractClient.MOD_NAME,
        version = "b1"
)
public class AbstractClient {

    public static final String MOD_ID = "abstract";
    public static final String MOD_NAME = "@b$tr@ct";
    public static String VERSION = " v1.3.2";

    // Initializing client stuff.
    public static CustomFontrenderer fr;
    public static SettingManager settingManager;
    public static RotationManager rotationManager;
    public static ModuleManager moduleManager;
    public static FriendManager friendManager;
    public static BetaManager betaManager;
    public static UniversalColour unicolour;

    @Mod.Instance(MOD_ID)
    public static AbstractClient INSTANCE;



    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        Display.setTitle(MOD_NAME + VERSION);
        unicolour = new UniversalColour(134, 255, 199);

        settingManager = new SettingManager();
        moduleManager = new ModuleManager();


        rotationManager = new RotationManager();
        friendManager = new FriendManager();
        betaManager = new BetaManager();

        fr = new CustomFontrenderer(new Font("Dream MMA", Font.PLAIN, 18), true, true);
        ConfigManager.loadConfig();
        Runtime.getRuntime().addShutdownHook(new ConfigManager());
        MinecraftForge.EVENT_BUS.register(new KeyEvent());

    }

}
