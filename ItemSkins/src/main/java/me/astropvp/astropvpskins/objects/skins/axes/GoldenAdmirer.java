package me.astropvp.astropvpskins.objects.skins.axes;

import com.compilex.CosmicArmorSets.CosmicArmorSetsAPI;
import com.compilex.CosmicArmorSets.enums.ArmorSet;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.WorldGuardUtils;
import me.astropvp.astropvpskins.AstroItemSkinsPlugin;
import me.astropvp.astropvpskins.objects.ItemSkin;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

public class GoldenAdmirer extends ItemSkin {
    public void onAttack(Player damager, Entity damaged, EntityDamageByEntityEvent event) {
        if(!(damaged instanceof Player))
            return;
        if (event.isCancelled()) return;
        if (!WorldGuardUtils.isPvPEnabled(damaged.getLocation()) || !WorldGuardUtils.isPvPEnabled(damager.getLocation())) return;
        if(damager.hasMetadata("admirer_disabled"))
            return;
        double chance = 0;
        if(CosmicArmorSetsAPI.getArmorSet((Player) damaged) != null && CosmicArmorSetsAPI.getArmorSet((Player) damaged) == ArmorSet.GOBLIN)
            chance += 35;
        if(CosmicArmorSetsAPI.getBonusCrystalsEquipped((Player) damaged) != null && CosmicArmorSetsAPI.getBonusCrystalsEquipped((Player) damaged).containsKey(ArmorSet.GOBLIN)) {
            int applied = CosmicArmorSetsAPI.getBonusCrystalsEquipped((Player) damaged).get(ArmorSet.GOBLIN);
            chance += applied * 8;
        }
        if(Math.random() < chance) {
            damager.sendMessage(CC.translate("&6&l*&e&l*&6&l* &e&lGOBLIN ARMOR SET &7(Blocked " + damaged.getName() + "'s Golden Admirer) &6&l*&e&l*&6&l*"));
            damaged.sendMessage(CC.translate("&6&l** GOLDEN ADMIRER BLOCKED &7(" + damager.getName() + "'s Goblin Armor Set) &6&l**"));
            ((Player) damager).playSound(damager.getLocation(), Sound.VILLAGER_YES, 0.5f, 0.5f);
            damager.setMetadata("admirer_disabled", new FixedMetadataValue(AstroItemSkinsPlugin.getInstance(), System.currentTimeMillis()));
            Bukkit.getScheduler().runTaskLaterAsynchronously(AstroItemSkinsPlugin.getInstance(), () -> damager.removeMetadata("admirer_disabled", AstroItemSkinsPlugin.getInstance()), 200L);
            return;
        }
        if(isAdmirerSilenced(((Player) damaged).getPlayer()))
            return;
        damaged.setMetadata("admirerSilence", (MetadataValue)new FixedMetadataValue((Plugin) AstroItemSkinsPlugin.getInstance(), System.currentTimeMillis() + 10000));
        damager.sendMessage(CC.GoldB + "** GOLDEN ADMIRER " + CC.Gray + "[10s] " + CC.GoldB + "**");
        damaged.sendMessage(CC.GoldB + "** GOLDEN ADMIRER " + CC.Gray + "[" + damager.getName() +  "] " + CC.GoldB + "**");
    }

    public static boolean isAdmirerSilenced(Player player) {
        return (player.hasMetadata("admirerSilence") && player.getMetadata("admirerSilence").get(0).asLong() > System.currentTimeMillis());
    }
}
