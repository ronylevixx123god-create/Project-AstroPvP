package net.astropvp.armorsets.struct.sets;

import com.cosmicpvp.cosmicutils.itembuilder.ItemBuilder;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import net.astropvp.armorsets.AstroArmorSets;
import net.astropvp.armorsets.struct.ArmorSet;
import net.astropvp.armorsets.struct.ArmorSetWeapon;
import net.astropvp.damagehandler.AstroDamageHandler;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class PhantomArmorSet extends ArmorSet implements ArmorSetWeapon {
    public ItemStack getHelmet() {
        return (new ItemBuilder(Material.DIAMOND_HELMET)).setName(CC.translate("&c&lPhantom Hood")).addLoreLines(CC.translate("&cThe fabled hood of the Phantom.")).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchantment(Enchantment.DURABILITY, 3).build();
    }

    public ItemStack getChestplate() {
        return (new ItemBuilder(Material.DIAMOND_CHESTPLATE)).setName(CC.translate("&c&lPhantom Hood")).addLoreLines(CC.translate("&cThe legendary shroud of the Phantom.")).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchantment(Enchantment.DURABILITY, 3).build();
    }

    public ItemStack getLeggings() {
        return (new ItemBuilder(Material.DIAMOND_LEGGINGS)).setName(CC.translate("&c&lPhantom Hood")).addLoreLines(CC.translate("&cThe demonic robe of the Phantom.")).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchantment(Enchantment.DURABILITY, 3).build();
    }

    public ItemStack getBoots() {
        return (new ItemBuilder(Material.DIAMOND_BOOTS)).setName(CC.translate("&c&lPhantom Hood")).addLoreLines(
                CC.translate("&cThe silent sandals of the Phantom."))
                .addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchantment(Enchantment.DURABILITY, 3).build();
    }

    public void onAttack(Player attacker, Entity attacked, EntityDamageByEntityEvent event) {
        AstroDamageHandler.getInstance().getDamageHandler().increaseDamage(20, event, "PhantomSet");
    }

    public void onAttackCrystal(Player attacker, Entity attacked, EntityDamageByEntityEvent event, int amount) {
        AstroDamageHandler.getInstance().getDamageHandler().increaseDamage(5, event, "PhantomCrystal", amount);
    }

    public List<String> getArmorLore() {
        List<String> lore = new ArrayList<>();
        lore.add(CC.translate("&c&lPHANTOM SET BONUS"));
        lore.add(CC.translate("&8● &cDeal 25% damage to all enemies."));
        lore.add(CC.translate("&7(&oRequires all 4 phantom items.&7)"));
        return lore;
    }

    public List<String> getArmorCrystalLore() {
        List<String> message = new ArrayList<>();
        message.add(CC.Red + "+5% Damage");
        return message;
    }

    public List<String> getArmorEquipMessage() {
        List<String> message = new ArrayList<>();
        message.add("");
        message.add(CC.translate("&c&lPHANTOM SET BONUS"));
        message.add(CC.translate("&8● &c+25% DMG"));
        message.add("");
        return message;
    }

    public List<ItemStack> getWeapons() {
        List<ItemStack> weapons = new ArrayList<>();
        ItemStack sword = new NBTWrapper(new ItemBuilder(Material.DIAMOND_SWORD)
                .setName(CC.RedB + "Phantom Scythe")
                .addLoreLines(CC.Gray + "An eerie blade designed to"
                        , CC.Gray + "cut through the enemies."
                        , ""
                        , CC.RedB + "PHANTOM WEAPON BONUS"
                        , CC.translate("&8● &cDeal +10% damage to all enemies.")
                        , CC.Gray + "(" + CC.GrayI + "Requires all 4 phantom items." + CC.Gray + ")")
                .addEnchantment(Enchantment.DAMAGE_ALL, 5).addEnchantment(Enchantment.DURABILITY, 3).build()).setString("cosmicType", "PHANTOM_SWORD").build();

        weapons.add(sword);
        return weapons;
    }


}
