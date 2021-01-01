package org.arkhamnetwork.enchantments.enchantments.armor;

import com.rit.sucy.*;
import java.util.concurrent.*;
import org.arkhamnetwork.enchantments.util.*;
import org.bukkit.*;
import org.arkhamnetwork.enchantments.*;
import org.bukkit.event.*;
import org.bukkit.plugin.*;
import org.bukkit.entity.*;

public class CreeperArmor extends CustomEnchantment
{
    protected static ConcurrentHashMap<String, Integer> creeperArmor;
    
    public CreeperArmor() {
        super("Creeper Armor", GeneralUtil.armor, 3);
        this.max = 3;
        this.base = 25.0;
        this.interval = 0.0;
        CreeperArmor.creeperArmor = new ConcurrentHashMap<String, Integer>();
        Bukkit.getPluginManager().registerEvents((Listener)new CreeperArmorListener(), (Plugin)Enchantments.getInstance());
    }
    
    public void applyEquipEffect(final Player player, final int enchantLevel) {
        CreeperArmor.creeperArmor.put(player.getName(), enchantLevel);
    }
    
    public void applyUnequipEffect(final Player player, final int enchantLevel) {
        CreeperArmor.creeperArmor.remove(player.getName());
    }
}
