package org.arkhamnetwork.enchantments.enchantments.axes;

import com.rit.sucy.*;
import org.arkhamnetwork.enchantments.util.*;
import com.google.common.collect.*;
import org.bukkit.event.entity.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import com.cosmicpvp.cosmicutils.customevents.*;
import org.bukkit.event.*;
import com.compilex.CosmicArmorSets.*;
import com.compilex.CosmicArmorSets.enums.*;
import net.minecraft.server.v1_7_R4.*;
import org.arkhamnetwork.enchantments.*;
import org.bukkit.metadata.*;
import org.bukkit.plugin.*;
import org.arkhamnetwork.enchantments.libs.*;
import org.bukkit.potion.*;
import java.util.*;

public class Blessed extends CustomEnchantment
{
    private static List<PotionEffectType> debuffs;
    
    public Blessed() {
        super("Blessed", GeneralUtil.axe, 7);
        this.max = 4;
        this.base = 30.0;
        this.interval = 10.0;
        Blessed.debuffs = (List<PotionEffectType>)Lists.newArrayList(new PotionEffectType[] { PotionEffectType.BLINDNESS, PotionEffectType.CONFUSION, PotionEffectType.HARM, PotionEffectType.HUNGER, PotionEffectType.POISON, PotionEffectType.SLOW, PotionEffectType.SLOW_DIGGING, PotionEffectType.WEAKNESS, PotionEffectType.WITHER });
    }
    
    public void applyEffect(final LivingEntity user, final LivingEntity target, final int enchantLevel, final EntityDamageByEntityEvent event) {
        if (!(target instanceof Player) || !(user instanceof Player)) {
            return;
        }
        final Player playerAttacker = (Player)user;
        final double random = Math.random();
        final double chance = 0.03 * enchantLevel;
        if (random < chance) {
            if (user.hasMetadata("deepWounds") && user.getMetadata("deepWounds").get(0).asLong() > System.currentTimeMillis()) {
                playerAttacker.sendMessage(ChatColor.DARK_RED + "** DEEP WOUNDS " + ChatColor.GRAY + "(NO BLESS)" + ChatColor.DARK_RED + " **");
                return;
            }
            Bleed.clearBleedEffect((LivingEntity)playerAttacker);
            playerAttacker.playSound(playerAttacker.getLocation(), Sound.SPLASH, 1.2f, 2.0f);
            blessPlayer(playerAttacker);
            Bukkit.getPluginManager().callEvent((Event)new CustomCosmicPlayerEvent(playerAttacker, "BLESS", new Object[] { target }));
        }
    }
    
    public static void blessPlayer(final Player player) {
        blessPlayer(player, false);
    }
    
    public static void blessPlayer(final Player player, final boolean respectDeepWounds) {
        final List<PotionEffectType> playerDebuffs = getActiveDebuffs(player);
        if (playerDebuffs.size() > 0) {
            if (respectDeepWounds && player.hasMetadata("deepWounds") && player.getMetadata("deepWounds").get(0).asLong() > System.currentTimeMillis()) {
                player.sendMessage(ChatColor.DARK_RED + "** DEEP WOUNDS " + ChatColor.GRAY + "(NO BLESS)" + ChatColor.DARK_RED + " **");
                return;
            }
            final ArmorSet as = CosmicArmorSetsAPI.getArmorSet(player);
            if (as != null && as.equals((Object)ArmorSet.KOTH)) {
                final long nextBlessedTextTick = player.hasMetadata("nextBlessedTextTick") ? player.getMetadata("nextBlessedTextTick").get(0).asLong() : 0L;
                if (MinecraftServer.currentTick >= nextBlessedTextTick) {
                    player.setMetadata("nextBlessedTextTick", (MetadataValue)new FixedMetadataValue((Plugin)Enchantments.getInstance(), (Object)(MinecraftServer.currentTick + 160)));
                    player.sendMessage(ChatColor.YELLOW.toString() + ChatColor.BOLD + "** BLESSED **");
                }
            }
            else {
                player.sendMessage(ChatColor.YELLOW.toString() + ChatColor.BOLD + "** BLESSED **");
            }
            PlayerPotionEffects.untrackPotionEffect(player, playerDebuffs.get(0));
        }
    }
    
    public static List<PotionEffectType> getActiveDebuffs(final Player p) {
        final List<PotionEffectType> result = new ArrayList<PotionEffectType>();
        for (final PotionEffect pe : p.getActivePotionEffects()) {
            if (Blessed.debuffs.contains(pe.getType())) {
                result.add(pe.getType());
            }
        }
        return result;
    }
}
