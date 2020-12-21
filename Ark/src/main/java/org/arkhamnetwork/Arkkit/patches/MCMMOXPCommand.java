/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.gmail.nossr50.api.ExperienceAPI
 *  org.bukkit.ChatColor
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 */
package org.arkhamnetwork.Arkkit.patches;

import com.gmail.nossr50.api.ExperienceAPI;
import java.text.DecimalFormat;
import java.util.List;
import org.arkhamnetwork.Arkkit.libs.AbstractCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

final class MCMMOXPCommand
extends AbstractCommand {
    private static DecimalFormat format = new DecimalFormat("#,###");

    public MCMMOXPCommand(String command, String usage, String description, List<String> aliases) {
        super(command, usage, description, aliases);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player p;
        Player player = p = sender instanceof Player ? (Player)sender : null;
        if (p == null) {
            return false;
        }
        if (args.length != 1) {
            p.sendMessage((Object)ChatColor.YELLOW + "/mcmmoxp <skill>");
            p.sendMessage((Object)ChatColor.GRAY + "Displays total EXP in mcMMO <skill>.");
            p.sendMessage((Object)ChatColor.GRAY + "Skills: taming, swords, alchemy, unarmed, archery, axes, acrobatics, fishing, excavation, mining, herbalism, repair, woodcutting");
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
        long currentXp = MCMMOXPCommand.getTotalMcmmoSkillXp(p, skillType);
        p.sendMessage(ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + "TOTAL " + skillType + " EXP: " + (Object)ChatColor.YELLOW + format.format(currentXp));
        p.sendMessage((Object)ChatColor.GRAY + "Use " + (Object)ChatColor.YELLOW + "/withdrawmcmmo " + skillType + (Object)ChatColor.GRAY + " to convert your skill EXP into a voucher.");
        return true;
    }

    private static long getTotalMcmmoSkillXp(Player p, String skillType) {
        int skillLevel = ExperienceAPI.getLevel((Player)p, (String)skillType);
        long totalXP = 0L;
        for (int i = 0; i < skillLevel; ++i) {
            totalXP += (long)ExperienceAPI.getXpNeededToLevel((int)i);
        }
        totalXP = (long)((float)totalXP + ExperienceAPI.getXPRaw((Player)p, (String)skillType));
        return totalXP;
    }
}

