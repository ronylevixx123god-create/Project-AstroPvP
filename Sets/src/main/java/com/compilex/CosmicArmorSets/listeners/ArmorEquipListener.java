package com.compilex.CosmicArmorSets.listeners;

import com.avaje.ebean.annotation.EnumValue;
import com.compilex.CosmicArmorSets.CosmicArmorSetsAPI;
import com.compilex.CosmicArmorSets.CosmicArmorSetsPlugin;
import me.astropvp.astropvpskins.armorequip.ArmorEquipEvent;
import org.arkhamnetwork.Arkkit.patches.HotbarArmorSwap;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class ArmorEquipListener implements Listener {
    @EventHandler
    public void onArmorChange(ArmorEquipEvent e) {
        Bukkit.getScheduler().runTaskLater(CosmicArmorSetsPlugin.get_(), () -> {
            CosmicArmorSetsAPI.getArmorSet(e.getPlayer());
            CosmicArmorSetsAPI.getAbilityArmorSet(e.getPlayer());
        }, 2L);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Bukkit.getScheduler().runTaskLater(CosmicArmorSetsPlugin.get_(), () -> {
            CosmicArmorSetsAPI.getArmorSet(e.getPlayer());
            CosmicArmorSetsAPI.getAbilityArmorSet(e.getPlayer());
        }, 20L);
    }
}
