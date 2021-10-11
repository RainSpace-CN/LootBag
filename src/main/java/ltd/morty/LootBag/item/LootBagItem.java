package ltd.morty.LootBag.item;

import java.util.List;
import java.util.Random;

import ltd.morty.LootBag.config.Config;
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
    	LootTable table = switch (random.nextInt(13)) {
			case 0 -> world.getServer().getLootTables().get(LootTables.VILLAGE_ARMORER);
			case 1 -> world.getServer().getLootTables().get(LootTables.SHIPWRECK_TREASURE);
			case 2 -> world.getServer().getLootTables().get(LootTables.JUNGLE_TEMPLE);
			case 3 -> world.getServer().getLootTables().get(LootTables.WOODLAND_MANSION);
			case 4 -> world.getServer().getLootTables().get(LootTables.SIMPLE_DUNGEON);
			case 5 -> world.getServer().getLootTables().get(LootTables.STRONGHOLD_LIBRARY);
			case 6 -> world.getServer().getLootTables().get(LootTables.RUINED_PORTAL);
			case 7 -> world.getServer().getLootTables().get(LootTables.DESERT_PYRAMID);
			case 8 -> world.getServer().getLootTables().get(LootTables.ABANDONED_MINESHAFT);
			case 9 -> world.getServer().getLootTables().get(LootTables.BURIED_TREASURE);
			case 10 -> world.getServer().getLootTables().get(LootTables.BASTION_TREASURE);
			case 11 -> world.getServer().getLootTables().get(LootTables.STRONGHOLD_CORRIDOR);
			default -> world.getServer().getLootTables().get(LootTables.END_CITY_TREASURE);
		};
		LootContext context = (new LootContext.Builder((ServerWorld)world)).withLuck(player.getLuck()).withParameter(LootParameters.THIS_ENTITY,player).withParameter(LootParameters.ORIGIN, player.position()).create(LootParameterSets.CHEST);
        List<ItemStack>loot = table.getRandomItems(context);
        while(loot.size()<Config.TIMES.get())loot.addAll(table.getRandomItems(context));
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
