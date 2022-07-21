package me.astropvp.astroscoreboard;


import com.compilex.CosmicKOTH.CosmicKOTHPlugin;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.FactionUtils;
import com.cosmicpvp.cosmicutils.utils.WorldGuardUtils;
import com.massivecraft.factions.Faction;
import me.astropvp.astroscoreboard.board.Scoreboard;
import me.astropvp.astroscoreboard.listeners.ListenerSB;
import me.astropvp.astroscoreboard.manager.BoardManager;
import me.ifamasssxd.cosmicduel.DuelAPI;
import me.ifamasssxd.cosmicduel.struct.enums.DuelState;
import me.ifamasssxd.cosmicduel.struct.newduel.NewDuel;
import me.ifamasssxd.cosmichcf.managers.OutpostManager;
import me.ifamasssxd.cosmichcf.struct.Outpost;
import me.neznamy.tab.api.TabAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;


public class AstroScoreboardPlugin extends JavaPlugin {

    private static AstroScoreboardPlugin astroScoreboardPlugin;
    public static BoardManager boardManager;

    public static void registerListener(Listener listener) {
        Bukkit.getServer().getPluginManager().registerEvents(listener, astroScoreboardPlugin);
    }

    public BoardManager getBoardManager() {
        return boardManager;
    }


    public void onEnable() {
        astroScoreboardPlugin = this;
        registerDefaultAndFactionScoreboard();
        registerStrongHoldScoreboard();
        registerDuelScoreboard();
        registerListener(new ListenerSB());
    }


    @Override
    public void onDisable() {
        astroScoreboardPlugin = null;
    }

    public void registerStrongHoldScoreboard() {
        OutpostManager outpostManager = CosmicKOTHPlugin.get_().getOutpostManager();
        Outpost infernalOutpost = outpostManager.getOutpostFromName("courtyard");
        Outpost frozenOutpost = outpostManager.getOutpostFromName("great_hall");

        TabAPI.getInstance().getPlaceholderManager().registerPlayerPlaceholder("%in-infernal-region%", getMS(5), player -> WorldGuardUtils.getRegionName(((Player) player.getPlayer()).getLocation()).contains("infernal"));
        TabAPI.getInstance().getPlaceholderManager().registerPlayerPlaceholder("%in-frozen-region%", getMS(5), player -> WorldGuardUtils.getRegionName(((Player) player.getPlayer()).getLocation()).contains("frozen"));
        TabAPI.getInstance().getPlaceholderManager().registerServerPlaceholder("%infernal-controlling-faction%", getMS(5), () -> strongHoldGetFaction(infernalOutpost.getDefendingFaction()));
        TabAPI.getInstance().getPlaceholderManager().registerServerPlaceholder("%frozen-controlling-faction%", getMS(5), () -> strongHoldGetFaction(frozenOutpost.getDefendingFaction()));
        TabAPI.getInstance().getPlaceholderManager().registerServerPlaceholder("%infernal-attacking-faction%", getMS(5), () -> strongHoldGetFaction(infernalOutpost.getAttackingFaction()));
        TabAPI.getInstance().getPlaceholderManager().registerServerPlaceholder("%frozen-attacking-faction%", getMS(5), () -> strongHoldGetFaction(frozenOutpost.getAttackingFaction()));
        TabAPI.getInstance().getPlaceholderManager().registerServerPlaceholder("%infernal-status%", getMS(5), () -> infernalOutpost.getOutpostState().getName());
        TabAPI.getInstance().getPlaceholderManager().registerServerPlaceholder("%frozen-status%", getMS(5), () -> frozenOutpost.getOutpostState().getName());
        TabAPI.getInstance().getPlaceholderManager().registerServerPlaceholder("%frozen-cap-percent%", getMS(1), () -> round(frozenOutpost.getCapPercentage(), 2));
        TabAPI.getInstance().getPlaceholderManager().registerServerPlaceholder("%infernal-cap-percent%", getMS(1), () -> round(infernalOutpost.getCapPercentage(), 2));
    }

