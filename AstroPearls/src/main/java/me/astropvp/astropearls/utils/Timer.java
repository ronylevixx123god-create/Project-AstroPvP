package me.astropvp.astropearls.utils;

import java.util.HashMap;
import java.util.UUID;

import com.cosmicpvp.cosmicutils.utils.CC;
import org.arkhamnetwork.Arkkit.patches.EnderpearlCooldown;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.Event.Result;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import me.astropvp.astropearls.AstroPearls;

public class Timer extends Handler implements Listener {
    public HashMap<UUID, Long> cooldown = new HashMap();

    public Timer(AstroPearls plugin) {
        super(plugin);
    }

    public void enable() {
        AstroPearls.getInstance().getServer().getPluginManager().registerEvents(this, AstroPearls.getInstance());
    }

    public void disable() {
        this.cooldown.clear();
    }

    public boolean isActive(Player player) {
        return this.cooldown.containsKey(player.getUniqueId()) && System.currentTimeMillis() < (Long)this.cooldown.get(player.getUniqueId());
    }

    public void quit(Player player) {
        if (this.cooldown.containsKey(player.getUniqueId()) && this.isActive(player)) {
            this.cooldown.remove(player.getUniqueId());
        }

    }

    public long getMillisecondsLeft(Player player) {
        return this.cooldown.containsKey(player.getUniqueId()) ? Math.max((Long)this.cooldown.get(player.getUniqueId()) - System.currentTimeMillis(), 0L) : 0L;
    }

