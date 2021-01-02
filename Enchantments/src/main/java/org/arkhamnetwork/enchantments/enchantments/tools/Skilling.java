package org.arkhamnetwork.enchantments.enchantments.tools;

import com.rit.sucy.CustomEnchantment;
import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public class Skilling extends CustomEnchantment {
    public Skilling() {
        super("Skilling", GeneralUtil.tools, 2);
        this.max = 10;
        this.base = 15.0;
        this.interval = 6.0;
        Bukkit.getPluginManager().registerEvents((Listener) new SkillingListener(), (Plugin) Enchantments.getInstance());
    }
}
