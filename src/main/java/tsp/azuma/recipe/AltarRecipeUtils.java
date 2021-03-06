package tsp.azuma.recipe;

import net.minecraft.item.Item;
import tsp.azuma.entity.InfusionAltarPedestalBlockEntity;

import java.util.ArrayList;
import java.util.List;

public class AltarRecipeUtils {

    public static boolean canCraft(AltarRecipe recipe, AltarState altarState) {
        boolean first = recipe.getFirstRingIngredients() == null || ringMatches(altarState.getFirstRingPedestals(), recipe.getFirstRingIngredients());
        boolean second = recipe.getSecondRingIngredients() == null || ringMatches(altarState.getSecondRingAltars(), recipe.getSecondRingIngredients());
        boolean third = recipe.getThirdRingIngredients() == null || ringMatches(altarState.getThirdRingAltars(), recipe.getThirdRingIngredients());

        return
                altarState.getCore().getHeldStack().getItem() == recipe.getCenterItem()
                        && first
                        && second
                        && third;
    }

    public static void takeItems(AltarRecipe recipe, AltarState altarState) {
        takeRingItems(altarState.getFirstRingPedestals(), recipe.getFirstRingIngredients());

        if(recipe.getSecondRingIngredients() != null) {
            takeRingItems(altarState.getSecondRingAltars(), recipe.getSecondRingIngredients());
        }

        if(recipe.getThirdRingIngredients() != null) {
            takeRingItems(altarState.getThirdRingAltars(), recipe.getThirdRingIngredients());
        }
    }

    public static void takeRingItems(List<InfusionAltarPedestalBlockEntity> pedestals, List<Item> ingredients) {
        if(pedestals != null) {
            for (Item item : ingredients) {
                for (InfusionAltarPedestalBlockEntity be : pedestals) {
                    if (be.getHeldStack().getItem().equals(item)) {
                        be.clearItem();
                        break;
                    }
                }
            }
        }
    }

    public static boolean ringMatches(List<InfusionAltarPedestalBlockEntity> pedestals, List<Item> ringIngredients) {
        // pedestals are null, not a match
        if(pedestals == null) {
            return false;
        }

        // no ingredients needed, technically a match
        if(ringIngredients == null) {
            return true;
        }

        List<Item> currentItems = new ArrayList<>();
        pedestals.forEach(pedestal -> currentItems.add(pedestal.getHeldStack().getItem()));

        for(Item ingredient : ringIngredients) {
            if(!currentItems.contains(ingredient)) {
                return false;
            }

            currentItems.remove(ingredient);
        }

        return true;
    }
}
