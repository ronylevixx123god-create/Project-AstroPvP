/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.CosmicSpaceChests.CosmicSpaceChestsAPI
 *  com.cosmicpvp.CosmicSpaceChests.libs.ItemRarity
 *  com.gmail.nossr50.events.experience.McMMOPlayerXpGainEvent
 *  com.massivecraft.factions.Board
 *  com.massivecraft.factions.FLocation
 *  com.massivecraft.factions.FPlayer
 *  com.massivecraft.factions.FPlayers
 *  com.massivecraft.factions.Faction
 *  com.massivecraft.factions.FactionChunkOwnershipCache
 *  me.ifamasssxd.cosmicfactionboosters.BoosterAPI
 *  me.ifamasssxd.cosmicfactionboosters.me.astropvp.cratekeys.enums.BoosterType
 *  me.ifamasssxd.killtracker.KillTrackerAPI
 *  org.arkhamnetwork.Arkkit.Arkkit
 *  org.arkhamnetwork.enchantments.EnchantmentBook
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.OfflinePlayer
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.entity.CreatureSpawnEvent
 *  org.bukkit.event.entity.CreatureSpawnEvent$SpawnReason
 *  org.bukkit.event.entity.EntityDeathEvent
 *  org.bukkit.me.astropvp.cratekeys.inventory.ItemStack
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 */
package com.compilex.CosmicOutposts.listeners;

import com.compilex.CosmicKOTH.CosmicKOTHPlugin;
import com.compilex.CosmicOutposts.CosmicOutpostsAPI;
import com.compilex.CosmicOutposts.utils.MonsterUtils;
import com.cosmicpvp.CosmicSpaceChests.CosmicSpaceChestsAPI;
import com.cosmicpvp.CosmicSpaceChests.libs.ItemRarity;
import com.cosmicpvp.cosmicutils.utils.FactionUtils;
import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import java.util.Random;
import me.ifamasssxd.cosmicfactionboosters.BoosterAPI;
import me.ifamasssxd.cosmicfactionboosters.enums.BoosterType;
import me.ifamasssxd.killtracker.KillTrackerAPI;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.enchantments.EnchantmentBook;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

public class OutpostRewardsListener
implements Listener {
    @EventHandler(priority=EventPriority.LOW)
    public void onEntityDeath(EntityDeathEvent e) {
        if (e.getEntity() instanceof Player) {
            return;
        }
        if (e.getEntity().getKiller() != null) {
            Faction killerFaction = FPlayers.getInstance().getByPlayer(e.getEntity().getKiller()).getFaction();
            if (Bukkit.getPluginManager().isPluginEnabled("CosmicFactionBoosters") && BoosterAPI.hasActiveBooster(killerFaction, BoosterType.EXP)) {
                return;
            }
            if (killerFaction.isNormal()) {
                double xpMod = 1.0;
                if (CosmicOutpostsAPI.controlsTraineeOutpost(e.getEntity().getKiller())) {
                    xpMod = 1.25;
                }
                if (CosmicOutpostsAPI.controlsHeroOutpost(e.getEntity().getKiller())) {
                    xpMod = 1.5;
                }
                if (CosmicOutpostsAPI.controlsVanillaOutpost(e.getEntity().getKiller())) {
                    xpMod = 1.75;
                }
                if (CosmicOutpostsAPI.controlsCosmonautOutpost(e.getEntity().getKiller())) {
                    xpMod = 2.0;
                }
                if (xpMod > 1.0) {
                    Player p;
                    Player le = e.getEntity().getKiller();
                    if (le instanceof Player && (!(p = le).hasMetadata("CosmicOutposts.lastXpBuffAlert") || System.currentTimeMillis() - p.getMetadata("CosmicOutposts.lastXpBuffAlert").get(0).asLong() > 10000L)) {
                        p.setMetadata("CosmicOutposts.lastXpBuffAlert", new FixedMetadataValue((Plugin) CosmicKOTHPlugin.get_(), System.currentTimeMillis()));
                        p.sendMessage(ChatColor.GREEN + ChatColor.BOLD.toString() + "+ " + xpMod + "x XP " + ChatColor.GREEN + "(" + killerFaction.getTag() + "'s Astro Outpost " + ChatColor.GRAY + "[Tier: " + CosmicOutpostsAPI.getOutpostPlayerControls(p).getOutpostTier().chatPrefix + "]" + ChatColor.GREEN + ")");
                    }
                    e.setDroppedExp((int)Math.round((double)e.getDroppedExp() * xpMod));
                }
            }
        }
    }




    @EventHandler
    public void onSoulDemonDeath(EntityDeathEvent e) {
        if (e.getEntity().hasMetadata("soulDemon")) {
            e.getDrops().clear();
            e.getEntity().getEquipment().clear();
            if (Math.random() < 0.65) {
                e.getDrops().add(KillTrackerAPI.createSoulShard(new Random().nextInt(50) + 20));
            } else if (Math.random() < 0.25) {
                e.getDrops().add(EnchantmentBook.getSecretDust(ItemRarity.SOUL, new Random().nextInt(50) + 1));
            } else {
                e.getDrops().add(CosmicSpaceChestsAPI.getItems(Math.random() < 0.2 ? ItemRarity.LEGENDARY : ItemRarity.ULTIMATE, 1)[0]);
            }
            CosmicKOTHPlugin.get_().getCosmonautOutpost().getRewardTask().mobKilled = true;
        }
        if (e.getEntity().hasMetadata("vanillaOutpostMonster")) {
            e.getDrops().clear();
            e.getEntity().getEquipment().clear();
            ItemStack drop = MonsterUtils.getVanillaMonsterDrop();
            if (drop != null) {
                e.getDrops().add(drop);
            }
            CosmicKOTHPlugin.get_().getVanillaOutpost().getVanillaMonsterTask().mobKilled = true;
        }
    }
}

