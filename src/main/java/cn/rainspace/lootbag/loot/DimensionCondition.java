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

public class DimensionCondition implements ILootCondition {

    public static final LootConditionType DIMENSION_CHECK = new LootConditionType(new DimensionCondition.Serializer());
    private final List<String> dimensionList;

    public DimensionCondition(List<String> dimensionList) {
        this.dimensionList = dimensionList;
    }

    @Override
    public LootConditionType getType() {
        return DIMENSION_CHECK;
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
            StringNBT dimensionType = (StringNBT) tag.get("dimensionType");
            if (dimensionType == null)
                return false;
            return dimensionList.contains(dimensionType.getAsString());
        }
        return false;
    }

    public static class Serializer implements ILootSerializer<DimensionCondition> {
        private final Type type = new TypeToken<List<String>>() {
        }.getType();

        public void serialize(JsonObject json, DimensionCondition instance, JsonSerializationContext jsonSerializationContext) {
            json.add("dimension_tag", new Gson().toJsonTree(instance.dimensionList, type).getAsJsonArray());
        }

        public DimensionCondition deserialize(JsonObject json, JsonDeserializationContext jsonDeserializationContext) {
            List<String> list = new Gson().fromJson(json.get("dimension_tag"), type);
            return new DimensionCondition(list);
        }
    }
}
