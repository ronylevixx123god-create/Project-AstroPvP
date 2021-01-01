/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.compilex.CosmicArmorSets.CosmicArmorSetsAPI
 *  com.compilex.CosmicArmorSets.enums.ArmorSet
 *  com.cosmicpvp.cosmicutils.CosmicUtils
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.cosmicpvp.cosmicutils.utils.EntityUtils
 *  com.cosmicpvp.cosmicutils.utils.HCFOutpostUtils
 *  com.cosmicpvp.cosmicutils.utils.MaskUtils
 *  com.cosmicpvp.cosmicutils.utils.WorldGuardUtils
 *  com.massivecraft.factions.Board
 *  com.massivecraft.factions.FLocation
 *  com.massivecraft.factions.FPlayer
 *  com.massivecraft.factions.FPlayers
 *  com.massivecraft.factions.Faction
 *  com.massivecraft.factions.struct.Relation
 *  me.ifamasssxd.factionupgrades.FactionUpgradeAPI
 *  me.ifamasssxd.factionupgrades.struct.FactionUpgrade
 *  org.arkhamnetwork.Arkkit.patches.CombatLog
 *  org.bukkit.Bukkit
 *  org.bukkit.Chunk
 *  org.bukkit.GameMode
 *  org.bukkit.Location
 *  org.bukkit.OfflinePlayer
 *  org.bukkit.World$Environment
 *  org.bukkit.entity.Player
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 */
package com.conaxgames.factions.utils.flying;

import com.compilex.CosmicArmorSets.CosmicArmorSetsAPI;
import com.compilex.CosmicArmorSets.enums.ArmorSet;
import com.cosmicpvp.cosmicutils.CosmicUtils;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.EntityUtils;
import com.cosmicpvp.cosmicutils.utils.HCFOutpostUtils;
import com.cosmicpvp.cosmicutils.utils.MaskUtils;
import com.cosmicpvp.cosmicutils.utils.WorldGuardUtils;
import com.massivecraft.factions.*;
import com.massivecraft.factions.struct.Relation;
import java.util.Set;
import me.ifamasssxd.factionupgrades.FactionUpgradeAPI;
import me.ifamasssxd.factionupgrades.struct.FactionUpgrade;
import org.arkhamnetwork.Arkkit.patches.CombatLog;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

