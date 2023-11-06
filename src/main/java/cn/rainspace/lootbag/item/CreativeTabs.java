package cn.rainspace.lootbag.item;

import cn.rainspace.lootbag.LootBag;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class CreativeTabs {
    // Create a Deferred Register to hold CreativeModeTabs which will all be registered under the "examplemod" namespace
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, LootBag.MODID);


    // Creates a creative tab with the id "examplemod:example_tab" for the example item, that is placed after the combat tab
    public static final RegistryObject<CreativeModeTab> EXAMPLE_TAB = CREATIVE_MODE_TABS.register("example_tab", () -> CreativeModeTab.builder()
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> Items.LOOT_BAG.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(Items.LOOT_BAG.get()); // Add the example item to the tab. For your own tabs, this method is preferred over the event
                output.accept(Items.MAGIC_MIRROR.get());
                output.accept(Items.FROST_WAND.get());
                output.accept(Items.BACKPACK.get());
            }).build());
}
