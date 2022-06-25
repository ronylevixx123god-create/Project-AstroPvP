package me.kingsavaqe.astroenvoy.envoy;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import me.kingsavaqe.astroenvoy.AstroEnvoyPlugin;
import me.kingsavaqe.astroenvoy.file.FileManager;
import me.kingsavaqe.astroenvoy.location.LocationManager;
import me.kingsavaqe.astroenvoy.utils.Base64Utils;
import net.minecraft.server.v1_8_R3.MojangsonParseException;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;

public class EnvoyManager {
    private final AstroEnvoyPlugin instance;

    private Envoy envoy;

    public EnvoyManager(AstroEnvoyPlugin instance) {
        this.instance = instance;
        Bukkit.getServer().getScheduler().runTask(instance, new Runnable() {
            public void run() {
                EnvoyManager.this.setupEnvoy();
            }
        });
    }

    public void onDisable() {
        removeCrates();
        saveEnvoy();
    }

    public void setupEnvoy() {
        LocationManager locationManager = this.instance.getLocationManager();
        FileConfiguration configLoad = getConfig().getFileConfiguration();
        this.envoy = new Envoy();
        if (configLoad.getString("Envoy") == null)
            return;
        this.envoy.setStarted(configLoad.getBoolean("Envoy.Started"));
        this.envoy.setCurrentDuration(configLoad.getInt("Envoy.Duration.Current"));
        this.envoy.setFixedDuration(configLoad.getInt("Envoy.Duration.Fixed"));
        this.envoy.setCurrentStartTime(configLoad.getInt("Envoy.StartTime.Current"));
        this.envoy.setFixedStartTime(configLoad.getInt("Envoy.StartTime.Fixed"));
        if (configLoad.getString("Envoy.Location.Crate") != null)
            for (String index : configLoad.getConfigurationSection("Envoy.Location.Crate").getKeys(false)) {
                Location location = locationManager.getLocation(getConfig(), "Envoy.Location.Crate." + index);
                location.setX(location.getBlockX() + 0.5D);
                location.setZ(location.getBlockZ() + 0.5D);
                this.envoy.addCrateLocation(location);
            }
        if (configLoad.getString("Envoy.LootItem") != null)
            for (String index : configLoad.getConfigurationSection("Envoy.LootItem").getKeys(false)) {
                try {
                    this.envoy.addLootItem(new LootItem(Base64Utils.deserializeItemStack(configLoad.getString("Envoy.LootItem." + index + ".Item")), configLoad
                            .getInt("Envoy.LootItem." + index + ".Chance")));
                } catch (MojangsonParseException e) {
                    e.printStackTrace();
                }
            }
        if (this.envoy.hasStarted())
            spawnCrates();
    }

    public void saveEnvoy() {
        LocationManager locationManager = this.instance.getLocationManager();
        FileManager.Config config = getConfig();
        FileConfiguration configLoad = config.getFileConfiguration();
        configLoad.set("Envoy", null);
        configLoad.set("Envoy.Started", this.envoy.hasStarted());
        configLoad.set("Envoy.Duration.Current", this.envoy.getCurrentDuration());
        configLoad.set("Envoy.Duration.Fixed", this.envoy.getFixedDuration());
        configLoad.set("Envoy.StartTime.Current", this.envoy.getCurrentStartTime());
        configLoad.set("Envoy.StartTime.Fixed", this.envoy.getFixedStartTime());
        if (this.envoy.hasCrateLocation())
            for (int i = 0; i < this.envoy.getCrateLocations().size(); i++)
                locationManager.setLocation(getConfig(), "Envoy.Location.Crate." + i, this.envoy.getCrateLocations().get(i), false);
        if (this.envoy.hasLootItems())
            for (int i = 0; i < this.envoy.getLootItems().size(); i++) {
                LootItem lootItem = this.envoy.getLootItems().get(i);
                configLoad.set("Envoy.LootItem." + i + ".Chance", Integer.valueOf(lootItem.getChance()));
                configLoad.set("Envoy.LootItem." + i + ".Item", Base64Utils.serializeItemStack(lootItem.getItemStack()));
            }
        config.saveFile();
    }

    public void spawnCrates() {
        spawnCrates(getCrateLocations());
    }

    public void spawnCrates(List<Location> crateLocations) {
        FileConfiguration configLoad = this.instance.getFileManager().getConfig(new File(this.instance.getDataFolder(), "config.yml")).getFileConfiguration();
        for (Location crateLocation : crateLocations) {
            Block block = crateLocation.getBlock();
            block.setType(Material.getMaterial(configLoad.getString("Envoy.Crate.Block.Material")));
            if (configLoad.getString("Envoy.Crate.Block.Data") != null)
                block.setData((byte)configLoad.getInt("Envoy.Crate.Block.Data"));
            ArmorStand armorStand = (ArmorStand)crateLocation.getWorld().spawnEntity(crateLocation.clone().add(0.0D, 0.1D, 0.0D), EntityType.ARMOR_STAND);
            armorStand.setCustomName(ChatColor.translateAlternateColorCodes('&', configLoad
                    .getString("Envoy.Crate.Hologram.CustomName")));
            armorStand.setCustomNameVisible(true);
            armorStand.setGravity(false);
            armorStand.setVisible(false);
            armorStand.setSmall(true);
            Firework firework = crateLocation.getWorld().spawn(crateLocation, Firework.class);
            FireworkMeta fireworkMeta = firework.getFireworkMeta();
            fireworkMeta.setPower(0);
            fireworkMeta.addEffect(FireworkEffect.builder().withColor(Color.PURPLE).withFade(Color.WHITE)
                    .with(FireworkEffect.Type.BALL).flicker(true).build());
            firework.setFireworkMeta(fireworkMeta);
        }
        this.envoy.setRandomCrateLocations(crateLocations);
    }

    public void removeCrates() {
        FileConfiguration configLoad = this.instance.getFileManager().getConfig(new File(this.instance.getDataFolder(), "config.yml")).getFileConfiguration();
        for (Location crateLocation : this.envoy.getCrateLocations()) {
            if(crateLocation == null) return;
            Block block = crateLocation.getBlock();
            if (block.getType() != Material.getMaterial(configLoad.getString("Envoy.Crate.Block.Material")))
                continue;
            block.setType(Material.AIR);
            removeNearbyHologram(crateLocation);
        }
        this.envoy.getRandomCrateLocations().clear();
    }

    public List<Location> getCrateLocations() {
        Random random = new Random();
        int spawnCrates = 55;
        if (Bukkit.getOnlinePlayers().size() >= 12)
            spawnCrates = (Bukkit.getOnlinePlayers().size()) / 3;
        if (spawnCrates > 80)
            spawnCrates = 80;
        List<Location> crateLocations = new ArrayList<>(this.envoy.getCrateLocations());
        int removeCrates = crateLocations.size() - spawnCrates;
        for (int i = 0; i < removeCrates; i++)
            crateLocations.remove(crateLocations.get(random.nextInt(crateLocations.size())));
        return crateLocations;
    }

    public void removeNearbyHologram(Location location) {
        for (Entity entity : location.getWorld().getNearbyEntities(location, 0.3D, 0.6D, 0.3D)) {
            if (!(entity instanceof ArmorStand))
                continue;
            entity.remove();
        }
    }

    public Envoy getEnvoy() {
        return this.envoy;
    }

    public FileManager.Config getConfig() {
        return this.instance.getFileManager().getConfig(new File(this.instance.getDataFolder(), "envoy.yml"));
    }
}
