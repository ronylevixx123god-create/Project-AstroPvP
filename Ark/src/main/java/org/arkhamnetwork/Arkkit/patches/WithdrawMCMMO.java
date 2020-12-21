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
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import org.arkhamnetwork.Arkkit.patches.MCMMOXPCommand;
import org.arkhamnetwork.Arkkit.patches.WithdrawMCMMOCommand;
import org.arkhamnetwork.Arkkit.patches.WithdrawMCMMOListener;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.arkhamnetwork.Arkkit.utils.ItemUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class WithdrawMCMMO
extends ArkPlugin {
    private static DecimalFormat format = new DecimalFormat("#,###");

    public WithdrawMCMMO(Plugin p) {
        super(p);
    }

    @Override
    public void enable() {
        this.registerListener(new WithdrawMCMMOListener());
        this.registerCommand(new WithdrawMCMMOCommand("withdrawmcmmo", "/<command>", "Converts your vanilla levels into a tradeable XP bottle.", new ArrayList<String>(Arrays.asList("withdrawskill", "mcmmocredit", "withdrawmcmmoxp"))));
        this.registerCommand(new MCMMOXPCommand("mcmmoxp", "/<command>", "Displays total exp values of specified mcMMO skill.", new ArrayList<String>(Arrays.asList("mxp", "mmoxp"))));
    }

    @Override
    public void disable() {
    }

    public static ItemStack getMCMMOXpCert(int xpAmount, String skillType, String trainer) {
        return ItemUtils.buildItem(Material.PAPER, (short)0, ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + "MCMMO XP Voucher " + (Object)ChatColor.GRAY + "(Right Click)", ChatColor.GOLD.toString() + "Skill " + (Object)ChatColor.GRAY + skillType, ChatColor.GOLD.toString() + "+" + format.format(xpAmount) + (Object)ChatColor.GRAY + " XP", ChatColor.GOLD.toString() + "Trainer " + (Object)ChatColor.GRAY + trainer, (Object)ChatColor.GRAY + "Applies mcMMO experience on use.");
    }

    protected static int getMCMMOXPCreditValue(ItemStack is) {
        if (is.getType() == Material.PAPER && is.hasItemMeta() && is.getItemMeta().hasDisplayName() && is.getItemMeta().getDisplayName().startsWith(ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + "MCMMO XP Voucher")) {
            return Integer.parseInt(ChatColor.stripColor((String)((String)is.getItemMeta().getLore().get(1)).split(" ")[0].replace("+", "").replaceAll(",", ""))) * is.getAmount();
        }
        return 0;
    }

    protected static SkillType getMCMMOXPCreditType(ItemStack is) {
        if (is.getType() == Material.PAPER && is.hasItemMeta() && is.getItemMeta().hasDisplayName() && is.getItemMeta().getDisplayName().startsWith((Object)ChatColor.GOLD + ChatColor.BOLD.toString() + "MCMMO XP Voucher")) {
            return SkillType.valueOf((String)ChatColor.stripColor((String)((String)is.getItemMeta().getLore().get(0)).split(" ")[1]).toUpperCase());
        }
        return null;
    }
}

