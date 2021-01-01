package org.arkhamnetwork.enchantments.enchantments.armor.boots;

import com.rit.sucy.*;
import org.arkhamnetwork.enchantments.util.*;
import org.bukkit.*;
import org.arkhamnetwork.enchantments.*;
import org.bukkit.event.*;
import org.bukkit.plugin.*;

public class Nimble extends CustomEnchantment
{
    public Nimble() {
        super("Nimble", GeneralUtil.boots, 2);
        this.max = 5;
        this.base = 15.0;
        this.interval = 6.0;
        Bukkit.getPluginManager().registerEvents((Listener)new NimbleListener(), (Plugin)Enchantments.getInstance());
    }
}
