/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.gmail.nossr50.datatypes.skills.SkillType
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import com.gmail.nossr50.datatypes.skills.SkillType;
import org.arkhamnetwork.Arkkit.patches.SkillGemsListener;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.arkhamnetwork.Arkkit.utils.ItemUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class SkillGems
extends ArkPlugin {
    public SkillGems(Plugin p) {
        super(p);
    }

    @Override
    public void enable() {
        this.registerListener(new SkillGemsListener());
    }

    @Override
    public void disable() {
    }

    public static ItemStack buildSkillGem(SkillType type, int credits) {
        return ItemUtils.buildItem(Material.GLOWSTONE_DUST, (short)0, ChatColor.GREEN.toString() + ChatColor.BOLD.toString() + "Skill Gem " + (Object)ChatColor.GRAY + "(Right Click)", ChatColor.GREEN.toString() + "Skill " + (Object)ChatColor.GRAY + type.getName(), ChatColor.GREEN.toString() + "+" + credits + " Level(s)", (Object)ChatColor.GRAY + "Increases mcMMO skill level.");
    }
}

