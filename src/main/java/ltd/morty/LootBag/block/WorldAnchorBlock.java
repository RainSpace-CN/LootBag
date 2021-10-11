package ltd.morty.LootBag.block;

import javax.annotation.Nullable;

import ltd.morty.LootBag.tileentity.WorldAnchorTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.world.ForgeChunkManager;

public class WorldAnchorBlock extends Block{
	public WorldAnchorBlock(Properties properties){
		super(properties);
	}
	
	@Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }
	
	@Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new WorldAnchorTileEntity();
    }
	
	@Override
	public void destroy(IWorld world, BlockPos pos, BlockState state) {
		if(!world.isClientSide()) {
			ChunkPos chunkpos = new ChunkPos(pos);
			ForgeChunkManager.forceChunk((ServerWorld) world, "loot_bag", pos, chunkpos.x, chunkpos.z, false, true);
		}
	}
	
	@Override
	public ActionResultType use(BlockState blockstate, World world, BlockPos blockpos, PlayerEntity player, Hand hand, BlockRayTraceResult result){
		if(!world.isClientSide&&hand==Hand.MAIN_HAND) {
			ItemStack itemstack = player.getItemInHand(hand);
			Item item = itemstack.getItem();
			if(item!=Items.ENDER_EYE) {
				return ActionResultType.PASS;
			}else {
				WorldAnchorTileEntity tileentity = (WorldAnchorTileEntity)world.getBlockEntity(blockpos);
				int timer = tileentity.enpower()/20;
				TranslationTextComponent translationTextComponent = new TranslationTextComponent("message.loot_bag.timer", timer);
			    player.sendMessage(translationTextComponent, player.getUUID());
				if (!player.abilities.instabuild) {
		        	itemstack.shrink(1);
		        }
			}
		}
		return ActionResultType.sidedSuccess(world.isClientSide);
	}
}
