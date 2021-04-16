package net.astropvp.core.items;

import com.compilex.CompilexPermissions.PermissionsAPI;
import com.cosmicpvp.cosmicutils.exceptions.CosmicException;
import com.cosmicpvp.cosmicutils.exceptions.PrefixedException;
import com.cosmicpvp.cosmicutils.nbt.NBTTagCompoundBuilder;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.ItemBuilder;
import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import me.ifamasssxd.cosmicskycustoms.struct.CosmicItem;
import me.ifamasssxd.cosmicskycustoms.struct.InventoryInteractable;
import me.ifamasssxd.cosmicskycustoms.struct.InventoryItemType;
import net.astropvp.core.guis.RogueScratchCardGUI;
import net.minecraft.server.v1_8_R3.NBTBase;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class RogueScratchCard extends CosmicItem
        implements InventoryInteractable {
    public RogueScratchCard() {
        super("rogue_scratchcard");
    }

    public ItemStack createItem(Player player, Object ... objects) {
        return this.applyItemData((new ItemBuilder(Material.PAPER, CC.translate("&c&lScratchCard &7(Right-Click)")
                , CC.Gray + ""
                , CC.translate("&7&nRight-Click&7 to have a chance"),
                CC.translate("&7to unlock a reward from this scratchcard!"),
                CC.translate(""),
                CC.translate("&c&l * &cReward: &fRogue Book"),
                CC.translate("&c&l * &cChance: &f0% - 100%"),
                CC.translate("&c&l * &cLevel: &fI - III"),
                CC.translate(""),
                CC.translate("&8[&c!&8] &fUnlocked from the &cScratch Merchant&f &7(/merchant)&f.")

        ).build())).build();
    }

    public boolean acceptsInventoryClickType(InventoryItemType inventoryItemType) {
        return inventoryItemType == InventoryItemType.CLICKED;
    }

    public static ItemStack getRogueSratchCard() {
        return new ItemBuilder(Material.PAPER, CC.translate("&c&lScratchCard &7(Right-Click)")
                , CC.Gray + ""
                , CC.translate("&7&nRight-Click&7 to have a chance"),
                CC.translate("&7to unlock a reward from this scratchcard!"),
                CC.translate(""),
                CC.translate("&c&l * &cReward: &fRogue Book"),
                CC.translate("&c&l * &cChance: &f0% - 100%"),
                CC.translate("&c&l * &cLevel: &fI - III"),
                CC.translate(""),
                CC.translate("&8[&c!&8] &fUnlocked from the &cScratch Merchant&f &7(/merchant)&f.")

        ).build();
    }

    public void onInventoryClick(Player player, ItemStack cursor, NBTWrapper wrapper, NBTWrapper clickedWrapper, ItemStack clicked, InventoryClickEvent e) throws CosmicException {

    }

    public void onItemUse(Player player, ItemStack item, NBTWrapper wrapper, PlayerInteractEvent event) throws CosmicException {
        if (item == null) {
            return;
        }
        if (wrapper == null) {
            return;
        }
        if (item.getType() == Material.AIR) {
            return;
        }

        if (item.getType() != Material.PAPER) {
            return;
        }

        if (!(player.getWorld().getName().equals("world"))) {
            throw new PrefixedException("You cannot claim this rank in this world!");
        }

        if (item.getAmount()>1) {
            throw new PrefixedException("You cannot apply multiple cards at once!");
        }
        NBTTagCompoundBuilder cosmicData = wrapper.getCompoundOrCreate("conaxData");

        cosmicData.setInt("rogue_scratchcard", Integer.valueOf(1));
        event.setCancelled(true);
        player.setItemInHand(null);
        wrapper.setNBTBase("conaxData", (NBTBase)cosmicData.get());
        item = wrapper.build();

        RogueScratchCardGUI card = new RogueScratchCardGUI(player);
        card.open();
    }
}

