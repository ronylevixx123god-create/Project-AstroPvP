package org.arkhamnetwork.enchantments.enchantments.swords;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.WorldUtil;
import com.rit.sucy.CustomEnchantment;
import me.astropvp.astropvpskins.AstroItemSkinsAPI;
import me.astropvp.astropvpskins.enums.SkinType;
import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.events.CustomEnchantProcOnEntityEvent;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

public class Trap extends CustomEnchantment {
    public Trap() {
        super("Trap", GeneralUtil.swords, 2);
        this.max = 3;
        this.base = 15.0;
        this.interval = 3.0;
        Bukkit.getPluginManager().registerEvents((Listener) new TrapEvents(), (Plugin) Enchantments.getInstance());
    }

    public void applyEffect(final LivingEntity player, final LivingEntity target, final int level, final EntityDamageByEntityEvent e) {
        if (!(target instanceof Player)) {
            return;
        }
        if (((Player) player).getWalkSpeed() < 0.2f) {
            return;
        }
        final double random = Math.random();
        double chance = 0.04 * level;
        if (random < chance) {
            final Player ptarget = (Player) target;
            if (target.hasMetadata("metaphysicalEnchant")) {
                final int metaphysicalLevel = target.getMetadata("metaphysicalEnchant").get(0).asInt();
                chance -= metaphysicalLevel * 0.025;
                if (random > chance) {
                    ptarget.sendMessage(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "** METAPHYSICAL (" + ChatColor.DARK_GRAY + "Trap blocked!" + ChatColor.BOLD + ") **");
                    return;
                }
            }


            if (!new CustomEnchantProcOnEntityEvent(player, target, (Event) e, this, level).callEvent()) {
                return;
            }
            if (target.hasMetadata("immune_freeze")) {
                ptarget.sendMessage(CC.DarkGrayB + "* DRAGON SLAYER [" + ChatColor.GRAY + "Trap blocked!" + CC.DarkGrayB + "] *");
                return;
            }

            if (AstroItemSkinsAPI.hasSkinEquipped(ptarget, SkinType.THANKSGIVING_HELMET_WITCH)) {
                ptarget.sendMessage(CC.translate("&e&l* WITCH HAT &7Blocked &eTrap &e&l*"));
                return;
            }

            ptarget.setWalkSpeed(0.0f);
            ptarget.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "(!) " + ChatColor.RED + "You have been trapped by " + ((Player) player).getName() + "!");
            Enchantments.getInstance().getServer().getScheduler().runTaskLater((Plugin) Enchantments.getInstance(), (Runnable) new Runnable() {
                @Override
                public void run() {
                    ptarget.setWalkSpeed(0.2f);
                    ptarget.sendMessage(ChatColor.GREEN.toString() + ChatColor.BOLD.toString() + "(!) " + ChatColor.GREEN + "You are no longer trapped!");
                }
            }, 35L);
            final Location l = ptarget.getLocation();
            WorldUtil.sendWorldEffect(l.getBlock(), 80, 2001);
        }
    }

    private static class TrapEvents implements Listener {
        @EventHandler
        public void onPlayerQuit(final PlayerQuitEvent e) {
            final Player pl = e.getPlayer();
            pl.setWalkSpeed(0.2f);
        }

        @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
        public void onEntityDamage(final EntityDamageEvent e) {
            if (e.getEntity() instanceof Player) {
                final Player p = (Player) e.getEntity();
                if (p.getWalkSpeed() == 0.0f && !p.isOp()) {
                    final Location l = p.getLocation();
                    WorldUtil.sendWorldEffect(l.getBlock(), 80, 2001);
                }
            }
        }
    }
}
