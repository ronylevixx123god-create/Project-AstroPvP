package me.kingsavaqe.astroenvoy.listeners;


import java.io.File;
import java.util.List;
import java.util.Random;

import me.kingsavaqe.astroenvoy.AstroEnvoyPlugin;
import me.kingsavaqe.astroenvoy.envoy.Envoy;
import me.kingsavaqe.astroenvoy.envoy.EnvoyManager;
import me.kingsavaqe.astroenvoy.playerdata.PlayerData;
import me.kingsavaqe.astroenvoy.playerdata.PlayerDataManager;
import me.kingsavaqe.astroenvoy.utils.NBTUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class Interact implements Listener {
    private final AstroEnvoyPlugin instance;

    public Interact(AstroEnvoyPlugin instance) {
        this.instance = instance;
    }

    @EventHandler
    public void onPlayerInteractWand(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK && event.getAction() != Action.LEFT_CLICK_BLOCK)
            return;
        PlayerDataManager playerDataManager = this.instance.getPlayerDataManager();
        EnvoyManager envoyManager = this.instance.getEnvoyManager();
        Envoy envoy = envoyManager.getEnvoy();
        if (!envoy.hasSetup() && !envoy.hasStarted())
            return;
        Player player = event.getPlayer();
        Block block = event.getClickedBlock();
        Random random = new Random();
        FileConfiguration configLoad = this.instance.getFileManager().getConfig(new File(this.instance.getDataFolder(), "config.yml")).getFileConfiguration();
        if (block.getType() != Material.getMaterial(configLoad.getString("Envoy.Crate.Block.Material")))
            return;
        if (!envoy.isRandomCrateLocation(block.getLocation()))
            return;
        event.setCancelled(true);
        block.getWorld().playEffect(block.getLocation(), Effect.STEP_SOUND, configLoad.getInt("Envoy.Crate.Block.ID"));
        if (configLoad.getBoolean("Envoy.Crate.Explosion.Enable") && random
                .nextInt(100) <= configLoad.getInt("Envoy.Crate.Explosion.Chance")) {
            block.getWorld().playSound(block.getLocation(), Sound.DIG_STONE, 1.0F, 1.0F);
            for (int i = 0; i < 3; i++)
                player.getWorld().spigot().playEffect(player.getLocation(), Effect.EXPLOSION_HUGE);
            player.getWorld().playSound(player.getLocation(), Sound.EXPLODE, 1.0F, 0.6F);
            if (player.getGameMode() != GameMode.CREATIVE || player.getGameMode() != GameMode.SPECTATOR)
                player.damage((random
                        .nextInt(configLoad.getInt("Envoy.Crate.Explosion.Damage.Max") - configLoad
                                .getInt("Envoy.Crate.Explosion.Damage.Min") + 1) + configLoad
                        .getInt("Envoy.Crate.Explosion.Damage.Min")));
            if (configLoad.getBoolean("Envoy.Crate.PotionEffects.Enable")) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 70, 2));
                player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 70, 2));
            }
            Vector direction = new Vector();
            direction.setX(0.0D + Math.random() - Math.random());
            direction.setY(Math.random());
            direction.setZ(0.0D + Math.random() - Math.random());
            player.setVelocity(direction);
        } else {
            block.getWorld().playSound(block.getLocation(), Sound.DIG_SNOW, 1.0F, 1.0F);
            block.getWorld().playSound(block.getLocation(), Sound.LEVEL_UP, 1.0F, 0.7F);
            for (int i = 0; i < 20; i++)
                block.getWorld().spigot().playEffect(block.getLocation(), Effect.HAPPY_VILLAGER, 0, 0, 0.0F, 0.0F, 0.0F, 0.2F, 1, 10);
            ItemStack randomLootItem = envoy.getRandomLootItem();
            if (randomLootItem.getType() != Material.AIR)
                block.getWorld().dropItemNaturally(block.getLocation(), envoy.getRandomLootItem());
            if (configLoad.getBoolean("Envoy.Crate.PotionEffects.Enable")) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 120, 2));
                player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 120, 2));
            }
            if (playerDataManager.hasPlayerData(player)) {
                PlayerData playerData = playerDataManager.getPlayerData(player);
                playerData.setOpened(playerData.getOpened() + 1);
            }
        }
        block.setType(Material.AIR);
        envoyManager.removeNearbyHologram(block.getLocation());
        envoy.removeRandomCrateLocation(block.getLocation());

    }

    @EventHandler
    public void onPlayerInteractSummoner(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK && event.getAction() != Action.RIGHT_CLICK_AIR)
            return;
        Player player = event.getPlayer();
        ItemStack is = event.getItem();
        if (is == null || is.getType() == Material.AIR)
            return;
        PlayerDataManager playerDataManager = this.instance.getPlayerDataManager();
        EnvoyManager envoyManager = this.instance.getEnvoyManager();
        FileConfiguration configLoad = this.instance.getFileManager().getConfig(new File(this.instance.getDataFolder(), "config.yml")).getFileConfiguration();
        if (is.getType() != Material.getMaterial(configLoad.getString("Envoy.Summoner.Item.Material")) ||
                !is.getItemMeta().getDisplayName().equals(
                        ChatColor.translateAlternateColorCodes('&', configLoad.getString("Envoy.Summoner.Displayname"))) ||
                !NBTUtil.hasTagString(is, "Envoy"))
            return;
        Envoy envoy = envoyManager.getEnvoy();
        if (!envoy.hasSetup()) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', configLoad
                    .getString("Listeners.Interact.Summoner.Setup.Message")));
            player.playSound(player.getLocation(), Sound.ANVIL_LAND, 1.0F, 1.0F);
            return;
        }
        if (envoy.hasStarted()) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', configLoad
                    .getString("Listeners.Interact.Summoner.Started.Message")));
            player.playSound(player.getLocation(), Sound.ANVIL_LAND, 1.0F, 1.0F);
            return;
        }
        if (is.getAmount() == 1) {
            player.getInventory().setItemInHand(null);
        } else {
            is.setAmount(is.getAmount() - 1);
            player.updateInventory();
        }
        envoy.setStarted(true);
        envoy.setCurrentStartTime(envoy.getFixedStartTime());
        List<Location> crateLocations = envoyManager.getCrateLocations();
        envoyManager.spawnCrates(crateLocations);
        for (Player all : Bukkit.getOnlinePlayers()) {
            if (!playerDataManager.hasPlayerData(all))
                continue;
            if (!playerDataManager.getPlayerData(all).hasNotifications())
                continue;
            for (String messageLine : configLoad.getStringList("Listeners.Interact.Summoner.Summoned.Lines"))
                all.sendMessage(ChatColor.translateAlternateColorCodes('&', messageLine
                        .replace("%player", player.getName()).replace("%crates", String.valueOf(crateLocations.size()))));
            if (configLoad.getString("Listeners.Interact.Summoner.Summoned.Sound") != null)
                all.playSound(all.getLocation(),
                        Sound.valueOf(configLoad.getString("Listeners.Interact.Summoner.Summoned.Sound")), 1.0F, 0.1F);
        }
    }
}
