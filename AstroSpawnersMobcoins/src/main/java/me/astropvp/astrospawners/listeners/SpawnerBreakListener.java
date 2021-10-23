package me.astropvp.astrospawners.listeners;

import com.compilex.CosmicCustomCreepers.enums.CreeperTypes;
import com.cosmicpvp.cosmicutils.utils.CC;
import me.astropvp.astrospawners.data.SpawnerData;
import me.astropvp.astrospawners.data.SpawnerManager;
import me.astropvp.astrospawners.items.ItemSpawner;
import me.astropvp.astrospawners.utils.EntityUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;


public class SpawnerBreakListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onExplode(EntityExplodeEvent event) {
        double chance = event.getEntity() instanceof TNTPrimed ? 0.15 : event.getEntity() instanceof Creeper ? 0.50 : 0;
        if (chance <= 0) {
            return;
        }
        if (!(event.getEntity() instanceof Creeper) && !(event.getEntity() instanceof TNTPrimed)) {
            return;
        }

        if (event.getEntity() instanceof Creeper) {

            Creeper c = (Creeper)event.getEntity();

            if (c.hasMetadata("customCreeperType")) {

                CreeperTypes egg_type = CreeperTypes.valueOf(c.getMetadata("customCreeperType").get(0).asString());

                if (egg_type == CreeperTypes.Lucky) {
                    chance += 1.25;
                }
            }
        }
        List<Block> blocks = event.blockList();
        for (int i = blocks.size() - 1; i >= 0; i--) {
            Block block = blocks.get(i);
            if (block.getType() == Material.MOB_SPAWNER) {
                SpawnerData data = SpawnerManager.findSpawnerData(block.getLocation());
                if (data != null) {
                    EntityType type = data.getEntityType();
                    int amount = data.getSpawnerAmount();
                    World world = block.getWorld();
                    Location location = block.getLocation();
                    int spawnerAmount = 0;
                    for(int pos = 0; pos < amount; pos++) {
                        if(chance > Math.random()) {
                            spawnerAmount++;
                        }
                    }
                    if (spawnerAmount > 0) {
                        world.dropItem(location, ItemSpawner.getItem(type, spawnerAmount));
                    }
                    data.setSpawnerAmount(0);
                    data.setNullSpawner(true);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onMine(BlockBreakEvent event) {
        if (event.getBlock().getType() == Material.MOB_SPAWNER) {
            ItemStack item = event.getPlayer().getItemInHand();
            Block block = event.getBlock();
            SpawnerData data = SpawnerManager.findSpawnerData(block.getLocation());
            if (data != null) {
                EntityType type = data.getEntityType();
                int amount = data.getSpawnerAmount();
                data.setSpawnerAmount(0);
                data.setNullSpawner(true);
                block.getWorld().dropItem(block.getLocation(), ItemSpawner.getItem(type, amount));
                event.getPlayer().sendMessage(CC.translate("&a&l(!)&a You have mined a &f" + EntityUtils.getEntityName(type) + "&a Spawner!"));
                event.getPlayer().sendMessage(CC.translate("&7The spawner has been dropped on the ground!"));
                SpawnerManager.removeSpawnerData(data);
                block.setType(Material.AIR);
                event.setCancelled(true);
            }
        }
    }
}
