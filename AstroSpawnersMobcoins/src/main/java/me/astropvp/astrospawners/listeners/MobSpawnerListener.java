package me.astropvp.astrospawners.listeners;

import com.cosmicpvp.cosmicutils.utils.CC;
import me.astropvp.astrospawners.AstroSpawners;
import me.astropvp.astrospawners.data.SpawnerData;
import me.astropvp.astrospawners.data.SpawnerDataHelper;
import me.astropvp.astrospawners.items.ItemSpawner;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import me.astropvp.astrospawners.utils.EntityUtils;

public class MobSpawnerListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlace(BlockPlaceEvent event) {
        if (event.getBlockPlaced().getType() != Material.MOB_SPAWNER) {
            return;
        }

        boolean allowSpawners = AstroSpawners.getInstance().getConfig().getBoolean("allow-spawner-placement");

        if (!allowSpawners) {
            event.getPlayer().sendMessage(CC.translate("&c&l(!)&c Spawner placement has been disabled!"));
            event.getPlayer().sendMessage(CC.translate("&7You can no longer place FTOP value."));
            event.setCancelled(true);
            return;
        }

        ItemStack item = event.getPlayer().getItemInHand();
        if (item.getAmount() > 1) {
            event.getPlayer().sendMessage(CC.translate("&c&l(!)&c You cannot place stacked spawners!"));
            event.getPlayer().sendMessage(CC.translate("&7Combine them by drag and dropping them together"));
            event.setCancelled(true);
            return;
        }


        EntityType type = ItemSpawner.getEntityTypeFromItem(item);

       if (!SpawnerDataHelper.canPlaceStackedSpawnerInThisChunk(event.getBlockAgainst().getChunk(), type)) {
           event.getPlayer().sendMessage(CC.translate("&c&l(!)&c This chunk already contains this spawner type."));
           event.getPlayer().sendMessage(CC.translate("&cPlease stack it on the existing spawner."));
           event.setCancelled(true);
           return;
       }

        if (type == null) {
            event.setCancelled(true);
            return;
        }
        int amount = ItemSpawner.getSpawnerAmount(item);
        if (amount <= 0) {
            event.setCancelled(true);
            return;
        }
        int math = amount >= ItemSpawner.getSpawnerLimit() ? ItemSpawner.getSpawnerLimit() : amount;
        int spawnersLeft = amount - math;
        event.getPlayer().sendMessage(CC.translate("&c&l- " + amount + " &c&l" + EntityUtils.getEntityName(type) + "&c&l Spawners"));
        event.getPlayer().sendMessage(CC.translate("&7There are now " + spawnersLeft + "&7 spawners left in your hand."));


