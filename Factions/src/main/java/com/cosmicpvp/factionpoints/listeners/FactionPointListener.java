/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.nbt.NBTTagCompoundBuilder
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.cosmicpvp.cosmicutils.utils.FactionUtils
 *  com.cosmicpvp.cosmicutils.utils.HCFOutpostUtils
 *  com.cosmicpvp.cosmicutils.utils.NBTWrapper
 *  com.cosmicpvp.cosmicutils.utils.NumberUtils
 *  com.cosmicpvp.cosmicutils.utils.TimeUtils
 *  com.massivecraft.factions.FLocation
 *  com.massivecraft.factions.Faction
 *  com.massivecraft.factions.P
 *  com.massivecraft.factions.struct.FLogType
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.block.Action
 *  org.bukkit.event.player.PlayerCommandPreprocessEvent
 *  org.bukkit.event.player.PlayerInteractEntityEvent
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.inventory.ItemStack
 */
package com.cosmicpvp.factionpoints.listeners;

import com.cosmicpvp.cosmicutils.nbt.NBTTagCompoundBuilder;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.FactionUtils;
import com.cosmicpvp.cosmicutils.utils.HCFOutpostUtils;
import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import com.cosmicpvp.cosmicutils.utils.NumberUtils;
import com.cosmicpvp.cosmicutils.utils.TimeUtils;
import com.cosmicpvp.factionpoints.FactionsCoreChunkAPI;
import com.cosmicpvp.factionpoints.FactionsPointsAPI;
import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.P;
import com.massivecraft.factions.struct.FLogType;
import java.util.concurrent.TimeUnit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class FactionPointListener
implements Listener {
    @EventHandler(priority=EventPriority.LOWEST, ignoreCancelled=true)
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        String lower = event.getMessage().toLowerCase();
        if (lower.startsWith("/f point")) {
            event.setMessage(lower.replace("/f points", "/factionpoints").replace("/f point", "/factionpoints"));
        } else if (lower.startsWith("/faction point")) {
            event.setMessage(lower.replace("/faction point", "/factionpoints").replace("/faction points", "/factionpoints"));
        }
    }

    @EventHandler(priority=EventPriority.HIGHEST)
    public void onPlayerInteract(PlayerInteractEntityEvent event) {
        Player player;
        if (event.getRightClicked() instanceof Player && (player = (Player)event.getRightClicked()).hasMetadata("NPC") && player.getName().contains("Point Merchant")) {
            event.setCancelled(true);
            player.performCommand("/factionpoints buy");
        }
    }

    @EventHandler(priority=EventPriority.HIGHEST)
    public void onInteractPointItem(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR) {
            ItemStack item = event.getItem();
            if (item == null || item.getType() == Material.AIR) {
                return;
            }
            NBTWrapper wrapper = new NBTWrapper(item);
            Player player = event.getPlayer();
            if (FactionsPointsAPI.isPointItem(wrapper)) {
                event.setCancelled(true);
                if (player.getWorld().getName().startsWith("world_duels")) {
                    player.sendMessage(CC.RedB + "(!) " + CC.Red + "You cannot claim Faction Points inside the duel world!");
                    return;
                }
                NBTTagCompoundBuilder builder = new NBTTagCompoundBuilder(wrapper.getNBTBase("cosmicData"));
                int points = builder.getInt("points");
                if (points >= 0) {
                    Long newPoints;
                    Faction facIn;
                    Long timeCreated = builder.getLong("created");
                    if (timeCreated != null) {
                        Long manualExpiration = builder.getLong("expiration");
                        long expireTime = TimeUnit.HOURS.toMillis(24L);
                        long timeSinceCreated = System.currentTimeMillis() - timeCreated;
                        if (manualExpiration != null && manualExpiration != 0L && manualExpiration < System.currentTimeMillis() || timeSinceCreated >= expireTime) {
                            long timeSinceExpired;
                            long l = timeSinceExpired = manualExpiration != null && manualExpiration != 0L ? System.currentTimeMillis() - manualExpiration : timeSinceCreated - expireTime;
                            if (timeSinceExpired > 0L) {
                                player.sendMessage(CC.RedB + "(!) " + CC.Red + "Your Factions Point Note expired " + CC.RedU + TimeUtils.formatSeconds((long)(timeSinceExpired / 1000L)) + CC.Red + " ago, it has been removed!");
                                player.setItemInHand(null);
                                player.updateInventory();
                                player.playSound(player.getLocation(), Sound.ZOMBIE_WOODBREAK, 1.0f, 1.1f);
                                P.logCosmicFactionPoints("Removing" + (Object)item + " from " + player.getName() + " due to it expiring " + timeSinceExpired + "ms ago!");
                                return;
                            }
                        }
                    }
                    if ((facIn = FactionUtils.getFaction((Player)player, (boolean)false)) == null || !facIn.isNormal()) {
                        player.sendMessage(CC.RedB + "(!) " + CC.Red + "You must be in a Faction to claim Faction Points!");
                        return;
                    }
                    FLocation chunk = FactionsCoreChunkAPI.getCoreChunk(facIn);
                    if (chunk == null) {
                        player.sendMessage(CC.RedB + "(!) " + CC.Red + "Your faction must have claimed land and a Core Chunk before you can claim Faction Points!");
                        return;
                    }
                    int pointsToApply = points;
                    if (HCFOutpostUtils.doesControlOutpost((Faction)facIn, (String)"frozen")) {
                        pointsToApply = (int)((double)pointsToApply + (double)pointsToApply * 0.2);
                    }
                    if ((newPoints = FactionsPointsAPI.modifyPoints(facIn, pointsToApply, true)) == null) {
                        player.playSound(player.getLocation(), Sound.FIZZ, 1.1f, 1.0f);
                        return;
                    }
                    if (item.getAmount() > 1) {
                        item.setAmount(item.getAmount() - 1);
                    } else {
                        player.setItemInHand(null);
                    }
                    player.playSound(player.getLocation(), Sound.ORB_PICKUP, 1.0f, 1.4f);
                    player.updateInventory();
                    facIn.sendMessage(CC.GreenB + "+ " + CC.Green + NumberUtils.formatSeconds((double)pointsToApply) + " Faction Points (" + player.getName() + ")");
                    try {
                        P.getP().getPointManager().logPointChange(facIn, player, "redeemed", pointsToApply);
                        P.p.getFlogManager().log(facIn, FLogType.F_POINTS, new String[]{CC.GreenB + "+ " + CC.Green + pointsToApply + " Faction Points (" + newPoints + ") ", player.getName(), "- " + CC.GreenB + "REDEEMED"});
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}

