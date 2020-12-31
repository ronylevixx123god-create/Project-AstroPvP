/*     */
package com.compilex.CosmicArmorSets.enums.armorsets;
/*     */

import com.compilex.CosmicArmorSets.CosmicArmorSetsAPI;
/*     */ import com.compilex.CosmicArmorSets.CosmicArmorSetsPlugin;
import com.compilex.CosmicArmorSets.enums.ArmorSetInfo;
import com.compilex.CosmicArmorSets.enums.OtherPlayerEventListenerSet;
import com.compilex.CosmicArmorSets.utils.ItemUtils;
/*     */ import com.compilex.CosmicOutposts.CosmicOutpostsAPI;
import com.compilex.CosmicOutposts.outpost.enums.OutpostTier;
/*     */ import com.cosmicpvp.cosmicutils.utils.CC;
/*     */ import com.cosmicpvp.cosmicutils.utils.EssentialsUtils;
/*     */ import com.cosmicpvp.cosmicutils.utils.FactionUtils;
import com.cosmicpvp.cosmicutils.utils.WorldGuardUtils;
/*     */ import com.google.common.collect.Lists;
import com.google.common.collect.MapMaker;
import com.massivecraft.factions.FPlayer;
/*     */ import com.massivecraft.factions.FPlayers;
/*     */ import com.massivecraft.factions.Faction;
/*     */ import com.massivecraft.factions.iface.RelationParticipator;
/*     */ import com.massivecraft.factions.struct.Relation;
/*     */ import com.rit.sucy.CustomEnchantment;
/*     */ import com.rit.sucy.EnchantmentAPI;
/*     */ import java.util.*;
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */ import java.util.concurrent.ThreadLocalRandom;

