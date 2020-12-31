/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.cosmicpvp.cosmicutils.utils.ItemBuilder
 *  com.cosmicpvp.cosmicutils.utils.NBTWrapper
 *  com.cosmicpvp.cosmicutils.utils.UpgradeUtils
 *  com.cosmicpvp.cosmicutils.utils.WorldGuardUtils
 *  com.google.common.collect.Lists
 *  com.massivecraft.factions.FPlayer
 *  com.massivecraft.factions.FPlayers
 *  com.massivecraft.factions.iface.RelationParticipator
 *  com.massivecraft.factions.struct.Relation
 *  com.rit.sucy.CustomEnchantment
 *  com.rit.sucy.EnchantmentAPI
 *  me.astropvp.astropvpskins.AstroItemSkinsAPI
 *  me.astropvp.astropvpskins.enums.SkinType
 *  org.arkhamnetwork.Arkkit.libs.ParticleEffect
 *  org.arkhamnetwork.enchantments.enchantments.armor.Phoenix
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.OfflinePlayer
 *  org.bukkit.Sound
 *  org.bukkit.enchantments.Enchantment
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.event.entity.EntityDamageEvent$DamageModifier
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.plugin.Plugin
 */
package com.compilex.CosmicArmorSets.enums.armorsets;

import com.compilex.CosmicArmorSets.CosmicArmorSetsAPI;
import com.compilex.CosmicArmorSets.CosmicArmorSetsPlugin;
import com.compilex.CosmicArmorSets.enums.ArmorSetInfo;
import com.compilex.CosmicArmorSets.enums.WeaponedArmorSet;
import com.compilex.CosmicArmorSets.utils.ItemUtils;
import com.compilex.CosmicArmorSets.utils.LocationUtils;
import com.cosmicpvp.cosmicutils.utils.*;
import com.google.common.collect.Lists;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.iface.RelationParticipator;
import com.massivecraft.factions.struct.Relation;
import com.rit.sucy.CustomEnchantment;
import com.rit.sucy.EnchantmentAPI;
import me.astropvp.astropvpskins.AstroItemSkinsAPI;
import me.astropvp.astropvpskins.enums.SkinType;
import org.arkhamnetwork.Arkkit.libs.ParticleEffect;
import org.arkhamnetwork.enchantments.enchantments.armor.Phoenix;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class MotherOfYijki
        extends ArmorSetInfo
        implements WeaponedArmorSet {
    @Override
    public ItemStack getHelmet(boolean applyTransmog) {
        return this.getWithTransmog(applyTransmog, ItemUtils.applyCustomEnchantments(ItemUtils.buildItem(Material.DIAMOND_HELMET, (short) 0, "&f&lMask of Yijki the Destroyer of Worlds", "&f&oThe god-mask of Yijki, imbued with", "&f&othe power of absolute destruction.", "", "&f&lYIJKI SET BONUS", "&f&l* &fEnemies deal 30% less damage to you.", "&f&l* &fRevenge of Yijki Passive Ability", "&7(&oRequires all 4 yijki items.&7)"), new LinkedList<Object>(Arrays.asList((Object[]) new CustomEnchantment[]{EnchantmentAPI.getEnchantment((String) "Planetary Deathbringer"), EnchantmentAPI.getEnchantment((String) "Drunk"), EnchantmentAPI.getEnchantment((String) "Enlighted"), EnchantmentAPI.getEnchantment((String) "Armored"), EnchantmentAPI.getEnchantment((String) "Tank"), EnchantmentAPI.getEnchantment((String) "Angelic"), EnchantmentAPI.getEnchantment((String) "Hardened"), EnchantmentAPI.getEnchantment((String) "Valor"), EnchantmentAPI.getEnchantment((String) "Dodge"), EnchantmentAPI.getEnchantment((String) "Guardians"), EnchantmentAPI.getEnchantment((String) "Sticky"), EnchantmentAPI.getEnchantment((String) "Implants"), EnchantmentAPI.getEnchantment((String) "Repair Guard")}))));
    }

    @Override
    public ItemStack getChestplate(boolean applyTransmog) {
        return this.getWithTransmog(applyTransmog, ItemUtils.applyCustomEnchantments(ItemUtils.buildItem(Material.DIAMOND_CHESTPLATE, (short) 0, "&f&lMantel of Yijki the Destroyer of Worlds", "&f&oThe forebidden mantel of Yijki,", "&f&owoven from the dust of fallen stars.", "", "&f&lYIJKI SET BONUS", "&f&l* &fEnemies deal 30% less damage to you.", "&f&l* &fRevenge of Yijki Passive Ability", "&7(&oRequires all 4 yijki items.&7)"), new LinkedList<Object>(Arrays.asList((Object[]) new CustomEnchantment[]{EnchantmentAPI.getEnchantment((String) "Godly Overload"), EnchantmentAPI.getEnchantment((String) "Blood Lust"), EnchantmentAPI.getEnchantment((String) "Enlighted"), EnchantmentAPI.getEnchantment((String) "Deathbringer"), EnchantmentAPI.getEnchantment((String) "Armored"), EnchantmentAPI.getEnchantment((String) "Tank"), EnchantmentAPI.getEnchantment((String) "Angelic"), EnchantmentAPI.getEnchantment((String) "Arrow Deflect"), EnchantmentAPI.getEnchantment((String) "Dodge"), EnchantmentAPI.getEnchantment((String) "Valor"), EnchantmentAPI.getEnchantment((String) "Hardened"), EnchantmentAPI.getEnchantment((String) "Aegis"), EnchantmentAPI.getEnchantment((String) "Spirit Link"), EnchantmentAPI.getEnchantment((String) "Blood Link"), EnchantmentAPI.getEnchantment((String) "Leadership")}))));
    }

    @Override
    public ItemStack getLeggings(boolean applyTransmog) {
        return this.getWithTransmog(applyTransmog, ItemUtils.applyCustomEnchantments(ItemUtils.buildItem(Material.DIAMOND_LEGGINGS, (short) 0, "&f&lRobeset of Yijki the Destroyer of Worlds", "&f&oThe fabled robes of Yijki,", "&f&ohalf angelic, half demonic.", "", "&f&lYIJKI SET BONUS", "&f&l* &fEnemies deal 30% less damage to you.", "&f&l* &fRevenge of Yijki Passive Ability", "&7(&oRequires all 4 yijki items.&7)"), new LinkedList<Object>(Arrays.asList((Object[]) new CustomEnchantment[]{EnchantmentAPI.getEnchantment((String) "Enlighted"), EnchantmentAPI.getEnchantment((String) "Clarity"), EnchantmentAPI.getEnchantment((String) "Deathbringer"), EnchantmentAPI.getEnchantment((String) "Nature Wrath"), EnchantmentAPI.getEnchantment((String) "Lucky"), EnchantmentAPI.getEnchantment((String) "Armored"), EnchantmentAPI.getEnchantment((String) "Tank"), EnchantmentAPI.getEnchantment((String) "Angelic"), EnchantmentAPI.getEnchantment((String) "Obsidianshield"), EnchantmentAPI.getEnchantment((String) "Cactus"), EnchantmentAPI.getEnchantment((String) "Valor"), EnchantmentAPI.getEnchantment((String) "Hardened"), EnchantmentAPI.getEnchantment((String) "Metaphysical"), EnchantmentAPI.getEnchantment((String) "Death God"), EnchantmentAPI.getEnchantment((String) "Dodge")}))));
    }

    @Override
    public ItemStack getBoots(boolean applyTransmog) {
        return this.getWithTransmog(applyTransmog, ItemUtils.applyCustomEnchantments(ItemUtils.buildItem(Material.DIAMOND_BOOTS, (short) 0, "&f&lFootwraps of Yijki the Destroyer of Worlds", "&f&oLight as a feather, heavy as", "&f&oa mountain - the footwraps of Yijki.", "", "&f&lYIJKI SET BONUS", "&f&l* &fEnemies deal 30% less damage to you.", "&f&l* &fRevenge of Yijki Passive Ability", "&7(&oRequires all 4 yijki items.&7)"), new LinkedList<Object>(Arrays.asList((Object[]) new CustomEnchantment[]{EnchantmentAPI.getEnchantment((String) "Divine Enlighted"), EnchantmentAPI.getEnchantment((String) "Gears"), EnchantmentAPI.getEnchantment((String) "Deathbringer"), EnchantmentAPI.getEnchantment((String) "Armored"), EnchantmentAPI.getEnchantment((String) "Tank"), EnchantmentAPI.getEnchantment((String) "Angelic"), EnchantmentAPI.getEnchantment((String) "Ender Walker"), EnchantmentAPI.getEnchantment((String) "Hardened"), EnchantmentAPI.getEnchantment((String) "Rocket Escape"), EnchantmentAPI.getEnchantment((String) "Springs"), EnchantmentAPI.getEnchantment((String) "Valor"), EnchantmentAPI.getEnchantment((String) "Dodge"), EnchantmentAPI.getEnchantment((String) "Guardians"), EnchantmentAPI.getEnchantment((String) "Sticky"), EnchantmentAPI.getEnchantment((String) "Diminish")}))));
    }

    @Override
    public boolean supportsBowDamage() {
        return true;
    }

    @Override
    public List<String> getEquipDescription() {
        return Lists.newArrayList((Object[]) new String[]{(Object) ChatColor.RED + "" + (Object) ChatColor.BOLD + "MOTHER OF YIJKI SET BONUS: " + (Object) ChatColor.RED + "-30% INCOMING DMG"});
    }

    @Override
    public boolean onPlayerDamaged(Player damaged, EntityDamageByEntityEvent event) {
        Material type;
        if (this.isBlockedOutpostWorld(event.getDamager())) {
            return false;
        }
        event.setDamage(event.getDamage(EntityDamageEvent.DamageModifier.BASE) * 0.7);
        if (event.getEntity() instanceof Player) {
            this.removeResistance((Player) event.getEntity());
        }
        double yijkiAbilityChance = 0.05;
        ItemStack handItem = damaged.getItemInHand();
        if (handItem != null && (type = handItem.getType()) != null && type == Material.DIAMOND_SWORD && new NBTWrapper(handItem).getString("cosmicType").equals("YIJKI_SWORD")) {
            yijkiAbilityChance *= 1.25;
        }
        if (Math.random() < yijkiAbilityChance) {
            this.activateYijki(event.getEntity().getLocation(), damaged);
        }
        return true;
    }

    public void activateYijki(Location center, Player player) {
        ArrayList<Location> procLocations = new ArrayList<Location>();
        ArrayList<Player> nearbyPlayers = new ArrayList<Player>();
        FPlayer fp = (FPlayer) FPlayers.i.get((OfflinePlayer) player);
        for (int i = 0; i < 16; ++i) {
            Location l = LocationUtils.getNearbyLocation(center, 8, 2, 0);
            l.getWorld().playSound(l, Sound.WITHER_SPAWN, 1.0f, 0.4f);
            ParticleEffect.playEffect((ParticleEffect) ParticleEffect.WITCH_MAGIC, (Location) l, (float) 0.7f, (int) 32);
            ParticleEffect.playEffect((ParticleEffect) ParticleEffect.WITCH_MAGIC, (Location) l.clone().add(0.0, 1.0, 0.0), (float) 0.7f, (int) 32);
            procLocations.add(l);
        }
        for (Entity nearbyEnt : player.getNearbyEntities(32.0, 64.0, 32.0)) {
            if (!(nearbyEnt instanceof Player)) continue;
            Player pNear = (Player) nearbyEnt;
            FPlayer fpNear = (FPlayer) FPlayers.i.get((OfflinePlayer) pNear);
            if (CosmicArmorSetsAPI.hasInfiniteLuck(pNear, 4, player)) continue;
            if (AstroItemSkinsAPI.hasSkinEquipped((Player) player, (SkinType) SkinType.CUPID_AMULET)) {
                player.sendMessage(CC.translate((String) "&c&l* Amulet of Angles &7(Block Yijki) &c&l*"));
                continue;
            }
            if (fpNear.getRelationTo((RelationParticipator) fp).isAtLeast(Relation.TRUCE) || !WorldGuardUtils.isPvPEnabled((Location) pNear.getLocation()))
                continue;
            pNear.sendMessage((Object) ChatColor.DARK_PURPLE + "" + (Object) ChatColor.BOLD + "** REVENGE OF YIJKI (" + (Object) ChatColor.RED + fp.getNameAsync() + " [1.5s]" + (Object) ChatColor.DARK_PURPLE + (Object) ChatColor.BOLD + ") **");
            nearbyPlayers.add(pNear);
        }
        Bukkit.getScheduler().runTaskLater((Plugin) CosmicArmorSetsPlugin.get_(), () -> {
            for (Location l : procLocations) {
                l.getWorld().strikeLightningEffect(l);
                l.getWorld().playSound(l, Sound.WITHER_DEATH, 1.0f, 0.4f);
                ParticleEffect.playEffect((ParticleEffect) ParticleEffect.LARGE_EXPLODE, (Location) l, (float) 0.7f, (int) 4);
                ParticleEffect.playEffect((ParticleEffect) ParticleEffect.LARGE_EXPLODE, (Location) l.clone().add(0.0, 1.0, 0.0), (float) 0.7f, (int) 4);
                for (Player pNear : nearbyPlayers) {
                    FPlayer fpNear;
                    if (!(pNear.getLocation().distanceSquared(l) <= 2.0) || (fpNear = (FPlayer) FPlayers.i.get((OfflinePlayer) pNear)).getRelationTo((RelationParticipator) fp).isAtLeast(Relation.TRUCE) || CosmicArmorSetsAPI.hasInfiniteLuck(pNear, 4, player) || !WorldGuardUtils.isPvPEnabled((Location) pNear.getLocation()))
                        continue;
                    double dmg = CosmicArmorSetsAPI.getPoltergeistChance(player);
                    if (dmg >= ThreadLocalRandom.current().nextDouble(100.0) + 1.0) {
                        pNear.sendMessage(CC.DarkRedB + "* POLTERGEIST [" + CC.Gray + "Immune: Mother of Yijki" + CC.DarkRedB + "] *");
                        continue;
                    }
                    int damage = 16;
                    int heroLevel = UpgradeUtils.getUpgradeLevel((Player) pNear, (String) "HEROIC_ARMOR_SET");
                    damage = (int) ((double) damage * (heroLevel == 1 ? 0.95 : (heroLevel == 2 ? 0.9 : (heroLevel == 3 ? 0.85 : (heroLevel == 4 ? 0.75 : 1.0)))));
                    if (Phoenix.hasPhoenixEquipped((Player) pNear) && Phoenix.procPhoenix((Player) pNear, (LivingEntity) player, (double) damage))
                        continue;
                    pNear.setHealth(Math.max(1.0, pNear.getHealth() - (double) damage));
                }
            }
        }, 20L);
    }

    @Override
    public boolean onPlayerAttacked(Player damager, EntityDamageByEntityEvent event) {
        Material type;
        this.removeResistance(damager);
        if (this.isBlockedOutpostWorld((Entity) damager)) {
            return false;
        }
        ItemStack handItem = damager.getItemInHand();
        if (handItem != null && (type = handItem.getType()) != null && (type == Material.DIAMOND_SWORD || type == Material.GOLD_SWORD) && new NBTWrapper(handItem).getString("cosmicType").equals("YIJKI_SWORD")) {
            event.setDamage(event.getDamage(EntityDamageEvent.DamageModifier.BASE) * 1.2);
        }
        return true;
    }

    @Override
    public ItemStack getWeapon(boolean applyTransmog) {
        return new NBTWrapper(new ItemBuilder(Material.DIAMOND_SWORD, CC.WhiteB + "Yijki's World Ender", new String[]{CC.Gray + "An absolutely massive, corrupted", CC.Gray + "executioner blade imbued with", CC.Gray + "highly destructive dark magic.", "", CC.WhiteB + "YIJKI WEAPON BONUS", CC.White + "Deal +20% damage to all enemies.", CC.White + "125% Revenge of Yijki Ability", CC.White + "Immune to Rot and Decay", CC.Gray + "(" + CC.GrayI + "Requires all 4 yijki items." + CC.Gray + ")"}).enchant(Enchantment.DAMAGE_ALL, 5).enchant(Enchantment.DURABILITY, 3).build()).setString("cosmicType", "YIJKI_SWORD").build();
    }

    @Override
    public boolean onPlayerDamagedBonus(Player damaged, int multiplier, EntityDamageByEntityEvent event) {
        if (this.isBlockedOutpostWorld((Entity) damaged)) {
            return false;
        }
        this.modifyDamage(damaged, event, -5.0, multiplier);
        if (Math.random() < (double) multiplier * 0.010000000000000002) {
            this.activateYijki(damaged.getLocation(), damaged);
        }
        return true;
    }

    @Override
    public boolean onPlayerAttackedBonus(Player damager, int multiplier, EntityDamageByEntityEvent event) {
        return false;
    }
}

