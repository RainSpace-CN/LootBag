package cn.rainspace.lootbag.tileentity;

import cn.rainspace.lootbag.block.ModBlocks;
import cn.rainspace.lootbag.utils.Const;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModTileEntityType {
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, Const.MOD_ID);
    public static final RegistryObject<TileEntityType<BackpackChestTileEntity>> BACKPACK_CHEST = TILE_ENTITIES.register("backpack_chest", () -> TileEntityType.Builder.of(BackpackChestTileEntity::new, ModBlocks.BACKPACK_CHEST.get()).build(null));

}
