package me.astropvp.astropvpskins.objects.skins.swords;

import com.cosmicpvp.cosmicutils.utils.CC;
import me.astropvp.astropvpskins.AstroItemSkinsPlugin;
import me.astropvp.astropvpskins.objects.ItemSkin;
import org.arkhamnetwork.enchantments.mastery.SoulMaster;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.metadata.FixedMetadataValue;

public class MaulsSabre extends ItemSkin {

    @Override
    public void onAttacked(Player damaged, Entity damager, EntityDamageByEntityEvent event) {

    }

    @Override
    public void onAttack(Player damager, Entity damaged, EntityDamageByEntityEvent event) {
        if (!(damaged instanceof Player)) return;
        new SoulMaster().applyEffect(damager, (LivingEntity) damaged, 6, event);
        event.setDamage(event.getDamage() * 1.25);
        if (Math.random() < 0.04) {
            damaged.setMetadata("noDefensiveCrystals", new FixedMetadataValue(AstroItemSkinsPlugin.getInstance(), true));

            damaged.sendMessage(CC.translate("&c&l* " + damager.getName() + "'s MAULS SABRE &7[7s No Defensive Crystals] &c&l*"));
            damager.sendMessage(CC.translate("&c&l* MAULS SABRE &7[7s No Defensive Crystals] - " + damaged.getName() + " &c&l*"));

            Bukkit.getScheduler().runTaskLaterAsynchronously(AstroItemSkinsPlugin.getInstance(), () -> {
                damaged.removeMetadata("noDefensiveCrystals", AstroItemSkinsPlugin.getInstance());
            }, 7 * 20L);
        }
    }

}