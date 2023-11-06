package cn.rainspace.lootbag.loot;

import cn.rainspace.lootbag.config.Config;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.IntRange;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraft.world.level.storage.loot.predicates.TimeCheck;
import org.apache.logging.log4j.util.Strings;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public record BiomeCheck(Optional<String> biomeTagList) implements LootItemCondition {
    public static final Codec<BiomeCheck> CODEC = RecordCodecBuilder.create((instance) -> instance.group(ExtraCodecs.strictOptionalField(Codec.STRING, "biome_tag").forGetter(BiomeCheck::biomeTagList)).apply(instance, BiomeCheck::new));
    public static final LootItemConditionType BIOME_CHECK = new LootItemConditionType(CODEC);

    @Override
    public @NotNull LootItemConditionType getType() {
        return BIOME_CHECK;
    }

    @Override
    public boolean test(LootContext lootContext) {
        if (!Config.BIOME_MODE.get()) {
            return true;
        }
        ItemStack itemstack = lootContext.getParamOrNull(LootContextParams.TOOL);
        if (itemstack == null) {
            return false;
        }
        CompoundTag tag = itemstack.getTag();
        if (tag != null) {
            for (String tagItem : tag.getAllKeys()) {
                if (Arrays.asList(biomeTagList.orElse("").split(",")).contains(tagItem)) {
                    return true;
                }
            }
        }
        return false;
    }
}
