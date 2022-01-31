package me.astropvp.astropvpskins.objects.skins.amulets;

import me.astropvp.astropvpskins.AstroItemSkinsPlugin;
import me.astropvp.astropvpskins.objects.ItemSkin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.ArrayList;
import java.util.List;

public class TheCrumbleSkin extends ItemSkin {
    private List<Player> crumbledPlayers = new ArrayList<>();

    @Override
    public void onAttacked(Player damaged, Entity damager, EntityDamageByEntityEvent event) {
        if(this.crumbledPlayers.contains(damaged)) {
            event.setDamage(event.getDamage() * 0.9);
        } else if(damaged.hasMetadata("divineFire")) {
            if(Math.random() < 0.075) {
                this.crumbledPlayers.add(damaged);
                Bukkit.getScheduler().runTaskLaterAsynchronously(AstroItemSkinsPlugin.getInstance(), () -> this.crumbledPlayers.remove(damaged), 160L);
            }
        }
    }

    @Override
    public void onAttack(Player damager, Entity damaged, EntityDamageByEntityEvent event) {
        if(!(damaged instanceof Player))
            return;
        if(((Player) damaged).getHealth() < 15) {
            if(Math.random() < 0.05) {
                damaged.setMetadata("noPhoenixProcs", new FixedMetadataValue(AstroItemSkinsPlugin.getInstance(), true));
                Bukkit.getScheduler().runTaskLaterAsynchronously(AstroItemSkinsPlugin.getInstance(), () -> damaged.removeMetadata("noPhoenixProcs", AstroItemSkinsPlugin.getInstance()), 120L);
            }
        }
    }
}
