package cn.rainspace.lootbag.block;

import cn.rainspace.lootbag.tileentity.ModTileEntityType;
import cn.rainspace.lootbag.utils.Const;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Const.MOD_ID);
    public static final RegistryObject<Block> BACKPACK_CHEST = BLOCKS.register("backpack_chest", () -> new BackpackChestBlock(AbstractBlock.Properties.of(Material.STONE).strength(-1.0F, 3600000.0F).noDrops(), () -> ModTileEntityType.BACKPACK_CHEST.get()));
}