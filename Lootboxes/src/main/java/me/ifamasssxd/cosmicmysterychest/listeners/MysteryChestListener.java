/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.compat.ASound
 *  com.cosmicpvp.cosmicutils.nbt.NBTTagCompoundBuilder
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.cosmicpvp.cosmicutils.utils.NBTWrapper
 *  org.arkhamnetwork.Arkkit.patches.ItemID
 *  org.arkhamnetwork.Arkkit.plugins.ArkPlugins
 *  org.bukkit.Bukkit
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.World
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.block.Action
 *  org.bukkit.event.block.BlockPlaceEvent
 *  org.bukkit.event.player.PlayerCommandPreprocessEvent
 *  org.bukkit.event.player.PlayerInteractEntityEvent
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.event.player.PlayerJoinEvent
 *  org.bukkit.inventory.ItemStack
 */
package me.ifamasssxd.cosmicmysterychest.listeners;

import com.conaxgames.customskulls.CoreSkulls;
import com.conaxgames.customskulls.ability.AbilityData;
import com.conaxgames.customskulls.blocks.CustomBlock;
import com.conaxgames.customskulls.util.CustomBlockUtil;
import com.cosmicpvp.cosmicutils.compat.ASound;
import com.cosmicpvp.cosmicutils.nbt.NBTTagCompoundBuilder;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.FactionUtils;
import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import com.massivecraft.factions.FPlayer;
import me.ifamasssxd.cosmicmysterychest.CosmicMysteryChest;
import me.ifamasssxd.cosmicmysterychest.exceptions.MysteryChestException;
import me.ifamasssxd.cosmicmysterychest.guis.ActiveMysteryChestGUI;
import me.ifamasssxd.cosmicmysterychest.guis.MysteryChestGUI;
import me.ifamasssxd.cosmicmysterychest.guis.MysteryChestItemViewerGUI;
import me.ifamasssxd.cosmicmysterychest.struct.GlobalMysteryChest;
import me.ifamasssxd.cosmicmysterychest.struct.MysteryChest;
import org.arkhamnetwork.Arkkit.patches.ItemID;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugins;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class MysteryChestListener
        implements Listener {
    @EventHandler
    public void onEntityView(PlayerInteractEntityEvent event) {
        if (event.getRightClicked() instanceof Player && event.getRightClicked().hasMetadata("NPC") && ((Player) event.getRightClicked()).getName().equalsIgnoreCase("Lootbox Merchant")) {
            event.setCancelled(true);
            GlobalMysteryChest chest = CosmicMysteryChest.get().getChestManager().getActiveMysteryChest();
            if (chest != null) {
                new ActiveMysteryChestGUI(event.getPlayer(), CosmicMysteryChest.get().getChestManager().getActiveMysteryChest()).openGUI(CosmicMysteryChest.get());
                ASound.BAT_TAKEOFF.play(event.getPlayer(), 1.1f);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerPreProcess(PlayerCommandPreprocessEvent event) {
        if (event.getMessage() == null) {
            return;
        }
        String msg = event.getMessage().toLowerCase();
        if (msg.startsWith("/lootbox")) {
            event.setMessage(msg.replace("/lootbox", "/mc"));
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerBlock(BlockPlaceEvent event) {
        NBTTagCompoundBuilder builder;
        NBTWrapper wrapper;
        if (event.getItemInHand() != null && event.getItemInHand().getType() != Material.AIR && (wrapper = new NBTWrapper(event.getItemInHand())).hasKey("conaxData") && (builder = wrapper.getCompound("conaxData")) != null && builder.hasKey("mysteryChest")) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(CC.RedB + "(!) " + CC.Red + "You cannot place a Lootbox!");
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onNBTInteract(PlayerInteractEvent event) {
        ItemStack d = new ItemStack(Material.DIAMOND);
        if (d.getAmount() > 1) {
            event.setCancelled(false);
        }

        if ((event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK || event.getAction() == Action.LEFT_CLICK_AIR) && event.hasItem()) {
            ItemStack item = event.getItem().clone();
            if (item == null || item.getType() == Material.AIR) {
                return;
            }

            NBTTagCompoundBuilder builder = null;
            NBTWrapper wrapper = new NBTWrapper(item);
            try {
                builder = wrapper.getCompound("conaxData");
            } catch (NullPointerException ignored) {
            }
            if (builder == null) {
                try {
                    builder = wrapper.getCompound("conaxData");
                } catch (NullPointerException ignored) {
                }
            }
            if (builder != null && builder.hasKey("mysteryChest")) {
                event.setCancelled(true);
                Player player = event.getPlayer();
                try {
                    if (!player.getWorld().getName().equals(((World) Bukkit.getWorlds().get(0)).getName())) {
                        throw new MysteryChestException("You must open this Lootbox in the Overworld!");
                    }

                    MysteryChest chest = CosmicMysteryChest.get().getChestManager().getMysteryChestMap().get(builder.getString("mysteryChest"));
                    int bundleSize = chest.getItemBundleSize();
                    if (bundleSize == 0) {
                        bundleSize = chest.getGarunteedItems().size();
                    }
                    if (getEmptySlots(player) < bundleSize) {
                        throw new MysteryChestException(CC.translate("&c&l(!)&c Please clear some inventory slots before opening this lootbox!"));
                    }
                    if (chest == null) {
                        throw new MysteryChestException("Unable to determine Lootbox!");
                    }
                    if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {
                        new MysteryChestItemViewerGUI(player, chest).openGUI(CosmicMysteryChest.get());
                        return;
                    }
                    int amt = event.getItem().getAmount();
                    if (amt == 1) {
                        player.setItemInHand(null);
                    } else if (amt > 1) {
                        ItemStack i = event.getItem();
                        i.setAmount(i.getAmount() - 1);
                        player.setItemInHand(i);
                    }
                    if (ArkPlugins.ITEM_ID.getArkPlugin().isRunning()) {
                        if (ItemID.isItemBlacklisted((ItemStack) item)) {
                            Bukkit.getLogger().info("[CosmicMysteryChest] Removing duped lootbox: " + (Object) item + " from " + player.getName() + "!");
                            throw new MysteryChestException("Lootbox has already been opened!");
                        }
                        long id = ItemID.getItemID((ItemStack) item);
                        if (id > 0L) {
                            ItemID.blacklistIID((long) id, (String) ("lootboxOpen: " + player.getName() + " " + chest.getInternalName()));
                        }
                    }
                    NBTWrapper nbtWrapper = new NBTWrapper(item);
                    /*long expiration = 0L;
                    NBTTagCompoundBuilder data = nbtWrapper.getconaxData();
                    expiration = data.getLong("expiration");
                    if (expiration > 0L && expiration < System.currentTimeMillis()) {
                        String since = TimeUtil.formatTimeSincePastTime(expiration, true);
                        player.sendMessage("");
                        player.sendMessage(CC.RedB + "(!) " + CC.Red + "You attempted to claim a Lootbox that expired " + since + " ago!");
                        player.sendMessage(CC.Gray + "It has been discarded as it is no longer valid!");
                        player.sendMessage("");
                        return;
                    }*/
                    player.playSound(player.getLocation(), Sound.LEVEL_UP, 1.0f, 3.0f);


                    if (CustomBlockUtil.chunkHasCertainBlock(player, "HALLOWEEN_CANDLES") && Math.random() < 0.075) {
                        FPlayer killer = FactionUtils.getFPlayer(player);
                        for (AbilityData data : CustomBlockUtil.getBlockDatasInChunk(player)) {
                            FPlayer owner = FactionUtils.getFPlayer(Bukkit.getPlayer(data.getOwner()));
                            CustomBlock customBlock = CoreSkulls.customBlocks.get(data.getAbilityType().getName());
                            if (killer.getFaction() == null) return;
                            if (customBlock == null) return;
                            if (owner.getFaction() == null) return;
                            if (killer.getFaction() == owner.getFaction() && customBlock.getIdentifier().equals("HALLOWEEN_CANDLES")) {
                                player.getInventory().addItem(chest.createClaimableItem(false));
                                player.sendMessage("");
                                player.sendMessage(CC.translate("&6&l* HALLOWEEN CANDLE CUSTOM BLOCK BONUS *"));
                                player.sendMessage(CC.translate("&a&l+ " + chest.getHandDisplay().getItemMeta().getDisplayName()));
                                player.sendMessage("");
                                player.playSound(player.getLocation(), Sound.BLAZE_DEATH, 10, 10);
                            }
                        }
                    }

                    new MysteryChestGUI(player, chest, 0L).skipGUI(CosmicMysteryChest.get());


                } catch (MysteryChestException exception) {
                    player.sendMessage(CC.RedB + CC.Red + exception.getMessage());
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void joinEvent(PlayerJoinEvent e) {
        CosmicMysteryChest.get().getShardAnimations().anotoggle(e.getPlayer());
    }

    public int getEmptySlots(Player p) {
        PlayerInventory inventory = p.getInventory();
        ItemStack[] cont = inventory.getContents();
        int i = 0;
        for (ItemStack item : cont)
            if (item != null && item.getType() != Material.AIR) {
                i++;
            }
        return 36 - i;
    }
}

