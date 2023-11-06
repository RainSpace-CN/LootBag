package cn.rainspace.lootbag.block;

import cn.rainspace.lootbag.block.entity.BlockEntitys;
import cn.rainspace.lootbag.utils.Const;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class Blocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Const.MOD_ID);
    public static final RegistryObject<Block> BACKPACK_CHEST = BLOCKS.register("backpack_chest", () -> new BackpackChestBlock(Block.Properties.of().mapColor(MapColor.STONE).strength(-1.0F, 3600000.0F).noLootTable(), () -> BlockEntitys.BACKPACK_CHEST.get()));
    public static final RegistryObject<Block> MAGIC_FROSTED_ICE = BLOCKS.register("magic_frosted_ice", () -> new MagicFrostedIceBlock(Block.Properties.of().mapColor(MapColor.ICE).friction(0.98F).instabreak().sound(SoundType.GLASS).noLootTable()));

}
