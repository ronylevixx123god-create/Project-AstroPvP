package me.astropvp.astrotournaments.inventories;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.exoticpvp.exoticarmorsets.armor.enums.ArmorSetType;

import me.galacticpvp.galacticpvputils.nbt.NBTWrapper;
import me.galacticpvp.galacticpvputils.struct.InventoryWrapper;
import me.galacticpvp.galacticpvputils.utils.CC;
import me.galacticpvp.galacticpvputils.utils.ItemBuilder;

public class ChooseSetInventory {
	public static Inventory getInventory() {
		InventoryWrapper invWrapper = new InventoryWrapper(Bukkit.createInventory(null, 18, CC.Gray + "Tournament: Choose your set"));
		int pos = 0;
		for(ArmorSetType set : Arrays.asList(ArmorSetType.DIMENSIONAL_TRAVELER, ArmorSetType.DRAGON_SLAYER, ArmorSetType.ENGINEER, 
		ArmorSetType.EXOTIC, ArmorSetType.MOTHER_OF_YIJKI, ArmorSetType.PHANTOM, ArmorSetType.RANGER, ArmorSetType.YETI)) {
			invWrapper.setContent(getClaimSetItem(set), pos++);
		}
		return invWrapper.get();
	}
	
	private static ItemStack getClaimSetItem(ArmorSetType set) {
		short id = (short)(set == ArmorSetType.MOTHER_OF_YIJKI ? 0 : set == ArmorSetType.DRAGON_SLAYER ? 4 :
		set == ArmorSetType.ENGINEER ? 1 : set == ArmorSetType.YETI ? 3 : set == ArmorSetType.RANGER ? 13 : 
	    set == ArmorSetType.EXOTIC ? 6 : set == ArmorSetType.DIMENSIONAL_TRAVELER ? 10 : set == ArmorSetType.PHANTOM ? 14 : 
	    set == ArmorSetType.SUPREME ? 15 : 0);
		ItemStack item = new ItemBuilder(Material.STAINED_GLASS_PANE).damage(id).displayname(set.getColor() + CC.Bold + set.getName() + " Armor Set")
		.lore(CC.Gray + "Click to choose the " + set.getName() + " Armor Set").build();
		NBTWrapper wrapper = new NBTWrapper(item);
		wrapper.setString("t_choose_set", set.name());
		return wrapper.build();
	}
}