        //THIS USED TO BE ASYNC
        Bukkit.getScheduler().runTask(AstroSpawners.getInstance(),
                (Runnable) new BukkitRunnable() {
                    @Override
                    public void run() {
                        SpawnerData data = new SpawnerData(event.getBlock().getLocation(), type, math);
                        new SpawnerDataHelper(data, false);
                    }
                });
        event.getPlayer().setItemInHand(spawnersLeft <= 0 ? null : ItemSpawner.getItem(type, spawnersLeft));
        Location location = event.getBlockPlaced().getLocation();
        Bukkit.getScheduler().runTaskLater(AstroSpawners.getInstance(), (Runnable) new BukkitRunnable() {
            @Override
            public void run() {
                location.getBlock().setType(Material.MOB_SPAWNER);
                ((CreatureSpawner)location.getBlock().getState()).setSpawnedType(type);
            }
        }, 1);
        //CHANGED
        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onSpawnerPlace(BlockPlaceEvent event) {
        Block block = event.getBlock();
        if (block.getType() == Material.MOB_SPAWNER) {
            if (event.getBlockAgainst().getType() == Material.MOB_SPAWNER) {
                EntityType type = ((CreatureSpawner) event.getBlockAgainst().getState()).getSpawnedType();
                if (ItemSpawner.getEntityTypeFromItem(event.getPlayer().getItemInHand()) == type) {
                    int amount = ItemSpawner.getSpawnerAmount(event.getPlayer().getItemInHand());
                    if (amount <= 0) {
                        event.setCancelled(true);
                        return;
                    }
                    //CHANGED
                    event.setCancelled(true);
                    Player player = event.getPlayer();
                    Bukkit.getScheduler().runTaskAsynchronously(AstroSpawners.getInstance(),
                            (Runnable) new BukkitRunnable() {
                                @Override
                                public void run() {
                                    SpawnerData data = new SpawnerData(event.getBlockAgainst().getLocation(), type, 1);
                                    SpawnerData found = SpawnerDataHelper.findSpawnerData(data);
                                    if (found != null) {
                                        if (found.getSpawnerAmount() >= ItemSpawner.getSpawnerLimit()) {
                                            player.sendMessage(CC.RedB + "(!) " + CC.Red + "You can not have more than "
                                                    + ItemSpawner.getSpawnerLimit() + " spawners stacked together!");
                                            event.setCancelled(true);
                                            return;
                                        }

                                        boolean allowSpawners = AstroSpawners.getInstance().getConfig().getBoolean("allow-spawner-placement");


                                        if (!allowSpawners) {
                                            event.getPlayer().sendMessage(CC.translate("&c&l(!)&c Spawner placement has been disabled!"));
                                            event.getPlayer().sendMessage(CC.translate("&7You can no longer place FTOP value."));
                                            event.setCancelled(true);
                                            return;
                                        }

                                        int math = found.getSpawnerAmount() + amount >= ItemSpawner.getSpawnerLimit()
                                                ? ItemSpawner.getSpawnerLimit() - found.getSpawnerAmount()
                                                : amount;
                                        int spawnersLeft = amount - math;
                                        found.setSpawnerAmount(found.getSpawnerAmount() + math);
                                        if (found.isNullSpawner()) {
                                            found.setNullSpawner(false);
                                        }
                                        player.setItemInHand(
                                                spawnersLeft <= 0 ? null : ItemSpawner.getItem(type, spawnersLeft));
                                    } else {
                                        int math = amount >= ItemSpawner.getSpawnerLimit()
                                                ? ItemSpawner.getSpawnerLimit()
                                                : amount;
                                        int spawnersLeft = amount - math;
                                        data.setSpawnerAmount(math);

                                        player.setItemInHand(
                                                spawnersLeft <= 0 ? null : ItemSpawner.getItem(type, spawnersLeft));
                                        new SpawnerDataHelper(data, false);
                                    }
                                    player.updateInventory();
                                }
                            });
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onSpawnerInteract(PlayerInteractEvent event) {
        if (event.getItem() != null) {
            if (event.getClickedBlock() != null) {
                if (event.getClickedBlock().getType() == Material.MOB_SPAWNER) {
                    if (event.getItem().getType() != Material.DIAMOND_PICKAXE)
                        event.getPlayer().sendMessage(CC.translate("&e&lTIP &fUse your fist to inspect this spawner!"));
                }
            }
        }
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getItem() == null) {
            Block block = event.getClickedBlock();
            if (block.getType() == Material.MOB_SPAWNER) {
                event.setCancelled(true);
                EntityType type = ((CreatureSpawner) block.getState()).getSpawnedType();
                Bukkit.getScheduler().runTaskAsynchronously(AstroSpawners.getInstance(),
                        (Runnable) new BukkitRunnable() {
                            @Override
                            public void run() {
                                SpawnerData data = new SpawnerData(block.getLocation(), type, 1);
                                SpawnerData found = SpawnerDataHelper.findSpawnerData(data);
                                Player player = event.getPlayer();
                                player.sendMessage("");
                                player.sendMessage(CC.GreenB + "Spawner Information");
                                player.sendMessage(CC.GreenB + " * " + CC.White + "Type: " +
                                        CC.Green + EntityUtils.getEntityName(type));
                                player.sendMessage(CC.GreenB + " * " + CC.White + "Amount: " + CC.Green
                                        + (found != null ? found.getSpawnerAmount() : 1) + "x");
                                player.sendMessage("");
                            }
                        });
            }
        }
    }
}
