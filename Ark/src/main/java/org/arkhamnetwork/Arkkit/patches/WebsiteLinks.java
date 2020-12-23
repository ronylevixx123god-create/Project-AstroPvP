package org.arkhamnetwork.Arkkit.patches;

import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.Collections;

public class WebsiteLinks
        extends ArkPlugin {
    public WebsiteLinks(Plugin p) {
        super(p);
    }

    @Override
    public void enable() {
        this.registerCommand(new CommandReport("report", "/<command>", "Displays link to player report form.", new ArrayList<>(Collections.singletonList("reportplayer"))));
    }

    @Override
    public void disable() {
    }
}

