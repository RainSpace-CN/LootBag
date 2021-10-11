package ltd.morty.LootBag.block;

import ltd.morty.LootBag.Global;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Global.MOD_ID);
    public static final RegistryObject<Block> worldAnchorBlock = BLOCKS.register("world_anchor_block", ()-> new WorldAnchorBlock(AbstractBlock.Properties.of(Material.STONE).strength(5)));
}