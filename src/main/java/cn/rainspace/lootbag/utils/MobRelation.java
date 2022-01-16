package cn.rainspace.lootbag.utils;


import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ambient.AmbientCreature;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Phantom;
import net.minecraft.world.entity.monster.Slime;

public class MobRelation {
    public static Relation determineRelation(LivingEntity entity) {
        if (entity instanceof Monster) {
            return Relation.FOE;
        } else if (entity instanceof Slime) {
            return Relation.FOE;
        } else if (entity instanceof Ghast) {
            return Relation.FOE;
        } else if (entity instanceof Phantom) {
            return Relation.FOE;
        } else if (entity instanceof EnderDragon) {
            return Relation.FOE;
        } else if (entity instanceof Animal) {
            return Relation.FRIEND;
        } else if (entity instanceof AmbientCreature) {
            return Relation.FRIEND;
        } else if (entity instanceof WaterAnimal) {
            return Relation.FRIEND;
        } else {
            return Relation.UNKNOWN;
        }
    }

    public enum Relation {
        FRIEND, FOE, UNKNOWN
    }
}