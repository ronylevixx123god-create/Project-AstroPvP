package com.massivecraft.factions.cmd;

import com.massivecraft.factions.*;
import com.massivecraft.factions.gui.faccess.FAccessMainGUI;
import com.massivecraft.factions.iface.RelationParticipator;
import com.massivecraft.factions.struct.Relation;
import com.massivecraft.factions.struct.Permission;
import com.massivecraft.factions.util.TL;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public class CmdAccess extends FCommand {
    public CmdAccess() {
        this.aliases.add("access");
        setHelpShort(ChatColor.GRAY + "Gives a specific faction or player access to a chunk.");
        this.optionalArgs.put("f/p/list/clear", "p");
        this.optionalArgs.put("name", "");
        this.optionalArgs.put("yes/no/all/none", "");
        this.requirements = new CommandRequirements.Builder(Permission.ADMIN).build();
    }

    @Override
    public void perform(CommandContext context) {
        if (context.args.size() <= 0 && context.faction != null) {
            (new FAccessMainGUI(context.fPlayer)).open();
            return;
        }
        String access = context.argAsString(0, "p");
        if (context.args.size() == 1) {
            if (access.equals("p")) {
//                (new FAccessPlayerGUI((Player)context.sender, context.faction)).open((Player)context.sender);
                return;
            }
            if (access.equals("f")) {
//                (new FAccessFactionGUI((Player)context.sender, context.faction)).open((Player)context.sender);
                return;
            }
        }
        String name = context.argAsString(1, "");
        if (context.args.size() == 2)
            if (access.equalsIgnoreCase("f")) {
                Faction fac = context.argAsFaction(1);
                if (fac != null && fac.isNormal()) {
                    if (context.faction.getRelationTo((RelationParticipator)fac) == Relation.ENEMY) {
                        context.sender.sendMessage(ChatColor.RED + "You cannot grant enemy factions access!");
                        return;
                    }
                    if (fac.getFactionDefaultPermissions().containsKey(fac.getId())) {
                        context.sender.sendMessage(ChatColor.RED + fac.getTag() + " already has a default permission list!");
                        context.sender.sendMessage(ChatColor.GRAY + "Use '/f access f' to view their permissions!");
                    } else {
                        fac.getFactionDefaultPermissions().put(fac.getId(), fac.getDefaultPermissions());
                        context.sender.sendMessage(ChatColor.RED + fac.getTag() + " has been granted a default permission set!");
                    }
                    return;
                }
                context.sender.sendMessage(ChatColor.RED + "No valid faction found with that name!");
            } else if (access.equalsIgnoreCase("p")) {
                FPlayer fplayer = FPlayers.getInstance().getById(name);
                if (fplayer != null) {
                    UUID uuid = fplayer.getUUID();
                    if (uuid == null) {
                        context.sender.sendMessage(ChatColor.RED + "Unable to find proper data for that player!");
                        return;
                    }
                    if (context.faction.getRelationTo((RelationParticipator)fplayer) == Relation.ENEMY) {
                        context.sender.sendMessage(ChatColor.RED + "You cannot grant enemy factions access!");
                        return;
                    }
                    Faction f = context.faction;
                    if (f.getDefaultPlayerPermissions().containsKey(uuid)) {
                        context.sender.sendMessage(ChatColor.RED + fplayer.getName() + " already has a default permission list!");
                        context.sender.sendMessage(ChatColor.GRAY + "Use '/f perm' to view their permissions!");
                    } else {
                        f.getDefaultPlayerPermissions().put(uuid, f.getDefaultPermissions());
                        context.sender.sendMessage(ChatColor.RED + fplayer.getName() + " has been granted a default permission set!");
                    }
                    return;
                }
                context.sender.sendMessage(ChatColor.RED + "No valid player found with that name!");
            }
        String action = context.argAsString(2, "");
        FPlayer other = context.argAsBestFPlayerMatch(1);
        if (context.sender instanceof Player) {
            Player p = (Player)context.sender;
            FPlayer fp = (FPlayer)FPlayers.getInstance().getByOfflinePlayer((OfflinePlayer)p);
            if (ChatColor.stripColor(fp.getFaction().getTag().toLowerCase()).equals("wilderness")) {
                p.sendMessage(ChatColor.RED + "You are not in a faction.");
                return;
            }
            Faction f = fp.getFaction();
            FLocation fl = new FLocation(p.getLocation());
            if (Board.getInstance().getFactionAt(fl) != f) {
                p.sendMessage(ChatColor.RED + "You must own the chunk you are trying to edit.");
                return;
            }
            if (Board.getInstance().getAllClaims(f).size() <= 0) {
                p.sendMessage(ChatColor.RED + "Your faction does not have any claimed land.");
                return;
            }
            if (access.equalsIgnoreCase("list")) {
                if (f.isLocationTracked(fl)) {
                    List<String> factions = f.getFactionAccess(fl);
                    List<String> players = f.getPlayerAccess(fl);
                    if (factions.size() <= 0 && players.size() <= 0) {
                        p.sendMessage(ChatColor.RED + "Nobody has access to this chunk.");
                        return;
                    }
//                    (new FAccessChunkList(fp, context.faction, fl)).open(p);
                } else {
                    p.sendMessage(ChatColor.RED + "Nobody has access to this chunk.");
                }
                return;
            }
            if (access.equalsIgnoreCase("clear")) {
                if (f.isLocationTracked(fl) && ((Set)f.getChunkClaims().get(fl)).size() > 0) {
                    f.getChunkClaims().remove(fl);
                    f.cleanupPermissions(fl);
                    p.sendMessage(ChatColor.RED + "All access has been cleared for this chunk.");
//                    P.p.logFactionEvent(f, FLogType.PERM_EDIT_CHUNK_ACCESS, new String[] { this.fme.getNameAsync(), ChatColor.RED + ChatColor.BOLD.toString() + "CLEARED", fl.getCoordString(), "ALL" });
                } else {
                    p.sendMessage(ChatColor.RED + "Nobody has access to this chunk.");
                }
                return;
            }
            if (name.isEmpty() || access.isEmpty()) {
                p.sendMessage(ChatColor.RED + "Invalid arguments.");
                p.sendMessage(ChatColor.RED + "/f access <p/f/list/clear> <name> <yes/no/none/all>");
                return;
            }
            if (access.equalsIgnoreCase("f") || access.equalsIgnoreCase("factions")) {
                Faction otherFac = context.argAsFaction(1);
                if (context.faction.getRelationTo((RelationParticipator)otherFac) == Relation.ENEMY) {
                    context.sender.sendMessage(ChatColor.RED + "You cannot grant enemy factions access!");
                    return;
                }
                attemptFactionAccess(p, f, otherFac, fl, action);
            } else if (action.isEmpty()) {
                context.sender.sendMessage(ChatColor.RED + "Invalid arguments.");
                context.sender.sendMessage(ChatColor.RED + "/f access <p/f/list/clear> <name> <yes/no/none/all>");
            } else {
                attemptFPlayerAccess(p, f, name, fl, action, other);
            }
        }
    }

    @Override
    public TL getUsageTranslation() {
        return null;
    }

    public void attemptFPlayerAccess(Player p, Faction f, String name, FLocation fl, String action, FPlayer otherplayer) {
        if (otherplayer == null) {
            p.sendMessage(ChatColor.RED + "That player does not exist.");
            return;
        }
        if (name != null && name.length() > 16) {
            p.sendMessage(ChatColor.RED + "Player names can not be longer then 16 characters.");
            return;
        }
        if (otherplayer.getRelationTo((RelationParticipator)f) == Relation.ENEMY) {
            p.sendMessage(ChatColor.RED + "You cannot grant enemy factions access!");
            return;
        }
        String showname = otherplayer.getName().contains("-") ? name : otherplayer.getName(), string = showname;
        if (p.getName().equalsIgnoreCase(showname)) {
            p.sendMessage(ChatColor.RED + "You can not add yourself to a faction chunk.");
            return;
        }
        if (action.equalsIgnoreCase("yes")) {
            if (f.addPlayerAccess(fl, otherplayer)) {
                f.sendMessage(ChatColor.GREEN + ChatColor.BOLD.toString() + "(!) " + ChatColor.GREEN + "The player " + showname + " now has access to the claim " + fl.formatXAndZ(",") + "! Use /f perm to customize what they can edit in this chunk.");
                otherplayer.sendMessage(ChatColor.GREEN + ChatColor.BOLD.toString() + "(!) " + ChatColor.GREEN + "You now have access to " + f.getTag() + "'s claimed chunk at x" + ((int)fl.getX() * 16) + ", z" + ((int)fl.getZ() * 16) + "!");
                f.createDefaultPermissions(fl, otherplayer);
//                P.p.logFactionEvent(f, FLogType.PERM_EDIT_CHUNK_ACCESS, new String[] { this.fme.getNameAsync(), ChatColor.GREEN + ChatColor.BOLD.toString() + "GRANTED", fl.formatXAndZ(","), showname });
            } else {
                p.sendMessage(ChatColor.RED + "That player already has access to this chunk.");
            }
        } else if (action.equalsIgnoreCase("no")) {
            if (f.removePlayerAccess(fl, otherplayer)) {
                p.sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + "(!) " + ChatColor.RED + "The player " + showname + " no longer has access to the chunk x" + ((int)fl.getX() * 16) + ", z" + ((int)fl.getZ() * 16) + "!");
                otherplayer.sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + "(!) " + ChatColor.RED + "You no longer have access to " + f.getTag() + "'s claimed chunk at x" + ((int)fl.getX() * 16) + ", z" + ((int)fl.getZ() * 16) + "!");
                f.removePermissions(fl, otherplayer);
//                P.p.logFactionEvent(f, FLogType.PERM_EDIT_CHUNK_ACCESS, new String[] { this.fme.getNameAsync(), ChatColor.RED + ChatColor.BOLD.toString() + "REMOVED", fl.formatXAndZ(","), showname });
            } else {
                p.sendMessage(ChatColor.RED + "That player does not have access to this chunk.");
            }
        } else if (action.equalsIgnoreCase("all")) {
            for (FLocation all : Board.getInstance().getAllClaims(f)) {
                f.addPlayerAccess(all, otherplayer);
                f.createDefaultPermissions(all, otherplayer);
            }
            f.sendMessage(ChatColor.GREEN + ChatColor.BOLD.toString() + "(!) " + ChatColor.GREEN + "The player " + showname + " now has access all of your claimed land!");
            otherplayer.sendMessage(ChatColor.YELLOW + ChatColor.BOLD.toString() + "(!) " + ChatColor.YELLOW + "You now have access to all of " + f.getTag() + "'s claimed land.");
//            P.p.logFactionEvent(f, FLogType.PERM_EDIT_CHUNK_ACCESS, new String[] { this.fme.getNameAsync(), ChatColor.GREEN.toString() + ChatColor.BOLD + "GRANTED", "ALL CHUNKS", showname });
        } else if (action.equalsIgnoreCase("none")) {
            int removed = 0;
            for (FLocation l : f.getChunkClaims().keySet()) {
                if (f.doesPlayerHaveChunkAccess(l, otherplayer, false)) {
                    removed++;
                    f.removePlayerAccess(l, otherplayer);
                }
                f.removePermissions(l, otherplayer);
            }
            if (removed > 0) {
                f.sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + "(!) " + ChatColor.RED + ChatColor.UNDERLINE.toString() + showname + ChatColor.RED + " no longer has access to any of your land.");
                otherplayer.sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + "(!) " + ChatColor.RED + "You no longer have access to " + ChatColor.RED + ChatColor.UNDERLINE.toString() + "any" + ChatColor.RED + " of " + f.getTag() + "'s land!");
