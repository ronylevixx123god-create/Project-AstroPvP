package me.astropvp.hotfixplugin.commands;

import com.compilex.CompilexPermissions.CompilexPermissions;
import com.compilex.CompilexPermissions.PermissionsAPI;
import com.compilex.CompilexPermissions.structure.PermissionUser;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class LuckPermFix implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if (!sender.isOp()) return false;

        for (PermissionUser user : CompilexPermissions.get().getDatabaseManager().get)

        return true;
    }
}
