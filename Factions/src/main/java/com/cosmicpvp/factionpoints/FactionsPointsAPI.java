/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.nbt.NBTTagCompoundBuilder
 *  com.cosmicpvp.cosmicutils.struct.DecimalFormatType
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.cosmicpvp.cosmicutils.utils.FactionUtils
 *  com.cosmicpvp.cosmicutils.utils.HCFOutpostUtils
 *  com.cosmicpvp.cosmicutils.utils.ItemBuilder
 *  com.cosmicpvp.cosmicutils.utils.NBTWrapper
 *  com.cosmicpvp.cosmicutils.utils.NumberUtils
 *  com.cosmicpvp.cosmicutils.utils.TimeUtils
 *  com.massivecraft.factions.FPlayer
 *  com.massivecraft.factions.Faction
 *  com.massivecraft.factions.P
 *  com.massivecraft.factions.struct.FLogType
 *  net.minecraft.server.v1_7_R4.NBTBase
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 */
package com.cosmicpvp.factionpoints;

import com.cosmicpvp.cosmicutils.nbt.NBTTagCompoundBuilder;
import com.cosmicpvp.cosmicutils.struct.DecimalFormatType;
import com.cosmicpvp.cosmicutils.utils.*;
import com.cosmicpvp.factionpoints.struct.PointData;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.P;
import com.massivecraft.factions.struct.FLogType;
import net.minecraft.server.v1_8_R3.NBTBase;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.text.SimpleDateFormat;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class FactionsPointsAPI {
    private static SimpleDateFormat format = new SimpleDateFormat("MMM dd hh:mmaa");

    public static ItemStack createPointItem(double amount, boolean applyIID) {
        return FactionsPointsAPI.createPointItem(amount, applyIID, null);
    }

    public static ItemStack createPointItem(double amount, boolean applyIID, Long manualExpiration) {
        long currentTime = System.currentTimeMillis();
        NBTTagCompoundBuilder cosmicData = new NBTTagCompoundBuilder().setDouble("points", amount).setLong("created", System.currentTimeMillis());
        if (manualExpiration != null && manualExpiration > 0L) {
            cosmicData.setLong("expiration", manualExpiration);
        }
        NBTWrapper wrapper = new NBTWrapper(new ItemBuilder(Material.PAPER, CC.GoldB + "Faction Points Note" + CC.Gray + " (Right Click)", CC.Gold + "Value " + CC.White + NumberUtils.format((double)amount, (DecimalFormatType)DecimalFormatType.MONEY).replace(".00", ""), "", CC.Gray + "Click to apply faction points", CC.Gray + "to your factions /f top value!", "", CC.RedB + "Valid Until " + CC.RedU + TimeUtils.formatTimeToDate((long)(manualExpiration != null ? manualExpiration : currentTime + TimeUnit.HOURS.toMillis(24L)), (String)"MMM d'%s' hh:mmaa", (String)"CST").replace(" 0", " ") + " CST").build()).setString("cosmicType", "fPointNote").setNBTBase("cosmicData", (NBTBase)cosmicData.get());
        if (applyIID) {
            wrapper.assignIID();
        }
        return wrapper.build();
    }

    public static Long modifyPoints(Player player, double modification) {
        FPlayer fplayer = FactionUtils.getFPlayer((Player)player);
        if (fplayer == null) {
            return null;
        }
        return FactionsPointsAPI.modifyPoints(fplayer.getFaction(), modification);
    }

    public static Long modifyPoints(Faction faction, double modification, boolean forceSendMessage) {
        if (forceSendMessage && faction != null) {
            CosmicFactionPoints.get().getPointManager().getFactionNotifiedMap().remove(faction.getId());
        }
        return FactionsPointsAPI.modifyPoints(faction, modification);
    }

    public static Long modifyPoints(Faction faction, double modification) {
        if (!FactionUtils.isNormal((Faction)faction)) {
            return null;
        }
        return CosmicFactionPoints.get().getPointManager().modifyFactionPoints(faction, modification);
    }

    public static Long setPoints(Faction faction, double newPoints) {
        if (!FactionUtils.isNormal((Faction)faction)) {
            return null;
        }
        CosmicFactionPoints.get().getPointManager().setFactionPoints(faction, newPoints);
        return (long)newPoints;
    }

    public static boolean removePoints(Faction faction) {
        return CosmicFactionPoints.get().getPointManager().removeFactionPoints(faction.getId()) != null;
    }

    public static boolean removePoints(String id) {
        return CosmicFactionPoints.get().getPointManager().removeFactionPoints(id) != null;
    }

    public static Long getPoints(Faction faction) {
        if (faction == null || !FactionUtils.isNormal((Faction)faction)) {
            return null;
        }
        return CosmicFactionPoints.get().getPointManager().getFactionPoints(faction);
    }

    public static PointData getPointData(Faction faction, boolean create) {
        if (!FactionUtils.isNormal((Faction)faction)) {
            return null;
        }
        return CosmicFactionPoints.get().getPointManager().getFactionPointData(faction, create);
    }

    public static boolean isPointItem(ItemStack item) {
        return FactionsPointsAPI.isPointItem(new NBTWrapper(item));
    }

    public static boolean isPointItem(NBTWrapper wrapper) {
        return wrapper.getString("cosmicType").equals("fPointNote");
    }

    public static Double getPointValue(NBTWrapper wrapper) {
        return new NBTTagCompoundBuilder(wrapper.getNBTBase("cosmicData")).getDouble("points");
    }

    public static Long giveFactionPoints(Faction faction, Player player, String eventName, Object data2) {
        int pointsToGive = FactionsPointsAPI.getPointsToGiveScaled(faction, player, eventName, data2);
        if (pointsToGive <= 0) {
            return 0L;
        }
        return FactionsPointsAPI.giveFactionPoints(faction, player, eventName, pointsToGive, data2);
    }

    public static Long giveFactionPoints(Faction faction, Player player, String eventName, int points, Object data2) {
        Long retr = null;
        boolean isSpammable;
        if (faction == null || !faction.isNormal()) {
            return 0L;
        }
        boolean override = eventName != null && (eventName.equals("f_upgrade") || eventName.equals("op_command"));
        boolean bl = isSpammable = eventName != null && (eventName.equals("outpost_vanilla") || eventName.equals("end_custom"));
        Long l = override ? CosmicFactionPoints.get().getPointManager().modifyFactionPoints(faction, points, true) : (retr = FactionsPointsAPI.modifyPoints(faction, points, !isSpammable));
        if (retr != null) {
            CosmicFactionPoints.get().getPointManager().logPointChange(faction, player, eventName, points);
            if (eventName != null) {
                try {
                    if (player != null) {
                        P.p.getFlogManager().log(faction, FLogType.F_POINTS, new String[]{CC.GreenB + "+ " + CC.Green + points + " Faction Points (" + retr + ")", player.getName(), "- " + FactionsPointsAPI.pretifyEventName(eventName)});
                    } else {
                        P.p.getFlogManager().log(faction, FLogType.F_POINTS, new String[]{CC.GreenB + "+ " + CC.Green + points + " Faction Points (" + retr + ")", "your faction", "- " + FactionsPointsAPI.pretifyEventName(eventName)});
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return retr;
    }

    private static String pretifyEventName(String name) {
        if (name.equals("boss_death")) {
            return "Boss Defeated";
        }
        if (name.equals("koth")) {
            return "KOTH";
        }
        if (name.equals("f_quest")) {
            return "/f quest";
        }
        if (name.equals("f_upgrade")) {
            return "/f Upgrade";
        }
        if (name.equals("vanilla_outpost") || name.equals("outpost_vanilla")) {
            return "Outpost";
        }
        return name;
    }

    public static ItemStack getFactionPointItem(Faction faction, Player player, String eventName, Object data2) {
        int pointsToGive = FactionsPointsAPI.getPointsToGiveScaled(faction, player, eventName, data2);
        if (pointsToGive <= 0) {
            return null;
        }
        return FactionsPointsAPI.createPointItem(pointsToGive, true);
    }

    public static ItemStack getFactionPointItem(Faction faction, Player player, String eventName, int points, Object data2) {
        return FactionsPointsAPI.createPointItem(points, true);
    }

    private static int getPointsToGiveScaled(Faction faction, Player player, String eventName, Object data2) {
        int retr = FactionsPointsAPI.getPointsToGive(faction, player, eventName, data2);
        if (eventName != null && eventName.equals("boss_death") && data2 instanceof Entity && ((Entity)data2).hasMetadata("playerSpawnedBoss")) {
            return retr;
        }
        long timeSinceStart = System.currentTimeMillis() - CosmicFactionPoints.get().getStartOfMap();
        long seconds = timeSinceStart / 1000L;
        long minutes = seconds / 60L;
        long hours = minutes / 60L;
        long days = hours / 24L;
        long weeks = days / 7L;
        int adjustedRetr = retr;
        if (eventName != null && eventName.equals("koth") && faction != null && HCFOutpostUtils.doesControlOutpost((Faction)faction, (String)"frozen")) {
            adjustedRetr = (int)((double)adjustedRetr + (double)adjustedRetr * 0.2);
        }
        double multiplier = Math.max(1.0, CosmicFactionPoints.get().getPointManager().getCurrentWeeklyPercent() * 0.01);
        CosmicFactionPoints.debug("Getting multiplier: " + multiplier + " weeks: " + weeks + ", retr: " + retr + ", adjusted: " + adjustedRetr);
        return (int)((double)adjustedRetr * multiplier);
    }

    private static int getBossPoints(String bossName, Entity ent) {
        boolean playerSpawned = false;
        if (bossName.equalsIgnoreCase("Brood Mother")) {
            return playerSpawned ? 1 : 2;
        }
        if (bossName.equalsIgnoreCase("King Slime")) {
            return playerSpawned ? 2 : 5;
        }
        if (bossName.equalsIgnoreCase("Undead Assassin")) {
            return playerSpawned ? 3 : 8;
        }
        if (ent != null && ent.hasMetadata("heroicEnvoyBoss")) {
            return 50;
        }
        if (bossName.equalsIgnoreCase("Plague Bloater")) {
            return playerSpawned ? 4 : 10;
        }
        if (bossName.equalsIgnoreCase("Yijki, World Destroyer")) {
            return playerSpawned ? 5 : 15;
        }
        return 0;
    }

    private static int getPointsToGive(Faction faction, Player player, String eventName, Object data2) {
        if (eventName == null) {
            return 0;
        }
        if (eventName.equalsIgnoreCase("treasure_drop")) {
            return 10;
        }
        if (eventName.equalsIgnoreCase("koth")) {
            return CosmicFactionPoints.get().isVanillaPlanet() ? 100 : 250;
        }
        if (eventName.equalsIgnoreCase("stronghold") || eventName.equalsIgnoreCase("hcf_outposts")) {
            return CosmicFactionPoints.get().isVanillaPlanet() ? 10 : 20;
        }
        if (eventName.equalsIgnoreCase("vanilla_envoy")) {
            String rarity;
            if (data2 != null && (rarity = (String)data2).equals("LEGENDARY")) {
                return ThreadLocalRandom.current().nextInt(3) + 2;
            }
            return 0;
        }
        if (eventName.equalsIgnoreCase("conquest")) {
            return 50;
        }
        if (eventName.equalsIgnoreCase("outpost_vanilla")) {
            return 1;
        }
        if (eventName.equalsIgnoreCase("end_custom")) {
            return 1 + ThreadLocalRandom.current().nextInt(2);
        }
        if (eventName.equalsIgnoreCase("boss_death") && data2 != null) {
            Object[] entityInfo = (Object[])data2;
            String givenName = (String)entityInfo[0];
            String bossName = ChatColor.stripColor((String)givenName);
            return FactionsPointsAPI.getBossPoints(bossName, (Entity)entityInfo[1]);
        }
        if (eventName.equalsIgnoreCase("contest_prize")) {
            int ranking = (Integer)data2;
            return ranking == 1 ? 10 : (ranking == 2 ? 5 : (ranking == 3 ? 2 : 0));
        }
        if (eventName.equalsIgnoreCase("space_chest")) {
            return 1 + ThreadLocalRandom.current().nextInt(25);
        }
        if (eventName.equalsIgnoreCase("lootbag")) {
            return 5 + ThreadLocalRandom.current().nextInt(6);
        }
        if (eventName.equalsIgnoreCase("f_quest")) {
            return 50;
        }
        if (eventName.equalsIgnoreCase("contests")) {
            int ranking = (Integer)data2;
            if (ranking == 1) {
                return 10;
            }
            if (ranking == 2) {
                return 5;
            }
            if (ranking == 3) {
                return 2;
            }
        }
        return 0;
    }
}

