package org.arkhamnetwork.enchantments.enchantments.armor.boots;

import com.rit.sucy.CustomEnchantment;
import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public class Nimble extends CustomEnchantment {
    public Nimble() {
        super("Nimble", GeneralUtil.boots, 2);
        this.max = 5;
        this.base = 15.0;
        this.interval = 6.0;
        Bukkit.getPluginManager().registerEvents((Listener) new NimbleListener(), (Plugin) Enchantments.getInstance());
    }
}
