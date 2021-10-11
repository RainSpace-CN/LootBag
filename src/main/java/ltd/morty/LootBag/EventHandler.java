package ltd.morty.LootBag;

import static ltd.morty.LootBag.utils.MobRelation.*;

import java.util.Random;

import ltd.morty.LootBag.config.Config;
import ltd.morty.LootBag.item.ModItems;
import ltd.morty.LootBag.utils.MobRelation.Relation;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber()
public class EventHandler {
	@SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        LivingEntity entity = event.getEntityLiving();
        if(determineRelation(entity)==Relation.FOE&&entity.getLastHurtByMob() instanceof PlayerEntity) {
        	Random random = new Random();
        	if(random.nextInt(100)<Config.DROP_CHANCE.get())
        	entity.spawnAtLocation(ModItems.lootBag.get());
        }
    }
}