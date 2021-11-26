package me.astropvp.struct;

import lombok.Getter;
import lombok.Setter;
import me.astropvp.enums.PlayerState;
import org.bukkit.entity.Player;

public class TournamentPlayer {
    @Getter private Player player;
    @Getter @Setter private PlayerState playerState;

    public TournamentPlayer(Player player) {
        this.player = player;
        this.playerState = PlayerState.WAITING;
    }
}
