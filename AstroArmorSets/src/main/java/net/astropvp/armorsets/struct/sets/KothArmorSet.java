package net.astropvp.armorsets.struct.sets;

import com.cosmicpvp.cosmicutils.itembuilder.ItemBuilder;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
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

public class KothArmorSet extends ArmorSet implements ArmorSetWeapon {
    public ItemStack getHelmet() {
        return (new ItemBuilder(Material.DIAMOND_HELMET)).setName(CC.translate("&f&l&k;&r &c&lK&6&l.&e&lO&a&l.&b&lT&5&l.&d&lH &f&l&k;&r &f&l&nHelmet&r")).addLoreLines(
                CC.translate("&dReceived from opening the koth lootbag.")
        ).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchantment(Enchantment.DURABILITY, 3).build();
    }

    public ItemStack getChestplate() {
        return (new ItemBuilder(Material.DIAMOND_CHESTPLATE)).setName(CC.translate("&f&l&k;&r &c&lK&6&l.&e&lO&a&l.&b&lT&5&l.&d&lH &f&l&k;&r &f&l&nChestplate&r")).addLoreLines(CC.translate("&dReceived from opening the koth lootbag.")).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchantment(Enchantment.DURABILITY, 3).build();
    }

    public ItemStack getLeggings() {
        return (new ItemBuilder(Material.DIAMOND_LEGGINGS)).setName(CC.translate("&f&l&k;&r &c&lK&6&l.&e&lO&a&l.&b&lT&5&l.&d&lH &f&l&k;&r &f&l&nLeggings&r")).addLoreLines(CC.translate("&dReceived from opening the koth lootbag.")).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchantment(Enchantment.DURABILITY, 3).build();
    }

    public ItemStack getBoots() {
        return (new ItemBuilder(Material.DIAMOND_BOOTS)).setName(CC.translate("&f&l&k;&r &c&lK&6&l.&e&lO&a&l.&b&lT&5&l.&d&lH &f&l&k;&r &f&l&nBoots&r")).addLoreLines(CC.translate("&dReceived from opening the koth lootbag.")).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchantment(Enchantment.DURABILITY, 3).build();
    }

    public void onAttack(Player attacker, Entity attacked, EntityDamageByEntityEvent event) {
        if (attacked instanceof Player) {
            AstroDamageHandler.getInstance().getDamageHandler().increaseDamage(20, event, "KothSet");
        } else {
            AstroDamageHandler.getInstance().getDamageHandler().increaseDamage(50, event, "KothSetMob");
        }
    }

    public void onAttackCrystal(Player attacker, Entity attacked, EntityDamageByEntityEvent event, int amount) {
        if (attacked instanceof Player) {
            AstroDamageHandler.getInstance().getDamageHandler().increaseDamage(5, event, "KothCrystal", amount);
        } else {
            AstroDamageHandler.getInstance().getDamageHandler().increaseDamage(12.5, event, "KothCrystalMob", amount);
        }
    }


    public List<String> getArmorLore() {
        List<String> lore = new ArrayList<>();
        lore.add(CC.translate("&c&lK&6&l.&e&lO&a&l.&b&lT&5&l.&d&lH &f&lSET BONUS"));
        lore.add(CC.translate("&8● &bDeal +20% damage to all enemies."));
        lore.add(CC.translate("&8● &bDeal +50% damage to mobs."));
        lore.add(CC.translate("&8● &bAuto Bless."));
        lore.add(CC.translate("&7(&oRequires all 4 koth items.&7)"));
        return lore;
    }

    public List<String> getArmorCrystalLore() {
        List<String> message = new ArrayList<>();
        message.add(CC.Aqua + "+5% Outgoing Damage");
        message.add(CC.Aqua + "+12.5% PVE Damage");
        return message;
    }

    public List<String> getArmorEquipMessage() {
        List<String> message = new ArrayList<>();
        message.add("");
        message.add(CC.translate("&c&lK&6&l.&e&lO&a&l.&b&lT&5&l.&d&lH &f&lSET BONUS"));
        message.add(CC.translate("&8● &bDeal +20% damage to all enemies."));
        message.add(CC.translate("&8● &bDeal +50% damage to mobs."));
        message.add(CC.translate("&8● &bAuto Bless."));
        message.add("");
        return message;
    }

    public List<ItemStack> getWeapons() {
        List<ItemStack> weapons = new ArrayList<>();
        ItemStack sword = new NBTWrapper(new ItemBuilder(Material.GOLD_SWORD)
                .setName(CC.translate("&f&l&k;&r &c&lK&6&l.&e&lO&a&l.&b&lT&5&l.&d&lH &f&l&k;&r &f&l&nSword&r"))
                .addLoreLines(CC.Gray + "Received from opening the koth lootbag."
                        , ""
                        , CC.translate("&c&lK&6&l.&e&lO&a&l.&b&lT&5&l.&d&lH &f&lWEAPON BONUS")
                        , CC.translate("&8● &bDeal +7.5% damage to all enemies.")
                        , CC.Gray + "(" + CC.GrayI + "Requires all 4 koth items." + CC.Gray + ")")
                .addEnchantment(Enchantment.DAMAGE_ALL, 5).addEnchantment(Enchantment.DURABILITY, 3).build()).setString("cosmicType", "KOTH_SWORD").build();

        ItemStack axe = new NBTWrapper(new ItemBuilder(Material.GOLD_AXE)
                .setName(CC.translate("&f&l&k;&r &c&lK&6&l.&e&lO&a&l.&b&lT&5&l.&d&lH &f&l&k;&r &f&l&nAxe&r"))
                .addLoreLines(CC.Gray + "Received from opening the koth lootbag."
                        , ""
                        , CC.translate("&c&lK&6&l.&e&lO&a&l.&b&lT&5&l.&d&lH &f&lWEAPON BONUS")
                        , CC.translate("&8● &bDeal +50% Durability Damage.")
                        , CC.Gray + "(" + CC.GrayI + "Requires all 4 koth items." + CC.Gray + ")")
                .addEnchantment(Enchantment.DAMAGE_ALL, 5).addEnchantment(Enchantment.DURABILITY, 3).build()).setString("cosmicType", "KOTH_AXE").build();

        //TODO make axe heroic
        //TODO Make sword heroic
        weapons.add(axe);
        weapons.add(sword);
        return weapons;
    }


}
