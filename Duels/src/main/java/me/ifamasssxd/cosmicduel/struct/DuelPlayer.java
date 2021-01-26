/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  me.cosmicpvp.CosmicGlobalKits.libs.GlobalKits
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.InventoryHolder
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.plugin.Plugin
 */
package me.ifamasssxd.cosmicduel.struct;

import com.google.common.collect.Lists;
import me.cosmicpvp.CosmicGlobalKits.libs.GlobalKits;
import me.ifamasssxd.cosmicduel.CosmicDuel;
import me.ifamasssxd.cosmicduel.godset.CustomGodSet;
import me.ifamasssxd.cosmicduel.utils.ItemBuilder;
import me.ifamasssxd.cosmicduel.utils.KitUtils;
import me.ifamasssxd.cosmicduel.utils.Serializer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

public class DuelPlayer {
    public boolean loading = false;
    private static Map<UUID, DuelPlayer> duelPlayers = new HashMap<UUID, DuelPlayer>();
    private String name;
    private UUID uuid;
    private Map<Kit, List<CustomKit>> customKits = new HashMap<Kit, List<CustomKit>>();
    private Map<GlobalKits, List<CustomGKit>> customGlobalKits = new HashMap<GlobalKits, List<CustomGKit>>();
    private Kit kitModifying;
    private CustomKit currentlyModifying;
    private GlobalKits gKitModifying;
    private CustomGKit gKitCurrentlyModifying;
    private CustomGodSet customGodSet;
    private int rankedWins = 0;
    private int wins = 0;
    private int rankedLosses = 0;
    private int losses = 0;
    private double elo = 1000.0;
    private long lastRankedMatch = 0L;
    private String lastSerializedData = null;
    private boolean pendingDeletion = false;
    private String lastSavedGodset = null;
    private static final String KIT_SEPERATOR = "$";
    private static final String KIT_TYPE_SEPERATOR = ":";
    private static final String CUSTOM_KIT_SEPERATOR = "#";

    public DuelPlayer(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
        duelPlayers.put(uuid, this);
    }

