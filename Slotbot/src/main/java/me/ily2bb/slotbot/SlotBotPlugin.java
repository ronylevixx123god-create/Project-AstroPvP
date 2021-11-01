package me.ily2bb.slotbot;

import com.cosmicpvp.cosmicutils.utils.CC;
import me.astropvp.astrospawners.AstroSpawners;
import me.astropvp.astrospawners.utils.PlayerData;
import me.astropvp.astrospawners.utils.PlayerDataManager;
import me.ily2bb.slotbot.commands.CommandSkinCommand;
import me.ily2bb.slotbot.commands.CommandSlotBot;
import me.ily2bb.slotbot.listener.SlotBotInventoryListener;
import me.ily2bb.slotbot.listener.WitchFix;
import me.ily2bb.slotbot.managers.SlotBotManager;
import me.ily2bb.slotbot.objects.Glow;
import me.ily2bb.slotbot.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class SlotBotPlugin extends JavaPlugin {
    public static SlotBotPlugin me;
    FileConfiguration config;

    public SlotBotPlugin() {
        this.config = this.getConfig();
    }

    public static SlotBotPlugin getInstance() {
        return SlotBotPlugin.me;
    }

    public void onEnable() {
        final File file = new File(this.getDataFolder() + File.separator + "config.yml");
        if (file.exists()) {
            this.getLogger().info(ChatColor.translateAlternateColorCodes('&', "&aSuccessfully loaded Slot Bots..."));
        } else {
            final String l = Utils.toBase64(new ItemStack[]{new ItemStack(Material.EMERALD)}, 9) + ":show:1";
            final List<String> list = Arrays.asList(l);
            this.getConfig().addDefault("me.astropvp.cratekeys.items", list);
            this.getConfig().addDefault("slot-bot-enabled", true);
            this.getLogger().info(ChatColor.translateAlternateColorCodes('&', "&aSuccessfully created config for Slot Bots..."));
        }
        this.loadConfig();
        this.config = this.getConfig();
        this.registerListeners();
        this.registerCommands();
        this.registerGlow();

        SlotBotPlugin.me = this;
    }

    public void loadConfig() {
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();
    }

    public void onDisable() {
        SlotBotPlugin.me = null;
    }

    public void registerListeners() {
        this.registerListener(new SlotBotInventoryListener());
        this.registerListener(new WitchFix());
    }

    public void registerCommands() {
        this.getCommand("slot").setExecutor(new CommandSlotBot());
        this.getCommand("addskin").setExecutor(new CommandSkinCommand());
    }

    public void registerListener(final Listener listener) {
        Bukkit.getServer().getPluginManager().registerEvents(listener, this);
    }

    public void registerGlow() {
        try {
            final Field f = Enchantment.class.getDeclaredField("acceptingNew");
            f.setAccessible(true);
            f.set(null, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            final Glow glow = new Glow(70);
            Enchantment.registerEnchantment((Enchantment) glow);
        } catch (IllegalArgumentException ex) {
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
