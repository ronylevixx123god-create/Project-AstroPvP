/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.cosmicpvp.cosmicutils.utils.ParticleEffect
 *  org.bukkit.Bukkit
 *  org.bukkit.Location
 *  org.bukkit.Sound
 *  org.bukkit.entity.Blaze
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.entity.SmallFireball
 *  org.bukkit.event.Event
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.event.entity.EntityDamageEvent
 *  org.bukkit.event.entity.EntityDeathEvent
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 */
package me.ifamasssxd.cosmicbosses.dungeonboss.destroyedoutpost;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.ParticleEffect;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import me.ifamasssxd.cosmicbosses.CosmicBosses;
import me.ifamasssxd.cosmicbosses.bosses.yijki.Yijki;
import me.ifamasssxd.cosmicbosses.dungeonboss.DungeonAbility;
import me.ifamasssxd.cosmicbosses.dungeonboss.DungeonYijki;
import me.ifamasssxd.cosmicbosses.dungeonboss.abilities.AfterImage;
import me.ifamasssxd.cosmicbosses.dungeonboss.abilities.ChainReaction;
import me.ifamasssxd.cosmicbosses.dungeonboss.abilities.EndOfWorld;
import me.ifamasssxd.cosmicbosses.dungeonboss.abilities.TemporalRift;
import me.ifamasssxd.cosmicbosses.dungeonboss.abilities.UnstoppableForce;
import me.ifamasssxd.cosmicbosses.dungeonboss.destroyedoutpost.DimensionTravelAbility;
import me.ifamasssxd.cosmicbosses.dungeonboss.destroyedoutpost.MinionsAbility;
import me.ifamasssxd.cosmicbosses.dungeonboss.destroyedoutpost.MotherOfYijkiEntity;
import me.ifamasssxd.cosmicbosses.entities.CustomEntity;
import me.ifamasssxd.cosmicbosses.struct.Boss;
import me.ifamasssxd.cosmicbosses.struct.BossMessage;
import me.ifamasssxd.cosmicbosses.struct.Phase;
import me.ifamasssxd.cosmicbosses.struct.enums.BossType;
import me.ifamasssxd.cosmicbosses.utils.BossUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Blaze;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.SmallFireball;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

