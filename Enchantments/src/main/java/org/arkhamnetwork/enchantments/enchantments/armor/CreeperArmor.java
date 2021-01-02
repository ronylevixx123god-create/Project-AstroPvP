package org.arkhamnetwork.enchantments.enchantments.armor;

import com.rit.sucy.CustomEnchantment;
import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.util.concurrent.ConcurrentHashMap;

public class CreeperArmor extends CustomEnchantment {
    protected static ConcurrentHashMap<String, Integer> creeperArmor;

    public CreeperArmor() {
        super("Creeper Armor", GeneralUtil.armor, 3);
        this.max = 3;
        this.base = 25.0;
        this.interval = 0.0;
        CreeperArmor.creeperArmor = new ConcurrentHashMap<String, Integer>();
        Bukkit.getPluginManager().registerEvents((Listener) new CreeperArmorListener(), (Plugin) Enchantments.getInstance());
    }

    public void applyEquipEffect(final Player player, final int enchantLevel) {
        CreeperArmor.creeperArmor.put(player.getName(), enchantLevel);
    }

    public void applyUnequipEffect(final Player player, final int enchantLevel) {
        CreeperArmor.creeperArmor.remove(player.getName());
    }
}
