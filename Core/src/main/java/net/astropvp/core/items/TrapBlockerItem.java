package net.astropvp.core.items;

import com.cosmicpvp.cosmicutils.exceptions.CosmicException;
import com.cosmicpvp.cosmicutils.exceptions.PrefixedException;
import com.cosmicpvp.cosmicutils.nbt.NBTTagCompoundBuilder;
import com.cosmicpvp.cosmicutils.utils.*;
import me.ifamasssxd.cosmicskycustoms.struct.CosmicItem;
import me.ifamasssxd.cosmicskycustoms.struct.InventoryInteractable;
import me.ifamasssxd.cosmicskycustoms.struct.InventoryItemType;
import net.astropvp.core.AstroCore;
import net.minecraft.server.v1_8_R3.NBTBase;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TrapBlockerItem extends CosmicItem
        implements InventoryInteractable {

    private static Map<UUID, Player> trappedPlayers;
    private static Map<UUID, Player> trapCooldown;




    public TrapBlockerItem() {
        super("trap_blocker");
        trappedPlayers = new HashMap<UUID, Player>();
        trapCooldown = new HashMap<UUID, Player>();
    }

    public ItemStack createItem(Player player, Object ... objects) {
        return this.applyItemData((new ItemBuilder(Material.BONE, CC.translate("&c&lTrap Blocker &7(Right-Click)")
                , CC.Gray + ""
                , CC.translate("&7&nRight-Click&7 to activate"),
                CC.translate("&7the &cTrap Blocker&7 ability."),
                CC.Gray + "",
                CC.translate("&7Players in a &f8x8&7 radius will"),
                CC.translate("&7not be able to break/place blocks"),
                CC.translate("&7anymore for &f15 seconds&7. "),
                CC.translate(""),
                CC.translate("&c&lUSES &f1")


        ).build())).build();
    }

    public boolean acceptsInventoryClickType(InventoryItemType inventoryItemType) {
        return inventoryItemType == InventoryItemType.CLICKED;
    }

    public void onInventoryClick(Player player, ItemStack cursor, NBTWrapper wrapper, NBTWrapper clickedWrapper, ItemStack clicked, InventoryClickEvent e) throws CosmicException {

    }

    public void onItemUse(Player player, ItemStack item, NBTWrapper wrapper, PlayerInteractEvent event) throws CosmicException {
        if (item == null) {
            return;
        }
        if (wrapper == null) {
            return;
        }
        if (item.getType() == Material.AIR) {
            return;
        }

        if (item.getType() != Material.BONE) {
            return;
        }


        if (!player.getLocation().getWorld().getName().equals("world") || !WorldGuardUtils.isPvPEnabled(player.getLocation())) {
            throw new PrefixedException("You cannot activate that ability here!");
        }

        if (trapCooldown.containsKey(player.getUniqueId())) {
            throw new PrefixedException("You cannot use this item yet (120s cooldown).");
        }





        NBTTagCompoundBuilder cosmicData = wrapper.getCompoundOrCreate("cosmicData");

        cosmicData.setInt("trap_blocker", 1);
        event.setCancelled(true);
        wrapper.setNBTBase("cosmicData", (NBTBase)cosmicData.get());
        item = wrapper.build();



        int amountOfPlayers=0;
        for (Entity entity : player.getNearbyEntities(10,10,10)) {
            if (!(entity instanceof Player))
                continue;
            amountOfPlayers++;
            Player p = (Player)entity;
            if (WorldGuardUtils.isPvPEnabled(p.getLocation()) && (FactionUtils.getFaction(p) != FactionUtils.getFaction(player) || FactionUtils.isWilderness(FactionUtils.getFaction(player)) && FactionUtils.isWilderness(FactionUtils.getFaction(p))) && !FactionUtils.isInAllyFaction(player, p)) {
                if (!isTrapped(p.getUniqueId())) {
                    trappedPlayers.put(p.getUniqueId(), p);
                    p.sendMessage(CC.translate("&c&l* TRAP BLOCKED &7by &f" + player.getName() + "&c&l *"));
                    p.playSound(p.getLocation(), Sound.ANVIL_BREAK, 10, 10);
                    amountOfPlayers++;
                }

                Bukkit.getScheduler().runTaskLaterAsynchronously(AstroCore.getInstance(), ()-> {
                    trappedPlayers.remove(p.getUniqueId());
                    p.sendMessage(CC.translate("&a&l* YOU ARE NO LONGER TRAP BLOCKED *"));
                }, 300L);
            }
        }

        if (amountOfPlayers == 0) {
            player.setItemInHand(new TrapBlockerItem().createItem(player));
            player.sendMessage(CC.translate("&c&lTRAP BLOCKER: &cThere are no available nearby enemies."));
            return;
        }

        player.sendMessage(CC.translate("&c&l* TRAP BLOCKED &f" + amountOfPlayers + "&7 nearby enemies &c&l*"));
        for (Player p : trappedPlayers.values()) {
            player.sendMessage(CC.translate(" &c&l* &7" + p.getName()));
        }
        trapCooldown.put(player.getUniqueId(),player);

        Bukkit.getScheduler().runTaskLater(AstroCore.getInstance(), ()-> {
            player.sendMessage(CC.translate("&a&l* YOU CAN NOW USE A NEW TRAP BLOCKER *"));
            trapCooldown.remove(player.getUniqueId());
        }, 2400L);
        player.playSound(player.getLocation(), Sound.ANVIL_LAND, 10, 10);

        if (item.getAmount() == 1) {
            player.setItemInHand(null);
        } else {
            item.setAmount(item.getAmount()-1);

        }
    }


    public static boolean isTrapped(UUID uuid) {
        return trappedPlayers.containsKey(uuid);
    }


    public static boolean isOnCooldown(UUID uuid) {
        return trapCooldown.containsKey(uuid);
    }
}

