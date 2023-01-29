package cn.rainspace.lootbag.inventory;

import net.minecraft.core.NonNullList;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;

public class LootBagInventory extends SimpleContainer {

    public LootBagInventory(NonNullList<ItemStack> items) {
        super(27);
        for (int i = 0; i < items.size(); i++) {
            this.setItem(i, items.get(i));
        }
    }
}
