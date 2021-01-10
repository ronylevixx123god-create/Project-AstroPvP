package org.arkhamnetwork.enchantments.enchantments.heroic;

import com.rit.sucy.CustomEnchantment;
import com.rit.sucy.EnchantmentAPI;
import org.arkhamnetwork.enchantments.Enchantments;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.plugin.Plugin;

import java.util.Random;

public abstract class HeroicCustomEnchantment extends CustomEnchantment {
    protected Random random;

    public HeroicCustomEnchantment(final String name, final String nonHeroicName, final Material[] naturalItems, final int weight) {
        this(name, nonHeroicName, naturalItems, weight, true, false);
    }

    public HeroicCustomEnchantment(final String name, final String nonHeroicName) {
        this(name, nonHeroicName, new Material[0], 0, true, true);
    }

    public HeroicCustomEnchantment(final String name, final String nonHeroicName, final boolean copyall) {
        this(name, nonHeroicName, new Material[0], 0, true, copyall);
    }

    public HeroicCustomEnchantment(final String name, final String nonHeroicName, final Material[] naturalItems, final int weight, final boolean useDefaultBase, final boolean copyAllDefaultStats) {
        super(name, naturalItems, weight);
        this.random = new Random();
        Bukkit.getScheduler().runTaskLater(Enchantments.getInstance(), () -> {
            this.heroicEnchantment = true;
            this.nonHeroicVersion = EnchantmentAPI.getEnchantment(nonHeroicName);
            if (copyAllDefaultStats || useDefaultBase) {
                this.base = this.nonHeroicVersion.getBase();
                this.interval = this.nonHeroicVersion.getInterval();
                if (copyAllDefaultStats) {
                    this.max = this.nonHeroicVersion.getMaxLevel();
                    this.naturalItems = this.nonHeroicVersion.getNaturalMaterials();
                    this.setWeight(this.nonHeroicVersion.getWeight());
                }
            }
            this.nonHeroicVersion.heroicVersion = this;
        }, 2L);
    }

    public void debug(final String msg) {
        Enchantments.getInstance().getLogger().info("[" + this.name() + "] " + msg);
    }
}
