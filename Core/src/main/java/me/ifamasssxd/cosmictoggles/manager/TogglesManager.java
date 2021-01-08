/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  org.bukkit.configuration.file.FileConfiguration
 *  org.bukkit.configuration.file.YamlConfiguration
 *  org.bukkit.entity.Player
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.scoreboard.DisplaySlot
 *  org.bukkit.scoreboard.Objective
 *  org.bukkit.scoreboard.Scoreboard
 */
package me.ifamasssxd.cosmictoggles.manager;

import com.google.common.collect.Lists;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import net.astropvp.core.AstroCore;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class TogglesManager {
    private File toggleFile;
    private FileConfiguration config;
    private final List<UUID> scoreboardToggles = new ArrayList<UUID>();

    public void loadToggles() {
        try {
            this.toggleFile = new File(AstroCore.getInstance().getDataFolder(), "toggles.yml");
            if (!this.toggleFile.exists()) {
                this.toggleFile.createNewFile();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        this.config = YamlConfiguration.loadConfiguration(this.toggleFile);
        this.scoreboardToggles.addAll(this.config.getStringList("scoreboard-toggles").stream().map(UUID::fromString).collect(Collectors.toList()));
    }

    public void clearScoreboard(Player player) {
        player.setMetadata("toggle_scoreboard", new FixedMetadataValue(AstroCore.getInstance(), ""));
        if (player.getScoreboard() != null) {
            Scoreboard sb = player.getScoreboard();
            if (sb.getObjective("Factions") != null) {
                sb.getObjective("Factions").unregister();
            } else {
                Objective obj = sb.registerNewObjective("FactionsRemove", "dummy");
                obj.setDisplaySlot(DisplaySlot.SIDEBAR);
                obj.unregister();
            }
        }
    }

    public void saveToggles() {
        if (!this.toggleFile.exists()) {
            try {
                this.toggleFile.createNewFile();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        ArrayList list = Lists.newArrayList();
        list.addAll(this.scoreboardToggles.stream().map(UUID::toString).collect(Collectors.toList()));
        this.config.set("scoreboard-toggles", list);
        try {
            this.config.save(this.toggleFile);
        }
        catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    public List<UUID> getScoreboardToggles() {
        return this.scoreboardToggles;
    }
}

