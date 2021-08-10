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
import net.minecraft.server.v1_8_R3.NBTBase;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class AstroPlusRank extends CosmicItem
        implements InventoryInteractable {
    public AstroPlusRank() {
        super("astro+_rank");
    }

    public ItemStack createItem(Player player, Object ... objects) {
        return this.applyItemData((new ItemBuilder(Material.NETHER_STAR, CC.translate("&8&l✪ &a&lAstro&e&l+ &e&lRANK &f&l&oVoucher &8&l✪ &7(Right Click)")
                , CC.Gray + ""
                , CC.translate("&7&nRight-Click&7 to unlock"),
                CC.translate("&7the &aAstro&e+ &7rank on this server!"),
                CC.translate(""),
                CC.translate("&eRequires: &fAstro Rank")

        ).build())).build();
    }

    public boolean acceptsInventoryClickType(InventoryItemType inventoryItemType) {
        return inventoryItemType == InventoryItemType.CLICKED;
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

        if (item.getType() != Material.NETHER_STAR) {
            return;
        }

        if (!(player.getWorld().getName().equals("world"))) {
            throw new PrefixedException("You cannot claim this rank in this world!");
        }

        String rank = PermissionsAPI.getPermissionUser(player.getUniqueId()).getGroup();

        if (!rank.equalsIgnoreCase("astro")) {
            throw new PrefixedException("You cannot redeem lower tier ranks.");
        }

        if (item.getAmount()>1) {
            throw new PrefixedException("You cannot apply multiple ranks at once!");
        }
        NBTTagCompoundBuilder cosmicData = wrapper.getCompoundOrCreate("conaxData");

        cosmicData.setInt("astro+Rank", Integer.valueOf(1));
        event.setCancelled(true);
        player.setItemInHand(null);
        wrapper.setNBTBase("conaxData", (NBTBase)cosmicData.get());
        item = wrapper.build();

        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + player.getName() + " group add astro+");
        player.sendMessage("");
        player.sendMessage(CC.translate("&b&l(!)&b Congratulations on redeeming the apollo rank!"));
        player.sendMessage(CC.translate("&7You now have access to all the apollo perks."));
        player.sendMessage("");
    }
}

