package com.compilex.CosmicArmorSets.enums.armorsets;

import com.compilex.CosmicArmorSets.enums.ArmorSetInfo;
import com.compilex.CosmicArmorSets.enums.OtherPlayerEventListenerSet;
import com.compilex.CosmicArmorSets.utils.ItemUtils;
import com.compilex.CosmicArmorSets.utils.TranslateList;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.rit.sucy.EnchantmentAPI;
import me.ifamasssxd.cosmicsky.inventorypets.CosmicSkyInventoryPets;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class ApolloSet extends ArmorSetInfo
        implements OtherPlayerEventListenerSet {
    public static List<Block> blockList = new ArrayList<Block>();

    @Override
    public ItemStack getHelmet(boolean applyTransmog) {
        return this.getWithTransmog(applyTransmog, ItemUtils.applyCustomEnchantments(ItemUtils.buildItem(Material.DIAMOND_HELMET, (short) 0, "&f&lApollo Crown", CC.Gray + "Apollo God Crown", "", "&f&lAPOLLO SET BONUS", "&f&l* &fYou deal 40% more damage.", "&f&l* &fEnemies deal 35% less damage to you.", "&f&l* &fBypass Chad Sunglasses.", "&f&l* &fx1.5 Pet Duration.", "&f&l* &fImmune to Witcher Pet", "&f&l* &fSpeed IV.", "&7(&oRequires all 4 Apollo items.&7)"), new LinkedList(Arrays.asList(EnchantmentAPI.getEnchantment("Godly Overload"), EnchantmentAPI.getEnchantment("Paladin Armored"), EnchantmentAPI.getEnchantment("Blood Lust"), EnchantmentAPI.getEnchantment("Enlighted"), EnchantmentAPI.getEnchantment("Deathbringer"), EnchantmentAPI.getEnchantment("Armored"), EnchantmentAPI.getEnchantment("Tank"), EnchantmentAPI.getEnchantment("Angelic"), EnchantmentAPI.getEnchantment("Arrow Deflect"), EnchantmentAPI.getEnchantment("Dodge"), EnchantmentAPI.getEnchantment("Valor"), EnchantmentAPI.getEnchantment("Aegis"), EnchantmentAPI.getEnchantment("Spirit Link"), EnchantmentAPI.getEnchantment("Leadership")))));
    }

    @Override
    public ItemStack getChestplate(boolean applyTransmog) {
        return this.getWithTransmog(applyTransmog, ItemUtils.applyCustomEnchantments(ItemUtils.buildItem(Material.DIAMOND_CHESTPLATE, (short) 0, "&f&lApollo Torso", CC.Gray + "Apollo God Torso", "", "&f&lAPOLLO SET BONUS", "&f&l* &fYou deal 40% more damage.", "&f&l* &fEnemies deal 35% less damage to you.", "&f&l* &fBypass Chad Sunglasses.", "&f&l* &fx1.5 Pet Duration.", "&f&l* &fImmune to Witcher Pet", "&f&l* &fSpeed IV.", "&7(&oRequires all 4 Apollo items.&7)"), new LinkedList(Arrays.asList(EnchantmentAPI.getEnchantment("Godly Overload"), EnchantmentAPI.getEnchantment("Paladin Armored"), EnchantmentAPI.getEnchantment("Blood Lust"), EnchantmentAPI.getEnchantment("Enlighted"), EnchantmentAPI.getEnchantment("Deathbringer"), EnchantmentAPI.getEnchantment("Armored"), EnchantmentAPI.getEnchantment("Tank"), EnchantmentAPI.getEnchantment("Angelic"), EnchantmentAPI.getEnchantment("Arrow Deflect"), EnchantmentAPI.getEnchantment("Dodge"), EnchantmentAPI.getEnchantment("Valor"), EnchantmentAPI.getEnchantment("Aegis"), EnchantmentAPI.getEnchantment("Spirit Link"), EnchantmentAPI.getEnchantment("Leadership")))));
    }

    @Override
    public ItemStack getLeggings(boolean applyTransmog) {
        return this.getWithTransmog(applyTransmog, ItemUtils.applyCustomEnchantments(ItemUtils.buildItem(Material.DIAMOND_LEGGINGS, (short) 0, "&f&lApollo Jeans", CC.Gray + "Apollo God Jeans", "", "&f&lAPOLLO SET BONUS", "&f&l* &fYou deal 40% more damage.", "&f&l* &fEnemies deal 35% less damage to you.", "&f&l* &fBypass Chad Sunglasses.", "&f&l* &fx1.5 Pet Duration.", "&f&l* &fImmune to Witcher Pet", "&f&l* &fSpeed IV.", "&7(&oRequires all 4 Apollo items.&7)"), new LinkedList(Arrays.asList(EnchantmentAPI.getEnchantment("Godly Overload"), EnchantmentAPI.getEnchantment("Paladin Armored"), EnchantmentAPI.getEnchantment("Blood Lust"), EnchantmentAPI.getEnchantment("Enlighted"), EnchantmentAPI.getEnchantment("Deathbringer"), EnchantmentAPI.getEnchantment("Armored"), EnchantmentAPI.getEnchantment("Tank"), EnchantmentAPI.getEnchantment("Angelic"), EnchantmentAPI.getEnchantment("Arrow Deflect"), EnchantmentAPI.getEnchantment("Dodge"), EnchantmentAPI.getEnchantment("Valor"), EnchantmentAPI.getEnchantment("Aegis"), EnchantmentAPI.getEnchantment("Spirit Link"), EnchantmentAPI.getEnchantment("Leadership")))));
    }

    @Override
    public ItemStack getBoots(boolean applyTransmog) {
        return this.getWithTransmog(applyTransmog, ItemUtils.applyCustomEnchantments(ItemUtils.buildItem(Material.DIAMOND_BOOTS, (short) 0, "&f&lApollo Kicks", CC.Gray + "Apollo God Kicks", "", "&f&lAPOLLO SET BONUS", "&f&l* &fYou deal 40% more damage.", "&f&l* &fEnemies deal 35% less damage to you.", "&f&l* &fBypass Chad Sunglasses.", "&f&l* &fx1.5 Pet Duration.", "&f&l* &fImmune to Witcher Pet", "&f&l* &fSpeed IV.", "&7(&oRequires all 4 Apollo items.&7)"), new LinkedList(Arrays.asList(EnchantmentAPI.getEnchantment("Godly Overload"), EnchantmentAPI.getEnchantment("Paladin Armored"), EnchantmentAPI.getEnchantment("Blood Lust"), EnchantmentAPI.getEnchantment("Enlighted"), EnchantmentAPI.getEnchantment("Deathbringer"), EnchantmentAPI.getEnchantment("Armored"), EnchantmentAPI.getEnchantment("Tank"), EnchantmentAPI.getEnchantment("Angelic"), EnchantmentAPI.getEnchantment("Arrow Deflect"), EnchantmentAPI.getEnchantment("Dodge"), EnchantmentAPI.getEnchantment("Valor"), EnchantmentAPI.getEnchantment("Aegis"), EnchantmentAPI.getEnchantment("Spirit Link"), EnchantmentAPI.getEnchantment("Leadership")))));
    }

    @Override
    public List<String> getEquipDescription() {
        return TranslateList.translateList(ChatColor.WHITE + "" + ChatColor.BOLD + "",
                "&f&lAPOLLO SET BONUS"
                , "&f&l➥ &fYou deal 40% more damage."
                , "&f&l➥ &fEnemies deal 35% less damage to you."
                , "&f&l➥ &fBypass Chad Sunglasses."
                , "&f&l➥ &fx1.5 Pet Duration."
                , "&f&l➥ &fImmune to Witcher Ability"
                , "&f&l➥ &fSpeed IV.",
                "");
    }

    @Override
    public boolean onPlayerDamaged(Player damaged, EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return false;
        }
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
        event.setDamage(event.getDamage(EntityDamageEvent.DamageModifier.BASE) * 0.70);
        return true;
    }

    @Override
    public boolean onPlayerAttacked(Player damager, EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return false;
        }
        if (this.isBlockedOutpostWorld(damager)) {
            return false;
        }
        event.setDamage(event.getDamage(EntityDamageEvent.DamageModifier.BASE) * 1.40);
        this.removeResistance(damager);
        return true;
    }

    @Override
    public boolean onPlayerDamagedBonus(Player damaged, int multiplier, EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return false;
        }
        if (event.isCancelled()) {
            return false;
        }
        if (this.isBlockedOutpostWorld(damaged)) {
            return false;
        }
        this.modifyDamage(damaged, event, -10.0, multiplier);
        return false;
    }

    @Override
    public boolean onPlayerAttackedBonus(Player damager, int applied, EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return false;
        }
        this.removeResistance(damager);
        if (this.isBlockedOutpostWorld(damager)) {
            return false;
        }
        return this.modifyDamage(damager, event, 15.0, applied);
    }

    @Override
    public void onEquip(Player player) {
        EnchantmentAPI.getEnchantment("Gears").applyEquipEffect(player, 4);
    }

    @Override
    public void onUnequip(Player player) {
        EnchantmentAPI.getEnchantment("Gears").applyUnequipEffect(player, 4);
    }


    @Override
    public void onPlayerEvent(Event paramEvent) {
    }
}

