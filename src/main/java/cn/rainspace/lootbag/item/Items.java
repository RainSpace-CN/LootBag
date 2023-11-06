package cn.rainspace.lootbag.item;

import cn.rainspace.lootbag.LootBag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class Items {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, LootBag.MOD_ID);

    public static final RegistryObject<Item> LOOT_BAG = ITEMS.register("loot_bag", () -> new LootBagItem(new Properties().stacksTo(1)));
    public static final RegistryObject<Item> BACKPACK = ITEMS.register("backpack", () -> new BackpackItem(new Properties().stacksTo(1).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> FROST_WAND = ITEMS.register("frost_wand", () -> new FrostWandItem(new Properties().durability(128).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> MAGIC_MIRROR = ITEMS.register("magic_mirror", () -> new MagicMirrorItem(new Properties().stacksTo(1).rarity(Rarity.UNCOMMON)));

}