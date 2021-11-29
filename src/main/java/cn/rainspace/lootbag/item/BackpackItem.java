package cn.rainspace.lootbag.item;

import cn.rainspace.lootbag.block.ModBlocks;
import cn.rainspace.lootbag.inventory.container.BackpackChestContainer;
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
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class BackpackItem extends Item {
    public BackpackItem(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if(world.isClientSide) {
            return ActionResult.success(itemstack);
        }
        CompoundNBT tag = itemstack.getTag() != null ? itemstack.getTag() : new CompoundNBT();
        BlockPos blockPos;
        ServerWorld overWorld = world.getServer().getLevel(World.OVERWORLD);
        if (!tag.contains("pos")) {
            blockPos = initChest(overWorld);
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
        TileEntity chest = overWorld.getBlockEntity(blockPos);
        if (chest == null || !(chest instanceof BackpackChestTileEntity)) {
            tag.remove("pos");
            itemstack.setTag(tag);
            return ActionResult.consume(itemstack);
        }
        ChunkPos chunkPos = new ChunkPos(blockPos);
        ForgeChunkManager.forceChunk(overWorld, Const.MOD_ID, blockPos, chunkPos.x, chunkPos.z, true, true);
        player.openMenu((BackpackChestTileEntity) chest);
        player.awardStat(Stats.ITEM_USED.get(this));
        return ActionResult.consume(itemstack);
    }

    private BlockPos initChest(World world) {
        int x = random.nextInt(100000) + 100000;
        int y = 0;
        int z = random.nextInt(100000) + 100000;
        BlockPos blockPos = new BlockPos(x, y, z);
        world.setBlockAndUpdate(blockPos, ModBlocks.BACKPACK_CHEST.get().defaultBlockState());
        return blockPos;
    }

    @SubscribeEvent
    public static void onCloseMenu(PlayerContainerEvent.Close event){
        if(event.getContainer() instanceof BackpackChestContainer) {
            BackpackChestContainer container = (BackpackChestContainer)event.getContainer();
            BackpackChestTileEntity tileEntity= (BackpackChestTileEntity)container.getContainer();
            ChunkPos chunkPos = new ChunkPos(tileEntity.getBlockPos());
            ForgeChunkManager.forceChunk((ServerWorld) tileEntity.getLevel(), Const.MOD_ID, tileEntity.getBlockPos(), chunkPos.x, chunkPos.z, false, true);
        }
    }
}
