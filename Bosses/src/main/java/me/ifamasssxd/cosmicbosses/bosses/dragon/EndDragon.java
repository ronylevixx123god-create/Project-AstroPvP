/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.compilex.CosmicFund.CosmicFundAPI
 *  com.cosmicpvp.CosmicSpaceChests.CosmicSpaceChestsAPI
 *  com.cosmicpvp.CosmicSpaceChests.libs.ItemRarity
 *  com.cosmicpvp.cosmicutils.CosmicUtils
 *  com.cosmicpvp.cosmicutils.itembuilder.ArmorSetItemBuilder
 *  com.cosmicpvp.cosmicutils.itembuilder.HeroicUpgradeBuilder
 *  com.cosmicpvp.cosmicutils.itembuilder.MaskBuilder
 *  com.cosmicpvp.cosmicutils.nms.MinecraftServerUtils
 *  com.cosmicpvp.cosmicutils.struct.EquipmentType
 *  com.cosmicpvp.cosmicutils.struct.factions.points.FactionPointUtils
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.cosmicpvp.cosmicutils.utils.ItemBuilder
 *  com.cosmicpvp.cosmicutils.utils.ItemUtils
 *  com.cosmicpvp.cosmicutils.utils.JSONMessage
 *  com.cosmicpvp.cosmicutils.utils.ParticleEffect
 *  com.google.common.collect.Lists
 *  me.ifamasssxd.cosmicconquest.CosmicConquestAPI
 *  me.ifamasssxd.cosmicevolutionkits.structure.EvolutionKits
 *  me.ifamasssxd.cosmicevolutionkits.structure.ekits.Lucky
 *  net.minecraft.server.v1_7_R4.Entity
 *  net.minecraft.server.v1_7_R4.World
 *  net.minecraft.server.v1_7_R4.WorldServer
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.EntityEffect
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.craftbukkit.v1_7_R4.CraftWorld
 *  org.bukkit.enchantments.Enchantment
 *  org.bukkit.entity.Blaze
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.EntityType
 *  org.bukkit.entity.LargeFireball
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.entity.Projectile
 *  org.bukkit.entity.Skeleton
 *  org.bukkit.entity.Skeleton$SkeletonType
 *  org.bukkit.entity.TNTPrimed
 *  org.bukkit.event.Event
 *  org.bukkit.event.entity.CreatureSpawnEvent$SpawnReason
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.event.entity.EntityDamageEvent
 *  org.bukkit.event.entity.EntityDamageEvent$DamageCause
 *  org.bukkit.event.entity.EntityDeathEvent
 *  org.bukkit.event.entity.ProjectileHitEvent
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.potion.PotionEffect
 *  org.bukkit.potion.PotionEffectType
 *  org.bukkit.scheduler.BukkitRunnable
 */
package me.ifamasssxd.cosmicbosses.bosses.dragon;

