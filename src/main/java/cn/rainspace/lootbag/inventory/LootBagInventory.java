package cn.rainspace.lootbag.inventory;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class LootBagInventory extends Inventory {
    public LootBagInventory(NonNullList<ItemStack> items) {
        super(27);
        for (int i = 0; i < items.size(); i++) {
            this.setItem(i, items.get(i));
        }
    }

}
