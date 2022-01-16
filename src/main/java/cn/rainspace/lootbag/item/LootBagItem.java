package cn.rainspace.lootbag.item;

import cn.rainspace.lootbag.config.Config;
import cn.rainspace.lootbag.loot.ModLootTables;
import cn.rainspace.lootbag.utils.MobRelation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.Random;

import static cn.rainspace.lootbag.utils.MobRelation.determineRelation;

public class LootBagItem extends Item {
    public LootBagItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (!world.isClientSide) {
            LootTable table = world.getServer().getLootTables().get(ModLootTables.LOOT_BAG_GIFT);
            LootContext context = (new LootContext.Builder((ServerLevel) world)).withLuck(player.getLuck()).withParameter(LootContextParams.THIS_ENTITY, player).withParameter(LootContextParams.ORIGIN, player.position()).create(LootContextParamSets.GIFT);
            List<ItemStack> loot = table.getRandomItems(context);
            for (int i = 0; i < loot.size(); i++) {
                GiveItem(player, loot.get(i));
            }
            itemstack.shrink(1);
        }
        return InteractionResultHolder.sidedSuccess(itemstack, world.isClientSide());
    }

    public boolean GiveItem(Player player, ItemStack itemStack) {
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
        public static void onLivingDeath(LivingDeathEvent event) {
            LivingEntity entity = event.getEntityLiving();
            if (determineRelation(entity) == MobRelation.Relation.FOE && entity.getLastHurtByMob() instanceof Player) {
                Random random = new Random();
                if (random.nextInt(100) < Config.DROP_CHANCE.get())
                    entity.spawnAtLocation(ModItems.LOOT_BAG.get());
            }
        }
    }

}
