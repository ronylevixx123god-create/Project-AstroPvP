package net.astropvp.core.guis;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.rit.sucy.EnchantmentAPI;
import me.ifamasssxd.cosmiccoinflip.utils.SlotUtils;
import me.ifamasssxd.itemfilter.utils.ItemBuilder;
import me.ily2bb.slotbot.CosmicSlotBotAPI;
import me.ily2bb.slotbot.SlotBotPlugin;
import me.ily2bb.slotbot.listener.SlotBotInventoryListener;
import me.ily2bb.slotbot.managers.SlotBotManager;
import net.astropvp.core.AstroCore;
import net.astropvp.core.utils.GUIBuilder;
import org.arkhamnetwork.enchantments.EnchantmentBook;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.github.paperspigot.Title;

import java.util.Random;

public class SlotbotScratchCardGUI extends GUIBuilder {
    public int amountClicked = 0;
    ItemStack book = CosmicSlotBotAPI.createSlotBotItem();


    public SlotbotScratchCardGUI(Player player) {
        super(player);
    }

    @Override
    protected String getName() {
        return CC.translate("&8Slotbot ScratchCard");
    }

    @Override
    protected int getSlots() {
        return 9;
    }

    public int getAmountClicked() {
        return amountClicked;
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

    }

    @Override
    protected ItemStack getItems(int slot) {
        if (slot == 0) {
            return ItemBuilder.buildItem(Material.STAINED_GLASS_PANE, 1, (short) 14,
                    CC.translate("&c&lCase #1"), CC.translate("&7Click to scratch this case."));
        }
        if (slot == 1) {
            return ItemBuilder.buildItem(Material.STAINED_GLASS_PANE, 1, (short) 14,
                    CC.translate("&c&lCase #2"), CC.translate("&7Click to scratch this case."));
        }
        if (slot == 2) {
            return ItemBuilder.buildItem(Material.STAINED_GLASS_PANE, 1, (short) 14,
                    CC.translate("&c&lCase #3"), CC.translate("&7Click to scratch this case."));

        }
        if (slot == 3) {
            return ItemBuilder.buildItem(Material.STAINED_GLASS_PANE, 1, (short) 14,
                    CC.translate("&c&lCase #4"), CC.translate("&7Click to scratch this case."));

        }
        if (slot == 4) {
            return ItemBuilder.buildItem(Material.STAINED_GLASS_PANE, 1, (short) 14,
                    CC.translate("&c&lCase #5"), CC.translate("&7Click to scratch this case."));

        }
        if (slot == 5) {
            return ItemBuilder.buildItem(Material.STAINED_GLASS_PANE, 1, (short) 14,
                    CC.translate("&c&lCase #6"), CC.translate("&7Click to scratch this case."));

        }
        if (slot == 6) {
            return ItemBuilder.buildItem(Material.STAINED_GLASS_PANE, 1, (short) 14,
                    CC.translate("&c&lCase #7"), CC.translate("&7Click to scratch this case."));
        }
        if (slot == 7) {
            return ItemBuilder.buildItem(Material.STAINED_GLASS_PANE, 1, (short) 14,
                    CC.translate("&c&lCase #8"), CC.translate("&7Click to scratch this case."));

        }
        if (slot == 8) {
            return ItemBuilder.buildItem(Material.STAINED_GLASS_PANE, 1, (short) 14,
                    CC.translate("&c&lCase #9"), CC.translate("&7Click to scratch this case."));

        }
        return null;
    }

