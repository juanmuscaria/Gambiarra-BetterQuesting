package dev.bq3_thermos_patch;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.Mixins;

import java.util.Map;

@IFMLLoadingPlugin.Name("BQ3ThermosFix")
@IFMLLoadingPlugin.MCVersion("1.7.10")
public class Plugin implements IFMLLoadingPlugin {

    public Plugin(){
        System.setProperty("mixin.debug.export", "true");
        System.out.println("Starting BQ3-Thermos-Patch");
        MixinBootstrap.init();
        Mixins.addConfiguration("patch.json");
        System.out.println("BQ3-Thermos-Patch applied");
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
