package com.conaxgames.customskulls.listeners;

import com.conaxgames.customskulls.CoreSkulls;
import com.conaxgames.customskulls.ability.AbilityData;
import com.conaxgames.customskulls.enums.AbilityType;
import com.conaxgames.customskulls.util.CC;
import com.conaxgames.customskulls.util.CrateKeyAPI;
import com.cosmicpvp.cosmicutils.utils.NumberUtils;
import de.inventivegames.particle.ParticleEffect;
import me.astropvp.cratekeys.AstroCrateKeysAPI;
import me.astropvp.cratekeys.AstroCrateKeysPlugin;
import me.astropvp.cratekeys.enums.CrateType;
import me.astropvp.cratekeys.utils.ConfigUtils;
import me.ily2bb.slotbot.CosmicSlotBotAPI;
import net.astropvp.CollectPlugin;
import net.astropvp.struct.CollectCategory;
import net.astropvp.struct.CollectType;
import org.arkhamnetwork.Arkkit.patches.balancetop.BalanceTop;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CrateKeyGUIListener implements Listener {


    @EventHandler
    public void onRightClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (e.getAction() != Action.RIGHT_CLICK_BLOCK) return;

        Block clickedBlock = e.getClickedBlock();
        if (clickedBlock.getType() != Material.NOTE_BLOCK) return;


        if (AbilityData.getByUUID(AbilityData.getUUID(clickedBlock.getLocation())) != null) {
            e.setCancelled(true);
            AbilityData data = AbilityData.getByUUID(AbilityData.getUUID(clickedBlock.getLocation()));
            if (data.getAbilityType() == AbilityType.SECURITY_SYSTEM) {
                HashMap<CrateType, Integer> keys = CrateKeyAPI.getCrateKeysInInventory(p.getInventory());
                int tickets = 0;
                for (Integer amount : keys.values()) {
                    tickets+=amount;
                }
                double price = tickets * 1000;


                Inventory inventory = Bukkit.createInventory(null, 9, "Personal CrateKey Opener");

                ItemStack confirmButton = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 5);
                ItemMeta meta = confirmButton.getItemMeta();
                meta.setDisplayName(CC.translate("&a&lConfirm Button"));
                List<String> lore = new ArrayList<>();
                lore.add(CC.translate("&7&nRight-Click&7 to open &f" + tickets + "&7 Cratekeys"));
                lore.add("");
                lore.add(CC.translate("&a&lPrice: &f$" + NumberUtils.formatMoney(price) + " &7($1,000 x &n" + tickets + "&7)"));
                meta.setLore(lore);
                confirmButton.setItemMeta(meta);
                inventory.setItem(4, confirmButton);


                CoreSkulls.getInSlotbotMachine().put(p.getUniqueId(), data);
                p.openInventory(inventory);
                p.sendMessage(CC.translate("&a&l(!)&a Opening the Personal CrateKey Opener!"));
                p.playSound(p.getLocation(), Sound.CHEST_OPEN, 5, 5);


            }
        }
    }

    @EventHandler
    public void inventoryClickEvent(InventoryClickEvent e) {
        if (e.getClickedInventory() == null) return;
        if (e.getClickedInventory().getTitle() == null) return;
        if (e.getClickedInventory().getTitle().equals("Personal CrateKey Opener")) {
            Player p = (Player) e.getWhoClicked();

            if (e.getSlot() == 4) {
                HashMap<CrateType, Integer> keys = CrateKeyAPI.getCrateKeysInInventory(p.getInventory());
                int tickets = 0;
                for (Integer amount : keys.values()) {
                    tickets+=amount;
                }
                if (tickets == 0) {
                    e.setCancelled(true);
                    p.sendMessage(CC.translate("&c&l(!)&c You have no cratekeys in your inventory!"));
                    return;
                }
                double price = tickets * 1000;
                if (BalanceTop.getEconomy().getBalance(p) < price) {
                    p.sendMessage(CC.translate("&c&l(!)&c You do not have enough money to use the CrateKey Opener!"));
                    e.setCancelled(true);
                    return;
                }

                List<ItemStack> loot = new ArrayList<>();
                for (Map.Entry<CrateType, Integer> crate : keys.entrySet()) {
                    CrateType type = crate.getKey();
                    Integer amount = crate.getValue();
                    while (amount != 0) {
                        ItemStack itemStack = ConfigUtils.getRandomItem(AstroCrateKeysPlugin.getCachedCrateLoot().get(type));
                        loot.add(itemStack);
                        amount--;
                    }
                }

                CollectPlugin.getInstance().getCollectManager().getPlayerCollect(p).add(new CollectCategory(CollectType.CRTAEKEY, loot.toArray(new ItemStack[0]), com.cosmicpvp.cosmicutils.utils.CC.GoldB + "CrateKey Opener Loot"));
                p.sendMessage("");
                p.sendMessage(CC.translate("&3&l(&b&lCOLLECT&3&l)&b The CrateKey Loot has been put into your &f/collect&b."));
                p.sendMessage("");

                //remove slots from player inv
                Inventory inventory = e.getWhoClicked().getInventory();
                for (ItemStack itemStack : inventory) {
                    if (AstroCrateKeysAPI.isCrateKey(itemStack)) {
                        inventory.remove(itemStack);
                    }
                }
                e.getWhoClicked().getInventory().setContents(inventory.getContents());
                BalanceTop.getEconomy().withdrawPlayer(p, price);
                p.sendMessage(CC.translate("&c&l- $" +NumberUtils.formatMoney(price)));
                p.closeInventory();
                p.playSound(p.getLocation(), Sound.VILLAGER_YES, 5,5);

                try {
                    ParticleEffect.WITCH_MAGIC.sendToPlayers(Bukkit.getOnlinePlayers(),e.getWhoClicked().getLocation(), 2,2,2,0.05f,40);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }


    @EventHandler
    public void cancelInvClick(InventoryClickEvent e) {
        if (e.getInventory().getName().equals("Personal CrateKey Opener")) {
            e.setCancelled(true);
        }
    }

}
