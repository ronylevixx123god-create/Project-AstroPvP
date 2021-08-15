package me.astropvp.astropvpskins.objects.skins.axes;

import me.astropvp.astropvpskins.AstroItemSkinsPlugin;
import me.astropvp.astropvpskins.objects.ItemSkin;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

public class GoldenAdmirer extends ItemSkin {
    public void onAttack(Player damager, Entity damaged, EntityDamageByEntityEvent event) {
        if(!(damaged instanceof Player))
            return;
        damaged.setMetadata("admirerSilence", (MetadataValue)new FixedMetadataValue((Plugin) AstroItemSkinsPlugin.getInstance(), System.currentTimeMillis() + 10000));
    }

    public static boolean isAdmirerSilenced(Player player) {
        return (player.hasMetadata("admirerSilence") && player.getMetadata("admirerSilence").get(0).asLong() > System.currentTimeMillis());
    }
}