    public boolean isModifying() {
        return this.kitModifying != null || this.currentlyModifying != null || this.gKitCurrentlyModifying != null || this.gKitModifying != null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void loadData(boolean async) {
        if (async && Bukkit.isPrimaryThread()) {
            Bukkit.getScheduler().scheduleAsyncDelayedTask((Plugin)CosmicDuel.get(), () -> this.loadData(false));
            return;
        }
        this.loading = true;
        try {
            PreparedStatement statement = CosmicDuel.get().getSqlDatabase().getConnection().prepareStatement("SELECT s.customKits, s.customGodSet, k.wins, k.losses, k.rankedWins, k.rankedLosses, k.elo, k.lastRankedMatch FROM duelStats s LEFT JOIN `" + CosmicDuel.get().getServerName() + "_stats` k ON (s.uuid = k.uuid) WHERE s.uuid = ?;");
            try {
                statement.setString(1, this.uuid.toString());
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.first()) {
                    String serialized = resultSet.getString("customKits");
                    String godset = resultSet.getString("customGodSet");
                    if (!serialized.isEmpty()) {
                        this.deserializeKits(serialized);
                        this.lastSerializedData = this.getSerializedKits();
                    }
                    if (godset != null) {
                        this.deserializeGodset(godset);
                        this.lastSavedGodset = this.getSerializedGodset();
                        if (this.customGodSet != null) {
                            this.customGodSet.checkOldGodset();
                        }
                    }
                    this.losses = resultSet.getInt("losses");
                    this.wins = resultSet.getInt("wins");
                    this.rankedWins = resultSet.getInt("rankedWins");
                    this.rankedLosses = resultSet.getInt("rankedLosses");
                    this.lastRankedMatch = resultSet.getLong("lastRankedMatch");
                    this.elo = this.rankedWins == 0 && this.rankedLosses == 0 && resultSet.getDouble("elo") == 0.0 ? 1000.0 : resultSet.getDouble("elo");
                } else {
                    this.elo = 1000.0;
                }
                this.loading = false;
                statement.close();
            }
            finally {
                if (Collections.singletonList(statement).get(0) != null) {
                    statement.close();
                }
            }
        }
        catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    public double getElo() {
        if (this.elo < 0.0) {
            this.elo = 0.0;
        }
        return this.elo;
    }

    public void saveRankedStats(boolean async) {
        if (this.loading) {
            return;
        }
        String query = String.format("INSERT IGNORE INTO `" + CosmicDuel.get().getServerName() + "_stats`(uuid, name, rankedWins, rankedLosses, wins, losses, elo, lastRankedMatch) VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s') ON DUPLICATE KEY UPDATE name = '%s', rankedWins = '%s', rankedLosses = '%s', wins = '%s', losses ='%s', elo = '%s', lastRankedMatch = '%s';", this.uuid.toString(), this.name, this.rankedWins, this.rankedLosses, this.wins, this.losses, this.getElo(), this.getLastRankedMatch(), this.name, this.rankedWins, this.rankedLosses, this.wins, this.losses, this.getElo(), this.getLastRankedMatch());
        CosmicDuel.get().getSqlDatabase().executeUpdate(query, async);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void saveData(boolean async) {
        if (this.loading) {
            Bukkit.getLogger().info("Not saving duel data since we couldnt finish loading in time.");
            return;
        }
        if (async && Bukkit.isPrimaryThread()) {
            Bukkit.getScheduler().scheduleAsyncDelayedTask((Plugin)CosmicDuel.get(), () -> this.saveData(false));
            return;
        }
        try {
            this.pendingDeletion = false;
            String serializedKits = this.getSerializedKits();
            String customGodSet = this.getSerializedGodset();
            if (this.lastSerializedData != null && this.lastSerializedData.equals(serializedKits) && this.lastSavedGodset != null && this.lastSavedGodset.equals(customGodSet)) {
                return;
            }
            this.lastSerializedData = serializedKits;
            this.lastSavedGodset = customGodSet;
            PreparedStatement statement = CosmicDuel.get().getSqlDatabase().getConnection().prepareStatement("INSERT IGNORE INTO duelStats(uuid, customKits, customGodSet) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE customKits = ?, customGodSet = ?;");
            try {
                statement.setString(1, this.uuid.toString());
                statement.setString(2, serializedKits);
                statement.setString(3, customGodSet);
                statement.setString(4, serializedKits);
                statement.setString(5, customGodSet);
                statement.executeUpdate();
            }
            finally {
                if (Collections.singletonList(statement).get(0) != null) {
                    statement.close();
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<CustomKit> getCustomKits(Kit kit) {
        return this.customKits.get((Object)kit);
    }

    public List<CustomGKit> getCustomGkits(GlobalKits kit) {
        return this.customGlobalKits.get((Object)kit);
    }

    public CustomKit getDefaultKit(Kit kitType) {
        if (this.customKits.containsKey((Object)kitType)) {
            for (CustomKit kit : this.customKits.get((Object)kitType)) {
                if (!kit.isDefault()) continue;
                return kit;
            }
        }
        return null;
    }

    public void deserializeKits(String serializedData) {
        if (serializedData.contains(KIT_SEPERATOR)) {
            for (String kitKits : serializedData.split("\\$")) {
                this.deserializeKitSection(kitKits);
            }
            return;
        }
        this.deserializeKitSection(serializedData);
    }

    private void deserializeKitSection(String wholeKitString) {
        String[] args = wholeKitString.split(KIT_TYPE_SEPERATOR);
        Kit kit = null;
        GlobalKits globalKit = null;
        String name = args[0];
        try {
            if (name.startsWith("GKIT;")) {
                name = name.split("GKIT;")[1];
                globalKit = GlobalKits.valueOf((String)name);
            } else {
                kit = Kit.valueOf(name);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            Bukkit.getLogger().severe("Unable to find proper kit type for kit with name: " + args[0] + " Data: " + args[1]);
            return;
        }
        wholeKitString = args[1];
        if (wholeKitString.contains(CUSTOM_KIT_SEPERATOR)) {
            for (String serialized : wholeKitString.split(CUSTOM_KIT_SEPERATOR)) {
                if (globalKit != null) {
                    this.deserializeGKitContent(globalKit, serialized);
                    continue;
                }
                this.deserializeKitContent(kit, serialized);
            }
            return;
        }
        if (globalKit != null) {
            this.deserializeGKitContent(globalKit, wholeKitString);
        } else {
            this.deserializeKitContent(kit, wholeKitString);
        }
    }

    private void deserializeGKitContent(GlobalKits kit, String kitString) {
        CustomGKit customKit = new CustomGKit(kit);
        String[] content = kitString.split("@");
        String name = content[0];
        if (name.equals("Default")) {
            customKit.setDefault(true);
        }
        customKit.setItems(Serializer.deserializeKitItemString(kitString.substring(kitString.indexOf("@"))));
        this.addCustomGKit(customKit);
    }

    private void deserializeKitContent(Kit kit, String kitString) {
        CustomKit customKit = new CustomKit(kit);
        String[] content = kitString.split("@");
        String name = content[0];
        if (name.equals("Default")) {
            customKit.setDefault(true);
        }
        customKit.addKitItems(Serializer.deserializeKitItemString(kitString.substring(kitString.indexOf("@"))));
        this.addCustomKit(customKit);
    }

    public void addCustomKit(CustomKit customKit) {
        if (this.customKits.containsKey((Object)customKit.getKitType())) {
            this.customKits.get((Object)customKit.getKitType()).add(customKit);
        } else {
            this.customKits.put(customKit.getKitType(), Lists.newArrayList(customKit));
        }
    }

    public void addCustomGKit(CustomGKit customKit) {
        if (this.customGlobalKits.containsKey((Object)customKit.getGlobalKit())) {
            this.customGlobalKits.get((Object)customKit.getGlobalKit()).add(customKit);
        } else {
            this.customGlobalKits.put(customKit.getGlobalKit(), Lists.newArrayList(customKit));
        }
    }

    public void removeCustomKit(CustomKit customKit) {
        if (this.customKits.containsKey((Object)customKit.getKitType())) {
            List<CustomKit> kits = this.customKits.get((Object)customKit.getKitType());
            kits.remove(customKit);
            if (kits.isEmpty()) {
                this.customKits.remove((Object)customKit.getKitType());
            } else {
                this.customKits.put(customKit.getKitType(), kits);
            }
        }
    }

    public void removeCustomGKit(CustomGKit customKit) {
        if (this.customGlobalKits.containsKey((Object)customKit.getGlobalKit())) {
            List<CustomGKit> kits = this.customGlobalKits.get((Object)customKit.getGlobalKit());
            kits.remove(customKit);
            if (kits.isEmpty()) {
                this.customGlobalKits.remove((Object)customKit.getGlobalKit());
            } else {
                this.customGlobalKits.put(customKit.getGlobalKit(), kits);
            }
        }
    }

    public void deserializeGodset(String string) {
        if (string.isEmpty()) {
            return;
        }
        CustomGodSet set = new CustomGodSet();
        for (String item : string.split("&")) {
            Material material;
            String[] splits = item.split(CUSTOM_KIT_SEPERATOR);
            String mat = splits[0];
            ArrayList enchant = Lists.newArrayList();
            if (splits.length > 1) {
                String enchants = item.split(CUSTOM_KIT_SEPERATOR)[1];
                enchant = Lists.newArrayList((Object[])enchants.split(KIT_TYPE_SEPERATOR));
            }
            if ((material = Material.getMaterial((String)mat)) == null) continue;
            set.getCustomEnchants().put(material, enchant);
        }
        this.customGodSet = set;
    }

    private String getSerializedGodset() {
        if (this.getCustomGodSet() == null) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<Material, List<String>> enchants : this.getCustomGodSet().getCustomEnchants().entrySet()) {
            StringBuilder enchantString = new StringBuilder();
            for (String enchant : enchants.getValue()) {
                enchantString.append(enchant).append(KIT_TYPE_SEPERATOR);
            }
            builder.append(enchants.getKey().name()).append(CUSTOM_KIT_SEPERATOR).append((CharSequence)enchantString).append("&");
        }
        return builder.toString();
    }

    private String getSerializedKits() {
        Object in;
        StringBuilder inside;
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<Kit, List<CustomKit>> entry : this.customKits.entrySet()) {
            builder.append(entry.getKey().name()).append(KIT_TYPE_SEPERATOR);
            inside = new StringBuilder();
            for (CustomKit customKit : entry.getValue()) {
                inside.append(customKit.isDefault() ? "Default" : "Name").append("@").append(Serializer.getSerializedItemList(customKit.getKitItems())).append(CUSTOM_KIT_SEPERATOR);
            }
            in = inside.toString();
            if (((String)in).endsWith(CUSTOM_KIT_SEPERATOR)) {
                in = ((String)in).substring(0, ((String)in).length() - 1);
            }
            builder.append((String)in).append(KIT_SEPERATOR);
        }
        for (Map.Entry<GlobalKits, List<CustomGKit>> entry : this.customGlobalKits.entrySet()) {
            builder.append("GKIT;").append(((GlobalKits)entry.getKey()).name()).append(KIT_TYPE_SEPERATOR);
            inside = new StringBuilder();
            for (CustomGKit customGKit : entry.getValue()) {
                inside.append(customGKit.isDefault() ? "Default" : "Name").append("@").append(Serializer.getSerializedItemList(customGKit.getItems())).append(CUSTOM_KIT_SEPERATOR);
            }
            in = inside.toString();
            if (((String)in).endsWith(CUSTOM_KIT_SEPERATOR)) {
                in = ((String)in).substring(0, ((String)in).length() - 1);
            }
            builder.append((String)in).append(KIT_SEPERATOR);
        }
        String retr = builder.toString();
        if (retr.endsWith(KIT_SEPERATOR)) {
            retr = retr.substring(0, retr.length() - 1);
        }
        return retr;
    }

    public Inventory createCustomInventory() {
        Inventory inventory = Bukkit.createInventory((InventoryHolder)null, 54, (String)"Select a Duel Kit");
        inventory.setItem(3, this.getKitItem(Kit.HEALER));
        inventory.setItem(5, this.getKitItem(Kit.DEBUFF));
        int slot = 9;
        for (GlobalKits globalKit : GlobalKits.values()) {
            try {
                if (KitUtils.isInvalidGKit(globalKit)) continue;
                if (slot == inventory.getSize() - 9) {
                    slot += 3;
                }
                inventory.setItem(slot++, this.getKitItem(globalKit));
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
        }
        return inventory;
    }

    public void setDefaultKit(CustomKit kit) {
        this.getCustomKits(kit.getKitType()).stream().filter(CustomKit::isDefault).forEach(custom -> custom.setDefault(false));
        kit.setDefault(true);
    }

    public void setDefaultGKit(CustomGKit kit) {
        this.getCustomGkits(kit.getGlobalKit()).stream().filter(CustomGKit::isDefault).forEach(custom -> custom.setDefault(false));
        kit.setDefault(true);
    }

    public CustomGKit getDefaultGKit(GlobalKits kit) {
        List<CustomGKit> gkits = this.getCustomGkits(kit);
        if (gkits != null) {
            for (CustomGKit customGKit : gkits) {
                if (!customGKit.isDefault()) continue;
                return customGKit;
            }
        }
        return null;
    }

    private ItemStack getKitItem(GlobalKits kit) {
        ArrayList lore = Lists.newArrayList();
        lore.addAll(kit.getItemLore());
        if (this.getCustomGkits(kit) != null) {
            int kits = this.getCustomGkits(kit).size();
            lore.add(ChatColor.GREEN.toString() + kits + " Custom Kit(s)");
        }
        lore.add("");
        lore.add((Object)ChatColor.GRAY + "Click to edit this Global Kit.");
        return ItemBuilder.buildItem(kit.getItemIcon(), 1, (short)0, (Object)ChatColor.YELLOW + ChatColor.BOLD.toString() + "GKIT: " + kit.getItemName(), lore);
    }

    private ItemStack getKitItem(Kit kit) {
        ArrayList lore = Lists.newArrayList();
        if (this.getCustomKits(kit) != null) {
            int kits = this.getCustomKits(kit).size();
            lore.add(ChatColor.GREEN.toString() + kits + " Custom Kit(s)");
        }
        lore.add("");
        lore.add((Object)ChatColor.GRAY + "Click to edit this kit.");
        return ItemBuilder.buildItem(kit.getMaterial(), 1, kit.getData(), (Object)ChatColor.YELLOW + ChatColor.BOLD.toString() + kit.getName(true), lore);
    }

    public static DuelPlayer getDuelPlayer(UUID uuid) {
        return duelPlayers.get(uuid);
    }

    public static DuelPlayer getDuelPlayer(Player player) {
        return duelPlayers.get(player.getUniqueId());
    }

    public static void removeDuelPlayer(Player player) {
        duelPlayers.remove(player.getUniqueId());
    }

    public static void setDuelPlayers(Map<UUID, DuelPlayer> duelPlayers) {
        DuelPlayer.duelPlayers = duelPlayers;
    }

    public String getName() {
        return this.name;
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public Map<Kit, List<CustomKit>> getCustomKits() {
        return this.customKits;
    }

    public Map<GlobalKits, List<CustomGKit>> getCustomGlobalKits() {
        return this.customGlobalKits;
    }

    public Kit getKitModifying() {
        return this.kitModifying;
    }

    public void setKitModifying(Kit kitModifying) {
        this.kitModifying = kitModifying;
    }

    public CustomKit getCurrentlyModifying() {
        return this.currentlyModifying;
    }

    public void setCurrentlyModifying(CustomKit currentlyModifying) {
        this.currentlyModifying = currentlyModifying;
    }

    public GlobalKits getGKitModifying() {
        return this.gKitModifying;
    }

    public void setGKitModifying(GlobalKits gKitModifying) {
        this.gKitModifying = gKitModifying;
    }

    public CustomGKit getGKitCurrentlyModifying() {
        return this.gKitCurrentlyModifying;
    }

    public void setGKitCurrentlyModifying(CustomGKit gKitCurrentlyModifying) {
        this.gKitCurrentlyModifying = gKitCurrentlyModifying;
    }

    public CustomGodSet getCustomGodSet() {
        return this.customGodSet;
    }

    public void setCustomGodSet(CustomGodSet customGodSet) {
        this.customGodSet = customGodSet;
    }

    public int getRankedWins() {
        return this.rankedWins;
    }

    public int getWins() {
        return this.wins;
    }

    public void setRankedWins(int rankedWins) {
        this.rankedWins = rankedWins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getRankedLosses() {
        return this.rankedLosses;
    }

    public int getLosses() {
        return this.losses;
    }

    public void setRankedLosses(int rankedLosses) {
        this.rankedLosses = rankedLosses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public void setElo(double elo) {
        this.elo = elo;
    }

    public long getLastRankedMatch() {
        return this.lastRankedMatch;
    }

    public void setLastRankedMatch(long lastRankedMatch) {
        this.lastRankedMatch = lastRankedMatch;
    }

    public boolean isPendingDeletion() {
        return this.pendingDeletion;
    }

    public void setPendingDeletion(boolean pendingDeletion) {
        this.pendingDeletion = pendingDeletion;
    }
}

