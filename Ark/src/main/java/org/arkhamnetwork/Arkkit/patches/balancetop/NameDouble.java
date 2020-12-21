/*
 * Decompiled with CFR 0.150.
 */
package org.arkhamnetwork.Arkkit.patches.balancetop;

public class NameDouble {
    String name;
    Double amount;

    public String getName() {
        return this.name;
    }

    public Double getAmount() {
        return this.amount;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public NameDouble(String name, Double amount) {
        this.name = name;
        this.amount = amount;
    }
}

