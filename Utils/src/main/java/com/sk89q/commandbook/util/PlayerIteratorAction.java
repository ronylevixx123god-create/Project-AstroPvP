/*
 * Decompiled with CFR 0.150.
 */
package com.sk89q.commandbook.util;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class PlayerIteratorAction {
    protected final CommandSender sender;
    private boolean included;
    private int affected = 0;

    public PlayerIteratorAction(CommandSender sender) {
        this.sender = sender;
    }

    public void iterate(Iterable<Player> targets) {
        for (Player player : targets) {
            this.perform(player);
            if (player.equals(this.sender)) {
                this.onCaller(player);
                this.included = true;
            } else {
                this.onVictim(this.sender, player);
            }
            ++this.affected;
        }
        if (!this.included) {
            this.onInform(this.sender, this.affected);
            this.onInformMany(this.sender, this.affected);
        } else if (this.affected > 1) {
            this.onInformMany(this.sender, this.affected);
        }
        this.onComplete(this.sender, this.affected);
    }

    public CommandSender getSender() {
        return this.sender;
    }

    public abstract void perform(Player var1);

    public abstract void onCaller(Player var1);

    public abstract void onVictim(CommandSender var1, Player var2);

    public void onInform(CommandSender sender, int affected) {
    }

    public void onInformMany(CommandSender sender, int affected) {
    }

    public void onComplete(CommandSender sender, int affected) {
    }
}

