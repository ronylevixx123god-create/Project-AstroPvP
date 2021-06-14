package net.astropvp.core.guis;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.api.Economy;
import com.earth2me.essentials.api.NoLoanPermittedException;
import com.earth2me.essentials.api.UserDoesNotExistException;
import me.ifamasssxd.itemfilter.utils.ItemBuilder;
import net.astropvp.core.utils.GUIBuilder;
import net.astropvp.core.utils.SpawnerGUIBuilder;
import net.astropvp.core.utils.SpawnerShopUtil;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class SpawnerShopGUI
        extends SpawnerGUIBuilder {
    public SpawnerShopGUI(Player player, int amount) {
        super(player, amount);
    }

    private ArrayList<SpawnerShopUtil> spawners = new ArrayList<>();

    //TODO Add amount in the lorer



    @Override
    protected String getName() {
        return CC.translate("&8Spawner Shop (" + amount + ")");
    }

    @Override
    protected int getSlots() {
        return 9;
    }

    @Override
    protected void setItems() {
        spawners.add(new SpawnerShopUtil(amount, "blaze", 750000));
        spawners.add(new SpawnerShopUtil(amount, "iron_golem", 5000000));
        spawners.add(new SpawnerShopUtil(amount, "villager", 25000000));
        spawners.add(new SpawnerShopUtil(amount, "witch", 10000000));
        spawners.add(new SpawnerShopUtil(amount, "creeper", 390000));

        for (int i = 0; i < 9; i++) {
        }
        this.inventory.setItem(0, getItems(0));
        this.inventory.setItem(2, getItems(2));

        this.inventory.setItem(4, getItems(4));

        this.inventory.setItem(6, getItems(6));


        this.inventory.setItem(8, getItems(8));


    }

    @Override
    protected ItemStack getItems(int slot) {
        if (slot == 0) {
            return ItemBuilder.buildItem(Material.MOB_SPAWNER, 1, (short) 0,
                    CC.translate("&a&lBlaze Spawner"), new String[]{"", CC.translate("&a&l* &fAmount: &a" + amount)
                            , CC.translate("&a&l* &fPrice: &7$" + DecimalFormat.getInstance().format(getSpawnerFromName("blaze").getTotalPrice())), "", CC.translate("&7&nLeft-Click&7 to buy &f" + amount + " &7Blaze Spawners")});
        }
        if (slot == 2) {
            return ItemBuilder.buildItem(Material.MOB_SPAWNER, 1, (short) 0,
                    CC.translate("&a&lIron Golem Spawner"), new String[]{"", CC.translate("&a&l* &fAmount: &a" + amount)
                            , CC.translate("&a&l* &fPrice: &7$" + DecimalFormat.getInstance().format(getSpawnerFromName("iron_golem").getTotalPrice())), "", CC.translate("&7&nLeft-Click&7 to buy &f" + amount + " &7Iron Golem Spawners")});

        }

        if (slot == 4) {
            return ItemBuilder.buildItem(Material.MOB_SPAWNER, 1, (short) 0,
                    CC.translate("&a&lVillager Spawner"), new String[]{"", CC.translate("&a&l* &fAmount: &a" + amount)
                            , CC.translate("&a&l* &fPrice: &7$" + DecimalFormat.getInstance().format(getSpawnerFromName("villager").getTotalPrice())), "", CC.translate("&7&nLeft-Click&7 to buy &f" + amount + " &7Villager Spawners")});
        }

        if (slot == 6) {
            return ItemBuilder.buildItem(Material.MOB_SPAWNER, 1, (short) 0,
                    CC.translate("&a&lWitch Spawner"), new String[]{"", CC.translate("&a&l* &fAmount: &a" + amount)
                            , CC.translate("&a&l* &fPrice: &7$" + DecimalFormat.getInstance().format(getSpawnerFromName("witch").getTotalPrice())), "", CC.translate("&7&nLeft-Click&7 to buy &f" + amount + " &7Witch Spawners")});

        }

        if (slot == 8) {
            return ItemBuilder.buildItem(Material.MOB_SPAWNER, 1, (short) 0,
                    CC.translate("&a&lCreeper Spawner"), new String[]{"", CC.translate("&a&l* &fAmount: &a" + amount)
                            , CC.translate("&a&l* &fPrice: &7$" + DecimalFormat.getInstance().format(getSpawnerFromName("creeper").getTotalPrice())), "", CC.translate("&7&nLeft-Click&7 to buy &f" + amount + " &7Creeper Spawners")});
        }
        return null;
    }

    @Override
    public void click(InventoryClickEvent event) throws UserDoesNotExistException, NoLoanPermittedException {
        if (event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR) return;
        double balance = Economy.getMoney(player.getName());
        double price;
        int amount;
        switch (event.getSlot()) {
            case 0:
                price = getSpawnerFromName("blaze").getTotalPrice();
                amount = getSpawnerFromName("blaze").getAmount();
                if (balance < price) {
                    player.sendMessage(CC.translate("&c&l(!)&c You don't have enough money to purchase this spawner."));
                    return;
                } else {
                    Economy.subtract(player.getName(), price);
                    player.sendMessage(CC.translate("&c&l- $" + DecimalFormat.getInstance().format(price)));
                    Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "givespawner spawner " + player.getName() + " " + amount + " blaze");
                }
                setItems();
                break;
            case 2:
                price = getSpawnerFromName("iron_golem").getTotalPrice();
                amount = getSpawnerFromName("iron_golem").getAmount();
                if (balance < price) {
                    player.sendMessage(CC.translate("&c&l(!)&c You don't have enough money to purchase this spawner."));
                    return;
                } else {
                    Economy.subtract(player.getName(), price);
                    player.sendMessage(CC.translate("&c&l- $" + DecimalFormat.getInstance().format(price)));
                    Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "givespawner spawner " + player.getName() + " " + amount + " iron_golem");
                }
                setItems();
                break;
            case 4:
                price = getSpawnerFromName("villager").getTotalPrice();
                amount = getSpawnerFromName("villager").getAmount();
                if (balance < price) {
                    player.sendMessage(CC.translate("&c&l(!)&c You don't have enough money to purchase this spawner."));
                    return;
                } else {
                    Economy.subtract(player.getName(), price);
                    player.sendMessage(CC.translate("&c&l- $" + DecimalFormat.getInstance().format(price)));
                    Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "givespawner spawner " + player.getName() + " " + amount + " villager");
                }
                setItems();
                break;
            case 6:
                price = getSpawnerFromName("witch").getTotalPrice();
                amount = getSpawnerFromName("witch").getAmount();
                if (balance < price) {
                    player.sendMessage(CC.translate("&c&l(!)&c You don't have enough money to purchase this spawner."));
                    return;
                } else {
                    Economy.subtract(player.getName(), price);
                    player.sendMessage(CC.translate("&c&l- $" + DecimalFormat.getInstance().format(price)));
                    Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "givespawner spawner " + player.getName() + " " + amount + " witch");
                }
                setItems();
                break;
            case 8:
                price = getSpawnerFromName("creeper").getTotalPrice();
                amount = getSpawnerFromName("creeper").getAmount();
                if (balance < price) {
                    player.sendMessage(CC.translate("&c&l(!)&c You don't have enough money to purchase this spawner."));
                    return;
                } else {
                    Economy.subtract(player.getName(), price);
                    player.sendMessage(CC.translate("&c&l- $" + DecimalFormat.getInstance().format(price)));
                    Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "givespawner spawner " + player.getName() + " " + amount + " creeper");
                }
                setItems();
                break;
        }
    }

    public SpawnerShopUtil getSpawnerFromName(String name) {
        for (SpawnerShopUtil spawner : spawners) {
            if (spawner.getMobName() == name) {
                return spawner;
            }
        }
        return null;
    }


    @Override
    public Inventory getInventory() {
        return this.inventory;
    }


}