import com.cosmicpvp.CosmicSpaceChests.CosmicSpaceChestsAPI;
import com.cosmicpvp.CosmicSpaceChests.libs.ItemRarity;
import com.cosmicpvp.cosmicutils.CosmicUtils;
import com.cosmicpvp.cosmicutils.itembuilder.ArmorSetItemBuilder;
import com.cosmicpvp.cosmicutils.itembuilder.HeroicUpgradeBuilder;
import com.cosmicpvp.cosmicutils.itembuilder.MaskBuilder;
import com.cosmicpvp.cosmicutils.nms.MinecraftServerUtils;
import com.cosmicpvp.cosmicutils.struct.EquipmentType;
import com.cosmicpvp.cosmicutils.struct.factions.points.FactionPointUtils;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.ItemBuilder;
import com.cosmicpvp.cosmicutils.utils.ItemUtils;
import com.cosmicpvp.cosmicutils.utils.JSONMessage;
import com.cosmicpvp.cosmicutils.utils.ParticleEffect;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import me.ifamasssxd.cosmicbosses.CosmicBosses;
import me.ifamasssxd.cosmicbosses.bosses.dragon.DragonFireball;
import me.ifamasssxd.cosmicbosses.bosses.dragon.DragonPhase;
import me.ifamasssxd.cosmicbosses.bosses.dragon.DragonTNTBomb;
import me.ifamasssxd.cosmicbosses.bosses.dragon.EndDragonEntity;
import me.ifamasssxd.cosmicbosses.bosses.dragon.phases.PhaseList;
import me.ifamasssxd.cosmicbosses.struct.BlockModifier;
import me.ifamasssxd.cosmicbosses.struct.Boss;
import me.ifamasssxd.cosmicbosses.struct.BossMessage;
import me.ifamasssxd.cosmicbosses.struct.GlobalBoss;
import me.ifamasssxd.cosmicbosses.struct.Phase;
import me.ifamasssxd.cosmicbosses.struct.enums.BossType;
import me.ifamasssxd.cosmicbosses.struct.enums.DamageType;
import me.ifamasssxd.cosmicbosses.struct.enums.Difficulty;
import me.ifamasssxd.cosmicbosses.utils.BossUtils;
import me.ifamasssxd.cosmicbosses.utils.StringUtils;
import me.ifamasssxd.cosmicconquest.CosmicConquestAPI;
import me.ifamasssxd.cosmicevolutionkits.structure.EvolutionKits;
import me.ifamasssxd.cosmicevolutionkits.structure.ekits.Lucky;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.World;
import net.minecraft.server.v1_8_R3.WorldServer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Blaze;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LargeFireball;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.Event;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class EndDragon
extends Boss
implements GlobalBoss {
    private EndDragonEntity dragon;
    private int endSpawnActivation;
    private int endViewDistance;
    private DragonTNTBomb bomb = new DragonTNTBomb(this);
    private DragonFireball fireball = new DragonFireball(this);
    private DragonPhase dragonPhase = DragonPhase.FIREBALL;
    private long lastPhaseChange;

    public EndDragon(Location location, BossType bossType) {
        super(location, bossType);
        this.modifier = new BlockModifier();
        this.getLootPool().add(CosmicSpaceChestsAPI.getDropPackageChest((ItemRarity)ItemRarity.GOD));
        this.getLootPool().add(CosmicSpaceChestsAPI.getDropPackageChest((ItemRarity)ItemRarity.GOD));
        this.getLootPool().add(CosmicSpaceChestsAPI.getDropPackageChest((ItemRarity)ItemRarity.GOD));
        this.getLootPool().add(CosmicSpaceChestsAPI.getDropPackageChest((ItemRarity)ItemRarity.GOD));
        this.getLootPool().add(CosmicSpaceChestsAPI.getDropPackageChest((ItemRarity)ItemRarity.GOD));
        this.getLootPool().add(CosmicSpaceChestsAPI.getDropPackageChest((ItemRarity)ItemRarity.GOD));
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.ENDER_DRAGON;
    }

    @Override
    public String getCustomName() {
        return CC.DarkRedB + "Timeless Dragon";
    }

    @Override
    public String getName() {
        return "Timeless Dragon";
    }

    @Override
    public void cleanup() {
        if (this.endSpawnActivation != 0) {
            ((CraftWorld)this.location.getWorld()).getHandle().spigotConfig.monsterActivationRange = this.endSpawnActivation;
        }
        if (this.endViewDistance != 0) {
            ((CraftWorld)this.location.getWorld()).getHandle().spigotConfig.viewDistance = this.endViewDistance;
        }
        this.removeMinions();
        this.clearScoreboard();
    }

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public LivingEntity spawnBoss() {
        WorldServer server = ((CraftWorld)this.location.getWorld()).getHandle();
        this.dragon = new EndDragonEntity((World)server, this);
        this.dragon.setPosition(this.location.getX(), this.location.getY(), this.location.getZ());
        this.dragon.getPhaseManager().setPhase(PhaseList.HOLDING_PATTERN);
        this.dragon.homeLocation = this.location.clone();
        this.dragon.homeLocation.setY((double)(BossUtils.getHighestBlockYAt(this.dragon.homeLocation, true) + 3));
        server.addEntity((Entity)this.dragon, CreatureSpawnEvent.SpawnReason.CUSTOM);
        LivingEntity e = (LivingEntity)this.dragon.getBukkitEntity();
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
        this.setPhase(this.getNewPhase());
        this.endSpawnActivation = server.spigotConfig.monsterActivationRange;
        this.endViewDistance = server.spigotConfig.viewDistance++;
        server.spigotConfig.monsterActivationRange *= 3;
        return e;
    }

    @Override
    public void onUpdate() {
        EndDragonEntity entity;
        ++this.loops;
        super.onUpdate();
        long timeSinceChange = System.currentTimeMillis() - this.lastPhaseChange;
        if (timeSinceChange >= 30000L) {
            this.onPhaseChange(this.getNewPhase());
        }
        if ((entity = this.getNMSEntity()) == null || !entity.isAlive()) {
            Bukkit.getLogger().info("EnderDragon Entity is not alive!");
            return;
        }
        if (entity.world.getChunkAtWorldCoordsIfLoaded((int)Math.floor(entity.locX), (int)Math.floor(entity.locZ)) == null) {
            return;
        }
        if (this.bomb.canUse()) {
            this.bomb.onAbility();
            this.bomb.activateCooldown();
        }
        if (this.fireball.canUse()) {
            this.fireball.onAbility();
        }
        if (this.loops % 16 == 0) {
            this.updateScoreboard();
        }
    }

    @Override
    public void onDamageTaken(EntityDamageByEntityEvent event, org.bukkit.entity.Entity damager) {
        if (!(damager instanceof Player)) {
            event.setCancelled(true);
            return;
        }
        super.onDamageTaken(event, damager);
    }

    @Override
    public void onDamageTaken(EntityDamageEvent event) {
        if (event.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION || event.getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION) {
            event.setCancelled(true);
            return;
        }
        event.setDamage(event.getDamage() / 3.0);
        super.onDamageTaken(event);
    }

    public EndDragonEntity getNMSEntity() {
        return this.dragon;
    }

    @Override
    public List<org.bukkit.entity.Entity> getMinions() {
        List<org.bukkit.entity.Entity> minions = super.getMinions();
        minions.removeIf(ent -> !ent.isValid());
        return minions;
    }

    public List<org.bukkit.entity.Entity> getMinions(EntityType type) {
        ArrayList found = Lists.newArrayList();
        List<org.bukkit.entity.Entity> minions = super.getMinions();
        minions.removeIf(ent -> !ent.isValid());
        for (org.bukkit.entity.Entity ent2 : minions) {
            if (ent2.getType() != type) continue;
            found.add(ent2);
        }
        return found;
    }

    protected Phase getNewPhase() {
        ArrayList available = Lists.newArrayList((Object[])DragonPhase.values());
        available.remove((Object)this.dragonPhase);
        return Phase.getPhase(((DragonPhase)((Object)available.get(this.random.nextInt(available.size())))).getPhase());
    }

    @Override
    public void onDeath(final EntityDeathEvent event) {
        List<ItemStack> items;
        ItemStack mask;
        super.onDeath(event);
        if (event != null && Math.random() <= 0.1 && (mask = new MaskBuilder("DRAGON_MASK").build()) != null) {
            Bukkit.getLogger().info("[EndDragon] Dropping dragon mask!");
            event.getEntity().getWorld().dropItem(event.getEntity().getLocation(), mask);
            event.getEntity().getWorld().playSound(event.getEntity().getLocation(), Sound.ITEM_PICKUP, 10.0f, 1.1f);
        }
        if (event != null && MinecraftServerUtils.isRunning() && CosmicUtils.get().isEvolutionKitEnabled() && (items = this.addLuckyBoxItems(Lists.newArrayList())) != null && items.size() > 1) {
            ItemStack itemstack;
            ItemStack armorsetItem;
            items.remove(0);
            int toGiveAmount = 10;
            while (items.size() < toGiveAmount) {
                this.addLuckyBoxItems(items);
            }
            final Location loc = event.getEntity().getLocation();
            final List<ItemStack> toGive = items.subList(0, Math.min(items.size(), toGiveAmount));
            if (this.random.nextDouble() <= 0.33 && (armorsetItem = new ArmorSetItemBuilder("DRAGON_SLAYER", EquipmentType.getRandomArmor(), true).build()) != null) {
                toGive.add(armorsetItem);
            }
            if (Math.random() <= 0.25 && (itemstack = new HeroicUpgradeBuilder("DRAGON_SLAYER", 50.0, 100.0).build()) != null) {
                toGive.add(itemstack);
            }
            ArrayList msgs = Lists.newArrayList();
            for (ItemStack item : toGive) {
                JSONMessage msg2 = new JSONMessage("");
                msg2.addItem(item, StringUtils.getCenteredMessage(CC.RedB + "* " + CC.White + ItemUtils.getDisplayName((ItemStack)item)), ChatColor.WHITE);
                msgs.add(msg2);
            }
            String header = StringUtils.getCenteredMessage(CC.RedB + "Timeless Dragon Bonus Rewards");
            for (Player pl : Bukkit.getOnlinePlayers()) {
                pl.sendMessage(header);
                msgs.forEach(msg -> msg.sendToPlayer(pl));
            }
            new BukkitRunnable(){

                public void run() {
                    if (toGive.isEmpty()) {
                        this.cancel();
                        return;
                    }
                    ItemStack item = (ItemStack)toGive.remove(0);
                    Location toSpawn = loc.clone();
                    toSpawn.add(EndDragon.this.random.nextGaussian(), 0.0, EndDragon.this.random.nextGaussian());
                    event.getEntity().getWorld().dropItemNaturally(toSpawn, item);
                    event.getEntity().getWorld().playSound(toSpawn, Sound.CHICKEN_EGG_POP, 10.0f, 1.4f);
                }
            }.runTaskTimer((Plugin)CosmicBosses.get(), 20L, 5L);
        }
    }

    private List<ItemStack> addLuckyBoxItems(List<ItemStack> items) {
        List found = ((Lucky)EvolutionKits.LUCKY.getKit()).getLuckyBoxItems(6 + this.random.nextInt(5), 5000.0);
        found.remove(0);
        items.addAll(found);
        return items;
    }

    @Override
    public void onPhaseChange(Phase newPhase) {
        DragonPhase newDragonPhase = DragonPhase.getDragonPhase(newPhase.getPhase());
        if (newDragonPhase == null) {
            return;
        }
        this.lastPhaseChange = System.currentTimeMillis();
        this.dragonPhase = newDragonPhase;
    }

    @Override
    public void onMinionUpdate(org.bukkit.entity.Entity minion, Event e) {
        if (e instanceof EntityDamageByEntityEvent) {
            EntityDamageByEntityEvent event = (EntityDamageByEntityEvent)e;
            if (!event.getDamager().equals((Object)minion)) {
                return;
            }
            if (!(event.getEntity() instanceof Player)) {
                event.setCancelled(true);
                return;
            }
            LivingEntity entity = (LivingEntity)event.getEntity();
            if (minion instanceof TNTPrimed) {
                double percent = 0.0;
                if (minion.hasMetadata("mouth_bomb")) {
                    percent = 15.0;
                } else if (minion.hasMetadata("dragon_bomb")) {
                    percent = 30.0;
                }
                Player player = (Player)entity;
                event.setDamage(0.0);
                if (percent > 0.0 && BossUtils.dealPercentMaxHealthDamage(player, percent, 20, true, true)) {
                    if (entity.isDead()) {
                        event.setCancelled(true);
                        event.setDamage(0.0);
                        Bukkit.getLogger().info("[EndDragon] Cancelling TNT damage for " + ((Player)event.getEntity()).getName() + " because they are dead!");
                        return;
                    }
                    entity.playEffect(EntityEffect.HURT);
                    ParticleEffect.sendToLocation((ParticleEffect)ParticleEffect.LARGE_SMOKE, (Location)entity.getLocation(), (float)1.0f, (float)1.0f, (float)1.0f, (float)0.01f, (int)15);
                    Bukkit.getLogger().info("[EnderDragon] Damaging " + entity.getHealth() + " by " + percent + " new health: " + entity.getHealth());
                }
            } else if (minion instanceof Blaze || minion instanceof Skeleton) {
                event.setDamage(0.01);
                if (!event.getEntity().isDead() && BossUtils.dealPercentMaxHealthDamage((Player)event.getEntity(), 10.0, 10, true, true)) {
                    if (!event.getEntity().isDead()) {
                        event.getEntity().playEffect(EntityEffect.HURT);
                    } else {
                        event.setCancelled(true);
                        event.setDamage(0.0);
                        Bukkit.getLogger().info("Cancelling damage for " + ((Player)event.getEntity()).getName());
                    }
                }
            }
        }
        super.onMinionUpdate(minion, e);
    }

    @Override
    public void onMiscUpdate(Event event) {
        if (event instanceof ProjectileHitEvent) {
            ProjectileHitEvent hitEvent = (ProjectileHitEvent)event;
            Projectile projectile = hitEvent.getEntity();
            Location loc = projectile.getLocation();
            int toSpawn = 1 + ThreadLocalRandom.current().nextInt(3);
            for (int i = 0; i < toSpawn; ++i) {
                this.spawnFireballMinion(loc.clone().add(this.random.nextGaussian() / 2.0, this.random.nextDouble() / 2.0, this.random.nextGaussian() / 2.0), true);
            }
            loc.getWorld().playSound(loc, Sound.EXPLODE, 4.0f, 1.1f);
            ParticleEffect.sendToLocation((ParticleEffect)ParticleEffect.HUGE_EXPLOSION, (Location)loc.add(0.0, 1.0, 0.0), (float)2.0f, (float)2.0f, (float)2.0f, (float)0.01f, (int)10);
            hitEvent.getEntity().getNearbyEntities(10.0, 10.0, 10.0).stream().filter(en -> en instanceof Player).map(e -> (Player)e).forEach(pl -> {
                if (!pl.isDead() && BossUtils.dealPercentMaxHealthDamage(pl, 10.0, 10, true, true)) {
                    BossUtils.pushAwayPlayer(pl, loc, 1.6);
                    pl.playEffect(EntityEffect.HURT);
                }
            });
            projectile.remove();
        }
    }

    private org.bukkit.entity.Entity spawnFireballMinion(Location location, boolean despawnOlds) {
        EntityType toSpawn = this.random.nextBoolean() ? EntityType.SKELETON : EntityType.BLAZE;
        List<org.bukkit.entity.Entity> minions = this.getMinions(toSpawn);
        int minionSize = minions.size();
        if (despawnOlds && minions.size() >= 15) {
            for (org.bukkit.entity.Entity ent : minions) {
                if (ent.getTicksLived() < 1200) continue;
                ent.remove();
                this.getMinions().remove((Object)ent);
                --minionSize;
            }
        }
        if (minionSize >= 15) {
            return null;
        }
        LivingEntity ent = (LivingEntity)this.getEntity().getWorld().spawnEntity(location, toSpawn);
        if (ent instanceof Skeleton) {
            ((Skeleton)ent).setSkeletonType(Skeleton.SkeletonType.WITHER);
        }
        ent.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 2));
        ent.setMaxHealth(ent.getMaxHealth() * 5.0);
        ent.setHealth(ent.getMaxHealth());
        ent.setCustomName(CC.LightPurpleB + "A Wandering Soul");
        ent.setCustomNameVisible(true);
        ent.setMetadata("no_drops", (MetadataValue)new FixedMetadataValue((Plugin)CosmicBosses.get(), (Object)this.getUuid()));
        ent.setMetadata("minion", (MetadataValue)new FixedMetadataValue((Plugin)CosmicBosses.get(), (Object)this.getUuid()));
        ent.getEquipment().setItemInHandDropChance(0.0f);
        ent.getEquipment().setHelmetDropChance(0.0f);
        ent.getEquipment().setChestplateDropChance(0.0f);
        ent.getEquipment().setLeggingsDropChance(0.0f);
        ent.getEquipment().setBootsDropChance(0.0f);
        ent.getEquipment().setItemInHand(new ItemBuilder(Material.DIAMOND_SWORD).enchant(Enchantment.DURABILITY, 3).enchant(Enchantment.DAMAGE_ALL, 5).build());
        this.getMinions().add((org.bukkit.entity.Entity)ent);
        return ent;
    }

    @Override
    public void onAttackPlayer(EntityDamageByEntityEvent event, Player player) {
        if (event.getDamager() instanceof LargeFireball) {
            this.spawnFireballMinion(event.getDamager().getLocation().add(0.0, 1.0, 0.0), false);
        }
        super.onAttackPlayer(event, player);
    }

    @Override
    public DamageType getDamageType() {
        return DamageType.FIRE;
    }

    @Override
    public Difficulty getDifficulty() {
        return Difficulty.END;
    }

    @Override
    public int getMaxHealth() {
        return 2000;
    }

    @Override
    public void sendMessage(String msg) {
        super.sendMessage(msg);
    }

    @Override
    public int getMessageRadius() {
        return 48;
    }

    @Override
    public List<BossMessage> getGeneralMessages() {
        return Lists.newArrayList(new BossMessage("You dare to enter my realm and disturb my slumber?", false, -1), new BossMessage("I will burn your world to ash!", false, 0), new BossMessage("Greater warriors than you have fallen before me.", false, 0));
    }

    static enum PathStyle {
        NORMAL,
        DIVE_BOMB_ABOVE,
        DIVE_BOMB_BELOW;

    }
}

