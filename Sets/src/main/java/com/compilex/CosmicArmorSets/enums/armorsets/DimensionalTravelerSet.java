/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  com.compilex.CosmicOutposts.CosmicOutpostsAPI
 *  com.compilex.CosmicOutposts.outpost.enums.OutpostTier
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.cosmicpvp.cosmicutils.utils.EssentialsUtils
 *  com.cosmicpvp.cosmicutils.utils.FactionUtils
 *  com.cosmicpvp.cosmicutils.utils.WorldGuardUtils
 *  com.google.common.collect.Lists
 *  com.google.common.collect.MapMaker
 *  com.massivecraft.factions.FPlayer
 *  com.massivecraft.factions.FPlayers
 *  com.massivecraft.factions.Faction
 *  com.massivecraft.factions.iface.RelationParticipator
 *  com.massivecraft.factions.struct.Relation
 *  com.rit.sucy.CustomEnchantment
 *  com.rit.sucy.EnchantmentAPI
 *  me.ifamasssxd.cosmicbosses.CosmicBosses
 *  net.minecraft.server.v1_7_R4.Block
 *  net.minecraft.server.v1_7_R4.Blocks
 *  net.minecraft.server.v1_7_R4.Entity
 *  net.minecraft.server.v1_7_R4.EntityFallingBlock
 *  net.minecraft.server.v1_7_R4.EntityHuman
 *  net.minecraft.server.v1_7_R4.World
 *  org.arkhamnetwork.enchantments.enchantments.armor.Clarity
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Effect
 *  org.bukkit.GameMode
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.OfflinePlayer
 *  org.bukkit.Sound
 *  org.bukkit.craftbukkit.v1_7_R4.CraftWorld
 *  org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.FallingBlock
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event
 *  org.bukkit.event.entity.CreatureSpawnEvent$SpawnReason
 *  org.bukkit.event.entity.EntityChangeBlockEvent
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.event.player.PlayerQuitEvent
 *  org.bukkit.event.player.PlayerTeleportEvent
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.potion.PotionEffect
 *  org.bukkit.potion.PotionEffectType
 */
package com.compilex.CosmicArmorSets.enums.armorsets;

