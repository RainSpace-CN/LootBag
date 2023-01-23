package cn.rainspace.lootbag.item;

import cn.rainspace.lootbag.config.Config;
import cn.rainspace.lootbag.loot.ModLootTables;
import net.minecraft.nbt.StringTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
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
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (!world.isClientSide) {
            LootTable table = world.getServer().getLootTables().get(ModLootTables.LOOT_BAG_GIFT);
            LootContext context = (new LootContext.Builder((ServerLevel) world)).withLuck(player.getLuck()).withParameter(LootContextParams.THIS_ENTITY, player).withParameter(LootContextParams.TOOL, itemstack).withParameter(LootContextParams.ORIGIN, player.position()).create(LootContextParamSets.GIFT);
            List<ItemStack> loot = table.getRandomItems(context);
            for (ItemStack itemStack : loot) {
                boolean shouldGet = true;
                for (String id : Config.BLACK_LIST.get()) {
                    if (id.equals(itemStack.getItem().builtInRegistryHolder().key().location().toString())) {
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
        return InteractionResultHolder.sidedSuccess(itemstack, world.isClientSide());
    }

    public boolean giveItem(Player player, ItemStack itemStack) {
        if (player.getInventory().getFreeSlot() >= 0) {
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
            MobSpawnType mobSpawnType = event.getSpawnReason();
            if (mobSpawnType == MobSpawnType.NATURAL) {
                event.getEntityLiving().addTag("natural");
            }
        }

        @SubscribeEvent
        public static void onLivingDeath(LivingDeathEvent event) {
            LivingEntity entity = event.getEntityLiving();
            if (!entity.getType().getCategory().isFriendly() && event.getSource().getDirectEntity() instanceof Player && (!Config.ONLY_DROP_BY_NATURAL_ENTITY.get() || entity.getTags().contains("natural"))) {
                Random random = new Random();
                if (random.nextInt(100) < Config.DROP_CHANCE.get()) {
                    ItemStack itemStack = new ItemStack(ModItems.LOOT_BAG.get());
                    entity.getLevel().getBiome(entity.getOnPos()).getTagKeys().forEach((e) -> {
                        itemStack.addTagElement(e.location().toString(), StringTag.valueOf(e.location().toString()));
                    });
                    entity.spawnAtLocation(itemStack);
                }
            }
        }
    }

}
