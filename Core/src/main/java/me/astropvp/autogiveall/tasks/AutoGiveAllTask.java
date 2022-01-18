/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.me.astropvp.cratekeys.net.astropvp.armorsets.utils.CC
 *  com.cosmicpvp.cosmicutils.me.astropvp.cratekeys.net.astropvp.armorsets.utils.StringUtils$DefaultFontInfo
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 */
package me.astropvp.autogiveall.tasks;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.StringUtils;
import me.astropvp.autogiveall.struct.GiveAllManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class AutoGiveAllTask
implements Runnable {

    private static final int CENTER_PX = 154;

    public static void sendCenteredMessage(String message) {
        message = ChatColor.translateAlternateColorCodes('&', message);
        int messagePxSize = 0;
        boolean previousCode = false;
        boolean isBold = false;
        for (char c : message.toCharArray()) {
            if (c == '§') {
            previousCode = true;
        } else if (previousCode == true) {
            previousCode = false;
            if (c == 'l' || c == 'L') {
                isBold = true;
            } else {
                isBold = false;
            }
        } else {
            StringUtils.DefaultFontInfo dFI = StringUtils.DefaultFontInfo.getDefaultFontInfo(c);
            messagePxSize += isBold ? dFI.getBoldLength() : dFI.getLength();
            messagePxSize++;
        }
    }
    int halvedMessageSize = messagePxSize / 2;
    int toCompensate = 154 - halvedMessageSize;
    int spaceLength = StringUtils.DefaultFontInfo.SPACE.getLength() + 1;
    int compensated = 0;
    StringBuilder sb = new StringBuilder();
    while (compensated < toCompensate) {
        sb.append(" ");
        compensated += spaceLength;
    }
    Bukkit.broadcastMessage(sb.toString() + message);
}

    public void run() {
        GiveAllManager.setNextGiveAll(GiveAllManager.getNextGiveAll() - 1L);
        long updatedTime = GiveAllManager.getNextGiveAll();
        if (updatedTime == 1200L || updatedTime == 900L || updatedTime == 600L || updatedTime == 300L || updatedTime == 180L || updatedTime == 60L) {
            announceGiveAll(updatedTime / 60L, true);
        } else if (updatedTime == 30L || updatedTime == 20L || updatedTime == 10L || updatedTime == 5L || updatedTime == 3L || updatedTime == 2L || updatedTime == 1L) {
            announceGiveAll(updatedTime, false);
        } else if (updatedTime == 0L) {
            GiveAllManager.doGiveAll();
        }
    }

    private void announceGiveAll(long time, boolean minutes) {
        sendCenteredMessage("");
        sendCenteredMessage(CC.translate("&2&m--&8&m--&7&m--&a&m--&r[ &a&lAuto Giveall &f]&a&m--&7&m--&8&m--&2&m--"));
        if (minutes) {
            sendCenteredMessage(CC.Green + "Time Remaining: " + CC.White + time + "m");
        } else {
            sendCenteredMessage(CC.Green + "Time Remaining: " + CC.White + time + "s");
        }
        sendCenteredMessage(CC.translate("&aUse the command &f/nextgiveall&a to stay updated!"));
        sendCenteredMessage("");
    }

}

