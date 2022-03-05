package me.astropvp.astroscoreboard.listeners;

import com.cosmicpvp.cosmicutils.utils.CC;
import io.netty.util.internal.ThreadLocalRandom;
import me.astropvp.astroscoreboard.AstroScoreboardPlugin;
import me.astropvp.astroscoreboard.manager.TabListManager;
import org.arkhamnetwork.Arkkit.utils.EssentialsUtils;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.Scoreboard;

public class PlayerListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onJoin(PlayerJoinEvent event) {


        //board.registerNewObjective("astro", "dummy");

        //String objectiveName = ChatColor.RED + "\u2764";
        //Player player = event.getPlayer();
        //player.setHealth(player.getHealth());
        //board.registerNewObjective(objectiveName, "health").setDisplaySlot(DisplaySlot.BELOW_NAME);
        //player.setScoreboard(board);
/*        try {
            TabListManager.sendTablist(player,
                    CC.translate("\n&e&l●&6&l●&f&l● &f[ &6&lAstro&f&lPvP &f] &f&l●&6&l●&e&l●\n&7play.astropvp.net\n"),
                    CC.translate("\n&6  Factions &e// &d" + getPing(player) + "ms &e// &a" + AstroScoreboardPlugin.df.format(EssentialsUtils.getEssentials().getTimer().getAverageTPS()) + " &7&oTPS &8➥ &f&ohttps://astropvp.net  \n" +
                            "&e" + Bukkit.getOnlinePlayers().size() + " &fOnline &8➥ &f&ohttps://buy.astropvp.net/\n"));
        } catch (Exception ignored) {}*/
    }

 /*   @EventHandler(priority = EventPriority.HIGHEST)
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        Scoreboard board = player.getScoreboard();
        if(board != null) {
            board.getTeams().forEach(team -> team.unregister());
        }

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onQuit(PlayerKickEvent event) {
        Player player = event.getPlayer();
        Scoreboard board = player.getScoreboard();
        if(board != null) {
            board.getTeams().forEach(team -> team.unregister());
        }

    }*/

    public static int getPing(Player player) {
        int ping = ((CraftPlayer) player).getHandle().ping;
        if (ping == 0) {
            ping = ThreadLocalRandom.current().nextInt(90, 150);
        } else if (ping > 80 && ping < 210) {
            ping = ThreadLocalRandom.current().nextInt(40, 80);

        } else if
        (ping > 210) {
            ping = ThreadLocalRandom.current().nextInt(100, 140);
        }
        return ping;
    }


}
