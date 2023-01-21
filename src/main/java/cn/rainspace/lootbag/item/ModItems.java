package cn.rainspace.lootbag.item;

import cn.rainspace.lootbag.utils.Const;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Const.MOD_ID);

    public static final RegistryObject<Item> LOOT_BAG = ITEMS.register("loot_bag", () -> new LootBagItem(new Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final RegistryObject<Item> BACKPACK = ITEMS.register("backpack", () -> new BackpackItem(new Properties().tab(CreativeModeTab.TAB_TOOLS).stacksTo(1).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> FROST_WAND = ITEMS.register("frost_wand", () -> new FrostWandItem(new Properties().tab(CreativeModeTab.TAB_TOOLS).durability(128).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> MAGIC_MIRROR = ITEMS.register("magic_mirror",()->new MagicMirrorItem(new Properties().tab(CreativeModeTab.TAB_TRANSPORTATION).stacksTo(1).rarity(Rarity.UNCOMMON)));

}