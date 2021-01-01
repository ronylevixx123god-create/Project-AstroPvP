package org.arkhamnetwork.enchantments.enchantments.tools;

import com.rit.sucy.*;
import org.arkhamnetwork.enchantments.util.*;
import org.bukkit.*;
import org.arkhamnetwork.enchantments.*;
import org.bukkit.event.*;
import org.bukkit.plugin.*;

public class Skilling extends CustomEnchantment
{
    public Skilling() {
        super("Skilling", GeneralUtil.tools, 2);
        this.max = 10;
        this.base = 15.0;
        this.interval = 6.0;
        Bukkit.getPluginManager().registerEvents((Listener)new SkillingListener(), (Plugin)Enchantments.getInstance());
    }
}
