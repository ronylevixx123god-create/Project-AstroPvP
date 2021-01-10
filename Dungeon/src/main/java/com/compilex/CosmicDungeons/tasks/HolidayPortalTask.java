/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.nms.MinecraftServerUtils
 *  com.cosmicpvp.cosmicutils.utils.TitleManager$TitleColor
 *  com.gmail.filoghost.holograms.api.Hologram
 *  com.gmail.filoghost.holograms.api.HolographicDisplaysAPI
 *  com.google.common.collect.Lists
 *  org.arkhamnetwork.Arkkit.libs.ParticleEffect
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.block.Block
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 */
package com.compilex.CosmicDungeons.tasks;

import com.compilex.CosmicDungeons.CosmicDungeonsPlugin;
import com.compilex.CosmicDungeons.DungeonsAPI;
import com.compilex.CosmicDungeons.dungeons.libs.Dungeon;
import com.compilex.CosmicDungeons.dungeons.libs.DungeonState;
import com.compilex.CosmicDungeons.utils.LocationUtils;
import com.cosmicpvp.cosmicutils.nms.MinecraftServerUtils;
import com.google.common.collect.Lists;
import net.astropvp.core.utils.TitleUtil;
import org.arkhamnetwork.Arkkit.libs.ParticleEffect;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.TimeZone;

