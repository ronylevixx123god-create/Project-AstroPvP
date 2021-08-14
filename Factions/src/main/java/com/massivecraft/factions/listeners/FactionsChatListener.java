/*
 * Decompiled with CFR 0.145.
 *
 * Could not load the following classes:
 *  com.compilex.CosmicReputation.ReputationAPI
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.earth2me.essentials.User
 *  com.earth2me.essentials.utils.DateUtil
 *  com.google.common.collect.Lists
 *  me.ifamasssxd.cosmicduel.struct.DuelPlayer
 *  net.milkbowl.vault.economy.Economy
 *  org.arkhamnetwork.Arkkit.libs.SeasonalWin
 *  org.arkhamnetwork.Arkkit.patches.SeasonalPrefixes
 *  org.arkhamnetwork.Arkkit.patches.chat_filter.ChatUtils
 *  org.arkhamnetwork.Arkkit.patches.networkevents.IgnoreUtils
 *  org.arkhamnetwork.Arkkit.plugins.ArkPlugin
 *  org.arkhamnetwork.Arkkit.plugins.ArkPlugins
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.OfflinePlayer
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.AsyncPlayerChatEvent
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.metadata.MetadataValue
 */
package com.massivecraft.factions.listeners;

import com.astropvp.cc.player.CosmicPlayers;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.earth2me.essentials.User;
import com.earth2me.essentials.utils.DateUtil;
import com.google.common.collect.Lists;
import com.massivecraft.factions.*;
import com.massivecraft.factions.cmd.CmdShow;
import com.massivecraft.factions.integration.Econ;
import com.massivecraft.factions.integration.EssentialsFeatures;
import com.massivecraft.factions.struct.ChatMode;
import com.massivecraft.factions.struct.Relation;
import com.massivecraft.factions.struct.Role;
import me.ifamasssxd.cosmicduel.struct.DuelPlayer;
import mkremins.fanciful.FancyMessage;
import org.arkhamnetwork.Arkkit.libs.SeasonalWin;
import org.arkhamnetwork.Arkkit.patches.SeasonalPrefixes;
import org.arkhamnetwork.Arkkit.patches.chat_filter.ChatUtils;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugins;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChatEvent;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FactionsChatListener implements Listener {
    public P p;
    private final DecimalFormat eloFormat = new DecimalFormat("#.#");
    private final DecimalFormat formatter = new DecimalFormat("#,###");
    private final Pattern webpattern = Pattern.compile("[-a-zA-Z0-9@:%_+.~#?&//=]{2,256}\\.[a-z]{2,4}\\b(/[-a-zA-Z0-9@:%_+.~#?&//=]*)?");
    private final Pattern COLOR_PATTERN = Pattern.compile("(?i)" + '§' + "[0-9A-FK-OR]");
    private final boolean vanillaGalaxy = (new File("plugins/CosmicDuelNoRanked.jar")).exists();

    public FactionsChatListener(P p) {
        this.p = p;
    }

    @EventHandler(
            priority = EventPriority.LOW
    )
    public void onPlayerEarlyChat(AsyncPlayerChatEvent event) {
        if (!event.isCancelled()) {
            Player talkingPlayer = event.getPlayer();
            String msg = event.getMessage();
            FPlayer me = FPlayers.i.get(talkingPlayer.getUniqueId().toString());
            ChatMode chat = me.getChatMode();

            if (chat != ChatMode.PUBLIC && this.p.handleCommand(talkingPlayer, msg, false, true)) {
                if (Conf.logPlayerCommands) {
                    Bukkit.getLogger().log(Level.INFO, "[PLAYER_COMMAND] " + talkingPlayer.getName() + ": " + msg);
                }
                event.setCancelled(true);
            } else {
                String upper = msg.toUpperCase();
                boolean showingSomething = upper.contains("[BRAG]") || upper.contains("[ITEM]") && talkingPlayer.getItemInHand() != null && talkingPlayer.getItemInHand().getType() != Material.AIR;
                boolean silentMuted = talkingPlayer.hasMetadata("silentMute");
                Faction myFaction;
                Iterator<FPlayer> var12;
                FPlayer fplayer;
                String playerName;
                String nameAndTitle;
                if (chat == ChatMode.FACTION) {
                    myFaction = me.getFaction();
                    playerName = me.getNameAsync();
                    nameAndTitle = me.describeToAsync(myFaction).replace(playerName, "");
                    if (!showingSomething) {
                        if (silentMuted) {
                            talkingPlayer.sendMessage(String.format(Conf.factionChatFormat, nameAndTitle, playerName, ChatUtils.getFilteredMessage(msg, talkingPlayer, true)));
                        } else {

                            String finalMsg2 = msg;
                            myFaction.getFPlayersWhereOnline(true).forEach(fpl -> {
                                if (fpl.isCachedAsOnline()) {
                                    fpl.getPlayer().sendMessage(String.format(Conf.factionChatFormat, nameAndTitle, playerName, ChatUtils.getFilteredMessage(finalMsg2, fpl.getPlayer(), true)));
                                }
                            });
                        }

                        Bukkit.getLogger().log(Level.INFO, ChatColor.stripColor("FactionChat (" + myFaction.getTag() + ") " + talkingPlayer.getName() + ": " + msg));
                        var12 = FPlayers.i.getOnline().iterator();

                        while(var12.hasNext()) {
                            fplayer = var12.next();
                            if (fplayer.isSpyingChat() && fplayer.getFaction() != myFaction) {
                                fplayer.sendMessage("[FCspy] " + myFaction.getTag() + ": " + String.format(Conf.factionChatFormat, nameAndTitle, playerName, ChatUtils.getFilteredMessage(msg, fplayer.getPlayer(), true)));
                            }
                        }

                        event.setCancelled(true);
                    }
                } else if (chat == ChatMode.MOD) {
                    myFaction = me.getFaction();
                    if (!me.getRole().isAtLeast(Role.MODERATOR)) {
                        me.getPlayer().sendMessage(ChatColor.RED + "You must be a faction moderator to speak in /f c m");
                        event.setCancelled(true);
                    } else {
                        playerName = me.getNameAsync();
                        nameAndTitle = ChatColor.stripColor(me.describeToAsync(myFaction).replace(playerName, ""));
                        if (!showingSomething) {
                            if (silentMuted) {
                                talkingPlayer.sendMessage(String.format(Conf.modChatFormat, nameAndTitle, playerName, ChatUtils.getFilteredMessage(msg, talkingPlayer, true)));
                            } else {
                                for (FPlayer pl : myFaction.getFPlayersWhereOnline(true)) {
                                    if (pl.getRole().isAtLeast(Role.MODERATOR) || pl.getRole().isCoLeader()) {
                                        pl.getPlayer().sendMessage(String.format(Conf.modChatFormat, nameAndTitle, playerName, ChatUtils.getFilteredMessage(msg, pl.getPlayer(), true)));
                                    }
                                }
                            }

                            Bukkit.getLogger().log(Level.INFO, ChatColor.stripColor("ModChat (" + myFaction.getTag() + ") " + talkingPlayer.getName() + ": " + msg));
                            var12 = FPlayers.i.getOnline().iterator();

                            while(var12.hasNext()) {
                                fplayer = var12.next();
                                if (fplayer.isSpyingChat() && fplayer.getFaction() != myFaction) {
                                    fplayer.sendMessage("[FMCspy] " + myFaction.getTag() + ": " + String.format(Conf.modChatFormat, nameAndTitle, playerName, ChatUtils.getFilteredMessage(msg, fplayer.getPlayer(), true)));
                                }
                            }

                            event.setCancelled(true);
                        }
                    }
                } else {
                    Iterator<FPlayer> var10;
                    if (chat == ChatMode.ALLIANCE) {
                        myFaction = me.getFaction();
                        if (!showingSomething) {
                            if (silentMuted) {
                                talkingPlayer.sendMessage(String.format(Conf.allianceChatFormat, ChatColor.stripColor(me.getNameAndTagAsync()), ChatUtils.getFilteredMessage(msg, talkingPlayer, true)));
                            } else {
                                String finalMsg1 = msg;
                                myFaction.getFPlayersWhereOnline(true).forEach(fpl -> {
                                    fpl.getPlayer().sendMessage(String.format(Conf.allianceChatFormat, ChatColor.stripColor(me.getNameAndTagAsync()), ChatUtils.getFilteredMessage(finalMsg1, fpl.getPlayer(), true)));
                                });
                            }

                            var10 = FPlayers.i.getOnline().iterator();

                            while(var10.hasNext()) {
                                fplayer = var10.next();
                                if (myFaction.getRelationTo(fplayer) == Relation.ALLY) {
                                    fplayer.sendMessage(String.format(Conf.allianceChatFormat, ChatColor.stripColor(me.getNameAndTagAsync()), ChatUtils.getFilteredMessage(msg, fplayer.getPlayer(), true)));
                                } else if (fplayer.isSpyingChat()) {
                                    fplayer.sendMessage("[ACspy] " + myFaction.getTag() + ": " + String.format(Conf.allianceChatFormat, ChatColor.stripColor(me.getNameAndTagAsync()), ChatUtils.getFilteredMessage(msg, fplayer.getPlayer(), true)));
                                }
                            }

                            Bukkit.getLogger().log(Level.INFO, ChatColor.stripColor("AllianceChat: (" + myFaction.getTag() + ") " + talkingPlayer.getName() + ": " + msg));
                            event.setCancelled(true);
                        }
                    } else if (chat == ChatMode.TRUCE) {
                        myFaction = me.getFaction();
                        if (!showingSomething) {
                            if (silentMuted) {
                                talkingPlayer.sendMessage(msg);
                            } else {
                                String finalMsg = msg;
                                myFaction.getFPlayersWhereOnline(true).forEach(fpl -> {
                                    fpl.getPlayer().sendMessage(String.format(Conf.truceChatFormat, ChatColor.stripColor(me.getNameAndTagAsync()), ChatUtils.getFilteredMessage(finalMsg, fpl.getPlayer(), true)));
                                });
                                var10 = FPlayers.i.getOnline().iterator();

                                while(var10.hasNext()) {
                                    fplayer = var10.next();
                                    if (myFaction.getRelationTo(fplayer) == Relation.TRUCE) {
                                        fplayer.sendMessage(String.format(Conf.truceChatFormat, ChatColor.stripColor(me.getNameAndTagAsync()), ChatUtils.getFilteredMessage(msg, talkingPlayer, true)));
                                    } else if (fplayer.isSpyingChat()) {
                                        fplayer.sendMessage("[FTCspy] " + myFaction.getTag() + ": " + String.format(Conf.truceChatFormat, ChatColor.stripColor(me.getNameAndTagAsync()), ChatUtils.getFilteredMessage(msg, fplayer.getPlayer(), true)));
                                    }
                                }
                            }

                            Bukkit.getLogger().log(Level.INFO, ChatColor.stripColor("TruceChat: (" + myFaction.getTag() + ") " + talkingPlayer.getName() + ": " + msg + " " + (silentMuted ? "SILENCED" : "")));
                            event.setCancelled(true);
                        }
                    }
                }
            }
        }
    }

    @EventHandler(
            priority = EventPriority.MONITOR,
            ignoreCancelled = true
    )
    public void onPlayerChatMonitor(PlayerChatEvent event) {
        FPlayer follower = FPlayers.i.get(event.getPlayer().getUniqueId().toString());
        double balance;
        User user;
        if (CmdShow.isUUID(follower.getAccountId())) {
            user = EssentialsFeatures.getEssentials().getUser(UUID.fromString(follower.getId()));
            balance = Econ.econ.getBalance(Bukkit.getOfflinePlayer(UUID.fromString(follower.getId())));
        } else {
            user = EssentialsFeatures.getEssentials().getUser(follower.getId());
            balance = Econ.econ.getBalance(Bukkit.getOfflinePlayer(follower.getId()));
        }

        int seasonalWins;
        List<SeasonalWin> seasonalWinDetails;
        List<FancyMessage> seasonalHoverInfo = null;
        if (ArkPlugins.SEASONAL_PREFIXES.getArkPlugin().isRunning()) {
            seasonalWins = SeasonalPrefixes.getSeasonalWins(event.getPlayer());
            seasonalWinDetails = SeasonalPrefixes.getSeasonalWinDetails(event.getPlayer());
            if (seasonalWins > 0) {
                seasonalHoverInfo = Lists.newArrayList();
                seasonalHoverInfo.add(new FancyMessage(ChatColor.valueOf(Conf.hoverNames) + event.getPlayer().getName() + "'s faction has " + ChatColor.valueOf(Conf.hoverChatColor) + ChatColor.UNDERLINE + seasonalWins + ChatColor.valueOf(Conf.hoverNames) + " Seasonal Planet trophies!"));

                for (SeasonalWin win : seasonalWinDetails) {
                    seasonalHoverInfo.add(new FancyMessage(ChatColor.valueOf(Conf.hoverNames) + "Planet: " + ChatColor.valueOf(Conf.hoverChatColor) + win.getPlanet()));
                    seasonalHoverInfo.add(new FancyMessage(ChatColor.valueOf(Conf.hoverNames) + "Faction: " + ChatColor.valueOf(Conf.hoverChatColor) + win.getFaction()));
                    seasonalHoverInfo.add(new FancyMessage(ChatColor.valueOf(Conf.hoverNames) + "Map: " + ChatColor.valueOf(Conf.hoverChatColor) + "#" + win.getSeason()));
                    seasonalHoverInfo.add(new FancyMessage(ChatColor.valueOf(Conf.hoverChatColor) + "----------"));
                }
            }
        }

        List<FancyMessage> creatorHoverInfo = null;
        String channelName = null;
        String channelURL = null;

        double elo = 0.0D;
        if (Bukkit.getPluginManager().isPluginEnabled("CosmicDuel") && !this.vanillaGalaxy) {
            try {
                DuelPlayer player = DuelPlayer.getDuelPlayer(event.getPlayer());
                if (player != null) {
                    elo = player.getElo();
                }
            } catch (Exception var38) {
            }
        }

        List<FancyMessage> player_faction_info = Lists.newArrayList(new FancyMessage(ChatColor.valueOf(Conf.hoverNames) + "Username: " + ChatColor.valueOf(Conf.hoverChatColor) + event.getPlayer().getName()), new FancyMessage(ChatColor.valueOf(Conf.hoverNames) + "Nick: " + ChatColor.valueOf(Conf.hoverChatColor) + event.getPlayer().getDisplayName()), new FancyMessage(ChatColor.valueOf(Conf.hoverNames) + "Faction: " + ChatColor.valueOf(Conf.hoverChatColor) + follower.getFaction().getTag()), null, new FancyMessage(ChatColor.valueOf(Conf.hoverNames) + "Balance: " + ChatColor.valueOf(Conf.hoverChatColor) + "$" + this.formatter.format(balance)));
        if (1 == 2) {
            player_faction_info.set(3, new FancyMessage(ChatColor.valueOf(Conf.hoverNames) + "Duel ELO: " + ChatColor.valueOf(Conf.hoverChatColor) + this.eloFormat.format(elo)));
        } else {
            player_faction_info.remove(3);
        }

        String player_display_name = event.getPlayer().getDisplayName();

        player_faction_info.add(new FancyMessage(ChatColor.valueOf(Conf.hoverNames) + "Power: " + ChatColor.valueOf(Conf.hoverChatColor) + follower.getPowerRounded() + ChatColor.valueOf(Conf.hoverSlashColor) + "/" + ChatColor.valueOf(Conf.hoverChatColor) + (int)Conf.powerPlayerMax));
        player_faction_info.add(new FancyMessage(ChatColor.valueOf(Conf.hoverNames) + "Logged in: " + ChatColor.valueOf(Conf.hoverChatColor) + DateUtil.formatDateDiff(user.getLastLogin()) + " ago"));
        player_faction_info.add(new FancyMessage(CC.translate("&eCosmic Client: ") + ((CosmicPlayers.get().getCosmicPlayer(event.getPlayer()).isOnCosmicClient()) ? ChatColor.GREEN.toString() + ChatColor.BOLD + "✓" : ChatColor.RED.toString() + ChatColor.BOLD + "✗")));

        event.setCancelled(true);

        Player player;
        FancyMessage fancy_message;
        for(Iterator<Player> var18 = event.getRecipients().iterator(); var18.hasNext(); fancy_message.send(player)) {
            player = var18.next();
            FPlayer you = FPlayers.i.get(player);
            String current_string = event.getFormat();

                current_string = current_string.replace("[FACTION]", follower.getChatTag(you).trim().replace(" ", ""));

            current_string = current_string.replace("%1$s", "/UniqueSplitString/");
            String[] current_message_string = current_string.split("/UniqueSplitString/");
            fancy_message = seasonalHoverInfo != null ? (new FancyMessage(current_message_string[0])).formattedTooltip(seasonalHoverInfo) : new FancyMessage(current_message_string[0]);
            if (current_message_string.length > 1) {
                if (channelURL == null) {
                    fancy_message.then(player_display_name).formattedTooltip(player_faction_info).command("/f who " + event.getPlayer().getName());
                } else {
                    String playerRank = player_display_name.substring(0, player_display_name.lastIndexOf(">") + 1);
                    String playerName = player_display_name.substring(player_display_name.lastIndexOf(">") + 1);
                    fancy_message.then(playerRank).formattedTooltip(creatorHoverInfo).link(channelURL).then(playerName).formattedTooltip(player_faction_info).command("/f who " + event.getPlayer().getName());
                }

                int current_count = 0;
                String[] var49 = current_message_string;
                int var27 = current_message_string.length;

                for(int var28 = 0; var28 < var27; ++var28) {
                    String split = var49[var28];
                    ++current_count;
                    if (current_count > 1) {
                        split = split.replace("%2$s", ChatUtils.getFilteredMessage(event.getMessage(), player, true));
                        List<ChatColor> last_colors = new ArrayList<ChatColor>();
                        String[] var31 = split.split(" ");
                        int var32 = var31.length;

                        for (String s : var31) {
                            String message_part = s;
                            fancy_message.then(message_part);
                            Matcher matcher = this.COLOR_PATTERN.matcher(message_part);
                            if (matcher.find() && matcher.group().length() > 0) {
                                last_colors.clear();

                                for (int i = 0; i <= matcher.groupCount(); ++i) {
                                    last_colors.add(ChatColor.getByChar(matcher.group(i).replace("§", "")));
                                }
                            }

                            if (!last_colors.isEmpty()) {

                                for (ChatColor color : last_colors) {
                                    fancy_message.color(color);
                                }
                            }

                            message_part = ChatColor.stripColor(message_part);
                            if (this.checkMessageForWebPattern(message_part)) {
                                fancy_message.link(message_part);
                            }

                            fancy_message.then(" ");
                        }
                    }
                }
            }
        }
        Bukkit.getConsoleSender().sendMessage(String.format(event.getFormat(), event.getPlayer().getDisplayName(), event.getMessage()).replace("•", "-"));
    }

    private boolean checkMessageForWebPattern(String message) {
        if (!message.startsWith("http://") && !message.startsWith("https://")) {
            return false;
        } else {
            Matcher regexMatcherurl = this.webpattern.matcher(message);

            String text;
            do {
                if (!regexMatcherurl.find()) {
                    return false;
                }

                text = regexMatcherurl.group().trim().replaceAll("www.", "").replaceAll("http://", "").replaceAll("https://", "");
            } while(regexMatcherurl.group().length() == 0 || text.length() == 0 || !this.webpattern.matcher(message).find());

            return true;
        }
    }
}
