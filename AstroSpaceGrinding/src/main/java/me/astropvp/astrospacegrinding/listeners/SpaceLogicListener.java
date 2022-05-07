package me.astropvp.astrospacegrinding.listeners;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.compilex.CompilexPermissions.PermissionsAPI;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.sk89q.worldedit.internal.expression.runtime.For;
import me.astropvp.astrospacegrinding.AstroSpaceGrindingPlugin;
import me.astropvp.astrospacegrinding.data.ForceFieldData;
import net.astropvp.core.utils.OutpostUtil;
import org.arkhamnetwork.Arkkit.patches.CombatLog;
import org.arkhamnetwork.enchantments.events.CustomEnchantProcOnEntityEvent;
import org.bukkit.*;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.inventory.ItemStack;

import com.google.common.collect.Lists;

import me.astropvp.astrospacegrinding.AstroSpaceGrindingAPI;
import me.astropvp.astrospacegrinding.data.SpacePlayerData;


public class SpaceLogicListener implements Listener {


    private static List<String> allowedCommands = Lists.newArrayList();

    static {
        allowedCommands.addAll(Arrays.asList("msg", "message", "staffmode", "staff", "freeze", "lms", "ft", "filter", "r", "bless", "blessme", "response", "space", "bless", "heal", "cf", "coinflip", "jackpot",
                "feed", "f", "fix", "filter edit", "filter toggle", "ft", "bless", "r", "reply", "spawn", "warp", "pots", "filters", "outpost"));
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onWorldLeave(PlayerTeleportEvent event) {
        if (event.getTo() == null) {
            return;
        }
        if (!event.getFrom().getWorld().getName().equals(AstroSpaceGrindingAPI.getWorldName())) {
            if (event.getTo().getWorld().getName().equals(AstroSpaceGrindingAPI.getWorldName())) {
                new SpacePlayerData(event.getPlayer());
            }
            return;
        }
        Player player = event.getPlayer();

        if (!event.getFrom().getWorld().getName().equals(event.getTo().getWorld().getName())) {
            if (CombatLog.inCombat(player)) {
                return;
            }
            SpacePlayerData.remove(player);
            player.sendMessage(CC.RedB + "(!) " + CC.Red + "As a result of leaving the Space Lands, "
                    + "you have lost your /space stats and your forcefield if you had one!");
            player.playSound(player.getLocation(), Sound.AMBIENCE_THUNDER, 0.75F, 2F);
            ForceFieldData.remove(player);
            SpacePlayerData.remove(player);

        }
    }


    @EventHandler(priority = EventPriority.LOW)
    public void enchantProc(CustomEnchantProcOnEntityEvent e) {
        if (e.getEnchant().heroicEnchantment) e.setCancelled(true);
    }


    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onRightClickEgg(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }
        if (event.getClickedBlock().getType() == Material.DRAGON_EGG) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onForcefieldDamage(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player && e.getEntity() instanceof Player) {
            Player damager = (Player) e.getDamager();
            Player damaged = (Player) e.getEntity();
            if (!damager.getWorld().getName().equals(AstroSpaceGrindingAPI.getWorldName()) || !damaged.getWorld().getName().equals(AstroSpaceGrindingAPI.getWorldName())) {
                return;
            }

            SpacePlayerData dataDamager = SpacePlayerData.findPlayer(damager);
            SpacePlayerData dataDamaged = SpacePlayerData.findPlayer(damaged);

            if (ForceFieldData.findPlayer(dataDamager.getPlayer()).isActive()) {
                e.setCancelled(true);
                damager.sendMessage(CC.translate("&b&l(!)&b You cannot hit " + damaged.getName() + " because you are still on &f&lFORCEFIELD &bprotection."));
                damager.playSound(damager.getLocation(), Sound.VILLAGER_NO, 5, 5);
            }

            if (ForceFieldData.findPlayer(dataDamaged.getPlayer()).isActive()) {
                e.setCancelled(true);
                damager.sendMessage(CC.translate("&b&l(!)&b You cannot hit " + damaged.getName() + " because he is still on &f&lFORCEFIELD &bprotection."));
                damager.playSound(damager.getLocation(), Sound.VILLAGER_NO, 5, 5);
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onOutpostHit(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player && e.getEntity() instanceof Player) {
            Player damager = (Player) e.getDamager();
            Player damaged = (Player) e.getEntity();
            if (!damager.getWorld().getName().equals(AstroSpaceGrindingAPI.getWorldName()) || !damaged.getWorld().getName().equals(AstroSpaceGrindingAPI.getWorldName())) {
                return;
            }

            if (OutpostUtil.hasMarsOutpost(damager)) {
                e.setDamage(e.getDamage() * 1.25);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onFlight(PlayerToggleFlightEvent event) {
        Player player = event.getPlayer();
        if (player.isOp()) {
            return;
        }
        if (!player.getWorld().getName().equals(AstroSpaceGrindingAPI.getWorldName())) {
            return;
        }
        if (event.isFlying()) {
            player.setAllowFlight(false);
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onCommand(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        if (!player.getWorld().getName().equals(AstroSpaceGrindingAPI.getWorldName())) {
            return;
        }

        if (player.isOp()) {
            return;
        }

        if (player.hasPermission("astrocore.staffmode")) {
            return;
        }
        if (allowedCommands.contains(event.getMessage().split(" ")[0].replace("/", "").toLowerCase())) {
            return;
        }
        player.sendMessage(CC.translate(  "&c&l(!) &c" + event.getMessage() + " is denied in this world!"));
        event.setMessage("/null");
        event.setCancelled(true);
    }


    @EventHandler
    public void PlayerSpawnMob(PlayerInteractEvent e) {
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getMaterial() == Material.MONSTER_EGG && e.getPlayer().getWorld().getName().equals(AstroSpaceGrindingAPI.getWorldName())) {
            e.setCancelled(true);
            e.getPlayer().sendMessage(CC.translate("&c&l(!)&c You cannot use spawn eggs in the space."));
        }
    }


}
