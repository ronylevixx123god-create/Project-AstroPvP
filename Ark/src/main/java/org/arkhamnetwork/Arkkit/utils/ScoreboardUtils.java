/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.cosmicpvp.scoreboard.CosmicScoreboardPlugin
 *  com.cosmicpvp.scoreboard.libs.UserBoardLinePlaceholder
 *  org.bukkit.entity.Player
 *  org.bukkit.plugin.java.JavaPlugin
 */
package org.arkhamnetwork.Arkkit.utils;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.scoreboard.CosmicScoreboardPlugin;
import com.cosmicpvp.scoreboard.libs.UserBoardLinePlaceholder;
import java.text.DecimalFormat;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.patches.EnderpearlCooldown;
import org.arkhamnetwork.Arkkit.patches.combatlog.CombatLog;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class ScoreboardUtils {
    private static final DecimalFormat format = new DecimalFormat("#.#");

    public static void startEPearlCooldown(final Player pl) {
        CosmicScoreboardPlugin.getPlugin().getPlaceholderManager().addUserLinePlaceholder(pl, (JavaPlugin)Arkkit.get(), new UserBoardLinePlaceholder(-2, 20){

            public String getLine() {
                double timeLeft = EnderpearlCooldown.getEnderpearlCooldownLeftDouble(pl) / 1000.0;
                if (timeLeft <= 0.0) {
                    this.setEnabled(false);
                    return null;
                }
                return CC.LightPurple + "EPearl: " + CC.Bold + format.format(timeLeft) + "s";
            }
        });
    }

    public static void startCombatPlaceholder(final Player pl) {
        CosmicScoreboardPlugin.getPlugin().getPlaceholderManager().addUserLinePlaceholder(pl, (JavaPlugin)Arkkit.get(), new UserBoardLinePlaceholder(-1, 20){

            public String getLine() {
                double tag = CombatLog.getTimeLeftInCombat(pl) / 1000L;
                if (tag <= 0.0) {
                    this.setEnabled(false);
                    return null;
                }
                return CC.Red + "Combat: " + CC.Bold + (int)Math.ceil(tag) + "s";
            }
        });
    }
}

