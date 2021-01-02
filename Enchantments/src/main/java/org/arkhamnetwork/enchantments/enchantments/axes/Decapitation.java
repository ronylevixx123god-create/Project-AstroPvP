package org.arkhamnetwork.enchantments.enchantments.axes;

import com.rit.sucy.CustomEnchantment;
import org.apache.commons.lang.WordUtils;
import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

import java.text.SimpleDateFormat;
import java.util.*;

public class Decapitation extends CustomEnchantment {
    public Decapitation() {
        super("Decapitation", GeneralUtil.axe, 7);
        this.max = 3;
        this.base = 30.0;
        this.interval = 10.0;
        Bukkit.getPluginManager().registerEvents((Listener) new DecapitationEvents(), (Plugin) Enchantments.getInstance());
    }

    public void applyEffect(final LivingEntity user, final LivingEntity target, final int enchantLevel, final EntityDamageByEntityEvent event) {
        if (!(target instanceof Player)) {
            return;
        }
        final Player ptarget = (Player) target;
        final double random = Math.random();
        final double chance = 0.03 * enchantLevel;
        if (random < chance) {
            DecapitationEvents.decapitationEnchant.add(ptarget.getName());
        }
    }

    private static class DecapitationEvents implements Listener {
        private static HashSet<String> decapitationEnchant;
        private static Calendar calendar;

        static {
            DecapitationEvents.decapitationEnchant = new HashSet<String>();
            DecapitationEvents.calendar = Calendar.getInstance(TimeZone.getDefault());
        }

        @EventHandler(priority = EventPriority.LOW)
        public void onPlayerDeath(final PlayerDeathEvent e) {
            final Player p_dead = e.getEntity();
            if (DecapitationEvents.decapitationEnchant.contains(p_dead.getName())) {
                Bukkit.getLogger().info("HEADLESS ENCH -- Dropping skull of " + p_dead.getName() + "!");
                if (p_dead.getKiller() != null && p_dead.getKiller() != null) {
                    e.getDrops().add(this.getPlayerHead(p_dead.getName(), p_dead.getKiller()));
                    if (Bukkit.getPluginManager().isPluginEnabled("CosmicContests") && !p_dead.getKiller().hasMetadata("cosmicContests_gotHead_" + p_dead.getName())) {
                        Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), "contests add " + p_dead.getKiller().getName() + " headsCollected 1");
                        p_dead.getKiller().setMetadata("cosmicContests_gotHead_" + p_dead.getName(), (MetadataValue) new FixedMetadataValue((Plugin) Enchantments.getInstance(), (Object) true));
                    }
                } else {
                    e.getDrops().add(GeneralUtil.getPlayerHead(p_dead.getName()));
                }
                DecapitationEvents.decapitationEnchant.remove(p_dead.getName());
            }
        }

        private ItemStack getPlayerHead(final String p_name, final Player pKiller) {
            final ItemStack is = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
            final SkullMeta sm = (SkullMeta) is.getItemMeta();
            sm.setOwner(p_name);
            sm.setDisplayName(ChatColor.WHITE + "Skull of " + p_name);
            final SimpleDateFormat month_date = new SimpleDateFormat("MMMM");
            final String monthName = month_date.format(DecapitationEvents.calendar.getTime());
            sm.setLore(new ArrayList(Arrays.asList(ChatColor.GRAY + "Defeated by " + ChatColor.WHITE + pKiller.getName() + ChatColor.GRAY + " on ", ChatColor.WHITE + monthName + " " + DecapitationEvents.calendar.get(5) + ", " + DecapitationEvents.calendar.get(1) + ChatColor.GRAY + " at ", ChatColor.WHITE + "" + pKiller.getLocation().getBlockX() + ", " + pKiller.getLocation().getBlockY() + ", " + pKiller.getLocation().getBlockZ() + ChatColor.GRAY + " with a(n) ", ChatColor.WHITE + this.getItemName(pKiller.getItemInHand()) + "!")));
            is.setItemMeta((ItemMeta) sm);
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
    }
}
