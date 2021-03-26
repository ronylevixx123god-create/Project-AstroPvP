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

import com.compilex.CompilexPermissions.PermissionsAPI;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.StringUtils;
import me.matthawkk.autogiveall.struct.GiveAllManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class AutoGiveAllTask
implements Runnable {

    @Override
    public void run() {
        //GiveAllManager.setNextGiveAll(GiveAllManager.getNextGiveAll() - 1L);
        long updatedTime = GiveAllManager.getNextGiveAll();
        if (updatedTime == 0L) {
            List list = new ArrayList<String>();
            Bukkit.broadcastMessage("");
            Bukkit.broadcastMessage(CC.translate("&8[&a&lAstro&e&lPvP&8] &7Online &b&lApollo Rank &7Donators"));
            for (Player player : Bukkit.getOnlinePlayers()) {
                String rank = PermissionsAPI.getPermissionUser(player.getUniqueId()).getGroup();
                if (rank.equals("apollo")) {
                    list.add(player.getDisplayName());
                }
            }
            Bukkit.broadcastMessage(list.toString());
            Bukkit.broadcastMessage(CC.translate("&7&o(( You can buy the apollo rank at &fbuy.astropvp.net&7 ))"));
        }
    }

}

