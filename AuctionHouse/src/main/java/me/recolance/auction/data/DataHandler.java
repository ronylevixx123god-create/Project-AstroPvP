/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.me.astropvp.cratekeys.utils.CC
 *  com.cosmicpvp.cosmicutils.me.astropvp.cratekeys.utils.GeneralUtil
 *  com.cosmicpvp.cosmicutils.me.astropvp.cratekeys.utils.ItemBuilder
 *  com.cosmicpvp.cosmicutils.me.astropvp.cratekeys.utils.ItemUtils
 *  com.google.common.collect.Lists
 *  net.minecraft.server.v1_8_R3.ItemStack
 *  net.minecraft.server.v1_8_R3.NBTCompressedStreamTools
 *  net.minecraft.server.v1_8_R3.NBTTagCompound
 *  net.minecraft.util.com.google.common.io.BaseEncoding
 *  org.apache.commons.lang.StringUtils
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.craftbukkit.v1_8_R3.me.astropvp.cratekeys.inventory.CraftItemStack
 *  org.bukkit.me.astropvp.cratekeys.inventory.ItemStack
 *  org.bukkit.material.MaterialData
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.scheduler.BukkitRunnable
 */
package me.recolance.auction.data;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.GeneralUtil;
import com.cosmicpvp.cosmicutils.utils.ItemBuilder;
import com.cosmicpvp.cosmicutils.utils.ItemUtils;
import com.google.common.collect.Lists;
import com.google.common.io.BaseEncoding;
import me.recolance.auction.Auction;
import me.recolance.auction.auction.Listing;
import me.recolance.auction.auction.UnclaimedListing;
import me.recolance.auction.menu.categories.AuctionCategory;
import net.minecraft.server.v1_8_R3.NBTCompressedStreamTools;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class DataHandler {
    private static List<AuctionCategory> auctionCategories = Lists.newLinkedList();
    private static long nextCategoryCalculation;
    private static List<Material> subCategoryItems;

    static {
        subCategoryItems = null;
    }

    public static void generateTables() {
        Connection connection = Database.getConnection();
        try {
            PreparedStatement listings = connection.prepareStatement("CREATE TABLE IF NOT EXISTS listings(id VARCHAR(255) NOT NULL,listed_by VARCHAR(255) NOT NULL,item TEXT NOT NULL,price INT NOT NULL,end_time BIGINT NOT NULL,PRIMARY KEY (id))");
            listings.execute();
            PreparedStatement expired = connection.prepareStatement("CREATE TABLE IF NOT EXISTS expired(id VARCHAR(255) NOT NULL,player VARCHAR(255) NOT NULL,item TEXT NOT NULL,expire_time BIGINT NOT NULL,PRIMARY KEY (id))");
            expired.execute();
            DataHandler.generateIndex();
            listings.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void generateIndex() {
        Connection connection = Database.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement("CREATE INDEX IDX_EXPIRED ON expired (player)");
            statement.execute();
        } catch (SQLException sQLException) {
            // empty catch block
        }
    }

    public static void loadListings() {
        Connection connection = Database.getConnection();
        try {
            PreparedStatement load = connection.prepareStatement("SELECT * FROM listings");
            ResultSet set = load.executeQuery();
            while (set.next()) {
                UUID playerId = UUID.fromString(set.getString("listed_by"));
                Listing listing = new Listing(UUID.fromString(set.getString("id")), set.getLong("end_time"), playerId, DataHandler.fromString(set.getString("item")), set.getInt("price"));
                DataHolder.addListing(listing);
                DataHolder.addPlayerListing(playerId, listing);
            }
            List<Listing> listings = DataHolder.getListings();
            Collections.sort(listings, new Comparator<Listing>() {

                @Override
                public int compare(Listing l1, Listing l2) {
                    return Long.valueOf(l1.getEndTime()).compareTo(l2.getEndTime());
                }
            });
            Collections.reverse(listings);
            load.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Material> getSubCategoryItems() {
        if (subCategoryItems == null) {
            subCategoryItems = Lists.newArrayList(GeneralUtil.armor);
            subCategoryItems.addAll(Arrays.asList(GeneralUtil.weapons));
            subCategoryItems.addAll(Arrays.asList(GeneralUtil.tools));
            subCategoryItems.addAll(Arrays.asList(Material.BOOK, Material.MOB_SPAWNER, Material.TNT, Material.PAPER));
        }
        return subCategoryItems;
    }

    private static boolean isSubCategoryItem(MaterialData data, ItemStack item) {
        String display;
        Material type = data.getItemType();
        boolean contains = DataHandler.getSubCategoryItems().contains((Object) type);
        Material dataType = data.getItemType();
        if (!contains && dataType == Material.EMERALD && item.getItemMeta().hasDisplayName() && (display = item.getItemMeta().getDisplayName()).startsWith(CC.RedB + "Soul Gem [")) {
            return true;
        }
        return contains;
    }

    public static List<AuctionCategory> getAuctionCategories() {
        if (nextCategoryCalculation == 0L || nextCategoryCalculation <= System.currentTimeMillis()) {
            auctionCategories.clear();
            long start = System.currentTimeMillis();
            HashMap subCategoryCategories = new HashMap();
            HashMap subCategoryNames = new HashMap();
            HashMap bookCategories = new HashMap();
            HashMap matchingItemNames = new HashMap();
            DataHolder.getListings().forEach(item -> {
                if (item.isValid() && !item.isExpired()) {
                    ItemStack selling = item.getItem().clone();
                    String name = ItemUtils.getDisplayName((ItemStack) selling);
                    if (name == null) {
                        name = StringUtils.capitaliseAllWords((String) item.toString().toLowerCase().replace("_", " "));
                    }
                    if (selling.getEnchantments().size() > 0) {
                        selling.getEnchantments().forEach((ench, level) -> selling.removeEnchantment(ench));
                    }
                    selling.setAmount(1);
                    MaterialData type = selling.getData();
                    Material mat = type.getItemType();
                    if (DataHandler.isSubCategoryItem(type, selling)) {
                        String subCategoryName = CC.White + StringUtils.capitaliseAllWords((String) mat.name().toLowerCase().replace("_", " "));
                        if (name.startsWith(CC.RedB + "Soul Gem") && mat == Material.EMERALD) {
                            subCategoryName = CC.RedB + "Soul Gems";
                        } else if (item.getBookRarity() != null) {
                            subCategoryName = CC.White + "Enchant Books";
                        }
                        String finalSubCategoryName = subCategoryName;
                        AuctionCategory category = (AuctionCategory) subCategoryCategories.computeIfAbsent(mat, e -> new AuctionCategory(finalSubCategoryName, ChatColor.stripColor(finalSubCategoryName), new ItemBuilder(selling).name(finalSubCategoryName).build(), Lists.newArrayList(), null, Lists.newLinkedList(), 10));
                        if (item.getBookRarity() != null) {
                            String bookName = item.getBookTierColor() + item.getBookTierName() + " Enchant Books";
                            AuctionCategory bookCategory = (AuctionCategory) bookCategories.computeIfAbsent(item.getBookRarity(), e -> new AuctionCategory(bookName, ChatColor.stripColor((String) bookName), new ItemBuilder(selling).name(bookName).build(), Lists.newArrayList(), null, Lists.newLinkedList(), item.getBookRarity()));
                            bookCategory.getCategoryItems().add((Listing) item);
                            if (!category.getSubCategories().contains(bookCategory)) {
                                category.getSubCategories().add(bookCategory);
                            }
                            return;
                        }
                        category.getCategoryItems().add((Listing) item);
                        return;
                    }
                    String finalName = name;
                    matchingItemNames.computeIfAbsent(name, e -> new AuctionCategory(finalName, ChatColor.stripColor(finalName), selling, Lists.newArrayList(), null, Lists.newLinkedList(), 10).getCategoryItems().add(item));
                }
            });
            auctionCategories.addAll(matchingItemNames.values());
            auctionCategories.addAll(subCategoryCategories.values());
            auctionCategories.forEach(AuctionCategory::sortCategoryItems);
            auctionCategories.forEach(AuctionCategory::sortSubCategories);
            Collections.sort(auctionCategories, AuctionCategory::compareTo);
            Auction.getPlugin().getLogger().info("Took " + (System.currentTimeMillis() - start) + "ms to calculate auction categories " + auctionCategories.size() + " Listings: " + DataHolder.getListings().size());
        }
        auctionCategories.removeIf(cat -> {
            cat.getCategoryItems().removeIf(item -> item.isExpired() || !item.isValid());
            return cat.getCategoryItems().isEmpty() && cat.getSubCategories().isEmpty();
        });
        return auctionCategories;
    }

    public static void loadUnclaimedListings(UUID playerId) {
        Connection connection = Database.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT id, item, expire_time FROM expired WHERE player=?");
            statement.setString(1, playerId.toString());
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                UnclaimedListing uL = new UnclaimedListing(UUID.fromString(set.getString("id")), playerId, DataHandler.fromString(set.getString("item")), set.getLong("expire_time"));
                DataHolder.addUnclaimedListing(playerId, uL);
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void saveAllListings(boolean async) {
        Connection connection = Database.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement("REPLACE INTO listings(id, listed_by, item, price, end_time) VALUES (?,?,?,?,?)");
            for (Listing l : DataHolder.getListings()) {
                statement.setString(1, l.getId().toString());
                statement.setString(2, l.getWhoListed().toString());
                statement.setString(3, DataHandler.toString(l.getItem()));
                statement.setInt(4, l.getPrice());
                statement.setLong(5, l.getEndTime());
                statement.addBatch();
            }
            if (async) {
                DataHandler.asyncExecuteBatch(statement);
            } else {
                statement.executeBatch();
                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void saveListing(Listing listing, boolean async) {
        Connection connection = Database.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement("REPLACE INTO listings(id, listed_by, item, price, end_time) VALUES (?,?,?,?,?)");
            statement.setString(1, listing.getId().toString());
            statement.setString(2, listing.getWhoListed().toString());
            statement.setString(3, DataHandler.toString(listing.getItem()));
            statement.setInt(4, listing.getPrice());
            statement.setLong(5, listing.getEndTime());
            if (async) {
                DataHandler.asyncExecute(statement);
            } else {
                statement.execute();
                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void saveUnclaimedListing(UnclaimedListing listing, boolean async) {
        Connection connection = Database.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement("REPLACE INTO expired(id, player, item, expire_time) VALUES (?,?,?,?)");
            statement.setString(1, listing.getId().toString());
            statement.setString(2, listing.getListedBy().toString());
            statement.setString(3, DataHandler.toString(listing.getItem()));
            statement.setLong(4, listing.getExpireTime());
            if (async) {
                DataHandler.asyncExecute(statement);
            } else {
                statement.execute();
                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteListing(Listing listing, boolean async) {
        Connection connection = Database.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM listings WHERE id=?");
            statement.setString(1, listing.getId().toString());
            if (async) {
                DataHandler.asyncExecute(statement);
            } else {
                statement.execute();
                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteUnclaimedListing(UnclaimedListing listing, boolean async) {
        Connection connection = Database.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM expired WHERE id=?");
            statement.setString(1, listing.getId().toString());
            if (async) {
                DataHandler.asyncExecute(statement);
            } else {
                statement.execute();
                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteOverExpiredUnclaimedListing(boolean async) {
        Connection connection = Database.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM expired WHERE expire_time<?");
            statement.setLong(1, System.currentTimeMillis());
            if (async) {
                DataHandler.asyncExecute(statement);
            } else {
                statement.execute();
                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void asyncExecute(final PreparedStatement statement) {
        if (Auction.stoppingServer) {
            try {
                statement.execute();
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return;
        }
        new BukkitRunnable() {

            public void run() {
                try {
                    statement.execute();
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }.runTaskAsynchronously((Plugin) Auction.plugin);
    }

    private static void asyncExecuteBatch(final PreparedStatement statement) {
        if (Auction.stoppingServer) {
            try {
                statement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return;
        }
        new BukkitRunnable() {

            public void run() {
                try {
                    statement.executeBatch();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }.runTaskAsynchronously((Plugin) Auction.plugin);
    }

    public static String toString(ItemStack item) {
        if (item == null) {
            return "null";
        }
        NBTTagCompound tagCompound = new NBTTagCompound();
        CraftItemStack.asNMSCopy((ItemStack) item).save(tagCompound);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            NBTCompressedStreamTools.a((NBTTagCompound) tagCompound, (OutputStream) outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return BaseEncoding.base64().encode(outputStream.toByteArray());
    }

    public static ItemStack fromString(String string) {
        if (string.equals("null")) {
            return null;
        }
        ByteArrayInputStream is = new ByteArrayInputStream(BaseEncoding.base64().decode((CharSequence) string));
        NBTTagCompound nbttagcompound = null;
        try {
            nbttagcompound = NBTCompressedStreamTools.a((InputStream) is);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return CraftItemStack.asBukkitCopy((net.minecraft.server.v1_8_R3.ItemStack) net.minecraft.server.v1_8_R3.ItemStack.createStack((NBTTagCompound) nbttagcompound));
    }
}

