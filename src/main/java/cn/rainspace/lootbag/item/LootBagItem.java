package cn.rainspace.lootbag.item;

import cn.rainspace.lootbag.config.Config;
import cn.rainspace.lootbag.loot.ModLootTables;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.LootTable;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.Random;

public class LootBagItem extends Item {
    public LootBagItem(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (!world.isClientSide) {
            LootTable table = world.getServer().getLootTables().get(ModLootTables.LOOT_BAG_GIFT);
            LootContext context = (new LootContext.Builder((ServerWorld) world))
                    .withLuck(player.getLuck())
                    .withParameter(LootParameters.THIS_ENTITY, player)
                    .withParameter(LootParameters.TOOL, itemstack)
                    .withParameter(LootParameters.ORIGIN, player.position())
                    .create(LootParameterSets.GIFT);
            List<ItemStack> loot = table.getRandomItems(context);
            for (ItemStack itemStack : loot) {
                boolean shouldGet = true;
                for (String id : Config.BLACK_LIST.get()) {
                    if (id.equals(itemStack.getItem().getRegistryName().toString())) {
                        shouldGet = false;
                        break;
                    }
                }
                if (shouldGet) {
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
            DamageSource source = event.getSource();
            Entity owner = null;
            if (source instanceof IndirectEntityDamageSource) {
                owner = source.getEntity();
            } else if (source instanceof EntityDamageSource) {
                owner = source.getDirectEntity();
            }
            if (!entity.getType().getCategory().isFriendly() && owner instanceof PlayerEntity && (!Config.ONLY_DROP_BY_NATURAL_ENTITY.get() || entity.getTags().contains("natural"))) {
                Random random = new Random();
                if (random.nextInt(100) < Config.DROP_CHANCE.get()) {
                    ItemStack itemStack = new ItemStack(ModItems.LOOT_BAG.get());
                    String biomeName = entity.level.getBiome(entity.blockPosition()).getRegistryName().toString();
                    String dimensionType = entity.level.dimensionType().effectsLocation().toString();
                    itemStack.addTagElement("biomeName", StringNBT.valueOf(biomeName));
                    itemStack.addTagElement("dimensionType", StringNBT.valueOf(dimensionType));
                    entity.spawnAtLocation(itemStack);
                }
            }
        }
    }

}
