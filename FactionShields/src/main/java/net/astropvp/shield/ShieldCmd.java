// 
// Decompiled by Procyon v0.5.36
// 

package net.astropvp.shield;

import com.cosmicpvp.cosmicutils.utils.FactionUtils;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FLocation;
import net.astropvp.config.ShieldConfig;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.struct.Relation;
import com.massivecraft.factions.struct.Role;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import net.astropvp.FactionShieldsPlugin;
import org.bukkit.command.CommandExecutor;

public class ShieldCmd implements CommandExecutor
{
    public ShieldCmd() {
        FactionShieldsPlugin.getInstance().getCommand("factionshield").setExecutor((CommandExecutor)this);
    }
    
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can use this command.");
            return false;
        }
        final FactionShieldsPlugin plugin = FactionShieldsPlugin.getInstance();
        final ShieldHandler shieldHandler = plugin.getShieldHandler();
        final Player player = (Player)sender;
        FPlayer fplayer = FPlayers.i.get(player);
        if (args.length < 1) {
            if (!shieldHandler.hasFaction(fplayer)) {
                player.sendMessage(ShieldConfig.NO_FACTION);
                return false;
            }
            final FactionShield shield;
            if ((shield = shieldHandler.getShield(player)) != null) {
                player.openInventory(shield.getInventory());
            }
            else {
                player.openInventory(FactionShieldsPlugin.getInstance().getInventoryHandler().getDefaultInventory());
            }
            return false;
        }
        else {
            if (!args[0].equalsIgnoreCase("setregion")) {
                final String lowerCase = args[0].toLowerCase();
                switch (lowerCase) {
                    case "reset": {
                        shieldHandler.reset(player);
                        break;
                    }
                    case "resetall": {
                        if (!shieldHandler.hasAdminPermission(player)) {
                            player.sendMessage(ShieldConfig.NO_PERMISSION);
                            return false;
                        }
                        shieldHandler.resetAll(player);
                        break;
                    }
                    case "forcereset": {
                        if (!shieldHandler.hasAdminPermission(player)) {
                            player.sendMessage(ShieldConfig.NO_PERMISSION);
                            return false;
                        }
                        if (args.length < 2) {
                            player.sendMessage("§cDid you mean: §7/fs forcereset <faction>");
                            return false;
                        }
                        Faction target = FactionUtils.getFactionByTag(args[1]);
                        if (target == null) {
                            player.sendMessage(ShieldConfig.FACTION_NOT_FOUND);
                            return false;
                        }
                        shieldHandler.forceReset(player, target);
                        break;
                    }
                    default: {
                        this.lookup(player, args);
                        break;
                    }
                }
                return false;
            }
            if (!shieldHandler.hasFaction(fplayer)) {
                player.sendMessage(ShieldConfig.NO_FACTION);
                return false;
            }
            final Role role = fplayer.getRole();
            if (role != Role.ADMIN && role != Role.COLEADER) {
                player.sendMessage(ShieldConfig.NO_FACTION_PERMISSION);
                return false;
            }
            if (!plugin.getShieldHandler().hasShield(player)) {
                player.sendMessage(ShieldConfig.REGION_SHIELD_FAILURE);
                return false;
            }
            if (fplayer.getRelationToLocation() != Relation.MEMBER) {
                player.sendMessage(ShieldConfig.REGION_SET_FAILURE);
                return false;
            }
            final FactionShield shield2 = plugin.getShieldHandler().getShield(player);
            if (!shield2.getRegion().isEmpty()) {
                player.sendMessage(ShieldConfig.REGION_ALREADY_SET);
                return false;
            }
            shield2.setRegion(new FLocation(player.getLocation()));
            player.sendMessage(ShieldConfig.REGION_SET);
            return false;
        }
    }
    
    private void lookup(final Player player, final String[] args) {
        final String factionLookup = args[0];
        Faction faction = FactionUtils.getFactionByTag(factionLookup);
        final ShieldHandler shieldHandler = FactionShieldsPlugin.getInstance().getShieldHandler();
        if (!shieldHandler.hasShield(faction)) {
            player.sendMessage(ShieldConfig.SHIELD_LOOKUP_FAILED);
            return;
        }
        final String time = shieldHandler.getShield(faction).getItemData().getTime();
        final String[] timeInfo = time.split(" ");
        final String endTime = shieldHandler.getEndTime(timeInfo[1], Integer.parseInt(timeInfo[0]));
        final String timezone = ShieldConfig.TIMEZONE;
        player.sendMessage(ShieldConfig.SHIELD_LOOKUP.replaceAll("%faction%", faction.getTag()).replaceAll("%start%", time).replaceAll("%end%", endTime).replaceAll(timezone, "").replaceAll("%timezone%", timezone));
    }
}
