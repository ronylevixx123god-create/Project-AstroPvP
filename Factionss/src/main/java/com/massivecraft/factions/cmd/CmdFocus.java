package com.massivecraft.factions.cmd;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.util.TL;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class CmdFocus extends FCommand {
    public CmdFocus() {
        this.aliases.add("focus");
        this.aliases.add("target");
        this.aliases.add("t");
        this.aliases.add("fo");
        this.requiredArgs.add("player name");
    }

    @Override
    public void perform(CommandContext context) {
        if (context.sender instanceof Player) {
            FPlayer pSender = (FPlayer) FPlayers.getInstance().getByPlayer(context.player);
            if (context.faction != null) {
                FPlayer playerToFocus = context.argAsBestFPlayerMatch(0);
                if (playerToFocus == null)
                    return;
                context.faction.setFocusedPlayer(pSender);
            }
        }
    }

    @Override
    public TL getUsageTranslation() {
        return null;
    }
}
