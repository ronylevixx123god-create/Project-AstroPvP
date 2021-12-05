package me.astropvp.astroscoreboard.board;

import com.compilex.CosmicDungeons.DungeonsAPI;
import com.compilex.CosmicDungeons.dungeons.events.AbandonedShipEvents;
import com.compilex.CosmicDungeons.dungeons.libs.DungeonPlayer;
import com.compilex.CosmicDungeons.dungeons.libs.DungeonType;
import com.compilex.CosmicKOTH.CosmicKOTHAPI;
import com.compilex.CosmicKOTH.CosmicKOTHPlugin;
import com.compilex.CosmicKOTH.tasks.KothEventTask;
import com.compilex.CosmicOutposts.outpost.Outpost;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.FactionUtils;
import com.cosmicpvp.cosmicutils.utils.NumberUtils;
import com.cosmicpvp.cosmicutils.utils.WorldGuardUtils;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.integration.Econ;
import com.reliableplugins.printer.Printer;
import com.reliableplugins.printer.PrinterPlayer;
import me.astropvp.astroendgrinding.data.EndPlayerData;
import me.astropvp.astroendgrinding.enums.MonsterType;
import me.astropvp.astroendgrinding.utils.TimeUtils;
import me.astropvp.astroscoreboard.AstroScoreboardPlugin;
import me.astropvp.astrospawners.AstroSpawners;
import me.astropvp.astrospawners.utils.PlayerData;
import me.astropvp.astrospawners.utils.PlayerDataManager;
import me.bryside.WildPvPPlugin;
import me.bryside.match.CageMatch;
import me.ifamasssxd.cosmicbosses.bosses.yijki.Yijki;
import me.ifamasssxd.cosmicbosses.dungeonboss.DungeonYijki;
import me.ifamasssxd.cosmicbosses.dungeonboss.planetnul.CorruptCosmonaut;
import me.ifamasssxd.cosmicbosses.struct.Boss;
import me.ifamasssxd.cosmicbosses.utils.BossUtils;
import me.ifamasssxd.cosmicbosses.utils.ScoreboardUtils;
import me.ifamasssxd.cosmicduel.CosmicDuel;
import me.ifamasssxd.cosmicduel.DuelAPI;
import me.ifamasssxd.cosmicduel.struct.TeamData;
import me.ifamasssxd.cosmicduel.struct.enums.DuelState;
import me.ifamasssxd.cosmicduel.struct.enums.DuelType;
import me.ifamasssxd.cosmicduel.struct.newduel.TeamDuel;
import me.ifamasssxd.cosmichcf.managers.OutpostManager;
import org.arkhamnetwork.enchantments.enchantments.axes.Corrupt;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Scoreboard implements BoardAdapter {
    private final AstroScoreboardPlugin plugin = AstroScoreboardPlugin.getInstance();
    private static DateFormat format = new SimpleDateFormat("MM/dd");
    private final Map<String, String> rankOrder = new HashMap<>();

    public List<String> getScoreboard(Player player, Board board) {
        return mainBoard(player);
    }

    public static String getDateSuffix() {
        return format.format(new Date());
    }

    public void preLoop() {
    }

    public Scoreboard() {
        rankOrder.put("owner", "1");
        rankOrder.put("manager", "2");
        rankOrder.put("admin", "3");
        rankOrder.put("banteam", "4");
        rankOrder.put("srmod", "5");
        rankOrder.put("mod", "6");
        rankOrder.put("helper", "7");
        rankOrder.put("media", "8");
        rankOrder.put("apollo", "9");
        rankOrder.put("astro+", "a");
        rankOrder.put("astro", "b");


    }


    /*public String getTitle(Player player) {
        FPlayer fplayer = FactionUtils.getFPlayer(player);

        return CC.translate("&a&lAstro &7⎜ &fMap V");
    }*/

    public String getTitle(Player player) {
        FPlayer fplayer = FactionUtils.getFPlayer(player);
        if(DungeonsAPI.get().getPlayerDungeonData(player).getCurrentDungeon() != null && (DungeonsAPI.get().getPlayerDungeonData(player).getCurrentDungeon().getDungeonType() == DungeonType.ABANDONED_SHIP || DungeonsAPI.get().getPlayerDungeonData(player).getCurrentDungeon().getDungeonType() == DungeonType.DESTROYED_OUTPOST)) {
            for(Entity entity : player.getNearbyEntities(32, 32, 32)) {
                if(entity.hasMetadata("boss")) {
                    if(Boss.bosses.get(entity.getUniqueId()) instanceof DungeonYijki) {
                        return DungeonsAPI.get().getPlayerDungeonData(player).getCurrentDungeon().getDungeonType() == DungeonType.ABANDONED_SHIP ? CC.GoldB + "Mother Of Yijki" : CC.LightPurpleB + "Mother Of Yijki";
                    }
                }
            }
        }
        if(DungeonsAPI.get().getPlayerDungeonData(player).getCurrentDungeon() != null && (DungeonsAPI.get().getPlayerDungeonData(player).getCurrentDungeon().getDungeonType() == DungeonType.PLANET_NUL)) {
            for(Entity entity : player.getNearbyEntities(32, 32, 32)) {
                if(entity.hasMetadata("boss")) {
                    if(Boss.bosses.get(entity.getUniqueId()) instanceof CorruptCosmonaut) {
                        return CC.GoldB + "The Corrupt Cosmonaut";
                    }
                }
            }
        }

        if (PrinterPlayer.fromPlayer(player) != null && PrinterPlayer.fromPlayer(player).isPrinting()) {
            return CC.translate("&d&lPrinter Mode&7 " + getDateSuffix());
        }

        if (WorldGuardUtils.getRegionName(player.getLocation()).contains("infernal")) {
            return CC.translate("&c&lInfernal Stronghold &7" + getDateSuffix());
        }

            if (WorldGuardUtils.getRegionName(player.getLocation()).contains("frozen")) {
            return CC.translate("&b&lFrozen Stronghold &7" + getDateSuffix());

          }else if (DuelAPI.getDuel(player) != null && (DuelAPI.getDuel(player).getDuelState() == DuelState.STARTING || DuelAPI.getDuel(player).getDuelState() == DuelState.PLAYING)) {
                if (DuelAPI.getDuel(player).getDuelType() == DuelType.ONE_VS_ONE) {
                    return CC.translate("&6&l1v1 Duel &7" + getDateSuffix());
                }

        } else if(!fplayer.getFaction().isWilderness()) {
            return CC.translate("&a&l" + fplayer.getFaction().getTag() + " &7" + getDateSuffix());
        }

        return CC.translate("&a&lAstro&f&lPvP &7" + getDateSuffix());
    }


    public List<String> mainBoard(Player player) {

        if (player.getWorld().getName().startsWith("dungeon_")) {
            if (DungeonsAPI.get().getPlayerDungeonData(player).getCurrentDungeon() != null && DungeonsAPI.get().getPlayerDungeonData(player).getCurrentDungeon().getDungeonType() == DungeonType.PLANET_NUL) {
                for (Entity entity : player.getNearbyEntities(32, 32, 32)) {
                    if (entity.hasMetadata("boss")) {
                        if (Boss.bosses.get(entity.getUniqueId()) instanceof CorruptCosmonaut) {
                            CorruptCosmonaut yijki = (CorruptCosmonaut) Boss.bosses.get(entity.getUniqueId());
                            List<String> board = new LinkedList<>();
                            board.add("");
                            board.add(CC.RedB + "Health");
                            DecimalFormat format = new DecimalFormat("###.#");
                            board.add(CC.Red + format.format(yijki.getEntity().getHealth()));
                            if (yijki.hasMinions()) {
                                board.add("");
                                board.add(CC.YellowB + "Minions");
                                board.add(CC.Yellow + yijki.getMinions().size());
                            }
                            board.add("");
                            board.add(CC.AquaB + "Ability");
                            board.add(CC.Aqua + yijki.getActiveAbility().getAbilityName());
                            board.add("");
                            board.add(CC.GreenB + "Damage Dealt");
                            int damage = yijki.getDamageTracker().containsKey(player) ? yijki.getDamageTracker().get(player) : 0;
                            int totalDamage = 0;
                            for (Integer dmg : (yijki.getDamageTracker()).values()) {
                                totalDamage += dmg;
                            }
                            double percent = (double) damage / totalDamage * 100.0;
                            String string = ChatColor.GREEN.toString() + damage + ChatColor.GRAY + " [" + format.format(percent) + "%]";
                            board.add(CC.Green + string);
                            return board;
                        }
                    }
                }
            }

            if (DungeonsAPI.get().getPlayerDungeonData(player).getCurrentDungeon() != null && (DungeonsAPI.get().getPlayerDungeonData(player).getCurrentDungeon().getDungeonType() == DungeonType.DESTROYED_OUTPOST || DungeonsAPI.get().getPlayerDungeonData(player).getCurrentDungeon().getDungeonType() == DungeonType.ABANDONED_SHIP)) {
                for (Entity entity : player.getNearbyEntities(32, 32, 32)) {
                    if (entity.hasMetadata("boss")) {
                        if (Boss.bosses.get(entity.getUniqueId()) instanceof DungeonYijki) {
                            DungeonYijki yijki = (DungeonYijki) Boss.bosses.get(entity.getUniqueId());
                            List<String> board = new LinkedList<>();
                            board.add("");
                            board.add(CC.RedB + "Health");
                            DecimalFormat format = new DecimalFormat("###.#");
                            board.add(CC.Red + format.format(yijki.getEntity().getHealth()));
                            board.add("");
                            board.add(CC.AquaB + "Ability");
                            board.add(CC.Aqua + yijki.getActiveAbility().getAbilityName());
                            board.add("");
                            board.add(CC.GreenB + "Weakness");
                            board.add(CC.Green + Yijki.WitherPhase.getPhase(yijki.getPhase()).getPhaseName());
                            board.add("");
                            board.add(CC.GreenB + "Damage Dealt");
                            int damage = yijki.getDamageTracker().containsKey(player) ? yijki.getDamageTracker().get(player) : 0;
                            int totalDamage = 0;
                            for (Integer dmg : (yijki.getDamageTracker()).values()) {
                                totalDamage += dmg;
                            }
                            double percent = (double) damage / totalDamage * 100.0;
                            String string = ChatColor.GREEN.toString() + damage + ChatColor.GRAY + " [" + format.format(percent) + "%]";
                            board.add(CC.Green + string);
                            return board;
                        }
                    }
                }
            }

            if (DungeonsAPI.get().getPlayerDungeonData(player).getCurrentDungeon() != null && (DungeonsAPI.get().getPlayerDungeonData(player).getCurrentDungeon().getDungeonType() == DungeonType.PLANET_NUL)) {
                for (Entity entity : player.getNearbyEntities(32, 32, 32)) {
                    if (entity.hasMetadata("boss")) {
                        if (Boss.bosses.get(entity.getUniqueId()) instanceof DungeonYijki) {
                            CorruptCosmonaut cosmonaut = (CorruptCosmonaut) Boss.bosses.get(entity.getUniqueId());
                            List<String> board = new LinkedList<>();
                            board.add("");
                            board.add(CC.RedB + "Health");
                            board.add(CC.Red + Math.round(cosmonaut.getEntity().getHealth()));
                            board.add("");
                            board.add(CC.AquaB + "Ability");
                            board.add(CC.Aqua + cosmonaut.getActiveAbility().getAbilityName());
                            board.add("");
                            board.add(CC.GreenB + "Damage Dealt");
                            int damage = cosmonaut.getDamageTracker().getOrDefault(player, 0);
                            int totalDamage = 0;
                            for (final Integer dmg : (cosmonaut.getDamageTracker()).values()) {
                                totalDamage += dmg;
                            }
                            final double percent = (double) damage / totalDamage * 100.0;
                            DecimalFormat format = new DecimalFormat("###.#");
                            String string = ChatColor.GREEN.toString() + damage + ChatColor.GRAY + " [" + format.format(percent) + "%]";
                            if (string.length() > 16) {
                                string = string.replace("[", "").replace("]", "");
                                if (string.length() > 16) {
                                    string = string.substring(0, 16);
                                }
                            }
                            board.add(CC.Green + string);
                            return board;
                        }
                    }
                }
            }
        }





        if (PrinterPlayer.fromPlayer(player) != null && PrinterPlayer.fromPlayer(player).isPrinting()) {
            PrinterPlayer printerPlayer = PrinterPlayer.fromPlayer(player);
            List<String> board = new LinkedList<>();
            board.add("");
            board.add(CC.translate("&d&lBalance"));
            board.add(CC.translate("&f$") + NumberUtils.formatMoney(printerPlayer.getRemainingBalance()));
            board.add("");
            board.add(CC.translate("&d&lMode"));
            board.add(CC.translate("&aEnabled"));
            board.add("");
            board.add(CC.translate("&d&lAccount"));
            board.add(CC.translate("&f") + player.getName());
            return board;
        }

        /*if (player.hasMetadata("cageMatch")) {
            List<String> board = new LinkedList<>();
            CageMatch pvpMatch = WildPvPPlugin.getInstance().getMatchManager().getMatchFromPlayer(player);

            board.add("");
            board.add(CC.translate("&a&lOpponent"));


            Player enemy;
            if (pvpMatch.getHost() == player) {
                enemy = pvpMatch.getOpponent();
            } else {
                enemy = pvpMatch.getHost();
            }

            board.add(CC.translate("&c") + enemy.getName());
            board.add("");
            board.add(CC.translate("&a&lExpiration"));
            board.add(String.valueOf(pvpMatch.getRuntime()) + "s");
            board.add("");
            board.add(CC.translate("&a&lType"));
            board.add(CC.translate("&fCage Match"));
            board.add("");
            board.add(CC.translate("&a&lAccount"));
            board.add(player.getName());

            return board;
        }*/

            if (WorldGuardUtils.getRegionName(player.getLocation()).contains("frozen")) {
            List<String> board = new LinkedList<>();
            OutpostManager outpostManager = CosmicKOTHPlugin.get_().getOutpostManager();
            me.ifamasssxd.cosmichcf.struct.Outpost outpost = outpostManager.getOutpostFromName("great_hall");

            board.add("");
            board.add(CC.translate("&b&lAttacking"));
            if (outpost.getAttackingFaction() != null) {
                board.add(CC.translate("&f") + outpost.getAttackingFaction().getTag());
            } else {
                board.add(CC.translate("&fN/A"));
            }
            board.add("");
            board.add(CC.translate("&b&lControlling"));
            if (outpost.getDefendingFaction() != null) {
                board.add(outpost.getDefendingFaction().getTag());
            } else {
                board.add(CC.translate("&fN/A"));
            }
            board.add("");
            board.add(CC.translate("&b&lCap %"));
            board.add(round(outpost.getCapPercentage(), 2) + "%");
            board.add("");


            board.add(CC.translate("&b&lStatus"));
            try {
                board.add(CC.translate("&f") + outpostManager.getOutpostInside(player.getLocation()).getOutpostState().getName());

            } catch (NullPointerException e) {
                board.add(CC.translate("&fN/A"));

            }

            board.add("");
            board.add(CC.translate("&b&lAccount"));
            board.add(CC.translate("&f") + player.getName());
            return board;
        }

        if (WorldGuardUtils.getRegionName(player.getLocation()).contains("infernal")) {
            List<String> board = new LinkedList<>();
            OutpostManager outpostManager = CosmicKOTHPlugin.get_().getOutpostManager();
            me.ifamasssxd.cosmichcf.struct.Outpost outpost = outpostManager.getOutpostFromName("courtyard");

            board.add("");
            board.add(CC.translate("&c&lAttacking"));
            if (outpost.getAttackingFaction() != null) {
                board.add(CC.translate("&f") + outpost.getAttackingFaction().getTag());
            } else {
                board.add(CC.translate("&fN/A"));
            }
            board.add("");
            board.add(CC.translate("&c&lControlling"));
            if (outpost.getDefendingFaction() != null) {
                board.add(outpost.getDefendingFaction().getTag());
            } else {
                board.add(CC.translate("&fN/A"));
            }
            board.add("");
            board.add(CC.translate("&c&lCap %"));
            board.add(round(outpost.getCapPercentage(), 2) + "%");
            board.add("");


            board.add(CC.translate("&c&lStatus"));
            try {
                board.add(CC.translate("&f") + outpostManager.getOutpostInside(player.getLocation()).getOutpostState().getName());

            } catch (NullPointerException e) {
                board.add(CC.translate("&fN/A"));

            }

            board.add("");
            board.add(CC.translate("&c&lAccount"));
            board.add(CC.translate("&f") + player.getName());
            return board;
        }


        if (CosmicKOTHAPI.isKOTHRunning() && player.getLocation().getWorld().getName().equals("world_koth")) {
            KothEventTask koth = CosmicKOTHPlugin.get_().getKoth();
            List<String> board = new LinkedList<>();
            board.add("");
            board.add(CC.translate(CC.White + "➥ " + "&a&lKOTH Event"));
            if (koth.getPlayerCapping() != null) {
                board.add(CC.translate("&a Capper&7: &f" + koth.getPlayerCapping().getName()));
                board.add(CC.translate("&a Time Left&7: &f") + koth.getSecondsUntilCap() + "s");
            }
            board.add(CC.translate("&a Location&7: &7X37, Y52, Z-28"));
            board.add("");
            board.add(CC.White + "play.astropvp.net");
            return board;
        }

        if (DuelAPI.getDuel(player) != null && (DuelAPI.getDuel(player).getDuelState() == DuelState.STARTING || DuelAPI.getDuel(player).getDuelState() == DuelState.PLAYING)) {
            List<String> board = new LinkedList<>();

            if (DuelAPI.getDuel(player).getDuelType() == DuelType.ONE_VS_ONE) {

                int seconds = DuelAPI.getDuel(player).getGameTimer();
                int minutes = seconds / 60;
                seconds = seconds - (minutes * 60);

                board.add("");
                board.add(CC.translate("&c&lOpponent"));
                board.add(CC.translate("&f" + DuelAPI.getDuel(player).getOppositePlayer(player).getName()));
                board.add("");
                if (DuelAPI.getDuel(player).getGameTimer() != 0) {
                    board.add(CC.translate("&6&lRuntime"));
                    if (minutes > 0) {
                        board.add(CC.translate("&e") + minutes + "m " + seconds + "s");
                    } else {
                        board.add(CC.translate("&e") + seconds + "s");

                    }
                    board.add("");
                }
                board.add(CC.translate("&6&lArena"));
                board.add(CC.translate("&f&l") + CC.strip(DuelAPI.getDuel(player).getArena().getName()));
                board.add("");
                board.add(CC.translate("&6&lDouble DMG"));
                if (DuelAPI.getDuel(player).getGameTimer() >= 300) {
                    board.add(CC.translate("&a&lEnabled"));
                } else {
                    board.add(CC.translate("&c&lDisabled"));
                }
                board.add("");
                board.add(CC.translate("&6&lAccount"));
                board.add(CC.translate("&e") + player.getName());
                return board;
            }
        }

        if (player.getLocation().getWorld().getName().equals("cosmic-station-vanilla")) {
            Outpost vanillaOutpost = CosmicKOTHPlugin.get_().getVanillaOutpost();
            List<String> board = new LinkedList<>();
            board.add("");
            board.add(CC.translate(CC.White + "➥ " + "&c&lVanilla Outpost"));
            if (vanillaOutpost.getCapTask().getAttackingFaction() != null) {
                board.add(CC.translate("&c Attacking&7: &f") + vanillaOutpost.getCapTask().getAttackingFaction().getTag());
            }

            if (vanillaOutpost.getCapTask().getDefendingFaction() != null) {
                board.add(CC.translate("&c Defending&7: &f") + vanillaOutpost.getCapTask().getDefendingFaction().getTag());
            }
            board.add(CC.translate("&c Cap&7: &a") + vanillaOutpost.getCapTask().roundCapPercentDisplay() + "%");
            board.add("");
            if (vanillaOutpost.getCapStatus() != null) {
                board.add(CC.translate("&c Status&7: &f") + vanillaOutpost.getCapStatus());
            }
            board.add("");
            board.add(CC.White + "play.astropvp.net");
            return board;
        }

        if (player.getLocation().getWorld().getName().equals("cosmic-station-1")) {
            Outpost vanillaOutpost = CosmicKOTHPlugin.get_().getTraineeOutpost();
            List<String> board = new LinkedList<>();
            board.add("");
            board.add(CC.translate(CC.White + "➥ " + "&7&lTrainee Outpost"));
            if (vanillaOutpost.getCapTask().getAttackingFaction() != null) {
                board.add(CC.translate("&7 Attacking&7: &f") + vanillaOutpost.getCapTask().getAttackingFaction().getTag());
            }

            if (vanillaOutpost.getCapTask().getDefendingFaction() != null) {
                board.add(CC.translate("&7 Defending&7: &f") + vanillaOutpost.getCapTask().getDefendingFaction().getTag());
            }
            board.add(CC.translate("&7 Cap&7: &a") + vanillaOutpost.getCapTask().roundCapPercentDisplay() + "%");
            board.add("");
            if (vanillaOutpost.getCapStatus() != null) {
                board.add(CC.translate("&7 Status&7: &f") + vanillaOutpost.getCapStatus());
            }
            board.add("");
            board.add(CC.White + "play.astropvp.net");
            return board;
        }

        if (player.getLocation().getWorld().getName().equals("cosmic-station-2")) {
            Outpost vanillaOutpost = CosmicKOTHPlugin.get_().getHeroOutpost();
            List<String> board = new LinkedList<>();
            board.add("");
            board.add(CC.translate(CC.White + "➥ " + "&b&lHero Outpost"));
            if (vanillaOutpost.getCapTask().getAttackingFaction() != null) {
                board.add(CC.translate("&b Attacking&7: &f") + vanillaOutpost.getCapTask().getAttackingFaction().getTag());
            }

            if (vanillaOutpost.getCapTask().getDefendingFaction() != null) {
                board.add(CC.translate("&b Defending&7: &f") + vanillaOutpost.getCapTask().getDefendingFaction().getTag());
            }
            board.add(CC.translate("&b Cap&7: &a") + vanillaOutpost.getCapTask().roundCapPercentDisplay() + "%");
            board.add("");
            if (vanillaOutpost.getCapStatus() != null) {
                board.add(CC.translate("&b Status&7: &f") + vanillaOutpost.getCapStatus());
            }
            board.add("");
            board.add(CC.White + "play.astropvp.net");
            return board;
        }

        if (player.getLocation().getWorld().getName().equals("cosmic-station-3")) {
            Outpost vanillaOutpost = CosmicKOTHPlugin.get_().getCosmonautOutpost();
            List<String> board = new LinkedList<>();
            board.add("");
            board.add(CC.translate(CC.White + "➥ " + "&6&lCosmonaut Outpost"));
            if (vanillaOutpost.getCapTask().getAttackingFaction() != null) {
                board.add(CC.translate("&6 Attacking&7: &f") + vanillaOutpost.getCapTask().getAttackingFaction().getTag());
            }

            if (vanillaOutpost.getCapTask().getDefendingFaction() != null) {
                board.add(CC.translate("&6 Defending&7: &f") + vanillaOutpost.getCapTask().getDefendingFaction().getTag());
            }
            board.add(CC.translate("&6 Cap&7: &a") + vanillaOutpost.getCapTask().roundCapPercentDisplay() + "%");
            board.add("");
            if (vanillaOutpost.getCapStatus() != null) {
                board.add(CC.translate("&6 Status&7: &f") + vanillaOutpost.getCapStatus());
            }
            board.add("");
            board.add(CC.White + "play.astropvp.net");
            return board;
        }

        if (player.getLocation().getWorld().getName().equals("world_the_end")) {
            int endPlayers = 0;
            int totalKills = 0;
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (p.getWorld().getName().equals("world_the_end")) {
                    endPlayers++;
                }
            }
            List<String> board = new LinkedList<>();
            EndPlayerData data = EndPlayerData.findPlayer(player);
            PlayerDataManager manager = AstroSpawners.getInstance().getManager();
            PlayerData dataCoins = manager.getPlayerData(player);
            board.add("");
            board.add(CC.translate(CC.White + "➥ " + "&5&lEnd Grinding"));
            for (MonsterType type : MonsterType.values()) {
                totalKills += data.getKills(type);
            }
            board.add(CC.translate("&d Kills&7: &f") + totalKills);
            board.add(CC.translate("&d End Players&7: &f") + endPlayers);
            board.add(CC.translate("&d Time Spent&7: &f" + TimeUtils.format(data.getTimeInEnd())));
            board.add(CC.translate("&d Mobcoins&7:&f " + NumberFormat.getIntegerInstance().format(dataCoins.getCoins()) + "⛁"));

            board.add("");
            board.add(CC.White + "play.astropvp.net");
            return board;
        }
/*
        if (WorldGuardUtils.inRegion(player.getLocation(), "infernalfull")) {
            List<String> board = new LinkedList<>();
            EndPlayerData me.astropvp.cratekeys.data = EndPlayerData.findPlayer(player);
            board.add("");
            board.add(CC.translate("&b&lStronghold"));
            board.add(String.valueOf(CosmicHCFOutposts.get().getOutpostManager().getOutpostInside(player.getLocation()).getOutpostState()));

            board.addLongLine(-3, this.getOutpostName() + " " + this.getPrettyFormattedCapturePercent(), -3);
            scoreboard.addLongLine(-4, CC.Gray + " " + this.getOutpostState().getName() + CC.Green + (this.getOutpostState() == OutpostState.CONTROLLED && this.defendingFaction != null ? " " + CC.Gray + "(" + CC.Green + this.defendingFaction.getTag() + CC.Gray + ")" : ""), -4);
            player.setMetadata("boss_scoreboard", new FixedMetadataValue(CosmicKOTHPlugin.get_(), System.currentTimeMillis() + 30000L));
            scoreboard.setSlot(DisplaySlot.SIDEBAR);

            return board;
        }*/


        PlayerDataManager manager = AstroSpawners.getInstance().getManager();
        PlayerData data = manager.getPlayerData(player);
        List<String> board = new LinkedList<>();
        board.add(" ");
        board.add(ChatColor.AQUA + ChatColor.BOLD.toString() + "Account");
        board.add(ChatColor.WHITE + player.getName());
        board.add(" ");
        board.add(ChatColor.GOLD + ChatColor.BOLD.toString() + "Balance");
        board.add(ChatColor.WHITE.toString() + "$" + NumberUtils.formatMoney((long) Econ.econ.getBalance((OfflinePlayer) player)));
        board.add(" ");
        board.add(ChatColor.RED + ChatColor.BOLD.toString() + "XP");
        board.add(ChatColor.WHITE.toString() + NumberUtils.formatMoney(player.getTotalExperience()));
        board.add(" ");
        board.add(ChatColor.LIGHT_PURPLE + ChatColor.BOLD.toString() + "Mobcoins");
        board.add(CC.White + NumberFormat.getIntegerInstance().format(data.getCoins()) + "⛁");

        /*board.add("");
        board.add(CC.GreenB + player.getName());
        board.add(CC.White + " ● " + CC.Green + "XP" + CC.Gray + ": " + CC.White + NumberFormat.getIntegerInstance().format(player.getTotalExperience()));
        board.add(CC.White + " ● "+ CC.Green + "Ping" + CC.Gray + ": " + CC.LightPurple + getPing() + "ms");
        board.add(CC.White + " ● "+ CC.Green + "Balance" + CC.Gray + ": " + CC.White + "$" + formatIntoShortMoney((long) Econ.econ.getBalance(player)));
        board.add(CC.White + " ● "+ CC.Green + "Mobcoins" + CC.Gray + ": " + CC.White + NumberFormat.getIntegerInstance().format(me.astropvp.cratekeys.data.getCoins()) + "⛁");
        board.add(" ");
        if (fplayer != null) {
            if (fplayer.hasFaction()) {
                board.add(CC.GreenB + fplayer.getFaction().getTag());
                board.add(CC.White + " ● " + CC.Green + "Power" + CC.Gray + ": " + CC.White + fplayer.getPowerRounded() + CC.Gray + "/" + CC.White + fplayer.getPowerMaxRounded());
                board.add(CC.White + " ● "+ CC.Green + "Online" + CC.Gray + ": " + CC.Green + fplayer.getFaction().getOnlinePlayers().size() + CC.Gray + "/" + CC.White + fplayer.getFaction().getFPlayers().size());
                board.add("");
            }
        }
        board.add(CC.Yellow + "play.astropvp.net");*/

        return board;
    }



    public long getInterval() {
        return 20L;
    }

    public int getPing() {
        int randomNum = ThreadLocalRandom.current().nextInt(20, 50 + 1);
        return randomNum;
    }

    public void onScoreboardCreate(Player player, org.bukkit.scoreboard.Scoreboard scoreboard) {

    }

    public AstroScoreboardPlugin getPlugin() {
        return this.plugin;
    }

    private static final NavigableMap<Long, String> suffixes = new TreeMap<>();

    static {
        suffixes.put(1_000L, "k");
        suffixes.put(1_000_000L, "M");
        suffixes.put(1_000_000_000L, "B");
        suffixes.put(1_000_000_000_000L, "T");
        suffixes.put(1_000_000_000_000_000L, "QD");
        suffixes.put(1_000_000_000_000_000_000L, "QT");
    }

    public static String formatIntoShortMoney(long value) {
        //Long.MIN_VALUE == -Long.MIN_VALUE so we need an adjustment here
        if (value == Long.MIN_VALUE) return formatIntoShortMoney(Long.MIN_VALUE + 1);
        if (value < 0) return "-" + formatIntoShortMoney(-value);
        if (value < 1000) return Long.toString(value); //deal with easy case

        Map.Entry<Long, String> e = suffixes.floorEntry(value);
        Long divideBy = e.getKey();
        String suffix = e.getValue();

        long truncated = value / (divideBy / 10); //the number part of the output times 10
        boolean hasDecimal = truncated < 100 && (truncated / 10d) != (truncated / 10);
        return hasDecimal ? (truncated / 10d) + suffix : (truncated / 10) + suffix;
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

}
