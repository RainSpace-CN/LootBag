package cn.rainspace.lootbag.item;

import cn.rainspace.lootbag.block.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class FrostWandItem extends Item {
    public FrostWandItem(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        BlockPos blockPos = player.blockPosition();
        if (blockPos.getY() <= 0 || blockPos.getY() >= 256) {
            return ActionResult.fail(itemStack);
        }
        BlockPos blockPos1 = blockPos.below();
        BlockState blockState = world.getBlockState(blockPos1);
        if (blockState.is(Blocks.AIR) || blockState.is(Blocks.WATER) || blockState.is(Blocks.LAVA)) {
            world.setBlockAndUpdate(blockPos1, ModBlocks.MAGIC_FROSTED_ICE.get().defaultBlockState());
            itemStack.hurtAndBreak(1, player, (entity) -> {
                entity.broadcastBreakEvent(hand);
            });
            return ActionResult.sidedSuccess(itemStack, world.isClientSide());
        } else {
            return ActionResult.fail(itemStack);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack itemStack, @Nullable World world, List<ITextComponent> list, ITooltipFlag iTooltipFlag) {
        super.appendHoverText(itemStack, world, list, iTooltipFlag);
        list.add(new TranslationTextComponent("explain.lootbag.frost_wand").withStyle(TextFormatting.AQUA));
    }
}
