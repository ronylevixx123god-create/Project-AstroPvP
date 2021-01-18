package me.ifamasssxd.cosmicbosses.bosses.skeletonhorse;

import com.cosmicpvp.CosmicSpaceChests.CosmicSpaceChestsAPI;
import com.cosmicpvp.CosmicSpaceChests.libs.ItemRarity;
import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;
import me.ifamasssxd.cosmicbosses.CosmicBosses;
import me.ifamasssxd.cosmicbosses.entities.CustomEntity;
import me.ifamasssxd.cosmicbosses.struct.Boss;
import me.ifamasssxd.cosmicbosses.struct.BossMessage;
import me.ifamasssxd.cosmicbosses.struct.Phase;
import me.ifamasssxd.cosmicbosses.struct.enums.BossType;
import me.ifamasssxd.cosmicbosses.struct.enums.DamageType;
import me.ifamasssxd.cosmicbosses.struct.enums.Difficulty;
import me.ifamasssxd.cosmicbosses.utils.BossUtils;
import me.ifamasssxd.cosmicbosses.utils.CustomItem;
import me.ifamasssxd.cosmicbosses.utils.ItemBuilder;
import net.minecraft.server.v1_8_R3.EntityHorse;
import net.minecraft.server.v1_8_R3.EntitySkeleton;
import org.arkhamnetwork.Arkkit.libs.ParticleEffect;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftHorse;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftSkeleton;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class UndeadAssassin extends Boss {
    Horse horse;

    EmpoweredArrow empoweredArrow = new EmpoweredArrow(this);

    Summoning summoning = new Summoning(this);

    public boolean invincible = false;

    int hits;

    long lastHit;

    long timeSinceInvincible;

    Entity lastTarget;

    int storedHealth;

    public UndeadAssassin(Location location, BossType bossType) {
        super(location, bossType);
        this.hits = 0;
        this.lastHit = System.currentTimeMillis();
        this.timeSinceInvincible = System.currentTimeMillis();
        this.storedHealth = getMaxHealth();
        getLootPool().add(CosmicSpaceChestsAPI.getDropPackageChest(ItemRarity.LEGENDARY));
        getLootPool().add(CosmicSpaceChestsAPI.getDropPackageChest(ItemRarity.LEGENDARY));
        getLootPool().add(CosmicSpaceChestsAPI.getDropPackageChest(ItemRarity.LEGENDARY));
        getLootPool().add(CosmicSpaceChestsAPI.getDropPackageChest(ItemRarity.ULTIMATE));
        getLootPool().add(CosmicSpaceChestsAPI.getDropPackageChest(ItemRarity.ULTIMATE));
    }

    public String getTitle() {
        return "Assassin";
    }

    public EntityType getEntityType() {
        return EntityType.SKELETON;
    }

    public LivingEntity spawnBoss() {
        LivingEntity e = (LivingEntity)BossUtils.createCustomEntity(CustomEntity.SKELETON_BOSS, this.location);
        this.uuid = e.getUniqueId();
        e.setMetadata("do_not_clear", (MetadataValue)new FixedMetadataValue((Plugin)CosmicBosses.getInstance(), ""));
        e.setMetadata("boss", (MetadataValue)new FixedMetadataValue((Plugin)CosmicBosses.getInstance(), ""));
        e.setMaxHealth(getMaxHealth());
        e.setHealth(e.getMaxHealth());
        setEnt(e);
        e.setCustomName(getDifficulty().getColor() + ChatColor.BOLD.toString() + getCustomName());
        e.setCustomNameVisible(true);
        e.setRemoveWhenFarAway(false);
        this.entity = e;
        bosses.put(e.getUniqueId(), this);
        BossMessage msg = getStartingMessage();
        if (msg != null)
            sendMessage(ChatColor.ITALIC.toString() + msg.getMessage());
        return e;
    }

    public void onBossSpawn() {
        LivingEntity skeleton = spawnBoss();
        skeleton.getEquipment().setItemInHand(ItemBuilder.buildArmor(Material.BOW, Enchantment.ARROW_DAMAGE, Enchantment.ARROW_FIRE));
        skeleton.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 2147483647, 2));
        setHelmet((new CustomItem(new ItemStack(Material.IRON_HELMET))).setUnbreakable(true).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3).build());
        setChestplate((new CustomItem(new ItemStack(Material.IRON_CHESTPLATE))).setUnbreakable(true).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).build());
        setLeggings((new CustomItem(new ItemStack(Material.IRON_LEGGINGS))).setUnbreakable(true).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3).addEnchant(Enchantment.THORNS, 1).build());
        setBoots((new CustomItem(new ItemStack(Material.IRON_BOOTS))).setUnbreakable(true).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).build());
        this.horse = (Horse)spawnMinion(EntityType.HORSE, skeleton.getLocation());
        this.horse.setMetadata("horse", (MetadataValue)new FixedMetadataValue((Plugin)CosmicBosses.getInstance(), ""));
        this.horse.setMaxHealth(getMaxHealth());
        this.horse.setHealth(this.horse.getMaxHealth());
        this.horse.setAdult();
        this.horse.setVariant(Horse.Variant.SKELETON_HORSE);
        this.horse.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 2147483647, 1));
        this.horse.setDomestication(this.horse.getMaxDomestication());
        this.horse.setPassenger((Entity)skeleton);
        alert();
    }

    public String getCustomName() {
        return "Undead Assassin";
    }

    public String getName() {
        return "Undead Assassin";
    }

    public void onUpdate() {
        this.loops++;
        LivingEntity entity = getEntity();
        if (this.invincible &&
                this.random.nextInt(10) <= 2) {
            ParticleEffect.sendToLocation(ParticleEffect.LAVA, entity.getLocation(), 1.0F, 1.0F, 1.0F, 0.05F, 50);
            ParticleEffect.sendToLocation(ParticleEffect.PORTAL, entity.getLocation(), 1.0F, 1.0F, 1.0F, 0.1F, 30);
        }
        if (this.loops % 12 == 0) {
            if (!this.invincible && getAntiFarm().checkStuck())
                getAntiFarm().unstickBoss();
            if (!entity.hasPotionEffect(PotionEffectType.SPEED))
                entity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 2147483647, 2));
        }
        if (this.loops % 4 == 0 && this.invincible &&
                entity.getHealth() < this.storedHealth)
            entity.setHealth(this.storedHealth);
        if (!this.invincible && getPhase() == Phase.TWO && this.summoning.canUse() && (this.hits >= 10 || (System.currentTimeMillis() - this.timeSinceInvincible) >= this.summoning
                .getCooldown() * 0.45D)) {
            this.hits = 0;
            this.timeSinceInvincible = System.currentTimeMillis();
            this.storedHealth = (int)entity.getHealth();
            sendMessage(ChatColor.ITALIC + "Rise my minions!");
            sendMessage(ChatColor.ITALIC + "Have you not figured it out? I. AM. ALREADY. DEAD. Now come, join me in the underworld.");
            sendMessage(ChatColor.WHITE + "You must defeat the minions to damage the boss!");
            this.summoning.onAbility();
        }
        if (getPhase() == Phase.ONE && this.empoweredArrow.canUse())
            this.empoweredArrow.onAbility();
        if (this.horse != null && this.horse.isValid()) {
            if (getEntity().getHealth() < getEntity().getMaxHealth() * 0.75D) {
                this.horse.damage(2.147483647E9D);
            } else {
                if (this.horse.getPassenger() == null)
                    this.horse.setPassenger((Entity)getEntity());
                Creature boss = (Creature)getEntity();
                if (boss.getTarget() != null)
                    if (boss.getTarget().getWorld() == boss.getWorld()) {
                        double distanceSquared = this.horse.getLocation().distanceSquared(boss.getTarget().getLocation());
                        if (distanceSquared > 100.0D) {
                            EntityHorse entityHorse = ((CraftHorse)this.horse).getHandle();
                            Location targetLoc = boss.getTarget().getLocation();
                            entityHorse.getNavigation().a(targetLoc.getX(), targetLoc.getY(), targetLoc.getZ(), 2.0D);
                        } else if (distanceSquared < 5.0D) {
                            EntityHorse entityHorse = ((CraftHorse)this.horse).getHandle();
                            Location targetLoc = BossUtils.getRandomLocationNearby(boss.getTarget().getLocation(), 7, 4, 0);
                            entityHorse.getNavigation().a(targetLoc.getX(), targetLoc.getY(), targetLoc.getZ(), 2.1D);
                        }
                    }
            }
        } else if ((this.horse == null || this.horse.isDead()) &&
                getPhase() != Phase.TWO) {
            onPhaseChange(Phase.TWO);
            this.horse = null;
        }
        for (Iterator<Entity> ent = getMinions().iterator(); ent.hasNext(); ) {
            Entity e = ent.next();
            if (e == null || !e.isValid()) {
                ent.remove();
                continue;
            }
            if (e.getLocation().distanceSquared(getEntity().getLocation()) >= 1200.0D)
                e.teleport(getEntity().getLocation().clone().add(0.0D, 15.0D, 0.0D));
        }
        if (this.invincible && getMinions().size() <= 0)
            removeInvincibility();
        if (this.loops % 16 == 0)
            updateScoreboard();
        if (!this.invincible && this.loops % 2 * 20 == 0) {
            Creature boss = (Creature)getEntity();
            if (!boss.isInsideVehicle() && boss.getTarget() != null && boss.getTarget().isValid() && boss.getTarget() instanceof Player && boss.getTarget()
                    .getWorld().equals(getEntity().getWorld())) {
                this.lastTarget = (Entity)boss.getTarget();
                double distance = this.lastTarget.getLocation().distanceSquared(getEntity().getLocation());
                if (this.lastTarget.isValid() && distance <= 200.0D && distance > 60.0D) {
                    EntitySkeleton entitySkeleton = ((CraftSkeleton)getEntity()).getHandle();
                    Entity entity1 = (Entity) ((CraftEntity)this.lastTarget).getHandle();
                    entitySkeleton.getNavigation().a((net.minecraft.server.v1_8_R3.Entity) entity1, 2.0D);
                }
            } else if (this.lastTarget != null) {
                double distance = getLocation().distanceSquared(getEntity().getLocation());
                if (this.lastTarget.getWorld().equals(getEntity().getWorld()) && !this.lastTarget.isDead() && getDamageTracker().containsKey(this.lastTarget.getUniqueId()) && distance <= 200.0D)
                    boss.setTarget((LivingEntity)this.lastTarget);
            }
        }
        super.onUpdate();
    }

    public void onAttackPlayer(EntityDamageByEntityEvent event, Player player) {
        if (getPhase() == Phase.TWO) {
            event.setDamage(event.getDamage() * 3.0D);
            player.setFireTicks(600);
            if (BossUtils.canAffectPlayer(player, true))
                BossUtils.dealPercentMaxHealthDamage(player, 10.0D, 10);
        }
        if (event.getDamager().hasMetadata("boss_empowered_arrow")) {
            Entity arrow = event.getDamager();
            event.getDamager().removeMetadata("boss_empowered_arrow", (Plugin)CosmicBosses.getInstance());
            arrow.getWorld().playSound(arrow.getLocation(), Sound.EXPLODE, 10.0F, 1.1F);
            if (arrow.getPassenger() != null)
                arrow.getPassenger().remove();
            player.setFireTicks(200);
            if (BossUtils.canAffectPlayer(player, true))
                BossUtils.dealPercentMaxHealthDamage(player, 40.0D, 10);
            if (this.random.nextInt(10) == 0) {
                ParticleEffect.sendToLocation(ParticleEffect.LARGE_SMOKE, arrow.getLocation(), 3.0F, 3.0F, 3.0F, 0.1F, 50);
                ParticleEffect.sendToLocation(ParticleEffect.FLAME, arrow.getLocation(), 3.0F, 3.0F, 3.0F, 0.1F, 15);
            }
            for (Entity ent : player.getNearbyEntities(4.0D, 4.0D, 4.0D)) {
                if (ent instanceof Player) {
                    Player pl = (Player)ent;
                    if (BossUtils.canAffectPlayer(pl, true)) {
                        BossUtils.dealPercentHealthDamage(pl, 10.0D, 10);
                        pl.setFireTicks(100);
                    }
                }
            }
        } else if (event.getDamager() instanceof org.bukkit.entity.Arrow) {
            event.setDamage(event.getDamage() * 5.0D);
        }
        super.onAttackPlayer(event, player);
    }

    public void onDamageTaken(EntityDamageByEntityEvent event, Entity damager) {
        if (event.getDamager() instanceof org.bukkit.entity.Projectile) {
            event.setCancelled(true);
            return;
        }
        if (this.invincible) {
            event.setCancelled(true);
            if (damager instanceof Player) {
                Player pl = (Player)damager;
                BossUtils.pushAwayPlayer((Entity)getEntity(), pl, 2.299999952316284D);
                sendMessage(ChatColor.WHITE + "You cannot kill what is already dead.");
            }
            getEntity().getWorld().playSound(getEntity().getLocation(), Sound.WITHER_IDLE, 3.0F, 1.8F);
            if (this.random.nextInt(10) <= 4)
                ParticleEffect.sendToLocation(ParticleEffect.EXPLODE, getEntity().getLocation(), 0.5F, 0.3F, 0.3F, 0.1F, 30);
            return;
        }
        if (System.currentTimeMillis() - this.lastHit <= 500L) {
            this.hits++;
        } else {
            this.hits = 1;
        }
        this.lastHit = System.currentTimeMillis();
        if (damager instanceof Player && event.getDamager() instanceof Player) {
            Player pl = (Player)damager;
            if (this.random.nextInt(100) <= 10) {
                if (BossUtils.canAffectPlayer(pl, true)) {
                    pl.setFireTicks(30);
                    BossUtils.pushAwayPlayer((Entity)getEntity(), pl, 10.0D);
                    if (this.random.nextInt(3) == 0)
                        pl.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 140, 3));
                    getEntity().getWorld().playSound(getEntity().getLocation(), Sound.WITHER_SHOOT, 4.0F, 0.8F);
                }
            } else if (getPhase() == Phase.TWO) {
                if (this.random.nextInt(10) <= 4) {
                    getEntity().teleport(BossUtils.getRandomLocationNearby(damager.getLocation(), 2, 1, 1));
                    getEntity().getWorld().playSound(getEntity().getLocation(), Sound.PIG_DEATH, 10.0F, 1.4F);
                    if (this.random.nextInt(3) > 1)
                        sendMessage(ChatColor.ITALIC + "Don't blink, you might die.");
                    ParticleEffect.sendToLocation(ParticleEffect.PORTAL, getEntity().getLocation(), 1.0F, 1.0F, 1.0F, 0.7F, 20);
                }
            }
        }
        super.onDamageTaken(event, damager);
    }

    public void onDamageTaken(EntityDamageEvent event) {
        if (event.getCause() == EntityDamageEvent.DamageCause.SUFFOCATION) {
            event.setCancelled(true);
            if (getAntiFarm().checkStuck())
                getAntiFarm().unstickBoss();
        }
        if (this.invincible)
            event.setCancelled(true);
        super.onDamageTaken(event);
    }

    public void onMinionUpdate(Entity minion, Event e) {
        if (minion.hasMetadata("horse")) {
            if (e instanceof EntityDeathEvent) {
                getEntity().leaveVehicle();
                getMinions().remove(minion);
                onPhaseChange(Phase.TWO);
            } else if (e instanceof EntityDamageByEntityEvent) {
                EntityDamageByEntityEvent event = (EntityDamageByEntityEvent)e;
                if (event.getDamager() instanceof Player)
                    onDamageTaken(event, event.getDamager());
            } else if (e instanceof EntityDamageEvent) {
                EntityDamageEvent event = (EntityDamageEvent)e;
                onDamageTaken(event);
            }
        } else if (e instanceof EntityDeathEvent) {
            getMinions().remove(minion);
            if (this.invincible && getMinions().size() == 0)
                removeInvincibility();
        } else if (e instanceof EntityDamageEvent) {
            EntityDamageEvent event = (EntityDamageEvent)e;
            if (event.getCause() == EntityDamageEvent.DamageCause.FIRE || event.getCause() == EntityDamageEvent.DamageCause.FIRE_TICK || event.getCause() == EntityDamageEvent.DamageCause.LAVA) {
                event.setCancelled(true);
                event.getEntity().setFireTicks(0);
            }
        }
    }

    public void removeMinion(Entity ent) {}

    public void removeInvincibility() {
        this.invincible = false;
        this.timeSinceInvincible = System.currentTimeMillis();
        this.summoning.activateCooldown();
        getEntity().removePotionEffect(PotionEffectType.INVISIBILITY);
    }

    public void onPhaseChange(Phase newPhase) {
        setPhase(newPhase);
        if (newPhase == Phase.TWO) {
            getEntity().removeMetadata("empower_arrow", (Plugin)CosmicBosses.getInstance());
            getEntity().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 2147483647, 2));
            setHelmet(ItemBuilder.buildArmor(Material.DIAMOND_HELMET, new Enchantment[] { Enchantment.PROTECTION_ENVIRONMENTAL }));
            setChestplate(ItemBuilder.buildArmor(Material.DIAMOND_CHESTPLATE, new Enchantment[] { Enchantment.THORNS }));
            setLeggings(ItemBuilder.buildArmor(Material.DIAMOND_LEGGINGS, new Enchantment[] { Enchantment.PROTECTION_FIRE }));
            setBoots(ItemBuilder.buildArmor(Material.DIAMOND_BOOTS, new Enchantment[] { Enchantment.PROTECTION_PROJECTILE }));
            ItemStack weapon = new ItemStack(Material.DIAMOND_SWORD, 1);
            weapon.addUnsafeEnchantment(Enchantment.DURABILITY, 100);
            weapon.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 6);
            weapon.addUnsafeEnchantment(Enchantment.FIRE_ASPECT, 1);
            getEntity().getEquipment().setItemInHandDropChance(0.0F);
            getEntity().getEquipment().setItemInHand(weapon);
            Location loc = getEntity().getLocation();
            loc.getWorld().playSound(loc, Sound.WITHER_SPAWN, 10.0F, 1.3F);
            Bukkit.getScheduler()
                    .scheduleSyncDelayedTask((Plugin)CosmicBosses.getInstance(), () -> loc.getWorld().playSound(loc, Sound.ENDERMAN_SCREAM, 10.0F, 1.8F), 40L);
            sendMessage(ChatColor.ITALIC + "So you killed my pet? Well guess what, now I'm going to KILL ALL OF YOU!", Sound.GHAST_MOAN, 1.3F);
        }
    }

    public void cleanup() {
        removeMinions();
        clearScoreboard();
    }

    public void onDeath(EntityDeathEvent event) {
        super.onDeath(event);
        if (event != null)
            sendMessage(ChatColor.ITALIC + "You should not have come here... you will never leave alive!");
    }

    public DamageType getDamageType() {
        return DamageType.FIRE;
    }

    public Difficulty getDifficulty() {
        return Difficulty.LEGENDARY;
    }

    public int getMaxHealth() {
        return 2000;
    }

    public void onMiscUpdate(Event event) {}

    public List<BossMessage> getGeneralMessages() {
        return Lists.newArrayList();
    }
}
