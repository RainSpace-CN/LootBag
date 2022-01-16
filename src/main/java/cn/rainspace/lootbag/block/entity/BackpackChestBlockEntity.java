package cn.rainspace.lootbag.block.entity;

import cn.rainspace.lootbag.inventory.container.BackpackChestMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class BackpackChestBlockEntity extends ChestBlockEntity {

    public BackpackChestBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntityType.BACKPACK_CHEST.get(),blockPos,blockState);
    }

    @Override
    public Component getDisplayName() {
        return new TranslatableComponent("container.backpack_chest");
    }

    @Override
    protected AbstractContainerMenu createMenu(int p_213906_1_, Inventory p_213906_2_) {
        return new BackpackChestMenu(p_213906_1_, p_213906_2_, this, 3);
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }
}
