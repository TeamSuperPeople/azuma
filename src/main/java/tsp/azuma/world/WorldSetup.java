package tsp.azuma.world;

import net.fabricmc.fabric.api.event.registry.RegistryEntryAddedCallback;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.RangeDecoratorConfig;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import tsp.azuma.registry.Blocks;
import tsp.azuma.registry.World;

public class WorldSetup {

    public static void setup() {
        Registry.BIOME.forEach(WorldSetup::handleBiome);
        RegistryEntryAddedCallback.event(Registry.BIOME).register((i, identifier, biome) -> handleBiome(biome));

        Registry.BIOME.forEach(biome -> {
            biome.addFeature(GenerationStep.Feature.TOP_LAYER_MODIFICATION, World.MANA_CRYSTAL.configure(new DefaultFeatureConfig()));
        });

        RegistryEntryAddedCallback.event(Registry.BIOME).register((i, identifier, biome) -> {
            biome.addFeature(GenerationStep.Feature.TOP_LAYER_MODIFICATION, World.MANA_CRYSTAL.configure(new DefaultFeatureConfig()));
        });
    }

    private static void handleBiome(Biome biome) {
        if (biome.getCategory() != Biome.Category.NETHER && biome.getCategory() != Biome.Category.THEEND) {
            biome.addFeature(
                    GenerationStep.Feature.UNDERGROUND_ORES,
                    Feature.ORE.configure(
                            new OreFeatureConfig(
                                    OreFeatureConfig.Target.NATURAL_STONE,
                                    Blocks.RUBY_ORE.getDefaultState(),
                                    8//Ore vein size
                            )).createDecoratedFeature(
                            Decorator.COUNT_RANGE.configure(new RangeDecoratorConfig(
                                            2, //Number of veins per chunk
                                            0, //Bottom Offset
                                            2, //Min y level
                                            16 //Max y level
                                    )
                            )
                    )
            );
        }
    }

    private WorldSetup() {
        // NO-OP
    }
}
