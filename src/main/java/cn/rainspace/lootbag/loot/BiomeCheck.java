package cn.rainspace.lootbag.loot;

import cn.rainspace.lootbag.config.Config;
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

public class BiomeCheck implements LootItemCondition {
    public static final LootItemConditionType BIOME_CHECK = new LootItemConditionType(new BiomeCheck.Serializer());
    private final List<String> biomeTagList;

    public BiomeCheck(List<String> biomeTagList) {
        this.biomeTagList = biomeTagList;
    }

    @Override
    public LootItemConditionType getType() {
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
                if (biomeTagList.contains(tagItem)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static class Serializer implements net.minecraft.world.level.storage.loot.Serializer<BiomeCheck> {
        private final Type type = new TypeToken<List<String>>() {
        }.getType();

        public void serialize(JsonObject json, BiomeCheck instance, JsonSerializationContext jsonSerializationContext) {

            json.add("biome_tag", new Gson().toJsonTree(instance.biomeTagList, type).getAsJsonArray());
        }

        public BiomeCheck deserialize(JsonObject json, JsonDeserializationContext jsonDeserializationContext) {
            List<String> list = new Gson().fromJson(json.get("biome_tag"), type);
            return new BiomeCheck(list);
        }
    }

}
