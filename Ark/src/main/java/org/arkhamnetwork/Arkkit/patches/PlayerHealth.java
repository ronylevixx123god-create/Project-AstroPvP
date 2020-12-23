/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.scoreboard.DisplaySlot
 *  org.bukkit.scoreboard.Objective
 *  org.bukkit.scoreboard.Scoreboard
 */
package org.arkhamnetwork.Arkkit.patches;

import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.HashMap;
import java.util.logging.Logger;

public class PlayerHealth
        extends ArkPlugin {
    private static final Logger log = Logger.getLogger("Minecraft");
    protected static HashMap<String, String> playerDisguises;

    public PlayerHealth(Plugin p) {
        super(p);
    }

    @Override
    public void enable() {
        log.info("[HealthDisplay] Starting.");
        playerDisguises = new HashMap();
        String objectiveName = ChatColor.RED + "\u2764";
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        for (Objective objective : scoreboard.getObjectivesByCriteria("health")) {
            objective.unregister();
        }
        scoreboard.registerNewObjective(objectiveName, "health").setDisplaySlot(DisplaySlot.BELOW_NAME);
        if (Bukkit.getPluginManager().isPluginEnabled("LibsDisguises")) {
            Bukkit.getLogger().info("[HealthDispaly] Detected LibsDisguises.jar, Launching SpoofHealthDisplay()...");
            this.registerListener(new DisguiseCommandListener());
            this.registerTask(new SpoofHealthDisplay().runTaskTimer(Arkkit.get(), 20L, 20L), PlayerHealth.class);
        }
    }

    @Override
    public void disable() {
        if (!Arkkit.get().shutting_down) {
            Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
            for (Objective objective : scoreboard.getObjectivesByCriteria("health")) {
                objective.unregister();
            }
        }
    }
}

