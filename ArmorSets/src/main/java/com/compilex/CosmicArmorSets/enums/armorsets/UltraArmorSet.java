package com.compilex.CosmicArmorSets.enums.armorsets;

import com.compilex.CosmicArmorSets.CosmicArmorSetsAPI;
import com.compilex.CosmicArmorSets.enums.ArmorSetInfo;
import com.compilex.CosmicArmorSets.enums.OtherPlayerEventListenerSet;
import com.compilex.CosmicArmorSets.utils.ItemUtils;
import com.compilex.CosmicArmorSets.utils.TranslateList;
import com.cosmicpvp.cosmicutils.nbt.NBTTagCompoundBuilder;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import com.google.common.collect.Lists;
import com.rit.sucy.CustomEnchantment;
import com.rit.sucy.EnchantmentAPI;
import me.ifamasssxd.cosmicsky.inventorypets.CosmicSkyInventoryPets;
import net.minecraft.server.v1_7_R4.NBTBase;
import org.arkhamnetwork.enchantments.enchantments.armor.Gears;
import org.arkhamnetwork.enchantments.libs.PlayerPotionEffects;
import org.arkhamnetwork.enchantments.libs.SpecificCustomEnchantment;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class UltraArmorSet extends ArmorSetInfo implements OtherPlayerEventListenerSet {
    public static List<Block> blockList = new ArrayList<>();

    public ItemStack getHelmet(boolean applyTransmog) {
        return getWithTransmog(applyTransmog, ItemUtils.applyCustomEnchantments(ItemUtils.buildItem(Material.DIAMOND_HELMET, (short) 0, "&7&lUltra Helmet", CC.Gray + "The Ultra Leggings"
                , ""
                , "&7&lULTRA SET BONUS"
                , "&7&l* &7You deal 35% more damage."
                , "&7&l* &7Enemies deal 20% less damage to you."
                , "&7&l* &740% Holy Ultra Negation."
                , "&7&l* &710% Chance to proc Ultra Rage."
                , "&7&l* &7x1.5 World Destroyer Duration."
                , "&7&l* &750% Chance to Bypass Utility Belt."
                , "&7(&oRequires all 4 Ultra items.&7)"), new LinkedList(Arrays.asList((Object[]) new CustomEnchantment[]{EnchantmentAPI.getEnchantment("Godly Overload"), EnchantmentAPI.getEnchantment("Paladin Armored"), EnchantmentAPI.getEnchantment("Blood Lust"), EnchantmentAPI.getEnchantment("Enlighted"), EnchantmentAPI.getEnchantment("Deathbringer"), EnchantmentAPI.getEnchantment("Armored"), EnchantmentAPI.getEnchantment("Tank"), EnchantmentAPI.getEnchantment("Angelic"), EnchantmentAPI.getEnchantment("Arrow Deflect"), EnchantmentAPI.getEnchantment("Dodge"),
                EnchantmentAPI.getEnchantment("Valor"), EnchantmentAPI.getEnchantment("Aegis"), EnchantmentAPI.getEnchantment("Spirit Link"), EnchantmentAPI.getEnchantment("Leadership")}))));
    }

    public ItemStack getChestplate(boolean applyTransmog) {
        return getWithTransmog(applyTransmog, ItemUtils.applyCustomEnchantments(ItemUtils.buildItem(Material.DIAMOND_CHESTPLATE, (short) 0, "&7&lUltra Body", CC.Gray + "The Ultra Leggings"
                , ""
                , "&7&lULTRA SET BONUS"
                , "&7&l* &7You deal 35% more damage."
                , "&7&l* &7Enemies deal 20% less damage to you."
                , "&7&l* &740% Holy Ultra Negation."
                , "&7&l* &710% Chance to proc Ultra Rage."
                , "&7&l* &7x1.5 World Destroyer Duration."
                , "&7&l* &750% Chance to Bypass Utility Belt."
                , "&7(&oRequires all 4 Ultra items.&7)"), new LinkedList(Arrays.asList((Object[]) new CustomEnchantment[]{EnchantmentAPI.getEnchantment("Godly Overload"), EnchantmentAPI.getEnchantment("Paladin Armored"), EnchantmentAPI.getEnchantment("Blood Lust"), EnchantmentAPI.getEnchantment("Enlighted"), EnchantmentAPI.getEnchantment("Deathbringer"), EnchantmentAPI.getEnchantment("Armored"), EnchantmentAPI.getEnchantment("Tank"), EnchantmentAPI.getEnchantment("Angelic"), EnchantmentAPI.getEnchantment("Arrow Deflect"), EnchantmentAPI.getEnchantment("Dodge"),
                EnchantmentAPI.getEnchantment("Valor"), EnchantmentAPI.getEnchantment("Aegis"), EnchantmentAPI.getEnchantment("Spirit Link"), EnchantmentAPI.getEnchantment("Leadership")}))));
    }

    public ItemStack getLeggings(boolean applyTransmog) {
        return getWithTransmog(applyTransmog, ItemUtils.applyCustomEnchantments(ItemUtils.buildItem(Material.DIAMOND_LEGGINGS, (short) 0, "&7&lUltra Jeans", CC.Gray + "The Ultra Leggings"
                , ""
                , "&7&lULTRA SET BONUS"
                , "&7&l* &7You deal 35% more damage."
                , "&7&l* &7Enemies deal 20% less damage to you."
                , "&7&l* &740% Holy Ultra Negation."
                , "&7&l* &710% Chance to proc Ultra Rage."
                , "&7&l* &7x1.5 World Destroyer Duration."
                , "&7&l* &750% Chance to Bypass Utility Belt."
                , "&7(&oRequires all 4 Ultra items.&7)"), new LinkedList(Arrays.asList((Object[]) new CustomEnchantment[]{EnchantmentAPI.getEnchantment("Godly Overload"), EnchantmentAPI.getEnchantment("Paladin Armored"), EnchantmentAPI.getEnchantment("Blood Lust"), EnchantmentAPI.getEnchantment("Enlighted"), EnchantmentAPI.getEnchantment("Deathbringer"), EnchantmentAPI.getEnchantment("Armored"), EnchantmentAPI.getEnchantment("Tank"), EnchantmentAPI.getEnchantment("Angelic"), EnchantmentAPI.getEnchantment("Arrow Deflect"), EnchantmentAPI.getEnchantment("Dodge"),
                EnchantmentAPI.getEnchantment("Valor"), EnchantmentAPI.getEnchantment("Aegis"), EnchantmentAPI.getEnchantment("Spirit Link"), EnchantmentAPI.getEnchantment("Leadership")}))));
    }

    public ItemStack getBoots(boolean applyTransmog) {
        return getWithTransmog(applyTransmog, ItemUtils.applyCustomEnchantments(ItemUtils.buildItem(Material.DIAMOND_BOOTS, (short) 0, "&7&lUltra Kicks", CC.Gray + "The Ultra Leggings"
                , ""
                , "&7&lULTRA SET BONUS"
                , "&7&l* &7You deal 35% more damage."
                , "&7&l* &7Enemies deal 20% less damage to you."
                , "&7&l* &740% Holy Ultra Negation."
                , "&7&l* &710% Chance to proc Ultra Rage."
                , "&7&l* &7x1.5 World Destroyer Duration."
                , "&7&l* &750% Chance to Bypass Utility Belt."
                , "&7(&oRequires all 4 Ultra items.&7)"),   new LinkedList(Arrays.asList((Object[]) new CustomEnchantment[]{EnchantmentAPI.getEnchantment("Godly Overload"), EnchantmentAPI.getEnchantment("Paladin Armored"), EnchantmentAPI.getEnchantment("Blood Lust"), EnchantmentAPI.getEnchantment("Enlighted"), EnchantmentAPI.getEnchantment("Deathbringer"), EnchantmentAPI.getEnchantment("Armored"), EnchantmentAPI.getEnchantment("Tank"), EnchantmentAPI.getEnchantment("Angelic"), EnchantmentAPI.getEnchantment("Arrow Deflect"), EnchantmentAPI.getEnchantment("Dodge"),
                EnchantmentAPI.getEnchantment("Valor"), EnchantmentAPI.getEnchantment("Aegis"), EnchantmentAPI.getEnchantment("Spirit Link"), EnchantmentAPI.getEnchantment("Leadership")}))));
    }

    public List<String> getEquipDescription() {
        return TranslateList.translateList(ChatColor.GRAY + "" + ChatColor.BOLD + "&7&lULTRA SET BONUS"
                , "&7&l* &7You deal 35% more damage."
                , "&7&l* &7Enemies deal 20% less damage to you."
                , "&7&l* &740% Holy Ultra Negation."
                , "&7&l* &710% Chance to proc Ultra Rage."
                , "&7&l* &7x1.5 World Destroyer Duration."
                , "&7&l* &750% Chance to Bypass Utility Belt.");
    }

    public boolean onPlayerDamaged(Player damaged, EntityDamageByEntityEvent event) {
        if (isBlockedOutpostWorld((Entity) damaged))
            return false;
        if (event.getEntity() instanceof Player)
            removeResistance((Player) event.getEntity());
        if (event.isCancelled())
            return false;
        removeResistance(damaged);
        event.setDamage(event.getDamage(EntityDamageEvent.DamageModifier.BASE) * 0.8);
        return true;
    }

    public boolean onPlayerAttacked(Player damager, EntityDamageByEntityEvent event) {
        if (isBlockedOutpostWorld((Entity) damager)) {
            return false;
        }
        event.setDamage(event.getDamage(EntityDamageEvent.DamageModifier.BASE) * 1.35D);
        removeResistance(damager);


        if (Math.random() <= 0.1) {
            activateUltraAbility(damager, (Player) event.getEntity());
        }


        return true;
    }

    public boolean onPlayerDamagedBonus(Player damaged, int multiplier, EntityDamageByEntityEvent event) {
        if (event.isCancelled())
            return false;
        if (isBlockedOutpostWorld((Entity) damaged))
            return false;
        modifyDamage(damaged, event, -10.0D, multiplier);
        return false;
    }

    public boolean onPlayerAttackedBonus(Player damager, int applied, EntityDamageByEntityEvent event) {
        removeResistance(damager);
        if (isBlockedOutpostWorld((Entity) damager)) {
            return false;
        }


        if (Math.random() <= 0.005 * applied) {
            activateUltraAbility(damager, (Player) event.getEntity());
        }


        return modifyDamage(damager, event, 10.0D, applied);
    }

    public void onEquip(Player player) {

    }

    //comments
    // yes, these are comments

    public void onUnequip(Player player) {

    }


    public void activateUltraAbility(Player player, final Player target) {
        target.setLastDamage(target.getLastDamage()*1.1);
        target.sendMessage(CC.translate("&e&l*&5&l*&c&l* &7&lULTRA RAGE &f[&c" + player.getName() + "&f] &c&l*&5&l*&e&l*"));

        player.sendMessage(CC.translate("&e&l*&5&l*&c&l* &7&lULTRA RAGE &f[&aUltra Rage&f] &c&l*&5&l*&e&l*"));
        target.setMetadata("ultra", new FixedMetadataValue(CosmicSkyInventoryPets.get(), true));
        target.setMetadata("bansheed", new FixedMetadataValue(CosmicSkyInventoryPets.get(), true));

        new BukkitRunnable() {
            public void run() {
                target.removeMetadata("ultra", CosmicSkyInventoryPets.get());
                target.removeMetadata("bansheed", CosmicSkyInventoryPets.get());

            }
        }.runTaskLater(CosmicSkyInventoryPets.get(), 20 * 8);
    }


    public void onPlayerEvent(Event paramEvent) {
    }
}