    @Override
    public void click(InventoryClickEvent event) {
        if (event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR) return;
        boolean won = false;
        switch (event.getSlot()) {
            case 0:
                if (amountClicked >= 1) {
                    event.setCancelled(true);
                    return;
                }
                amountClicked++;

                int number1 = 0;
                int random1 = generateRandomNumber();
                this.inventory.setItem(random1, book);
                player.playSound(player.getLocation(), Sound.NOTE_PLING, 10,10);

                if (random1 == number1) {
                    won = true;
                }
                break;
            case 1:
                if (amountClicked >= 1) {
                    event.setCancelled(true);
                    return;
                }
                amountClicked++;
                int number2 = 1;
                int random2 = generateRandomNumber();
                this.inventory.setItem(random2, book);
                player.playSound(player.getLocation(), Sound.NOTE_PLING, 10,10);


                if (random2 == number2) {
                    won = true;
                }
                //setItems();
                break;
            case 2:
                if (amountClicked >= 1) {
                    event.setCancelled(true);
                    return;
                }
                amountClicked++;
                int number3 = 2;
                int random3 = generateRandomNumber();
                this.inventory.setItem(random3, book);
                player.playSound(player.getLocation(), Sound.NOTE_PLING, 10,10);


                if (random3 == number3) {
                    won = true;
                }
                //setItems();
                break;
            case 3:
                if (amountClicked >= 1) {
                    event.setCancelled(true);
                    return;
                }
                amountClicked++;
                int number4 = 3;
                int random4 = generateRandomNumber();
                this.inventory.setItem(random4, book);
                player.playSound(player.getLocation(), Sound.NOTE_PLING, 10,10);


                if (random4 == number4) {
                    won = true;
                }
                //setItems();
                break;
            case 4:
                if (amountClicked >= 1) {
                    event.setCancelled(true);
                    return;
                }
                amountClicked++;
                int number5 = 4;
                int random5 = generateRandomNumber();
                this.inventory.setItem(random5, book);
                player.playSound(player.getLocation(), Sound.NOTE_PLING, 10,10);


                if (random5 == number5) {
                    won = true;
                }
                //setItems();
                break;
            case 5:
                if (amountClicked >= 1) {
                    event.setCancelled(true);
                    return;
                }
                amountClicked++;
                int number6 = 5;
                int random6 = generateRandomNumber();
                this.inventory.setItem(random6, book);
                player.playSound(player.getLocation(), Sound.NOTE_PLING, 10,10);


                if (random6 == number6) {
                    won = true;
                }
                //setItems();
                break;
            case 6:
                if (amountClicked >= 1) {
                    event.setCancelled(true);
                    return;
                }
                amountClicked++;
                int number7 = 6;
                int random7 = generateRandomNumber();
                this.inventory.setItem(random7, book);
                player.playSound(player.getLocation(), Sound.NOTE_PLING, 10,10);


                if (random7 == number7) {
                    won = true;
                }

                //setItems();
                break;
            case 7:
                if (amountClicked >= 1) {
                    event.setCancelled(true);
                    return;
                }
                amountClicked++;
                int number8 = 7;
                int random8 = generateRandomNumber();
                this.inventory.setItem(random8, book);
                player.playSound(player.getLocation(), Sound.NOTE_PLING, 10,10);


                if (random8 == number8) {
                    won = true;
                }
                //setItems();
                break;
            case 8:
                if (amountClicked >= 1) {
                    event.setCancelled(true);
                    return;
                }
                amountClicked++;
                int number9 = 8;
                int random9 = generateRandomNumber();
                this.inventory.setItem(random9, book);
                player.playSound(player.getLocation(), Sound.NOTE_PLING, 10,10);


                if (random9 == number9) {
                    won = true;
                }
                //setItems();
                break;
        }
        boolean finalWon = won;
        Bukkit.getScheduler().runTaskLaterAsynchronously(AstroCore.getInstance(), () -> {
            if (finalWon == true) {
                player.sendMessage("");
                player.sendMessage(CC.translate("&a&l(!) &aYou have Won! You have received the Reward!"));
                player.sendMessage("");
                player.sendTitle(new Title(CC.translate("&a&lYou won!"), CC.translate("&7You have received the reward."),10,20,10));
                player.getInventory().addItem(book);
                player.playSound(player.getLocation(), Sound.VILLAGER_YES, 10,10);

            } else {
                player.sendTitle(new Title(CC.translate("&c&lYou Lost!"), CC.translate("&7Better Luck Next Time."),10,20,10));
                player.sendMessage("");
                player.sendMessage(CC.translate("&c&l(!) &cYou have Lost!"));
                player.sendMessage("");
                player.playSound(player.getLocation(), Sound.ANVIL_BREAK, 10,10);
            }
            player.closeInventory();
        }, 25);


    }

    @Override
    public Inventory getInventory() {
        return this.inventory;
    }

    public int generateRandomNumber() {
        Random rand = new Random();
        return rand.nextInt((8 - 0) + 1) + 0;

    }

    public void cancelReward() {
        if (amountClicked >= 1) {
            return;
        }
        if (1 == generateRandomNumber()) {
            player.sendTitle(new Title(CC.translate("&a&lYou won!"), CC.translate("&7You have received the reward."),10,20,10));
            player.sendMessage("");
            player.sendMessage(CC.translate("&a&l(!) &aYou have Won! You have received the Reward!"));
            player.sendMessage("");
            player.getInventory().addItem(book);
            player.playSound(player.getLocation(), Sound.VILLAGER_YES, 10,10);

        } else {
            player.sendTitle(new Title(CC.translate("&c&lYou Lost!"), CC.translate("&7Better Luck Next Time."),10,20,10));
            player.sendMessage("");
            player.sendMessage(CC.translate("&c&l(!) &cYou have Lost!"));
            player.sendMessage("");
            player.playSound(player.getLocation(), Sound.ANVIL_BREAK, 10,10);

        }
        return;
    }

    public int generateRandomNumberOneToThree() {
        Random rand = new Random();
        return rand.nextInt((3 - 1) + 1) + 1;

    }

    public int generateRandomNumberOneToHundred() {
        Random rand = new Random();
        return rand.nextInt((100 - 1) + 1) + 1;

    }


}
