/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.GeneralUtil
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Player
 */
package me.astropvp.tasks;

import com.cosmicpvp.cosmicutils.utils.GeneralUtil;
import me.astropvp.objects.skins.PortablePicnicSkin;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class PortablePicnicTask {

    public boolean isAlly(Player p1, Player p2) {
        return GeneralUtil.isAlly((Player)p1, (Player)p2);
    }
}

