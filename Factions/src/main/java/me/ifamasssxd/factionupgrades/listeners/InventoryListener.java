/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.massivecraft.factions.FPlayer
 *  com.massivecraft.factions.FPlayers
 *  com.massivecraft.factions.Faction
 *  com.massivecraft.factions.struct.Role
 *  me.ifamasssxd.cosmicconquest.CosmicConquestAPI
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.OfflinePlayer
 *  org.bukkit.Sound
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event$Result
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.me.astropvp.cratekeys.inventory.InventoryClickEvent
 *  org.bukkit.me.astropvp.cratekeys.inventory.ItemStack
 */
package me.ifamasssxd.factionupgrades.listeners;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.P;
import com.massivecraft.factions.struct.Role;
import me.ifamasssxd.cosmicconquest.CosmicConquestAPI;
import me.ifamasssxd.factionupgrades.FactionUpgradeAPI;
import me.ifamasssxd.factionupgrades.struct.FactionUpgrade;
import me.ifamasssxd.factionupgrades.struct.FactionUpgradeInfo;
import me.ifamasssxd.factionupgrades.struct.Upgrade;
import me.ifamasssxd.factionupgrades.struct.UpgradeManager;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.text.DecimalFormat;
import java.util.Arrays;

public class InventoryListener
implements Listener {
    private final DecimalFormat format = new DecimalFormat("#,###");

    @EventHandler
    public void onInventoryListener(InventoryClickEvent event) {
        Player player = (Player)event.getWhoClicked();
        if (event.getInventory().getName().equals("Faction Upgrades")) {
            event.setCancelled(true);
            event.setResult(Event.Result.DENY);
            ItemStack item = event.getCurrentItem();
            if (item == null || item.getType() == Material.AIR || !item.hasItemMeta() || !item.getItemMeta().hasDisplayName()) {
                return;
            }
            if (event.getRawSlot() > event.getInventory().getSize() - 1) {
                return;
            }
            FPlayer fplayer = FPlayers.i.get(player);
            boolean canChange = fplayer.getRole().isAtLeast(Role.COLEADER);
            if (!fplayer.hasFaction() || !fplayer.getRole().isAtLeast(Role.COLEADER)) {
                player.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "(!)" + ChatColor.YELLOW + " Only faction (co)leaders can edit /f upgrades!");
                return;
            }
            Faction faction = fplayer.getFaction();
            String name = ChatColor.stripColor(item.getItemMeta().getDisplayName());
            UpgradeManager upgradeManager = P.getP().getUpgradeManager();
            FactionUpgrade upgrade = Arrays.stream(FactionUpgrade.values()).filter(upgr -> upgr.getUpgrade().getDisplayName(0).equalsIgnoreCase(name)).findFirst().orElse(null);
            if (upgrade == null) {
                return;
            }
            Upgrade upgr2 = upgrade.getUpgrade();
            FactionUpgradeInfo info = upgradeManager.getFactionUpgradeInfo(faction);
            if (info == null) {
                if (!canChange) {
                    player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "(!)" + ChatColor.RED + " Only faction (co)leaders can edit /f upgrades!");
                    return;
                }
                info = new FactionUpgradeInfo(faction.getId());
                upgradeManager.getUpgradeInfo().put(faction.getId(), info);
            }
            int currentTier = info.getUpgradeTier(upgrade);
            int nextTier = currentTier + 1;
            double cash = upgrade.getTokenCost(nextTier);
            int tokenCost = upgrade.getTokenCost(nextTier);
            if (currentTier >= upgr2.getMaxUpgradeLevel()) {
                player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "(!) " + ChatColor.RED + "That upgrade is already max level!");
                return;
            }
            if (tokenCost > 0 && !CosmicConquestAPI.hasTokensInInventory(player, tokenCost)) {
                player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "(!) " + ChatColor.RED + "You do not have " + tokenCost + "x Faction Crystals!");
                return;
            }
            if (cash > 0.0 && !P.getP().getEcon().has(player, cash)) {
                player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "(!) " + ChatColor.RED + "You do not have enough /bal $ to purchase this upgrade!");
                player.sendMessage(ChatColor.GRAY + "Upgrade Cost: " + ChatColor.UNDERLINE + "$" + this.format.format(cash));
                return;
            }
            if (tokenCost > 0) {
                player.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD + "- " + this.format.format(tokenCost) + ChatColor.RED + "x Faction Crystals");
                CosmicConquestAPI.removeTokensFromInventory(player, tokenCost);
            }
            if (cash > 0.0) {
                player.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD + "- $" + ChatColor.RED + this.format.format(cash));
                P.getP().getEcon().withdrawPlayer((OfflinePlayer)player, cash);
            }
            for (FPlayer fplayers : faction.getFPlayers()) {
                if (!fplayers.isOnline()) continue;
                if (currentTier == 0) {
                    fplayers.sendMessage("");
                    fplayers.sendMessage(ChatColor.AQUA + ChatColor.BOLD.toString() + "/f upgrade Unlock");
                    fplayers.sendMessage(ChatColor.GRAY.toString() + ChatColor.BOLD + "** " + ChatColor.GRAY + player.getName() + " has unlocked Tier 1 of the " + ChatColor.AQUA + ChatColor.BOLD + upgr2.getDisplayName(0) + ChatColor.GREEN + " /f upgrade!");
                    fplayers.sendMessage("");
                } else {
                    fplayers.sendMessage("");
                    fplayers.sendMessage(ChatColor.GREEN + ChatColor.BOLD.toString() + "/f upgrade Upgrade");
                    fplayers.sendMessage(ChatColor.GRAY.toString() + player.getName() + " has increased your faction's " + ChatColor.GREEN + ChatColor.BOLD + upgr2.getDisplayName(currentTier) + ChatColor.GRAY + " /f upgrade to Tier " + nextTier + "!");
                    fplayers.sendMessage("");
                }
                fplayers.getPlayer().playSound(fplayers.getPlayer().getLocation(), Sound.LEVEL_UP, 1.0f, 0.8f);
            }
            info.setUpgradeTier(upgrade, nextTier);
            FactionUpgradeAPI.processPerkPurchase(faction, upgrade, nextTier);
            player.playSound(player.getLocation(), Sound.LEVEL_UP, 1.0f, 0.9f);
            player.updateInventory();
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "fquests add F_UPGRADES " + faction.getTag());
        }
    }
}

