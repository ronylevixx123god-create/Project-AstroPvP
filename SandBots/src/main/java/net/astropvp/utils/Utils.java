// 
// Decompiled by Procyon v0.5.36
// 

package net.astropvp.utils;

import java.util.List;

import com.cosmicpvp.cosmicutils.utils.FactionUtils;
import com.cosmicpvp.cosmicutils.utils.ItemBuilder;
import com.massivecraft.factions.FPlayers;
import org.bukkit.World;
import org.bukkit.Bukkit;
import java.util.UUID;
import net.astropvp.config.ConfigFile;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.block.Block;
import java.util.ArrayList;
import net.astropvp.config.FilesManager;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.util.EulerAngle;
import org.bukkit.plugin.Plugin;
import net.astropvp.animations.Animation;
import net.astropvp.inventories.SandBotGui;
import net.astropvp.inventories.FactionManageGui;
import net.astropvp.inventories.RemoveGUI;
import net.astropvp.enums.GuiType;
import java.util.concurrent.ConcurrentHashMap;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import net.astropvp.managers.SandBotManager;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import net.astropvp.SandBots;

public class Utils
{
    private static SandBots plugin;
    public static Color enabledArmorColor;
    public static Color disabledArmorColor;
    
    public static String color(final String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }
    
    public static void dropitem(final Player player, final Location loc, final ItemStack item) {
        final ItemStack tmpitem = item;
        final Entity drop = (Entity)player.getWorld().dropItem(loc, item);
        final Vector v = new Vector().setY(0.7);
        drop.setVelocity(v);
    }
    
    public static void dropitem(final Location loc, final ItemStack item) {
        final ItemStack tmpitem = item;
        final Entity drop = (Entity)loc.getWorld().dropItem(loc, item);
        final Vector v = new Vector().setY(0.7);
        drop.setVelocity(v);
    }
    
    public static void spawnBot(final Player player, final Location loc) {
        if (FactionUtils.getFactionId(player) == null) {
            player.sendMessage(color(Utils.plugin.getConfig().getString("no-faction")));
            return;
        }
        if (FactionUtils.getFactionAt(loc).getId() == null || !FactionUtils.getFactionAt(loc).getId().equals(FactionUtils.getFactionId(player))) {
            player.sendMessage(color(Utils.plugin.getConfig().getString("no-claim")));
            return;
        }
        if (getFactionAliveBotsCount(FactionUtils.getFactionId(player)) >= Utils.plugin.getConfig().getInt("faction-bot-limit")) {
            player.sendMessage(color(Utils.plugin.getConfig().getString("faction-bot-full")));
            return;
        }
        for (final SandBotManager bot : SandBots.sandBots) {
            if (bot.getLocation() != null) {
                if (bot.getLocation().getWorld() == null) {
                    continue;
                }
                if (bot.getLocation().getWorld().equals(loc.getWorld()) && bot.getLocation().distance(loc) <= 2 * Utils.plugin.getConfig().getInt("bot-fill-radius") && FactionUtils.getFactionAt(loc).getId() != null && FactionUtils.getFactionAt(bot.getLocation()).getId() != null && FactionUtils.getFactionAt(loc).getId().equals(FactionUtils.getFactionAt(bot.getLocation()).getId())) {
                    player.sendMessage(color(Utils.plugin.getConfig().getString("bot-close-to-each-other").replace("%distance%", String.valueOf(2 * Utils.plugin.getConfig().getInt("bot-fill-radius") - (int)bot.getLocation().distance(loc)))));
                    return;
                }
                continue;
            }
        }
        final ItemStack item = player.getItemInHand();
        if (item.getAmount() == 1) {
            player.getInventory().remove(item);
        }
        else {
            player.getItemInHand().setAmount(item.getAmount() - 1);
        }
        final ArmorStand stand = (ArmorStand)loc.getWorld().spawn(loc, (Class)ArmorStand.class);
        stand.setVisible(true);
        stand.setArms(true);
        stand.setGravity(false);
        stand.setSmall(false);
        stand.setBasePlate(false);
        stand.setItemInHand(new ItemStack(Material.SAND));
        final ItemStack playerhead = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
        final SkullMeta meta = (SkullMeta)playerhead.getItemMeta();
        meta.setOwner(player.getName());
        meta.setDisplayName(player.getName());
        playerhead.setItemMeta((ItemMeta)meta);
        stand.setCustomName("ignoreArmorstand");
        stand.setHelmet(playerhead);
        stand.setChestplate(getColorArmor(Material.LEATHER_CHESTPLATE, Utils.disabledArmorColor));
        stand.setLeggings(getColorArmor(Material.LEATHER_LEGGINGS, Utils.disabledArmorColor));
        stand.setBoots(getColorArmor(Material.LEATHER_BOOTS, Utils.disabledArmorColor));
        final EulerAngle rarm = stand.getRightArmPose();
        final EulerAngle larm = stand.getLeftArmPose();
        final EulerAngle newrarm = rarm.setX(-1.5).setZ(-0.30000001192092896).setY(0.5);
        final EulerAngle newlarm = larm.setX(-0.20000000298023224).setZ(-0.20000000298023224);
        stand.setRightArmPose(newrarm);
        stand.setLeftArmPose(newlarm);
        final EulerAngle rleg = stand.getRightLegPose();
        final EulerAngle lleg = stand.getLeftLegPose();
        final EulerAngle newrleg = rleg.setX(-0.20000000298023224).setZ(0.20000000298023224);
        final EulerAngle newlleg = lleg.setX(0.20000000298023224).setZ(-0.20000000298023224);
        stand.setRightLegPose(newrleg);
        stand.setLeftLegPose(newlleg);
        stand.setCustomNameVisible(true);
        stand.setCustomName(color(Utils.plugin.getConfig().getString("bot-custom-name").replace("%faction%", FactionUtils.getFactionTag(player))));
        final SandBotManager bot2 = new SandBotManager(stand, player);
        SandBots.sandBots.add(bot2);
        final ConcurrentHashMap<GuiType, MenuManager> menus = new ConcurrentHashMap<GuiType, MenuManager>();
        menus.put(GuiType.REMOVEGUI, new RemoveGUI(Utils.plugin, bot2));
        menus.put(GuiType.FACTIONGUI, new FactionManageGui(Utils.plugin));
        menus.put(GuiType.SANDBOTGUI, new SandBotGui(Utils.plugin, bot2));
        SandBots.botGuis.put(bot2, menus);
        new Animation(bot2, Utils.plugin).runTaskTimerAsynchronously((Plugin) Utils.plugin, 0L, 2L);
    }
    
