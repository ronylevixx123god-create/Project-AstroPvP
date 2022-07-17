package net.astropvp.armorsets.struct.sets;

import com.cosmicpvp.cosmicutils.itembuilder.ItemBuilder;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import net.astropvp.armorsets.struct.ArmorSet;
import net.astropvp.armorsets.struct.ArmorSetWeapon;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class NebulaArmorSet extends ArmorSet implements ArmorSetWeapon {
    public ItemStack getHelmet() {
        return (new ItemBuilder(Material.DIAMOND_HELMET)).setName(CC.translate("&5&lNebula Dome")).
                addLoreLines(CC.translate("&5Astronomical Nebula Dome.")).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchantment(Enchantment.DURABILITY, 3).build();
    }

    public ItemStack getChestplate() {
        return (new ItemBuilder(Material.DIAMOND_CHESTPLATE)).setName(CC.translate("&5&lNebula Suit")).
                addLoreLines(CC.translate("&5Astronomical Nebula Suit.")).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchantment(Enchantment.DURABILITY, 3).build();
    }

    public ItemStack getLeggings() {
        return (new ItemBuilder(Material.DIAMOND_LEGGINGS)).setName(CC.translate("&5&lNebula Shorts")).
                addLoreLines(CC.translate("&5Astronomical Nebula Shorts")).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchantment(Enchantment.DURABILITY, 3).build();
    }

    public ItemStack getBoots() {
        return (new ItemBuilder(Material.DIAMOND_BOOTS)).setName(CC.translate("&5&lNebula Space Boots")).addLoreLines(
                        CC.translate("&5Astronomical Nebula Space Boots."))
                .addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchantment(Enchantment.DURABILITY, 3).build();

    }

    public void onAttack(Player attacker, Entity attacked, EntityDamageByEntityEvent event) {
        double increasePercent = 10;
        event.setDamage(event.getDamage() * (1 + (increasePercent  * 0.01)));    }

    public void onAttackCrystal(Player attacker, Entity attacked, EntityDamageByEntityEvent event, int amount) {
        double increasePercent = 2.5;
        event.setDamage(event.getDamage() * (1 + (increasePercent * amount * 0.01)));    }

    public void onDamagedCrystal(Entity attacker, Player attacked, EntityDamageByEntityEvent event, int amount) {
        double reductionPercent = 2.5;
        event.setDamage(event.getDamage() * (1-(reductionPercent*amount*0.01)));    }

    public void onDamaged(Entity attacker, Player attacked, EntityDamageByEntityEvent event) {
        double reductionPercent = 10;
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
        message.add(CC.DarkPurple + "+8% Outgoing Damage");
        message.add(CC.DarkPurple + "-6% Incoming Damage");
        message.add(CC.DarkPurple + "Instant Health Pots heal for more HP");
        message.add(CC.DarkPurple + "15% Chance to bypass Heroic Armor");
        message.add(CC.DarkPurple + "10% Nebula Ability chance");
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

    public List<ItemStack> getWeapons() {
        List<ItemStack> weapons = new ArrayList<>();
        ItemStack sword = new NBTWrapper(new ItemBuilder(Material.DIAMOND_AXE)
                .setName(CC.RedB + "Yeti Maul")
                .addLoreLines(CC.Gray + "A penetrating axe used by a Yeti."
                        , ""
                        , CC.AquaB + "YETI WEAPON BONUS"
                        , CC.translate("&8● &bDeal +7.5% damage to all enemies")
                        , CC.translate("&8● &bBypass 50% of Heroic Armor")
                        , CC.Gray + "(" + CC.GrayI + "Requires all 4 yeti items." + CC.Gray + ")")
                .addEnchantment(Enchantment.DAMAGE_ALL, 5).addEnchantment(Enchantment.DURABILITY, 3).build()).setString("cosmicType", "YETI_AXE").build();

        weapons.add(sword);
        return weapons;
    }


}
