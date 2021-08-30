package com.conaxgames.customskulls;

import com.conaxgames.customskulls.ability.AbilityData;
import com.conaxgames.customskulls.blocks.CustomBlock;
import com.conaxgames.customskulls.blocks.impl.*;
import com.conaxgames.customskulls.commands.AbilityBlocksCommand;
import com.conaxgames.customskulls.data.LoadCustomBlocks;
import com.conaxgames.customskulls.enums.AbilityType;
import com.conaxgames.customskulls.listeners.CustomAbilityListener;
import com.conaxgames.customskulls.listeners.CustomBlockListener;
import com.conaxgames.customskulls.listeners.MobGrinderGUIListener;
import com.conaxgames.customskulls.struct.MobGrinderCollection;
import com.conaxgames.customskulls.util.CC;
import com.conaxgames.customskulls.util.CustomBlockUtil;
import com.conaxgames.customskulls.util.inventoryui.UIListener;
import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import de.tr7zw.nbtinjector.NBTInjector;
import lombok.Getter;
import me.ifamasssxd.cosmicmysterychest.CosmicMysteryChest;
import me.ifamasssxd.cosmicmysterychest.struct.MysteryChest;
import net.milkbowl.vault.economy.Economy;
import org.arkhamnetwork.Arkkit.patches.ExpBottles;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.lang.reflect.Field;
import java.util.*;

public class CoreSkulls extends JavaPlugin {

    @Getter
    public static CoreSkulls instance;
    public static Map<String, CustomBlock> customBlocks = new HashMap<>();
    public static Map<String, AbilityData> mobGrinderCustomBlocks;
    private static Field bukkitCommand;
    @Getter
    private static Economy economy = null;
    private static List<MobGrinderCollection> mobGrinderCollections = new ArrayList<>();

    @Override
    public void onEnable() {
        instance = this;
        getConfig().options().copyDefaults(true);
        saveConfig();
        File file = new File(this.getDataFolder() + File.separator + "data");
        if (!file.exists()) {
            file.mkdir();
            info("Successfully made directory /data/ :)");
        }

        if (!setupEconomy()) {
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        registerCustomBlocks();
        Collections.singletonList(new AbilityBlocksCommand()).forEach(this::registerCommand);

        ItemStack grinderLootbox = CosmicMysteryChest.get().getChestManager().getMysteryChestMap().get("GrindingLootbox").createClaimableItem(false);

        getServer().getPluginManager().registerEvents(new UIListener(), this);
        getServer().getPluginManager().registerEvents(new CustomBlockListener(), this);
        getServer().getPluginManager().registerEvents(new CustomAbilityListener(), this);
        //getServer().getPluginManager().registerEvents(new MobGrinderGUIListener(), this);
        LoadCustomBlocks.load();

        mobGrinderCustomBlocks = new HashMap<>();

        for (Map.Entry<String, AbilityData> entry : AbilityData.getAbilityDataMap().entrySet()) {
            String uuid = entry.getKey();
            AbilityData data = entry.getValue();

            if (data.getAbilityType() == AbilityType.TRIALS_FAME_AND_FORTUNE) {
                mobGrinderCustomBlocks.put(data.getUuid(), data);
                mobGrinderCollections.add(new MobGrinderCollection());
                info("Loaded all the Mob Grinder CustomBlocks in memory!");
            }
        }

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
            for (Map.Entry<String, AbilityData> entry : mobGrinderCustomBlocks.entrySet()) {
                String uuid = entry.getKey();
                AbilityData data = entry.getValue();

                data.getCollectedLoot().addExp(500);
                data.getCollectedLoot().addLootboxes(1);
                Bukkit.broadcastMessage(String.valueOf("EXP: " + data.getCollectedLoot().exp));
                Bukkit.broadcastMessage(String.valueOf("Lootboxes: " + data.getCollectedLoot().lootboxes));


                if (CustomBlockUtil.getClosestChestLocation(data.getLocation()) != null) {


                    Location chestLocation = CustomBlockUtil.getClosestChestLocation(data.getLocation());
                    Chest chest = (Chest) chestLocation.getBlock().getState();
                    chest.getInventory().addItem(grinderLootbox);
                    if (!chest.getInventory().contains(Material.EXP_BOTTLE)) {
                        chest.getInventory().addItem(ExpBottles.getXpBottle(500, "Mob Grinder"));
                        return;
                    }
                    for (int i = 0; i < chest.getInventory().getSize(); i++) {
                        if (chest.getInventory().getItem(i) == null) {
                            continue;
                        } else if (ExpBottles.isXpBottle(chest.getInventory().getItem(i))) {
                            chest.getInventory().setItem(i, ExpBottles.getXpBottle(ExpBottles.getXpAmount(chest.getInventory().getItem(i)) + 500, "Mob Grinder"));
                            break;
                        }
                    }
                }
            }

        }, 15 * 20, 10 * 20);
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
        } catch (Exception e) {
            e.printStackTrace();

        }
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

}
