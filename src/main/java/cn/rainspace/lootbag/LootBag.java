package cn.rainspace.lootbag;

import cn.rainspace.lootbag.block.Blocks;
import cn.rainspace.lootbag.block.entity.BlockEntitys;
import cn.rainspace.lootbag.container.menu.ModMenuType;
import cn.rainspace.lootbag.gui.screen.BackpackChestScreen;
import cn.rainspace.lootbag.item.CreativeTabs;
import cn.rainspace.lootbag.item.Items;
import cn.rainspace.lootbag.loot.BiomeCheck;
import cn.rainspace.lootbag.loot.LootTables;
import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.storage.loot.LootDataManager;
import net.minecraft.world.level.Level;
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
import net.minecraftforge.registries.RegisterEvent;
import org.slf4j.Logger;

import java.util.Set;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(LootBag.MODID)
public class LootBag {

    // Define mod id in a common place for everything to reference
    public static final String MODID = "lootbag";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();
    // Create a Deferred Register to hold Blocks which will all be registered under the "lootbag" namespace
    public LootBag() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the Deferred Register to the mod event bus so blocks get registered
        Blocks.BLOCKS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so items get registered
        Items.ITEMS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so tabs get registered
        CreativeTabs.CREATIVE_MODE_TABS.register(modEventBus);
        BlockEntitys.BLOCK_ENTITIES.register(modEventBus);
        ModMenuType.CONTAINERS.register(modEventBus);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, cn.rainspace.lootbag.config.Config.SERVER_CONFIG);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }



    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    @SubscribeEvent
    public static void registerLootData(RegisterEvent event) {
        if (!event.getRegistryKey().equals(Registries.LOOT_CONDITION_TYPE)){
            return;
        }
        event.register(Registries.LOOT_CONDITION_TYPE, new ResourceLocation("lootbag:biome_condition"), () -> BiomeCheck.BIOME_CHECK);
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

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
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
                MenuScreens.register(ModMenuType.BACKPACK_CHEST_CONTAINER.get(), BackpackChestScreen::new);
            });
        }

    }
}
