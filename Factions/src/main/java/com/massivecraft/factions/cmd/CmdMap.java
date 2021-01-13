package com.massivecraft.factions.cmd;

import com.massivecraft.factions.Board;
import com.massivecraft.factions.Conf;
import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.struct.Permission;

public class CmdMap extends FCommand {
    public CmdMap() {
        this.aliases.add("map");
        this.optionalArgs.put("on/off", "once");
        this.permission = Permission.MAP.node;
        this.disableOnLock = false;
        this.senderMustBePlayer = true;
        this.senderMustBeMember = false;
        this.senderMustBeModerator = false;
        this.senderMustBeAdmin = false;
    }

    public void perform() {
        if (argIsSet(0)) {
            if (argAsBool(0, !this.fme.isMapAutoUpdating()).booleanValue()) {
                if (!payForCommand(Conf.econCostMap, "to show the map", "for showing the map"))
                    return;
                this.fme.setMapAutoUpdating(true);
                msg("<i>Map auto update <green>ENABLED.", new Object[0]);
                showMap();
            } else {
                this.fme.setMapAutoUpdating(false);
                msg("<i>Map auto update <red>DISABLED.", new Object[0]);
            }
        } else {
            if (!payForCommand(Conf.econCostMap, "to show the map", "for showing the map"))
                return;
            showMap();
        }
    }

    public void showMap() {
        sendMessage(Board.getMap(this.myFaction, new FLocation(this.fme), this.fme.getPlayer()));
    }
}
