package net.astropvp.adminitems.items;

import com.cosmicpvp.cosmicutils.utils.WorldGuardUtils;
import net.astropvp.adminitems.AstroAdminItemsAPI;
import net.astropvp.adminitems.cooldowns.AbilityCooldown;
import net.astropvp.adminitems.language.Language;
import net.astropvp.adminitems.struct.AdminItem;
import net.astropvp.adminitems.struct.Throwable;
import net.astropvp.adminitems.struct.annotations.ConfigValue;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.util.Vector;

import java.util.*;

import org.bukkit.inventory.*;
import org.bukkit.event.*;
import org.bukkit.event.entity.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.CooldownUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class SwitcherBall extends AdminItem implements Throwable {

    @ConfigValue()
    private int cooldown = 60;

    private Map<UUID, Player> snowballPlayers = new HashMap<>();


    @Override
    public String getName() {
        return "SwitcherBall";
    }

    @Override
    public String getDisplayName() {
        return "&d&lSwitcher Ball";
    }

    @Override
    public List<String> getPerks() {
        return Arrays.asList(
                "&7Upon hit, your location will be switched",
                "&7with the person you hit with the ball.",
                "&7There is a 60 seconds cooldown",
                "&7between every switcher ball use."
        );
    }

    @Override
    public List<String> getLore() {
        return Arrays.asList(
                "&7&oEver wanted to know how it is",
                "&7&oto step in someone else's shoes?"
        );
    }


    @Override
    public ChatColor getColor() {
        return ChatColor.LIGHT_PURPLE;
    }

    @Override
    public Material getMaterial() {
        return Material.SNOW_BALL;
    }

    @Override
    public String getCooldownID() {
        return "switcherBall";
    }


    @EventHandler
    public void onPlayerLaunchEgg(ProjectileLaunchEvent e) {
        if (!(e.getEntity() instanceof Snowball)) {
            return;
        }
        if (!(e.getEntity().getShooter() instanceof Player)) {
            return;
        }
        final Player shooter = (Player) e.getEntity().getShooter();
        if (!AstroAdminItemsAPI.isAdminItem(shooter.getItemInHand())) {
            return;
        }

        if (!AstroAdminItemsAPI.canUseAdminItem(shooter, shooter.getItemInHand(), getCooldownID(), this)) {
            return;
        }

        this.snowballPlayers.put(e.getEntity().getUniqueId(), ((Player) e.getEntity().getShooter()).getPlayer());
    }


    @EventHandler
    public void onPlayerHitBySnowball(EntityDamageByEntityEvent e) {
        if (!(e.getEntity() instanceof Player)) {
            return;
        }
        if (!(e.getDamager() instanceof Snowball)) {
            return;
        }
        final Player hit = (Player) e.getEntity();
        final Entity damager = e.getDamager();
        final Snowball snowball = (Snowball) damager;
        if (!(snowball.getShooter() instanceof Player)) {
            return;
        }
        final Player shooter = (Player) snowball.getShooter();
        if (!this.snowballPlayers.containsKey(snowball.getUniqueId())) {
            return;
        }

        if (this.snowballPlayers.get(snowball.getUniqueId()) != shooter) {
            return;
        }

        if (!WorldGuardUtils.isPvPEnabled(shooter.getLocation()) || !WorldGuardUtils.isPvPEnabled(hit.getLocation())) return;

        this.snowballPlayers.remove(snowball.getUniqueId());
        final Location loc = shooter.getLocation();
        shooter.teleport(hit, PlayerTeleportEvent.TeleportCause.UNKNOWN);
        hit.teleport(loc, PlayerTeleportEvent.TeleportCause.UNKNOWN);
        shooter.sendMessage(CC.translate(Language.SWITCHERED.getMessage().replace("%shot%", hit.getName())));
        hit.sendMessage(CC.translate(Language.SWITCHERED_BY_PLAYER.getMessage().replace("%player%", shooter.getName())));
        CooldownUtils.registerCooldown(shooter, new AbilityCooldown(getCooldownID(), cooldown));

    }

    @EventHandler
    public void onHit(ProjectileHitEvent event) {
        if (event.getEntity() instanceof Snowball) {
            if (snowballPlayers.containsKey(event.getEntity().getUniqueId())) {
                Player shooter = snowballPlayers.get(event.getEntity().getUniqueId());
                Language.SWITCHER_HIT_NONE.send(shooter);
                //shooter.setItemInHand(AdminItemsPlugin.getInstance().getAdminItemRegister().getAdminItemFromID("SwitcherBall").getItem(1));
                snowballPlayers.remove(event.getEntity().getUniqueId());
            }
        }


    }


}
