/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.customevents.CustomCosmicPlayerEvent
 *  com.cosmicpvp.cosmicutils.nbt.NBTTagCompoundBuilder
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.cosmicpvp.cosmicutils.utils.NBTWrapper
 *  com.google.common.collect.Lists
 *  de.dustplanet.silkspawners.SilkSpawners
 *  de.dustplanet.util.SilkUtil
 *  net.minecraft.server.v1_8_R3.NBTBase
 *  org.apache.commons.lang.StringUtils
 *  org.arkhamnetwork.Arkkit.patches.ItemID
 *  org.arkhamnetwork.Arkkit.patches.MysteryMobSpawners
 *  org.arkhamnetwork.Arkkit.plugins.ArkPlugins
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.OfflinePlayer
 *  org.bukkit.Sound
 *  org.bukkit.command.CommandSender
 *  org.bukkit.configuration.ConfigurationSection
 *  org.bukkit.configuration.file.YamlConfiguration
 *  org.bukkit.entity.EntityType
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event
 *  org.bukkit.event.inventory.ClickType
 *  org.bukkit.event.inventory.InventoryClickEvent
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.ItemMeta
 */
package me.ifamasssxd.cosmicguishop.struct;

import com.cosmicpvp.cosmicutils.customevents.CustomCosmicPlayerEvent;
import com.cosmicpvp.cosmicutils.nbt.NBTTagCompoundBuilder;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import com.google.common.collect.Lists;
import de.dustplanet.silkspawners.SilkSpawners;
import de.dustplanet.util.SilkUtil;
import me.ifamasssxd.cosmicguishop.CosmicGUIShop;
import me.ifamasssxd.cosmicguishop.menus.ClickableItemStack;
import me.ifamasssxd.cosmicguishop.menus.ConfirmPurchaseMenu;
import me.ifamasssxd.cosmicguishop.menus.GUIItemCallback;
import me.ifamasssxd.cosmicguishop.menus.GUIMenu;
import me.ifamasssxd.cosmicguishop.utils.ItemBuilder;
import me.ifamasssxd.cosmicguishop.utils.ItemUtils;
import me.ifamasssxd.cosmicguishop.utils.SlotUtils;
import net.minecraft.server.v1_8_R3.NBTBase;
import org.apache.commons.lang.StringUtils;
import org.arkhamnetwork.Arkkit.patches.ItemID;
import org.arkhamnetwork.Arkkit.patches.MysteryMobSpawners;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugins;
import org.bukkit.*;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShopManager {
    private static final String SHOP_NAME = "Shop Items";
    public static DecimalFormat moneyFormat = new DecimalFormat("#,###.##");
    int loreThreshold = 1;
    private Map<ShopCategory, ShopItemMap> shopItems = new HashMap<ShopCategory, ShopItemMap>();
    private File priceFile;
    private YamlConfiguration priceConfig;
    private SilkUtil silkSpawners;

    private static ItemStack getMobSpawner(EntityType entityType) {
        Bukkit.getLogger().info("TYPE: " + (Object) entityType + " ID: " + entityType.getTypeId());
        ItemStack item = ItemBuilder.buildItem(Material.MOB_SPAWNER, 1, entityType.getTypeId(), ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + StringUtils.capitaliseAllWords((String) entityType.name().replace("_", " ").toLowerCase()) + ChatColor.GOLD.toString() + " Spawner", (Object) ChatColor.GRAY + "Be careful where you place this!");
        item.setDurability(entityType.getTypeId());
        return item;
    }

    public void loadAllShopItems() throws NoSuchFieldException, IllegalAccessException, IOException {
        if (this.silkSpawners == null) {
            SilkSpawners spawners = (SilkSpawners) Bukkit.getPluginManager().getPlugin("SilkSpawners");
            Field field = spawners.getClass().getDeclaredField("su");
            field.setAccessible(true);
            this.silkSpawners = (SilkUtil) field.get((Object) spawners);
            Bukkit.getLogger().info("Loaded SilkSpawner Utilities...");
        }
        this.shopItems.clear();
        if (!CosmicGUIShop.get().getDataFolder().exists()) {
            CosmicGUIShop.get().getDataFolder().mkdir();
        }
        this.priceFile = new File(CosmicGUIShop.get().getDataFolder(), "prices.yml");
        if (!this.priceFile.exists()) {
            CosmicGUIShop.get().saveResource("prices.yml", false);
        }
        this.priceConfig = YamlConfiguration.loadConfiguration((File) this.priceFile);
        ConfigurationSection section = this.priceConfig.getConfigurationSection("prices");
        for (String category : section.getKeys(false)) {
            ShopCategory cat = ShopCategory.getFromName(category);
            if (cat == null) {
                Bukkit.getLogger().info("Unable to find category for " + category + "!");
                continue;
            }
            ConfigurationSection catSection = section.getConfigurationSection(category);
            if (catSection == null) {
                Bukkit.getLogger().info("No item section found for " + category);
                continue;
            }
            ShopItemMap items = new ShopItemMap();
            for (String itemString : catSection.getKeys(false)) {
                ShopMaterialData data = this.getShopMaterialFromString(itemString);
                if (data == null) continue;
                double price = catSection.getDouble(itemString + ".buy");
                if (price == -1.0) {
                    catSection.set(itemString, null);
                    Bukkit.getLogger().info("Deleting " + itemString);
                    this.saveFile();
                    continue;
                }
                double sell = catSection.getDouble(itemString + ".sell");
                if (price <= 0.0 && sell <= 0.0) {
                    Bukkit.getLogger().info("UNABLE TO LOAD ITEM WITH NO PRICE: " + itemString);
                    continue;
                }
                if (!data.toString().equals(itemString)) {
                    Bukkit.getLogger().info("Converting " + itemString + " -> " + data.toString());
                    ShopManager manager = CosmicGUIShop.get().getShopManager();
                    catSection.set(itemString, null);
                    manager.getPriceConfig().set("prices." + cat.name() + "." + data.toString() + ".buy", (Object) price);
                    manager.getPriceConfig().set("prices." + cat.name() + "." + data.toString() + ".sell", (Object) sell);
                    this.saveFile();
                }
                items.put(data, new ItemPrice(price, sell));
            }
            this.shopItems.put(cat, items);
        }
    }

    public ShopMaterialData getShopMaterialFromString(String itemString) {
        ShopMaterialData data;
        if (itemString == null) {
            return null;
        }
        int count = 1;
        if (itemString.contains("#")) {
            String countString = itemString.split("#")[1];
            itemString = itemString.split("#")[0];
            if (StringUtils.isNumeric((String) countString)) {
                count = Integer.parseInt(countString);
            }
        }
        if (itemString.contains("@") || itemString.contains(":")) {
            String name = itemString;
            name = name.replace(":", "@");
            String matName = name.split("@")[0];
            Material material = Material.matchMaterial((String) matName);
            short extra = Short.parseShort(name.split("@")[1]);
            if (material == null) {
                Bukkit.getLogger().info("Unable to load material for " + itemString);
                return null;
            }
            data = new ShopMaterialData(material, extra, count);
        } else {
            Material found = Material.matchMaterial((String) itemString);
            if (found == null) {
                Bukkit.getLogger().info("Unable to load material for " + itemString);
                return null;
            }
            data = new ShopMaterialData(found, count);
        }
        return data;
    }

    public void setPrice(ShopCategory category, ShopMaterialData data, ItemPrice price) {
        ShopManager manager = CosmicGUIShop.get().getShopManager();
        manager.getPriceConfig().set("prices." + category.name() + "." + data.toString() + ".buy", (Object) price.getBuyPrice());
        manager.getPriceConfig().set("prices." + category.name() + "." + data.toString() + ".sell", (Object) price.getSellPrice());
    }

    public GUIMenu getShopMenu(final Player player) {
        GUIMenu menu = new GUIMenu("Shop Categories", SlotUtils.fitSlots(ShopCategory.values().length));
        int slot = 0;
        for (final ShopCategory category : ShopCategory.values()) {
            if (slot == 9) {
                slot = 11;
            }
            menu.setItem(slot++, new ClickableItemStack(ItemBuilder.buildItem(category.getDisplayData().getType(), category.getDisplayData().getData(), category.getDisplayName(), (List<String>) Lists.newArrayList(new String[]{(Object) ChatColor.GRAY + "Click to view this category."}))).setClickCallback(new GUIItemCallback() {

                @Override
                public void onClick(InventoryClickEvent event) {
                    ShopManager.this.getCategoryMenu(player, category).open(player);
                }
            }));
        }
        return menu;
    }

    public GUIMenu getCategoryMenu(final Player player, ShopCategory category) {
        ShopItemMap items = this.shopItems.get((Object) category);
        String name = "Shop Items (" + ChatColor.stripColor((String) category.getDisplayName()) + ")";
        if (name.length() > 32) {
            name = name.substring(0, 32);
        }
        final GUIMenu menu = new GUIMenu(name, items == null ? 9 : SlotUtils.fitSlots(items.size() + 1));
        if (items != null) {
            int index = 0;
            final int emptySlots = SlotUtils.getEmptySlots(player);
            for (Map.Entry item : items.entrySet()) {
                ItemStack displayItem;
                final ShopMaterialData data = (ShopMaterialData) item.getKey();
                final ItemPrice price = (ItemPrice) item.getValue();
                boolean isCollectionChest = (data.getType() == Material.CHEST || data.getType() == Material.TRAPPED_CHEST) && data.getData() == 100;
                boolean isPotionBundle = data.getType() == Material.POTION && data.getSellCount() > 1;
                int potionBundleCount = isPotionBundle ? data.getData() - 100 : 0;
                ArrayList lore = Lists.newArrayList();
                if (isCollectionChest) {
                    lore.addAll(Lists.newArrayList((Object[]) new String[]{(Object) ChatColor.GRAY + "Create a chest that will", (Object) ChatColor.GRAY + "automatically collect specified", (Object) ChatColor.GRAY + "mob drops when a creature dies", (Object) ChatColor.GRAY + "within the same chunk.", ""}));
                } else {
                    lore.add("");
                }
                if (price.getBuyPrice() > 0.0) {
                    lore.add((Object) ChatColor.GREEN + "Buy Price");
                    lore.add((Object) ChatColor.GREEN + ChatColor.BOLD.toString() + " $" + (Object) ChatColor.GREEN + moneyFormat.format(price.getBuyPrice()) + (Object) ChatColor.GREEN + "/ea");
                }
                if (price.getSellPrice() > 0.0) {
                    if (lore.size() > 1) {
                        lore.add("");
                    }
                    lore.add((Object) ChatColor.RED + "Sell Price");
                    lore.add((Object) ChatColor.RED + ChatColor.BOLD.toString() + " $" + (Object) ChatColor.RED + moneyFormat.format(price.getSellPrice()) + (Object) ChatColor.RED + "/ea");
                }
                lore.add("");
                lore.add((Object) ChatColor.GRAY + ChatColor.STRIKETHROUGH.toString() + "-----------------------");
                if (price.getBuyPrice() > 0.0) {
                    lore.add((Object) ChatColor.YELLOW + "Left-Click " + (Object) ChatColor.GRAY + "to buy " + (Object) ChatColor.YELLOW + "1x");
                    if (data.getType() != Material.MOB_SPAWNER) {
                        lore.add((Object) ChatColor.YELLOW + "Shift Left-Click " + (Object) ChatColor.GRAY + "to buy " + (Object) ChatColor.YELLOW + data.getType().getMaxStackSize() + "x");
                        if (emptySlots > 1) {
                            lore.add((Object) ChatColor.YELLOW + "Middle-Click" + (Object) ChatColor.GRAY + " to buy inventory " + (Object) ChatColor.YELLOW + data.getType().getMaxStackSize() * emptySlots + "x");
                        }
                    }
                }
                if (price.getSellPrice() > 0.0) {
                    lore.add("");
                    lore.add((Object) ChatColor.YELLOW + "Right-Click " + (Object) ChatColor.GRAY + "to sell " + (Object) ChatColor.YELLOW + "1x");
                    lore.add((Object) ChatColor.YELLOW + "Shift Right-Click " + (Object) ChatColor.GRAY + "to sell " + (Object) ChatColor.YELLOW + data.getType().getMaxStackSize() + "x");
                }
                short dura = data.getData();
                String string = (Object) ChatColor.YELLOW + ChatColor.BOLD.toString() + (data.getSellCount() > 1 ? data.getSellCount() + "x " : "") + ItemUtils.getPrettyName(data);
                if (isCollectionChest) {
                    string = (Object) ChatColor.YELLOW + ChatColor.BOLD.toString() + "Collection " + (data.getType() == Material.TRAPPED_CHEST ? "Trapped " : "") + "Chest";
                    dura = 0;
                }
                try {
                    displayItem = data.getType() == Material.MOB_SPAWNER ? (data.getData() == 101 ? MysteryMobSpawners.getMysteryMobSpawner() : this.silkSpawners.newSpawnerItem(data.getData(), (Object) ChatColor.YELLOW + ChatColor.BOLD.toString() + "%creature% Spawner", 1, false)) : null;
                } catch (Exception e) {
                    Bukkit.getLogger().info("Problem loading " + data.getData() + " Spawner!");
                    e.printStackTrace();
                    displayItem = null;
                }
                menu.setItem(index++, new ClickableItemStack(displayItem != null ? displayItem : ItemBuilder.buildItem(data.getType(), data.getSellCount(), dura, string)).setLore(lore).setDura(data.getType() == Material.MOB_SPAWNER ? (short) 0 : dura).setClickCallback(new GUIItemCallback() {

                    @Override
                    public void onClick(InventoryClickEvent event) {
                        boolean fillInventory = false;
                        int sellAmount = event.getClick().equals(ClickType.RIGHT) ? 1 : (event.getClick().equals(ClickType.SHIFT_RIGHT) ? data.getType().getMaxStackSize() : -1);
                        int buyAmount = event.getClick().equals(ClickType.LEFT) ? 1 : ((event.getClick().equals(ClickType.SHIFT_LEFT) && data.getType() != Material.MOB_SPAWNER) ? data.getType().getMaxStackSize() : ((fillInventory = (data.getType() != Material.MOB_SPAWNER && event.getClick().equals(ClickType.MIDDLE) && emptySlots > 1)) ? (emptySlots * data.getType().getMaxStackSize()) : 1));
                        if (sellAmount != -1) {
                            if (price.getSellPrice() <= 0.0D) {
                                player.sendMessage(ChatColor.RED + "You cannot sell this item to the shop!");
                                return;
                            }
                            int amountInInventory = ShopManager.this.getAmountInInventory(player, data);
                            if (amountInInventory > sellAmount) {
                                amountInInventory = sellAmount;
                            }
                            if (sellAmount > amountInInventory && amountInInventory > 0) {
                                sellAmount = amountInInventory;
                            }
                            if (amountInInventory >= sellAmount) {
                                double cashToGive = (double) sellAmount * price.getSellPrice();
                                ShopManager.this.removeItemsFromInventory(player, data, sellAmount);
                                CosmicGUIShop.getEconomy().depositPlayer((OfflinePlayer) player, cashToGive);
                                player.playSound(player.getLocation(), Sound.VILLAGER_YES, 3.0f, 0.93f);
                                player.sendMessage((Object) ChatColor.GREEN + ChatColor.BOLD.toString() + "+ $" + moneyFormat.format(cashToGive));
                                player.sendMessage((Object) ChatColor.GREEN + "Successfully sold " + (Object) ChatColor.GREEN + (Object) ChatColor.BOLD + sellAmount + "x " + (Object) ChatColor.GREEN + ItemUtils.getPrettyName(data) + " for " + (Object) ChatColor.GREEN + (Object) ChatColor.BOLD + "$" + (Object) ChatColor.GREEN + moneyFormat.format(price.getSellPrice()) + (Object) ChatColor.GREEN + "/ea");
                                Bukkit.getLogger().info(player.getName() + " sold " + sellAmount + " " + data.toString() + " for " + cashToGive);
                                Bukkit.getPluginManager().callEvent((Event) new CustomCosmicPlayerEvent(player, "SHOP_SELL", new Object[]{cashToGive, sellAmount, data.getType(), data.getData()}));
                            } else if (amountInInventory <= 0) {
                                player.sendMessage((Object) ChatColor.RED + "You do not have any to sell!");
                            }
                        } else if (buyAmount != -1) {
                            if (price.getBuyPrice() <= 0.0) {
                                player.sendMessage((Object) ChatColor.RED + "You cannot buy this item from the shop!");
                                return;
                            }
                            double totalPrice = price.getBuyPrice() * (double) buyAmount;
                            if (!CosmicGUIShop.getEconomy().has((OfflinePlayer) player, totalPrice)) {
                                player.sendMessage((Object) ChatColor.RED + ChatColor.BOLD.toString() + "(!) " + (Object) ChatColor.RED + "You do not enough money to purchase this!");
                                player.sendMessage((Object) ChatColor.GRAY + "Price: " + (Object) ChatColor.GRAY + moneyFormat.format(price.getBuyPrice()) + (Object) ChatColor.GRAY + "/ea");
                                return;
                            }
                            if (player.getInventory().firstEmpty() == -1) {
                                player.sendMessage((Object) ChatColor.RED + "You do not have enough inventory space!");
                                return;
                            }
                            if (fillInventory) {
                                int finalBuyAmount = buyAmount;
                                new ConfirmPurchaseMenu("Confirm Inventory Purchase", 9, new ItemStack(data.getType(), 1, data.getData()), ItemUtils.getPrettyName(data), buyAmount, totalPrice, acceptStatus -> {
                                    if (acceptStatus.booleanValue()) {
                                        if (!CosmicGUIShop.getEconomy().has((OfflinePlayer) player, totalPrice)) {
                                            player.sendMessage((Object) ChatColor.RED + "You no longer have the funds!");
                                            player.closeInventory();
                                            return;
                                        }
                                        if (player.getInventory().firstEmpty() == -1) {
                                            player.sendMessage(ChatColor.RED + "You do not have enough inventory space!");
                                            return;
                                        }
                                        int slots = SlotUtils.getEmptySlots(player);
                                        if (slots < emptySlots) {
                                            player.sendMessage(ChatColor.RED + "You do not have the inventory space needed!");
                                            return;
                                        }
                                        ShopManager.this.finishPurchase(player, data, totalPrice, buyAmount);
                                        menu.open(player);
                                    } else {
                                        player.sendMessage((Object) ChatColor.RED + "Purchase Cancelled!");
                                        menu.open(player);
                                    }
                                }).open(player);
                                return;
                            }
                            ShopManager.this.finishPurchase(player, data, totalPrice, buyAmount);
                        }
                    }
                }));
            }
        }
        menu.setItem(menu.getSize() - 1, this.getBackArrow(player));
        return menu;
    }

    public void finishPurchase(Player player, ShopMaterialData data, double totalPrice, int buyAmount) {
        boolean isPotionBundle;
        CosmicGUIShop.getEconomy().withdrawPlayer((OfflinePlayer) player, totalPrice);
        player.sendMessage((Object) ChatColor.RED + ChatColor.BOLD.toString() + "- $" + (Object) ChatColor.RED + moneyFormat.format(totalPrice));
        player.sendMessage((Object) ChatColor.GREEN + "Successfully purchased " + (Object) ChatColor.GREEN + (Object) ChatColor.BOLD + buyAmount + "x " + (Object) ChatColor.GREEN + ItemUtils.getPrettyName(data) + "!");
        player.playSound(player.getLocation(), Sound.ORB_PICKUP, 1.0f, 1.4f);
        Material type = data.getType();
        if ((type == Material.CHEST || type == Material.TRAPPED_CHEST) && data.getData() == 100) {
            Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), (String) ("collectionchest give" + (type == Material.TRAPPED_CHEST ? "trapped" : "") + " " + player.getName() + " " + buyAmount));
            Bukkit.getPluginManager().callEvent((Event) new CustomCosmicPlayerEvent(player, "SHOP_BUY", new Object[]{totalPrice, data, buyAmount}));
            return;
        }
        int maxStack = type.getMaxStackSize();
        boolean bl = isPotionBundle = data.getType() == Material.POTION && data.getSellCount() > 1;
        if (isPotionBundle) {
            for (int i = 0; i < buyAmount; ++i) {
                ItemStack toGive = this.getCustomItem(data, data.getSellCount(), ChatColor.WHITE.toString());
                player.getInventory().addItem(new ItemStack[]{toGive});
            }
            return;
        }
        if (buyAmount > maxStack) {
            int stacksToGive = buyAmount / maxStack;
            buyAmount -= stacksToGive * maxStack;
            for (int i = 0; i < stacksToGive; ++i) {
                ItemStack toGive = this.getCustomItem(data, maxStack, ChatColor.WHITE.toString());
                player.getInventory().addItem(new ItemStack[]{toGive});
            }
            if (buyAmount > 0 && buyAmount > maxStack) {
                ItemStack toGive = this.getCustomItem(data, maxStack, ChatColor.WHITE.toString());
                player.getInventory().addItem(new ItemStack[]{toGive});
            }
        } else {
            ItemStack toGive = this.getCustomItem(data, buyAmount, ChatColor.WHITE.toString());
            player.getInventory().addItem(new ItemStack[]{toGive});
        }
        Bukkit.getPluginManager().callEvent((Event) new CustomCosmicPlayerEvent(player, "SHOP_BUY", new Object[]{totalPrice, buyAmount, data.getType(), data.getData()}));
    }

    public ItemStack getCustomItem(ShopMaterialData data, int amount, String color) {
        ItemStack toGive = new ItemStack(data.getType(), amount, data.getData());
        if (data.getType() == Material.MOB_SPAWNER) {
            if (data.getData() == 101) {
                toGive = MysteryMobSpawners.getMysteryMobSpawner();
            } else {
                EntityType type = EntityType.fromId((int) data.getData());
                if (type == null) {
                    Bukkit.getLogger().info("Data: " + data.getData() + " from : " + data);
                    type = EntityType.PIG;
                }
                toGive = ShopManager.getMobSpawner(type);
                if (ArkPlugins.ITEM_ID.getArkPlugin().isRunning()) {
                    toGive = ItemID.setItemID((ItemStack) toGive, (long) ItemID.generateNewItemID());
                }
            }
        }
        if (data.getType() == Material.POTION && data.getSellCount() > 1) {
            ItemMeta im = toGive.getItemMeta();
            ArrayList lore = Lists.newArrayList((Object[]) new String[]{CC.Gray + "Stackable (" + data.getSellCount() + ")"});
            im.setLore((List) lore);
            toGive.setItemMeta(im);
            toGive = new NBTWrapper(toGive).setString("cosmicType", "stackable").setNBTBase("cosmicData", (NBTBase) new NBTTagCompoundBuilder().setInt("maxStackSize", Integer.valueOf(data.getSellCount())).setLong("uniquePots", Long.valueOf(System.nanoTime())).get()).build();
            toGive.setAmount(data.getSellCount());
        }
        return toGive;
    }

    public ClickableItemStack getBackArrow(final Player player) {
        return new ClickableItemStack(ItemBuilder.buildItem(Material.ARROW, 1, (short) 0, ChatColor.GREEN.toString() + (Object) ChatColor.BOLD + "Back", (List<String>) Lists.newArrayList(new String[]{(Object) ChatColor.GRAY + "Click to return to categories."}))).setClickCallback(new GUIItemCallback() {

            @Override
            public void onClick(InventoryClickEvent event) {
                Bukkit.getLogger().info("Back..");
                ShopManager.this.getShopMenu(player).open(player);
            }
        });
    }

    private int getAmountInInventory(Player player, ShopMaterialData data) {
        int found = 0;
        for (ItemStack item : player.getInventory()) {
            if (item == null || this.isUnsellable(item) || item.getType() != data.getType() || item.getDurability() != data.getData())
                continue;
            found += item.getAmount();
        }
        return found;
    }

    public boolean isUnsellable(ItemStack item) {
        return item != null && item.getItemMeta().hasLore() && item.getItemMeta().getLore().size() > this.loreThreshold && ItemUtils.hasEssentialsPrice(item);
    }

    private void removeItemsFromInventory(Player player, ShopMaterialData data, int amount) {
        int toRemove = amount;
        for (ItemStack item : player.getInventory()) {
            if (toRemove <= 0) break;
            if (item == null || this.isUnsellable(item) || item.getType() != data.getType() || item.getDurability() != data.getData())
                continue;
            if (item.getAmount() > toRemove) {
                item.setAmount(item.getAmount() - toRemove);
                toRemove = 0;
                continue;
            }
            toRemove -= item.getAmount();
            player.getInventory().removeItem(new ItemStack[]{item});
        }
        player.updateInventory();
    }

    public void saveFile() throws IOException {
        this.priceConfig.save(this.priceFile);
    }

    public Map<ShopCategory, ShopItemMap> getShopItems() {
        return this.shopItems;
    }

    public YamlConfiguration getPriceConfig() {
        return this.priceConfig;
    }
}

