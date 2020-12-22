/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.compat.ParamBuilder
 *  com.cosmicpvp.cosmicutils.utils.ReflectionUtils
 *  com.earth2me.essentials.Essentials
 *  com.earth2me.essentials.User
 *  com.massivecraft.factions.Board
 *  com.massivecraft.factions.FLocation
 *  com.sk89q.worldguard.protection.managers.RegionManager
 *  com.sk89q.worldguard.protection.regions.ProtectedRegion
 *  me.vaqxine.NetworkManager.NetworkManager
 *  net.minecraft.server.v1_7_R4.Entity
 *  net.minecraft.server.v1_7_R4.EntityInsentient
 *  net.minecraft.server.v1_7_R4.PathfinderGoal
 *  net.minecraft.server.v1_7_R4.PathfinderGoalSelector
 *  net.minecraft.server.v1_7_R4.World
 *  org.arkhamnetwork.Arkkit.commands.CommandArkReboot
 *  org.bukkit.Bukkit
 *  org.bukkit.GameMode
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.World
 *  org.bukkit.World$Environment
 *  org.bukkit.block.Block
 *  org.bukkit.craftbukkit.v1_7_R4.CraftWorld
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Item
 *  org.bukkit.entity.Player
 *  org.bukkit.event.entity.CreatureSpawnEvent$SpawnReason
 *  org.bukkit.event.player.PlayerTeleportEvent$TeleportCause
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.util.Vector
 */
package me.ifamasssxd.cosmicbosses.utils;

import com.cosmicpvp.cosmicutils.compat.ParamBuilder;
import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.User;
import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import me.ifamasssxd.cosmicbosses.CosmicBosses;
import me.ifamasssxd.cosmicbosses.bosses.yijki.WitherBoss;
import me.ifamasssxd.cosmicbosses.dungeonboss.DungeonYijki;
import me.ifamasssxd.cosmicbosses.dungeonboss.YijkiRunnerEntity;
import me.ifamasssxd.cosmicbosses.entities.CustomEntity;
import me.ifamasssxd.cosmicbosses.struct.Boss;
import me.ifamasssxd.cosmicbosses.struct.enums.BossType;
import me.ifamasssxd.cosmicbosses.struct.enums.ChunkCoords;
import me.ifamasssxd.cosmicbosses.utils.ReflectionUtils;
import me.ifamasssxd.cosmicbosses.utils.WorldGuardUtils;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityInsentient;
import net.minecraft.server.v1_8_R3.PathfinderGoal;
import net.minecraft.server.v1_8_R3.PathfinderGoalSelector;
import net.minecraft.server.v1_8_R3.World;
import org.arkhamnetwork.Arkkit.commands.CommandArkReboot;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

public class BossUtils {
    private static Essentials ess;
    static boolean factions;
    private static HashMap<ChunkCoords, Boolean> warzone;
    private static Random rand;

    public BossUtils() {
        factions = Bukkit.getPluginManager().isPluginEnabled("Factions");
    }

    public static boolean isInGodMode(Player player) {
        if (player.getGameMode() == GameMode.CREATIVE) {
            return true;
        }
        if (ess != null) {
            User user = ess.getUser(player);
            return user.isVanished() || user.isGodModeEnabled();
        }
        return false;
    }

    public static int getHighestBlockYAt(Location location, boolean needSolid) {
        for (int i = 255; i > 0; --i) {
            Block block = location.getWorld().getBlockAt(location.getBlockX(), i, location.getBlockZ());
            Material type = block.getType();
            if ((type == Material.AIR || needSolid) && (!needSolid || !type.isBlock() || !type.isSolid() || type.isTransparent())) continue;
            return i;
        }
        return location.getWorld().getHighestBlockYAt(location.getBlockX(), location.getBlockZ());
    }