    public static ItemStack getColorArmor(final Material m, final Color c) {
        final ItemStack i = new ItemStack(m, 1);
        final LeatherArmorMeta meta = (LeatherArmorMeta)i.getItemMeta();
        meta.setColor(c);
        meta.setDisplayName("SandBotArmor");
        i.setItemMeta((ItemMeta)meta);
        return i;
    }
    
    public static String getFactionID(final Player player) {
        return FactionUtils.getFactionId(player);
    }
    
    public static Integer getFactionAliveBotsCount(final String factionID) {
        Integer count = 0;
        for (final SandBotManager bot : SandBots.sandBots) {
            if (bot.getFactionID().equals(factionID)) {
                ++count;
            }
        }
        return count;
    }
    
    public static void setupConfigFiles(final FilesManager manager) {
        manager.folderSetup("Data");
        manager.fileSetup("config");
        manager.fileSetup("sandbots", "Data");
    }
    
    public static void saveDataOfSandbots(final FilesManager manager) {
        if (manager.getAllFiles("default/Data").values().size() < 1) {
            return;
        }
        final FileConfiguration data = manager.getFile("default/Data/sandbots.yml").getConfig();
        for (final SandBotManager bot : SandBots.sandBots) {
            data.set("SandBots." + bot.getSandbotUUID() + ".sandBotUuid", (Object)bot.getSandbotUUID().toString());
            data.set("SandBots." + bot.getSandbotUUID() + ".ownerUuid", (Object)bot.getOwnerUUID().toString());
            data.set("SandBots." + bot.getSandbotUUID() + ".ownerName", (Object)bot.getOwnerName());
            data.set("SandBots." + bot.getSandbotUUID() + ".location", (Object)locationToString(bot.getLocation()));
            data.set("SandBots." + bot.getSandbotUUID() + ".balance", (Object)bot.getBalance());
            data.set("SandBots." + bot.getSandbotUUID() + ".isActivated", (Object)bot.isActivated());
            data.set("SandBots." + bot.getSandbotUUID() + ".factionID", (Object)bot.getFactionID());
            data.set("SandBots." + bot.getSandbotUUID() + ".animate", (Object)bot.isAnimate());
            final ArrayList<String> blockLocations = new ArrayList<String>();
            for (final Block block : bot.getLapisBlocks()) {
                blockLocations.add(locationToString(block.getLocation()));
            }
            data.set("SandBots." + bot.getSandbotUUID() + ".lapisBlocks", (Object)blockLocations);
        }
        manager.getFile("default/Data/sandbots.yml").saveConfig();
    }
    
