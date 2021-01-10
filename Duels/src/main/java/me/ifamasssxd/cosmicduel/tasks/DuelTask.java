/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.CosmicSpaceChests.CosmicSpaceChestsAPI
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.entity.Player
 */
package me.ifamasssxd.cosmicduel.tasks;

import com.cosmicpvp.CosmicSpaceChests.CosmicSpaceChestsAPI;
import me.ifamasssxd.cosmicduel.DuelAPI;
import me.ifamasssxd.cosmicduel.struct.enums.DuelCreateState;
import me.ifamasssxd.cosmicduel.struct.enums.DuelState;
import me.ifamasssxd.cosmicduel.struct.enums.EndReason;
import me.ifamasssxd.cosmicduel.struct.enums.Setting;
import me.ifamasssxd.cosmicduel.struct.newduel.NewDuel;
import me.ifamasssxd.cosmicduel.utils.PlayerUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class DuelTask
implements Runnable {
    @Override
    public void run() {
        for (NewDuel d : new ArrayList<>(DuelAPI.duels)) {
            if (d.getDuelState() != DuelState.PLAYING || d.getCreateState() != DuelCreateState.DONE) continue;
            d.setGameTimer(d.getGameTimer() + 1);
            d.setPvpTimer(d.getPvpTimer() - 1);
            if (d.getPvpTimer() <= 0) {
                d.sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + "(!) " + ChatColor.RED + "Damage has not been taken for 2 minutes.", true);
                d.sendMessage(ChatColor.RED + "The duel has been cancelled.", true);
                d.onDuelEnd(EndReason.NO_WINNER, null);
                continue;
            }
            if (d.getGameTimer() < 600 && d.getSettings().hasSetting(Setting.ENVOY)) {
                int delay;
                int n = delay = d.getGameTimer() < 180 ? 6 : 10;
                if (d.getGameTimer() == 1 || d.getGameTimer() % delay == 0) {
                    this.spawnEnvoy(d);
                }
            }
            if (d.getGameTimer() == 300) {
                d.sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + "(!) " + ChatColor.RED + "All damage is now " + ChatColor.RED + ChatColor.BOLD.toString() + "2X" + ChatColor.RED + "!", true);
                continue;
            }
            if (d.getGameTimer() >= 660) {
                for (Player p : d.getPlayers()) {
                    if (p.isDead()) continue;
                    p.damage(0.0);
                    p.setHealth(PlayerUtils.getHealth(p) < 2.0 ? 0.0 : PlayerUtils.getHealth(p) - 2.0);
                }
                continue;
            }
            if (d.getGameTimer() < 600) continue;
            for (Player p : d.getPlayers()) {
                if (p.isDead()) continue;
                p.damage(0.0);
                p.setHealth(PlayerUtils.getHealth(p) < 1.0 ? 0.0 : PlayerUtils.getHealth(p) - 1.0);
            }
        }
    }

    public void spawnEnvoy(NewDuel d) {
        for (Player p : d.getPlayers()) {
            CosmicSpaceChestsAPI.dropChest(p.getLocation().clone().add(0.0, 10.0, 0.0));
        }
    }
}