    public static int getHighestBlockYAt(org.bukkit.World world, int x, int z, boolean needSolid) {
        for (int i = 255; i > 0; --i) {
            Block block = world.getBlockAt(x, i, z);
            Material type = block.getType();
            if ((type == Material.AIR || needSolid) && (!needSolid || !type.isBlock() || !type.isSolid() || type.isTransparent())) continue;
            return i;
        }
        return world.getHighestBlockYAt(x, z);
    }

    public static boolean canAffectPlayer(Player pl, boolean checkBattlefield) {
        return BossUtils.canAffectPlayer(pl, checkBattlefield, false);
    }

    public static boolean canAffectPlayer(Player pl, boolean checkBattlefield, boolean checkEnd) {
        if (ess == null) {
            ess = (Essentials)Bukkit.getPluginManager().getPlugin("Essentials");
        }
        if (pl.hasMetadata("NPC")) {
            return false;
        }
        if (ess != null && (ess.getUser(pl).isVanished() || ess.getUser(pl).isGodModeEnabled())) {
            return false;
        }
        if (WorldGuardUtils.isPvPEnabled(pl.getLocation())) {
            if (checkEnd && pl.getLocation().getWorld().getEnvironment() == World.Environment.THE_END) {
                return true;
            }
            String regionName = WorldGuardUtils.getRegionName(pl.getLocation());
            if (regionName.contains("warzone") || checkBattlefield && regionName.contains("battlefield")) {
                FLocation ploc;
                return !factions || Board.getFactionAt((FLocation)(ploc = new FLocation(pl.getLocation()))).isWarZone();
            }
        }
        return false;
    }

    public static org.bukkit.entity.Entity spawnDungeonBoss(Location location, int phase) {
        DungeonYijki boss = (DungeonYijki)BossType.DUNGEON_YIJKI.createInstance(location);
        boss.onBossSpawn();
        try {
            boss.setBossPhase(DungeonYijki.BossPhase.values()[phase - 1]);
        }
        catch (Exception e) {
            Bukkit.getLogger().info("Invalid Dungeon Yijki Phase: " + phase + "! " + DungeonYijki.BossPhase.values().length);
        }
        return boss.getEntity();
    }

    public static void teleportPlayer(Player p, Location location) {
        p.setMetadata("bypassCombatTeleport", (MetadataValue)new FixedMetadataValue((Plugin)CosmicBosses.getInstance(), (Object)location));
        p.teleport(location, PlayerTeleportEvent.TeleportCause.UNKNOWN);
    }

    public static org.bukkit.entity.Entity spawnDungeonBoss(Location location, int phase, BossType type) {
        DungeonYijki boss = (DungeonYijki)type.createInstance(location);
        boss.onBossSpawn();
        try {
            boss.setBossPhase(DungeonYijki.BossPhase.values()[phase - 1]);
        }
        catch (Exception e) {
            Bukkit.getLogger().info("Invalid Dungeon Yijki Phase: " + phase + "! " + DungeonYijki.BossPhase.values().length);
        }
        return boss.getEntity();
    }

    public static org.bukkit.entity.Entity createDungeonGhost(Location spawn, Location target, int aliveDuration) {
        CraftWorld craft = (CraftWorld)spawn.getWorld();
        YijkiRunnerEntity entity = new YijkiRunnerEntity((World)craft.getHandle(), spawn, target, aliveDuration);
        entity.setPosition(spawn.getX(), spawn.getY(), spawn.getZ());
        craft.getHandle().addEntity((Entity)entity, CreatureSpawnEvent.SpawnReason.CUSTOM);
        return entity.getBukkitEntity();
    }

