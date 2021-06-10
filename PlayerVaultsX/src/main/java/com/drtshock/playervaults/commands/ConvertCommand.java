package com.drtshock.playervaults.commands;

import com.drtshock.playervaults.converters.*;
import org.bukkit.command.*;
import com.drtshock.playervaults.translations.*;
import com.drtshock.playervaults.*;
import com.drtshock.playervaults.vaultmanagement.*;
import java.util.*;
import org.bukkit.plugin.*;

public class ConvertCommand implements CommandExecutor
{
    private final List<Converter> converters;
    
    public ConvertCommand() {
        (this.converters = new ArrayList<Converter>()).add(new BackpackConverter());
    }
    
    public boolean onCommand(final CommandSender commandSender, final Command command, final String s, final String[] array) {
        if (!commandSender.hasPermission("playervaults.convert")) {
            commandSender.sendMessage(Lang.TITLE.toString() + Lang.NO_PERMS);
        }
        else if (array.length == 0) {
            commandSender.sendMessage(Lang.TITLE + "/pvconvert <all | plugin name>");
        }
        else {
            final String anotherString = array[0];
            final ArrayList<Converter> list = new ArrayList<Converter>();
            if (anotherString.equalsIgnoreCase("all")) {
                list.addAll(this.converters);
            }
            else {
                for (final Converter converter : this.converters) {
                    if (converter.getName().equalsIgnoreCase(anotherString)) {
                        list.add(converter);
                    }
                }
            }
            if (list.size() <= 0) {
                commandSender.sendMessage(Lang.TITLE.toString() + Lang.CONVERT_PLUGIN_NOT_FOUND);
            }
            else {
                commandSender.sendMessage(Lang.TITLE + Lang.CONVERT_BACKGROUND.toString());
                PlayerVaults.getInstance().getServer().getScheduler().runTaskLaterAsynchronously((Plugin)PlayerVaults.getInstance(), (Runnable)new Runnable() {
                    @Override
                    public void run() {
                        int i = 0;
                        VaultOperations.setLocked(true);
                        for (final Converter converter : list) {
                            if (converter.canConvert()) {
                                i += converter.run(commandSender);
                            }
                        }
                        VaultOperations.setLocked(false);
                        commandSender.sendMessage(Lang.TITLE + Lang.CONVERT_COMPLETE.toString().replace("%converted", i + ""));
                    }
                }, 5L);
            }
        }
        return true;
    }
}
