package cn.rainspace.lootbag.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class Config {
	public static ForgeConfigSpec COMMON_CONFIG;
    public static ForgeConfigSpec.IntValue DROP_CHANCE;
    public static ForgeConfigSpec.IntValue TIMES;

    static {
        ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
        COMMON_BUILDER.comment("General settings").push("general");
        DROP_CHANCE = COMMON_BUILDER.comment("Drop chance of Loot Bag").defineInRange("Chance", 2, 0, 100);
        TIMES = COMMON_BUILDER.comment("Nummber of draws from loot tables").defineInRange("Times", 10, 0, Integer.MAX_VALUE);
        COMMON_BUILDER.pop();
        COMMON_CONFIG = COMMON_BUILDER.build();
    }
}
