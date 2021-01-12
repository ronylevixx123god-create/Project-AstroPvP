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
import com.google.gson.reflect.TypeToken;
import com.massivecraft.factions.Faction;
import java.io.File;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.logging.Logger;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.jvm.internal.TypeIntrinsics;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv = {1, 1, 11}, bv = {1, 0, 2}, k = 1, d1 = {"\000n\n\002\030\002\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\006\n\002\b\005\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\020%\n\002\020\016\n\002\020\t\n\002\b\005\n\002\030\002\n\002\b\003\n\002\030\002\n\002\b\007\n\002\020\013\n\002\b\n\n\002\020\002\n\002\b\002\n\002\030\002\n\002\b\005\n\002\030\002\n\002\b\002\n\002\020\b\n\002\b\020\030\0002\b\022\004\022\0020\0020\001B\005\006\002\020\003J\006\020.\032\0020/J\032\0200\032\004\030\0010\0342\006\0201\032\002022\b\b\002\0203\032\0020$J\027\0204\032\004\030\0010\0222\b\0201\032\004\030\00102\006\002\0205J(\0206\032\0020/2\006\0201\032\002022\b\0207\032\004\030\001082\006\0209\032\0020\0212\006\020:\032\0020;J\037\020<\032\004\030\0010\0222\b\0201\032\004\030\001022\006\020=\032\0020\005\006\002\020>J\037\020<\032\004\030\0010\0222\b\0201\032\004\030\001022\006\020=\032\0020\022\006\002\020?J)\020<\032\004\030\0010\0222\b\0201\032\004\030\001022\006\020=\032\0020\0222\b\b\002\020@\032\0020$\006\002\020AJ\020\020B\032\0020/2\006\020C\032\0020\002H\026J\020\020D\032\0020/2\006\020C\032\0020\002H\026J\020\020E\032\004\030\0010\0342\006\0201\032\0020\021J\016\020F\032\0020/2\006\020G\032\0020$J\026\020H\032\0020/2\006\0201\032\002022\006\020I\032\0020\005J\026\020H\032\0020/2\006\0201\032\002022\006\020I\032\0020\022J\006\020J\032\0020$R\032\020\004\032\0020\005X\016\006\016\n\000\032\004\b\006\020\007\"\004\b\b\020\tR\020\020\n\032\004\030\0010\013X\016\006\002\n\000R\026\020\f\032\n \016*\004\030\0010\r0\rX\004\006\002\n\000R&\020\017\032\016\022\004\022\0020\021\022\004\022\0020\0220\020X\016\006\016\n\000\032\004\b\023\020\024\"\004\b\025\020\026R&\020\027\032\016\022\004\022\0020\021\022\004\022\0020\0300\020X\016\006\016\n\000\032\004\b\031\020\024\"\004\b\032\020\026R\036\020\033\032\016\022\004\022\0020\021\022\004\022\0020\0340\0208\002@\002X\016\006\002\n\000R\020\020\035\032\004\030\0010\013X\016\006\002\n\000R\032\020\036\032\0020\022X\016\006\016\n\000\032\004\b\037\020 \"\004\b!\020\"R\016\020#\032\0020$X\016\006\002\n\000R\032\020%\032\0020$X\016\006\016\n\000\032\004\b&\020'\"\004\b(\020)R\026\020*\032\n \016*\004\030\0010\r0\rX\004\006\002\n\000R\032\020+\032\0020\005X\016\006\016\n\000\032\004\b,\020\007\"\004\b-\020\t\006K"}, d2 = {"Lcom/cosmicpvp/factionpoints/managers/PointManager;", "Lcom/cosmicpvp/cosmicutils/struct/managers/CosmicManager;", "Lcom/cosmicpvp/factionpoints/CosmicFactionPoints;", "()V", "currentWeeklyPercent", "", "getCurrentWeeklyPercent", "()D", "setCurrentWeeklyPercent", "(D)V", "factionLogFile", "Ljava/io/File;", "factionLogToken", "Ljava/lang/reflect/Type;", "kotlin.jvm.PlatformType", "factionNotifiedMap", "", "", "", "getFactionNotifiedMap", "()Ljava/util/Map;", "setFactionNotifiedMap", "(Ljava/util/Map;)V", "factionPointLogs", "Lcom/cosmicpvp/factionpoints/struct/PointChangeLogs;", "getFactionPointLogs", "setFactionPointLogs", "factionPoints", "Lcom/cosmicpvp/factionpoints/struct/PointData;", "factionPointsFile", "lastSetExpiration", "getLastSetExpiration", "()J", "setLastSetExpiration", "(J)V", "loadedData", "", "pointsBuyable", "getPointsBuyable", "()Z", "setPointsBuyable", "(Z)V", "token", "weeklyIncreasePercent", "getWeeklyIncreasePercent", "setWeeklyIncreasePercent", "doWeeklyReset", "", "getFactionPointData", "faction", "Lcom/massivecraft/factions/Faction;", "createIfNull", "getFactionPoints", "(Lcom/massivecraft/factions/Faction;)Ljava/lang/Long;", "logPointChange", "involved", "Lorg/bukkit/entity/Player;", "reason", "points", "", "modifyFactionPoints", "modification", "(Lcom/massivecraft/factions/Faction;D)Ljava/lang/Long;", "(Lcom/massivecraft/factions/Faction;J)Ljava/lang/Long;", "overrideAddition", "(Lcom/massivecraft/factions/Faction;JZ)Ljava/lang/Long;", "onDisable", "cosmicFactionPoints", "onEnable", "removeFactionPoints", "saveExpirationData", "changeIncrease", "setFactionPoints", "newPoints", "togglePointsBuyable", "cosmicfactionpoints"})
public final class PointManager extends CosmicManager<CosmicFactionPoints> {
    private Map factionPoints;

