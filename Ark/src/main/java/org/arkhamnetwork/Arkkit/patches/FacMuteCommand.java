/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.compat.ParamBuilder
 *  com.cosmicpvp.cosmicutils.utils.FactionUtils
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandSender
 */
package org.arkhamnetwork.Arkkit.patches;

import com.cosmicpvp.cosmicutils.compat.ParamBuilder;
import com.cosmicpvp.cosmicutils.utils.FactionUtils;
import java.util.List;
import org.arkhamnetwork.Arkkit.libs.AbstractCommand;
import org.arkhamnetwork.Arkkit.patches.ExtraMute;
import org.arkhamnetwork.Arkkit.utils.TimeUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

class FacMuteCommand
extends AbstractCommand {
    private ExtraMute instance;

    public FacMuteCommand(ExtraMute mute, String command, String usage, String description, List<String> aliases) {
        super(command, usage, description, aliases);
        this.instance = mute;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        long time;
        if (!sender.isOp() && !sender.hasPermission("facmute.mute")) {
            return true;
        }
        if (args.length < 3) {
            sender.sendMessage((Object)ChatColor.RED + "Invalid Usage: /facmute <fac> <length> <reason>");
            return true;
        }
        String facName = args[0];
        String length = args[1];
        StringBuilder reason = new StringBuilder();
        for (int i = 2; i < args.length; ++i) {
            reason.append(args[i]).append(" ");
        }
        try {
            time = TimeUtils.parseDateDiff(length, true);
        }
        catch (Exception e) {
            sender.sendMessage((Object)ChatColor.RED + "Invalid time given!");
            return true;
        }
        List fplayerIds = FactionUtils.getFPlayerIdsByTag((String)args[0]);
        if (fplayerIds == null) {
            sender.sendMessage((Object)ChatColor.RED + "Faction doesnt exist.");
            return true;
        }
        String id = FactionUtils.getFactionIdByTag((String)facName);
        if (id == null) {
            sender.sendMessage((Object)ChatColor.RED + "Faction doesnt exist.");
            return true;
        }
        sender.sendMessage((Object)ChatColor.RED + "Faction muted.");
        FactionUtils.callFactionMethodByTag((String)facName, (String)"sendMessage", (ParamBuilder)new ParamBuilder(String.class, (Object)((Object)ChatColor.RED + ChatColor.BOLD.toString() + "(!) " + (Object)ChatColor.RED + "Your faction has been muted for " + length + " by " + sender.getName() + "!")));
        for (String fplayerId : fplayerIds) {
            Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), (String)("tempmute " + fplayerId + " " + length + " " + reason));
        }
        this.instance.getFactionsMuted().put(id, time);
        return false;
    }
}

