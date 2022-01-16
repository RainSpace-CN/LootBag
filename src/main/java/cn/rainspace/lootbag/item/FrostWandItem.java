package cn.rainspace.lootbag.item;

import cn.rainspace.lootbag.block.ModBlocks;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class FrostWandItem extends Item {
    public FrostWandItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        BlockPos blockPos = player.blockPosition();
        BlockPos blockPos1 = blockPos.below();
        BlockState blockState = world.getBlockState(blockPos1);
        if (blockState.is(Blocks.AIR) || blockState.is(Blocks.WATER) || blockState.is(Blocks.LAVA)) {
            world.setBlockAndUpdate(blockPos1, ModBlocks.MAGIC_FROSTED_ICE.get().defaultBlockState());
            itemStack.hurtAndBreak(1, player, (entity) -> {
                entity.broadcastBreakEvent(hand);
            });
            return InteractionResultHolder.sidedSuccess(itemStack, world.isClientSide());
        } else {
            return InteractionResultHolder.fail(itemStack);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack itemStack, @Nullable Level world, List<Component> list, TooltipFlag iTooltipFlag) {
        super.appendHoverText(itemStack, world, list, iTooltipFlag);
        list.add(new TranslatableComponent("explain.lootbag.frost_wand").withStyle(ChatFormatting.AQUA));
    }
}
