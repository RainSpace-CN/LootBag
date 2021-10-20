package cn.rainspace.lootbag.inventory.container;

import cn.rainspace.lootbag.utils.Const;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModContainerType {
    public static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, Const.MOD_ID);
    public static final RegistryObject<ContainerType<BackpackChestContainer>> BACKPACK_CHEST_CONTAINER = CONTAINERS.register("backpack_chest_container", () ->
            IForgeContainerType.create((int windowId, PlayerInventory inv, PacketBuffer data) ->
                    new BackpackChestContainer(windowId, inv, new Inventory(27), 3)));
}
