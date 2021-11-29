package me.astropvp.struct;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class TournamentManager {
    @Getter @Setter private ItemStack helmet;
    @Getter @Setter private ItemStack chestplate;
    @Getter @Setter private ItemStack leggings;
    @Getter @Setter private ItemStack boots;
    @Getter @Setter private ItemStack weapon;

    public TournamentManager() {
        this.helmet = new ItemStack(Material.DIAMOND_HELMET);
        this.chestplate = new ItemStack(Material.DIAMOND_CHESTPLATE);
        this.leggings = new ItemStack(Material.DIAMOND_LEGGINGS);
        this.boots = new ItemStack(Material.DIAMOND_BOOTS);
        this.weapon = new ItemStack(Material.GOLD_SWORD);
    }
}
