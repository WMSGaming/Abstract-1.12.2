package com.wms.abstractclient.module;

import java.util.ArrayList;
import java.util.List;
import com.wms.abstractclient.module.Module.Category;
import com.wms.abstractclient.module.modules.chat.*;
import com.wms.abstractclient.module.modules.combat.*;
import com.wms.abstractclient.module.modules.exploit.*;
import com.wms.abstractclient.module.modules.hub.*;
import com.wms.abstractclient.module.modules.misc.*;
import com.wms.abstractclient.module.modules.movement.*;
import com.wms.abstractclient.module.modules.render.*;

public class ModuleManager {

    List<Module> modules;
    public ModuleManager(){
        this.modules = new ArrayList<>();

        //Combat
        modules.add(new AutoCrystalRewrite());
        modules.add(new AutoTotem());
        modules.add(new Velocity());
        modules.add(new Criticals());
        modules.add(new Aura());
        modules.add(new Burrow());
        modules.add(new Surround());
        modules.add(new XPThrower());
        modules.add(new AutoBowRelease());
        modules.add(new AntiFriendHit());
        modules.add(new BedAura());
        modules.add(new SelfWeb());

        //Exploit
        modules.add(new Bow32k());
        modules.add(new PacketFly());
        modules.add(new AntiHunger());
        modules.add(new GlobalSoundLogger());
        modules.add(new BurrowBypass());
        modules.add(new CornerClip());
        modules.add(new PetTP());
        modules.add(new EntityFlight());
        modules.add(new LiveOverflowDisabler());
        modules.add(new ExtraCarry());
        modules.add(new Ghost());
        modules.add(new PortalGodMode());
        modules.add(new VClip());

        //Movement
        modules.add(new Sprint());
        modules.add(new ReverseStep());
        modules.add(new Flight());
        modules.add(new ElytraFlight());
        modules.add(new Speed());
        modules.add(new NoSlow());
        modules.add(new NoFall());
        modules.add(new TimeChanger());
        modules.add(new AutoWalk());
        modules.add(new NoPush());
        modules.add(new Step());

        //Misc
        modules.add(new Fakeplayer());
        modules.add(new Autolog());
        modules.add(new MCF());
        modules.add(new NoRotate());
        modules.add(new DiscordRPC());
        modules.add(new AutoRespawn());
        modules.add(new Replenish());
        modules.add(new PlayerDetector());
        modules.add(new CopyCoords());
        modules.add(new FastPlace());
        modules.add(new MCPearl());
        modules.add(new NoEntityTrace());
        modules.add(new NoBuildHeight());

        //Render
        modules.add(new Fullbright());
        modules.add(new Search());
        modules.add(new Moonwalk());
        modules.add(new Viewmodel());
        modules.add(new CrystalChams());
        modules.add(new HoleEsp());
        modules.add(new Fov());
        modules.add(new Ambience());
        modules.add(new SwingSpeed());
        modules.add(new YawLock());
        modules.add(new NoHurtcam());
        modules.add(new Aspect());
        modules.add(new PlayerEsp());
        modules.add(new KillEffects());

        //Chat
        modules.add(new GreenChat());
        modules.add(new ChatSuffix());
        modules.add(new AutoEz());
        modules.add(new PopLag());
        modules.add(new AntiPopLag());
        modules.add(new CoordShare());
        modules.add(new ESpammer());
        modules.add(new ChatPing());
        modules.add(new EzPopper());
        modules.add(new AutoDupe());
        modules.add(new AntiRetardClient());
        modules.add(new Announcer());
        modules.add(new AutoExcuse());
        modules.add(new ChatEncrypt());

        //Hub
        modules.add(new ClickGuiModule());
        modules.add(new Colour());
        modules.add(new HudEditor());
        modules.add(new Cape());
        modules.add(new Notifications());

    }
    public List<Module> getModuleFromCategory(Category c){
        List<Module> mods = new ArrayList();
        for(Module module : modules){
            if(module.getCategory() == c){
                mods.add(module);
            }
        }
        return mods;
    }
    public Module getModuleFromName(String name){
        Module m = null;
        for(Module module : modules){
            if(module.getName().equalsIgnoreCase(name)){
                m = module;
            }
        }
        return m;
    }
    public List<Module> getModules(){
        return modules;
    }

}
