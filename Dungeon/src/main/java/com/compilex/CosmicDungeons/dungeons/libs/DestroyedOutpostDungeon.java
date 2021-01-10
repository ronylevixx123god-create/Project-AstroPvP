/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.compilex.CosmicArmorSets.enums.ArmorSet
 *  com.cosmicpvp.cosmicutils.customevents.CustomCosmicPlayerEvent
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.cosmicpvp.cosmicutils.utils.FactionUtils
 *  com.cosmicpvp.cosmicutils.utils.ItemBuilder
 *  com.cosmicpvp.cosmicutils.utils.ItemUtils
 *  com.cosmicpvp.cosmicutils.utils.JSONMessage
 *  com.cosmicpvp.cosmicutils.utils.NBTWrapper
 *  com.cosmicpvp.cosmicutils.utils.PlayerUtil
 *  com.cosmicpvp.cosmicutils.utils.TimeUtils
 *  com.cosmicpvp.cosmicutils.utils.TitleManager$TitleColor
 *  com.massivecraft.factions.Faction
 *  me.ifamasssxd.cosmicbosses.dungeonboss.destroyedoutpost.MotherOfYijki
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.World
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.plugin.Plugin
 */
package com.compilex.CosmicDungeons.dungeons.libs;

import com.compilex.CosmicArmorSets.enums.ArmorSet;
import com.compilex.CosmicDungeons.CosmicDungeonsPlugin;
import com.compilex.CosmicDungeons.DungeonsAPI;
import com.compilex.CosmicDungeons.DungeonsRewardsAPI;
import com.compilex.CosmicDungeons.dungeons.events.DestroyedOutpostEvents;
import com.compilex.CosmicDungeons.dungeons.libs.Dungeon;
import com.compilex.CosmicDungeons.dungeons.libs.DungeonPlayer;
import com.compilex.CosmicDungeons.dungeons.libs.DungeonType;
import com.compilex.CosmicDungeons.utils.WorldGuardUtils;
import com.cosmicpvp.cosmicutils.customevents.CustomCosmicPlayerEvent;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.FactionUtils;
import com.cosmicpvp.cosmicutils.utils.ItemBuilder;
import com.cosmicpvp.cosmicutils.utils.ItemUtils;
import com.cosmicpvp.cosmicutils.utils.JSONMessage;
import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import com.cosmicpvp.cosmicutils.utils.PlayerUtil;
import com.cosmicpvp.cosmicutils.utils.TimeUtils;
import com.massivecraft.factions.Faction;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import me.ifamasssxd.cosmicbosses.dungeonboss.destroyedoutpost.MotherOfYijki;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class DestroyedOutpostDungeon
extends Dungeon {
    public MotherOfYijki boss;
    public boolean bossPendingRespawn = false;
    public boolean hasBeenDefeated = false;

    public boolean isBossPendingRespawn() {
        return this.bossPendingRespawn;
    }

    public void setBossPendingRespawn(boolean bossPendingRespawn) {
        this.bossPendingRespawn = bossPendingRespawn;
    }

    public DestroyedOutpostDungeon(World dungeonWorld, String dungeonName, LinkedList<String> dungeonDescription) {
        super(dungeonWorld, DungeonType.DESTROYED_OUTPOST, dungeonName, dungeonDescription);
        this.setSpawnLocation(new Location(dungeonWorld, 267.0, 208.0, -11.0));
        this.hasBeenDefeated = CosmicDungeonsPlugin.get().getConfig().getBoolean("destroyedOutpostDefeated", false);
    }

    @Override
    public void reset() {
        super.reset();
        if (this.boss != null && this.boss.isBossSpawned()) {
            this.boss.getEntity().remove();
        }
        this.boss = null;
    }

    public ItemStack createJunkReturningItem() {
        return new NBTWrapper(new ItemBuilder(Material.WATCH).name(CC.GoldB + "Clock of Returning " + CC.Gray + "(Right Click)").lore("", CC.Gray + "Capable of transporting you", CC.Gray + "back to the portal dimension.").build()).setString("cosmicType", "dungeonJunkOfReturning").build();
    }

    public ItemStack createReturningClock() {
        return new NBTWrapper(new ItemBuilder(Material.WATCH).name(CC.GoldB + "Clock of Returning " + CC.Gray + "(Right Click)").lore("", CC.Gray + "Capable of transporting you", CC.Gray + "back to the Mother of Yijki's", CC.Gray + "cave, from the Lava Realm.").build()).setString("cosmicType", "dungeonClockOfReturning").build();
    }

    public boolean isJunkOfReturning(ItemStack item) {
        return item != null && item.getType() == Material.WATCH && new NBTWrapper(item).getString("cosmicType").equals("dungeonJunkOfReturning");
    }

    public boolean isClockOfReturning(ItemStack item) {
        return item != null && item.getType() == Material.WATCH && new NBTWrapper(item).getString("cosmicType").equals("dungeonClockOfReturning");
    }

    public void onDungeonComplete() {
        HashSet<String> factionIdsToCredit = new HashSet<String>();
        String playerList = "";
        boolean firstToComplete = !this.hasBeenDefeated;
        boolean armorSetsEnabled = Bukkit.getPluginManager().isPluginEnabled("CosmicArmorSets");
        if (firstToComplete) {
            CosmicDungeonsPlugin.get().getConfig().set("destroyedOutpostDefeated", (Object)true);
            CosmicDungeonsPlugin.get().saveConfig();
            this.hasBeenDefeated = true;
            Bukkit.getLogger().info("[CosmicDungeons] Destroyed Outpost Dungeon defeated for the first time! Giving Dimensional Armor..");
        }
        HashSet<UUID> validLooters = new HashSet<UUID>();
        List<Player> winners = this.getPlayers();
        for (Player p : winners) {
            Faction faction;
            ItemStack reward;
            DungeonPlayer dp = DungeonsAPI.get().getPlayerDungeonData(p);
            if (dp == null || dp.getCurrentDungeon() == null || !dp.getCurrentDungeon().equals(this)) continue;
            Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), ("contests add " + p.getName() + " dungeonsCompleted 1"));
            Bukkit.getPluginManager().callEvent((Event)new CustomCosmicPlayerEvent(p, "DUNGEON_COMPLETE", new Object[]{this.dungeonType.name()}));
            if (!dp.getCooldowns().canPlayerDoDungeon(this)) {
                p.sendMessage((Object)ChatColor.RED + "" + (Object)ChatColor.BOLD + "(!) " + (Object)ChatColor.RED + "You cannot receive rewards from the " + this.getDungeonName() + (Object)ChatColor.RED + " dungeon for " + com.compilex.CosmicDungeons.utils.TimeUtils.formatDifference(dp.getCooldowns().getCooldown(this)) + ".");
                continue;
            }
            if (!WorldGuardUtils.getRegionName(p.getLocation()).equals("victory_ship")) {
                p.sendMessage((Object)ChatColor.RED + "" + (Object)ChatColor.BOLD + "(!) " + (Object)ChatColor.RED + "You were not standing in the victory ship of the dungeon, so you do not get a reward!");
                continue;
            }
            validLooters.add(p.getUniqueId());
            p.sendMessage((Object)ChatColor.GOLD + "" + (Object)ChatColor.BOLD + "+1 Dungeon Loot Bag");
            if (p.getInventory().firstEmpty() != -1) {
                p.getInventory().addItem(new ItemStack[]{DungeonsRewardsAPI.getDungeonLootBag(p, this)});
            } else {
                p.getWorld().dropItem(p.getLocation(), DungeonsRewardsAPI.getDungeonLootBag(p, this));
                p.sendMessage((Object)ChatColor.GRAY + "(No room in inventory, dropped on ground.)");
            }
            if (firstToComplete && armorSetsEnabled && (reward = ArmorSet.DIMENSIONAL_TRAVELER.getRandomPeice(true)) != null) {
                PlayerUtil.giveOrDropProtectedItem((Player)p, (ItemStack)reward, (int)-1, null, null);
                JSONMessage msg = new JSONMessage("");
                msg.addText((Player)null, CC.GoldB + "+1 ", ChatColor.GOLD);
                msg.addItem(reward, CC.DarkPurple + ItemUtils.getDisplayName((ItemStack)reward), ChatColor.DARK_PURPLE);
                msg.addText((Player)null, CC.Gold + " (" + CC.GoldB + "SRC: " + CC.Gold + "Planet First)", ChatColor.GREEN);
                msg.sendToPlayer(p);
                Bukkit.getLogger().info("[CosmicDungeons] Giving " + p.getName() + " Planet First Completion reward: " + reward.toString());
            }
            playerList = playerList + p.getName() + ", ";
            long bonusCD = dp.getCooldowns().addDungeonCooldown(this, 300000L);
            dp.logDungeonComplete(this);
            if (bonusCD > 0L) {
                Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)CosmicDungeonsPlugin.get_(), () -> p.sendMessage(CC.YellowB + "-" + TimeUtils.formatSeconds((long)(bonusCD / 1000L)) + CC.Yellow + "'s on your next Dungeon Loot cooldown! " + CC.GoldB + "(" + CC.Gold + "Heroic Dungeon Looter /f upgrade" + CC.GoldB + ")"), 3L);
            }
            if ((faction = FactionUtils.getFaction((Player)p)) == null || !faction.isNormal()) continue;
            factionIdsToCredit.add(faction.getTag());
        }
        if (playerList.endsWith(", ")) {
            playerList = playerList.substring(0, playerList.length() - 2);
        }
        // todo: titlemanager
        //this.broadcastTitle("DUNGEON COMPLETE!", "+1 Dungeon Lootbag", TitleManager.TitleColor.GREEN, TitleManager.TitleColor.GRAY);
        Bukkit.broadcastMessage("");
        Bukkit.broadcastMessage((ChatColor.GOLD + "" + ChatColor.BOLD + "*-*-* DUNGEON COMPLETE! *-*-*"));
        Bukkit.broadcastMessage((ChatColor.GOLD + "The " + this.getDungeonName() + (Object)ChatColor.GOLD + " has been cleared by: " + (Object)ChatColor.GRAY + playerList + (Object)ChatColor.GOLD + " in " + com.compilex.CosmicDungeons.utils.TimeUtils.formatDifference(this.getTimeSinceDungeonStart())));
        this.getTopDungeonTime().checkLatestTime((int)(this.getTimeSinceDungeonStart() / 1000L), this, winners, validLooters);
        if (firstToComplete) {
            for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
                pl.sendMessage("");
                pl.sendMessage(CC.Gold + "As a reward for being the 1st group to defeat the Destroyed Outpost this map, all members of the party have received a bonus piece of the " + CC.DarkPurple + "Dimensional Traveler Armor Set" + CC.Gold + "!");
            }
        }
        Bukkit.broadcastMessage("");
        factionIdsToCredit.forEach(tag -> Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), ("fquests add DUNGEON_MASTER " + tag)));
    }

    @Override
    public DestroyedOutpostEvents getDungeonEvents() {
        return (DestroyedOutpostEvents)super.getDungeonEvents();
    }
}

