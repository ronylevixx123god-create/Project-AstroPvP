package me.astropvp.astropvpskins.objects.skins.swords;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.WorldGuardUtils;
import com.rit.sucy.EnchantmentAPI;
import me.astropvp.astropvpskins.objects.ItemSkin;
import me.ifamasssxd.cosmicduel.DuelAPI;
import net.astropvp.core.killtracker.KillTrackerAPI;
import net.astropvp.damagehandler.AstroDamageHandler;
import org.arkhamnetwork.enchantments.enchantments.axes.Rogue;
import org.arkhamnetwork.enchantments.mastery.MasteryMarkOfTheBeast;
import org.arkhamnetwork.enchantments.mastery.SoulMaster;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class MutatedSeasonsBeatings extends ItemSkin {
    @Override
    public void onAttack(Player damager, Entity damaged, EntityDamageByEntityEvent event) {
        if (event.isCancelled()) return;
        if (DuelAPI.isSpectator(damager)) return;
        if (!WorldGuardUtils.isPvPEnabled(damaged.getLocation()) || !WorldGuardUtils.isPvPEnabled(damager.getLocation())) return;
        if (!(damaged instanceof Player)) return;
        if (((Player) damaged).getItemInHand() != null &&  EnchantmentAPI.itemHasEnchantment(((Player) damaged).getItemInHand(), "Hero Killer")) {
            AstroDamageHandler.getInstance().getDamageHandler().increaseDamage(10, event, "&4&lSeasons Beatings");
        }

        if (KillTrackerAPI.hasGodMode((Player) damaged)) {
            AstroDamageHandler.getInstance().getDamageHandler().increaseDamage(14, event, "&4&lSeasons Beatings");
        }


    }



}


