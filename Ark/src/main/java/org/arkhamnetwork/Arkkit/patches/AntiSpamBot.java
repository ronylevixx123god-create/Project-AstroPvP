/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import java.util.HashMap;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.patches.AntiSpamBotChatTask;
import org.arkhamnetwork.Arkkit.patches.AntiSpamBotListener;
import org.arkhamnetwork.Arkkit.patches.AntiSpamBotLoginTask;
import org.arkhamnetwork.Arkkit.patches.VipOnlyCommand;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.bukkit.plugin.Plugin;

public class AntiSpamBot
extends ArkPlugin {
    protected static HashMap<String, Integer> messageUses;
    protected static boolean vipOnly;
    protected static volatile HashMap<String, Integer> UUIDLoginCount;
    protected static volatile HashMap<String, Integer> IPLoginCount;

    public AntiSpamBot(Plugin p) {
        super(p);
    }

    @Override
    public void enable() {
        int clearTicks = Arkkit.get().getConfig().getInt("settings.anti_spam_bot.messageUsesClearTicks");
        this.registerListener(new AntiSpamBotListener());
        this.registerTask(new AntiSpamBotChatTask().runTaskTimer((Plugin)Arkkit.get(), clearTicks, clearTicks), AntiSpamBotChatTask.class);
        this.registerTask(new AntiSpamBotLoginTask().runTaskTimer((Plugin)Arkkit.get(), 1200L, 1200L), AntiSpamBotLoginTask.class);
        this.registerCommand(new VipOnlyCommand("viponly", "/<command>", "Toggles VIP only public chat."));
    }

    @Override
    public void disable() {
    }

    static {
        vipOnly = false;
    }
}

