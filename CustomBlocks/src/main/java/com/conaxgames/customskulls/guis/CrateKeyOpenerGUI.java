package com.conaxgames.customskulls.guis;

import com.conaxgames.customskulls.CoreSkulls;
import com.conaxgames.customskulls.ability.AbilityData;
import com.conaxgames.customskulls.data.SaveCustomBlock;
import com.conaxgames.customskulls.struct.MobGrinderCache;
import com.conaxgames.customskulls.util.CC;
import com.conaxgames.customskulls.util.CrateKeyAPI;
import com.conaxgames.customskulls.util.CustomBlockUtil;
import com.conaxgames.customskulls.util.inventoryui.TimeUtil;
import com.cosmicpvp.cosmicutils.utils.EconomyUtils;
import com.cosmicpvp.cosmicutils.utils.ItemBuilder;
import com.cosmicpvp.cosmicutils.utils.NumberUtils;
import de.inventivegames.particle.ParticleEffect;
import lombok.Getter;
import me.astropvp.cratekeys.AstroCrateKeysAPI;
import me.astropvp.cratekeys.AstroCrateKeysPlugin;
import me.astropvp.cratekeys.enums.CrateType;
import me.astropvp.cratekeys.utils.ConfigUtils;
import me.mattstudios.mfgui.gui.guis.GuiItem;
import me.mattstudios.mfgui.gui.guis.PaginatedGui;
import org.arkhamnetwork.Arkkit.patches.balancetop.BalanceTop;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class CrateKeyOpenerGUI {
    private final PaginatedGui customBlockGui;

    public CrateKeyOpenerGUI(AbilityData data, Block block, Player player) {
            player.sendMessage(CC.translate("&a&l(!)&a Opening the Personal CrateKey Opener!"));
            player.playSound(player.getLocation(), Sound.CHEST_OPEN, 5, 5);
         this.customBlockGui = new PaginatedGui(3, 27, "CrateKey Custom Block Menu");

            HashMap<CrateType, Integer> keys = CrateKeyAPI.getCrateKeysInInventory(player.getInventory());
            int tickets = 0;
            for (Integer amount : keys.values()) {
                tickets += amount;
            }
            double price = tickets * 1000;



            ItemStack confirmButton = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 5);
            ItemMeta meta = confirmButton.getItemMeta();
            meta.setDisplayName(CC.translate("&a&lConfirm Button"));
            List<String> lore = new ArrayList<>();
            lore.add(CC.translate("&7&nRight-Click&7 to open &f" + tickets + "&7 Cratekeys"));
            lore.add("");
            lore.add(CC.translate("&a&lPrice: &f$" + NumberUtils.formatMoney(price) + " &7($1,000 x &n" + tickets + "&7)"));
            meta.setLore(lore);
            confirmButton.setItemMeta(meta);

        int finalTickets = tickets;

        this.customBlockGui.setItem(2, 4, new GuiItem(CoreSkulls.customBlocks.get(data.getAbilityType().getName()).getCustomBlockItem()));


        customBlockGui.setItem(2, 6, new GuiItem(confirmButton, event -> {

                if (finalTickets == 0) {
                    player.sendMessage(CC.translate("&c&l(!)&c You have no cratekeys in your inventory!"));
                    return;
                }
                if (BalanceTop.getEconomy().getBalance(player) < price) {
                    player.sendMessage(CC.translate("&c&l(!)&c You do not have enough money to use the CrateKey Opener!"));
                    return;
                }



                Location nearbyChest = CustomBlockUtil.getClosestChestLocation(data.getLocation());
                if (nearbyChest != null) {
                    Chest chest = (Chest) nearbyChest.getBlock().getState();
                    if (!CustomBlockUtil.isEmpty(chest.getInventory())) {
                        player.sendMessage(CC.translate("&c&l(!)&c Please empty the chest before opening new cratekeys!"));
                        return;
                    }
                    for (Map.Entry<CrateType, Integer> crate : keys.entrySet()) {
                        CrateType type = crate.getKey();
                        Integer amount = crate.getValue();
                        while (amount != 0) {
                            if (!CustomBlockUtil.isEmpty(chest.getInventory())) {
                                Location newNearbyChest = CustomBlockUtil.getClosestChestLocation(data.getLocation());
                                if (newNearbyChest != null) {
                                    chest = (Chest)newNearbyChest.getBlock().getState();
                                }
                            }
                            ItemStack itemStack = ConfigUtils.getRandomItem(AstroCrateKeysPlugin.getCachedCrateLoot().get(type));
                            chest.getInventory().addItem(itemStack);
                            amount--;
                        }
                    }

                } else {
                    player.sendMessage(CC.translate("&c&l(!)&c There are no nearby empty chests to fill with loot"));
                    return;
                }




                //remove slots from player inv
                Inventory inventory = player.getInventory();
                for (ItemStack itemStack : inventory) {
                    if (AstroCrateKeysAPI.isCrateKey(itemStack)) {
                        inventory.remove(itemStack);
                    }
                }
                player.getInventory().setContents(inventory.getContents());
                BalanceTop.getEconomy().withdrawPlayer(player, price);
                player.sendMessage(CC.translate("&c&l- $" + NumberUtils.formatMoney(price)));
                player.closeInventory();
                player.playSound(player.getLocation(), Sound.VILLAGER_YES, 5, 5);

                try {
                    ParticleEffect.WITCH_MAGIC.sendToPlayers(Bukkit.getOnlinePlayers(), player.getLocation(), 2, 2, 2, 0.05f, 40);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }));


            customBlockGui.setDefaultClickAction(event -> event.setCancelled(true));



            customBlockGui.getFiller().fillBorder(new GuiItem(new ItemBuilder(new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)7)).name(CC.translate("&7")).build()));
        }



}
