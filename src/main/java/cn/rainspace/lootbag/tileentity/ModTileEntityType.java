package cn.rainspace.lootbag.tileentity;

import cn.rainspace.lootbag.block.ModBlocks;
import cn.rainspace.lootbag.Global;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModTileEntityType {
	public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, Global.MOD_ID);
	
	public static final RegistryObject<TileEntityType<WorldAnchorTileEntity>> worldAnchorTileEntity = TILE_ENTITIES.register("world_anchor_tileentity", ()->TileEntityType.Builder.of(WorldAnchorTileEntity::new, ModBlocks.worldAnchorBlock.get()).build(null));
}
