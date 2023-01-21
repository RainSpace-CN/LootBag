package cn.rainspace.lootbag.block.entity;

import cn.rainspace.lootbag.block.ModBlocks;
import cn.rainspace.lootbag.utils.Const;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntityType {
    public static final DeferredRegister<BlockEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, Const.MOD_ID);
    public static final RegistryObject<BlockEntityType<BackpackChestBlockEntity>> BACKPACK_CHEST = TILE_ENTITIES.register("backpack_chest", () -> BlockEntityType.Builder.of(BackpackChestBlockEntity::new, ModBlocks.BACKPACK_CHEST.get()).build(null));
    public static final RegistryObject<BlockEntityType<MagicFrostedIceBlockEntity>> MAGIC_FROSTED_ICE = TILE_ENTITIES.register("magic_frosted_ice", () -> BlockEntityType.Builder.of(MagicFrostedIceBlockEntity::new, ModBlocks.MAGIC_FROSTED_ICE.get()).build(null));

}
