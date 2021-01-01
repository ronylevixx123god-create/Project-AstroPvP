package org.arkhamnetwork.enchantments.enchantments.armor;

import com.cosmicpvp.cosmicutils.utils.ParticleEffect;
import com.rit.sucy.*;
import org.bukkit.entity.*;
import org.arkhamnetwork.enchantments.util.*;
import org.arkhamnetwork.enchantments.*;
import org.arkhamnetwork.enchantments.enchantments.swords.*;
import com.google.common.collect.*;
import org.bukkit.potion.*;
import org.bukkit.*;
import org.arkhamnetwork.Arkkit.libs.*;
import org.bukkit.plugin.*;
import java.util.*;
import org.bukkit.metadata.*;

public class Clarity extends CustomEnchantment
{
    private Map<Player, Integer> claritiedPlayers;

    public Clarity() {
        super("Clarity", GeneralUtil.armor, 2);
        this.claritiedPlayers = new MapMaker().weakKeys().concurrencyLevel(32).makeMap();
        this.max = 3;
        this.base = 10.0;
        this.interval = 5.0;
        Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin)Enchantments.getInstance(), () -> this.claritiedPlayers.forEach((player, maxBlindLevelToRemove) -> {
            if (!(!player.isValid())) {
                if (!player.hasMetadata(Blind.RECENTLY_BLINDED_METADATA) || ((MetadataValue)player.getMetadata(Blind.RECENTLY_BLINDED_METADATA).get(0)).asLong() <= System.currentTimeMillis()) {
                    Lists.newArrayList((Iterable)player.getActivePotionEffects()).iterator();
                    Iterator<PotionEffect> iterator = player.getActivePotionEffects().iterator();
                    while (iterator.hasNext()) {
                        PotionEffect pe = iterator.next();
                        if (pe.getType().getName().equals("BLINDNESS") && pe.getAmplifier() <= maxBlindLevelToRemove) {
                            player.removePotionEffect(PotionEffectType.BLINDNESS);
                            player.playSound(player.getLocation(), Sound.FIZZ, 1.0f, 0.6f);
                            ParticleEffect.playEffect(ParticleEffect.SPELL, player.getEyeLocation(), 0.6f, 20);
                        }
                    }
                }
            }
        }), 3L, 3L);
    }

    public void applyEquipEffect(final Player player, final int level) {
        final int maxBlindLevelToRemove = (level == 3) ? 3 : (level - 1);
        this.claritiedPlayers.put(player, maxBlindLevelToRemove);
        player.setMetadata("clarityEnchant", (MetadataValue)new FixedMetadataValue((Plugin)Enchantments.getInstance(), (Object)level));
    }

    public static boolean canBlindnessApply(final Player player, final int blindLevel) {
        if (!player.hasMetadata("clarityEnchant")) {
            return true;
        }
        final int level = player.getMetadata("clarityEnchant").get(0).asInt();
        if (level <= 0) {
            return true;
        }
        final int maxBlindLevelToRemove = (level == 3) ? 3 : (level - 1);
        return blindLevel > maxBlindLevelToRemove;
    }

    public void applyUnequipEffect(final Player player, final int level) {
        player.removeMetadata("clarityEnchant", (Plugin)Enchantments.getInstance());
        this.claritiedPlayers.remove(player);
    }
}