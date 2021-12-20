package com.compilex.CosmicArmorSets.enums.armorsets;

import com.compilex.CosmicArmorSets.enums.ArmorSetInfo;
import com.compilex.CosmicArmorSets.enums.OtherPlayerEventListenerSet;
import com.compilex.CosmicArmorSets.enums.WeaponedArmorSet;
import com.compilex.CosmicArmorSets.utils.ItemUtils;
import com.compilex.CosmicArmorSets.utils.TranslateList;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.ItemBuilder;
import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import com.cosmicpvp.cosmicutils.utils.WorldGuardUtils;
import com.rit.sucy.EnchantmentAPI;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class SantaArmorSet extends ArmorSetInfo
        implements OtherPlayerEventListenerSet, WeaponedArmorSet {
    public static List<Block> blockList = new ArrayList<Block>();

    @Override
    public ItemStack getHelmet(boolean applyTransmog) {
        return this.getWithTransmog(applyTransmog, ItemUtils.applyCustomEnchantments(ItemUtils.buildItem(Material.DIAMOND_HELMET, (short) 0, "&2&lChristmas Hat", CC.Gray + "Christmas 2021 Edition", "", "&2&lCHRISTMAS SET BONUS", "&f&l* &fYou deal 40% more damage.", "&f&l* &fEnemies deal 35% less damage to you.", "&f&l* &fBypass Chad Sunglasses.", "&f&l* &fx1.5 Pet Duration.", "&f&l* &fImmune to Witcher Pet", "&f&l* &fSpeed IV.", "&7(&oRequires all 4 Apollo items.&7)"), new LinkedList(Arrays.asList(EnchantmentAPI.getEnchantment("Godly Overload"), EnchantmentAPI.getEnchantment("Paladin Armored"), EnchantmentAPI.getEnchantment("Blood Lust"), EnchantmentAPI.getEnchantment("Enlighted"), EnchantmentAPI.getEnchantment("Deathbringer"), EnchantmentAPI.getEnchantment("Armored"), EnchantmentAPI.getEnchantment("Tank"), EnchantmentAPI.getEnchantment("Angelic"), EnchantmentAPI.getEnchantment("Arrow Deflect"), EnchantmentAPI.getEnchantment("Dodge"), EnchantmentAPI.getEnchantment("Valor"), EnchantmentAPI.getEnchantment("Aegis"), EnchantmentAPI.getEnchantment("Spirit Link"), EnchantmentAPI.getEnchantment("Leadership")))));
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
return true;
    }

    @Override
    public boolean onPlayerAttacked(Player damager, EntityDamageByEntityEvent event) {
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
        return false;
    }

    @Override
    public void onEquip(Player player) {
        String regionName = WorldGuardUtils.getRegionName(player.getLocation());
        if (regionName.contains("parkour") && !regionName.contains("enchants"))
            return;
        //EnchantmentAPI.getEnchantment("Gears").applyEquipEffect(player, 4);
    }

    @Override
    public void onUnequip(Player player) {
        // EnchantmentAPI.getEnchantment("Gears").applyUnequipEffect(player, 4);
    }


    @Override
    public void onPlayerEvent(Event paramEvent) {
    }

    @Override
    public ItemStack getWeapon(boolean p0) {
        return (new NBTWrapper((new ItemBuilder(Material.DIAMOND_SWORD, CC.DarkGreenB + "Santa Candy Cane", CC.Gray + "A titan-forged grandmaster", CC.Gray + "warhammer that crushes all.", "", CC.DarkGreenB + "SANTA WEAPON BONUS", CC.DarkGreen + "15% Damage Reduction.", CC.DarkGreen + "Immune to Ultra Strike",  CC.Gray + "(" + CC.GrayI + "Requires all 4 santa items." + CC.Gray + ")")).enchant(Enchantment.DAMAGE_ALL, 5).enchant(Enchantment.DURABILITY, 3).build())).setString("cosmicType", "SANTA_SWORD").build();
    }
}

