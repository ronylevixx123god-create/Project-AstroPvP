/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.sk89q.bukkit.util.CommandsManagerRegistration
 *  com.sk89q.minecraft.util.commands.CommandException
 *  com.sk89q.minecraft.util.commands.CommandPermissionsException
 *  com.sk89q.minecraft.util.commands.CommandUsageException
 *  com.sk89q.minecraft.util.commands.CommandsManager
 *  com.sk89q.minecraft.util.commands.MissingNestedCommandException
 *  com.sk89q.minecraft.util.commands.WrappedCommandException
 *  com.sk89q.util.yaml.YAMLFormat
 *  com.sk89q.util.yaml.YAMLNode
 *  com.sk89q.util.yaml.YAMLProcessor
 *  com.sk89q.worldedit.blocks.ItemType
 */
package com.sk89q.commandbook;

import com.sk89q.bukkit.util.CommandsManagerRegistration;
import com.sk89q.commandbook.commands.CommandBookCommands;
import com.sk89q.commandbook.config.LegacyCommandBookConfigurationMigrator;
import com.sk89q.commandbook.session.SessionComponent;
import com.sk89q.commandbook.util.ItemUtil;
import com.sk89q.minecraft.util.commands.CommandException;
import com.sk89q.minecraft.util.commands.CommandPermissionsException;
import com.sk89q.minecraft.util.commands.CommandUsageException;
import com.sk89q.minecraft.util.commands.CommandsManager;
import com.sk89q.minecraft.util.commands.MissingNestedCommandException;
import com.sk89q.minecraft.util.commands.WrappedCommandException;
import com.sk89q.util.yaml.YAMLFormat;
import com.sk89q.util.yaml.YAMLNode;
import com.sk89q.util.yaml.YAMLProcessor;
import com.sk89q.worldedit.blocks.ItemType;
import com.zachsthings.libcomponents.AbstractComponent;
import com.zachsthings.libcomponents.InjectComponent;
import com.zachsthings.libcomponents.InjectComponentAnnotationHandler;
import com.zachsthings.libcomponents.bukkit.BasePlugin;
import com.zachsthings.libcomponents.bukkit.DefaultsFileYAMLProcessor;
import com.zachsthings.libcomponents.bukkit.YAMLNodeConfigurationNode;
import com.zachsthings.libcomponents.bukkit.YAMLProcessorConfigurationFile;
import com.zachsthings.libcomponents.config.ConfigurationFile;
import com.zachsthings.libcomponents.loader.ClassLoaderComponentLoader;
import com.zachsthings.libcomponents.loader.ConfigListedComponentLoader;
import com.zachsthings.libcomponents.loader.JarFilesComponentLoader;
import com.zachsthings.libcomponents.loader.StaticComponentLoader;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.yaml.snakeyaml.error.YAMLException;

