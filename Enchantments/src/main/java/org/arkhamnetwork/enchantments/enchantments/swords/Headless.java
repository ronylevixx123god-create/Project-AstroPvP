package org.arkhamnetwork.enchantments.enchantments.swords;

import com.rit.sucy.*;
import org.arkhamnetwork.enchantments.util.*;
import org.arkhamnetwork.enchantments.*;
import org.bukkit.plugin.*;
import org.bukkit.entity.*;
import org.bukkit.event.entity.*;
import org.bukkit.command.*;
import org.bukkit.metadata.*;
import org.bukkit.event.*;
import org.bukkit.inventory.*;
import org.bukkit.*;
import java.text.*;
import org.bukkit.inventory.meta.*;
import net.minecraft.util.org.apache.commons.lang3.text.*;
import java.util.*;

public class Headless extends CustomEnchantment
{
    public Headless() {
        super("Headless", GeneralUtil.swords, 7);
        this.max = 3;
        this.base = 30.0;
        this.interval = 10.0;
        Bukkit.getPluginManager().registerEvents((Listener)new HeadlessEvents(), (Plugin)Enchantments.getInstance());
    }
    
    public void applyEffect(final LivingEntity user, final LivingEntity target, final int enchantLevel, final EntityDamageByEntityEvent event) {
        if (!(target instanceof Player)) {
            return;
        }
        final Player ptarget = (Player)target;
        final double random = Math.random();
        final double chance = 0.03 * enchantLevel;
        if (random < chance) {
            HeadlessEvents.headlessEnchant.add(ptarget.getName());
        }
    }
    
    private static class HeadlessEvents implements Listener
    {
        private static HashSet<String> headlessEnchant;
        private static Calendar calendar;
        
        @EventHandler(priority = EventPriority.LOW)
        public void onPlayerDeath(final PlayerDeathEvent e) {
            final Player p_dead = e.getEntity();
            if (HeadlessEvents.headlessEnchant.contains(p_dead.getName())) {
                Bukkit.getLogger().info("HEADLESS ENCH -- Dropping skull of " + p_dead.getName() + "!");
                if (p_dead.getKiller() != null && p_dead.getKiller() instanceof Player) {
                    e.getDrops().add(this.getPlayerHead(p_dead.getName(), p_dead.getKiller()));
                    if (Bukkit.getPluginManager().isPluginEnabled("CosmicContests") && !p_dead.getKiller().hasMetadata("cosmicContests_gotHead_" + p_dead.getName())) {
                        Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "contests add " + p_dead.getKiller().getName() + " headsCollected 1");
                        p_dead.getKiller().setMetadata("cosmicContests_gotHead_" + p_dead.getName(), (MetadataValue)new FixedMetadataValue((Plugin)Enchantments.getInstance(), (Object)true));
                    }
                }
                else {
                    e.getDrops().add(GeneralUtil.getPlayerHead(p_dead.getName()));
                }
                HeadlessEvents.headlessEnchant.remove(p_dead.getName());
            }
        }
        
        private ItemStack getPlayerHead(final String p_name, final Player pKiller) {
            final ItemStack is = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
            final SkullMeta sm = (SkullMeta)is.getItemMeta();
            sm.setOwner(p_name);
            sm.setDisplayName(ChatColor.WHITE + "Skull of " + p_name);
            final SimpleDateFormat month_date = new SimpleDateFormat("MMMM");
            final String monthName = month_date.format(HeadlessEvents.calendar.getTime());
            sm.setLore((List)new ArrayList(Arrays.asList(ChatColor.GRAY + "Defeated by " + ChatColor.WHITE + pKiller.getName() + ChatColor.GRAY + " on ", ChatColor.WHITE + monthName + " " + HeadlessEvents.calendar.get(5) + ", " + HeadlessEvents.calendar.get(1) + ChatColor.GRAY + " at ", ChatColor.WHITE + "" + pKiller.getLocation().getBlockX() + ", " + pKiller.getLocation().getBlockY() + ", " + pKiller.getLocation().getBlockZ() + ChatColor.GRAY + " with a(n) ", ChatColor.WHITE + this.getItemName(pKiller.getItemInHand()) + "!")));
            is.setItemMeta((ItemMeta)sm);
            return is;
        }
        
        private String getItemName(final ItemStack i) {
            if (i == null || i.getType() == Material.AIR) {
                return "Fists";
            }
            if (i.hasItemMeta() && i.getItemMeta().hasDisplayName()) {
                return i.getItemMeta().getDisplayName();
            }
            return WordUtils.capitalize(i.getType().name());
        }
        
        static {
            HeadlessEvents.headlessEnchant = new HashSet<String>();
            HeadlessEvents.calendar = Calendar.getInstance(TimeZone.getDefault());
        }
    }
}
