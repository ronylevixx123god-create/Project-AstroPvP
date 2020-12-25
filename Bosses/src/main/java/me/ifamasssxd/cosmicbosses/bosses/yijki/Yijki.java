/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.CosmicSpaceChests.CosmicSpaceChestsAPI
 *  com.cosmicpvp.CosmicSpaceChests.libs.ItemRarity
 *  com.google.common.collect.Lists
 *  org.arkhamnetwork.Arkkit.libs.ParticleEffect
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.enchantments.Enchantment
 *  org.bukkit.entity.Arrow
 *  org.bukkit.entity.Creature
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.EntityType
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.entity.Skeleton
 *  org.bukkit.entity.Skeleton$SkeletonType
 *  org.bukkit.entity.ThrownPotion
 *  org.bukkit.entity.Wither
 *  org.bukkit.entity.WitherSkull
 *  org.bukkit.event.Event
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.event.entity.EntityDamageEvent
 *  org.bukkit.event.entity.EntityDamageEvent$DamageCause
 *  org.bukkit.event.entity.EntityDeathEvent
 *  org.bukkit.event.entity.EntityTargetEvent
 *  org.bukkit.event.entity.ProjectileLaunchEvent
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.potion.PotionEffect
 *  org.bukkit.potion.PotionEffectType
 *  org.bukkit.util.Vector
 */
package me.ifamasssxd.cosmicbosses.bosses.yijki;

