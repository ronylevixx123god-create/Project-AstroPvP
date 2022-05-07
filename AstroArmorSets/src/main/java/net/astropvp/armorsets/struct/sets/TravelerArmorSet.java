package net.astropvp.armorsets.struct.sets;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.WorldGuardUtils;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.perms.Relation;

import net.astropvp.armorsets.AstroArmorSets;
import net.astropvp.armorsets.struct.ArmorSet;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import com.cosmicpvp.cosmicutils.itembuilder.ItemBuilder;

import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Vector;

import java.util.*;
import java.util.stream.Collectors;

public class TravelerArmorSet extends ArmorSet implements Listener {


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

    }


    public void onDamaged(Entity attacker, Player attacked, EntityDamageByEntityEvent event) {

    }

    public void onAttack(Player attacker, Entity attacked, EntityDamageByEntityEvent event) {
        double increasePercent = 30;
        event.setDamage(event.getDamage() * 1 + (increasePercent  * 0.01));    }

    public void onAttackCrystal(Player attacker, Entity attacked, EntityDamageByEntityEvent event, int amount) {
        double increasePercent = 7.5;
        event.setDamage(event.getDamage() * 1 + (increasePercent * amount * 0.01));    }

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
