/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.compilex.CosmicArmorSets.CosmicArmorSetsAPI
 *  com.compilex.CosmicArmorSets.enums.ArmorSet
 *  com.cosmicpvp.CosmicSpaceChests.CosmicSpaceChestsAPI
 *  com.cosmicpvp.CosmicSpaceChests.libs.ItemRarity
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.cosmicpvp.cosmicutils.utils.ItemBuilder
 *  com.google.common.collect.Lists
 *  net.minecraft.server.v1_8_R3.Entity
 *  net.minecraft.server.v1_8_R3.EntityLiving
 *  net.minecraft.server.v1_8_R3.World
 *  org.arkhamnetwork.Arkkit.libs.ParticleEffect
 *  org.arkhamnetwork.Arkkit.patches.Halloween
 *  org.arkhamnetwork.Arkkit.patches.NewYears
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.EntityEffect
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.block.Block
 *  org.bukkit.block.BlockFace
 *  org.bukkit.craftbukkit.v1_8_R3.CraftWorld
 *  org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity
 *  org.bukkit.enchantments.Enchantment
 *  org.bukkit.entity.Creature
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.EntityType
 *  org.bukkit.entity.Item
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.entity.Projectile
 *  org.bukkit.entity.Snowball
 *  org.bukkit.entity.WitherSkull
 *  org.bukkit.entity.Zombie
 *  org.bukkit.event.Event
 *  org.bukkit.event.entity.CreatureSpawnEvent$SpawnReason
 *  org.bukkit.event.entity.EntityChangeBlockEvent
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.event.entity.EntityDamageEvent
 *  org.bukkit.event.entity.EntityDamageEvent$DamageCause
 *  org.bukkit.event.entity.EntityDeathEvent
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.ItemMeta
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.potion.PotionEffect
 *  org.bukkit.potion.PotionEffectType
 *  org.bukkit.util.Vector
 */
package me.ifamasssxd.cosmicbosses.bosses.xmasbattlefield;

