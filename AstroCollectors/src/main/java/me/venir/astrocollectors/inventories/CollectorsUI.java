package me.venir.astrocollectors.inventories;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.NumberUtils;
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
import me.venir.astrocollectors.utils.SpawnerUtil;
import me.venir.astrocollectors.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;

public class CollectorsUI {

    @Getter
    private final Gui collectorUI;
    private final Collector collector;

    public CollectorsUI(AstroCollectors plugin, Player player, Location location) {
        CollectorManager collectorManager = CollectorManager.DELEGATE;
        collector = collectorManager.getCollector(location);
        FLocation fLocation = new FLocation(location);
        Faction faction = Board.getInstance().getFactionAt(fLocation);

        collectorUI = new Gui(5, ChatColor.DARK_GRAY + faction.getTag() + "'s Collector");

        BukkitTask runnable = Bukkit.getScheduler().runTaskTimer(plugin, this::updateItems, 20L, 20L);

        collectorUI.setCloseGuiAction(event -> runnable.cancel());

        collectorUI.setDefaultClickAction(event -> event.setCancelled(true));

        ItemStack glass = new com.cosmicpvp.cosmicutils.utils.ItemBuilder(new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 7)).name("").build();
        collectorUI.getFiller().fillBorder(new GuiItem(glass));

        collectorUI.setItem(3,3, ItemBuilder
                .from(Material.GOLD_NUGGET)
                .setName(ChatColor.GREEN + ChatColor.BOLD.toString() + "Mob Drops")
                .setLore(getMobDropsLore())
                .asGuiItem(event -> {
                    event.setCancelled(true);
                    collector.addMobDropSellLog(player, collector.getWorth());
                    collector.sellWorth(player);
                    updateItems();
                }));

        collectorUI.setItem(3,4, ItemBuilder
                .from(Material.TNT)
                .setName(ChatColor.GREEN + ChatColor.BOLD.toString() + "TNT Drops")
                .setLore(getTntLore())
                .asGuiItem(event -> {
                    event.setCancelled(true);
                    long amount = collector.getTnt();
                    if (amount == 0) return;
                    collector.claimTNT(player);
                    updateItems();
                }));

        collectorUI.setItem(3,6, ItemBuilder
                .from(Material.PAPER)
                .setName(ChatColor.GREEN + ChatColor.BOLD.toString() + "Sell Logs")
                .setLore(getSellLogLore())
                .asGuiItem(event -> event.setCancelled(true)));

        collectorUI.setItem(3,7, ItemBuilder
                .from(Material.MOB_SPAWNER)
                .setName(ChatColor.GOLD + ChatColor.BOLD.toString() + "Active Spawners")
                .setLore(getSpawnersLore())
                .asGuiItem(event -> event.setCancelled(true)));

    }

    public void updateItems() {

        collectorUI.updateItem(3, 3, ItemBuilder
                .from(Material.GOLD_NUGGET)
                .setName(ChatColor.GREEN + ChatColor.BOLD.toString() + "Mob Drops")
                .setLore(Utils.format(getMobDropsLore()))
                .asGuiItem().getItemStack());

        collectorUI.updateItem(3,4, ItemBuilder
                .from(Material.TNT)
                .setName(ChatColor.GREEN + ChatColor.BOLD.toString() + "TNT Drops")
                .setLore(Utils.format(getTntLore()))
                .asGuiItem().getItemStack());

        collectorUI.updateItem(3,6, ItemBuilder
                .from(Material.PAPER)
                .setName(ChatColor.GREEN + ChatColor.BOLD.toString() + "Sell Logs")
                .setLore(Utils.format(getSellLogLore()))
                .asGuiItem().getItemStack());

        collectorUI.update();
    }

    private List<String> getSpawnersLore() {
        List<String> lore = new ArrayList<>();
        double totalPerHour = 0;
        lore.add(CC.translate("&e&lSpawners:"));
        for (Map.Entry<EntityType, Integer> entry : collector.getSpawnersInChunk().entrySet()) {
            EntityType type = entry.getKey();
            int amount = entry.getValue();
            double value = SpawnerUtil.getMoneyPerMinute(type);
            lore.add(CC.translate("&e&l * &f" + amount + "x " + getEntityName(type) + " Spawners &8➥ &a$" + NumberUtils.formatMoney(value*60*amount) + "/hour"));
            totalPerHour += value * amount * 60;
        }
        lore.add(CC.translate("&e&lTotal: &a$" + NumberUtils.formatMoney(totalPerHour) + "/hour"));
        lore.add("");
        lore.add(CC.translate("&7The chunkcollector system allows spawners to"));
        lore.add(CC.translate("&7activate while the chunk is unloaded."));
        lore.add(CC.translate("&7The amount you make per hour increases depending"));
        lore.add(CC.translate("&7on how many and which spawners you have placed."));

        return lore;
    }

    private List<String> getSellLogLore() {
        List<CollectorSellLog> sellLogs = collector.getSellLogs();
        List<String> sellLogsLore = new ArrayList<>();
        if (!sellLogs.isEmpty()) {
            sellLogsLore.add("");
        }
        sellLogs.stream().limit(10).forEach(log -> {
            String sellTypeString = log.getSellLogType() == CollectorSellLog.SellLogType.TNT ? "Deposited &c" + Utils.formatNum((long) log.getWorth()) + "&7 TNT" : "Sold &a&2$&a" + Utils.formatNum(log.getWorth()) + "&7 Worth of drops";
            sellLogsLore.add(1, Utils.format("&7&o" + log.getSellerName() + "&r&7 " + sellTypeString + " &8(&7" + Utils.splitToComponentTimes((System.currentTimeMillis() - log.getSellDate()) / 1000) + "ago&8)"));
        });
        return sellLogsLore;
    }

    private List<String> getTntLore() {
        return Utils.format(Arrays.asList("",
                "&7Right-Click to deposit your TNT",
                "&7into your faction TNT bank",
                "",
                "&a&l * Amount: &f" + Utils.formatNum(collector.getTnt())));
    }

    private List<String> getMobDropsLore() {
        return Utils.format(Arrays.asList("",
                "&7Right-Click to withdraw the money",
                "&7to your own balance",
                "",
                "&a&l * Worth: &2$&f" + Utils.formatNum(collector.getWorth())));
    }

    public static String getEntityName(EntityType type) {
        if (type == EntityType.IRON_GOLEM) {
            return "Iron Golem";
        }
        if (type == EntityType.MUSHROOM_COW) {
            return "Mushroom Cow";
        }
        return type.getName();
    }

}