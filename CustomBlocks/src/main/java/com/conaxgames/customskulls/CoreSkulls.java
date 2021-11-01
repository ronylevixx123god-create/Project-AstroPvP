package com.conaxgames.customskulls;

import com.conaxgames.customskulls.ability.AbilityData;
import com.conaxgames.customskulls.blocks.CustomBlock;
import com.conaxgames.customskulls.blocks.impl.*;
import com.conaxgames.customskulls.commands.AbilityBlocksCommand;
import com.conaxgames.customskulls.data.LoadCustomBlocks;
import com.conaxgames.customskulls.listeners.CustomAbilityListener;
import com.conaxgames.customskulls.listeners.CustomBlockListener;
import com.conaxgames.customskulls.listeners.SlotbotMachineGUIListener;
import com.conaxgames.customskulls.tasks.MobGrinderRunnable;
import com.conaxgames.customskulls.util.CC;
import com.conaxgames.customskulls.util.inventoryui.UIListener;
import lombok.Getter;
import me.astropvp.astrospawners.AstroSpawners;
import me.astropvp.astrospawners.utils.MobStackerUtils;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CoreSkulls extends JavaPlugin {

    @Getter public static CoreSkulls instance;
    public static Map<String, CustomBlock> customBlocks = new HashMap<>();
    private static Field bukkitCommand;
    @Getter private static Economy economy = null;

    public static Map<UUID, AbilityData> inSlotbotMachine;
    public static MobStackerUtils mobStackerUtils;


    @Override
    public void onEnable() {
        instance = this;
        getConfig().options().copyDefaults(true);
        saveConfig();
        File file = new File(this.getDataFolder() + File.separator + "me.astropvp.cratekeys.data");
        if(!file.exists()) {
            file.mkdir();
            info("Successfully made directory /me.astropvp.cratekeys.data/ :)");
        }

        if (!setupEconomy() ) {
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        registerCustomBlocks();
        Collections.singletonList(new AbilityBlocksCommand()).forEach(this:: registerCommand);
        getServer().getPluginManager().registerEvents(new UIListener(), this);
        getServer().getPluginManager().registerEvents(new CustomBlockListener(), this);
        getServer().getPluginManager().registerEvents(new CustomAbilityListener(), this);
        getServer().getPluginManager().registerEvents(new SlotbotMachineGUIListener(), this);
        mobStackerUtils = AstroSpawners.getInstance().getMobStackerUtils();
        LoadCustomBlocks.load();
        info("CoreSkulls!");
        inSlotbotMachine = new HashMap<>();
        registerTasks();

    }

    private void registerCommand(Command cmd) {
        this.registerCommand(cmd, this.getName());
    }

    @Override
    public void onDisable() {

    }

    private void registerCustomBlocks() {
        customBlocks.put("HALLOWEEN_CAULDRON", new Cauldron());
        customBlocks.put("THANKSGIVING_PIE", new PumpkinPie());
        customBlocks.put("TREASURE_CHEST", new TreasureChest());
        customBlocks.put("COSMIC_TROPHY", new AstroTrophy());
        customBlocks.put("LEVIATHAN_TOTEM", new LeviathanTotem());
        customBlocks.put("HALLOWEEN_CANDLES", new HalloweenCandle());
        customBlocks.put("COSMIC_TOKEN_GENERATOR", new SlotbotMachine());
        customBlocks.put("TRIALS_FAME_AND_FORTUNE", new MobGrinder());
    }

    public void info(String string) {
        Bukkit.getConsoleSender().sendMessage(CC.YELLOW + string);
    }

    public void registerCommand(Command cmd, String fallback) {
        try {
            if (bukkitCommand == null) {
                bukkitCommand = Bukkit.getServer().getClass().getDeclaredField("commandMap");
                bukkitCommand.setAccessible(true);
            }
            CommandMap commandMap = (CommandMap) bukkitCommand.get(Bukkit.getServer());
            commandMap.register(cmd.getName(), fallback, cmd);
        } catch(Exception e){
            e.printStackTrace();

        }
    }

    private void registerTasks() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new MobGrinderRunnable(), 15*20, 3);
    }



    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        economy = rsp.getProvider();
        return economy != null;
    }


    public static Map<UUID, AbilityData> getInSlotbotMachine() {
        return inSlotbotMachine;
    }
}
