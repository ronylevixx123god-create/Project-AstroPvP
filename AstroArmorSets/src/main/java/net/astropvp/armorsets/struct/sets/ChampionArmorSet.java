package net.astropvp.armorsets.struct.sets;

import com.cosmicpvp.cosmicutils.itembuilder.ItemBuilder;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.rit.sucy.CustomEnchantment;
import me.astropvp.astropvpskins.AstroItemSkinsAPI;
import me.astropvp.astropvpskins.enums.SkinType;
import net.astropvp.armorsets.AstroArmorSets;
import net.astropvp.armorsets.struct.ArmorSet;
import net.astropvp.armorsets.utils.config.ConfigManager;
import org.arkhamnetwork.enchantments.enchantments.armor.Gears;
import org.arkhamnetwork.enchantments.enchantments.axes.Blessed;
import org.arkhamnetwork.enchantments.libs.PlayerPotionEffects;
import org.arkhamnetwork.enchantments.libs.SpecificCustomEnchantment;
import org.arkhamnetwork.enchantments.listeners.AutoBlessListener;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class ChampionArmorSet extends ArmorSet {
    public ItemStack getHelmet() {
        return (new ItemBuilder(Material.DIAMOND_HELMET)).setName(CC.translate("&6&lChampion Crown")).addLoreLines(CC.translate("&6Champion Winner Crown.")).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchantment(Enchantment.DURABILITY, 3).build();
    }

    public ItemStack getChestplate() {
        return (new ItemBuilder(Material.DIAMOND_CHESTPLATE)).setName(CC.translate("&6&lChampion Body")).addLoreLines(CC.translate("&6Champion Winner Chestplate.")).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchantment(Enchantment.DURABILITY, 3).build();
    }

    public ItemStack getLeggings() {
        return (new ItemBuilder(Material.DIAMOND_LEGGINGS)).setName(CC.translate("&6&lChampion Leggings")).addLoreLines(CC.translate("&6Champion Winner Leggings.")).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchantment(Enchantment.DURABILITY, 3).build();
    }

    public ItemStack getBoots() {
        return (new ItemBuilder(Material.DIAMOND_BOOTS)).setName(CC.translate("&6&lChampion Kicks")).addLoreLines(
                CC.translate("&6Champion Winner Boots."))
                .addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchantment(Enchantment.DURABILITY, 3).build();
    }

    @Override
    public int getLadderTier() {
        return 3;
    }

    public void onAttack(Player attacker, Entity attacked, EntityDamageByEntityEvent event) {
        double increasePercent = 35 * (1 + (AstroArmorSets.getInstance().getConfigManager().getConfig(ConfigManager.ConfigFile.SETTINGS).getInt("championSetDamageIncrease") * 0.01));
        event.setDamage(event.getDamage() * (1 + (increasePercent * 0.01)));
        if (Math.random() < 0.015) {
            if (Math.random() < 0.3 && AstroItemSkinsAPI.hasSkinEquipped((Player) attacked, SkinType.HALLOWEEN_2022_AMULET)) {
                attacked.sendMessage(CC.translate("&4&l* MOMENTO MORI &7[Blocked Champion Wrath] &4&l*"));
                return;
            }
            activateChampionAbility(attacker, (Player) attacked);
        }
    }

    public void onAttackCrystal(Player attacker, Entity attacked, EntityDamageByEntityEvent event, int amount) {
        double increasePercent = 8 * (1 + (AstroArmorSets.getInstance().getConfigManager().getConfig(ConfigManager.ConfigFile.SETTINGS).getInt("championSetDamageIncrease") * 0.01));
        event.setDamage(event.getDamage() * (1 + (increasePercent * amount * 0.01)));
        if (attacked instanceof Player) {

            if (Math.random() < 0.0025) {
                if (Math.random() < 0.3 && AstroItemSkinsAPI.hasSkinEquipped((Player) attacked, SkinType.HALLOWEEN_2022_AMULET)) {
                    attacked.sendMessage(CC.translate("&4&l* MOMENTO MORI &7[Blocked Champion Wrath] &4&l*"));
                    return;
                }
                activateChampionAbility(attacker, (Player) attacked);
            }
        }
    }

    public void onDamagedCrystal(Entity attacker, Player attacked, EntityDamageByEntityEvent event, int amount) {
        double reductionPercent = 4.5 * (1 + (AstroArmorSets.getInstance().getConfigManager().getConfig(ConfigManager.ConfigFile.SETTINGS).getInt("championSetDamageDecrease") * 0.01));
        event.setDamage(event.getDamage() * (1 - (reductionPercent * amount * 0.01)));
    }

    public void onDamaged(Entity attacker, Player attacked, EntityDamageByEntityEvent event) {
        double reductionPercent = 15 * (1 + (AstroArmorSets.getInstance().getConfigManager().getConfig(ConfigManager.ConfigFile.SETTINGS).getInt("championSetDamageDecrease") * 0.01));
        event.setDamage(event.getDamage() * (1 - (reductionPercent * 0.01)));
    }

    public List<String> getArmorLore() {
        List<String> lore = new ArrayList<>();
        lore.add(CC.translate("&6&lCHAMPION SET BONUS"));
        lore.add(CC.translate("&8● &6Deal +35% damage to all enemies."));
        lore.add(CC.translate("&8● &6Enemies deal 15% less damage to you."));
        lore.add(CC.translate("&8● &6Champion Wrath Ability."));
        lore.add(CC.translate("&8● &6Auto Bless"));
        lore.add(CC.translate("&7(&oRequires all 4 champion items.&7)"));
        return lore;
    }

    public List<String> getArmorCrystalLore() {
        List<String> message = new ArrayList<>();
        message.add(CC.Gold + "-4.5% Incoming Damage");
        message.add(CC.Gold + "+8% Outgoing Damage");
        message.add(CC.Gold + "Champion Wrath Ability");
        return message;
    }

    public List<String> getArmorEquipMessage() {
        List<String> message = new ArrayList<>();
        message.add("");
        message.add(CC.translate("&6&lCHAMPION SET BONUS"));
        message.add(CC.translate("&8● &6Deal +35% damage to all enemies."));
        message.add(CC.translate("&8● &6Enemies deal 15% less damage to you."));
        message.add(CC.translate("&8● &6Champion Wrath Ability."));
        message.add(CC.translate("&8● &6Auto Bless"));
        message.add("");
        return message;
    }

    public void onEquip(Player player) {
        Blessed.blessPlayer(player);
        AutoBlessListener.blessedPlayers.add(player.getUniqueId());
    }

    public void onUnequip(Player player) {
        AutoBlessListener.blessedPlayers.remove(player.getUniqueId());
    }

    public void activateChampionAbility(final Player player, final Player target) {
        if (target.hasMetadata("banshee_scrolls") || target.hasMetadata("bloodHoundNoBless") || player.hasMetadata("banshee_active")) {
            return;
        }

        player.sendMessage("");
        player.sendMessage(CC.translate((String) "&6&l* CHAMPION WRATH PROC *"));
        player.sendMessage(CC.translate((String) "&a&lBUFF: &cBanshee RKO Proc &aActivated"));
        player.sendMessage(CC.translate((String) "&a&lBUFF: &cAnti Utility Proc &aActivated"));
        player.sendMessage(CC.translate((String) "&4&lTARGET: &f") + target.getName());
        player.sendMessage("");
        target.sendMessage(CC.translate((String) ("&6&l* CHAMPION WRATH &7[&c" + player.getName() + "&7]")));
        target.setMetadata("noDefenseProcs", (MetadataValue) new FixedMetadataValue((Plugin) AstroArmorSets.getInstance(), (Object) true));
        player.setMetadata("immune_freeze", (MetadataValue) new FixedMetadataValue((Plugin) AstroArmorSets.getInstance(), (Object) true));
        target.setMetadata("banshee_scrolls", (MetadataValue) new FixedMetadataValue((Plugin) AstroArmorSets.getInstance(), (Object) true));
        target.setMetadata("champion_ability", (MetadataValue) new FixedMetadataValue((Plugin) AstroArmorSets.getInstance(), (Object) true));
        target.playSound(target.getLocation(), Sound.BLAZE_DEATH, 10.0f, 10.0f);
        PlayerPotionEffects.trackPotionEffect((Player) player, (SpecificCustomEnchantment) new SpecificCustomEnchantment((CustomEnchantment) new Gears(), 5), (PotionEffect) new PotionEffect(PotionEffectType.SPEED, 160, 4));
        new BukkitRunnable() {

            public void run() {
                target.removeMetadata("noDefenseProcs", (Plugin) AstroArmorSets.getInstance());
                player.removeMetadata("immune_freeze", (Plugin) AstroArmorSets.getInstance());
                target.removeMetadata("banshee_scrolls", (Plugin) AstroArmorSets.getInstance());
                target.removeMetadata("champion_ability", (Plugin) AstroArmorSets.getInstance());
                target.sendMessage(CC.translate((String) ("&6&l* CHAMPION WRATH FROM &7" + player.getName() + " &c&lEXPIRED &6&l*")));
                PlayerPotionEffects.untrackPotionEffect((Player) player, (PotionEffectType) PotionEffectType.SPEED);
                PlayerPotionEffects.trackPotionEffect((Player) player, (SpecificCustomEnchantment) new SpecificCustomEnchantment((CustomEnchantment) new Gears(), 3), (PotionEffect) new PotionEffect(PotionEffectType.SPEED, 160, 3));
            }
        }.runTaskLater((Plugin) AstroArmorSets.getInstance(), 160L);
    }
}
