/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.gui.CosmicGUI
 *  com.cosmicpvp.cosmicutils.gui.InventoryItem
 *  com.cosmicpvp.cosmicutils.struct.factions.points.FactionPointUtils
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.cosmicpvp.cosmicutils.utils.FactionUtils
 *  com.cosmicpvp.cosmicutils.utils.ItemBuilder
 *  com.cosmicpvp.cosmicutils.utils.JSONMessage
 *  com.cosmicpvp.cosmicutils.utils.NumberUtils
 *  com.massivecraft.factions.FPlayer
 *  com.massivecraft.factions.Faction
 *  com.massivecraft.factions.iface.RelationParticipator
 *  com.massivecraft.factions.struct.Relation
 *  com.massivecraft.factions.struct.Role
 *  me.ifamasssxd.cosmicconquest.CosmicConquestAPI
 *  net.minecraft.server.v1_7_R4.ItemStack
 *  net.minecraft.server.v1_7_R4.NBTBase
 *  net.minecraft.server.v1_7_R4.NBTTagCompound
 *  net.minecraft.server.v1_7_R4.NBTTagList
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.DyeColor
 *  org.bukkit.Material
 *  org.bukkit.OfflinePlayer
 *  org.bukkit.Sound
 *  org.bukkit.command.CommandSender
 *  org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 */
package me.ifamasssxd.factionupgrades.struct.menu;

import com.cosmicpvp.cosmicutils.gui.CosmicGUI;
import com.cosmicpvp.cosmicutils.gui.InventoryItem;
import com.cosmicpvp.cosmicutils.struct.factions.points.FactionPointUtils;
import com.cosmicpvp.cosmicutils.utils.*;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.P;
import com.massivecraft.factions.iface.RelationParticipator;
import com.massivecraft.factions.struct.Relation;
import com.massivecraft.factions.struct.Role;
import me.ifamasssxd.cosmicconquest.CosmicConquestAPI;
import me.ifamasssxd.factionupgrades.FactionUpgradeAPI;
import me.ifamasssxd.factionupgrades.struct.*;
import net.minecraft.server.v1_8_R3.NBTBase;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagList;
import org.bukkit.*;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.text.DecimalFormat;

