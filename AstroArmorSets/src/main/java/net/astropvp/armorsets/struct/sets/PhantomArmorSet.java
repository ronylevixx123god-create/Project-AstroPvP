package net.astropvp.armorsets.struct.sets;

import com.cosmicpvp.cosmicutils.itembuilder.ItemBuilder;
import com.cosmicpvp.cosmicutils.utils.CC;
import net.astropvp.armorsets.struct.ArmorSet;
import net.astropvp.armorsets.struct.ArmorSetWeapon;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class PhantomArmorSet extends ArmorSet implements ArmorSetWeapon {
    public ItemStack getHelmet() {
        return (new ItemBuilder(Material.DIAMOND_HELMET)).setName(CC.translate("&c&lPhantom Hood")).addLoreLines(CC.translate("&cThe fabled hood of the Phantom.")).build();
    }

    public ItemStack getChestplate() {
        return (new ItemBuilder(Material.DIAMOND_CHESTPLATE)).setName(CC.translate("&c&lPhantom Hood")).addLoreLines(CC.translate("&cThe legendary shroud of the Phantom.")).build();
    }

    public ItemStack getLeggings() {
        return (new ItemBuilder(Material.DIAMOND_LEGGINGS)).setName(CC.translate("&c&lPhantom Hood")).addLoreLines(CC.translate("&cThe demonic robe of the Phantom.")).build();
    }

    public ItemStack getBoots() {
        return (new ItemBuilder(Material.DIAMOND_BOOTS)).setName(CC.translate("&c&lPhantom Hood")).addLoreLines(
                CC.translate("&cThe silent sandals of the Phantom."))
                .build();
    }

    public void onAttack(Player attacker, Entity attacked, EntityDamageByEntityEvent event) {
        event.setDamage(event.getDamage() * 1.25D);
    }

    public List<String> getArmorLore() {
        List<String> lore = new ArrayList<>();
        lore.add(CC.translate("&c&lPHANTOM SET BONUS"));
        lore.add(CC.translate("&cDeal 25% damage to all enemies."));
        lore.add(CC.translate("&7(&oRequires all 4 phantom items.&7)"));
        return lore;
    }

    public List<String> getArmorCrystalLore() {
        return null;
    }

    public List<String> getArmorEquipMessage() {
        List<String> message = new ArrayList<>();
        message.add("");
        message.add(CC.translate("&c&lPHANTOM SET BONUS:"));
        message.add(CC.translate("&c+25% DMG"));
        message.add("");
        return message;
    }

    public ItemStack[] getWeapons() {
        //code weapon here
       return null;
    }
}
