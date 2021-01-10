package net.astropvp.core.utils;

import net.astropvp.core.AstroCore;
import org.bukkit.entity.Player;

import java.util.List;

public class TitleUtil {
    public static void sendTitle(Player player, String string) {
        AstroCore.getTitleManagerAPI().sendTitle(player, string);
    }
    public static void sendSubTitle(Player player, String string) {
        AstroCore.getTitleManagerAPI().sendSubtitle(player, string);
    }

    public static void sendTitleList(List<Player> players, String string) {
        for (Player p : players) {
            AstroCore.getTitleManagerAPI().sendTitle(p, string);
        }
    }
    public static void sendSubTitleList(List<Player> players, String string) {
        for (Player p : players) {
            AstroCore.getTitleManagerAPI().sendSubtitle(p, string);
        }
    }
}
