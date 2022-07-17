package org.arkhamnetwork.enchantments.enchantments.swords;

import com.cosmicpvp.cosmicutils.utils.GeneralUtil;
import com.cosmicpvp.cosmicutils.utils.WorldUtil;
import com.google.common.collect.Maps;
import com.rit.sucy.CustomEnchantment;
import me.astropvp.astropvpskins.AstroItemSkinsAPI;
import me.astropvp.astropvpskins.enums.SkinType;
import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.objects.RageObject;
import org.arkhamnetwork.enchantments.util.config.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

import java.util.Map;
import java.util.UUID;

public class Rage extends CustomEnchantment {
    private static final Map<UUID, RageObject> comboMap = Maps.newHashMap();

    public Rage() {
        super("Rage", GeneralUtil.weapons, 2);
        this.max = 6;
        this.base = 10.0;
        this.interval = 8.0;
        Bukkit.getPluginManager().registerEvents(new RageListener(), (Plugin) Enchantments.getInstance());
    }

    public void applyEffect(LivingEntity user, LivingEntity target, int enchantLevel, EntityDamageByEntityEvent event) {
        if (!(target instanceof Player)) return;
        Player attacker = (Player) user;


        procCombo((Player) target, attacker, enchantLevel, event);


    }

    public static RageObject getCombo(Player p) {
        return comboMap.get(p.getUniqueId());
    }

    public static RageObject getOrDefaultCombo(Player attacker, Player victim) {
        if (!comboMap.containsKey(attacker.getUniqueId())) {
            comboMap.put(attacker.getUniqueId(), new RageObject(0, System.currentTimeMillis(), victim));
        }
        return comboMap.get(attacker.getUniqueId());
    }

    public static void addCombo(Player player) {
        getCombo(player).setCount(getCombo(player).getCount() + 1);
    }

    public static void resetCombo(Player player) {
        RageObject rageObject = getCombo(player);
        rageObject.setCount(0);
        comboMap.put(player.getUniqueId(), rageObject);
    }

    public static void setLastHitTime(Player player) {
        getCombo(player).setLastHitTime(System.currentTimeMillis());
    }

    public static void procCombo(Player victim, Player attacker, int level, EntityDamageByEntityEvent e) {
        RageObject combo = getOrDefaultCombo(attacker, victim);
        double comboCount = combo.getCount();

        if (combo.getLastHitTime() + 20000 < System.currentTimeMillis()) {
            setLastHitTime(attacker);
            resetCombo(attacker);
            return;
        }

        comboCount *= (1+Enchantments.getInstance().getConfigManager().getConfig(ConfigManager.ConfigFile.SETTINGS).getDouble("rageMultiplier"));

        if (AstroItemSkinsAPI.hasSkinEquipped((Player) victim, SkinType.VALENTINES_2021_AMULET)) {
            comboCount *= 0.75;
        }

        if (comboCount > 10) {
            comboCount = 10;
        }

        e.setDamage(e.getDamage() * (1 + Math.min(comboCount, 10.0) / 10));
        WorldUtil.sendWorldEffect(victim.getLocation().getBlock(), Material.REDSTONE_BLOCK.getId(), 2001);


        setLastHitTime(attacker);
        addCombo(attacker);
    }
}
