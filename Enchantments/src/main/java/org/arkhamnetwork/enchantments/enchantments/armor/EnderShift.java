package org.arkhamnetwork.enchantments.enchantments.armor;

import com.cosmicpvp.cosmicutils.utils.ParticleEffect;
import com.rit.sucy.*;
import org.arkhamnetwork.enchantments.*;
import org.bukkit.plugin.*;
import org.bukkit.entity.*;
import org.arkhamnetwork.enchantments.util.*;
import org.bukkit.potion.*;
import org.arkhamnetwork.Arkkit.libs.*;
import org.bukkit.*;
import java.util.*;
import org.bukkit.event.*;
import org.bukkit.event.entity.*;

public class EnderShift extends CustomEnchantment
{
    protected static HashMap<String, Long> last_ender_shift;
    protected static List<String> ender_shifters;
    
    public EnderShift() {
        super("Ender Shift", GeneralUtil.helmets_and_boots, 3);
        this.max = 3;
        this.base = 25.0;
        this.interval = 5.0;
        Bukkit.getPluginManager().registerEvents((Listener)new EnderShiftEvents(), (Plugin)Enchantments.getInstance());
    }
    
    public void applyDefenseEffect(final LivingEntity player, final LivingEntity attacker, final int level, final EntityDamageEvent e) {
        if (player instanceof Player) {
            double dmg = e.getDamage(EntityDamageEvent.DamageModifier.BASE);
            dmg *= ArmorUtil.getArmorDamageNullificationPercent((float)ArmorUtil.getArmorValue((Player)player));
            if (player.getHealth() - dmg <= 0.0 && this.canEnderShift((Player)player) && Math.random()<0.1) {
                final Player pl = (Player)player;
                EnderShift.last_ender_shift.put(pl.getUniqueId().toString(), System.currentTimeMillis());
                e.setCancelled(true);
                e.setDamage(0.0);
                pl.sendMessage("");
                pl.sendMessage(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD.toString() + "(!) " + ChatColor.LIGHT_PURPLE + "You were about to die, so you have entered the Ender dimension, escape to safety!");
                pl.sendMessage("");
                pl.playSound(pl.getLocation(), Sound.ENDERDRAGON_GROWL, 1.0f, 0.54f);
                player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 20 * (level + 7), 0));
                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * (level + 7), level + 2), true);
                player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 20 * (level + 7), level - 1), true);
                player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 20 * (level + 7), level + 2));
                final String uuid = player.getUniqueId().toString();
                EnderShift.ender_shifters.add(uuid);
                final Location loc = pl.getLocation();
                ParticleEffect.sendToLocation(ParticleEffect.WITCH_MAGIC, loc, (float)Math.random() * 2.0f, (float)Math.random() * 2.0f, (float)Math.random() * 2.0f, 1.25f, 100);
                Bukkit.getScheduler().runTaskLater((Plugin)Enchantments.getInstance(), (Runnable)new Runnable() {
                    @Override
                    public void run() {
                        if (Bukkit.getPlayer(String.valueOf(UUID.fromString(uuid))) != null) {
                            ParticleEffect.sendToLocation(ParticleEffect.WITCH_MAGIC, Bukkit.getPlayer(String.valueOf(UUID.fromString(uuid))).getLocation(), (float)Math.random() * 2.0f, (float)Math.random() * 2.0f, (float)Math.random() * 2.0f, 1.25f, 100);
                        }
                        EnderShift.ender_shifters.remove(uuid);
                    }
                }, (long)(20 * (level + 7) + 10));
            }
        }
    }
    
    private boolean canEnderShift(final Player pl) {
        return !EnderShift.last_ender_shift.containsKey(pl.getUniqueId().toString()) || System.currentTimeMillis() - EnderShift.last_ender_shift.get(pl.getUniqueId().toString()) > 30000L;
    }
    
    private static boolean isEnderShifting(final Player pl) {
        return EnderShift.ender_shifters.contains(pl.getUniqueId().toString());
    }
    
    static {
        EnderShift.last_ender_shift = new HashMap<String, Long>();
        EnderShift.ender_shifters = new ArrayList<String>();
    }
    
    private static class EnderShiftEvents implements Listener
    {
        @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
        public void onPlayerShootBow(final EntityShootBowEvent e) {
            if (e.getEntity() instanceof Player && isEnderShifting((Player)e.getEntity())) {
                e.setCancelled(true);
                ((Player)e.getEntity()).playSound(e.getEntity().getLocation(), Sound.ENDERMAN_TELEPORT, 1.0f, 0.54f);
            }
        }
        
        @EventHandler(priority = EventPriority.LOWEST)
        public void onEntityDamageByEntityEvent(final EntityDamageByEntityEvent e) {
            if (e.getDamager() instanceof Player && isEnderShifting((Player)e.getDamager())) {
                e.setCancelled(true);
                e.setDamage(0.0);
                ((Player)e.getDamager()).playSound(e.getDamager().getLocation(), Sound.ENDERMAN_TELEPORT, 1.0f, 0.54f);
            }
        }
    }
}