import me.ifamasssxd.cosmicbosses.CosmicBosses;
import net.minecraft.server.v1_7_R4.*;
/*     */
/*     */
/*     */
/*     */
/*     */ import net.minecraft.server.v1_7_R4.World;
import org.arkhamnetwork.enchantments.enchantments.armor.Clarity;
import org.bukkit.*;
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */ import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
/*     */ import org.bukkit.entity.FallingBlock;
/*     */ import org.bukkit.entity.LivingEntity;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.Event;
/*     */ import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
/*     */ import org.bukkit.event.entity.EntityDamageByEntityEvent;
/*     */ import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.metadata.FixedMetadataValue;
/*     */ import org.bukkit.metadata.MetadataValue;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/*     */
/*     */ public class DimensionalTravelerSet extends ArmorSetInfo implements OtherPlayerEventListenerSet {
    /*     */
    public ItemStack getHelmet(boolean applyTransmog) {
        /*  51 */
        return getWithTransmog(applyTransmog, ItemUtils.applyCustomEnchantments(ItemUtils.buildItem(Material.DIAMOND_HELMET, (short) 0, "&5&lInterdimensional Hood", CC.Gray + "A hood that fades in and out of", CC.Gray + "existence inside the dimension of the owner.", "", "&5&lTRAVELER SET BONUS", "&5&l* &5You deal 30% more damage.", "&5&l* &5Dimensional Shift Passive Ability", "&7(&oRequires all 4 dimensional traveler items.&7)"), new LinkedList(
                /*     */
                /*     */
                /*     */
                /*     */
                /*     */
                /*     */
                /*     */
                /*     */
                /*  60 */             Arrays.asList(/*  61 */                 EnchantmentAPI.getEnchantment("Alien Implants"),
                /*  62 */                 EnchantmentAPI.getEnchantment("Planetary Deathbringer"),
                /*  63 */                 EnchantmentAPI.getEnchantment("Drunk"),
                /*  64 */                 EnchantmentAPI.getEnchantment("Enlighted"),
                /*  65 */                 EnchantmentAPI.getEnchantment("Armored"),
                /*  66 */                 EnchantmentAPI.getEnchantment("Tank"),
                /*  67 */                 EnchantmentAPI.getEnchantment("Angelic"),
                /*  68 */                 EnchantmentAPI.getEnchantment("Hardened"),
                /*  69 */                 EnchantmentAPI.getEnchantment("Valor"),
                /*  70 */                 EnchantmentAPI.getEnchantment("Dodge"),
                /*  71 */                 EnchantmentAPI.getEnchantment("Guardians"),
                /*  72 */                 EnchantmentAPI.getEnchantment("Sticky"),
                /*  73 */                 EnchantmentAPI.getEnchantment("Implants"),
                /*  74 */                 EnchantmentAPI.getEnchantment("Repair Guard")))));
        /*     */
    }

    /*     */
    /*     */
    /*     */
    public ItemStack getChestplate(boolean applyTransmog) {
        /*  79 */
        return getWithTransmog(applyTransmog, ItemUtils.applyCustomEnchantments(ItemUtils.buildItem(Material.DIAMOND_CHESTPLATE, (short) 0, "&5&lChestplate of Ad Infinitum", CC.Gray + "A cross-dimensional chestplate", CC.Gray + "with impossibly infinite density", CC.Gray + "and defensive properties.", "", "&5&lTRAVELER SET BONUS", "&5&l* &5You deal 30% more damage.", "&5&l* &5Dimensional Shift Passive Ability", "&7(&oRequires all 4 dimensional traveler items.&7)"), new LinkedList(
                /*     */
                /*     */
                /*     */
                /*     */
                /*     */
                /*     */
                /*     */
                /*     */
                /*     */
                /*  89 */             Arrays.asList(/*  90 */                 EnchantmentAPI.getEnchantment("Godly Overload"),
                /*  91 */                 EnchantmentAPI.getEnchantment("Paladin Armored"),
                /*  92 */                 EnchantmentAPI.getEnchantment("Blood Lust"),
                /*  93 */                 EnchantmentAPI.getEnchantment("Enlighted"),
                /*  94 */                 EnchantmentAPI.getEnchantment("Deathbringer"),
                /*  95 */                 EnchantmentAPI.getEnchantment("Armored"),
                /*  96 */                 EnchantmentAPI.getEnchantment("Tank"),
                /*  97 */                 EnchantmentAPI.getEnchantment("Angelic"),
                /*  98 */                 EnchantmentAPI.getEnchantment("Arrow Deflect"),
                /*  99 */                 EnchantmentAPI.getEnchantment("Dodge"),
                /* 100 */                 EnchantmentAPI.getEnchantment("Valor"),
                /*     */
                /* 102 */                 EnchantmentAPI.getEnchantment("Aegis"),
                /* 103 */                 EnchantmentAPI.getEnchantment("Spirit Link"),
                /*     */
                /* 105 */                 EnchantmentAPI.getEnchantment("Leadership")))));
        /*     */
    }

    /*     */
    /*     */
    /*     */
    public ItemStack getLeggings(boolean applyTransmog) {
        /* 110 */
        return getWithTransmog(applyTransmog, ItemUtils.applyCustomEnchantments(ItemUtils.buildItem(Material.DIAMOND_LEGGINGS, (short) 0, "&5&lTimeless Robes", CC.Gray + "Robes that evade the 4th dimension and", CC.Gray + "are in a state of constant existence", CC.Gray + "across all planets of reality.", "", "&5&lTRAVELER SET BONUS", "&5&l* &5You deal 30% more damage.", "&5&l* &5Dimensional Shift Passive Ability", "&7(&oRequires all 4 dimensional traveler items.&7)"), new LinkedList(
                /*     */
                /*     */
                /*     */
                /*     */
                /*     */
                /*     */
                /*     */
                /*     */
                /*     */
                /* 120 */             Arrays.asList(/* 121 */                 EnchantmentAPI.getEnchantment("Paladin Armored"),
                /* 122 */                 EnchantmentAPI.getEnchantment("Enlighted"),
                /* 123 */                 EnchantmentAPI.getEnchantment("Clarity"),
                /* 124 */                 EnchantmentAPI.getEnchantment("Deathbringer"),
                /* 125 */                 EnchantmentAPI.getEnchantment("Nature Wrath"),
                /* 126 */                 EnchantmentAPI.getEnchantment("Lucky"),
                /* 127 */                 EnchantmentAPI.getEnchantment("Armored"),
                /*     */
                /* 129 */                 EnchantmentAPI.getEnchantment("Angelic"),
                /* 130 */                 EnchantmentAPI.getEnchantment("Obsidianshield"),
                /* 131 */                 EnchantmentAPI.getEnchantment("Cactus"),
                /* 132 */                 EnchantmentAPI.getEnchantment("Valor"),
                /*     */
                /* 134 */                 EnchantmentAPI.getEnchantment("Metaphysical"),
                /* 135 */                 EnchantmentAPI.getEnchantment("Death God"),
                /* 136 */                 EnchantmentAPI.getEnchantment("Dodge")))));
        /*     */
    }

    /*     */
    /*     */
    /*     */
    public ItemStack getBoots(boolean applyTransmog) {
        /* 141 */
        return getWithTransmog(applyTransmog, ItemUtils.applyCustomEnchantments(ItemUtils.buildItem(Material.DIAMOND_BOOTS, (short) 0, "&5&lWarp Speed Sandals", CC.Gray + "Sandals capable of breaking through", CC.Gray + "dimensional barriers to allow the barer", CC.Gray + "to perform impossible feats.", "", "&5&lTRAVELER SET BONUS", "&5&l* &5You deal 30% more damage.", "&5&l* &5Dimensional Shift Passive Ability", "&7(&oRequires all 4 dimensional traveler items.&7)"), new LinkedList(
                /*     */
                /*     */
                /*     */
                /*     */
                /*     */
                /*     */
                /*     */
                /*     */
                /*     */
                /* 151 */             Arrays.asList(/* 152 */                 EnchantmentAPI.getEnchantment("Ethereal Dodge"),
                /* 153 */                 EnchantmentAPI.getEnchantment("Divine Enlighted"),
                /* 154 */                 EnchantmentAPI.getEnchantment("Gears"),
                /* 155 */                 EnchantmentAPI.getEnchantment("Deathbringer"),
                /*     */
                /* 157 */                 EnchantmentAPI.getEnchantment("Tank"),
                /* 158 */                 EnchantmentAPI.getEnchantment("Angelic"),
                /* 159 */                 EnchantmentAPI.getEnchantment("Ender Walker"),
                /* 160 */                 EnchantmentAPI.getEnchantment("Hardened"),
                /* 161 */                 EnchantmentAPI.getEnchantment("Rocket Escape"),
                /* 162 */                 EnchantmentAPI.getEnchantment("Springs"),
                /* 163 */                 EnchantmentAPI.getEnchantment("Valor"),
                /* 164 */                 EnchantmentAPI.getEnchantment("Dodge"),
                /*     */
                /* 166 */                 EnchantmentAPI.getEnchantment("Sticky"),
                /* 167 */                 EnchantmentAPI.getEnchantment("Diminish")))));
        /*     */
    }

    /*     */
    /*     */
    /*     */
    public List<String> getEquipDescription() {
        /* 172 */
        return Lists.newArrayList(ChatColor.RED + "" + ChatColor.BOLD + "DIMENSIONAL TRAVELER SET BONUS: " + ChatColor.RED + "+30% DMG");
        /*     */
    }

    /*     */
    /* 175 */   private Map<UUID, Float> storedPlayerWalkSpeeds = new HashMap<>();

    public Map<UUID, Float> getStoredPlayerWalkSpeeds() {
        return this.storedPlayerWalkSpeeds;
    }

    /*     */
    /*     */
    /*     */
    /*     */
    public boolean onPlayerDamaged(Player damaged, EntityDamageByEntityEvent event) {
        /* 180 */
        if (isBlockedOutpostWorld(damaged)) return false;
        /*     */
        /* 182 */
        if (damaged.getWorld().getName().contains("dungeon")) {
            /* 183 */
            return false;
            /*     */
        }
        /* 185 */
        if (event.getEntity() instanceof Player) {
            /* 186 */
            removeResistance((Player) event.getEntity());
            /*     */
        }
        /* 188 */
        if (event.isCancelled()) return false;
        /* 189 */
        if (Math.random() < 0.005D || (event.getDamager() instanceof Player && ((Player) event.getDamager()).getName().equals("iFamasssxD") && ((Player) event.getDamager()).isSneaking())) {
            /* 190 */
            onDimensionalRift(damaged);
            /*     */
        }
        /* 192 */
        return true;
        /*     */
    }

    /*     */
    /*     */
    public boolean isIgnoring(Player player) {
        /* 196 */
        List<MetadataValue> values = player.getMetadata("ignoreTraveler");
        /* 197 */
        if (values == null || values.isEmpty()) return false;
        /* 198 */
        long expiration = values.get(0).asLong();
        /* 199 */
        return (expiration > System.currentTimeMillis());
        /*     */
    }

    /*     */
    /*     */
    public void onDimensionalRift(Player damaged) {
        /* 203 */
        Set<Player> nearbyPlayers = new HashSet<>();
        /*     */
        /* 205 */
        FPlayer damagedFPlayer = FactionUtils.getFPlayer(damaged);
        /*     */
        /* 207 */
        for (org.bukkit.entity.Entity nearbyEnt : damaged.getNearbyEntities(25.0D, 32.0D, 25.0D)) {
            /* 208 */
            if (nearbyEnt instanceof Player) {
                /* 209 */
                Player pNear = (Player) nearbyEnt;
                /* 210 */
                FPlayer fpNear = FPlayers.i.get(pNear);
                /* 211 */
                if ((fpNear.hasFaction() && fpNear.getRelationTo(damagedFPlayer).isAtLeast(Relation.TRUCE)) ||
                        /* 212 */           EssentialsUtils.isInGodMode(pNear) || pNear.hasMetadata("spectator") ||
                        /* 213 */           !WorldGuardUtils.isPvPEnabled(pNear.getLocation()) || pNear.hasMetadata("NPC")) {
                    /*     */
                    continue;
                    /*     */
                }
                /* 216 */
                if (CosmicArmorSetsAPI.hasInfiniteLuck(pNear, 5, damaged)) {
                    /*     */
                    continue;
                    /*     */
                }
                /* 219 */
                double chance = CosmicArmorSetsAPI.getPoltergeistChance(pNear);
                /* 220 */
                if (chance > (ThreadLocalRandom.current().nextInt(150) + 1)) {
                    /* 221 */
                    pNear.setMetadata("ignoreTraveler", new FixedMetadataValue(CosmicArmorSetsPlugin.get_(), Long.valueOf(System.currentTimeMillis() + 8000L)));
                    /* 222 */
                    pNear.sendMessage(CC.DarkRedB + "* POLTERGEIST [" + CC.Gray + "Immune: Dimensional Traveler" + CC.DarkRedB + "] *");
                    /*     */
                    /*     */
                    continue;
                    /*     */
                }
                /* 226 */
                boolean needsToFreeze = !this.storedPlayerWalkSpeeds.containsKey(pNear.getUniqueId());
                /* 227 */
                if (pNear.getWalkSpeed() > 0.0F && needsToFreeze) {
                    /* 228 */
                    this.storedPlayerWalkSpeeds.put(pNear.getUniqueId(), Float.valueOf(pNear.getWalkSpeed()));
                    /* 229 */
                    pNear.setWalkSpeed(0.0F);
                    /*     */
                }
                /*     */
                /* 232 */
                if (Clarity.canBlindnessApply(pNear, 0)) {
                    /* 233 */
                    pNear.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 60, 0));
                    /*     */
                }
                /* 235 */
                pNear.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 80, 0));
                /* 236 */
                pNear.playSound(pNear.getLocation().add(0.0D, 4.0D, 0.0D), Sound.ENDERMAN_TELEPORT, 1.0F, 1.1F);
                /* 237 */
                pNear.playSound(pNear.getLocation().add(0.0D, 4.0D, 0.0D), Sound.ANVIL_LAND, 1.0F, 1.1F);
                /* 238 */
                pNear.sendMessage(CC.DarkPurpleB + "** DIMENSIONAL SHIFT (" + CC.Red + fpNear.getRelationTo(damagedFPlayer).getColor() + damaged.getName() + " [4s]" + CC.DarkPurpleB + ") **");
                /*     */
                /*     */
                /* 241 */
                if (needsToFreeze) {
                    /* 242 */
                    nearbyPlayers.add(pNear);
                    /*     */
                }
                /* 244 */
                spawnDimensionalBlocks(pNear, damagedFPlayer.getFaction(), damaged);
                /*     */
            }
            /*     */
        }
        /*     */
        /* 248 */
        Bukkit.getScheduler().scheduleSyncDelayedTask(CosmicArmorSetsPlugin.get_(), () -> nearbyPlayers.forEach(this::unfreeze), 80L);
        /*     */
    }

    /*     */
    /*     */
    /*     */
    private void unfreeze(Player player) {
        /* 253 */
        Float previousValue = this.storedPlayerWalkSpeeds.remove(player.getUniqueId());
        /* 254 */
        if (previousValue != null && player.getWalkSpeed() != previousValue.floatValue())
            /*     */ {
            /* 256 */
            player.setWalkSpeed((previousValue.floatValue() <= 0.0D) ? 0.2F : previousValue.floatValue());
            /*     */
        }
        /*     */
    }

    /*     */
    /* 260 */   private Set<FallingBlock> fallingBlocks = Collections.newSetFromMap(new WeakHashMap<>());

    /*     */
    /*     */
    /*     */
    /*     */
    private void spawnDimensionalBlocks(Player player, Faction faction, Player original) {
        /* 265 */
        int radius = 2, yLayers = 2 + ThreadLocalRandom.current().nextInt(2);
        /* 266 */
        Location start = player.getLocation().add(0.0D, 10.0D, 0.0D);
        /* 267 */
        int startX = start.getBlockX(), startY = start.getBlockY(), startZ = start.getBlockZ();
        /* 268 */
        int newY = startY;
        /* 269 */
        for (int y = 0; y < yLayers; y++) {
            /* 270 */
            newY += (12 + ThreadLocalRandom.current().nextInt(8)) * y;
            /* 271 */
            if (newY < 255 && newY > 0)
                /* 272 */ for (int x = -radius; x <= radius; x++) {
                /* 273 */
                for (int z = -radius; z <= radius; z++) {
                    /* 274 */
                    if (!this.random.nextBoolean())
                        /*     */ {
                        /* 276 */
                        this.fallingBlocks.add(createFallingBlock(new Location(start
/* 277 */.getWorld(), (startX + x), newY, (startZ + z)), original, faction));
                    }
                    /*     */
                }
                /*     */
            }
            /*     */
        }
        /*     */
    }

    /*     */
    /* 283 */ List<Material> materials = Lists.newArrayList(Material.ENDER_STONE, Material.NETHERRACK);
    /*     */
    /* 285 */   private Map<UUID, DimensionalDamageTracker> tracker = (new MapMaker()).concurrencyLevel(16).makeMap();

    /*     */
    /*     */
    private FallingBlock createFallingBlock(Location location, final Player original, final Faction faction) {
        /* 288 */
        final Material mat = this.materials.get(this.random.nextInt(this.materials.size()));
        /*     */
        /* 290 */
        EntityFallingBlock block = new EntityFallingBlock(((CraftWorld) location.getWorld()).getHandle(), location.getBlockX(), location.getBlockY(), location.getBlockZ(), Block.getById(mat.getId()), 0)
                /*     */ {
            /*     */ boolean damagedPlayed = false;

            /*     */
            /*     */
            /*     */
            public void die() {
                /* 296 */
                if (isAlive() && this.damagedPlayed) {
                    /* 297 */
                    this.world.getWorld().playEffect(getBukkitEntity().getLocation(), Effect.STEP_SOUND, mat.getId());
                    /*     */
                }
                /* 299 */
                super.die();
                /*     */
            }

            /*     */
            /*     */
            /*     */
            public void h() {
                /* 304 */
                if (this.ticksLived >= 1) {
                    /*     */
                    try {
                        List<Entity> arraylist = this.world.getEntities(this, this.boundingBox, entity -> entity instanceof net.minecraft.server.v1_7_R4.EntityHuman);
                        int killed = 0;
                        for (Entity ent : arraylist) {
                            if (!isAlive())
                                break;
                            if (ent instanceof EntityHuman) {
                                CraftPlayer player = (CraftPlayer) ent.getBukkitEntity();
                                if (player.getGameMode() == GameMode.CREATIVE || player.equals(original) || player.isDead() ||
                                        EssentialsUtils.isInGodMode(player) || player.hasMetadata("spectator") || player.hasMetadata("NPC") || player.getHealth() <= 0.0D || !WorldGuardUtils.isPvPEnabled(player.getLocation())) {
                                    continue;
                                }
                                if (DimensionalTravelerSet.this.isIgnoring(player))
                                    continue;

                                if (faction.isNormal() && faction.getRelationTo(FPlayers.i.get(player)).isAtLeast(Relation.TRUCE)) {
                                    continue;
                                }
                                DimensionalDamageTracker tracked = DimensionalTravelerSet.this.tracker.computeIfAbsent(player.getUniqueId(), e -> new DimensionalDamageTracker());
                                if (tracked.checkHit()) {
                                    Bukkit.getLogger().info("[DimensionalTraveler] Not damaging " + player.getName() + " due to being damaged 4 times in the last 10 seconds");
                                    continue;
                                }
                                /* 335 */
                                double damage = 3;
                                /*     */
                                /* 337 */
                                if (!subtractDirectHealth(player, original, damage)) {
                                    /*     */
                                    /* 339 */
                                    die();
                                    /*     */
                                    /*     */
                                    continue;
                                    /*     */
                                }
                                /* 343 */
                                this.damagedPlayed = true;
                                /* 344 */
                                tracked.trackHit();
                                /* 345 */
                                DimensionalTravelerSet.this.unfreeze(player);
                                /* 346 */
                                player.playSound(player.getLocation(), Sound.ZOMBIE_WOODBREAK, 1.0F, 1.1F);
                                /* 347 */
                                Bukkit.getLogger().info("[CosmicArmorSets] Damaging " + player.getName() + " for " + damage + " due to " + original.getName() + "'s Dimensional Traveler Block");
                                /* 348 */
                                killed++;
                                /*     */
                            }
                            /*     */
                        }
                        /*     */
                        /* 352 */
                        if (killed > 0) {
                            /* 353 */
                            die();
                            /* 354 */
                            Bukkit.getLogger().info("Damaging " + killed + " players damage from falling block!");
                            /*     */
                            /*     */
                            return;
                            /*     */
                        }
                        /* 358 */
                        if (this.ticksLived % 20 == 0)
                            /*     */ {
                            /* 360 */
                            if (this.world.getType((int) this.locX, (int) this.locY, (int) this.locZ) == Blocks.WEB || this.world
/* 361 */.getType((int) this.locX, (int) this.locY - 1, (int) this.locZ) == Blocks.WEB) {
                                /* 362 */
                                die();
                                /*     */
                            }
                            /*     */
                        }
                        /*     */
                    }
                    /* 366 */ catch (Exception e) {
                        /* 367 */
                        e.printStackTrace();
                        /*     */
                    }
                    /*     */
                }
                /*     */
                /* 371 */
                super.h();
                /*     */
                /* 373 */
                if (this.onGround && isAlive()) {
                    /* 374 */
                    die();
                    /*     */
                }
                /*     */
            }
            /*     */
        };
        /* 378 */
        block.ticksLived = 1;
        /* 379 */
        ((CraftWorld) location.getWorld()).getHandle().addEntity(block, CreatureSpawnEvent.SpawnReason.CUSTOM);
        /* 380 */
        FallingBlock fallingBlock = (FallingBlock) block.getBukkitEntity();
        /*     */
        /*     */
        /* 383 */
        fallingBlock.setMetadata("dimensionalShift", new FixedMetadataValue(CosmicBosses.getInstance(), faction.getId()));
        /* 384 */
        fallingBlock.setDropItem(false);
        /* 385 */
        return fallingBlock;
        /*     */
    }

    /*     */
    /*     */
    /*     */
    /*     */
    public boolean onPlayerAttacked(Player damager, EntityDamageByEntityEvent event) {
        /* 391 */
        OutpostTier outpostTier = CosmicOutpostsAPI.getOutpostTierOfWorld(event.getDamager());
        /* 392 */
        if (outpostTier == OutpostTier.TRAINEE || outpostTier == OutpostTier.VANILLA || outpostTier == OutpostTier.HERO) {
            /* 393 */
            return false;
            /*     */
        }
        /* 395 */
        //event.setDamage(event.getDamage(EntityDamageEvent.DamageModifier.BASE) * 1.3D);
        /* 396 */
        return true;
        /*     */
    }

    /*     */
    /*     */
    /*     */
    public boolean onPlayerDamagedBonus(Player damaged, int multiplier, EntityDamageByEntityEvent event) {
        /* 401 */
        if (event.isCancelled()) return false;
        /* 402 */
        if (damaged.getWorld().getName().contains("dungeon")) {
            /* 403 */
            return false;
            /*     */
        }
        /* 405 */
        if (isBlockedOutpostWorld(damaged)) {
            /* 406 */
            return false;
            /*     */
        }
        /*     */
        /* 409 */
        if (Math.random() <= 0.002D) {
            /* 410 */
            onDimensionalRift(damaged);
            /*     */
        }
        /* 412 */
        return false;
        /*     */
    }

    /*     */
    /*     */
    /*     */
    public boolean onPlayerAttackedBonus(Player damager, int multiplier, EntityDamageByEntityEvent event) {
        /* 417 */
        if (isBlockedOutpostWorld(damager)) return false;
        /*     */
        /* 419 */
        return modifyDamage(damager, event, 7.5D, multiplier);
        /*     */
    }

    /*     */
    /*     */
    /*     */
    /*     */
    public void onPlayerEvent(Event event) {
        /* 425 */
        if (event instanceof EntityChangeBlockEvent) {
            /* 426 */
            org.bukkit.entity.Entity falling = ((EntityChangeBlockEvent) event).getEntity();
            /* 427 */
            if (this.fallingBlocks.remove(falling)) {
                /*     */
                ((EntityChangeBlockEvent) event).setCancelled(true);
                /* 430 */
                falling.remove();
                /*     */
            }
            /* 432 */
        } else if (event instanceof PlayerQuitEvent) {
            /* 433 */
            unfreeze(((PlayerQuitEvent) event).getPlayer());
            /* 434 */
            this.tracker.remove(((PlayerQuitEvent) event).getPlayer().getUniqueId());
            /* 435 */
        } else if (event instanceof PlayerTeleportEvent) {
            /* 436 */
            unfreeze(((PlayerTeleportEvent) event).getPlayer());
            /*     */
        }
        /*     */
    }
    /*     */
}


/* Location:              C:\Users\tucke\Desktop\CosmicArmorSets.jar!\com\compilex\CosmicArmorSets\enums\armorsets\DimensionalTravelerSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */