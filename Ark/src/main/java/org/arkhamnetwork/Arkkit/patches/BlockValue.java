/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  com.massivecraft.factions.Board
 *  com.massivecraft.factions.FLocation
 *  org.bukkit.Bukkit
 *  org.bukkit.Chunk
 *  org.bukkit.Material
 *  org.bukkit.World
 *  org.bukkit.block.Block
 *  org.bukkit.block.BlockState
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.libs.BlockValueLocation;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.plugin.Plugin;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class BlockValue
        extends ArkPlugin {
    public static HashMap<String, BlockValueLocation> trackedBlockValues;
    protected static HashSet<Material> blocksWithValue;
    private static Thread blockValueSaveThread;
    private static boolean blocksSaved;

    static {
        blocksWithValue = new HashSet<Material>(Arrays.asList(Material.HOPPER, Material.BEACON));
    }

    public BlockValue(Plugin p) {
        super(p);
        blocksSaved = false;
    }

    protected static void saveBlockValues() {
        try {
            new File("blockValues.dat").delete();
            BufferedWriter out = new BufferedWriter(new FileWriter("blockValues.dat"));
            for (BlockValueLocation sl : trackedBlockValues.values()) {
                if (sl.getBlockType() == Material.AIR) continue;
                try {
                    out.write(sl.string() + "\n");
                } catch (Exception err) {
                    if (sl != null) {
                        Bukkit.getLogger().info("[BlockValue] Failed to parse BlockValueLocation at " + sl.getX() + "," + sl.getY() + "," + sl.getZ());
                    }
                    err.printStackTrace();
                }
            }
            out.close();
            Bukkit.getLogger().info("[Arkkit (BlockValue)] Saved " + trackedBlockValues.size() + " blockValue locations to disk.");
        } catch (Exception err) {
            err.printStackTrace();
        }
    }

    public static boolean isValueBlock(Block b) {
        try {
            return trackedBlockValues.containsKey(b.getWorld().getName() + "," + b.getX() + "," + b.getY() + "," + b.getZ());
        } catch (Exception err) {
            return false;
        }
    }

    public static int getBlockValueBase(Material m) {
        return Arkkit.get().getConfig().getInt("settings.blockValue." + m.name());
    }

    @Override
    public void enable() {
        blockValueSaveThread = new Thread() {

            @Override
            public void run() {
                BlockValue.saveBlockValues();
                blocksSaved = true;
            }
        };
        blockValueSaveThread.setName("BlockValueAsyncSaveThread");
        this.loadBlockValues();
        this.registerListener(new BlockValueListener());
        this.registerCommand(new CommandBlockValue("blockvalue", "/<command>", "Define block values."));
        this.scanLoadedChunks();
    }

    @Override
    public synchronized void disable() {
        blockValueSaveThread.start();
        while (!blocksSaved) {
            try {
                Bukkit.getLogger().info("Waiting for BlockValueSaveThread to complete saving " + trackedBlockValues.size() + " spawners to proceed...");
                Thread.sleep(500L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        blockValueSaveThread.interrupt();
    }

    private void loadBlockValues() {
        trackedBlockValues = new HashMap();
        int count = 0;
        if (!new File("blockValues.dat").exists()) {
            try {
                new File("blockValues.dat").createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File("blockValues.dat")));
            String line = "";
            while ((line = reader.readLine()) != null) {
                if (line.length() <= 0) continue;
                try {
                    String[] data = line.split(",");
                    Material blockType = Material.AIR;
                    long placeTime = 0L;
                    if (data.length != 6) {
                        Bukkit.getLogger().info("[Arkkit (BlockValue)] Failed to parse blockValue: " + line);
                        continue;
                    }
                    blockType = Material.valueOf(data[4]);
                    placeTime = Long.parseLong(data[5]);
                    if (blockType == Material.AIR) continue;
                    trackedBlockValues.put(data[0] + "," + data[1] + "," + data[2] + "," + data[3], new BlockValueLocation(Integer.valueOf(data[1]), Integer.valueOf(data[2]), Integer.valueOf(data[3]), data[0], blockType, placeTime));
                    ++count;
                } catch (NullPointerException npe) {
                    npe.printStackTrace();
                }
            }
            reader.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        Bukkit.getLogger().info("[Arkkit (BlockValue)] Loaded " + count + " blockValue locations from disk.");
    }

    private void scanLoadedChunks() {
        for (World w : Bukkit.getWorlds()) {
            for (Chunk c : w.getLoadedChunks()) {
                String cs = c.getX() + "," + c.getZ();
                FLocation fl = new FLocation(c.getBlock(0, 0, 0));
                if (!Board.getFactionAt(fl).isNormal()) continue;
                int blocksFound = 0;
                for (BlockState blockState : c.getTileEntities()) {
                    String blockString;
                    if (!blocksWithValue.contains(blockState.getBlock().getType()) || trackedBlockValues.containsKey(blockString = BlockValueListener.convertBlockToString(blockState.getBlock())))
                        continue;
                    trackedBlockValues.put(blockString, BlockValueListener.convertBlockToBlockValueLocation(blockState.getBlock()));
                    ++blocksFound;
                }
                if (blocksFound <= 0) continue;
                Bukkit.getLogger().info("[Arkkit (BlockValue)] Tracked " + blocksFound + " new valueBlocks in chunk: " + cs);
            }
        }
    }
}

