package me.astropvp.struct;

import me.astropvp.enums.PlayerState;
import org.bukkit.entity.Player;

public class TournamentPlayer {
    private Player player;

    private PlayerState playerState;

    public TournamentPlayer(Player player) {
        this.player = player;
        this.playerState = PlayerState.WAITING;
    }

    public Player getPlayer() { return this.player; }

    public PlayerState getPlayerState() { return this.playerState; }

    public void setPlayerState(PlayerState playerState) {
        this.playerState = playerState;
    }


}
