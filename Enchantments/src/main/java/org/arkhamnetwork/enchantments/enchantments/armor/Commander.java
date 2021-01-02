package org.arkhamnetwork.enchantments.enchantments.armor;

import com.rit.sucy.CustomEnchantment;
import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.util.concurrent.ConcurrentHashMap;

public class Commander extends CustomEnchantment {
    protected static ConcurrentHashMap<String, Integer> commanders;
    private CommanderTask commanderTask;

    public Commander() {
        super("Commander", GeneralUtil.armor, 2);
        this.max = 5;
        this.base = 20.0;
        this.interval = 5.0;
        Commander.commanders = new ConcurrentHashMap<String, Integer>();
        Bukkit.getPluginManager().registerEvents((Listener) new CommanderListener(), (Plugin) Enchantments.getInstance());
        (this.commanderTask = new CommanderTask()).runTaskTimer((Plugin) Enchantments.getInstance(), 20L, 160L);
    }

    public void applyEquipEffect(final Player player, final int enchantLevel) {
        Commander.commanders.put(player.getName(), enchantLevel);
    }

    public void applyUnequipEffect(final Player player, final int enchantLevel) {
        Commander.commanders.remove(player.getName());
    }
}
