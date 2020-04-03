package tsp.asuna;

import nerdhub.cardinal.components.api.event.ItemComponentCallback;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.event.registry.RegistryEntryAddedCallback;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import tsp.asuna.api.cca.ItemManaComponent;
import tsp.asuna.api.ManaDurable;
import tsp.asuna.recipe.AltarRecipes;
import tsp.asuna.recipe.InfusionAltarRecipe;
import tsp.asuna.registry.Blocks;
import tsp.asuna.registry.Components;
import tsp.asuna.registry.Items;

public class Asuna implements ModInitializer {

    public static final ItemGroup ASUNA_SPELLS = FabricItemGroupBuilder.build(id("spells"), () -> new ItemStack(Items.MIASMA));
    public static final String MODID = "asuna";

    @Override
    public void onInitialize() {
        RegistryEntryAddedCallback.event(Registry.ITEM).register((i, identifier, item) -> {
            if (item instanceof ManaDurable) {
                ItemComponentCallback.event(item).register((stack, components) -> components.put(Components.MANA, new ItemManaComponent(((ManaDurable) item).getMaxMana())));
            }
        });

        Components.init();
        Items.init();
        Blocks.init();

        AltarRecipes.registerRecipe(
                new InfusionAltarRecipe.Builder(id("diamond"), InfusionAltarRecipe.RecipeTier.SIMPLE)
                        .centerStack(net.minecraft.item.Items.DIAMOND_ORE)
                        .withFirstRingOf(new Item[]{net.minecraft.item.Items.COAL})
                        .withOutput(new ItemStack(net.minecraft.item.Items.DIAMOND))
                        .build()
        );

        AltarRecipes.registerRecipe(
                new InfusionAltarRecipe.Builder(id("blaze"), InfusionAltarRecipe.RecipeTier.ADVANCED)
                        .centerStack(net.minecraft.item.Items.DIAMOND_ORE)
                        .withFirstRingOf(new Item[]{net.minecraft.item.Items.COAL})
                        .withSecondRingOf(new Item[]{net.minecraft.item.Items.MAGMA_BLOCK})
                        .withThirdRingOf(new Item[]{net.minecraft.item.Items.BLAZE_ROD})
                        .withOutput(new ItemStack(net.minecraft.item.Items.BLAZE_POWDER))
                        .build()
        );

        AltarRecipes.registerRecipe(
                new InfusionAltarRecipe.Builder(id("emerald"), InfusionAltarRecipe.RecipeTier.MEDIUM)
                        .centerStack(net.minecraft.item.Items.DIAMOND_ORE)
                        .withFirstRingOf(new Item[]{net.minecraft.item.Items.COAL})
                        .withSecondRingOf(new Item[]{net.minecraft.item.Items.LAPIS_BLOCK})
                        .withOutput(new ItemStack(net.minecraft.item.Items.EMERALD))
                        .build()
        );
    }

    public static Identifier id(String path) {
        return new Identifier(MODID, path);
    }
}
