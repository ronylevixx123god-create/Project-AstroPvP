package me.astropvp.astroscoreboard.board;

import com.compilex.CosmicDungeons.DungeonsAPI;
import com.compilex.CosmicDungeons.dungeons.libs.DungeonType;
import com.compilex.CosmicKOTH.CosmicKOTHPlugin;
import com.compilex.CosmicKOTH.tasks.KothEventTask;
import com.compilex.CosmicOutposts.outpost.Outpost;
import com.cosmicpvp.cosmicutils.utils.*;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import me.astropvp.astroscoreboard.AstroScoreboardPlugin;
import me.astropvp.astroscoreboard.utils.TimeUtils;
import me.astropvp.astrospacegrinding.data.SpacePlayerData;
import me.astropvp.astrospacegrinding.enums.MonsterType;
import me.astropvp.astrospawners.AstroSpawners;
import me.astropvp.astrospawners.utils.PlayerData;
import me.astropvp.astrospawners.utils.PlayerDataManager;
import me.ifamasssxd.cosmicbosses.bosses.kingslime.KingSlime;
import me.ifamasssxd.cosmicbosses.bosses.plaguebloater.PlagueBloater;
import me.ifamasssxd.cosmicbosses.bosses.skeletonhorse.UndeadAssassin;
import me.ifamasssxd.cosmicbosses.bosses.yijki.Yijki;
import me.ifamasssxd.cosmicbosses.dungeonboss.DungeonYijki;
import me.ifamasssxd.cosmicbosses.dungeonboss.planetnul.CorruptCosmonaut;
import me.ifamasssxd.cosmicbosses.struct.Boss;
import me.ifamasssxd.cosmicduel.DuelAPI;
import me.ifamasssxd.cosmicduel.struct.enums.DuelState;
import me.ifamasssxd.cosmicduel.struct.enums.DuelType;
import me.ifamasssxd.cosmichcf.managers.OutpostManager;
import net.astropvp.astroevents.AstroEvents;
import net.astropvp.astroevents.game.MiniGameRegister;
import net.astropvp.astroevents.game.MiniGameState;
import net.astropvp.astroevents.game.MiniGameType;
import net.astropvp.astroevents.game.games.ParkourMiniGame;
import net.astropvp.astroevents.game.games.SumoMiniGame;
import net.astropvp.astroevents.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Scoreboard implements BoardAdapter {
    private final AstroScoreboardPlugin plugin = AstroScoreboardPlugin.getInstance();
    private final Map<String, String> rankOrder = new HashMap<>();

    public List<String> getScoreboard(Player player, Board board) {
        return mainBoard(player);
    }

    private static final DateFormat format = new SimpleDateFormat("MM/dd");

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
        if (DungeonsAPI.get().getPlayerDungeonData(player).getCurrentDungeon() != null && (DungeonsAPI.get().getPlayerDungeonData(player).getCurrentDungeon().getDungeonType() == DungeonType.ABANDONED_SHIP || DungeonsAPI.get().getPlayerDungeonData(player).getCurrentDungeon().getDungeonType() == DungeonType.DESTROYED_OUTPOST)) {
            for (Entity entity : player.getNearbyEntities(32, 32, 32)) {
                if (entity.hasMetadata("boss")) {
                    if (Boss.bosses.get(entity.getUniqueId()) instanceof DungeonYijki) {
                        return DungeonsAPI.get().getPlayerDungeonData(player).getCurrentDungeon().getDungeonType() == DungeonType.ABANDONED_SHIP ? CC.GoldB + "Mother Of Yijki" : CC.LightPurpleB + "Mother Of Yijki";
                    }
                }
            }
        }
        if (DungeonsAPI.get().getPlayerDungeonData(player).getCurrentDungeon() != null && (DungeonsAPI.get().getPlayerDungeonData(player).getCurrentDungeon().getDungeonType() == DungeonType.PLANET_NUL)) {
            for (Entity entity : player.getNearbyEntities(32, 32, 32)) {
                if (entity.hasMetadata("boss")) {
                    if (Boss.bosses.get(entity.getUniqueId()) instanceof CorruptCosmonaut) {
                        return CC.GoldB + "The Corrupt Cosmonaut";
                    }
                }
            }
        }


        if (!WorldGuardUtils.getRegionsIn(player.getLocation()).contains("spawn_region") && WorldGuardUtils.getRegionsIn(player.getLocation()).contains("spawnwarzone")) {
            for (Entity entity : player.getNearbyEntities(16, 16, 16)) {
                if (entity.hasMetadata("boss")) {
                    if (Boss.bosses.get(entity.getUniqueId()) instanceof KingSlime) {
                        return CC.translate("&e&lKing Slime &7" + getDateSuffix());
                    } else if (Boss.bosses.get(entity.getUniqueId()) instanceof UndeadAssassin) {
                        return CC.translate("&c&lUndead Assassin &7" + getDateSuffix());
                    } else if (Boss.bosses.get(entity.getUniqueId()) instanceof PlagueBloater) {
                        return CC.translate("&2&lPlague Bloater &7" + getDateSuffix());
                    }
                }
            }
        }


        if (WorldGuardUtils.getRegionName(player.getLocation()).contains("infernal")) {
            return CC.translate("&c&lInfernal Stronghold &7" + getDateSuffix());
        }

        if (player.getLocation().getWorld().getName().equals("events_world")) {
            return CC.translate("&6&lAstro&f&lPvP &7" + getDateSuffix());
        }

        if (WorldGuardUtils.getRegionName(player.getLocation()).contains("frozen")) {
            return CC.translate("&b&lFrozen Stronghold &7" + getDateSuffix());

        }
        if (DuelAPI.getDuel(player) != null && (DuelAPI.getDuel(player).getDuelState() == DuelState.STARTING || DuelAPI.getDuel(player).getDuelState() == DuelState.PLAYING)) {
            if (DuelAPI.getDuel(player).getDuelType() == DuelType.ONE_VS_ONE) {
                return CC.translate("&6&l1v1 Duel &7" + getDateSuffix());
            }
        }
        KothEventTask koth = CosmicKOTHPlugin.get_().getKoth();

        if (koth != null && WorldGuardUtils.getRegionsIn(player) != null && (WorldGuardUtils.getRegionsIn(player).contains("desertkoth") || WorldGuardUtils.getRegionsIn(player).contains("desertkoth2"))) {
            return CC.translate("&6&lDesert KOTH &7" + getDateSuffix());
        }

        if (player.getLocation().getWorld().getName().equals("space")) {
            return CC.translate("&b&lSpace Lands &7" + getDateSuffix());

        }

            if (!FactionUtils.isWilderness(fplayer.getFaction())) {
            return CC.translate("&a&l" + fplayer.getFaction().getTag() + " &7" + getDateSuffix());
        }


        return CC.translate("&6&lAstro&f&lPvP &7" + getDateSuffix());
    }


    public List<String> mainBoard(Player player) {

        if (player.getWorld().getName().startsWith("dungeon_")) {
            if (DungeonsAPI.get().getPlayerDungeonData(player).getCurrentDungeon() != null && DungeonsAPI.get().getPlayerDungeonData(player).getCurrentDungeon().getDungeonType() == DungeonType.PLANET_NUL) {
                for (Entity entity : player.getNearbyEntities(16, 16, 16)) {
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
                            int damage = yijki.getDamageTracker().getOrDefault(player.getUniqueId(), 0);
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
                for (Entity entity : player.getNearbyEntities(16, 16, 16)) {
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
                            int damage = yijki.getDamageTracker().getOrDefault(player.getUniqueId(), 0);
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
                for (Entity entity : player.getNearbyEntities(16, 16, 16)) {
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
                            int damage = cosmonaut.getDamageTracker().getOrDefault(player.getUniqueId(), 0);
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


        if (DungeonsAPI.get().getPlayerDungeonData(player).getCurrentDungeon() != null && (DungeonsAPI.get().getPlayerDungeonData(player).getCurrentDungeon().getDungeonType() == DungeonType.PLANET_NUL)) {
            for (Entity entity : player.getNearbyEntities(16, 16, 16)) {
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
                        int damage = cosmonaut.getDamageTracker().getOrDefault(player.getUniqueId(), 0);
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

        if (!WorldGuardUtils.getRegionsIn(player.getLocation()).contains("spawn_region") && WorldGuardUtils.getRegionsIn(player.getLocation()).contains("spawnwarzone")) {
            for (Entity entity : player.getNearbyEntities(16, 16, 16)) {
                if (entity.hasMetadata("boss")) {
                    if (Boss.bosses.get(entity.getUniqueId()) instanceof KingSlime) {
                        KingSlime slime = (KingSlime) Boss.bosses.get(entity.getUniqueId());
                        List<String> board = new LinkedList<>();
                        board.add("");
                        board.add(CC.RedB + "Health");
                        board.add(CC.Red + Math.round(slime.getEntity().getHealth()));
                        board.add("");
                        board.add(CC.GreenB + "Damage Dealt");
                        int damage = slime.getDamageTracker().getOrDefault(player.getUniqueId(), 0);
                        int totalDamage = 0;
                        for (final Integer dmg : (slime.getDamageTracker()).values()) {
                            totalDamage += dmg;
                        }
                        final double percent = (double) damage / totalDamage * 100.0;
                        DecimalFormat format = new DecimalFormat("###.#");
                        String string = ChatColor.GREEN.toString() + damage + ChatColor.GRAY + " [" + format.format(percent)+ "%]";
                        if (string.length() > 16) {
                            string = string.replace("[", "").replace("]", "");
                            if (string.length() > 16) {
                                string = string.substring(0, 16);
                            }
                        }
                        board.add(CC.Green + string);
                        return board;
                    } else if (Boss.bosses.get(entity.getUniqueId()) instanceof UndeadAssassin) {
                        UndeadAssassin assassin = (UndeadAssassin) Boss.bosses.get(entity.getUniqueId());
                        List<String> board = new LinkedList<>();
                        board.add("");
                        board.add(CC.RedB + "Health");
                        board.add(CC.Red + Math.round(assassin.getEntity().getHealth()));
                        board.add("");
                        board.add(CC.GreenB + "Damage Dealt");
                        int damage = assassin.getDamageTracker().getOrDefault(player.getUniqueId(), 0);
                        int totalDamage = 0;
                        for (final Integer dmg : (assassin.getDamageTracker()).values()) {
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
                    } else if (Boss.bosses.get(entity.getUniqueId()) instanceof PlagueBloater) {
                        PlagueBloater plague = (PlagueBloater) Boss.bosses.get(entity.getUniqueId());
                        List<String> board = new LinkedList<>();
                        board.add("");
                        board.add(CC.RedB + "Health");
                        board.add(CC.Red + Math.round(plague.getEntity().getHealth()));
                        board.add("");
                        board.add(CC.GreenB + "Damage Dealt");
                        int damage = plague.getDamageTracker().getOrDefault(player.getUniqueId(), 0);
                        int totalDamage = 0;
                        for (final Integer dmg : (plague.getDamageTracker()).values()) {
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

        if (player.getLocation().getWorld().getName().equals("space")) {
            SpacePlayerData data = SpacePlayerData.findPlayer(player);
            List<String> board = new LinkedList<>();
            board.add("");
            board.add(CC.translate("&3&lAccount"));
            board.add(player.getName());
            board.add("");
            board.add(CC.translate("&b&lStats"));
            board.add(CC.translate("&8» &7Mobs Killed:&b " + data.getKills(MonsterType.SPACE_ALIEN)));
            board.add(CC.translate("&8» &7Time Spent:&b " + TimeUtils.format(data.getTimeInSpace())));
            board.add(CC.translate("&8» &7Money Made: &3$&b" + NumberUtils.formatMoney(data.getMoneyMade())));
            board.add(CC.translate("&8» &7XP Earned:&b " + NumberUtils.formatMoney(data.getXpMade())));
            board.add(CC.translate(""));
            board.add(CC.Gray + "play.astropvp.net");
            return board;
        }

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

        KothEventTask koth = CosmicKOTHPlugin.get_().getKoth();
        if (koth != null && WorldGuardUtils.getRegionsIn(player) != null && (WorldGuardUtils.getRegionsIn(player).contains("desertkoth") || WorldGuardUtils.getRegionsIn(player).contains("desertkoth2"))) {
            List<String> board = new LinkedList<>();
            board.add("");
            if (koth.getPlayerCapping() != null) {
                board.add(CC.translate("&8» &6Capper&7: &e" + koth.getPlayerCapping().getName()));
                board.add(CC.translate("&8» &6Time Left&7: &a") + koth.getSecondsUntilCap() + "s");
                board.add("");
            }
            board.add(CC.translate("&8» &6Nearby Players:&c " + player.getNearbyEntities(48, 60, 48).size()));
            board.add(CC.translate("&8» &6Account:&f " + player.getName()));
            board.add("");
            board.add(CC.translate("&fX&72504&7, &fY&719, &fZ&74638"));
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

        if (player.getLocation().getWorld().getName().equals("events_world")) {
            List<String> board = new LinkedList<>();
            board.add("");

            MiniGameRegister miniGameRegister = AstroEvents.getInstance().getMiniGameRegister();

            if (miniGameRegister.getMiniGame(MiniGameType.SUMO) != null && miniGameRegister.getMiniGame(MiniGameType.PARKOUR) != null) {
                SumoMiniGame sumoMiniGame = (SumoMiniGame) miniGameRegister.getMiniGame(MiniGameType.SUMO);
                ParkourMiniGame parkourMiniGame = (ParkourMiniGame) miniGameRegister.getMiniGame(MiniGameType.PARKOUR);

                if (sumoMiniGame.isParticipant(player)) {
                    board.add(CC.translate(CC.White + "&6&lSumo Event"));
                    board.add("");
                    board.add(CC.translate("&6● &7Status: &6" + Utils.toNiceString(sumoMiniGame.getMiniGameState().name())));
                    board.add(CC.translate("&6● &7Participants: &6" + sumoMiniGame.getParticipants().size() + "&8/&6" + sumoMiniGame.getMaxPlayers()));
                    board.add(CC.translate("&6● &7Spectators: &6" + sumoMiniGame.getSpectators().size()));

                    if (sumoMiniGame.getMiniGameState() == MiniGameState.IN_PROGRESS) {
                        board.add("");
                        if (sumoMiniGame.getDuelPlayers().getLeft() != null && sumoMiniGame.getDuelPlayers().getLeft().getPlayer() != null)
                            board.add(CC.translate("&6● &7First Fighter: &6" + sumoMiniGame.getDuelPlayers().getLeft().getPlayer().getName()));
                        if (sumoMiniGame.getDuelPlayers().getRight() != null && sumoMiniGame.getDuelPlayers().getRight().getPlayer() != null)
                            board.add(CC.translate("&6● &7Second Fighter: &6" + sumoMiniGame.getDuelPlayers().getRight().getPlayer().getName()));
                    }

                    board.add("");
                    board.add(CC.White + "play.astropvp.net");
                    return board;
                } else if (parkourMiniGame.isParticipant(player)) {
                    board.add(CC.translate(CC.White + "&6&lParkour Event"));
                    board.add("");
                    board.add(CC.translate("&6● &7Status: &6" + Utils.toNiceString(parkourMiniGame.getMiniGameState().name())));
                    board.add(CC.translate("&6● &7Participants: &6" + parkourMiniGame.getParticipants().size() + "&8/&6" + parkourMiniGame.getMaxPlayers()));
                    board.add(CC.translate("&6● &7Spectators: &6" + parkourMiniGame.getSpectators().size()));
                    board.add("");
                    board.add(CC.White + "play.astropvp.net");
                    return board;
                }
            }
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

        FPlayer fPlayer = FPlayers.getInstance().getByPlayer(player);
        int kill = fPlayer.getKills();
        int death = fPlayer.getDeaths();
        double kdr;
        if (death == 0) {
            kdr = kill;
        } else {
            kdr = (double) kill / death;
        }
        List<String> board = new LinkedList<>();
        board.add(" ");
        board.add(ChatColor.AQUA + ChatColor.BOLD.toString() + "Account");
        board.add(ChatColor.WHITE + player.getName());
        board.add(" ");
        board.add(ChatColor.GOLD + ChatColor.BOLD.toString() + "Balance");
        board.add(ChatColor.WHITE.toString() + "$" + NumberUtils.formatMoney((long) EconomyUtils.getBalance((OfflinePlayer) player)));
        board.add(" ");
        board.add(ChatColor.LIGHT_PURPLE + ChatColor.BOLD.toString() + "XP");
        board.add(ChatColor.WHITE.toString() + NumberUtils.formatMoney(player.getTotalExperience()));
        board.add("");
        board.add(ChatColor.RED + ChatColor.BOLD.toString() + "KDR");
        board.add(CC.translate("&f"+ String.format("%.1f", kdr) + "&7 [" + kill + ":" + death + "]"));
        board.add("");
        if (!FactionUtils.getFaction(player).isWilderness()) {
            Faction faction = FactionUtils.getFaction(player);
            board.add(ChatColor.YELLOW + ChatColor.BOLD.toString() + "Faction");
            board.add(CC.translate("&a" + faction.getOnlinePlayers().size() + " &7/ 15"));
        } else {
            board.add(ChatColor.YELLOW + ChatColor.BOLD.toString() + "Server");
            board.add(CC.Gray + "play.astropvp.net");

        }

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
        return 20;
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
