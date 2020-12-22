/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.server.v1_7_R4.EntityItem
 *  net.minecraft.server.v1_7_R4.MinecraftServer
 *  org.bukkit.Bukkit
 *  org.bukkit.Material
 *  org.bukkit.World
 *  org.bukkit.configuration.ConfigurationSection
 *  org.bukkit.craftbukkit.v1_7_R4.entity.CraftItem
 *  org.bukkit.entity.Ambient
 *  org.bukkit.entity.Animals
 *  org.bukkit.entity.Boat
 *  org.bukkit.entity.EnderSignal
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.EntityType
 *  org.bukkit.entity.ExperienceOrb
 *  org.bukkit.entity.FallingBlock
 *  org.bukkit.entity.Fireball
 *  org.bukkit.entity.IronGolem
 *  org.bukkit.entity.Item
 *  org.bukkit.entity.Minecart
 *  org.bukkit.entity.Monster
 *  org.bukkit.entity.Slime
 *  org.bukkit.entity.Snowball
 *  org.bukkit.entity.Snowman
 *  org.bukkit.entity.Spider
 *  org.bukkit.entity.WaterMob
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.scheduler.BukkitRunnable
 */
package org.arkhamnetwork.Arkkit.patches;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.server.v1_8_R3.EntityItem;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.arkhamnetwork.Arkkit.utils.WorldGuardUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftItem;
import org.bukkit.entity.Ambient;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Boat;
import org.bukkit.entity.EnderSignal;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.Item;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Slime;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.Snowman;
import org.bukkit.entity.Spider;
import org.bukkit.entity.WaterMob;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class AutoEntityClear
extends ArkPlugin {
    protected int clearEverySeconds = 300;
    protected List<Integer> warningIntervals;
    protected List<String> warningMessage;
    protected static List<String> clearMessage;
    protected static List<String> forceClearCommands;
    protected static String forceClearCommandPermission;
    public static int secondsUntilNextClear;
    private static boolean clearMonsters;
    private static boolean clearAnimals;
    private static boolean clearWaterCreatures;
    private static boolean clearAmbientCreatures;
    private static boolean clearEmptyBoats;
    private static boolean clearEmptyMinecarts;
    private static boolean clearFallingBlocks;
    private static boolean clearExperienceOrbs;
    private static boolean clearDroppedItems;
    private static boolean clearSnowball;
    private static boolean clearFireball;
    private static boolean clearEnderSignal;
    protected static List<Integer> dontRemoveItems;

    public AutoEntityClear(Plugin p) {
        super(p);
    }

    @Override
    public void enable() {
        ConfigurationSection pluginSection = Arkkit.get().getConfig().getConfigurationSection("settings.autoEntityClear");
        if (!pluginSection.getBoolean("enabled")) {
            return;
        }
        this.clearEverySeconds = pluginSection.getInt("clearEverySeconds");
        this.warningIntervals = pluginSection.getIntegerList("warningIntervals");
        this.warningMessage = pluginSection.getStringList("warningMessage");
        clearMessage = pluginSection.getStringList("clearMessage");
        forceClearCommands = pluginSection.getStringList("forceClearCommands");
        forceClearCommandPermission = pluginSection.getString("forceClearCommandPermission");
        secondsUntilNextClear = this.clearEverySeconds;
        clearMonsters = pluginSection.getBoolean("clearOptions.monsters");
        clearAnimals = pluginSection.getBoolean("clearOptions.animals");
        clearWaterCreatures = pluginSection.getBoolean("clearOptions.waterCreatures");
        clearAmbientCreatures = pluginSection.getBoolean("clearOptions.ambientCreatures");
        clearEmptyBoats = pluginSection.getBoolean("clearOptions.emptyBoats");
        clearEmptyMinecarts = pluginSection.getBoolean("clearOptions.emptyMinecarts");
        clearFallingBlocks = pluginSection.getBoolean("clearOptions.fallingBlocks");
        clearExperienceOrbs = pluginSection.getBoolean("clearOptions.experienceOrbs");
        clearDroppedItems = pluginSection.getBoolean("clearOptions.droppedItems");
        clearSnowball = pluginSection.getBoolean("clearOptions.snowball");
        clearFireball = pluginSection.getBoolean("clearOptions.fireball");
        clearEnderSignal = pluginSection.getBoolean("clearOptions.ender-signal");
        dontRemoveItems = pluginSection.getIntegerList("clearOptions.dontRemoveItems");
        this.registerListener(new AutoEntityClearListener());
        this.registerTask(new clearTask().runTaskTimer((Plugin) Arkkit.get(), 600L, 600L), clearTask.class);
        this.registerCommand(new CommandClearLag("clearlag", "/<command>", "Displays time until next clearLag event.", new ArrayList<String>(Arrays.asList("ncl", "nextclearlag", "cl"))));
    }

    @Override
    public void disable() {
    }

    protected static void clear() {
        for (World world : Bukkit.getWorlds()) {
            long expiryAge = 12000L;
            if (world.getName().equals("world_koth")) {
                expiryAge = 900L;
            }
            if (world.getName().startsWith("cosmic-station")) {
                expiryAge = 2400L;
            }
            if (world.getName().startsWith("dungeon_destroyed")) {
                expiryAge = 2400L;
            }
            for (Entity entity : world.getEntities()) {
                    int entityAge = 0;
                    if (entity.hasMetadata("timeDetected")) {
                        if (entity instanceof Item) {
                            EntityItem ei = (EntityItem) ((CraftItem) entity).getHandle();
                            entityAge = ei.age;
                            if (ei.droppedByContainer) {
                                entityAge *= 5;
                            } else if (ei.droppedByMob) {
                                entityAge *= 3;
                            } else {
                                Material m = ((Item) entity).getItemStack().getType();
                                if ((m.isBlock() || m == Material.IRON_BLOCK || m == Material.IRON_INGOT || m == Material.SULPHUR || m == Material.RED_MUSHROOM || m == Material.GOLD_NUGGET || m == Material.GOLD_INGOT) && m != Material.MOB_SPAWNER && m != Material.CHEST && m != Material.ENDER_CHEST) {
                                    entityAge *= 5;
                                }
                                if (!entity.hasMetadata("pvpOff") && WorldGuardUtils.isPvPDisabled(entity.getLocation())) {
                                    entity.setMetadata("pvpOff", (MetadataValue) new FixedMetadataValue((Plugin) Arkkit.get(), (Object) true));
                                }
                            }
                        } else {
                            entity.setMetadata("timeDetected", (MetadataValue) new FixedMetadataValue((Plugin) Arkkit.get(), MinecraftServer.currentTick));
                            continue;
                            entityAge = MinecraftServer.currentTick - ((MetadataValue) entity.getMetadata("timeDetected").get(0)).asInt();
                        }
                        if (entity.hasMetadata("pvpOff")) {
                            entityAge *= 10;
                        }
                        if ((long) entityAge < expiryAge || entity.hasMetadata("cbe_boss") || entity.hasMetadata("NPC") || entity.hasMetadata("customCreeperType") || entity.hasMetadata("do_not_clear") || entity.getType() == EntityType.ENDER_PEARL)
                            continue;
                        if (entity instanceof Snowman || entity instanceof Slime || entity instanceof IronGolem || entity instanceof Spider) {
                            if (!AutoEntityClear.clearMonsters) continue;
                            entity.remove();
                            continue;
                        }
                        if (entity instanceof Monster) {
                            if (!AutoEntityClear.clearMonsters) continue;
                            entity.remove();
                            continue;
                        }
                        if (entity instanceof Animals) {
                            if (!AutoEntityClear.clearAnimals) continue;
                            entity.remove();
                            continue;
                        }
                        if (entity instanceof WaterMob) {
                            if (!AutoEntityClear.clearWaterCreatures) continue;
                            entity.remove();
                            continue;
                        }
                        if (entity instanceof Ambient) {
                            if (!AutoEntityClear.clearAmbientCreatures) continue;
                            entity.remove();
                            continue;
                        }
                        if (entity instanceof Boat) {
                            Entity boat = entity;
                            if (boat.getPassenger() != null || !AutoEntityClear.clearEmptyBoats) continue;
                            entity.remove();
                            continue;
                        }
                        if (entity instanceof Minecart) {
                            Entity minecart = entity;
                            if (minecart.getPassenger() != null || !AutoEntityClear.clearEmptyMinecarts) continue;
                            entity.remove();
                            continue;
                        }
                        if (entity instanceof FallingBlock) {
                            if (!AutoEntityClear.clearFallingBlocks) continue;
                            entity.remove();
                            continue;
                        }
                        if (entity instanceof ExperienceOrb) {
                            if (!AutoEntityClear.clearExperienceOrbs) continue;
                            entity.remove();
                            continue;
                        }
                        if (entity instanceof Item) {
                            Item item = (Item) entity;
                            if (AutoEntityClear.dontRemoveItems.contains(item.getItemStack().getTypeId()) && (item.getItemStack().hasItemMeta() && item.getItemStack().getItemMeta().hasDisplayName() || item.getItemStack().getType() == Material.MOB_SPAWNER) || !AutoEntityClear.clearDroppedItems)
                                continue;
                            entity.remove();
                            continue;
                        }
                        if (entity instanceof Snowball) {
                            if (!AutoEntityClear.clearSnowball) continue;
                            entity.remove();
                            continue;
                        }
                        if (entity instanceof Fireball) {
                            if (!AutoEntityClear.clearFireball) continue;
                            entity.remove();
                            continue;
                        }
                        if (!(entity instanceof EnderSignal) || !AutoEntityClear.clearEnderSignal) continue;
                        entity.remove();
                    }
                    break;
                }
            }
        }

        static {
            clearMonsters = false;
            clearAnimals = false;
            clearWaterCreatures = false;
            clearAmbientCreatures = false;
            clearEmptyBoats = false;
            clearEmptyMinecarts = false;
            clearFallingBlocks = false;
            clearExperienceOrbs = false;
            clearDroppedItems = false;
            clearSnowball = false;
            clearFireball = false;
            clearEnderSignal = false;
            dontRemoveItems = new ArrayList<Integer>();
        }

        class clearTask
                extends BukkitRunnable {
            clearTask() {
            }

            public synchronized void run() {
                AutoEntityClear.clear();
            }
        }
    }


