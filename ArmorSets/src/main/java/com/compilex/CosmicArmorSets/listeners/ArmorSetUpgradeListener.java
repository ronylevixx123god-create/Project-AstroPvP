/*     */
package com.compilex.CosmicArmorSets.listeners;
/*     */
/*     */

import com.compilex.CosmicArmorSets.CosmicArmorSetsAPI;
/*     */ import com.compilex.CosmicArmorSets.CosmicArmorSetsPlugin;
/*     */ import com.compilex.CosmicArmorSets.enums.ArmorSet;
/*     */ import com.compilex.CosmicArmorSets.enums.HeroicArmorType;
/*     */ import com.cosmicpvp.cosmicutils.nbt.NBTTagCompoundBuilder;
/*     */ import com.cosmicpvp.cosmicutils.utils.CC;
/*     */ import com.cosmicpvp.cosmicutils.utils.GeneralUtil;
/*     */ import com.cosmicpvp.cosmicutils.utils.ItemUtils;
/*     */ import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
/*     */ import com.cosmicpvp.cosmicutils.utils.PlayerUtil;
/*     */ import java.util.concurrent.ThreadLocalRandom;
/*     */ import net.minecraft.server.v1_7_R4.NBTBase;
import org.apache.commons.lang.StringUtils;
/*     */ import org.arkhamnetwork.Arkkit.libs.ParticleEffect;
/*     */ import org.arkhamnetwork.Arkkit.patches.ItemID;
/*     */ import org.arkhamnetwork.Arkkit.plugins.ArkPlugins;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.Sound;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.EventPriority;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.event.block.Action;
/*     */ import org.bukkit.event.inventory.ClickType;
/*     */ import org.bukkit.event.inventory.InventoryClickEvent;
/*     */ import org.bukkit.event.player.PlayerInteractEvent;
/*     */ import org.bukkit.inventory.ItemStack;

