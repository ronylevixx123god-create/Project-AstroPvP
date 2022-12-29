package net.astropvp.customblocks.data;

import com.cosmicpvp.cosmicutils.utils.LocationUtils;
import net.astropvp.customblocks.CustomBlocks;
import net.astropvp.customblocks.struct.CustomBlock;
import net.astropvp.customblocks.struct.CustomBlockType;
import net.astropvp.storage.StoragePlugin;
import net.astropvp.storage.utils.SaveLoadManager;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;

public class CustomBlockManager extends SaveLoadManager<CustomBlockData> {


    public CustomBlockManager(CustomBlocks plugin) {
        super(plugin, StoragePlugin.getInstance().getGson(), "/data", CustomBlockData.class);
    }

    public CustomBlockData getCustomBlockData(Location location) {
        return getFromCache(LocationUtils.getStringFromLocation(location));
    }


    /*public static boolean canPlaceStackedSpawnerInThisChunk(Chunk chunk, EntityType spawnertype) {
        for (BlockState blockState : chunk.getTileEntities()) {
            if (blockState instanceof CreatureSpawner) {
                EntityType type = ((CreatureSpawner) blockState).getSpawnedType();
                if (type == spawnertype) {
                    return false;
                }
            }
        }
        return true;
    }*/

}

