package cn.rainspace.lootbag.item;

import cn.rainspace.lootbag.block.ModBlocks;
import cn.rainspace.lootbag.inventory.container.BackpackChestMenu;
import cn.rainspace.lootbag.block.entity.BackpackChestBlockEntity;
import cn.rainspace.lootbag.utils.Const;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.world.ForgeChunkManager;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class BackpackItem extends Item {
    public BackpackItem(Properties properties) {
        super(properties);
    }

    @SubscribeEvent
    public static void onCloseMenu(PlayerContainerEvent.Close event) {
        if (event.getContainer() instanceof BackpackChestMenu) {
            BackpackChestMenu container = (BackpackChestMenu) event.getContainer();
            BackpackChestBlockEntity tileEntity = (BackpackChestBlockEntity) container.getContainer();
            ChunkPos chunkPos = new ChunkPos(tileEntity.getBlockPos());
            ForgeChunkManager.forceChunk((ServerLevel) tileEntity.getLevel(), Const.MOD_ID, tileEntity.getBlockPos(), chunkPos.x, chunkPos.z, false, true);
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (world.isClientSide) {
            return InteractionResultHolder.success(itemstack);
        }
        CompoundTag tag = itemstack.getTag() != null ? itemstack.getTag() : new CompoundTag();
        BlockPos blockPos;
        ServerLevel overWorld = world.getServer().getLevel(Level.OVERWORLD);
        if (!tag.contains("pos")) {
            blockPos = initChest(overWorld);
            CompoundTag pos = new CompoundTag();
            pos.putInt("x", blockPos.getX());
            pos.putInt("y", blockPos.getY());
            pos.putInt("z", blockPos.getZ());
            tag.put("pos", pos);
            itemstack.setTag(tag);
        } else {
            CompoundTag pos = (CompoundTag) tag.get("pos");
            blockPos = new BlockPos(pos.getInt("x"), pos.getInt("y"), pos.getInt("z"));
        }
        BlockEntity chest = overWorld.getBlockEntity(blockPos);
        if (chest == null || !(chest instanceof BackpackChestBlockEntity)) {
            tag.remove("pos");
            itemstack.setTag(tag);
            return InteractionResultHolder.consume(itemstack);
        }
        ChunkPos chunkPos = new ChunkPos(blockPos);
        ForgeChunkManager.forceChunk(overWorld, Const.MOD_ID, blockPos, chunkPos.x, chunkPos.z, true, true);
        player.openMenu((BackpackChestBlockEntity) chest);
        player.awardStat(Stats.ITEM_USED.get(this));
        return InteractionResultHolder.consume(itemstack);
    }

    private BlockPos initChest(Level world) {
        Random random = new Random();
        int x = random.nextInt(100000) + 100000;
        int y = 0;
        int z = random.nextInt(100000) + 100000;
        BlockPos blockPos = new BlockPos(x, y, z);
        world.setBlockAndUpdate(blockPos, ModBlocks.BACKPACK_CHEST.get().defaultBlockState());
        return blockPos;
    }
}
