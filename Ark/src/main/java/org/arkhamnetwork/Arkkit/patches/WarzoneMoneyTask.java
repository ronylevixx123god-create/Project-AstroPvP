/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.compilex.CosmicFund.CosmicFundAPI
 *  com.cosmicpvp.CosmicBossEggs.boss.type.Bosses
 *  com.cosmicpvp.cosmicutils.customevents.CustomCosmicPlayerEvent
 *  me.ifamasssxd.factionupgrades.FactionUpgradeAPI
 *  me.ifamasssxd.factionupgrades.struct.FactionUpgrade
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.GameMode
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.OfflinePlayer
 *  org.bukkit.Sound
 *  org.bukkit.block.Block
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.scheduler.BukkitRunnable
 */
package org.arkhamnetwork.Arkkit.patches;

import com.compilex.CosmicFund.CosmicFundAPI;
import com.cosmicpvp.CosmicBossEggs.boss.type.Bosses;
import com.cosmicpvp.cosmicutils.customevents.CustomCosmicPlayerEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import me.ifamasssxd.factionupgrades.FactionUpgradeAPI;
import me.ifamasssxd.factionupgrades.struct.FactionUpgrade;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.libs.ExpUtils;
import org.arkhamnetwork.Arkkit.patches.WarzoneMoney;
import org.arkhamnetwork.Arkkit.patches.WarzoneMoneyLMSTask;
import org.arkhamnetwork.Arkkit.utils.EssentialsUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

