/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.World
 *  org.bukkit.configuration.ConfigurationSection
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
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.scheduler.BukkitRunnable
 */
package org.arkhamnetwork.Arkkit.patches;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.patches.AutoEntityClearListenerLegacy;
import org.arkhamnetwork.Arkkit.patches.CommandClearLagLegacy;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
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
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class AutoEntityClearLegacy
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

    public AutoEntityClearLegacy(Plugin p) {
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
        this.registerListener(new AutoEntityClearListenerLegacy());
        this.registerTask(new clearTask().runTaskTimer((Plugin)Arkkit.get(), 20L, 20L), clearTask.class);
        this.registerCommand(new CommandClearLagLegacy("clearlag", "/<command>", "Displays time until next clearLag event.", new ArrayList<String>(Arrays.asList("ncl", "nextclearlag", "cl"))));
    }

    @Override
    public void disable() {
    }

    protected static void clear() {
        for (String cM : clearMessage) {
            Bukkit.broadcastMessage((String)ChatColor.translateAlternateColorCodes((char)'&', (String)cM));
        }
        for (World world : Bukkit.getWorlds()) {
            for (Entity entity : world.getEntities()) {
                if (entity.hasMetadata("cbe_boss") || entity.hasMetadata("NPC") || entity.hasMetadata("customCreeperType") || entity.hasMetadata("do_not_clear") || entity.getType() == EntityType.ENDER_PEARL) continue;
                if (entity instanceof Snowman || entity instanceof Slime || entity instanceof IronGolem || entity instanceof Spider) {
                    if (!clearMonsters) continue;
                    entity.remove();
                    continue;
                }
                if (entity instanceof Monster) {
                    if (!clearMonsters) continue;
                    entity.remove();
                    continue;
                }
                if (entity instanceof Animals) {
                    if (!clearAnimals) continue;
                    entity.remove();
                    continue;
                }
                if (entity instanceof WaterMob) {
                    if (!clearWaterCreatures) continue;
                    entity.remove();
                    continue;
                }
                if (entity instanceof Ambient) {
                    if (!clearAmbientCreatures) continue;
                    entity.remove();
                    continue;
                }
                if (entity instanceof Boat) {
                    Boat boat = (Boat)entity;
                    if (boat.getPassenger() != null || !clearEmptyBoats) continue;
                    entity.remove();
                    continue;
                }
                if (entity instanceof Minecart) {
                    Minecart minecart = (Minecart)entity;
                    if (minecart.getPassenger() != null || !clearEmptyMinecarts) continue;
                    entity.remove();
                    continue;
                }
                if (entity instanceof FallingBlock) {
                    if (!clearFallingBlocks) continue;
                    entity.remove();
                    continue;
                }
                if (entity instanceof ExperienceOrb) {
                    if (!clearExperienceOrbs) continue;
                    entity.remove();
                    continue;
                }
                if (entity instanceof Item) {
                    Item item = (Item)entity;
                    if (dontRemoveItems.contains(item.getItemStack().getTypeId()) && (item.getItemStack().hasItemMeta() && item.getItemStack().getItemMeta().hasDisplayName() || item.getItemStack().getType() == Material.MOB_SPAWNER) || !clearDroppedItems) continue;
                    entity.remove();
                    continue;
                }
                if (entity instanceof Snowball) {
                    if (!clearSnowball) continue;
                    entity.remove();
                    continue;
                }
                if (entity instanceof Fireball) {
                    if (!clearFireball) continue;
                    entity.remove();
                    continue;
                }
                if (!(entity instanceof EnderSignal) || !clearEnderSignal) continue;
                entity.remove();
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
        int currentCount;

        clearTask() {
            this.currentCount = AutoEntityClearLegacy.this.clearEverySeconds;
        }

        public synchronized void run() {
            if (this.currentCount <= 0) {
                AutoEntityClearLegacy.clear();
                this.currentCount = AutoEntityClearLegacy.this.clearEverySeconds;
                secondsUntilNextClear = AutoEntityClearLegacy.this.clearEverySeconds;
                return;
            }
            if (AutoEntityClearLegacy.this.warningIntervals.contains(this.currentCount)) {
                for (String wM : AutoEntityClearLegacy.this.warningMessage) {
                    Bukkit.broadcastMessage((String)ChatColor.translateAlternateColorCodes((char)'&', (String)wM.replace("[SECONDS]", String.valueOf(this.currentCount))));
                }
            }
            --secondsUntilNextClear;
            --this.currentCount;
        }
    }
}

