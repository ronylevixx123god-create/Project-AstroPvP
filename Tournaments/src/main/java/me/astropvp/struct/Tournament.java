package me.astropvp.struct;

import com.google.common.collect.Lists;
import me.astropvp.CosmicTournamentsPlugin;
import me.astropvp.inventories.LobbyInventory;
import me.astropvp.struct.enums.TournamentState;
import me.astropvp.tasks.TournamentLobbyTask;
import me.astropvp.tasks.TournamentWaitingTask;
import me.astropvp.utils.InventoryStorage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;

public class Tournament {
    private List<ActiveTournamentPlayer> playerList;
    private List<TournamentMatch> tournamentMatches;
    private HashMap<Player, String[]> storedInventories;
    private TournamentState state;
    private int lobbyTaskId;
    private int waitingTaskId;
    private int lobbyWaitingTime;
    private int startingTime;


    public Tournament() {
        playerList = Lists.newArrayList();
        storedInventories = new HashMap<>();
        this.state = TournamentState.OPEN;
        this.lobbyWaitingTime = 60;
        this.lobbyTaskId = Bukkit.getScheduler().scheduleAsyncRepeatingTask(CosmicTournamentsPlugin.getInstance(), new TournamentLobbyTask(this), 20L, 20L);
    }

    public void registerPlayer(Player player) {
        ActiveTournamentPlayer activeTournamentPlayer = new ActiveTournamentPlayer(player);
        playerList.add(activeTournamentPlayer);
        this.storedInventories.put(player, InventoryStorage.storeInventory(player));
        LobbyInventory.initiate(player);
    }

    public void unregisterPlayer(Player player) {
        playerList.removeIf(atp -> atp.getPlayer() == player);
        InventoryStorage.restoreInventory(player, this.storedInventories.get(player));
    }

    public void close() {
        this.state = TournamentState.CLOSED;
        this.startingTime = 10;
        Bukkit.getScheduler().cancelTask(this.lobbyTaskId);
        this.waitingTaskId = Bukkit.getScheduler().scheduleAsyncRepeatingTask(CosmicTournamentsPlugin.getInstance(), new TournamentWaitingTask(this), 20L, 20L);
    }

    public void start() { Bukkit.getScheduler().cancelTask(this.waitingTaskId); }

    public TournamentMatch getActiveTournamentMatch(Player player) {
        for(TournamentMatch match : tournamentMatches) {
            if(match.getPlayer1() == player || match.getPlayer2() == player) {
                return match;
            }
        }
        return null;
    }

    public int getLobbyWaitingTime() { return this.lobbyWaitingTime; }
    public void setLobbyWaitingTime(int time) { this.lobbyWaitingTime = time; }
    public int getStartingTime() { return this.startingTime; }
    public void setStartingTime(int time) { this.startingTime = time; }
    public TournamentState getState() { return this.state; }
    public List<ActiveTournamentPlayer> getPlayerList() { return this.playerList; }

}
