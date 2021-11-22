package net.astropvp.core.listeners;

import com.compilex.CosmicArmorSets.CosmicArmorSetsAPI;
import com.compilex.CosmicArmorSets.enums.ArmorSet;
import com.compilex.CosmicOutposts.CosmicOutpostsAPI;
import me.astropvp.astropvpskins.AstroItemSkinsAPI;
import me.astropvp.astropvpskins.enums.SkinType;
import me.ifamasssxd.cosmichcf.api.HCFOutpostAPI;
import me.ifamasssxd.cosmichcf.struct.OutpostType;
import me.ifamasssxd.cosmicmasks.MaskType;
import net.astropvp.CollectPlugin;
import net.astropvp.core.AstroCore;
import net.astropvp.core.utils.HolyWhiteScrollUtils;
import com.cosmicpvp.cosmicutils.CosmicUtils;
import com.cosmicpvp.cosmicutils.customevents.CustomCosmicPlayerEvent;
import com.cosmicpvp.cosmicutils.exceptions.CosmicException;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.FactionUtils;
import com.cosmicpvp.cosmicutils.utils.ItemUtils;
import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.astropvp.struct.CollectCategory;
import net.astropvp.struct.CollectType;
import net.minecraft.server.v1_8_R3.ItemPotion;
import org.arkhamnetwork.Arkkit.patches.ExpBottles;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;

public class HolyWhiteScrollListener implements Listener {
    private HashMap<String, List<ItemStack>> savedItems = new HashMap<>();

