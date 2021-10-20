package cn.rainspace.lootbag.item;

import cn.rainspace.lootbag.utils.Const;
import net.minecraft.item.Item;
import net.minecraft.item.Item.Properties;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Const.MOD_ID);

    public static final RegistryObject<Item> lootBag = ITEMS.register("loot_bag", () -> new LootBagItem(new Properties().tab(ItemGroup.TAB_MISC)));
    public static final RegistryObject<Item> BACKPACK = ITEMS.register("backpack", () -> new BackpackItem(new Properties().tab(ItemGroup.TAB_MISC).stacksTo(1)));

}