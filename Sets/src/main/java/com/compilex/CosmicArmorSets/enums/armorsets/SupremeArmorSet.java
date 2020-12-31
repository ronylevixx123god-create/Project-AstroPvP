/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  com.compilex.CosmicOutposts.CosmicOutpostsAPI
 *  com.compilex.CosmicOutposts.outpost.enums.OutpostTier
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.cosmicpvp.cosmicutils.utils.ItemBuilder
 *  com.cosmicpvp.cosmicutils.utils.NBTWrapper
 *  com.google.common.collect.Lists
 *  com.rit.sucy.CustomEnchantment
 *  com.rit.sucy.EnchantmentAPI
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.enchantments.Enchantment
 *  org.bukkit.entity.Arrow
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.event.entity.EntityDamageEvent
 *  org.bukkit.event.entity.EntityDamageEvent$DamageCause
 *  org.bukkit.event.entity.EntityDamageEvent$DamageModifier
 *  org.bukkit.event.entity.FoodLevelChangeEvent
 *  org.bukkit.event.player.PlayerChangedWorldEvent
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.plugin.Plugin
 */
package com.compilex.CosmicArmorSets.enums.armorsets;

import com.compilex.CosmicArmorSets.CosmicArmorSetsAPI;
import com.compilex.CosmicArmorSets.CosmicArmorSetsPlugin;
import com.compilex.CosmicArmorSets.enums.ArmorSetInfo;
import com.compilex.CosmicArmorSets.enums.OtherPlayerEventListenerSet;
import com.compilex.CosmicArmorSets.enums.WeaponedArmorSet;
import com.compilex.CosmicArmorSets.utils.ItemUtils;
import com.compilex.CosmicOutposts.CosmicOutpostsAPI;
import com.compilex.CosmicOutposts.outpost.enums.OutpostTier;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.ItemBuilder;
import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import com.google.common.collect.Lists;
import com.rit.sucy.CustomEnchantment;
import com.rit.sucy.EnchantmentAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class SupremeArmorSet
        extends ArmorSetInfo
        implements OtherPlayerEventListenerSet,
        WeaponedArmorSet {
    @Override
    public ItemStack getHelmet(boolean applyTransmog) {
        LinkedList<CustomEnchantment> enchants = new LinkedList<CustomEnchantment>(Arrays.asList(new CustomEnchantment[]{EnchantmentAPI.getEnchantment((String) "Drunk"), EnchantmentAPI.getEnchantment((String) "Deathbringer"), EnchantmentAPI.getEnchantment((String) "Enlighted"), EnchantmentAPI.getEnchantment((String) "Armored"), EnchantmentAPI.getEnchantment((String) "Dodge"), EnchantmentAPI.getEnchantment((String) "Tank"), EnchantmentAPI.getEnchantment((String) "Spirits"), EnchantmentAPI.getEnchantment((String) "Marksman"), EnchantmentAPI.getEnchantment((String) "Angelic"), EnchantmentAPI.getEnchantment((String) "Heavy"), EnchantmentAPI.getEnchantment((String) "Valor"), EnchantmentAPI.getEnchantment((String) "Smoke Bomb"), EnchantmentAPI.getEnchantment((String) "Hardened")}));
        if (Math.random() <= 0.1) {
            enchants.add(this.getEnchantment("Phoenix"));
        } else {
            enchants.add(this.getEnchantment("Marksman"));
        }
        return this.getWithTransmog(applyTransmog, ItemUtils.applyCustomEnchantments(ItemUtils.buildItem(Material.DIAMOND_HELMET, (short) 0, "&4&lSupreme Headgear", "&4&oA lightweight headpiece", "&4&ofit for take off.", "", "&4&lSUPREME SET BONUS", "&4&l* &4Gears IV", "&4&l* &4No Fall Damage / Food Loss", "&4&l* &4Deal +15% damage to all enemies", "&4&l* &4Enemy arrows deal 10% more damage to you.", "&4&l* &4+200% Clout", "&7(&oRequires all 4 supreme items.&7)"), enchants));
    }

    @Override
    public ItemStack getChestplate(boolean applyTransmog) {
        ItemStack item = ItemUtils.applyCustomEnchantments(ItemUtils.buildItem(Material.DIAMOND_CHESTPLATE, (short) 0, "&4&lSupreme Vest", "&4&oAn aerodynamic vest that", "&4&ois capable of sustaining flight.", "", "&4&lSUPREME SET BONUS", "&4&l* &4Gears IV", "&4&l* &4No Fall Damage / Food Loss", "&4&l* &4Deal +15% damage to all enemies", "&4&l* &4Enemy arrows deal 10% more damage to you.", "&4&l* &4+200% Clout", "&7(&oRequires all 4 supreme items.&7)"), new LinkedList<Object>(Arrays.asList((Object[]) new CustomEnchantment[]{EnchantmentAPI.getEnchantment((String) "Aegis"), EnchantmentAPI.getEnchantment((String) "Deathbringer"), EnchantmentAPI.getEnchantment((String) "Blood Lust"), EnchantmentAPI.getEnchantment((String) "Diminish"), EnchantmentAPI.getEnchantment((String) "Armored"), EnchantmentAPI.getEnchantment((String) "Overload"), EnchantmentAPI.getEnchantment((String) "Leadership"), EnchantmentAPI.getEnchantment((String) "Blood Link"), EnchantmentAPI.getEnchantment((String) "Tank"), EnchantmentAPI.getEnchantment((String) "Marksman"), EnchantmentAPI.getEnchantment((String) "Angelic"), EnchantmentAPI.getEnchantment((String) "Repair Guard")})));
        if (Math.random() <= 0.1) {
            this.addEnchantment("Enlighted", this.getMaxEnchantLevel("Enlighted"), item);
            this.addEnchantment("Divine Enlighted", ItemUtils.getRandomArmorSetEnchantLevel("Divine Enlighted"), item);
        } else {
            this.addEnchantment("Enlighted", this.getMaxEnchantLevel("Enlighted"), item);
        }
        return this.getWithTransmog(applyTransmog, item);
    }

    @Override
    public ItemStack getLeggings(boolean applyTransmog) {
        ItemStack item = ItemUtils.applyCustomEnchantments(ItemUtils.buildItem(Material.DIAMOND_LEGGINGS, (short) 0, "&4&lSupreme Chaps", "&4&oLightweight and clout powered,", "&4&oprovides enough thrust to boost", "&4&oeven the most feeble into the skies.", "", "&4&lSUPREME SET BONUS", "&4&l* &4Gears IV", "&4&l* &4No Fall Damage / Food Loss", "&4&l* &4Deal +15% damage to all enemies", "&4&l* &4Enemy arrows deal 10% more damage to you.", "&4&l* &4+200% Clout", "&7(&oRequires all 4 supreme items.&7)"), new LinkedList<Object>(Arrays.asList((Object[]) new CustomEnchantment[]{EnchantmentAPI.getEnchantment((String) "Deathbringer"), EnchantmentAPI.getEnchantment((String) "Enlighted"), EnchantmentAPI.getEnchantment((String) "Armored"), EnchantmentAPI.getEnchantment((String) "Clarity"), EnchantmentAPI.getEnchantment((String) "Tank"), EnchantmentAPI.getEnchantment((String) "Lucky"), EnchantmentAPI.getEnchantment((String) "Angelic"), EnchantmentAPI.getEnchantment((String) "Guardians"), EnchantmentAPI.getEnchantment((String) "Obsidianshield"), EnchantmentAPI.getEnchantment((String) "Valor"), EnchantmentAPI.getEnchantment((String) "Sticky"), EnchantmentAPI.getEnchantment((String) "Cactus")})));
        if (Math.random() <= 0.1) {
            this.addEnchantment("Nature Wrath", ItemUtils.getRandomArmorSetEnchantLevel("Nature Wrath"), item);
        } else {
            this.addEnchantment("Protection", ItemUtils.getRandomArmorSetEnchantLevel("Protection"), item);
        }
        return this.getWithTransmog(applyTransmog, item);
    }

    @Override
    public ItemStack getBoots(boolean applyTransmog) {
        ItemStack item = ItemUtils.applyCustomEnchantments(ItemUtils.buildItem(Material.DIAMOND_BOOTS, (short) 0, "&4&lSupreme Thruster Boots", "&4&oSupreme boots capable of", "&4&oconverting clout into flight.", "", "&4&lSUPREME SET BONUS", "&4&l* &4Gears IV", "&4&l* &4No Fall Damage / Food Loss", "&4&l* &4Deal +15% damage to all enemies", "&4&l* &4Enemy arrows deal 10% more damage to you.", "&4&l* &4+200% Clout", "&7(&oRequires all 4 supreme items.&7)"), new LinkedList<Object>(Arrays.asList((Object[]) new CustomEnchantment[]{EnchantmentAPI.getEnchantment((String) "Gears"), EnchantmentAPI.getEnchantment((String) "Enlighted"), EnchantmentAPI.getEnchantment((String) "Armored"), EnchantmentAPI.getEnchantment((String) "Tank"), EnchantmentAPI.getEnchantment((String) "Marksman"), EnchantmentAPI.getEnchantment((String) "Angelic"), EnchantmentAPI.getEnchantment((String) "Heavy"), EnchantmentAPI.getEnchantment((String) "Ender Walker"), EnchantmentAPI.getEnchantment((String) "Hardened"), EnchantmentAPI.getEnchantment((String) "Rocket Escape")})));
        item = this.addEnchantment("Springs", 2, item);
        if (Math.random() <= 0.1) {
            item = this.addEnchantment("Deathbringer", this.getMaxEnchantLevel("Deathbringer"), item);
            item = this.addEnchantment("Planetary Deathbringer", ItemUtils.getRandomArmorSetEnchantLevel("Planetary Deathbringer"), item);
        } else {
            item = this.addEnchantment("Deathbringer", ItemUtils.getRandomArmorSetEnchantLevel("Deathbringer"), item);
            item = this.addEnchantment("Ghost", ItemUtils.getRandomArmorSetEnchantLevel("Ghost"), item);
        }
        return this.getWithTransmog(applyTransmog, item);
    }

    @Override
    public List<String> getEquipDescription() {
        return Lists.newArrayList((Object[]) new String[]{(Object) ChatColor.DARK_RED + "" + (Object) ChatColor.BOLD + "SUPREME SET BONUS:", CC.DarkRedB + " * " + CC.DarkRed + "+15% OUTGOING DAMAGE", CC.DarkRedB + " * " + CC.DarkRed + "+10% INCOMING ARROW DAMAGE", CC.DarkRedB + " * " + CC.DarkRed + "NO FALL DAMAGE", CC.DarkRedB + " * " + CC.DarkRed + "NO FOOD LOSS", CC.DarkRedB + " * " + CC.DarkRed + "GEARS IV", CC.DarkRedB + " * " + CC.DarkRed + "+200% CLOUT (Flight Enabled)"});
    }

    @Override
    public boolean onPlayerDamaged(Player damaged, EntityDamageByEntityEvent event) {
        Material type;
        ItemStack handItem;
        if (this.isBlockedOutpostWorld(event.getDamager())) {
            return false;
        }
        if (event.getDamager() instanceof Arrow) {
            event.setDamage(event.getDamage(EntityDamageEvent.DamageModifier.BASE) * 1.1);
        }
        if ((handItem = damaged.getItemInHand()) != null && (type = handItem.getType()) != null && (type == Material.DIAMOND_SWORD || type == Material.GOLD_SWORD) && new NBTWrapper(handItem).getString("cosmicType").equals("SUPREME_SWORD")) {
            event.setDamage(event.getDamage(EntityDamageEvent.DamageModifier.BASE) * 0.9);
        }
        return true;
    }

    @Override
    public boolean onPlayerDamagedBonus(Player damaged, int multiplier, EntityDamageByEntityEvent event) {
        return false;
    }

    @Override
    public boolean onPlayerAttackedBonus(Player damager, int multiplier, EntityDamageByEntityEvent event) {
        if (this.isBlockedOutpostWorld((Entity) damager)) {
            return false;
        }
        return this.modifyDamage(damager, event, 3.0, multiplier);
    }

    @Override
    public void onEquip(Player player) {
        OutpostTier outpostTier = CosmicOutpostsAPI.getOutpostTierOfWorld((LivingEntity) player);
        if (outpostTier == OutpostTier.TRAINEE || outpostTier == OutpostTier.VANILLA || outpostTier == OutpostTier.HERO) {
            return;
        }
        EnchantmentAPI.getEnchantment((String) "Gears").applyEquipEffect(player, 4);
    }

    @Override
    public void onUnequip(Player player) {
        EnchantmentAPI.getEnchantment((String) "Gears").applyUnequipEffect(player, 4);
    }

    @Override
    public void onPlayerEvent(Event event) {
        PlayerChangedWorldEvent world;
        if (event instanceof EntityDamageEvent) {
            if (((EntityDamageEvent) event).getCause() == EntityDamageEvent.DamageCause.FALL) {
                ((EntityDamageEvent) event).setCancelled(true);
            }
        } else if (event instanceof FoodLevelChangeEvent) {
            FoodLevelChangeEvent e = (FoodLevelChangeEvent) event;
            if (e.getFoodLevel() < ((Player) e.getEntity()).getFoodLevel()) {
                e.setCancelled(true);
            }
        } else if (event instanceof PlayerChangedWorldEvent && (world = (PlayerChangedWorldEvent) event).getFrom() != null) {
            Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin) CosmicArmorSetsPlugin.get_(), () -> {
                this.onUnequip(world.getPlayer());
                Bukkit.getLogger().info("Removing Gears 4 affected from " + ((PlayerChangedWorldEvent) event).getPlayer().getName());
            }, 1L);
        }
    }

    @Override
    public boolean onPlayerAttacked(Player damager, EntityDamageByEntityEvent event) {
        Material type;
        if (this.isBlockedOutpostWorld(event.getDamager())) {
            return false;
        }
        if (event.getEntity() instanceof Player && CosmicArmorSetsAPI.hasInfiniteLuck((Player) event.getEntity(), 2, damager)) {
            return false;
        }
        ItemStack handItem = ((Player) event.getDamager()).getItemInHand();
        if (handItem != null && (type = handItem.getType()) != null && (type == Material.DIAMOND_SWORD || type == Material.GOLD_SWORD) && new NBTWrapper(handItem).getString("cosmicType").equals("SUPREME_SWORD")) {
            event.setDamage(event.getDamage(EntityDamageEvent.DamageModifier.BASE) * 0.9);
        }
        event.setDamage(event.getDamage(EntityDamageEvent.DamageModifier.BASE) * 1.15);
        return true;
    }

    @Override
    public ItemStack getWeapon(boolean applyTransmog) {
        ItemStack item = new NBTWrapper(new ItemBuilder(Material.DIAMOND_SWORD, CC.DarkRedB + "Supreme Fanny Pack", new String[]{CC.Gray + "They laughed at you, they", CC.Gray + "told you a fanny pack is not", CC.Gray + "a practical piece of clothing...", CC.Gray + "now they will pay for their ignorance!", "", CC.DarkRedB + "SUPREME WEAPON BONUS", CC.DarkRed + "Deal +20% damage to all enemies", CC.DarkRed + "Enemies deal 10% less damage to you.", CC.Gray + "(" + CC.GrayI + "Requires all 4 supreme items." + CC.Gray + ")"}).enchant(Enchantment.DAMAGE_ALL, 5).enchant(Enchantment.DURABILITY, 3).build()).setString("cosmicType", "SUPREME_SWORD").build();
        this.addEnchantment("Block", this.getMaxEnchantLevel("Block"), item);
        this.addEnchantment("Reflective Block", this.getMaxEnchantLevel("Reflective Block"), item);
        return item;
    }
}

