/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.help.HelpTopic
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches.commandcooldown;

import org.arkhamnetwork.Arkkit.libs.AbstractCommand;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.bukkit.Bukkit;
import org.bukkit.help.HelpTopic;
import org.bukkit.plugin.Plugin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class CommandCooldowns
        extends ArkPlugin {
    public static HashMap<String, String> aliasToCmd;
    private static CommandCooldowns plugi;

    static {
        plugi = null;
    }

    private final File config = new File("plugins/Arkkit/command-cooldowns.yml");
    private CommandCooldownManager cooldown_manager;

    public CommandCooldowns(Plugin p) {
        super(p);
    }

    public static final CommandCooldowns get() {
        return plugi;
    }

    @Override
    public void enable() {
        plugi = this;
        this.cooldown_manager = new CommandCooldownManager();
        aliasToCmd = new HashMap();
        try {
            if (!this.config.exists()) {
                this.config.createNewFile();
            }
            this.loadCooldowns();
        } catch (IOException err) {
            Bukkit.getLogger().warning("Failed to loadCooldowns(), IOException thrown.");
            err.printStackTrace();
        }
        this.registerAliases();
        this.registerListeners();
    }

    @Override
    public void disable() {
    }

    private void registerAliases() {
        for (HelpTopic cmdLabel : Bukkit.getServer().getHelpMap().getHelpTopics()) {
            String cmd = cmdLabel.getName();
            if (!cmd.contains("/") || AbstractCommand.cmap.getCommand(cmd = cmd.replace("/", "")) == null) continue;
            for (String alias : AbstractCommand.cmap.getCommand(cmd).getAliases()) {
                if (cmd.equals(alias)) continue;
                aliasToCmd.put(alias, cmd);
            }
        }
        Bukkit.getLogger().info("[CommandCooldowns] Registered " + aliasToCmd.size() + " alias -> cmd mappings.");
    }

    private void loadCooldowns() throws IOException {
        String s;
        if (!this.config.exists()) {
            return;
        }
        BufferedReader br = new BufferedReader(new FileReader(this.config));
        while ((s = br.readLine()) != null) {
            if (!s.contains("=")) continue;
            String cmd = s.split("=")[0];
            long cooldown = Long.parseLong(s.split("=")[1]);
            this.cooldown_manager.registerCommandCooldown(cmd, cooldown);
            Bukkit.getLogger().info("Loaded command /" + cmd + " with cooldown of " + cooldown + "ms!");
        }
        br.close();
    }

    private void registerListeners() {
        this.registerListener(new CommandCooldownListener());
        this.registerListener(new CommandAliasListener());
    }

    public CommandCooldownManager getCooldownManager() {
        return this.cooldown_manager;
    }

    public void reloadCooldownConfig() {
        this.cooldown_manager = new CommandCooldownManager();
        try {
            this.loadCooldowns();
        } catch (IOException err) {
            Bukkit.getLogger().warning("Failed to loadCooldowns(), IOException thrown.");
            err.printStackTrace();
        }
    }
}

