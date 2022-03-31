package net.astropvp.armorsets.struct.sets;

import com.cosmicpvp.cosmicutils.itembuilder.ItemBuilder;
import com.cosmicpvp.cosmicutils.utils.CC;
import net.astropvp.armorsets.AstroArmorSets;
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

public class TravelerArmorSet extends ArmorSet {
    public ItemStack getHelmet() {
        return (new ItemBuilder(Material.DIAMOND_HELMET)).setName(CC.translate("&5&lTraveler Hood")).addLoreLines(CC.translate("&5A hood that fades in and out.")).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchantment(Enchantment.DURABILITY, 3).build();
    }

    public ItemStack getChestplate() {
        return (new ItemBuilder(Material.DIAMOND_CHESTPLATE)).setName(CC.translate("&5&lTraveler Chestplate")).addLoreLines(CC.translate("&5A chestplate that fades in and out.")).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchantment(Enchantment.DURABILITY, 3).build();
    }

    public ItemStack getLeggings() {
        return (new ItemBuilder(Material.DIAMOND_LEGGINGS)).setName(CC.translate("&5&lTraveler Robes")).addLoreLines(CC.translate("&5Robes that fades in and out.")).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchantment(Enchantment.DURABILITY, 3).build();
    }

    public ItemStack getBoots() {
        return (new ItemBuilder(Material.DIAMOND_BOOTS)).setName(CC.translate("&5&lTraveler Sandals")).addLoreLines(CC.translate("&5A sandal that fades in and out.")).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchantment(Enchantment.DURABILITY, 3).build();
    }


    public void onDamagedCrystal(Entity attacker, Player attacked, EntityDamageByEntityEvent event, int amount) {
        //TODO trav ability
    }


    public void onDamaged(Entity attacker, Player attacked, EntityDamageByEntityEvent event) {
        //TODO trav ability
    }

    public void onAttack(Player attacker, Entity attacked, EntityDamageByEntityEvent event) {
        AstroDamageHandler.getInstance().getDamageHandler().increaseDamage(30* AstroArmorSets.CUSTOM_SET_MULTIPLIER, event, "TravelerSet");
    }

    public void onAttackCrystal(Player attacker, Entity attacked, EntityDamageByEntityEvent event, int amount) {
        AstroDamageHandler.getInstance().getDamageHandler().increaseDamage(7.5, event, "TravelerCrystal", amount);
    }

    public List<String> getArmorLore() {
        List<String> lore = new ArrayList<>();
        lore.add(CC.translate("&5&lTRAVELER SET BONUS"));
        lore.add(CC.translate("&8● &5Deal +30% damage to all enemies."));
        lore.add(CC.translate("&8● &5Dimensional Shift Passive Ability."));
        lore.add(CC.translate("&7(&oRequires all 4 traveler items.&7)"));
        return lore;
    }

    public List<String> getArmorCrystalLore() {
        List<String> message = new ArrayList<>();
        message.add(CC.DarkPurple + "+7.5% Outgoing Damage");
        message.add(CC.DarkPurple + "20% Dimensional Shift Ability");
        return message;
    }

    public List<String> getArmorEquipMessage() {
        List<String> message = new ArrayList<>();
        message.add("");
        message.add(CC.translate("&5&lTRAVELER SET BONUS"));
        message.add(CC.translate("&8● &5Deal +30% damage to all enemies."));
        message.add(CC.translate("&8● &5Dimensional Shift Passive Ability."));
        message.add("");
        return message;
    }



}
