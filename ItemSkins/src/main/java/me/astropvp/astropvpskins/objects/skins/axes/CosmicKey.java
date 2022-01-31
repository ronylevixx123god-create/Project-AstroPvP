package me.astropvp.astropvpskins.objects.skins.axes;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.WorldGuardUtils;
import me.astropvp.astropvpskins.objects.ItemSkin;
import net.astropvp.damagehandler.AstroDamageHandler;
import org.arkhamnetwork.enchantments.enchantments.swords.DivineImmolation;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class CosmicKey extends ItemSkin {
    @Override
    public void onAttack(Player damager, Entity damaged, EntityDamageByEntityEvent event) {
        if (event.isCancelled()) return;
        if (!WorldGuardUtils.isPvPEnabled(damaged.getLocation()) || !WorldGuardUtils.isPvPEnabled(damager.getLocation())) return;
        new DivineImmolation().applyEffect(damager, (LivingEntity) damaged, 4, event);
        //code atlas 3 ability
        if (damager.getHealth() <= 15) {
            AstroDamageHandler.getInstance().getDamageHandler().increaseDamage(10, event, "&9&lCosmic Key");
            damager.sendMessage(CC.translate("&9&l** COSMIC KEY [&7+10% Damage] &9&l**"));
        }
        //code kronos 8 ability
    }
}


