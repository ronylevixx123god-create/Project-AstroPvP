package net.astropvp.core.utils;

import java.util.List;

import com.cosmicpvp.cosmicutils.utils.CC;
import net.astropvp.core.AstroCore;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

import com.google.common.collect.Lists;


public class EntityUtils {

    public static void clearAllEntities() {
        for(World world : Bukkit.getWorlds()) {
            for(Entity entity : world.getEntities()) {
                if(entity.hasMetadata("do_not_clear") || entity.hasMetadata("antiLoot_uuid") || entity.hasMetadata("antiLoot_time")) {
                    continue;
                }
                if(!(entity instanceof Player)) {
                    entity.remove();
                }
            }
        }
    }
}
