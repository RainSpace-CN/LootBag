package cn.rainspace.lootbag.item;

import java.util.List;
import java.util.Random;

import cn.rainspace.lootbag.config.Config;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;

public class LootBagItem extends Item {
  public LootBagItem(Properties properties) {
		super(properties);
	}
  
  @Override
  public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
    ItemStack itemstack = player.getItemInHand(hand);
    if(!world.isClientSide){
    	Random random = new Random();
		LootTable table;
		switch (random.nextInt(13)) {
			case 0:
				table = world.getServer().getLootTables().get(LootTables.VILLAGE_ARMORER);
				break;
			case 1:
				table = world.getServer().getLootTables().get(LootTables.SHIPWRECK_TREASURE);
				break;
			case 2:
				table = world.getServer().getLootTables().get(LootTables.JUNGLE_TEMPLE);
				break;
			case 3:
				table = world.getServer().getLootTables().get(LootTables.WOODLAND_MANSION);
				break;
			case 4:
				table = world.getServer().getLootTables().get(LootTables.SIMPLE_DUNGEON);
				break;
			case 5:
				table = world.getServer().getLootTables().get(LootTables.STRONGHOLD_LIBRARY);
				break;
			case 6:
				table = world.getServer().getLootTables().get(LootTables.RUINED_PORTAL);
				break;
			case 7:
				table = world.getServer().getLootTables().get(LootTables.DESERT_PYRAMID);
				break;
			case 8:
				table = world.getServer().getLootTables().get(LootTables.ABANDONED_MINESHAFT);
				break;
			case 9:
				table = world.getServer().getLootTables().get(LootTables.BURIED_TREASURE);
				break;
			case 10:
				table = world.getServer().getLootTables().get(LootTables.BASTION_TREASURE);
				break;
			case 11:
				table = world.getServer().getLootTables().get(LootTables.STRONGHOLD_CORRIDOR);
				break;
			default:
				table = world.getServer().getLootTables().get(LootTables.END_CITY_TREASURE);
				break;
		}
		LootContext context = (new LootContext.Builder((ServerWorld)world)).withLuck(player.getLuck()).withParameter(LootParameters.THIS_ENTITY,player).withParameter(LootParameters.ORIGIN, player.position()).create(LootParameterSets.CHEST);
        List<ItemStack>loot = table.getRandomItems(context);
        while(loot.size()< Config.TIMES.get())loot.addAll(table.getRandomItems(context));
    	for(int i=0;i<Config.TIMES.get();i++) {
    		if(player.inventory.getFreeSlot()>=0)
    			player.addItem(loot.get(i));
    		else
    			player.drop(loot.get(i),true);
    	}
    	itemstack.shrink(1);
    }
    return ActionResult.sidedSuccess(itemstack, world.isClientSide());
  }
}
