/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.cosmicpvp.cosmicutils.utils.StringUtils$DefaultFontInfo
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 */
package me.matthawkk.autogiveall.tasks;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.StringUtils;
import me.matthawkk.autogiveall.struct.GiveAllManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class AutoGiveAllTask
implements Runnable {
    private static final int CENTER_PX = 154;

    public static void sendCenteredMessage(String message) {
        message = ChatColor.translateAlternateColorCodes((char)'&', (String)message);
        int messagePxSize = 0;
        boolean previousCode = false;
        boolean isBold = false;
        for (char c : message.toCharArray()) {
            if (c == '\u00a7') {
                previousCode = true;
                continue;
            }
            if (previousCode) {
                previousCode = false;
                if (c == 'l' || c == 'L') {
                    isBold = true;
                    continue;
                }
                isBold = false;
                continue;
            }
            StringUtils.DefaultFontInfo dFI = StringUtils.DefaultFontInfo.getDefaultFontInfo((char)c);
            messagePxSize += isBold ? dFI.getBoldLength() : dFI.getLength();
            ++messagePxSize;
        }
        int halvedMessageSize = messagePxSize / 2;
        int toCompensate = 154 - halvedMessageSize;
        int spaceLength = StringUtils.DefaultFontInfo.SPACE.getLength() + 1;
        StringBuilder sb = new StringBuilder();
        for (int compensated = 0; compensated < toCompensate; compensated += spaceLength) {
            sb.append(" ");
        }
        Bukkit.broadcastMessage((String)(sb.toString() + message));
    }

    @Override
    public void run() {
        GiveAllManager.setNextGiveAll(GiveAllManager.getNextGiveAll() - 1L);
        long updatedTime = GiveAllManager.getNextGiveAll();
        if (updatedTime == 1200L || updatedTime == 900L || updatedTime == 600L || updatedTime == 300L || updatedTime == 180L || updatedTime == 60L) {
            this.announceGiveAll(updatedTime / 60L, true);
        } else if (updatedTime == 30L || updatedTime == 20L || updatedTime == 10L || updatedTime == 5L || updatedTime == 3L || updatedTime == 2L || updatedTime == 1L) {
            this.announceGiveAll(updatedTime, false);
        } else if (updatedTime == 0L) {
            GiveAllManager.doGiveAll();
        }
    }

    private void announceGiveAll(long time, boolean minutes) {
        AutoGiveAllTask.sendCenteredMessage("");
        AutoGiveAllTask.sendCenteredMessage(CC.AquaB + "AUTO GIVE ALL");
        if (minutes) {
            AutoGiveAllTask.sendCenteredMessage(CC.Aqua + "Time Remaining: " + CC.White + time + "m");
        } else {
            AutoGiveAllTask.sendCenteredMessage(CC.Aqua + "Time Remaining: " + CC.White + time + "s");
        }
        AutoGiveAllTask.sendCenteredMessage(CC.Gray + "Use the command /nextgiveall to stay updated!");
        AutoGiveAllTask.sendCenteredMessage("");
    }
}

