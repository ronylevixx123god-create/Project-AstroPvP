/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;

public class AntiSpamBot
        extends ArkPlugin {
    protected static HashMap<String, Integer> messageUses;
    protected static boolean vipOnly;
    protected static volatile HashMap<String, Integer> UUIDLoginCount;
    protected static volatile HashMap<String, Integer> IPLoginCount;

    static {
        vipOnly = false;
    }

    public AntiSpamBot(Plugin p) {
        super(p);
    }

    @Override
    public void enable() {
        int clearTicks = Arkkit.get().getConfig().getInt("settings.anti_spam_bot.messageUsesClearTicks");
        this.registerListener(new AntiSpamBotListener());
        this.registerTask(new AntiSpamBotChatTask().runTaskTimer(Arkkit.get(), clearTicks, clearTicks), AntiSpamBotChatTask.class);
        this.registerTask(new AntiSpamBotLoginTask().runTaskTimer(Arkkit.get(), 1200L, 1200L), AntiSpamBotLoginTask.class);
        this.registerCommand(new VipOnlyCommand("viponly", "/<command>", "Toggles VIP only public chat."));
    }

    @Override
    public void disable() {
    }
}

