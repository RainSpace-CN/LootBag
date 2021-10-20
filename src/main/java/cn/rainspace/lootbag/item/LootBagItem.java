package cn.rainspace.lootbag.item;

import cn.rainspace.lootbag.config.Config;
import cn.rainspace.lootbag.loot.ModLootTables;
import cn.rainspace.lootbag.utils.MobRelation;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.LootTable;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
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
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (!world.isClientSide) {
            LootTable table = world.getServer().getLootTables().get(ModLootTables.LOOT_BAG_GIFT);
            LootContext context = (new LootContext.Builder((ServerWorld) world)).withLuck(player.getLuck()).withParameter(LootParameters.THIS_ENTITY, player).withParameter(LootParameters.ORIGIN, player.position()).create(LootParameterSets.GIFT);
            List<ItemStack> loot = table.getRandomItems(context);
            for (int i = 0; i < loot.size(); i++) {
                GiveItem(player, loot.get(i));
            }
            itemstack.shrink(1);
        }
        return ActionResult.sidedSuccess(itemstack, world.isClientSide());
    }

    public boolean GiveItem(PlayerEntity player, ItemStack itemStack) {
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
        public static void onLivingDeath(LivingDeathEvent event) {
            LivingEntity entity = event.getEntityLiving();
            if (determineRelation(entity) == MobRelation.Relation.FOE && entity.getLastHurtByMob() instanceof PlayerEntity) {
                Random random = new Random();
                if (random.nextInt(100) < Config.DROP_CHANCE.get())
                    entity.spawnAtLocation(ModItems.lootBag.get());
            }
        }
    }

}
