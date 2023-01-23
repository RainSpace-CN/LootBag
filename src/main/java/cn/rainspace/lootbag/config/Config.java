package cn.rainspace.lootbag.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class Config {
    public static ForgeConfigSpec SERVER_CONFIG;
    public static ForgeConfigSpec.IntValue DROP_CHANCE;
    public static ForgeConfigSpec.BooleanValue ONLY_DROP_BY_NATURAL_ENTITY;

    static {
        ForgeConfigSpec.Builder SERVER_BUILDER = new ForgeConfigSpec.Builder();
        SERVER_BUILDER.comment("General settings").push("general");
        DROP_CHANCE = SERVER_BUILDER.comment("Drop chance of Loot Bag").defineInRange("Chance", 4, 0, 100);
        ONLY_DROP_BY_NATURAL_ENTITY = SERVER_BUILDER.define("onlyDropByNaturalEntity", true);
        SERVER_BUILDER.pop();
        SERVER_CONFIG = SERVER_BUILDER.build();
    }

}
