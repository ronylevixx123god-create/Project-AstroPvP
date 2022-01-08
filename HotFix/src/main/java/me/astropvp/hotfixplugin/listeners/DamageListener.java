package me.astropvp.hotfixplugin.listeners;

import com.cosmicpvp.cosmicutils.nbt.NBTTagCompoundBuilder;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.FactionUtils;
import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.Faction;
import me.astropvp.astropvpskins.AstroItemSkinsAPI;
import me.astropvp.astropvpskins.enums.SkinType;
import me.ifamasssxd.cosmicsky.inventorypets.CosmicSkyInventoryPets;
import org.arkhamnetwork.enchantments.enchantments.swords.Block;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerVelocityEvent;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class DamageListener implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        if (event.getMessage().toLowerCase().contains("jndi")) {
            event.setMessage("/null");
            event.setCancelled(true);
        }
    }

/*    @EventHandler(priority = EventPriority.MONITOR)
    public void diaSwordFix(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player) {
            Player damager = (Player) e.getDamager();
            if (damager.getItemInHand() != null) {
                NBTWrapper wrapper = new NBTWrapper(damager.getItemInHand());
                NBTTagCompoundBuilder cosmicData = wrapper.hasKey("cosmicData") ? wrapper.getCompound("cosmicData") : new NBTTagCompoundBuilder();
                if(cosmicData.hasKey("dmgIncr"))
                    e.setDamage(e.getDamage() + cosmicData.getDouble("dmgIncr"));
            }
        }
    }*/



/*    @EventHandler
    public void diaPvP(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
            Player damager = (Player) e.getDamager();
            Player damaged = (Player) e.getEntity();


            if (damaged.getInventory().getHelmet() != null) {
                if (damaged.getInventory().getHelmet().getType() == Material.DIAMOND_HELMET) {
                    damaged.setHealth(damaged.getHealth() - 18);
                }
            } else if (damaged.getInventory().getChestplate() != null) {
                if (damaged.getInventory().getChestplate().getType() == Material.DIAMOND_CHESTPLATE) {
                    damaged.setHealth(damaged.getHealth() - 18);
                }
            } else if (damaged.getInventory().getLeggings() != null) {
                if (damaged.getInventory().getLeggings().getType() == Material.DIAMOND_LEGGINGS) {
                    damaged.setHealth(damaged.getHealth() - 18);
                }
            } else if (damaged.getInventory().getBoots() != null) {
                if (damaged.getInventory().getBoots().getType() == Material.DIAMOND_BOOTS) {
                    damaged.setHealth(damaged.getHealth() - 18);
                }
            }
        }
    }*/

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        if ((e.getBlock().getType() == Material.BEDROCK && !e.getPlayer().isOp())) {
            e.getPlayer().sendMessage(CC.translate("&cYou are not allowed to place this block!"));
            e.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onIllegalSandStacking(EntityChangeBlockEvent event) {
        if (event.getTo() != Material.SAND) return;
        Faction faction = Board.getInstance().getFactionAt(new FLocation(event.getBlock()));
        if (faction.isWarZone() || faction.isSafeZone()) event.setCancelled(true);
    }

    @EventHandler
    public void onPvPInInvis(EntityDamageByEntityEvent e) {
        if (!(e.getEntity() instanceof Player)) return;
        if (!(e.getDamager() instanceof Player)) return;
        if (((Player) e.getDamager()).getGameMode() != GameMode.SURVIVAL) return;
        Player victim = (Player) e.getEntity();
        if (victim.hasPotionEffect(PotionEffectType.INVISIBILITY)) {
            victim.removePotionEffect(PotionEffectType.INVISIBILITY);
        }


    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onDurabilityLoss(PlayerItemDamageEvent event) {
        event.setCancelled(true);
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

