package me.astropvp.moneyshop.struct;

import lombok.Getter;
import me.astropvp.cratekeys.enums.CrateType;
import me.astropvp.cratekeys.items.CrateKeyItem;
import me.ifamasssxd.cosmicmysterychest.CosmicMysteryChest;
import me.ifamasssxd.cosmicskycustoms.CosmicSkyCustomItems;
import me.ily2bb.slotbot.CosmicSlotBotAPI;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
@Getter

public enum MoneyShopItems {
    SLOTBOT(500000000.0, CosmicSlotBotAPI.createSlotBotItem()),
    PREMADE(100000000.0, CosmicMysteryChest.get().getChestManager().getMysteryChestMap().get("PremadeMeta").createClaimableItem(false)),
    GODLY(100000000.0, CrateKeyItem.getItem(CrateType.GODLY)),
    APOLLO(500000000000.0, CosmicSkyCustomItems.get().getItemManager().getCustomItem("apollo_rank").createItem(null)),
    NEBULA(1500000000000.0, CosmicSkyCustomItems.get().getItemManager().getCustomItem("nebula_rank").createItem(null));



    private final double price;

    private final ItemStack itemStack;


    MoneyShopItems(double price, ItemStack itemStack) {
        this.price = price;
        this.itemStack = itemStack;
    }

}
