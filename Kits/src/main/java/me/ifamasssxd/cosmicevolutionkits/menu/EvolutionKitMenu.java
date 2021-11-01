/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.gui.CosmicGUI
 *  com.cosmicpvp.cosmicutils.gui.InventoryItem
 *  com.cosmicpvp.cosmicutils.me.astropvp.cratekeys.utils.CC
 *  org.bukkit.ChatColor
 *  org.bukkit.entity.Player
 *  org.bukkit.event.me.astropvp.cratekeys.inventory.ClickType
 *  org.bukkit.plugin.java.JavaPlugin
 */
package me.ifamasssxd.cosmicevolutionkits.menu;

import com.cosmicpvp.cosmicutils.gui.CosmicGUI;
import com.cosmicpvp.cosmicutils.gui.InventoryItem;
import com.cosmicpvp.cosmicutils.utils.CC;
import me.cosmicpvp.CosmicGlobalKits.CosmicGlobalKits;
import me.ifamasssxd.cosmicevolutionkits.structure.EvolutionKit;
import me.ifamasssxd.cosmicevolutionkits.structure.EvolutionKits;
import me.ifamasssxd.cosmicevolutionkits.structure.KitPlayer;
import me.ifamasssxd.cosmicevolutionkits.structure.ekits.Mimic;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.plugin.java.JavaPlugin;

public class EvolutionKitMenu
extends CosmicGUI {
    public EvolutionKitMenu(Player player) {
        super(player, "Evolution Kits", 9);
    }

    public void redraw() {
        this.inventory.clear();
        this.getInventoryItems().clear();
        KitPlayer kitPlayer = KitPlayer.kitPlayerMap.get(this.player.getUniqueId());
        if (kitPlayer == null) {
            this.closeWithDelay();
            return;
        }
        for (EvolutionKits kit : EvolutionKits.values()) {
            EvolutionKit internalKit = kit.getKit();
            this.setItem(internalKit.getInventorySlot(), new InventoryItem(kitPlayer.getKitItem(kit, this.player)).click(ClickType.RIGHT, () -> {
                CosmicGUI previewMenu = new KitPreviewMenu(this.player, kit, kit == EvolutionKits.MIMIC ? ((Mimic)internalKit).getRandomNotRigged(this.player, 10) : internalKit.getItems(this.player, 10)).setParentGUI(this);
                this.closeWithDelay(pl -> previewMenu.openGUI(CosmicGlobalKits.getPlugin()));
            }).click(ClickType.LEFT, () -> {
                if (kitPlayer.isLoadingData()) {
                    this.player.sendMessage(CC.RedB + "(!) " + CC.Red + "Please wait while your /vkit me.astropvp.cratekeys.data is loaded!");
                    this.closeWithDelay();
                    return;
                }
                if (!kitPlayer.hasAccess(this.player, kit)) {
                    this.player.closeInventory();
                    this.player.sendMessage((Object)ChatColor.RED + ChatColor.BOLD.toString() + "(!) " + (Object)ChatColor.RED + "You have not unlocked this Evolution Kit!");
                    this.player.sendMessage((Object)ChatColor.GRAY + "You can preview a max level version by " + (Object)ChatColor.UNDERLINE + "right-clicking" + (Object)ChatColor.GRAY + " the /vkit item. Unlock access to this kit with a Fallen Hero from " + (Object)ChatColor.GRAY + (Object)ChatColor.UNDERLINE + "buy.astropvp.net!");
                    return;
                }
                if (kit.checkFund(this.player)) {
                    return;
                }
                String cooldownString = kitPlayer.getCooldownTimer(kit);
                if (cooldownString != null) {
                    this.player.sendMessage((Object)ChatColor.YELLOW + "You " + (Object)ChatColor.YELLOW + (Object)ChatColor.UNDERLINE + "cannot" + (Object)ChatColor.YELLOW + " use this Evolution Kit for another " + cooldownString + "!");
                    this.player.closeInventory();
                    return;
                }
                kit.getKit().onClaim(this.player, kit, kitPlayer);
            }));
        }
        this.player.updateInventory();
    }

    public boolean isOpen() {
        return this.player != null && this.player.isValid() && this.player.getOpenInventory() != null && this.player.getOpenInventory().getTopInventory() != null && this.player.getOpenInventory().getTopInventory().equals((Object)this.inventory);
    }
}

