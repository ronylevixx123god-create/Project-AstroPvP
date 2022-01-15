package net.astropvp.armorsets.struct;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.ily2bb.slotbot.CosmicSlotBotAPI;
import org.bukkit.inventory.ItemStack;

@Getter
@RequiredArgsConstructor
public enum Reward {
    SLOTBOT(CosmicSlotBotAPI.createSlotBotItem(), 10000000);


    private ItemStack itemStack;
    private double price;

    Reward(ItemStack itemStack, double price) {
        this.itemStack = itemStack;
        this.price = price;
    }
}