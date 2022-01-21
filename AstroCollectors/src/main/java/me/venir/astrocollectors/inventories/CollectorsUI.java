package me.venir.astrocollectors.inventories;

import com.cosmicpvp.cosmicutils.utils.FactionUtils;
import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.Faction;
import lombok.Getter;
import me.mattstudios.mfgui.gui.components.util.ItemBuilder;
import me.mattstudios.mfgui.gui.guis.Gui;
import me.mattstudios.mfgui.gui.guis.GuiItem;
import me.venir.astrocollectors.AstroCollectors;
import me.venir.astrocollectors.collector.Collector;
import me.venir.astrocollectors.collector.CollectorManager;
import me.venir.astrocollectors.collector.CollectorSellLog;
import me.venir.astrocollectors.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CollectorsUI {

    @Getter
    private final Gui collectorUI;
    private final Collector collector;

    public CollectorsUI(AstroCollectors plugin, Player player, Location location) {
        CollectorManager collectorManager = CollectorManager.DELEGATE;
        collector = collectorManager.getCollector(location);
        FLocation fLocation = new FLocation(location);
        Faction faction = FactionUtils.getFactionAt(fLocation);

        collectorUI = new Gui(1, ChatColor.GREEN + faction.getTag() + "'s Collector");

        BukkitTask runnable = Bukkit.getScheduler().runTaskTimer(plugin, this::updateItems, 20L, 20L);

        collectorUI.setCloseGuiAction(event -> runnable.cancel());

        collectorUI.setDefaultClickAction(event -> event.setCancelled(true));

        collectorUI.setItem(0, new GuiItem(new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 7)));
        collectorUI.setItem(1, new GuiItem(new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 5)));
        collectorUI.setItem(7, new GuiItem(new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 5)));
        collectorUI.setItem(8, new GuiItem(new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 7)));

        collectorUI.setItem(2, ItemBuilder
                .from(Material.GOLD_NUGGET)
                .setName(ChatColor.GREEN + ChatColor.BOLD.toString() + "Mob Drops")
                .setLore(getMobDropsLore())
                .asGuiItem(event -> {
                    event.setCancelled(true);
                    int amount = collector.getTotalMobDropAmount();
                    if (amount == 0) return;
                    collector.addMobDropSellLog(player, amount, collector.getAllMobDropsWorth());
                    collector.sellAllMobDrops(player);
                    updateItems();
                }));

        collectorUI.setItem(3, ItemBuilder
                .from(Material.TNT)
                .setName(ChatColor.GREEN + ChatColor.BOLD.toString() + "TNT Drops")
                .setLore(getTntLore())
                .asGuiItem(event -> {
                    event.setCancelled(true);
                    int amount = collector.getAmount(Material.TNT);
                    if (amount == 0) return;
                    collector.sellTNT(player);
                    updateItems();
                }));

        collectorUI.setItem(6, ItemBuilder
                .from(Material.PAPER)
                .setName(ChatColor.GREEN + ChatColor.BOLD.toString() + "Sell Logs")
                .setLore(getSellLogLore())
                .asGuiItem(event -> event.setCancelled(true)));

    }

    public void updateItems() {

        collectorUI.updateItem(2, ItemBuilder
                .from(Material.GOLD_NUGGET)
                .setName(ChatColor.GREEN + ChatColor.BOLD.toString() + "Mob Drops")
                .setLore(Utils.format(getMobDropsLore()))
                .asGuiItem().getItemStack());

        collectorUI.updateItem(3, ItemBuilder
                .from(Material.TNT)
                .setName(ChatColor.GREEN + ChatColor.BOLD.toString() + "TNT Drops")
                .setLore(Utils.format(getTntLore()))
                .asGuiItem().getItemStack());

        collectorUI.updateItem(6, ItemBuilder
                .from(Material.PAPER)
                .setName(ChatColor.GREEN + ChatColor.BOLD.toString() + "Sell Logs")
                .setLore(Utils.format(getSellLogLore()))
                .asGuiItem().getItemStack());

        collectorUI.update();
    }

    private List<String> getSellLogLore() {
        List<CollectorSellLog> sellLogs = collector.getSellLogs();
        List<String> sellLogsLore = new ArrayList<>();
        sellLogsLore.add("");
        sellLogs.stream().limit(10).forEach(log -> {
            String sellTypeString = log.getSellLogType() == CollectorSellLog.SellLogType.TNT ? "Deposited &c" + Utils.formatNum(log.getAmount()) + "&7 TNT" : "Sold &a" + Utils.formatNum(log.getAmount()) + "&7 drops for &2$&a" + Utils.formatNum(log.getWorth()) + "&7";
            sellLogsLore.add(1, Utils.format("&7&o" + log.getSellerName() + "&r&7 " + sellTypeString + " &8(&7" + Utils.splitToComponentTimes((System.currentTimeMillis() - log.getSellDate()) / 1000) + "ago&8)"));
        });
        return sellLogsLore;
    }

    private List<String> getTntLore() {
        return Utils.format(Arrays.asList("",
                "&7Right-Click to deposit your TNT",
                "&7into your faction TNT bank",
                "",
                "&a&l* AMOUNT: &f" + Utils.formatNum(collector.getAmount(Material.TNT))));
    }

    private List<String> getMobDropsLore() {
        return Utils.format(Arrays.asList("",
                "&7Right-Click to withdraw the money",
                "&7to your own balance",
                "",
                "&a&l* AMOUNT: &f" + Utils.formatNum(collector.getTotalMobDropAmount()),
                "&a&l* WORTH: &2$&f" + Utils.formatNum(collector.getAllMobDropsWorth())));
    }

}
