/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.VanishUtils
 *  com.gmail.nossr50.runnables.skills.BleedTimerTask
 *  com.google.common.collect.Lists
 *  org.apache.commons.lang.StringUtils
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Sound
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandExecutor
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 */
package me.ifamasssxd.cosmicduel.commands;

import com.cosmicpvp.cosmicutils.utils.VanishUtils;
import com.gmail.nossr50.runnables.skills.BleedTimerTask;
import com.google.common.collect.Lists;
import me.ifamasssxd.cosmicduel.CosmicDuel;
import me.ifamasssxd.cosmicduel.DuelAPI;
import me.ifamasssxd.cosmicduel.godset.CustomGodSet;
import me.ifamasssxd.cosmicduel.struct.DuelInvite;
import me.ifamasssxd.cosmicduel.struct.DuelMenu;
import me.ifamasssxd.cosmicduel.struct.DuelPlayer;
import me.ifamasssxd.cosmicduel.struct.QueueManager;
import me.ifamasssxd.cosmicduel.struct.enums.CancelReason;
import me.ifamasssxd.cosmicduel.struct.enums.DuelCreateState;
import me.ifamasssxd.cosmicduel.struct.enums.DuelState;
import me.ifamasssxd.cosmicduel.struct.enums.Reason;
import me.ifamasssxd.cosmicduel.struct.newduel.ClassicDuel;
import me.ifamasssxd.cosmicduel.struct.newduel.GlobalQueuePopInventory;
import me.ifamasssxd.cosmicduel.struct.newduel.NewDuel;
import me.ifamasssxd.cosmicduel.struct.ranked.RankedTopManager;
import me.ifamasssxd.cosmicduel.utils.CollectionBin;
import me.ifamasssxd.cosmicduel.utils.PlayerUtils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

import java.util.Map;
import java.util.UUID;

