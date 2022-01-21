/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  org.bukkit.Bukkit
 */
package net.astropvp.core.autobroadcast.tasks;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.google.common.collect.Lists;
import net.astropvp.core.autobroadcast.struct.BroadcastMessages;
import org.bukkit.Bukkit;

import java.util.List;

public class AutoBroadcastTask
implements Runnable {
    private final List<String> messagesList = Lists.newArrayList();
    int i;

    public AutoBroadcastTask() {
        for (BroadcastMessages messages : BroadcastMessages.values()) {
            this.messagesList.add(messages.getMessage());
        }
    }

    @Override
    public void run() {
        Bukkit.broadcastMessage(CC.translate("&7&m----&f&m----&6&m----&e&l[ &6&lAstro&f&lPvP &e&l]&6&m----&f&m----&7&m----"));
        Bukkit.broadcastMessage(CC.translate(this.messagesList.get(this.i)));
        Bukkit.broadcastMessage(CC.translate("&7&m----&f&m----&6&m----&e&m-----------&6&m----&f&m----&7&m----"));
        this.i = this.i == this.messagesList.size() - 1 ? 0 : ++this.i;
    }
}

