package cn.rainspace.lootbag.item;

import cn.rainspace.lootbag.LootBag;
import cn.rainspace.lootbag.block.Blocks;
import cn.rainspace.lootbag.block.entity.BackpackChestBlockEntity;
import cn.rainspace.lootbag.container.menu.BackpackChestMenu;
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
        if (event.getContainer() instanceof BackpackChestMenu container) {
            BackpackChestBlockEntity blockEntity = (BackpackChestBlockEntity) container.getContainer();
            ChunkPos chunkPos = new ChunkPos(blockEntity.getBlockPos());
            ForgeChunkManager.forceChunk((ServerLevel) blockEntity.getLevel(), LootBag.MOD_ID, blockEntity.getBlockPos(), chunkPos.x, chunkPos.z, false, true);
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        CompoundTag tag = itemStack.getTag() != null ? itemStack.getTag() : new CompoundTag();
        if (world.isClientSide()) {
            return InteractionResultHolder.success(itemStack);
        }
        ServerLevel overWorld = world.getServer().getLevel(Level.OVERWORLD);
        BlockPos blockPos = this.getChestPos(overWorld, itemStack);
        BlockEntity chest = overWorld.getBlockEntity(blockPos);
        if (!(chest instanceof BackpackChestBlockEntity)) {
            tag.remove("pos");
            itemStack.setTag(tag);
            return InteractionResultHolder.consume(itemStack);
        }
        ChunkPos chunkPos = new ChunkPos(blockPos);
        ForgeChunkManager.forceChunk(overWorld, LootBag.MOD_ID, blockPos, chunkPos.x, chunkPos.z, true, true);
        player.openMenu((BackpackChestBlockEntity) chest);
        player.awardStat(Stats.ITEM_USED.get(this));
        return InteractionResultHolder.consume(itemStack);
    }

    public BlockPos getChestPos(Level world, ItemStack itemStack) {
        CompoundTag tag = itemStack.getOrCreateTag();
        BlockPos blockPos;
        if (!tag.contains("pos")) {
            blockPos = this.initChest(world, itemStack, tag);
        } else {
            CompoundTag pos = (CompoundTag) tag.get("pos");
            blockPos = new BlockPos(pos.getInt("x"), pos.getInt("y"), pos.getInt("z"));
        }
        return blockPos;
    }

    public BlockPos initChest(Level world, ItemStack itemStack, CompoundTag tag) {
        Random random = new Random();
        int x = random.nextInt(100000) + 100000;
        int y = -64;
        int z = random.nextInt(100000) + 100000;
        CompoundTag pos = new CompoundTag();
        pos.putInt("x", x);
        pos.putInt("y", y);
        pos.putInt("z", z);
        tag.put("pos", pos);
        itemStack.setTag(tag);
        BlockPos blockPos = new BlockPos(x, y, z);
        world.setBlockAndUpdate(blockPos, Blocks.BACKPACK_CHEST.get().defaultBlockState());
        return blockPos;
    }
}
