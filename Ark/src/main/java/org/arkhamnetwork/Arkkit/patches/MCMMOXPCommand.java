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
import org.arkhamnetwork.Arkkit.libs.AbstractCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;
import java.util.List;

final class MCMMOXPCommand
        extends AbstractCommand {
    private static final DecimalFormat format = new DecimalFormat("#,###");

    public MCMMOXPCommand(String command, String usage, String description, List<String> aliases) {
        super(command, usage, description, aliases);
    }

    private static long getTotalMcmmoSkillXp(Player p, String skillType) {
        int skillLevel = ExperienceAPI.getLevel(p, skillType);
        long totalXP = 0L;
        for (int i = 0; i < skillLevel; ++i) {
            totalXP += ExperienceAPI.getXpNeededToLevel(i);
        }
        totalXP = (long) ((float) totalXP + ExperienceAPI.getXPRaw(p, skillType));
        return totalXP;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player p;
        Player player = p = sender instanceof Player ? (Player) sender : null;
        if (p == null) {
            return false;
        }
        if (args.length != 1) {
            p.sendMessage(ChatColor.YELLOW + "/mcmmoxp <skill>");
            p.sendMessage(ChatColor.GRAY + "Displays total EXP in mcMMO <skill>.");
            p.sendMessage(ChatColor.GRAY + "Skills: taming, swords, alchemy, unarmed, archery, axes, acrobatics, fishing, excavation, mining, herbalism, repair, woodcutting");
            return true;
        }
        String skillType = args[0].toUpperCase();
        if (!ExperienceAPI.isValidSkillType(skillType)) {
            p.sendMessage(ChatColor.RED + "The skill '" + skillType + "' is not valid.");
            p.sendMessage(ChatColor.GRAY + "Skills: taming, swords, alchemy, unarmed, archery, axes, acrobatics, fishing, excavation, mining, herbalism, repair, woodcutting");
            return true;
        }
        if (skillType.equals("SALVAGE") || skillType.equals("SMELTING")) {
            p.sendMessage(ChatColor.RED + "The skill '" + skillType + "' has no XP value.");
            p.sendMessage(ChatColor.GRAY + "Skills: taming, swords, alchemy, unarmed, archery, axes, acrobatics, fishing, excavation, mining, herbalism, repair, woodcutting");
            return true;
        }
        long currentXp = MCMMOXPCommand.getTotalMcmmoSkillXp(p, skillType);
        p.sendMessage(ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + "TOTAL " + skillType + " EXP: " + ChatColor.YELLOW + format.format(currentXp));
        p.sendMessage(ChatColor.GRAY + "Use " + ChatColor.YELLOW + "/withdrawmcmmo " + skillType + ChatColor.GRAY + " to convert your skill EXP into a voucher.");
        return true;
    }
}

