package me.venir.astrocollectors.commands;

import me.astropvp.astrospawners.AstroSpawners;
import me.venir.astrocollectors.collector.CollectorManager;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RemoveCollectorCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Location location = ((Player)commandSender).getLocation();

        if (!CollectorManager.DELEGATE.isCollectorContained(location.getChunk())) return true;
        Location collectorLocation = CollectorManager.DELEGATE.getCollector(location).getLocation();
        assert collectorLocation != null;
        CollectorManager.DELEGATE.removeCollector(location);
        collectorLocation.getBlock().setType(Material.AIR,true);
        commandSender.sendMessage(ChatColor.GREEN + "You have removed the chunk collector in this chunk. You may now place a new one!");
        return true;
    }
}
