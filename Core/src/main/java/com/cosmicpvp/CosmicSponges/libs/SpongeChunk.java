/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.io.netty.util.internal.ConcurrentSet
 *  org.bukkit.Bukkit
 *  org.bukkit.Chunk
 *  org.bukkit.Material
 *  org.bukkit.block.Block
 */
package com.cosmicpvp.CosmicSponges.libs;

import com.cosmicpvp.CosmicSponges.CosmicSpongesAPI;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import io.netty.util.internal.ConcurrentSet;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class SpongeChunk {
    private String fileName;
    private String worldName;
    private int chunkX;
    private int chunkZ;
    private volatile ConcurrentSet<AbstractSpongeBlock> sponges;
    private ConcurrentSet<String> noFlow;
    private boolean forceSave = false;

    public SpongeChunk(Chunk c) {
        this.chunkX = c.getX();
        this.chunkZ = c.getZ();
        this.worldName = c.getWorld().getName();
        this.fileName = this.chunkX + "." + this.chunkZ + ".sponge";
        this.noFlow = new ConcurrentSet();
        this.sponges = new ConcurrentSet();
    }

    public boolean canLiquidFlow(Block liquid) {
        return !this.noFlow.contains((Object)(liquid.getX() + "," + liquid.getY() + "," + liquid.getZ()));
    }

    private File getFile() {
        return new File("plugins/CosmicSponges/spongeChunks/" + this.worldName + "/" + this.fileName);
    }

    public void load() {
        if (this.getFile().exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(this.getFile()));){
                String line;
                while ((line = br.readLine()) != null) {
                    String[] s = line.split(",");
                    this.sponges.add(new AbstractSpongeBlock(this.worldName, Integer.parseInt(s[0]), Integer.parseInt(s[1]), Integer.parseInt(s[2]), this));
                }
                if (this.sponges.size() > 0 && CosmicSpongesAPI.debug) {
                    Bukkit.getLogger().info("[CosmicSponges] Loaded " + this.sponges.size() + " sponges in " + this.fileName);
                }
            }
            catch (Exception err) {
                err.printStackTrace();
                Bukkit.getLogger().info("[CosmicSponges] Failed to load() " + this.getFileName() + ", running scan+clean!");
                this.scan();
                this.clean();
            }
        }
        this.clean();
    }

    public void clean() {
        for (AbstractSpongeBlock a : this.sponges) {
            if (a.getBlock().getType() == Material.SPONGE) continue;
            Bukkit.getLogger().info("[CosmicSponges] Cleaned null sponge at " + a.getBlockX() + ", " + a.getBlockY() + ", " + a.getBlockZ());
            a.removeEffectedCoords();
            this.sponges.remove((Object)a);
            this.forceSave = true;
        }
    }

    @Deprecated
    public void scan() {
        Chunk c = Bukkit.getWorld((String)this.worldName).getChunkAt(this.chunkX, this.chunkZ);
        for (int x = 0; x < 16; ++x) {
            for (int z = 0; z < 16; ++z) {
                for (int y = 0; y < 256; ++y) {
                    Block b = c.getBlock(x, y, z);
                    if (b.getType() != Material.SPONGE) continue;
                    this.sponges.add(new AbstractSpongeBlock(b, this));
                }
            }
        }
        if (this.sponges.size() > 0 && CosmicSpongesAPI.debug) {
            Bukkit.getLogger().info("[CosmicSponges] Scanned " + this.fileName + ", located " + this.sponges.size() + " sponges!");
        }
    }

    public void save() {
        ArrayList<String> lines = new ArrayList<String>();
        for (AbstractSpongeBlock sponge : this.sponges) {
            lines.add(sponge.getBlockX() + "," + sponge.getBlockY() + "," + sponge.getBlockZ());
        }
        Path file = Paths.get(this.getFile().toURI());
        try {
            if (lines.size() > 0) {
                Files.write(file, lines, Charset.forName("UTF-8"), new OpenOption[0]);
            } else if (Files.exists(file, new LinkOption[0])) {
                Files.delete(file);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        if (lines.size() > 0 && CosmicSpongesAPI.debug) {
            Bukkit.getLogger().info("[CosmicSponges] Saved " + this.sponges.size() + " sponges to " + this.fileName);
        }
    }

    public String getFileName() {
        return this.fileName;
    }

    public String getWorldName() {
        return this.worldName;
    }

    public int getChunkX() {
        return this.chunkX;
    }

    public int getChunkZ() {
        return this.chunkZ;
    }

    public ConcurrentSet<AbstractSpongeBlock> getSponges() {
        return this.sponges;
    }

    public ConcurrentSet<String> getNoFlow() {
        return this.noFlow;
    }

    public void setForceSave(boolean forceSave) {
        this.forceSave = forceSave;
    }

    public boolean isForceSave() {
        return this.forceSave;
    }
}

