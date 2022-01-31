package me.astropvp.astropvpskins.objects.skins.amulets;

import me.astropvp.astropvpskins.AstroItemSkinsPlugin;
import me.astropvp.astropvpskins.objects.ItemSkin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.metadata.FixedMetadataValue;

public class CrankedAmuletSkin extends ItemSkin {
    @Override
    public void onAttacked(Player damaged, Entity damager, EntityDamageByEntityEvent event) {
        if(Math.random() < 0.03) {
            damager.setMetadata("noSoulProc", new FixedMetadataValue(AstroItemSkinsPlugin.getInstance(), true));
            Bukkit.getScheduler().runTaskLaterAsynchronously(AstroItemSkinsPlugin.getInstance(), () -> {
                damager.removeMetadata("noSoulProc", AstroItemSkinsPlugin.getInstance());
            }, 140L);
        }
    }
}
