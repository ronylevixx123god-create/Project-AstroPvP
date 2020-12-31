package com.compilex.CosmicArmorSets.listeners;

import com.compilex.CosmicArmorSets.CosmicArmorSetsAPI;
import com.compilex.CosmicArmorSets.CosmicArmorSetsPlugin;
import com.compilex.CosmicArmorSets.enums.AbilityArmorSet;
import com.compilex.CosmicArmorSets.enums.ArmorSet;
import com.compilex.CosmicArmorSets.enums.armorsets.DragonSlayerArmorSet;
import com.cosmicpvp.cosmicutils.compat.ASound;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.MapMaker;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import me.ifamasssxd.cosmicskycustoms.struct.CustomItemBuilder;
import org.arkhamnetwork.Arkkit.patches.ItemID;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugins;
import org.arkhamnetwork.enchantments.MasteryCustomEnchantment;
import org.arkhamnetwork.enchantments.events.CustomEnchantProcOnEntityEvent;
import org.arkhamnetwork.enchantments.events.PlayerAttemptTinkerItem;
import org.arkhamnetwork.enchantments.libs.TinkeredItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.libs.com.google.gson.internal.Pair;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class ArmorSetCrystalTinkererListener implements Listener {
    private Map antiMkitsEquipped;

    public ArmorSetCrystalTinkererListener() {
        this.antiMkitsEquipped = (new MapMaker()).expireAfterWrite(1L, TimeUnit.SECONDS).makeMap();
    }

    @EventHandler(
            priority = EventPriority.LOWEST
    )
    public void onPlayerAttemptTinkerItem(PlayerAttemptTinkerItem event) {
        if (event.getItem() != null) {
            TinkeredItem item = event.getItem();
            if (item.getOfferForItem() == null) {
                ArmorSet set = ArmorSet.getArmorSetFromItem(item.getItem());
                if (set != null) {
                    event.setCancelled(false);
                    item.setXpValue(0.0D);
                    item.setOfferForItem(ArmorSet.createCrystal(Lists.newArrayList(new ArmorSet[]{set}), 20.0D));
                } else if (item.getItem() != null && item.getItem().getType() != Material.AIR) {
                    AbilityArmorSet fromArmor = AbilityArmorSet.getSetFromMasteryShard(item.getItem());
                    if (fromArmor != null) {
                        event.setCancelled(false);
                        item.setXpValue(0.0D);
                        item.setOfferForItem(fromArmor.createAntiMkitShard(item.getItem().getAmount()));
                    }
                }
            }
        }

    }

    @EventHandler
    public void onEnchantProc(CustomEnchantProcOnEntityEvent event) {
        if (event.getEnchant() != null && event.getTarget() instanceof Player) {
            String name = event.getEnchant().name();
            if (!name.equals("Silence") && !name.equals("IceAspect")) {
                if (event.getEnchant() instanceof MasteryCustomEnchantment && event.getTarget() instanceof Player) {
                    String armorSetFrom = ((MasteryCustomEnchantment)event.getEnchant()).getArmorSet();
                    if (armorSetFrom != null && !armorSetFrom.isEmpty()) {
                        Player target = (Player)event.getTarget();
                        Set equipped = this.getAntiMkitsEquipped(target);
                        if (equipped != null && equipped.contains(armorSetFrom)) {
                            if (target.hasMetadata("revoked")) {
                                return;
                            }

                            String customName = event.getPlayer() instanceof Player ? ((Player)event.getPlayer()).getName() : event.getPlayer().getCustomName();
                            event.setCancelled(true);
                            CosmicArmorSetsPlugin.get_().getLogger().info("Cancelling " + event.getEnchant().name() + " against " + target.getName() + " from " + customName + " due to Anti M-Kit!");
                            if (target.isOp()) {
                                target.sendMessage(CC.RedB + "DBG: " + CC.Red + "Anti M-Mkit Cancelled (" + armorSetFrom + ") from " + customName + " - " + event.getEnchant().name());
                            }
                        }
                    }
                }
            } else {
                Map equipped = CosmicArmorSetsAPI.getBonusCrystalsEquipped((Player)event.getTarget());
                Integer dragonSlayer = (Integer)equipped.get(ArmorSet.DRAGON_SLAYER);
                if (dragonSlayer != null) {
                    ((DragonSlayerArmorSet)ArmorSet.DRAGON_SLAYER.getArmorSetInfo()).onEnchantImmuneToProced((Player)event.getTarget(), (Player)event.getPlayer(), name, dragonSlayer, event);
                }
            }
        }

    }

    @EventHandler(
            priority = EventPriority.HIGHEST,
            ignoreCancelled = true
    )
    public void onInventoryClickShardsTogether(InventoryClickEvent event) {
        ItemStack clicked = event.getCurrentItem();
        ItemStack cursor = event.getCursor();
        if (event.getWhoClicked().getInventory() != null && event.getInventory() != null) {
            if (clicked != null && clicked.getType() != Material.AIR && cursor != null && cursor.getType() != Material.AIR) {
                if (clicked.getType() == cursor.getType()) {
                    if (event.getWhoClicked().getInventory() != null && event.getWhoClicked().getOpenInventory() != null && event.getWhoClicked().getInventory().equals(event.getWhoClicked().getOpenInventory().getBottomInventory())) {
                        NBTWrapper clickedWrap = new NBTWrapper(clicked);
                        NBTWrapper cursorWrap = new NBTWrapper(cursor);
                        Pair clickSet = AbilityArmorSet.getFromAntiMkitCrystal(clicked);
                        if (clickSet != null) {
                            Pair cursorSet = AbilityArmorSet.getFromAntiMkitCrystal(cursor);
                            if (cursorSet != null) {
                                Player player = (Player)event.getWhoClicked();
                                if (!player.getWorld().equals(Bukkit.getWorlds().get(0))) {
                                    player.sendMessage(CC.RedB + "(!) " + CC.Red + "You must combine these crystals in the Overworld!");
                                } else {
                                    event.setCancelled(true);
                                    if (!((Set)clickSet.first).equals(cursorSet.first)) {
                                        player.sendMessage(CC.RedB + "(!) " + CC.Red + "You must combine Anti M-Kit Crystals of the same type!");
                                    } else {
                                        int newTotal = (Integer)clickSet.second * clicked.getAmount() + (Integer)cursorSet.second * cursor.getAmount();
                                        if (newTotal > 100) {
                                            player.sendMessage(CC.RedB + "(!) " + CC.Red + "You cannot combine an Anti M-Kit Crystal over 100% success rate!");
                                        } else {
                                            boolean cursorBlack = false;
                                            if ((clickedWrap.getIID() == -1L || !ItemID.blacklist_iid.contains(clickedWrap.getIID())) && !(cursorBlack = cursorWrap.getIID() != -1L && ItemID.blacklist_iid.contains(cursorWrap.getIID()))) {
                                                if (ArkPlugins.ITEM_ID.getArkPlugin().isRunning()) {
                                                    if (clickedWrap.getIID() != -1L) {
                                                        ItemID.blacklistIID(clickedWrap.getIID(), player.getName() + " Combined ");
                                                    }

                                                    if (cursorWrap.getIID() != -1L) {
                                                        ItemID.blacklistIID(cursorWrap.getIID(), player.getName() + " Combined ");
                                                    }
                                                }

                                                player.setItemOnCursor((ItemStack)null);
                                                event.setCurrentItem((new CustomItemBuilder("ANTI_MKIT", new Object[]{newTotal, cursorSet.first})).build());
                                                ASound.LEVEL_UP.play(player, 10.0F, 1.1F);
                                                ASound.ORB_PICKUP.play(player, 10.0F, 1.1F);
                                            } else {
                                                player.sendMessage(CC.RedB + "(!) " + CC.Red + "Anti M-Kit crystal already redeemed!");
                                                if (cursorBlack) {
                                                    player.setItemOnCursor((ItemStack)null);
                                                } else {
                                                    event.setCurrentItem((ItemStack)null);
                                                }

                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public Set getAntiMkitsEquipped(Player player) {
        Set found = (Set)this.antiMkitsEquipped.get(player.getUniqueId());
        if (found == null) {
            found = new HashSet();
            ItemStack[] var3 = player.getInventory().getArmorContents();
            int var4 = var3.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                ItemStack armor = var3[var5];
                if (armor != null && armor.getType() != Material.AIR) {
                    Set fromArmor = AbilityArmorSet.getAntiMkitCrystal(armor);
                    if (fromArmor != null) {
                        Iterator var8 = fromArmor.iterator();

                        while(var8.hasNext()) {
                            AbilityArmorSet set = (AbilityArmorSet)var8.next();
                            ((Set)found).add(set.name());
                        }
                    }
                }
            }

            this.antiMkitsEquipped.put(player.getUniqueId(), found);
        }

        return (Set)found;
    }
}