    public static void spawnBoss(String[] args) {
        Location spawn;
        String bossName = args[0];
        org.bukkit.World world = Bukkit.getWorld((String)args[1]);
        Location loc = new Location(world, (double)Integer.parseInt(args[2]), (double)Integer.parseInt(args[3]), (double)Integer.parseInt(args[4]));
        if (!loc.getChunk().isLoaded()) {
            loc.getChunk().load();
        }
        if (!(spawn = new Location(world, (double)Integer.parseInt(args[5]), (double)Integer.parseInt(args[6]), (double)Integer.parseInt(args[7]))).getChunk().isLoaded()) {
            spawn.getChunk().load();
        }
        double health = Double.parseDouble(args[8]);
        BossType type = BossType.getBossType(bossName);
        if (type == null) {
            return;
        }
        Boss newBoss = type.createInstance(spawn);
        newBoss.onBossSpawn();
        newBoss.getEntity().teleport(loc);
        newBoss.getEntity().setHealth(health);
    }

    public static boolean isWarzone(Location loc) {
        if (loc.getWorld().getName().toLowerCase().startsWith("dungeon")) {
            return true;
        }
        ChunkCoords coords = new ChunkCoords(loc.getChunk().getX(), loc.getChunk().getZ());
        if (warzone.containsKey(coords)) {
            return warzone.get(coords);
        }
        String regionName = WorldGuardUtils.getRegionName(loc);
        if (regionName.contains("warzone")) {
            FLocation ploc;
            if (factions && !Board.getFactionAt((FLocation)(ploc = new FLocation(loc))).getId().equals("-2")) {
                warzone.put(coords, false);
                return false;
            }
            warzone.put(coords, true);
            return true;
        }
        return false;
    }

