package com.massivecraft.factions.cmd;

import com.massivecraft.factions.Factions;
import net.astropvp.SandBots;
import net.astropvp.inventories.FactionManageGui;

public class CmdSandBot extends FCommand{
    public CmdSandBot() {
        this.aliases.add("sandbots");
        this.aliases.add("sandbot");
        this.disableOnLock = false;
        this.senderMustBePlayer = true;
        this.senderMustBeMember = true;
        this.senderMustBeModerator = true;
        this.senderMustBeAdmin = false;
    }

    @Override
    public void perform() {
        new FactionManageGui(SandBots.get()).open(this.me);
    }
}
