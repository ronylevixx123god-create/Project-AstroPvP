/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.struct.managers.CosmicManager
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.cosmicpvp.cosmicutils.utils.JSONUtils
 *  com.cosmicpvp.cosmicutils.utils.NumberUtils
 *  com.google.common.collect.MapMaker
 *  com.massivecraft.factions.Faction
 *  net.minecraft.util.com.google.gson.reflect.TypeToken
 *  org.bukkit.Bukkit
 *  org.bukkit.entity.Player
 *  org.bukkit.plugin.Plugin
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package com.cosmicpvp.factionpoints.managers;

import com.cosmicpvp.cosmicutils.struct.managers.CosmicManager;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.JSONUtils;
import com.cosmicpvp.cosmicutils.utils.NumberUtils;
import com.cosmicpvp.factionpoints.CosmicFactionPoints;
import com.cosmicpvp.factionpoints.FactionsCoreChunkAPI;
import com.cosmicpvp.factionpoints.menus.PointPurchaseMenu;
import com.cosmicpvp.factionpoints.struct.CoreChunkData;
import com.cosmicpvp.factionpoints.struct.PointChangeLogs;
import com.cosmicpvp.factionpoints.struct.PointData;
import com.google.common.collect.MapMaker;
import com.massivecraft.factions.Faction;
import java.io.File;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.jvm.internal.TypeIntrinsics;
import net.minecraft.util.com.google.gson.reflect.TypeToken;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 1, 11}, bv={1, 0, 2}, k=1, d1={"\u0000n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\n\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0010\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0003J\u0006\u0010.\u001a\u00020/J\u001a\u00100\u001a\u0004\u0018\u00010\u001c2\u0006\u00101\u001a\u0002022\b\b\u0002\u00103\u001a\u00020$J\u0017\u00104\u001a\u0004\u0018\u00010\u00122\b\u00101\u001a\u0004\u0018\u000102\u00a2\u0006\u0002\u00105J(\u00106\u001a\u00020/2\u0006\u00101\u001a\u0002022\b\u00107\u001a\u0004\u0018\u0001082\u0006\u00109\u001a\u00020\u00112\u0006\u0010:\u001a\u00020;J\u001f\u0010<\u001a\u0004\u0018\u00010\u00122\b\u00101\u001a\u0004\u0018\u0001022\u0006\u0010=\u001a\u00020\u0005\u00a2\u0006\u0002\u0010>J\u001f\u0010<\u001a\u0004\u0018\u00010\u00122\b\u00101\u001a\u0004\u0018\u0001022\u0006\u0010=\u001a\u00020\u0012\u00a2\u0006\u0002\u0010?J)\u0010<\u001a\u0004\u0018\u00010\u00122\b\u00101\u001a\u0004\u0018\u0001022\u0006\u0010=\u001a\u00020\u00122\b\b\u0002\u0010@\u001a\u00020$\u00a2\u0006\u0002\u0010AJ\u0010\u0010B\u001a\u00020/2\u0006\u0010C\u001a\u00020\u0002H\u0016J\u0010\u0010D\u001a\u00020/2\u0006\u0010C\u001a\u00020\u0002H\u0016J\u0010\u0010E\u001a\u0004\u0018\u00010\u001c2\u0006\u00101\u001a\u00020\u0011J\u000e\u0010F\u001a\u00020/2\u0006\u0010G\u001a\u00020$J\u0016\u0010H\u001a\u00020/2\u0006\u00101\u001a\u0002022\u0006\u0010I\u001a\u00020\u0005J\u0016\u0010H\u001a\u00020/2\u0006\u00101\u001a\u0002022\u0006\u0010I\u001a\u00020\u0012J\u0006\u0010J\u001a\u00020$R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0016\u0010\f\u001a\n \u000e*\u0004\u0018\u00010\r0\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R&\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u00120\u0010X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R&\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u00180\u0010X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u0014\"\u0004\b\u001a\u0010\u0016R\u001e\u0010\u001b\u001a\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u001c0\u00108\u0002@\u0002X\u0083\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u001d\u001a\u0004\u0018\u00010\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u001e\u001a\u00020\u0012X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010 \"\u0004\b!\u0010\"R\u000e\u0010#\u001a\u00020$X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010%\u001a\u00020$X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b&\u0010'\"\u0004\b(\u0010)R\u0016\u0010*\u001a\n \u000e*\u0004\u0018\u00010\r0\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010+\u001a\u00020\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b,\u0010\u0007\"\u0004\b-\u0010\t\u00a8\u0006K"}, d2={"Lcom/cosmicpvp/factionpoints/managers/PointManager;", "Lcom/cosmicpvp/cosmicutils/struct/managers/CosmicManager;", "Lcom/cosmicpvp/factionpoints/CosmicFactionPoints;", "()V", "currentWeeklyPercent", "", "getCurrentWeeklyPercent", "()D", "setCurrentWeeklyPercent", "(D)V", "factionLogFile", "Ljava/io/File;", "factionLogToken", "Ljava/lang/reflect/Type;", "kotlin.jvm.PlatformType", "factionNotifiedMap", "", "", "", "getFactionNotifiedMap", "()Ljava/util/Map;", "setFactionNotifiedMap", "(Ljava/util/Map;)V", "factionPointLogs", "Lcom/cosmicpvp/factionpoints/struct/PointChangeLogs;", "getFactionPointLogs", "setFactionPointLogs", "factionPoints", "Lcom/cosmicpvp/factionpoints/struct/PointData;", "factionPointsFile", "lastSetExpiration", "getLastSetExpiration", "()J", "setLastSetExpiration", "(J)V", "loadedData", "", "pointsBuyable", "getPointsBuyable", "()Z", "setPointsBuyable", "(Z)V", "token", "weeklyIncreasePercent", "getWeeklyIncreasePercent", "setWeeklyIncreasePercent", "doWeeklyReset", "", "getFactionPointData", "faction", "Lcom/massivecraft/factions/Faction;", "createIfNull", "getFactionPoints", "(Lcom/massivecraft/factions/Faction;)Ljava/lang/Long;", "logPointChange", "involved", "Lorg/bukkit/entity/Player;", "reason", "points", "", "modifyFactionPoints", "modification", "(Lcom/massivecraft/factions/Faction;D)Ljava/lang/Long;", "(Lcom/massivecraft/factions/Faction;J)Ljava/lang/Long;", "overrideAddition", "(Lcom/massivecraft/factions/Faction;JZ)Ljava/lang/Long;", "onDisable", "cosmicFactionPoints", "onEnable", "removeFactionPoints", "saveExpirationData", "changeIncrease", "setFactionPoints", "newPoints", "togglePointsBuyable", "cosmicfactionpoints"})
public final class PointManager
extends CosmicManager<CosmicFactionPoints> {
    private Map<String, PointData> factionPoints;
    @NotNull
    private Map<String, PointChangeLogs> factionPointLogs;
    @NotNull
    private Map<String, Long> factionNotifiedMap;
    private boolean loadedData;
    private File factionPointsFile;
    private File factionLogFile;
    private final Type token;
    private final Type factionLogToken;
    private boolean pointsBuyable;
    private double currentWeeklyPercent;
    private double weeklyIncreasePercent;
    private long lastSetExpiration;

    @NotNull
    public final Map<String, PointChangeLogs> getFactionPointLogs() {
        return this.factionPointLogs;
    }

    public final void setFactionPointLogs(@NotNull Map<String, PointChangeLogs> map) {
        Intrinsics.checkParameterIsNotNull(map, "<set-?>");
        this.factionPointLogs = map;
    }

    @NotNull
    public final Map<String, Long> getFactionNotifiedMap() {
        return this.factionNotifiedMap;
    }

    public final void setFactionNotifiedMap(@NotNull Map<String, Long> map) {
        Intrinsics.checkParameterIsNotNull(map, "<set-?>");
        this.factionNotifiedMap = map;
    }

    public final boolean getPointsBuyable() {
        return this.pointsBuyable;
    }

    public final void setPointsBuyable(boolean bl) {
        this.pointsBuyable = bl;
    }

    public final double getCurrentWeeklyPercent() {
        return this.currentWeeklyPercent;
    }

    public final void setCurrentWeeklyPercent(double d) {
        this.currentWeeklyPercent = d;
    }

    public final double getWeeklyIncreasePercent() {
        return this.weeklyIncreasePercent;
    }

    public final void setWeeklyIncreasePercent(double d) {
        this.weeklyIncreasePercent = d;
    }

    public final long getLastSetExpiration() {
        return this.lastSetExpiration;
    }

    public final void setLastSetExpiration(long l) {
        this.lastSetExpiration = l;
    }

    public final void logPointChange(@NotNull Faction faction, @Nullable Player involved, @NotNull String reason, int points) {
        Intrinsics.checkParameterIsNotNull((Object)faction, "faction");
        Intrinsics.checkParameterIsNotNull(reason, "reason");
        this.factionPointLogs.computeIfAbsent(faction.getId(), logPointChange.1.INSTANCE).logChange(faction, involved, reason, points);
    }

    public final boolean togglePointsBuyable() {
        this.pointsBuyable = !this.pointsBuyable;
        CosmicFactionPoints cosmicFactionPoints = CosmicFactionPoints.get();
        Intrinsics.checkExpressionValueIsNotNull((Object)cosmicFactionPoints, "CosmicFactionPoints.get()");
        cosmicFactionPoints.getConfig().set("pointsBuyable", (Object)this.pointsBuyable);
        CosmicFactionPoints.get().saveConfig();
        return this.pointsBuyable;
    }

    @Nullable
    public final Long getFactionPoints(@Nullable Faction faction) {
        Long l;
        if (faction == null || !faction.isNormal()) {
            l = null;
        } else {
            PointData pointData2 = this.factionPoints.get(faction.getId());
            l = pointData2 != null ? Long.valueOf(pointData2.points) : null;
        }
        return l;
    }

    @Nullable
    public final PointData getFactionPointData(@NotNull Faction faction, boolean createIfNull) {
        Intrinsics.checkParameterIsNotNull((Object)faction, "faction");
        PointData found = this.factionPoints.get(faction.getId());
        if (found == null && createIfNull) {
            found = new PointData(0L);
            Map<String, PointData> map = this.factionPoints;
            String string = faction.getId();
            Intrinsics.checkExpressionValueIsNotNull(string, "faction.id");
            String string2 = string;
            PointData pointData2 = found;
            map.put(string2, pointData2);
        }
        return found;
    }

    @Nullable
    public static /* bridge */ /* synthetic */ PointData getFactionPointData$default(PointManager pointManager, Faction faction, boolean bl, int n, Object object) {
        if ((n & 2) != 0) {
            bl = false;
        }
        return pointManager.getFactionPointData(faction, bl);
    }

    @Nullable
    public final Long modifyFactionPoints(@Nullable Faction faction, double modification) {
        return this.modifyFactionPoints(faction, (long)modification);
    }

    @Nullable
    public final Long modifyFactionPoints(@Nullable Faction faction, long modification) {
        return this.modifyFactionPoints(faction, modification, false);
    }

    @Nullable
    public final Long modifyFactionPoints(@Nullable Faction faction, long modification, boolean overrideAddition) {
        boolean wasPositive;
        CoreChunkData data2;
        boolean addition;
        if (faction == null) {
            return null;
        }
        boolean bl = addition = modification >= 0L;
        if (!(!addition || overrideAddition || (data2 = FactionsCoreChunkAPI.getChunkData(faction)) != null && data2.canEarnFactionPoints())) {
            if (!this.factionNotifiedMap.containsKey(faction.getId())) {
                String string = faction.getId();
                Intrinsics.checkExpressionValueIsNotNull(string, "faction.id");
                this.factionNotifiedMap.put(string, System.currentTimeMillis());
                faction.sendMessage(CC.RedB + "(!) " + CC.Red + "Your faction cannot earn Faction Points unless you have a Core Chunk claimed for more then 24 hours!");
            }
            Bukkit.getLogger().info("Denying point modification of " + modification + " for " + faction.getTag() + " due to canEarnFactionPoints == false!");
            return null;
        }
        String id = faction.getId();
        PointData currentPoints2 = this.factionPoints.computeIfAbsent(id, modifyFactionPoints.currentPoints.1.INSTANCE);
        long oldPoints = currentPoints2.points;
        long newPoints = currentPoints2.points;
        boolean bl2 = wasPositive = newPoints >= 0L;
        newPoints = addition ? (newPoints += modification) : (newPoints -= Math.abs(modification));
        if (wasPositive && newPoints < 0L && addition) {
            StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
            String string = "[CosmicFactionPoints] Seems there was an overflow when adding points to " + id + " (" + faction.getTag() + ") previous=%s, newValue=%s, added=%s retaining previous..";
            Object[] arrobject = new Object[]{currentPoints2, newPoints, modification};
            Logger logger = Bukkit.getLogger();
            String string2 = String.format(string, Arrays.copyOf(arrobject, arrobject.length));
            Intrinsics.checkExpressionValueIsNotNull(string2, "java.lang.String.format(format, *args)");
            String string3 = string2;
            logger.info(string3);
            newPoints = currentPoints2.points;
        }
        if (newPoints < 0L) {
            newPoints = 0L;
        }
        currentPoints2.points = newPoints;
        CosmicFactionPoints.log("Modifying old points=" + oldPoints + " by " + modification + " for " + faction.getTag() + " (" + id + ") newPoints=" + newPoints);
        return newPoints;
    }

    @Nullable
    public static /* bridge */ /* synthetic */ Long modifyFactionPoints$default(PointManager pointManager, Faction faction, long l, boolean bl, int n, Object object) {
        if ((n & 4) != 0) {
            bl = false;
        }
        return pointManager.modifyFactionPoints(faction, l, bl);
    }

    public final void setFactionPoints(@NotNull Faction faction, long newPoints) {
        Intrinsics.checkParameterIsNotNull((Object)faction, "faction");
        PointData pointData2 = this.factionPoints.computeIfAbsent(faction.getId(), setFactionPoints.pointData.1.INSTANCE);
        pointData2.points = newPoints;
    }

    public final void setFactionPoints(@NotNull Faction faction, double newPoints) {
        Intrinsics.checkParameterIsNotNull((Object)faction, "faction");
        this.setFactionPoints(faction, (long)newPoints);
    }

    @Nullable
    public final PointData removeFactionPoints(@NotNull String faction) {
        Intrinsics.checkParameterIsNotNull(faction, "faction");
        this.factionPointLogs.remove(faction);
        return this.factionPoints.remove(faction);
    }

    public void onEnable(@NotNull CosmicFactionPoints cosmicFactionPoints) {
        Intrinsics.checkParameterIsNotNull((Object)cosmicFactionPoints, "cosmicFactionPoints");
        this.factionPointsFile = JSONUtils.getOrCreateFile((File)cosmicFactionPoints.getDataFolder(), (String)"factionPointsData.json");
        this.factionLogFile = JSONUtils.getOrCreateFile((File)cosmicFactionPoints.getDataFolder(), (String)"pointLogs.json");
        File file = this.factionPointsFile;
        if (file == null) {
            Intrinsics.throwNpe();
        }
        Object object = JSONUtils.fromJson((File)file, (Object)this.token, new HashMap());
        if (object == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.MutableMap<kotlin.String, com.cosmicpvp.factionpoints.struct.PointData>");
        }
        this.factionPoints = TypeIntrinsics.asMutableMap(object);
        Object object2 = JSONUtils.fromJson((File)this.factionLogFile, (Object)this.factionLogToken, new HashMap());
        if (object2 == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.MutableMap<kotlin.String, com.cosmicpvp.factionpoints.struct.PointChangeLogs>");
        }
        this.factionPointLogs = TypeIntrinsics.asMutableMap(object2);
        this.pointsBuyable = cosmicFactionPoints.getConfig().getBoolean("pointsBuyable");
        PointPurchaseMenu.POINTS_PER_DAY = cosmicFactionPoints.getConfig().getInt("points-purchasable-per-day", PointPurchaseMenu.POINTS_PER_DAY);
        PointPurchaseMenu.COST_PER_POINT = cosmicFactionPoints.getConfig().getInt("cost-per-point", 1000000);
        this.currentWeeklyPercent = cosmicFactionPoints.getConfig().getDouble("current-week-increase", 100.0);
        this.weeklyIncreasePercent = cosmicFactionPoints.getConfig().getDouble("weekly-increase", 50.0);
        Bukkit.getLogger().info("[CosmicFactionPoints] Loaded " + this.factionPoints.size() + " factions point data, weeklyPercent: " + this.currentWeeklyPercent + " Increase: " + this.weeklyIncreasePercent);
        this.loadedData = true;
        this.lastSetExpiration = cosmicFactionPoints.getConfig().getLong("last-week-reset");
        if (this.lastSetExpiration == 0L) {
            this.lastSetExpiration = PointPurchaseMenu.getNextResetTime() - TimeUnit.HOURS.toMillis(24L);
            Bukkit.getLogger().info("Setting lastReset to current next reset since unable to find a previous reset time! New: " + this.lastSetExpiration);
        }
        Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin)cosmicFactionPoints, new Runnable(this){
            final /* synthetic */ PointManager this$0;

            public final void run() {
                long timePassed = System.currentTimeMillis() - this.this$0.getLastSetExpiration();
                if (timePassed >= TimeUnit.DAYS.toMillis(7L)) {
                    this.this$0.doWeeklyReset();
                }
            }
            {
                this.this$0 = pointManager;
            }
        }, 60L, 60L);
        Map<String, PointChangeLogs> map = this.factionPointLogs;
        for (Map.Entry<String, PointChangeLogs> entry : map.entrySet()) {
            if (entry.getValue() == null) continue;
            entry.getValue().cleanupOldLogs();
        }
    }

    public final void doWeeklyReset() {
        this.lastSetExpiration = System.currentTimeMillis();
        double newIncrease = 1.0 + 0.01 * this.weeklyIncreasePercent;
        double oldPercent = this.currentWeeklyPercent;
        int oldLimit = PointPurchaseMenu.POINTS_PER_DAY;
        this.currentWeeklyPercent = oldPercent * newIncrease;
        PointPurchaseMenu.POINTS_PER_DAY = (int)((double)oldLimit * newIncrease);
        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
        String string = "Incrementing currentWeeklyPercent from %s -> %s by %s lastExpiration=%s, oldPointsPerDay =%s, newPointsPerDay=%s";
        Object[] arrobject = new Object[]{String.valueOf(oldPercent), String.valueOf(this.currentWeeklyPercent), String.valueOf(newIncrease), String.valueOf(this.lastSetExpiration), String.valueOf(oldLimit), String.valueOf(PointPurchaseMenu.POINTS_PER_DAY)};
        Logger logger = Bukkit.getLogger();
        String string2 = String.format(string, Arrays.copyOf(arrobject, arrobject.length));
        Intrinsics.checkExpressionValueIsNotNull(string2, "java.lang.String.format(format, *args)");
        String string3 = string2;
        logger.info(string3);
        Bukkit.broadcastMessage((String)(CC.AquaB + "*** New Faction Point Multiplier: " + CC.AquaU + NumberUtils.formatMoney((double)this.currentWeeklyPercent) + "%" + CC.AquaB + " ***"));
        this.saveExpirationData(false);
    }

    public final void saveExpirationData(boolean changeIncrease) {
        CosmicFactionPoints cosmicFactionPoints = CosmicFactionPoints.get();
        Intrinsics.checkExpressionValueIsNotNull((Object)cosmicFactionPoints, "CosmicFactionPoints.get()");
        cosmicFactionPoints.getConfig().set("last-week-reset", (Object)this.lastSetExpiration);
        CosmicFactionPoints cosmicFactionPoints2 = CosmicFactionPoints.get();
        Intrinsics.checkExpressionValueIsNotNull((Object)cosmicFactionPoints2, "CosmicFactionPoints.get()");
        cosmicFactionPoints2.getConfig().set("current-week-increase", (Object)this.currentWeeklyPercent);
        CosmicFactionPoints cosmicFactionPoints3 = CosmicFactionPoints.get();
        Intrinsics.checkExpressionValueIsNotNull((Object)cosmicFactionPoints3, "CosmicFactionPoints.get()");
        cosmicFactionPoints3.getConfig().set("points-purchasable-per-day", (Object)PointPurchaseMenu.POINTS_PER_DAY);
        if (changeIncrease) {
            CosmicFactionPoints cosmicFactionPoints4 = CosmicFactionPoints.get();
            Intrinsics.checkExpressionValueIsNotNull((Object)cosmicFactionPoints4, "CosmicFactionPoints.get()");
            cosmicFactionPoints4.getConfig().set("weekly-increase", (Object)this.weeklyIncreasePercent);
        }
        CosmicFactionPoints.get().saveConfig();
    }

    public void onDisable(@NotNull CosmicFactionPoints cosmicFactionPoints) {
        Intrinsics.checkParameterIsNotNull((Object)cosmicFactionPoints, "cosmicFactionPoints");
        if (this.loadedData) {
            File file = this.factionPointsFile;
            if (file == null) {
                Intrinsics.throwNpe();
            }
            JSONUtils.saveJSONToFile((File)file, this.factionPoints, (Object)this.token);
            File file2 = this.factionLogFile;
            if (file2 == null) {
                Intrinsics.throwNpe();
            }
            JSONUtils.saveJSONToFile((File)file2, this.factionPointLogs, (Object)this.factionLogToken);
        } else {
            Bukkit.getLogger().info("[CosmicFactionPoints] Not saving faction points due to loadedData == false!");
        }
    }

    public PointManager() {
        ConcurrentMap concurrentMap = new MapMaker().concurrencyLevel(16).makeMap();
        Intrinsics.checkExpressionValueIsNotNull(concurrentMap, "MapMaker().concurrencyLevel(16).makeMap()");
        this.factionPoints = concurrentMap;
        ConcurrentMap concurrentMap2 = new MapMaker().concurrencyLevel(16).makeMap();
        Intrinsics.checkExpressionValueIsNotNull(concurrentMap2, "MapMaker().concurrencyLevel(16).makeMap()");
        this.factionPointLogs = concurrentMap2;
        ConcurrentMap concurrentMap3 = new MapMaker().concurrencyLevel(16).expireAfterWrite(60L, TimeUnit.MINUTES).makeMap();
        Intrinsics.checkExpressionValueIsNotNull(concurrentMap3, "MapMaker().concurrencyLe\u2026meUnit.MINUTES).makeMap()");
        this.factionNotifiedMap = concurrentMap3;
        this.token = new TypeToken<Map<String, ? extends PointData>>(){}.getType();
        this.factionLogToken = new TypeToken<Map<String, ? extends PointChangeLogs>>(){}.getType();
        this.pointsBuyable = true;
        this.weeklyIncreasePercent = 10.0;
    }
}

