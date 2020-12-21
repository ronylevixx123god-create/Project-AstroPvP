/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.gmail.nossr50.api.ExperienceAPI
 *  org.bukkit.ChatColor
 *  org.bukkit.Sound
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 */
package org.arkhamnetwork.Arkkit.patches;

import com.gmail.nossr50.api.ExperienceAPI;
import java.text.DecimalFormat;
import java.util.List;
import org.arkhamnetwork.Arkkit.libs.AbstractCommand;
import org.arkhamnetwork.Arkkit.patches.ItemID;
import org.arkhamnetwork.Arkkit.patches.WithdrawMCMMO;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugins;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

final class WithdrawMCMMOCommand
extends AbstractCommand {
    private static DecimalFormat format = new DecimalFormat("#,###");

    public WithdrawMCMMOCommand(String command, String usage, String description, List<String> aliases) {
        super(command, usage, description, aliases);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player p;
        Player player = p = sender instanceof Player ? (Player)sender : null;
        if (p == null) {
            return false;
        }
        if (args.length != 2) {
            p.sendMessage(ChatColor.YELLOW.toString() + (Object)ChatColor.BOLD + "/withdrawmcmmo <skill> <xp>");
            p.sendMessage((Object)ChatColor.GRAY + "Withdraws <xp> exp from mcMMO <skill>.");
            p.sendMessage((Object)ChatColor.GRAY + "Use " + (Object)ChatColor.YELLOW + "/stats" + (Object)ChatColor.GRAY + " to view your current mcmmo levels.");
            p.sendMessage((Object)ChatColor.GRAY + "Use " + (Object)ChatColor.YELLOW + "/mcmmoxp" + (Object)ChatColor.GRAY + " to view your mcmmo xp values.");
            return true;
        }
        String skillType = args[0].toUpperCase();
        if (!ExperienceAPI.isValidSkillType((String)skillType)) {
            p.sendMessage((Object)ChatColor.RED + "The skill '" + skillType + "' is not valid.");
            p.sendMessage((Object)ChatColor.GRAY + "Skills: taming, swords, alchemy, unarmed, archery, axes, acrobatics, fishing, excavation, mining, herbalism, repair, woodcutting");
            return true;
        }
        if (skillType.equals("SALVAGE") || skillType.equals("SMELTING")) {
            p.sendMessage((Object)ChatColor.RED + "The skill '" + skillType + "' has no XP value.");
            p.sendMessage((Object)ChatColor.GRAY + "Skills: taming, swords, alchemy, unarmed, archery, axes, acrobatics, fishing, excavation, mining, herbalism, repair, woodcutting");
            return true;
        }
        int amount = 0;
        try {
            amount = Integer.parseInt(args[1]);
        }
        catch (NumberFormatException nfe) {
            p.sendMessage((Object)ChatColor.RED + "Invalid <XP> amount specified.");
            return true;
        }
        if (amount < 100) {
            p.sendMessage((Object)ChatColor.RED + "You must withdraw at least 100XP points per skill voucher.");
            return true;
        }
        long currentXp = WithdrawMCMMOCommand.getTotalMcmmoSkillXpLong(p, skillType);
        if (currentXp < (long)amount) {
            p.sendMessage((Object)ChatColor.RED + "You only have " + format.format(currentXp) + " xp points to convert.");
            p.sendMessage((Object)ChatColor.GRAY + "View your exp values and levels with /stats");
            return true;
        }
        if (p.getInventory().firstEmpty() == -1) {
            p.sendMessage((Object)ChatColor.RED + "You do not have any inventory space.");
            return true;
        }
        this.removeXPAndDelevel(p, skillType, amount);
        p.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "- " + format.format(amount) + " " + skillType + " XP");
        p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1.0f, 0.2f);
        ItemStack mcmmoCert = WithdrawMCMMO.getMCMMOXpCert(amount, skillType, p.getName());
        if (ArkPlugins.ITEM_ID.getArkPlugin().isRunning()) {
            ItemID.setItemID(mcmmoCert, ItemID.generateNewItemID());
        }
        p.getInventory().addItem(new ItemStack[]{mcmmoCert});
        return true;
    }

    private void removeXPAndDelevel(Player p, String skillType, int xp) {
        WithdrawMCMMOCommand.setTotalMcMMOSkillXp(p, skillType, WithdrawMCMMOCommand.getTotalMcmmoSkillXpLong(p, skillType) - (long)xp);
    }

    protected static void addXPAndLevel(Player p, String skillType, int xp) {
        WithdrawMCMMOCommand.setTotalMcMMOSkillXp(p, skillType, WithdrawMCMMOCommand.getTotalMcmmoSkillXpLong(p, skillType) + (long)xp);
    }

    private static void setTotalMcMMOSkillXp(Player p, String skillType, long xp) {
        int level = 0;
        while (xp >= (long)ExperienceAPI.getXpNeededToLevel((int)level)) {
            xp -= (long)ExperienceAPI.getXpNeededToLevel((int)level);
            ++level;
        }
        ExperienceAPI.setLevel((Player)p, (String)skillType, (int)level);
        if (xp < Integer.MAX_VALUE) {
            ExperienceAPI.setXP((Player)p, (String)skillType, (int)((int)xp));
        } else {
            ExperienceAPI.setXP((Player)p, (String)skillType, (int)0);
        }
    }

    private static long getTotalMcmmoSkillXpLong(Player p, String skillType) {
        int skillLevel = ExperienceAPI.getLevel((Player)p, (String)skillType);
        long totalXP = 0L;
        for (int i = 0; i < skillLevel; ++i) {
            totalXP += (long)ExperienceAPI.getXpNeededToLevel((int)i);
        }
        totalXP = (long)((float)totalXP + ExperienceAPI.getXPRaw((Player)p, (String)skillType));
        return totalXP;
    }
}

