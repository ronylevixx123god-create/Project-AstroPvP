package me.astropvp.hotfixplugin.listeners;

import com.cosmicpvp.cosmicutils.nbt.NBTTagCompoundBuilder;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.FactionUtils;
import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import me.astropvp.astropvpskins.AstroItemSkinsAPI;
import me.astropvp.astropvpskins.enums.SkinType;
import me.ifamasssxd.cosmicsky.inventorypets.CosmicSkyInventoryPets;
import org.arkhamnetwork.enchantments.enchantments.swords.Block;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.util.Vector;

public class DamageListener implements Listener {
    @EventHandler
    public void onAttack(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player) {
            if (((Player) e.getDamager()).getItemInHand() != null) {
                if (((Player) e.getDamager()).getItemInHand().getType().name().endsWith("_AXE"))
                    e.setDamage(e.getDamage() * 0.83);
            }
        }
    }

    @EventHandler
    public void diaPvP(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
            Player damager = (Player) e.getDamager();
            Player damaged = (Player) e.getEntity();

            if (damaged.getInventory().getHelmet() != null) {
                if (damaged.getInventory().getHelmet().getType() == Material.DIAMOND_HELMET) {
                    damaged.setHealth(damaged.getHealth()-18);
                }
            } else if (damaged.getInventory().getChestplate() != null) {
                if (damaged.getInventory().getChestplate().getType() == Material.DIAMOND_CHESTPLATE) {
                    damaged.setHealth(damaged.getHealth()-18);
                }
            } else if (damaged.getInventory().getLeggings() != null) {
                if (damaged.getInventory().getLeggings().getType() == Material.DIAMOND_LEGGINGS) {
                    damaged.setHealth(damaged.getHealth()-18);
                }
            } else if (damaged.getInventory().getBoots() != null) {
                if (damaged.getInventory().getBoots().getType() == Material.DIAMOND_BOOTS) {
                    damaged.setHealth(damaged.getHealth()-18);
                }
            }
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        if ((e.getBlock().getType() == Material.BEDROCK && !e.getPlayer().isOp())) {
            e.getPlayer().sendMessage(CC.translate("&cYou are not allowed to place this block!"));
            e.setCancelled(true);
        }
    }


/*    @EventHandler
    public void fixSlots(InventoryClickEvent e) {
        if (e.getWhoClicked() instanceof Player) {
            if (e.getCursor() != null) {
                if (e.getCursor().getType() != null && e.getCursor().getType() == Material.PAPER) {
                    NBTWrapper wrapper = new NBTWrapper(e.getCursor());
                    if (wrapper.hasKey("xpShopPrice")) {
                        wrapper.remove("xpShopPrice");
                        NBTTagCompoundBuilder builder = new NBTTagCompoundBuilder(wrapper.getNBTBase("cosmicData"));


                        e.setCursor(wrapper.setNBTBase("cosmicData", builder.get()).build());
                    }

                }
            }
        }
    }*/
}