    public HashMap<String, List<ItemStack>> getSavedItems() {
        return this.savedItems;
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onPlayerApplyHolyWhiteScroll(InventoryClickEvent e) {
        if (e.getClickedInventory() == null || e.getWhoClicked().getOpenInventory() == null || e.getWhoClicked().getOpenInventory().getBottomInventory() == null || !e.getClickedInventory().equals(e.getWhoClicked().getOpenInventory().getBottomInventory()))
            return;
        if (e.getCursor() != null && HolyWhiteScrollUtils.isOldHolyWhiteScroll(e.getCursor()))
            try {
                NBTWrapper cursor = new NBTWrapper(e.getCursor());
                if (cursor.getconaxData() != null)
                    return;
                NBTWrapper wrapper = (e.getCurrentItem() != null && e.getCurrentItem().getType() != Material.AIR) ? new NBTWrapper(e.getCurrentItem()) : null;
                AstroCore.getInstance().getHolyWhitescrollItem().onInventoryClick((Player)e.getWhoClicked(), e.getCursor(), cursor, wrapper, e.getCurrentItem(), e);
            } catch (CosmicException e2) {
                ((Player)e.getWhoClicked()).sendMessage(e2.getMessage());
                e.setCancelled(true);
            }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerDeath(PlayerDeathEvent e) {
        if(e.getEntity().getKiller() == null)
            return;
        if(e.getKeepInventory())
            return;
        if (!e.getEntity().getLocation().getWorld().getName().equals("world"))
            return;
        if (!e.getEntity().getKiller().getLocation().getWorld().getName().equals("world"))
            return;
        //if ((e.getEntity() != null && e.getEntity() == null) || (e.getEntity().getKiller() != null && e.getEntity().getKiller() == null))
            //return;
        Player killer = e.getEntity().getKiller();
        boolean wearingMonopolyMask = (killer != null && MaskType.MONOPOLY.hasEquipped(killer));
        boolean controlling = (killer != null && CosmicUtils.get().isOutpostsEnabled() && CosmicOutpostsAPI.controlsCosmonautOutpost(killer));
        List<ItemStack> savedItems = new ArrayList<>();
        List<ItemStack> drops = e.getDrops();
        for (ItemStack i : drops) {
            if (HolyWhiteScrollUtils.itemHasHolyWhitescroll(i)) {
                boolean mask = false;
                HolyWhiteScrollUtils.removeHolyWhitescroll(i);
                boolean killerIsControllingFrozen = (FactionUtils.getFaction(killer).isNormal() && HCFOutpostAPI.isControllingOutpost(FactionUtils.getFaction(killer), OutpostType.THE_GREAT_HALL));
                boolean isBansheed = e.getEntity().hasMetadata("banshee_scrolls");
                boolean isControlling = (controlling && Math.random() > 0.1D);
                boolean isMonopolied = (wearingMonopolyMask && Math.random() <= (killerIsControllingFrozen ? 0.48D : 0.33D));
                boolean isTravAxe = false;
                boolean isKilledHoldingTravAxe = false;
                if (killer != null && CosmicArmorSetsAPI.getArmorSet(killer) == ArmorSet.DIMENSIONAL_TRAVELER) {
                    boolean flag = false;
                    for (ItemStack item : killer.getInventory().getContents()) {
                        if (item != null && item.getType() == Material.DIAMOND_AXE && (new NBTWrapper(item)).getString("cosmicType").equals("TRAVELER_AXE"))
                            flag = true;
                    }
                    isTravAxe = (Math.random() < 0.5D && flag);
                }
                if (e.getEntity() != null && CosmicArmorSetsAPI.getArmorSet(e.getEntity()) == ArmorSet.DIMENSIONAL_TRAVELER)
                    for (ItemStack item2 : e.getEntity().getInventory().getContents()) {
                        if (item2 != null && item2.getType() == Material.DIAMOND_AXE && (new NBTWrapper(item2)).getString("cosmicType").equals("TRAVELER_AXE"))
                            isKilledHoldingTravAxe = true;
                    }
                assert killer != null;
                if (CosmicArmorSetsAPI.getArmorSet(killer) != null && CosmicArmorSetsAPI.getArmorSet(killer) == ArmorSet.ULTRA && Math.random() < 0.2D) {
                    e.getEntity().sendMessage(CC.AquaB + "* ULTRA NEGATION [" + CC.Gray + killer.getName() + " (- " + ItemUtils.getDisplayName(i) + CC.Gray + ")" + CC.AquaB + "] *");
                    killer.sendMessage(CC.AquaB + "* ULTRA NEGATION [" + CC.Gray + e.getEntity().getName() + " (+ " + ItemUtils.getDisplayName(i) + CC.Gray + ")" + CC.AquaB + "] *");
                    Bukkit.getLogger().info("Dropping " + i + " due to " + killer.getName() + "'s Ultra Armor Set procing.");
                    continue;
                }
                if (!isBansheed && !isControlling && !isMonopolied && !isTravAxe) {
                    savedItems.add(i);
                    continue;
                }
                (new ItemStack[1])[0] = i;

                if (isMonopolied && !isKilledHoldingTravAxe) {
                    if (AstroItemSkinsAPI.hasSkinEquipped(e.getEntity().getPlayer(), SkinType.CHRISTMAS_HELMET_STAR) && Math.random() < 0.33) {
                        killer.sendMessage(CC.translate("&a&l* " + e.getEntity().getName() + " 's NORTH STAR &7Blocked Monopoly Mask &a&l* "));
                        e.getEntity().getPlayer().sendMessage(CC.translate("&a&l* NORTH STAR &7Saved you from getting monoed &a&l*"));
                        continue;
                    }
                    Bukkit.getPluginManager().callEvent(new CustomCosmicPlayerEvent(e.getEntity(), "HOLY_WHITESCROLL_DEATH", Lists.newArrayList(new ItemStack[1])));
                    e.getEntity().getWorld().playSound(e.getEntity().getLocation(), Sound.ITEM_BREAK, 5.0F, 1.2F);
                    e.getEntity().getWorld().playSound(e.getEntity().getLocation(), Sound.WITHER_SHOOT, 3.0F, 10.0F);
                    if (killerIsControllingFrozen && Math.random() <0.1) {
                        e.getEntity().sendMessage(CC.RedB + "* FROZEN STRONGHOLD MONOPOLY [" + CC.Gray + killer.getName() + " (- " + ItemUtils.getDisplayName(i) + CC.Gray + ")" + CC.RedB + "] *");
                        killer.sendMessage(CC.GreenB + "* FROZEN STRONGHOLD MONOPOLY [" + CC.Gray + e.getEntity().getName() + " (+ " + ItemUtils.getDisplayName(i) + CC.Gray + ")" + CC.GreenB + "] *");
                    } else {
                        e.getEntity().sendMessage(CC.RedB + "* MONOPOLY MASK [" + CC.Gray + killer.getName() + " (- " + ItemUtils.getDisplayName(i) + CC.Gray + ")" + CC.RedB + "] *");
                        killer.sendMessage(CC.GreenB + "* MONOPOLY MASK [" + CC.Gray + e.getEntity().getName() + " (+ " + ItemUtils.getDisplayName(i) + CC.Gray + ")" + CC.GreenB + "] *");
                    }
                    Bukkit.getLogger().info("Dropping " + i + " due to " + killer.getName() + "'s Monopoly Mask procing.");
                    continue;
                }
                if (isControlling) {
                    e.getEntity().sendMessage(CC.RedB + "* COSMONAUT OUTPOST PERK [" + CC.Gray + killer.getName() + " (- " + ItemUtils.getDisplayName(i) + CC.Gray + ")" + CC.RedB + "] *");
                    killer.sendMessage(CC.GreenB + "* COSMONAUT OUTPOST PERK [" + CC.Gray + e.getEntity().getName() + " (+ " + ItemUtils.getDisplayName(i) + CC.Gray + ")" + CC.GreenB + "] *");
                    Bukkit.getLogger().info("Dropping " + i + " due to " + killer.getName() + "'s Cosmonaut procing.");
                    continue;
                }
                if (isBansheed) {
                    e.getEntity().sendMessage(CC.RedB + "* BANSHEE RKO [" + CC.Gray + killer.getName() + " (- " + ItemUtils.getDisplayName(i) + CC.Gray + ")" + CC.RedB + "] *");
                    killer.sendMessage(CC.GreenB + "* BANSHEE RKO [" + CC.Gray + e.getEntity().getName() + " (+ " + ItemUtils.getDisplayName(i) + CC.Gray + ")" + CC.GreenB + "] *");
                    Bukkit.getLogger().info("Dropping " + i + " due to " + killer.getName() + "'s Banshee RKO procing.");
                    continue;
                }
                if (!isTravAxe)
                    continue;
                e.getEntity().sendMessage(CC.RedB + "* TRAVELER AXE HOLY WHITE SCROLL NEGATION [" + CC.Gray + killer.getName() + " (- " + ItemUtils.getDisplayName(i) + CC.Gray + ")" + CC.RedB + "] *");
                killer.sendMessage(CC.GreenB + "* TRAVELER AXE HOLY WHITE SCROLL NEGATION [" + CC.Gray + e.getEntity().getName() + " (+ " + ItemUtils.getDisplayName(i) + CC.Gray + ")" + CC.GreenB + "] *");
                Bukkit.getLogger().info("Dropping " + i + " due to " + killer.getName() + "'s Traveler Axe White Scroll Negation procing.");
            }
        }
        for (ItemStack i : savedItems)
            e.getDrops().remove(i);
        if (savedItems.size() > 0)
            this.savedItems.put(e.getEntity().getUniqueId().toString(), savedItems);

        Player killed = e.getEntity();
        e.getDrops().add(ExpBottles.getXpBottle(killed.getTotalExperience(), killed.getName()));
        List<ItemStack> itemList = e.getDrops();
        itemList.removeIf(itemStack -> itemStack.getType() == Material.POTION);

        CollectPlugin.getInstance().getCollectManager().getPlayerCollect(killer).add(new CollectCategory(CollectType.DEATH_INVENTORY, itemList.toArray(new ItemStack[itemList.size() - 1]), CC.RedB + killed.getName() + "'s Death Inventory"));
        e.getDrops().clear();
        itemList.clear();
        killer.sendMessage("");
        killer.sendMessage(CC.translate("&3&l(&b&lCOLLECT&3&l)&b The Death Loot from &f" + killed.getName() + "&b has been put into your &f/collect&b."));
        killer.sendMessage("");
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerRespawn(PlayerRespawnEvent e) {
        if (e.getPlayer().getLastDamageCause() == null) return;
        if (e.getPlayer().getLastDamageCause().getCause() == null) return;

        if (e.getPlayer().getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.VOID) return;
        List<ItemStack> toReturn = this.savedItems.remove(e.getPlayer().getUniqueId().toString());
        if (toReturn != null) {
            for (ItemStack i : toReturn) {
                Bukkit.getLogger().info("[CosmicDungeons (HolyWhiteScroll)] Player " + e.getPlayer().getName() + " (" + e.getPlayer().getUniqueId() + ") respawned with Holy White Scroll item: " + i);
                e.getPlayer().getInventory().addItem(new ItemStack[] { HolyWhiteScrollUtils.removeHolyWhitescroll(i) });
            }
            Bukkit.getPluginManager().callEvent((Event)new CustomCosmicPlayerEvent(e.getPlayer(), "HOLY_WHITESCROLL_DEATH", new Object[] { toReturn }));
        }
    }
}
