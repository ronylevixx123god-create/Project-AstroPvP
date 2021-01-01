package org.arkhamnetwork.enchantments.enchantments.swords;

import com.cosmicpvp.cosmicutils.compat.ASound;
import com.rit.sucy.*;
import me.astropvp.astropvpskins.AstroItemSkinsAPI;
import me.astropvp.astropvpskins.enums.SkinType;
import org.arkhamnetwork.enchantments.util.*;
import org.arkhamnetwork.enchantments.*;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.plugin.*;
import org.bukkit.entity.*;
import org.bukkit.metadata.*;
import org.arkhamnetwork.enchantments.events.*;
import com.cosmicpvp.cosmicutils.utils.*;
import org.bukkit.*;
import org.bukkit.event.player.*;
import org.bukkit.event.entity.*;
import org.bukkit.event.*;

public class Trap extends CustomEnchantment
{
    public Trap() {
        super("Trap", GeneralUtil.swords, 2);
        this.max = 3;
        this.base = 15.0;
        this.interval = 3.0;
        Bukkit.getPluginManager().registerEvents((Listener)new TrapEvents(), (Plugin)Enchantments.getInstance());
    }
    
    public void applyEffect(final LivingEntity player, final LivingEntity target, final int level, final EntityDamageByEntityEvent e) {
        if (!(target instanceof Player)) {
            return;
        }
        if (((Player)player).getWalkSpeed() < 0.2f) {
            return;
        }
        final double random = Math.random();
        double chance = 0.04 * level;
        if (random < chance) {
            final Player ptarget = (Player)target;
            if (target.hasMetadata("metaphysicalEnchant")) {
                final int metaphysicalLevel = target.getMetadata("metaphysicalEnchant").get(0).asInt();
                chance -= metaphysicalLevel * 0.025;
                if (random > chance) {
                    ptarget.sendMessage(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "** METAPHYSICAL (" + ChatColor.DARK_GRAY + "Trap blocked!" + ChatColor.BOLD + ") **");
                    return;
                }
            }



            if (!new CustomEnchantProcOnEntityEvent(player, target, (Event)e, this, level).callEvent()) {
                return;
            }
            if (target.hasMetadata("immune_freeze")) {
                ptarget.sendMessage(CC.DarkGrayB + "* DRAGON SLAYER [" + ChatColor.GRAY + "Trap blocked!" + CC.DarkGrayB + "] *");
                return;
            }
            ptarget.setWalkSpeed(0.0f);
            ptarget.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "(!) " + ChatColor.RED + "You have been trapped by " + ((Player)player).getName() + "!");
            Enchantments.getInstance().getServer().getScheduler().runTaskLater((Plugin)Enchantments.getInstance(), (Runnable)new Runnable() {
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
    
    private static class TrapEvents implements Listener
    {
        @EventHandler
        public void onPlayerQuit(final PlayerQuitEvent e) {
            final Player pl = e.getPlayer();
            pl.setWalkSpeed(0.2f);
        }
        
        @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
        public void onEntityDamage(final EntityDamageEvent e) {
            if (e.getEntity() instanceof Player) {
                final Player p = (Player)e.getEntity();
                if (p.getWalkSpeed() == 0.0f && !p.isOp()) {
                    final Location l = p.getLocation();
                    WorldUtil.sendWorldEffect(l.getBlock(), 80, 2001);
                }
            }
        }
    }
}
