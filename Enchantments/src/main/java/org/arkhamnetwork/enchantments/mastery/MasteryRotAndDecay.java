package org.arkhamnetwork.enchantments.mastery;

import com.compilex.CosmicArmorSets.CosmicArmorSetsAPI;
import com.compilex.CosmicArmorSets.enums.ArmorSet;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.FactionUtils;
import com.cosmicpvp.cosmicutils.utils.ItemUtils;
import com.cosmicpvp.cosmicutils.utils.LocationUtils;
import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import com.cosmicpvp.cosmicutils.utils.NumberUtils;
import com.cosmicpvp.cosmicutils.utils.ReflectionUtils;
import com.google.common.collect.MapMaker;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.iface.RelationParticipator;
import com.massivecraft.factions.struct.Relation;
import com.rit.sucy.CustomEnchantment;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import lombok.SneakyThrows;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.EntityTrackerEntry;
import net.minecraft.server.v1_8_R3.WorldServer;
import org.arkhamnetwork.Arkkit.libs.ParticleEffect;
import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.MasteryCustomEnchantment;
import org.arkhamnetwork.enchantments.events.CustomEnchantProcOnEntityEvent;
import org.arkhamnetwork.enchantments.listeners.CustomEnchantListener;
import org.arkhamnetwork.enchantments.util.ArmorSlot;
import org.arkhamnetwork.enchantments.util.ArmorUtil;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.arkhamnetwork.enchantments.util.WorldGuardUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.EntityEffect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class MasteryRotAndDecay extends MasteryCustomEnchantment {
    private Map<UUID, Long> decayingMessage = (new MapMaker()).expireAfterWrite(5L, TimeUnit.SECONDS).makeMap();

    private Map<UUID, Integer> rotAndDecayStacks = (new MapMaker()).expireAfterWrite(3L, TimeUnit.SECONDS).makeMap();

    public MasteryRotAndDecay() {
        super("Rot and Decay", GeneralUtil.armor, 11);
        this.max = 10;
        this.armorSet = "NECROMANCER";
        Bukkit.getPluginManager().registerEvents((Listener)new RotAndDecayListener(this), (Plugin)Enchantments.getInstance());
    }

    public boolean decayDamage(Player player, int level) {
        if (CosmicArmorSetsAPI.getArmorSet(player) == ArmorSet.MOTHER_OF_YIJKI) {
            ItemStack handItem = player.getItemInHand();
            if (handItem != null) {
                Material type = handItem.getType();
                if (type != null && type == Material.DIAMOND_SWORD && (new NBTWrapper(handItem)).getString("cosmicType").equals("YIJKI_SWORD"))
                    return false;
            }
        }
        Integer currentStacks = this.rotAndDecayStacks.getOrDefault(player.getUniqueId(), Integer.valueOf(0));
        Integer integer1 = currentStacks, integer2 = currentStacks = Integer.valueOf(currentStacks.intValue() + 1);
        int maxStacksAllowed = Math.min(6, level);
        if (currentStacks.intValue() >= maxStacksAllowed)
            currentStacks = Integer.valueOf(maxStacksAllowed);
        this.rotAndDecayStacks.put(player.getUniqueId(), currentStacks);
        double damage = (currentStacks.intValue() * 2);
        player.damage(damage);
        player.sendMessage(CC.RedB + "* DECAYING [" + CC.Gray + "-" + NumberUtils.formatSeconds(damage) + "HP (" + currentStacks + " stacks)" + CC.RedB + "] *");
        return true;
    }

    @SneakyThrows
    public void applyDefenseEffect(LivingEntity user, LivingEntity target, int enchantLevel, EntityDamageEvent event) {
        if (target == null || !(target instanceof Player) || !(event instanceof EntityDamageByEntityEvent))
            return;
        if (event.isCancelled())
            return;
        double chance = 0.05D + enchantLevel * 0.01D;
        if (Math.random() <= chance) {
            if (WorldGuardUtils.isPvPDisabled(user.getLocation()))
                return;
            if (!(new CustomEnchantProcOnEntityEvent(user, target, (Event)event, (CustomEnchantment)this, enchantLevel)).callEvent())
                return;
            World world = user.getLocation().getWorld();
            int radius = 3 + enchantLevel / 3;
            Block block = user.getLocation().getBlock();
            Player damaged = (Player)user;
            Location playerLoc = user.getLocation();
            applyRotAndDecayEffect(damaged, block, radius, enchantLevel, event);
            int cleanedUp = 0;
            int zombiesToSpawn = 1 + enchantLevel / 3;
            int zombiesWeNeed = zombiesToSpawn;
            for (Entity ent : damaged.getNearbyEntities(15.0D, 15.0D, 15.0D)) {
                if (ent instanceof Player)
                    continue;
                String summoner = null;
                if (ent.hasMetadata("undeadruseSummoner")) {
                    summoner = ((MetadataValue)ent.getMetadata("undeadruseSummoner").get(0)).asString();
                } else if (ent.hasMetadata("guardianSummoner")) {
                    summoner = ((MetadataValue)ent.getMetadata("guardianSummoner").get(0)).asString();
                } else if (ent.hasMetadata("acenstoralSummoner")) {
                    summoner = ((MetadataValue)ent.getMetadata("acenstoralSummoner").get(0)).asString();
                }
                if (summoner != null) {
                    Player pSummoner = Bukkit.getPlayer(summoner);
                    if (pSummoner == null || !summoner.equalsIgnoreCase(damaged.getName()) || GeneralUtil.isAlly(damaged, pSummoner)) {
                        ent.remove();
                        ParticleEffect.sendToLocation(ParticleEffect.LARGE_SMOKE, ent.getLocation().add(0.0D, 1.0D, 0.0D), 0.3F, 0.3F, 0.3F, 0.01F, 10);
                        ParticleEffect.playEffect(ParticleEffect.WITCH_MAGIC, ent.getLocation().add(0.0D, 1.0D, 0.0D), 0.7F, 12);
                        cleanedUp++;
                    }
                }
            }
            if (cleanedUp > 0)
                Bukkit.getLogger().info("[RotAndDecay] " + damaged.getName() + " has cleared " + cleanedUp + " summons at " + LocationUtils.printPretty(damaged.getLocation(), ChatColor.GREEN, false));
            Random random = ThreadLocalRandom.current();
            int actuallySpawned = 0;
            while (zombiesWeNeed > 0) {
                zombiesWeNeed--;
                if ((playerLoc.getChunk().getEntities()).length < 50) {
                    Zombie z = (Zombie)playerLoc.getWorld().spawnEntity(playerLoc.clone().add(0.0D, 1.0D, 0.0D), EntityType.ZOMBIE);
                    z.setMetadata("undeadruseSummoner", (MetadataValue)new FixedMetadataValue((Plugin)Enchantments.getInstance(), damaged.getName()));
                    z.setMetadata("rotAndDecay", (MetadataValue)new FixedMetadataValue((Plugin)Enchantments.getInstance(), damaged.getName()));
                    z.setMetadata("no_drops", (MetadataValue)new FixedMetadataValue((Plugin)Enchantments.getInstance(), damaged.getName()));
                    z.setBaby(false);
                    z.setCanPickupItems(false);
                    z.setCustomName(ChatColor.DARK_GREEN.toString() + ChatColor.BOLD.toString() + damaged.getName() + "'s Rotting Corpse");
                    z.setCustomNameVisible(true);
                    z.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 2147483647, (enchantLevel > 6) ? 2 : ((enchantLevel > 3) ? 1 : 0)));
                    Vector vel = new Vector((random.nextBoolean() ? -1 : 1) * Math.random() / 2.0D, Math.random() / 10.0D, (random.nextBoolean() ? -1 : 1) * Math.random() / 2.0D);
                    if (vel.length() != 0.0D)
                        vel.normalize();
                    z.setVelocity(vel);
                    actuallySpawned++;
                }
            }
            Bukkit.getLogger().info("[RotAndDecay] Spawned (" + actuallySpawned + " / " + zombiesToSpawn + ") zombies near " + LocationUtils.getStringFromLocation(playerLoc) + " for " + damaged.getName());
        }
    }

    public void applyRotAndDecayEffect(final Player sourcePlayer, Block centerBlock, int radius, final int enchantLevel, EntityDamageEvent event) throws NoSuchFieldException, IllegalAccessException {
        int duration = enchantLevel;
        final Set<Location> blocks = new HashSet<>();
        int endX = centerBlock.getX() + radius, endY = centerBlock.getY() + radius, endZ = centerBlock.getZ() + radius;
        int startX = centerBlock.getX() - radius, startY = centerBlock.getY() - radius, startZ = centerBlock.getZ() - radius;
        for (int x = startX; x < endX; x++) {
            for (int y = startY; y < endY; y++) {
                for (int z = startZ; z < endZ; z++) {
                    Location loc = new Location(centerBlock.getWorld(), x, y, z);
                    Block bl = loc.getBlock();
                    Material blType = bl.getType();
                    Material aboveType;
                    if (blType.isSolid() && !blType.isTransparent() && blType != Material.STEP && blType != Material.WOOD_STEP && ((
                            aboveType = bl.getRelative(BlockFace.UP).getType()) == Material.AIR || aboveType == Material.WATER || aboveType == Material.STATIONARY_WATER || aboveType == Material.LAVA || aboveType == Material.STATIONARY_LAVA))
                        if (!WorldGuardUtils.isPvPDisabled(loc) && loc.distanceSquared(centerBlock.getLocation()) <= (radius * radius))
                            blocks.add(loc);
                }
            }
        }
        Location playerLoc = centerBlock.getLocation();
        final EntityPlayer player = ((CraftPlayer)sourcePlayer).getHandle();
        final Set<EntityPlayer> affected = new HashSet<>();
        affected.add(player);
        if (blocks.size() > 0) {
            EntityTrackerEntry entry = (EntityTrackerEntry)(((WorldServer)player.world).getTracker()).trackedEntities.get(player.getId());
            if (entry != null) {
                CopyOnWriteArrayList<EntityPlayer> trackedPlayers = (CopyOnWriteArrayList<EntityPlayer>)ReflectionUtils.getObject(EntityTrackerEntry.class, entry, "trackedPlayers");
                for (EntityPlayer tracked : trackedPlayers) {
                    if (tracked instanceof EntityPlayer) {
                        EntityPlayer pl = tracked;
                        if (pl.playerConnection == null || pl.playerConnection.isDisconnected() || (
                                event != null && !(new CustomEnchantProcOnEntityEvent(sourcePlayer, pl.getBukkitEntity(), event, this, enchantLevel)).callEvent()))
                            continue;
                        affected.add(pl);
                    }
                }
            }
        } else {
            Bukkit.getLogger().info("[RotAndDecay] No available blocks found near " + LocationUtils.getStringFromLocation(sourcePlayer.getLocation()) + " for " + sourcePlayer.getName());
        }
        if (affected.size() > 0) {
            Faction faction = FactionUtils.getFaction(sourcePlayer);
            for (Location loc : blocks) {
                for (EntityPlayer affect : affected) {
                    if (affect.equals(player)) {
                        affect.getBukkitEntity().sendBlockChange(loc, Material.GLOWSTONE, (byte)0);
                        continue;
                    }
                    if (faction != null) {
                        Faction fpl = FactionUtils.getFaction((Player)affect.getBukkitEntity(), false);
                        if (fpl != null && faction.getRelationTo((RelationParticipator)fpl).isAtLeast(Relation.TRUCE)) {
                            affect.getBukkitEntity().sendBlockChange(loc, Material.GLOWSTONE, (byte)0);
                            continue;
                        }
                    }
                    affect.getBukkitEntity().sendBlockChange(loc, Material.ENDER_STONE, (byte)0);
                }
            }
            Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)Enchantments.getInstance(), () -> {
                try {
                    Map<Location, MaterialData> found = (Map<Location, MaterialData>) blocks;
                    affected.forEach(null);
                } catch (Exception exception) {}
                affected.clear();
            }, (20 * duration));
            (new BukkitRunnable() {
                public void run() {
                    if (affected.isEmpty()) {
                        cancel();
                        return;
                    }
                    for (Iterator<EntityPlayer> iter = affected.iterator(); iter.hasNext(); ) {
                        EntityPlayer entPlayer = iter.next();
                        if (entPlayer.playerConnection == null || entPlayer.playerConnection.isDisconnected()) {
                            iter.remove();
                            continue;
                        }
                        if (entPlayer.equals(player) || entPlayer.dead || !entPlayer.isAlive())
                            continue;
                        CraftPlayer player = entPlayer.getBukkitEntity();
                        if (player.getGameMode() != GameMode.SURVIVAL || player.hasMetadata("spectator"))
                            continue;
                        Location current = new Location((World)entPlayer.getWorld().getWorld(), Math.floor(entPlayer.locX), Math.floor(entPlayer.locY), Math.floor(entPlayer.locZ));
                        if ((!blocks.contains(current) && !blocks.contains(current.clone().add(0.0D, -1.0D, 0.0D))) ||
                                GeneralUtil.isAtleastTruce((Player)player, sourcePlayer))
                            continue;
                        MasteryRotAndDecay.this.decayDamage((Player)entPlayer.getBukkitEntity(), enchantLevel);
                    }
                }
            }).runTaskTimer((Plugin)Enchantments.getInstance(), 20L, 10L);
        }
    }

    class RotAndDecayListener extends CustomEnchantListener<MasteryRotAndDecay> {
        public RotAndDecayListener(MasteryRotAndDecay enchant) {
            super(enchant);
        }

        @EventHandler(priority = EventPriority.NORMAL)
        public void onEntityDeath(EntityDeathEvent event) {
            if (event.getEntity().hasMetadata("rotAndDecay"))
                event.getDrops().clear();
        }

        @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
        public void onEntityDamage(EntityDamageByEntityEvent event) {
            if (event.getEntity() instanceof Player && event.getDamager() instanceof Zombie &&
                    event.getDamager().hasMetadata("rotAndDecay")) {
                String who = ((MetadataValue)event.getDamager().getMetadata("rotAndDecay").get(0)).asString();
                Player damagedPlayer = (Player)event.getEntity();
                event.setCancelled(true);
                event.setDamage(0.0D);
                event.getDamager().remove();
                ParticleEffect.sendToLocation(ParticleEffect.LARGE_SMOKE, event.getDamager().getLocation().add(0.0D, 0.75D, 0.0D), 0.5F, 0.5F, 0.5F, 0.01F, 20);
                if (CosmicArmorSetsAPI.getArmorSet(damagedPlayer) == ArmorSet.MOTHER_OF_YIJKI) {
                    ItemStack handItem = damagedPlayer.getItemInHand();
                    if (handItem != null) {
                        Material type = handItem.getType();
                        if (type != null && type == Material.DIAMOND_SWORD && (new NBTWrapper(handItem)).getString("cosmicType").equals("YIJKI_SWORD"))
                            return;
                    }
                }
                damagedPlayer.playEffect(EntityEffect.HURT);
                if (Math.random() <= 0.5D) {
                    double damage = damagedPlayer.getMaxHealth() * 0.1D;
                    GeneralUtil.reducePlayerHealth(damagedPlayer, damage);
                    damagedPlayer.sendMessage(CC.RedB + "* ROTTED [" + CC.Gray + "-" + NumberUtils.formatSeconds(damage) + "HP (" + who + ")" + CC.RedB + "] *");
                    Bukkit.getLogger().info("[RotAndDecay] Damaging " + damagedPlayer.getName() + " by " + damage);
                } else {
                    for (ItemStack armorItem : damagedPlayer.getInventory().getArmorContents()) {
                        if (armorItem != null) {
                            Material type = armorItem.getType();
                            if (type != Material.AIR) {
                                short duraToTake = (short)(int)Math.ceil(ItemUtils.getMaxDurability(armorItem) * 0.025D);
                                ArmorSlot slot = ArmorSlot.getArmorType(armorItem);
                                if (slot != null) {
                                    ArmorUtil.modifyPlayerArmor(damagedPlayer, slot, -duraToTake, false);
                                    damagedPlayer.getWorld().playSound(damagedPlayer.getLocation(), Sound.ZOMBIE_METAL, 3.0F, 1.5F);
                                    damagedPlayer.sendMessage(CC.RedB + "* DECAYED [" + CC.Gray + "- " + duraToTake + " Durability" + CC.RedB + "] *");
                                    Bukkit.getLogger().info("[RotAndDecay] Damaging " + damagedPlayer.getName() + "'s Armor by " + duraToTake);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}