public class MotherOfYijki
extends DungeonYijki {
    private final double DIMENSIONAL_SHIFT_HP_GAP = 20.0;
    private long lastDimensionalShift;
    private long lastSpawnedMinions;
    public boolean inDifferentDimension = false;
    private Location originalSpawn;
    private double healthBeforeTeleporting = this.getMaxHealth();
    private MinionsAbility minionsAbility;
    private double lastDimensionalShiftHealth = 100.0;
    private boolean inMainWorld;

    public MotherOfYijki(Location location, BossType bossType) {
        super(location, bossType);
        this.originalSpawn = location;
        this.minionsAbility = new MinionsAbility(this);
        this.lastSpawnedMinions = System.currentTimeMillis() - 5000L;
        this.inMainWorld = location.getWorld().equals(Bukkit.getWorlds().get(0));
    }

    @Override
    public BossType getBossType() {
        return BossType.MOTHER_OF_YIJKI;
    }

    @Override
    public DungeonYijki.BossPhase getBossPhase() {
        return super.getBossPhase();
    }

    public void teleportBackToSpawn() {
        this.teleportBossInstance(this.originalSpawn, false);
        if (this.getEntity().getHealth() < this.healthBeforeTeleporting) {
            this.getEntity().setHealth(this.healthBeforeTeleporting);
        }
        if (this.minionsAbility != null) {
            this.minionsAbility.clearEntities();
        }
    }

    public void teleportBossInstance(Location newLocation, boolean different) {
        this.inDifferentDimension = different;
        this.healthBeforeTeleporting = this.getEntity().getHealth();
        this.lastDimensionalShiftHealth = this.getHealthPercent();
        this.location = newLocation;
        this.getEntity().teleport(this.location);
        if (!different) {
            this.lastDimensionalShift = System.currentTimeMillis();
        }
    }

    @Override
    public void findNewAbility() {
        List<Class<? extends DungeonAbility>> available = this.bossPhase.getAbilityClasses();
        if (this.activeAbility != null) {
            available.remove(this.activeAbility.getClass());
        }
        if (this.lastPhaseClass != null) {
            available.remove(this.lastPhaseClass);
        }
        if (this.inDifferentDimension) {
            available.remove(AfterImage.class);
        }
        available.remove(EndOfWorld.class);
        double healthPercent = this.getHealthPercent();
        if (healthPercent > 90.0) {
            available.remove(UnstoppableForce.class);
        }
        if (this.inDifferentDimension || this.lastDimensionalShift != 0L && System.currentTimeMillis() - this.lastDimensionalShift <= TimeUnit.SECONDS.toMillis(10L)) {
            available.remove(DimensionTravelAbility.class);
        }
        if (healthPercent < 50.0 && Math.random() <= 0.25) {
            available.remove(UnstoppableForce.class);
        }
        this.activateAbility(available.get(ThreadLocalRandom.current().nextInt(available.size())));
    }

    public void activateAbility(Class<? extends DungeonAbility> abilityFound) {
        try {
            DungeonAbility object;
            Constructor<? extends DungeonAbility> constr = abilityFound.getDeclaredConstructor(Boss.class);
            this.activeAbility = object = constr.newInstance(this);
            this.activeAbility.onAbilityEnable();
            this.lastPhaseClass = this.activeAbility.getClass();
            this.abilityActivateTime = System.currentTimeMillis();
            Bukkit.getLogger().info("Activating ability: " + this.activeAbility.getClass().getName());
            this.updateScoreboard();
        }
        catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    public LivingEntity spawnBoss() {
        MotherOfYijkiEntity boss = (MotherOfYijkiEntity)BossUtils.createCustomEntity(CustomEntity.DUNGEON_MOTHER_OF_YIJKI, this, this.location);
        this.bossEntity = boss;
        boss.setBoss(this);
        LivingEntity e = (LivingEntity)boss.getBukkitEntity();
        this.uuid = e.getUniqueId();
        e.setMetadata("do_not_clear", (MetadataValue)new FixedMetadataValue((Plugin)CosmicBosses.getInstance(), (Object)""));
        e.setMetadata("boss", (MetadataValue)new FixedMetadataValue((Plugin)CosmicBosses.getInstance(), (Object)""));
        e.setMaxHealth((double)this.getMaxHealth());
        e.setHealth(e.getMaxHealth());
        this.setEnt(e);
        e.setRemoveWhenFarAway(false);
        e.setCustomName(this.getColor(true) + this.getCustomName());
        e.setCustomNameVisible(true);
        this.entity = e;
        bosses.put(e.getUniqueId(), this);
        BossMessage msg = this.getStartingMessage();
        if (msg != null) {
            this.sendMessage(msg.getMessage());
        }
        this.setPhase(this.getRandomPhase());
        return e;
    }

    @Override
    public void onUpdate() {
        if (this.getEntity().getLocation().getY() <= 70.0 && !this.inDifferentDimension && !this.inMainWorld) {
            this.getAntiFarm().unstickBoss();
        }
        if (this.inDifferentDimension && this.getEntity().getHealth() < this.healthBeforeTeleporting) {
            this.getEntity().setHealth(this.healthBeforeTeleporting);
            Bukkit.getLogger().info("[CosmicBosses] Setting MotherOfYijki health to " + this.healthBeforeTeleporting + " since it wasnt >=!");
        }
        ++this.loops;
        if (this.loops % this.getSeconds(this.getPhase() == Phase.FIVE ? 25 : 45) == 0 && !this.inDifferentDimension) {
            Phase next = this.getRandomPhase();
            this.onPhaseChange(next);
            this.lastPhaseSwitch = System.currentTimeMillis();
        }
        if (this.loops % this.getSeconds(1) == 0) {
            this.updateScoreboard();
        }
        if (!(System.currentTimeMillis() - this.lastSpawnedMinions < 100000L || this.minionsAbility.getEntities().size() >= 5 || this.inDifferentDimension || this.lastDimensionalShift != 0L && System.currentTimeMillis() - this.lastDimensionalShift < TimeUnit.SECONDS.toMillis(90L))) {
            this.getEntity().getWorld().getPlayers().forEach(pl -> this.sendMessage((Player)pl, "Go my children! Siphon from the life force of these pitiful creatures for your mother - heal me!"));
            this.lastSpawnedMinions = System.currentTimeMillis();
            this.minionsAbility.onAbility();
        }
    }

    @Override
    public void onDamageTaken(EntityDamageEvent event) {
        if (this.inDifferentDimension) {
            event.setCancelled(true);
            return;
        }
        super.onDamageTaken(event);
        if (this.inMainWorld) {
            return;
        }
        double newHealth = this.getEntity().getHealth();
        double newHealthPercent = this.getHealthPercent(newHealth - event.getFinalDamage());
        if (newHealth - event.getFinalDamage() <= 0.0 || newHealthPercent <= 0.0) {
            return;
        }
        double healthDifferenceSinceLast = this.lastDimensionalShiftHealth - newHealthPercent;
        Bukkit.getLogger().info("New Health: " + newHealth + " Percent: " + newHealthPercent + " heal difference: " + healthDifferenceSinceLast);
        if (healthDifferenceSinceLast >= 20.0 && !this.inDifferentDimension && (this.lastDimensionalShift == 0L || System.currentTimeMillis() - this.lastDimensionalShift >= TimeUnit.SECONDS.toMillis(10L))) {
            Bukkit.getLogger().info("Activating Dimension Rift due to health difference: " + newHealthPercent + " from: " + this.lastDimensionalShiftHealth);
            this.lastDimensionalShiftHealth = newHealthPercent;
            Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)CosmicBosses.getInstance(), () -> {
                this.clearAbilities();
                this.activateAbility(DimensionTravelAbility.class);
            }, 1L);
        }
    }

    @Override
    protected Phase getRandomPhase() {
        List available = this.phases.stream().filter(ph -> ph != this.getPhase()).collect(Collectors.toList());
        if (this.entity == null) {
            available.remove((Object)Phase.ONE);
            available.remove((Object)Phase.THREE);
        } else {
            double healthPercent = this.getHealthPercent();
            if (healthPercent > 50.0) {
                available.remove((Object)Yijki.WitherPhase.BOW.getPhase());
            }
            if (healthPercent > 25.0) {
                available.remove((Object)Yijki.WitherPhase.NONE.getPhase());
            }
        }
        available.remove((Object)Yijki.WitherPhase.MIRROR.getPhase());
        available.remove((Object)Yijki.WitherPhase.POTIONS.getPhase());
        return (Phase)((Object)available.get(this.random.nextInt(available.size())));
    }

    @Override
    public void onDamageTaken(EntityDamageByEntityEvent event, Entity damager) {
        if (this.inDifferentDimension) {
            event.setCancelled(true);
            if (damager instanceof Player) {
                ((Player)damager).sendMessage(CC.RedB + "(!) " + CC.Red + "You must escape this dimension before you can damage the Mother of Yijki!");
            }
            return;
        }
        super.onDamageTaken(event, damager);
    }

    @Override
    public String getCustomName() {
        return CC.LightPurpleB + "Mother Of Yijki";
    }

    @Override
    public int getShootDelay() {
        return this.activeAbility instanceof TemporalRift ? 10 : (int)(this.activeAbility instanceof ChainReaction ? 10.0 : (double)((int)this.bossPhase.getFireRateMultiplier()) * 0.9);
    }

    @Override
    public void onMinionUpdate(Entity minion, Event e) {
        super.onMinionUpdate(minion, e);
        if (minion instanceof Blaze && e instanceof EntityDamageByEntityEvent) {
            EntityDamageByEntityEvent event = (EntityDamageByEntityEvent)e;
            if (event.getDamager() instanceof SmallFireball) {
                if (event.getEntity() instanceof Player) {
                    event.setDamage(event.getDamage() * 2.4);
                } else {
                    event.setCancelled(true);
                }
            } else if (event.getEntity().equals((Object)minion) && event.getDamager() instanceof Player) {
                ParticleEffect.sendToLocation((ParticleEffect)ParticleEffect.LARGE_EXPLODE, (Location)event.getEntity().getLocation(), (float)1.0f, (float)1.0f, (float)1.0f, (float)0.01f, (int)15);
                for (Entity ent : event.getEntity().getNearbyEntities(8.0, 8.0, 8.0)) {
                    if (!(ent instanceof Player)) continue;
                    BossUtils.pushAwayPlayer(minion, (Player)ent, (double)0.6f);
                }
            }
        }
    }

    @Override
    public void onDeath(EntityDeathEvent event) {
        super.onDeath(event);
        if (event != null && event.getEntity() != null) {
            event.getEntity().getWorld().playSound(event.getEntity().getLocation(), Sound.ENDERDRAGON_DEATH, 10.0f, 1.1f);
        }
    }

    @Override
    public void cleanup() {
        if (!this.getLocation().getWorld().getName().toLowerCase().startsWith("dungeon")) {
            this.modifier.replaceAll();
            Bukkit.getLogger().info("Removing all blocks modified by Dungeon Yijki due to not being in dungeon world!");
        } else {
            Bukkit.getLogger().info("Skipping block replacement from Dungeon Yijki due to being in dungeon world!");
        }
        this.removeMinions();
    }

    @Override
    public String getName() {
        return this.getCustomName();
    }

    public boolean isInDifferentDimension() {
        return this.inDifferentDimension;
    }

    public void setLastDimensionalShiftHealth(double lastDimensionalShiftHealth) {
        this.lastDimensionalShiftHealth = lastDimensionalShiftHealth;
    }

    public double getLastDimensionalShiftHealth() {
        return this.lastDimensionalShiftHealth;
    }
}

