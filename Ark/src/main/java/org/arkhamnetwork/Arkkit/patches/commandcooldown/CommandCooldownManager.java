/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.entity.Player
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches.commandcooldown;

import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.libs.AbstractCommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CommandCooldownManager {
    private final HashMap<String, Long> command_cooldowns = new HashMap();

    public void registerCommandCooldown(String cmd, long cooldown) {
        this.command_cooldowns.put(cmd, cooldown);
    }

    public void unregisterCommandCooldown(String cmd) {
        this.command_cooldowns.remove(cmd);
    }

    public boolean isRegistered(String cmd) {
        List<String> commandsAndAliases = this.getCommandAndAliases(cmd);
        for (String cmds : commandsAndAliases) {
            if (!this.command_cooldowns.containsKey(cmds)) continue;
            return true;
        }
        return false;
    }

    public long getCooldownMillis(String cmd) {
        List<String> commandsAndAliases = this.getCommandAndAliases(cmd);
        long longCD = 0L;
        for (String cmds : commandsAndAliases) {
            if (!this.command_cooldowns.containsKey(cmds) || this.command_cooldowns.get(cmds) <= longCD) continue;
            longCD = this.command_cooldowns.get(cmds);
        }
        return longCD;
    }

    public void addCooldown(Player pl, String cmd) {
        long cd = this.getCooldownMillis(cmd);
        if (cd > 60000L && pl.hasPermission("arkkit.command_cooldowns." + cmd + ".1mincd")) {
            cd = 60000L;
        }
        List<String> commandsAndAliases = this.getCommandAndAliases(cmd);
        if (cd > 0L) {
            for (String cmds : commandsAndAliases) {
                pl.setMetadata("arkham_cooldown_" + cmds, new FixedMetadataValue(Arkkit.get(), System.currentTimeMillis() + cd));
            }
        }
    }

    public void addCooldown(Player pl, String cmd, long cd_ms) {
        long cd = cd_ms;
        if (cd > 60000L && pl.hasPermission("arkkit.command_cooldowns." + cmd + ".1mincd")) {
            cd = 60000L;
        }
        List<String> commandsAndAliases = this.getCommandAndAliases(cmd);
        if (cd > 0L) {
            for (String cmds : commandsAndAliases) {
                pl.setMetadata("arkham_cooldown_" + cmds, new FixedMetadataValue(Arkkit.get(), System.currentTimeMillis() + cd));
            }
        }
    }

    private List<String> getCommandAndAliases(String cmd) {
        ArrayList<String> commandsAndAliases = new ArrayList<String>();
        if (CommandCooldowns.aliasToCmd.containsKey(cmd)) {
            cmd = CommandCooldowns.aliasToCmd.get(cmd);
        }
        commandsAndAliases.add(cmd);
        try {
            if (AbstractCommand.cmap.getCommand(cmd) != null && AbstractCommand.cmap.getCommand(cmd).getAliases() != null && AbstractCommand.cmap.getCommand(cmd).getAliases().size() > 0) {
                for (String alias : AbstractCommand.cmap.getCommand(cmd).getAliases()) {
                    commandsAndAliases.add(alias);
                }
            }
        } catch (NullPointerException npe) {
            Bukkit.getLogger().info("(CommandCooldownManager) No command '" + cmd + "' registered, could not lookup aliases.");
        }
        return commandsAndAliases;
    }

    public boolean hasCooldown(Player pl, String cmd) {
        return pl.hasMetadata("arkham_cooldown_" + cmd) && this.getCooldownMillisRemaining(pl, cmd) > 0L;
    }

    public long getCooldownMillisRemaining(Player pl, String cmd) {
        if (!pl.hasMetadata("arkham_cooldown_" + cmd)) {
            return 0L;
        }
        long expiration = pl.getMetadata("arkham_cooldown_" + cmd).get(0).asLong();
        if (System.currentTimeMillis() > expiration) {
            return 0L;
        }
        return expiration - System.currentTimeMillis();
    }
}

