/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.scheduler.BukkitRunnable
 */
package org.arkhamnetwork.Arkkit.patches;

import org.arkhamnetwork.Arkkit.patches.AntiSpamBot;
import org.bukkit.scheduler.BukkitRunnable;

final class AntiSpamBotLoginTask
extends BukkitRunnable {
    AntiSpamBotLoginTask() {
    }

    public void run() {
        AntiSpamBot.UUIDLoginCount.clear();
        AntiSpamBot.IPLoginCount.clear();
    }
}

