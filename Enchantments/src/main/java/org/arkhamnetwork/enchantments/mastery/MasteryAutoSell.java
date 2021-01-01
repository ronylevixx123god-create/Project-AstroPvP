package org.arkhamnetwork.enchantments.mastery;

import org.arkhamnetwork.enchantments.*;
import org.bukkit.material.*;
import org.arkhamnetwork.enchantments.util.*;
import me.ifamasssxd.cosmicguishop.*;
import me.ifamasssxd.cosmicguishop.struct.*;
import org.bukkit.entity.*;
import org.bukkit.block.*;
import org.bukkit.event.block.*;
import org.bukkit.inventory.*;
import com.rit.sucy.*;
import org.bukkit.*;
import com.cosmicpvp.cosmicutils.customevents.*;
import org.bukkit.event.*;
import java.util.*;

public class MasteryAutoSell extends MasteryCustomEnchantment
{
    private ShopManager shopManager;
    private static HashMap<MaterialData, Double> cachedShopPrices;
    
    public MasteryAutoSell() {
        super("Auto Sell", GeneralUtil.tools, 10);
        this.max = 1;
        MasteryAutoSell.cachedShopPrices = new HashMap<MaterialData, Double>();
        this.shopManager = CosmicGUIShop.get().getShopManager();
        for (final ShopItemMap items : this.shopManager.getShopItems().values()) {
            for (final Map.Entry<ShopMaterialData, ItemPrice> itemData : items.entrySet()) {
                MasteryAutoSell.cachedShopPrices.put(new MaterialData(itemData.getKey().getType(), (byte)itemData.getKey().getData()), itemData.getValue().getSellPrice());
            }
        }
        Bukkit.getLogger().info("[Enchantments (AutoSell)] Cached " + MasteryAutoSell.cachedShopPrices.size() + " /shop material sell prices.");
    }
    
    public void applyToolEffect(final Player player, final Block block, final int enchantLevel, final BlockEvent event) {
        if (event instanceof BlockBreakEvent) {
            final BlockBreakEvent bbe = (BlockBreakEvent)event;
            final Material m = bbe.getBlock().getType();
            if (bbe.getBlock().getDrops(player.getItemInHand()).size() < 0) {
                return;
            }
            final Collection<ItemStack> drops = (Collection<ItemStack>)bbe.getBlock().getDrops(player.getItemInHand());
            if (drops.size() <= 0) {
                return;
            }
            final ItemStack drop = (ItemStack)drops.toArray()[0];
            final boolean hasDetonate = EnchantmentAPI.itemHasEnchantment(player.getItemInHand(), "Detonate") || EnchantmentAPI.itemHasEnchantment(player.getItemInHand(), "Atomic Detonate");
            if (hasDetonate) {
                return;
            }
            if (bbe.getBlock().getDrops().size() < 0 || ((bbe.getBlock().getType() == Material.IRON_ORE || bbe.getBlock().getType() == Material.GOLD_ORE) && EnchantmentAPI.itemHasEnchantment(player.getItemInHand(), "Auto Smelt"))) {
                return;
            }
            final double price = getSellPrice(drop.getData());
            if (price <= 0.0) {
                return;
            }
            block.setTypeIdAndData(0, (byte)0, true);
            player.giveExp(bbe.getExpToDrop());
            CosmicGUIShop.getEconomy().depositPlayer((OfflinePlayer)player, price);
            Bukkit.getPluginManager().callEvent((Event)new CustomCosmicPlayerEvent(player, "SHOP_SELL", new Object[] { price, drop.getAmount(), drop.getType(), drop.getData() }));
        }
    }
    
    public static double getSellPrice(final MaterialData md) {
        return MasteryAutoSell.cachedShopPrices.getOrDefault(md, 0.0);
    }
}
