package org.arkhamnetwork.enchantments.enchantments.weapons;

import com.rit.sucy.*;
import org.arkhamnetwork.enchantments.util.*;
import org.bukkit.*;
import org.arkhamnetwork.enchantments.*;
import org.bukkit.event.*;
import org.bukkit.plugin.*;

public class Training extends CustomEnchantment
{
    public Training() {
        super("Training", GeneralUtil.weapons, 2);
        this.max = 10;
        this.base = 15.0;
        this.interval = 6.0;
        Bukkit.getPluginManager().registerEvents((Listener)new TrainingListener(), (Plugin)Enchantments.getInstance());
    }
}