    public HashMap<UUID, Long> getActiveCooldowns() {
        return this.cooldown;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        final Player player = event.getPlayer();
        Action action = event.getAction();
        if (!player.getGameMode().equals(GameMode.CREATIVE)) {
            if (AstroPearls.getInstance().getConfig().getBoolean("Timer.Enabled")) {
                if (event.hasItem() && (action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK)) && event.getItem().getType().equals(Material.ENDER_PEARL)) {
                    if (this.isActive(player)) {
                        event.setUseItemInHand(Result.DENY);
                        if (AstroPearls.getInstance().getConfig().getBoolean("Timer.Send-Message")) {
                            player.sendMessage(CC.translate(AstroPearls.getInstance().getConfig().getString("Timer.Message").replace("%time%", Utils.getRemaining(this.getMillisecondsLeft(player), true))));
                        }
                    } else if (event.getItem().getType() == Material.ENDER_PEARL && event.getItem().getItemMeta().getDisplayName() != null && event.getItem().getItemMeta().getDisplayName().equals(CC.translate(AstroPearls.getInstance().getConfig().getString("fast-pearl-name")))) {
                        (new BukkitRunnable() {
                            public void run() {
                                AstroPearls.getInstance().getTimer().cooldown.put(player.getUniqueId(), System.currentTimeMillis() + (long)(AstroPearls.getInstance().getConfig().getInt("fast-pearl-cooldown") * 1000));
                            }
                        }).runTaskLater(AstroPearls.getInstance(), 1L);
                    } else if (event.getItem().getType() == Material.ENDER_PEARL) {
                        (new BukkitRunnable() {
                            public void run() {
                                AstroPearls.getInstance().getTimer().cooldown.put(player.getUniqueId(), System.currentTimeMillis() + (long)(AstroPearls.getInstance().getConfig().getInt("timer.time") * 1000));
                            }
                        }).runTaskLater(AstroPearls.getInstance(), 1L);
                    }
                }

            }
        }
    }

    @EventHandler
    public void SensiblePearls(PlayerInteractEvent event) {
        if (event.hasBlock()) {
            Player player = event.getPlayer();
            Block block = event.getClickedBlock();
            Action action = event.getAction();
            if (!player.getGameMode().equals(GameMode.CREATIVE)) {
                if (EnderpearlCooldown.isEnderpearlCooldownOver(player)) {
                    ItemStack old;
                    if (AstroPearls.getInstance().getConfig().getBoolean("Settings.Spigot1-7") && !player.getGameMode().equals(GameMode.CREATIVE) && !AstroPearls.getInstance().getTimer().isActive(player) && (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) && block.getType().isSolid() && player.getInventory().getItemInHand().getType() == Material.ENDER_PEARL) {
                        old = player.getItemInHand();
                        if (!AstroPearls.isSoulPearl(old)) {
                            event.getPlayer().setItemInHand(old);
                        }

                        player.launchProjectile(EnderPearl.class);
                        player.getWorld().playSound(player.getLocation(), Sound.SHOOT_ARROW, 0.5F, 0.4F);
                        event.setCancelled(true);
                    }

                    if (!player.getGameMode().equals(GameMode.CREATIVE) && !AstroPearls.getInstance().getTimer().isActive(player) && action == Action.RIGHT_CLICK_BLOCK && block.getType() == Material.DISPENSER && player.getInventory().getItemInHand().getType() == Material.ENDER_PEARL || !player.getGameMode().equals(GameMode.CREATIVE) && !AstroPearls.getInstance().getTimer().isActive(player) && action == Action.RIGHT_CLICK_BLOCK && block.getType() == Material.STONE_BUTTON && player.getInventory().getItemInHand().getType() == Material.ENDER_PEARL || !player.getGameMode().equals(GameMode.CREATIVE) && !AstroPearls.getInstance().getTimer().isActive(player) && action == Action.RIGHT_CLICK_BLOCK && block.getType() == Material.WOOD_BUTTON && player.getInventory().getItemInHand().getType() == Material.ENDER_PEARL || !player.getGameMode().equals(GameMode.CREATIVE) && !AstroPearls.getInstance().getTimer().isActive(player) && action == Action.RIGHT_CLICK_BLOCK && block.getType() == Material.LEVER && player.getInventory().getItemInHand().getType() == Material.ENDER_PEARL || !player.getGameMode().equals(GameMode.CREATIVE) && !AstroPearls.getInstance().getTimer().isActive(player) && action == Action.RIGHT_CLICK_BLOCK && block.getType() == Material.FIRE && player.getInventory().getItemInHand().getType() == Material.ENDER_PEARL || !player.getGameMode().equals(GameMode.CREATIVE) && !AstroPearls.getInstance().getTimer().isActive(player) && action == Action.RIGHT_CLICK_BLOCK && block.getType() == Material.TRAP_DOOR && player.getInventory().getItemInHand().getType() == Material.ENDER_PEARL || !player.getGameMode().equals(GameMode.CREATIVE) && !AstroPearls.getInstance().getTimer().isActive(player) && action == Action.RIGHT_CLICK_BLOCK && block.getType() == Material.WOOD_DOOR && player.getInventory().getItemInHand().getType() == Material.ENDER_PEARL || !player.getGameMode().equals(GameMode.CREATIVE) && !AstroPearls.getInstance().getTimer().isActive(player) && action == Action.RIGHT_CLICK_BLOCK && block.getType() == Material.WOODEN_DOOR && player.getInventory().getItemInHand().getType() == Material.ENDER_PEARL || !player.getGameMode().equals(GameMode.CREATIVE) && !AstroPearls.getInstance().getTimer().isActive(player) && action == Action.RIGHT_CLICK_BLOCK && block.getType() == Material.BREWING_STAND && player.getInventory().getItemInHand().getType() == Material.ENDER_PEARL || !player.getGameMode().equals(GameMode.CREATIVE) && !AstroPearls.getInstance().getTimer().isActive(player) && action == Action.RIGHT_CLICK_BLOCK && block.getType() == Material.HOPPER && player.getInventory().getItemInHand().getType() == Material.ENDER_PEARL || !player.getGameMode().equals(GameMode.CREATIVE) && !AstroPearls.getInstance().getTimer().isActive(player) && action == Action.RIGHT_CLICK_BLOCK && block.getType() == Material.DROPPER && player.getInventory().getItemInHand().getType() == Material.ENDER_PEARL || !player.getGameMode().equals(GameMode.CREATIVE) && !AstroPearls.getInstance().getTimer().isActive(player) && action == Action.RIGHT_CLICK_BLOCK && block.getType() == Material.IRON_DOOR_BLOCK && player.getInventory().getItemInHand().getType() == Material.ENDER_PEARL || !player.getGameMode().equals(GameMode.CREATIVE) && !AstroPearls.getInstance().getTimer().isActive(player) && action == Action.RIGHT_CLICK_BLOCK && block.getType() == Material.FURNACE && player.getInventory().getItemInHand().getType() == Material.ENDER_PEARL || !player.getGameMode().equals(GameMode.CREATIVE) && !AstroPearls.getInstance().getTimer().isActive(player) && action == Action.RIGHT_CLICK_BLOCK && block.getType() == Material.BURNING_FURNACE && player.getInventory().getItemInHand().getType() == Material.ENDER_PEARL || !player.getGameMode().equals(GameMode.CREATIVE) && !AstroPearls.getInstance().getTimer().isActive(player) && action == Action.RIGHT_CLICK_BLOCK && block.getType() == Material.ANVIL && player.getInventory().getItemInHand().getType() == Material.ENDER_PEARL || !player.getGameMode().equals(GameMode.CREATIVE) && !AstroPearls.getInstance().getTimer().isActive(player) && action == Action.RIGHT_CLICK_BLOCK && block.getType() == Material.BEACON && player.getInventory().getItemInHand().getType() == Material.ENDER_PEARL || !player.getGameMode().equals(GameMode.CREATIVE) && !AstroPearls.getInstance().getTimer().isActive(player) && action == Action.RIGHT_CLICK_BLOCK && block.getType() == Material.ENCHANTMENT_TABLE && player.getInventory().getItemInHand().getType() == Material.ENDER_PEARL || !player.getGameMode().equals(GameMode.CREATIVE) && !AstroPearls.getInstance().getTimer().isActive(player) && action == Action.RIGHT_CLICK_BLOCK && block.getType() == Material.BED && player.getInventory().getItemInHand().getType() == Material.ENDER_PEARL || !player.getGameMode().equals(GameMode.CREATIVE) && !AstroPearls.getInstance().getTimer().isActive(player) && action == Action.RIGHT_CLICK_BLOCK && block.getType() == Material.BED_BLOCK && player.getInventory().getItemInHand().getType() == Material.ENDER_PEARL || !player.getGameMode().equals(GameMode.CREATIVE) && !AstroPearls.getInstance().getTimer().isActive(player) && action == Action.RIGHT_CLICK_BLOCK && block.getType() == Material.CHEST && player.getInventory().getItemInHand().getType() == Material.ENDER_PEARL || !player.getGameMode().equals(GameMode.CREATIVE) && !AstroPearls.getInstance().getTimer().isActive(player) && action == Action.RIGHT_CLICK_BLOCK && block.getType() == Material.ENDER_CHEST && player.getInventory().getItemInHand().getType() == Material.ENDER_PEARL || !player.getGameMode().equals(GameMode.CREATIVE) && !AstroPearls.getInstance().getTimer().isActive(player) && action == Action.RIGHT_CLICK_BLOCK && block.getType() == Material.TRAPPED_CHEST && player.getInventory().getItemInHand().getType() == Material.ENDER_PEARL || !player.getGameMode().equals(GameMode.CREATIVE) && !AstroPearls.getInstance().getTimer().isActive(player) && action == Action.RIGHT_CLICK_BLOCK && block.getType() == Material.WORKBENCH && player.getInventory().getItemInHand().getType() == Material.ENDER_PEARL || !player.getGameMode().equals(GameMode.CREATIVE) && !AstroPearls.getInstance().getTimer().isActive(player) && action == Action.RIGHT_CLICK_BLOCK && block.getType() == Material.FENCE_GATE && player.getInventory().getItemInHand().getType() == Material.ENDER_PEARL) {
                        old = player.getItemInHand();
                        if (!AstroPearls.isSoulPearl(old)) {
                            event.getPlayer().setItemInHand(old);
                        }

                        player.launchProjectile(EnderPearl.class);
                        player.getWorld().playSound(player.getLocation(), Sound.SHOOT_ARROW, 0.5F, 0.4F);
                        event.setCancelled(true);
                    }

                }
            }
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        if (this.cooldown.containsKey(player.getUniqueId())) {
            this.cooldown.remove(player.getUniqueId());
        }

    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (this.cooldown.containsKey(player.getUniqueId()) && !this.isActive(player)) {
            this.cooldown.remove(player.getUniqueId());
        }

    }
}
