package cn.rainspace.lootbag.block.entity;

import cn.rainspace.lootbag.block.MagicFrostedIceBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class MagicFrostedIceBlockEntity extends BlockEntity  {
    private static final int LIVING_TIME = 15000;
    private int counter = LIVING_TIME;

    public MagicFrostedIceBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntityType.MAGIC_FROSTED_ICE.get(),blockPos,blockState);
    }

    public static void serverTick(Level level, BlockPos blockPos, BlockState blockState, MagicFrostedIceBlockEntity blockEntity) {
        blockEntity.counter -= 50;
        if (blockEntity.counter < 0) {
            level.setBlockAndUpdate(blockPos, Blocks.AIR.defaultBlockState());
        } else if (blockEntity.counter < LIVING_TIME / 4) {
            level.setBlock(blockPos, blockState.setValue(MagicFrostedIceBlock.AGE, Integer.valueOf(3)), 2);
        } else if (blockEntity.counter < LIVING_TIME * 2 / 4) {
            level.setBlock(blockPos, blockState.setValue(MagicFrostedIceBlock.AGE, Integer.valueOf(2)), 2);
        } else if (blockEntity.counter < LIVING_TIME * 3 / 4) {
            level.setBlock(blockPos, blockState.setValue(MagicFrostedIceBlock.AGE, Integer.valueOf(1)), 2);
        }
    }
}
