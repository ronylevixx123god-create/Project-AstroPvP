package net.astropvp.core.items;

import com.cosmicpvp.cosmicutils.exceptions.CosmicException;
import com.cosmicpvp.cosmicutils.exceptions.PrefixedException;
import com.cosmicpvp.cosmicutils.nbt.NBTTagCompoundBuilder;
import com.cosmicpvp.cosmicutils.utils.*;
import com.massivecraft.factions.Factions;
import me.astropvp.astropvpskins.AstroItemSkinsAPI;
import me.astropvp.astropvpskins.enums.SkinType;
import me.ifamasssxd.cosmicskycustoms.struct.CosmicItem;
import me.ifamasssxd.cosmicskycustoms.struct.InventoryInteractable;
import me.ifamasssxd.cosmicskycustoms.struct.InventoryItemType;
import net.astropvp.core.AstroCore;
import net.astropvp.core.titles.events.TitleChangeEvent;
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

    private static Map<UUID, UUID> trappedPlayers;
    private static Map<UUID, UUID> trapCooldown;
    private static Map<UUID, UUID> trappedPlayersCooldown;


    public TrapBlockerItem() {
        super("trap_blocker");
        trappedPlayers = new HashMap<UUID, UUID>();
        trapCooldown = new HashMap<UUID, UUID>();
        trappedPlayersCooldown = new HashMap<>();
    }

    public ItemStack createItem(Player player, Object... objects) {
        return this.applyItemData((new ItemBuilder(Material.BONE, CC.translate("&c&lTrap Blocker &7(10x10 radius)")
                , CC.Gray + ""
                , CC.translate("&7&nRight-Click&7 to activate"),
                CC.translate("&7the &cTrap Blocker&7 ability."),
                CC.Gray + "",
                CC.translate("&7Players in a &f10x10&7 radius will"),
                CC.translate("&7not be able to break/place blocks"),
                CC.translate("&7and open doors anymore for &f15 seconds&7. "),
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


        if (!player.getLocation().getWorld().getName().equals("world") && !(player.getWorld().getName().equals("flat")) || !WorldGuardUtils.isPvPEnabled(player.getLocation())) {
            throw new PrefixedException("You cannot activate that ability here!");
        }

        if (trapCooldown.containsKey(player.getUniqueId())) {
            throw new PrefixedException("You cannot use this item yet (120s cooldown).");
        }


        NBTTagCompoundBuilder cosmicData = wrapper.getCompoundOrCreate("cosmicData");

        cosmicData.setInt("trap_blocker", 1);
        event.setCancelled(true);
        wrapper.setNBTBase("cosmicData", (NBTBase) cosmicData.get());
        item = wrapper.build();


        if (FactionUtils.getFaction(player) == null) {
            throw new PrefixedException("You cannot use this item when you don't have a faction!");
        }
        int amountOfPlayers = 0;
        for (Entity entity : player.getNearbyEntities(10, 10, 10)) {
            if (!(entity instanceof Player))
                continue;
            Player p = (Player) entity;
            UUID puuid = p.getUniqueId();
            try {
                if (FactionUtils.getFaction(p) == null) continue;
                if (WorldGuardUtils.isPvPEnabled(p.getLocation()) && (FactionUtils.getFaction(p) != FactionUtils.getFaction(player)) && !FactionUtils.isInAllyFaction(player, p) && !GeneralUtil.isAtleastTruce(player, p)) {
                    if (!isTrapped(p.getUniqueId())) {
                        if (MaskUtils.hasMaskEquipped(p, "ELF") && Math.random() <= 0.25) {
                            p.sendMessage(CC.translate("&a&l * ELF MASK &7Blocked &cTrap Blocker &a&l*"));
                            continue;
                        }

                        if (AstroItemSkinsAPI.hasSkinEquipped(p, SkinType.EASTER_2022_BACKPACK) || AstroItemSkinsAPI.hasSkinEquipped(p, SkinType.EASTER_2022_MUTATED_BACKPACK)) {
                            p.sendMessage(CC.translate("&d&l* Door to Wonderland &7Blocked &cTrap Blocker &d&l*"));
                            continue;
                        }

                        if (trappedPlayersCooldown.containsKey(puuid)) {
                            player.sendMessage(CC.translate("&c&l(Trap Blocker)&c " + p.getName() + " is still on trap blocker cooldown, this lasts 5m."));
                            continue;
                        }
                        trappedPlayers.put(p.getUniqueId(), puuid);
                        trappedPlayersCooldown.put(p.getUniqueId(), p.getUniqueId());
                        p.sendMessage(CC.translate("&c&l* TRAP BLOCKED &7by &f" + player.getName() + "&c&l *"));
                        p.playSound(p.getLocation(), Sound.ANVIL_BREAK, 1, 1);
                        amountOfPlayers++;
                    }


                    Bukkit.getScheduler().runTaskLaterAsynchronously(AstroCore.getInstance(), () -> {
                        trappedPlayers.remove(puuid);
                        p.sendMessage(CC.translate("&a&l* YOU ARE NO LONGER TRAP BLOCKED *"));
                    }, 20 *15);

                    Bukkit.getScheduler().runTaskLaterAsynchronously(AstroCore.getInstance(), () -> {
                        trappedPlayersCooldown.remove(puuid);
                        p.sendMessage(CC.translate("&c&l(Trap Blocker)&c You can now get trap blocked again."));
                    }, 20 * 300);
                }
            } catch (Exception e) {
                trappedPlayers.remove(puuid);
                e.printStackTrace();
            }

            if (amountOfPlayers == 0) {
                throw new PrefixedException("There are no available nearby enemies.");
            }

            if (item.getAmount() == 1) {
                player.setItemInHand(null);
            } else {
                item.setAmount(item.getAmount() - 1);

            }


            player.sendMessage(CC.translate("&c&l* TRAP BLOCKED &f" + amountOfPlayers + "&7 nearby enemies &c&l*"));
            for (UUID u : trappedPlayers.values()) {
                player.sendMessage(CC.translate(" &c&l* &7" + Bukkit.getPlayer(u).getName()));
            }
            trapCooldown.put(player.getUniqueId(), player.getUniqueId());

            Bukkit.getScheduler().runTaskLater(AstroCore.getInstance(), () -> {
                player.sendMessage(CC.translate("&a&l* YOU CAN NOW USE A NEW TRAP BLOCKER *"));
                trapCooldown.remove(player.getUniqueId());
            }, 2400L);

            player.playSound(player.getLocation(), Sound.ANVIL_LAND, 1, 1);


        }
    }


    public static boolean isTrapped(UUID uuid) {
        return trappedPlayers.containsKey(uuid);
    }


    public static boolean isOnCooldown(UUID uuid) {
        return trapCooldown.containsKey(uuid);
    }
}

