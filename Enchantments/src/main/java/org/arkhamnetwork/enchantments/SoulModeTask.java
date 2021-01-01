package org.arkhamnetwork.enchantments;

import com.cosmicpvp.cosmicutils.utils.ParticleEffect;
import org.arkhamnetwork.enchantments.cmds.*;
import org.bukkit.entity.*;
import me.ifamasssxd.killtracker.*;
import org.bukkit.*;
import com.compilex.CosmicOutposts.*;
import com.cosmicpvp.cosmicutils.utils.*;
import org.bukkit.plugin.*;
import org.bukkit.metadata.*;
import org.arkhamnetwork.enchantments.util.*;
import java.util.*;
import com.rit.sucy.*;
import org.bukkit.inventory.*;

public class SoulModeTask implements Runnable {
    boolean cosmicOutposts;

    public SoulModeTask() {
        this.cosmicOutposts = false;
        this.cosmicOutposts = Bukkit.getPluginManager().isPluginEnabled("CosmicOutposts");
    }

    public void run() {
        if (!CommandEnchDust.soulEnabled)
            return;
        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
            if (KillTrackerAPI.hasGodMode(p)) {
                boolean KOTH = p.getWorld().getName().equals("world_koth");
                if (!KillTrackerAPI.hasSoulAmount(p, KOTH ? 8 : 4)) {
                    KillTrackerAPI.toggleGodMode(p);
                    p.sendMessage("");
                    p.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "** SOUL MODE: " + ChatColor.UNDERLINE + "OFF" + ChatColor.RED.toString() + ChatColor.BOLD.toString() + " **");
                    p.sendMessage(ChatColor.GRAY + "You have no soul gems left!");
                    p.sendMessage("");
                    p.playSound(p.getLocation(), Sound.FIRE_IGNITE, 1.0F, 1.2F);
                } else {
                    int cost = KOTH ? (getSoulCostPerSecond(p) * 2) : getSoulCostPerSecond(p);
                    if (this.cosmicOutposts && CosmicOutpostsAPI.controlsCosmonautOutpost(p))
                        cost = Math.max(0, cost / 2);
                    int heroic = UpgradeUtils.getUpgradeLevel(p, "HEROIC_SOUL_MASTERY");
                    if (heroic > 0 && cost > 0) {
                        cost *= (int)((heroic == 1) ? 0.7D : ((heroic == 2) ? 0.55D : 1.0D));
                        cost = (int)Math.ceil(cost);
                    }
                    if (cost > 0) {
                        KillTrackerAPI.removeSoulsFromGems(p, cost);
                        p.setMetadata("lastSoulRemoveEvent", (MetadataValue)new FixedMetadataValue((Plugin)Enchantments.getInstance(), Long.valueOf(System.currentTimeMillis())));
                        if (!p.hasMetadata("spectator"))
                            ParticleEffect.playEffect(ParticleEffect.SPELL, p.getLocation().add(0.0D, 1.0D, 0.0D), 0.5F, 65);
                        p.playSound(p.getLocation(), Sound.EAT, 0.4F, 0.2F);
                        if (KillTrackerAPI.getAllSouls(p) % 100 == 0)
                            p.sendMessage(ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + "** SOULS: " + ChatColor.UNDERLINE + KillTrackerAPI.getAllSouls(p) + ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + " **");
                    } else if (!p.hasMetadata("spectator")) {
                        ParticleEffect.playEffect(ParticleEffect.ENCHANTMENT_TABLE, p.getLocation().add(0.0D, 1.0D, 0.0D), 1.5F, 80);
                    }
                }
            }
        }
    }

    private int getSoulCostPerSecond(Player p) {
        int x = 0;
        ItemStack hand = p.getItemInHand();
        if (hand != null) {
            if (EnchantmentAPI.itemHasEnchantment(hand, "Divine Immolation"))
                x += 5;
            if (EnchantmentAPI.itemHasEnchantment(hand, "Soul Trap"))
                x += 2;
            if (EnchantmentAPI.itemHasEnchantment(hand, "Hero Killer"))
                x++;
            if (EnchantmentAPI.itemHasEnchantment(hand, "Sabotage"))
                x += 2;
        }
        return x;
    }
}
