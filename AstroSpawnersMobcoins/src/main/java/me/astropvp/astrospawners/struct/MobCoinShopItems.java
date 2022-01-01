package me.astropvp.astrospawners.struct;

import lombok.Getter;
import me.astropvp.cratekeys.enums.CrateType;
import me.astropvp.cratekeys.items.CrateKeyItem;
import me.ifamasssxd.cosmicmysterychest.CosmicMysteryChest;
import me.ifamasssxd.cosmicmysterychest.struct.MysteryChest;
import me.ifamasssxd.cosmicskycustoms.CosmicSkyCustomItems;
import me.ily2bb.slotbot.CosmicSlotBotAPI;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public enum MobCoinShopItems {
    SLOTBOT(125000, CosmicSlotBotAPI.createSlotBotItem()),
    PREMADE(5000, CosmicMysteryChest.get().getChestManager().getMysteryChestMap().get("PremadeMeta").createClaimableItem(false)),
    WINTERKEY(10000, CrateKeyItem.getItem(CrateType.valueOf("WINTER"))),
    QUADCRYSTAL(1000, CosmicMysteryChest.get().getChestManager().getMysteryChestMap().get("QuadCrystal").createClaimableItem(false)),
    ADMINWEAPON(350, CosmicMysteryChest.get().getChestManager().getMysteryChestMap().get("AdminWeapon").createClaimableItem(false));


    private final int price;
    @Getter
    private final ItemStack itemStack;


    MobCoinShopItems(int price, ItemStack itemStack) {
        this.price = price;
        this.itemStack = itemStack;
    }

    public int getPrice(Player player) {
        if (player.hasPermission("astrocore.apollo")) {
            return (int) (price*0.8);
        }
        return price;
    }
}
