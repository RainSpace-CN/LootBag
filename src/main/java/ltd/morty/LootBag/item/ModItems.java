package ltd.morty.LootBag.item;

import ltd.morty.LootBag.Global;
import ltd.morty.LootBag.block.ModBlocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Item.Properties;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
 
public class ModItems
{
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Global.MOD_ID);
    
    public static final RegistryObject<Item> lootBag = ITEMS.register("loot_bag", ()->new LootBagItem(new Properties().tab(ItemGroup.TAB_MISC)));
    public static final RegistryObject<Item> obsidianBlock = ITEMS.register("world_anchor_block", ()->new BlockItem(ModBlocks.worldAnchorBlock.get(), new Item.Properties().tab(ItemGroup.TAB_DECORATIONS)));
}