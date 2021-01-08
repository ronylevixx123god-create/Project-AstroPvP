/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  com.compilex.CosmicOutposts.CosmicOutpostsAPI
 *  com.compilex.CosmicOutposts.outpost.enums.OutpostTier
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.cosmicpvp.cosmicutils.utils.WorldGuardUtils
 *  com.google.common.collect.Lists
 *  com.massivecraft.factions.FPlayer
 *  com.massivecraft.factions.FPlayers
 *  com.massivecraft.factions.iface.RelationParticipator
 *  com.massivecraft.factions.struct.Relation
 *  com.rit.sucy.CustomEnchantment
 *  com.rit.sucy.EnchantmentAPI
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.OfflinePlayer
 *  org.bukkit.Sound
 *  org.bukkit.block.Block
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.event.entity.EntityDamageEvent$DamageModifier
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.scheduler.BukkitRunnable
 */
package com.compilex.CosmicArmorSets.enums.armorsets;

import com.compilex.CosmicArmorSets.CosmicArmorSetsPlugin;
import com.compilex.CosmicArmorSets.enums.ArmorSetInfo;
import com.compilex.CosmicArmorSets.enums.OtherPlayerEventListenerSet;
import com.compilex.CosmicArmorSets.utils.ItemUtils;
import com.compilex.CosmicOutposts.CosmicOutpostsAPI;
import com.compilex.CosmicOutposts.outpost.enums.OutpostTier;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.WorldGuardUtils;
import com.google.common.collect.Lists;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.iface.RelationParticipator;
import com.massivecraft.factions.struct.Relation;
import com.rit.sucy.EnchantmentAPI;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;
import java.util.stream.Collectors;

