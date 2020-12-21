/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.gmail.nossr50.datatypes.skills.SkillType
 */
package com.cosmicpvp.cosmicutils.itembuilder;

import com.cosmicpvp.cosmicutils.itembuilder.ItemBuilder;
import com.cosmicpvp.cosmicutils.utils.CustomItemUtils;
import com.gmail.nossr50.datatypes.skills.SkillType;
import java.util.concurrent.ThreadLocalRandom;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class McMMOSkillVoucherBuilder
extends ItemBuilder {
    private SkillType type;
    private int minLevel = -1;
    private int maxLevel = -1;
    private int levels;
    private boolean random;

    public McMMOSkillVoucherBuilder(String skillType, int minLevel, int maxLevel) {
        super(Material.PAPER);
        this.minLevel = minLevel;
        this.maxLevel = maxLevel;
        this.type = SkillType.getSkill((String)skillType);
    }

    public McMMOSkillVoucherBuilder(String type, int levels) {
        this(SkillType.getSkill((String)type), levels);
    }

    public McMMOSkillVoucherBuilder(boolean random, int min, int max) {
        super(Material.PAPER);
        this.minLevel = min;
        this.maxLevel = max;
        this.random = random;
    }

    public McMMOSkillVoucherBuilder(SkillType type, int minLevel, int maxLevel) {
        super(Material.PAPER);
        this.type = type;
        this.minLevel = minLevel;
        this.maxLevel = maxLevel;
    }

    public McMMOSkillVoucherBuilder(SkillType type, int levels) {
        super(Material.PAPER);
        this.type = type;
        this.levels = levels;
    }

    @Override
    public ItemStack build() {
        int level = this.levels;
        if (this.minLevel != -1 && this.maxLevel != -1) {
            level = this.minLevel + ThreadLocalRandom.current().nextInt(this.maxLevel - this.minLevel + 1);
        }
        SkillType found = this.type;
        if (this.random) {
            found = SkillType.values()[ThreadLocalRandom.current().nextInt(SkillType.values().length)];
        }
        return CustomItemUtils.getSkillVoucher(found, level);
    }
}

