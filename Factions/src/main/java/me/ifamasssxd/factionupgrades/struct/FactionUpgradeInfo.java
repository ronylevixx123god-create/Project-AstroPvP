/*
 * Decompiled with CFR 0.150.
 */
package me.ifamasssxd.factionupgrades.struct;

import java.util.HashMap;
import java.util.Map;

public class FactionUpgradeInfo {
    private String factionID;
    private Map<FactionUpgrade, Integer> upgradeTiers = new HashMap<FactionUpgrade, Integer>();

    public FactionUpgradeInfo(String factionID) {
        this.factionID = factionID;
    }

    public int getUpgradeTier(FactionUpgrade upgrade) {
        Integer stored = this.upgradeTiers.get((Object)upgrade);
        if (stored != null) {
            return stored;
        }
        return 0;
    }

    public void setUpgradeTier(FactionUpgrade upgrade, int tier) {
        this.upgradeTiers.put(upgrade, tier);
    }

    public String getFactionID() {
        return this.factionID;
    }

    public Map<FactionUpgrade, Integer> getUpgradeTiers() {
        return this.upgradeTiers;
    }
}

