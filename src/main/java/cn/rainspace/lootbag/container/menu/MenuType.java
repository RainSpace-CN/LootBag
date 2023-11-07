package cn.rainspace.lootbag.container.menu;

import cn.rainspace.lootbag.LootBag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MenuType {
    public static final DeferredRegister<net.minecraft.world.inventory.MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, LootBag.MOD_ID);
    public static final RegistryObject<net.minecraft.world.inventory.MenuType<BackpackChestMenu>> BACKPACK_CHEST_CONTAINER = CONTAINERS.register("backpack_chest_container", () ->
            IForgeMenuType.create((int windowId, Inventory inv, FriendlyByteBuf data) ->
                    new BackpackChestMenu(windowId, inv, new SimpleContainer(27), 3)));
}
