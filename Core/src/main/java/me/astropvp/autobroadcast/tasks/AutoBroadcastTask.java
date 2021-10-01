/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  org.bukkit.Bukkit
 */
package me.astropvp.autobroadcast.tasks;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.google.common.collect.Lists;
import me.astropvp.autobroadcast.struct.BroadcastMessages;
import org.arkhamnetwork.Arkkit.patches.chat_filter.ChatUtils;
import org.bukkit.Bukkit;
import sun.misc.MessageUtils;

import java.util.List;

public class AutoBroadcastTask
implements Runnable {
    private final List<String> messagesList = Lists.newArrayList();
    int i;

    public AutoBroadcastTask() {
        for (BroadcastMessages messages : BroadcastMessages.values()) {
            this.messagesList.add(messages.getMessage());
            int n = this.messagesList.size() - 1;
        }
    }

    @Override
    public void run() {
        Bukkit.broadcastMessage(CC.translate(this.messagesList.get(this.i)));
        this.i = this.i == this.messagesList.size() - 1 ? 0 : ++this.i;
    }
}

