package me.astropvp.command;

import com.cosmicpvp.cosmicutils.utils.CC;
import me.astropvp.GenBucketPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class GenbucketToggleCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (GenBucketPlugin.isGenEnabled()) {
            commandSender.sendMessage(CC.translate("&cGenbuckets are now disabled!"));
            GenBucketPlugin.setGenEnabled(false);
            return true;
        } else {
            commandSender.sendMessage(CC.translate("&aGenbuckets are now enabled!"));
            GenBucketPlugin.setGenEnabled(true);
            return true;
        }
    }
}
