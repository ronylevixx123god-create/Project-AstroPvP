package com.conaxgames.customskulls.listeners;

import com.conaxgames.customskulls.CoreSkulls;
import com.conaxgames.customskulls.ability.AbilityData;
import com.conaxgames.customskulls.blocks.CustomBlock;
import com.conaxgames.customskulls.enums.AbilityType;
import com.conaxgames.customskulls.util.CC;
import com.conaxgames.customskulls.util.CustomBlockUtil;
import com.cosmicpvp.cosmicutils.utils.InventoryUtils;
import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import com.cosmicpvp.cosmicutils.utils.NumberUtils;
import de.inventivegames.particle.ParticleEffect;
import me.ily2bb.slotbot.CosmicSlotBotAPI;
import me.ily2bb.slotbot.SlotBotPlugin;
import me.ily2bb.slotbot.managers.SlotBotManager;
import me.ily2bb.slotbot.objects.ItemListCacher;
import me.ily2bb.slotbot.utils.Utils;
import net.astropvp.CollectPlugin;
import net.astropvp.struct.CollectCategory;
import net.astropvp.struct.CollectType;
import org.arkhamnetwork.Arkkit.patches.balancetop.BalanceTop;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
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
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class SlotbotMachineGUIListener implements Listener {


    @EventHandler
    public void onRightClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (e.getAction() != Action.RIGHT_CLICK_BLOCK) return;

        Block clickedBlock = e.getClickedBlock();
        if (clickedBlock.getType() != Material.NOTE_BLOCK) return;


        if (AbilityData.getByUUID(AbilityData.getUUID(clickedBlock.getLocation())) != null) {
            e.setCancelled(true);
            AbilityData data = AbilityData.getByUUID(AbilityData.getUUID(clickedBlock.getLocation()));
            if (data.getAbilityType() == AbilityType.COSMIC_TOKEN_GENERATOR) {
                int tickets = CosmicSlotBotAPI.getSlotsInInventory(p);
                double price = tickets * 10000;


                Inventory inventory = Bukkit.createInventory(null, 9, "Personal Slotbot Machine");

                ItemStack confirmButton = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 5);
                ItemMeta meta = confirmButton.getItemMeta();
                meta.setDisplayName(CC.translate("&a&lConfirm Button"));
                List<String> lore = new ArrayList<>();
                lore.add(CC.translate("&7&nRight-Click&7 to open &f" + tickets + "&7 Slotbot tickets"));
                lore.add("");
                lore.add(CC.translate("&a&lPrice: &f$" + NumberUtils.formatMoney(price) + " &7($10,000 x &n" + tickets + "&7)"));
                meta.setLore(lore);
                confirmButton.setItemMeta(meta);
                inventory.setItem(4, confirmButton);


                CoreSkulls.getInSlotbotMachine().put(p.getUniqueId(), data);
                p.openInventory(inventory);
                p.sendMessage(CC.translate("&a&l(!)&a Opening the Personal Slotbot Machine!"));
                p.playSound(p.getLocation(), Sound.CHEST_OPEN, 5, 5);


            }
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        if (event.getInventory().getTitle() != null && event.getInventory().getTitle().equals("Personal Slotbot Machine")) {
            CoreSkulls.getInSlotbotMachine().remove(event.getPlayer().getUniqueId());

        }
    }


    @EventHandler
    public void inventoryClickEvent(InventoryClickEvent e) {
        if (e.getClickedInventory() == null) return;
        if (e.getClickedInventory().getTitle() == null) return;
        if (e.getClickedInventory().getTitle().equals("Personal Slotbot Machine")) {
            Player p = (Player) e.getWhoClicked();

            if (e.getSlot() == 4) {
                int tickets = CosmicSlotBotAPI.getSlotsInInventory(p);
                if (tickets == 0) {
                    e.setCancelled(true);
                    p.sendMessage(CC.translate("&c&l(!)&c You have no slots in your inventory!"));
                    return;
                }
                double price = tickets * 10000;
                if (BalanceTop.getEconomy().getBalance(p) < price) {
                    p.sendMessage(CC.translate("&c&l(!)&c You do not have enough money to use the Slotbot Machine!"));
                    e.setCancelled(true);
                    return;
                }
                int a = tickets;
                List<ItemStack> loot = new ArrayList<>();
                while (a != 0) {
                    final ItemStack itemStack = CosmicSlotBotAPI.getRandomSlotBotLootItem();
                    loot.add(itemStack);
                    a--;
                }

                CollectPlugin.getInstance().getCollectManager().getPlayerCollect(p.getUniqueId()).add(new CollectCategory(CollectType.SLOTBOT, loot.toArray(new ItemStack[0]), com.cosmicpvp.cosmicutils.utils.CC.GreenB + "Personal Slotbot Machine Loot", System.currentTimeMillis()));
                p.sendMessage("");
                p.sendMessage(CC.translate("&3&l(&b&lCOLLECT&3&l)&b The SlotBot Loot has been put into your &f/collect&b."));
                p.sendMessage("");

                //remove slots from player inv
                Inventory inventory = e.getWhoClicked().getInventory();
                for (ItemStack itemStack : inventory) {
                    if (CosmicSlotBotAPI.isSlotbotTicket(itemStack)) {
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
        if (e.getInventory().getName().equals("Personal Slotbot Machine")) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void cancelView(InventoryOpenEvent e) {
        if (e.getInventory().getTitle().equals("Personal Slotbot Machine") && (e.getInventory().getViewers().size() > 1)) {
            e.getPlayer().sendMessage(CC.translate("&c&l(!)&c You cannot open the Slotbot Machine when someone else is using it."));
            e.setCancelled(true);
        }
    }

}
