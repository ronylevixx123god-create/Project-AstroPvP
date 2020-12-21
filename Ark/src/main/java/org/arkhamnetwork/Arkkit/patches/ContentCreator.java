/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  lilypad.client.connect.api.Connect
 *  org.bukkit.Bukkit
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import com.google.common.collect.Lists;
import java.util.UUID;
import lilypad.client.connect.api.Connect;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.patches.CommandSetChannel;
import org.arkhamnetwork.Arkkit.patches.ContentCreatorListener;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class ContentCreator
extends ArkPlugin {
    public static final String metadata_contentCreatorName = "contentCreatorName";
    public static final String metadata_contentCreatorURL = "contentCreatorURL";
    protected static String contentCreator_lilypadChannel = "contentCreator";
    protected static Connect lilypadConnect;
    public ContentCreatorListener contentCreatorListener;

    public ContentCreator(Plugin p) {
        super(p);
    }

    @Override
    public void enable() {
        super.enable();
        this.registerCommand(new CommandSetChannel("setchannel", "/<command>", "Set content creator channel name and link.", Lists.newArrayList((Object[])new String[]{"setyoutubechannel", "settwitchchannel"})));
        this.contentCreatorListener = new ContentCreatorListener();
        lilypadConnect = (Connect)Bukkit.getServer().getServicesManager().getRegistration(Connect.class).getProvider();
        this.registerListener(this.contentCreatorListener);
        lilypadConnect.registerEvents((Object)this.contentCreatorListener);
    }

    @Override
    public void disable() {
        super.disable();
    }

    public static String getChannelName(UUID playerUUID) {
        return Arkkit.get().getConfig().getString("settings.contentCreator." + playerUUID.toString() + ".channelName", null);
    }

    public static String getChannelURL(UUID playerUUID) {
        return Arkkit.get().getConfig().getString("settings.contentCreator." + playerUUID.toString() + ".channelURL", null);
    }
}