public class CosmicFlyingTask
implements Runnable {
    public static boolean flyingOnlyInBases = false;
    public static boolean flyingInAllyBases = false;
    public static boolean flyingInTruceBases = false;
    private boolean factionUpgrades = false;
    private boolean armorSets = Bukkit.getPluginManager().isPluginEnabled("CosmicArmorSets");

    public CosmicFlyingTask() {
        flyingOnlyInBases = P.getP().getConfig().getBoolean("flying-only-in-bases");
        flyingInAllyBases = P.getP().getConfig().getBoolean("flying-in-ally-bases");
        flyingInTruceBases = P.getP().getConfig().getBoolean("flying-in-truce-bases");
        this.factionUpgrades = Bukkit.getPluginManager().isPluginEnabled("FactionUpgrades");
    }

    @Override
    public void run() {
        for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
            boolean inEndWithDragon;
            if ((pl.isOp() || pl.hasPermission("essentials.tpo")) && !pl.getName().equals("Woofless") && !pl.getName().equals("TBNRfrags") && !pl.getName().equals("Lachlan") && !pl.getName().equals("Vikkstar123") || pl.getWorld().getName().equals("world_duels") || pl.getWorld().getName().equals("world_duels2") || pl.getGameMode() == GameMode.CREATIVE) continue;
            boolean inEnd = pl.getWorld().getEnvironment() == World.Environment.THE_END;
            boolean bl = inEndWithDragon = inEnd && EntityUtils.isEndDragonSpawned();
            if (inEnd) {
                if (!inEndWithDragon || !pl.getAllowFlight() && !pl.isFlying()) continue;
                pl.setAllowFlight(false);
                pl.setFlying(false);
                pl.sendMessage(CC.RedB + "(!) " + CC.Red + "You cannot fly while the Timeless Dragon controls the air space!");
                continue;
            }
            boolean inDungeon = pl.getWorld().getName().startsWith("dungeon_");
            if (inDungeon) {
                if (!pl.getAllowFlight() && !pl.isFlying()) continue;
                pl.setAllowFlight(false);
                pl.setFlying(false);
                continue;
            }
            if (pl.hasMetadata("allow_flight") && ((MetadataValue)pl.getMetadata("allow_flight").get(0)).asLong() > System.currentTimeMillis()) continue;
            try {
                Set regions = WorldGuardUtils.getRegionsIn((Location)pl.getLocation());
                if (this.containsRegion(regions, "spawn_", false, true) || regions.contains("spawn")) {
                    pl.setAllowFlight(true);
                    pl.setFlySpeed(0.1f);
                    continue;
                }
                if (this.containsRegion(regions, "parkour", true, false)) continue;
                if (!regions.contains("koth_middle") && this.hasPermission(pl)) {
                    if (!CombatLog.inCombat((Player)pl)) {
                        if (flyingOnlyInBases) {
                            FPlayer fp = (FPlayer)FPlayers.i.get((OfflinePlayer)pl);
                            if (flyingInAllyBases || flyingInTruceBases ? fp.getRelationToLocation().isAtLeast(flyingInTruceBases ? Relation.TRUCE : Relation.ALLY) : fp.getRelationToLocation().isMember()) {
                                pl.setAllowFlight(true);
                                pl.setFlySpeed(0.1f);
                                pl.setMetadata("inClaim", (MetadataValue)new FixedMetadataValue(P.getP(), (Object)true));
                                continue;
                            }
                            pl.setAllowFlight(false);
                            pl.setFlying(false);
                            pl.removeMetadata("inClaim", P.getP());
                            continue;
                        }
                        if (pl.getWorld().getName().startsWith("cosmic-station")) {
                            pl.setAllowFlight(false);
                            pl.setFlying(false);
                            continue;
                        }
                        pl.setAllowFlight(true);
                        pl.setFlySpeed(0.1f);
                        continue;
                    }
                    pl.setAllowFlight(false);
                    pl.setFlying(false);
                    continue;
                }
                pl.setAllowFlight(false);
                pl.setFlying(false);
            }
            catch (Exception err) {
                err.printStackTrace();
            }
        }
    }

    private boolean containsRegion(Set<String> region, String str, boolean contains, boolean startsWith) {
        for (String rg : region) {
            if (startsWith && rg.startsWith(str)) {
                return true;
            }
            if (rg.equals(str)) {
                return true;
            }
            if (!contains || !rg.contains(str)) continue;
            return true;
        }
        return false;
    }

    private boolean hasPermission(Player pl) {
        boolean has_permission = pl.hasPermission("essentials.fly");
        if (!has_permission && this.armorSets) {
            try {
                if (CosmicArmorSetsAPI.getArmorSet((Player)pl) == ArmorSet.SUPREME) {
                    has_permission = true;
                }
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        if (!has_permission && MaskUtils.hasMaskEquipped((Player)pl, (String)"REINDEER")) {
            has_permission = true;
        }
        boolean inCombat = CombatLog.inCombat((Player)pl);
        if (!has_permission && !inCombat && this.factionUpgrades) {
            Faction f = this.getFactionAtLocationCached(pl);
            if (f == null || !f.attached()) {
                f = Board.getFactionAt((FLocation)new FLocation(pl.getLocation()));
                pl.setMetadata("cachedFactionAtLocation", (MetadataValue)new FixedMetadataValue(P.getP(), (Object)f));
                pl.setMetadata("cachedLastChunk", (MetadataValue)new FixedMetadataValue(P.getP(), (Object)pl.getLocation().getChunk()));
            }
            if (f.isNormal()) {
                Relation relationToLocation = ((FPlayer)FPlayers.i.get((OfflinePlayer)pl)).getRelationToLocation();
                int flyPerkTier = FactionUpgradeAPI.getPerkLevel((Faction)f, (FactionUpgrade)FactionUpgrade.ENHANCED_FLIGHT);
                if (flyPerkTier >= 1 && relationToLocation.isMember()) {
                    has_permission = true;
                }
                if (flyPerkTier >= 2 && relationToLocation.isAlly()) {
                    has_permission = true;
                }
                if (flyPerkTier >= 3 && relationToLocation.isTruce()) {
                    has_permission = true;
                }
            }
        }
        if (!has_permission && !inCombat && CosmicUtils.get().isCosmicGalaxy() && HCFOutpostUtils.doesControlOutpost((Player)pl, (String)"infernal")) {
            has_permission = true;
        }
        return has_permission;
    }

    private Faction getFactionAtLocationCached(Player p) {
        if (p.hasMetadata("cachedLastChunk") && ((Chunk)((MetadataValue)p.getMetadata("cachedLastChunk").get(0)).value()).equals((Object)p.getLocation().getChunk())) {
            return p.hasMetadata("cachedFactionAtLocation") ? (Faction)((MetadataValue)p.getMetadata("cachedFactionAtLocation").get(0)).value() : null;
        }
        return null;
    }
}

