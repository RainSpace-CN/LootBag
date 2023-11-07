package cn.rainspace.lootbag.item;

import cn.rainspace.lootbag.LootBag;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class CreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, LootBag.MOD_ID);

    public static final RegistryObject<CreativeModeTab> EXAMPLE_TAB = CREATIVE_MODE_TABS.register("example_tab", () -> CreativeModeTab.builder()
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .title(Component.translatable("item.lootbag.loot_bag"))
            .icon(() -> Items.LOOT_BAG.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(Items.LOOT_BAG.get()); // Add the example item to the tab. For your own tabs, this method is preferred over the event
                output.accept(Items.MAGIC_MIRROR.get());
                output.accept(Items.FROST_WAND.get());
                output.accept(Items.BACKPACK.get());
            }).build());
}