final class WarzoneMoneyTask
extends BukkitRunnable {
    private static List<Material> transparentMaterials = new ArrayList<Material>(Arrays.asList(new Material[]{Material.AIR, Material.WEB, Material.WATER, Material.STATIONARY_WATER}));
    private static List<Material> liquids = new ArrayList<Material>(Arrays.asList(new Material[]{Material.WATER, Material.STATIONARY_WATER, Material.LAVA, Material.STATIONARY_LAVA}));
    private long iteration = 0L;

    WarzoneMoneyTask() {
    }

    public void run() {
        UUID uuid;
        Player p2;
        ++this.iteration;
        ArrayList<String> payedIps = new ArrayList<String>();
        boolean contestsEnabled = Bukkit.getPluginManager().isPluginEnabled("CosmicContests");
        List<Player> playersInWarzone = WarzoneMoney.getPlayersInMainWarzone();
        for (Player p2 : Bukkit.getOnlinePlayers()) {
            try {
                if (playersInWarzone.contains((Object)p2)) {
                    double distFrom0;
                    Location lastLocation;
                    int secondsInWarzone;
                    if (p2.getGameMode() != GameMode.SURVIVAL || EssentialsUtils.getEssentials().getUser(p2).isVanished()) continue;
                    if (!p2.hasMetadata("warzoneEntryTime")) {
                        p2.setMetadata("warzoneEntryTime", (MetadataValue)new FixedMetadataValue((Plugin)Arkkit.get(), (Object)System.currentTimeMillis()));
                        continue;
                    }
                    if (!p2.hasMetadata("warzonePayoutTip")) {
                        p2.setMetadata("warzonePayoutTip", (MetadataValue)new FixedMetadataValue((Plugin)Arkkit.get(), (Object)true));
                        p2.sendMessage("");
                        p2.sendMessage((Object)ChatColor.YELLOW + "** You have entered the planet's main " + (Object)ChatColor.BOLD + "WARZONE. " + (Object)ChatColor.YELLOW + "The longer you stay in this PvP-enabled area, and the closer you are to the center (" + (Object)ChatColor.BOLD + WarzoneMoney.warzoneCenter.getBlockX() + "x, " + WarzoneMoney.warzoneCenter.getBlockZ() + "z" + (Object)ChatColor.YELLOW + "), the more $ and XP will be rewarded every minute!");
                        p2.sendMessage((Object)ChatColor.GRAY + "Keep moving, and do " + (Object)ChatColor.UNDERLINE + "not" + (Object)ChatColor.GRAY + " /fly to stay eligible!");
                        p2.sendMessage("");
                    }
                    if ((secondsInWarzone = WarzoneMoneyTask.getSecondsInWarzone(p2)) < 60) continue;
                    int minutesInWarzone = secondsInWarzone / 60;
                    if (payedIps.contains(p2.getAddress().getHostString()) || p2.isFlying()) {
                        if (minutesInWarzone > 1) {
                            if (p2.isFlying()) {
                                p2.sendMessage((Object)ChatColor.RED + "** Your warzone reward timer has been reset because you toggled /fly.");
                            }
                            if (payedIps.contains(p2.getAddress().getHostString())) {
                                p2.sendMessage((Object)ChatColor.RED + "** Your warzone reward timer has been reset because you have multiple accounts in the warzone on the same IP address.");
                            }
                        }
                        WarzoneMoneyTask.disqualifyPlayer(p2);
                        continue;
                    }
                    if (!(transparentMaterials.contains((Object)p2.getEyeLocation().getBlock().getType()) || transparentMaterials.contains((Object)p2.getLocation().getBlock().getType()) || transparentMaterials.contains((Object)p2.getLocation().add(0.0, 1.0, 0.0).getBlock().getType()))) {
                        if (minutesInWarzone > 1) {
                            p2.sendMessage((Object)ChatColor.RED + "** Your warzone reward timer has been reset because you are inside of a block.");
                        }
                        WarzoneMoneyTask.disqualifyPlayer(p2);
                        continue;
                    }
                    if (secondsInWarzone % 60 >= 50 && secondsInWarzone % 60 <= 54 && this.inWater(p2)) {
                        p2.sendMessage(ChatColor.RED.toString() + (Object)ChatColor.BOLD + "(!) " + (Object)ChatColor.RED + "You are about to lose your warzone reward multiplier because you have been fully submerged in water or lava for too long!");
                    }
                    Location location = lastLocation = p2.hasMetadata("warzoneLastLocation") ? (Location)((MetadataValue)p2.getMetadata("warzoneLastLocation").get(0)).value() : null;
                    if (lastLocation != null) {
                        lastLocation.setY(p2.getLocation().getY());
                    }
                    double distanceToLast = -1.0;
                    double distanceToCenter = -1.0;
                    if (secondsInWarzone % 60 >= 50 && secondsInWarzone % 60 <= 54 && p2.hasMetadata("warzoneLastLocation")) {
                        distanceToLast = p2.getLocation().distanceSquared(lastLocation);
                        distanceToCenter = this.getDistanceToCenter(p2);
                        if (distanceToLast < 25.0) {
                            p2.sendMessage(ChatColor.RED.toString() + (Object)ChatColor.BOLD + "(!) " + (Object)ChatColor.RED + "You are about to lose your warzone reward multiplier because you have not moved far enough " + (Object)ChatColor.GRAY + "(away from " + lastLocation.getBlockX() + "x, " + lastLocation.getBlockZ() + "z)" + (Object)ChatColor.RED + " in the last minute!");
                        }
                    }
                    if (p2.hasMetadata("lastWarzoneMinutePayout") && ((MetadataValue)p2.getMetadata("lastWarzoneMinutePayout").get(0)).asInt() == minutesInWarzone) continue;
                    if (this.inWater(p2)) {
                        if (minutesInWarzone > 1) {
                            p2.sendMessage((Object)ChatColor.RED + "** Your warzone reward timer has been reset because you are fully submerged in water or lava.");
                        }
                        WarzoneMoneyTask.disqualifyPlayer(p2);
                        continue;
                    }
                    if (distanceToCenter == -1.0) {
                        distanceToCenter = this.getDistanceToCenter(p2);
                    }
                    if (lastLocation != null) {
                        distanceToLast = p2.getLocation().distanceSquared(lastLocation);
                    }
                    p2.setMetadata("warzoneLastLocation", (MetadataValue)new FixedMetadataValue((Plugin)Arkkit.get(), (Object)p2.getLocation()));
                    p2.setMetadata("lastDistanceToCenter", (MetadataValue)new FixedMetadataValue((Plugin)Arkkit.get(), (Object)distanceToCenter));
                    if ((double)p2.getWorld().getHighestBlockAt(p2.getLocation()).getY() > p2.getLocation().getY() && (distFrom0 = p2.getLocation().distanceSquared(new Location(Bukkit.getWorld((String)"world"), 0.0, p2.getLocation().getY(), 0.0))) > 122500.0) {
                        if (minutesInWarzone > 1) {
                            p2.sendMessage((Object)ChatColor.RED + "** Your warzone reward timer has been reset because you are too far underground and too far away from the warzone center.");
                        }
                        WarzoneMoneyTask.disqualifyPlayer(p2);
                        continue;
                    }
                    if (lastLocation != null && (!lastLocation.getWorld().equals((Object)p2.getWorld()) || distanceToLast < 25.0 && distanceToLast != -1.0)) {
                        p2.setMetadata("warzoneEntryTime", (MetadataValue)new FixedMetadataValue((Plugin)Arkkit.get(), (Object)System.currentTimeMillis()));
                        WarzoneMoney.warzoneTimes.remove(p2.getUniqueId());
                        if (minutesInWarzone <= 1) continue;
                        p2.sendMessage((Object)ChatColor.RED + "** Your warzone reward timer has been reset because you stopped moving.");
                        continue;
                    }
                    boolean xp = new Random().nextBoolean();
                    int payout = this.getWarzonePayout(p2, minutesInWarzone, distanceToCenter, xp);
                    if (xp) {
                        ExpUtils.setTotalExperience(p2, ExpUtils.getTotalExperience(p2) + payout);
                        WarzoneMoney.warzoneXp.put(p2.getUniqueId(), WarzoneMoney.warzoneXp.containsKey(p2.getUniqueId()) ? WarzoneMoney.warzoneXp.get(p2.getUniqueId()) + payout : payout);
                    } else {
                        Arkkit.economy.depositPlayer((OfflinePlayer)p2, (double)payout);
                        WarzoneMoney.warzoneMoney.put(p2.getUniqueId(), WarzoneMoney.warzoneMoney.containsKey(p2.getUniqueId()) ? WarzoneMoney.warzoneMoney.get(p2.getUniqueId()) + payout : payout);
                    }
                    if (!p2.hasMetadata("lastWarzoneMinutePayout") || ((MetadataValue)p2.getMetadata("lastWarzoneMinutePayout").get(0)).asInt() != minutesInWarzone) {
                        p2.setMetadata("lastWarzoneMinutePayout", (MetadataValue)new FixedMetadataValue((Plugin)Arkkit.get(), (Object)minutesInWarzone));
                    }
                    payedIps.add(p2.getAddress().getHostString());
                    WarzoneMoney.warzoneTimes.put(p2.getUniqueId(), minutesInWarzone);
                    if (WarzoneMoney.isLMS(p2) && lastLocation != null) {
                        for (Player pl : playersInWarzone) {
                            if (p2.equals((Object)pl)) {
                                p2.sendMessage(WarzoneMoney.LMS_PREFIX + (Object)ChatColor.YELLOW + "Your last known location has been sent to enemy players... " + (Object)ChatColor.UNDERLINE + lastLocation.getBlockX() + "x, " + lastLocation.getBlockY() + "y, " + lastLocation.getBlockZ() + "z");
                                continue;
                            }
                            pl.sendMessage(WarzoneMoney.LMS_PREFIX + (Object)ChatColor.YELLOW + p2.getName() + "'s location: " + (Object)ChatColor.GOLD + lastLocation.getBlockX() + "x, " + lastLocation.getBlockY() + "y, " + lastLocation.getBlockZ() + "z");
                        }
                        Bukkit.getPluginManager().callEvent((Event)new CustomCosmicPlayerEvent(p2, "LMS_HOLD", new Object[]{minutesInWarzone}));
                    }
                    if (!contestsEnabled) continue;
                    Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), (String)("contests add " + p2.getName() + " warzoneTime 1"));
                    continue;
                }
                if (!p2.hasMetadata("warzoneEntryTime")) continue;
                if (WarzoneMoneyTask.getSecondsInWarzone(p2) > 60) {
                    p2.sendMessage((Object)ChatColor.RED + "** Your warzone reward timer has been reset because you left the Warzone!");
                }
                WarzoneMoneyTask.disqualifyPlayer(p2);
            }
            catch (Exception err) {
                err.printStackTrace();
            }
        }
        if (Arkkit.get().getConfig().getBoolean("settings.warzoneMoney.treasureGoblins") && this.iteration % 120L == 0L && Bukkit.getPlayer((UUID)(uuid = WarzoneMoney.warzoneTimes.keySet().toArray(new UUID[WarzoneMoney.warzoneTimes.size()])[new Random().nextInt(WarzoneMoney.warzoneTimes.size())])) != null && (p2 = Bukkit.getPlayer((UUID)uuid)).hasMetadata("warzoneEntryTime")) {
            Location lastLocation;
            Bosses.PIG_ZOMBIE.b.spawnBoss(p2.getLocation().add(0.0, 1.0, 0.0));
            Location location = lastLocation = p2.hasMetadata("warzoneLastLocation") ? (Location)((MetadataValue)p2.getMetadata("warzoneLastLocation").get(0)).value() : null;
            if (lastLocation != null) {
                lastLocation.setY(p2.getLocation().getY());
                for (Player pl : WarzoneMoney.getPlayersInMainWarzone()) {
                    pl.sendMessage((Object)ChatColor.GOLD + "" + (Object)ChatColor.BOLD + "HALLOWEEN > " + (Object)ChatColor.YELLOW + "A " + (Object)ChatColor.UNDERLINE + "Treasure Goblin" + (Object)ChatColor.YELLOW + " has spawned at " + (Object)ChatColor.YELLOW + p2.getName() + "'s location: " + (Object)ChatColor.GOLD + lastLocation.getBlockX() + "x, " + lastLocation.getBlockY() + "y, " + lastLocation.getBlockZ() + "z");
                }
            }
        }
    }

    private boolean inWater(Player p) {
        Block b1 = p.getLocation().getBlock();
        Block b2 = p.getEyeLocation().getBlock();
        Block b3 = p.getLocation().add(0.0, 1.0, 0.0).getBlock();
        return liquids.contains((Object)b1.getType()) && liquids.contains((Object)b2.getType()) && liquids.contains((Object)b3.getType());
    }

    protected static void disqualifyPlayer(Player p) {
        if (WarzoneMoney.isLMS(p)) {
            for (Player player : WarzoneMoney.getPlayersInMainWarzone()) {
            }
            WarzoneMoneyLMSTask.pLMS = null;
        }
        WarzoneMoney.warzoneTimes.remove(p.getUniqueId());
        WarzoneMoney.warzoneXp.remove(p.getUniqueId());
        WarzoneMoney.warzoneMoney.remove(p.getUniqueId());
        p.removeMetadata("warzoneEntryTime", (Plugin)Arkkit.get());
        p.removeMetadata("warzoneLastLocation", (Plugin)Arkkit.get());
        p.removeMetadata("lastWarzoneMinutePayout", (Plugin)Arkkit.get());
        p.removeMetadata("lastLMSAlert", (Plugin)Arkkit.get());
    }

    protected static int getSecondsInWarzone(Player p) {
        return (int)(p.hasMetadata("warzoneEntryTime") ? (System.currentTimeMillis() - ((MetadataValue)p.getMetadata("warzoneEntryTime").get(0)).asLong()) / 1000L : 0L);
    }

    private int getWarzonePayout(Player p, int minutes, double distanceToCenter, boolean xp) {
        if (distanceToCenter < 0.0) {
            return 0;
        }
        double distanceMod = 1.0 - distanceToCenter / 500.0;
        double closeMod = this.getCloseToSpawnModifier(distanceToCenter);
        int payout = (int)Math.min((double)(250 + minutes * 50) * distanceMod, 1500.0);
        payout = (int)((double)payout * closeMod);
        payout /= 2;
        if (WarzoneMoney.isLMS(p)) {
            payout *= 5;
        }
        if (Bukkit.getPluginManager().isPluginEnabled("CosmicFund") && !CosmicFundAPI.isLMSEnabled()) {
            payout /= 2;
        }
        if (Bukkit.getPluginManager().isPluginEnabled("FactionUpgrades")) {
            try {
                int warzoneControl = FactionUpgradeAPI.getPerkLevel((Player)p, (FactionUpgrade)FactionUpgrade.WARZONE_CONTROL);
                if (warzoneControl == 1) {
                    payout = (int)((double)payout * 1.25);
                }
                if (warzoneControl == 2) {
                    payout = (int)((double)payout * 1.5);
                }
                if (warzoneControl == 3) {
                    payout = (int)((double)payout * 2.0);
                }
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        if (minutes == 1) {
            p.sendMessage("");
        }
        if (xp) {
            payout = WarzoneMoney.isVanillaGalaxy ? payout / 10 : payout / 2;
            Bukkit.getLogger().info("[Arkkit (WarzoneMoney]) Adding " + payout + " EXP to " + p.getName() + " as Warzone reward #" + minutes + " at a distance of " + distanceToCenter + "!");
            p.sendMessage(ChatColor.AQUA.toString() + ChatColor.BOLD.toString() + " + " + payout + "XP" + (Object)ChatColor.GRAY + " (Warzone Reward " + (Object)ChatColor.AQUA + "#" + minutes + (Object)ChatColor.GRAY + " [distance:" + (int)distanceToCenter + "blocks x" + closeMod + (WarzoneMoney.isLMS(p) ? " + LMS 5.0x bonus" : "") + "])");
        } else {
            payout = WarzoneMoney.isVanillaGalaxy ? payout / 2 : payout;
            Bukkit.getLogger().info("[Arkkit (WarzoneMoney]) Adding $" + payout + " to " + p.getName() + " as Warzone reward #" + minutes + " at a distance of " + distanceToCenter + "!");
            p.sendMessage(ChatColor.GREEN.toString() + ChatColor.BOLD.toString() + " + $" + payout + (Object)ChatColor.GRAY + " (Warzone Reward " + (Object)ChatColor.GREEN + "#" + minutes + (Object)ChatColor.GRAY + " [distance:" + (int)distanceToCenter + "blocks x" + closeMod + (WarzoneMoney.isLMS(p) ? " + LMS 5.0x bonus" : "") + "])");
        }
        p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1.0f, 0.77f);
        if (minutes == 1) {
            p.sendMessage((Object)ChatColor.GRAY + "* For every minute you stay mobile in the main Warzone, you will recieve increasing amounts of $ and XP. The closer you are to the center, the " + (Object)ChatColor.UNDERLINE + "higher your payout %");
            p.sendMessage("");
            p.sendMessage((Object)ChatColor.GRAY + "** If you manage to get within 100, 50, 25, or 10 blocks of the center of the warzone (" + (Object)ChatColor.RED + WarzoneMoney.warzoneCenter.getBlockX() + "x, " + WarzoneMoney.warzoneCenter.getBlockZ() + "z" + (Object)ChatColor.GRAY + ") you will recieve up to an additional 3x " + (Object)ChatColor.UNDERLINE + "reward multiplier.");
        } else if (minutes < 5) {
            p.sendMessage((Object)ChatColor.GRAY + "* The longer you are in the warzone, and the closer you are to the center (" + WarzoneMoney.warzoneCenter.getBlockX() + "x, " + WarzoneMoney.warzoneCenter.getBlockZ() + "z), the more $/XP you make!");
        }
        return payout;
    }

    private double getDistanceToCenter(Player p) {
        Location center = WarzoneMoney.warzoneCenter.clone();
        center.setY(p.getLocation().getY());
        if (!p.getWorld().equals((Object)center.getWorld())) {
            return -1.0;
        }
        return Math.min(500.0, p.getLocation().distance(center));
    }

    private double getCloseToSpawnModifier(double distance) {
        if (distance < 10.0) {
            return 3.0;
        }
        if (distance < 25.0) {
            return 2.0;
        }
        if (distance < 50.0) {
            return 1.5;
        }
        if (distance < 100.0) {
            return 1.2;
        }
        return 1.0;
    }

    private Location getRandomWarzoneLocation() {
        return null;
    }
}

