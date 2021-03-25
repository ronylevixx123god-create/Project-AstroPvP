package me.ifamasssxd.cosmicmasks.struct;


import com.compilex.CosmicOutposts.CosmicOutpostsAPI;
import com.compilex.CosmicOutposts.outpost.enums.OutpostTier;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import me.ifamasssxd.cosmicmasks.CosmicMasks;
import org.arkhamnetwork.enchantments.libs.AbstractPotionEffect;
import org.arkhamnetwork.enchantments.libs.PlayerPotionEffects;
import org.arkhamnetwork.enchantments.libs.SpecificCustomEnchantment;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class TerminatorMask extends CosmicMask {
    public void onEquip(Player player) {
        PlayerPotionEffects effects = PlayerPotionEffects.getTrackedPotionEffects(player);
        if (effects != null)
            effects.getEffects().forEach((enchantment, effect) -> {
                Iterator<AbstractPotionEffect> iterator = effect.iterator();
                while (iterator.hasNext()) {
                    AbstractPotionEffect pEffect = iterator.next();
                    if (pEffect.getEffectType().getId() == PotionEffectType.SPEED.getId() && pEffect.getPotionLevel() < 3) {
                        Bukkit.getLogger().info("[" + getClass().getSimpleName() + "] Found equipped speed: " + pEffect.getPotionLevel());
                        pEffect.setPotionLevel(3);
                        //player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 2147483647, pEffect.getPotionLevel()), true);
                    }
                }
            });
    }

    public void onUnequip(Player player) {
        //if (hasEffect(player, PotionEffectType.SPEED, 3, false))
            //player.removePotionEffect(PotionEffectType.SPEED);
        PlayerPotionEffects effects = PlayerPotionEffects.getTrackedPotionEffects(player);
        if (effects != null)
            effects.getEffects().forEach((enchantment, effect) -> {
                Iterator<AbstractPotionEffect> iterator = effect.iterator();
                while (iterator.hasNext()) {
                    AbstractPotionEffect pEffect = iterator.next();
                    if (pEffect.getEffectType().getId() == PotionEffectType.SPEED.getId()) {
                        Bukkit.getLogger().info("[" + getClass().getSimpleName() + "] Found equipped speed: " + pEffect.getPotionLevel());
                        pEffect.resetOriginalLevel();
                    }
                }
            });
    }

    public void onUpdate(Player player) {
        if (CosmicMasks.get().isOutposts()) {
            OutpostTier tier = CosmicOutpostsAPI.getOutpostTierOfWorld((LivingEntity)player);
            if (tier != null && tier != OutpostTier.COSMONAUT && tier != OutpostTier.NONE) {
                if (hasEffect(player, PotionEffectType.SPEED, 3, false))
                    //player.removePotionEffect(PotionEffectType.SPEED);
                return;
            }
        }
        boolean detectedNew = false;
        PlayerPotionEffects effects = PlayerPotionEffects.getTrackedPotionEffects(player);
        if (effects != null)
            for (Collection<AbstractPotionEffect> e : effects.getEffects().values()) {
                for (AbstractPotionEffect eff : e) {
                    if (eff.getEffectType().getId() == PotionEffectType.SPEED.getId() &&
                            eff.getPotionLevel() == eff.getOriginalLevel() && eff.getPotionLevel() != 3) {
                        //player.removePotionEffect(PotionEffectType.SPEED);
                        eff.setPotionLevel(3);
                        detectedNew = true;
                        Bukkit.getLogger().info("Removing reindeer speed boost being detected: " + eff.getEffectType());
                    }
                }
            }
        if ((!detectedNew && hasEffect(player, PotionEffectType.SPEED, 3, false)) || (
                !detectedNew && !player.hasPotionEffect(PotionEffectType.SPEED))) {

        }
            //addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 200, 3), true);
    }
}
