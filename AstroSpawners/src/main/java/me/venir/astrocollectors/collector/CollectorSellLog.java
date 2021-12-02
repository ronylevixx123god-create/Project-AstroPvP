package me.venir.astrocollectors.collector;

import lombok.Getter;

@Getter
public class CollectorSellLog {

    private final long sellDate;
    private final String sellerName;
    private final SellLogType sellLogType;
    private final int amount;
    private final double worth;

    public CollectorSellLog(String sellerName, SellLogType sellLogType, int amount) {
        this.sellerName = sellerName;
        this.sellLogType = sellLogType;
        this.amount = amount;
        this.sellDate = System.currentTimeMillis();
        this.worth = 0;
    }

    public CollectorSellLog(String sellerName, SellLogType sellLogType, int amount, double worth) {
        this.sellerName = sellerName;
        this.sellLogType = sellLogType;
        this.amount = amount;
        this.sellDate = System.currentTimeMillis();
        this.worth = worth;
    }

    public enum SellLogType {
        TNT,
        MOB_DROPS
    }
}