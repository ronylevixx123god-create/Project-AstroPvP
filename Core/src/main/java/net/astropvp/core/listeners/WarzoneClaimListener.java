package net.astropvp.core.listeners;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.WorldGuardUtils;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.event.FactionDisbandEvent;
import com.massivecraft.factions.event.LandClaimEvent;
import com.massivecraft.factions.event.LandUnclaimAllEvent;
import com.massivecraft.factions.event.LandUnclaimEvent;
import net.astropvp.core.AstroCore;
import org.bukkit.Chunk;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class WarzoneClaimListener implements Listener {
    @EventHandler
    public void onWarzoneClaim(LandClaimEvent e) {
        Faction faction = e.getFaction();
        int[] offset = {-1, 0, 1};
        for(int x : offset) {
            for(int z : offset) {
                Chunk chunk = e.getLocation().getWorld().getChunkAt(e.getLocation().getChunk().getX() + x, e.getLocation().getChunk().getZ() + z);
                if(WorldGuardUtils.getRegionName(chunk.getBlock(1, 80, 1).getLocation()).equalsIgnoreCase("infernalfull")) {
                    if(AstroCore.getInstance().getClaimsOnInfernal(faction) >= 5) {
                        e.getFPlayer().getPlayer().sendMessage(CC.RedB + "(!) " + CC.Red + "You can only have 5 claims on this warzone.");
                        e.setCancelled(true);
                    } else {
                        AstroCore.getInstance().setClaimsOnInfernal(faction, AstroCore.getInstance().getClaimsOnInfernal(faction) + 1);
                    }
                    return;
                }
                if(WorldGuardUtils.getRegionName(chunk.getBlock(1, 80, 1).getLocation()).equalsIgnoreCase("frozen")) {
                    if(AstroCore.getInstance().getClaimsOnFrozen(faction) >= 5) {
                        e.getFPlayer().getPlayer().sendMessage(CC.RedB + "(!) " + CC.Red + "You can only have 5 claims on this warzone.");
                        e.setCancelled(true);
                    } else {
                        AstroCore.getInstance().setClaimsOnFrozen(faction, AstroCore.getInstance().getClaimsOnFrozen(faction) + 1);
                    }
                    return;
                }
                if(WorldGuardUtils.getRegionName(chunk.getBlock(1, 80, 1).getLocation()).equalsIgnoreCase("desertwarzone")) {
                    if(AstroCore.getInstance().getClaimsOnDesert(faction) >= 5) {
                        e.getFPlayer().getPlayer().sendMessage(CC.RedB + "(!) " + CC.Red + "You can only have 5 claims on this warzone.");
                        e.setCancelled(true);
                    } else {
                        AstroCore.getInstance().setClaimsOnDesert(faction, AstroCore.getInstance().getClaimsOnDesert(faction) + 1);
                    }
                    return;
                }
            }
        }

    }

    @EventHandler
    public void onWarzoneUnclaim(LandUnclaimEvent e) {
        Faction faction = e.getFaction();
        int[] offset = {-1, 0, 1};
        for(int x : offset) {
            for(int z : offset) {
                Chunk chunk = e.getLocation().getWorld().getChunkAt(e.getLocation().getChunk().getX() + x, e.getLocation().getChunk().getZ() + z);
                if(WorldGuardUtils.getRegionName(chunk.getBlock(1, 80, 1).getLocation()).equalsIgnoreCase("infernal"))
                    AstroCore.getInstance().setClaimsOnInfernal(faction, AstroCore.getInstance().getClaimsOnInfernal(faction) - 1);
                if(WorldGuardUtils.getRegionName(chunk.getBlock(1, 80, 1).getLocation()).equalsIgnoreCase("frozen"))
                    AstroCore.getInstance().setClaimsOnFrozen(faction, AstroCore.getInstance().getClaimsOnFrozen(faction) - 1);
                if(WorldGuardUtils.getRegionName(chunk.getBlock(1, 80, 1).getLocation()).equalsIgnoreCase("desertwarzone"))
                    AstroCore.getInstance().setClaimsOnDesert(faction, AstroCore.getInstance().getClaimsOnDesert(faction) - 1);
            }
        }
    }

    @EventHandler
    public void onWarzoneUnclaimAll(LandUnclaimAllEvent e) {
        Faction faction = e.getFaction();
        AstroCore.getInstance().setClaimsOnFrozen(faction, 0);
        AstroCore.getInstance().setClaimsOnInfernal(faction, 0);
        AstroCore.getInstance().setClaimsOnDesert(faction, 0);
    }

    @EventHandler
    public void onFactionDisband(FactionDisbandEvent e) {
        Faction faction = e.getFaction();
        if(AstroCore.getInstance().getClaimsOnFrozen().containsKey(faction))
            AstroCore.getInstance().getClaimsOnFrozen().remove(faction);
        if(AstroCore.getInstance().getClaimsOnInfernal().containsKey(faction))
            AstroCore.getInstance().getClaimsOnInfernal().remove(faction);
        if(AstroCore.getInstance().getClaimsOnDesert().containsKey(faction))
            AstroCore.getInstance().getClaimsOnDesert().remove(faction);
    }
}