public class HolidayPortalTask
implements Runnable {
    long lastPortalSpawnedTick = 0L;
    public static int spawnHour9AM = 9;
    public static int spawnHour7PM = 19;
    public static LinkedList<Integer> spawnHours = Lists.newLinkedList((Iterable)Lists.newArrayList((Object[])new Integer[]{spawnHour9AM, spawnHour7PM}));
    private static boolean forceSpawn = false;
    private static Block portalBlockA = null;
    private static Block portalBlockB = null;

    public static void setForceSpawn(boolean forceSpawn) {
        HolidayPortalTask.forceSpawn = forceSpawn;
    }

    public static void setPortalBlockA(Block portalBlockA) {
        HolidayPortalTask.portalBlockA = portalBlockA;
    }

    public static void setPortalBlockB(Block portalBlockB) {
        HolidayPortalTask.portalBlockB = portalBlockB;
    }

    public HolidayPortalTask() {
        Location lA = LocationUtils.convertStringToLocation(CosmicDungeonsPlugin.get_().getConfig().getString("holiday.portalBlockA"));
        Location lB = LocationUtils.convertStringToLocation(CosmicDungeonsPlugin.get_().getConfig().getString("holiday.portalBlockB"));
        if (lA != null && lB != null) {
            portalBlockA = lA.getBlock();
            portalBlockB = lB.getBlock();
        }
    }

    @Override
    public void run() {
        Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("CST"));
        if (spawnHours.contains(c.get(11)) || forceSpawn) {
            if ((long)MinecraftServerUtils.getCurrentServerTick() - this.lastPortalSpawnedTick < 144000L) {
                return;
            }
            int id = Math.random() <= 0.5 ? 1 : 0;
            final Dungeon dungeon = DungeonsAPI.get().getDungeon(id);
            if (dungeon.getDungeonStatus() == DungeonState.IN_PROGRESS) {
                if (dungeon.getPlayers().size() == 0) {
                    dungeon.reset();
                }
            } else {
                if (dungeon.getDungeonStatus() == DungeonState.LOADING) {
                    return;
                }
                if (portalBlockA == null || portalBlockB == null) {
                    return;
                }
                if (portalBlockA.getType() != Material.AIR || portalBlockB.getType() != Material.AIR) {
                    return;
                }
                this.lastPortalSpawnedTick = MinecraftServerUtils.getCurrentServerTick();
                Bukkit.broadcastMessage("");
                Bukkit.broadcastMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "*** DUNGEON PORTAL ***");
                Bukkit.broadcastMessage(ChatColor.GOLD + "A dungeon portal to... " + dungeon.getDungeonName());
                Bukkit.broadcastMessage(ChatColor.GOLD + "will spawn in " + ChatColor.YELLOW + "5 minutes" + ChatColor.GOLD + " at: " + ChatColor.YELLOW + portalBlockA.getX() + "x, " + portalBlockA.getY() + "y, " + portalBlockA.getZ() + "z");
                Bukkit.broadcastMessage("");
                Bukkit.getScheduler().runTaskLater(CosmicDungeonsPlugin.get_(), new Runnable(){

                    @Override
                    public void run() {
                        Bukkit.broadcastMessage("");
                        Bukkit.broadcastMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "*** DUNGEON PORTAL ***");
                        Bukkit.broadcastMessage(ChatColor.GOLD + "A dungeon portal to... " + dungeon.getDungeonName());
                        Bukkit.broadcastMessage(ChatColor.GOLD + "will spawn in " + ChatColor.YELLOW + "1 minute" + ChatColor.GOLD + " at: " + ChatColor.YELLOW + portalBlockA.getX() + "x, " + portalBlockA.getY() + "y, " + portalBlockA.getZ() + "z");
                        Bukkit.broadcastMessage("");
                        Bukkit.getScheduler().runTaskLater(CosmicDungeonsPlugin.get_(), new Runnable(){

                            @Override
                            public void run() {
                                if (dungeon.getDungeonStatus() == DungeonState.IN_PROGRESS) {
                                    if (dungeon.getPlayers().size() == 0) {
                                        dungeon.reset();
                                    }
                                } else {
                                    if (dungeon.getDungeonStatus() == DungeonState.LOADING) {
                                        return;
                                    }
                                    dungeon.reset();
                                    for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
                                        pl.removeMetadata("lastDungeonDeath", CosmicDungeonsPlugin.get_());
                                    }
                                    portalBlockA.setMetadata("dungeonPortalBlock", new FixedMetadataValue(CosmicDungeonsPlugin.get_(), dungeon.getDungeonID()));
                                    portalBlockB.setMetadata("dungeonPortalBlock", new FixedMetadataValue(CosmicDungeonsPlugin.get_(), dungeon.getDungeonID()));
                                    portalBlockA.setTypeIdAndData(90, (byte)0, false);
                                    portalBlockB.setTypeIdAndData(90, (byte)0, false);
                                    CosmicDungeonsPlugin.get_().getDungeonPortalListener().portalBlocks.add(portalBlockA.getLocation());
                                    CosmicDungeonsPlugin.get_().getDungeonPortalListener().portalBlocks.add(portalBlockB.getLocation());
                                    dungeon.setGlobalPortal(true);
                                    final Block dungeon_portalBlockA = dungeon.getSpawnLocation().getBlock();
                                    final Block dungeon_portalBlockB = dungeon.getSpawnLocation().clone().add(0.0, 1.0, 0.0).getBlock();
                                    dungeon_portalBlockA.setMetadata("dungeonPortalBlock", new FixedMetadataValue(CosmicDungeonsPlugin.get_(), dungeon.getDungeonID()));
                                    dungeon_portalBlockB.setMetadata("dungeonPortalBlock", new FixedMetadataValue(CosmicDungeonsPlugin.get_(), dungeon.getDungeonID()));
                                    dungeon_portalBlockA.setTypeIdAndData(90, (byte)0, false);
                                    dungeon_portalBlockB.setTypeIdAndData(90, (byte)0, false);
                                    CosmicDungeonsPlugin.get_().getDungeonPortalListener().portalBlocks.add(dungeon_portalBlockA.getLocation());
                                    CosmicDungeonsPlugin.get_().getDungeonPortalListener().portalBlocks.add(dungeon_portalBlockB.getLocation());
                                    ParticleEffect.playEffect(ParticleEffect.WITCH_MAGIC, portalBlockA.getLocation(), 0.45f, 50);
                                    ParticleEffect.playEffect(ParticleEffect.PORTAL, portalBlockA.getLocation(), 0.6f, 50);
                                    ParticleEffect.playEffect(ParticleEffect.PORTAL, portalBlockB.getLocation(), 0.6f, 50);
                                    portalBlockA.getWorld().playSound(portalBlockA.getLocation(), Sound.AMBIENCE_THUNDER, 2.0f, 0.35f);
                                    dungeon.setDungeonStatus(DungeonState.LOADING);
                                    dungeon.setPortalLocation(portalBlockA.getLocation());
                                // todo: holos
                                    //                                    final Hologram holo_a = HolographicDisplaysAPI.createHologram((Plugin)CosmicDungeonsPlugin.get_(), (Location)portalBlockB.getLocation().add(0.5, 1.25, 0.5), (String[])new String[]{dungeon.getDungeonName()});
//                                    final Hologram holo_b = HolographicDisplaysAPI.createHologram((Plugin)CosmicDungeonsPlugin.get_(), (Location)dungeon_portalBlockB.getLocation().add(0.5, 1.25, 0.5), (String[])new String[]{(Object)ChatColor.RED + "" + (Object)ChatColor.BOLD + "Exit Portal"});
//                                    if (dungeon.isAbandondedSpaceship()) {
//                                        dungeon.setObjectiveHologram(HolographicDisplaysAPI.createHologram((Plugin)CosmicDungeonsPlugin.get_(), (Location)new Location(dungeon.getWorld(), -2.0, 82.0, 90.0), (String[])new String[]{(Object)ChatColor.RED + "" + (Object)ChatColor.BOLD + "LOCKED", (Object)ChatColor.GRAY + "The airlock will automatically", (Object)ChatColor.GRAY + "unlock once the dungeon portal closes."}));
//                                    }
                                    Bukkit.broadcastMessage("");
                                    Bukkit.broadcastMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "*** DUNGEON PORTAL ***");
                                    Bukkit.broadcastMessage(ChatColor.YELLOW + "???" + ChatColor.GOLD + " has spawned a dungeon portal to ");
                                    Bukkit.broadcastMessage(dungeon.getDungeonName() + ChatColor.GOLD + " at: " + ChatColor.YELLOW + portalBlockA.getX() + "x, " + portalBlockA.getY() + "y, " + portalBlockA.getZ() + "z");
                                    Bukkit.broadcastMessage(ChatColor.GOLD + "The portal will close in " + ChatColor.UNDERLINE + "100 seconds" + ChatColor.GOLD + ", queue up fast!");
                                    Bukkit.broadcastMessage("");
                                    Bukkit.getScheduler().runTaskLater(CosmicDungeonsPlugin.get_(), new Runnable(){

                                        @Override
                                        public void run() {
                                            dungeon.broadcastMessage("The dungeon portal will close in 75s...");
                                        }
                                    }, 300L);
                                    Bukkit.getScheduler().runTaskLater(CosmicDungeonsPlugin.get_(), new Runnable(){

                                        @Override
                                        public void run() {
                                            dungeon.broadcastMessage("The dungeon portal will close in 60s...");

                                            TitleUtil.sendTitleList(dungeon.getPlayers(), ChatColor.GOLD + "Dungeon Portal");
                                            TitleUtil.sendSubTitleList(dungeon.getPlayers(), ChatColor.YELLOW + "The dungeon portal will close in 60s...");

                                            Bukkit.broadcastMessage("");
                                            Bukkit.broadcastMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "*** DUNGEON PORTAL ***");
                                            Bukkit.broadcastMessage(ChatColor.YELLOW + "The dungeon portal to ");
                                            Bukkit.broadcastMessage(dungeon.getDungeonName() + ChatColor.GOLD + " at: " + ChatColor.YELLOW + portalBlockA.getX() + "x, " + portalBlockA.getY() + "y, " + portalBlockA.getZ() + "z will close in " + ChatColor.UNDERLINE + "60 seconds" + ChatColor.GOLD + ", hurry up!");
                                            Bukkit.broadcastMessage("");
                                        }
                                    }, 600L);
                                    Bukkit.getScheduler().runTaskLater(CosmicDungeonsPlugin.get_(), new Runnable(){

                                        @Override
                                        public void run() {
                                            dungeon.broadcastMessage("The dungeon portal will close in 30s...");
//
                                            TitleUtil.sendTitleList(dungeon.getPlayers(), ChatColor.GOLD + "Dungeon Portal");
                                            TitleUtil.sendSubTitleList(dungeon.getPlayers(), ChatColor.YELLOW + "The dungeon portal will close in 30s...");
                                        }
                                    }, 1200L);
                                    Bukkit.getScheduler().runTaskLater(CosmicDungeonsPlugin.get_(), new Runnable(){

                                        @Override
                                        public void run() {
                                            dungeon.broadcastMessage("The dungeon portal will close in 15s...");
                                        }
                                    }, 1500L);
                                    Bukkit.getScheduler().runTaskLater(CosmicDungeonsPlugin.get_(), new Runnable(){

                                        @Override
                                        public void run() {
                                            dungeon.broadcastMessage("The dungeon portal will close in 10s...");
                                        }
                                    }, 1800L);
                                    Bukkit.getScheduler().runTaskLater(CosmicDungeonsPlugin.get_(), new Runnable(){

                                        @Override
                                        public void run() {
                                            dungeon.broadcastMessage("The dungeon portal will close in 5s...");

                                            TitleUtil.sendTitleList(dungeon.getPlayers(), ChatColor.GOLD + "Dungeon Portal");
                                            TitleUtil.sendSubTitleList(dungeon.getPlayers(), ChatColor.YELLOW + "The dungeon portal will close in 5s...");
                                        }
                                    }, 1900L);
                                    Bukkit.getScheduler().runTaskLater(CosmicDungeonsPlugin.get_(), new Runnable(){

                                        @Override
                                        public void run() {
                                            portalBlockA.removeMetadata("dungeonPortalBlock", CosmicDungeonsPlugin.get_());
                                            portalBlockB.removeMetadata("dungeonPortalBlock", CosmicDungeonsPlugin.get_());
                                            dungeon_portalBlockA.removeMetadata("dungeonPortalBlock", CosmicDungeonsPlugin.get_());
                                            dungeon_portalBlockB.removeMetadata("dungeonPortalBlock", CosmicDungeonsPlugin.get_());
                                            portalBlockA.getLocation().getChunk().load();
                                            portalBlockA.setType(Material.AIR);
                                            portalBlockB.setType(Material.AIR);
                                            dungeon_portalBlockA.setType(Material.AIR);
                                            dungeon_portalBlockB.setType(Material.AIR);
                                            CosmicDungeonsPlugin.get_().getDungeonPortalListener().portalBlocks.remove(portalBlockA.getLocation());
                                            CosmicDungeonsPlugin.get_().getDungeonPortalListener().portalBlocks.remove(portalBlockB.getLocation());
                                            CosmicDungeonsPlugin.get_().getDungeonPortalListener().portalBlocks.remove(dungeon_portalBlockA.getLocation());
                                            CosmicDungeonsPlugin.get_().getDungeonPortalListener().portalBlocks.remove(dungeon_portalBlockB.getLocation());
                                            // todo: holos
                                            //                                            holo_a.delete();
//                                            holo_b.delete();
                                            portalBlockA.getWorld().playSound(portalBlockA.getLocation(), Sound.AMBIENCE_THUNDER, 2.0f, 0.35f);
                                            ParticleEffect.playEffect(ParticleEffect.WITCH_MAGIC, portalBlockA.getLocation(), 0.45f, 50);
                                            ParticleEffect.playEffect(ParticleEffect.PORTAL, portalBlockA.getLocation(), 0.6f, 50);
                                            ParticleEffect.playEffect(ParticleEffect.PORTAL, portalBlockB.getLocation(), 0.6f, 50);
                                            dungeon_portalBlockA.getWorld().playSound(portalBlockA.getLocation(), Sound.AMBIENCE_THUNDER, 2.0f, 0.35f);
                                            ParticleEffect.playEffect(ParticleEffect.WITCH_MAGIC, dungeon_portalBlockA.getLocation(), 0.45f, 50);
                                            ParticleEffect.playEffect(ParticleEffect.PORTAL, dungeon_portalBlockA.getLocation(), 0.6f, 50);
                                            ParticleEffect.playEffect(ParticleEffect.PORTAL, dungeon_portalBlockB.getLocation(), 0.6f, 50);
                                            Bukkit.broadcastMessage("");
                                            Bukkit.broadcastMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "*** DUNGEON PORTAL ***");
                                            Bukkit.broadcastMessage(ChatColor.GOLD + "The portal has closed, " + ChatColor.UNDERLINE + dungeon.getPlayers().size() + ChatColor.GOLD + " player(s) made it into the");
                                            Bukkit.broadcastMessage(dungeon.getDungeonName() + ChatColor.GOLD + " /dungeon!");
                                            Bukkit.broadcastMessage("");
                                            if (dungeon.getPlayers().size() > 0) {
                                                dungeon.setDungeonStatus(DungeonState.IN_PROGRESS);
                                            } else {
                                                dungeon.setDungeonStatus(DungeonState.EMPTY);
                                            }
                                            dungeon.getPlayers().stream().filter(pl -> !pl.hasMetadata("NPC")).forEach(pl -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "contests add " + pl.getName() + " dungeonsEntered 1"));
                                        }
                                    }, 2000L);
                                }
                            }
                        }, 1200L);
                    }
                }, 4800L);
            }
        }
    }
}

