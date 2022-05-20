package me.astropvp.astropvpskins.armorequip;

import org.bukkit.entity.*;
import org.bukkit.inventory.*;
import org.bukkit.event.block.*;
import org.bukkit.*;
import java.util.*;
import org.bukkit.event.inventory.*;
import org.bukkit.event.*;
import org.bukkit.event.player.*;
import org.bukkit.event.entity.*;

public class ArmorListener implements Listener
{
    private final List<String> blockedMaterials;

    public ArmorListener(final List<String> blockedMaterials) {
        this.blockedMaterials = blockedMaterials;
    }

    @EventHandler
    public final void inventoryClick(final InventoryClickEvent e) {
        try {
            boolean shift = false;
            boolean numberkey = false;
            if (e.isCancelled()) {
                return;
            }
            if (e.getAction() == InventoryAction.NOTHING) {
                return;
            }
            if (e.getClick().equals(ClickType.SHIFT_LEFT) || e.getClick().equals(ClickType.SHIFT_RIGHT)) {
                shift = true;
            }
            if (e.getClick().equals(ClickType.NUMBER_KEY)) {
                numberkey = true;
            }
            if (e.getSlotType() != InventoryType.SlotType.ARMOR && e.getSlotType() != InventoryType.SlotType.QUICKBAR && e.getSlotType() != InventoryType.SlotType.CONTAINER) {
                return;
            }
            if (e.getClickedInventory() != null && !e.getClickedInventory().getType().equals(InventoryType.PLAYER)) {
                return;
            }
            if (!e.getInventory().getType().equals(InventoryType.CRAFTING) && !e.getInventory().getType().equals(InventoryType.PLAYER)) {
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
                    Label_0493: {
                        if (newArmorType.equals(ArmorType.HELMET)) {
                            if (equipping) {
                                if (this.isAirOrNull(e.getWhoClicked().getInventory().getHelmet())) {
                                    break Label_0493;
                                }
                            }
                            else if (!this.isAirOrNull(e.getWhoClicked().getInventory().getHelmet())) {
                                break Label_0493;
                            }
                        }
                        if (newArmorType.equals(ArmorType.CHESTPLATE)) {
                            if (equipping) {
                                if (this.isAirOrNull(e.getWhoClicked().getInventory().getChestplate())) {
                                    break Label_0493;
                                }
                            }
                            else if (!this.isAirOrNull(e.getWhoClicked().getInventory().getChestplate())) {
                                break Label_0493;
                            }
                        }
                        if (newArmorType.equals(ArmorType.LEGGINGS)) {
                            if (equipping) {
                                if (this.isAirOrNull(e.getWhoClicked().getInventory().getLeggings())) {
                                    break Label_0493;
                                }
                            }
                            else if (!this.isAirOrNull(e.getWhoClicked().getInventory().getLeggings())) {
                                break Label_0493;
                            }
                        }
                        if (!newArmorType.equals(ArmorType.BOOTS)) {
                            return;
                        }
                        if (equipping) {
                            if (!this.isAirOrNull(e.getWhoClicked().getInventory().getBoots())) {
                                return;
                            }
                        }
                        else if (this.isAirOrNull(e.getWhoClicked().getInventory().getBoots())) {
                            return;
                        }
                    }
                    final ArmorEquipEvent armorEquipEvent = new ArmorEquipEvent((Player)e.getWhoClicked(), ArmorEquipEvent.EquipMethod.SHIFT_CLICK, newArmorType, equipping ? null : e.getCurrentItem(), equipping ? e.getCurrentItem() : null);
                    Bukkit.getServer().getPluginManager().callEvent(armorEquipEvent);
                    if (armorEquipEvent.isCancelled()) {
                        e.setCancelled(true);
                    }
                }
            }
            else {
                ItemStack newArmorPiece = e.getCursor();
                ItemStack oldArmorPiece = e.getCurrentItem();
                if (numberkey) {
                    if (e.getClickedInventory().getType().equals(InventoryType.PLAYER)) {
                        final ItemStack hotbarItem = e.getClickedInventory().getItem(e.getHotbarButton());
                        if (!this.isAirOrNull(hotbarItem)) {
                            newArmorType = ArmorType.matchType(hotbarItem);
                            newArmorPiece = hotbarItem;
                            oldArmorPiece = e.getClickedInventory().getItem(e.getSlot());
                        }
                        else {
                            newArmorType = ArmorType.matchType(this.isAirOrNull(e.getCurrentItem()) ? e.getCursor() : e.getCurrentItem());
                        }
                    }
                }
                else if (this.isAirOrNull(e.getCursor()) && !this.isAirOrNull(e.getCurrentItem())) {
                    newArmorType = ArmorType.matchType(e.getCurrentItem());
                }
                if (newArmorType != null && e.getRawSlot() == newArmorType.getSlot()) {
                    ArmorEquipEvent.EquipMethod method = ArmorEquipEvent.EquipMethod.PICK_DROP;
                    if (e.getAction().equals(InventoryAction.HOTBAR_SWAP) || numberkey) {
                        method = ArmorEquipEvent.EquipMethod.HOTBAR_SWAP;
                    }
                    final ArmorEquipEvent armorEquipEvent2 = new ArmorEquipEvent((Player)e.getWhoClicked(), method, newArmorType, oldArmorPiece, newArmorPiece);
                    Bukkit.getServer().getPluginManager().callEvent(armorEquipEvent2);
                    if (armorEquipEvent2.isCancelled()) {
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
    public void playerInteractEvent(final PlayerInteractEvent e) {
        if (e.getAction() == Action.PHYSICAL) {
            return;
        }
        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            final Player player = e.getPlayer();
            if (e.getClickedBlock() != null && e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                final Material mat = e.getClickedBlock().getType();
                for (final String s : this.blockedMaterials) {
                    if (mat.name().equalsIgnoreCase(s)) {
                        return;
                    }
                }
            }
            final ArmorType newArmorType = ArmorType.matchType(e.getItem());
            if (newArmorType != null && ((newArmorType.equals(ArmorType.HELMET) && this.isAirOrNull(e.getPlayer().getInventory().getHelmet())) || (newArmorType.equals(ArmorType.CHESTPLATE) && this.isAirOrNull(e.getPlayer().getInventory().getChestplate())) || (newArmorType.equals(ArmorType.LEGGINGS) && this.isAirOrNull(e.getPlayer().getInventory().getLeggings())) || (newArmorType.equals(ArmorType.BOOTS) && this.isAirOrNull(e.getPlayer().getInventory().getBoots())))) {
                final ArmorEquipEvent armorEquipEvent = new ArmorEquipEvent(e.getPlayer(), ArmorEquipEvent.EquipMethod.HOTBAR, ArmorType.matchType(e.getItem()), null, e.getItem());
                Bukkit.getServer().getPluginManager().callEvent(armorEquipEvent);
                if (armorEquipEvent.isCancelled()) {
                    e.setCancelled(true);
                    player.updateInventory();
                }
            }
        }
    }

    @EventHandler
    public void inventoryDrag(final InventoryDragEvent event) {
        final ArmorType type = ArmorType.matchType(event.getOldCursor());
        if (event.getRawSlots().isEmpty()) {
            return;
        }
        if (type != null && type.getSlot() == event.getRawSlots().stream().findFirst().orElse(0)) {
            final ArmorEquipEvent armorEquipEvent = new ArmorEquipEvent((Player)event.getWhoClicked(), ArmorEquipEvent.EquipMethod.DRAG, type, null, event.getOldCursor());
            Bukkit.getServer().getPluginManager().callEvent(armorEquipEvent);
            if (armorEquipEvent.isCancelled()) {
                event.setResult(Event.Result.DENY);
                event.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void itemBreakEvent(final PlayerItemBreakEvent e) {
        final ArmorType type = ArmorType.matchType(e.getBrokenItem());
        if (type != null) {
            final Player p = e.getPlayer();
            final ArmorEquipEvent armorEquipEvent = new ArmorEquipEvent(p, ArmorEquipEvent.EquipMethod.BROKE, type, e.getBrokenItem(), null);
            Bukkit.getServer().getPluginManager().callEvent(armorEquipEvent);
            if (armorEquipEvent.isCancelled()) {
                final ItemStack i = e.getBrokenItem().clone();
                i.setAmount(1);
                i.setDurability((short)(i.getDurability() - 1));
                if (type.equals(ArmorType.HELMET)) {
                    p.getInventory().setHelmet(i);
                }
                else if (type.equals(ArmorType.CHESTPLATE)) {
                    p.getInventory().setChestplate(i);
                }
                else if (type.equals(ArmorType.LEGGINGS)) {
                    p.getInventory().setLeggings(i);
                }
                else if (type.equals(ArmorType.BOOTS)) {
                    p.getInventory().setBoots(i);
                }
            }
        }
    }

    @EventHandler
    public void onQuit(final PlayerQuitEvent event) {
        final Player player = event.getPlayer();
        for (final ItemStack item : player.getInventory().getContents()) {
            if (!this.isAirOrNull(item)) {
                final ArmorType type = ArmorType.matchType(item);
                final ArmorEquipEvent armorEquipEvent = new ArmorEquipEvent(player, ArmorEquipEvent.EquipMethod.QUIT_SERVER, type, item, null);
                Bukkit.getServer().getPluginManager().callEvent(armorEquipEvent);
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void playerDeathEvent(final PlayerDeathEvent e) {
        final Player p = e.getEntity();
        for (final ItemStack i : p.getInventory().getArmorContents()) {
            if (!this.isAirOrNull(i)) {
                Bukkit.getServer().getPluginManager().callEvent(new ArmorEquipEvent(p, ArmorEquipEvent.EquipMethod.DEATH, ArmorType.matchType(i), i, null));
            }
        }
    }

    private boolean isAirOrNull(final ItemStack item) {
        return item == null || item.getType().equals(Material.AIR);
    }
}
