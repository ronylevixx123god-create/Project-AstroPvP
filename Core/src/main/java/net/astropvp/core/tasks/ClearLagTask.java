package net.astropvp.core.tasks;

import com.cosmicpvp.cosmicutils.utils.CC;
import net.astropvp.core.utils.EntityUtils;
import net.astropvp.core.utils.TimeUtils;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;


public class ClearLagTask extends BukkitRunnable {

    private int ticks = 15 * 60;
    @Override
    public void run() {
        ticks--;
        if((ticks >= 1 && ticks <= 5) || ticks == 30 || ticks == 60 || ticks == 300) {
            broadcastMessage(ticks);
        }
        else if(ticks == 0) {
            Bukkit.broadcastMessage("");
            Bukkit.broadcastMessage(CC.translate("&b&l*** &3&lALL DROPPED ITEMS HAVE BEEN CLEARED &b&l***"));
            Bukkit.broadcastMessage("");
            ticks = 15 * 60;
            EntityUtils.clearAllEntities();
        }
    }

    private void broadcastMessage(int seconds) {
        Bukkit.broadcastMessage("");
        Bukkit.broadcastMessage(CC.translate("&b&l*** &3&lALL DROPPED WILL BE CLEARED IN &f&n" + TimeUtils.format(seconds)  + "&r &b&l***"));
        Bukkit.broadcastMessage("");
    }

}