import com.compilex.CosmicArmorSets.CosmicArmorSetsAPI;
import com.compilex.CosmicArmorSets.enums.ArmorSet;
import com.cosmicpvp.CosmicSpaceChests.CosmicSpaceChestsAPI;
import com.cosmicpvp.CosmicSpaceChests.libs.ItemRarity;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.ItemBuilder;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import me.ifamasssxd.cosmicbosses.CosmicBosses;
import me.ifamasssxd.cosmicbosses.bosses.holidayspirit.Freeze;
import me.ifamasssxd.cosmicbosses.bosses.xmasbattlefield.BattlefieldSnowmanEntity;
import me.ifamasssxd.cosmicbosses.bosses.xmasbattlefield.ElfSpawn;
import me.ifamasssxd.cosmicbosses.struct.Boss;
import me.ifamasssxd.cosmicbosses.struct.BossMessage;
import me.ifamasssxd.cosmicbosses.struct.Phase;
import me.ifamasssxd.cosmicbosses.struct.enums.BossType;
import me.ifamasssxd.cosmicbosses.struct.enums.DamageType;
import me.ifamasssxd.cosmicbosses.struct.enums.Difficulty;
import me.ifamasssxd.cosmicbosses.utils.BossUtils;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.World;
import org.arkhamnetwork.Arkkit.libs.ParticleEffect;
import org.arkhamnetwork.Arkkit.patches.Halloween;
import org.arkhamnetwork.Arkkit.patches.NewYears;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Creature;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.WitherSkull;
import org.bukkit.entity.Zombie;
import org.bukkit.event.Event;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class BattlefieldSnowman
extends Boss {
    Freeze freezeAbility;
    public BattlefieldSnowmanEntity nmsEntity;
    private ElfSpawn elfSpawn;
    private ItemStack skull;
    private int hitCounter = 0;
    List<String> msg = Lists.newArrayList((Object[])new String[]{"Feel the spirit of the holidays consume you!", "This is so much FUN! Are you having fun [NAME]?!", "You're in for a real treat!", "Happy Halloween! Thankful Thanksgiving! Merry Christmas! Buhahaha!"});

    public BattlefieldSnowman(Location location, BossType bossType) {
        super(location, bossType);
        this.freezeAbility = new Freeze(this);
        this.freezeAbility.activateCooldown();
        this.elfSpawn = new ElfSpawn(this);
        this.elfSpawn.activateCooldown();
        this.getLootPool().add(CosmicSpaceChestsAPI.getDropPackageChest((ItemRarity)ItemRarity.LEGENDARY));
        this.getLootPool().add(CosmicSpaceChestsAPI.getDropPackageChest((ItemRarity)ItemRarity.LEGENDARY));
        this.getLootPool().add(CosmicSpaceChestsAPI.getDropPackageChest((ItemRarity)ItemRarity.LEGENDARY));
        this.getLootPool().add(CosmicSpaceChestsAPI.getDropPackageChest((ItemRarity)ItemRarity.ULTIMATE));
        this.getLootPool().add(CosmicSpaceChestsAPI.getDropPackageChest((ItemRarity)ItemRarity.ULTIMATE));
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.SNOWMAN;
    }

    @Override
    public String getCustomName() {
        return (Object)ChatColor.GOLD + ChatColor.BOLD.toString() + this.getName();
    }

    public LivingEntity getTarget() {
        Creature creature = (Creature)this.getEntity();
        return creature.getTarget();
    }

    @Override
    public LivingEntity spawnBoss() {
        CraftWorld world = (CraftWorld)this.location.getWorld();
        this.nmsEntity = new BattlefieldSnowmanEntity((World)world.getHandle(), this);
        this.nmsEntity.setPosition(this.location.getX(), this.location.getY(), this.location.getZ());
        world.getHandle().addEntity((Entity)this.nmsEntity, CreatureSpawnEvent.SpawnReason.CUSTOM);
        LivingEntity ent = (LivingEntity)this.nmsEntity.getBukkitEntity();
        this.setupBoss(ent);
        return ent;
    }

    @Override
    public String getName() {
        return "Evil Holiday Spirit";
    }

    @Override
    public void cleanup() {
        CosmicBosses.getInstance().getBlockChangeManager().restoreAllBlocks(this.getEntity().getEntityId());
        this.removeMinions();
    }

    @Override
    public void onUpdate() {
        if (this.nmsEntity == null || this.nmsEntity.dead) {
            return;
        }
        ++this.loops;
        LivingEntity ent = this.getEntity();
        Location loc = ent.getLocation();
        if (this.freezeAbility.canUse()) {
            this.freezeAbility.onAbility();
            this.sendMessage((Object)ChatColor.GOLD + "You look like you need to CHILL out!");
            for (org.bukkit.entity.Entity enti : this.getNearbyEntities(20.0, EntityType.PLAYER)) {
                Player player = (Player)enti;
                player.playSound(player.getLocation(), Sound.ZOMBIE_REMEDY, 10.0f, 0.8f);
            }
        }
        if (this.elfSpawn.canUse() && this.getMinions().size() < 20) {
            this.elfSpawn.onAbility();
            this.elfSpawn.activateCooldown();
        }
        if (this.loops % this.getSeconds(10) == 0) {
            String msg = this.getRandomMessage();
            for (org.bukkit.entity.Entity et : this.getEntity().getNearbyEntities(32.0, 32.0, 32.0)) {
                if (!(et instanceof Player)) continue;
                Player player = (Player)et;
                this.sendMessage(player, (Object)ChatColor.GOLD + msg);
            }
        }
        Block under = loc.getBlock().getRelative(BlockFace.DOWN);
        Block current = loc.getBlock();
        if (current.getType() == Material.SNOW && under.getType() == Material.ICE) {
            ent.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20, 2), true);
        } else {
            ent.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
        }
        for (org.bukkit.entity.Entity minion : this.getMinions()) {
            if (!minion.isValid() || !(minion.getLocation().distanceSquared(this.getEntity().getLocation()) > 500.0)) continue;
            Location location = BossUtils.getRandomLocationNearby(this.getEntity().getLocation(), 5, 1, 3, true);
            minion.teleport(location.add(0.0, 1.0, 0.0));
        }
        if (this.loops % this.getSeconds(4) == 0) {
            this.updateScoreboard();
        }
    }

    @Override
    public String getTitle() {
        return "Spirit";
    }

    @Override
    public void onPhaseChange(Phase newPhase) {
    }

    @Override
    public org.bukkit.entity.Entity spawnMinion(EntityType type, Location l) {
        org.bukkit.entity.Entity entity = super.spawnMinion(type, l);
        LivingEntity livingEntity = (LivingEntity)entity;
        if (livingEntity instanceof Zombie) {
            ((Zombie)livingEntity).setBaby(true);
        }
        livingEntity.setCustomNameVisible(true);
        if (type == EntityType.ZOMBIE) {
            ((Zombie)livingEntity).setVillager(false);
            livingEntity.setCustomName(CC.RedB + "Exploding Elf");
            livingEntity.setMetadata("exploding", (MetadataValue)new FixedMetadataValue((Plugin)CosmicBosses.get(), (Object)""));
            if (this.skull == null) {
                this.skull = new ItemBuilder(Material.SKULL_ITEM).data((short)3).setSkullOwner("MysteryMerchant").build();
            }
            livingEntity.getEquipment().setHelmet(this.skull.clone());
        } else {
            livingEntity.setCustomName(CC.GreenB + "Holiday Ghoul");
        }
        livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 1));
        livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 2));
        ItemStack minWeapon = new ItemStack(Material.DIAMOND_SWORD, 1);
        minWeapon.addEnchantment(Enchantment.DAMAGE_ALL, 5);
        livingEntity.getEquipment().setItemInHand(minWeapon);
        livingEntity.setMaxHealth(65.0);
        livingEntity.setHealth(65.0);
        return entity;
    }

    @Override
    public void onMinionUpdate(org.bukkit.entity.Entity minion, Event e) {
        if (e instanceof EntityDamageByEntityEvent) {
            EntityDamageByEntityEvent event = (EntityDamageByEntityEvent)e;
            if (event.getDamager() instanceof Player) {
                Player damager = (Player)event.getDamager();
                if (ThreadLocalRandom.current().nextBoolean()) {
                    damager.damage(((EntityDamageByEntityEvent)e).getFinalDamage());
                    damager.playSound(damager.getLocation(), Sound.ZOMBIE_WOODBREAK, 0.2f, 2.0f);
                }
            } else if (event.getDamager().equals((Object)minion) && event.getEntity() instanceof Player) {
                if (event.getDamager().hasMetadata("exploding")) {
                    minion.getNearbyEntities(6.0, 6.0, 6.0).stream().filter(ent -> ent instanceof Player).map(ent -> (Player)ent).forEach(pl -> {
                        if (BossUtils.dealPercentHealthDamage(pl, 20.0, 10) && !pl.isDead()) {
                            pl.playEffect(EntityEffect.HURT);
                            BossUtils.pushAwayPlayer(minion, pl, (double)1.4f);
                        }
                    });
                    ParticleEffect.sendToLocation((ParticleEffect)ParticleEffect.FIREWORKS_SPARK, (Location)minion.getLocation(), (float)0.75f, (float)0.75f, (float)0.75f, (float)0.01f, (int)30);
                    ParticleEffect.sendToLocation((ParticleEffect)ParticleEffect.EXPLODE, (Location)minion.getLocation(), (float)1.0f, (float)1.0f, (float)1.0f, (float)0.01f, (int)10);
                    minion.getWorld().playSound(minion.getLocation(), Sound.GHAST_SCREAM2, 1.0f, 1.9f);
                    minion.getWorld().playSound(minion.getLocation(), Sound.EXPLODE, 3.0f, 2.0f);
                    minion.playEffect(EntityEffect.DEATH);
                    minion.remove();
                    this.removeMinion(minion);
                    return;
                }
                Player player = (Player)event.getEntity();
                if (ThreadLocalRandom.current().nextInt(10) <= 4) {
                    ItemStack handItem = player.getItemInHand();
                    int inHandSlot = player.getInventory().getHeldItemSlot();
                    ItemStack held = player.getInventory().getItem(inHandSlot);
                    if (held == null || held.getType() == Material.AIR) {
                        return;
                    }
                    int slotToSwap = ThreadLocalRandom.current().nextInt(9);
                    while (slotToSwap == inHandSlot) {
                        slotToSwap = ThreadLocalRandom.current().nextInt(9);
                    }
                    ItemStack itemInNewSlot = player.getInventory().getItem(slotToSwap);
                    if (itemInNewSlot != null && itemInNewSlot.getType() != Material.AIR) {
                        player.setItemInHand(itemInNewSlot);
                    } else {
                        player.setItemInHand(null);
                    }
                    player.getInventory().setItem(slotToSwap, handItem);
                    player.updateInventory();
                    player.playSound(player.getLocation(), Sound.GHAST_SCREAM2, 1.0f, 1.9f);
                    player.sendMessage((Object)ChatColor.RED + ChatColor.BOLD.toString() + "Holiday Ghoul >> " + (Object)ChatColor.RED + "You wanna see a trick?");
                }
            } else if (!(event.getDamager() instanceof Player)) {
                event.setCancelled(true);
            }
        } else if (e instanceof EntityDamageEvent) {
            EntityDamageEvent event = (EntityDamageEvent)e;
            if (event.getCause().name().startsWith("FIRE") || event.getCause() == EntityDamageEvent.DamageCause.LAVA) {
                event.setCancelled(true);
            } else if (event.getCause() == EntityDamageEvent.DamageCause.FALL) {
                event.setCancelled(true);
            }
        } else if (e instanceof EntityChangeBlockEvent) {
            ((EntityChangeBlockEvent)e).setCancelled(true);
        }
        super.onMinionUpdate(minion, e);
    }

    @Override
    public void onAttackPlayer(EntityDamageByEntityEvent event, Player player) {
        if (event.getDamager() instanceof Snowball) {
            event.setDamage((double)(7 + this.random.nextInt(2)));
            if (this.random.nextBoolean()) {
                player.getWorld().playSound(player.getLocation(), Sound.EXPLODE, 1.0f, 1.1f);
                ParticleEffect.sendToLocation((ParticleEffect)ParticleEffect.LARGE_EXPLODE, (Location)player.getLocation().clone(), (float)1.0f, (float)1.0f, (float)1.0f, (float)0.01f, (int)5);
            }
            if (!event.isCancelled()) {
                for (org.bukkit.entity.Entity ent : player.getNearbyEntities(5.0, 3.0, 5.0)) {
                    Player other;
                    if (!(ent instanceof Player) || !BossUtils.canAffectPlayer(other = (Player)ent, true)) continue;
                    other.damage(5.0);
                }
                player.setVelocity(event.getDamager().getLocation().getDirection().multiply(1.65).add(new Vector(0.0, 0.3, 0.0)));
                if (ThreadLocalRandom.current().nextInt(5) <= 1 && this.getMinions().size() < 8) {
                    this.spawnMinion(EntityType.PIG_ZOMBIE, event.getDamager().getLocation().clone().add(0.0, 1.0, 0.0));
                }
            }
        } else if (event.getDamager() instanceof WitherSkull) {
            if (this.random.nextBoolean()) {
                player.getWorld().playSound(player.getLocation(), Sound.EXPLODE, 1.0f, 0.9f);
                ParticleEffect.sendToLocation((ParticleEffect)ParticleEffect.LARGE_EXPLODE, (Location)player.getLocation().clone(), (float)1.0f, (float)1.0f, (float)1.0f, (float)0.01f, (int)5);
            }
            this.shootSkullsNearby(event.getEntity(), 10.0);
        }
        super.onAttackPlayer(event, player);
    }

    public void shootSkullsNearby(org.bukkit.entity.Entity near, double dist) {
        if (this.nmsEntity == null) {
            return;
        }
        block0: for (org.bukkit.entity.Entity ent : near.getNearbyEntities(dist, dist, dist)) {
            boolean goingToSplit;
            Player other;
            if (!(ent instanceof Player) || !BossUtils.canAffectPlayer(other = (Player)ent, true)) continue;
            boolean bl = goingToSplit = this.random.nextDouble() <= 0.8 && !this.nmsEntity.recentlySplitPlayer.containsKey(other.getUniqueId());
            if (!goingToSplit) {
                other.damage(10.0);
                other.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 200, 1));
                Location loc = this.getEntity().getLocation();
                Vector kb = near.getLocation().toVector().subtract(loc.toVector());
                if (kb.length() != 0.0) {
                    kb = kb.normalize();
                }
                kb.multiply(3);
                if (kb.getY() < 1.5) {
                    kb.setY(1.5);
                }
                other.setVelocity(kb);
                continue;
            }
            if (!(other.getLocation().distanceSquared(near.getLocation()) <= 64.0)) continue;
            int spawned = 0;
            for (org.bukkit.entity.Entity nearby : other.getNearbyEntities(10.0, 10.0, 10.0)) {
                if (spawned >= 2) continue block0;
                if (!(nearby instanceof Player) || !BossUtils.canAffectPlayer((Player)nearby, true) || this.nmsEntity.recentlySplitPlayer.containsKey(nearby.getUniqueId())) continue;
                ++spawned;
                this.nmsEntity.recentlySplitPlayer.put(nearby.getUniqueId(), System.currentTimeMillis());
                Location nearLoc = nearby.getLocation();
                this.nmsEntity.shootAtTarget((EntityLiving)((CraftEntity)other).getHandle(), nearLoc.getX(), nearLoc.getY(), nearLoc.getZ(), true);
                Bukkit.getLogger().info("[BattleSnowman] Splitting Skull towards " + ((Player)nearby).getName() + " from other: " + other.getName());
            }
        }
    }

    @Override
    public void onDeath(EntityDeathEvent event) {
        if (event != null) {
            this.sendMessage((Object)ChatColor.GOLD + "You may have defeated me, but you will never be rid of holiday spirit.", Sound.ENDERDRAGON_DEATH, 1.3f);
            ParticleEffect.sendToLocation((ParticleEffect)ParticleEffect.HUGE_EXPLOSION, (Location)event.getEntity().getLocation(), (float)5.0f, (float)5.0f, (float)5.0f, (float)0.01f, (int)100);
            event.getEntity().getWorld().playSound(event.getEntity().getLocation(), Sound.EXPLODE, 10.0f, 1.0f);
            event.getEntity().getNearbyEntities(100.0, 50.0, 100.0).stream().filter(ent -> ent instanceof Player).forEach(ent -> {
                Player player = (Player)ent;
                if (BossUtils.canAffectPlayer(player, true)) {
                    player.setVelocity(player.getVelocity().add(new Vector(0, 3, 0)));
                }
            });
            Location location = event.getEntity().getLocation();
            for (int i = 0; i < 100; ++i) {
                int inStack = this.random.nextInt(6) + 10;
                i += inStack;
                ItemStack candy = Halloween.getHalloweenCandy().clone();
                candy.setAmount(inStack);
                Item item = location.getWorld().dropItemNaturally(location, this.addUniqueLore(candy));
                item.setMetadata("removeDurability", (MetadataValue)new FixedMetadataValue((Plugin)CosmicBosses.getInstance(), (Object)""));
            }
            int cakes = 50 + this.random.nextInt(51);
            for (int i = 0; i < cakes; ++i) {
                int inStack = this.random.nextInt(6) + 10;
                i += inStack;
                ItemStack cake = this.random.nextDouble() <= 0.8 ? NewYears.getExplosiveCake().clone() : NewYears.getCosmicCracker().clone();
                cake.setAmount(inStack);
                Item item = location.getWorld().dropItemNaturally(location, this.addUniqueLore(cake));
                item.setMetadata("removeDurability", (MetadataValue)new FixedMetadataValue((Plugin)CosmicBosses.getInstance(), (Object)""));
            }
            if (ThreadLocalRandom.current().nextInt(100) == 0) {
                location.getWorld().dropItemNaturally(location, CosmicArmorSetsAPI.getArmorSet((ArmorSet)ArmorSet.PHANTOM)[ThreadLocalRandom.current().nextInt(4)]);
                Bukkit.getLogger().info("Dropping Phantom Piece at " + (Object)location);
            }
        }
        super.onDeath(event);
    }

    private ItemStack addUniqueLore(ItemStack item) {
        ItemMeta im = item.getItemMeta();
        List<String> lore = im.hasLore() ? im.getLore() : Lists.newArrayList();
        lore.add(this.random.nextInt(1000000) + "");
        im.setLore(lore);
        item.setItemMeta(im);
        return item;
    }

    @Override
    public void onDamageTaken(EntityDamageEvent event) {
        if (event.getCause().name().startsWith("FIRE") || event.getCause() == EntityDamageEvent.DamageCause.DROWNING || event.getCause() == EntityDamageEvent.DamageCause.FALL) {
            event.setCancelled(true);
        }
        super.onDamageTaken(event);
    }

    @Override
    public void onDamageTaken(EntityDamageByEntityEvent event, org.bukkit.entity.Entity damager) {
        if (event.getDamager() instanceof Projectile && damager instanceof Player) {
            event.setCancelled(true);
            return;
        }
        if (damager instanceof Player && !((Player)damager).isOp()) {
            Player player = (Player)damager;
            ++this.hitCounter;
            if (this.hitCounter >= 4) {
                this.hitCounter = 0;
                this.getEntity().getWorld().dropItemNaturally(this.getEntity().getLocation(), Halloween.getHalloweenCandy().clone());
            }
            double percentToReflect = 10.0;
            double healthMissing = (double)this.getMaxHealth() - this.getEntity().getHealth();
            double percentageMissing = healthMissing / (double)this.getMaxHealth() * 100.0;
            double mult = percentageMissing / 15.0;
            percentToReflect = Math.min(90.0, percentToReflect * mult);
            player.damage(event.getDamage() * (percentToReflect * 0.01), (org.bukkit.entity.Entity)this.getEntity());
        }
        event.setDamage(event.getDamage() / 3.0);
        super.onDamageTaken(event, damager);
    }

    @Override
    public void onMiscUpdate(Event event) {
    }

    @Override
    public DamageType getDamageType() {
        return DamageType.FIRE;
    }

    @Override
    public Difficulty getDifficulty() {
        return Difficulty.LEGENDARY_PLUS;
    }

    @Override
    public int getMaxHealth() {
        return 2000;
    }

    public String getRandomMessage() {
        return this.msg.get(this.random.nextInt(this.msg.size()));
    }

    @Override
    public List<BossMessage> getGeneralMessages() {
        return Lists.newArrayList((Object[])new BossMessage[]{new BossMessage("I've brought you something good to eat!", false, -1)});
    }
}

