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

import java.util.ArrayList;
import java.util.Arrays;

public class Ypperin
        extends ArkPlugin {
    public Ypperin(Plugin p) {
        super(p);
    }

    @Override
    public void enable() {
        this.registerCommand(new CommandOEnderchest("oenderchest", "/<command> [args]", "View an offline player's enderchest."));
        this.registerCommand(new CommandXptop("xptop", "/<command> [args]", "Scan all offline players for their XP levels."));
        this.registerCommand(new CommandOXp("oxp", "/<command> [args]", "Gets XP level of offline player."));
        this.registerCommand(new CommandVUnban("vunban", "/<command> [args]", "Remove player from bukkit ban list."));
        this.registerCommand(new CommandChunkLoad("chunkload", "/<command> [args]", "Toggles entity chunk loading."));
        this.registerCommand(new CommandCWarzone("cwarzone", "/<command> [args]", "A better way to claim warzone chunks."));
        this.registerCommand(new CommandWipeWhitelist("wipewhitelist", "/<command> [args]", "Clear local whitelist of all players."));
        this.registerCommand(new CommandWipeOps("wipeops", "/<command> [args]", "Clear local ops list of all players."));
        this.registerCommand(new CommandDisableClaims("disableclaim", "/<command> [args]", "Toggle ability for players to claim chunks.", new ArrayList<String>(Arrays.asList("disableclaims", "toggleclaims"))));
        this.registerCommand(new CommandToggleLogic("togglelogic", "/<command> [args]", "Toggles all BlockPhysicsEvents."));
        this.registerListener(new DisableClaimsListener());
        this.registerTask(new VanishAuditTask().runTaskTimer(Arkkit.get(), 20L, 20L), VanishAuditTask.class);
    }

    @Override
    public void disable() {
    }
}

