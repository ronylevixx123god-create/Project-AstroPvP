package net.astropvp.core.utils;

import java.text.DecimalFormat;

public class SpawnerShopUtil {
    private int amount;
    private String mobName;
    private double priceForOne;
    private double totalPrice;

    public SpawnerShopUtil(int amount, String mobName, double priceForOne) {
        this.amount = amount;
        this.mobName = mobName;
        this.priceForOne = priceForOne;
        this.totalPrice = amount * priceForOne;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getMobName() {
        return mobName;
    }

    public void setMobName(String mobName) {
        this.mobName = mobName;
    }

    public double getPriceForOne() {
        return priceForOne;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setPriceForOne(double priceForOne) {
        this.priceForOne = priceForOne;
    }
}
