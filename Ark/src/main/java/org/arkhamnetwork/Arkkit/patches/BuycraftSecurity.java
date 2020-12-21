/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class BuycraftSecurity
extends ArkPlugin {
    public BuycraftSecurity(Plugin p) {
        super(p);
    }

    @Override
    public void enable() {
        Path path = Paths.get("plugins/Buycraft/settings.conf", new String[0]);
        if (!path.toFile().exists()) {
            Bukkit.getLogger().info("[Arkkit (BuycraftSecurity)] No Buycraft settings.conf found, disabling...");
            this.kill();
            return;
        }
        Charset charset = StandardCharsets.UTF_8;
        try {
            String content = new String(Files.readAllBytes(path), charset);
            if (content.contains("autoUpdate=true")) {
                Bukkit.getLogger().info("[Arkkit (BuycraftSecurity)] Disabling Buycraft's autoUpdate feature. Requires reboot to take effect.");
                content = content.replace("autoUpdate=true", "autoUpdate=false");
            }
            Files.write(path, content.getBytes(charset), new OpenOption[0]);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void disable() {
    }
}

