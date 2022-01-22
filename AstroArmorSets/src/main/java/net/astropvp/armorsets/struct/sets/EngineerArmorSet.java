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

public class EngineerArmorSet extends ArmorSet implements ArmorSetWeapon {
    public ItemStack getHelmet() {
        return (new ItemBuilder(Material.DIAMOND_HELMET)).setName(CC.translate("&6&lEngineer Face Mask")).addLoreLines(CC.translate("&6Engineer face mask.")).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchantment(Enchantment.DURABILITY, 3).build();
    }

    public ItemStack getChestplate() {
        return (new ItemBuilder(Material.DIAMOND_CHESTPLATE)).setName(CC.translate("&6&lEngineer Explosion-Proof Torso")).addLoreLines(CC.translate("&6The mantel of engineers.")).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchantment(Enchantment.DURABILITY, 3).build();
    }

    public ItemStack getLeggings() {
        return (new ItemBuilder(Material.DIAMOND_LEGGINGS)).setName(CC.translate("&6&lEngineer Protective Chaps")).addLoreLines(CC.translate("&6The fabled robes of engineers.")).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchantment(Enchantment.DURABILITY, 3).build();
    }

    public ItemStack getBoots() {
        return (new ItemBuilder(Material.DIAMOND_BOOTS)).setName(CC.translate("&6&lEngineer Steel Toed Boots")).addLoreLines(CC.translate("&6Light as feather, heavy as stone.")).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchantment(Enchantment.DURABILITY, 3).build();
    }


    public void onDamagedCrystal(Entity attacker, Player attacked, EntityDamageByEntityEvent event, int amount) {
        AstroDamageHandler.getInstance().getDamageHandler().reduceDamage(9, event, "EngineerCrystal", amount);
        //TODO engineer ability
    }


    public void onDamaged(Entity attacker, Player attacked, EntityDamageByEntityEvent event) {
        AstroDamageHandler.getInstance().getDamageHandler().reduceDamage(35, event, "EngineerSet");
        //TODO engineer ability

    }

    public List<String> getArmorLore() {
        List<String> lore = new ArrayList<>();
        lore.add(CC.translate("&6&lENGINEER SET BONUS"));
        lore.add(CC.translate("&8● &6Enemies deal 35% less damage to you."));
        lore.add(CC.translate("&8● &6Invisible on /near."));
        lore.add(CC.translate("&8● &6Immune to Teleblock Enchant."));
        lore.add(CC.translate("&8● &6Gears IV."));
        lore.add(CC.translate("&8● &6B-2 Bomber Passive Ability."));
        lore.add(CC.translate("&7(&oRequires all 4 engineer items.&7)"));
        return lore;
    }

    public List<String> getArmorCrystalLore() {
        List<String> message = new ArrayList<>();
        message.add(CC.Gold + "-9% Incoming Damage");
        message.add(CC.Gold + "20% B-2 Bomber Passive Ability");
        return message;
    }

    public List<String> getArmorEquipMessage() {
        List<String> message = new ArrayList<>();
        message.add("");
        message.add(CC.translate("&6&lENGINEER SET BONUS"));
        message.add(CC.translate("&8● &6Enemies deal 35% less damage to you."));
        message.add(CC.translate("&8● &6Invisible on /near."));
        message.add(CC.translate("&8● &6Immune to Teleblock Enchant."));
        message.add(CC.translate("&8● &6Gears IV."));
        message.add(CC.translate("&8● &6B-2 Bomber Passive Ability."));
        message.add("");
        return message;
    }

    public List<ItemStack> getWeapons() {
        List<ItemStack> weapons = new ArrayList<>();
        ItemStack sword = new NBTWrapper(new ItemBuilder(Material.DIAMOND_SWORD)
                .setName(CC.translate("&6&lEngineer Hammer"))
                .addLoreLines(CC.Gray + "A titan-forged grandmaster"
                        ,CC.Gray + "warhammer that crushes all."
                        , ""
                        , CC.GoldB + "ENGINEER WEAPON BONUS"
                        , CC.translate("&8● &6Deal +15% damage to all enemies.")
                        , CC.translate("&8● &6+20% B-2 Bomber Ability.")
                        , CC.translate("&8● &6Immune to World Destroyer Pet.")
                        , CC.translate("&8● &6Immune to Obliterate, Shockwave & Guardian Enchant.")
                        , CC.Gray + "(" + CC.GrayI + "Requires all 4 yijki items." + CC.Gray + ")")
                .addEnchantment(Enchantment.DAMAGE_ALL, 5).addEnchantment(Enchantment.DURABILITY, 3).build()).setString("cosmicType", "ENGINEER_SWORD").build();

        weapons.add(sword);
        return weapons;
    }


}
