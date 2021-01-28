package com.compilex.CosmicArmorSets.listeners;

import com.compilex.CosmicArmorSets.CosmicArmorSetsAPI;
import com.compilex.CosmicArmorSets.CosmicArmorSetsPlugin;
import com.compilex.CosmicArmorSets.enums.ArmorSet;
import com.compilex.CosmicArmorSets.enums.HeroicArmorType;
import com.cosmicpvp.cosmicutils.nbt.NBTTagCompoundBuilder;
import com.cosmicpvp.cosmicutils.utils.*;
import org.apache.commons.lang.StringUtils;
import org.arkhamnetwork.Arkkit.patches.ItemID;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugins;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.ThreadLocalRandom;

public class ArmorSetUpgradeListener
        implements Listener {
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK && event.getAction() != Action.RIGHT_CLICK_AIR) {
            return;
        }
        if (event.getAction() != Action.RIGHT_CLICK_AIR && event.isCancelled()) {
            return;
        }
        ItemStack item = event.getItem();
        if (item == null || item.getType() == Material.AIR) {
            return;
        }
        Player player = event.getPlayer();
        NBTWrapper wrapper = new NBTWrapper(item);
        if (wrapper.getString("cosmicType").equals("RANDOM_ARMOR_UPGRADE")) {
            long id;
            event.setCancelled(true);
            if (!player.getWorld().getName().equals(Bukkit.getWorlds().get(0).getName())) {
                player.sendMessage(CC.RedB + "(!) " + CC.Red + "You can only unlock a Armor Upgrade in the Overworld!");
                return;
            }
            if (ArkPlugins.ITEM_ID.getArkPlugin().isRunning() && item.getAmount() == 1 && (id = ItemID.getItemID(item)) != -1L) {
                if (ItemID.blacklist_iid.contains(id)) {
                    player.sendMessage(CC.RedB + "(!) " + CC.Red + "You attempted to claim an already claimed Upgrade!");
                    player.setItemInHand(null);
                    player.updateInventory();
                    Bukkit.getLogger().info(player.getName() + " used a Blacklisted Random Armor Upgrade: " + id + " item: " + item);
                    return;
                }
                ItemID.blacklistIID(id, "random armor upgrade use " + player.getName());
            }
            ArmorSet received = CosmicArmorSetsAPI.getRandomArmorSetFromUpgrade();
            ItemStack retr = CosmicArmorSetsAPI.createArmorSetUpgrade(received, 1 + ThreadLocalRandom.current().nextInt(100));
            if (item.getAmount() > 1) {
                item.setAmount(item.getAmount() - 1);
                PlayerUtil.giveOrDropProtectedItem(player, retr, -1);
            } else {
                player.setItemInHand(retr);
            }
            player.sendMessage(CC.GreenB + "(!) " + CC.Green + "You have received a " + ItemUtils.getDisplayName(retr) + CC.Green + " from your Random Heroic Upgrade!");
            player.updateInventory();
            player.playSound(player.getLocation(), Sound.LEVEL_UP, 1.0f, 1.1f);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onInventoryClick(InventoryClickEvent event) throws Exception {
        if (event.getClick() != ClickType.LEFT && event.getClick() != ClickType.RIGHT) {
            return;
        }
        Player player = (Player) event.getWhoClicked();
        if (event.getWhoClicked().getInventory() == null || !event.getWhoClicked().getInventory().equals(player.getInventory())) {
            return;
        }
        ItemStack clicked = event.getCurrentItem();
        ItemStack cursor = event.getCursor();
        if (cursor == null) {
            return;
        }
        if (clicked == null || clicked.getType() == Material.AIR || !GeneralUtil.isArmor(clicked)) {
            return;
        }
        NBTWrapper cursorWrapper = null;
        String cosmicType = null;
        if (cursor.getType() == Material.INK_SACK && (cosmicType = (cursorWrapper = new NBTWrapper(cursor)).getString("cosmicType")).equals("HEROIC_ARMOR_UPGRADE")) {
            event.setCancelled(true);
            if (!player.getWorld().getName().equals(Bukkit.getWorlds().get(0).getName()) && !player.getName().equals("world_end")) {
                player.sendMessage(CC.Red + "(!) " + CC.Red + "You must apply this Upgrade in the Overworld!");
                return;
            }
            NBTTagCompoundBuilder builder = cursorWrapper.getCompound("conaxData");
            if (builder == null) {
                return;
            }
            String setType = builder.getString("setType");
            if (setType != null && !setType.isEmpty()) {
                boolean correctApplication;
                boolean allSetsAllowed = setType.equalsIgnoreCase("ALL");
                ArmorSet requiredSet = ArmorSet.fromName(setType);
                ArmorSet clickedArmorSet = ArmorSet.getArmorSetFromItem(clicked);
                boolean bl = correctApplication = allSetsAllowed && clickedArmorSet != null || requiredSet != null && requiredSet.equals(clickedArmorSet);
                if (!correctApplication) {
                    player.sendMessage(CC.RedB + "(!) " + CC.Red + "You must apply this Heroic Armor Upgrade to a " + (allSetsAllowed || requiredSet == null ? "" : StringUtils.capitaliseAllWords(requiredSet.name().replace("_", " ").toLowerCase())) + CC.Red + " Armor Set piece!");
                    return;
                }
                HeroicArmorType type = HeroicArmorType.getFromMaterial(clicked.getType());
                if (!clicked.getType().name().contains("DIAMOND_") || type == null) {
                    player.sendMessage(CC.RedB + "(!) " + CC.Red + "You cannot apply this to non diamond armor set pieces!");
                    return;
                }
                Long iid = null;
                if (ArkPlugins.ITEM_ID.getArkPlugin().isRunning() && (iid = ItemID.getItemID(cursor)) != -1L && ItemID.blacklist_iid.contains(iid)) {
                    player.sendMessage(CC.RedB + "(!) " + CC.Red + "That Armor Upgrade has already been used!");
                    player.getInventory().removeItem(cursor);
                    player.setItemOnCursor(null);
                    player.updateInventory();
                    Bukkit.getLogger().info("[CosmicArmorSets] Removing blacklisted armor upgrade from " + player.getName() + ": " + cursor);
                    return;
                }
                double chance = builder.getDouble("success");
                if (chance > 100.0) {
                    chance = 100.0;
                }
                if (chance < 0.0) {
                    chance = 0.0;
                }
                if (cursor.getAmount() > 1) {
                    cursor.setAmount(cursor.getAmount() - 1);
                } else {
                    player.setItemOnCursor(null);
                    player.updateInventory();
                    if (iid != null && ArkPlugins.ITEM_ID.getArkPlugin().isRunning()) {
                        ItemID.blacklistIID(iid, "armorset upgrade " + player.getName() + iid);
                    }
                }
                if (chance >= (double) (ThreadLocalRandom.current().nextInt(100) + 1)) {
                    Bukkit.getLogger().info("Upgrading " + player.getName() + "'s " + clicked + " item to heroic version!");
                    de.inventivegames.particle.ParticleEffect.HAPPY_VILLAGER.sendToPlayers(Bukkit.getOnlinePlayers(), player.getLocation(), 0f, 0.5f, 0f, 0.1f, 30);
                    player.getWorld().playSound(player.getLocation(), Sound.VILLAGER_YES, 1.4f, 0.9f);
                    player.getWorld().playSound(player.getLocation(), Sound.LEVEL_UP, 1.4f, 0.9f);
                    ItemStack retItem = CosmicArmorSetsAPI.convertItemToHeroic(clicked, type, true);
                    NBTWrapper wrapper = new NBTWrapper(retItem);
                    NBTTagCompoundBuilder display = wrapper.hasKey("display") ? new NBTTagCompoundBuilder(wrapper.getNBTBase("display")) : new NBTTagCompoundBuilder();
                    int color = 0;
                    if (clickedArmorSet == ArmorSet.ULTRA) {
                        color = 12104883;
                    } else if (clickedArmorSet == ArmorSet.CHAMPION) {
                        color = 16420882;
                    } else if (clickedArmorSet == ArmorSet.ANCIENT) {
                        color = 3650995;
                    } else if (clickedArmorSet == ArmorSet.MOTHER_OF_YIJKI) {
                        color = 0xFFFFFF;
                    } else if (clickedArmorSet == ArmorSet.KOTH) {
                        color = 31999;
                    } else if (clickedArmorSet == ArmorSet.RANGER) {
                        color = 5765888;
                    } else if (clickedArmorSet == ArmorSet.SUPREME) {
                        color = 8264475;
                    } else if (clickedArmorSet == ArmorSet.DRAGON_SLAYER) {
                        color = 0xFFEE00;
                    } else if (clickedArmorSet == ArmorSet.DIMENSIONAL_TRAVELER) {
                        color = 12001978;
                    } else if (clickedArmorSet == ArmorSet.PHANTOM) {
                        color = 14294340;
                    } else if (clickedArmorSet == ArmorSet.YETI) {
                        color = 518113;
                    } else if (clickedArmorSet == ArmorSet.ENGINEER) {
                        color = 15176967;
                    } else if (clickedArmorSet == ArmorSet.OMNI) {
                        color = 0x76CEF7;
                    }
                    display.setInt("color", color);
                    wrapper.setNBTBase("display", display.get());
                    retItem = wrapper.build();
                    event.setCurrentItem(retItem);
                    player.sendMessage(CC.GreenB + "(!) " + CC.Green + "You have upgraded your " + ItemUtils.getDisplayName(clicked) + CC.Green + " to the Heroic Armor version!");
                } else {
                    player.sendMessage(CC.RedB + "(!) " + CC.Red + "You failed to apply your " + ItemUtils.getDisplayName(cursor) + CC.Red + "!");
                    player.playSound(player.getLocation(), Sound.LAVA_POP, 1.0f, 0.9f);
                    de.inventivegames.particle.ParticleEffect.LAVA.sendToPlayers(Bukkit.getOnlinePlayers(), player.getLocation(), 0f, 0.5f, 0f, 0.1f, 30);
                    CosmicArmorSetsPlugin.get_().getLogger().info(player.getName() + " failed to apply Heroic Armor Upgrade!");
                }
            }
        }
    }
}