//                P.p.logFactionEvent(f, FLogType.PERM_EDIT_CHUNK_ACCESS, new String[] { this.fme.getNameAsync(), ChatColor.RED.toString() + ChatColor.BOLD + "REMOVED", "ALL CHUNKS", showname });
            } else {
                p.sendMessage(ChatColor.RED + "That player did not have access to any of your land.");
            }
        }
    }

    public void attemptFactionAccess(Player p, Faction f, Faction otherFac, FLocation fl, String action) {
        if (otherFac == null || otherFac.isWarZone() || otherFac.isSafeZone()) {
            p.sendMessage(ChatColor.RED + "That is " + ChatColor.RED + ChatColor.UNDERLINE.toString() + "not" + ChatColor.RED + " a valid faction.");
            return;
        }
        if (action.equalsIgnoreCase("yes")) {
            if (f.addFactionAccess(fl, otherFac)) {
                f.sendMessage(ChatColor.GREEN + ChatColor.BOLD.toString() + "(!) " + ChatColor.GREEN + "The faction " + otherFac.getTag() + " now has access to the claim " + fl.formatXAndZ("") + "! Use /f perm to customize what they can edit in this chunk.");
                otherFac.sendMessage(ChatColor.GREEN + ChatColor.BOLD.toString() + "(!) " + ChatColor.GREEN + "Your faction now has access to " + f.getTag() + "'s claimed chunk at " + fl.formatXAndZ(",!"));
                f.createDefaultPermissions(fl, otherFac);
//                P.p.logFactionEvent(f, FLogType.PERM_EDIT_CHUNK_ACCESS, new String[] { this.fme.getNameAsync(), ChatColor.GREEN + ChatColor.BOLD.toString() + "GRANTED", fl.formatXAndZ(","), otherFac.getTag() });
            } else {
                p.sendMessage(ChatColor.RED + "That faction already has access to this chunk.");
            }
        } else if (action.equalsIgnoreCase("no")) {
            if (f.removeFactionAccess(fl, otherFac)) {
                f.sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + "(!) " + ChatColor.RED + "The faction " + otherFac.getTag() + " no longer has access to the chunk x" + ((int)fl.getX() * 16) + ", z" + ((int)fl.getZ() * 16) + "!");
                otherFac.sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + "(!) " + ChatColor.RED + "Your faction no longer has access to " + f.getTag() + "'s claimed chunk at x" + ((int)fl.getX() * 16) + ", z" + ((int)fl.getZ() * 16) + "!");
                f.removePermissions(fl, otherFac);
//                P.p.logFactionEvent(f, FLogType.PERM_EDIT_CHUNK_ACCESS, new String[] { this.fme.getNameAsync(), ChatColor.RED + ChatColor.BOLD.toString() + "REMOVED", fl.formatXAndZ(","), otherFac.getTag() });
            } else {
                p.sendMessage(ChatColor.RED + "That faction does not have access to this chunk.");
            }
        } else if (action.equalsIgnoreCase("all")) {
            for (FLocation all : Board.getInstance().getAllClaims(f)) {
                f.addFactionAccess(all, otherFac);
                f.createDefaultPermissions(all, otherFac);
            }
            f.sendMessage(ChatColor.YELLOW + ChatColor.BOLD.toString() + "(!) " + ChatColor.YELLOW + "The faction " + otherFac.getTag() + " now has access to all of your land.");
            otherFac.sendMessage(ChatColor.YELLOW + ChatColor.BOLD.toString() + "(!) " + ChatColor.YELLOW + "Your faction now has access to all of " + f.getTag() + "'s land.");
//            P.p.logFactionEvent(f, FLogType.PERM_EDIT_CHUNK_ACCESS, new String[] { this.fme.getNameAsync(), ChatColor.GREEN + ChatColor.BOLD.toString() + "GRANTED", "ALL CHUNKS", otherFac.getTag() });
        } else if (action.equalsIgnoreCase("none")) {
            int removed = 0;
            for (FLocation l : f.getChunkClaims().keySet()) {
                if (f.doesFactionHaveAccess(l, otherFac)) {
                    removed++;
                    f.removeFactionAccess(l, otherFac);
                }
                f.removePermissions(l, otherFac);
            }
            if (removed > 0) {
                f.sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + "(!) " + ChatColor.RED + ChatColor.UNDERLINE.toString() + otherFac.getTag() + ChatColor.RED + " no longer has access to any of your land.");
                otherFac.sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + "(!) " + ChatColor.RED + "Your faction no longer has access to " + ChatColor.RED + ChatColor.UNDERLINE.toString() + "any" + ChatColor.RED + " of " + f.getTag() + "'s land!");
//                P.p.logFactionEvent(f, FLogType.PERM_EDIT_CHUNK_ACCESS, new String[] { this.fme.getNameAsync(), ChatColor.RED + ChatColor.BOLD.toString() + "REMOVED", "ALL CHUNKS", otherFac.getTag() });
            } else {
                p.sendMessage(ChatColor.RED + "That faction did not have access to any of your land.");
            }
        }
    }
}
