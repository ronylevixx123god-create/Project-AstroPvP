/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  org.bukkit.Bukkit
 */
package me.matthawkk.tasks;

import com.google.common.collect.Lists;
import java.util.List;
import me.matthawkk.struct.BroadcastMessages;
import org.bukkit.Bukkit;

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
        Bukkit.broadcastMessage(this.messagesList.get(this.i));
        this.i = this.i == this.messagesList.size() - 1 ? 0 : ++this.i;
    }
}