public class AstroSet
        extends ArmorSetInfo
        implements OtherPlayerEventListenerSet {
    public static List<Block> blockList = new ArrayList<Block>();

    @Override
    public ItemStack getHelmet(boolean applyTransmog) {
        return this.getWithTransmog(applyTransmog, ItemUtils.applyCustomEnchantments(ItemUtils.buildItem(Material.DIAMOND_HELMET, (short) 0, "&a&lAstro Hood", CC.Gray + "The Astro Hood", "", "&a&lASTRO SET BONUS", "&a&l* &aYou deal 30% more damage.", "&a&l* &aAstro Killer Passive Ability", "&a&l* &aGears IV.", "&a&l* &aYou take 10% less damage.", "&7(&oRequires all 4 Astro items.&7)"), new LinkedList<>(Arrays.asList(EnchantmentAPI.getEnchantment("Alien Implants"), EnchantmentAPI.getEnchantment("Planetary Deathbringer"), EnchantmentAPI.getEnchantment("Drunk"), EnchantmentAPI.getEnchantment("Enlighted"), EnchantmentAPI.getEnchantment("Armored"), EnchantmentAPI.getEnchantment("Tank"), EnchantmentAPI.getEnchantment("Angelic"), EnchantmentAPI.getEnchantment("Hardened"), EnchantmentAPI.getEnchantment("Valor"), EnchantmentAPI.getEnchantment("Dodge"), EnchantmentAPI.getEnchantment("Guardians"), EnchantmentAPI.getEnchantment("Sticky"), EnchantmentAPI.getEnchantment("Implants"), EnchantmentAPI.getEnchantment("Repair Guard")))));
    }

    @Override
    public ItemStack getChestplate(boolean applyTransmog) {
        return this.getWithTransmog(applyTransmog, ItemUtils.applyCustomEnchantments(ItemUtils.buildItem(Material.DIAMOND_CHESTPLATE, (short) 0, "&a&lAstro Gauntlet", CC.Gray + "The Astro Gauntlet", "", "&a&lASTRO SET BONUS", "&a&l* &aYou deal 30% more damage.", "&a&l* &aAstro Killer Passive Ability", "&a&l* &aGears IV.", "&a&l* &aYou take 10% less damage.", "&7(&oRequires all 4 Astro items.&7)"), new LinkedList<>(Arrays.asList(EnchantmentAPI.getEnchantment("Godly Overload"), EnchantmentAPI.getEnchantment("Paladin Armored"), EnchantmentAPI.getEnchantment("Blood Lust"), EnchantmentAPI.getEnchantment("Enlighted"), EnchantmentAPI.getEnchantment("Deathbringer"), EnchantmentAPI.getEnchantment("Armored"), EnchantmentAPI.getEnchantment("Tank"), EnchantmentAPI.getEnchantment("Angelic"), EnchantmentAPI.getEnchantment("Arrow Deflect"), EnchantmentAPI.getEnchantment("Dodge"), EnchantmentAPI.getEnchantment("Valor"), EnchantmentAPI.getEnchantment("Aegis"), EnchantmentAPI.getEnchantment("Spirit Link"), EnchantmentAPI.getEnchantment("Leadership")))));
    }

    @Override
    public ItemStack getLeggings(boolean applyTransmog) {
        return this.getWithTransmog(applyTransmog, ItemUtils.applyCustomEnchantments(ItemUtils.buildItem(Material.DIAMOND_LEGGINGS, (short) 0, "&a&lAstro Robe", CC.Gray + "The Astro Robe", "", "&a&lASTRO SET BONUS", "&a&l* &aYou deal 30% more damage.", "&a&l* &aAstro Killer Passive Ability", "&a&l* &aGears IV.", "&a&l* &aYou take 10% less damage.", "&7(&oRequires all 4 Astro items.&7)"), new LinkedList<>(Arrays.asList(EnchantmentAPI.getEnchantment("Paladin Armored"), EnchantmentAPI.getEnchantment("Enlighted"), EnchantmentAPI.getEnchantment("Clarity"), EnchantmentAPI.getEnchantment("Deathbringer"), EnchantmentAPI.getEnchantment("Nature Wrath"), EnchantmentAPI.getEnchantment("Lucky"), EnchantmentAPI.getEnchantment("Armored"), EnchantmentAPI.getEnchantment("Angelic"), EnchantmentAPI.getEnchantment("Obsidianshield"), EnchantmentAPI.getEnchantment("Cactus"), EnchantmentAPI.getEnchantment("Valor"), EnchantmentAPI.getEnchantment("Metaphysical"), EnchantmentAPI.getEnchantment("Death God"), EnchantmentAPI.getEnchantment("Dodge")))));
    }

    @Override
    public ItemStack getBoots(boolean applyTransmog) {
        return this.getWithTransmog(applyTransmog, ItemUtils.applyCustomEnchantments(ItemUtils.buildItem(Material.DIAMOND_BOOTS, (short) 0, "&a&lAstro Kicks", CC.Gray + "The Astro Kicks", "", "&a&lASTRO SET BONUS", "&a&l* &aYou deal 30% more damage.", "&a&l* &aAstro Killer Passive Ability", "&a&l* &aGears IV.", "&a&l* &aYou take 10% less damage.", "&7(&oRequires all 4 Astro items.&7)"), new LinkedList<>(Arrays.asList(EnchantmentAPI.getEnchantment("Ethereal Dodge"), EnchantmentAPI.getEnchantment("Divine Enlighted"), EnchantmentAPI.getEnchantment("Gears"), EnchantmentAPI.getEnchantment("Deathbringer"), EnchantmentAPI.getEnchantment("Tank"), EnchantmentAPI.getEnchantment("Angelic"), EnchantmentAPI.getEnchantment("Ender Walker"), EnchantmentAPI.getEnchantment("Hardened"), EnchantmentAPI.getEnchantment("Rocket Escape"), EnchantmentAPI.getEnchantment("Springs"), EnchantmentAPI.getEnchantment("Valor"), EnchantmentAPI.getEnchantment("Dodge"), EnchantmentAPI.getEnchantment("Sticky"), EnchantmentAPI.getEnchantment("Diminish")))));
    }

    @Override
    public List<String> getEquipDescription() {
        return Lists.newArrayList(ChatColor.GREEN + "" + ChatColor.BOLD + "&a&lASTRO SET BONUS", "&a&l* &aYou deal 30% more damage.", "&a&l* &aAstro Killer Passive Ability", "&a&l* &aGears IV.", "&a&l* &aYou take 10% less damage.");
    }

    @Override
    public boolean onPlayerDamaged(Player damaged, EntityDamageByEntityEvent event) {
        if (this.isBlockedOutpostWorld(damaged)) {
            return false;
        }
        if (event.getEntity() instanceof Player) {
            this.removeResistance((Player) event.getEntity());
        }
        if (event.isCancelled()) {
            return false;
        }
        this.removeResistance(damaged);
        event.setDamage(event.getDamage(EntityDamageEvent.DamageModifier.BASE) * 0.8);
        return true;
    }

    @Override
    public boolean onPlayerAttacked(Player damager, EntityDamageByEntityEvent event) {
        if (Math.random() < 0.01) {
            OutpostTier outpostTier = CosmicOutpostsAPI.getOutpostTierOfWorld(event.getDamager());
            if (outpostTier == OutpostTier.TRAINEE || outpostTier == OutpostTier.VANILLA || outpostTier == OutpostTier.HERO) {
                return false;
            }
            event.setDamage(event.getDamage(EntityDamageEvent.DamageModifier.BASE) * 1.28);
            if (event.getEntity() instanceof Player && event.getEntity().getLocation().distance(damager.getLocation()) <= 2.0 && ((Player) event.getEntity()).getHealth() < damager.getHealth()) {
                if (damager.hasMetadata("FalloutAbility") && System.currentTimeMillis() - damager.getMetadata("FalloutAbility").get(0).asLong() <= 120000L) {
                    return false;
                }
                damager.setMetadata("FalloutAbility", new FixedMetadataValue(CosmicArmorSetsPlugin.get_(), System.currentTimeMillis()));
                FPlayer fp = FPlayers.i.get(damager);
                for (Entity nearbyEnt : damager.getNearbyEntities(32.0, 64.0, 32.0)) {
                    if (nearbyEnt instanceof Player) {
                        Player pNear = (Player) nearbyEnt;
                        FPlayer fpNear = FPlayers.i.get(pNear);
                        if (fpNear.getRelationTo(fp).isAtLeast(Relation.TRUCE) || !WorldGuardUtils.isPvPEnabled(pNear.getLocation()))
                            continue;
                        pNear.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "** ASTRO KILLER (" + ChatColor.GRAY + fp.getNameAsync() + " [6s]" + ChatColor.GREEN + ChatColor.BOLD + ") **");
                    }
                    damager.getLocation().getWorld().playSound(damager.getLocation(), Sound.GHAST_SCREAM, 1.0f, 1.0f);
                    this.activateFalloutKillerAbility(damager, (Player) event.getEntity());
                }
            }
        }
        return true;
    }

    @Override
    public boolean onPlayerDamagedBonus(Player damaged, int multiplier, EntityDamageByEntityEvent event) {
        if (event.isCancelled()) {
            return false;
        }
        if (this.isBlockedOutpostWorld(damaged)) {
            return false;
        }
        this.modifyDamage(damaged, event, -5.0, multiplier);
        return false;
    }

    @Override
    public boolean onPlayerAttackedBonus(Player damager, int applied, EntityDamageByEntityEvent event) {
        if (Math.random() < 0.01) {
            if (this.isBlockedOutpostWorld(damager)) {
                return false;
            }
            if (event.getDamager().getWorld().getName().equals("world_duels")) {
                return false;
            }
            this.modifyDamage(damager, event, 5.0, applied);
            if (event.getEntity() instanceof Player && event.getEntity().getLocation().distance(damager.getLocation()) <= 2.0 && ((Player) event.getEntity()).getHealth() < damager.getHealth()) {
                if (damager.hasMetadata("FalloutAbility") && System.currentTimeMillis() - damager.getMetadata("FalloutAbility").get(0).asLong() <= 65000L) {
                    return false;
                }
                damager.setMetadata("FalloutAbility", new FixedMetadataValue(CosmicArmorSetsPlugin.get_(), System.currentTimeMillis()));
                FPlayer fp = FPlayers.i.get(damager);
                for (Entity nearbyEnt : damager.getNearbyEntities(32.0, 64.0, 32.0)) {
                    Player pNear;
                    FPlayer fpNear;
                    if (!(nearbyEnt instanceof Player) || (fpNear = FPlayers.i.get(pNear = (Player) nearbyEnt)).getRelationTo(fp).isAtLeast(Relation.TRUCE) || !WorldGuardUtils.isPvPEnabled(pNear.getLocation()))
                        continue;
                    pNear.sendMessage("");
                    pNear.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "** ASTRO KILLER (" + ChatColor.GRAY + fp.getNameAsync() + " [6s]" + ChatColor.GREEN + ChatColor.BOLD + ") **");
                    pNear.sendMessage("");
                }
                damager.getLocation().getWorld().playSound(damager.getLocation(), Sound.GHAST_SCREAM, 1.0f, 1.0f);
                this.activateFalloutKillerAbility(damager, (Player) event.getEntity());
            }
        }
        return true;
    }

    @Override
    public void onEquip(Player player) {
        EnchantmentAPI.getEnchantment("Gears").applyEquipEffect(player, 4);
    }

    @Override
    public void onUnequip(Player player) {
        EnchantmentAPI.getEnchantment("Gears").applyUnequipEffect(player, 4);
    }

    public Block setBlock(Location location, Material material) {
        if (location.getBlock().getType() == Material.AIR) {
            location.getBlock().setType(material);
            return location.getBlock();
        }
        return null;
    }

    public List<Block> setOutlinedBlocks(Location location, Material material) {
        ArrayList<Block> blocks = new ArrayList<Block>();
        blocks.add(this.setBlock(location.clone().add(2.0, 0.0, 0.0), material));
        blocks.add(this.setBlock(location.clone().add(-2.0, 0.0, 0.0), material));
        blocks.add(this.setBlock(location.clone().add(0.0, 0.0, 2.0), material));
        blocks.add(this.setBlock(location.clone().add(0.0, 0.0, -2.0), material));
        blocks.add(this.setBlock(location.clone().add(2.0, 0.0, 2.0), material));
        blocks.add(this.setBlock(location.clone().add(-2.0, 0.0, 2.0), material));
        blocks.add(this.setBlock(location.clone().add(2.0, 0.0, -2.0), material));
        blocks.add(this.setBlock(location.clone().add(-2.0, 0.0, -2.0), material));
        blocks.add(this.setBlock(location.clone().add(-2.0, 0.0, -1.0), material));
        blocks.add(this.setBlock(location.clone().add(1.0, 0.0, 2.0), material));
        blocks.add(this.setBlock(location.clone().add(-1.0, 0.0, 2.0), material));
        blocks.add(this.setBlock(location.clone().add(2.0, 0.0, 1.0), material));
        blocks.add(this.setBlock(location.clone().add(2.0, 0.0, -1.0), material));
        blocks.add(this.setBlock(location.clone().add(-1.0, 0.0, -2.0), material));
        blocks.add(this.setBlock(location.clone().add(1.0, 0.0, -2.0), material));
        blocks.add(this.setBlock(location.clone().add(-2.0, 0.0, 1.0), material));
        blocks.add(this.setBlock(location.clone().add(-1.0, 0.0, -2.0), material));
        blocks.add(this.setBlock(location.clone().add(1.0, 0.0, -2.0), material));
        return blocks;
    }

    public List<Block> setInsideBlocks(Location location, Material material) {
        ArrayList<Block> blocks = new ArrayList<Block>();
        blocks.add(this.setBlock(location, material));
        blocks.add(this.setBlock(location.clone().add(1.0, 0.0, 0.0), material));
        blocks.add(this.setBlock(location.clone().add(-1.0, 0.0, 0.0), material));
        blocks.add(this.setBlock(location.clone().add(0.0, 0.0, 1.0), material));
        blocks.add(this.setBlock(location.clone().add(0.0, 0.0, -1.0), material));
        blocks.add(this.setBlock(location.clone().add(1.0, 0.0, 1.0), material));
        blocks.add(this.setBlock(location.clone().add(-1.0, 0.0, 1.0), material));
        blocks.add(this.setBlock(location.clone().add(-1.0, 0.0, -1.0), material));
        blocks.add(this.setBlock(location.clone().add(1.0, 0.0, -1.0), material));
        return blocks;
    }

    public void activateFalloutKillerAbility(Player player, final Player target) {
        final ArrayList<Block> blocks = new ArrayList<Block>();
        blocks.addAll(this.setOutlinedBlocks(player.getLocation().add(0.0, -1.0, 0.0), Material.DIAMOND_BLOCK));
        blocks.addAll(this.setInsideBlocks(player.getLocation().add(0.0, -1.0, 0.0), Material.SOUL_SAND));
        blocks.addAll(this.setOutlinedBlocks(player.getLocation().clone().add(0.0, 0.0, 0.0), Material.IRON_FENCE));
        blocks.addAll(this.setOutlinedBlocks(player.getLocation().clone().add(0.0, 1.0, 0.0), Material.IRON_FENCE));
        blocks.addAll(this.setOutlinedBlocks(player.getLocation().clone().add(0.0, 2.0, 0.0), Material.IRON_FENCE));
        blocks.addAll(this.setOutlinedBlocks(player.getLocation().clone().add(0.0, 3.0, 0.0), Material.DIAMOND_BLOCK));
        blocks.addAll(this.setInsideBlocks(player.getLocation().clone().add(0.0, 3.0, 0.0), Material.DIAMOND_BLOCK));
        blockList.addAll(blocks);
        new BukkitRunnable() {
            int counter = 0;

            public void run() {
                ++this.counter;
                target.damage(3.5);
                if (this.counter == 6) {
                    List<Block> newBlocks = blocks.stream().filter(Objects::nonNull).collect(Collectors.toList());
                    for (Block block : newBlocks) {
                        block.setType(Material.AIR);
                    }
                    blockList.removeAll(newBlocks);
                    this.cancel();
                }
            }
        }.runTaskTimer(CosmicArmorSetsPlugin.get_(), 0L, 20L);
    }

    @Override
    public void onPlayerEvent(Event paramEvent) {
    }
}

