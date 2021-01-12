package me.ifamasssxd.factionstop.struct;

import com.google.common.collect.Lists;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.Factions;
import com.massivecraft.factions.P;
import com.massivecraft.factions.integration.Econ;
import com.massivecraft.factions.struct.Role;
import me.ifamasssxd.factionupgrades.FactionUpgrades;
import me.ifamasssxd.factionupgrades.struct.FactionUpgradeInfo;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.libs.BlockValueLocation;
import org.arkhamnetwork.Arkkit.libs.SpawnerLocation;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugins;
import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class StoredFaction
implements Comparable<StoredFaction> {
    String factionID;
    List<SpawnerLocation> factionSpawners;
    private final List<BlockValueLocation> factionContainers;
    double totalSpawnerWorth = 0.0;
    double totalBalance = 0.0;
    double containerWorth;
    double bankBalance;
    double highestPlayerBalance = 0.0;
    String highestPlayer = "None";
    String owner;
    int kothWins = 0;
    private final DecimalFormat format = new DecimalFormat("#,##0");
    private boolean useSpawnerFee = true;
    private double upgradeTotal;
    private static final HashMap<EntityType, Integer> cachedPrices = new HashMap();
    long lastBalanceUpdate = -1L;

    public StoredFaction(String factionId, List<SpawnerLocation> spawners, List<BlockValueLocation> factionContainers) {
        this.factionID = factionId;
        this.factionSpawners = spawners;
        this.factionContainers = factionContainers;
        this.useSpawnerFee = ArkPlugins.SPAWNER_FEE.getArkPlugin().isRunning();
    }

    public void calculateSpawnerWorth() {
        if (!this.useSpawnerFee) {
            System.out.println("Not loading worth.");
            return;
        }
        this.totalSpawnerWorth = 0.0;
        long day = TimeUnit.DAYS.toMillis(1L);
        for (SpawnerLocation spawner : this.factionSpawners) {
            double spawnerCost = this.getSpawnerFeeBase(spawner.getSpawnerType());
            if (P.getP().isGradualSpawnerValues()) {
                long aliveTime = spawner.getMillisecondsSincePlace();
                long days = aliveTime / day;
                double percent = 20L + days * 20L;
                if (percent > 100.0) {
                    percent = 100.0;
                }
                if (percent < 20.0) {
                    percent = 20.0;
                }
                spawnerCost *= 0.01 * percent;
            }
            this.totalSpawnerWorth += spawnerCost * (double)spawner.getSpawnerCount();
        }
    }

    public void calculateContainerWorth() {
        this.containerWorth = 0.0;
        for (BlockValueLocation loc : this.factionContainers) {
            this.containerWorth += loc.getBlockValue();
        }
    }

    private int getSpawnerFeeBase(EntityType type) {
        int price;
        Integer cachedPrice = cachedPrices.get(type);
        if (cachedPrice != null) {
            return cachedPrice;
        }
        switch (type) {
            case GHAST: {
                price = 5000000;
                break;
            }
            case MAGMA_CUBE: 
            case MUSHROOM_COW: {
                price = 4000000;
                break;
            }
            default: {
                price = Arkkit.get().getConfig().getInt("settings.spawner_fee." + type.toString().toLowerCase()) * 10;
            }
        }
        cachedPrices.put(type, price);
        return price;
    }

    public void calculateTotalBalance() {
        this.calculateContainerWorth();
        this.totalBalance = 0.0;
        double currentHighest = 0.0;
        Faction faction = this.getFaction();
        if (faction == null) {
            return;
        }
        FPlayer highest = null;
        this.lastBalanceUpdate = System.currentTimeMillis();
        for (FPlayer player : this.getFaction().getFPlayers()) {
            if (player == null) continue;
            if (player.getRole() == Role.ADMIN) {
                this.owner = player.getNameAsync();
            }
            try {
                double bal = P.getP().getEcon().getBalance(player.getNameAsync());
                this.totalBalance += bal;
                if (!(bal > currentHighest)) continue;
                currentHighest = bal;
                highest = player;
            }
            catch (Exception e) {
                System.out.println("Null offline player for " + player.getNameAsync() + " - " + player.getId());
            }
        }
        if (highest != null) {
            this.highestPlayer = highest.getNameAsync();
            this.highestPlayerBalance = currentHighest;
        }
        if (P.getP().upgradesEnabled) {
            FactionUpgradeInfo info = FactionUpgrades.get().getUpgradeManager().getFactionUpgradeInfo(faction);
            if (info == null || info.getUpgradeTiers() == null) {
                return;
            }
            info.getUpgradeTiers().forEach((upgrade, level) -> {
                if (level > 0) {
                    for (int i = level.intValue(); i > 0; --i) {
                        if (upgrade == null || upgrade.getUpgrade() == null) continue;
                        int tokens = upgrade.getUpgrade().getTokenCost(i);
                        double money = upgrade.getUpgrade().getMoneyCost(i);
                        this.upgradeTotal += money + (double)(tokens * 1000000);
                    }
                }
            });
        }
        if (P.getP().isIncludeBank()) {
            this.bankBalance = Econ.getFactionBalance(faction);
        }
    }

    public double getTotalWorth() {
        return this.totalSpawnerWorth + this.upgradeTotal + this.bankBalance + this.containerWorth;
    }

    public Faction getFaction() {
        return Factions.i.get(this.factionID);
    }

    public String getHoverData(Faction otherFaction) {
        Faction faction = this.getFaction();
        ArrayList<String> retr = Lists.newArrayList();
        ChatColor key = ChatColor.AQUA;
        ChatColor value = ChatColor.LIGHT_PURPLE;
        retr.add(key + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + (otherFaction != null && otherFaction.isNormal() ? faction.getTag(otherFaction) : faction.getTag()));
        retr.add(value + "Leader: " + key + this.owner);
        retr.add("");
        retr.add(value + "Faction Wealth: " + key + ChatColor.BOLD.toString() + "$" + key + this.format.format(this.getTotalWorth()));
        retr.add(value + "Member Wealth: " + key + ChatColor.BOLD.toString() + "$" + key + this.format.format(this.totalBalance));
        if (this.useSpawnerFee) {
            retr.add(value + "Spawner Value: " + key + ChatColor.BOLD.toString() + "$" + key + this.format.format(this.totalSpawnerWorth));
            retr.add("" + value + "Mob Spawners: " + key + this.getMobSpawnerCount());
        }
        retr.add(value + "Block Value: " + key + ChatColor.BOLD.toString() + "$" + key + this.format.format(this.containerWorth));
        if (P.getP().isIncludeBank()) {
            retr.add(value + "Faction Bank: " + key + ChatColor.BOLD + "$" + key + this.format.format(this.bankBalance));
        }
        retr.add(value + "Faction Upgrades: " + key + ChatColor.BOLD + "$" + key + this.format.format(this.upgradeTotal));
        retr.add(value + "KOTH Wins: " + key + this.kothWins);
        retr.add(value + "Richest Member:");
        retr.add("  " + key + this.highestPlayer + ":  " + key + ChatColor.BOLD.toString() + "$" + key + this.format.format((long)this.highestPlayerBalance));
        StringBuilder builder = new StringBuilder();
        for (String val : retr) {
            builder.append(val);
            builder.append("\n");
        }
        return builder.toString();
    }

    @Override
    public int compareTo(StoredFaction other) {
        return this.compareTo(other, false);
    }

    public int compareTo(StoredFaction other, boolean forceWealth) {
        double otherWorth;
        double totalWorth = this.getTotalWorth();
        double d = otherWorth = other.getTotalWorth();
        if (otherWorth == totalWorth) {
            return 0;
        }
        if (otherWorth < totalWorth) {
            return 1;
        }
        return -1;
    }

    public int getMobSpawnerCount() {
        int total = 0;
        for (SpawnerLocation spawner : this.factionSpawners) {
            total += spawner.getSpawnerCount();
        }
        return total;
    }

    public String getFactionID() {
        return this.factionID;
    }

    public List<SpawnerLocation> getFactionSpawners() {
        return this.factionSpawners;
    }

    public List<BlockValueLocation> getFactionContainers() {
        return this.factionContainers;
    }

    public double getTotalSpawnerWorth() {
        return this.totalSpawnerWorth;
    }

    public double getTotalBalance() {
        return this.totalBalance;
    }

    public double getContainerWorth() {
        return this.containerWorth;
    }

    public double getBankBalance() {
        return this.bankBalance;
    }

    public double getHighestPlayerBalance() {
        return this.highestPlayerBalance;
    }

    public String getHighestPlayer() {
        return this.highestPlayer;
    }

    public String getOwner() {
        return this.owner;
    }

    public int getKothWins() {
        return this.kothWins;
    }

    public void setKothWins(int kothWins) {
        this.kothWins = kothWins;
    }
}

