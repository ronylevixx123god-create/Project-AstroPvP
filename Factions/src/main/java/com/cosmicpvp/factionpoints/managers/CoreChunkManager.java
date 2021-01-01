/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.struct.managers.CosmicManager
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.cosmicpvp.cosmicutils.utils.FactionUtils
 *  com.cosmicpvp.cosmicutils.utils.JSONUtils
 *  com.cosmicpvp.cosmicutils.utils.NumberUtils
 *  com.massivecraft.factions.Board
 *  com.massivecraft.factions.FLocation
 *  com.massivecraft.factions.FPlayer
 *  com.massivecraft.factions.Faction
 *  com.massivecraft.factions.Factions
 *  com.massivecraft.factions.P
 *  com.massivecraft.factions.struct.FLogType
 *  net.minecraft.util.com.google.gson.reflect.TypeToken
 *  org.bukkit.Bukkit
 *  org.bukkit.Chunk
 *  org.bukkit.World
 *  org.bukkit.command.CommandSender
 *  org.bukkit.craftbukkit.v1_7_R4.util.HashTreeSet
 *  org.bukkit.plugin.Plugin
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package com.cosmicpvp.factionpoints.managers;

import com.cosmicpvp.cosmicutils.struct.managers.CosmicManager;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.FactionUtils;
import com.cosmicpvp.cosmicutils.utils.JSONUtils;
import com.cosmicpvp.cosmicutils.utils.NumberUtils;
import com.cosmicpvp.factionpoints.CosmicFactionPoints;
import com.cosmicpvp.factionpoints.FactionsPointsAPI;
import com.cosmicpvp.factionpoints.struct.ChunkNotification;
import com.cosmicpvp.factionpoints.struct.CoreChunkData;
import com.cosmicpvp.factionpoints.struct.ExpiringChunkData;
import com.cosmicpvp.factionpoints.utils.FactionHelper;
import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.Factions;
import com.massivecraft.factions.P;
import com.massivecraft.factions.struct.FLogType;
import java.io.File;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import net.minecraft.util.com.google.gson.reflect.TypeToken;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.util.HashTreeSet;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 1, 11}, bv={1, 0, 2}, k=1, d1={"\u0000h\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0003J\u0010\u0010 \u001a\u0004\u0018\u00010\u00062\u0006\u0010!\u001a\u00020\u0015J\u0010\u0010\"\u001a\u0004\u0018\u00010\u00162\u0006\u0010#\u001a\u00020$J\u0010\u0010%\u001a\u0004\u0018\u00010\u00062\u0006\u0010#\u001a\u00020$J\u0016\u0010&\u001a\u00020\u001f2\u0006\u0010#\u001a\u00020$2\u0006\u0010'\u001a\u00020(J\u000e\u0010)\u001a\u00020\u001f2\u0006\u0010*\u001a\u00020\u0006J\u0006\u0010+\u001a\u00020,J\u0010\u0010-\u001a\u00020,2\u0006\u0010.\u001a\u00020\u0002H\u0016J\u0010\u0010/\u001a\u00020,2\u0006\u0010.\u001a\u00020\u0002H\u0016J\u0016\u00100\u001a\u00020,2\u0006\u0010#\u001a\u00020$2\u0006\u00101\u001a\u00020\u0006J\u0010\u00102\u001a\u0004\u0018\u00010\u00162\u0006\u0010#\u001a\u00020$J\u0016\u00103\u001a\u00020,2\u0006\u0010#\u001a\u00020$2\u0006\u00101\u001a\u00020\u0006J\u0010\u00104\u001a\u0004\u0018\u00010\u00062\u0006\u0010#\u001a\u00020$J\u0010\u00105\u001a\u0004\u0018\u00010\u00062\u0006\u0010#\u001a\u00020$J\u000e\u00106\u001a\u00020,2\u0006\u0010#\u001a\u00020$J\u0016\u00107\u001a\u00020,2\u0006\u0010#\u001a\u00020$2\u0006\u00108\u001a\u000209J\u0016\u0010:\u001a\u00020,2\u0006\u0010#\u001a\u00020$2\u0006\u00101\u001a\u00020\u0006R*\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u00058\u0006@\u0006X\u0087\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u0016\u0010\f\u001a\n \u000e*\u0004\u0018\u00010\r0\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u000f\u001a\n \u000e*\u0004\u0018\u00010\r0\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0011X\u0082.\u00a2\u0006\u0002\n\u0000R&\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020\u00160\u0014X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001aR&\u0010\u001b\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00150\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\t\"\u0004\b\u001d\u0010\u000bR\u000e\u0010\u001e\u001a\u00020\u001fX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006;"}, d2={"Lcom/cosmicpvp/factionpoints/managers/CoreChunkManager;", "Lcom/cosmicpvp/cosmicutils/struct/managers/CosmicManager;", "Lcom/cosmicpvp/factionpoints/CosmicFactionPoints;", "()V", "chunksToExpire", "Ljava/util/concurrent/ConcurrentHashMap;", "Lcom/massivecraft/factions/FLocation;", "Lcom/cosmicpvp/factionpoints/struct/ExpiringChunkData;", "getChunksToExpire", "()Ljava/util/concurrent/ConcurrentHashMap;", "setChunksToExpire", "(Ljava/util/concurrent/ConcurrentHashMap;)V", "coreChunkToken", "Ljava/lang/reflect/Type;", "kotlin.jvm.PlatformType", "expirationToken", "expiringClaimsFile", "Ljava/io/File;", "factionCoreChunkFile", "factionCoreData", "", "", "Lcom/cosmicpvp/factionpoints/struct/CoreChunkData;", "getFactionCoreData", "()Ljava/util/Map;", "setFactionCoreData", "(Ljava/util/Map;)V", "fastFactionCoreMap", "getFastFactionCoreMap", "setFastFactionCoreMap", "loadedData", "", "getClaimFromId", "id", "getCoreData", "faction", "Lcom/massivecraft/factions/Faction;", "getFactionCoreChunk", "isFactionCoreChunk", "chunk", "Lorg/bukkit/Chunk;", "isWarzoneNearby", "fLocation", "markExpiringChunks", "", "onDisable", "plugin", "onEnable", "onFactionClaimedLand", "flocation", "onFactionDisband", "onFactionUnclaimChunk", "removeAllFactionCoreChunk", "removeFactionCoreChunk", "removePointsFromUnclaimAll", "sendExpiringChunkInfo", "sender", "Lorg/bukkit/command/CommandSender;", "setFactionCoreChunk", "cosmicfactionpoints"})
public final class CoreChunkManager
extends CosmicManager<CosmicFactionPoints> {
    @NotNull
    private Map<String, CoreChunkData> factionCoreData = new ConcurrentHashMap();
    @NotNull
    private volatile ConcurrentHashMap<FLocation, ExpiringChunkData> chunksToExpire = new ConcurrentHashMap();
    @NotNull
    private ConcurrentHashMap<FLocation, String> fastFactionCoreMap = new ConcurrentHashMap();
    private File factionCoreChunkFile;
    private File expiringClaimsFile;
    private final Type coreChunkToken = new TypeToken<ConcurrentHashMap<String, CoreChunkData>>(){}.getType();
    private final Type expirationToken = new TypeToken<ConcurrentHashMap<FLocation, ExpiringChunkData>>(){}.getType();
    private boolean loadedData;

    @NotNull
    public final Map<String, CoreChunkData> getFactionCoreData() {
        return this.factionCoreData;
    }

    public final void setFactionCoreData(@NotNull Map<String, CoreChunkData> map) {
        Intrinsics.checkParameterIsNotNull(map, "<set-?>");
        this.factionCoreData = map;
    }

    @NotNull
    public final ConcurrentHashMap<FLocation, ExpiringChunkData> getChunksToExpire() {
        return this.chunksToExpire;
    }

    public final void setChunksToExpire(@NotNull ConcurrentHashMap<FLocation, ExpiringChunkData> concurrentHashMap) {
        Intrinsics.checkParameterIsNotNull(concurrentHashMap, "<set-?>");
        this.chunksToExpire = concurrentHashMap;
    }

    @NotNull
    public final ConcurrentHashMap<FLocation, String> getFastFactionCoreMap() {
        return this.fastFactionCoreMap;
    }

    public final void setFastFactionCoreMap(@NotNull ConcurrentHashMap<FLocation, String> concurrentHashMap) {
        Intrinsics.checkParameterIsNotNull(concurrentHashMap, "<set-?>");
        this.fastFactionCoreMap = concurrentHashMap;
    }

    @Nullable
    public final FLocation getFactionCoreChunk(@NotNull Faction faction) {
        Intrinsics.checkParameterIsNotNull((Object)faction, "faction");
        CoreChunkData coreChunkData = this.factionCoreData.get(faction.getId());
        return coreChunkData != null ? coreChunkData.masterChunk : null;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final boolean isFactionCoreChunk(@NotNull Faction faction, @NotNull Chunk chunk) {
        Intrinsics.checkParameterIsNotNull((Object)faction, "faction");
        Intrinsics.checkParameterIsNotNull((Object)chunk, "chunk");
        FLocation current = this.getFactionCoreChunk(faction);
        if (current == null) return false;
        if ((int)current.getX() != chunk.getX()) return false;
        if ((int)current.getZ() != chunk.getZ()) return false;
        String string = current.getWorldName();
        World world = chunk.getWorld();
        Intrinsics.checkExpressionValueIsNotNull((Object)world, "chunk.world");
        if (!Intrinsics.areEqual(string, world.getName())) return false;
        return true;
    }

    @Nullable
    public final FLocation removeFactionCoreChunk(@NotNull Faction faction) {
        Intrinsics.checkParameterIsNotNull((Object)faction, "faction");
        CoreChunkData coreChunkData = this.factionCoreData.get(faction.getId());
        if (coreChunkData == null) {
            return null;
        }
        CoreChunkData coreData = coreChunkData;
        FLocation master = coreData.masterChunk;
        coreData.coreChunkRemovedAt = System.currentTimeMillis();
        coreData.coreChunkClaimTime = null;
        if (master != null) {
            this.fastFactionCoreMap.remove((Object)master);
        }
        coreData.masterChunk = null;
        return master;
    }

    public final void removePointsFromUnclaimAll(@NotNull Faction faction) {
        Intrinsics.checkParameterIsNotNull((Object)faction, "faction");
        Long points = FactionsPointsAPI.getPoints(faction);
        if (points == null || points <= 0L) {
            return;
        }
        double toRemove = Math.ceil((double)points.longValue() * 0.01);
        if (Double.compare(toRemove, points.longValue()) > 0 || points <= 1L) {
            toRemove = points.longValue();
        }
        if (toRemove > 0.0) {
            FactionsPointsAPI.modifyPoints(faction, -toRemove);
            for (FPlayer fplayer : faction.getFPlayersWhereOnline(true)) {
                fplayer.sendMessage(CC.RedB + "- " + NumberUtils.formatSeconds((double)toRemove) + CC.Red + " Faction Points (Unclaiming Core Chunk)");
                fplayer.sendMessage(CC.Gray + "Without a Core Chunk, your faction:");
                fplayer.sendMessage(CC.RedB + " * " + CC.White + "Cannot gain Faction Points.");
                fplayer.sendMessage(CC.RedB + " * " + CC.White + "Will lose 4% of your Faction Points every hour.");
                fplayer.sendMessage(CC.RedB + " * " + CC.White + "Will lose ALL your Faction Points after 24 hours.");
            }
        }
    }

    @Nullable
    public final FLocation removeAllFactionCoreChunk(@NotNull Faction faction) {
        Intrinsics.checkParameterIsNotNull((Object)faction, "faction");
        FLocation loc = this.removeFactionCoreChunk(faction);
        String string = faction.getId();
        Intrinsics.checkExpressionValueIsNotNull(string, "faction.id");
        FLocation found = this.getClaimFromId(string);
        if (found != null) {
            this.fastFactionCoreMap.remove((Object)found);
        }
        return loc;
    }

    @Nullable
    public final FLocation getClaimFromId(@NotNull String id) {
        Intrinsics.checkParameterIsNotNull(id, "id");
        for (Map.Entry<FLocation, String> entry : this.fastFactionCoreMap.entrySet()) {
            if (!Intrinsics.areEqual(entry.getValue(), id)) continue;
            return entry.getKey();
        }
        return null;
    }

    @Nullable
    public final CoreChunkData onFactionDisband(@NotNull Faction faction) {
        Intrinsics.checkParameterIsNotNull((Object)faction, "faction");
        return this.factionCoreData.remove(faction.getId());
    }

    public final void onFactionUnclaimChunk(@NotNull Faction faction, @NotNull FLocation flocation) {
        Intrinsics.checkParameterIsNotNull((Object)faction, "faction");
        Intrinsics.checkParameterIsNotNull((Object)flocation, "flocation");
        boolean removed = false;
        boolean bl = removed = this.chunksToExpire.remove((Object)flocation) != null;
        if (!removed) {
            boolean bl2 = removed = this.fastFactionCoreMap.remove((Object)flocation) != null;
        }
        if (removed) {
            Bukkit.getLogger().info("Removing expiring claim info for " + faction.getTag() + " at " + flocation.toString());
        }
    }

    @Nullable
    public final CoreChunkData getCoreData(@NotNull Faction faction) {
        Intrinsics.checkParameterIsNotNull((Object)faction, "faction");
        return this.factionCoreData.get(faction.getId());
    }

    /*
     * WARNING - void declaration
     */
    public final void setFactionCoreChunk(@NotNull Faction faction, @NotNull FLocation flocation) {
        Object object;
        void $receiver$iv;
        Intrinsics.checkParameterIsNotNull((Object)faction, "faction");
        Intrinsics.checkParameterIsNotNull((Object)flocation, "flocation");
        Object object2 = this.factionCoreData;
        String string = faction.getId();
        Intrinsics.checkExpressionValueIsNotNull(string, "faction.id");
        String key$iv = string;
        Object value$iv = $receiver$iv.get(key$iv);
        if (value$iv == null) {
            CoreChunkData answer$iv = new CoreChunkData();
            $receiver$iv.put(key$iv, answer$iv);
            object = answer$iv;
        } else {
            object = value$iv;
        }
        CoreChunkData data2 = (CoreChunkData)object;
        data2.masterChunk = flocation;
        data2.coreChunkRemovedAt = null;
        data2.coreChunkClaimTime = System.currentTimeMillis();
        object2 = this.fastFactionCoreMap;
        String string2 = faction.getId();
        Intrinsics.checkExpressionValueIsNotNull(string2, "faction.id");
        String string3 = string2;
        object2.put(flocation, string3);
        Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)CosmicFactionPoints.get(), new Runnable(faction, flocation){
            final /* synthetic */ Faction $faction;
            final /* synthetic */ FLocation $flocation;

            public final void run() {
                this.$faction.sendMessage(CC.AquaB + "(!) " + "CORE CHUNK CLAIMED: " + this.$flocation.getX() + "x " + this.$flocation.getZ() + "z");
                this.$faction.sendMessage(CC.Gray + "Your Core Chunk must be protected at all times.");
                this.$faction.sendMessage(CC.Gray + "If an enemy faction is able to breach your " + "Core Chunk with TNT, you will lose up to " + CC.Aqua + "25%" + CC.Gray + " of your Faction's Points.");
                this.$faction.sendMessage(CC.Gray + "All chunks not " + CC.Aqua + "directly connected" + CC.Gray + " to your Core Chunk will expire after " + CC.AquaU + "24 hours" + CC.Gray + ".");
                this.$faction.sendMessage(CC.Red + "PROTECT YOUR CORE CHUNK AT ALL COSTS!");
            }
            {
                this.$faction = faction;
                this.$flocation = fLocation;
            }
        }, 20L);
        object2 = StringCompanionObject.INSTANCE;
        string3 = "[CosmicFactionPoints] Setting Core Chunk for %s, id=%s, chunk=%s,%s";
        Object[] arrobject = new Object[]{faction.getTag(), faction.getId(), flocation.getX(), flocation.getZ()};
        Logger logger = Bukkit.getLogger();
        String string4 = String.format(string3, Arrays.copyOf(arrobject, arrobject.length));
        Intrinsics.checkExpressionValueIsNotNull(string4, "java.lang.String.format(format, *args)");
        String string5 = string4;
        logger.info(string5);
    }

    public final void onFactionClaimedLand(@NotNull Faction faction, @NotNull FLocation flocation) {
        Intrinsics.checkParameterIsNotNull((Object)faction, "faction");
        Intrinsics.checkParameterIsNotNull((Object)flocation, "flocation");
        if (!faction.isNormal()) {
            this.chunksToExpire.remove((Object)flocation);
            return;
        }
        ExpiringChunkData data2 = this.chunksToExpire.computeIfAbsent(flocation, onFactionClaimedLand.data.1.INSTANCE);
        if (!data2.connectedToWarzone) {
            long start = System.currentTimeMillis();
            if (this.isWarzoneNearby(flocation)) {
                data2.connectedToWarzone = true;
                CosmicFactionPoints.debug("Storing chunk at " + flocation.toString() + " as isConnectedToWarzone!");
            }
            CosmicFactionPoints.debug("Scanned chunk for warzone in " + (System.currentTimeMillis() - start) + "ms to check nearby warzone chunks!");
        }
        data2.onClaimed(faction);
    }

    /*
     * WARNING - void declaration
     */
    public final boolean isWarzoneNearby(@NotNull FLocation fLocation) {
        Intrinsics.checkParameterIsNotNull((Object)fLocation, "fLocation");
        int n = -3;
        int n2 = 3;
        while (n <= n2) {
            void x;
            int n3 = -3;
            int n4 = 3;
            while (n3 <= n4) {
                void z;
                if (x != false || z != false) {
                    Faction at;
                    Faction faction = at = Board.getFactionAt((FLocation)fLocation.getRelative((int)x, (int)z));
                    Intrinsics.checkExpressionValueIsNotNull((Object)faction, "at");
                    if (faction.isWarZone()) {
                        return true;
                    }
                }
                ++z;
            }
            ++x;
        }
        return false;
    }

    public void onEnable(@NotNull CosmicFactionPoints plugin) {
        Object currentId;
        Object fId;
        Object at;
        Object value;
        Object key;
        Intrinsics.checkParameterIsNotNull((Object)plugin, "plugin");
        File file = JSONUtils.getOrCreateFile((File)plugin.getDataFolder(), (String)"factionCoreData.json");
        Intrinsics.checkExpressionValueIsNotNull(file, "JSONUtils.getOrCreateFil\u2026, \"factionCoreData.json\")");
        this.factionCoreChunkFile = file;
        File file2 = JSONUtils.getOrCreateFile((File)plugin.getDataFolder(), (String)"expiringClaimData.json");
        Intrinsics.checkExpressionValueIsNotNull(file2, "JSONUtils.getOrCreateFil\u2026\"expiringClaimData.json\")");
        this.expiringClaimsFile = file2;
        File file3 = this.factionCoreChunkFile;
        if (file3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("factionCoreChunkFile");
        }
        Object object = JSONUtils.fromJson((File)file3, (Object)this.coreChunkToken, new ConcurrentHashMap());
        if (object == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.util.concurrent.ConcurrentHashMap<kotlin.String, com.cosmicpvp.factionpoints.struct.CoreChunkData>");
        }
        this.factionCoreData = (ConcurrentHashMap)object;
        File file4 = this.expiringClaimsFile;
        if (file4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("expiringClaimsFile");
        }
        Object object2 = JSONUtils.fromJson((File)file4, (Object)this.expirationToken, new ConcurrentHashMap());
        if (object2 == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.util.concurrent.ConcurrentHashMap<com.massivecraft.factions.FLocation, com.cosmicpvp.factionpoints.struct.ExpiringChunkData>");
        }
        this.chunksToExpire = (ConcurrentHashMap)object2;
        this.loadedData = true;
        HashMap invalid = new HashMap();
        Map<String, CoreChunkData> map = this.factionCoreData;
        Iterator<Map.Entry<String, CoreChunkData>> iterator2 = map.entrySet().iterator();
        while (iterator2.hasNext()) {
            Map.Entry<String, CoreChunkData> entry;
            Map.Entry<String, CoreChunkData> entry2 = entry = iterator2.next();
            key = entry2.getKey();
            entry2 = entry;
            value = entry2.getValue();
            if (((CoreChunkData)value).masterChunk != null) {
                FLocation fLocation;
                Map map2;
                at = Board.getIdAt((FLocation)((CoreChunkData)value).masterChunk);
                if (at == null || Intrinsics.areEqual(at, key) ^ true) {
                    map2 = invalid;
                    Intrinsics.checkExpressionValueIsNotNull((Object)((CoreChunkData)value).masterChunk, "value.masterChunk");
                    map2.put(key, fLocation);
                    continue;
                }
                map2 = this.fastFactionCoreMap;
                Intrinsics.checkExpressionValueIsNotNull((Object)((CoreChunkData)value).masterChunk, "value.masterChunk");
                Object object3 = key;
                map2.put(fLocation, object3);
                if (((CoreChunkData)value).coreChunkClaimTime != null) continue;
                ExpiringChunkData expiring = this.chunksToExpire.get((Object)((CoreChunkData)value).masterChunk);
                if (expiring != null) {
                    ((CoreChunkData)value).coreChunkClaimTime = expiring.timeClaimed;
                    Bukkit.getLogger().info("[CosmicFactionPoints] Fixing no coreChunkClaimTime being found for " + (String)key + " while they HAVE a core chunk, setting to CORE CHUNK CLAIMED TIME=" + ((CoreChunkData)value).coreChunkClaimTime + "!");
                    continue;
                }
                ((CoreChunkData)value).coreChunkClaimTime = System.currentTimeMillis() - TimeUnit.HOURS.toMillis(48L);
                Bukkit.getLogger().info("[CosmicFactionPoints] Fixing no coreChunkClaimTime being found for " + (String)key + " while they HAVE a core chunk, setting to " + ((CoreChunkData)value).coreChunkClaimTime + "!");
                continue;
            }
            if (((CoreChunkData)value).coreChunkRemovedAt != null) continue;
            Long l = ((CoreChunkData)value).coreChunkRemovedAt;
            if (l != null && l == -1L) continue;
            Bukkit.getLogger().info("[CosmicFactionPoints] Fixing no coreChunkRemovedAt being found for " + (String)key + " while not having a core chunk!");
            ((CoreChunkData)value).coreChunkRemovedAt = System.currentTimeMillis();
        }
        key = invalid;
        for (Map.Entry<String, CoreChunkData> entry : key.entrySet()) {
            at = entry;
            fId = at.getKey();
            at = entry;
            FLocation chunk = (FLocation)at.getValue();
            this.factionCoreData.remove(fId);
            Bukkit.getLogger().info("[CosmicFactionPoints] Removing CoreChunkData from " + (String)fId + " claimed at " + chunk.toString());
            currentId = this.fastFactionCoreMap.get((Object)chunk);
            if (currentId == null || !Intrinsics.areEqual(currentId, fId)) continue;
            this.fastFactionCoreMap.remove((Object)chunk);
            Bukkit.getLogger().info("[CosmicFactionPoints] Removing CoreChunk from fastFactionCoreMap " + (String)fId + " claimed at " + chunk.toString());
        }
        fId = this.chunksToExpire;
        for (Map.Entry<String, CoreChunkData> entry : fId.entrySet()) {
            currentId = entry;
            FLocation claim = (FLocation)currentId.getKey();
            currentId = entry;
            value = (ExpiringChunkData)((Object)currentId.getValue());
            at = Board.getIdAt((FLocation)claim);
            if (at == null || Intrinsics.areEqual(at, ((ExpiringChunkData)value).fId) ^ true) {
                CosmicFactionPoints.log("Removing invalid chunkdata claim at " + (Object)claim + " due to no longer being owned by " + ((ExpiringChunkData)value).fId + " instead owned by " + (String)at);
                this.chunksToExpire.remove((Object)claim);
                continue;
            }
            if (!Intrinsics.areEqual(at, "-2")) continue;
            ((ExpiringChunkData)value).expirationTime = 0L;
        }
        this.markExpiringChunks();
        Bukkit.getScheduler().scheduleAsyncRepeatingTask((Plugin)CosmicFactionPoints.get(), new Runnable(this){
            final /* synthetic */ CoreChunkManager this$0;

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             * WARNING - void declaration
             */
            public final void run() {
                HashSet<Object> locs = new HashSet<Object>();
                ConcurrentHashMap<FLocation, ExpiringChunkData> concurrentHashMap = this.this$0.getChunksToExpire();
                synchronized (concurrentHashMap) {
                    Object key;
                    Map.Entry<K, V> entry;
                    Map.Entry<K, V> entry2;
                    Map toAlert = null;
                    Object object = this.this$0.getChunksToExpire();
                    Iterator<Map.Entry<K, V>> iterator2 = object.entrySet().iterator();
                    while (iterator2.hasNext()) {
                        entry = entry2 = iterator2.next();
                        key = (FLocation)entry.getKey();
                        entry = entry2;
                        ExpiringChunkData value = (ExpiringChunkData)entry.getValue();
                        if (value.expirationTime == 0L || value.fId != null && Intrinsics.areEqual(value.fId, "-2")) continue;
                        if (value.expirationTime < System.currentTimeMillis()) {
                            locs.add(key);
                            continue;
                        }
                        long timeUntilExpiration = value.expirationTime - System.currentTimeMillis();
                        int secondsToExpiration = (int)TimeUnit.MILLISECONDS.toSeconds(timeUntilExpiration);
                        if (secondsToExpiration > 14400 || secondsToExpiration != 14400 && secondsToExpiration != 3600 && secondsToExpiration != 1800 && secondsToExpiration != 600) continue;
                        if (toAlert == null) {
                            toAlert = new LinkedHashMap<K, V>();
                        }
                        ((HashTreeSet)toAlert.computeIfAbsent(value.fId, onEnable.1.1.INSTANCE)).add((Object)new ChunkNotification((FLocation)key, secondsToExpiration));
                    }
                    if (toAlert != null && !(entry2 = toAlert).isEmpty()) {
                        key = toAlert;
                        iterator2 = key.entrySet().iterator();
                        while (iterator2.hasNext()) {
                            Object[] arrobject;
                            void thisCollection$iv;
                            void $receiver$iv;
                            entry = entry2 = iterator2.next();
                            String fId = (String)entry.getKey();
                            entry = entry2;
                            HashTreeSet notifs = (HashTreeSet)entry.getValue();
                            Faction fac = Factions.i.get(fId);
                            if (fac == null || !fac.isNormal()) continue;
                            Collection collection = (Collection)notifs;
                            StringBuilder stringBuilder = new StringBuilder().append("Sending notifications to ").append(fac.getTag()).append(": ");
                            if ($receiver$iv == null) {
                                throw new TypeCastException("null cannot be cast to non-null type java.util.Collection<T>");
                            }
                            if (thisCollection$iv.toArray(new ChunkNotification[0]) == null) {
                                throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
                            }
                            CosmicFactionPoints.debug(stringBuilder.append(Arrays.toString(arrobject)).toString());
                            for (FPlayer onlineFPlayer : fac.getFPlayersWhereOnline(true)) {
                                for (ChunkNotification chunkNotif : notifs) {
                                    onlineFPlayer.sendMessage(chunkNotif.toString());
                                    onlineFPlayer.sendMessage("");
                                }
                            }
                        }
                    }
                    object = Unit.INSTANCE;
                }
                if (locs.size() > 0) {
                    Bukkit.getScheduler().runTask((Plugin)CosmicFactionPoints.get(), new Runnable(this, locs){
                        final /* synthetic */ onEnable.1 this$0;
                        final /* synthetic */ HashSet $locs;

                        public final void run() {
                            for (FLocation expiredLoc : this.$locs) {
                                this.this$0.this$0.getChunksToExpire().remove((Object)expiredLoc);
                                this.this$0.this$0.getFastFactionCoreMap().remove((Object)expiredLoc);
                                CosmicFactionPoints.debug("Removing expired claim at " + expiredLoc.toString());
                                Board.removeAt((FLocation)expiredLoc);
                            }
                            Bukkit.getLogger().info("[CosmicFactionPoints] Expiring " + this.$locs.size() + " faction chunks.");
                        }
                        {
                            this.this$0 = var1_1;
                            this.$locs = hashSet;
                        }
                    });
                }
            }
            {
                this.this$0 = coreChunkManager;
            }
        }, 300L, 1200L);
        Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin)plugin, new Runnable(this){
            final /* synthetic */ CoreChunkManager this$0;

            public final void run() {
                long time = TimeUnit.HOURS.toMillis(24L);
                Map<String, CoreChunkData> map = this.this$0.getFactionCoreData();
                Iterator<Map.Entry<String, CoreChunkData>> iterator2 = map.entrySet().iterator();
                while (iterator2.hasNext()) {
                    Faction fac;
                    Map.Entry<String, CoreChunkData> entry;
                    Map.Entry<String, CoreChunkData> entry2 = entry = iterator2.next();
                    String key = entry2.getKey();
                    entry2 = entry;
                    CoreChunkData value = entry2.getValue();
                    if (value.masterChunk != null || value.coreChunkRemovedAt == null) continue;
                    Long l = value.coreChunkRemovedAt;
                    if (l != null && l == -1L) continue;
                    long l2 = System.currentTimeMillis();
                    Long l3 = value.coreChunkRemovedAt;
                    Intrinsics.checkExpressionValueIsNotNull(l3, "value.coreChunkRemovedAt");
                    long timeSince = l2 - l3;
                    if (timeSince < time) continue;
                    if (FactionUtils.getFactionById((String)key) == null) {
                        continue;
                    }
                    Long l4 = FactionsPointsAPI.getPoints(fac);
                    if (l4 == null) {
                        continue;
                    }
                    long points = l4;
                    Bukkit.getLogger().info("[CosmicFactionPoints] Clearing " + points + " points for " + (Object)value.masterChunk + " due to having no master chunk for 7 days!");
                    FactionsPointsAPI.removePoints(key);
                    value.coreChunkRemovedAt = -1L;
                    P p = P.p;
                    Intrinsics.checkExpressionValueIsNotNull((Object)p, "P.p");
                    p.getFlogManager().log(fac, FLogType.F_POINTS, new String[]{"Inactivity", CC.RedB + "RELEASED", String.valueOf(points) + " Faction Points"});
                }
            }
            {
                this.this$0 = coreChunkManager;
            }
        }, 1200L, 1200L);
        long l = 72000;
        long threshhold = TimeUnit.MINUTES.toMillis(15L);
        Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin)plugin, new Runnable(this, threshhold){
            final /* synthetic */ CoreChunkManager this$0;
            final /* synthetic */ long $threshhold;

            public final void run() {
                Map<String, CoreChunkData> map = this.this$0.getFactionCoreData();
                Iterator<Map.Entry<String, CoreChunkData>> iterator2 = map.entrySet().iterator();
                while (iterator2.hasNext()) {
                    Faction faction;
                    Map.Entry<String, CoreChunkData> entry;
                    Map.Entry<String, CoreChunkData> entry2 = entry = iterator2.next();
                    String fId = entry2.getKey();
                    entry2 = entry;
                    CoreChunkData value = entry2.getValue();
                    if (value.masterChunk != null) continue;
                    if (value.coreChunkRemovedAt != null) {
                        long l = System.currentTimeMillis();
                        Long l2 = value.coreChunkRemovedAt;
                        Intrinsics.checkExpressionValueIsNotNull(l2, "value.coreChunkRemovedAt");
                        if (l - l2 <= this.$threshhold) continue;
                    }
                    if (FactionUtils.getFactionById((String)fId) == null) {
                        continue;
                    }
                    Long points = CosmicFactionPoints.get().pointManager.getFactionPoints(faction);
                    if (points == null || points <= 0L) continue;
                    double pointsToRemove = Math.ceil((double)points.longValue() * 0.04);
                    if (Double.compare(pointsToRemove, points.longValue()) > 0 || points <= 1L) {
                        pointsToRemove = points.longValue();
                    }
                    FactionsPointsAPI.modifyPoints(faction, -pointsToRemove);
                    faction.sendMessage(CC.RedB + "- " + NumberUtils.formatSeconds((double)pointsToRemove) + CC.Red + " Faction Points (No Core Chunk)");
                    P p = P.p;
                    Intrinsics.checkExpressionValueIsNotNull((Object)p, "P.p");
                    p.getFlogManager().log(faction, FLogType.F_POINTS, new String[]{"Missing Core Chunk", CC.RedB + "RELEASED", String.valueOf(pointsToRemove) + " Faction Points"});
                    Bukkit.getLogger().info("[CosmicFactionPoints] Removing " + pointsToRemove + " points from " + faction.getTag() + " due to lastUnclaimedCoreChunk=" + value.coreChunkRemovedAt);
                }
            }
            {
                this.this$0 = coreChunkManager;
                this.$threshhold = l;
            }
        }, l, l);
    }

    public final void markExpiringChunks() {
        Map<String, CoreChunkData> data2 = MapsKt.toMap(this.factionCoreData);
        CompletableFuture.runAsync(new Runnable(this, data2){
            final /* synthetic */ CoreChunkManager this$0;
            final /* synthetic */ Map $data;

            /*
             * WARNING - void declaration
             */
            public final void run() {
                void toCleanup;
                long start = System.currentTimeMillis();
                Ref.ObjectRef<T> objectRef = new Ref.ObjectRef<T>();
                objectRef.element = new HashSet<E>();
                Map map = this.$data;
                Iterator<Map.Entry<K, V>> iterator2 = map.entrySet().iterator();
                while (iterator2.hasNext()) {
                    Map.Entry<K, V> entry;
                    Map.Entry<K, V> entry2 = entry = iterator2.next();
                    String key = (String)entry2.getKey();
                    entry2 = entry;
                    CoreChunkData value = (CoreChunkData)entry2.getValue();
                    Faction faction = Factions.i.get(key);
                    if (faction == null) {
                        ((HashSet)toCleanup.element).add(key);
                        Bukkit.getLogger().info("Found null faction in entries: " + key + " cleaning up!");
                        continue;
                    }
                    if (value.masterChunk == null || faction.isWarZone()) continue;
                    FactionHelper.markAllNonAttachedToCore(faction, value.masterChunk);
                }
                CosmicFactionPoints.log("Took " + (System.currentTimeMillis() - start) + "ms to scan " + this.this$0.getFactionCoreData().size() + " faction core data, cleaned up " + ((HashSet)toCleanup.element).size() + " factions.");
                if (((HashSet)toCleanup.element).size() > 0) {
                    Bukkit.getScheduler().runTask((Plugin)CosmicFactionPoints.get(), new Runnable(this, (Ref.ObjectRef)toCleanup){
                        final /* synthetic */ markExpiringChunks.1 this$0;
                        final /* synthetic */ Ref.ObjectRef $toCleanup;

                        public final void run() {
                            for (String fID : (HashSet)this.$toCleanup.element) {
                                this.this$0.this$0.getFactionCoreData().remove(fID);
                            }
                        }
                        {
                            this.this$0 = var1_1;
                            this.$toCleanup = objectRef;
                        }
                    });
                }
            }
            {
                this.this$0 = coreChunkManager;
                this.$data = map;
            }
        });
    }

    public final void sendExpiringChunkInfo(@NotNull Faction faction, @NotNull CommandSender sender) {
        Intrinsics.checkParameterIsNotNull((Object)faction, "faction");
        Intrinsics.checkParameterIsNotNull((Object)sender, "sender");
        String id = faction.getId();
        Bukkit.getScheduler().scheduleAsyncDelayedTask((Plugin)CosmicFactionPoints.get(), new Runnable(this, id, sender){
            final /* synthetic */ CoreChunkManager this$0;
            final /* synthetic */ String $id;
            final /* synthetic */ CommandSender $sender;

            public final void run() {
                long start = System.currentTimeMillis();
                HashTreeSet set = new HashTreeSet();
                Map map = this.this$0.getChunksToExpire();
                Iterator iterator2 = map.entrySet().iterator();
                while (iterator2.hasNext()) {
                    Map.Entry<K, V> entry;
                    Map.Entry<K, V> entry2 = entry = iterator2.next();
                    FLocation key = (FLocation)entry2.getKey();
                    entry2 = entry;
                    ExpiringChunkData value = (ExpiringChunkData)entry2.getValue();
                    if (value.fId == null || !Intrinsics.areEqual(value.fId, this.$id) || value.expirationTime == 0L) continue;
                    int timeUntilExpiration = (int)((value.expirationTime - System.currentTimeMillis()) / (long)1000);
                    set.add((Object)new ChunkNotification(key, timeUntilExpiration));
                }
                if (set.isEmpty()) {
                    this.$sender.sendMessage(CC.RedB + "(!) " + CC.Red + "No expiring claims found to display!");
                } else {
                    for (ChunkNotification notif : set) {
                        this.$sender.sendMessage(notif.toString(false));
                    }
                }
                CosmicFactionPoints.debug("Took " + (System.currentTimeMillis() - start) + "ms to calculate expiring claims for " + this.$sender.getName());
            }
            {
                this.this$0 = coreChunkManager;
                this.$id = string;
                this.$sender = commandSender;
            }
        });
    }

    public void onDisable(@NotNull CosmicFactionPoints plugin) {
        Intrinsics.checkParameterIsNotNull((Object)plugin, "plugin");
        if (!this.loadedData) {
            return;
        }
        File file = this.factionCoreChunkFile;
        if (file == null) {
            Intrinsics.throwUninitializedPropertyAccessException("factionCoreChunkFile");
        }
        JSONUtils.saveJSONToFile((File)file, this.factionCoreData, (Object)this.coreChunkToken);
        File file2 = this.expiringClaimsFile;
        if (file2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("expiringClaimsFile");
        }
        JSONUtils.saveJSONToFile((File)file2, this.chunksToExpire, (Object)this.expirationToken);
    }
}

