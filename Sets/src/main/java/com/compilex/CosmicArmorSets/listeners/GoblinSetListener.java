package com.compilex.CosmicArmorSets.listeners;

import com.compilex.CosmicArmorSets.CosmicArmorSetsAPI;
import com.compilex.CosmicArmorSets.CosmicArmorSetsPlugin;
import com.compilex.CosmicArmorSets.enums.ArmorSet;
import com.compilex.CosmicArmorSets.enums.CachedGoblinSet;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.FactionUtils;
import com.cosmicpvp.cosmicutils.utils.WorldGuardUtils;
import com.massivecraft.factions.Faction;
import me.astropvp.astrospawners.utils.ActionBarMessageUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import java.util.ArrayList;
import java.util.List;

public class GoblinSetListener implements Listener {
    @EventHandler
    public void onHit(EntityDamageByEntityEvent e) {
        if (!WorldGuardUtils.isPvPEnabled(e.getDamager().getLocation()) || !WorldGuardUtils.isPvPEnabled(e.getEntity().getLocation()))
            return;
        if (e.isCancelled()) return;
        if (e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
            Player damager = (Player) e.getDamager();

            if (!CosmicArmorSetsAPI.getBonusCrystalsEquipped(damager).containsKey(ArmorSet.GOBLIN) && CosmicArmorSetsAPI.getArmorSet(damager) == null || CosmicArmorSetsAPI.getArmorSet(damager) != ArmorSet.GOBLIN) {
                return;
            }

            CachedGoblinSet set = CosmicArmorSetsPlugin.get_().getGoblinSetManager().getCachedGoblinSet(damager);
            if (set.getLastHit() != -1 && System.currentTimeMillis() - set.getLastHit() < set.getLastHit() + 30000)
                set.setLastHit(-1);
            set.setDamageDealt(set.getDamageDealt() + e.getFinalDamage());
            ActionBarMessageUtils.sendMessage(damager, CC.translate("&e&lSuper Charge: &7" + (int)set.getDamageDealt() + "/500"));
            set.setLastHit(System.currentTimeMillis());
        }
    }

    private void procAbility(Player damaged) {
        List<Entity> nearby = damaged.getNearbyEntities(16, 16, 16);
        for (Entity entity : nearby) {
            if (!(entity instanceof Player))
                continue;
            Player player = (Player) entity;
            player.playSound(player.getLocation(), Sound.EXPLODE, 1.0f, 1.0f);
            player.sendMessage(CC.translate("&6&l*&e&l*&6&l* &e&lGOBLIN ARMOR SET ABILITY &7(" + damaged.getName() + ") &6&l*&e&l*&6&l*"));
        }
        removeNearbyBlocks(damaged, damaged.getLocation());
    }

    @EventHandler
    public void onSneak(PlayerToggleSneakEvent p) {
        if (!WorldGuardUtils.isPvPEnabled(p.getPlayer().getLocation()) || !WorldGuardUtils.isPvPEnabled(p.getPlayer().getLocation()))
            return;
        if (!CosmicArmorSetsAPI.getBonusCrystalsEquipped(p.getPlayer()).containsKey(ArmorSet.GOBLIN) && CosmicArmorSetsAPI.getArmorSet(p.getPlayer()) == null || CosmicArmorSetsAPI.getArmorSet(p.getPlayer()) != ArmorSet.GOBLIN) {
            return;
        }

        CachedGoblinSet set = CosmicArmorSetsPlugin.get_().getGoblinSetManager().getCachedGoblinSet(p.getPlayer());
        if (set == null ) return;

        if (set.getDamageDealt() >= 500 || p.getPlayer().isOp()) {
            procAbility(p.getPlayer());
            set.setDamageDealt(0);
            p.getPlayer().sendMessage("");
            p.getPlayer().sendMessage(CC.translate(CC.translate("&6&l*&e&l*&6&l* &e&lGOBLIN ARMOR SET ABILITY &6&l*&e&l*&6&l*")));
            p.getPlayer().sendMessage(CC.translate("&7&o(( Removing all doors, strings, beds, enchant tables... ))"));
            p.getPlayer().sendMessage("");
        }

    }

    private void removeNearbyBlocks(Player player ,Location location) {
        for (int x = location.getBlockX() - 5; x <= location.getBlockX()+5; x++) {
            for (int y = location.getBlockY() - 5; y <= location.getBlockY()+5; y++) {
                for (int z = location.getBlockZ() - 5; z <= location.getBlockZ()+5; z++) {
                    Material m = location.getWorld().getBlockAt(x, y, z).getType();
                    Faction faction = FactionUtils.getFaction(player);
                    if (FactionUtils.getFactionAt(location.getWorld().getBlockAt(x, y, z).getLocation()) == faction) continue;
                    if (FactionUtils.isWarZone(location.getWorld().getBlockAt(x, y, z).getLocation())) continue;
                    if (!WorldGuardUtils.isPvPEnabled(location.getWorld().getBlockAt(x, y, z).getLocation())) continue;
                    if (m.name().contains("DOOR") || m.name().contains("SLAB") || m == Material.BED || m == Material.ENCHANTMENT_TABLE || m == Material.SIGN_POST || m.name().contains("PLATE") || m.name().contains("STAIR") || m.name().contains("CHEST") || m == Material.STRING) {
                        location.getWorld().getBlockAt(x, y, z).setType(Material.AIR);
                    }
                }
            }
        }
    }


}
