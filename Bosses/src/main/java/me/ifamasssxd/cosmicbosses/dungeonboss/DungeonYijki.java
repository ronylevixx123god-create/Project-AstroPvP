/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.google.common.collect.Lists
 *  net.minecraft.server.v1_7_R4.Entity
 *  net.minecraft.server.v1_7_R4.World
 *  org.arkhamnetwork.Arkkit.libs.ParticleEffect
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Effect
 *  org.bukkit.Location
 *  org.bukkit.Sound
 *  org.bukkit.craftbukkit.v1_7_R4.CraftWorld
 *  org.bukkit.entity.Arrow
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.EntityType
 *  org.bukkit.entity.FallingBlock
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.entity.Projectile
 *  org.bukkit.entity.ThrownPotion
 *  org.bukkit.entity.WitherSkull
 *  org.bukkit.event.Event
 *  org.bukkit.event.entity.CreatureSpawnEvent$SpawnReason
 *  org.bukkit.event.entity.EntityChangeBlockEvent
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.event.entity.EntityDamageEvent
 *  org.bukkit.event.entity.EntityDeathEvent
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.potion.PotionEffect
 *  org.bukkit.potion.PotionEffectType
 */
package me.ifamasssxd.cosmicbosses.dungeonboss;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.google.common.collect.Lists;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import me.ifamasssxd.cosmicbosses.CosmicBosses;
import me.ifamasssxd.cosmicbosses.bosses.yijki.Yijki;
import me.ifamasssxd.cosmicbosses.dungeonboss.DungeonAbility;
import me.ifamasssxd.cosmicbosses.dungeonboss.DungeonBoss;
import me.ifamasssxd.cosmicbosses.dungeonboss.DungeonYijkiEntity;
import me.ifamasssxd.cosmicbosses.dungeonboss.DungeonYijkiImage;
import me.ifamasssxd.cosmicbosses.dungeonboss.abilities.AfterImage;
import me.ifamasssxd.cosmicbosses.dungeonboss.abilities.AntiWither;
import me.ifamasssxd.cosmicbosses.dungeonboss.abilities.ChainReaction;
import me.ifamasssxd.cosmicbosses.dungeonboss.abilities.DeathCurse;
import me.ifamasssxd.cosmicbosses.dungeonboss.abilities.EndOfWorld;
import me.ifamasssxd.cosmicbosses.dungeonboss.abilities.TemporalRift;
import me.ifamasssxd.cosmicbosses.dungeonboss.abilities.UnstoppableForce;
import me.ifamasssxd.cosmicbosses.entities.CustomEntity;
import me.ifamasssxd.cosmicbosses.struct.BlockModifier;
import me.ifamasssxd.cosmicbosses.struct.Boss;
import me.ifamasssxd.cosmicbosses.struct.BossMessage;
import me.ifamasssxd.cosmicbosses.struct.Phase;
import me.ifamasssxd.cosmicbosses.struct.PreBossDeathEvent;
import me.ifamasssxd.cosmicbosses.struct.enums.BossType;
import me.ifamasssxd.cosmicbosses.struct.enums.DamageType;
import me.ifamasssxd.cosmicbosses.struct.enums.Difficulty;
import me.ifamasssxd.cosmicbosses.utils.BossUtils;
import me.ifamasssxd.cosmicbosses.utils.WorldGuardUtils;
import net.minecraft.server.v1_7_R4.Entity;
import net.minecraft.server.v1_7_R4.World;
import org.arkhamnetwork.Arkkit.libs.ParticleEffect;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.entity.WitherSkull;
import org.bukkit.event.Event;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class DungeonYijki
extends Boss
implements DungeonBoss {
    protected Phase lastPhase;
    protected long lastPhaseSwitch;
    protected BossPhase bossPhase = BossPhase.PHASE_2;
    protected DungeonYijkiEntity bossEntity;
    protected DungeonAbility activeAbility;
    private EndOfWorld endOfWorldAbility;
    protected Class<? extends DungeonAbility> lastPhaseClass;
    protected long abilityActivateTime = System.currentTimeMillis();
    private List<Location> spawnLocations = Lists.newArrayList();
    protected List<Phase> phases = Lists.newArrayList((Object[])new Phase[]{Phase.ONE, Phase.TWO, Phase.THREE, Phase.FOUR, Phase.FIVE, Phase.SIX});

    public DungeonYijki(Location location, BossType bossType) {
        super(location, bossType);
        this.modifier = new BlockModifier();
        this.spawnLocations.add(location);
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.WITHER;
    }

    @Override
    public String getCustomName() {
        return "Mother of Yijki";
    }

    @Override
    public String getName() {
        return "Mother of Yijki";
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

    public Location getRandomSpawnLocation() {
        if (this.spawnLocations != null && this.spawnLocations.size() > 0) {
            Location random;
            this.location = random = this.spawnLocations.get(ThreadLocalRandom.current().nextInt(this.spawnLocations.size()));
            return random;
        }
        return super.getLocation();
    }

    @Override
    public void onUpdate() {
        ++this.loops;
        if (this.loops % this.getSeconds(this.getPhase() == Phase.FIVE ? 28 : 45) == 0) {
            Phase next = this.getRandomPhase();
            this.onPhaseChange(next);
            this.lastPhaseSwitch = System.currentTimeMillis();
        }
        if (this.loops % this.getSeconds(1) == 0) {
            this.updateScoreboard();
        }
    }

    public void setBossPhase(BossPhase phase) {
        this.bossPhase = phase;
        this.getEntity().setMaxHealth((double)phase.getMaxHealth());
        this.getEntity().setHealth((double)phase.getMaxHealth());
        this.getEntity().addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 36000, 1, true));
    }

    public void clearAbilities() {
        if (this.activeAbility != null) {
            this.activeAbility.onAbilityDisable();
            this.activeAbility = null;
        }
    }

    @Override
    public void onAttackPlayer(EntityDamageByEntityEvent event, Player player) {
        if (this.bossPhase != null && event.getEntity() instanceof Projectile) {
            event.setDamage((double)this.bossPhase.getProjectileDamage());
        }
        if (event.getEntity() instanceof WitherSkull && event.getEntity() instanceof Player) {
            Player pl = (Player)event.getEntity();
            pl.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 20, 0));
        }
        super.onAttackPlayer(event, player);
    }

    public List<Player> getWorldPlayers() {
        return this.getEntity().getWorld().getPlayers();
    }

    @Override
    public void onDamageTaken(EntityDamageEvent event) {
        super.onDamageTaken(event);
        if (!event.isCancelled() && event.getFinalDamage() >= this.getEntity().getHealth()) {
            Bukkit.getPluginManager().callEvent((Event)new PreBossDeathEvent((org.bukkit.entity.Entity)this.getEntity(), this, event));
        }
    }

    @Override
    public void onDeath(EntityDeathEvent event) {
        super.onDeath(event);
        if (event != null && event.getEntity() != null) {
            ParticleEffect.sendToLocation((ParticleEffect)ParticleEffect.LARGE_SMOKE, (Location)event.getEntity().getLocation(), (float)1.5f, (float)1.5f, (float)1.5f, (float)0.01f, (int)30);
        }
    }

    @Override
    public void onDamageTaken(EntityDamageByEntityEvent event, org.bukkit.entity.Entity damager) {
        Player p;
        if (!(damager instanceof Player) && !(damager instanceof Arrow)) {
            event.setCancelled(true);
            return;
        }
        if (this.bossPhase != null) {
            event.setDamage((double)this.bossPhase.getDamageReduction() * event.getDamage());
        }
        Player player = p = damager instanceof Arrow ? (Player)((Arrow)damager).getShooter() : (Player)damager;
        if (this.activeAbility instanceof AfterImage && ((AfterImage)this.activeAbility).isAnyImagesAlive()) {
            event.setCancelled(true);
            event.setDamage(0.0);
            p.sendMessage(CC.RedB + "(!) " + CC.Red + "You must kill all After Images before you can deal more damage!");
            p.playSound(p.getLocation(), Sound.ITEM_BREAK, 1.0f, 1.1f);
            return;
        }
        Yijki.WitherPhase phase = null;
        if (this.getPhase() == Phase.TWO) {
            if (!(event.getDamager() instanceof Arrow)) {
                phase = Yijki.WitherPhase.BOW;
            }
        } else if (this.getPhase() == Phase.ONE || this.getPhase() == Phase.SIX) {
            if (event.getDamager() instanceof Projectile || p.getItemInHand() != null && !p.getItemInHand().getType().name().endsWith("_SWORD")) {
                phase = Yijki.WitherPhase.SWORDS;
            }
        } else if (this.getPhase() == Phase.THREE) {
            if (event.getDamager() instanceof Projectile || p.getItemInHand() != null && !p.getItemInHand().getType().name().endsWith("_AXE")) {
                phase = Yijki.WitherPhase.AXE;
            }
        } else if (this.getPhase() == Phase.FOUR) {
            if (!(event.getEntity() instanceof ThrownPotion)) {
                phase = Yijki.WitherPhase.POTIONS;
            }
        } else if (this.getPhase() == Phase.FIVE) {
            phase = Yijki.WitherPhase.NONE;
        }
        if (phase != null) {
            event.setCancelled(true);
            event.setDamage(0.0);
            p.sendMessage(String.format(phase.getMessage(), this.getColor(true) + this.getName()));
            p.playSound(p.getLocation(), Sound.ITEM_BREAK, 1.0f, 1.1f);
            return;
        }
        if (p.getWorld().getName().endsWith("yijki_a")) {
            if (!WorldGuardUtils.isInRegionsFast(p.getLocation(), "bridge", "hanger")) {
                event.setCancelled(true);
                p.sendMessage(CC.RedB + "(!) " + CC.Red + "You cannot damage Yijki outside of the boss room!");
                return;
            }
        }
        if (this.getEntity().getHealth() - event.getFinalDamage() <= 250.0 && this.endOfWorldAbility == null && this.bossPhase == BossPhase.PHASE_3) {
            if (this.getEntity().getHealth() <= event.getFinalDamage()) {
                event.setCancelled(true);
            }
            Bukkit.getLogger().info("Triggering EndOfWorld ability due to health being " + this.getEntity().getHealth() + " and damage being " + event.getFinalDamage());
            this.endOfWorldAbility = new EndOfWorld(this);
            this.endOfWorldAbility.onAbilityEnable();
        }
        super.onDamageTaken(event, damager);
        if (!event.isCancelled() && event.getFinalDamage() >= this.getEntity().getHealth()) {
            Bukkit.getPluginManager().callEvent((Event)new PreBossDeathEvent((org.bukkit.entity.Entity)this.getEntity(), this, (EntityDamageEvent)event));
        }
    }

    @Override
    public org.bukkit.entity.Entity spawnMinion(EntityType type, Location l) {
        org.bukkit.entity.Entity ent;
        if (type == EntityType.WITHER) {
            CraftWorld craftWorld = (CraftWorld)l.getWorld();
            DungeonYijkiImage image = new DungeonYijkiImage((World)craftWorld.getHandle(), this);
            image.setPosition(l.getX(), l.getY(), l.getZ());
            craftWorld.getHandle().addEntity((Entity)image, CreatureSpawnEvent.SpawnReason.CUSTOM);
            ent = image.getBukkitEntity();
        } else {
            ent = l.getWorld().spawnEntity(l, type);
        }
        ent.setMetadata("minion", (MetadataValue)new FixedMetadataValue((Plugin)CosmicBosses.getInstance(), (Object)this.getEntity().getUniqueId()));
        ent.setMetadata("do_not_clear", (MetadataValue)new FixedMetadataValue((Plugin)CosmicBosses.getInstance(), (Object)""));
        this.getMinions().add(ent);
        if (ent instanceof LivingEntity) {
            LivingEntity li = (LivingEntity)ent;
            li.setRemoveWhenFarAway(false);
            li.setCustomName(this.getColor(true) + this.getName() + "'s Minion");
            li.setCustomNameVisible(true);
            if (li.getEquipment() != null) {
                li.getEquipment().setItemInHandDropChance(0.0f);
                li.getEquipment().setHelmetDropChance(0.0f);
                li.getEquipment().setChestplateDropChance(0.0f);
                li.getEquipment().setLeggingsDropChance(0.0f);
                li.getEquipment().setBootsDropChance(0.0f);
            }
        }
        return ent;
    }

    @Override
    public LivingEntity spawnBoss() {
        DungeonYijkiEntity boss;
        this.bossEntity = boss = (DungeonYijkiEntity)BossUtils.createCustomEntity(CustomEntity.DUNGEON_YIJKI, this, this.location);
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
    public void onEntityChangeBlock(EntityChangeBlockEvent event, org.bukkit.entity.Entity boss) {
        if (event.getEntity().hasMetadata("chainReactionBlock")) {
            FallingBlock block = (FallingBlock)event.getEntity();
            event.setCancelled(true);
            if (ThreadLocalRandom.current().nextBoolean()) {
                event.getEntity().getWorld().playEffect(event.getEntity().getLocation(), Effect.STEP_SOUND, block.getMaterial().getId());
            }
        }
    }

    @Override
    public void setPhase(Phase phase) {
        this.lastPhase = this.getPhase();
        super.setPhase(phase);
    }

    @Override
    public void onPhaseChange(Phase newPhase) {
        if (newPhase.getPhase() > 6) {
            newPhase = Phase.ONE;
        }
        this.setPhase(newPhase);
        String tip = "";
        Yijki.WitherPhase ph = Yijki.WitherPhase.getPhase(newPhase);
        this.sendMessage((Object)ChatColor.YELLOW + ChatColor.BOLD.toString() + ph.getPhaseName() + " Phase");
        if (newPhase != Phase.SIX) {
            tip = (Object)ChatColor.YELLOW + "IMMUNE to " + (Object)ChatColor.YELLOW + ChatColor.BOLD.toString() + "ALL" + (Object)ChatColor.YELLOW + " damage except for " + (Object)ChatColor.YELLOW + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString();
            switch (ph) {
                case SWORDS: {
                    tip = tip + "SWORDS";
                    break;
                }
                case AXE: {
                    tip = tip + "AXES";
                    break;
                }
                case BOW: {
                    tip = tip + "BOWS";
                    break;
                }
                case POTIONS: {
                    tip = tip + "POTION";
                    break;
                }
                case NONE: {
                    tip = (Object)ChatColor.YELLOW + ChatColor.BOLD.toString() + "INVINCIBLE" + (Object)ChatColor.YELLOW + " to " + (Object)ChatColor.YELLOW + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "ALL" + (Object)ChatColor.YELLOW + " damage!";
                    break;
                }
            }
            this.sendMessage(this.getColor(false) + tip, Sound.WITHER_IDLE, 0.8f);
        }
        this.updateScoreboard();
        if (ph != Yijki.WitherPhase.NONE) {
            this.sendMessage((Object)ChatColor.ITALIC + "I have reprogrammed my defenses against your attacks. You can no longer harm me as you have before.");
        }
    }

    public int getShootDelay() {
        return this.activeAbility != null && this.activeAbility instanceof TemporalRift ? 13 : (this.activeAbility != null && this.activeAbility instanceof ChainReaction ? 12 : (int)this.bossPhase.getFireRateMultiplier());
    }

    protected double getHealthPercent(double health) {
        if (this.getEntity() == null || !this.getEntity().isValid()) {
            return 0.0;
        }
        return health / this.getEntity().getMaxHealth() * 100.0;
    }

    public void findNewAbility() {
        List<Class<? extends DungeonAbility>> available = this.bossPhase.getAbilityClasses();
        if (this.activeAbility != null) {
            available.remove(this.activeAbility.getClass());
        }
        if (this.lastPhaseClass != null) {
            available.remove(this.lastPhaseClass);
        }
        if (this.getHealthPercent() > 90.0) {
            available.remove(UnstoppableForce.class);
        }
        Class<? extends DungeonAbility> abilityFound = available.get(ThreadLocalRandom.current().nextInt(available.size()));
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

    protected Phase getRandomPhase() {
        List available = this.phases.stream().filter(ph -> ph != this.getPhase()).collect(Collectors.toList());
        if (this.entity == null) {
            available.remove((Object)Phase.ONE);
            available.remove((Object)Phase.THREE);
        } else {
            double healthPercent = this.getHealthPercent();
            if (healthPercent > 35.0) {
                available.remove((Object)Phase.ONE);
            }
            if (healthPercent > 50.0) {
                available.remove((Object)Phase.THREE);
            }
            if (healthPercent >= 70.0) {
                if (ThreadLocalRandom.current().nextBoolean() && !this.getPhase().equals((Object)Phase.THREE) && !available.contains((Object)Phase.THREE)) {
                    available.add(Phase.THREE);
                } else if (ThreadLocalRandom.current().nextInt(3) <= 1 && !this.getPhase().equals((Object)Phase.ONE) && !available.contains((Object)Phase.ONE)) {
                    available.add(Phase.ONE);
                }
            }
        }
        available.remove((Object)Phase.SIX);
        available.remove((Object)Phase.FOUR);
        return (Phase)((Object)available.get(this.random.nextInt(available.size())));
    }

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public void onMiscUpdate(Event event) {
        if (this.activeAbility != null) {
            this.activeAbility.onAbilityEvent(event);
        }
    }

    @Override
    public void onMinionUpdate(org.bukkit.entity.Entity minion, Event e) {
        if (this.activeAbility != null) {
            this.activeAbility.onAbilityEvent(e);
        }
    }

    @Override
    public DamageType getDamageType() {
        return DamageType.WITHER;
    }

    @Override
    public Difficulty getDifficulty() {
        return Difficulty.LEGENDARY_PLUS;
    }

    @Override
    public int getMaxHealth() {
        return this.bossPhase.getMaxHealth();
    }

    @Override
    public List<BossMessage> getGeneralMessages() {
        return Lists.newArrayList((Object[])new BossMessage[]{new BossMessage("Now you will truly meet death!", false, -1)});
    }

    @Override
    public Entity getNMSEntity() {
        return this.bossEntity;
    }

    public BossPhase getBossPhase() {
        return this.bossPhase;
    }

    public DungeonYijkiEntity getBossEntity() {
        return this.bossEntity;
    }

    public DungeonAbility getActiveAbility() {
        return this.activeAbility;
    }

    public EndOfWorld getEndOfWorldAbility() {
        return this.endOfWorldAbility;
    }

    public long getAbilityActivateTime() {
        return this.abilityActivateTime;
    }

    public List<Location> getSpawnLocations() {
        return this.spawnLocations;
    }

    public static enum BossPhase {
        PHASE_1(1000, 6, 0.25f, 15.0f, Lists.newArrayList((Object[])new Class[]{ChainReaction.class, TemporalRift.class, UnstoppableForce.class})),
        PHASE_2(1500, 10, 0.25f, 10.0f, Lists.newArrayList((Object[])new Class[]{AntiWither.class, AfterImage.class, UnstoppableForce.class, TemporalRift.class, ChainReaction.class})),
        PHASE_3(2000, 14, 0.1f, 6.0f, Lists.newArrayList((Object[])new Class[]{DeathCurse.class, EndOfWorld.class, AfterImage.class, UnstoppableForce.class, TemporalRift.class, ChainReaction.class}));

        private int maxHealth;
        private int projectileDamage;
        private float damageReduction;
        private float fireRateMultiplier;
        private List<Class<? extends DungeonAbility>> abilityClasses;

        public List<Class<? extends DungeonAbility>> getAbilityClasses() {
            return Lists.newArrayList(this.abilityClasses);
        }

        private BossPhase(int maxHealth, int projectileDamage, float damageReduction, float fireRateMultiplier, List<Class<? extends DungeonAbility>> abilityClasses) {
            this.maxHealth = maxHealth;
            this.projectileDamage = projectileDamage;
            this.damageReduction = damageReduction;
            this.fireRateMultiplier = fireRateMultiplier;
            this.abilityClasses = abilityClasses;
        }

        public int getMaxHealth() {
            return this.maxHealth;
        }

        public int getProjectileDamage() {
            return this.projectileDamage;
        }

        public float getDamageReduction() {
            return this.damageReduction;
        }

        public float getFireRateMultiplier() {
            return this.fireRateMultiplier;
        }
    }
}

