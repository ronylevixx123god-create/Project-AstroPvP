package me.bryside.astrorevive.commands;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import me.bryside.astrorevive.AstroRevivePlugin;
import me.bryside.astrorevive.inventories.DeathInventory;
import me.bryside.astrorevive.inventories.ScrollerInventory;
import me.bryside.astrorevive.Utils.SortMapByValue;
import me.bryside.astrorevive.Utils.TimeUtil;
import me.bryside.astrorevive.Utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;


public class ReviveCommand
        implements CommandExecutor, Listener {
    AstroRevivePlugin inst = null;

    public ReviveCommand(AstroRevivePlugin inst) {
        this.inst = inst;
    }


    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerDeath(PlayerDeathEvent event) {
        DeathInventory inv = new DeathInventory(this.inst, event.getEntity(), getKillerName(event), event.getEntity().getLastDamage() + "");
    }

    public String getKillerName(PlayerDeathEvent event) {
        try {
            return event.getEntity().getKiller().getName();
        } catch (Exception e) {
            return "N/A";
        }
    }


    public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
        if (!cmd.getName().equalsIgnoreCase("revive")) {
            return false;
        }
        if (!sender.hasPermission("prorevive.revive")) {
            sender.sendMessage("§c[!] You don't has access to that command.");
            return true;
        }

        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Error, you are not a player!");
            return true;
        }

        if (args.length == 0) {

            sender.sendMessage("§c§lUSAGE: §c/revive <player>");
            sender.sendMessage("§7Revive a player with their most recent death inventory.");
            return true;
        }

        Player player = (Player) sender;
        Player target = Bukkit.getPlayer(args[0]);

        if (args[0].equalsIgnoreCase("total")) {
            int time = (int) (this.inst.getDeathInventories().size() * 1.3D);
            sender.sendMessage("§c§l(!)§c Total inventories §f" + this.inst.getDeathInventories().size() + " §cTime to save (aprox) §f" +
                    TimeUtil.formatTime(time));
            return true;
        }

        if (args[0].equalsIgnoreCase("box")) {

            ArrayList<ItemStack> items = new ArrayList<>();
            this.inst.getDeathInventories()
                    .forEach(di -> items.add(Util.createItem("§6§lDeath of§7 " + di.getPlayer(), Material.CHEST, new String[]{"§cID§f #" + di.getID()})));


            new ScrollerInventory(items, "Revive Box", player);
            return true;
        }

        if (target == null) {
            player.sendMessage("§c[!] Player " + args[0] + " is not online!");
            return true;
        }

        if (getInventories(target.getUniqueId()).isEmpty()) {
            player.sendMessage("§c§l(!)§c No death inventories found for player " + target.getName() + ".");
            return true;
        }

        deathInventory(player, target, 0);
        return true;
    }

    public void deathInventory(Player player, Player target, int page) {
        Inventory inventory = Bukkit.createInventory(null, 54, "§cRevive: " + target.getName());
        DeathInventory deathInventory = getInventories(target.getUniqueId()).get(page);
        ItemStack information = Util.createItem("§6Information", 1, Material.STAINED_GLASS_PANE, "§6Time of death: §f" +
                date(deathInventory.getTime()), "§f (" +
                TimeUtil.formatTime(System.currentTimeMillis() - deathInventory.getTime().longValue()) + " ago )", "§6Total Items: §f" + deathInventory
                .getTotal(), "§6Total Inventories: §f" +
                getInventories(target.getUniqueId()).size(), "§6Page:§f " + (page + 1), "§6Killer:§f " + deathInventory
                .getKiller(), "§6Damage:§f " + deathInventory.getDamage());
        ItemStack next = Util.createItem("§bNext Inventory", 7, Material.CARPET, "§7§m--------------------------------", "§bClick to go to the next inventory.", "§7§m--------------------------------");

        ItemStack back = Util.createItem("§bPrevious Inventory", 7, Material.CARPET, "§7§m--------------------------------", "§bClick to go to the previous inventory.", "§7§m--------------------------------");


        ItemStack revive = Util.createItem("§aRevive", 5, Material.STAINED_GLASS_PANE, "§7§m--------------------------------", "§aClick here to revive §f" + target
                .getName(), "§7§m--------------------------------");

        ItemStack remove = Util.createItem("§cDelete Inventory", 14, Material.STAINED_GLASS_PANE, "§7§m--------------------------------", "§cClick here to delete this inventory.", "§7§m--------------------------------");


        for (ItemStack item : deathInventory.getArmor()) {
            if (item != null)
                inventory.addItem(new ItemStack[]{item});
        }
        for (ItemStack item : deathInventory.getItems()) {
            if (item != null)
                inventory.addItem(new ItemStack[]{item});
        }
        inventory.setItem(53, next);
        inventory.setItem(45, back);
        inventory.setItem(50, revive);
        inventory.setItem(48, remove);
        inventory.setItem(49, information);
        player.openInventory(inventory);
    }

    public String date(Long dong) {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");
        Date resultdate = new Date(dong.longValue());
        return sdf.format(resultdate);
    }

    public ArrayList<DeathInventory> getInventories(UUID player) {
        ArrayList<DeathInventory> result = new ArrayList<>();
        HashMap<DeathInventory, Long> invs = new HashMap<>();
        for (DeathInventory inv : this.inst.getDeathInventories()) {
            if (inv.getPlayer().equals(player) && !inv.isRevived())
                result.add(inv);
        }
        for (DeathInventory di : result)
            invs.put(di, di.getTime());
        Map<DeathInventory, Long> sortedMapAsc = SortMapByValue.sortByComparator(invs, true);
        result.clear();
        result.addAll(sortedMapAsc.keySet());
        Collections.reverse(result);
        return result;
    }


    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (event.getInventory().getTitle().startsWith("§cRevive: ")) {
            event.setCancelled(true);

            if (event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR ||
                    !event.getCurrentItem().getItemMeta().hasDisplayName()) {
                return;
            }
            Player target = Bukkit.getPlayer(event.getInventory().getTitle().split(" ")[1]);
            if (target == null) {
                player.sendMessage("§cThat player has logged out.");
                player.closeInventory();

                return;
            }
            ArrayList<DeathInventory> inventories = getInventories(target.getUniqueId());

            int currentPage = Integer.parseInt(((String) event.getInventory().getItem(49).getItemMeta().getLore().get(4)).split(" ")[1]) - 1;

            if (event.getCurrentItem().getItemMeta().getDisplayName().startsWith("§bNext Inventory")) {

                if (currentPage == inventories.size() - 1) {
                    player.sendMessage("§c§l(!)§c There is no next page available.");
                    return;
                }
                deathInventory(player, target, currentPage + 1);

                return;
            }
            if (event.getCurrentItem().getItemMeta().getDisplayName().startsWith("§bPrevious Inventory")) {
                if (currentPage == 0) {
                    player.sendMessage("§c§l(!)§c There is no previous page avalible.");
                    return;
                }
                deathInventory(player, target, currentPage - 1);

                return;
            }
            if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§aRevive")) {
                DeathInventory deathInventory = getInventories(target.getUniqueId()).get(currentPage);

                deathInventory.revive(player);
                player.sendMessage("§eThe player §f" + target.getName() + "§e has been revived.");


                player.closeInventory();
                return;
            }
        }
    }


}
