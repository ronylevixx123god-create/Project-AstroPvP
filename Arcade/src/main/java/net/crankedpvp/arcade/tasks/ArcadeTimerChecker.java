package net.crankedpvp.arcade.tasks;

import com.cosmicpvp.cosmicutils.utils.CC;
import net.crankedpvp.arcade.ArcadeAPI;
import net.crankedpvp.arcade.ArcadePlugin;
import net.crankedpvp.arcade.language.Language;
import net.crankedpvp.arcade.struct.ArcadeManager;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class ArcadeTimerChecker extends BukkitRunnable {
    public static List<Integer> openhours = Arrays.asList(0, 2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 22);
    private ArcadeManager arcadeManager = ArcadePlugin.getInstance().getArcadeManager();

    @Override
    public void run() {

        Calendar now = Calendar.getInstance();
        int hour = now.get(Calendar.HOUR_OF_DAY);
        if (openhours.contains(hour)) {
            if (!arcadeManager.isEnabled()) {
                for (String msg : Language.ENABLED_MESSAGE.getValuesAsList()) {
                    Bukkit.broadcastMessage(CC.translate(msg));
                }
                arcadeManager.setEnabled(true);
            }
        } else {
            if (arcadeManager.isEnabled()) {
                for (String msg : Language.CLOSED_MESSAGE.getValuesAsList()) {
                    Bukkit.broadcastMessage(CC.translate(msg));
                }
                arcadeManager.setEnabled(false);
                arcadeManager.closeArcade();
            }
        }
    }
}
