package cn.rainspace.lootbag.tileentity;

import cn.rainspace.lootbag.block.MagicFrostedIceBlock;
import net.minecraft.block.Blocks;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;

public class MagicFrostedIceTileEntity extends TileEntity implements ITickableTileEntity {
    private static final int LIVING_TIME = 15000;
    private int counter = LIVING_TIME;

    public MagicFrostedIceTileEntity() {
        super(ModTileEntityType.MAGIC_FROSTED_ICE.get());
    }

    @Override
    public void tick() {
        counter-=50;
        if(counter<0){
            this.level.setBlockAndUpdate(this.getBlockPos(), Blocks.AIR.defaultBlockState());
        }else if(counter<LIVING_TIME/4){
            this.level.setBlock(this.getBlockPos(), this.getBlockState().setValue(MagicFrostedIceBlock.AGE, Integer.valueOf(3)), 2);
        }else if(counter<LIVING_TIME*2/4){
            this.level.setBlock(this.getBlockPos(), this.getBlockState().setValue(MagicFrostedIceBlock.AGE, Integer.valueOf(2)), 2);
        }else if(counter<LIVING_TIME*3/4){
            this.level.setBlock(this.getBlockPos(), this.getBlockState().setValue(MagicFrostedIceBlock.AGE, Integer.valueOf(1)), 2);
        }
    }
}
