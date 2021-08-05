package me.astropvp.astropvpskins.listeners;

import com.cosmicpvp.cosmicutils.utils.CC;
import me.astropvp.astropvpskins.AstroItemSkinsAPI;
import me.astropvp.astropvpskins.AstroItemSkinsPlugin;
import me.astropvp.astropvpskins.enums.SkinType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.github.paperspigot.Title;

import java.util.List;

public class MauiHookListener implements Listener {
    @EventHandler
    public void onMauiHook(EntityDamageByEntityEvent e) {
        if (!(e.getDamager() instanceof Player)) return;
        if (!(e.getEntity() instanceof Player)) return;
        if (Math.random() > 0.06) return;
        if (!(AstroItemSkinsAPI.isInHand((Player) e.getDamager(), SkinType.SUMMER_2021_SWORD))) {
            return;
        }

        if (e.getDamager().hasMetadata("mauiEffect") && e.getDamager().getMetadata("mauiEffect").get(0).asInt() == 10 && Math.random() < 0.3) {
            e.getDamager().sendMessage(CC.translate("&c&l* MAUI ABILITY is Ready for Use &c&l*"));
            return;
        }

        if (!(e.getDamager().hasMetadata("mauiEffect"))) {
            e.getDamager().setMetadata("mauiEffect", new FixedMetadataValue(AstroItemSkinsPlugin.getInstance(), 1));

        } else {
            e.getDamager().setMetadata("mauiEffect", new FixedMetadataValue(AstroItemSkinsPlugin.getInstance(), e.getDamager().getMetadata("mauiEffect").get(0).asInt() + 1));
        }
        e.getDamager().sendMessage(CC.translate("&c&l* MAUI'S HOOK &7[&fStack x" + e.getDamager().getMetadata("mauiEffect").get(0).asInt() + "&7] &c&l*"));

    }


    @EventHandler
    public void sneakMauiEffect(PlayerToggleSneakEvent e) {

        if (e.isSneaking() && e.getPlayer().hasMetadata("mauiEffect") && AstroItemSkinsAPI.isInHand(e.getPlayer(), SkinType.SUMMER_2021_SWORD)) {
            if (e.getPlayer().getMetadata("mauiEffect").get(0).asInt() >= 10) {
                List<Entity> nearbyPlayers = e.getPlayer().getNearbyEntities(8, 8, 8);
                if (nearbyPlayers.isEmpty()) {
                    e.getPlayer().sendMessage(CC.translate("&cNo nearby enemies to activate Transcendence Ability!"));
                }
                e.getPlayer().removeMetadata("mauiEffect", AstroItemSkinsPlugin.getInstance());
                e.getPlayer().sendMessage(CC.translate("&c&l* TRANSCENDENCE ABILITY &con Nearby Players &7(8x8) &c&l*"));
                e.getPlayer().sendMessage(CC.translate("&7(Damaging enemies, Silencing them and their Pets.)"));
                e.getPlayer().sendTitle(new Title(CC.translate("&c&l* Transcendence Ability *"), CC.translate("&7Maui's Stack Reset"), 10, 20, 10));

                for (Entity entity : nearbyPlayers) {
                    if (!(entity instanceof Player)) {
                        continue;
                    }

                    Player target = ((Player) entity).getPlayer();


                    target.setMetadata("transEffect", new FixedMetadataValue(AstroItemSkinsPlugin.getInstance(), ""));
                    target.sendTitle(new Title(CC.translate("&c&l* &f" + e.getPlayer().getName() + "'s &cTranscendence Ability &c&l*"), CC.translate("&7Silenced (Pets) and Massive Damage"), 10, 20, 10));

                    target.playSound(target.getLocation(), Sound.WITHER_HURT, 1.0f, 0.25f);
                    target.sendMessage(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "* SILENCED " + ChatColor.GRAY + " [7s] " + ChatColor.DARK_PURPLE + ChatColor.BOLD + "*");
                    target.setMetadata("noDefenseProcs", (MetadataValue) new FixedMetadataValue((Plugin) AstroItemSkinsPlugin.getInstance(), Boolean.valueOf(true)));


                    Bukkit.getScheduler().runTaskLaterAsynchronously(AstroItemSkinsPlugin.getInstance(), () -> {
                        target.removeMetadata("noDefenseProcs", AstroItemSkinsPlugin.getInstance());
                    }, 140L);

                    Bukkit.getScheduler().runTaskLaterAsynchronously(AstroItemSkinsPlugin.getInstance(), () -> {
                        target.damage(2);
                    }, 140L);

                    new BukkitRunnable() {

                        int count = 0;

                        @Override
                        public void run() {
                            if(count >= 20) {
                                cancel();
                            }
                            count++;
                            target.damage(4);
                        }

                    }.runTaskTimer(AstroItemSkinsPlugin.getInstance(), 0L, 10L);

                    Bukkit.getScheduler().runTaskLaterAsynchronously(AstroItemSkinsPlugin.getInstance(), () -> {
                        target.sendMessage(CC.translate("&a&lPET: &aYour Pets are no longer Silenced."));
                        target.removeMetadata("transEffect", AstroItemSkinsPlugin.getInstance());
                    }, 200L);


                }
            }
        }
    }
}
