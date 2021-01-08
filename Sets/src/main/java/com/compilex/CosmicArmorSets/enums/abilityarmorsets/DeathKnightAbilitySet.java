package com.compilex.CosmicArmorSets.enums.abilityarmorsets;

import com.compilex.CosmicArmorSets.CosmicArmorSetsAPI;
import com.compilex.CosmicArmorSets.enums.AbilityArmorSet;
import com.compilex.CosmicArmorSets.enums.AbilityArmorSetInfo;
import com.compilex.CosmicArmorSets.enums.HeroicArmorType;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.ItemBuilder;
import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import com.google.common.collect.Lists;
import org.arkhamnetwork.enchantments.EnchantmentBook;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class DeathKnightAbilitySet
        extends AbilityArmorSetInfo {
    private final String color = CC.Blue;
    private final String bulletPoint = this.color + CC.Bold + " * " + this.color;
    private final Color armorColor = Color.fromRGB(2243001);

    public Color getArmorColor() {
        return this.armorColor;
    }

    @Override
    public List<String> getAbilityArmorLore() {
        return Lists.newArrayList();
    }

    @Override
    public ItemStack getHelmet(Player player, boolean applyTransmog, boolean mastery) {
        ItemStack helmet = this.setLeatherColor(CosmicArmorSetsAPI.convertItemToHeroic(AbilityArmorSet.DEATH_KNIGHT.applyAbilityNBT(new NBTWrapper(new ItemBuilder(Material.DIAMOND_HELMET, this.color + CC.Bold + "Crown of the Lich King").build())).build(), HeroicArmorType.HELMET, true), this.armorColor);
        this.addNearlyMaxedEnchants(helmet, "Armored", "Implants", "Drunk", "Arrow Deflect", "Death God", "Protection", "Ender Shift", "Aquatic", "Repair Guard");
        if (this.random.nextDouble() <= 0.6) {
            this.addNearlyMaxed(helmet, "Valor");
        }
        if (this.random.nextDouble() <= 0.6) {
            this.addNearlyMaxed(helmet, "Angelic");
        }
        if (this.random.nextDouble() <= 0.6) {
            this.addNearlyMaxed(helmet, "Tank");
        }
        if (this.random.nextDouble() <= 0.2) {
            this.addNearlyMaxedEnchants(helmet, "Paladin Armored");
        }
        if (this.random.nextDouble() <= 0.175) {
            this.addNearlyMaxedEnchants(helmet, "Alien Implants");
        }
        if (this.random.nextDouble() <= 0.35) {
            this.addNearlyMaxed(helmet, "Immortal");
        }
        if (mastery) {
            this.addRandomMasteryEnchants(helmet, 2);
        }
        return applyTransmog ? EnchantmentBook.applyTransmogScroll(helmet) : helmet;
    }

    @Override
    public ItemStack getChestplate(Player player, boolean applyTransmog, boolean mastery) {
        ItemStack chestplate = this.setLeatherColor(CosmicArmorSetsAPI.convertItemToHeroic(AbilityArmorSet.DEATH_KNIGHT.applyAbilityNBT(new NBTWrapper(new ItemBuilder(Material.DIAMOND_CHESTPLATE, this.color + CC.Bold + "Death Knight Platebody").build())).build(), HeroicArmorType.CHESTPLATE, true), this.armorColor);
        this.addNearlyMaxedEnchants(chestplate, "Armored", "Overload", "Deathbringer", "Enlighted", "Diminish", "Aegis", "Leadership", "Blood Lust", "Blood Link");
        if (this.random.nextDouble() <= 0.6) {
            this.addNearlyMaxed(chestplate, "Valor");
        }
        if (this.random.nextDouble() <= 0.6) {
            this.addNearlyMaxed(chestplate, "Angelic");
        }
        if (this.random.nextDouble() <= 0.6) {
            this.addNearlyMaxed(chestplate, "Tank");
        }
        if (this.random.nextDouble() <= 0.2) {
            this.addNearlyMaxed(chestplate, "Paladin Armored");
        }
        if (this.random.nextDouble() <= 0.175) {
            this.addNearlyMaxed(chestplate, "Godly Overload");
        }
        if (this.random.nextDouble() <= 0.225) {
            this.addNearlyMaxed(chestplate, "Planetary Deathbringer");
        }
        if (this.random.nextDouble() <= 0.225) {
            this.addNearlyMaxed(chestplate, "Divine Enlighted");
        }
        if (this.random.nextDouble() <= 0.15) {
            this.addNearlyMaxed(chestplate, "Vengeful Diminish");
        }
        if (mastery) {
            this.addRandomMasteryEnchants(chestplate, 2);
        }
        return applyTransmog ? EnchantmentBook.applyTransmogScroll(chestplate) : chestplate;
    }

    @Override
    public ItemStack getLeggings(Player player, boolean applyTransmog, boolean mastery) {
        ItemStack leggings = this.setLeatherColor(CosmicArmorSetsAPI.convertItemToHeroic(AbilityArmorSet.DEATH_KNIGHT.applyAbilityNBT(new NBTWrapper(new ItemBuilder(Material.DIAMOND_LEGGINGS, this.color + CC.Bold + "Death Knight Leggings").build())).build(), HeroicArmorType.LEGGINGS, true), this.armorColor);
        this.addNearlyMaxedEnchants(leggings, "Armored", "Nature Wrath", "Dodge", "Obsidianshield", "Cactus", "Lucky", "Voodoo", "Spirits");
        if (this.random.nextDouble() <= 0.6) {
            this.addNearlyMaxed(leggings, "Valor");
        }
        if (this.random.nextDouble() <= 0.6) {
            this.addNearlyMaxed(leggings, "Angelic");
        }
        if (this.random.nextDouble() <= 0.6) {
            this.addNearlyMaxed(leggings, "Tank");
        }
        if (this.random.nextDouble() <= 0.2) {
            this.addNearlyMaxed(leggings, "Paladin Armored");
        }
        if (this.random.nextDouble() <= 0.3) {
            this.addNearlyMaxed(leggings, "Immortal");
        }
        if (mastery) {
            this.addRandomMasteryEnchants(leggings, 2);
        }
        return applyTransmog ? EnchantmentBook.applyTransmogScroll(leggings) : leggings;
    }

    @Override
    public ItemStack getBoots(Player player, boolean applyTransmog, boolean mastery) {
        ItemStack boots = this.setLeatherColor(CosmicArmorSetsAPI.convertItemToHeroic(AbilityArmorSet.DEATH_KNIGHT.applyAbilityNBT(new NBTWrapper(new ItemBuilder(Material.DIAMOND_BOOTS, this.color + CC.Bold + "Boots of the Scourge").build())).build(), HeroicArmorType.BOOTS, true), this.armorColor);
        this.addNearlyMaxedEnchants(boots, "Armored", "Metaphysical", "Ender Walker", "Springs", "Rocket Escape", "Phoenix", "Guardians", "Clarity");
        this.addMaxedEnchant(boots, "Gears");
        if (this.random.nextDouble() <= 0.6) {
            this.addNearlyMaxed(boots, "Valor");
        }
        if (this.random.nextDouble() <= 0.6) {
            this.addNearlyMaxed(boots, "Angelic");
        }
        if (this.random.nextDouble() <= 0.2) {
            this.addNearlyMaxed(boots, "Paladin Armored");
        }
        if (this.random.nextDouble() <= 0.225) {
            this.addNearlyMaxed(boots, "Ethereal Dodge");
        }
        if (this.random.nextDouble() <= 0.125) {
            this.addNearlyMaxed(boots, "Guided Rocket Escape");
        }
        if (mastery) {
            this.addRandomMasteryEnchants(boots, 2);
        }
        return applyTransmog ? EnchantmentBook.applyTransmogScroll(boots) : boots;
    }

    public ItemStack addRandomMasteryEnchants(ItemStack item, int count) {
        ArrayList enchants = Lists.newArrayList((Object[]) new String[]{"Chain Lifesteal", "Death Pact", "Tombstone", "Permafrost"});
        for (int i = 0; i < count && !enchants.isEmpty(); ++i) {
            String random = (String) enchants.get(ThreadLocalRandom.current().nextInt(enchants.size()));
            if (random != null) {
                enchants.remove(random);
            }
            this.addNearlyMaxed(item, random);
        }
        return item;
    }

    @Override
    public List<String> getEquipDescription() {
        return Lists.newArrayList();
    }

    @Override
    public boolean onPlayerDamaged(Player damaged, EntityDamageByEntityEvent event) {
        return false;
    }

    @Override
    public boolean onPlayerAttacked(Player damager, EntityDamageByEntityEvent event) {
        return false;
    }
}

