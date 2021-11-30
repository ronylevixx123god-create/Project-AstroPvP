package com.astropvp.cc;

import com.astropvp.cc.command.PopupCommand;
import com.astropvp.cc.command.ReloadCommand;
import com.astropvp.cc.command.VoiceChannelCommand;
import com.astropvp.cc.config.Config;
import com.astropvp.cc.injection.BuycraftInjection;
import com.astropvp.cc.listener.PlayerListener;
import com.astropvp.cc.packet.PacketCommons;
import com.astropvp.cc.packet.PacketIO;
import com.astropvp.cc.player.CosmicPlayer;
import com.astropvp.cc.voice.VoiceTask;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;

public class CCVerify extends JavaPlugin implements Listener {
    private static CCVerify i;
    public static CCVerify get(){return i;}

    @Override
    public void onEnable() {
        i = this;

        saveDefaultConfig();

        getServer().getPluginManager().registerEvents(this, this);

        PacketCommons.bootstrap();

        PacketIO.create();

        VoiceTask.start();

        Config.bootStrap(getConfig(), new File(getDataFolder(), "config.yml"));

        getCommand("voicechat").setExecutor(new VoiceChannelCommand());
        getCommand("popup").setExecutor(new PopupCommand());
        getCommand("ccreload").setExecutor(new ReloadCommand());

        getServer().getPluginManager().registerEvents(new PlayerListener(), this);

        if(Config.getInst().getPurchasefeed().isPurchaseFeedEnabled())BuycraftInjection.inject();
    }

    public void cmdReload(){
        Config.bootStrap(getConfig(), new File(getDataFolder(), "config.yml"));
    }

    @Override
    public void onDisable() {
        PacketIO.get().shutdown();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        PacketIO.get().addPlayer(event.getPlayer());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        PacketIO.get().removePlayer(event.getPlayer());
    }
}
