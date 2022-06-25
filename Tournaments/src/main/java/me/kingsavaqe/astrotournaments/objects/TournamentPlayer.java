package me.kingsavaqe.astrotournaments.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import me.astropvp.astropvpskins.enums.SkinType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
public class TournamentPlayer {
    private final UUID uuid;
    private final SkinType helmetSkin;
    private final SkinType chestplateSkin;
    private final SkinType leggingsSkin;
    private final SkinType bootsSkin;
    private final SkinType swordSkin;

    public Player getPlayer() {
        return Bukkit.getPlayer(uuid);
    }


}

