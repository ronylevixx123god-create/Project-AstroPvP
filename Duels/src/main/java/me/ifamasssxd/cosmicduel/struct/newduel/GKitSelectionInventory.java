/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  me.cosmicpvp.CosmicGlobalKits.libs.GlobalKits
 *  org.bukkit.Sound
 *  org.bukkit.entity.Player
 *  org.bukkit.event.inventory.InventoryClickEvent
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 */
package me.ifamasssxd.cosmicduel.struct.newduel;

import me.cosmicpvp.CosmicGlobalKits.libs.GlobalKits;
import me.ifamasssxd.cosmicduel.CosmicDuel;
import me.ifamasssxd.cosmicduel.inventory.CustomInventory;
import me.ifamasssxd.cosmicduel.inventory.CustomInventoryItem;
import me.ifamasssxd.cosmicduel.struct.Kit;
import me.ifamasssxd.cosmicduel.struct.enums.DuelCreateState;
import me.ifamasssxd.cosmicduel.utils.EnchantUtils;
import me.ifamasssxd.cosmicduel.utils.ItemBuilder;
import me.ifamasssxd.cosmicduel.utils.KitUtils;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

public class GKitSelectionInventory
extends CustomInventory {
    NewDuel duel;

    public GKitSelectionInventory(NewDuel duel) {
        super("Select a Duel GKit", 6);
        this.duel = duel;
    }

    public void prepareInventory(Player player) {
        int index = 0;
        GlobalKits[] arrglobalKits = GlobalKits.values();
        int n = arrglobalKits.length;
        int n2 = 0;
        while (true) {
            if (n2 >= n) {
                player.setMetadata("close_exempt", (MetadataValue)new FixedMetadataValue((Plugin)CosmicDuel.get(), (Object)""));
                this.openInventory(player);
                return;
            }
            GlobalKits globalKit = arrglobalKits[n2];
            if (!KitUtils.isInvalidGKit(globalKit)) {
                CustomInventoryItem globalKitItem = new CustomInventoryItem(ItemBuilder.buildItem(globalKit.getItemIcon(), 1, (short)0, globalKit.getItemName(), globalKit.getItemLore()));
                if (this.duel.getCustomGKit() == globalKit) {
                    globalKitItem.addUnsafeEnchantment(EnchantUtils.getCustomEnchant(), 1);
                }
                globalKitItem.setCallback((CustomInventory inventory, CustomInventoryItem customItem, InventoryClickEvent event) -> {
                    event.setCancelled(true);
                    this.duel.setCustomGKit(globalKit);
                    this.duel.setKit(Kit.GKIT);
                    player.playSound(player.getLocation(), Sound.ORB_PICKUP, 1.0f, 1.0f);
                    this.duel.getSettings().updateSettingsInventory();
                    this.duel.setCreateState(DuelCreateState.SETTINGS);
                    player.setMetadata("close_exempt", (MetadataValue)new FixedMetadataValue((Plugin)CosmicDuel.get(), (Object)""));
                    this.duel.getSettings().getSettings().openInventory(player);
                });
                if (index == this.getInventorySize() - 9) {
                    index += 3;
                }
                this.insertItem(index++, globalKitItem);
            }
            ++n2;
        }
    }
}

