/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.scheduler.BukkitRunnable
 */
package org.arkhamnetwork.Arkkit.patches;

import org.arkhamnetwork.Arkkit.patches.AntiSpamBot;
import org.arkhamnetwork.Arkkit.patches.AntiSpamBotListener;
import org.bukkit.scheduler.BukkitRunnable;

final class AntiSpamBotChatTask
extends BukkitRunnable {
    AntiSpamBotChatTask() {
    }

    public void run() {
        AntiSpamBot.messageUses.clear();
        AntiSpamBotListener.IPMessageCount.clear();
    }
}

