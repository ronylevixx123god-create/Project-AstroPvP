package com.compilex.CosmicArmorSets.enums;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

@Getter @Setter
public class CachedGoblinSet {
    private Player player;

    private long lastHit;

    private double damageDealt;

    public CachedGoblinSet(Player player) {
        this.player = player;
        this.lastHit = -1;
        this.damageDealt = 0;
    }
}
