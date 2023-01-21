package cn.rainspace.lootbag.item;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.ITeleporter;

import javax.annotation.Nullable;
import java.util.List;

public class MagicMirrorItem extends Item {
    public MagicMirrorItem(Properties properties) {
        super(properties);
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (!level.isClientSide()) {
            ServerPlayer serverPlayer = (ServerPlayer) player;
            BlockPos blockPos = serverPlayer.getRespawnPosition();
            if (blockPos == null) {
                serverPlayer.displayClientMessage(Component.translatable("message.lootbag.cannotFindRespawnPosition"), true);
                return InteractionResultHolder.consume(itemStack);
            }
            ServerLevel serverLevel = level.getServer().getLevel(serverPlayer.getRespawnDimension());
            ServerLevel serverLevel1 = serverLevel != null ? serverLevel : level.getServer().overworld();
            if (level != serverLevel1) {
                player.changeDimension(serverLevel1, new ITeleporter() {
                    @Override
                    public boolean isVanilla() {
                        return false;
                    }
                });
            }
            serverPlayer.moveTo(blockPos.getX(), blockPos.getY(), blockPos.getZ());
            level.playSound(null, blockPos, SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS, 1.0F, 1.0F);
            serverLevel1.playSound(null, blockPos, SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS, 1.0F, 1.0F);
        }
        return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide());
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack itemStack, @Nullable Level world, List<Component> list, TooltipFlag iTooltipFlag) {
        super.appendHoverText(itemStack, world, list, iTooltipFlag);
        list.add(Component.translatable("explain.lootbag.magic_mirror").withStyle(ChatFormatting.AQUA));
    }
}
