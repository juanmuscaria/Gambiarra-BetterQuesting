package a.b.c.gambiarra;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.Mixins;

import java.util.Map;

@IFMLLoadingPlugin.Name("MagiHandlers")
@IFMLLoadingPlugin.MCVersion("1.7.10")
public class Plugin implements IFMLLoadingPlugin {

    public Plugin(){
        System.setProperty("mixin.debug.export", "true");
        System.out.println("Iniciando o mixin");
        MixinBootstrap.init();
        Mixins.addConfiguration("patch.json");
        System.out.println("terminado.");
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
