package cn.rainspace.lootbag.tileentity;

import cn.rainspace.lootbag.block.ModBlocks;
import cn.rainspace.lootbag.Global;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModTileEntityType {
	public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, Global.MOD_ID);
}
