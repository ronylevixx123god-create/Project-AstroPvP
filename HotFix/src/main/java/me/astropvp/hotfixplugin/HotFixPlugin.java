package me.astropvp.hotfixplugin;

import me.astropvp.astropvpskins.commands.CommandMultiSkin;
import me.astropvp.hotfixplugin.commands.FixSlotsCommand;
import me.astropvp.hotfixplugin.listeners.*;
import me.ifamasssxd.cosmicduel.CosmicDuel;
import me.ifamasssxd.cosmicduel.DuelAPI;
import me.ifamasssxd.cosmicduel.struct.enums.CancelReason;
import me.ifamasssxd.cosmicduel.struct.enums.DuelState;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class HotFixPlugin extends JavaPlugin {
    private static HotFixPlugin instance;


    @Override
    public void onEnable() {
        instance = this;
        System.out.println("[HotFix] HotFixPlugin has been Loaded!");
        Bukkit.getPluginManager().registerEvents(new DamageListener(), this);
        Bukkit.getPluginManager().registerEvents(new TeleportSafetyListener(), this);

        getCommand("fixslot").setExecutor(new FixSlotsCommand());
        Bukkit.getPluginManager().registerEvents(new SpawnersFix(), this);
        new ProtocolListener();


    }

    @Override
    public void onDisable() {
        instance = null;
    }

    public static HotFixPlugin getInstance() {
        return instance;
    }
}
