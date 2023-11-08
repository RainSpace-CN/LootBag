package cn.rainspace.lootbag.loot;

import cn.rainspace.lootbag.LootBag;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class LootConditions {

    public static final DeferredRegister<LootItemConditionType> LOOT_CONDITIONS = DeferredRegister.create(Registries.LOOT_CONDITION_TYPE, LootBag.MOD_ID);
    public static RegistryObject<LootItemConditionType> BIOME_CHECK = LOOT_CONDITIONS.register("biome_condition", () -> BiomeCheck.BIOME_CHECK);
}