/*     */
/*     */ public class ArmorSetUpgradeListener implements Listener {
    /*     */
    @EventHandler(priority = EventPriority.NORMAL)
    /*     */ public void onPlayerInteract(PlayerInteractEvent event) {
        /*  35 */
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK && event.getAction() != Action.RIGHT_CLICK_AIR)
            /*  36 */ return;
        if (event.getAction() != Action.RIGHT_CLICK_AIR && event.isCancelled())
            /*     */ return;
        /*  38 */
        ItemStack item = event.getItem();
        /*  39 */
        if (item == null || item.getType() == Material.AIR)
            /*     */ return;
        /*  41 */
        Player player = event.getPlayer();
        /*     */
        /*  43 */
        NBTWrapper wrapper = new NBTWrapper(item);
        /*  44 */
        if (wrapper.getString("cosmicType").equals("RANDOM_ARMOR_UPGRADE")) {
            /*     */
            /*  46 */
            event.setCancelled(true);
            /*  47 */
            if (!player.getWorld().getName().equals(((World) Bukkit.getWorlds().get(0)).getName())) {
                /*  48 */
                player.sendMessage(CC.RedB + "(!) " + CC.Red + "You can only unlock a Armor Upgrade in the Overworld!");
                /*     */
                /*     */
                return;
                /*     */
            }
            /*  52 */
            if (ArkPlugins.ITEM_ID.getArkPlugin().isRunning() && item.getAmount() == 1) {
                /*  53 */
                long id = ItemID.getItemID(item);
                /*  54 */
                if (id != -1L) {
                    /*     */
                    /*  56 */
                    if (ItemID.blacklist_iid.contains(Long.valueOf(id))) {
                        /*  57 */
                        player.sendMessage(CC.RedB + "(!) " + CC.Red + "You attempted to claim an already claimed Upgrade!");
                        /*  58 */
                        player.setItemInHand(null);
                        /*  59 */
                        player.updateInventory();
                        /*  60 */
                        Bukkit.getLogger().info(player.getName() + " used a Blacklisted Random Armor Upgrade: " + id + " item: " + item);
                        /*     */
                        /*     */
                        return;
                        /*     */
                    }
                    /*  64 */
                    ItemID.blacklistIID(id, "random armor upgrade use " + player.getName());
                    /*     */
                }
                /*     */
            }
            /*     */
            /*  68 */
            ArmorSet received = CosmicArmorSetsAPI.getRandomArmorSetFromUpgrade();
            /*  69 */
            ItemStack retr = CosmicArmorSetsAPI.createArmorSetUpgrade(received, (1 + ThreadLocalRandom.current().nextInt(100)));
            /*     */
            /*  71 */
            if (item.getAmount() > 1) {
                /*  72 */
                item.setAmount(item.getAmount() - 1);
                /*  73 */
                PlayerUtil.giveOrDropProtectedItem(player, retr, -1);
                /*     */
            } else {
                /*  75 */
                player.setItemInHand(retr);
                /*  76 */
            }
            player.sendMessage(CC.GreenB + "(!) " + CC.Green + "You have received a " + ItemUtils.getDisplayName(retr) + CC.Green + " from your Random Heroic Upgrade!");
            /*  77 */
            player.updateInventory();
            /*  78 */
            player.playSound(player.getLocation(), Sound.LEVEL_UP, 1.0F, 1.1F);
            /*     */
        }
        /*     */
    }

    /*     */
    /*     */
    /*     */
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    /*     */ public void onInventoryClick(InventoryClickEvent event) {
        /*  85 */
        if (event.getClick() != ClickType.LEFT && event.getClick() != ClickType.RIGHT)
            /*     */ return;
        /*  87 */
        Player player = (Player) event.getWhoClicked();
        /*  88 */
        if (event.getWhoClicked().getInventory() == null || !event.getWhoClicked().getInventory().equals(player.getInventory())) {
            /*     */
            return;
            /*     */
        }
        /*     */
        /*  92 */
        ItemStack clicked = event.getCurrentItem();
        /*  93 */
        ItemStack cursor = event.getCursor();
        /*     */
        /*  95 */
        if (cursor == null)
            /*  96 */ return;
        if (clicked == null || clicked.getType() == Material.AIR || !GeneralUtil.isArmor(clicked))
            /*     */ return;
        /*  98 */
        NBTWrapper cursorWrapper = null;
        /*  99 */
        String cosmicType = null;
        /* 100 */
        if (cursor.getType() == Material.INK_SACK && (cosmicType = (cursorWrapper = new NBTWrapper(cursor)).getString("cosmicType")).equals("HEROIC_ARMOR_UPGRADE")) {
            /* 101 */
            event.setCancelled(true);
            /* 102 */
            if (!player.getWorld().getName().equals(((World) Bukkit.getWorlds().get(0)).getName()) && !player.getName().equals("world_end")) {
                /* 103 */
                player.sendMessage(CC.Red + "(!) " + CC.Red + "You must apply this Upgrade in the Overworld!");
                /*     */
                /*     */
                return;
                /*     */
            }
            /* 107 */
            NBTTagCompoundBuilder builder = cursorWrapper.getCompound("cosmicData");
            /* 108 */
            if (builder == null)
                /*     */ return;
            /* 110 */
            String setType = builder.getString("setType");
            /* 111 */
            if (setType != null && !setType.isEmpty()) {
                /*     */
                /* 113 */
                boolean allSetsAllowed = setType.equalsIgnoreCase("ALL");
                /* 114 */
                ArmorSet requiredSet = ArmorSet.fromName(setType);
                /*     */
                /* 116 */
                ArmorSet clickedArmorSet = ArmorSet.getArmorSetFromItem(clicked);
                /*     */
                /*     */
                /* 119 */
                boolean correctApplication = ((allSetsAllowed && clickedArmorSet != null) || (requiredSet != null && requiredSet.equals(clickedArmorSet)));
                /* 120 */
                if (!correctApplication) {
                    /* 121 */
                    player.sendMessage(CC.RedB + "(!) " + CC.Red + "You must apply this Heroic Armor Upgrade to a " + ((allSetsAllowed || requiredSet == null) ? "" :
                            /* 122 */               StringUtils.capitaliseAllWords(requiredSet.name().replace("_", " ").toLowerCase())) + CC.Red + " Armor Set piece!");
                    /*     */
                    /*     */
                    return;
                    /*     */
                }
                /* 126 */
                HeroicArmorType type = HeroicArmorType.getFromMaterial(clicked.getType());
                /* 127 */
                if (!clicked.getType().name().contains("DIAMOND_") || type == null) {
                    /* 128 */
                    player.sendMessage(CC.RedB + "(!) " + CC.Red + "You cannot apply this to non diamond armor set pieces!");
                    /*     */
                    /*     */
                    return;
                    /*     */
                }
                /* 132 */
                Long iid = null;
                /* 133 */
                if (ArkPlugins.ITEM_ID.getArkPlugin().isRunning()) {
                    /* 134 */
                    iid = Long.valueOf(ItemID.getItemID(cursor));
                    /* 135 */
                    if (iid.longValue() != -1L && ItemID.blacklist_iid.contains(iid)) {
                        /* 136 */
                        player.sendMessage(CC.RedB + "(!) " + CC.Red + "That Armor Upgrade has already been used!");
                        /* 137 */
                        player.getInventory().removeItem(new ItemStack[]{cursor});
                        /* 138 */
                        player.setItemOnCursor(null);
                        /* 139 */
                        player.updateInventory();
                        /* 140 */
                        Bukkit.getLogger().info("[CosmicArmorSets] Removing blacklisted armor upgrade from " + player.getName() + ": " + cursor);
                        /*     */
                        return;
                        /*     */
                    }
                    /*     */
                }
                /* 144 */
                double chance = builder.getDouble("success").doubleValue();
                /* 145 */
                if (chance > 100.0D)
                    /* 146 */ chance = 100.0D;
                /* 147 */
                if (chance < 0.0D) {
                    /* 148 */
                    chance = 0.0D;
                    /*     */
                }
                /* 150 */
                if (cursor.getAmount() > 1) {
                    /* 151 */
                    cursor.setAmount(cursor.getAmount() - 1);
                    /*     */
                } else {
                    /* 153 */
                    player.setItemOnCursor(null);
                    /* 154 */
                    player.updateInventory();
                    /* 155 */
                    if (iid != null && ArkPlugins.ITEM_ID.getArkPlugin().isRunning()) {
                        /* 156 */
                        ItemID.blacklistIID(iid.longValue(), "armorset upgrade " + player.getName() + iid);
                        /*     */
                    }
                    /*     */
                }
                /*     */
                /* 160 */
                if (chance >= (ThreadLocalRandom.current().nextInt(100) + 1)) {
                    /* 161 */
                    Bukkit.getLogger().info("Upgrading " + player.getName() + "'s " + clicked + " item to heroic version!");
                    /* 162 */
                    ParticleEffect.playEffect(ParticleEffect.HAPPY_VILLAGER, player.getLocation().add(0.0D, 0.4D, 0.0D), 0.1F, 30);
                    /* 163 */
                    player.getWorld().playSound(player.getLocation(), Sound.VILLAGER_YES, 1.4F, 0.9F);
                    /* 164 */
                    player.getWorld().playSound(player.getLocation(), Sound.LEVEL_UP, 1.4F, 0.9F);
                    ItemStack retItem = CosmicArmorSetsAPI.convertItemToHeroic(clicked, type, true);
                    /* 167 */
                    NBTWrapper wrapper = new NBTWrapper(retItem);
                    /* 168 */
                    NBTTagCompoundBuilder display = wrapper.hasKey("display") ? new NBTTagCompoundBuilder(wrapper.getNBTBase("display")) : new NBTTagCompoundBuilder();
                    /*     */
                    /* 170 */
                    int color = 0;
                    /*     */
                    /* 172 */
                    if (clickedArmorSet == ArmorSet.ULTRA) {
                        /* 173 */
                        color = 0xB8B4B3;
                        /* 174 */
                    } else if (clickedArmorSet == ArmorSet.CHAMPION) {
                        /* 175 */
                        color = 0xFA9012;
                        /* 176 */
                    } else if (clickedArmorSet == ArmorSet.ANCIENT) {
                        /* 177 */
                        color = 0x37B5B3;
                        /* 178 */
                    } else if (clickedArmorSet == ArmorSet.MOTHER_OF_YIJKI) {
                        /* 179 */
                        color = 16777215;
                        /* 180 */
                    } else if (clickedArmorSet == ArmorSet.KOTH) {
                        /* 181 */
                        color = 31999;
                        /* 182 */
                    } else if (clickedArmorSet == ArmorSet.RANGER) {
                        /* 183 */
                        color = 5765888;
                        /* 184 */
                    } else if (clickedArmorSet == ArmorSet.SUPREME) {
                        /* 185 */
                        color = 8264475;
                        /* 186 */
                    } else if (clickedArmorSet == ArmorSet.DRAGON_SLAYER) {
                        /* 187 */
                        color = 16772608;
                        /* 188 */
                    }else if (clickedArmorSet == ArmorSet.DIMENSIONAL_TRAVELER) {
                        /* 177 */             color = 12001978;
                    } else if (clickedArmorSet == ArmorSet.PHANTOM) {
                        /* 189 */
                        color = 14294340;
                        /* 190 */
                    } else if (clickedArmorSet == ArmorSet.YETI) {
                        /* 191 */
                        color = 518113;
                        /* 192 */
                    } else if (clickedArmorSet == ArmorSet.ENGINEER) {
                        /* 193 */
                        color = 15176967;
                        /* 194 */
                    } else if (clickedArmorSet == ArmorSet.OMNI) {
                        /* 195 */
                        color = 2765648;
                        /* 196 */
                    }
                    /*     */
                    /* 200 */
                    display.setInt("color", Integer.valueOf(color));
                    /* 201 */
                    wrapper.setNBTBase("display", (NBTBase) display.get());
                    /* 202 */
                    retItem = wrapper.build();
                    /* 165 */
                    event.setCurrentItem(retItem);
                    /* 166 */
                    player.sendMessage(CC.GreenB + "(!) " + CC.Green + "You have upgraded your " + ItemUtils.getDisplayName(clicked) + CC.Green + " to the Heroic Armor version!");
                    /*     */
                } else {
                    /* 168 */
                    player.sendMessage(CC.RedB + "(!) " + CC.Red + "You failed to apply your " + ItemUtils.getDisplayName(cursor) + CC.Red + "!");
                    /* 169 */
                    player.playSound(player.getLocation(), Sound.LAVA_POP, 1.0F, 0.9F);
                    /* 170 */
                    ParticleEffect.playEffect(ParticleEffect.LAVA, player.getLocation().add(0.0D, 0.4D, 0.0D), 0.1F, 30);
                    /* 171 */
                    CosmicArmorSetsPlugin.get_().getLogger().info(player.getName() + " failed to apply Heroic Armor Upgrade!");
                    /*     */
                }
                /*     */
            }
            /*     */
        }
        /*     */
    }
    /*     */
}


/* Location:              C:\Users\tucke\Desktop\CosmicArmorSets.jar!\com\compilex\CosmicArmorSets\listeners\ArmorSetUpgradeListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */