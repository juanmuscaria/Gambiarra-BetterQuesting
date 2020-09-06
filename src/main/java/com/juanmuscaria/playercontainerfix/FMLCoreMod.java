package com.juanmuscaria.playercontainerfix;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.Mixins;

import java.util.Map;

@IFMLLoadingPlugin.Name("MagiHandlers")
@IFMLLoadingPlugin.MCVersion("1.7.10")
public class FMLCoreMod implements IFMLLoadingPlugin {
    public static Logger logger = LogManager.getLogger("PlayerContainerFix");

    public FMLCoreMod() {
        //System.setProperty("mixin.debug.export", "true"); // this is not needed
        logger.info("[PlayerContainerFix]-Adding ContainerPlayer patches!");
        MixinBootstrap.init();
        Mixins.addConfiguration("PlayerContainerFixMixins.json");
    }

    @Override
    public String[] getASMTransformerClass() {
        return new String[0];
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> map) {

    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }
}
