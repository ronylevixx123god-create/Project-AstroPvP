package me.ifamasssxd.cosmicbosses.dungeonboss.destroyedoutpost;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.WorldGuardUtils;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import me.ifamasssxd.cosmicbosses.dungeonboss.DungeonAbility;
import me.ifamasssxd.cosmicbosses.struct.Boss;
import me.ifamasssxd.cosmicbosses.utils.BossUtils;
import org.arkhamnetwork.Arkkit.utils.LocationUtils;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv = {1, 1, 11}, bv = {1, 0, 2}, k = 1, d1 = {"\000<\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\b\n\002\020\016\n\000\n\002\020\t\n\000\n\002\020\002\n\002\b\003\n\002\030\002\n\002\b\002\n\002\020\013\n\000\030\0002\0020\001B\r\022\006\020\002\032\0020\003\006\002\020\004J\b\020\016\032\0020\017H\026J\b\020\020\032\0020\021H\026J\b\020\022\032\0020\023H\026J\b\020\024\032\0020\023H\026J\022\020\025\032\0020\0232\b\020\026\032\004\030\0010\027H\026J\b\020\030\032\0020\023H\026J\b\020\031\032\0020\032H\026R\034\020\005\032\004\030\0010\006X\016\006\016\n\000\032\004\b\007\020\b\"\004\b\t\020\nR\034\020\013\032\004\030\0010\006X\016\006\016\n\000\032\004\b\f\020\b\"\004\b\r\020\n\006\033"}, d2 = {"Lme/ifamasssxd/cosmicbosses/dungeonboss/destroyedoutpost/DimensionTravelAbility;", "Lme/ifamasssxd/cosmicbosses/dungeonboss/DungeonAbility;", "boss", "Lme/ifamasssxd/cosmicbosses/struct/Boss;", "(Lme/ifamasssxd/cosmicbosses/struct/Boss;)V", "iceCaveTeleport", "Lorg/bukkit/Location;", "getIceCaveTeleport", "()Lorg/bukkit/Location;", "setIceCaveTeleport", "(Lorg/bukkit/Location;)V", "lavaCaveTelport", "getLavaCaveTelport", "setLavaCaveTelport", "getAbilityName", "", "getCooldown", "", "onAbilityDisable", "", "onAbilityEnable", "onAbilityEvent", "event", "Lorg/bukkit/event/Event;", "onTick", "shouldStopTicking", "", "CosmicBosses"})
public final class DimensionTravelAbility extends DungeonAbility {
    @Nullable
    private Location iceCaveTeleport;

    @Nullable
    private Location lavaCaveTelport;

    public DimensionTravelAbility(@NotNull Boss boss) {
        super(boss);
    }

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

    public void onTick() {}

    public long getCooldown() {
        return TimeUnit.SECONDS.toMillis(15L);
    }

    public boolean shouldStopTicking() {
        return true;
    }

    public void onAbilityEnable() {
        Intrinsics.checkExpressionValueIsNotNull(this.boss.entity, "boss.entity");
        this.iceCaveTeleport = new Location(this.boss.entity.getWorld(), 770.0D, 105.0D, -671.0D);
        Intrinsics.checkExpressionValueIsNotNull(this.boss.entity, "boss.entity");
        this.lavaCaveTelport = new Location(this.boss.entity.getWorld(), -480.0D, 75.0D, -762.0D);
        Location caveSelected = ThreadLocalRandom.current().nextBoolean() ? this.iceCaveTeleport : this.lavaCaveTelport;
        Intrinsics.checkExpressionValueIsNotNull(this.boss.entity, "boss.entity");
        Intrinsics.checkExpressionValueIsNotNull(this.boss.entity.getWorld(), "boss.entity.world");
        for (Player pl : this.boss.entity.getWorld().getPlayers()) {
            Intrinsics.checkExpressionValueIsNotNull(pl, "pl");
            if (WorldGuardUtils.isInRegionsFast(pl.getLocation(), new String[] { "yijki_realm" })) {
                BossUtils.teleportPlayer(pl, caveSelected);
                pl.playSound(pl.getLocation(), Sound.WITHER_SHOOT, 3.0F, 1.1F);
                System.out.println("[CosmicBosses] Teleporting " + pl.getName() + " to " + LocationUtils.convertLocationToString(caveSelected) + " from travel ability!");
            }
        }
        this.boss.sendMessage(CC.White + "I am the source of all destructive powers in this universe. There is no end to how many environments I can end you in.");
        if (this.boss == null)
            throw new TypeCastException("null cannot be cast to non-null type me.ifamasssxd.cosmicbosses.dungeonboss.destroyedoutpost.MotherOfYijki");
        ((MotherOfYijki)this.boss).teleportBossInstance(caveSelected, true);
    }

    public void onAbilityDisable() {}

    public void onAbilityEvent(@Nullable Event event) {}

    @NotNull
    public String getAbilityName() {
        return "Dimensional Transport";
    }
}
