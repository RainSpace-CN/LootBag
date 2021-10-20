package cn.rainspace.lootbag.item;

import cn.rainspace.lootbag.block.ModBlocks;
import cn.rainspace.lootbag.tileentity.BackpackChestTileEntity;
import cn.rainspace.lootbag.utils.Const;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.world.ForgeChunkManager;

public class BackpackItem extends Item {
    public BackpackItem(Properties p_i48487_1_) {
        super(p_i48487_1_);
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        CompoundNBT tag = itemstack.getTag() != null ? itemstack.getTag() : new CompoundNBT();
        BlockPos blockPos;
        if (!tag.contains("pos")) {
            blockPos = initChest(world);
            CompoundNBT pos = new CompoundNBT();
            pos.putInt("x", blockPos.getX());
            pos.putInt("y", blockPos.getY());
            pos.putInt("z", blockPos.getZ());
            tag.put("pos", pos);
            itemstack.setTag(tag);
        } else {
            CompoundNBT pos = (CompoundNBT) tag.get("pos");
            blockPos = new BlockPos(pos.getInt("x"), pos.getInt("y"), pos.getInt("z"));
        }
        TileEntity chest = world.getBlockEntity(blockPos);
        if (!world.isClientSide() && (chest == null || !(chest instanceof BackpackChestTileEntity))) {
            tag.remove("pos");
            itemstack.setTag(tag);
            return ActionResult.sidedSuccess(itemstack, world.isClientSide());
        }
        if (!world.isClientSide()) {
            ChunkPos chunkpos = new ChunkPos(blockPos);
            ForgeChunkManager.forceChunk((ServerWorld) world, Const.MOD_ID, blockPos, chunkpos.x, chunkpos.z, true, true);
        }
        player.openMenu((BackpackChestTileEntity) chest);
        player.awardStat(Stats.ITEM_USED.get(this));
        return ActionResult.sidedSuccess(itemstack, world.isClientSide());
    }

    private BlockPos initChest(World world) {
        int x = random.nextInt(100000) + 100000;
        int y = 0;
        int z = random.nextInt(100000) + 100000;
        BlockPos blockPos = new BlockPos(x, y, z);
        world.setBlockAndUpdate(blockPos, ModBlocks.BACKPACK_CHEST.get().defaultBlockState());
        return blockPos;
    }
}
