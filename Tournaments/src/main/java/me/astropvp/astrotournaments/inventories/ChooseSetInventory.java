package me.astropvp.astrotournaments.inventories;

import java.util.Arrays;

import com.compilex.CosmicArmorSets.enums.ArmorSet;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import me.astropvp.astrotournaments.utils.InventoryWrapper;
import me.astropvp.astrotournaments.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;



public class ChooseSetInventory {
	public static Inventory getInventory() {
		InventoryWrapper invWrapper = new InventoryWrapper(Bukkit.createInventory(null, 18, CC.Gray + "Tournament: Choose your set"));
		int pos = 0;
		for(ArmorSet set : Arrays.asList(ArmorSet.DIMENSIONAL_TRAVELER, ArmorSet.DRAGON_SLAYER, ArmorSet.ENGINEER
		, ArmorSet.MOTHER_OF_YIJKI, ArmorSet.PHANTOM, ArmorSet.RANGER, ArmorSet.YETI)) {
			invWrapper.setContent(getClaimSetItem(set), pos++);
		}
		return invWrapper.get();
	}
	
	private static ItemStack getClaimSetItem(ArmorSet set) {
		short id = (short)(set == ArmorSet.MOTHER_OF_YIJKI ? 0 : set == ArmorSet.DRAGON_SLAYER ? 4 :
		set == ArmorSet.ENGINEER ? 1 : set == ArmorSet.YETI ? 3 : set == ArmorSet.RANGER ? 13 : set == ArmorSet.DIMENSIONAL_TRAVELER ? 10 : set == ArmorSet.PHANTOM ? 14 :
	    set == ArmorSet.SUPREME ? 15 : 0);
		ItemStack item = new ItemBuilder(Material.STAINED_GLASS_PANE).damage(id).displayname(set.getColor() + CC.Bold + set.getDisplayName() + " Armor Set")
		.lore(CC.Gray + "Click to choose the " + set.getDisplayName() + " Armor Set").build();
		NBTWrapper wrapper = new NBTWrapper(item);
		wrapper.setString("t_choose_set", set.name());
		return wrapper.build();
	}
}
