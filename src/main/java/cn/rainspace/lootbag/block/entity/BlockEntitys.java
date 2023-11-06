package cn.rainspace.lootbag.block.entity;

import cn.rainspace.lootbag.block.Blocks;
import cn.rainspace.lootbag.utils.Const;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockEntitys {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Const.MOD_ID);
    public static final RegistryObject<BlockEntityType<BackpackChestBlockEntity>> BACKPACK_CHEST = BLOCK_ENTITIES.register("backpack_chest", () -> BlockEntityType.Builder.of(BackpackChestBlockEntity::new, Blocks.BACKPACK_CHEST.get()).build(null));
    public static final RegistryObject<BlockEntityType<MagicFrostedIceBlockEntity>> MAGIC_FROSTED_ICE = BLOCK_ENTITIES.register("magic_frosted_ice", () -> BlockEntityType.Builder.of(MagicFrostedIceBlockEntity::new, Blocks.MAGIC_FROSTED_ICE.get()).build(null));

}
