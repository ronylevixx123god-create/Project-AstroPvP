package me.astropvp.struct;

import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;
import me.astropvp.CosmicTournamentsPlugin;
import me.astropvp.inventories.LobbyInventory;
import me.astropvp.enums.TournamentState;
import me.astropvp.tasks.TournamentLobbyTask;
import me.astropvp.tasks.TournamentWaitingTask;
import me.astropvp.utils.InventoryStorage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Tournament {
    //Tournament Startup

    @Getter private List<TournamentPlayer> playerList;
    @Getter private List<TournamentPlayer> waitingList;
    @Getter private HashMap<Player, String[]> storedInventories;
    @Getter private List<TournamentMatch> currentMatches;
    @Getter private TournamentState state;
    @Getter private int lobbyTaskId;
    @Getter private int waitingTaskId;
    @Getter @Setter private int lobbyWaitingTime;
    @Getter @Setter private int startingTime;
    @Getter private LobbyInventory lobbyInventory;

    public Tournament() {
        this.playerList = Lists.newArrayList();
        this.storedInventories = new HashMap<>();
        this.waitingList = new ArrayList<>();
        this.currentMatches = new ArrayList<>();
        this.lobbyInventory = new LobbyInventory();
        this.state = TournamentState.OPEN;
        this.lobbyWaitingTime = 60;
        this.lobbyTaskId = Bukkit.getScheduler().scheduleAsyncRepeatingTask(CosmicTournamentsPlugin.getInstance(), new TournamentLobbyTask(this), 20L, 20L);
    }

    public TournamentPlayer getTournamentPlayer(Player p) {
        for (TournamentPlayer tournamentPlayer : getPlayerList()) {
            if (p == tournamentPlayer.getPlayer()) {
                return tournamentPlayer;
            }
        }
        return null;
    }

    //Tournament Joining/leaving logic

    public void registerPlayer(Player player) {
        TournamentPlayer activeTournamentPlayer = new TournamentPlayer(player);
        playerList.add(activeTournamentPlayer);
        this.storedInventories.put(player, InventoryStorage.storeInventory(player));
        LobbyInventory.initiate(player);
    }

    public void unregisterPlayer(Player player) {
        playerList.removeIf(atp -> atp.getPlayer() == player);
        InventoryStorage.restoreInventory(player, this.storedInventories.get(player));
    }

    //Tournament Start Logic

    public void close() {
        this.state = TournamentState.CLOSED;
        this.startingTime = 10;
        Bukkit.getScheduler().cancelTask(this.lobbyTaskId);
        this.waitingTaskId = Bukkit.getScheduler().scheduleAsyncRepeatingTask(CosmicTournamentsPlugin.getInstance(), new TournamentWaitingTask(this), 20L, 20L);
    }

    public void start() {
        Bukkit.getScheduler().cancelTask(this.waitingTaskId);
        this.waitingList.addAll(this.playerList);
        for(int i = 0; i < 10; i++) {
            TournamentPlayer player1 = this.waitingList.get(0);
            this.waitingList.remove(player1);
            TournamentPlayer player2 = this.waitingList.get(0);
            this.waitingList.remove(player2);
            TournamentMatch match = new TournamentMatch(player1, player2, this);
            this.currentMatches.add(match);
        }
    }

    //Active Tournament Logic

    public void endMatch(TournamentMatch match) {
        match.getTask().cancel();
        this.currentMatches.remove(match);
        this.nextMatch();
    }

    public void nextMatch() {
        if(this.waitingList.size() == 0) {
            endTournament();
        }
        TournamentPlayer player1 = this.waitingList.get(0);
        this.waitingList.remove(player1);
        TournamentPlayer player2 = this.waitingList.get(0);
        this.waitingList.remove(player2);
        TournamentMatch match = new TournamentMatch(player1, player2, this);
        this.currentMatches.add(match);
    }

    public void endTournament() {

    }

    public TournamentMatch getMatch(TournamentPlayer player) {
        for(TournamentMatch match : this.currentMatches) {
            if(match.getPlayer1() == player || match.getPlayer2() == player)
                return match;
            continue;
        }
        return null;
    }

}
