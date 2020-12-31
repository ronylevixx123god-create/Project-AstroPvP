package net.astropvp.core.commands;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.FactionUtils;
import com.google.common.collect.Lists;
import java.util.List;

import net.astropvp.core.cachers.LastManStandingCacher;
import net.astropvp.core.enums.CapType;
import net.astropvp.core.utils.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LastManStandingCommand implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
        if (!(sender instanceof Player))
            return false;
        Player player = (Player)sender;
        if (args.length == 0 || !args[0].contentEquals("top")) {
            LastManStandingCacher capper = LastManStandingCacher.getLongestPlayerAlive(CapType.SPAWN);
            player.sendMessage("");
            player.sendMessage(String.valueOf(CC.AquaB) + "*** AstroPvP: " + CC.DarkAquaB + "LAST MAN STANDING " + CC.AquaB + "***");
            player.sendMessage("");
            player.sendMessage(String.valueOf(CC.AquaB) + "Current SPAWN LMS: " + CC.DarkAqua + ((capper == null) ? "N/A" : (String.valueOf(FactionUtils.getFPlayer(capper.getPlayer()).getRole().getPrefix()) + ChatColor.stripColor(FactionUtils.getFPlayer(capper.getPlayer()).getFaction().getTag()) + " " + capper.getPlayerName() + " " + CC.Gray + "[" + capper.formatTimeCapped(capper.getLMSTime()) + "]")));
            if (capper != null) {
                player.sendMessage(String.valueOf(CC.AquaB) + "Total Earned: " + CC.DarkAqua + "$" + StringUtils.formatWithCommas(capper.getMoneyEarned()) + CC.AquaB + " + " + CC.DarkAqua + StringUtils.formatWithCommas(capper.getExperienceEarned()) + "XP");
                player.sendMessage(String.valueOf(CC.AquaB) + "Last Location: " + CC.DarkAqua + (int)capper.getPlayer().getLocation().getX() + CC.Bold + "x " + CC.DarkAqua + (int)capper.getPlayer().getLocation().getY() + CC.Bold + "y " + CC.DarkAqua + (int)capper.getPlayer().getLocation().getZ() + CC.Bold + "z");
            }
            player.sendMessage(String.valueOf(CC.AquaB) + "Players: " + CC.DarkAqua + LastManStandingCacher.getPlayersAround(CapType.SPAWN));
            player.sendMessage("");
            capper = LastManStandingCacher.getLongestPlayerAlive(CapType.DESERT);
            player.sendMessage(String.valueOf(CC.AquaB) + "Current DESERT LMS: " + CC.DarkAqua + ((capper == null) ? "N/A" : (String.valueOf(FactionUtils.getFPlayer(capper.getPlayer()).getRole().getPrefix()) + ChatColor.stripColor(FactionUtils.getFPlayer(capper.getPlayer()).getFaction().getTag()) + " " + capper.getPlayerName() + " " + CC.Gray + "[" + capper.formatTimeCapped(capper.getLMSTime()) + "]")));
            if (capper != null) {
                player.sendMessage(String.valueOf(CC.AquaB) + "Total Earned: " + CC.DarkAqua + "$" + StringUtils.formatWithCommas(capper.getMoneyEarned()) + CC.AquaB + " + " + CC.DarkAqua + StringUtils.formatWithCommas(capper.getExperienceEarned()) + "XP");
                player.sendMessage(String.valueOf(CC.AquaB) + "Last Location: " + CC.DarkAqua + (int)capper.getPlayer().getLocation().getX() + CC.Bold + "x " + CC.DarkAqua + (int)capper.getPlayer().getLocation().getY() + CC.Bold + "y " + CC.DarkAqua + (int)capper.getPlayer().getLocation().getZ() + CC.Bold + "z");
            }
            player.sendMessage(String.valueOf(CC.AquaB) + "Players: " + CC.DarkAqua + LastManStandingCacher.getPlayersAround(CapType.DESERT));
            player.sendMessage("");
            player.sendMessage(String.valueOf(CC.Gray) + "Use " + CC.White + "/lms top " + CC.Gray + "to view all LMS players in order of duration.");
            player.sendMessage("");
            return false;
        }
        int page = 1;
        if (args.length > 1)
            try {
                page = Math.min(1, Integer.valueOf(args[1]).intValue());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        player.sendMessage(String.valueOf(CC.AquaB) + "Top Surviving Players (" + CC.DarkAquaB + page + CC.AquaB + "/" + CC.DarkAquaB + (1 + LastManStandingCacher.getCachers().size() / 10) + CC.AquaB + ")");
        List<LastManStandingCacher> cachers = Lists.newArrayList();
        int i;
        for (i = (page - 1) * 10; i < (page - 1) * 10 + 10 && i < LastManStandingCacher.getCachers().size(); i++) {
            LastManStandingCacher cacher = LastManStandingCacher.getCachers().get(i);
            long capping = cacher.getMilisecondsPlayerWasCapping();
            int pos = 0;
            for (LastManStandingCacher x : cachers) {
                if (x.getMilisecondsPlayerWasCapping() < capping)
                    break;
                pos++;
            }
            cachers.add(pos, cacher);
        }
        for (i = 0; i < cachers.size(); i++) {
            LastManStandingCacher capper2 = cachers.get(i);
            if (capper2 == null) {
                cachers.remove(i);
                i = 0;
            } else {
                player.sendMessage(String.valueOf(CC.AquaB) + (i + 1) + ". " + CC.DarkAqua + FactionUtils.getFPlayer(capper2.getPlayer()).getRole().getPrefix() + ChatColor.stripColor(FactionUtils.getFPlayer(capper2.getPlayer()).getFaction().getTag()) + " " + capper2.getPlayerName() + ": " + CC.Aqua + capper2.formatTimeCapped(capper2.getSecondsPlayerWasCapping()) + ((i == 0) ? (String.valueOf(CC.DarkAquaB) + " - LMS for [" + CC.Aqua + capper2.formatTimeCapped(capper2.getLMSTime()) + CC.DarkAquaB + "]") : ""));
            }
        }
        return false;
    }
}

