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
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class NebulaArmorSet extends ArmorSetInfo implements OtherPlayerEventListenerSet, WeaponedArmorSet {
    @Override
    public ItemStack getHelmet(boolean applyTransmog) {
        LinkedList<CustomEnchantment> enchants = new LinkedList<CustomEnchantment>(Arrays.asList(EnchantmentAPI.getEnchantment("Drunk"), EnchantmentAPI.getEnchantment("Deathbringer"), EnchantmentAPI.getEnchantment("Enlighted"), EnchantmentAPI.getEnchantment("Armored"), EnchantmentAPI.getEnchantment("Dodge"), EnchantmentAPI.getEnchantment("Tank"), EnchantmentAPI.getEnchantment("Spirits"), EnchantmentAPI.getEnchantment("Marksman"), EnchantmentAPI.getEnchantment("Angelic"), EnchantmentAPI.getEnchantment("Heavy"), EnchantmentAPI.getEnchantment("Valor"), EnchantmentAPI.getEnchantment("Smoke Bomb"), EnchantmentAPI.getEnchantment("Hardened")));
        if (Math.random() <= 0.1) {
            enchants.add(this.getEnchantment("Phoenix"));
        } else {
            enchants.add(this.getEnchantment("Marksman"));
        }
        return this.getWithTransmog(applyTransmog, ItemUtils.applyCustomEnchantments(ItemUtils.buildItem(Material.DIAMOND_HELMET, (short) 0, "&d&lNebula Helmet", "&d&lNEBULA SET BONUS", "&d&l* &dDeal 30% More Damage All Enemies", "&d&l* &dReceive 60% Less Damage", "&d&l* &dGalaxy Time Warp Ability", "&d&l  &7 (Lose all of your defensive bonuses", "&d&l  &7 from this set in exchange for", "&d&l  &7 Speed IV, 50% more damage from", "&d&l  &7 behind and death protection for", "&d&l  &7 for 12 seconds.)", "&d&l  &7&o Shift + Block to activate."), enchants));
    }

    @Override
    public ItemStack getChestplate(boolean applyTransmog) {
        ItemStack item = ItemUtils.applyCustomEnchantments(ItemUtils.buildItem(Material.DIAMOND_CHESTPLATE, (short) 0, "&d&lNebula Chestplate", "&d&lNEBULA SET BONUS", "&d&l* &dDeal 30% More Damage All Enemies", "&d&l* &dReceive 60% Less Damage", "&d&l* &dGalaxy Time Warp Ability", "&d&l  &7 (Lose all of your defensive bonuses", "&d&l  &7 from this set in exchange for", "&d&l  &7 Speed IV, 50% more damage from", "&d&l  &7 behind and death protection for", "&d&l  &7 for 12 seconds.)", "&d&l  &7&o Shift + Block to activate."), new LinkedList(Arrays.asList(EnchantmentAPI.getEnchantment("Aegis"), EnchantmentAPI.getEnchantment("Deathbringer"), EnchantmentAPI.getEnchantment("Blood Lust"), EnchantmentAPI.getEnchantment("Diminish"), EnchantmentAPI.getEnchantment("Armored"), EnchantmentAPI.getEnchantment("Overload"), EnchantmentAPI.getEnchantment("Leadership"), EnchantmentAPI.getEnchantment("Blood Link"), EnchantmentAPI.getEnchantment("Tank"), EnchantmentAPI.getEnchantment("Marksman"), EnchantmentAPI.getEnchantment("Angelic"), EnchantmentAPI.getEnchantment("Repair Guard"))));
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
        ItemStack item = ItemUtils.applyCustomEnchantments(ItemUtils.buildItem(Material.DIAMOND_LEGGINGS, (short) 0, "&d&lNebula Leggings", "&d&lNEBULA SET BONUS", "&d&l* &dDeal 30% More Damage All Enemies", "&d&l* &dReceive 60% Less Damage", "&d&l* &dGalaxy Time Warp Ability", "&d&l  &7 (Lose all of your defensive bonuses", "&d&l  &7 from this set in exchange for", "&d&l  &7 Speed IV, 50% more damage from", "&d&l  &7 behind and death protection for", "&d&l  &7 for 12 seconds.)", "&d&l  &7&o Shift + Block to activate."), new LinkedList(Arrays.asList(EnchantmentAPI.getEnchantment("Deathbringer"), EnchantmentAPI.getEnchantment("Enlighted"), EnchantmentAPI.getEnchantment("Armored"), EnchantmentAPI.getEnchantment("Clarity"), EnchantmentAPI.getEnchantment("Tank"), EnchantmentAPI.getEnchantment("Lucky"), EnchantmentAPI.getEnchantment("Angelic"), EnchantmentAPI.getEnchantment("Guardians"), EnchantmentAPI.getEnchantment("Obsidianshield"), EnchantmentAPI.getEnchantment("Valor"), EnchantmentAPI.getEnchantment("Sticky"), EnchantmentAPI.getEnchantment("Cactus"))));
        if (Math.random() <= 0.1) {
            this.addEnchantment("Nature Wrath", ItemUtils.getRandomArmorSetEnchantLevel("Nature Wrath"), item);
        } else {
            this.addEnchantment("Protection", ItemUtils.getRandomArmorSetEnchantLevel("Protection"), item);
        }
        return this.getWithTransmog(applyTransmog, item);
    }

    @Override
    public ItemStack getBoots(boolean applyTransmog) {
        ItemStack item = ItemUtils.applyCustomEnchantments(ItemUtils.buildItem(Material.DIAMOND_BOOTS, (short) 0, "&d&lNebula Boots", "&d&lNEBULA SET BONUS", "&d&l* &dDeal 30% More Damage All Enemies", "&d&l* &dReceive 60% Less Damage", "&d&l* &dGalaxy Time Warp Ability", "&d&l  &7 (Lose all of your defensive bonuses", "&d&l  &7 from this set in exchange for", "&d&l  &7 Speed IV, 50% more damage from", "&d&l  &7 behind and death protection for", "&d&l  &7 for 12 seconds.)", "&d&l  &7&o Shift + Block to activate."), new LinkedList(Arrays.asList(EnchantmentAPI.getEnchantment("Gears"), EnchantmentAPI.getEnchantment("Enlighted"), EnchantmentAPI.getEnchantment("Armored"), EnchantmentAPI.getEnchantment("Tank"), EnchantmentAPI.getEnchantment("Marksman"), EnchantmentAPI.getEnchantment("Angelic"), EnchantmentAPI.getEnchantment("Heavy"), EnchantmentAPI.getEnchantment("Ender Walker"), EnchantmentAPI.getEnchantment("Hardened"), EnchantmentAPI.getEnchantment("Rocket Escape"))));
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
        return Lists.newArrayList("&d&lNEBULA SET BONUS", "&d&l* &dDeal 30% More Damage All Enemies", "&d&l* &dReceive 60% Less Damage", "&d&l* &dGalaxy Time Warp Ability", "&d&l  &7 (Lose all of your defensive bonuses", "&d&l  &7 from this set in exchange for", "&d&l  &7 Speed IV, 50% more damage from", "&d&l  &7 behind and death protection for", "&d&l  &7 for 12 seconds.)", "&d&l  &7&o Shift + Block to activate.");
    }

    @Override
    public boolean onPlayerDamaged(Player damaged, EntityDamageByEntityEvent event) {
        event.setDamage(event.getDamage() * 1.3);
        return true;
    }

    @Override
    public boolean onPlayerDamagedBonus(Player damaged, int multiplier, EntityDamageByEntityEvent event) {
        this.modifyDamage(damaged, event, 8, multiplier);
        return false;
    }

    @Override
    public boolean onPlayerAttackedBonus(Player damager, int multiplier, EntityDamageByEntityEvent event) {
        this.modifyDamage(damager, event, -15, multiplier);
        return false;
    }

    @Override
    public void onEquip(Player player) {
    }

    @Override
    public void onUnequip(Player player) {
    }

    @Override
    public void onPlayerEvent(Event event) {
    }

    @Override
    public boolean onPlayerAttacked(Player damager, EntityDamageByEntityEvent event) {
        event.setDamage(event.getDamage() * 0.4);
        return true;
    }

    @Override
    public ItemStack getWeapon(boolean applyTransmog) {
        ItemStack item = new NBTWrapper(new ItemBuilder(Material.DIAMOND_SWORD, CC.DarkRedB + "Supreme Fanny Pack", CC.Gray + "They laughed at you, they", CC.Gray + "told you a fanny pack is not", CC.Gray + "a practical piece of clothing...", CC.Gray + "now they will pay for their ignorance!", "", CC.DarkRedB + "NEBULA WEAPON BONUS", CC.DarkRed + "Deal +20% damage to all enemies", CC.DarkRed + "Enemies deal 12% less damage to you.", CC.Gray + "(" + CC.GrayI + "Requires all 4 supreme items." + CC.Gray + ")").enchant(Enchantment.DAMAGE_ALL, 5).enchant(Enchantment.DURABILITY, 3).build()).setString("cosmicType", "NEBULA_SWORD").build();
        this.addEnchantment("Block", this.getMaxEnchantLevel("Block"), item);
        this.addEnchantment("Reflective Block", this.getMaxEnchantLevel("Reflective Block"), item);
        return item;
    }

}
