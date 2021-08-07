package me.astropvp.astropvpskins.objects.skins.helmets;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.GeneralUtil;
import me.astropvp.astropvpskins.AstroItemSkinsPlugin;
import me.astropvp.astropvpskins.enums.SkinType;
import me.astropvp.astropvpskins.objects.ItemSkin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

import java.util.List;
import java.util.stream.Collectors;

public class LeviathanTrophySkin extends ItemSkin {

    public void onAttacked(Player damaged, Entity damager, EntityDamageByEntityEvent event) {
        if(!(damager instanceof Player))
            return;
        if(Math.random() < 0.03) {
            List<Player> players = (List<Player>)damager.getNearbyEntities(7.0D, 12.0D, 7.0D).stream().filter(entry -> (entry != damager)).filter(entry -> entry instanceof Player).filter(entry -> !GeneralUtil.isAtleastTruce((Player)entry, (Player) damager)).filter(it -> !it.hasMetadata("noSoulProcs")).map(entry -> (Player)entry).collect(Collectors.toList());
            for (Player p : players) {
                p.sendMessage(CC.translate("&c&l* " + damager.getName() + "'s LEVIATHAN TROPHY &7(12s Soul Enchant Silence) &c&l*"));
                p.setMetadata("noSoulProcs", (MetadataValue)new FixedMetadataValue((Plugin) AstroItemSkinsPlugin.getInstance(), Boolean.valueOf(true)));
                Bukkit.getScheduler().runTaskLaterAsynchronously((Plugin)AstroItemSkinsPlugin.getInstance(), () -> p.removeMetadata("noSoulProcs", (Plugin)AstroItemSkinsPlugin.getInstance()), 240L);
            }
            damager.sendMessage(CC.translate("&3&l* LEVIATHAN TROPHY &7(7x7 Soul Enchant Silence, " + players.size() + " enem" + ((players.size() > 1) ? "ies" : "y") + " affected) &3&l*"));
        }
    }
}