import com.compilex.CosmicArmorSets.CosmicArmorSetsAPI;
import com.compilex.CosmicArmorSets.CosmicArmorSetsPlugin;
import com.compilex.CosmicArmorSets.enums.ArmorSetInfo;
import com.compilex.CosmicArmorSets.enums.OtherPlayerEventListenerSet;
import com.compilex.CosmicArmorSets.utils.ItemUtils;
import com.compilex.CosmicOutposts.CosmicOutpostsAPI;
import com.compilex.CosmicOutposts.outpost.enums.OutpostTier;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.EssentialsUtils;
import com.cosmicpvp.cosmicutils.utils.FactionUtils;
import com.cosmicpvp.cosmicutils.utils.WorldGuardUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.MapMaker;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.iface.RelationParticipator;
import com.massivecraft.factions.struct.Relation;
import com.rit.sucy.CustomEnchantment;
import com.rit.sucy.EnchantmentAPI;
import me.ifamasssxd.cosmicbosses.CosmicBosses;
import net.minecraft.server.v1_8_R3.World;
import net.minecraft.server.v1_8_R3.*;
import org.arkhamnetwork.enchantments.enchantments.armor.Clarity;
import org.bukkit.Material;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class DimensionalTravelerSet
        extends ArmorSetInfo
        implements OtherPlayerEventListenerSet {
    private final Map<UUID, Float> storedPlayerWalkSpeeds = new HashMap<UUID, Float>();
    private final Set<FallingBlock> fallingBlocks = Collections.newSetFromMap(new WeakHashMap());
    private final Map<UUID, DimensionalDamageTracker> tracker = new MapMaker().concurrencyLevel(16).makeMap();
    List<Material> materials = Lists.newArrayList(Material.ENDER_STONE, Material.NETHERRACK);

    @Override
    public ItemStack getHelmet(boolean applyTransmog) {
        return this.getWithTransmog(applyTransmog, ItemUtils.applyCustomEnchantments(ItemUtils.buildItem(Material.DIAMOND_HELMET, (short) 0, "&5&lInterdimensional Hood", CC.Gray + "A hood that fades in and out of", CC.Gray + "existence inside the dimension of the owner.", "", "&5&lTRAVELER SET BONUS", "&5&l* &5You deal 30% more damage.", "&5&l* &5Dimensional Shift Passive Ability", "&7(&oRequires all 4 dimensional traveler items.&7)"), new LinkedList<CustomEnchantment>(Arrays.asList(EnchantmentAPI.getEnchantment("Alien Implants"), EnchantmentAPI.getEnchantment("Planetary Deathbringer"), EnchantmentAPI.getEnchantment("Drunk"), EnchantmentAPI.getEnchantment("Enlighted"), EnchantmentAPI.getEnchantment("Armored"), EnchantmentAPI.getEnchantment("Tank"), EnchantmentAPI.getEnchantment("Angelic"), EnchantmentAPI.getEnchantment("Hardened"), EnchantmentAPI.getEnchantment("Valor"), EnchantmentAPI.getEnchantment("Dodge"), EnchantmentAPI.getEnchantment("Guardians"), EnchantmentAPI.getEnchantment("Sticky"), EnchantmentAPI.getEnchantment("Implants"), EnchantmentAPI.getEnchantment("Repair Guard")))));
    }

    @Override
    public ItemStack getChestplate(boolean applyTransmog) {
        return this.getWithTransmog(applyTransmog, ItemUtils.applyCustomEnchantments(ItemUtils.buildItem(Material.DIAMOND_CHESTPLATE, (short) 0, "&5&lChestplate of Ad Infinitum", CC.Gray + "A cross-dimensional chestplate", CC.Gray + "with impossibly infinite density", CC.Gray + "and defensive properties.", "", "&5&lTRAVELER SET BONUS", "&5&l* &5You deal 30% more damage.", "&5&l* &5Dimensional Shift Passive Ability", "&7(&oRequires all 4 dimensional traveler items.&7)"), new LinkedList<CustomEnchantment>(Arrays.asList(EnchantmentAPI.getEnchantment("Godly Overload"), EnchantmentAPI.getEnchantment("Paladin Armored"), EnchantmentAPI.getEnchantment("Blood Lust"), EnchantmentAPI.getEnchantment("Enlighted"), EnchantmentAPI.getEnchantment("Deathbringer"), EnchantmentAPI.getEnchantment("Armored"), EnchantmentAPI.getEnchantment("Tank"), EnchantmentAPI.getEnchantment("Angelic"), EnchantmentAPI.getEnchantment("Arrow Deflect"), EnchantmentAPI.getEnchantment("Dodge"), EnchantmentAPI.getEnchantment("Valor"), EnchantmentAPI.getEnchantment("Aegis"), EnchantmentAPI.getEnchantment("Spirit Link"), EnchantmentAPI.getEnchantment("Leadership")))));
    }

    @Override
    public ItemStack getLeggings(boolean applyTransmog) {
        return this.getWithTransmog(applyTransmog, ItemUtils.applyCustomEnchantments(ItemUtils.buildItem(Material.DIAMOND_LEGGINGS, (short) 0, "&5&lTimeless Robes", CC.Gray + "Robes that evade the 4th dimension and", CC.Gray + "are in a state of constant existence", CC.Gray + "across all planets of reality.", "", "&5&lTRAVELER SET BONUS", "&5&l* &5You deal 30% more damage.", "&5&l* &5Dimensional Shift Passive Ability", "&7(&oRequires all 4 dimensional traveler items.&7)"), new LinkedList<CustomEnchantment>(Arrays.asList(EnchantmentAPI.getEnchantment("Paladin Armored"), EnchantmentAPI.getEnchantment("Enlighted"), EnchantmentAPI.getEnchantment("Clarity"), EnchantmentAPI.getEnchantment("Deathbringer"), EnchantmentAPI.getEnchantment("Nature Wrath"), EnchantmentAPI.getEnchantment("Lucky"), EnchantmentAPI.getEnchantment("Armored"), EnchantmentAPI.getEnchantment("Angelic"), EnchantmentAPI.getEnchantment("Obsidianshield"), EnchantmentAPI.getEnchantment("Cactus"), EnchantmentAPI.getEnchantment("Valor"), EnchantmentAPI.getEnchantment("Metaphysical"), EnchantmentAPI.getEnchantment("Death God"), EnchantmentAPI.getEnchantment("Dodge")))));
    }

    @Override
    public ItemStack getBoots(boolean applyTransmog) {
        return this.getWithTransmog(applyTransmog, ItemUtils.applyCustomEnchantments(ItemUtils.buildItem(Material.DIAMOND_BOOTS, (short) 0, "&5&lWarp Speed Sandals", CC.Gray + "Sandals capable of breaking through", CC.Gray + "dimensional barriers to allow the barer", CC.Gray + "to perform impossible feats.", "", "&5&lTRAVELER SET BONUS", "&5&l* &5You deal 30% more damage.", "&5&l* &5Dimensional Shift Passive Ability", "&7(&oRequires all 4 dimensional traveler items.&7)"), new LinkedList<CustomEnchantment>(Arrays.asList(EnchantmentAPI.getEnchantment("Ethereal Dodge"), EnchantmentAPI.getEnchantment("Divine Enlighted"), EnchantmentAPI.getEnchantment("Gears"), EnchantmentAPI.getEnchantment("Deathbringer"), EnchantmentAPI.getEnchantment("Tank"), EnchantmentAPI.getEnchantment("Angelic"), EnchantmentAPI.getEnchantment("Ender Walker"), EnchantmentAPI.getEnchantment("Hardened"), EnchantmentAPI.getEnchantment("Rocket Escape"), EnchantmentAPI.getEnchantment("Springs"), EnchantmentAPI.getEnchantment("Valor"), EnchantmentAPI.getEnchantment("Dodge"), EnchantmentAPI.getEnchantment("Sticky"), EnchantmentAPI.getEnchantment("Diminish")))));
    }

    @Override
    public List<String> getEquipDescription() {
        return Lists.newArrayList(ChatColor.RED + "" + ChatColor.BOLD + "DIMENSIONAL TRAVELER SET BONUS: " + ChatColor.RED + "+30% DMG");
    }

    public Map<UUID, Float> getStoredPlayerWalkSpeeds() {
        return this.storedPlayerWalkSpeeds;
    }

    @Override
    public boolean onPlayerDamaged(Player damaged, EntityDamageByEntityEvent event) {
        if (this.isBlockedOutpostWorld(damaged)) {
            return false;
        }
        if (damaged.getWorld().getName().contains("dungeon")) {
            return false;
        }
        if (event.getEntity() instanceof Player) {
            this.removeResistance((Player) event.getEntity());
        }
        if (event.isCancelled()) {
            return false;
        }
        if (Math.random() < 0.005 || event.getDamager() instanceof Player && event.getDamager().getName().equals("iFamasssxD") && ((Player) event.getDamager()).isSneaking()) {
            this.onDimensionalRift(damaged);
        }
        return true;
    }

    public boolean isIgnoring(Player player) {
        List values = player.getMetadata("ignoreTraveler");
        if (values == null || values.isEmpty()) {
            return false;
        }
        long expiration = ((MetadataValue) values.get(0)).asLong();
        return expiration > System.currentTimeMillis();
    }

    public void onDimensionalRift(Player damaged) {
        HashSet<Player> nearbyPlayers = new HashSet<Player>();
        FPlayer damagedFPlayer = FactionUtils.getFPlayer(damaged);
        for (org.bukkit.entity.Entity nearbyEnt : damaged.getNearbyEntities(25.0, 32.0, 25.0)) {
            boolean needsToFreeze;
            Player pNear;
            FPlayer fpNear;
            if (!(nearbyEnt instanceof Player) || (fpNear = FPlayers.i.get(pNear = (Player) nearbyEnt)).hasFaction() && fpNear.getRelationTo(damagedFPlayer).isAtLeast(Relation.TRUCE) || EssentialsUtils.isInGodMode(pNear) || pNear.hasMetadata("spectator") || !WorldGuardUtils.isPvPEnabled(pNear.getLocation()) || pNear.hasMetadata("NPC") || CosmicArmorSetsAPI.hasInfiniteLuck(pNear, 5, damaged))
                continue;
            double chance = CosmicArmorSetsAPI.getPoltergeistChance(pNear);
            if (chance > (double) (ThreadLocalRandom.current().nextInt(150) + 1)) {
                pNear.setMetadata("ignoreTraveler", new FixedMetadataValue(CosmicArmorSetsPlugin.get_(), System.currentTimeMillis() + 8000L));
                pNear.sendMessage(CC.DarkRedB + "* POLTERGEIST [" + CC.Gray + "Immune: Dimensional Traveler" + CC.DarkRedB + "] *");
                continue;
            }
            boolean bl = needsToFreeze = !this.storedPlayerWalkSpeeds.containsKey(pNear.getUniqueId());
            if (pNear.getWalkSpeed() > 0.0f && needsToFreeze) {
                this.storedPlayerWalkSpeeds.put(pNear.getUniqueId(), Float.valueOf(pNear.getWalkSpeed()));
            }
            if (Clarity.canBlindnessApply(pNear, 0)) {
                pNear.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 60, 0));
            }
            pNear.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 80, 0));
            pNear.playSound(pNear.getLocation().add(0.0, 4.0, 0.0), Sound.ENDERMAN_TELEPORT, 1.0f, 1.1f);
            pNear.playSound(pNear.getLocation().add(0.0, 4.0, 0.0), Sound.ANVIL_LAND, 1.0f, 1.1f);
            pNear.sendMessage(CC.DarkPurpleB + "** DIMENSIONAL SHIFT (" + CC.Red + fpNear.getRelationTo(damagedFPlayer).getColor() + damaged.getName() + " [4s]" + CC.DarkPurpleB + ") **");
            this.spawnDimensionalBlocks(pNear, damagedFPlayer.getFaction(), damaged);
        }
        Bukkit.getScheduler().scheduleSyncDelayedTask(CosmicArmorSetsPlugin.get_(), () -> nearbyPlayers.forEach(this::unfreeze), 80L);
    }

    private void unfreeze(Player player) {
        Float previousValue = this.storedPlayerWalkSpeeds.remove(player.getUniqueId());
        if (previousValue != null && player.getWalkSpeed() != previousValue.floatValue()) {
            player.setWalkSpeed((double) previousValue.floatValue() <= 0.0 ? 0.2f : previousValue.floatValue());
        }
    }

    private void spawnDimensionalBlocks(Player player, Faction faction, Player original) {
        int radius = 2;
        int yLayers = 2 + ThreadLocalRandom.current().nextInt(2);
        Location start = player.getLocation().add(0.0, 10.0, 0.0);
        int startX = start.getBlockX();
        int startY = start.getBlockY();
        int startZ = start.getBlockZ();
        int newY = startY;
        for (int y = 0; y < yLayers; ++y) {
            if ((newY += (12 + ThreadLocalRandom.current().nextInt(8)) * y) >= 255 || newY <= 0) continue;
            for (int x = -radius; x <= radius; ++x) {
                for (int z = -radius; z <= radius; ++z) {
                    if (this.random.nextBoolean()) continue;
                    this.fallingBlocks.add(this.createFallingBlock(new Location(start.getWorld(), startX + x, newY, startZ + z), original, faction));
                }
            }
        }
    }

    private FallingBlock createFallingBlock(Location location, final Player original, final Faction faction) {
        final Material mat = this.materials.get(this.random.nextInt(this.materials.size()));
        EntityFallingBlock block = new EntityFallingBlock(location, (World) location.getWorld(), location.getX(), location.getY(), location.getZ(), Block.getById(mat.getId()).getBlockData()) {
            boolean damagedPlayed = false;

            public void die() {
                if (isAlive() && this.damagedPlayed)
                    this.world.getWorld().playEffect(getBukkitEntity().getLocation(), Effect.STEP_SOUND, mat.getId());
                super.die();
            }

            public void h() {
                if (this.ticksLived >= 1)
                    try {
                        List<Entity> arraylist = this.world.getEntities(this, this.getBoundingBox());
                        int killed = 0;
                        for (Entity ent : arraylist) {
                            if (!isAlive())
                                break;
                            if (!(ent instanceof EntityHuman))
                                continue;
                            CraftPlayer player = (CraftPlayer) ent.getBukkitEntity();
                            if (player.getGameMode() == GameMode.CREATIVE || player.equals(original) || player.isDead() || EssentialsUtils.isInGodMode(player) || player.hasMetadata("spectator") || player.hasMetadata("NPC"))
                                continue;
                            if (player.getHealth() <= 0.0D)
                                continue;
                            if (!WorldGuardUtils.isPvPEnabled(player.getLocation()))
                                continue;
                            if (DimensionalTravelerSet.this.isIgnoring(player))
                                continue;
                            if (faction.isNormal() && faction.getRelationTo(FPlayers.i.get(player)).isAtLeast(Relation.TRUCE))
                                continue;
                            if (CosmicArmorSetsAPI.hasInfiniteLuck(player, 5, original))
                                continue;
                            DimensionalDamageTracker tracked = DimensionalTravelerSet.this.tracker.computeIfAbsent(player.getUniqueId(), e -> new DimensionalDamageTracker());
                            if (tracked.checkHit()) {
                                Bukkit.getLogger().info("[DimensionalTraveler] Not damaging " + player.getName() + " due to being damaged 4 times in the last 10 seconds");
                                continue;
                            }
                            double damage = Math.min(player.getMaxHealth(), 44.0D) * 0.15D;
                            if (!ArmorSetInfo.subtractDirectHealth(player, original, damage)) {
                                die();
                                continue;
                            }
                            this.damagedPlayed = true;
                            tracked.trackHit();
                            DimensionalTravelerSet.this.unfreeze(player);
                            player.playSound(player.getLocation(), Sound.ZOMBIE_WOODBREAK, 1.0F, 1.1F);
                            Bukkit.getLogger().info("[CosmicArmorSets] Damaging " + player.getName() + " for " + damage + " due to " + original.getName() + "'s Dimensional Traveler Block");
                            killed++;
                        }
                        if (killed > 0) {
                            die();
                            Bukkit.getLogger().info("Damaging " + killed + " players damage from falling block!");
                            return;
                        }
                        if (this.ticksLived % 20 == 0 && (this.world.getType(new BlockPosition(this.locX, (int) this.locY, (int) this.locZ)) == Blocks.WEB || this.world.getType(new BlockPosition(this.locX, (int) this.locY - 1, (int) this.locZ)) == Blocks.WEB))
                            die();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                super.h();
                if (this.onGround && isAlive())
                    die();
            }
        };
        block.ticksLived = 1;
        ((CraftWorld) location.getWorld()).getHandle().addEntity(block, CreatureSpawnEvent.SpawnReason.CUSTOM);
        FallingBlock fallingBlock = (FallingBlock) block.getBukkitEntity();
        fallingBlock.setMetadata("dimensionalShift", new FixedMetadataValue(CosmicBosses.getInstance(), faction.getId()));
        fallingBlock.setDropItem(false);
        return fallingBlock;
    }

    @Override
    public boolean onPlayerAttacked(Player damager, EntityDamageByEntityEvent event) {
        OutpostTier outpostTier = CosmicOutpostsAPI.getOutpostTierOfWorld(event.getDamager());
        return outpostTier != OutpostTier.TRAINEE && outpostTier != OutpostTier.VANILLA && outpostTier != OutpostTier.HERO;
    }

    @Override
    public boolean onPlayerDamagedBonus(Player damaged, int multiplier, EntityDamageByEntityEvent event) {
        if (event.isCancelled()) {
            return false;
        }
        if (damaged.getWorld().getName().contains("dungeon")) {
            return false;
        }
        if (this.isBlockedOutpostWorld(damaged)) {
            return false;
        }
        if (Math.random() <= 0.002) {
            this.onDimensionalRift(damaged);
        }
        return false;
    }

    @Override
    public boolean onPlayerAttackedBonus(Player damager, int multiplier, EntityDamageByEntityEvent event) {
        if (this.isBlockedOutpostWorld(damager)) {
            return false;
        }
        return this.modifyDamage(damager, event, 4.5, multiplier);
    }

    @Override
    public void onPlayerEvent(Event event) {
        if (event instanceof EntityChangeBlockEvent) {
            org.bukkit.entity.Entity falling = ((EntityChangeBlockEvent) event).getEntity();
            if (this.fallingBlocks.remove(falling)) {
                ((EntityChangeBlockEvent) event).setCancelled(true);
                falling.remove();
            }
        } else if (event instanceof PlayerQuitEvent) {
            this.unfreeze(((PlayerQuitEvent) event).getPlayer());
            this.tracker.remove(((PlayerQuitEvent) event).getPlayer().getUniqueId());
        } else if (event instanceof PlayerTeleportEvent) {
            this.unfreeze(((PlayerTeleportEvent) event).getPlayer());
        }
    }
}