    public static void removeBotFromConfigIfExist(final FilesManager manager, final SandBotManager bot) {
        final ConfigFile data = manager.getFile("default/Data/sandbots.yml");
        if (data.getConfig().isSet("SandBots." + bot.getSandbotUUID())) {
            data.getConfig().set("SandBots." + bot.getSandbotUUID(), (Object)null);
            data.saveConfig();
        }
    }
    
    public static void loadDataOfSandbots(final FilesManager manager) {
        Integer counter = 0;
        final ConfigFile data = manager.getFile("default/Data/sandbots.yml");
        if (data.getConfig().isConfigurationSection("SandBots")) {
            for (final String sandBotsUUIDS : data.getConfig().getConfigurationSection("SandBots").getKeys(false)) {
                final ArrayList<Block> lapisBlocks = new ArrayList<Block>();
                for (final String locstring : data.getConfig().getStringList("SandBots." + sandBotsUUIDS + ".lapisBlocks")) {
                    lapisBlocks.add(stringToLocation(locstring).getBlock());
                }
                final SandBotManager bot = new SandBotManager(UUID.fromString(data.getConfig().getString("SandBots." + sandBotsUUIDS + ".sandBotUuid")), UUID.fromString(data.getConfig().getString("SandBots." + sandBotsUUIDS + ".ownerUuid")), data.getConfig().getString("SandBots." + sandBotsUUIDS + ".ownerName"), lapisBlocks, data.getConfig().getDouble("SandBots." + sandBotsUUIDS + ".balance"), stringToLocation(data.getConfig().getString("SandBots." + sandBotsUUIDS + ".location")), data.getConfig().getBoolean("SandBots." + sandBotsUUIDS + ".isActivated"), data.getConfig().getString("SandBots." + sandBotsUUIDS + ".factionID"), data.getConfig().getBoolean("SandBots." + sandBotsUUIDS + ".animate"));
                SandBots.sandBots.add(bot);
                if (bot.getLocation() != null && bot.getLocation().getWorld() != null) {
                    bot.getLocation().getWorld().loadChunk(bot.getLocation().getChunk());
                }
                final ConcurrentHashMap<GuiType, MenuManager> menus = new ConcurrentHashMap<GuiType, MenuManager>();
                menus.put(GuiType.REMOVEGUI, new RemoveGUI(Utils.plugin, bot));
                menus.put(GuiType.FACTIONGUI, new FactionManageGui(Utils.plugin));
                menus.put(GuiType.SANDBOTGUI, new SandBotGui(Utils.plugin, bot));
                SandBots.botGuis.put(bot, menus);
                ++counter;
                new Animation(bot, Utils.plugin).runTaskTimerAsynchronously((Plugin) Utils.plugin, 0L, 2L);
            }
        }
        Bukkit.getConsoleSender().sendMessage(color("&8&l[&a&l!&8&l] Successfully loaded " + counter + " SandBots"));
    }
    
    public static String locationToString(final Location location) {
        if (location == null || location.getWorld() == null) {
            return "Unknown:0:0:0";
        }
        final String loc = location.getX() + ":" + location.getY() + ":" + location.getZ() + ":" + location.getWorld().getName();
        return loc;
    }
    
    public static Location stringToLocation(final String string) {
        Location loc;
        try {
            loc = new Location(Bukkit.getWorld(string.split(":")[3]), Double.parseDouble(string.split(":")[0]), Double.parseDouble(string.split(":")[1]), Double.parseDouble(string.split(":")[2]));
        }
        catch (Exception e) {
            return null;
        }
        return loc;
    }
    
    public static Entity getEntityByUUID(final UUID uuid) {
        for (final World world : Bukkit.getServer().getWorlds()) {
            for (final Entity entity : world.getEntities()) {
                if (entity.getUniqueId().equals(uuid)) {
                    return entity;
                }
            }
        }
        return null;
    }
    
