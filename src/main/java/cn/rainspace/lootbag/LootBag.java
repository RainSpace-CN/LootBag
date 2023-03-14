package cn.rainspace.lootbag;

import cn.rainspace.lootbag.block.ModBlocks;
import cn.rainspace.lootbag.config.Config;
import cn.rainspace.lootbag.gui.screen.BackpackChestScreen;
import cn.rainspace.lootbag.inventory.container.ModContainerType;
import cn.rainspace.lootbag.item.ModItems;
import cn.rainspace.lootbag.loot.BiomeCondition;
import cn.rainspace.lootbag.loot.DimensionCondition;
import cn.rainspace.lootbag.loot.ModLootTables;
import cn.rainspace.lootbag.tileentity.ModTileEntityType;
import cn.rainspace.lootbag.utils.Const;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.loot.LootTableManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Set;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Const.MOD_ID)
public class LootBag {
    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();

    public LootBag() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::registerLootData);
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        ModItems.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModBlocks.BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModTileEntityType.TILE_ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModContainerType.CONTAINERS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SERVER_CONFIG);
    }

    private void registerLootData(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            Registry.register(Registry.LOOT_CONDITION_TYPE, new ResourceLocation("lootbag:biome_condition"), BiomeCondition.BIOME_CHECK);
            Registry.register(Registry.LOOT_CONDITION_TYPE, new ResourceLocation("lootbag:dimension_condition"), DimensionCondition.DIMENSION_CHECK);
        });
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        // do something that can only be done on the client
        //LOGGER.info("Got game settings {}", event.getMinecraftSupplier().get().options);
        event.enqueueWork(() -> {
            ScreenManager.register(ModContainerType.BACKPACK_CHEST_CONTAINER.get(), BackpackChestScreen::new);
        });
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class WorldEvents {
        @SubscribeEvent
        public static void onLoad(final WorldEvent.Load event) {
            World world = (World) event.getWorld();
            if (world instanceof ServerWorld) {
                Set<ResourceLocation> lootTables = ModLootTables.all();
                LootTableManager manager = world.getServer().getLootTables();
                lootTables.forEach((e) -> {
                    MinecraftForge.EVENT_BUS.post(new LootTableLoadEvent(e, manager.get(e), manager));
                });
            }
        }
    }
}
