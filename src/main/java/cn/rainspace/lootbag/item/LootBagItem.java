package cn.rainspace.lootbag.item;

import cn.rainspace.lootbag.config.Config;
import cn.rainspace.lootbag.inventory.LootBagInventory;
import cn.rainspace.lootbag.inventory.container.LootBagContainer;
import cn.rainspace.lootbag.loot.ModLootTables;
import cn.rainspace.lootbag.tileentity.LootBagTileEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.LootTable;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.List;
import java.util.Random;

public class LootBagItem extends Item {
    public LootBagItem(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (!world.isClientSide && hand == Hand.MAIN_HAND) {
            LootTable table = world.getServer().getLootTables().get(ModLootTables.LOOT_BAG_GIFT);
            LootContext context = (new LootContext.Builder((ServerWorld) world))
                    .withLuck(player.getLuck())
                    .withParameter(LootParameters.THIS_ENTITY, player)
                    .withParameter(LootParameters.TOOL, itemstack)
                    .withParameter(LootParameters.ORIGIN, player.position())
                    .create(LootParameterSets.GIFT);
            List<ItemStack> loot = table.getRandomItems(context);
            NonNullList<ItemStack> filteredLoot = NonNullList.create();
            for (ItemStack itemStack : loot) {
                boolean shouldGet = true;
                for (String id : Config.BLACK_LIST.get()) {
                    if (id.equals(itemStack.getItem().getRegistryName().toString())) {
                        shouldGet = false;
                        break;
                    }
                }
                if (shouldGet) {
                    filteredLoot.add(itemStack);
                }
            }
            if (Config.SLOT_MODE.get()) {
                NetworkHooks.openGui((ServerPlayerEntity) player, new LootBagTileEntity((id, playerInventory, unused) -> {
                    return new LootBagContainer(ContainerType.GENERIC_9x3, id, playerInventory, new LootBagInventory(filteredLoot), 3);
                }, new TranslationTextComponent("item.lootbag.loot_bag")));
            } else {
                for (ItemStack itemStack : filteredLoot) {
                    giveItem(player, itemStack);
                }
            }
            itemstack.shrink(1);
        }
        return ActionResult.sidedSuccess(itemstack, world.isClientSide());
    }

    public boolean giveItem(PlayerEntity player, ItemStack itemStack) {
        if (player.inventory.getFreeSlot() >= 0) {
            player.addItem(itemStack);
            return true;
        } else {
            player.drop(itemStack, true);
            return false;
        }
    }

    @Mod.EventBusSubscriber()
    public static class LootBagEvent {
        @SubscribeEvent
        public static void onLivingSpawn(LivingSpawnEvent.SpecialSpawn event) {
            SpawnReason mobSpawnType = event.getSpawnReason();
            if (mobSpawnType == SpawnReason.NATURAL) {
                event.getEntityLiving().addTag("natural");
            }
        }

        @SubscribeEvent
        public static void onLivingDeath(LivingDeathEvent event) {
            LivingEntity entity = event.getEntityLiving();
            if (!entity.getType().getCategory().isFriendly() && entity.getLastHurtByMob() instanceof PlayerEntity && (!Config.ONLY_DROP_BY_NATURAL_ENTITY.get() || entity.getTags().contains("natural"))) {
                Random random = new Random();
                if (random.nextInt(100) < Config.DROP_CHANCE.get()) {
                    ItemStack itemStack = new ItemStack(ModItems.LOOT_BAG.get());
                    String biomeName = entity.level.getBiome(entity.blockPosition()).getRegistryName().toString();
                    World world = entity.level;
                    String dimensionTypeString = "";
                    if (world.dimension() == World.OVERWORLD) {
                        dimensionTypeString = "minecraft:overworld";
                    } else if (world.dimension() == World.NETHER) {
                        dimensionTypeString = "minecraft:the_nether";
                    } else if (world.dimension() == World.END) {
                        dimensionTypeString = "minecraft:the_end";
                    }
                    itemStack.addTagElement("biomeName", StringNBT.valueOf(biomeName));
                    itemStack.addTagElement("dimensionType", StringNBT.valueOf(dimensionTypeString));
                    entity.spawnAtLocation(itemStack);
                }
            }
        }
    }

}
