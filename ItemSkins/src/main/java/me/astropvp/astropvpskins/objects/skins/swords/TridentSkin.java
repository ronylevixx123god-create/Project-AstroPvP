package me.astropvp.astropvpskins.objects.skins.swords;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.WorldGuardUtils;
import me.astropvp.astropvpskins.enums.SkinType;
import me.astropvp.astropvpskins.objects.ItemSkin;
import org.arkhamnetwork.enchantments.enchantments.axes.Blessed;
import org.arkhamnetwork.enchantments.enchantments.axes.Hex;
import org.arkhamnetwork.enchantments.mastery.MasteryMarkOfTheBeast;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class TridentSkin extends ItemSkin {

    public void onAttack(Player damager, Entity damaged, EntityDamageByEntityEvent event) {
        if (event.isCancelled()) return;
        if (!WorldGuardUtils.isPvPEnabled(damaged.getLocation()) || !WorldGuardUtils.isPvPEnabled(damager.getLocation())) return;
        if(Math.random() <= 0.08) {
            damaged.sendMessage("");
            damaged.sendMessage(CC.translate("&3&l* TRIDENT OF DEEP &7[" + damager.getName() + "] &3&l*"));
            damaged.sendMessage(CC.translate("&3&l * &7+ Hex"));
            damaged.sendMessage(CC.translate("&3&l * &7+ Mark Of the Beast"));
            damaged.sendMessage(CC.translate("&7"));
            (new Hex()).applyEffect((LivingEntity)damager, (LivingEntity)damaged, 4, event);
            (new MasteryMarkOfTheBeast()).applyDefenseEffect((LivingEntity)damager, (LivingEntity)damaged, 6, event);

        }
    }
}

