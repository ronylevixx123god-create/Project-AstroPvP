package me.venir.astrocollectors.commands;

import me.venir.astrocollectors.AstroCollectors;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReloadCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        AstroCollectors.INSTANCE.getConfigManager().reloadFiles();
        AstroCollectors.INSTANCE.getSpawnerUtil().reloadTntIncomeValue();
        AstroCollectors.INSTANCE.getSpawnerUtil().reloadMobIncomeMap();
        return true;
    }
}
