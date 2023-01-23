package cn.rainspace.lootbag.inventory;

import cn.rainspace.lootbag.item.ModItems;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class  BackpackSlot extends Slot {

    public BackpackSlot(IInventory p_i1824_1_, int p_i1824_2_, int p_i1824_3_, int p_i1824_4_) {
        super(p_i1824_1_, p_i1824_2_, p_i1824_3_, p_i1824_4_);
    }

    @Override
    public boolean mayPlace(ItemStack itemStack) {
        return itemStack.getItem() != ModItems.BACKPACK.get() && itemStack.getItem() != Items.SHULKER_BOX;
    }
}
