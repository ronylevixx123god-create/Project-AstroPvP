package net.astropvp.core.guis;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.api.Economy;
import com.earth2me.essentials.api.NoLoanPermittedException;
import com.earth2me.essentials.api.UserDoesNotExistException;
import me.ifamasssxd.itemfilter.utils.ItemBuilder;
import net.astropvp.core.utils.GUIBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.math.BigDecimal;

public class SpawnerShopGUI
        extends GUIBuilder {
    public SpawnerShopGUI(Player player) {
        super(player);
    }

    @Override
    protected String getName() {
        return CC.translate("&8Spawner Shop");
    }

    @Override
    protected int getSlots() {
        return 18;
    }

    @Override
    protected void setItems() {
        for (int i = 0; i < 9; i++) {
        }
        this.inventory.setItem(0, getItems(0));
        this.inventory.setItem(1, getItems(1));
        this.inventory.setItem(2, getItems(2));
        this.inventory.setItem(3, getItems(3));

        this.inventory.setItem(4, getItems(4));
        this.inventory.setItem(5, getItems(5));
        this.inventory.setItem(6, getItems(6));
        this.inventory.setItem(7, getItems(7));
        this.inventory.setItem(8, getItems(8));
        this.inventory.setItem(9, getItems(9));

        this.inventory.setItem(10, getItems(10));

        this.inventory.setItem(11, getItems(11));

        this.inventory.setItem(12, getItems(12));
        this.inventory.setItem(13, getItems(13));
        this.inventory.setItem(14, getItems(14));

    }

    @Override
    protected ItemStack getItems(int slot) {
        if (slot == 0) {
            return ItemBuilder.buildItem(Material.MOB_SPAWNER, 16, (short) 0,
                    CC.translate("&a&lBlaze Spawner"), CC.translate("&a&l* &fPrice: &7$12,000,000"));
        }
        if (slot == 1) {
            return ItemBuilder.buildItem(Material.MOB_SPAWNER, 32, (short) 0,
                    CC.translate("&a&lBlaze Spawner"), CC.translate("&a&l* &fPrice: &7$24,000,000"));
        }
        if (slot == 2) {
            return ItemBuilder.buildItem(Material.MOB_SPAWNER, 64, (short) 0,
                    CC.translate("&a&lBlaze Spawner"), CC.translate("&a&l* &fPrice: &7$48,000,000"));

        }
        if (slot == 3) {
            return ItemBuilder.buildItem(Material.MOB_SPAWNER, 16, (short) 0,
                    CC.translate("&a&lIron Golem Spawner"), CC.translate("&a&l* &fPrice: &7$80,000,000"));

        }
        if (slot == 4) {
            return ItemBuilder.buildItem(Material.MOB_SPAWNER, 32, (short) 0,
                    CC.translate("&a&lIron Golem Spawner"), CC.translate("&a&l* &fPrice: &7$160,000,000"));

        }
        if (slot == 5) {
            return ItemBuilder.buildItem(Material.MOB_SPAWNER, 64, (short) 0,
                    CC.translate("&a&lIron Golem Spawner"), CC.translate("&a&l* &fPrice: &7$320,000,000"));

        }
        if (slot == 6) {
            return ItemBuilder.buildItem(Material.MOB_SPAWNER, 16, (short) 0,
                    CC.translate("&a&lVillager Spawner"), CC.translate("&a&l* &fPrice: &7$400,000,000"));
        }
            if (slot == 7) {
                return ItemBuilder.buildItem(Material.MOB_SPAWNER, 32, (short) 0,
                        CC.translate("&a&lVillager Spawner"), CC.translate("&a&l* &fPrice: &7$800,000,000"));

            }
        if (slot == 8) {
            return ItemBuilder.buildItem(Material.MOB_SPAWNER, 64, (short) 0,
                    CC.translate("&a&lVillager Spawner"), CC.translate("&a&l* &fPrice: &7$1,600,000,000"));

        }
        if (slot == 9) {
            return ItemBuilder.buildItem(Material.MOB_SPAWNER, 16, (short) 0,
                    CC.translate("&a&lWitch Spawner"), CC.translate("&a&l* &fPrice: &7$160,000,000"));

        }
        if (slot == 10) {
            return ItemBuilder.buildItem(Material.MOB_SPAWNER, 32, (short) 0,
                    CC.translate("&a&lWitch Spawner"), CC.translate("&a&l* &fPrice: &7$320,000,000"));

        }
        if (slot == 11) {
            return ItemBuilder.buildItem(Material.MOB_SPAWNER, 64, (short) 0,
                    CC.translate("&a&lWitch Spawner"), CC.translate("&a&l* &fPrice: &7$640,000,000"));

        }
        if (slot == 12) {
            return ItemBuilder.buildItem(Material.MOB_SPAWNER, 16, (short) 0,
                    CC.translate("&a&lCreeper Spawner"), CC.translate("&a&l* &fPrice: &7$6,240,000"));
        }
        if (slot == 13) {
            return ItemBuilder.buildItem(Material.MOB_SPAWNER, 32, (short) 0,
                    CC.translate("&a&lCreeper Spawner"), CC.translate("&a&l* &fPrice: &7$12,480,000"));
        }
        if (slot == 14) {
            return ItemBuilder.buildItem(Material.MOB_SPAWNER, 64, (short) 0,
                    CC.translate("&a&lCreeper Spawner"), CC.translate("&a&l* &fPrice: &7$24,960,000"));
        }
        return null;
    }

    @Override
    public void click(InventoryClickEvent event) throws UserDoesNotExistException, NoLoanPermittedException {
        if (event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR) return;
        double balance = Economy.getMoney(player.getName());
        int price;
        int amount;
        switch (event.getSlot()) {
            case 0:
                price = 12000000;
                amount = 16;
                if (balance<price) {
                    player.sendMessage(CC.translate("&c&l(!)&c You don't have enough money to purchase this spawner."));
                    return;
                } else {
                    Economy.subtract(player.getName(), price);
                    player.sendMessage(CC.translate("&c&l- $" + price));
                    Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "givespawner spawner " + player.getName() + " " + amount + " blaze");
                }
                setItems();
                break;
            case 1:
                price = 24000000;
                amount = 32;
                if (balance<price) {
                    player.sendMessage(CC.translate("&c&l(!)&c You don't have enough money to purchase this spawner."));
                    return;
                } else {
                    Economy.subtract(player.getName(), price);
                    player.sendMessage(CC.translate("&c&l- $" + price));
                    Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "givespawner spawner " + player.getName() + " " + amount + " blaze");
                }
                setItems();
                break;
            case 2:
                price = 48000000;
                amount = 64;
                if (balance<price) {
                    player.sendMessage(CC.translate("&c&l(!)&c You don't have enough money to purchase this spawner."));
                    return;
                } else {
                    Economy.subtract(player.getName(), price);
                    player.sendMessage(CC.translate("&c&l- $" + price));
                    Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "givespawner spawner " + player.getName() + " " + amount + " blaze");
                }
                setItems();
                break;
            case 3:
                price = 80000000;
                amount = 16;
                if (balance<price) {
                    player.sendMessage(CC.translate("&c&l(!)&c You don't have enough money to purchase this spawner."));
                    return;
                } else {
                    Economy.subtract(player.getName(), price);
                    player.sendMessage(CC.translate("&c&l- $" + price));
                    Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "givespawner spawner " + player.getName() + " " + amount + " iron_golem");
                }
                setItems();
                break;
            case 4:
                price = 160000000;
                amount = 32;
                if (balance<price) {
                    player.sendMessage(CC.translate("&c&l(!)&c You don't have enough money to purchase this spawner."));
                    return;
                } else {
                    Economy.subtract(player.getName(), price);
                    player.sendMessage(CC.translate("&c&l- $" + price));
                    Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "givespawner spawner " + player.getName() + " " + amount + " iron_golem");
                }
                setItems();
                break;
            case 5:
                price = 320000000;
                amount = 64;
                if (balance<price) {
                    player.sendMessage(CC.translate("&c&l(!)&c You don't have enough money to purchase this spawner."));
                    return;
                } else {
                    Economy.subtract(player.getName(), price);
                    player.sendMessage(CC.translate("&c&l- $" + price));
                    Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "givespawner spawner " + player.getName() + " " + amount + " iron_golem");
                }
                setItems();
                break;
            case 6:
                price = 400000000;
                amount = 16;
                if (balance<price) {
                    player.sendMessage(CC.translate("&c&l(!)&c You don't have enough money to purchase this spawner."));
                    return;
                } else {
                    Economy.subtract(player.getName(), price);
                    player.sendMessage(CC.translate("&c&l- $" + price));
                    Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "givespawner spawner " + player.getName() + " " + amount + " villager");
                }
                setItems();
                break;
            case 7:
                price = 800000000;
                amount = 32;
                if (balance<price) {
                    player.sendMessage(CC.translate("&c&l(!)&c You don't have enough money to purchase this spawner."));
                    return;
                } else {
                    Economy.subtract(player.getName(), price);
                    player.sendMessage(CC.translate("&c&l- $" + price));
                    Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "givespawner spawner " + player.getName() + " " + amount + " villager");
                }
                break;
            case 8:
                price = 1600000000;
                amount = 64;
                if (balance<price) {
                    player.sendMessage(CC.translate("&c&l(!)&c You don't have enough money to purchase this spawner."));
                    return;
                } else {
                    Economy.subtract(player.getName(), price);
                    player.sendMessage(CC.translate("&c&l- $" + price));
                    Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "givespawner spawner " + player.getName() + " " + amount + " villager");
                }
                setItems();
                break;
            case 9:
                price = 160000000;
                amount = 16;
                if (balance<price) {
                    player.sendMessage(CC.translate("&c&l(!)&c You don't have enough money to purchase this spawner."));
                    return;
                } else {
                    Economy.subtract(player.getName(), price);
                    player.sendMessage(CC.translate("&c&l- $" + price));
                    Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "givespawner spawner " + player.getName() + " " + amount + " witch");
                }
                setItems();
                break;
            case 10:
                price = 320000000;
                amount = 32;
                if (balance<price) {
                    player.sendMessage(CC.translate("&c&l(!)&c You don't have enough money to purchase this spawner."));
                    return;
                } else {
                    Economy.subtract(player.getName(), price);
                    player.sendMessage(CC.translate("&c&l- $" + price));
                    Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "givespawner spawner " + player.getName() + " " + amount + " witch");
                }
                setItems();
                break;
            case 11:
                price = 640000000;
                amount = 64;
                if (balance<price) {
                    player.sendMessage(CC.translate("&c&l(!)&c You don't have enough money to purchase this spawner."));
                    return;
                } else {
                    Economy.subtract(player.getName(), price);
                    player.sendMessage(CC.translate("&c&l- $" + price));
                    Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "givespawner spawner " + player.getName() + " " + amount + " witch");
                }
                setItems();
                break;
            case 12:
                price = 6240000;
                amount = 16;
                if (balance<price) {
                    player.sendMessage(CC.translate("&c&l(!)&c You don't have enough money to purchase this spawner."));
                    return;
                } else {
                    Economy.subtract(player.getName(), price);
                    player.sendMessage(CC.translate("&c&l- $" + price));
                    Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "givespawner spawner " + player.getName() + " " + amount + " creeper");
                }
                setItems();
                break;
            case 13:
                price = 12480000;
                amount = 32;
                if (balance<price) {
                    player.sendMessage(CC.translate("&c&l(!)&c You don't have enough money to purchase this spawner."));
                    return;
                } else {
                    Economy.subtract(player.getName(), price);
                    player.sendMessage(CC.translate("&c&l- $" + price));
                    Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "givespawner spawner " + player.getName() + " " + amount + " creeper");
                }
                setItems();
                break;
            case 14:
                price = 24960000;
                amount = 64;
                if (balance<price) {
                    player.sendMessage(CC.translate("&c&l(!)&c You don't have enough money to purchase this spawner."));
                    return;
                } else {
                    Economy.subtract(player.getName(), price);
                    player.sendMessage(CC.translate("&c&l- $" + price));
                    Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "givespawner spawner " + player.getName() + " " + amount + " creeper");
                }
                setItems();
                break;
        }
    }

    @Override
    public Inventory getInventory() {
        return this.inventory;
    }


}