    public static ArrayList<Block> getNearbyBlocks(final SandBotManager s) {
        final ArrayList<Block> blocks = new ArrayList<Block>();
        final Integer offsetX = Utils.plugin.getConfig().getInt("bot-fill-radius");
        final Integer offsetY = Utils.plugin.getConfig().getInt("bot-fill-radius");
        final Integer offsetZ = Utils.plugin.getConfig().getInt("bot-fill-radius");
        for (int x = s.getLocation().getBlockX() - offsetX; x <= s.getLocation().getBlockX() + offsetX; ++x) {
            for (int y = s.getLocation().getBlockY() - offsetY; y <= s.getLocation().getBlockY() + offsetY; ++y) {
                for (int z = s.getLocation().getBlockZ() - offsetZ; z <= s.getLocation().getBlockZ() + offsetZ; ++z) {
                    final Block block = s.getSandbotModel().getWorld().getBlockAt(x, y, z);
                    if (block.getType().equals(Material.LAPIS_BLOCK) && FactionUtils.getFactionAt(block.getLocation()).getId() != null && FactionUtils.getFactionAt(block.getLocation()).getId().equals(s.getFactionID())) {
                        blocks.add(s.getSandbotModel().getWorld().getBlockAt(x, y, z));
                    }
                }
            }
        }
        return blocks;
    }
    
    public static ItemStack botItem(final String name, final List<String> lore, final SandBotManager bot) {
        final ArrayList<String> updatedPlaceholdersLore = new ArrayList<String>();
        for (final String s : lore) {
            updatedPlaceholdersLore.add(s.replace("%botowner%", bot.getOwnerName()).replace("%botfaction%", FactionUtils.getFaction(FPlayers.i.get(Bukkit.getServer().getOfflinePlayer(bot.getOwnerUUID()))).getTag().replace("%balance%", bot.getBalance().toString()).replace("%isActivated%", bot.isActivated() ? Utils.plugin.getConfig().getString("bot-core-enabled-placeholder") : Utils.plugin.getConfig().getString("bot-core-disabled-placeholder")).replace("%blocksd%", String.valueOf(bot.getLapisBlocks().size())).replace("%botlocationx%", String.valueOf(bot.getLocation().getX())).replace("%botlocationy%", String.valueOf(bot.getLocation().getY())).replace("%botlocationz%", String.valueOf(bot.getLocation().getZ()))));
        }
        return new ItemBuilder(Material.SKULL).setSkullOwner(bot.getOwnerName()).name(name).lore(updatedPlaceholdersLore).build();
    }
    
    public static ItemStack botItem(final String name, final List<String> lore, final SandBotManager bot, final ItemStack item) {
        final ArrayList<String> updatedPlaceholdersLore = new ArrayList<String>();
        for (final String s : lore) {
            updatedPlaceholdersLore.add(s.replace("%botowner%", bot.getOwnerName()).replace("%botfaction%", FactionUtils.getFaction(FPlayers.i.get(Bukkit.getServer().getOfflinePlayer(bot.getOwnerUUID()))).getTag().replace("%balance%", bot.getBalance().toString()).replace("%isActivated%", bot.isActivated() ? Utils.plugin.getConfig().getString("bot-core-enabled-placeholder") : Utils.plugin.getConfig().getString("bot-core-disabled-placeholder")).replace("%blocksd%", String.valueOf(bot.getLapisBlocks().size())).replace("%botlocationx%", String.valueOf(bot.getLocation().getX())).replace("%botlocationy%", String.valueOf(bot.getLocation().getY())).replace("%botlocationz%", String.valueOf(bot.getLocation().getZ()))));
        }
        return new ItemBuilder(item).name(name).lore(updatedPlaceholdersLore).build();
    }
    
    public static void removeBot(final SandBotManager bot) {
        for (final World world : Bukkit.getWorlds()) {
            for (final Entity entity : world.getEntities()) {
                if (entity.getUniqueId().equals(bot.getSandbotUUID())) {
                    entity.remove();
                }
            }
        }
        removeBotFromConfigIfExist(Utils.plugin.getManager(), bot);
        SandBots.botGuis.remove(bot);
        SandBots.sandBots.remove(bot);
    }
    
    public static boolean getArmorAnimateBoolean() {
        return Utils.plugin.getConfig().getBoolean("bot-animate-armor");
    }
    
    public static void logToServer(final String text, final Boolean prefix) {
        Bukkit.getServer().getConsoleSender().sendMessage((prefix ? "[LunarSandBot] " : "") + text);
    }
    
    static {
        Utils.plugin = (SandBots) SandBots.getPlugin((Class) SandBots.class);
        Utils.enabledArmorColor = Color.fromRGB(Utils.plugin.getConfig().getInt("bot-enabled-red"), Utils.plugin.getConfig().getInt("bot-enabled-green"), Utils.plugin.getConfig().getInt("bot-enabled-blue"));
        Utils.disabledArmorColor = Color.fromRGB(Utils.plugin.getConfig().getInt("bot-disabled-red"), Utils.plugin.getConfig().getInt("bot-disabled-green"), Utils.plugin.getConfig().getInt("bot-disabled-blue"));
    }
}
