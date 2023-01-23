package cn.rainspace.lootbag.item;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.ITeleporter;

import javax.annotation.Nullable;
import java.util.List;

public class MagicMirrorItem extends Item {
    public MagicMirrorItem(Properties properties) {
        super(properties);
    }

    public ActionResult<ItemStack> use(World level, PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (!level.isClientSide()) {
            ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;
            BlockPos blockPos = serverPlayer.getRespawnPosition();
            if (blockPos == null) {
                serverPlayer.displayClientMessage(new TranslationTextComponent("message.lootbag.cannotFindRespawnPosition"), true);
                return ActionResult.consume(itemStack);
            }
            ServerWorld serverLevel = level.getServer().getLevel(serverPlayer.getRespawnDimension());
            ServerWorld serverLevel1 = serverLevel != null ? serverLevel : level.getServer().overworld();
            if (level != serverLevel1) {
                player.changeDimension(serverLevel1, new ITeleporter() {
                    @Override
                    public boolean isVanilla() {
                        return false;
                    }
                });
            }
            serverPlayer.moveTo(blockPos.getX(), blockPos.getY(), blockPos.getZ());
            level.playSound(null, blockPos, SoundEvents.ENDERMAN_TELEPORT, SoundCategory.PLAYERS, 1.0F, 1.0F);
            serverLevel1.playSound(null, blockPos, SoundEvents.ENDERMAN_TELEPORT, SoundCategory.PLAYERS, 1.0F, 1.0F);
        }
        return ActionResult.sidedSuccess(itemStack, level.isClientSide());
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack itemStack, @Nullable World world, List<ITextComponent> list, ITooltipFlag iTooltipFlag) {
        super.appendHoverText(itemStack, world, list, iTooltipFlag);
        list.add(new TranslationTextComponent("explain.lootbag.magic_mirror").withStyle(TextFormatting.AQUA));
    }
}
