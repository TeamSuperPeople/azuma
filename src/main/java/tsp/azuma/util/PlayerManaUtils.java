package tsp.azuma.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import tsp.azuma.api.ItemManaProvider;
import tsp.azuma.api.cca.ManaComponent;
import tsp.azuma.registry.Components;

public class PlayerManaUtils {

    public static int getManaFromPlayer(PlayerEntity playerEntity) {
        int mana = 0;

        for(ItemStack stack : playerEntity.inventory.main) {
            if(stack.getItem() instanceof ItemManaProvider) {
                mana += Components.MANA.get(stack).getMana();
            }
        }

        return mana;
    }

    public static void takeManaFromPlayer(PlayerEntity playerEntity, int requiredMana) {

        for(ItemStack stack : playerEntity.inventory.main) {
            if(stack.getItem() instanceof ItemManaProvider) {
                ManaComponent manaComponent = Components.MANA.get(stack);
                int foundMana = manaComponent.getMana();

                // component has mana
                if(foundMana >= 0) {



                    // ItemStack has enough mana to fit requirements. Deplete stack mana and return.
                    if(foundMana >= requiredMana) {
                        manaComponent.decrement(requiredMana);
                        return;
                    }

                    // ItemStack did not have enough mana to fit requirements. Set to 0, lower requiredMana cost, and continue.
                    else {
                        requiredMana -= foundMana;
                        manaComponent.decrement(foundMana);
                    }
                }
            }
        }

    }

    private PlayerManaUtils() {
        // NO-OP
    }
}