    public static Entity createCustomEntity(CustomEntity type, Boss boss, Location location) {
        CraftWorld world = (CraftWorld)location.getWorld();
        try {
            Entity entity = (Entity)type.getCustomClass().getDeclaredConstructor(World.class).newInstance(new Object[]{world.getHandle()});
            entity.setPosition(location.getX(), location.getY(), location.getZ());
            world.getHandle().addEntity(entity, CreatureSpawnEvent.SpawnReason.CUSTOM);
            if (entity instanceof WitherBoss) {
                ((WitherBoss)entity).setBoss(boss);
            }
            return entity;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static org.bukkit.entity.Entity createCustomEntity(CustomEntity type, Location location) {
        CraftWorld world = (CraftWorld)location.getWorld();
        try {
            Entity entity = (Entity)type.getCustomClass().getDeclaredConstructor(World.class).newInstance(new Object[]{world.getHandle()});
            entity.setPosition(location.getX(), location.getY(), location.getZ());
            world.getHandle().addEntity(entity, CreatureSpawnEvent.SpawnReason.CUSTOM);
            return entity.getBukkitEntity();
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static PathfinderGoalSelector getSelector(EntityInsentient ent, String select) {
        Object goalSelector = ReflectionUtils.getObject(EntityInsentient.class, (Object)ent, select);
        return (PathfinderGoalSelector)goalSelector;
    }

    public static void addSelector(EntityInsentient insent, int index, PathfinderGoal goal) {
        Object goalSelector = ReflectionUtils.getObject(EntityInsentient.class, (Object)insent, "goalSelector");
        if (goalSelector != null) {
            com.cosmicpvp.cosmicutils.utils.ReflectionUtils.callReflectionMethod((Object)goalSelector, (String)"a", (ParamBuilder)new ParamBuilder(Integer.TYPE, (Object)index).add(PathfinderGoal.class, (Object)goal));
        }
    }

    public static void addTargetSelector(EntityInsentient insent, int index, PathfinderGoal goal) {
        Object goalSelector = ReflectionUtils.getObject(EntityInsentient.class, (Object)insent, "targetSelector");
        if (goalSelector != null) {
            com.cosmicpvp.cosmicutils.utils.ReflectionUtils.callReflectionMethod((Object)goalSelector, (String)"a", (ParamBuilder)new ParamBuilder(Integer.TYPE, (Object)index).add(PathfinderGoal.class, (Object)goal));
        }
    }

    public static void clearGoalSelectors(EntityInsentient ent) {
        Object goalSelector = ReflectionUtils.getObject(EntityInsentient.class, (Object)ent, "goalSelector");
        List c = (List)ReflectionUtils.getObject(PathfinderGoalSelector.class, goalSelector, "c");
        c.clear();
        List b = (List)ReflectionUtils.getObject(PathfinderGoalSelector.class, goalSelector, "b");
        b.clear();
        Object targetSelector = ReflectionUtils.getObject(EntityInsentient.class, (Object)ent, "targetSelector");
        List targC = (List)ReflectionUtils.getObject(PathfinderGoalSelector.class, targetSelector, "c");
        targC.clear();
        List targB = (List)ReflectionUtils.getObject(PathfinderGoalSelector.class, targetSelector, "b");
        targB.clear();
    }

    public static void dropWhitelistedItem(Player p, ItemStack is) {
        Item item = p.getWorld().dropItem(p.getLocation(), is);
        item.setMetadata("whitelisted_item", (MetadataValue)new FixedMetadataValue((Plugin)CosmicBosses.getInstance(), (Object)p.getName()));
        item.setMetadata("do_not_clear", (MetadataValue)new FixedMetadataValue((Plugin)CosmicBosses.getInstance(), (Object)""));
    }

    public static Location getRandomLocationNearby(Location l, int maxDist, int yBoost) {
        return BossUtils.getRandomLocationNearby(l, maxDist, 2, yBoost);
    }

    public static Location getRandomRegionLocation(String regionName) {
        RegionManager regionMan = WorldGuardUtils.getWorldGuard().getGlobalRegionManager().get(Bukkit.getWorld((String)"world"));
        if (regionMan == null) {
            return null;
        }
        ProtectedRegion region = regionMan.getRegion(regionName);
        if (region == null) {
            return null;
        }
        Random r = new Random();
        int minX = region.getMinimumPoint().getBlockX();
        int maxX = region.getMaximumPoint().getBlockX();
        int minZ = region.getMinimumPoint().getBlockZ();
        int maxZ = region.getMaximumPoint().getBlockZ();
        int x = minX + r.nextInt(maxX + 1 - minX);
        int z = minZ + r.nextInt(maxZ + 1 - minZ);
        int y = Bukkit.getWorld((String)"world").getHighestBlockYAt(x, z);
        return new Location(Bukkit.getWorld((String)"world"), (double)x, (double)y, (double)z);
    }

    public static Location getRandomLocationNearby(Location l, int maxDist, int minDistance, int yBoost) {
        Location retrLoc = l.clone();
        retrLoc.add((double)((rand.nextBoolean() ? 1 : -1) * (rand.nextInt(maxDist - minDistance) + minDistance)), 0.0, (double)((rand.nextBoolean() ? 1 : -1) * (rand.nextInt(maxDist - minDistance) + minDistance)));
        retrLoc.add(0.5, (double)yBoost, 0.5);
        return retrLoc;
    }

    public static Location getRandomLocationNearby(Location l, int maxDist, int minDistance, int yBoost, boolean highestY) {
        Location retrLoc = l.clone();
        retrLoc.add((double)((rand.nextBoolean() ? 1 : -1) * (rand.nextInt(maxDist - minDistance) + minDistance)), 0.0, (double)((rand.nextBoolean() ? 1 : -1) * (rand.nextInt(maxDist - minDistance) + minDistance)));
        retrLoc.add(0.5, (double)yBoost, 0.5);
        if (highestY && retrLoc.getBlock().getType() == Material.AIR) {
            while (retrLoc.getBlock().getType() == Material.AIR && retrLoc.getY() > 0.0) {
                retrLoc.setY(retrLoc.getY() - 1.0);
            }
        }
        return retrLoc;
    }

    public static void pushAwayPlayer(org.bukkit.entity.Entity entity, Player p, double speed) {
        Vector unitVector = p.getLocation().toVector().subtract(entity.getLocation().toVector());
        if (unitVector.length() != 0.0) {
            unitVector.normalize();
        }
        p.setVelocity(unitVector.multiply(speed));
    }

    public static void pushAwayPlayer(Player p, Location toPushFrom, double speed) {
        Vector unitVector = p.getLocation().toVector().subtract(toPushFrom.toVector());
        if (unitVector.length() != 0.0) {
            unitVector.normalize();
        }
        p.setVelocity(unitVector.multiply(speed));
    }

    public static List<Boss> getAllBossesFromType(Class<? extends Boss> clazz) {
        ArrayList<Boss> bosses = new ArrayList<Boss>();
        for (Boss boss : Boss.bosses.values()) {
            if (boss == null || !boss.getClass().isAssignableFrom(clazz)) continue;
            bosses.add(boss);
        }
        return bosses;
    }

    public static Boss getBossFromMinion(org.bukkit.entity.Entity e) {
        Boss boss2;
        UUID uuid;
        if (e.getMetadata("minion").size() > 0 && (uuid = (UUID)((MetadataValue)e.getMetadata("minion").get(0)).value()) != null && (boss2 = Boss.bosses.get(uuid)) != null) {
            return boss2;
        }
        for (Boss boss2 : Boss.bosses.values()) {
            if (!boss2.hasMinions() || !boss2.getMinions().contains((Object)e)) continue;
            return boss2;
        }
        return null;
    }

    public static boolean dealPercentMaxHealthDamage(Player p, double percentage, int maxDamage, boolean check) {
        return BossUtils.dealPercentMaxHealthDamage(p, percentage, maxDamage, check, false);
    }

    public static boolean dealPercentMaxHealthDamage(Player p, double percentage, int maxDamage, boolean check, boolean checkEnd) {
        if (p.isDead() || p.getHealth() <= 0.0 || check && !BossUtils.canAffectPlayer(p, true, checkEnd) || p.getGameMode() == GameMode.CREATIVE) {
            return false;
        }
        double damage = p.getMaxHealth() * (percentage * 0.01);
        if (damage > (double)maxDamage) {
            damage = maxDamage;
        }
        if (p.getHealth() - damage <= 0.0) {
            p.setHealth(0.0);
        } else {
            p.setHealth(p.getHealth() - damage);
        }
        return true;
    }

    public static boolean dealPercentMaxHealthDamage(Player p, double percentage, int maxDamage) {
        return BossUtils.dealPercentMaxHealthDamage(p, percentage, maxDamage, true);
    }

    public static boolean dealPercentHealthDamage(Player p, double percentage, int maxDamage) {
        if (p.isDead() || !BossUtils.canAffectPlayer(p, true)) {
            return false;
        }
        double damage = p.getHealth() * (percentage * 0.01);
        if (damage > (double)maxDamage) {
            damage = maxDamage;
        }
        if (p.getHealth() - damage <= 0.0) {
            p.setHealth(0.0);
        } else {
            p.setHealth(p.getHealth() - damage);
        }
        return true;
    }

    public static boolean isRebooting() {
        return Bukkit.getPluginManager().isPluginEnabled("Arkkit") && CommandArkReboot.restart_task_exists || Bukkit.getPluginManager().isPluginEnabled("NetworkManager") && NetworkManager.plugin.rebooting;
    }

    public static void dealPercentHealthDamage(Player p, double percentage, int maxDamage, int minDamage) {
        double damage = p.getHealth() * (percentage * 0.01);
        if (!BossUtils.canAffectPlayer(p, true)) {
            return;
        }
        if (damage > (double)maxDamage) {
            damage = maxDamage;
        }
        if (damage < (double)minDamage) {
            damage = minDamage;
        }
        if (p.getHealth() - damage <= 0.0) {
            p.setHealth(0.0);
        } else {
            p.setHealth(p.getHealth() - damage);
        }
    }

    static {
        factions = true;
        warzone = new HashMap();
        rand = new Random();
    }
}

