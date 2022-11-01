package net.astropvp.core.guis;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.earth2me.essentials.api.Economy;
import com.earth2me.essentials.api.NoLoanPermittedException;
import com.earth2me.essentials.api.UserDoesNotExistException;
import me.ifamasssxd.cosmicguishop.utils.ItemBuilder;
import net.astropvp.core.utils.SpawnerGUIBuilder;
import net.astropvp.core.utils.SpawnerShopUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class SpawnerShopGUI
        extends SpawnerGUIBuilder {
    public SpawnerShopGUI(Player player, int amount) {
        super(player, amount);
    }

    private final ArrayList<SpawnerShopUtil> spawners = new ArrayList<>();

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
        spawners.add(new SpawnerShopUtil(amount, "creeper", 390000));

        spawners.add(new SpawnerShopUtil(amount, "pig_zombie", 450000));
        spawners.add(new SpawnerShopUtil(amount, "zombie", 115000));
        spawners.add(new SpawnerShopUtil(amount, "skeleton", 115000));

        spawners.add(new SpawnerShopUtil(amount, "spider", 95000));
        spawners.add(new SpawnerShopUtil(amount, "cave_spider", 90000));

        this.inventory.setItem(0, getItems(0));
        this.inventory.setItem(1, getItems(1));
        this.inventory.setItem(2, getItems(2));

        this.inventory.setItem(3, getItems(3));
        this.inventory.setItem(4, getItems(4));
        this.inventory.setItem(5, getItems(5));
        this.inventory.setItem(6, getItems(6));
        this.inventory.setItem(7, getItems(7));

    }

    @Override
    protected ItemStack getItems(int slot) {
        if (slot == 0) {
            return ItemBuilder.buildItem(Material.MOB_SPAWNER, 1, (short) 0,
                    CC.translate("&a&lBlaze Spawner"), "", CC.translate("&a&l* &fAmount: &a" + amount)
                    , CC.translate("&a&l* &fPrice: &7$" + DecimalFormat.getInstance().format(getSpawnerFromName("blaze").getTotalPrice())), "", CC.translate("&7&nLeft-Click&7 to buy &f" + amount + " &7Blaze Spawners"));
        }
        if (slot == 1) {
            return ItemBuilder.buildItem(Material.MOB_SPAWNER, 1, (short) 0,
                    CC.translate("&a&lIron Golem Spawner"), "", CC.translate("&a&l* &fAmount: &a" + amount)
                    , CC.translate("&a&l* &fPrice: &7$" + DecimalFormat.getInstance().format(getSpawnerFromName("iron_golem").getTotalPrice())), "", CC.translate("&7&nLeft-Click&7 to buy &f" + amount + " &7Iron Golem Spawners"));

        }

        if (slot == 2) {
            return ItemBuilder.buildItem(Material.MOB_SPAWNER, 1, (short) 0,
                    CC.translate("&a&lCreeper Spawner"), "", CC.translate("&a&l* &fAmount: &a" + amount)
                    , CC.translate("&a&l* &fPrice: &7$" + DecimalFormat.getInstance().format(getSpawnerFromName("creeper").getTotalPrice())), "", CC.translate("&7&nLeft-Click&7 to buy &f" + amount + " &7Creeper Spawners"));
        }

        if (slot == 3) {
            return ItemBuilder.buildItem(Material.MOB_SPAWNER, 1, (short) 0,
                    CC.translate("&a&lPigman Zombie"), "", CC.translate("&a&l* &fAmount: &a" + amount)
                    , CC.translate("&a&l* &fPrice: &7$" + DecimalFormat.getInstance().format(getSpawnerFromName("pig_zombie").getTotalPrice())), "", CC.translate("&7&nLeft-Click&7 to buy &f" + amount + " &7Pigman Zombie Spawners"));
        }

        if (slot == 4) {
            return ItemBuilder.buildItem(Material.MOB_SPAWNER, 1, (short) 0,
                    CC.translate("&a&lZombie Spawner"), "", CC.translate("&a&l* &fAmount: &a" + amount)
                    , CC.translate("&a&l* &fPrice: &7$" + DecimalFormat.getInstance().format(getSpawnerFromName("zombie").getTotalPrice())), "", CC.translate("&7&nLeft-Click&7 to buy &f" + amount + " &7Zombie Spawners"));
        }

        if (slot == 5) {
            return ItemBuilder.buildItem(Material.MOB_SPAWNER, 1, (short) 0,
                    CC.translate("&a&lSkeleton Spawner"), "", CC.translate("&a&l* &fAmount: &a" + amount)
                    , CC.translate("&a&l* &fPrice: &7$" + DecimalFormat.getInstance().format(getSpawnerFromName("skeleton").getTotalPrice())), "", CC.translate("&7&nLeft-Click&7 to buy &f" + amount + " &7Skeleton Spawners"));
        }

        if (slot == 6) {
            return ItemBuilder.buildItem(Material.MOB_SPAWNER, 1, (short) 0,
                    CC.translate("&a&lSpider Spawner"), "", CC.translate("&a&l* &fAmount: &a" + amount)
                    , CC.translate("&a&l* &fPrice: &7$" + DecimalFormat.getInstance().format(getSpawnerFromName("spider").getTotalPrice())), "", CC.translate("&7&nLeft-Click&7 to buy &f" + amount + " &7Spider Spawners"));
        }

        if (slot == 7) {
            return ItemBuilder.buildItem(Material.MOB_SPAWNER, 1, (short) 0,
                    CC.translate("&a&lCave Spider Spawner"), "", CC.translate("&a&l* &fAmount: &a" + amount)
                    , CC.translate("&a&l* &fPrice: &7$" + DecimalFormat.getInstance().format(getSpawnerFromName("cave_spider").getTotalPrice())), "", CC.translate("&7&nLeft-Click&7 to buy &f" + amount + " &7Cave SpiderSpawners"));
        }
        return null;
    }

    @Override
    public void click(InventoryClickEvent event) throws UserDoesNotExistException, NoLoanPermittedException {
        if (event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR) return;
        double balance = Economy.getMoney(player.getName());
        double price;
        int amount;
        if (player.getInventory().firstEmpty() == -1) {
            player.sendMessage(CC.translate("&c&l(!)&c You cannot buy this with a full inventory"));
            return;
        }
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
            case 1:
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
            case 2:
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
            case 3:
                price = getSpawnerFromName("pig_zombie").getTotalPrice();
                amount = getSpawnerFromName("pig_zombie").getAmount();
                if (balance < price) {
                    player.sendMessage(CC.translate("&c&l(!)&c You don't have enough money to purchase this spawner."));
                    return;
                } else {
                    Economy.subtract(player.getName(), price);
                    player.sendMessage(CC.translate("&c&l- $" + DecimalFormat.getInstance().format(price)));
                    Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "givespawner spawner " + player.getName() + " " + amount + " pig_zombie");
                }
                setItems();
                break;
            case 4:

                price = getSpawnerFromName("zombie").getTotalPrice();
                amount = getSpawnerFromName("zombie").getAmount();
                if (balance < price) {
                    player.sendMessage(CC.translate("&c&l(!)&c You don't have enough money to purchase this spawner."));
                    return;
                } else {
                    Economy.subtract(player.getName(), price);
                    player.sendMessage(CC.translate("&c&l- $" + DecimalFormat.getInstance().format(price)));
                    Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "givespawner spawner " + player.getName() + " " + amount + " zombie");
                }
                setItems();
                break;
            case 5:

                price = getSpawnerFromName("skeleton").getTotalPrice();
                amount = getSpawnerFromName("skeleton").getAmount();
                if (balance < price) {
                    player.sendMessage(CC.translate("&c&l(!)&c You don't have enough money to purchase this spawner."));
                    return;
                } else {
                    Economy.subtract(player.getName(), price);
                    player.sendMessage(CC.translate("&c&l- $" + DecimalFormat.getInstance().format(price)));
                    Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "givespawner spawner " + player.getName() + " " + amount + " skeleton");
                }
                setItems();
                break;
            case 6:
                price = getSpawnerFromName("spider").getTotalPrice();
                amount = getSpawnerFromName("spider").getAmount();
                if (balance < price) {
                    player.sendMessage(CC.translate("&c&l(!)&c You don't have enough money to purchase this spawner."));
                    return;
                } else {
                    Economy.subtract(player.getName(), price);
                    player.sendMessage(CC.translate("&c&l- $" + DecimalFormat.getInstance().format(price)));
                    Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "givespawner spawner " + player.getName() + " " + amount + " spider");
                }
                setItems();
                break;
            case 7:
                price = getSpawnerFromName("cave_spider").getTotalPrice();
                amount = getSpawnerFromName("cave_spider").getAmount();
                if (balance < price) {
                    player.sendMessage(CC.translate("&c&l(!)&c You don't have enough money to purchase this spawner."));
                    return;
                } else {
                    Economy.subtract(player.getName(), price);
                    player.sendMessage(CC.translate("&c&l- $" + DecimalFormat.getInstance().format(price)));
                    Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "givespawner spawner " + player.getName() + " " + amount + " cave_spider");
                }
                setItems();
                break;
        }
    }

    public SpawnerShopUtil getSpawnerFromName(String name) {
        for (SpawnerShopUtil spawner : spawners) {
            if (spawner.getMobName().equals(name)) {
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
