package net.astropvp.core.listeners;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.WorldGuardUtils;
import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import net.astropvp.core.utils.ChatManager;
import org.arkhamnetwork.Arkkit.patches.combatlog.CombatLog;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.*;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.ItemStack;

public class ServerFixes implements Listener {

    @EventHandler
    public void onEntitySpawn2(EntityTargetEvent event) {
        if (event.getEntity() instanceof org.bukkit.entity.Blaze || event.getEntity() instanceof org.bukkit.entity.Creeper
                || event.getEntity() instanceof org.bukkit.entity.Skeleton)
            event.setCancelled(true);
    }

    @EventHandler
    public void onEntitySpawn3(EntitySpawnEvent event) {
        if (event.getEntity() instanceof org.bukkit.entity.IronGolem && !event.getEntity().hasMetadata("guardianSummoner")) {
            ((IronGolem) event.getEntity()).setHealth(1);
        }
    }

    @EventHandler
    public void onDmg(EntityDamageEvent e) {
        Entity ent = e.getEntity();
        if (ent instanceof org.bukkit.entity.Blaze &&
                e.getCause() == EntityDamageEvent.DamageCause.DROWNING)
            e.setCancelled(true);
    }

    @EventHandler
    public void Interact(InventoryClickEvent event){
        HumanEntity player = event.getWhoClicked();

        if((player instanceof Player)) {
            if (event.getInventory().getType() == InventoryType.ANVIL) {
                event.setCancelled(true);
                Player clicker = (Player) event.getWhoClicked();
                clicker.sendMessage("You cannot use anvils.");
            }
        }
    }




    


    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        e.setJoinMessage(null);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        if(e.getBlock().getType() == Material.HOPPER) {
            e.getPlayer().sendMessage(CC.translate("&c&l(!)&c You cannot place hoppers, buy chunk collectors in /shop instead!"));
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        e.setQuitMessage(null);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void cmdFix(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        if (event.getMessage().toLowerCase().startsWith("/pex") || event.getMessage().toLowerCase().startsWith("/permissionsex") || event.getMessage().toLowerCase().startsWith("/ea") || event.getMessage().toLowerCase().startsWith("/ae") || event.getMessage().toLowerCase().startsWith("/voucher") || event.getMessage().toLowerCase().startsWith("/el") || event.getMessage().toLowerCase().startsWith("/em") || event.getMessage().toLowerCase().startsWith("/elitelootbox") || event.getMessage().toLowerCase().startsWith("/elitemasks") || event.getMessage().toLowerCase().startsWith("/elitearmor") || event.getMessage().toLowerCase().startsWith("/advancedenchantments") || event.getMessage().toLowerCase().equals("/pl") || event.getMessage().toLowerCase().startsWith("/plugins") || event.getMessage().toLowerCase().startsWith("/souls") || event.getMessage().toLowerCase().startsWith("/soul") || event.getMessage().toLowerCase().startsWith("/buysouls") || event.getMessage().toLowerCase().startsWith("/buysoul") || event.getMessage().toLowerCase().startsWith("/icanhasbukkit") || event.getMessage().toLowerCase().startsWith("/advancedenchantments") || event.getMessage().toLowerCase().startsWith("/about") || event.getMessage().toLowerCase().startsWith("/advancedenchantments") || event.getMessage().toLowerCase().startsWith("/calc") || event.getMessage().toLowerCase().startsWith("/calc") || event
                .getMessage().toLowerCase().startsWith("//eval") || event.getMessage().toLowerCase().startsWith("/eval") || event.getMessage().toLowerCase().startsWith("/alchemist") || event
                .getMessage().toLowerCase().startsWith("/me") || event.getMessage().toLowerCase().startsWith("/eme") || event.getMessage().toLowerCase().startsWith("/tpets") || event.getMessage().toLowerCase().startsWith("/mem") || event
                .getMessage().toLowerCase().startsWith("/smite") || event.getMessage().toLowerCase().startsWith("/lightning") || event.getMessage().toLowerCase().startsWith("/esmite") || event.getMessage().toLowerCase().startsWith("/elightning") || event
                .getMessage().toLowerCase().startsWith("/tpets") || event.getMessage().toLowerCase().startsWith("/treasurepets") || event.getMessage().toLowerCase().startsWith("/emem") || event.getMessage().toLowerCase().startsWith("/crate") || event.getMessage().toLowerCase().startsWith("/crates") || event.getMessage().toLowerCase().startsWith("/cc") || event.getMessage().toLowerCase().startsWith("/crazycrates") || event.getMessage().toLowerCase().startsWith("/kill") || event.getMessage().toLowerCase().startsWith("/ekill") || event.getMessage().toLowerCase().startsWith("/?") || event.getMessage().toLowerCase().startsWith("/ehelp") || event.getMessage().toLowerCase().startsWith("//calc") || event.getMessage().toLowerCase().startsWith("/plugman") || event.getMessage().toLowerCase().startsWith("/minecraft") || event.getMessage().toLowerCase().startsWith("/spigot") || event.getMessage().toLowerCase().startsWith("/bukkit") || event.getMessage().toLowerCase().startsWith("/asets") || event.getMessage().toLowerCase().startsWith("/advanced") || event.getMessage().toLowerCase().startsWith("/ce") || event.getMessage().toLowerCase().startsWith("/armor")) {
            if (player.hasPermission("commandfix.bypass"))
                return;
            event.setCancelled(true);
            player.sendMessage(ChatManager.translate("&f&l<&a&lAstro&e&lPvP&f&l> &7Unknown Command. Type &f\"/help\" &7for help."));
        }
    }


    @EventHandler
    public void playerDamage(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player && event.getDamager().getType() == EntityType.PRIMED_TNT)
            event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (player.getItemInHand().getType().equals(Material.EYE_OF_ENDER)) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerInteractSnowball(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (player.getItemInHand().getType().equals(Material.SNOW_BALL) && player.getItemInHand().getItemMeta().hasLore()) {
                player.sendMessage(CC.translate("&cYou can't throw omni crystals!"));
                e.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerCommand(final PlayerCommandPreprocessEvent event) {
        final Player player = event.getPlayer();
        final String msg = event.getMessage().toLowerCase();
        if (CombatLog.inCombat(player) && msg.startsWith("/dis") || CombatLog.inCombat(player) && msg.startsWith("/d")) {
            event.setCancelled(true);
            player.sendMessage(ChatColor.RED + "You cannot use that command while in combat!");
        }
    }


    @EventHandler
    public void onFallDamage(EntityDamageEvent e) {
        if (e.getCause() == EntityDamageEvent.DamageCause.FALL && e.getEntity() instanceof Player)
            e.setCancelled(true);
    }


    @EventHandler
    public void onEntityDamage(EntityDamageEvent e) {
        if (e.getEntity().getType() == EntityType.DROPPED_ITEM)
            e.setCancelled(true);
    }

    @EventHandler
    public void disableHopper(InventoryPickupItemEvent e) {
        if (e.getInventory().getType() != InventoryType.HOPPER) {
            return;
        }
        if (e.getInventory().getType() == InventoryType.HOPPER) {
            e.setCancelled(true);
        }
    }


    @EventHandler
    public void craftItem(PrepareItemCraftEvent e) {
        Material itemType = e.getRecipe().getResult().getType();
        Byte itemData = Byte.valueOf(e.getRecipe().getResult().getData().getData());
        if (itemType == Material.BOAT || itemType == Material.BOOK_AND_QUILL || itemType == Material.GOLDEN_APPLE || itemType == Material.HOPPER) {
            e.getInventory().setResult(new ItemStack(Material.AIR));
            for (HumanEntity he : e.getViewers()) {
                if (he instanceof Player)
                    ((Player) he).sendMessage(ChatColor.RED + "You are not allowed to craft this!");
            }
        }
    }

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent e) {
        if (e.toWeatherState()) {
            e.setCancelled(true);
        }
    }


    @EventHandler
    public void onLoseHunger(FoodLevelChangeEvent e) {
        if (e.getEntity() instanceof Player)
            e.setCancelled(true);
    }

    @EventHandler
    public void tntDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player && e
                .getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION)
            e.setCancelled(true);
    }

    /*@EventHandler(priority = EventPriority.HIGHEST)
    public void damageModi(EntityDamageByEntityEvent e) {
        if (!(e.getEntity() instanceof Player) || !(e.getDamager() instanceof Player)) {
            return;
        }
        if (e.getDamager() instanceof Player) {
            Player p = (Player) e.getDamager();
            if (p.getItemInHand().getType() == Material.STONE_SWORD || p.getItemInHand().getType() == Material.WOOD_SWORD || p.getItemInHand().getType() == Material.IRON_SWORD) {
                e.setDamage(-100);
            }
        }
    }*/



    @EventHandler(priority = EventPriority.HIGHEST)
    public void cancelCreeperDMG(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player && event.getDamager().getType() == EntityType.CREEPER)
            event.setCancelled(true);
    }



    @EventHandler
    public void onTrade(InventoryOpenEvent e){
        if(e.getInventory().getType() == InventoryType.MERCHANT){
            e.setCancelled(true);
        }
    }


}
