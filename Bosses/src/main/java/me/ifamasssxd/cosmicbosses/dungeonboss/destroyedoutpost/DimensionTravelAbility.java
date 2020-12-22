/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.cosmicpvp.cosmicutils.utils.WorldGuardUtils
 *  kotlin.Metadata
 *  kotlin.TypeCastException
 *  kotlin.jvm.internal.Intrinsics
 *  org.arkhamnetwork.Arkkit.utils.LocationUtils
 *  org.bukkit.Location
 *  org.bukkit.Sound
 *  org.bukkit.World
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package me.ifamasssxd.cosmicbosses.dungeonboss.destroyedoutpost;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.WorldGuardUtils;
import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import me.ifamasssxd.cosmicbosses.dungeonboss.DungeonAbility;
import me.ifamasssxd.cosmicbosses.dungeonboss.destroyedoutpost.MotherOfYijki;
import me.ifamasssxd.cosmicbosses.struct.Boss;
import me.ifamasssxd.cosmicbosses.utils.BossUtils;
import org.arkhamnetwork.Arkkit.utils.LocationUtils;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 1, 11}, bv={1, 0, 2}, k=1, d1={"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\b\u0010\u000e\u001a\u00020\u000fH\u0016J\b\u0010\u0010\u001a\u00020\u0011H\u0016J\b\u0010\u0012\u001a\u00020\u0013H\u0016J\b\u0010\u0014\u001a\u00020\u0013H\u0016J\u0012\u0010\u0015\u001a\u00020\u00132\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017H\u0016J\b\u0010\u0018\u001a\u00020\u0013H\u0016J\b\u0010\u0019\u001a\u00020\u001aH\u0016R\u001c\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u001c\u0010\u000b\u001a\u0004\u0018\u00010\u0006X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\b\"\u0004\b\r\u0010\n\u00a8\u0006\u001b"}, d2={"Lme/ifamasssxd/cosmicbosses/dungeonboss/destroyedoutpost/DimensionTravelAbility;", "Lme/ifamasssxd/cosmicbosses/dungeonboss/DungeonAbility;", "boss", "Lme/ifamasssxd/cosmicbosses/struct/Boss;", "(Lme/ifamasssxd/cosmicbosses/struct/Boss;)V", "iceCaveTeleport", "Lorg/bukkit/Location;", "getIceCaveTeleport", "()Lorg/bukkit/Location;", "setIceCaveTeleport", "(Lorg/bukkit/Location;)V", "lavaCaveTelport", "getLavaCaveTelport", "setLavaCaveTelport", "getAbilityName", "", "getCooldown", "", "onAbilityDisable", "", "onAbilityEnable", "onAbilityEvent", "event", "Lorg/bukkit/event/Event;", "onTick", "shouldStopTicking", "", "CosmicBosses"})
public final class DimensionTravelAbility
extends DungeonAbility {
    @Nullable
    private Location iceCaveTeleport;
    @Nullable
    private Location lavaCaveTelport;

    @Nullable
    public final Location getIceCaveTeleport() {
        return this.iceCaveTeleport;
    }

    public final void setIceCaveTeleport(@Nullable Location location) {
        this.iceCaveTeleport = location;
    }

    @Nullable
    public final Location getLavaCaveTelport() {
        return this.lavaCaveTelport;
    }

    public final void setLavaCaveTelport(@Nullable Location location) {
        this.lavaCaveTelport = location;
    }

    @Override
    public void onTick() {
    }

    @Override
    public long getCooldown() {
        return TimeUnit.SECONDS.toMillis(15L);
    }

    @Override
    public boolean shouldStopTicking() {
        return true;
    }

    @Override
    public void onAbilityEnable() {
        LivingEntity livingEntity = this.boss.entity;
        Intrinsics.checkExpressionValueIsNotNull((Object)livingEntity, (String)"boss.entity");
        this.iceCaveTeleport = new Location(livingEntity.getWorld(), 770.0, 105.0, -671.0);
        LivingEntity livingEntity2 = this.boss.entity;
        Intrinsics.checkExpressionValueIsNotNull((Object)livingEntity2, (String)"boss.entity");
        this.lavaCaveTelport = new Location(livingEntity2.getWorld(), -480.0, 75.0, -762.0);
        Location caveSelected = ThreadLocalRandom.current().nextBoolean() ? this.iceCaveTeleport : this.lavaCaveTelport;
        LivingEntity livingEntity3 = this.boss.entity;
        Intrinsics.checkExpressionValueIsNotNull((Object)livingEntity3, (String)"boss.entity");
        World world = livingEntity3.getWorld();
        Intrinsics.checkExpressionValueIsNotNull((Object)world, (String)"boss.entity.world");
        Iterator iterator = world.getPlayers().iterator();
        while (iterator.hasNext()) {
            Player pl;
            Player player = pl = (Player)iterator.next();
            Intrinsics.checkExpressionValueIsNotNull((Object)player, (String)"pl");
            if (!WorldGuardUtils.isInRegionsFast((Location)player.getLocation(), (String[])new String[]{"yijki_realm"})) continue;
            BossUtils.teleportPlayer(pl, caveSelected);
            pl.playSound(pl.getLocation(), Sound.WITHER_SHOOT, 3.0f, 1.1f);
            System.out.println("[CosmicBosses] Teleporting " + pl.getName() + " to " + LocationUtils.convertLocationToString((Location)caveSelected) + " from travel ability!");
        }
        this.boss.sendMessage(CC.White + "I am the source of all destructive powers in this universe. There is no end to how many environments I can end you in.");
        Boss boss = this.boss;
        if (boss == null) {
            throw new TypeCastException("null cannot be cast to non-null type me.ifamasssxd.cosmicbosses.dungeonboss.destroyedoutpost.MotherOfYijki");
        }
        ((MotherOfYijki)boss).teleportBossInstance(caveSelected, true);
    }

    @Override
    public void onAbilityDisable() {
    }

    @Override
    public void onAbilityEvent(@Nullable Event event) {
    }

    @Override
    @NotNull
    public String getAbilityName() {
        return "Dimensional Transport";
    }

    public DimensionTravelAbility(@NotNull Boss boss) {
        Intrinsics.checkParameterIsNotNull((Object)boss, (String)"boss");
        super(boss);
    }
}

