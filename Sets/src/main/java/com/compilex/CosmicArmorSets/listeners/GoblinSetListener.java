package com.compilex.CosmicArmorSets.listeners;

import com.compilex.CosmicArmorSets.CosmicArmorSetsAPI;
import com.compilex.CosmicArmorSets.CosmicArmorSetsPlugin;
import com.compilex.CosmicArmorSets.enums.ArmorSet;
import com.compilex.CosmicArmorSets.enums.CachedGoblinSet;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.WorldGuardUtils;
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

import java.util.ArrayList;
import java.util.List;

public class GoblinSetListener implements Listener {
    @EventHandler
    public void onHit(EntityDamageByEntityEvent e) {
        if(!WorldGuardUtils.isPvPEnabled(e.getDamager().getLocation()) || !WorldGuardUtils.isPvPEnabled(e.getEntity().getLocation()))
            return;
        if(e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
            Player damaged = (Player) e.getEntity();
            if(!damaged.isSneaking())
                return;
            int i = 0;
            if(CosmicArmorSetsAPI.getBonusCrystalsEquipped(damaged).containsKey(ArmorSet.GOBLIN))
                i = CosmicArmorSetsAPI.getBonusCrystalsEquipped(damaged).get(ArmorSet.GOBLIN);
            if(CosmicArmorSetsAPI.getArmorSet(damaged) == ArmorSet.GOBLIN)
                i = 4;
            if(Math.random() < (i * 25)) {
                CachedGoblinSet set = CosmicArmorSetsPlugin.get_().getGoblinSetManager().getCachedGoblinSet(damaged);
                if(set.getLastHit() != -1 && System.currentTimeMillis() - set.getLastHit() < set.getLastHit() + 30000)
                    set.setLastHit(-1);
                set.setDamageDealt(set.getDamageDealt() + e.getDamage());
                ActionBarMessageUtils.sendMessage(damaged, CC.translate("&e&lSuper Charge: &7" + set.getDamageDealt() + "/8500"));
                set.setLastHit(System.currentTimeMillis());
                if(set.getDamageDealt() >= 8500) {
                    procAbility(damaged);
                    set.setDamageDealt(0);
                }
            }
        }
    }

    private void procAbility(Player damaged) {
        List<Entity> nearby = damaged.getNearbyEntities(16, 16, 16);
        for(Entity entity : nearby) {
            if(!(entity instanceof Player))
                return;
            Player player = (Player) entity;
            player.playSound(player.getLocation(), Sound.EXPLODE, 1.0f, 1.0f);
            player.sendMessage(CC.translate("&6&l*&e&l*&6&l* &e&lGOBLIN ARMOR SET ABILITY &7(" + damaged + ") &6&l*&e&l*&6&l*"));
        }
        removeNearbyBlocks(damaged.getLocation());
    }

    private void removeNearbyBlocks(Location location) {
        for(int x = location.getBlockX() - 5; x <= location.getBlockX(); x++) {
            for(int y = location.getBlockY() - 5; y <= location.getBlockY(); y++) {
                for(int z = location.getBlockZ() - 5; z <= location.getBlockZ(); z++) {
                    Material m = location.getWorld().getBlockAt(x, y, z).getType();
                    if(m.name().contains("DOOR") || m.name().contains("SLAB") || m == Material.BED || m == Material.ENCHANTMENT_TABLE || m == Material.SIGN_POST || m.name().contains("PLATE") || m.name().contains("STAIR") || m.name().contains("CHEST") || m == Material.STRING) {
                        location.getWorld().getBlockAt(x, y, z).setType(Material.AIR);
                    }
                }
            }
        }
    }


}
