/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.itembuilder.PhoenixFeatherBuilder
 *  com.cosmicpvp.cosmicutils.itembuilder.SpeedTrinketBuilder
 *  com.cosmicpvp.cosmicutils.itembuilder.YijkiStaffBuilder
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.cosmicpvp.cosmicutils.utils.NumberUtils
 *  com.cosmicpvp.cosmicutils.utils.PlayerUtil
 *  com.google.common.collect.Lists
 *  org.bukkit.Bukkit
 *  org.bukkit.Location
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandExecutor
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 */
package com.compilex.CosmicDungeons.cmds;

import com.compilex.CosmicDungeons.CosmicDungeonsPlugin;
import com.compilex.CosmicDungeons.DungeonsAPI;
import com.compilex.CosmicDungeons.DungeonsRewardsAPI;
import com.compilex.CosmicDungeons.dungeons.libs.DestroyedOutpostDungeon;
import com.compilex.CosmicDungeons.dungeons.libs.Dungeon;
import com.compilex.CosmicDungeons.dungeons.libs.DungeonPlayer;
import com.compilex.CosmicDungeons.dungeons.libs.DungeonType;
import com.compilex.CosmicDungeons.items.DungeonKeyItem;
import com.compilex.CosmicDungeons.struct.PortalLog;
import com.compilex.CosmicDungeons.tasks.HolidayPortalTask;
import com.compilex.CosmicDungeons.utils.ItemUtils;
import com.compilex.CosmicDungeons.utils.LocationUtils;
import com.compilex.CosmicDungeons.utils.Logger;
import com.compilex.CosmicDungeons.utils.TimeUtils;
import com.cosmicpvp.cosmicutils.itembuilder.PhoenixFeatherBuilder;
import com.cosmicpvp.cosmicutils.itembuilder.SpeedTrinketBuilder;
import com.cosmicpvp.cosmicutils.itembuilder.YijkiStaffBuilder;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.NumberUtils;
import com.cosmicpvp.cosmicutils.utils.PlayerUtil;
import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CommandAdminDungeon
implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.isOp()) {
            return true;
        }
        if (args.length == 0) {
            sender.sendMessage("/adungeon <debug/reloadconfig/setcooldown/givekey/saveplayers/givecoords/givemap/findmap/setspawn/materials/soulpearl/soulanvil/reset/lootbag/setholidayportal/togglekey/destroyedoutpostcomplete>");
            return true;
        }
        if (args.length == 1 && args[0].equals("reloadconfig")) {
            CosmicDungeonsPlugin.get_().reloadConfig();
            sender.sendMessage("reloaded config");
            return true;
        }
        if (args[0].equalsIgnoreCase("destroyedoutpostcomplete")) {
            boolean newValue;
            DestroyedOutpostDungeon dungeon = (DestroyedOutpostDungeon)DungeonType.DESTROYED_OUTPOST.getDungeonInstance();
            dungeon.hasBeenDefeated = newValue = !dungeon.hasBeenDefeated;
            CosmicDungeonsPlugin.get().getConfig().set("destroyedOutpostDefeated", (Object)newValue);
            CosmicDungeonsPlugin.get().saveConfig();
            sender.sendMessage(CC.Red + "Destroyed Outpost been defeated set to: " + newValue);
            return true;
        }
        if (args[0].equalsIgnoreCase("giveclock")) {
            ((Player)sender).getInventory().addItem(((DestroyedOutpostDungeon)DungeonType.DESTROYED_OUTPOST.getDungeonInstance()).createReturningClock());
            sender.sendMessage(CC.Red + "clock received!");
            return true;
        }
        if (args[0].equalsIgnoreCase("givekey")) {
            if (args.length != 2) {
                sender.sendMessage(CC.Red + "Please provide a dungeon type: " + Arrays.toString((Object[])DungeonType.values()));
                return true;
            }
            try {
                DungeonType type = DungeonType.valueOf(args[1]);
                ((Player)sender).getInventory().addItem(type.getDungeonInstance().getDungeonPortal());
                sender.sendMessage(CC.Red + " Portal Added!");
                return true;
            }
            catch (Exception e) {
                e.printStackTrace();
                sender.sendMessage(CC.Red + "Unable to find dungeon of that type: " + Arrays.toString((Object[])DungeonType.values()));
                sender.sendMessage(CC.RedB + "/adungeon givekey <dungeonType>");
                return true;
            }
        }
        if (args[0].equalsIgnoreCase("giveportalkey")) {
            Long val;
            String pName = args[1];
            String dungeon2 = args[2];
            Player p = Bukkit.getPlayer((String)pName);
            if (p == null) {
                sender.sendMessage(CC.Red + "Player not online!");
                return true;
            }
            DungeonType type2 = DungeonType.getFromName(dungeon2);
            if (type2 == null) {
                sender.sendMessage(CC.Red + "Dungeon Type invalid! Valid: " + Arrays.toString((Object[])DungeonType.values()));
                return true;
            }
            ItemStack key = args.length == 4 && (val = NumberUtils.parseLongOrNull((String)args[3])) != null ? new DungeonKeyItem(type2, val).build() : new DungeonKeyItem(type2, -1L).build();
            if (key != null) {
                PlayerUtil.giveOrDropProtectedItem((Player)p, (ItemStack)key, (int)-1);
                p.sendMessage(CC.YellowB + "(!) " + CC.Yellow + type2.getDungeonName() + " Dungeon Portal Key" + CC.Yellow + " added to your inventory!");
                sender.sendMessage(CC.Red + "Key added!");
            }
            return true;
        }
        if (args[0].equals("debug")) {
            Logger.debug = !Logger.debug;
            sender.sendMessage("CosmicDungeons debug => " + Logger.debug);
            return true;
        }
        if (args[0].equals("disable")) {
            CosmicDungeonsPlugin.get_().setDisableDungeons(true);
            sender.sendMessage("Dungeons disabled!");
            return true;
        }
        if (args[0].equals("enable")) {
            CosmicDungeonsPlugin.get_().setDisableDungeons(false);
            sender.sendMessage("Dungeons enabled!");
            return true;
        }
        if (args[0].equalsIgnoreCase("givemap") || args[0].equalsIgnoreCase("findmap") || args[0].equalsIgnoreCase("map")) {
            Player player;
            Player player2 = player = sender instanceof Player ? (Player)sender : null;
            if (args.length >= 2) {
                player = Bukkit.getPlayer((String)args[1]);
            }
            if (player == null) {
                sender.sendMessage(CC.Red + "Invalid player given!");
                return true;
            }
            DungeonPlayer dp = CosmicDungeonsPlugin.get_().getApi().getPlayerDungeonData(player);
            if (dp == null || dp.getCurrentDungeon() == null || dp.getCurrentDungeon().getDungeonType() != DungeonType.DESTROYED_OUTPOST) {
                sender.sendMessage(CC.Red + "Player '" + player.getName() + "' needs to be inside an active Destroyed Outpost dungeon to have the proper Dungeon data.");
                return true;
            }
            DestroyedOutpostDungeon dungeon3 = (DestroyedOutpostDungeon)dp.getCurrentDungeon();
            if (args[0].equalsIgnoreCase("findmap")) {
                Location mapChest = dungeon3.getDungeonEvents().getDimensionalMapChest();
                if (mapChest == null) {
                    sender.sendMessage(CC.Red + "Unable to find a chest that stored the map, didnt spawn?");
                    return true;
                }
                sender.sendMessage(CC.Red + "Chest containing map: " + LocationUtils.convertLocationToString(mapChest));
                sender.sendMessage(CC.Red + " hasBeenOpened: " + dungeon3.getDungeonEvents().getLootedChestBlocks().contains((Object)mapChest));
                player.teleport(mapChest);
                return true;
            }
            if (args[0].equalsIgnoreCase("map")) {
                sender.sendMessage(CC.Red + "Active Directions:");
                dungeon3.getDungeonEvents().getActivePortalDestinations().forEach((value, direction) -> sender.sendMessage(CC.Red + "#" + (value + 1) + " - " + (Object)direction));
                return true;
            }
            ItemStack item = dungeon3.getDungeonEvents().createDimensionalMap();
            player.getInventory().addItem(item);
            sender.sendMessage(CC.Red + "Dimensional Map added to " + player.getName() + "'s inventory!");
            return true;
        }
        if (args[0].equalsIgnoreCase("setfastestdungeonrun") && args.length == 3) {
            DungeonType type = DungeonType.getFromName(args[1]);
            if (type == null) {
                sender.sendMessage(CC.Red + "Invalid dungeon type: " + Arrays.toString((Object[])DungeonType.values()));
                return true;
            }
            Integer completed = NumberUtils.parseIntOrNull((String)args[2]);
            if (completed == null) {
                sender.sendMessage(CC.RedB + " (!) " + CC.Red + "Invalid time given!");
                return true;
            }
            type.getDungeonInstance().getTopDungeonTime().setSecondsCompletedIn(completed);
            type.getDungeonInstance().getTopDungeonTime().saveData();
            sender.sendMessage(CC.Red + "Dungeon time set to: " + completed);
            return true;
        }
        if (args[0].equalsIgnoreCase("givecoordinates") || args[0].equalsIgnoreCase("givecoords")) {
            if (args.length == 3) {
                Player player = Bukkit.getPlayer((String)args[1]);
                DungeonPlayer pl = CosmicDungeonsPlugin.get().getApi().getPlayerDungeonData(player);
                DungeonType type3 = DungeonType.getFromName(args[2]);
                if (type3 == null) {
                    sender.sendMessage(CC.Red + "Invalid dungeon type: " + Arrays.toString((Object[])DungeonType.values()));
                    return true;
                }
                if (pl != null) {
                    pl.getLastCompleted().put(type3, System.currentTimeMillis());
                    pl.getPlayerConfig().set("dungeons.stats." + type3.getInternalName() + ".lastComplete", (Object)System.currentTimeMillis());
                    sender.sendMessage(CC.Red + "Last Complete time set to current time!");
                    return true;
                }
            }
            sender.sendMessage(CC.Red + "/adungeon givecoords <player> <dungeon>");
            return true;
        }
        if (args[0].equals("setcooldown")) {
            if (args.length == 4) {
                String pName = args[1];
                String dungeon2 = args[2];
                long duration = Long.parseLong(args[3]);
                Player p2 = Bukkit.getPlayer((String)pName);
                if (p2 != null) {
                    DungeonPlayer dp2 = CosmicDungeonsPlugin.get_().getApi().getPlayerDungeonData(p2);
                    Dungeon d = CosmicDungeonsPlugin.get_().getApi().getDungeonByWorldName(dungeon2);
                    dp2.getCooldowns().setDungeonCooldown(d, duration);
                    sender.sendMessage("set cooldown of " + d.toString() + " for " + dp2.getBukkitPlayer().getName() + " to +" + duration);
                    return true;
                }
            } else {
                sender.sendMessage("/adungeo setcooldown <player> <dungeon> <duration>");
            }
            return true;
        }
        if (args[0].equals("resetcooldowns")) {
            if (args.length == 2) {
                String dungeon4 = args[1];
                for (Player p2 : Bukkit.getServer().getOnlinePlayers()) {
                    if (p2 == null) continue;
                    DungeonPlayer dp2 = CosmicDungeonsPlugin.get_().getApi().getPlayerDungeonData(p2);
                    Dungeon d = CosmicDungeonsPlugin.get_().getApi().getDungeonByWorldName(dungeon4);
                    dp2.getCooldowns().removeDungeonCooldown(d);
                    sender.sendMessage("set cooldown of " + d.toString() + " for " + dp2.getBukkitPlayer().getName() + " to +" + '\u0000');
                }
                return true;
            }
            sender.sendMessage("/adungeon resetcooldowns <dungeon>");
            return true;
        }
        if (args[0].equals("setspawn") && sender instanceof Player) {
            Player pSender = (Player)sender;
            Dungeon dungeon5 = DungeonsAPI.get().getDungeonByWorldName(pSender.getWorld().getName());
            if (dungeon5 != null) {
                dungeon5.setSpawnLocation(pSender.getLocation());
                dungeon5.getWorld().setSpawnLocation(pSender.getLocation().getBlockX(), pSender.getLocation().getBlockY(), pSender.getLocation().getBlockZ());
                CosmicDungeonsPlugin.get_().getConfig().set("dungeons." + dungeon5.getWorldName() + ".spawnLocation", (Object)LocationUtils.convertLocationToString(pSender.getLocation()));
                CosmicDungeonsPlugin.get_().saveConfig();
                pSender.sendMessage("Spawn for " + dungeon5.getDungeonName() + "(id: " + dungeon5.getDungeonID() + ") has been set to " + (Object)pSender.getLocation());
                return true;
            }
            pSender.sendMessage("ERROR: dungeon = null");
            return true;
        }
        if (args[0].equals("materials") && sender instanceof Player) {
            Player pSender = (Player)sender;
            pSender.getInventory().addItem(new ItemStack[]{ItemUtils.setAmount(DungeonsAPI.get().getEnchantedObsidian().clone(), 10)});
            pSender.getInventory().addItem(new ItemStack[]{DungeonsAPI.get().getDimensionWeb()});
            pSender.getInventory().addItem(new ItemStack[]{DungeonsAPI.get().getCosmonautFuel()});
            pSender.getInventory().addItem(new ItemStack[]{DungeonsAPI.get().getTeleportationCrystal()});
            pSender.getInventory().addItem(new ItemStack[]{DungeonsAPI.get().getInterdimensionalCore()});
            return true;
        }
        if (args[0].equals("soulpearl") && sender instanceof Player) {
            Player pSender = (Player)sender;
            pSender.getInventory().addItem(new ItemStack[]{DungeonsRewardsAPI.getSoulPearl()});
            return true;
        }
        if (args[0].equals("soulanvil") && sender instanceof Player) {
            Player pSender = (Player)sender;
            pSender.getInventory().addItem(new ItemStack[]{DungeonsRewardsAPI.getSoulAnvil()});
            return true;
        }
        if (args[0].equals("speedtrinket") && sender instanceof Player) {
            Player pSender = (Player)sender;
            pSender.getInventory().addItem(new ItemStack[]{new SpeedTrinketBuilder().build()});
            return true;
        }
        if (args[0].equals("yijkistaff") && sender instanceof Player) {
            Player pSender = (Player)sender;
            pSender.getInventory().addItem(new ItemStack[]{new YijkiStaffBuilder().build()});
            return true;
        }
        if (args[0].equals("phoenixfeather") && sender instanceof Player) {
            Player pSender = (Player)sender;
            pSender.getInventory().addItem(new ItemStack[]{new PhoenixFeatherBuilder().build()});
            return true;
        }
        if (args[0].equals("holywhite") && sender instanceof Player) {
            Player pSender = (Player)sender;
            pSender.getInventory().addItem(new ItemStack[]{DungeonsRewardsAPI.getHolyWhiteScroll()});
            return true;
        }
        if (args[0].equals("reset")) {
            int id = Integer.parseInt(args[1]);
            Dungeon d2 = DungeonsAPI.get().getDungeon(id);
            d2.reset();
            sender.sendMessage("Executed reset() for: " + d2);
            return true;
        }
        if (args[0].equals("lootbag") && sender instanceof Player) {
            Player pSender = (Player)sender;
            pSender.getInventory().addItem(new ItemStack[]{DungeonsRewardsAPI.getDungeonLootBag(pSender, DungeonsAPI.get().getDungeon(0))});
            pSender.getInventory().addItem(new ItemStack[]{DungeonsRewardsAPI.getDungeonLootBag(pSender, DungeonsAPI.get().getDungeon(1))});
            return true;
        }
        if (args[0].equals("saveplayers")) {
            for (DungeonPlayer player2 : CosmicDungeonsPlugin.get_().getApi().getPlayers().values()) {
                player2.save();
            }
            sender.sendMessage("saved " + CosmicDungeonsPlugin.get_().getApi().getPlayers().size() + " DungeonPlayers");
            return true;
        }
        if (args[0].equals("setholidayportal") && sender instanceof Player) {
            Player pSender = (Player)sender;
            Location l1 = pSender.getLocation();
            Location l2 = pSender.getLocation().add(0.0, 1.0, 0.0);
            CosmicDungeonsPlugin.get_().getConfig().set("holiday.portalBlockA", (Object)LocationUtils.convertLocationToString(l1));
            CosmicDungeonsPlugin.get_().getConfig().set("holiday.portalBlockB", (Object)LocationUtils.convertLocationToString(l2));
            CosmicDungeonsPlugin.get_().saveConfig();
            HolidayPortalTask.setPortalBlockA(l1.getBlock());
            HolidayPortalTask.setPortalBlockB(l2.getBlock());
            pSender.sendMessage("Set locations of Holiday Portal to...");
            pSender.sendMessage("l1: " + (Object)l1.getBlock().getLocation());
            pSender.sendMessage("l2: " + (Object)l2.getBlock().getLocation());
            return true;
        }
        if (args[0].equals("spawnholidayportal")) {
            HolidayPortalTask.setForceSpawn(true);
            sender.sendMessage("Holiday portal will spawn in next task tick (<60 seconds)");
            return true;
        }
        if (args[0].equals("toggleholidayportals")) {
            CosmicDungeonsPlugin.get_().getConfig().set("holiday.dailyPortals", (Object)(!CosmicDungeonsPlugin.get_().getConfig().getBoolean("holiday.dailyPortals") ? 1 : 0));
            CosmicDungeonsPlugin.get_().saveConfig();
            sender.sendMessage("dailyPortals => " + CosmicDungeonsPlugin.get_().getConfig().getBoolean("holiday.dailyPortals"));
            return true;
        }
        if (args[0].equalsIgnoreCase("togglecheapkeys")) {
            boolean current = !CosmicDungeonsPlugin.get().isLowCostKeys();
            CosmicDungeonsPlugin.get().setLowCostKeys(current);
            CosmicDungeonsPlugin.get().getConfig().set("lowCostKeys", (Object)current);
            CosmicDungeonsPlugin.get().saveConfig();
            sender.sendMessage(CC.Red + "Low Cost Keys: " + current);
            return true;
        }
        if (args[0].equals("togglekey")) {
            String dungeonName = args[1];
            Dungeon d2 = DungeonsAPI.get().getDungeonByWorldName(dungeonName);
            d2.setKeyRequired(!d2.isKeyRequired());
            sender.sendMessage("key requirement for next " + d2.toString() + " dungeon portal: " + d2.isKeyRequired());
            return true;
        }
        if (args[0].equals("togglekeybuy") || args[0].equalsIgnoreCase("togglebuy")) {
            DungeonType type = DungeonType.getFromName(args[1]);
            if (type == null) {
                sender.sendMessage(CC.Red + "Invalid dungeon type, valid: " + Arrays.toString((Object[])DungeonType.values()));
                return true;
            }
            if (CosmicDungeonsPlugin.get().getDisabledDungeons().contains((Object)type)) {
                CosmicDungeonsPlugin.get().getDisabledDungeons().remove((Object)type);
                CosmicDungeonsPlugin.get().getConfig().set("dungeons.disabled." + type.name(), (Object)false);
            } else {
                CosmicDungeonsPlugin.get().getDisabledDungeons().add(type);
                CosmicDungeonsPlugin.get().getConfig().set("dungeons.disabled." + type.name(), (Object)true);
            }
            CosmicDungeonsPlugin.get().saveConfig();
            sender.sendMessage(CC.Red + "Keys toggled from Dungeon Master, disabled: " + CosmicDungeonsPlugin.get().getDisabledDungeons().contains((Object)type));
            return true;
        }
        if (args[0].equalsIgnoreCase("cleartoptimes")) {
            for (DungeonType type4 : DungeonType.values()) {
                type4.getDungeonInstance().getTopDungeonTime().clear();
                type4.getDungeonInstance().getTopDungeonTime().save(CosmicDungeonsPlugin.get().getConfig());
            }
            CosmicDungeonsPlugin.get().saveConfig();
            sender.sendMessage(CC.Red + "Top times cleared!");
            return true;
        }
        if (args[0].equalsIgnoreCase("toggletelecoords")) {
            DungeonType type = DungeonType.DESTROYED_OUTPOST;
            if (type.getDungeonInstance() != null) {
                type.getDungeonInstance().setGlobalPortal(!type.getDungeonInstance().isGlobalPortal());
                sender.sendMessage(CC.RedB + "Set Destroyed Outpost Portal to Global: " + type.getDungeonInstance().isGlobalPortal());
                sender.sendMessage(CC.Gray + "This will be reset the next time the dungeon is reset!");
            }
            return true;
        }
        if (args[0].equalsIgnoreCase("portalhistory")) {
            sender.sendMessage(CC.Red + "Viewing all logs...");
            CosmicDungeonsPlugin.get().getPortalLogs().forEach((dungeonType, logs) -> {
                List<PortalLog> logList = Lists.newLinkedList(logs);
                logList.sort(Comparator.comparingLong(PortalLog::getPlacetime));
                sender.sendMessage(CC.Red + " " + dungeonType.getDungeonName() + " Portal Log:");
                for (PortalLog log : logList)
                    sender.sendMessage(" " + log.getPlacer() + " placed at " + log.getX() + "x, " + log.getY() + "y, " + log.getZ() + "z " + TimeUtils.formatDifference(System.currentTimeMillis() - log.getPlacetime()) + " ago");
            });
            return true;
        }
        return true;
    }
}
