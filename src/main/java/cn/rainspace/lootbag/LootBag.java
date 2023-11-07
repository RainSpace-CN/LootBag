package cn.rainspace.lootbag;

import cn.rainspace.lootbag.block.Blocks;
import cn.rainspace.lootbag.block.entity.BlockEntitys;
import cn.rainspace.lootbag.container.menu.MenuType;
import cn.rainspace.lootbag.gui.screen.BackpackChestScreen;
import cn.rainspace.lootbag.item.CreativeTabs;
import cn.rainspace.lootbag.item.Items;
import cn.rainspace.lootbag.loot.LootConditions;
import cn.rainspace.lootbag.loot.LootTables;
import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootDataManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;

import java.util.Set;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(LootBag.MOD_ID)
@Mod.EventBusSubscriber(modid = LootBag.MOD_ID)
public class LootBag {

    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "lootbag";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    // Create a Deferred Register to hold Blocks which will all be registered under the "lootbag" namespace
    public LootBag() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        Blocks.BLOCKS.register(modEventBus);
        Items.ITEMS.register(modEventBus);
        CreativeTabs.CREATIVE_MODE_TABS.register(modEventBus);
        BlockEntitys.BLOCK_ENTITIES.register(modEventBus);
        MenuType.CONTAINERS.register(modEventBus);
        LootConditions.LOOT_CONDITIONS.register(modEventBus);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, cn.rainspace.lootbag.config.Config.SERVER_CONFIG);
    }

    @SubscribeEvent
    public static void onLoad(final LevelEvent.Load event) {
        Level level = (Level) event.getLevel();
        if (level instanceof ServerLevel) {
            Set<ResourceLocation> lootTables = LootTables.all();
            LootDataManager manager = level.getServer().getLootData();
            lootTables.forEach((e) -> {
                MinecraftForge.EVENT_BUS.post(new LootTableLoadEvent(e, manager.getLootTable(e)));
            });
        }
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void commonSetup(FMLCommonSetupEvent event) {
            // Some common setup code
            LOGGER.info("HELLO FROM COMMON SETUP");
            LOGGER.info("DIRT BLOCK >> {}", ForgeRegistries.BLOCKS.getKey(net.minecraft.world.level.block.Blocks.DIRT));
        }

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            // Some client setup code
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
            event.enqueueWork(() -> {
                MenuScreens.register(MenuType.BACKPACK_CHEST_CONTAINER.get(), BackpackChestScreen::new);
            });
        }

    }
}
