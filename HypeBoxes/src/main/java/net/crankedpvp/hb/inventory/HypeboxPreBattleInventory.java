package net.crankedpvp.hb.inventory;

import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import net.crankedpvp.hb.HypeboxesPlugin;
import net.crankedpvp.hb.struct.HypeBox;
import net.crankedpvp.hb.struct.HypeBoxManager;
import net.crankedpvp.hb.utils.CC;
import net.crankedpvp.hb.utils.Clickable;
import net.crankedpvp.hb.utils.ItemBuilder;
import net.crankedpvp.hb.utils.LuckpermUtils;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HypeboxPreBattleInventory {

    private final Gui preBattleInv;

    public Gui getUI() {
        return this.preBattleInv;
    }

    private final Map<String, Integer> hypeboxAmounts = new HashMap<>();

    private boolean isSpinning = false;

    private final Player player;

    public HypeboxPreBattleInventory(Player player) {

        this.player = player;

        this.preBattleInv = new Gui(3, "Hypebox Battles");
        this.preBattleInv.setDefaultTopClickAction(event -> event.setCancelled(true));

        this.preBattleInv.setDefaultClickAction(event -> {
            ItemStack clickedItem = event.getCurrentItem();
            if (clickedItem != null) {
                if(HypeBoxManager.isHypebox(clickedItem)) {
                    HypeBox hypeBox = HypeBoxManager.getHypeBoxFromItemStack(clickedItem);
                    int amount = clickedItem.getAmount();
                    event.setCurrentItem(null);
                    addToAmount(hypeBox, amount);
                    updateSlot();
                } else {
                    event.setCancelled(true);
                }
            }
        });

        this.preBattleInv.setItem(Arrays.asList(1, 11, 19, 7, 15, 25), new GuiItem(createFillerItem(DyeColor.RED)));
        this.preBattleInv.setItem(2, 5, new GuiItem(createEmptySlot(), event -> {
            tryOpenHypeboxBattle(player);
        }));

        this.preBattleInv.setCloseGuiAction(event -> {
            if(!isSpinning) {
                hypeboxAmounts.forEach((hb, amt) -> {
                    ItemStack itemStack = HypeboxesPlugin.getInstance().getHypeBoxRegister().getHypebox(hb).createHypeBox(amt);
                    player.getInventory().addItem(itemStack);
                    player.sendMessage(CC.translate("&aYour hype boxes have been refunded"));
                });
            }
        });

    }

    private void tryOpenHypeboxBattle(Player player) {
        int amt = 0;
        for (String s : this.hypeboxAmounts.keySet()) {
            if(this.hypeboxAmounts.get(s) > 0) amt += this.hypeboxAmounts.get(s);
        }
        if(amt < 3) {
            player.sendMessage(CC.translate("&cYou must open a minimum of 3 hypeboxes at once"));
            player.closeInventory();
            return;
        }
        /*
        if(amt > 8) {
            player.sendMessage(CC.translate("&cYou can only open 8 hypeboxes at a time"));
            player.closeInventory();
            return;
        }

         */
        for (String s : this.hypeboxAmounts.keySet()) {
            if(this.hypeboxAmounts.get(s) > 0) {
                this.isSpinning = true;
                player.closeInventory();
                HypeboxBattleInventory hypeboxBattleInventory = new HypeboxBattleInventory(player, hypeboxAmounts);
                hypeboxBattleInventory.getUI().open(player);

                UUID uuid = UUID.randomUUID();
                HypeboxesPlugin.getInstance().getHypeboxBattles().put(uuid, hypeboxBattleInventory);
                Clickable clickable = new Clickable("&f✧ &eClick to watch &c" + LuckpermUtils.getDisplayName(player) + " &eplay against the &6Astro Bot &ein a &4&lHype Box Battle", "&eClick to view &6" + player.getName() + "'s &ehypebox battle &f✧", "/viewbattleopen " + uuid);
                clickable.add(CC.translate("\n&eYou can purchase hypeboxes at &6buy.astropvp.net&e!"));
                for(Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                    onlinePlayer.sendMessage("");
                    clickable.sendToPlayer(onlinePlayer);
                    onlinePlayer.sendMessage("");
                }
                return;
            }
        }
        player.sendMessage(CC.translate("&cYou can't do this battle, contact an admin to find out why"));
    }

    private void updateSlot() {
        if(hypeboxAmounts.isEmpty()) {
            this.preBattleInv.setItem(2, 5, new GuiItem(createEmptySlot(), event -> {
                tryOpenHypeboxBattle(player);
            }));
        } else {
            this.preBattleInv.setItem(2, 5, new GuiItem(createReadySlot(), event -> {
                tryOpenHypeboxBattle(player);
            }));
        }

        this.preBattleInv.update();
    }

    private ItemStack createEmptySlot() {
        return new ItemBuilder(Material.BARRIER, "&c&lInsert Hypebox", "&7Click a hypebox in your inventory",
                "&7to insert it into the battle.").colorize().build();
    }

    private ItemStack createReadySlot() {
        ItemBuilder itemBuilder = new ItemBuilder(Material.SLIME_BALL, "&a&lInsert Hypebox", "&7Click a hypebox in your inventory",
                "&7to insert it into the battle.", "", "&fCurrent Hypeboxes:");
        for (String s : hypeboxAmounts.keySet()) {
            itemBuilder.addLore(" &f* " + hypeboxAmounts.get(s) + "x " + s + " Hypeboxes");
        }
        itemBuilder.addLore("", "&a&lClick &fto enter the hypebox battle");
        return itemBuilder.colorize().build();
    }


    private ItemStack createFillerItem(DyeColor dyeColor) {
        ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1, dyeColor.getData());
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(CC.translate(" "));
        item.setItemMeta(meta);
        return item;
    }

    private void addToAmount(HypeBox hypeBox, int amount) {
        String id = hypeBox.getId();
        if(this.hypeboxAmounts.containsKey(id)) {
            this.hypeboxAmounts.put(id, this.hypeboxAmounts.get(id) + amount);
        } else {
            this.hypeboxAmounts.put(id, amount);
        }
    }

}
