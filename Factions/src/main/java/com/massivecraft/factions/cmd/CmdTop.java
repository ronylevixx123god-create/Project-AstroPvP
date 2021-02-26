package com.massivecraft.factions.cmd;

import com.massivecraft.factions.P;
import org.bukkit.Bukkit;

public class CmdTop extends FCommand {

    public CmdTop() {
        this.aliases.add("top");
        this.senderMustBePlayer = false;
        this.senderMustBeMember = false;
        this.senderMustBeModerator = false;
        this.senderMustBeAdmin = false;
        this.setHelpShort("faction top commands");
        this.helpLong.add(this.p.txt.parseTags("<i>The faction top commands."));
    }

    @Override
    public void perform() {
        Bukkit.getPlayer(sender.getName()).chat("/ftopp");
    }
}

