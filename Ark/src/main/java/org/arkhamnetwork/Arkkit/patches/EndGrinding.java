/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.WorldUtil
 *  org.bukkit.Bukkit
 *  org.bukkit.Location
 *  org.bukkit.World
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import com.cosmicpvp.cosmicutils.utils.WorldUtil;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.arkhamnetwork.Arkkit.utils.LocationUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;

public class EndGrinding
        extends ArkPlugin {
    public static boolean hcf;
    public static boolean fundEnabled;
    protected static HashMap<Location, EndMonsterType> mobSpawnpoints;

    static {
        hcf = false;
        fundEnabled = false;
    }

    public EndGrinding(Plugin p) {
        super(p);
    }

    protected static void setEndPortalBlocks(int blockId) {
        World w = Bukkit.getWorld("world_end");
        if (w != null) {
            WorldUtil.allowEnderPortalOutsideOverworld(true);
            w.getBlockAt(55, 42, 229).setTypeIdAndData(blockId, (byte) 0, false);
            w.getBlockAt(55, 42, 228).setTypeIdAndData(blockId, (byte) 0, false);
            w.getBlockAt(55, 42, 227).setTypeIdAndData(blockId, (byte) 0, false);
            w.getBlockAt(54, 42, 230).setTypeIdAndData(blockId, (byte) 0, false);
            w.getBlockAt(54, 42, 229).setTypeIdAndData(blockId, (byte) 0, false);
            w.getBlockAt(54, 42, 228).setTypeIdAndData(blockId, (byte) 0, false);
            w.getBlockAt(54, 42, 227).setTypeIdAndData(blockId, (byte) 0, false);
            w.getBlockAt(54, 42, 226).setTypeIdAndData(blockId, (byte) 0, false);
            w.getBlockAt(53, 42, 230).setTypeIdAndData(blockId, (byte) 0, false);
            w.getBlockAt(53, 42, 229).setTypeIdAndData(blockId, (byte) 0, false);
            w.getBlockAt(53, 42, 228).setTypeIdAndData(blockId, (byte) 0, false);
            w.getBlockAt(53, 42, 227).setTypeIdAndData(blockId, (byte) 0, false);
            w.getBlockAt(53, 42, 226).setTypeIdAndData(blockId, (byte) 0, false);
            w.getBlockAt(52, 42, 230).setTypeIdAndData(blockId, (byte) 0, false);
            w.getBlockAt(52, 42, 229).setTypeIdAndData(blockId, (byte) 0, false);
            w.getBlockAt(52, 42, 228).setTypeIdAndData(blockId, (byte) 0, false);
            w.getBlockAt(52, 42, 227).setTypeIdAndData(blockId, (byte) 0, false);
            w.getBlockAt(52, 42, 226).setTypeIdAndData(blockId, (byte) 0, false);
            w.getBlockAt(51, 42, 229).setTypeIdAndData(blockId, (byte) 0, false);
            w.getBlockAt(51, 42, 228).setTypeIdAndData(blockId, (byte) 0, false);
            w.getBlockAt(51, 42, 227).setTypeIdAndData(blockId, (byte) 0, false);
            WorldUtil.allowEnderPortalOutsideOverworld(false);
        }
    }

    @Override
    public void enable() {
        this.loadConfig();
        this.registerCommand(new CommandAdminEnd("adminend", "/<command>", "Setup end dimension variables.", Arrays.asList("aend")));
        this.registerListener(new EndGrindingListener());
        this.registerListener(new EndTeleportListener());
        this.registerTask(new EndGrindingMobSpawnTask().runTaskTimer(Arkkit.get(), 100L, 100L), EndGrindingMobSpawnTask.class);
        hcf = new File("plugins/HCFactions.jar").exists();
        fundEnabled = new File("plugins/CosmicFund.jar").exists();
    }

    @Override
    public void disable() {
        this.unregisterCommands();
        this.unregisterListeners();
        this.unregisterTasks();
    }

    private void loadConfig() {
        mobSpawnpoints = new HashMap();
        for (EndMonsterType type : EndMonsterType.values()) {
            for (String s : Arkkit.get().getConfig().getStringList("settings.end_grinding.mobSpawns." + type.toString())) {
                mobSpawnpoints.put(LocationUtils.convertStringToLocation(s), type);
                Bukkit.getLogger().info("[EndGrinding] Registered " + s + " as a " + type.toString() + " spawnpoint!");
            }
        }
    }
}

