package net.astropvp.core.guis;

import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import com.decimatemc.decimatecommons.DecimateCommons;
import com.decimatemc.decimatecommons.inventory.ClickableItem;
import com.decimatemc.decimatecommons.inventory.InventoryListener;
import com.decimatemc.decimatecommons.inventory.SmartInventory;
import com.decimatemc.decimatecommons.inventory.content.InventoryContents;
import com.decimatemc.decimatecommons.inventory.content.InventoryProvider;
import com.decimatemc.decimatecommons.util.CommonsUtil;
import com.rit.sucy.CustomEnchantment;
import com.rit.sucy.EnchantmentAPI;
import com.rit.sucy.service.ERomanNumeral;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import me.ifamasssxd.cosmicskycustoms.CosmicSkyCustomItems;
import org.arkhamnetwork.enchantments.libs.EnchanterRarity;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GodlyTransmogGUI implements InventoryProvider {
    private SmartInventory inventory;

    private final ItemStack originalItem;

    private ItemStack displayItem;

    private boolean didConfirm = false;

    private int currentSelectedIdx = -1;

    private LinkedList<CustomEnchantment> enchantmentsOrder;

    private Map<CustomEnchantment, Integer> enchantments;

    public GodlyTransmogGUI(Player player, ItemStack item) {
        this.originalItem = item;
        this.displayItem = (new NBTWrapper(item)).build().clone();
        this.enchantments = EnchantmentAPI.getEnchantments(this.originalItem);
        this.enchantmentsOrder = new LinkedList<>(EnchantmentAPI.getEnchantments(this.originalItem).keySet());
        this.inventory = SmartInventory.builder().id(UUID.randomUUID().toString()).type(InventoryType.CHEST).size(4, 9).manager(DecimateCommons.getInstance().getInventoryManager()).title("Godly Transmog Scroll").provider(this).listener(new InventoryListener(InventoryCloseEvent.class, event -> {
            if (!this.didConfirm) {
                player.getInventory().addItem(new ItemStack[] { this.originalItem });
                player.getInventory().addItem(new ItemStack[] { CosmicSkyCustomItems.get().getItemManager().getCustomItem("godly_transmog_scroll").createItem(null, new Object[0]) });
            }
        })).build();
    }

    private void removeEnchantsFromDisplay() {
        for (Map.Entry<CustomEnchantment, Integer> data : this.enchantments.entrySet())
            ((CustomEnchantment)data.getKey()).removeFromItem(this.displayItem);
    }

    public void init(Player player, InventoryContents contents) {
        contents.fill(null);
        removeEnchantsFromDisplay();
        ItemMeta meta = this.displayItem.getItemMeta();
        List<String> lore = meta.getLore();
        int i;
        for (i = this.enchantmentsOrder.size() - 1; i >= 0; i--) {
            CustomEnchantment enchantment = this.enchantmentsOrder.get(i);
            lore.add(0, EnchantmentAPI.getChatColor(enchantment) + enchantment.name() + " " + ERomanNumeral.numeralOf(((Integer)this.enchantments.get(enchantment)).intValue()));
        }
        meta.setLore(lore);
        this.displayItem.setItemMeta(meta);
        for (i = 0; i < this.enchantmentsOrder.size(); i++) {
            CustomEnchantment enchantment = this.enchantmentsOrder.get(i);
            int finalIdx = i;
            Arrays.<EnchanterRarity>stream(EnchanterRarity.values())
                    .filter(r -> (r.getTier() == enchantment.getTier()))
                    .findFirst()
                    .ifPresent(enchanterRarity -> contents.add(ClickableItem.of((this.currentSelectedIdx != finalIdx) ? CommonsUtil.createItem(Material.STAINED_GLASS_PANE, EnchantmentAPI.getChatColor(enchantment) + enchantment.name() + " " + ERomanNumeral.numeralOf(((Integer)this.enchantments.get(enchantment)).intValue()), 1, (enchanterRarity.getPaneColor().getWoolData() == 6) ? 2 : enchanterRarity.getPaneColor().getWoolData(), new String[0]) : CommonsUtil.addGlow(CommonsUtil.createItem(Material.STAINED_GLASS_PANE, EnchantmentAPI.getChatColor(enchantment) + enchantment.name() + " " + ERomanNumeral.numeralOf(((Integer)this.enchantments.get(enchantment)).intValue()), 1, (enchanterRarity.getPaneColor().getWoolData() == 6) ? 2 : enchanterRarity.getPaneColor().getWoolData(), new String[] { "&a&l*SELECTED*", "&7Click another enchantment to", "&7swap its position with this one." })), ())));
        }
        contents.set(3, 4, ClickableItem.of(this.displayItem, event -> {
            this.didConfirm = true;
            player.getInventory().addItem(new ItemStack[] { this.displayItem });
            player.closeInventory();
        }));
    }

    public SmartInventory getInventory() {
        return this.inventory;
    }
}