public class FUpgradeMenu
extends CosmicGUI {
    private static DecimalFormat format = new DecimalFormat("#,###");
    private FPlayer fPlayer;
    private Faction overrideToSee;

    public FUpgradeMenu(Player player, Faction factionToSee) {
        super(player, "Faction Upgrades", 54);
        this.fPlayer = FactionUtils.getFPlayer((Player)player);
        this.overrideToSee = factionToSee;
    }

    private boolean canChangeSettings() {
        return this.overrideToSee.getRelationTo((RelationParticipator)this.fPlayer) == Relation.MEMBER && this.fPlayer.getRole().isAtLeast(Role.COLEADER);
    }

    public void redraw() {
        UpgradeManager manager = P.getP().getUpgradeManager();
        FactionUpgradeInfo upgradeInfo = manager.getFactionUpgradeInfo(this.overrideToSee);
        int slot = 0;
        for (FactionUpgrade upgrade : FactionUpgrade.values()) {
            Upgrade upgr = upgrade.getUpgrade();
            ItemStack display = upgradeInfo.getUpgradeTier(upgrade) == upgr.getMaxUpgradeLevel() || upgrade.isHeroic() ? this.addGlow(upgrade.createUpgradeItem(this.overrideToSee.getId(), upgradeInfo)) : upgrade.createUpgradeItem(this.overrideToSee.getId(), upgradeInfo);
            boolean heroic = upgrade.isHeroic();
            if (heroic && slot < 29) {
                slot = 29;
            }
            if (slot == 9) {
                slot = 10;
            }
            if (slot == 34) {
                slot = 38;
            }
            int n = slot++;
            this.setItem(n, new InventoryItem(display).click(() -> {
                Long spawnerValue;
                FactionUpgrade required;
                long valueRequired;
                if (!this.canChangeSettings()) {
                    this.player.sendMessage(CC.YellowB + "(!)" + CC.Yellow + " Only faction (co)leaders can edit /f upgrades!");
                    return;
                }
                int currentTier = upgradeInfo.getUpgradeTier(upgrade);
                int nextTier = currentTier + 1;
                double cash = upgrade.getMoneyCost(nextTier);
                int tokenCost = upgrade.getTokenCost(nextTier);
                int heroicTokenCost = upgrade.getHeroicTokenCost(nextTier);
                long l = valueRequired = heroic && upgr instanceof HeroicUpgrade ? ((HeroicUpgrade)upgr).getFTopSpawnerValueRequirement() : 0L;
                if (currentTier >= upgr.getMaxUpgradeLevel()) {
                    this.player.sendMessage((Object)ChatColor.RED + "" + (Object)ChatColor.BOLD + "(!) " + (Object)ChatColor.RED + "That upgrade is already max level!");
                    return;
                }
                if (!this.canUnlock(upgrade, upgradeInfo) && (required = upgrade.getChildUpgrade()) != null) {
                    this.player.sendMessage(CC.RedB + "(!) " + CC.Red + "You must have " + CC.GoldB + required.getUpgrade().getDisplayName(0) + " " + JSONMessage.getNumber((int)required.getUpgrade().getMaxUpgradeLevel()) + CC.Red + " to unlock this Upgrade!");
                    return;
                }
                if (tokenCost > 0 && !CosmicConquestAPI.hasTokensInInventory((Player)this.player, (int)tokenCost)) {
                    this.player.sendMessage((Object)ChatColor.RED + "" + (Object)ChatColor.BOLD + "(!) " + (Object)ChatColor.RED + "You do not have " + tokenCost + "x Faction Crystals!");
                    return;
                }
                if (cash > 0.0 && !P.getP().getEcon().has((OfflinePlayer)this.player, cash)) {
                    this.player.sendMessage((Object)ChatColor.RED + "" + (Object)ChatColor.BOLD + "(!) " + (Object)ChatColor.RED + "You do not have enough /bal $ to purchase this upgrade!");
                    this.player.sendMessage((Object)ChatColor.GRAY + "Upgrade Cost: " + (Object)ChatColor.UNDERLINE + "$" + format.format(cash));
                    return;
                }
                if (heroicTokenCost > 0 && !CosmicConquestAPI.hasHeroicTokensInInventory((Player)this.player, (int)heroicTokenCost)) {
                    this.player.sendMessage((Object)ChatColor.RED + "" + (Object)ChatColor.BOLD + "(!) " + (Object)ChatColor.RED + "You do not have " + heroicTokenCost + "x Heroic Faction Crystals!");
                    return;
                }
                if (heroic && (spawnerValue = manager.canAccessHeroic(this.overrideToSee, upgrade)) != null) {
                    this.player.sendMessage(CC.RedB + "(!) " + CC.Red + "Your faction must have a /f top Spawner value of at least " + CC.RedB + "$" + CC.Red + format.format(valueRequired) + " to use this /f upgrade!");
                    this.player.sendMessage(CC.GrayB + "Current Spawner Value: $" + CC.Gray + (spawnerValue == -1L ? "0" : format.format(spawnerValue)));
                    return;
                }
                if (heroicTokenCost > 0) {
                    this.player.sendMessage(ChatColor.RED.toString() + (Object)ChatColor.BOLD + "- " + format.format(heroicTokenCost) + (Object)ChatColor.RED + "x Heroic Faction Crystals");
                    CosmicConquestAPI.removeHeroicTokensFromInventory((Player)this.player, (int)heroicTokenCost);
                }
                if (tokenCost > 0) {
                    this.player.sendMessage(ChatColor.RED.toString() + (Object)ChatColor.BOLD + "- " + format.format(tokenCost) + (Object)ChatColor.RED + "x Faction Crystals");
                    CosmicConquestAPI.removeTokensFromInventory((Player)this.player, (int)tokenCost);
                }
                if (cash > 0.0) {
                    this.player.sendMessage(ChatColor.RED.toString() + (Object)ChatColor.BOLD + "- $" + (Object)ChatColor.RED + format.format(cash));
                    P.getP().getEcon().withdrawPlayer((OfflinePlayer)this.player, cash);
                }
                for (FPlayer fplayers : this.overrideToSee.getFPlayers()) {
                    String color;
                    if (!fplayers.isOnline()) continue;
                    String string = color = heroic ? CC.GoldB : CC.AquaB;
                    if (currentTier == 0) {
                        fplayers.sendMessage("");
                        fplayers.sendMessage(color + "/f upgrade Unlock");
                        fplayers.sendMessage(ChatColor.GRAY.toString() + (Object)ChatColor.BOLD + "** " + (Object)ChatColor.GRAY + this.player.getName() + " has unlocked Tier 1 of the " + color + upgr.getDisplayName(0) + (Object)ChatColor.GREEN + " /f upgrade!");
                        fplayers.sendMessage("");
                    } else {
                        fplayers.sendMessage("");
                        fplayers.sendMessage(color + "/f upgrade Upgrade");
                        fplayers.sendMessage(ChatColor.GRAY.toString() + this.player.getName() + " has increased your faction's " + color + upgr.getDisplayName(currentTier) + (Object)ChatColor.GRAY + " /f upgrade to Tier " + nextTier + "!");
                        fplayers.sendMessage("");
                    }
                    fplayers.getPlayer().playSound(fplayers.getPlayer().getLocation(), Sound.LEVEL_UP, 1.0f, 0.8f);
                }
                upgradeInfo.setUpgradeTier(upgrade, nextTier);
                FactionUpgradeAPI.processPerkPurchase(this.overrideToSee, upgrade, nextTier);
                if (FactionPointUtils.isPointsEnabled()) {
                    double d = 0;
                    double rewardPoints = upgrade.getFactionPointsForTier(nextTier);
                    if (d > 0.0) {
                        FactionPointUtils.giveFactionPoints((Faction)this.overrideToSee, (Player)this.player, (String)"f_upgrade", (int)((int)rewardPoints), null);
                        this.overrideToSee.sendMessage(CC.GreenB + "+ " + CC.Green + NumberUtils.formatMoney((double)rewardPoints) + " Faction Points");
                    }
                }
                this.player.playSound(this.player.getLocation(), Sound.LEVEL_UP, 1.0f, 0.9f);
                Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), (String)("fquests add F_UPGRADES " + this.overrideToSee.getTag()));
                this.redraw();
            }));
        }
        for (int i = 8; i < this.size; ++i) {
            ItemStack is = this.inventory.getItem(i);
            if (is != null && is.getType() != Material.AIR) continue;
            this.setItem(i, new ItemBuilder(new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)DyeColor.BLACK.getWoolData())).name(" ").build(), null);
        }
    }

    private boolean canUnlock(FactionUpgrade upgrade, FactionUpgradeInfo info) {
        FactionUpgrade childRequired = upgrade.getChildUpgrade();
        if (childRequired == null) {
            return true;
        }
        int childLevel = info.getUpgradeTier(childRequired);
        return childLevel >= childRequired.getUpgrade().getMaxUpgradeLevel();
    }

    private ItemStack addGlow(ItemStack item) {
        NBTTagCompound tag;
        net.minecraft.server.v1_8_R3.ItemStack nmsStack;
        block3: {
            block2: {
                nmsStack = CraftItemStack.asNMSCopy((ItemStack)item);
                tag = null;
                if (nmsStack.hasTag()) break block2;
                tag = new NBTTagCompound();
                nmsStack.setTag(tag);
                if (tag != null) break block3;
            }
            tag = nmsStack.getTag();
        }
        NBTTagList ench = new NBTTagList();
        tag.set("ench", (NBTBase)ench);
        nmsStack.setTag(tag);
        return CraftItemStack.asCraftMirror((net.minecraft.server.v1_8_R3.ItemStack)nmsStack);
    }
}

