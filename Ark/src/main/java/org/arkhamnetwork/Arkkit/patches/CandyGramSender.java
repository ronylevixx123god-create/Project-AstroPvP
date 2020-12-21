/*
 * Decompiled with CFR 0.150.
 */
package org.arkhamnetwork.Arkkit.patches;

enum CandyGramSender {
    YOU("", 0),
    ANONYMOUS("Anonymous", 10000),
    SECRET_ADMIRERER("Secret Admirer", 100000);

    String name;
    private int cost;

    private CandyGramSender(String name, int cost) {
        this.name = name;
        this.cost = cost;
    }

    public String getName() {
        return this.name;
    }

    public int getCost() {
        return this.cost;
    }
}

