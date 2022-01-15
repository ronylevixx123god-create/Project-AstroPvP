/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.me.astropvp.cratekeys.net.astropvp.armorsets.utils.CC
 *  com.google.common.collect.Lists
 *  com.massivecraft.factions.FPlayer
 *  com.massivecraft.factions.FPlayers
 *  com.massivecraft.factions.iface.RelationParticipator
 *  net.minecraft.server.v1_7_R4.ItemStack
 *  net.minecraft.server.v1_7_R4.NBTBase
 *  net.minecraft.server.v1_7_R4.NBTTagCompound
 *  net.minecraft.server.v1_7_R4.NBTTagList
 *  org.arkhamnetwork.Arkkit.me.astropvp.cratekeys.net.astropvp.armorsets.utils.WorldGuardUtils
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.DyeColor
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.OfflinePlayer
 *  org.bukkit.craftbukkit.v1_7_R4.me.astropvp.cratekeys.inventory.CraftItemStack
 *  org.bukkit.entity.Player
 *  org.bukkit.me.astropvp.cratekeys.inventory.Inventory
 *  org.bukkit.me.astropvp.cratekeys.inventory.ItemStack
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.scheduler.BukkitRunnable
 */
package me.ifamasssxd.cosmicevolutionkits.structure;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.google.common.collect.Lists;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import me.cosmicpvp.CosmicGlobalKits.CosmicGlobalKits;
import me.ifamasssxd.cosmicevolutionkits.utils.*;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagList;
import org.arkhamnetwork.Arkkit.utils.WorldGuardUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class KitPlayer {
    public static Map<UUID, KitPlayer> kitPlayerMap;
    private final KitCooldowns kitCooldowns;
    private final Map<EvolutionKits, Integer> kitLevels;
    private final UUID uuid;
    private final String name;
    public boolean loadingData;
    private int amountClaimed;

    public KitPlayer(final UUID uuid, final String name) {
        this.kitCooldowns = new KitCooldowns();
        this.kitLevels = new HashMap<EvolutionKits, Integer>();
        this.loadingData = true;
        this.amountClaimed = 0;
        this.uuid = uuid;
        this.name = name;
    }

    public void loadData(final boolean async) {
        if (async) {
            Bukkit.getScheduler().scheduleAsyncDelayedTask(CosmicGlobalKits.getPlugin(), () -> this.loadData(false));
            return;
        }
        PreparedStatement statement = null;
        try {
            statement = CosmicGlobalKits.getPlugin().getSqlDatabase().getConnection().prepareStatement("SELECT kitName, lastKitUse, kitLevel, numberOfUses FROM `" +CosmicGlobalKits.getPlugin().getServerName() + "_evolutionKitData` WHERE uuid = ?;");
            statement.setString(1, this.uuid.toString());
            final ResultSet results = statement.executeQuery();
            while (results.next()) {
                final EvolutionKits kit = EvolutionKits.getFromName(results.getString("kitName"));
                if (kit == null) {
                    continue;
                }
                this.kitCooldowns.put(kit, results.getLong("lastKitUse"));
                this.kitLevels.put(kit, results.getInt("kitLevel"));
                this.amountClaimed = results.getInt("numberOfUses");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            if (statement != null) {
                try {
                    statement.close();
                }
                catch (Exception ex) {
                    e.printStackTrace();
                }
            }
        }
        finally {
            if (statement != null) {
                try {
                    statement.close();
                }
                catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
        this.loadingData = false;
    }

    public int getLevel(final EvolutionKits kit) {
        final Integer level = this.kitLevels.get(kit);
        if (level == null) {
            return 1;
        }
        return level;
    }

    public void levelUp(final Player player, final int level, final EvolutionKits parent, final String source) {
        this.getKitLevels().put(parent, level);
        player.sendMessage(ChatColor.YELLOW.toString() + ChatColor.BOLD + ">> " + ChatColor.YELLOW + "Congratulations! Your " + parent.getKit().getItemName() + ChatColor.YELLOW + " /vkit is now level " + ChatColor.YELLOW + ChatColor.UNDERLINE.toString() + level + ChatColor.YELLOW + "!");
        player.sendMessage(ChatColor.GRAY + "You will now receive higher enchantment levels the next time you claim this /vkit!");
        final String msg = (ChatColor.GRAY.toString() + ChatColor.BOLD + "** ").intern() + ChatColor.WHITE + player.getName() + "'s " + parent.getKit().getItemName() + ChatColor.GRAY + " /vkit has evolved to level " + ChatColor.WHITE + ChatColor.UNDERLINE + level + ChatColor.GRAY + ((source != null) ? (" using an " + CC.Green + source + CC.Gray + "!") : "!").intern();
        player.sendMessage("");
        if (level >= 8) {
            Bukkit.broadcastMessage(msg);
        }
        else {
            final FPlayer fplayer = FPlayers.i.get(player);
            for (final Player p : Bukkit.getOnlinePlayers()) {
                final FPlayer fp = FPlayers.i.get(p);
                if (!WorldGuardUtils.isPvPDisabled(p.getLocation()) && !fplayer.getRelationTo(fp).isMember()) {
                    continue;
                }
                p.sendMessage(msg);
            }
        }


        player.sendMessage(CC.GreenB + "Level " + level);
        player.sendMessage(CC.Gray +parent.getKit().getItemName() + ChatColor.GREEN + " Level UP!");
        new BukkitRunnable() {
            int counter = 0;

            public void run() {
                if (!player.isOnline() || player.isDead() || this.counter >= 6) {
                    this.cancel();
                    return;
                }
                RandomFireworks.getManager().launchRandomFirework(player.getLocation());
                ++this.counter;
            }
        }.runTaskTimer(CosmicGlobalKits.getPlugin(), 20L, 5L);
    }

    public void setCooldown(final EvolutionKits kit) {
        this.kitCooldowns.put(kit, System.currentTimeMillis());
        this.save(kit);
    }

    public void save(final EvolutionKits kit) {
        CosmicGlobalKits.getPlugin().getSqlQueryThread().addQuery("REPLACE INTO `" + CosmicGlobalKits.getPlugin().getServerName() + "_evolutionKitData`(uuid, name, kitName, kitLevel, lastKitUse, numberOfUses) VALUES ('" + this.uuid.toString() + "', '" + this.name + "', '" + kit.name() + "', '" + this.getLevel(kit) + "', '" + this.kitCooldowns.getOrDefault(kit, 0L) + "', '" + this.amountClaimed + "');");
    }

    public Inventory getKitInventory(final Player player) {
        final Inventory inventory = Bukkit.createInventory(null, InventoryUtils.getRoundedSize(EvolutionKits.values().length), "Evolution Kits");
        for (final EvolutionKits kit : EvolutionKits.values()) {
            final ItemStack item = this.getKitItem(kit, player);
            inventory.setItem(kit.getKit().getInventorySlot(), item);
        }
        return inventory;
    }

    public ItemStack getKitItem(final EvolutionKits kit, final Player player) {
        return this.getKitItem(kit, player, true);
    }

    public ItemStack getKitItem(final EvolutionKits kit, final Player player, final boolean unlockLore) {
        final EvolutionKit kitInstance = kit.getKit();
        List<String> lore = kitInstance.getLore();
        Material material;
        short data;
        if (player == null || this.hasAccess(player, kit)) {
            final List<String> newLore = Lists.newArrayList();
            final Integer level = this.kitLevels.get(kit);
            newLore.add(ChatColor.YELLOW.toString() + ChatColor.BOLD + "Kit Level: " + ChatColor.YELLOW + ChatColor.UNDERLINE + ((level == null) ? 1 : level) + ChatColor.YELLOW + " / 10");
            newLore.addAll(lore);
            lore = newLore;
            lore.add("");
            lore.add(ChatColor.GREEN + ChatColor.BOLD.toString() + "UNLOCKED");
            final String cooldownTimer = this.getCooldownTimer(kit);
            if (cooldownTimer != null) {
                material = Material.STAINED_GLASS_PANE;
                data = DyeColor.YELLOW.getWoolData();
                lore.add(ChatColor.YELLOW + "Claim again in " + ChatColor.YELLOW + ChatColor.BOLD.toString() + cooldownTimer);
            }
            else {
                material = kit.getKit().getDisplayMaterial();
                data = kit.getKit().getDisplayData();
                if (unlockLore) {
                    lore.add(ChatColor.GRAY + "Click to receive kit.");
                }
            }
        }
        else {
            material = Material.STAINED_GLASS_PANE;
            data = DyeColor.RED.getWoolData();
            lore.add("");
            lore.add(ChatColor.RED + ChatColor.BOLD.toString() + "LOCKED");
            if (unlockLore) {
                lore.add(ChatColor.GRAY + "Unlock at " + ChatColor.GRAY + ChatColor.UNDERLINE.toString() + "buy.astropvp.net");
            }
        }
        ItemStack item = ItemBuilder.buildItem(material, data, kitInstance.getItemName() + " Kit", lore);
        final net.minecraft.server.v1_8_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
        NBTTagCompound compound;
        if (!nmsItem.hasTag()) {
            compound = new NBTTagCompound();
            nmsItem.setTag(compound);
        }
        else {
            compound = nmsItem.getTag();
        }
        if (data == 4) {
            final NBTTagList enchList = new NBTTagList();
            compound.set("ench", enchList);
        }
        compound.setString("evolutionKit", kit.name());
        nmsItem.setTag(compound);
        item = CraftItemStack.asCraftMirror(nmsItem);
        return item;
    }

    public boolean hasAccess(final Player player, final EvolutionKits kit) {
        return player.hasPermission("cosmicevolutionkits.kit." + kit.name().toLowerCase());
    }

    public String getCooldownTimer(final EvolutionKits kit) {
        final Long lastUsed = this.kitCooldowns.get(kit);
        if (lastUsed == null) {
            return null;
        }
        final long timePassed = System.currentTimeMillis() - lastUsed;
        final long secondsLeft = (kit.getCooldown() - timePassed) / 1000L;
        if (timePassed > kit.getCooldown() || secondsLeft <= 0L) {
            this.kitCooldowns.remove(kit);
            return null;
        }
        return TimeUtils.formatDifference(secondsLeft);
    }

    public KitCooldowns getKitCooldowns() {
        return this.kitCooldowns;
    }

    public Map<EvolutionKits, Integer> getKitLevels() {
        return this.kitLevels;
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public boolean isLoadingData() {
        return this.loadingData;
    }

    public void setAmountClaimed(final int amountClaimed) {
        this.amountClaimed = amountClaimed;
    }

    public int getAmountClaimed() {
        return this.amountClaimed;
    }

    static {
        KitPlayer.kitPlayerMap = new HashMap<UUID, KitPlayer>();
    }
}