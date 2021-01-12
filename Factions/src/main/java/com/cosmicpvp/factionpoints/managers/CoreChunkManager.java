package com.cosmicpvp.factionpoints.managers;

import com.google.gson.reflect.TypeToken;
import com.massivecraft.factions.*;
import lombok.SneakyThrows;
import org.bukkit.command.CommandSender;
import java.util.concurrent.CompletableFuture;
import org.bukkit.plugin.Plugin;
import java.util.concurrent.TimeUnit;
import java.util.HashMap;
import kotlin.TypeCastException;
import com.cosmicpvp.cosmicutils.utils.JSONUtils;
import java.util.function.Function;
import org.bukkit.Bukkit;
import java.util.Iterator;
import com.cosmicpvp.cosmicutils.utils.NumberUtils;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.factionpoints.FactionsPointsAPI;
import org.bukkit.World;
import org.bukkit.Chunk;
import org.jetbrains.annotations.Nullable;
import kotlin.jvm.internal.Intrinsics;
import java.lang.reflect.Type;
import java.io.File;
import com.cosmicpvp.factionpoints.struct.ExpiringChunkData;

import java.util.concurrent.ConcurrentHashMap;
import org.jetbrains.annotations.NotNull;
import com.cosmicpvp.factionpoints.struct.CoreChunkData;
import java.util.Map;
import kotlin.Metadata;
import com.cosmicpvp.factionpoints.CosmicFactionPoints;
import com.cosmicpvp.cosmicutils.struct.managers.CosmicManager;

@Metadata(mv = { 1, 1, 11 }, bv = { 1, 0, 2 }, k = 1, d1 = { "\u0000h\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0003J\u0010\u0010 \u001a\u0004\u0018\u00010\u00062\u0006\u0010!\u001a\u00020\u0015J\u0010\u0010\"\u001a\u0004\u0018\u00010\u00162\u0006\u0010#\u001a\u00020$J\u0010\u0010%\u001a\u0004\u0018\u00010\u00062\u0006\u0010#\u001a\u00020$J\u0016\u0010&\u001a\u00020\u001f2\u0006\u0010#\u001a\u00020$2\u0006\u0010'\u001a\u00020(J\u000e\u0010)\u001a\u00020\u001f2\u0006\u0010*\u001a\u00020\u0006J\u0006\u0010+\u001a\u00020,J\u0010\u0010-\u001a\u00020,2\u0006\u0010.\u001a\u00020\u0002H\u0016J\u0010\u0010/\u001a\u00020,2\u0006\u0010.\u001a\u00020\u0002H\u0016J\u0016\u00100\u001a\u00020,2\u0006\u0010#\u001a\u00020$2\u0006\u00101\u001a\u00020\u0006J\u0010\u00102\u001a\u0004\u0018\u00010\u00162\u0006\u0010#\u001a\u00020$J\u0016\u00103\u001a\u00020,2\u0006\u0010#\u001a\u00020$2\u0006\u00101\u001a\u00020\u0006J\u0010\u00104\u001a\u0004\u0018\u00010\u00062\u0006\u0010#\u001a\u00020$J\u0010\u00105\u001a\u0004\u0018\u00010\u00062\u0006\u0010#\u001a\u00020$J\u000e\u00106\u001a\u00020,2\u0006\u0010#\u001a\u00020$J\u0016\u00107\u001a\u00020,2\u0006\u0010#\u001a\u00020$2\u0006\u00108\u001a\u000209J\u0016\u0010:\u001a\u00020,2\u0006\u0010#\u001a\u00020$2\u0006\u00101\u001a\u00020\u0006R*\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u00058\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u0016\u0010\f\u001a\n \u000e*\u0004\u0018\u00010\r0\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u000f\u001a\n \u000e*\u0004\u0018\u00010\r0\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0011X\u0082.¢\u0006\u0002\n\u0000R&\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020\u00160\u0014X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001aR&\u0010\u001b\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00150\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\t\"\u0004\b\u001d\u0010\u000bR\u000e\u0010\u001e\u001a\u00020\u001fX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006;" }, d2 = { "Lcom/cosmicpvp/factionpoints/managers/CoreChunkManager;", "Lcom/cosmicpvp/cosmicutils/struct/managers/CosmicManager;", "Lcom/cosmicpvp/factionpoints/CosmicFactionPoints;", "()V", "chunksToExpire", "Ljava/util/concurrent/ConcurrentHashMap;", "Lcom/massivecraft/factions/FLocation;", "Lcom/cosmicpvp/factionpoints/struct/ExpiringChunkData;", "getChunksToExpire", "()Ljava/util/concurrent/ConcurrentHashMap;", "setChunksToExpire", "(Ljava/util/concurrent/ConcurrentHashMap;)V", "coreChunkToken", "Ljava/lang/reflect/Type;", "kotlin.jvm.PlatformType", "expirationToken", "expiringClaimsFile", "Ljava/io/File;", "factionCoreChunkFile", "factionCoreData", "", "", "Lcom/cosmicpvp/factionpoints/struct/CoreChunkData;", "getFactionCoreData", "()Ljava/util/Map;", "setFactionCoreData", "(Ljava/util/Map;)V", "fastFactionCoreMap", "getFastFactionCoreMap", "setFastFactionCoreMap", "loadedData", "", "getClaimFromId", "id", "getCoreData", "faction", "Lcom/massivecraft/factions/Faction;", "getFactionCoreChunk", "isFactionCoreChunk", "chunk", "Lorg/bukkit/Chunk;", "isWarzoneNearby", "fLocation", "markExpiringChunks", "", "onDisable", "plugin", "onEnable", "onFactionClaimedLand", "flocation", "onFactionDisband", "onFactionUnclaimChunk", "removeAllFactionCoreChunk", "removeFactionCoreChunk", "removePointsFromUnclaimAll", "sendExpiringChunkInfo", "sender", "Lorg/bukkit/command/CommandSender;", "setFactionCoreChunk", "cosmicfactionpoints" })
public final class CoreChunkManager extends CosmicManager<CosmicFactionPoints>
{
    @NotNull
    private Map<String, CoreChunkData> factionCoreData;
    @NotNull
    private volatile ConcurrentHashMap<FLocation, ExpiringChunkData> chunksToExpire;
    @NotNull
    private ConcurrentHashMap<FLocation, String> fastFactionCoreMap;
    private File factionCoreChunkFile;
    private File expiringClaimsFile;
    private boolean loadedData;
    private final Type coreChunkToken = new TypeToken<ConcurrentHashMap<String, CoreChunkData>>(){}.getType();
    private final Type expirationToken = new TypeToken<ConcurrentHashMap<FLocation, ExpiringChunkData>>(){}.getType();

