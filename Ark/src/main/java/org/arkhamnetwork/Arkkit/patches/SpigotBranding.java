/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.plugin.Plugin
 *  org.spigotmc.SpigotConfig
 */
package org.arkhamnetwork.Arkkit.patches;

import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.spigotmc.SpigotConfig;

import java.util.HashMap;

public class SpigotBranding
        extends ArkPlugin {
    private final HashMap<String, String> default_variables = new HashMap();

    public SpigotBranding(Plugin p) {
        super(p);
    }

    @Override
    public void enable() {
        this.default_variables.put("restartMessage", SpigotConfig.restartMessage);
        this.default_variables.put("whitelistMessage", SpigotConfig.whitelistMessage);
        this.default_variables.put("unknownCommandMessage", SpigotConfig.unknownCommandMessage);
        this.default_variables.put("serverFullMessage", SpigotConfig.serverFullMessage);
        this.default_variables.put("outdatedClientMessage", SpigotConfig.outdatedClientMessage);
        this.default_variables.put("outdatedServerMessage", SpigotConfig.outdatedServerMessage);
        String spigot_prefix = ChatColor.translateAlternateColorCodes('&', this.getBukkitPlugin().getConfig().getString("language.spigot_branding.global_prefix"));
        String store_url = ChatColor.translateAlternateColorCodes('&', this.getBukkitPlugin().getConfig().getString("language.spigot_branding.store_url"));
        SpigotConfig.restartMessage = spigot_prefix + " \u00a7fServer is restarting!";
        SpigotConfig.whitelistMessage = spigot_prefix + " \u00a7cServer is under maintenance!";
        SpigotConfig.unknownCommandMessage = spigot_prefix + " \u00a7fUnknown command. Type '\u00a77/help\u00a7f' for help.";
        SpigotConfig.serverFullMessage = spigot_prefix + " \u00a7fThe server is currently full. Donate at \u00a7b\u00a7l" + store_url + " \u00a7fto join full servers!";
        SpigotConfig.outdatedClientMessage = spigot_prefix + "\u00a7cYou are running an outdated client! Please upgrade! (Server v" + Bukkit.getVersion() + ")";
        SpigotConfig.outdatedServerMessage = spigot_prefix + "\u00a7cYour client version is not supported. (Server v" + Bukkit.getVersion() + ")";
    }

    @Override
    public void disable() {
        SpigotConfig.restartMessage = this.default_variables.get("restartMessage");
        SpigotConfig.whitelistMessage = this.default_variables.get("whitelistMessage");
        SpigotConfig.unknownCommandMessage = this.default_variables.get("unknownCommandMessage");
        SpigotConfig.serverFullMessage = this.default_variables.get("serverFullMessage");
        SpigotConfig.outdatedClientMessage = this.default_variables.get("outdatedClientMessage");
        SpigotConfig.outdatedServerMessage = this.default_variables.get("outdatedServerMessage");
        this.default_variables.clear();
    }
}

