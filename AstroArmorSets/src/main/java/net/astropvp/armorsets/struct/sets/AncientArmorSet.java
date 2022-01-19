package net.astropvp.armorsets.struct.sets;

import com.cosmicpvp.cosmicutils.itembuilder.ItemBuilder;
import com.cosmicpvp.cosmicutils.utils.CC;
import net.astropvp.armorsets.struct.ArmorSet;
import net.astropvp.damagehandler.AstroDamageHandler;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class AncientArmorSet extends ArmorSet {
    public ItemStack getHelmet() {
        return (new ItemBuilder(Material.DIAMOND_HELMET)).setName(CC.translate("&3&lAncient Cap")).addLoreLines(CC.translate("&3Powerful martine helmet.")).build();
    }

    public ItemStack getChestplate() {
        return (new ItemBuilder(Material.DIAMOND_CHESTPLATE)).setName(CC.translate("&3&lAncient Body")).addLoreLines(CC.translate("&3Powerful martine chestplate.")).build();
    }

    public ItemStack getLeggings() {
        return (new ItemBuilder(Material.DIAMOND_LEGGINGS)).setName(CC.translate("&3&lAncient Legs")).addLoreLines(CC.translate("&3Powerful martine leggings.")).build();
    }

    public ItemStack getBoots() {
        return (new ItemBuilder(Material.DIAMOND_BOOTS)).setName(CC.translate("&3&lAncient Boots")).addLoreLines(CC.translate("&3Powerful martine boots.")).build();
    }


    public void onDamagedCrystal(Entity attacker, Player attacked, EntityDamageByEntityEvent event, int amount) {
        AstroDamageHandler.getInstance().getDamageHandler().reduceDamage(3, event, "AncientCrystal", amount);
        //TODO Reduce kb effect
    }


    public void onDamaged(Entity attacker, Player attacked, EntityDamageByEntityEvent event) {
        AstroDamageHandler.getInstance().getDamageHandler().reduceDamage(15, event, "AncientSet");

    }

    public void onAttack(Player attacker, Entity attacked, EntityDamageByEntityEvent event) {
        AstroDamageHandler.getInstance().getDamageHandler().increaseDamage(35, event, "AncientSet");
        //TODO Ancient Ability

    }

    public void onAttackCrystal(Player attacker, Entity attacked, EntityDamageByEntityEvent event, int amount) {
        AstroDamageHandler.getInstance().getDamageHandler().increaseDamage(7, event, "AncientCrystal", amount);
        //TODO Ancient Ability

    }

    public List<String> getArmorLore() {
        List<String> lore = new ArrayList<>();
        lore.add(CC.translate("&3&lANCIENT SET BONUS"));
        lore.add(CC.translate("&8● &3Enemies deal 15% less damage to you."));
        lore.add(CC.translate("&8● &3Deal +35% damage to all enemies."));
        lore.add(CC.translate("&8● &3+10% Damage from Outgoing Soul Enchants."));
        lore.add(CC.translate("&8● &3-50% Knockback."));
        lore.add(CC.translate("&8● &3Auto Bless."));
        lore.add(CC.translate("&8● &3Leviathan's Curse Ability."));
        lore.add(CC.translate("&7(&oRequires all 4 ancient items.&7)"));
        return lore;
    }

    public List<String> getArmorCrystalLore() {
        List<String> message = new ArrayList<>();
        message.add(CC.DarkAqua + "+7% Outgoing Damage");
        message.add(CC.DarkAqua + "-3% Incoming Damage");
        message.add(CC.DarkAqua + "10% Leviathan's Curse Ability");
        return message;
    }

    public List<String> getArmorEquipMessage() {
        List<String> message = new ArrayList<>();
        message.add("");
        message.add(CC.translate("&3&lANCIENT SET BONUS"));
        message.add(CC.translate("&8● &3Enemies deal 15% less damage to you."));
        message.add(CC.translate("&8● &3Deal +35% damage to all enemies."));
        message.add(CC.translate("&8● &3+10% Damage from Outgoing Soul Enchants."));
        message.add(CC.translate("&8● &3-50% Knockback."));
        message.add(CC.translate("&8● &3Auto Bless."));
        message.add(CC.translate("&8● &3Leviathan's Curse Ability."));
        message.add("");
        return message;
    }



}