    @NotNull
    private Map factionPointLogs;

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

    @Metadata(mv = {1, 1, 11}, bv = {1, 0, 2}, k = 3, d1 = {"\000\016\n\000\n\002\030\002\n\000\n\002\020\016\n\000\020\000\032\0020\0012\006\020\002\032\0020\003H\n\006\002\b\004"}, d2 = {"<anonymous>", "Lcom/cosmicpvp/factionpoints/struct/PointChangeLogs;", "it", "", "apply"})
    static final class PointManager$logPointChange$1<T, R> implements Function<String, PointChangeLogs> {
        public static final PointManager$logPointChange$1 INSTANCE = new PointManager$logPointChange$1();

        @NotNull
        public final PointChangeLogs apply(@NotNull String it) {
            Intrinsics.checkParameterIsNotNull(it, "it");
            return new PointChangeLogs();
        }
    }

    @Metadata(mv = {1, 1, 11}, bv = {1, 0, 2}, k = 3, d1 = {"\000\016\n\000\n\002\030\002\n\000\n\002\020\016\n\000\020\000\032\0020\0012\006\020\002\032\0020\003H\n\006\002\b\004"}, d2 = {"<anonymous>", "Lcom/cosmicpvp/factionpoints/struct/PointData;", "it", "", "apply"})
    static final class PointManager$modifyFactionPoints$currentPoints$1<T, R> implements Function<String, PointData> {
        public static final PointManager$modifyFactionPoints$currentPoints$1 INSTANCE = new PointManager$modifyFactionPoints$currentPoints$1();

        @NotNull
        public final PointData apply(@NotNull String it) {
            Intrinsics.checkParameterIsNotNull(it, "it");
            return new PointData(0L);
        }
    }

    @Metadata(mv = {1, 1, 11}, bv = {1, 0, 2}, k = 3, d1 = {"\000\016\n\000\n\002\030\002\n\000\n\002\020\016\n\000\020\000\032\0020\0012\006\020\002\032\0020\003H\n\006\002\b\004"}, d2 = {"<anonymous>", "Lcom/cosmicpvp/factionpoints/struct/PointData;", "it", "", "apply"})
    static final class PointManager$setFactionPoints$pointData$1<T, R> implements Function<String, PointData> {
        public static final PointManager$setFactionPoints$pointData$1 INSTANCE = new PointManager$setFactionPoints$pointData$1();

        @NotNull
        public final PointData apply(@NotNull String it) {
            Intrinsics.checkParameterIsNotNull(it, "it");
            return new PointData(0L);
        }
    }

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

    @Metadata(mv = {1, 1, 11}, bv = {1, 0, 2}, k = 1, d1 = {"\000\027\n\000\n\002\030\002\n\002\020$\n\002\020\016\n\002\030\002\n\000*\001\000\b\n\030\0002\024\022\020\022\016\022\004\022\0020\003\022\004\022\0020\0040\0020\001\006\005"}, d2 = {"com/cosmicpvp/factionpoints/managers/PointManager$token$1", "Lnet/minecraft/util/com/google/gson/reflect/TypeToken;", "", "", "Lcom/cosmicpvp/factionpoints/struct/PointData;", "cosmicfactionpoints"})
    public static final class PointManager$token$1 extends TypeToken<Map<String, ? extends PointData>> {}

