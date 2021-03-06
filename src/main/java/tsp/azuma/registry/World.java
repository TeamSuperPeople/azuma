package tsp.azuma.registry;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import tsp.azuma.Azuma;
import tsp.azuma.world.feature.ManaCrystalFeature;

public class World {

    public static final Feature<DefaultFeatureConfig> MANA_CRYSTAL = register("mana_crystal", new ManaCrystalFeature());

    public static void init() {

    }

    private static <C extends FeatureConfig, F extends Feature<C>> F register(String name, F feature) {
        return Registry.register(Registry.FEATURE, Azuma.id(name), feature);
    }
}
