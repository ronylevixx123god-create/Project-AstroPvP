package com.compilex.CosmicArmorSets.items;

import com.compilex.CosmicArmorSets.enums.ArmorSet;
import com.cosmicpvp.cosmicutils.compat.ASound;
import com.cosmicpvp.cosmicutils.exceptions.CosmicException;
import com.cosmicpvp.cosmicutils.exceptions.PrefixedException;
import com.cosmicpvp.cosmicutils.nbt.NBTTagCompoundBuilder;
import com.cosmicpvp.cosmicutils.nbt.NBTTagListBuilder;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.GeneralUtil;
import com.cosmicpvp.cosmicutils.utils.ItemBuilder;
import com.cosmicpvp.cosmicutils.utils.ItemUtils;
import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import com.cosmicpvp.cosmicutils.utils.NumberUtils;
import com.google.common.collect.Lists;
import com.rit.sucy.CustomEnchantment;
import com.rit.sucy.EnchantmentAPI;
import java.util.List;
import me.ifamasssxd.cosmicskycustoms.exceptions.ItemDupedException;
import me.ifamasssxd.cosmicskycustoms.struct.CosmicItem;
import me.ifamasssxd.cosmicskycustoms.struct.InventoryInteractable;
import me.ifamasssxd.cosmicskycustoms.struct.InventoryItemType;
import net.minecraft.server.v1_7_R4.NBTBase;
import org.arkhamnetwork.Arkkit.patches.ItemID;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugins;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ArmorSetBonusCrystalItem extends CosmicItem implements InventoryInteractable {
	public ArmorSetBonusCrystalItem() {
		super("bonusArmorCrystal");
	}

	public static ItemStack applyCrystalToItem(List<ArmorSet> set, ItemStack item) {
		NBTWrapper wrapper = new NBTWrapper(item);
		NBTTagCompoundBuilder data = wrapper.getCompoundOrCreate("cosmicData");
		NBTTagListBuilder list = new NBTTagListBuilder();
		String loreBuild = "";
		NBTTagListBuilder nbtTagListBuilder = new NBTTagListBuilder();
		StringBuilder sb = new StringBuilder();
		for (ArmorSet s : set) {
			list.addString(s.name());
			loreBuild = loreBuild + s.getDisplayName() + CC.Gray + ", ";
		}
		data.setNBTBase("crystalBonuses", (NBTBase)list.build());
		data.setNBTBase("bonuses", (NBTBase)list.build());
		wrapper.setNBTBase("cosmicData", (NBTBase)data.get());
		item = wrapper.build();
		ItemMeta im = item.getItemMeta();
		List<String> lore = (im.getLore() == null) ? Lists.newArrayList() : im.getLore();
		String str = loreBuild;
		if (str.endsWith(", "))
			str = str.substring(0, str.length() - 2);
		lore.add(CC.GoldB + "Armor Crystal (" + str + CC.GoldB + ")");
		im.setLore(lore);
		item.setItemMeta(im);
		return item;
	}

	public ItemStack createItem(Player player, Object... objects) {
		List<ArmorSet> set = Lists.newArrayList();
		Number chance = null;
		for (Object object : objects) {
			if (object instanceof Number) {
				chance = (Number)object;
			} else if (object instanceof ArmorSet) {
				set.add((ArmorSet)object);
			} else if (object instanceof List) {
				set = (List<ArmorSet>)object;
			} else {
				Number num = NumberUtils.parseNumberOrNull(object.toString());
				if (num != null) {
					chance = num;
				} else {
					ArmorSet fromName = ArmorSet.fromName(object.toString());
					if (fromName != null)
						set.add(fromName);
				}
			}
		}
		if (set.isEmpty() || chance == null)
			return null;
		List<String> lore = Lists.newArrayList(new String[] { CC.Green + chance.intValue() + "% Success Rate", CC.Gray + "Can be applied to any non", CC.Gray + "armor set that is not", CC.Gray + "already equipped with a", CC.Gray + "bonus crystal to gain", CC.Gray + "a passive advantage!", "" });
		lore.add(CC.GoldB + "Crystal Bonus:");
		NBTTagListBuilder list = new NBTTagListBuilder();
		for (ArmorSet s : set) {
			list.addString(s.name());
			lore.add(" " + s.getColor() + CC.Bold + s.getDisplayName());
			List<String> list2 = Lists.newArrayList();
			ArmorSet set2 = s;
			for (String str : s.getCrystalDescription()) {
				lore.add("  " + s.getColor() + CC.Bold + "* " + s.getColor() + str);
			}
		}
		String name = null;
		if (set.size() > 1) {
			StringBuilder builder = new StringBuilder();
			for (ArmorSet found : set)
				builder.append(found.getFirstDisplayNameLetter()).append(CC.Gold).append(", ");
			String combined = builder.toString();
			if (combined.endsWith(", "))
				combined = combined.substring(0, combined.length() - 2);
			name = CC.GoldB + "Multi Armor Set Crystal (" + combined + CC.GoldB + ")";
		} else if (!set.isEmpty()) {
			name = CC.GoldB + "Armor Crystal (" + ((ArmorSet)set.stream().findFirst().orElse(null)).getDisplayName() + CC.GoldB + ")";
		}
		return applyItemData((new ItemBuilder(Material.NETHER_STAR, name, new String[0])).lore(lore).build()).setNBTBase("cosmicData", (NBTBase)(new NBTTagCompoundBuilder()).setNBTBase("bonuses", (NBTBase)list.build()).setDouble("bonusChance", Double.valueOf(chance.doubleValue())).get()).assignIID().build();
	}

	public boolean acceptsInventoryClickType(InventoryItemType inventoryItemType) {
		return (inventoryItemType == InventoryItemType.CURSOR);
	}

	public void onInventoryClick(Player player, ItemStack cursor, NBTWrapper cursorWrapper, NBTWrapper clickedWrapper, ItemStack clicked, InventoryClickEvent event) throws CosmicException {
		if (clickedWrapper == null || !GeneralUtil.isArmor(clicked))
			return;
		event.setCancelled(true);
		NBTTagCompoundBuilder data = cursorWrapper.getCompound("cosmicData");
		if (data == null)
			return;
		List<ArmorSet> sets = ArmorSet.getArmorCrystalBonusesFromCrystal(data);
		if (sets != null) {
			ArmorSet itemSet = ArmorSet.getArmorSetFromItem(clicked);
			if (ArmorSet.getArmorSetFromItem(clicked) != ArmorSet.OMNI) {
				if (itemSet != null) {
					throw new PrefixedException("You cannot apply an Armor Set Crystal to an existing Armor Set piece!");
				}
			}
			if (cursorWrapper.isBlacklistedIID())
				throw new ItemDupedException("Armor set crystal already used!");
			if (!player.getWorld().getName().equals(((World)Bukkit.getWorlds().get(0)).getName()) &&
					!player.getName().equals("world_end"))
				throw new PrefixedException("You must apply this Armor Set Crystal in the Overworld!");
			List<ArmorSet> bonusApplied = ArmorSet.getFromArmorBonusCrystal(clickedWrapper);
			if (bonusApplied != null && !bonusApplied.isEmpty())
				throw new PrefixedException("You cannot apply multiple Armor Set Crystals to a single piece of gear!");
			if (cursor.getAmount() > 1) {
				cursor.setAmount(cursor.getAmount() - 1);
				player.setItemOnCursor(cursor);
			} else {
				player.setItemOnCursor(null);
			}
			if (ArkPlugins.ITEM_ID.getArkPlugin().isRunning())
				ItemID.blacklistIID(cursorWrapper.getIID(), String.valueOf(String.valueOf(player.getName())) + " bonus crystal");
			double chanceToApply = data.hasKey("bonusChance") ? data.getDouble("bonusChance").doubleValue() : 20.0D;
			if (Math.random() > chanceToApply * 0.01D) {
				ASound.GLASS.play(player, 1.1F, 0.8F);
				Bukkit.getLogger().info(String.valueOf(String.valueOf(player.getName())) + " failed to apply " + sets.toString() + " armor set crystal!");
				player.updateInventory();
				throw new PrefixedException("Your Armor Crystal (" +
						ItemUtils.getDisplayName(cursor) + CC.Red + ") fails to apply!");
			}
			event.setCurrentItem(ArmorSet.applyCrystalToItem(sets, clicked));
			player.sendMessage("");
			player.sendMessage(String.valueOf(String.valueOf(CC.GoldB)) + "(!) " + ItemUtils.getDisplayName(cursor) + " " + CC.Gold + "applied to " + clicked
					.getItemMeta().getDisplayName() + CC.Gold + "!");
			player.sendMessage(String.valueOf(String.valueOf(CC.GoldB)) + "Crystal Bonus:");
			for (ArmorSet set : sets) {
				player.sendMessage(" " + set.getDisplayName() + ":");
				set.getCrystalDescription()
						.forEach(de -> player.sendMessage(set.getColor() + CC.Bold + "  * " + set.getColor() + de));
				Bukkit.getLogger()
						.info("Applying " + set.name() + " Set Bonus Crystal to " + ItemUtils.getDisplayName(clicked));
			}
			player.sendMessage("");
			ASound.LEVEL_UP.play(player, 1.1F);
			player.updateInventory();
		}
	}
}
