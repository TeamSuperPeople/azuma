package tsp.asuna.block;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.EntityContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import tsp.asuna.entities.ManaRelayBlockEntity;

public class ManaRelayBlock extends Block implements BlockEntityProvider {

    private static final VoxelShape SHAPE = Block.createCuboidShape(4, 0, 4, 12, 8, 12);

    public ManaRelayBlock() {
        super(FabricBlockSettings.of(Material.STONE).lightLevel(14).build());
    }

    @Override
    public BlockEntity createBlockEntity(BlockView view) {
        return new ManaRelayBlockEntity();
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, EntityContext context) {
        return SHAPE;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView view, BlockPos pos, EntityContext context) {
        return SHAPE;
    }
}