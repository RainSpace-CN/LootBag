package cn.rainspace.lootbag.config;

import com.google.common.collect.ImmutableList;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.List;

public class Config {
    public static ForgeConfigSpec SERVER_CONFIG;
    public static ForgeConfigSpec.IntValue DROP_CHANCE;
    public static ForgeConfigSpec.BooleanValue ONLY_DROP_BY_NATURAL_ENTITY;
    public static ForgeConfigSpec.BooleanValue BIOME_MODE;

    public static ForgeConfigSpec.ConfigValue<List<? extends String>> BLACK_LIST;

    static {
        ForgeConfigSpec.Builder SERVER_BUILDER = new ForgeConfigSpec.Builder();
        SERVER_BUILDER.comment("General settings").push("general");
        DROP_CHANCE = SERVER_BUILDER
                .comment("Drop chance of Loot Bag")
                .defineInRange("Chance", 4, 0, 100);
        ONLY_DROP_BY_NATURAL_ENTITY = SERVER_BUILDER
                .comment("Only drop Loot Bag by natural entity")
                .define("onlyDropByNaturalEntity", true);
        BIOME_MODE = SERVER_BUILDER
                .comment("Spawn different Loot Bag in different biomes")
                .define("BiomeMode", true);
        BLACK_LIST = SERVER_BUILDER
                .comment("Disable some items from Loot Bag")
                .define("BlackList", ImmutableList.of("minecraft:example_item"), Config::isStringList);
        SERVER_BUILDER.pop();

        SERVER_CONFIG = SERVER_BUILDER.build();
    }

    private static boolean isStringList(Object obj) {
        if (obj instanceof List<?>) {
            for (Object entry : (List<?>) obj) {
                if (!(entry instanceof String)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

}
