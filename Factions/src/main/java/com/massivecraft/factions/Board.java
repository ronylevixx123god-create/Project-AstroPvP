package com.massivecraft.factions;

import com.compilex.CosmicOutposts.CosmicOutpostsAPI;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.factionpoints.CosmicFactionPoints;
import com.cosmicpvp.factionpoints.FactionsCoreChunkAPI;
import com.cosmicpvp.factionpoints.struct.ExpiringChunkData;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Lists;
import com.google.gson.reflect.TypeToken;
import com.massivecraft.factions.struct.FactionPermissions;
import com.massivecraft.factions.struct.Permission;
import com.massivecraft.factions.struct.PermissionManager;
import com.massivecraft.factions.struct.Relation;
import com.massivecraft.factions.util.AsciiCompass;
import com.massivecraft.factions.util.TimeUtil;
import com.massivecraft.factions.zcore.util.DiscUtil;
import com.wimbli.WorldBorder.BorderData;
import com.wimbli.WorldBorder.WorldBorder;
import mkremins.fanciful.FancyMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.io.File;
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

public class Board {
    private static transient File file = new File(P.p.getDataFolder(), "board.json");

    public static transient ConcurrentHashMap<FLocation, String> flocationIds = new ConcurrentHashMap<>();

    public static transient ConcurrentHashMap<String, Integer> fclaimCounts = new ConcurrentHashMap<>();

    private static Board me;

    public Board() {
        me = this;
    }

    public static Board getInstance() {
        return me;
    }

    public static String getIdAt(FLocation flocation) {
        if (!flocationIds.containsKey(flocation))
            return "0";
        return flocationIds.get(flocation);
    }

    public static List<String> disbandingFactions = new ArrayList<>();

