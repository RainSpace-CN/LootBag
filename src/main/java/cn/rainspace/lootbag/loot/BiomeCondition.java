package cn.rainspace.lootbag.loot;

import cn.rainspace.lootbag.config.Config;
import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.reflect.TypeToken;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.ILootSerializer;
import net.minecraft.loot.LootConditionType;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.StringNBT;

import java.lang.reflect.Type;
import java.util.List;

public class BiomeCondition implements ILootCondition {
    public static final LootConditionType BIOME_CHECK = new LootConditionType(new BiomeCondition.Serializer());
    private final List<String> biomeTagList;

    public BiomeCondition(List<String> biomeTagList) {
        this.biomeTagList = biomeTagList;
    }

    @Override
    public LootConditionType getType() {
        return BIOME_CHECK;
    }

    @Override
    public boolean test(LootContext lootContext) {
        if (!Config.BIOME_MODE.get()) {
            return true;
        }
        ItemStack itemstack = lootContext.getParamOrNull(LootParameters.TOOL);
        if (itemstack == null) {
            return false;
        }
        CompoundNBT tag = itemstack.getTag();
        if (tag != null) {
            StringNBT biomeName = (StringNBT) tag.get("biomeName");
            if (biomeName == null)
                return false;
            return biomeTagList.contains(biomeName.getAsString());
        }
        return false;
    }

    public static class Serializer implements ILootSerializer<BiomeCondition> {
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
