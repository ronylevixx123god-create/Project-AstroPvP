package me.astropvp.utils;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import me.astropvp.struct.GenBucketTask;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;

public class GenBucketUtils {
    public static boolean isGenBucketsRunning(Chunk chunk) {
        Set<UUID> playersUsingGenBuckets = GenBucketTask.getTasks().keySet();
        if (playersUsingGenBuckets != null) {
            Iterator var2 = playersUsingGenBuckets.iterator();
            if (var2.hasNext()) {
                UUID uuid = (UUID)var2.next();
                Player p = Bukkit.getPlayer(uuid);
                List<GenBucketTask> genTasks = GenBucketTask.getTasks(p);
                if (genTasks != null) {
                    Iterator var6 = genTasks.iterator();

                    while(var6.hasNext()) {
                        GenBucketTask genBucketTask = (GenBucketTask)var6.next();
                        if (chunk.equals(genBucketTask.getCurrentLocation().getChunk())) {
                            return true;
                        }
                    }
                }

                return false;
            }
        }

        return false;
    }
}
