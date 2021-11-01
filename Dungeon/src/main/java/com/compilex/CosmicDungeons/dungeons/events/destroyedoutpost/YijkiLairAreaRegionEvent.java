/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.me.astropvp.cratekeys.utils.CC
 *  com.cosmicpvp.cosmicutils.me.astropvp.cratekeys.utils.WorldGuardUtils
 *  kotlin.TypeCastException
 *  kotlin.jvm.internal.Intrinsics
 *  me.ifamasssxd.cosmicbosses.dungeonboss.destroyedoutpost.MotherOfYijki
 *  me.ifamasssxd.cosmicbosses.struct.Boss
 *  me.ifamasssxd.cosmicbosses.struct.me.astropvp.cratekeys.enums.BossType
 *  me.ifamasssxd.cosmicbosses.me.astropvp.cratekeys.utils.BossUtils
 *  org.bukkit.Location
 *  org.bukkit.entity.Player
 *  org.bukkit.event.player.PlayerTeleportEvent$TeleportCause
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 */
package com.compilex.CosmicDungeons.dungeons.events.destroyedoutpost;

import com.compilex.CosmicDungeons.CosmicDungeonsPlugin;
import com.compilex.CosmicDungeons.dungeons.events.RegionEvent;
import com.compilex.CosmicDungeons.dungeons.libs.DestroyedOutpostDungeon;
import com.compilex.CosmicDungeons.dungeons.libs.Dungeon;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.WorldGuardUtils;
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

public final class YijkiLairAreaRegionEvent
extends RegionEvent {
    public YijkiLairAreaRegionEvent(Dungeon d, String regionName) {
        super(d, regionName);
        if (d == null) {
            YijkiLairAreaRegionEvent.$$$reportNull$$$0(0);
        }
        if (regionName == null) {
            YijkiLairAreaRegionEvent.$$$reportNull$$$0(1);
        }
    }

    @Override
    public void run() {
        if (this.dungeon == null) {
            throw new TypeCastException("null cannot be cast to non-null type com.compilex.CosmicDungeons.dungeons.libs.DestroyedOutpostDungeon");
        }
        Intrinsics.checkExpressionValueIsNotNull((Object)BossUtils.spawnDungeonBoss((Location)new Location(this.dungeon.world, -96.0, 78.0, -643.0), (int)3, (BossType)BossType.MOTHER_OF_YIJKI), (String)"BossUtils.spawnDungeonBo\u2026BossType.MOTHER_OF_YIJKI)");
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

    private static void $$$reportNull$$$0(int n) {
        String format = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] args = new Object[3];
        switch (n) {
            default: {
                args[0] = "d";
                break;
            }
            case 1: {
                args[0] = "regionName";
            }
        }
        args[1] = "com/compilex/CosmicDungeons/dungeons/events/destroyedoutpost/YijkiLairAreaRegionEvent";
        args[2] = "<init>";
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", args));
    }
}

