/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  me.ifamasssxd.cosmicbosses.struct.enums.BossType
 */
package com.cosmicpvp.cosmicutils.itembuilder;

import com.cosmicpvp.cosmicutils.itembuilder.ItemBuilder;
import com.cosmicpvp.cosmicutils.utils.CustomItemUtils;
import java.util.concurrent.ThreadLocalRandom;
import me.ifamasssxd.cosmicbosses.struct.enums.BossType;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class BossEggBuilder
extends ItemBuilder {
    private String bossName;
    int min;
    int max;

    public BossEggBuilder(String boss) {
        super(Material.MONSTER_EGG);
        this.bossName = boss;
    }

    public BossEggBuilder(String boss, int min, int max) {
        this(boss);
        this.min = min;
        this.max = max;
    }

    @Override
    public ItemStack build() {
        ItemStack retr = CustomItemUtils.getNewBossEgg(BossType.valueOf((String)this.bossName));
        if (this.min != 0 && this.max != 0) {
            retr.setAmount(ThreadLocalRandom.current().nextInt(this.max - this.min + 1) + this.min);
        }
        return retr;
    }
}

