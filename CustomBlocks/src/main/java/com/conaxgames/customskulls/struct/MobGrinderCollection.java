package com.conaxgames.customskulls.struct;

import lombok.Getter;
import lombok.Setter;

public class MobGrinderCollection {
    @Getter @Setter
    public int lootboxes;
    @Getter @Setter
    public double exp;


    public MobGrinderCollection() {
        this.exp =0;
        this.lootboxes = 0;
        //TODO: here you would load the amount from the config instead of putting 0
    }



    public void addLootboxes(int amount) {
        this.lootboxes+=amount;
    }

    public void addExp(double amount) {
       this.exp+=amount;
    }
}
