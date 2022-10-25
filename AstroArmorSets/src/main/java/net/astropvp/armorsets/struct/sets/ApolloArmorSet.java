package net.astropvp.armorsets.struct.sets;

import com.cosmicpvp.cosmicutils.itembuilder.ItemBuilder;
import com.cosmicpvp.cosmicutils.utils.CC;
import net.astropvp.armorsets.AstroArmorSets;
import net.astropvp.armorsets.struct.ArmorSet;
import net.astropvp.armorsets.struct.ArmorSetWeapon;
import net.astropvp.armorsets.utils.config.ConfigManager;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ApolloArmorSet extends ArmorSet implements ArmorSetWeapon {
    public ItemStack getHelmet() {
        return (new ItemBuilder(Material.DIAMOND_HELMET)).setName(CC.translate("&f&lApollo Crown")).addLoreLines(CC.translate("&fTApollo God Crown.")).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchantment(Enchantment.DURABILITY, 3).build();
    }

    public ItemStack getChestplate() {
        return (new ItemBuilder(Material.DIAMOND_CHESTPLATE)).setName(CC.translate("&f&lApollo Torso")).addLoreLines(CC.translate("&fTApollo God Torso.")).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchantment(Enchantment.DURABILITY, 3).build();
    }

    public ItemStack getLeggings() {
        return (new ItemBuilder(Material.DIAMOND_LEGGINGS)).setName(CC.translate("&f&lApollo Jeans")).addLoreLines(CC.translate("&fTApollo God Jeans.")).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchantment(Enchantment.DURABILITY, 3).build();
    }

    public ItemStack getBoots() {
        return (new ItemBuilder(Material.DIAMOND_BOOTS)).setName(CC.translate("&f&lApollo Kicks")).addLoreLines(
                CC.translate("&fTApollo God Kicks."))
                .addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchantment(Enchantment.DURABILITY, 3).build();
    }

    public void onAttack(Player attacker, Entity attacked, EntityDamageByEntityEvent event) {
        double increasePercent = 40 * (1 + (AstroArmorSets.getInstance().getConfigManager().getConfig(ConfigManager.ConfigFile.SETTINGS).getInt("appoloSetDamageIncrease")  *  0.01));
        event.setDamage(event.getDamage() * (1 + (increasePercent  * 0.01)));    }

    public void onAttackCrystal(Player attacker, Entity attacked, EntityDamageByEntityEvent event, int amount) {
        double increasePercent = 15;
        event.setDamage(event.getDamage() * (1 + (increasePercent * amount * 0.01)));    }

    public void onDamagedCrystal(Entity attacker, Player attacked, EntityDamageByEntityEvent event, int amount) {
        double reductionPercent = 10;
        event.setDamage(event.getDamage() * (1-(reductionPercent*amount*0.01)));    }

    public void onDamaged(Entity attacker, Player attacked, EntityDamageByEntityEvent event) {
        double reductionPercent = 25 * (1 + (AstroArmorSets.getInstance().getConfigManager().getConfig(ConfigManager.ConfigFile.SETTINGS).getInt("apolloSetDamageDecrease")  *  0.01));
        event.setDamage(event.getDamage() * (1-(reductionPercent*0.01)));
    }

    public List<String> getArmorLore() {
        List<String> lore = new ArrayList<>();
        lore.add(CC.translate("&b&lYETI SET BONUS"));
        lore.add(CC.translate("&8● &bDeal +10% damage to all enemies."));
        lore.add(CC.translate("&8● &bEnemies deal 10% less damage to you."));
        lore.add(CC.translate("&7(&oRequires all 4 yeti items.&7)"));
        return lore;
    }

    public List<String> getArmorCrystalLore() {
        List<String> message = new ArrayList<>();
        message.add(CC.Aqua + "-2.5% Incoming Damage");
        message.add(CC.Aqua + "+2.5% Outgoing Damage");
        return message;
    }

    public List<String> getArmorEquipMessage() {
        List<String> message = new ArrayList<>();
        message.add("");
        message.add(CC.translate("&b&lYETI SET BONUS"));
        message.add(CC.translate("&8● &b+10% Outgoing DMG"));
        message.add(CC.translate("&8● &b-10% Incoming DMG"));
        message.add("");
        return message;
    }