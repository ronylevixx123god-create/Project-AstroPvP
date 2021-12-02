package com.massivecraft.factions.upgrade;

import java.util.HashMap;
import java.util.Map;

public class FactionUpgradeInfo {
    private String factionID;

    private Map<FactionUpgrade, Integer> upgradeTiers = new HashMap<>();

    public FactionUpgradeInfo(String factionID) {
        this.factionID = factionID;
    }

    public int getUpgradeTier(FactionUpgrade upgrade) {
        Integer stored = this.upgradeTiers.get(upgrade);
        if (stored != null)
            return stored.intValue();
        return 0;
    }

    public void setUpgradeTier(FactionUpgrade upgrade, int tier) {
        this.upgradeTiers.put(upgrade, Integer.valueOf(tier));
    }

    public String getFactionID() {
        return this.factionID;
    }

    public Map<FactionUpgrade, Integer> getUpgradeTiers() {
        return this.upgradeTiers;
    }
}
