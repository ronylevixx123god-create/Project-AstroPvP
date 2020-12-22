/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.cosmicpvp.cosmicutils.utils.ItemBuilder
 *  kotlin.Metadata
 *  kotlin.TypeCastException
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.server.v1_7_R4.Entity
 *  net.minecraft.server.v1_7_R4.World
 *  net.minecraft.server.v1_7_R4.WorldServer
 *  org.arkhamnetwork.Arkkit.utils.LocationUtils
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.World
 *  org.bukkit.block.Block
 *  org.bukkit.craftbukkit.v1_7_R4.CraftWorld
 *  org.bukkit.craftbukkit.v1_7_R4.entity.CraftEntity
 *  org.bukkit.enchantments.Enchantment
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.event.entity.CreatureSpawnEvent$SpawnReason
 *  org.bukkit.inventory.EntityEquipment
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 *  org.jetbrains.annotations.NotNull
 */
package me.ifamasssxd.cosmicbosses.dungeonboss.destroyedoutpost;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.ItemBuilder;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import me.ifamasssxd.cosmicbosses.CosmicBosses;
import me.ifamasssxd.cosmicbosses.dungeonboss.destroyedoutpost.HealingBlazeEntity;
import me.ifamasssxd.cosmicbosses.dungeonboss.destroyedoutpost.MotherOfYijki;
import me.ifamasssxd.cosmicbosses.struct.Boss;
import me.ifamasssxd.cosmicbosses.struct.BossAbility;
import me.ifamasssxd.cosmicbosses.struct.EntityContainer;
import net.minecraft.server.v1_7_R4.Entity;
import net.minecraft.server.v1_7_R4.World;
import net.minecraft.server.v1_7_R4.WorldServer;
import org.arkhamnetwork.Arkkit.utils.LocationUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftEntity;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 1, 11}, bv={1, 0, 2}, k=1, d1={"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003B\r\u0012\u0006\u0010\u0004\u001a\u00020\u0002\u00a2\u0006\u0002\u0010\u0005J\b\u0010\u0014\u001a\u00020\u0015H\u0016J\b\u0010\u0016\u001a\u00020\u0017H\u0016J\b\u0010\u0018\u001a\u00020\u0019H\u0016J\u000e\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\b0\u0007H\u0016J\u0006\u0010\u001b\u001a\u00020\u001cJ\b\u0010\u001d\u001a\u00020\u0015H\u0016J\u0010\u0010\u001e\u001a\u00020\u00152\u0006\u0010\u001f\u001a\u00020\u000fH\u0002R \u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR \u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013\u00a8\u0006 "}, d2={"Lme/ifamasssxd/cosmicbosses/dungeonboss/destroyedoutpost/MinionsAbility;", "Lme/ifamasssxd/cosmicbosses/struct/BossAbility;", "Lme/ifamasssxd/cosmicbosses/dungeonboss/destroyedoutpost/MotherOfYijki;", "Lme/ifamasssxd/cosmicbosses/struct/EntityContainer;", "boss", "(Lme/ifamasssxd/cosmicbosses/dungeonboss/destroyedoutpost/MotherOfYijki;)V", "entitySet", "", "Lorg/bukkit/entity/Entity;", "getEntitySet", "()Ljava/util/Set;", "setEntitySet", "(Ljava/util/Set;)V", "locationLists", "", "Lorg/bukkit/Location;", "getLocationLists", "()Ljava/util/List;", "setLocationLists", "(Ljava/util/List;)V", "clearEntities", "", "getAbilityName", "", "getCooldown", "", "getEntities", "hasBlazesAlive", "", "onAbility", "spawnBlaze", "location", "CosmicBosses"})
public final class MinionsAbility
extends BossAbility<MotherOfYijki>
implements EntityContainer {
    @NotNull
    private Set<org.bukkit.entity.Entity> entitySet;
    @NotNull
    private List<? extends Location> locationLists;

    @NotNull
    public final Set<org.bukkit.entity.Entity> getEntitySet() {
        return this.entitySet;
    }

    public final void setEntitySet(@NotNull Set<org.bukkit.entity.Entity> set) {
        Intrinsics.checkParameterIsNotNull(set, (String)"<set-?>");
        this.entitySet = set;
    }

    @NotNull
    public final List<Location> getLocationLists() {
        return this.locationLists;
    }

    public final void setLocationLists(@NotNull List<? extends Location> list) {
        Intrinsics.checkParameterIsNotNull(list, (String)"<set-?>");
        this.locationLists = list;
    }

    @Override
    public void clearEntities() {
        Iterable $receiver$iv = this.getEntities();
        for (Object element$iv : $receiver$iv) {
            org.bukkit.entity.Entity ent = (org.bukkit.entity.Entity)element$iv;
            ent.remove();
        }
        this.getEntities().clear();
    }

    @Override
    @NotNull
    public Set<org.bukkit.entity.Entity> getEntities() {
        return this.entitySet;
    }

    @Override
    public long getCooldown() {
        return TimeUnit.SECONDS.toMillis(100L);
    }

    @Override
    @NotNull
    public String getAbilityName() {
        return "Siphon";
    }

    public final boolean hasBlazesAlive() {
        boolean bl;
        block3: {
            Iterable $receiver$iv = this.entitySet;
            if ($receiver$iv instanceof Collection && ((Collection)$receiver$iv).isEmpty()) {
                bl = false;
            } else {
                for (Object element$iv : $receiver$iv) {
                    org.bukkit.entity.Entity it = (org.bukkit.entity.Entity)element$iv;
                    if (!(!it.isDead())) continue;
                    bl = true;
                    break block3;
                }
                bl = false;
            }
        }
        return bl;
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public void onAbility() {
        int toSpawn;
        int n;
        int n2;
        Boss boss = this.boss;
        if (boss == null) {
            throw new TypeCastException("null cannot be cast to non-null type me.ifamasssxd.cosmicbosses.dungeonboss.destroyedoutpost.MotherOfYijki");
        }
        boolean otherDimension = ((MotherOfYijki)boss).inDifferentDimension;
        if (!otherDimension) {
            Iterable $receiver$iv = this.locationLists;
            for (Object element$iv : $receiver$iv) {
                Location loc = (Location)element$iv;
                this.spawnBlaze(loc);
            }
        }
        if ((n2 = 0) <= (n = (toSpawn = otherDimension ? 1 : ThreadLocalRandom.current().nextInt(2) + 1))) {
            while (true) {
                void i;
                Block block;
                Location nearbyLocation;
                LivingEntity livingEntity = ((MotherOfYijki)this.boss).entity;
                Intrinsics.checkExpressionValueIsNotNull((Object)livingEntity, (String)"boss.entity");
                Location location = nearbyLocation = LocationUtils.getNearbyLocation((Location)livingEntity.getLocation(), (int)1, (int)2);
                Intrinsics.checkExpressionValueIsNotNull((Object)location, (String)"nearbyLocation");
                Block block2 = block = location.getBlock();
                Intrinsics.checkExpressionValueIsNotNull((Object)block2, (String)"block");
                if (block2.isEmpty()) {
                    Location location2 = block.getLocation();
                    Intrinsics.checkExpressionValueIsNotNull((Object)location2, (String)"block.location");
                    this.spawnBlaze(location2);
                }
                if (i == n) break;
                ++i;
            }
        }
    }

    private final void spawnBlaze(Location location) {
        EntityEquipment equipment;
        org.bukkit.World world = location.getWorld();
        if (world == null) {
            throw new TypeCastException("null cannot be cast to non-null type org.bukkit.craftbukkit.v1_7_R4.CraftWorld");
        }
        WorldServer world2 = ((CraftWorld)world).getHandle();
        Boss boss = this.boss;
        if (boss == null) {
            throw new TypeCastException("null cannot be cast to non-null type me.ifamasssxd.cosmicbosses.dungeonboss.destroyedoutpost.MotherOfYijki");
        }
        HealingBlazeEntity healingEntity = new HealingBlazeEntity((MotherOfYijki)boss, (World)world2);
        healingEntity.setPosition(location.getX(), location.getY(), location.getZ());
        world2.addEntity((Entity)healingEntity, CreatureSpawnEvent.SpawnReason.CUSTOM);
        CraftEntity craftEntity = healingEntity.getBukkitEntity();
        if (craftEntity == null) {
            throw new TypeCastException("null cannot be cast to non-null type org.bukkit.entity.LivingEntity");
        }
        LivingEntity livingEntity = (LivingEntity)craftEntity;
        livingEntity.setHealth(livingEntity.getMaxHealth());
        livingEntity.setCustomName(CC.AquaB + "Mothers Fallen Angel");
        livingEntity.setCustomNameVisible(true);
        Plugin plugin = (Plugin)CosmicBosses.get();
        LivingEntity livingEntity2 = ((MotherOfYijki)this.boss).entity;
        Intrinsics.checkExpressionValueIsNotNull((Object)livingEntity2, (String)"boss.entity");
        livingEntity.setMetadata("minion", (MetadataValue)new FixedMetadataValue(plugin, (Object)livingEntity2.getUniqueId()));
        EntityEquipment entityEquipment = equipment = livingEntity.getEquipment();
        Intrinsics.checkExpressionValueIsNotNull((Object)entityEquipment, (String)"equipment");
        entityEquipment.setChestplate(new ItemBuilder(Material.DIAMOND_CHESTPLATE).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 5).enchant(Enchantment.THORNS, 2).build());
        equipment.setChestplateDropChance(0.0f);
        this.getEntities().add((org.bukkit.entity.Entity)livingEntity);
    }

    public MinionsAbility(@NotNull MotherOfYijki boss) {
        Intrinsics.checkParameterIsNotNull((Object)boss, (String)"boss");
        super((Boss)boss);
        Set set = Collections.newSetFromMap(new WeakHashMap());
        Intrinsics.checkExpressionValueIsNotNull(set, (String)"Collections.newSetFromMap(WeakHashMap())");
        this.entitySet = set;
        Object[] arrobject = new Location[3];
        Location location = boss.location;
        Intrinsics.checkExpressionValueIsNotNull((Object)location, (String)"boss.location");
        arrobject[0] = new Location(location.getWorld(), -100.0, 82.0, -656.0);
        Location location2 = boss.location;
        Intrinsics.checkExpressionValueIsNotNull((Object)location2, (String)"boss.location");
        arrobject[1] = new Location(location2.getWorld(), -94.0, 82.0, -628.0);
        Location location3 = boss.location;
        Intrinsics.checkExpressionValueIsNotNull((Object)location3, (String)"boss.location");
        arrobject[2] = new Location(location3.getWorld(), -66.0, 82.0, -663.0);
        this.locationLists = CollectionsKt.listOf((Object[])arrobject);
    }
}

