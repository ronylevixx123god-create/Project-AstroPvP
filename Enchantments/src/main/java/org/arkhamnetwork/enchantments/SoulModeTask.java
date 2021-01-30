package org.arkhamnetwork.enchantments;

import com.compilex.CosmicOutposts.CosmicOutpostsAPI;
import com.cosmicpvp.cosmicutils.utils.UpgradeUtils;
import com.rit.sucy.EnchantmentAPI;
import lombok.SneakyThrows;
import me.ifamasssxd.killtracker.KillTrackerAPI;
import org.arkhamnetwork.enchantments.cmds.CommandEnchDust;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

public class SoulModeTask implements Runnable {
    boolean cosmicOutposts;

    public SoulModeTask() {
        this.cosmicOutposts = false;
        this.cosmicOutposts = Bukkit.getPluginManager().isPluginEnabled("CosmicOutposts");
    }

    @SneakyThrows
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
                        cost *= (int) ((heroic == 1) ? 0.7D : ((heroic == 2) ? 0.55D : 1.0D));
                        cost = (int) Math.ceil(cost);
                    }
                    if (cost > 0) {
                        KillTrackerAPI.removeSoulsFromGems(p, cost);
                        p.setMetadata("lastSoulRemoveEvent", (MetadataValue) new FixedMetadataValue((Plugin) Enchantments.getInstance(), System.currentTimeMillis()));
                        if (!p.hasMetadata("spectator"))
                            //ParticleEffect.playEffect(ParticleEffect.SPELL, p.getLocation().add(0.0D, 1.0D, 0.0D), 0.5F, 65);
                            de.inventivegames.particle.ParticleEffect.ENCHANTMENT_TABLE.sendToPlayer(p.getPlayer(),p.getLocation(), 0,0, 0,1.5f,10);
                        p.playSound(p.getLocation(), Sound.EAT, 0.4F, 0.2F);
                        if (KillTrackerAPI.getAllSouls(p) % 100 == 0)
                            p.sendMessage(ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + "** SOULS: " + ChatColor.UNDERLINE + KillTrackerAPI.getAllSouls(p) + ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + " **");
                    } else if (!p.hasMetadata("spectator")) {
                        //ParticleEffect.playEffect(ParticleEffect.ENCHANTMENT_TABLE, p.getLocation().add(0.0D, 1.0D, 0.0D), 1.5F, 80);
                        de.inventivegames.particle.ParticleEffect.ENCHANTMENT_TABLE.sendToPlayer(p.getPlayer(),p.getLocation(), 0,0, 0,1.5f,10);
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