    public static Faction getFactionAt(FLocation flocation) {
        String id = getIdAt(flocation);
        Faction f = Factions.i.get(id);
        if (f == null && !id.equals("0") && !disbandingFactions.contains(id)) {
            Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "arkreboot");
            if (flocation != null)
                Bukkit.getLogger().severe("Unable to get faction with id=" + id + " for location=" + flocation.toString());
        }
        return f;
    }

    public static void setIdAt(String id, FLocation flocation) {
        Faction oldFaction = getFactionAt(flocation);
        if (oldFaction != null && oldFaction.isNormal()) {
            String oldId = oldFaction.getId();
            if (fclaimCounts.containsKey(oldId))
                fclaimCounts.put(oldId, Integer.valueOf(((Integer)fclaimCounts.get(oldId)).intValue() - 1));
        }
        clearOwnershipAt(flocation);
        if (id.equals("0"))
            removeAt(flocation);
        flocationIds.put(flocation, id);
        Integer currentCounts = fclaimCounts.getOrDefault(id, Integer.valueOf(0));
        fclaimCounts.put(id, Integer.valueOf(currentCounts.intValue() + 1));
    }

    public static void setFactionAt(Faction faction, FLocation flocation) {
        setIdAt(faction.getId(), flocation);
    }

    public static void removeAt(FLocation flocation) {
        String id = flocationIds.get(flocation);
        if (id == null)
            return;
        Integer claims = fclaimCounts.get(id);
        if (claims != null) {
            fclaimCounts.put(id, Integer.valueOf(Math.max(0, claims.intValue() - 1)));
        } else {
            Bukkit.getLogger().info("[Factions] Faction ID " + id + " called removeAt with no fclaimCounts value.");
        }
        clearOwnershipAt(flocation);
        flocationIds.remove(flocation);
    }

    public static void clearOwnershipAt(FLocation flocation) {
        Faction faction = getFactionAt(flocation);
        if (faction != null && faction.isNormal())
            faction.clearClaimOwnership(flocation);
        FactionWrapper wrapper = FactionWrappers.get(faction);
        if (wrapper != null) {
            for (FLocation l : wrapper.chunkClaims.keySet()) {
                if (l.equals(flocation))
                    wrapper.chunkClaims.remove(l);
            }
            for (Map.Entry<String, Location> entry : wrapper.allyWarps.entrySet()) {
                if (((Location)entry.getValue()).getChunk().equals(((Location)entry.getValue()).getWorld().getChunkAt((int)flocation.getX(), (int)flocation.getZ()))) {
                    wrapper.allyWarps.remove(entry.getKey());
                    faction.sendMessage(ChatColor.YELLOW + ChatColor.BOLD.toString() + "(!) " + ChatColor.YELLOW + "You have removed the ally warp for " + Factions.i
                            .get(entry.getKey()).getTag() + " due to unclaiming land.");
                }
            }
            for (Map.Entry<String, Location> entry : wrapper.allAllyWarps.entrySet()) {
                Location l = entry.getValue();
                String name = entry.getKey();
                if (l.getChunk().equals(l.getWorld().getChunkAt((int)flocation.getX(), (int)flocation.getZ()))) {
                    wrapper.allyWarps.remove(name);
                    faction.sendMessage(ChatColor.YELLOW + ChatColor.BOLD.toString() + "(!) " + ChatColor.YELLOW + "You have removed the open ally warp " + name + " due to unclaiming land.");
                }
            }
        }
    }

    public static int unclaimAll(String factionId) {
        Faction faction = Factions.i.get(factionId);
        if (faction != null && faction.isNormal())
            faction.clearAllClaimOwnership();
        FactionPermissions permissions = PermissionManager.get().getPermissions(faction);
        int found = 0;
        Iterator<Map.Entry<FLocation, String>> iter = flocationIds.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<FLocation, String> entry = iter.next();
            if (((String)entry.getValue()).equals(factionId)) {
                iter.remove();
                found++;
            }
        }
        if (permissions != null) {
            permissions.getFactionDefaultPermissions().clear();
            permissions.getFactionChunkPermissionMap().clear();
            permissions.getDefaultPlayerPermissions().clear();
            permissions.getPlayerPermissionMap().clear();
            permissions.getFactionEdittedChunkPermissions().clear();
            permissions.getPlayerEdittedChunkPermissions().clear();
            if (faction != null)
                Bukkit.getLogger().info("Removing all Faction Permissions for " + faction.getTag());
        }
        fclaimCounts.put(factionId, Integer.valueOf(0));
        return found;
    }

    public static List<FLocation> getAllClaimedLand(Faction f) {
        List<FLocation> locs = new ArrayList<>();
        for (Map.Entry<FLocation, String> entry : flocationIds.entrySet()) {
            if (((String)entry.getValue()).equals(f.getId()))
                locs.add(entry.getKey());
        }
        return locs;
    }

    public static boolean isBorderLocation(FLocation flocation) {
        Faction faction = getFactionAt(flocation);
        FLocation a = flocation.getRelative(1, 0);
        FLocation b = flocation.getRelative(-1, 0);
        FLocation c = flocation.getRelative(0, 1);
        FLocation d = flocation.getRelative(0, -1);
        return (faction != getFactionAt(a) || faction != getFactionAt(b) || faction != getFactionAt(c) || faction != getFactionAt(d));
    }

    public static boolean isConnectedLocation(FLocation flocation, Faction faction) {
        FLocation a = flocation.getRelative(1, 0);
        FLocation b = flocation.getRelative(-1, 0);
        FLocation c = flocation.getRelative(0, 1);
        FLocation d = flocation.getRelative(0, -1);
        return (faction == getFactionAt(a) || faction == getFactionAt(b) || faction == getFactionAt(c) || faction == getFactionAt(d));
    }

    public static void clean() {
        for (Map.Entry<FLocation, String> entry : flocationIds.entrySet()) {
            if (!Factions.i.exists(entry.getValue())) {
                P.p.log("Board cleaner removed " + (String)entry.getValue() + " from " + entry.getKey());
                flocationIds.remove(entry.getKey());
                fclaimCounts.remove(entry.getValue());
            }
        }
    }

    public static int getFactionCoordCount(String factionId) {
        Integer found = fclaimCounts.get(factionId);
        if (found != null)
            return found.intValue();
        int ret = 0;
        Iterator<String> factionIds = flocationIds.values().iterator();
        while (factionIds.hasNext()) {
            String thatFactionId = factionIds.next();
            if (thatFactionId.equals(factionId))
                ret++;
        }
        fclaimCounts.put(factionId, Integer.valueOf(ret));
        return ret;
    }

    public static int getFactionCoordCount(Faction faction) {
        return getFactionCoordCount(faction.getId());
    }

    public static int getFactionCoordCountInWorld(Faction faction, String worldName) {
        String factionId = faction.getId();
        int ret = 0;
        Iterator<Map.Entry<FLocation, String>> iter = flocationIds.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<FLocation, String> entry = iter.next();
            if (((String)entry.getValue()).equals(factionId) && ((FLocation)entry.getKey()).getWorldName().equals(worldName))
                ret++;
        }
        return ret;
    }

    public static ArrayList<FancyMessage> getMap(Faction faction, FLocation fLocation, Player player) {
        double wMult = 1.0;
        double hMult = 1.0;
        if (player.hasPermission("cosmicpvp.heroic") || P.p.outpostsEnabled && CosmicOutpostsAPI.controlsTraineeOutpost(player)) {
            hMult = 1.85;
            wMult = 1.25;
        }
        return Board.getMap(faction, fLocation, player.getLocation().getYaw(), wMult, hMult, player);
    }

    public static ArrayList<FancyMessage> getMap(Faction faction, FLocation flocation, double inDegrees) {
        return Board.getMap(faction, flocation, inDegrees, 1.0, 1.0, null);
    }

    public static ArrayList<FancyMessage> getMap(Faction faction, FLocation flocation, double inDegrees, double widthMultiplier, double heightMultiplier, Player player) {
        FPlayer fPlayer = FPlayers.i.get(player);
        long timeSinceClaimed;
        ExpiringChunkData data;
        ArrayList<FancyMessage> ret = new ArrayList<>();
        Faction factionLoc = Board.getFactionAt(flocation);
        ArrayList<String> asciiCompass = AsciiCompass.getAsciiCompass(inDegrees, ChatColor.DARK_GREEN, P.p.txt.parse("<gray>"));

        String str = "(" + flocation.getCoordString() + ") " + factionLoc.getTag(faction);
        if (P.p.factionPointsEnabled && (data = CosmicFactionPoints.get().getChunkManager().getChunksToExpire().get(flocation)) != null && data.getTimeClaimed() > 0L && (timeSinceClaimed = System.currentTimeMillis() - data.getTimeClaimed()) > 0L) {
            str = timeSinceClaimed < TimeUnit.HOURS.toMillis(24L) ? str + " (claim " + TimeUtil.formatSeconds(timeSinceClaimed / 1000L) + " old)" : str + " (claim >1d old)";
        }
        int spawnerCount = Board.getSpawnersInChunk(flocation);
        if (spawnerCount > 0 && player != null) {
            new FancyMessage(P.p.txt.titleize(str)).formattedTooltip(Board.getChunkSpawnerHoverData(flocation)).send(player);
        } else {
            ret.add(new FancyMessage(P.p.txt.titleize(str)));
        }
        int halfWidth = Conf.mapWidth / 2;
        // Use player's value for height
        int halfHeight = Conf.mapHeight / 2;
        FLocation topLeft = flocation.getRelative(-halfWidth, -halfHeight);
        int width = halfWidth * 2 + 1;
        int height = halfHeight * 2 + 1;
        if (Conf.showMapFactionKey) {
            --height;
        }
        Map<String, Character> fList = new HashMap<>();
        int chrIdx = 0;
        for (int dz = 0; dz < height; dz++) {
            FancyMessage row = new FancyMessage("");
            if (dz < 3) {
                row.then(asciiCompass.get(dz));
            }
            for (int dx = (dz < 3 ? 6 : 3); dx < width; dx++) {
                if (dx == halfWidth && dz == halfHeight) {
                    row.then("+").color(ChatColor.AQUA).tooltip("You are here");
                } else {
                    FLocation flocationHere = topLeft.getRelative(dx, dz);
                    Faction factionHere = Board.getFactionAt(flocationHere);
                    Relation relation = faction.getRelationTo(factionHere);
                    if (factionHere.isWilderness()) {
                        row.then("-").color(ChatColor.GRAY);
                        if (fPlayer.getPlayer().hasPermission(Permission.CLAIM.node)) {

                            row.tooltip(String.format(ChatColor.translateAlternateColorCodes('&', "Click to try to claim &2(%1$d, %2$d)"), dx + topLeft.getX(), dz + topLeft.getZ()))
                                    .command(String.format("/f claimat %s %d %d", flocation.getWorldName(), dx + topLeft.getX(), dz + topLeft.getZ()));
                        }
                    } else if (factionHere.isSafeZone()) {
                        row.then("+").color(Conf.colorPeaceful).tooltip(oneLineToolTip(factionHere, fPlayer));
                    } else if (factionHere.isWarZone()) {
                        row.then("+").color(Conf.colorWar).tooltip(oneLineToolTip(factionHere, fPlayer));
                    } else if (factionHere == faction
                            || factionHere == factionLoc
                            || relation.isAtLeast(Relation.ALLY)
                            || Conf.showNeutralFactionsOnMap
                            && relation.equals(Relation.NEUTRAL)
                            || Conf.showTruceFactionsOnMap
                            && relation.equals(Relation.TRUCE)
                            || Conf.showEnemyFactionsOnMap
                            && relation.equals(Relation.ENEMY)) {
                        if (!fList.containsKey(factionHere.getTag())) {
                            if (Conf.mapKeyChrs.length < chrIdx) continue;
                            try {
                                fList.put(factionHere.getTag(), Conf.mapKeyChrs[chrIdx++]);
                            } catch (ArrayIndexOutOfBoundsException err) {
                                err.printStackTrace();
                                continue;
                            }
                        }
                        char tag = fList.get(factionHere.getTag());
                        // row.then(String.valueOf(tag)).color(factionHere.getColorTo(faction)).tooltip(oneLineToolTip(factionHere, fPlayer));
                        //TRENT FIX HERE
                        //if (P.p.factionPointsEnabled && FactionsCoreChunkAPI.isCoreChunk(flocationHere)){
                        //    row.then("$").color(ChatColor.YELLOW).tooltip(faction.getTag() + "'s Core").color(ChatColor.YELLOW);
                        //    Bukkit.broadcastMessage("IS CORE");
                        //}else{
                        //    row.then("!").color(factionHere.getColorTo(faction)).tooltip(oneLineToolTip(factionHere, fPlayer));
                        //    Bukkit.broadcastMessage("IS NOT CORE");
                        //}
                        //row.then((P.p.factionPointsEnabled && FactionsCoreChunkAPI.isCoreChunk(flocationHere) ? ChatColor.YELLOW : factionHere.getColorTo(faction)) + "" + tag);


                        row.then((P.p.factionPointsEnabled && FactionsCoreChunkAPI.isCoreChunk(flocationHere) ? ChatColor.YELLOW : factionHere.getColorTo(faction)) + "" + tag).tooltip(oneLineToolTip(factionHere, fPlayer));
                    } else {
                        row.then("-").color(ChatColor.GRAY);
                    }
                }
            }
            ret.add(row);
        }

        if (Conf.showMapFactionKey) {
            FancyMessage fRow = new FancyMessage("");
            for (String key : fList.keySet()) {
                Faction f = Factions.i.getByTag(key);
                fRow.then(String.format("%s%s: %s ", f != null ? f.getColorTo(faction) : ChatColor.GRAY, fList.get(key), key));
            }
            ret.add(fRow);
        }
        return ret;
    }

    private static List<String> oneLineToolTip(Faction faction, FPlayer to) {
        return Collections.singletonList(faction.describeTo(to));
    }

    private static ConcurrentMap<Object, Object> expiringSpawnerCountMap = CacheBuilder.newBuilder().expireAfterWrite(1L, TimeUnit.MINUTES).build().asMap();

    private static ConcurrentMap<Object, Object> expiringChunkSpawnerHoverData = CacheBuilder.newBuilder().expireAfterWrite(1L, TimeUnit.MINUTES).build().asMap();

    private static int getSpawnersInChunk(FLocation fLocation) {
        MobSpawnerData[] data = (MobSpawnerData[]) expiringSpawnerCountMap.get(fLocation.toString());
        if (data != null)
            return getSpawnerCount(data);
        List<MobSpawnerData> listData = Lists.newArrayList();
        for (BlockState spawner : fLocation.toChunk().getTileEntities()) {
            if (spawner instanceof CreatureSpawner)
                listData.add(new MobSpawnerData((CreatureSpawner)spawner));
        }
        MobSpawnerData[] array = listData.toArray(new MobSpawnerData[listData.size()]);
        expiringSpawnerCountMap.put(fLocation.toString(), array);
        expiringChunkSpawnerHoverData.put(fLocation.toString(), Board.getChunkSpawnerHoverData(fLocation));
        return getSpawnerCount(array);
    }

    private static int getSpawnerCount(MobSpawnerData[] data) {
        int total = 0;
        return total;
    }

    private static List<FancyMessage> getChunkSpawnerHoverData(FLocation fLocation) {
        List<FancyMessage> hoverData = (List<FancyMessage>) expiringChunkSpawnerHoverData.getOrDefault(fLocation.toString(), new ArrayList<>());
        if (hoverData.size() > 0)
            return hoverData;
        HashMap<EntityType, Integer> spawnerTypeCounts = new HashMap<>();
        for (MobSpawnerData m : (MobSpawnerData[]) expiringSpawnerCountMap.get(fLocation.toString()))
            spawnerTypeCounts.put(m.getSpawnerType(), spawnerTypeCounts.getOrDefault(m.getSpawnerType(), 0));
        hoverData.add(new FancyMessage(CC.Aqua + "Chunk: " + CC.White + fLocation.toRawString()));
        for (Map.Entry<EntityType, Integer> entry : spawnerTypeCounts.entrySet())
            hoverData.add(new FancyMessage(CC.White + entry.getValue() + "x " + CC.Gray + entry.getKey().name().replace("_", " ")));
        return hoverData;
    }

    public static Map<String, Map<String, String>> dumpAsSaveFormat() {
        Map<String, Map<String, String>> worldCoordIds = new HashMap<>();
        for (Map.Entry<FLocation, String> entry : flocationIds.entrySet()) {
            String worldName = ((FLocation)entry.getKey()).getWorldName();
            String coords = ((FLocation)entry.getKey()).getCoordString();
            String id = entry.getValue();
            if (!worldCoordIds.containsKey(worldName))
                worldCoordIds.put(worldName, new TreeMap<>());
            ((Map<String, String>)worldCoordIds.get(worldName)).put(coords, id);
        }
        return worldCoordIds;
    }

    public static void loadFromSaveFormat(Map<String, Map<String, String>> worldCoordIds) {
        boolean worldBorderExists = (new File("plugins/WorldBorder.jar")).exists();
        flocationIds.clear();
        fclaimCounts.clear();
        for (Map.Entry<String, Map<String, String>> entry : worldCoordIds.entrySet()) {
            String worldName = entry.getKey();
            Object worldBorder = null;
            int originalRadiusX = -1, originalRadiusZ = -1;
            if (worldBorderExists) {
                BorderData border = null;
                if (worldName.contains("dungeon_") || worldName.contains("end") || worldName.contains("nether")) {
                    worldBorder = border = WorldBorder.plugin.getWorldBorder(worldName);
                    if (worldBorder != null) {
                        border.setRadiusX((originalRadiusX = border.getRadiusX()) + 160);
                        border.setRadiusZ((originalRadiusZ = border.getRadiusZ()) + 160);
                    }
                }
            }
            int removed = 0;
            for (Map.Entry<String, String> entry2 : (Iterable<Map.Entry<String, String>>)((Map)entry.getValue()).entrySet()) {
                String[] coords = ((String)entry2.getKey()).trim().split("[,\\s]+");
                int x = Integer.parseInt(coords[0]);
                int z = Integer.parseInt(coords[1]);
                String factionId = entry2.getValue();
                if (factionId.equals("-2") && worldBorder != null && !((BorderData)worldBorder).insideBorder((x * 16), (z * 16))) {
                    removed++;
                    continue;
                }
                flocationIds.put(new FLocation(worldName, x, z), factionId);
            }
            if (removed > 0)
                Bukkit.getLogger().info("Skipping load of " + removed + " claims due to being outside of the WorldBorder in " + worldName + "...");
            if (worldBorder != null && originalRadiusX != -1) {
                ((BorderData)worldBorder).setRadiusX(originalRadiusX);
                ((BorderData)worldBorder).setRadiusZ(originalRadiusZ);
                Bukkit.getLogger().info("[Factions] Restoring WorldBorder in world " + worldName + " to " + originalRadiusX + ", " + originalRadiusZ);
            }
        }
        for (String id : flocationIds.values()) {
            if (fclaimCounts.containsKey(id)) {
                fclaimCounts.put(id, Integer.valueOf(((Integer)fclaimCounts.get(id)).intValue() + 1));
                continue;
            }
            fclaimCounts.put(id, Integer.valueOf(1));
        }
    }

    public static boolean save() {
        try {
            DiscUtil.write(file, P.p.gson.toJson(dumpAsSaveFormat()));
        } catch (Exception e) {
            e.printStackTrace();
            P.p.log("Failed to save the board to disk.");
            return false;
        }
        return true;
    }

    public static boolean load() {
        P.p.log("Loading board from disk");
        if (!file.exists()) {
            P.p.log("No board to load from disk. Creating new file.");
            save();
            return true;
        }
        try {
            Type type = (new TypeToken<Map<String, Map<String, String>>>() {

            }).getType();
            Map<String, Map<String, String>> worldCoordIds = P.p.gson.fromJson(DiscUtil.read(file), type);
            loadFromSaveFormat(worldCoordIds);
        } catch (Exception e) {
            e.printStackTrace();
            P.p.log("Failed to load the board from disk.");
            return false;
        }
        return true;
    }
}
