package cn.rainspace.lootbag.tileentity;

import cn.rainspace.lootbag.inventory.container.BackpackChestContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.tileentity.ChestTileEntity;

public class BackpackChestTileEntity extends ChestTileEntity {

    public BackpackChestTileEntity() {
        super(ModTileEntityType.BACKPACK_CHEST.get());
    }

    @Override
    protected Container createMenu(int p_213906_1_, PlayerInventory p_213906_2_) {
        return new BackpackChestContainer(p_213906_1_, p_213906_2_, this, 3);
    }

    @Override
    public boolean stillValid(PlayerEntity player) {
        return true;
    }
}
