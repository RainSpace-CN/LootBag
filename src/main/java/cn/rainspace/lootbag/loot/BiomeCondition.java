package cn.rainspace.lootbag.loot;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.reflect.TypeToken;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

import java.lang.reflect.Type;
import java.util.List;

public class BiomeCondition implements LootItemCondition {
    public static final LootItemConditionType BIOME_CHECK = new LootItemConditionType(new BiomeCondition.Serializer());
    private final List<String> biomeTagList;

    public BiomeCondition(List<String> biomeTagList) {
        this.biomeTagList = biomeTagList;
    }

    @Override
    public LootItemConditionType getType() {
        return BIOME_CHECK;
    }

    @Override
    public boolean test(LootContext lootContext) {
        ItemStack itemstack = lootContext.getParamOrNull(LootContextParams.TOOL);
        if (itemstack == null) {
            return false;
        }
        CompoundTag tag = itemstack.getTag();
        if (tag != null) {
            for (String tagItem : tag.getAllKeys()) {
                if (biomeTagList.contains(tagItem)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static class Serializer implements net.minecraft.world.level.storage.loot.Serializer<BiomeCondition> {
        private final Type type = new TypeToken<List<String>>() {
        }.getType();

        public void serialize(JsonObject json, BiomeCondition instance, JsonSerializationContext jsonSerializationContext) {

            json.add("biome_tag", new Gson().toJsonTree(instance.biomeTagList, type).getAsJsonArray());
        }

        public BiomeCondition deserialize(JsonObject json, JsonDeserializationContext jsonDeserializationContext) {
            List<String> list = new Gson().fromJson(json.get("biome_tag"), type);
            return new BiomeCondition(list);
        }
    }

}