    @NotNull
    public final Map<String, CoreChunkData> getFactionCoreData() {
        return this.factionCoreData;
    }

    public final void setFactionCoreData(@Nullable Map<String, CoreChunkData> map) {
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

    @Nullable
    public final ConcurrentHashMap<FLocation, String> getFastFactionCoreMap() {
        return this.fastFactionCoreMap;
    }

    public final void setFastFactionCoreMap(@NotNull ConcurrentHashMap<FLocation, String> concurrentHashMap) {
        Intrinsics.checkParameterIsNotNull(concurrentHashMap, "<set-?>");
        this.fastFactionCoreMap = concurrentHashMap;
    }

    @Nullable
    public final FLocation getFactionCoreChunk(@Nullable final Faction faction) {
        Intrinsics.checkParameterIsNotNull(faction, "faction");
        final CoreChunkData coreChunkData = this.factionCoreData.get(faction.getId());
        return (coreChunkData != null) ? coreChunkData.masterChunk : null;
    }

    public final boolean isFactionCoreChunk(@Nullable final Faction faction, @Nullable final Chunk chunk) {
        Intrinsics.checkParameterIsNotNull(faction, "faction");
        Intrinsics.checkParameterIsNotNull(chunk, "chunk");
        final FLocation current = this.getFactionCoreChunk(faction);
        if (current != null) {
            if ((int)current.getX() == chunk.getX() && (int)current.getZ() == chunk.getZ()) {
                final String worldName = current.getWorldName();
                final World world = chunk.getWorld();
                Intrinsics.checkExpressionValueIsNotNull(world, "chunk.world");
                if (Intrinsics.areEqual(worldName, world.getName())) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    @Nullable
    public final FLocation removeFactionCoreChunk(@Nullable final Faction faction) {
        Intrinsics.checkParameterIsNotNull(faction, "faction");
        final CoreChunkData coreChunkData = this.factionCoreData.get(faction.getId());
        if (coreChunkData != null) {
            final CoreChunkData coreData = coreChunkData;
            final FLocation master = coreData.masterChunk;
            coreData.coreChunkRemovedAt = System.currentTimeMillis();
            coreData.coreChunkClaimTime = null;
            if (master != null) {
                this.fastFactionCoreMap.remove(master);
            }
            coreData.masterChunk = null;
            return master;
        }
        return null;
    }

    public final void removePointsFromUnclaimAll(@NotNull final Faction faction) {
        Intrinsics.checkParameterIsNotNull(faction, "faction");
        final Long points = FactionsPointsAPI.getPoints(faction);
        if (points == null || points <= 0L) {
            return;
        }
        double toRemove = Math.ceil(points * 0.01);
        if (Double.compare(toRemove, points) > 0 || points <= 1L) {
            toRemove = points;
        }
        if (toRemove > 0) {
            FactionsPointsAPI.modifyPoints(faction, -toRemove);
            for (final FPlayer fplayer : faction.getFPlayersWhereOnline(true)) {
                fplayer.sendMessage(CC.RedB + "- " + NumberUtils.formatSeconds(toRemove) + CC.Red + " Faction Points (Unclaiming Core Chunk)");
                fplayer.sendMessage(CC.Gray + "Without a Core Chunk, your faction:");
                fplayer.sendMessage(CC.RedB + " * " + CC.White + "Cannot gain Faction Points.");
                fplayer.sendMessage(CC.RedB + " * " + CC.White + "Will lose 4% of your Faction Points every hour.");
                fplayer.sendMessage(CC.RedB + " * " + CC.White + "Will lose ALL your Faction Points after 24 hours.");
            }
        }
    }

    @Nullable
    public final FLocation removeAllFactionCoreChunk(@NotNull final Faction faction) {
        Intrinsics.checkParameterIsNotNull(faction, "faction");
        final FLocation loc = this.removeFactionCoreChunk(faction);
        final String id = faction.getId();
        Intrinsics.checkExpressionValueIsNotNull(id, "faction.id");
        final FLocation found = this.getClaimFromId(id);
        if (found != null) {
            this.fastFactionCoreMap.remove(found);
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
    public final CoreChunkData onFactionDisband(@NotNull final Faction faction) {
        Intrinsics.checkParameterIsNotNull(faction, "faction");
        return this.factionCoreData.remove(faction.getId());
    }

    public final void onFactionUnclaimChunk(@NotNull final Faction faction, @NotNull final FLocation flocation) {
        Intrinsics.checkParameterIsNotNull(faction, "faction");
        Intrinsics.checkParameterIsNotNull(flocation, "flocation");
        boolean removed = false;
        removed = (this.chunksToExpire.remove(flocation) != null);
        if (!removed) {
            removed = (this.fastFactionCoreMap.remove(flocation) != null);
        }
        if (removed) {
            Bukkit.getLogger().info("Removing expiring claim info for " + faction.getTag() + " at " + flocation.toString());
        }
    }

    @Nullable
    public final CoreChunkData getCoreData(@NotNull final Faction faction) {
        Intrinsics.checkParameterIsNotNull(faction, "faction");
        return this.factionCoreData.get(faction.getId());
    }

    public final void setFactionCoreChunk(@NotNull final Faction faction, @NotNull final FLocation flocation) {
        //
        // This method could not be decompiled.
        //
        // Original Bytecode:
        //
        //     1: ldc             "faction"
        //     3: invokestatic    kotlin/jvm/internal/Intrinsics.checkParameterIsNotNull:(Ljava/lang/Object;Ljava/lang/String;)V
        //     6: aload_2         /* flocation */
        //     7: ldc_w           "flocation"
        //    10: invokestatic    kotlin/jvm/internal/Intrinsics.checkParameterIsNotNull:(Ljava/lang/Object;Ljava/lang/String;)V
        //    13: aload_0         /* this */
        //    14: getfield        com/cosmicpvp/factionpoints/managers/CoreChunkManager.factionCoreData:Ljava/util/Map;
        //    17: astore          4
        //    19: aload_1         /* faction */
        //    20: invokevirtual   com/massivecraft/factions/Faction.getId:()Ljava/lang/String;
        //    23: dup
        //    24: ldc_w           "faction.id"
        //    27: invokestatic    kotlin/jvm/internal/Intrinsics.checkExpressionValueIsNotNull:(Ljava/lang/Object;Ljava/lang/String;)V
        //    30: astore          key$iv
        //    32: aload           $receiver$iv
        //    34: aload           key$iv
        //    36: invokeinterface java/util/Map.get:(Ljava/lang/Object;)Ljava/lang/Object;
        //    41: astore          value$iv
        //    43: aload           value$iv
        //    45: ifnonnull       75
        //    48: nop
        //    49: new             Lcom/cosmicpvp/factionpoints/struct/CoreChunkData;
        //    52: dup
        //    53: invokespecial   com/cosmicpvp/factionpoints/struct/CoreChunkData.<init>:()V
        //    56: astore          answer$iv
        //    58: aload           $receiver$iv
        //    60: aload           key$iv
        //    62: aload           answer$iv
        //    64: invokeinterface java/util/Map.put:(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
        //    69: pop
        //    70: aload           answer$iv
        //    72: goto            77
        //    75: aload           value$iv
        //    77: nop
        //    78: checkcast       Lcom/cosmicpvp/factionpoints/struct/CoreChunkData;
        //    81: astore_3        /* data */
        //    82: aload_3         /* data */
        //    83: aload_2         /* flocation */
        //    84: putfield        com/cosmicpvp/factionpoints/struct/CoreChunkData.masterChunk:Lcom/massivecraft/factions/FLocation;
        //    87: aload_3         /* data */
        //    88: aconst_null
        //    89: checkcast       Ljava/lang/Long;
        //    92: putfield        com/cosmicpvp/factionpoints/struct/CoreChunkData.coreChunkRemovedAt:Ljava/lang/Long;
        //    95: aload_3         /* data */
        //    96: invokestatic    java/lang/System.currentTimeMillis:()J
        //    99: invokestatic    java/lang/Long.valueOf:(J)Ljava/lang/Long;
        //   102: putfield        com/cosmicpvp/factionpoints/struct/CoreChunkData.coreChunkClaimTime:Ljava/lang/Long;
        //   105: aload_0         /* this */
        //   106: getfield        com/cosmicpvp/factionpoints/managers/CoreChunkManager.fastFactionCoreMap:Ljava/util/concurrent/ConcurrentHashMap;
        //   109: checkcast       Ljava/util/Map;
        //   112: astore          4
        //   114: aload_1         /* faction */
        //   115: invokevirtual   com/massivecraft/factions/Faction.getId:()Ljava/lang/String;
        //   118: dup
        //   119: ldc_w           "faction.id"
        //   122: invokestatic    kotlin/jvm/internal/Intrinsics.checkExpressionValueIsNotNull:(Ljava/lang/Object;Ljava/lang/String;)V
        //   125: astore          5
        //   127: aload           4
        //   129: aload_2         /* flocation */
        //   130: aload           5
        //   132: invokeinterface java/util/Map.put:(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
        //   137: pop
        //   138: invokestatic    org/bukkit/Bukkit.getScheduler:()Lorg/bukkit/scheduler/BukkitScheduler;
        //   141: invokestatic    com/cosmicpvp/factionpoints/CosmicFactionPoints.get:()Lcom/cosmicpvp/factionpoints/CosmicFactionPoints;
        //   144: checkcast       Lorg/bukkit/plugin/Plugin;
        //   147: new             Lcom/cosmicpvp/factionpoints/managers/CoreChunkManager$setFactionCoreChunk$1;
        //   150: dup
        //   151: aload_1         /* faction */
        //   152: aload_2         /* flocation */
        //   153: invokespecial   com/cosmicpvp/factionpoints/managers/CoreChunkManager$setFactionCoreChunk$1.<init>:(Lcom/massivecraft/factions/Faction;Lcom/massivecraft/factions/FLocation;)V
        //   156: checkcast       Ljava/lang/Runnable;
        //   159: ldc2_w          20
        //   162: invokeinterface org/bukkit/scheduler/BukkitScheduler.scheduleSyncDelayedTask:(Lorg/bukkit/plugin/Plugin;Ljava/lang/Runnable;J)I
        //   167: pop
        //   168: invokestatic    org/bukkit/Bukkit.getLogger:()Ljava/util/logging/Logger;
        //   171: getstatic       kotlin/jvm/internal/StringCompanionObject.INSTANCE:Lkotlin/jvm/internal/StringCompanionObject;
        //   174: astore          4
        //   176: ldc_w           "[CosmicFactionPoints] Setting Core Chunk for %s, id=%s, chunk=%s,%s"
        //   179: astore          5
        //   181: iconst_4
        //   182: anewarray       Ljava/lang/Object;
        //   185: dup
        //   186: iconst_0
        //   187: aload_1         /* faction */
        //   188: invokevirtual   com/massivecraft/factions/Faction.getTag:()Ljava/lang/String;
        //   191: aastore
        //   192: dup
        //   193: iconst_1
        //   194: aload_1         /* faction */
        //   195: invokevirtual   com/massivecraft/factions/Faction.getId:()Ljava/lang/String;
        //   198: aastore
        //   199: dup
        //   200: iconst_2
        //   201: aload_2         /* flocation */
        //   202: invokevirtual   com/massivecraft/factions/FLocation.getX:()J
        //   205: invokestatic    java/lang/Long.valueOf:(J)Ljava/lang/Long;
        //   208: aastore
        //   209: dup
        //   210: iconst_3
        //   211: aload_2         /* flocation */
        //   212: invokevirtual   com/massivecraft/factions/FLocation.getZ:()J
        //   215: invokestatic    java/lang/Long.valueOf:(J)Ljava/lang/Long;
        //   218: aastore
        //   219: astore          6
        //   221: astore          9
        //   223: aload           5
        //   225: aload           6
        //   227: dup
        //   228: arraylength
        //   229: invokestatic    java/util/Arrays.copyOf:([Ljava/lang/Object;I)[Ljava/lang/Object;
        //   232: invokestatic    java/lang/String.format:(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
        //   235: dup
        //   236: ldc_w           "java.lang.String.format(format, *args)"
        //   239: invokestatic    kotlin/jvm/internal/Intrinsics.checkExpressionValueIsNotNull:(Ljava/lang/Object;Ljava/lang/String;)V
        //   242: astore          10
        //   244: aload           9
        //   246: aload           10
        //   248: invokevirtual   java/util/logging/Logger.info:(Ljava/lang/String;)V
        //   251: return
        //    StackMapTable: 00 02 FF 00 4B 00 07 07 00 02 07 00 37 07 00 4A 00 07 00 3D 07 01 14 07 01 5E 00 00 41 07 01 5E
        //
        // The error that occurred was:
        //
        // java.lang.NullPointerException
        //     at com.strobel.decompiler.ast.AstBuilder.convertLocalVariables(AstBuilder.java:2895)
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2445)
        //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:211)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at com.strobel.decompiler.DecompilerDriver.decompileType(DecompilerDriver.java:330)
        //     at com.strobel.decompiler.DecompilerDriver.decompileJar(DecompilerDriver.java:251)
        //     at com.strobel.decompiler.DecompilerDriver.main(DecompilerDriver.java:126)
        //
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }

    public final void onFactionClaimedLand(@NotNull Faction faction, @NotNull FLocation flocation) {
        Intrinsics.checkParameterIsNotNull(faction, "faction");
        Intrinsics.checkParameterIsNotNull(flocation, "flocation");
        if (!faction.isNormal()) {
            this.chunksToExpire.remove(flocation);
            return;
        }
    }
    // todo: fuck kotlin
//        ExpiringChunkData data = this.chunksToExpire.computeIfAbsent(flocation, CoreChunkManager$onFactionClaimedLand$data$.INSTANCE);
//        if (!data.connectedToWarzone) {
//            long start = System.currentTimeMillis();
//            if (isWarzoneNearby(flocation)) {
//                data.connectedToWarzone = true;
//                CosmicFactionPoints.debug("Storing chunk at " + flocation.toString() + " as isConnectedToWarzone!");
//            }
//            CosmicFactionPoints.debug("Scanned chunk for warzone in " + (System.currentTimeMillis() - start) + "ms to check nearby warzone chunks!");
//        }
//        data.onClaimed(faction);
//    }

    public final boolean isWarzoneNearby(@NotNull FLocation fLocation) {
        Intrinsics.checkParameterIsNotNull(fLocation, "fLocation");
        byte b;
        byte b1;
        for (b = -3, b1 = 3; b <= b1; b++) {
            byte b2;
            byte b3;
            for (b2 = -3, b3 = 3; b2 <= b3; b2++) {
                Faction at = Board.getFactionAt(fLocation.getRelative(b, b2));
                Intrinsics.checkExpressionValueIsNotNull(at, "at");
                if ((b != 0 || b2 != 0) && at.isWarZone())
                    return true;
            }
        }
        return false;
    }

    @SneakyThrows
    public void onEnable(@Nullable final CosmicFactionPoints plugin, P p) {
        Intrinsics.checkParameterIsNotNull(plugin, "plugin");
        final File orCreateFile = JSONUtils.getOrCreateFile(plugin.getDataFolder(), "factionCoreData.json");
        Intrinsics.checkExpressionValueIsNotNull(orCreateFile, "JSONUtils.getOrCreateFil\u2026, \"factionCoreData.json\")");
        this.factionCoreChunkFile = orCreateFile;
        final File orCreateFile2 = JSONUtils.getOrCreateFile(plugin.getDataFolder(), "expiringClaimData.json");
        Intrinsics.checkExpressionValueIsNotNull(orCreateFile2, "JSONUtils.getOrCreateFil\u2026\"expiringClaimData.json\")");
        this.expiringClaimsFile = orCreateFile2;
        final File factionCoreChunkFile = this.factionCoreChunkFile;
        if (factionCoreChunkFile == null) {
            Intrinsics.throwUninitializedPropertyAccessException("factionCoreChunkFile");
        }
        final Object fromJson = JSONUtils.fromJson(factionCoreChunkFile, (Object)this.coreChunkToken, new ConcurrentHashMap());
        if (fromJson == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.util.concurrent.ConcurrentHashMap<kotlin.String, com.cosmicpvp.factionpoints.struct.CoreChunkData>");
        }
        this.factionCoreData = (ConcurrentHashMap<String, CoreChunkData>)fromJson;
        final File expiringClaimsFile = this.expiringClaimsFile;
        if (expiringClaimsFile == null) {
            Intrinsics.throwUninitializedPropertyAccessException("expiringClaimsFile");
        }
        final Object fromJson2 = JSONUtils.fromJson(expiringClaimsFile, (Object)this.expirationToken, (Object)new ConcurrentHashMap());
        if (fromJson2 == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.util.concurrent.ConcurrentHashMap<com.massivecraft.factions.FLocation, com.cosmicpvp.factionpoints.struct.ExpiringChunkData>");
        }
        this.chunksToExpire = (ConcurrentHashMap<FLocation, ExpiringChunkData>)fromJson2;
        this.loadedData = true;
        final HashMap invalid = new HashMap();
        for (final Map.Entry<String, CoreChunkData> entry : this.factionCoreData.entrySet()) {
            final String key = entry.getKey();
            final CoreChunkData value = entry.getValue();
            if (value.masterChunk != null) {
                final String at = Board.getIdAt(value.masterChunk);
                if (at == null || (Intrinsics.areEqual(at, key) ^ true)) {
                    final HashMap hashMap = invalid;
                    final FLocation masterChunk = value.masterChunk;
                    Intrinsics.checkExpressionValueIsNotNull(masterChunk, "value.masterChunk");
                    hashMap.put(key, masterChunk);
                }
                else {
                    final ConcurrentHashMap<FLocation, String> concurrentHashMap = this.fastFactionCoreMap;
                    final FLocation masterChunk2 = value.masterChunk;
                    Intrinsics.checkExpressionValueIsNotNull(masterChunk2, "value.masterChunk");
                    concurrentHashMap.put(masterChunk2, key);
                    if (value.coreChunkClaimTime != null) {
                        continue;
                    }
                    final ExpiringChunkData expiring = this.chunksToExpire.get(value.masterChunk);
                    if (expiring != null) {
                        value.coreChunkClaimTime = expiring.timeClaimed;
                        Bukkit.getLogger().info("[CosmicFactionPoints] Fixing no coreChunkClaimTime being found for " + key + " while they HAVE a core chunk, setting to CORE CHUNK CLAIMED TIME=" + value.coreChunkClaimTime + "!");
                    }
                    else {
                        value.coreChunkClaimTime = System.currentTimeMillis() - TimeUnit.HOURS.toMillis(48L);
                        Bukkit.getLogger().info("[CosmicFactionPoints] Fixing no coreChunkClaimTime being found for " + key + " while they HAVE a core chunk, setting to " + value.coreChunkClaimTime + "!");
                    }
                }
            }
            else {
                if (value.coreChunkRemovedAt != null) {
                    continue;
                }
                final Long coreChunkRemovedAt = value.coreChunkRemovedAt;
                if (coreChunkRemovedAt != null) {
                    if (coreChunkRemovedAt == -1L) {
                        continue;
                    }
                }
                Bukkit.getLogger().info("[CosmicFactionPoints] Fixing no coreChunkRemovedAt being found for " + key + " while not having a core chunk!");
                value.coreChunkRemovedAt = System.currentTimeMillis();
            }
        }
        // todo: fuck kotlin
//        for (Map.Entry<String, CoreChunkData> entry : invalid.entrySet()) {
//            final String fId = entry.getKey();
//            final FLocation chunk = (FLocation)entry.getValue();
//            this.factionCoreData.remove(fId);
//            Bukkit.getLogger().info("[CosmicFactionPoints] Removing CoreChunkData from " + fId + " claimed at " + chunk.toString());
//            final String currentId = this.fastFactionCoreMap.get(chunk);
//            if (currentId != null && Intrinsics.areEqual(currentId, fId)) {
//                this.fastFactionCoreMap.remove(chunk);
//                Bukkit.getLogger().info("[CosmicFactionPoints] Removing CoreChunk from fastFactionCoreMap " + fId + " claimed at " + chunk.toString());
//            }
//        }
        for (final Map.Entry<FLocation, ExpiringChunkData> entry3 : this.chunksToExpire.entrySet()) {
            final FLocation claim = entry3.getKey();
            final ExpiringChunkData value2 = entry3.getValue();
            final String at = Board.getIdAt(claim);
            if (at == null || (Intrinsics.areEqual(at, value2.fId) ^ true)) {
                CosmicFactionPoints.log("Removing invalid chunkdata claim at " + claim + " due to no longer being owned by " + value2.fId + " instead owned by " + at);
                this.chunksToExpire.remove(claim);
            }
            else {
                if (!Intrinsics.areEqual(at, "-2")) {
                    continue;
                }
                value2.expirationTime = 0L;
            }
        }
        this.markExpiringChunks();
        // todo: fuck kotlin
//        Bukkit.getScheduler().scheduleAsyncRepeatingTask((Plugin)CosmicFactionPoints.get(), (Runnable)new CoreChunkManager$onEnable.CoreChunkManager$onEnable$1(this), 300L, 1200L);
//        Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin)plugin, (Runnable)new CoreChunkManager$onEnable.CoreChunkManager$onEnable$2(this), 1200L, 1200L);
        final long time = 72000;
        final long threshhold = TimeUnit.MINUTES.toMillis(15L);
        // todo: fuck kotlin
        //   Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin)plugin, (Runnable)new CoreChunkManager$onEnable.CoreChunkManager$onEnable$3(this, threshhold), time, time);
    }

    public final void markExpiringChunks() {
        // todo: fuck kotlin
//        final Map data = MapsKt__MapsKt.toMap((Map<?, ?>)this.factionCoreData);
//        CompletableFuture.runAsync((Runnable)new CoreChunkManager$markExpiringChunks.CoreChunkManager$markExpiringChunks$1(this, data));
    }

    public final void sendExpiringChunkInfo(@NotNull final Faction faction, @NotNull final CommandSender sender) {
        Intrinsics.checkParameterIsNotNull(faction, "faction");
        Intrinsics.checkParameterIsNotNull(sender, "sender");
        final String id = faction.getId();
        // todo: fuck kotlin
//        Bukkit.getScheduler().scheduleAsyncDelayedTask((Plugin)CosmicFactionPoints.get(), (Runnable)new CoreChunkManager$sendExpiringChunkInfo.CoreChunkManager$sendExpiringChunkInfo$1(this, id, sender));
    }

    @Override
    public void onEnable(CosmicFactionPoints cosmicFactionPoints) {
    }

    @SneakyThrows
    public void onDisable(@NotNull final CosmicFactionPoints plugin) {
        Intrinsics.checkParameterIsNotNull(plugin, "plugin");
        if (!this.loadedData) {
            return;
        }
        final File factionCoreChunkFile = this.factionCoreChunkFile;
        if (factionCoreChunkFile == null) {
            Intrinsics.throwUninitializedPropertyAccessException("factionCoreChunkFile");
        }
        JSONUtils.saveJSONToFile(factionCoreChunkFile, (Object)this.factionCoreData, (Object)this.coreChunkToken);
        final File expiringClaimsFile = this.expiringClaimsFile;
        if (expiringClaimsFile == null) {
            Intrinsics.throwUninitializedPropertyAccessException("expiringClaimsFile");
        }
        JSONUtils.saveJSONToFile(expiringClaimsFile, (Object)this.chunksToExpire, (Object)this.expirationToken);
    }
}
