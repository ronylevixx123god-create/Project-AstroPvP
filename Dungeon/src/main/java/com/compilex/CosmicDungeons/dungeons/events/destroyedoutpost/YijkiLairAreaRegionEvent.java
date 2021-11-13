/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.cosmicpvp.cosmicutils.utils.WorldGuardUtils
 *  kotlin.Metadata
 *  kotlin.TypeCastException
 *  kotlin.jvm.internal.Intrinsics
 *  me.ifamasssxd.cosmicbosses.dungeonboss.destroyedoutpost.MotherOfYijki
 *  me.ifamasssxd.cosmicbosses.struct.Boss
 *  me.ifamasssxd.cosmicbosses.struct.enums.BossType
 *  me.ifamasssxd.cosmicbosses.utils.BossUtils
 *  org.bukkit.Location
 *  org.bukkit.entity.Player
 *  org.bukkit.event.player.PlayerTeleportEvent$TeleportCause
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 *  org.jetbrains.annotations.NotNull
 */
package com.compilex.CosmicDungeons.dungeons.events.destroyedoutpost;

import com.compilex.CosmicDungeons.CosmicDungeonsPlugin;
import com.compilex.CosmicDungeons.dungeons.events.RegionEvent;
import com.compilex.CosmicDungeons.dungeons.libs.DestroyedOutpostDungeon;
import com.compilex.CosmicDungeons.dungeons.libs.Dungeon;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.WorldGuardUtils;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import me.ifamasssxd.cosmicbosses.dungeonboss.destroyedoutpost.MotherOfYijki;
import me.ifamasssxd.cosmicbosses.struct.Boss;
import me.ifamasssxd.cosmicbosses.struct.enums.BossType;
import me.ifamasssxd.cosmicbosses.utils.BossUtils;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 1, 10}, bv={1, 0, 2}, k=1, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\b\u0010\u0007\u001a\u00020\bH\u0016\u00a8\u0006\t"}, d2={"Lcom/compilex/CosmicDungeons/dungeons/events/destroyedoutpost/YijkiLairAreaRegionEvent;", "Lcom/compilex/CosmicDungeons/dungeons/events/RegionEvent;", "d", "Lcom/compilex/CosmicDungeons/dungeons/libs/Dungeon;", "regionName", "", "(Lcom/compilex/CosmicDungeons/dungeons/libs/Dungeon;Ljava/lang/String;)V", "run", "", "CosmicDungeons"})
public final class YijkiLairAreaRegionEvent
extends RegionEvent {
    public YijkiLairAreaRegionEvent(@NotNull Dungeon d, @NotNull String regionName) {
        super(d, regionName);
    }

    @Override
    public void run() {
        if (this.dungeon == null) {
            throw new TypeCastException("null cannot be cast to non-null type com.compilex.CosmicDungeons.dungeons.libs.DestroyedOutpostDungeon");
        }
        ((DestroyedOutpostDungeon)this.dungeon).boss = (MotherOfYijki)Boss.bosses.get(BossUtils.spawnDungeonBoss((Location)new Location(this.dungeon.world, -96.0, 78.0, -643.0), (int)3, (BossType)BossType.MOTHER_OF_YIJKI).getUniqueId());
        Intrinsics.checkExpressionValueIsNotNull((Object)this.dungeon, (String)"this.dungeon");
        for (Player player : this.dungeon.getPlayers()) {
            Location location = player.getLocation();
            Intrinsics.checkExpressionValueIsNotNull((Object)location, (String)"location");
            double x = location.getX();
            double z = location.getZ();
            if (x > 400.0 && x < 540.0 && z > -2050.0 && z < -623.0) {
                player.sendMessage(CC.RedB + "The Mother of Yijki has been summoned, you have been forcibly teleported into its dimension!");
                if (this.dungeon == null) {
                    throw new TypeCastException("null cannot be cast to non-null type com.compilex.CosmicDungeons.dungeons.libs.DestroyedOutpostDungeon");
                }
                player.setMetadata("ignore_combat_tag", (MetadataValue)new FixedMetadataValue((Plugin)CosmicDungeonsPlugin.get(), (Object)((DestroyedOutpostDungeon)this.dungeon).getDungeonEvents().mainYijkiCave));
                if (this.dungeon == null) {
                    throw new TypeCastException("null cannot be cast to non-null type com.compilex.CosmicDungeons.dungeons.libs.DestroyedOutpostDungeon");
                }
                player.teleport(((DestroyedOutpostDungeon)this.dungeon).getDungeonEvents().mainYijkiCave, PlayerTeleportEvent.TeleportCause.UNKNOWN);
                player.removeMetadata("ignore_combat_tag", (Plugin)CosmicDungeonsPlugin.get());
                continue;
            }
            if (WorldGuardUtils.inRegion((Location)location, (String)"yijki_realm") || WorldGuardUtils.inRegion((Location)location, (String)"yijki_portal_exit")) continue;
            player.sendMessage(CC.RedB + "You were not protected by the Interdimensional Portals at the time the Mother of Yijki was spawned, so you were killed instantly by her planet ending ability.");
            Intrinsics.checkExpressionValueIsNotNull((Object)player, (String)"player");
            player.damage(player.getMaxHealth() + 99.0);
        }
    }
}