    @Metadata(mv = {1, 1, 11}, bv = {1, 0, 2}, k = 1, d1 = {"\000\027\n\000\n\002\030\002\n\002\020$\n\002\020\016\n\002\030\002\n\000*\001\000\b\n\030\0002\024\022\020\022\016\022\004\022\0020\003\022\004\022\0020\0040\0020\001\006\005"}, d2 = {"com/cosmicpvp/factionpoints/managers/PointManager$factionLogToken$1", "Lnet/minecraft/util/com/google/gson/reflect/TypeToken;", "", "", "Lcom/cosmicpvp/factionpoints/struct/PointChangeLogs;", "cosmicfactionpoints"})
    public static final class PointManager$factionLogToken$1 extends TypeToken<Map<String, ? extends PointChangeLogs>> {}

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
        Intrinsics.checkParameterIsNotNull(faction, "faction");
        Intrinsics.checkParameterIsNotNull(reason, "reason");
        ((PointChangeLogs)this.factionPointLogs.computeIfAbsent(faction.getId(), PointManager$logPointChange$1.INSTANCE)).logChange(faction, involved, reason, points);
    }

    public final boolean togglePointsBuyable() {
        this.pointsBuyable = !this.pointsBuyable;
        Intrinsics.checkExpressionValueIsNotNull(CosmicFactionPoints.get(), "CosmicFactionPoints.get()");
        CosmicFactionPoints.get().getConfig().set("pointsBuyable", Boolean.valueOf(this.pointsBuyable));
        CosmicFactionPoints.get().saveConfig();
        return this.pointsBuyable;
    }

    @Nullable
    public final Long getFactionPoints(@Nullable Faction faction) {
        Long l;
        if (faction == null || !faction.isNormal()) {
            l = null;
        } else {
            PointData pointData2 = (PointData) this.factionPoints.get(faction.getId());
            l = pointData2 != null ? Long.valueOf(pointData2.points) : null;
        }
        return l;
    }

    @Nullable
    public final PointData getFactionPointData(@NotNull Faction faction, boolean createIfNull) {
        Intrinsics.checkParameterIsNotNull(faction, "faction");
        PointData found = (PointData) this.factionPoints.get(faction.getId());
        if (found == null && createIfNull) {
            found = new PointData(0L);
            Map<String, PointData> map = this.factionPoints;
            Intrinsics.checkExpressionValueIsNotNull(faction.getId(), "faction.id");
            String str = faction.getId();
            PointData pointData = found;
            map.put(str, pointData);
        }
        return found;
    }

    @Nullable
    public final Long modifyFactionPoints(@Nullable Faction faction, double modification) {
        return modifyFactionPoints(faction, (long)modification);
    }

    @Nullable
    public final Long modifyFactionPoints(@Nullable Faction faction, long modification) {
        return modifyFactionPoints(faction, modification, false);
    }

    @Nullable
    public final Long modifyFactionPoints(@Nullable Faction faction, long modification, boolean overrideAddition) {
        if (faction == null)
            return null;
        boolean addition = (modification >= 0L);
        if (addition && !overrideAddition) {
            CoreChunkData data = FactionsCoreChunkAPI.getChunkData(faction);
            if (data == null || !data.canEarnFactionPoints()) {
                if (!this.factionNotifiedMap.containsKey(faction.getId())) {
                    Intrinsics.checkExpressionValueIsNotNull(faction.getId(), "faction.id");
                    this.factionNotifiedMap.put(faction.getId(), Long.valueOf(System.currentTimeMillis()));
                    faction.sendMessage(CC.RedB + "(!) " + CC.Red + "Your faction cannot earn Faction Points unless you have a Core Chunk claimed for more then 24 hours!");
                }
                Bukkit.getLogger().info("Denying point modification of " + modification + " for " + faction.getTag() + " due to canEarnFactionPoints == false!");
                return null;
            }
        }
        String id = faction.getId();
        PointData currentPoints = (PointData) this.factionPoints.computeIfAbsent(id, PointManager$modifyFactionPoints$currentPoints$1.INSTANCE);
        long oldPoints = currentPoints.points;
        long newPoints = currentPoints.points;
        boolean wasPositive = (newPoints >= 0L);
        if (addition) {
            newPoints += modification;
        } else {
            newPoints -= Math.abs(modification);
        }
        if (wasPositive && newPoints < 0L && addition) {
            StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
            String str1 = "[CosmicFactionPoints] Seems there was an overflow when adding points to " + id + " (" + faction.getTag() + ") previous=%s, newValue=%s, added=%s retaining previous..";
            Object[] arrayOfObject = { currentPoints, Long.valueOf(newPoints), Long.valueOf(modification) };
            Logger logger = Bukkit.getLogger();
            Intrinsics.checkExpressionValueIsNotNull(String.format(str1, Arrays.copyOf(arrayOfObject, arrayOfObject.length)), "java.lang.String.format(format, *args)");
            String str2 = String.format(str1, Arrays.copyOf(arrayOfObject, arrayOfObject.length));
            logger.info(str2);
            newPoints = currentPoints.points;
        }
        if (newPoints < 0L)
            newPoints = 0L;
        currentPoints.points = newPoints;
        CosmicFactionPoints.log("Modifying old points=" + oldPoints + " by " + modification + " for " + faction.getTag() + " (" + id + ") newPoints=" + newPoints);
        return Long.valueOf(newPoints);
    }

    public final void setFactionPoints(@NotNull Faction faction, long newPoints) {
        Intrinsics.checkParameterIsNotNull(faction, "faction");
        PointData pointData = (PointData) this.factionPoints.computeIfAbsent(faction.getId(), PointManager$setFactionPoints$pointData$1.INSTANCE);
        pointData.points = newPoints;
    }

    public final void setFactionPoints(@NotNull Faction faction, double newPoints) {
        Intrinsics.checkParameterIsNotNull(faction, "faction");
        setFactionPoints(faction, (long)newPoints);
    }

    @Nullable
    public final PointData removeFactionPoints(@NotNull String faction) {
        Intrinsics.checkParameterIsNotNull(faction, "faction");
        this.factionPointLogs.remove(faction);
        return (PointData) this.factionPoints.remove(faction);
    }

    @SneakyThrows
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
        Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin)cosmicFactionPoints, new Runnable(){
            /* synthetic */ PointManager this$0;

            public final void run() {
                long timePassed = System.currentTimeMillis() - this.this$0.getLastSetExpiration();
                if (timePassed >= TimeUnit.DAYS.toMillis(7L)) {
                    this.this$0.doWeeklyReset();
                }
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
        double newIncrease = 1.0D + 0.01D * this.weeklyIncreasePercent;
        double oldPercent = this.currentWeeklyPercent;
        int oldLimit = PointPurchaseMenu.POINTS_PER_DAY;
        this.currentWeeklyPercent = oldPercent * newIncrease;
        PointPurchaseMenu.POINTS_PER_DAY = (int)(oldLimit * newIncrease);
        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
        String str1 = "Incrementing currentWeeklyPercent from %s -> %s by %s lastExpiration=%s, oldPointsPerDay =%s, newPointsPerDay=%s";
        Object[] arrayOfObject = { String.valueOf(oldPercent), String.valueOf(this.currentWeeklyPercent), String.valueOf(newIncrease), String.valueOf(this.lastSetExpiration), String.valueOf(oldLimit), String.valueOf(PointPurchaseMenu.POINTS_PER_DAY) };
        Logger logger = Bukkit.getLogger();
        Intrinsics.checkExpressionValueIsNotNull(String.format(str1, Arrays.copyOf(arrayOfObject, arrayOfObject.length)), "java.lang.String.format(format, *args)");
        String str2 = String.format(str1, Arrays.copyOf(arrayOfObject, arrayOfObject.length));
        logger.info(str2);
        Bukkit.broadcastMessage(CC.AquaB + "*** New Faction Point Multiplier: " + CC.AquaU + NumberUtils.formatMoney(this.currentWeeklyPercent) + "%" + CC.AquaB + " ***");
        saveExpirationData(false);
    }

    public final void saveExpirationData(boolean changeIncrease) {
        Intrinsics.checkExpressionValueIsNotNull(CosmicFactionPoints.get(), "CosmicFactionPoints.get()");
        CosmicFactionPoints.get().getConfig().set("last-week-reset", this.lastSetExpiration);
        Intrinsics.checkExpressionValueIsNotNull(CosmicFactionPoints.get(), "CosmicFactionPoints.get()");
        CosmicFactionPoints.get().getConfig().set("current-week-increase", this.currentWeeklyPercent);
        Intrinsics.checkExpressionValueIsNotNull(CosmicFactionPoints.get(), "CosmicFactionPoints.get()");
        CosmicFactionPoints.get().getConfig().set("points-purchasable-per-day", PointPurchaseMenu.POINTS_PER_DAY);
        if (changeIncrease) {
            Intrinsics.checkExpressionValueIsNotNull(CosmicFactionPoints.get(), "CosmicFactionPoints.get()");
            CosmicFactionPoints.get().getConfig().set("weekly-increase", this.weeklyIncreasePercent);
        }
        CosmicFactionPoints.get().saveConfig();
    }

    @SneakyThrows
    public void onDisable(@NotNull CosmicFactionPoints cosmicFactionPoints) {
        Intrinsics.checkParameterIsNotNull(cosmicFactionPoints, "cosmicFactionPoints");
        if (this.loadedData) {
            if (this.factionPointsFile == null)
                Intrinsics.throwNpe();
            JSONUtils.saveJSONToFile(this.factionPointsFile, this.factionPoints, this.token);
            if (this.factionLogFile == null)
                Intrinsics.throwNpe();
            JSONUtils.saveJSONToFile(this.factionLogFile, this.factionPointLogs, this.factionLogToken);
        } else {
            Bukkit.getLogger().info("[CosmicFactionPoints] Not saving faction points due to loadedData == false!");
        }
    }
}
