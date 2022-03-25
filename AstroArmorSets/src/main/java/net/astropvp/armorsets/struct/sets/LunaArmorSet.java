package net.astropvp.armorsets.struct.sets;

import com.cosmicpvp.cosmicutils.itembuilder.ItemBuilder;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.StringUtils;
import com.cosmicpvp.cosmicutils.utils.WorldGuardUtils;
import me.astropvp.astropvpskins.objects.skins.amulets.CannonballAmulet;
import net.astropvp.armorsets.AstroArmorSets;
import net.astropvp.armorsets.struct.ArmorSet;
import net.astropvp.damagehandler.AstroDamageHandler;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class LunaArmorSet extends ArmorSet {
    public static List<UUID> lunaStrikedPlayers = new ArrayList<>();

    public ItemStack getHelmet() {
        return (new ItemBuilder(Material.DIAMOND_HELMET)).setName(CC.translate("&7&lLuna Helmet")).addLoreLines(CC.translate("&7From the galaxy to astro.")).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchantment(Enchantment.DURABILITY, 3).build();
    }

    public ItemStack getChestplate() {
        return (new ItemBuilder(Material.DIAMOND_CHESTPLATE)).setName(CC.translate("&7&lLuna Chain Torso")).addLoreLines(CC.translate("&7From the galaxy to astro.")).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchantment(Enchantment.DURABILITY, 3).build();
    }

    public ItemStack getLeggings() {
        return (new ItemBuilder(Material.DIAMOND_LEGGINGS)).setName(CC.translate("&7&lLuna Jeans")).addLoreLines(CC.translate("&7From the galaxy to astro.")).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchantment(Enchantment.DURABILITY, 3).build();
    }

    public ItemStack getBoots() {
        return (new ItemBuilder(Material.DIAMOND_BOOTS)).setName(CC.translate("&7&lLuna Kicks")).addLoreLines(CC.translate("&7From the galaxy to astro.")).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchantment(Enchantment.DURABILITY, 3).build();
    }


    public void onDamagedCrystal(Entity attacker, Player attacked, EntityDamageByEntityEvent event, int amount) {
        AstroDamageHandler.getInstance().getDamageHandler().reduceDamage(4*AstroArmorSets.CUSTOM_SET_MULTIPLIER, event, "LunaCrystal", amount);

    }


    public void onDamaged(Entity attacker, Player attacked, EntityDamageByEntityEvent event) {
        AstroDamageHandler.getInstance().getDamageHandler().reduceDamage(25, event, "LunaSet");

    }

    public void onAttack(Player attacker, Entity attacked, EntityDamageByEntityEvent event) {
        AstroDamageHandler.getInstance().getDamageHandler().increaseDamage(15, event, "LunaSet");
        if (Math.random() < 0.016 && attacked instanceof Player) {
            if (!WorldGuardUtils.isPvPEnabled(attacker.getLocation()) || !WorldGuardUtils.isPvPEnabled(attacked.getLocation())) return;

            lunaStrike((Player) attacked);
        }
    }

    public void onAttackCrystal(Player attacker, Entity attacked, EntityDamageByEntityEvent event, int amount) {
        AstroDamageHandler.getInstance().getDamageHandler().increaseDamage(6*AstroArmorSets.CUSTOM_SET_MULTIPLIER, event, "LunaCrystal", amount);
        if (Math.random() < 0.016 && attacked instanceof Player) {
            if (!WorldGuardUtils.isPvPEnabled(attacker.getLocation()) || !WorldGuardUtils.isPvPEnabled(attacked.getLocation())) return;

            lunaStrike((Player) attacked);
        }
    }

    public List<String> getArmorLore() {
        List<String> lore = new ArrayList<>();
        lore.add(CC.translate("&7&lLUNA SET BONUS"));
        lore.add(CC.translate("&8● &7Enemies deal 15% less damage to you."));
        lore.add(CC.translate("&8● &7Deal +35% damage to all enemies."));
        lore.add(CC.translate("&8● &7+10% Soul Enchant Damage."));
        lore.add(CC.translate("&8● &710% Luna Strike Ability."));
        lore.add(CC.translate("&7(&oRequires all 4 luna items.&7)"));
        return lore;
    }

    public List<String> getArmorCrystalLore() {
        List<String> message = new ArrayList<>();
        message.add(CC.Gray + "+6% Outgoing Damage");
        message.add(CC.Gray + "+10% Soul Enchant Damage");
        message.add(CC.Gray + "-4% Incoming Damage");
        message.add(CC.Gray + "10% Luna Strike Ability");
        message.add(CC.Gray + "+5% Monopoly Effect Chance");
        return message;
    }

    public List<String> getArmorEquipMessage() {
        List<String> message = new ArrayList<>();
        message.add("");
        message.add(CC.translate("&7&lLUNA SET BONUS"));
        message.add(CC.translate("&8● &7Enemies deal 15% less damage to you."));
        message.add(CC.translate("&8● &7Deal +35% damage to all enemies."));
        message.add(CC.translate("&8● &7+10% Soul Enchant Damage."));
        message.add(CC.translate("&8● &710% Luna Strike Ability."));
        message.add("");
        return message;
    }

    public void lunaStrike(Player enemy) {
        if (isLunaStriked(enemy)) return;

        if (CannonballAmulet.active.contains(enemy.getUniqueId())) {
            return;
        }


            lunaStrikedPlayers.add(enemy.getUniqueId());
        enemy.sendMessage("");
        enemy.sendMessage(StringUtils.getCenteredMessage(CC.translate("&8&l✰ &7&lLUNA STRIKE ABILITY (" + enemy.getName() + ") &8&l✰")));
        enemy.sendMessage(StringUtils.getCenteredMessage(CC.translate("&7&l* &7Increased Rage Stack Limit")));
        enemy.sendMessage(StringUtils.getCenteredMessage(CC.translate("&7&l* &7Negate " + enemy.getName() + "'s Soul Enchants (5s)")));
        enemy.sendMessage(StringUtils.getCenteredMessage(CC.translate("&7&l* &7Negate all incoming Pet Damage modifiers (5s)")));
        enemy.sendMessage("");

        Bukkit.getScheduler().runTaskLaterAsynchronously(AstroArmorSets.getInstance(), () -> {
            if (isLunaStriked(enemy)) {
                lunaStrikedPlayers.remove(enemy.getUniqueId());
            }
        }, 100L);
    }

    public static boolean isLunaStriked(Player player) {
        return lunaStrikedPlayers.contains(player.getUniqueId());
    }




}
