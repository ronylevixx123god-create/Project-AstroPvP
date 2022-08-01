package net.astropvp.core.struct;

import lombok.Getter;
import lombok.Setter;
import net.astropvp.storage.utils.KeyToSave;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.UUID;

@Getter
@Setter
public class AstroPlayer implements KeyToSave {
    private UUID uuid;
    private boolean factionsLandTitleEnabled;
    private boolean killTitleEnabled;
    private ChatColor chatColor;

    public AstroPlayer(UUID uuid) {
        this.uuid = uuid;
        this.factionsLandTitleEnabled = true;
        this.killTitleEnabled = true;
        this.chatColor = ChatColor.WHITE;
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(uuid);
    }

    @Override
    public String getKey() {
        return uuid.toString();
    }

    @Override
    public void setKey(String s) {
        this.uuid = UUID.fromString(s);
    }
}
