package me.astropvp.struct;

import me.astropvp.struct.enums.PlayerState;
import org.bukkit.entity.Player;

public class ActiveTournamentPlayer {
    private Player player;

    private PlayerState playerState;

    public ActiveTournamentPlayer(Player player) {
        this.player = player;
        this.playerState = PlayerState.WAITING;
    }

    public Player getPlayer() { return this.player; }

    public PlayerState getPlayerState() { return this.playerState; }


}
