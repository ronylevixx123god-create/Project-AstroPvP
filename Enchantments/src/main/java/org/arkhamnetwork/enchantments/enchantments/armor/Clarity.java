package org.arkhamnetwork.enchantments.enchantments.armor;

import com.google.common.collect.Lists;
import com.google.common.collect.MapMaker;
import com.rit.sucy.CustomEnchantment;
import de.inventivegames.particle.ParticleEffect;
import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.enchantments.swords.Blind;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Map;

public class Clarity extends CustomEnchantment {
    private Map<Player, Integer> claritiedPlayers;

    public Clarity() {
        super("Clarity", GeneralUtil.armor, 2);
        this.claritiedPlayers = new MapMaker().weakKeys().concurrencyLevel(32).makeMap();
        this.max = 3;
        this.base = 10.0;
        this.interval = 5.0;
        Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin) Enchantments.getInstance(), () -> this.claritiedPlayers.forEach((player, maxBlindLevelToRemove) -> {
            if (!(!player.isValid())) {
                if (!player.hasMetadata(Blind.RECENTLY_BLINDED_METADATA) || ((MetadataValue) player.getMetadata(Blind.RECENTLY_BLINDED_METADATA).get(0)).asLong() <= System.currentTimeMillis()) {
                    Lists.newArrayList((Iterable) player.getActivePotionEffects()).iterator();
                    for (PotionEffect pe : player.getActivePotionEffects()) {
                        if (pe.getType().getName().equals("BLINDNESS") && pe.getAmplifier() <= maxBlindLevelToRemove) {
                            player.removePotionEffect(PotionEffectType.BLINDNESS);
                            player.playSound(player.getLocation(), Sound.FIZZ, 1.0f, 0.6f);
                            try {
                                ParticleEffect.SPELL.sendToPlayers(Bukkit.getOnlinePlayers(), player.getLocation(), 0, 1, 0, 0.6f, 20);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }), 20L, 10L);
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

    public void applyEquipEffect(final Player player, final int level) {
        final int maxBlindLevelToRemove = (level == 3) ? 3 : (level - 1);
        this.claritiedPlayers.put(player, maxBlindLevelToRemove);
        player.setMetadata("clarityEnchant", (MetadataValue) new FixedMetadataValue((Plugin) Enchantments.getInstance(), (Object) level));
    }

    public void applyUnequipEffect(final Player player, final int level) {
        player.removeMetadata("clarityEnchant", (Plugin) Enchantments.getInstance());
        this.claritiedPlayers.remove(player);
    }
}