    public void registerDefaultAndFactionScoreboard() {
        TabAPI.getInstance().getPlaceholderManager().registerPlayerPlaceholder("%has-faction%", getMS(10), player -> !FactionUtils.getFPlayer((Player) player.getPlayer()).getFaction().isWilderness());
        TabAPI.getInstance().getPlaceholderManager().registerPlayerPlaceholder("%kills%", getMS(10), player -> FactionUtils.getFPlayer((Player) player.getPlayer()).getKills());
        TabAPI.getInstance().getPlaceholderManager().registerPlayerPlaceholder("%deaths%", getMS(10), player -> FactionUtils.getFPlayer((Player) player.getPlayer()).getDeaths());
        TabAPI.getInstance().getPlaceholderManager().registerPlayerPlaceholder("%kdr%", getMS(10), player -> getPlayerKDR((Player) player.getPlayer()));
        TabAPI.getInstance().getPlaceholderManager().registerServerPlaceholder("%date%", getMS(86400), Scoreboard::getDateSuffix);
    }

    public void registerDuelScoreboard() {
        TabAPI.getInstance().getPlaceholderManager().registerPlayerPlaceholder("%in-duel%", getMS(5), player -> inDuel((Player) player.getPlayer()));
        TabAPI.getInstance().getPlaceholderManager().registerPlayerPlaceholder("%duel-opponent%", getMS(10), player -> DuelAPI.getDuel((Player) player.getPlayer()).getOppositePlayer((Player) player.getPlayer()).getName());
        TabAPI.getInstance().getPlaceholderManager().registerPlayerPlaceholder("%duel-runtime%", getMS(1), player -> getDuelRunTime((Player) player.getPlayer()));
        TabAPI.getInstance().getPlaceholderManager().registerPlayerPlaceholder("%duel-arena%", getMS(10), player -> CC.strip(DuelAPI.getDuel((Player) player.getPlayer()).getArena().getName()));
        TabAPI.getInstance().getPlaceholderManager().registerPlayerPlaceholder("%duel-damage%", getMS(1), player -> getDoubleDamageString(DuelAPI.getDuel((Player) player.getPlayer())));


    }

    public double getPlayerKDR(Player player) {
        double kdr;
        if (FactionUtils.getFPlayer((Player) player.getPlayer()).getDeaths() == 0) {
            kdr = FactionUtils.getFPlayer((Player) player.getPlayer()).getKills();
        } else {
            kdr = (double) FactionUtils.getFPlayer((Player) player.getPlayer()).getKills() / FactionUtils.getFPlayer((Player) player.getPlayer()).getDeaths();
        }
        return round(kdr,2);
    }

    public String getDoubleDamageString(NewDuel duel) {
        if (duel.getGameTimer() >= 300) {
            return CC.translate("&a&lEnabled");
        } else {
            return CC.translate("&c&lDisabled");
        }
    }

    public String getDuelRunTime(Player player) {
        int seconds = DuelAPI.getDuel(player).getGameTimer();
        int minutes = seconds / 60;
        seconds = seconds - (minutes * 60);
        if (minutes > 0) {
            return +minutes + "m " + seconds + "s";
        } else {
            return seconds + "s";

        }
    }

    public boolean inDuel(Player player) {
        return DuelAPI.getDuel(player) != null && (DuelAPI.getDuel(player).getDuelState() == DuelState.STARTING || DuelAPI.getDuel(player).getDuelState() == DuelState.PLAYING);
    }

    public String strongHoldGetFaction(Faction faction) {
        if (faction == null) {
            return "N/A";
        } else {
            return faction.getTag();
        }
    }


    public int getMS(int seconds) {
        return seconds * 1000;
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }


    public static AstroScoreboardPlugin getInstance() {
        return astroScoreboardPlugin;
    }

}
