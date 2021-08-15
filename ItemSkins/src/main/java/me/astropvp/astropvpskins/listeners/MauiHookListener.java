package me.astropvp.astropvpskins.listeners;

import com.cosmicpvp.cosmicutils.utils.CC;
import java.util.List;

import com.cosmicpvp.cosmicutils.utils.WorldGuardUtils;
import me.astropvp.astropvpskins.AstroItemSkinsAPI;
import me.astropvp.astropvpskins.AstroItemSkinsPlugin;
import me.astropvp.astropvpskins.enums.SkinType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.github.paperspigot.Title;

public class MauiHookListener implements Listener {
    @EventHandler
    public void onMauiHook(EntityDamageByEntityEvent e) {
        if (!(e.getDamager() instanceof Player))
            return;
        if (!(e.getEntity() instanceof Player))
            return;
        if (Math.random() > 0.06D)
            return;
        if (!AstroItemSkinsAPI.hasSkinEquipped((Player)e.getDamager(), SkinType.SUMMER_2021_SWORD))
            return;
        if (!WorldGuardUtils.isPvPEnabled(e.getDamager().getLocation())) {
            return;
        }

        if (!WorldGuardUtils.isPvPEnabled(e.getEntity().getLocation())) {
            return;
        }
        if (e.getDamager().hasMetadata("mauiEffect") && ((MetadataValue)e.getDamager().getMetadata("mauiEffect").get(0)).asInt() == 10 && Math.random() < 0.3D) {
            e.getDamager().sendMessage(CC.translate("&c&l* MAUI ABILITY is Ready for Use &c&l*"));
            return;
        }
        if (!e.getDamager().hasMetadata("mauiEffect")) {
            e.getDamager().setMetadata("mauiEffect", (MetadataValue)new FixedMetadataValue((Plugin)AstroItemSkinsPlugin.getInstance(), Integer.valueOf(1)));
        } else {
            e.getDamager().setMetadata("mauiEffect", (MetadataValue)new FixedMetadataValue((Plugin)AstroItemSkinsPlugin.getInstance(), Integer.valueOf(((MetadataValue)e.getDamager().getMetadata("mauiEffect").get(0)).asInt() + 1)));
        }
        e.getDamager().sendMessage(CC.translate("&c&l* MAUI'S HOOK &7[&fStack x" + ((MetadataValue)e.getDamager().getMetadata("mauiEffect").get(0)).asInt() + "&7] &c&l*"));
    }

    @EventHandler
    public void sneakMauiEffect(PlayerToggleSneakEvent e) {
        if (e.isSneaking() && e.getPlayer().hasMetadata("mauiEffect") && AstroItemSkinsAPI.hasSkinEquipped(e.getPlayer(), SkinType.SUMMER_2021_SWORD) && (
                (MetadataValue)e.getPlayer().getMetadata("mauiEffect").get(0)).asInt() >= 10) {
            List<Entity> nearbyPlayers = e.getPlayer().getNearbyEntities(8.0D, 8.0D, 8.0D);
            if (nearbyPlayers.isEmpty())
                e.getPlayer().sendMessage(CC.translate("&cNo nearby enemies to activate Transcendence Ability!"));
            e.getPlayer().removeMetadata("mauiEffect", (Plugin)AstroItemSkinsPlugin.getInstance());
            e.getPlayer().sendMessage(CC.translate("&c&l* TRANSCENDENCE ABILITY &con Nearby Players &7(8x8) &c&l*"));
            e.getPlayer().sendMessage(CC.translate("&7(Damaging enemies, Silencing them and their Pets.)"));
            e.getPlayer().sendTitle(new Title(CC.translate("&c&l* Transcendence Ability *"), CC.translate("&7Maui's Stack Reset"), 10, 20, 10));
            for (Entity entity : nearbyPlayers) {
                if (!(entity instanceof Player))
                    continue;
                final Player target = ((Player)entity).getPlayer();
                target.setMetadata("transEffect", (MetadataValue)new FixedMetadataValue((Plugin)AstroItemSkinsPlugin.getInstance(), ""));
                target.sendTitle(new Title(CC.translate("&c&l* &f" + e.getPlayer().getName() + "'s &cTranscendence Ability &c&l*"), CC.translate("&7Silenced (Pets) and Massive Damage"), 10, 20, 10));
                target.playSound(target.getLocation(), Sound.WITHER_HURT, 1.0F, 0.25F);
                target.sendMessage(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "* SILENCED " + ChatColor.GRAY + " [7s] " + ChatColor.DARK_PURPLE + ChatColor.BOLD + "*");
                target.setMetadata("noDefenseProcs", (MetadataValue)new FixedMetadataValue((Plugin)AstroItemSkinsPlugin.getInstance(), Boolean.valueOf(true)));
                Bukkit.getScheduler().runTaskLaterAsynchronously((Plugin)AstroItemSkinsPlugin.getInstance(), () -> target.removeMetadata("noDefenseProcs", (Plugin)AstroItemSkinsPlugin.getInstance()), 140L);
                Bukkit.getScheduler().runTaskLaterAsynchronously((Plugin)AstroItemSkinsPlugin.getInstance(), () -> target.damage(2.0D), 140L);
                (new BukkitRunnable() {
                    int count = 0;

                    public void run() {
                        if (this.count >= 20)
                            cancel();
                        this.count++;
                        target.damage(4.0D);
                    }
                }).runTaskTimer((Plugin)AstroItemSkinsPlugin.getInstance(), 0L, 10L);
                Bukkit.getScheduler().runTaskLaterAsynchronously((Plugin)AstroItemSkinsPlugin.getInstance(), () -> {
                    target.sendMessage(CC.translate("&a&lPET: &aYour Pets are no longer Silenced."));
                    target.removeMetadata("transEffect", (Plugin)AstroItemSkinsPlugin.getInstance());
                },200L);
            }
        }
    }
}
