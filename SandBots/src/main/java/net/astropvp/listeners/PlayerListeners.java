// 
// Decompiled by Procyon v0.5.36
// 

package net.astropvp.listeners;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.FactionUtils;
import net.astropvp.inventories.FactionManageGui;
import net.astropvp.inventories.RemoveGUI;
import net.astropvp.inventories.SandBotGui;
import net.astropvp.items.SandBotItem;
import net.astropvp.SandBots;
import net.astropvp.enums.GuiType;
import net.astropvp.managers.SandBotManager;
import net.astropvp.utils.Utils;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import java.util.Collection;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ArmorStand;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

public class PlayerListeners implements Listener
{
    private SandBots plugin;

    public PlayerListeners(final SandBots plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents((Listener)this, (Plugin)plugin);
    }
    
    @EventHandler
    public void onPlaceSandBot(final PlayerInteractEvent e) {
        final Player player = e.getPlayer();
        final ItemStack item = player.getInventory().getItemInHand();
        if (e.getAction().equals((Object)Action.RIGHT_CLICK_BLOCK) && SandBotItem.isSandBot(item)) {
            e.setCancelled(true);
            final Block blockclick = e.getClickedBlock();
            final Location loc = blockclick.getLocation();
            loc.setY(loc.getY() + 1.0);
            loc.setZ(loc.getZ() + 0.5);
            loc.setX(loc.getX() + 0.5);
            Utils.spawnBot(player, loc);
            player.sendMessage("");
            player.sendMessage(CC.translate("&a&l(!)&a You have placed a &e&lSandbot&a."));
            player.sendMessage(CC.translate("&7&o(( You can manage the bot by clicking it or typing &f/f sandbot&7&o. ))"));
            player.sendMessage("");
        }
    }
    
    @EventHandler
    public void onBlockPlace(final BlockPlaceEvent event) {
        if (event.getBlock().getType() == Material.LAPIS_BLOCK) {
            for (final SandBotManager bot : SandBots.sandBots) {
                if (bot.getLocation().toVector().distance(event.getBlock().getLocation().toVector()) <= 10 && FactionUtils.getFactionAt(event.getBlock().getLocation()).getId() != null && FactionUtils.getFactionAt(event.getBlock().getLocation()).getId().equals(bot.getFactionID())) {
                    bot.addBlock(event.getBlock());
                }
            }
        }
    }
    
    @EventHandler
    public void onBlockBreak(final BlockBreakEvent event) {
        if (event.getBlock().getType() == Material.LAPIS_BLOCK) {
            for (final SandBotManager bot : SandBots.sandBots) {
                if (bot.getLocation().toVector().distance(event.getBlock().getLocation().toVector()) <= 10 && FactionUtils.getFactionAt(event.getBlock().getLocation()).getId() != null && FactionUtils.getFactionAt(event.getBlock().getLocation()).getId().equals(bot.getFactionID())) {
                    bot.removeBlock(event.getBlock());
                }
            }
        }
    }
    
    @EventHandler
    public void onDamageEvent(final EntityDamageEvent e) {
        final Entity armorstand = e.getEntity();
        SandBotManager bot = null;
        if (armorstand instanceof ArmorStand) {
            for (final SandBotManager bottmp : SandBots.sandBots) {
                if (bottmp.getSandbotUUID().equals(armorstand.getUniqueId())) {
                    bot = bottmp;
                    break;
                }
            }
            if (bot != null) {
                e.setCancelled(true);
            }
        }
    }
    
    @EventHandler
    public void onExplode(final EntityExplodeEvent e) {
        final Collection<Entity> entities = e.getLocation().getWorld().getNearbyEntities(e.getLocation(), 10.0, 10.0, 10.0);
        if (entities.stream().anyMatch(entity -> entity.getType().equals((Object)EntityType.ARMOR_STAND))) {
            SandBotManager bot = null;
            for (final SandBotManager bottmp : SandBots.sandBots) {
                if (entities.stream().anyMatch(entity -> entity.getUniqueId().equals(bottmp.getSandbotUUID()))) {
                    bot = bottmp;
                    break;
                }
            }
            if (bot != null) {
                e.setCancelled(true);
                final ItemStack botdropItem = SandBotItem.get();
                Utils.dropitem(bot.getLocation(), botdropItem);
                Utils.removeBot(bot);
            }
        }
    }
    
    @EventHandler
    public void onPlayerInteractAtEntity(final PlayerInteractAtEntityEvent event) {
        final Player player = event.getPlayer();
        final Entity armorstand = event.getRightClicked();
        SandBotManager bot = null;
        if (armorstand.getType().equals((Object)EntityType.ARMOR_STAND)) {
            for (final SandBotManager bottmp : SandBots.sandBots) {
                if (bottmp.getSandbotUUID().equals(armorstand.getUniqueId())) {
                    bot = bottmp;
                    break;
                }
            }
            if (bot != null) {
                event.setCancelled(true);
                if (!player.isOp()) {
                    if (!bot.getFactionID().equals(FactionUtils.getFactionId(player))) {
                        player.sendMessage(CC.RedB + "(!) " + CC.Red + "You cant edit other factions sandbots");
                    }
                    else if (!bot.getFactionID().equals(FactionUtils.getFactionId(player))) {
                        ((RemoveGUI)SandBots.botGuis.get(bot).get(GuiType.REMOVEGUI)).open(player);
                    }
                    else {
                        ((SandBotGui)SandBots.botGuis.get(bot).get(GuiType.SANDBOTGUI)).open(player);
                    }
                }
                else {
                    ((SandBotGui)SandBots.botGuis.get(bot).get(GuiType.SANDBOTGUI)).open(player);
                }
            }
        }
    }

}
