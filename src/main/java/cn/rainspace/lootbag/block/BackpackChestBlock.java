package cn.rainspace.lootbag.block;

import cn.rainspace.lootbag.block.entity.BackpackChestBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import java.util.function.Supplier;

public class BackpackChestBlock extends ChestBlock {

    public BackpackChestBlock(Properties p_i225757_1_, Supplier<BlockEntityType<? extends ChestBlockEntity>> p_i225757_2_) {
        super(p_i225757_1_, p_i225757_2_);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos p_154834_, BlockState p_154835_) {
        return new BackpackChestBlockEntity(p_154834_, p_154835_);
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos blockPos, Player player, InteractionHand hand, BlockHitResult result) {
        return InteractionResult.sidedSuccess(world.isClientSide());
    }
}