public final class CommandBook
extends BasePlugin {
    private static CommandBook instance;
    private CommandsManager<CommandSender> commands;
    protected Map<String, Integer> itemNames;
    public boolean broadcastChanges;
    public boolean useDisplayNames;
    public boolean lookupWithDisplayNames;
    public boolean crappyWrapperCompat;

    public CommandBook() {
        instance = this;
    }

    public static CommandBook inst() {
        return instance;
    }

    public static Logger logger() {
        return CommandBook.inst().getLogger();
    }

    public static void registerEvents(Listener listener) {
        CommandBook.server().getPluginManager().registerEvents(listener, CommandBook.inst());
    }

    @Override
    public void onEnable() {
        super.onEnable();
        final CommandBook plugin = this;
        this.commands = new CommandsManager<CommandSender>(){

            public boolean hasPermission(CommandSender player, String perm) {
                return plugin.hasPermission(player, perm);
            }
        };
        final CommandsManagerRegistration cmdRegister = new CommandsManagerRegistration((Plugin)this, this.commands);
        if (this.lowPriorityCommandRegistration) {
            this.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable(){

                @Override
                public void run() {
                    cmdRegister.register(CommandBookCommands.CommandBookParentCommand.class);
                }
            }, 1L);
        } else {
            cmdRegister.register(CommandBookCommands.CommandBookParentCommand.class);
        }
    }

    @Override
    public void registerComponentLoaders() {
        File classesDir;
        File configDir = new File(this.getDataFolder(), "config/");
        this.componentManager.addComponentLoader(new StaticComponentLoader(this.getLogger(), configDir, new AbstractComponent[]{new SessionComponent()}){

            @Override
            public ConfigurationFile createConfigurationNode(File file) {
                return new YAMLProcessorConfigurationFile(new YAMLProcessor(file, true, YAMLFormat.EXTENDED));
            }
        });
        DefaultsFileYAMLProcessor jarComponentAliases = new DefaultsFileYAMLProcessor("components.yml", false);
        try {
            jarComponentAliases.load();
        }
        catch (IOException e) {
            this.getLogger().severe("Error loading component aliases!");
            e.printStackTrace();
        }
        catch (YAMLException e) {
            this.getLogger().severe("Error loading component aliases!");
            e.printStackTrace();
        }
        this.componentManager.addComponentLoader(new ConfigListedComponentLoader(this.getLogger(), new YAMLNodeConfigurationNode((YAMLNode)this.config), new YAMLNodeConfigurationNode((YAMLNode)jarComponentAliases), configDir));
        for (String dir : this.config.getStringList("component-class-dirs", Arrays.asList("component-classes"))) {
            classesDir = new File(this.getDataFolder(), dir);
            if (!classesDir.exists() || !classesDir.isDirectory()) {
                classesDir.mkdirs();
            }
            this.componentManager.addComponentLoader(new ClassLoaderComponentLoader(this.getLogger(), classesDir, configDir){

                @Override
                public ConfigurationFile createConfigurationNode(File file) {
                    return new YAMLProcessorConfigurationFile(new YAMLProcessor(file, true, YAMLFormat.EXTENDED));
                }
            });
        }
        for (String dir : this.config.getStringList("component-jar-dirs", Arrays.asList("component-jars"))) {
            classesDir = new File(this.getDataFolder(), dir);
            if (!classesDir.exists() || !classesDir.isDirectory()) {
                classesDir.mkdirs();
            }
            this.componentManager.addComponentLoader(new JarFilesComponentLoader(this.getLogger(), classesDir, configDir){

                @Override
                public ConfigurationFile createConfigurationNode(File file) {
                    return new YAMLProcessorConfigurationFile(new YAMLProcessor(file, true, YAMLFormat.EXTENDED));
                }
            });
        }
        this.componentManager.registerAnnotationHandler(InjectComponent.class, new InjectComponentAnnotationHandler(this.componentManager));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        try {
            this.commands.execute(cmd.getName(), args, (Object)sender, new Object[]{sender});
        }
        catch (CommandPermissionsException e) {
            sender.sendMessage((Object)((Object)ChatColor.RED) + "You don't have permission.");
        }
        catch (MissingNestedCommandException e) {
            sender.sendMessage((Object)((Object)ChatColor.RED) + e.getUsage());
        }
        catch (CommandUsageException e) {
            sender.sendMessage((Object)((Object)ChatColor.RED) + e.getMessage());
            sender.sendMessage((Object)((Object)ChatColor.RED) + e.getUsage());
        }
        catch (WrappedCommandException e) {
            if (e.getCause() instanceof NumberFormatException) {
                sender.sendMessage((Object)((Object)ChatColor.RED) + "Number expected, string received instead.");
            } else {
                sender.sendMessage((Object)((Object)ChatColor.RED) + "An error has occurred. See console.");
                e.printStackTrace();
            }
        }
        catch (CommandException e) {
            sender.sendMessage((Object)((Object)ChatColor.RED) + e.getMessage());
        }
        return true;
    }

    @Override
    public YAMLProcessor createConfiguration() {
        File configFile = new File(this.getDataFolder(), "config.yml");
        YAMLProcessor config = new YAMLProcessor(configFile, true, YAMLFormat.EXTENDED);
        DefaultsFileYAMLProcessor comments = new DefaultsFileYAMLProcessor("config-comments.yml", false);
        try {
            if (!configFile.exists()) {
                configFile.getParentFile().mkdirs();
                configFile.createNewFile();
            }
            config.load();
            comments.load();
        }
        catch (Exception e) {
            this.getLogger().log(Level.WARNING, "Error loading configuration: ", e);
        }
        for (Map.Entry e : comments.getMap().entrySet()) {
            if (e.getValue() == null) continue;
            config.setComment((String)e.getKey(), e.getValue().toString());
        }
        String result = new LegacyCommandBookConfigurationMigrator(configFile, config).migrate();
        if (result != null) {
            CommandBook.logger().severe("Error migrating CommandBook configuration: " + result);
        }
        return config;
    }

    @Override
    public void populateConfiguration(YAMLProcessor config) {
        this.loadItemList(config);
        this.useDisplayNames = config.getBoolean("use-display-names", true);
        this.lookupWithDisplayNames = config.getBoolean("lookup-with-display-names", true);
        this.broadcastChanges = config.getBoolean("broadcast-changes", true);
        this.crappyWrapperCompat = config.getBoolean("crappy-wrapper-compat", true);
        if (this.crappyWrapperCompat) {
            this.getLogger().info("Maximum wrapper compatibility is enabled. Some features have been disabled to be compatible with poorly written server wrappers.");
        }
    }

    protected void loadItemList(YAMLProcessor config) {
        Object itemNamesTemp = config.getProperty("item-names");
        if (itemNamesTemp != null && itemNamesTemp instanceof Map) {
            this.itemNames = new HashMap<String, Integer>();
            try {
                Map temp = (Map)itemNamesTemp;
                for (Map.Entry entry : temp.entrySet()) {
                    String name = entry.getKey().toString().toLowerCase();
                    if (!(entry.getValue() instanceof Integer)) continue;
                    this.itemNames.put(name, (Integer)entry.getValue());
                }
            }
            catch (ClassCastException classCastException) {}
        } else {
            this.itemNames = new HashMap<String, Integer>();
        }
    }

    public ItemStack getItem(String name) {
        try {
            return this.getCommandItem(name);
        }
        catch (CommandException e) {
            return null;
        }
    }

    public ItemStack getCommandItem(String name) throws CommandException {
        int id;
        String[] parts;
        int dmg = 0;
        String dataName = null;
        String enchantmentName = null;
        if (name.contains("|")) {
            parts = name.split("\\|");
            name = parts[0];
            enchantmentName = parts[1];
        }
        if (name.contains(":")) {
            parts = name.split(":", 2);
            dataName = parts[1];
            name = parts[0];
        }
        try {
            id = Integer.parseInt(name);
        }
        catch (NumberFormatException e) {
            Integer idTemp = CommandBook.inst().itemNames.get(name.toLowerCase());
            if (idTemp != null) {
                id = idTemp;
            }
            ItemType type = ItemType.lookup((String)name);
            if (type == null) {
                throw new CommandException("No item type known by '" + name + "'");
            }
            id = type.getID();
        }
        if (dataName != null) {
            dmg = ItemUtil.matchItemData(id, dataName);
        }
        ItemStack stack = new ItemStack(id, 1, (short)dmg);
        if (enchantmentName != null) {
            String[] enchantments;
            for (String enchStr : enchantments = enchantmentName.split(",")) {
                int level = 1;
                if (enchStr.contains(":")) {
                    String[] parts2 = enchStr.split(":");
                    enchStr = parts2[0];
                    try {
                        level = Integer.parseInt(parts2[1]);
                    }
                    catch (NumberFormatException ignore) {
                        // empty catch block
                    }
                }
                Enchantment ench = null;
                String testName = enchStr.toLowerCase().replaceAll("[_\\-]", "");
                for (Enchantment possible : Enchantment.values()) {
                    if (!possible.getName().toLowerCase().replaceAll("[_\\-]", "").equals(testName)) continue;
                    ench = possible;
                    break;
                }
                if (ench == null) {
                    throw new CommandException("Unknown enchantment '" + enchStr + "'");
                }
                if (!ench.canEnchantItem(stack)) {
                    throw new CommandException("Invalid enchantment '" + ench.getName() + "' for item '" + name + "'");
                }
                if (ench.getMaxLevel() < level) {
                    throw new CommandException("Level '" + level + "' is above the maximum level for enchantment '" + ench.getName() + "'");
                }
                stack.addEnchantment(ench, level);
            }
        }
        return stack;
    }

    public String toInetAddressString(CommandSender sender) {
        if (sender instanceof Player) {
            return ((Player)sender).getAddress().getAddress().getHostAddress();
        }
        return "127.0.0.1";
    }
}

