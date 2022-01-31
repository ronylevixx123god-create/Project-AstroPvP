package com.conaxgames.customskulls;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketListener;
import com.conaxgames.customskulls.ability.AbilityData;
import com.conaxgames.customskulls.blocks.CustomBlock;
import com.conaxgames.customskulls.blocks.impl.*;
import com.conaxgames.customskulls.commands.AbilityBlocksCommand;
import com.conaxgames.customskulls.data.LoadCustomBlocks;
import com.conaxgames.customskulls.data.SaveAllGrinderCustomBlocks;
import com.conaxgames.customskulls.data.SaveCustomBlock;
import com.conaxgames.customskulls.listeners.*;
import com.conaxgames.customskulls.struct.MobGrinderCache;
import com.conaxgames.customskulls.tasks.MobGrinderCacherTask;
import com.conaxgames.customskulls.tasks.MobGrinderRunnable;
import com.conaxgames.customskulls.util.CC;
import com.conaxgames.customskulls.util.inventoryui.UIListener;
import lombok.Getter;
import lombok.Setter;
import me.astropvp.astrospawners.AstroSpawners;
import me.astropvp.astrospawners.utils.MobStackerUtils;
import me.astropvp.cratekeys.enums.CrateType;
import me.astropvp.cratekeys.items.CrateKeyItem;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.lang.reflect.Field;
import java.util.*;

@Getter
public class CoreSkulls extends JavaPlugin {

    @Getter
    public static CoreSkulls instance;

    public static Map<String, CustomBlock> customBlocks = new HashMap<>();

    private static Field bukkitCommand;

    @Setter
    @Getter
    public static boolean isSavingGrinderBlocks = false;

    private final ItemStack astroKey = CrateKeyItem.getItem(CrateType.ASTRO);

    private final ItemStack legKey = CrateKeyItem.getItem(CrateType.LEGENDARY);


    public static Map<UUID, AbilityData> inSlotbotMachine;

    public static MobStackerUtils mobStackerUtils;


    @Getter
    public Map<String, MobGrinderCache> grinderBlocks;





    @Override
    public void onEnable() {
        instance = this;
        getConfig().options().copyDefaults(true);
        saveConfig();
        File file = new File(this.getDataFolder() + File.separator + "data");
        if(!file.exists()) {
            file.mkdir();
            info("Successfully made directory /data/ :)");
        }


        registerCustomBlocks();
        Collections.singletonList(new AbilityBlocksCommand()).forEach(this:: registerCommand);
        getServer().getPluginManager().registerEvents(new UIListener(), this);
        getServer().getPluginManager().registerEvents(new CustomBlockListener(), this);
        getServer().getPluginManager().registerEvents(new CustomAbilityListener(), this);
        getServer().getPluginManager().registerEvents(new CrateKeyGUIListener(), this);
        mobStackerUtils = AstroSpawners.getInstance().getMobStackerUtils();
        LoadCustomBlocks.load();
        inSlotbotMachine = new HashMap<>();
        grinderBlocks = new HashMap<>();
        ProtocolLibrary.getProtocolManager().addPacketListener((PacketListener)new CustomBlockPacketListener());
        registerTasks();
        info("CustomBlocks has been loaded!");


    }

    private void registerCommand(Command cmd) {
        this.registerCommand(cmd, this.getName());
    }

    @Override
    public void onDisable() {
        System.out.println("Saving all Grinder Customblocks");
        SaveAllGrinderCustomBlocks.saveAllGrinderCustomBlocks();
    }

    private void registerCustomBlocks() {
        customBlocks.put("HALLOWEEN_CAULDRON", new Cauldron());
        customBlocks.put("THANKSGIVING_PIE", new PumpkinPie());
        customBlocks.put("TREASURE_CHEST", new TreasureChest());
        customBlocks.put("COSMIC_TROPHY", new AstroTrophy());
        customBlocks.put("LEVIATHAN_TOTEM", new LeviathanTotem());
        customBlocks.put("HALLOWEEN_CANDLES", new HalloweenCandle());
        customBlocks.put("COSMIC_TOKEN_GENERATOR", new SlotbotMachine());
        customBlocks.put("BLAZE_STONE_TOTEM", new MobGrinder());
        customBlocks.put("SECURITY_SYSTEM", new CrateKeyOpener());
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
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new MobGrinderRunnable(), 15*20, 5);
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new MobGrinderCacherTask(), 5*20, 15*20);
        Bukkit.getScheduler().scheduleAsyncRepeatingTask(this, SaveAllGrinderCustomBlocks::saveAllGrinderCustomBlocks,120*20L, 1200*20);

    }


    public static Map<UUID, AbilityData> getInSlotbotMachine() {
        return inSlotbotMachine;
    }
}