import com.cosmicpvp.CosmicSpaceChests.CosmicSpaceChestsAPI;
import com.cosmicpvp.CosmicSpaceChests.libs.ItemRarity;
import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import me.ifamasssxd.cosmicbosses.CosmicBosses;
import me.ifamasssxd.cosmicbosses.commands.CommandBoss;
import me.ifamasssxd.cosmicbosses.entities.CustomEntity;
import me.ifamasssxd.cosmicbosses.struct.*;
import me.ifamasssxd.cosmicbosses.struct.enums.BossType;
import me.ifamasssxd.cosmicbosses.struct.enums.DamageType;
import me.ifamasssxd.cosmicbosses.struct.enums.Difficulty;
import me.ifamasssxd.cosmicbosses.struct.enums.WeaponType;
import me.ifamasssxd.cosmicbosses.utils.BossUtils;
import me.ifamasssxd.cosmicbosses.utils.ItemBuilder;
import org.arkhamnetwork.Arkkit.libs.ParticleEffect;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.entity.Wither;
import org.bukkit.entity.WitherSkull;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class Yijki extends Boss implements GlobalBoss {
    Cage cage;
    long lastPhaseSwitch = 0L;
    private Phase lastPhase = this.getPhase();
    List<Phase> phases = Lists.newArrayList(Phase.ONE, Phase.TWO, Phase.THREE, Phase.FOUR, Phase.FIVE, Phase.SIX);
    private List<Wither> clones = Lists.newArrayList();

    public Yijki(Location location, BossType bossType) {
        super(location, bossType);
        this.modifier = new BlockModifier();
        this.cage = new Cage(this, this.getModifier());
        this.getLootPool().add(CosmicSpaceChestsAPI.getDropPackageChest((ItemRarity)ItemRarity.LEGENDARY));
        this.getLootPool().add(CosmicSpaceChestsAPI.getDropPackageChest((ItemRarity)ItemRarity.LEGENDARY));
        this.getLootPool().add(CosmicSpaceChestsAPI.getDropPackageChest((ItemRarity)ItemRarity.LEGENDARY));
        this.getLootPool().add(CosmicSpaceChestsAPI.getDropPackageChest((ItemRarity)ItemRarity.LEGENDARY));
        this.getLootPool().add(CosmicSpaceChestsAPI.getDropPackageChest((ItemRarity)ItemRarity.LEGENDARY));
        this.getLootPool().add(CosmicSpaceChestsAPI.getDropPackageChest((ItemRarity)ItemRarity.LEGENDARY));
        this.getLootPool().add(CosmicSpaceChestsAPI.getDropPackageChest((ItemRarity)ItemRarity.LEGENDARY));
        this.getLootPool().add(CosmicSpaceChestsAPI.getDropPackageChest((ItemRarity)ItemRarity.LEGENDARY));
    }

    @Override
    public String getTitle() {
        return "Yijki";
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.WITHER;
    }

    @Override
    public LivingEntity spawnBoss() {
        WitherBoss boss = (WitherBoss)BossUtils.createCustomEntity(CustomEntity.WITHER, this, this.location);
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
        return e;
    }

    @Override
    public String getCustomName() {
        return "Yijki, World Destroyer";
    }

    @Override
    public String getName() {
        return "Yijki, World Destroyer";
    }

    @Override
    public void onUpdate() {
        double distance;
        ++this.loops;
        if (this.loops % 5 == 0 && this.getAntiFarm().checkStuck()) {
            this.getAntiFarm().unstickBoss();
        }
        if (this.loops % this.getSeconds(30) == 0) {
            Phase next = this.getRandomPhase();
            this.lastPhase = this.getPhase();
            this.onPhaseChange(next);
            this.lastPhaseSwitch = System.currentTimeMillis();
        }
        if (this.loops % this.getSeconds(5) == 0 && this.getMinions().size() < 20) {
            int toSpawn = this.random.nextInt(3) + 2;
            for (int i = 0; i < toSpawn && this.getMinions().size() < 10; ++i) {
                this.spawnMinion();
            }
        }
        if (this.cage.canUse() && System.currentTimeMillis() - this.lastPhaseSwitch >= 5000L && this.getEntity().getTicksLived() >= 200) {
            this.cage.onAbility();
            this.sendMessage((Object)ChatColor.ITALIC + "You are dead. I have already killed you. You have just not yet witnessed it for yourself.");
            for (int i = 0; i < this.random.nextInt(3) + 3 && this.getMinions().size() < 20; ++i) {
                this.spawnMinion();
            }
            for (Entity ent : this.getMinions()) {
                if (!(ent instanceof Skeleton)) continue;
                ent.setMetadata("berserk", (MetadataValue)new FixedMetadataValue((Plugin)CosmicBosses.getInstance(), (Object)(System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(10L))));
            }
        }
        for (Wither wither : this.clones) {
            Creature boss;
            if (!wither.isValid() || wither.getWorld() != this.getEntity().getWorld()) continue;
            distance = wither.getLocation().distanceSquared(this.getEntity().getLocation());
            if (distance > 800.0) {
                wither.teleport(this.getEntity().getLocation());
                continue;
            }
            if (wither.getTarget() != null || (boss = (Creature)this.getEntity()).getTarget() == null || !(boss.getTarget() instanceof Player)) continue;
            wither.setTarget(boss.getTarget());
        }
        Iterator<Entity> iter = this.getMinions().iterator();
        while (iter.hasNext()) {
            Creature wither;
            LivingEntity target;
            Entity ent = iter.next();
            if (ent == null || ent.isDead()) {
                iter.remove();
                continue;
            }
            distance = ent.getLocation().distanceSquared(this.getEntity().getLocation());
            if ((ent.getWorld() != this.getEntity().getWorld() || distance > 750.0) && (target = (wither = (Creature)this.getEntity()).getTarget()) != null && target instanceof Player && target.getWorld() == wither.getWorld() && target.getLocation().distanceSquared(wither.getLocation()) < 1000.0 && BossUtils.canAffectPlayer((Player)target, true)) {
                ent.teleport(BossUtils.getRandomLocationNearby(wither.getLocation(), 3, 2, 1));
            }
            if (!ent.hasMetadata("berserk") || this.loops % this.getSeconds(1) != 0 || this.random.nextInt(10) != 0) continue;
            long expire = ((MetadataValue)ent.getMetadata("berserk").get(0)).asLong();
            if (expire < System.currentTimeMillis()) {
                ent.removeMetadata("berserk", (Plugin)CosmicBosses.getInstance());
                return;
            }
            ParticleEffect.sendToLocation((ParticleEffect)ParticleEffect.LARGE_SMOKE, (Location)ent.getLocation(), (float)1.0f, (float)1.0f, (float)1.0f, (float)0.1f, (int)10);
        }
        if (this.loops % this.getSeconds(4) == 0) {
            this.updateScoreboard();
        }
        super.onUpdate();
    }

    private Skeleton spawnMinion() {
        Skeleton wither = (Skeleton)this.spawnMinion(EntityType.SKELETON, BossUtils.getRandomLocationNearby(this.getEntity().getLocation(), 10, 4, 2));
        wither.setSkeletonType(Skeleton.SkeletonType.WITHER);
        wither.setMetadata("no_drops", (MetadataValue)new FixedMetadataValue((Plugin)CosmicBosses.getInstance(), (Object)""));
        wither.setMaxHealth(55.0);
        wither.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 2));
        wither.setHealth(wither.getMaxHealth());
        wither.getEquipment().setItemInHand(ItemBuilder.buildArmor(Material.DIAMOND_SWORD, Enchantment.DAMAGE_ALL, Enchantment.FIRE_ASPECT));
        wither.getEquipment().setItemInHandDropChance(0.0f);
        if (this.random.nextInt(100) <= 60) {
            if (this.random.nextBoolean()) {
                wither.getEquipment().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
            }
            if (this.random.nextBoolean()) {
                wither.getEquipment().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
            }
            if (this.random.nextBoolean()) {
                wither.getEquipment().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
            }
            if (this.random.nextBoolean()) {
                wither.getEquipment().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
            }
        }
        return wither;
    }

    private Phase getRandomPhase() {
        List available = this.phases.stream().filter(ph -> ph != this.lastPhase).collect(Collectors.toList());
        return (Phase)((Object)available.get(this.random.nextInt(available.size())));
    }

    @Override
    public void onMinionUpdate(Entity minion, Event e) {
        EntityTargetEvent event;
        if (e instanceof EntityDamageByEntityEvent) {
            EntityDamageByEntityEvent event2 = (EntityDamageByEntityEvent)e;
            if (event2.getDamager().equals((Object)minion) && !event2.isCancelled()) {
                event2.getEntity().setFireTicks(60);
                if (event2.getEntity() instanceof Player) {
                    Player pl = (Player)event2.getEntity();
                    if (minion.getType() == EntityType.WITHER) {
                        if (BossUtils.canAffectPlayer(pl, true)) {
                            double maxHealth = this.getMaxHealth();
                            int bossMissingHealth = (int)((double)this.getMaxHealth() / this.getEntity().getHealth());
                            double percentHealthLost = (double)bossMissingHealth / maxHealth * 100.0;
                            int extraDamage = (int)Math.ceil(percentHealthLost / 5.0);
                            int percentDamage = Math.min(25, 5 + extraDamage);
                            if (this.random.nextInt(100) < 25) {
                                percentDamage += 5;
                            }
                            BossUtils.dealPercentMaxHealthDamage(pl, percentDamage, 20);
                            pl.playSound(pl.getLocation(), Sound.ZOMBIE_WOODBREAK, 3.0f, 1.3f);
                            if (pl.isSprinting() && this.random.nextInt(100) < 20) {
                                pl.setSprinting(false);
                                pl.playSound(pl.getLocation(), Sound.WITHER_HURT, 1.0f, 1.4f);
                            }
                        }
                    } else {
                        pl.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 100, 1));
                        if (this.random.nextInt(10) <= 7) {
                            pl.removePotionEffect(PotionEffectType.ABSORPTION);
                            pl.removePotionEffect(PotionEffectType.REGENERATION);
                        }
                        if (minion.hasMetadata("berserk")) {
                            long expire = ((MetadataValue)minion.getMetadata("berserk").get(0)).asLong();
                            if (expire < System.currentTimeMillis()) {
                                minion.removeMetadata("berserk", (Plugin)CosmicBosses.getInstance());
                                return;
                            }
                            event2.setDamage(0.0);
                            BossUtils.dealPercentMaxHealthDamage(pl, 20.0, 30);
                        }
                    }
                }
            } else if (event2.getDamager() instanceof WitherSkull || event2.getDamager().getType() == EntityType.UNKNOWN) {
                event2.setCancelled(true);
            }
        } else if (e instanceof EntityDamageEvent) {
            EntityDamageEvent event3 = (EntityDamageEvent)e;
            if (event3.getCause() == EntityDamageEvent.DamageCause.FALL || event3.getCause() == EntityDamageEvent.DamageCause.FIRE || event3.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION || event3.getCause() == EntityDamageEvent.DamageCause.WITHER) {
                event3.setCancelled(true);
            }
        } else if (e instanceof EntityTargetEvent && (event = (EntityTargetEvent)e).getEntity() instanceof Wither && !(event.getTarget() instanceof Player)) {
            event.setCancelled(true);
            event.setTarget(null);
            Wither wither = (Wither)event.getEntity();
            Creature boss = (Creature)this.getEntity();
            if (boss.getTarget() != null) {
                wither.setTarget(boss.getTarget());
            }
        }
    }

    @Override
    public void onPhaseChange(Phase newPhase) {
        if (newPhase.getPhase() > 6) {
            newPhase = Phase.ONE;
        }
        this.setPhase(newPhase);
        String tip = "";
        WitherPhase ph = WitherPhase.getPhase(newPhase);
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
        if (ph == WitherPhase.MIRROR) {
            this.cleanupClones();
            Location spawn = this.getEntity().getLocation();
            for (int i = 0; i < 2; ++i) {
                Wither wither = (Wither)spawn.getWorld().spawnEntity(spawn, EntityType.WITHER);
                this.clones.add(wither);
                wither.setCustomName(ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + "Yijki, World Destroyer");
                wither.setMetadata("minion", (MetadataValue)new FixedMetadataValue((Plugin)CosmicBosses.getInstance(), (Object)this.getUuid()));
                wither.setMaxHealth((double)(this.getMaxHealth() / 5));
                wither.setHealth(wither.getMaxHealth());
                Creature boss = (Creature)this.getEntity();
                if (boss.getTarget() == null) continue;
                wither.setTarget(boss.getTarget());
            }
        } else {
            this.cleanupClones();
        }
        this.updateScoreboard();
        if (ph != WitherPhase.NONE) {
            this.sendMessage((Object)ChatColor.ITALIC + "I have reprogrammed my defenses against your attacks. You can no longer harm me as you have before.");
        }
    }

    @Override
    public void cleanup() {
        this.removeMinions();
        this.cleanupClones();
        this.getModifier().removeAllBlocks(true);
        this.clearScoreboard();
    }

    private void cleanupClones() {
        if (this.clones.size() > 0) {
            this.clones.forEach(Entity::remove);
            this.clones.clear();
        }
    }

    @Override
    public void onAttackPlayer(EntityDamageByEntityEvent event, Player player) {
        if (BossUtils.canAffectPlayer(player, true)) {
            double maxHealth = this.getMaxHealth();
            int bossMissingHealth = (int)((double)this.getMaxHealth() / this.getEntity().getHealth());
            double percentHealthLost = (double)bossMissingHealth / maxHealth * 100.0;
            int extraDamage = (int)Math.ceil(percentHealthLost / 5.0);
            int percentDamage = Math.min(25, 5 + extraDamage);
            if (this.random.nextInt(100) < 40) {
                percentDamage += 5;
            }
            BossUtils.dealPercentMaxHealthDamage(player, percentDamage, 25);
            player.playSound(player.getLocation(), Sound.ZOMBIE_WOODBREAK, 3.0f, 1.3f);
            if (player.isSprinting() && this.random.nextInt(100) < 20) {
                player.setSprinting(false);
                player.playSound(player.getLocation(), Sound.WITHER_HURT, 1.0f, 1.4f);
            }
            if (this.random.nextInt(100) <= 60) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 2));
            }
            if (player.isOnGround()) {
                player.setVelocity(new Vector(0, 1, 0));
            }
        }
        super.onAttackPlayer(event, player);
    }

    @Override
    public void onDamageTaken(EntityDamageByEntityEvent event, Entity damager) {
        if (damager instanceof Player || damager instanceof Arrow) {
            Player p = damager instanceof Arrow ? (Player)((Arrow)damager).getShooter() : (Player)damager;
            WitherPhase phase = null;
            if (this.getPhase() == Phase.ONE || this.getPhase() == Phase.SIX) {
                if (p.getItemInHand() != null && !p.getItemInHand().getType().name().endsWith("_SWORD")) {
                    phase = WitherPhase.SWORDS;
                }
            } else if (this.getPhase() == Phase.TWO) {
                if (!(event.getDamager() instanceof Arrow)) {
                    phase = WitherPhase.BOW;
                }
            } else if (this.getPhase() == Phase.THREE) {
                if (p.getItemInHand() != null && !p.getItemInHand().getType().name().endsWith("_AXE")) {
                    phase = WitherPhase.AXE;
                }
            } else if (this.getPhase() == Phase.FOUR) {
                if (!(event.getEntity() instanceof ThrownPotion)) {
                    phase = WitherPhase.POTIONS;
                }
            } else if (this.getPhase() == Phase.FIVE) {
                phase = WitherPhase.NONE;
            }
            if (phase != null) {
                event.setCancelled(true);
                event.setDamage(0.0);
                p.sendMessage(String.format(phase.getMessage(), this.getColor(true) + this.getName()));
                p.playSound(p.getLocation(), Sound.ITEM_BREAK, 1.0f, 1.1f);
                return;
            }
        }
        event.setDamage(event.getDamage() / 3.0);
        double missingHealth = (double)this.getMaxHealth() - this.getEntity().getHealth();
        double damageReduction = missingHealth / (double)this.getMaxHealth();
        double newDamage = event.getDamage() - event.getDamage() * damageReduction;
        event.setDamage(newDamage);
        this.getEntity().setCustomName(this.getColor(true) + this.getCustomName());
        super.onDamageTaken(event, damager);
    }

    @Override
    public void onDamageTaken(EntityDamageEvent event) {
        if (event.getCause() == EntityDamageEvent.DamageCause.SUFFOCATION) {
            event.setCancelled(true);
            if (this.getAntiFarm().checkStuck()) {
                this.getAntiFarm().unstickBoss();
            }
        }
        if (event.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION) {
            event.setCancelled(true);
        }
        super.onDamageTaken(event);
    }

    @Override
    public void onDeath(EntityDeathEvent event) {
        super.onDeath(event);
        if (event != null) {
            this.sendMessage((Object)ChatColor.ITALIC + "I-Impossible...", Sound.ENDERDRAGON_GROWL, 0.8f);
            if (Math.random() <= 0.1) {
                event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), CommandBoss.getDimensionalCore());
                Bukkit.getLogger().info("Dropping Interdimensional Core!");
            }
        }
        CosmicBosses.getInstance().getBlockChangeManager().restoreAllBlocks(this.getEntity().getEntityId());
    }

    @Override
    public List<BossMessage> getGeneralMessages() {
        return Lists.newArrayList(new BossMessage("There is no stopping me. No defeating me. You cannot win.", false, 0), new BossMessage("I am Yijki, destroyer of worlds. I'm afraid your time has come.", false, -1), new BossMessage("Your fate is inevitable, give up now.", false, 0), new BossMessage("Feel the power of absolute darkness.", false, 0));
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
        return 2000;
    }

    @Override
    public void onMiscUpdate(Event event) {
        Creature wither;
        if (event instanceof ProjectileLaunchEvent && (wither = (Creature)this.getEntity()).getTarget() != null && !(wither.getTarget() instanceof Player)) {
            wither.setTarget(null);
            ((ProjectileLaunchEvent)event).setCancelled(true);
        }
    }

    public static enum WitherPhase {
        SWORDS(Phase.ONE, "Sword", WeaponType.SWORD),
        BOW(Phase.TWO, "Bow", WeaponType.BOW),
        AXE(Phase.THREE, "Axe", WeaponType.AXE),
        POTIONS(Phase.FOUR, "Potion", null),
        NONE(Phase.FIVE, "Invincible", null, (Object)ChatColor.RED + "Nothing can damage this creature!"),
        MIRROR(Phase.SIX, "Mirror", WeaponType.SWORD, (Object)ChatColor.RED + "Clones have been spawned!");

        Phase phase;
        String phaseName;
        WeaponType type;
        String msg;

        private WitherPhase(Phase phase, String name, WeaponType type) {
            this.phase = phase;
            this.phaseName = name;
            this.type = type;
        }

        private WitherPhase(Phase phase, String name, WeaponType type, String msg) {
            this.phase = phase;
            this.phaseName = name;
            this.type = type;
            this.msg = msg;
        }

        public String getMessage() {
            if (this.msg != null) {
                return this.msg;
            }
            return (Object)ChatColor.RED + "You must use a " + (Object)ChatColor.RED + ChatColor.BOLD.toString() + this.getPhaseName().toUpperCase() + (Object)ChatColor.RED + " to damage %s!";
        }

        public static WitherPhase getPhase(Phase phase) {
            for (WitherPhase ph : WitherPhase.values()) {
                if (!ph.getPhase().equals((Object)phase)) continue;
                return ph;
            }
            return WitherPhase.values()[ThreadLocalRandom.current().nextInt(WitherPhase.values().length)];
        }

        public Phase getPhase() {
            return this.phase;
        }

        public String getPhaseName() {
            return this.phaseName;
        }

        public WeaponType getType() {
            return this.type;
        }

        public String getMsg() {
            return this.msg;
        }
    }
}