public class CommandDuel
implements CommandExecutor {
    String red = (Object)ChatColor.RED + ChatColor.BOLD.toString() + "(!) ";

    public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
        if (args.length == 1 && sender.isOp() && args[0].equalsIgnoreCase("togglequeue")) {
            CosmicDuel.queueEnabled = !CosmicDuel.queueEnabled;
            CosmicDuel.get().getConfig().set("queueEnabled", (Object)CosmicDuel.queueEnabled);
            CosmicDuel.get().saveConfig();
            sender.sendMessage((Object)ChatColor.RED + "Queue Enabled: " + CosmicDuel.queueEnabled);
            return true;
        }
        if (!(sender instanceof Player)) {
            return true;
        }
        Player p = (Player)sender;
        if (CosmicDuel.get().isShuttingDown()) {
            p.sendMessage((Object)ChatColor.RED + ChatColor.BOLD.toString() + "(!) " + (Object)ChatColor.RED + "You cannot use duel commands while the server is rebooting.");
            return true;
        }
        QueueManager manager = CosmicDuel.get().getQueueManager();
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("help")) {
                this.sendHelp(p);
                return true;
            }
            if (sender.isOp()) {
                if (args[0].equalsIgnoreCase("listqueue")) {
                    int index = 0;
                    p.sendMessage((Object)ChatColor.RED + "Players in Queue:");
                    for (Map.Entry<UUID, Long> entry : CosmicDuel.get().getQueueManager().getRankedQueue().getSearching().entrySet()) {
                        Player pl = Bukkit.getPlayer((UUID)entry.getKey());
                        if (pl == null) continue;
                        p.sendMessage(ChatColor.RED.toString() + index++ + ". " + pl.getName() + " Queue Time: " + (System.currentTimeMillis() - entry.getValue()) + " ms");
                    }
                    return true;
                }
                if (args[0].equalsIgnoreCase("recalctop")) {
                    RankedTopManager topManager = CosmicDuel.get().getTopManager();
                    if (topManager.isLoading()) {
                        sender.sendMessage((Object)ChatColor.RED + "Please wait for the top to calculate..");
                        return true;
                    }
                    topManager.loadTopPlayers();
                } else if (args[0].equalsIgnoreCase("list")) {
                    int index = 1;
                    for (NewDuel duel : DuelAPI.duels) {
                        String players = "";
                        for (Player pl2 : duel.getPlayers()) {
                            players = players + pl2.getName() + ", ";
                        }
                        sender.sendMessage(index++ + ". " + duel.getDuelID() + " State: " + duel.getDuelState().name() + " Create State: " + duel.getCreateState().name() + " players: " + players + " arena: " + (duel.getArena() == null ? "None" : duel.getArena().getName()));
                    }
                    return true;
                }
            }
            if (args[0].equalsIgnoreCase("unranked") || args[0].equalsIgnoreCase("queue")) {
                if (!CosmicDuel.queueEnabled) {
                    p.sendMessage((Object)ChatColor.RED + "Duel queues are currently disabled.");
                    return true;
                }
                if (PlayerUtils.inCombat(p)) {
                    p.sendMessage((Object)ChatColor.RED + "You cannot do this while in combat!");
                    return true;
                }
                if (manager.getDuel(p) != null || manager.isSearching(p) || manager.getRankedQueue().isInRankedQueue(p)) {
                    p.sendMessage((Object)ChatColor.RED + "You are currently in queue.");
                    return true;
                }
                if (manager.getPreparingDuel(p) != null) {
                    p.sendMessage((Object)ChatColor.RED + "Finish preparing your duel!");
                    return true;
                }
                if (DuelAPI.hasDuel(p)) {
                    p.sendMessage((Object)ChatColor.RED + "You cannot do this while in a duel.");
                    return true;
                }
                manager.queuePlayer(p);
                return true;
            }
            if (args[0].equalsIgnoreCase("leave")) {
                if (!p.hasMetadata("in_ranked_queue_menu") || p.getMetadata("in_ranked_queue_menu").size() <= 0) {
                    NewDuel duel2;
                    if (DuelAPI.hasDuel(p)) {
                        duel2 = DuelAPI.getDuel(p);
                        if (DuelAPI.isDuelQueued(duel2)) {
                            duel2.sendMessage((Object)ChatColor.RED + ChatColor.BOLD.toString() + "(!) " + (Object)ChatColor.RED + "Duel was in queue and it has been cancelled by " + p.getName() + ".", true);
                            DuelAPI.cancelDuel(duel2, CancelReason.LEFT_DURING_QUEUE);
                            return true;
                        }
                        for (DuelInvite invite : DuelAPI.getAllDuelInvites(p, DuelAPI.InviteType.INVITER)) {
                            DuelAPI.invite_timer.remove(invite);
                            System.out.println("Declining invite for " + p.getName());
                        }
                        p.sendMessage(this.red + (Object)ChatColor.RED + "You left the duel.");
                        duel2.removePlayer(p, Reason.LEFT_DUEL, false);
                        if (duel2.getPlayers().size() <= 0 && duel2.getDuelState() == DuelState.IN_QUEUE && duel2.getCreateState() == DuelCreateState.PLAYER_SELECT) {
                            DuelAPI.duels.remove(duel2);
                        }
                    } else if (p.hasMetadata("spectator")) {
                        NewDuel spectating = DuelAPI.getDuelSpectating(p);
                        if (spectating != null) {
                            spectating.removeSpectator(p);
                        } else {
                            p.sendMessage(this.red + (Object)ChatColor.RED + "You are not spectating a duel.. resetting player data.");
                            p.removeMetadata("spectator", (Plugin)CosmicDuel.get());
                            PlayerUtils.setCollides(p, true);
                            BleedTimerTask.remove((LivingEntity)p);
                            if (!VanishUtils.isVanished((Player)p)) {
                                for (Player pl3 : Bukkit.getServer().getOnlinePlayers()) {
                                    pl3.showPlayer(p);
                                }
                            }
                        }
                    } else {
                        duel2 = manager.getDuel(p);
                        if (duel2 != null) {
                            manager.removeDuelFromQueue(duel2);
                            p.sendMessage((Object)ChatColor.RED + "You have been removed from the Global Duel Queue.");
                            return true;
                        }
                        if (manager.isSearching(p)) {
                            manager.removePlayerFromQueue(p);
                            p.sendMessage((Object)ChatColor.RED + "You have been removed from the Global Duel Queue.");
                            return true;
                        }
                        if (manager.getRankedQueue().isInRankedQueue(p)) {
                            manager.getRankedQueue().removeFromQueue(p);
                            p.sendMessage((Object)ChatColor.RED + "You have been removed from the Ranked Duel Queue.");
                            return true;
                        }
                        p.sendMessage(this.red + (Object)ChatColor.RED + "You are not in a duel.");
                    }
                    return true;
                }
                GlobalQueuePopInventory queue = (GlobalQueuePopInventory)((MetadataValue)p.getMetadata("in_ranked_queue_menu").get(0)).value();
                if (queue != null) {
                    queue.declineDuel(p, true, true);
                    return true;
                }
                p.removeMetadata("in_ranked_queue_menu", (Plugin)CosmicDuel.get());
                p.sendMessage((Object)ChatColor.RED + "Your duel queue menu data has been cleared!");
                return true;
            }
            if (args[0].equalsIgnoreCase("top")) {
                RankedTopManager rankedTop = CosmicDuel.get().getTopManager();
                if (rankedTop.isLoading()) {
                    p.sendMessage((Object)ChatColor.RED + "Please wait while the top players are being loaded...");
                    return true;
                }
                rankedTop.showTop(p, 1);
                return true;
            }
            if (args[0].equalsIgnoreCase("toggle")) {
                p.sendMessage((Object)ChatColor.YELLOW + ChatColor.BOLD.toString() + "(!) " + (Object)ChatColor.YELLOW + "Duels have been toggled " + (CosmicDuel.get().getDuelToggles().togglePlayer(p) ? (Object)ChatColor.RED + ChatColor.BOLD.toString() + "OFF" : (Object)ChatColor.GREEN + ChatColor.BOLD.toString() + "ON"));
                return true;
            }
            if (args[0].equalsIgnoreCase("collect")) {
                CollectionBin.showCollectionBin(p);
                return true;
            }
            if (args[0].equalsIgnoreCase("spectate")) {
                p.performCommand("spectate");
                return true;
            }
            if (args[0].equalsIgnoreCase("customize") || args[0].equalsIgnoreCase("custom")) {
                if (!PlayerUtils.isInventoryEmpty(p)) {
                    p.sendMessage((Object)ChatColor.RED + "Your inventory must be empty to customize your duel kits!");
                    return true;
                }
                if (PlayerUtils.inCombat(p)) {
                    p.sendMessage((Object)ChatColor.RED + "You cannot use this command while in combat.");
                    return true;
                }
                if (DuelAPI.getDuel(p) != null) {
                    p.sendMessage((Object)ChatColor.RED + "You cannot customize your kits in a duel!");
                    return true;
                }
                DuelPlayer dPlayer = DuelPlayer.getDuelPlayer(p);
                if (dPlayer.getCurrentlyModifying() == null) {
                    p.openInventory(dPlayer.createCustomInventory());
                }
                return true;
            }
            if (args[0].equalsIgnoreCase("godset")) {
                if (PlayerUtils.inCombat(p)) {
                    p.sendMessage((Object)ChatColor.RED + "You cannot edit a Duel Godset in combat!");
                    return true;
                }
                if (DuelAPI.hasDuel(p)) {
                    p.sendMessage((Object)ChatColor.RED + "You are already involved in a duel!");
                    return true;
                }
                QueueManager queueManager = CosmicDuel.get().getQueueManager();
                if (queueManager.isInQueue(p) || queueManager.isSearching(p) || queueManager.getRankedQueue().isInRankedQueue(p)) {
                    p.sendMessage((Object)ChatColor.RED + "You cannot edit your duel godset in queue.");
                    return true;
                }
                DuelPlayer player = DuelPlayer.getDuelPlayer(p);
                if (player.getCustomGodSet() == null) {
                    player.setCustomGodSet(new CustomGodSet());
                }
                p.openInventory(player.getCustomGodSet().getEditInventory());
                p.playSound(p.getLocation(), Sound.BLAZE_DEATH, 1.0f, 1.3f);
                return true;
            }
            if (args[0].equalsIgnoreCase("ranked")) {
                Player player2 = (Player)sender;
                DuelPlayer pl4 = DuelPlayer.getDuelPlayer(player2);
                if (!DuelMenu.checkCanQueue(pl4, player2)) {
                    return true;
                }
                player2.sendMessage((Object)ChatColor.GREEN + ChatColor.BOLD.toString() + "(!) " + (Object)ChatColor.GREEN + "You have been placed in the " + (Object)ChatColor.GREEN + ChatColor.BOLD.toString() + "RANKED" + (Object)ChatColor.GREEN + " duel queue!");
                player2.sendMessage((Object)ChatColor.GREEN + "Use " + (Object)ChatColor.GREEN + ChatColor.UNDERLINE.toString() + "/duel leave" + (Object)ChatColor.GREEN + " to leave the queue.");
                player2.sendMessage((Object)ChatColor.GRAY + "Searching for an opponent...");
                CosmicDuel.get().getQueueManager().getRankedQueue().addPlayerToQueue(player2);
                player2.closeInventory();
                return true;
            }
            Player pl5 = Bukkit.getPlayer((String)args[0]);
            if (pl5 == null) {
                p.sendMessage((Object)ChatColor.RED + "The player '" + args[0] + "' does not exist.");
                return false;
            }
            if (pl5.equals((Object)p)) {
                p.sendMessage((Object)ChatColor.RED + "You cannot duel yourself.");
                return true;
            }
            if (!DuelAPI.checkStatus(p, pl5)) {
                return true;
            }
            CosmicDuel.logMessage("Creating duel for " + p.getName() + " and inviting " + pl5.getName());
            try {
                ClassicDuel newDuel = new ClassicDuel(p, Lists.newArrayList(args[0]));
                DuelAPI.duels.add(newDuel);
                newDuel.setCreateState(DuelCreateState.SETTINGS);
                newDuel.getSettings().getSettings().openInventory(p);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        } else if (args.length == 2 && (args[0].equalsIgnoreCase("accept") || args[0].equalsIgnoreCase("decline") || args[0].equalsIgnoreCase("settings") || args[0].equalsIgnoreCase("top"))) {
            if (args[0].equalsIgnoreCase("accept")) {
                if (!DuelAPI.hasPendingInvite(p)) {
                    p.sendMessage(this.red + (Object)ChatColor.RED + "You do not have a pending invite.");
                    return true;
                }
                if (DuelAPI.isRebooting()) {
                    p.sendMessage(this.red + (Object)ChatColor.RED + "You cannot duel while the server is rebooting!");
                    return true;
                }
                String p_name = args[1];
                DuelAPI.acceptInvite(p, p_name, true);
                return true;
            }
            if (args[0].equalsIgnoreCase("decline")) {
                if (!DuelAPI.hasPendingInvite(p)) {
                    p.sendMessage(this.red + (Object)ChatColor.RED + "You do not have a pending invite.");
                    return true;
                }
                String p_name = args[1];
                DuelAPI.declineDuel(p, p_name, true);
            } else if (args[0].equalsIgnoreCase("settings")) {
                if (!DuelAPI.hasPendingInvite(p)) {
                    p.sendMessage(this.red + (Object)ChatColor.RED + "You do not have a pending invite.");
                    return true;
                }
                String p_name = args[1];
                DuelAPI.sendSettings(p, p_name);
            } else if (args[0].equalsIgnoreCase("top")) {
                if (!StringUtils.isNumeric((String)args[1])) {
                    p.sendMessage((Object)ChatColor.RED + "Please enter a valid page number.");
                    return true;
                }
                int page = Integer.parseInt(args[1]);
                RankedTopManager rankedTop2 = CosmicDuel.get().getTopManager();
                if (rankedTop2.isLoading()) {
                    p.sendMessage((Object)ChatColor.RED + "Please wait while the top players are being loaded...");
                    return true;
                }
                rankedTop2.showTop(p, page);
                return true;
            }
        } else if (args.length == 0) {
            new DuelMenu(p).openInventory(p);
        } else {
            this.sendHelp(p);
        }
        return false;
    }

    private void sendHelp(Player p) {
        p.sendMessage("");
        p.sendMessage((Object)ChatColor.YELLOW + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "Duel Commands");
        p.sendMessage((Object)ChatColor.YELLOW + "/duel <name>");
        p.sendMessage((Object)ChatColor.GRAY + "Invite a player to a settings specific duel.");
        p.sendMessage((Object)ChatColor.YELLOW + "/duel ranked");
        p.sendMessage((Object)ChatColor.GRAY + "Enter the duel ranked queue.");
        p.sendMessage((Object)ChatColor.YELLOW + "/duel godset");
        p.sendMessage((Object)ChatColor.GRAY + "Access the Duel Godset edit menu.");
        p.sendMessage((Object)ChatColor.YELLOW + "/duel top");
        p.sendMessage((Object)ChatColor.GRAY + "View top Duel ELO players.");
        p.sendMessage((Object)ChatColor.YELLOW + "/duel toggle");
        p.sendMessage((Object)ChatColor.GRAY + "Toggle duel invites from other players.");
        p.sendMessage((Object)ChatColor.YELLOW + "/duel collect");
        p.sendMessage((Object)ChatColor.GRAY + "Access your Stake Collection Bin.");
        p.sendMessage((Object)ChatColor.YELLOW + "/duel spectate");
        p.sendMessage((Object)ChatColor.GRAY + "Access the duel spectate menu.");
        p.sendMessage((Object)ChatColor.YELLOW + "/duel unranked");
        p.sendMessage((Object)ChatColor.GRAY + "Enter the Global Duel Queue to duel a random opponent.");
        p.sendMessage((Object)ChatColor.YELLOW + "/duel custom");
        p.sendMessage((Object)ChatColor.GRAY + "Customize your duel kit loadouts.");
        p.sendMessage("");
    }
}

