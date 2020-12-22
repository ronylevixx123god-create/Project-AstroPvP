/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.CosmicSpaceChests.CosmicSpaceChestsAPI
 *  com.cosmicpvp.CosmicSpaceChests.libs.ItemRarity
 */
package me.ifamasssxd.cosmicbosses;

import me.ifamasssxd.cosmicbosses.boss.type.Boss;
import me.ifamasssxd.cosmicbosses.listeners.BossEggListener;
import com.cosmicpvp.CosmicBossEggs.listeners.BossListener;
import me.ifamasssxd.cosmicbosses.utils.WorldGuardUtil;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.plugin.java.JavaPlugin;

public class CosmicBossEggsPlugin extends JavaPlugin {
    public static Logger log = new Logger();
    public static CosmicBossEggsPlugin cbe;

    @Override
    public void onEnable() {
        cbe = this;
        WorldGuardUtil.hook();
        this.registerListeners();
    }

    @Override
    public void onDisable() {
        for (Entity ent : Boss.boss_map.keySet()) {
            LivingEntity le = (LivingEntity)ent;
            le.damage(2.147483647E9);
            le.remove();
        }
    }

    private void registerListeners() {
        this.getServer().getPluginManager().registerEvents(new BossListener(), this);
        this.getServer().getPluginManager().registerEvents(new BossEggListener(), this);
    }

    public static CosmicBossEggsPlugin getPlugin() {
        return cbe;
    }

}

