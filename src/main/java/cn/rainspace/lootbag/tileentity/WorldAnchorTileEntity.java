package cn.rainspace.lootbag.tileentity;

import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.world.ForgeChunkManager;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.server.ServerWorld;
public class WorldAnchorTileEntity extends TileEntity implements ITickableTileEntity{
	private int timer = 0;
	private static final int VALUE = 100;
	public WorldAnchorTileEntity() {
		super(ModTileEntityType.worldAnchorTileEntity.get());
	}
	
	@Override
	public void tick(){
		if(!level.isClientSide) {
			if(timer==1) {
				ChunkPos chunkpos = new ChunkPos(getBlockPos());
				ForgeChunkManager.forceChunk((ServerWorld) level, "lootbag", getBlockPos(), chunkpos.x, chunkpos.z, false, true);
			}
			if(timer>0)
				timer--;
		}
	}
	
	public int enpower() {
		if(timer==0) {
			ChunkPos chunkpos = new ChunkPos(getBlockPos());
			ForgeChunkManager.forceChunk((ServerWorld) level, "lootbag", getBlockPos(), chunkpos.x, chunkpos.z, true, true);
		}
		timer+=VALUE;
		return timer;
	}
}
