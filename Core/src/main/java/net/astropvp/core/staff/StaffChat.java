package net.astropvp.core.staff;

import com.cosmicpvp.cosmicutils.utils.CC;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class StaffChat {
    public static void send(Player sender, String msg) {
        Bukkit.getOnlinePlayers().forEach(player -> {
            if (player.hasPermission("astrocore.staff")) {
                player.sendMessage(CC.LightPurple + "[S] " + CC.White + sender.getName() + " " + CC.Gray + msg);
            }
        });
    }

}
