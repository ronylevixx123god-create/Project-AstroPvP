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

public class YijkiArmorSet extends ArmorSet implements ArmorSetWeapon {
    public ItemStack getHelmet() {
        return (new ItemBuilder(Material.DIAMOND_HELMET)).setName(CC.translate("&f&lYijki Mask")).addLoreLines(CC.translate("&fYijki face mask.")).build();
    }

    public ItemStack getChestplate() {
        return (new ItemBuilder(Material.DIAMOND_CHESTPLATE)).setName(CC.translate("&f&lYijki Torso")).addLoreLines(CC.translate("&fThe forbidden mantel of yijki.")).build();
    }

    public ItemStack getLeggings() {
        return (new ItemBuilder(Material.DIAMOND_LEGGINGS)).setName(CC.translate("&f&lYijki Robeset")).addLoreLines(CC.translate("&fThe fabled robes of yijki.")).build();
    }

    public ItemStack getBoots() {
        return (new ItemBuilder(Material.DIAMOND_BOOTS)).setName(CC.translate("&f&lYijki Footwraps")).addLoreLines(CC.translate("&fLight as feather, heavy as stone.")).build();
    }


    public void onDamagedCrystal(Entity attacker, Player attacked, EntityDamageByEntityEvent event, int amount) {
        AstroDamageHandler.getInstance().getDamageHandler().reduceDamage(5, event, "YijkiCrystal", amount);
        //TODO yijki ability
    }


    public void onDamaged(Entity attacker, Player attacked, EntityDamageByEntityEvent event) {
        AstroDamageHandler.getInstance().getDamageHandler().reduceDamage(30, event, "YijkiSet");
        //TODO yijki ability

    }

    public List<String> getArmorLore() {
        List<String> lore = new ArrayList<>();
        lore.add(CC.translate("&f&lYIJKI SET BONUS"));
        lore.add(CC.translate("&8● &fEnemies deal 30% less damage to you."));
        lore.add(CC.translate("&8● &fRevenge of Yijki Passive Ability."));
        lore.add(CC.translate("&7(&oRequires all 4 yijki items.&7)"));
        return lore;
    }

    public List<String> getArmorCrystalLore() {
        List<String> message = new ArrayList<>();
        message.add(CC.White + "-5% Incoming Damage");
        message.add(CC.White + "20% Revenge of Yijki Ability");
        return message;
    }

    public List<String> getArmorEquipMessage() {
        List<String> message = new ArrayList<>();
        message.add("");
        message.add(CC.translate("&f&lYIJKI SET BONUS"));
        message.add(CC.translate("&8● &fEnemies deal 30% less damage to you."));
        message.add(CC.translate("&8● &fRevenge of Yijki Passive Ability."));
        message.add("");
        return message;
    }

    public List<ItemStack> getWeapons() {
        List<ItemStack> weapons = new ArrayList<>();
        ItemStack sword = new NBTWrapper(new ItemBuilder(Material.DIAMOND_SWORD)
                .setName(CC.translate("&f&lYijki World Ender"))
                .addLoreLines(CC.Gray + "They laughed at you, they"
                        ,CC.Gray + "An Absolute massive, corruped"
                        , CC.Gray + "executioner blade imbued with"
                        , CC.Gray + "highly destructive dark magic."
                        , ""
                        , CC.WhiteB + "YIJKI WEAPON BONUS"
                        , CC.translate("&8● &fDeal +20% damage to all enemies.")
                        , CC.translate("&8● &f+25% Revenge of Yijki Ability.")
                        , CC.Gray + "(" + CC.GrayI + "Requires all 4 yijki items." + CC.Gray + ")")
                .addEnchantment(Enchantment.DAMAGE_ALL, 5).addEnchantment(Enchantment.DURABILITY, 3).build()).setString("cosmicType", "YIJKI_SWORD").build();

        weapons.add(sword);
        return weapons;
    }


}
