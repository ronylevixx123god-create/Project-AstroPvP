/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.Material
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event
 *  org.bukkit.event.Event$Result
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.block.Action
 *  org.bukkit.event.entity.PlayerDeathEvent
 *  org.bukkit.event.inventory.ClickType
 *  org.bukkit.event.inventory.InventoryAction
 *  org.bukkit.event.inventory.InventoryClickEvent
 *  org.bukkit.event.inventory.InventoryDragEvent
 *  org.bukkit.event.inventory.InventoryType
 *  org.bukkit.event.inventory.InventoryType$SlotType
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.event.player.PlayerItemBreakEvent
 *  org.bukkit.inventory.ItemStack
 */
package me.astropvp.events;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.inventory.ItemStack;

public class ArmorListener
implements Listener {
    private final List<String> blockedMaterials;

    public ArmorListener(List<String> blockedMaterials) {
        this.blockedMaterials = blockedMaterials;
    }

    @EventHandler
    public final void inventoryClick(InventoryClickEvent e) {
        try {
            boolean shift = false;
            boolean numberkey = false;
            if (e.isCancelled()) {
                return;
            }
            if (e.getAction() == InventoryAction.NOTHING) {
                return;
            }
            if (e.getClick().equals((Object)ClickType.SHIFT_LEFT) || e.getClick().equals((Object)ClickType.SHIFT_RIGHT)) {
                shift = true;
            }
            if (e.getClick().equals((Object)ClickType.NUMBER_KEY)) {
                numberkey = true;
            }
            if (e.getSlotType() != InventoryType.SlotType.ARMOR && e.getSlotType() != InventoryType.SlotType.QUICKBAR && e.getSlotType() != InventoryType.SlotType.CONTAINER) {
                return;
            }
            if (e.getClickedInventory() != null && !e.getClickedInventory().getType().equals((Object)InventoryType.PLAYER)) {
                return;
            }
            if (!e.getInventory().getType().equals((Object)InventoryType.CRAFTING) && !e.getInventory().getType().equals((Object)InventoryType.PLAYER)) {
                return;
            }
            if (!(e.getWhoClicked() instanceof Player)) {
                return;
            }
            ArmorType newArmorType = ArmorType.matchType(shift ? e.getCurrentItem() : e.getCursor());
            if (!shift && newArmorType != null && e.getRawSlot() != newArmorType.getSlot()) {
                return;
            }
            if (shift) {
                newArmorType = ArmorType.matchType(e.getCurrentItem());
                if (newArmorType != null) {
                    boolean equipping = true;
                    if (e.getRawSlot() == newArmorType.getSlot()) {
                        equipping = false;
                    }
                    if (newArmorType.equals((Object)ArmorType.HELMET) && (!equipping ? !this.isAirOrNull(e.getWhoClicked().getInventory().getHelmet()) : this.isAirOrNull(e.getWhoClicked().getInventory().getHelmet())) || newArmorType.equals((Object)ArmorType.CHESTPLATE) && (!equipping ? !this.isAirOrNull(e.getWhoClicked().getInventory().getChestplate()) : this.isAirOrNull(e.getWhoClicked().getInventory().getChestplate())) || newArmorType.equals((Object)ArmorType.LEGGINGS) && (!equipping ? !this.isAirOrNull(e.getWhoClicked().getInventory().getLeggings()) : this.isAirOrNull(e.getWhoClicked().getInventory().getLeggings())) || newArmorType.equals((Object)ArmorType.BOOTS) && (equipping ? this.isAirOrNull(e.getWhoClicked().getInventory().getBoots()) : !this.isAirOrNull(e.getWhoClicked().getInventory().getBoots()))) {
                        ArmorEquipEvent armorEquipEvent = new ArmorEquipEvent((Player)e.getWhoClicked(), ArmorEquipEvent.EquipMethod.SHIFT_CLICK, newArmorType, equipping ? null : e.getCurrentItem(), equipping ? e.getCurrentItem() : null);
                        Bukkit.getServer().getPluginManager().callEvent((Event)armorEquipEvent);
                        if (armorEquipEvent.isCancelled()) {
                            e.setCancelled(true);
                        }
                    }
                }
            } else {
                ItemStack newArmorPiece = e.getCursor();
                ItemStack oldArmorPiece = e.getCurrentItem();
                if (numberkey) {
                    if (e.getClickedInventory().getType().equals((Object)InventoryType.PLAYER)) {
                        ItemStack hotbarItem = e.getClickedInventory().getItem(e.getHotbarButton());
                        if (!this.isAirOrNull(hotbarItem)) {
                            newArmorType = ArmorType.matchType(hotbarItem);
                            newArmorPiece = hotbarItem;
                            oldArmorPiece = e.getClickedInventory().getItem(e.getSlot());
                        } else {
                            newArmorType = ArmorType.matchType(!this.isAirOrNull(e.getCurrentItem()) ? e.getCurrentItem() : e.getCursor());
                        }
                    }
                } else if (this.isAirOrNull(e.getCursor()) && !this.isAirOrNull(e.getCurrentItem())) {
                    newArmorType = ArmorType.matchType(e.getCurrentItem());
                }
                if (newArmorType != null && e.getRawSlot() == newArmorType.getSlot()) {
                    ArmorEquipEvent.EquipMethod method = ArmorEquipEvent.EquipMethod.PICK_DROP;
                    if (e.getAction().equals((Object)InventoryAction.HOTBAR_SWAP) || numberkey) {
                        method = ArmorEquipEvent.EquipMethod.HOTBAR_SWAP;
                    }
                    ArmorEquipEvent armorEquipEvent = new ArmorEquipEvent((Player)e.getWhoClicked(), method, newArmorType, oldArmorPiece, newArmorPiece);
                    Bukkit.getServer().getPluginManager().callEvent((Event)armorEquipEvent);
                    if (armorEquipEvent.isCancelled()) {
                        e.setCancelled(true);
                    }
                }
            }
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @EventHandler
    public void playerInteractEvent(PlayerInteractEvent e) {
        if (e.getAction() == Action.PHYSICAL) {
            return;
        }
        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            ArmorType newArmorType;
            Player player = e.getPlayer();
            if (e.getClickedBlock() != null && e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                Material mat = e.getClickedBlock().getType();
                for (String s : this.blockedMaterials) {
                    if (!mat.name().equalsIgnoreCase(s)) continue;
                    return;
                }
            }
            if ((newArmorType = ArmorType.matchType(e.getItem())) != null && (newArmorType.equals((Object)ArmorType.HELMET) && this.isAirOrNull(e.getPlayer().getInventory().getHelmet()) || newArmorType.equals((Object)ArmorType.CHESTPLATE) && this.isAirOrNull(e.getPlayer().getInventory().getChestplate()) || newArmorType.equals((Object)ArmorType.LEGGINGS) && this.isAirOrNull(e.getPlayer().getInventory().getLeggings()) || newArmorType.equals((Object)ArmorType.BOOTS) && this.isAirOrNull(e.getPlayer().getInventory().getBoots()))) {
                ArmorEquipEvent armorEquipEvent = new ArmorEquipEvent(e.getPlayer(), ArmorEquipEvent.EquipMethod.HOTBAR, ArmorType.matchType(e.getItem()), null, e.getItem());
                Bukkit.getServer().getPluginManager().callEvent((Event)armorEquipEvent);
                if (armorEquipEvent.isCancelled()) {
                    e.setCancelled(true);
                    player.updateInventory();
                }
            }
        }
    }

    @EventHandler
    public void inventoryDrag(InventoryDragEvent event) {
        ArmorType type = ArmorType.matchType(event.getOldCursor());
        if (event.getRawSlots().isEmpty()) {
            return;
        }
        if (type != null && type.getSlot() == event.getRawSlots().stream().findFirst().orElse(0).intValue()) {
            ArmorEquipEvent armorEquipEvent = new ArmorEquipEvent((Player)event.getWhoClicked(), ArmorEquipEvent.EquipMethod.DRAG, type, null, event.getOldCursor());
            Bukkit.getServer().getPluginManager().callEvent((Event)armorEquipEvent);
            if (armorEquipEvent.isCancelled()) {
                event.setResult(Event.Result.DENY);
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void itemBreakEvent(PlayerItemBreakEvent e) {
        ArmorType type = ArmorType.matchType(e.getBrokenItem());
        if (type != null) {
            Player p = e.getPlayer();
            ArmorEquipEvent armorEquipEvent = new ArmorEquipEvent(p, ArmorEquipEvent.EquipMethod.BROKE, type, e.getBrokenItem(), null);
            Bukkit.getServer().getPluginManager().callEvent((Event)armorEquipEvent);
            if (armorEquipEvent.isCancelled()) {
                ItemStack i = e.getBrokenItem().clone();
                i.setAmount(1);
                i.setDurability((short)(i.getDurability() - 1));
                if (type.equals((Object)ArmorType.HELMET)) {
                    p.getInventory().setHelmet(i);
                } else if (type.equals((Object)ArmorType.CHESTPLATE)) {
                    p.getInventory().setChestplate(i);
                } else if (type.equals((Object)ArmorType.LEGGINGS)) {
                    p.getInventory().setLeggings(i);
                } else if (type.equals((Object)ArmorType.BOOTS)) {
                    p.getInventory().setBoots(i);
                }
            }
        }
    }

    @EventHandler
    public void playerDeathEvent(PlayerDeathEvent e) {
        Player p = e.getEntity();
        for (ItemStack i : p.getInventory().getArmorContents()) {
            if (this.isAirOrNull(i)) continue;
            Bukkit.getServer().getPluginManager().callEvent((Event)new ArmorEquipEvent(p, ArmorEquipEvent.EquipMethod.DEATH, ArmorType.matchType(i), i, null));
        }
    }

    private boolean isAirOrNull(ItemStack item) {
        return item == null || item.getType().equals((Object)Material.AIR);
    }
}

