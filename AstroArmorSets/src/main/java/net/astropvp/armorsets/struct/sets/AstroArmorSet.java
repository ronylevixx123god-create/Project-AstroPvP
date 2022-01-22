package net.astropvp.armorsets.struct.sets;

import com.cosmicpvp.cosmicutils.itembuilder.ItemBuilder;
import com.cosmicpvp.cosmicutils.utils.CC;
import net.astropvp.armorsets.struct.ArmorSet;
import net.astropvp.damagehandler.AstroDamageHandler;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class AstroArmorSet extends ArmorSet {
    public ItemStack getHelmet() {
        return (new ItemBuilder(Material.DIAMOND_HELMET)).setName(CC.translate("&a&lAstro Cap")).addLoreLines(CC.translate("&aGodly astro helmet.")).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchantment(Enchantment.DURABILITY, 3).build();
    }

    public ItemStack getChestplate() {
        return (new ItemBuilder(Material.DIAMOND_CHESTPLATE)).setName(CC.translate("&a&lAstro Torso")).addLoreLines(CC.translate("&aGodly astro chestplate.")).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchantment(Enchantment.DURABILITY, 3).build();
    }

    public ItemStack getLeggings() {
        return (new ItemBuilder(Material.DIAMOND_LEGGINGS)).setName(CC.translate("&a&lAstro Pants")).addLoreLines(CC.translate("&aGodly astro leggings.")).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchantment(Enchantment.DURABILITY, 3).build();
    }

    public ItemStack getBoots() {
        return (new ItemBuilder(Material.DIAMOND_BOOTS)).setName(CC.translate("&a&lAstro Sneakers")).addLoreLines(CC.translate("&aGodly astro boots.")).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchantment(Enchantment.DURABILITY, 3).build();
    }


    public void onDamagedCrystal(Entity attacker, Player attacked, EntityDamageByEntityEvent event, int amount) {
        AstroDamageHandler.getInstance().getDamageHandler().reduceDamage(3, event, "AstroCrystal", amount);
        //TODO astro Ability
    }


    public void onDamaged(Entity attacker, Player attacked, EntityDamageByEntityEvent event) {
        AstroDamageHandler.getInstance().getDamageHandler().reduceDamage(10, event, "AstroSet");
        //TODO astro Ability

    }

    public void onAttack(Player attacker, Entity attacked, EntityDamageByEntityEvent event) {
        AstroDamageHandler.getInstance().getDamageHandler().increaseDamage(30, event, "AstroSet");

    }

    public void onAttackCrystal(Player attacker, Entity attacked, EntityDamageByEntityEvent event, int amount) {
        AstroDamageHandler.getInstance().getDamageHandler().increaseDamage(7, event, "AstroCrystal", amount);

    }

    public List<String> getArmorLore() {
        List<String> lore = new ArrayList<>();
        lore.add(CC.translate("&a&lASTRO SET BONUS"));
        lore.add(CC.translate("&8● &aEnemies deal 10% less damage to you."));
        lore.add(CC.translate("&8● &aDeal +30% damage to all enemies."));
        lore.add(CC.translate("&8● &a+15% Monopoly Effect Chance."));
        lore.add(CC.translate("&8● &aAstro Cage Ability."));
        lore.add(CC.translate("&7(&oRequires all 4 astro items.&7)"));
        return lore;
    }

    public List<String> getArmorCrystalLore() {
        List<String> message = new ArrayList<>();
        message.add(CC.Green + "+7% Outgoing Damage");
        message.add(CC.Green + "-3% Incoming Damage");
        message.add(CC.Green + "10% Astro Cage Ability");
        message.add(CC.Green + "+5% Monopoly Effect Chance");
        return message;
    }

    public List<String> getArmorEquipMessage() {
        List<String> message = new ArrayList<>();
        message.add("");
        message.add(CC.translate("&a&lASTRO SET BONUS"));
        message.add(CC.translate("&8● &aEnemies deal 10% less damage to you."));
        message.add(CC.translate("&8● &aDeal +30% damage to all enemies."));
        message.add(CC.translate("&8● &a+15% Monopoly Effect Chance."));
        message.add(CC.translate("&8● &aAstro Cage Ability."));
        message.add("");
        return message;
    }



}
