package cn.rainspace.lootbag.container;

import cn.rainspace.lootbag.item.Items;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class BackpackSlot extends Slot {

    public BackpackSlot(Container p_i1824_1_, int p_i1824_2_, int p_i1824_3_, int p_i1824_4_) {
        super(p_i1824_1_, p_i1824_2_, p_i1824_3_, p_i1824_4_);
    }

    @Override
    public boolean mayPlace(ItemStack itemStack) {
        return itemStack.getItem() != Items.BACKPACK.get() && itemStack.getItem() != net.minecraft.world.item.Items.SHULKER_BOX;
    }
}