package cn.rainspace.lootbag.event;

import cn.rainspace.lootbag.config.Config;
import cn.rainspace.lootbag.item.ModItems;
import cn.rainspace.lootbag.utils.MobRelation;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;

import static cn.rainspace.lootbag.utils.MobRelation.determineRelation;

@Mod.EventBusSubscriber()
public class LootBagEvent {
    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        LivingEntity entity = event.getEntityLiving();
        if(determineRelation(entity)== MobRelation.Relation.FOE&&entity.getLastHurtByMob() instanceof PlayerEntity) {
            Random random = new Random();
            if(random.nextInt(100)< Config.DROP_CHANCE.get())
                entity.spawnAtLocation(ModItems.lootBag.get());
        }
    }
}
