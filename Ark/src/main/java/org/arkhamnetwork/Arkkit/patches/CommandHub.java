/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 */
package org.arkhamnetwork.Arkkit.patches;

import java.util.List;
import java.util.Random;
import org.arkhamnetwork.Arkkit.libs.AbstractCommand;
import org.arkhamnetwork.Arkkit.patches.HubCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

final class CommandHub
extends AbstractCommand {
    public CommandHub(String command, String usage, String description, List<String> aliases) {
        super(command, usage, description, aliases);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player)sender;
            String ran_hub = this.getRandomHub();
            HubCommand.redirect(ran_hub, p);
            return true;
        }
        return false;
    }

    private String getRandomHub() {
        if (HubCommand.SERVERS.size() > 0) {
            return HubCommand.SERVERS.get(new Random().nextInt(HubCommand.SERVERS.size()));
        }
        return "hub";
    }
}

