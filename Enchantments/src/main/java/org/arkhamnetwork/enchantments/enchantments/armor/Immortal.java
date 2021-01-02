package org.arkhamnetwork.enchantments.enchantments.armor;

import com.earth2me.essentials.Essentials;
import com.rit.sucy.CustomEnchantment;
import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public class Immortal extends CustomEnchantment {
    protected static boolean cosmicOutposts;
    protected static boolean factionUpgrades;
    Essentials ess;

    public Immortal() {
        super("Immortal", GeneralUtil.armor, 2);
        this.max = 4;
        this.base = 15.0;
        this.interval = 3.0;
        Immortal.cosmicOutposts = Bukkit.getPluginManager().isPluginEnabled("CosmicOutposts");
        Immortal.factionUpgrades = Bukkit.getPluginManager().isPluginEnabled("FactionUpgrades");
        this.ess = (Essentials) Bukkit.getPluginManager().getPlugin("Essentials");
        Bukkit.getPluginManager().registerEvents((Listener) new ImmortalListener(), (Plugin) Enchantments.getInstance());
    }
}
