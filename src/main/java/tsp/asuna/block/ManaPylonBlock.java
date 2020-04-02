package tsp.asuna.block;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.world.BlockView;
import tsp.asuna.entities.ManaPylonBlockEntity;

public class ManaPylonBlock extends Block implements BlockEntityProvider {

    public ManaPylonBlock() {
        super(FabricBlockSettings.of(Material.STONE).nonOpaque().build());
    }

    @Override
    public BlockEntity createBlockEntity(BlockView view) {
        return new ManaPylonBlockEntity();
    }
}