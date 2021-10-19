package cn.rainspace.lootbag.utils;

import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.monster.GhastEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.PhantomEntity;
import net.minecraft.entity.monster.SlimeEntity;
import net.minecraft.entity.passive.AmbientEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.SquidEntity;
import net.minecraft.entity.passive.WaterMobEntity;

public class MobRelation{
	public enum Relation {
		FRIEND, FOE, UNKNOWN
	}

	public static Relation determineRelation(Entity entity) {
		if (entity instanceof MonsterEntity) {
			return Relation.FOE;
		} else if (entity instanceof SlimeEntity) {
		    return Relation.FOE;
		} else if (entity instanceof GhastEntity) {
		    return Relation.FOE;
		} else if (entity instanceof PhantomEntity) {
		    return Relation.FOE;
	    } else if (entity instanceof EnderDragonEntity) {
		    return Relation.FOE;
		} else if (entity instanceof AnimalEntity) {
		    return Relation.FRIEND;
		} else if (entity instanceof SquidEntity) {
		    return Relation.FRIEND;
		} else if (entity instanceof AmbientEntity) {
		    return Relation.FRIEND;
		} else if (entity instanceof AgeableEntity) {
		    return Relation.FRIEND;
		} else if (entity instanceof WaterMobEntity) {
		    return Relation.FRIEND;
		} else {
		    return Relation.UNKNOWN;
		}
	}
}